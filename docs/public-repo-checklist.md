# Making the repo public safely

What's already handled in the repo, what to switch on in GitHub settings, and the rules to keep following.

## Threat model in one paragraph

On a public repo anyone can open issues, comment, and send pull requests from forks. The assets worth protecting
are: the `CLAUDE_CODE_OAUTH_TOKEN` secret (your Claude subscription), write access to `main`, and — later — the app
signing key / Play Console credentials. The main attack paths are (1) a fork PR whose code runs in a workflow that
has secrets, (2) a stranger triggering Claude, and (3) prompt injection: text written by a stranger that Claude
reads and follows.

## Already in place (in this repo)

| Control | Where |
|---|---|
| CI that runs PR code has a read-only token and references no secrets | `.github/workflows/ci.yml` |
| Only the repo owner can trigger Claude (on top of the action's own write-access check) | `claude.yml` `if:` |
| Only the owner's and `claude[bot]`'s comments are fed to Claude (`include_comments_by_actor`) | `claude.yml` |
| Claude's shell access is limited to `./gradlew`, `gh pr create`, `gh pr view` | `claude.yml` `claude_args` |
| Auto-review skips fork PRs and drafts; only `claude[bot]` is an allowed bot (never `*`) | `claude-code-review.yml` |
| Every third-party action pinned to a full commit SHA | all workflows |
| Gradle wrapper jar checksum-validated in CI | `setup-gradle` (default) |
| Dependency/action updates proposed weekly, only after a 14-day cooldown | `.github/dependabot.yml` |
| The Claude GitHub App cannot modify workflow files | GitHub App permissions |
| Signing material ignored by git | `.gitignore` |

## Turn on in GitHub settings (before flipping to public)

**Settings → Actions → General**
- *Approval for running fork pull request workflows from contributors*: **Require approval for all external
  contributors**.
- *Workflow permissions*: **Read repository contents and packages permissions**.
- Uncheck **Allow GitHub Actions to create and approve pull requests**.

**Settings → Rules → Rulesets → New branch ruleset** targeting the default branch (`main`):
- **Restrict deletions**, **Block force pushes**.
- **Require a pull request before merging** with **1 required approval**. Claude's PRs are authored by
  `claude[bot]`, so your approval is the gate.
- **Require status checks to pass**: `build` (the CI job).
- **Bypass list**: *Repository admin* — so you can merge PRs you author yourself (e.g. workflow changes, which
  Claude cannot make), since GitHub doesn't let you approve your own PR.

**Settings → Code security**
- Enable **Secret scanning** and **Push protection**.
- Enable **Dependabot alerts** and **Dependabot security updates**.
- Enable **Private vulnerability reporting** (`SECURITY.md` points to it).

**Before the switch**: the history is tiny today, but run a final check that no secrets were ever committed
(`git log -p | grep -i -E 'key|token|secret|password'`).

## Rules to keep following

- **Never** use `pull_request_target` or `workflow_run` in a workflow that checks out PR code. They run with secrets.
- **Never** give a PR-triggered workflow access to signing or Play credentials. Those live only in a protected
  GitHub *Environment* used by the release workflow (see [releasing.md](releasing.md)).
- **Read before you `@claude` on someone else's issue.** Comment filtering doesn't cover the issue *body* itself.
  If a stranger files a good request, restate it in your own words (your own issue or comment) and point Claude at that.
- **Review `claude[bot]` PRs as untrusted code.** Pay extra attention to build files (`*.gradle.kts`,
  `libs.versions.toml`, `gradle/wrapper/*`), new dependencies, and anything touching permissions in
  `AndroidManifest.xml`.
- **Residual risk to know about:** Claude can run `./gradlew`, and Gradle build scripts are code, so a successful
  prompt injection could in principle run arbitrary commands on the runner, where the Claude token is present.
  The owner-only trigger and comment filtering are what keep untrusted text out; if the token is ever suspected
  leaked, revoke it (re-run `claude setup-token` / regenerate) and replace the secret.
- Don't enable `show_full_output` or Actions debug logging on a public repo — logs are public.
