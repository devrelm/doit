# Releasing (plan)

Nothing here is implemented yet. This is the order to do it in, and how to keep signing keys safe in a public repo.

## Background: Android signing

Every Android app is signed. Updates must be signed with the same key, so **losing the key means you can never
update the app** under that ID, and **leaking it lets someone else ship "updates"** as you. Debug builds use an
auto-generated debug key on your machine, which is fine for development but not for releases.

## Phase 1 — sideloading (now)

- Run from Android Studio (see [local-development.md](local-development.md)), or
- install the `app-debug` APK artifact from any PR's CI run.

No secrets involved.

## Phase 2 — signed builds on GitHub Releases

1. Generate an **upload key** locally (Studio: *Build → Generate Signed App Bundle/APK → Create new*, or `keytool`).
   Store the `.jks` and its passwords in your password manager. Never commit it (`.gitignore` already blocks
   `*.jks`, `*.keystore`, `keystore.properties`).
2. Create a GitHub **Environment** named `release` (*Settings → Environments*):
   - **Required reviewers**: you. Every release job pauses until you click *Approve*.
   - **Deployment branches and tags**: only tags matching `v*`.
   - Add environment secrets (not repository secrets): `SIGNING_KEYSTORE_BASE64`, `SIGNING_KEYSTORE_PASSWORD`,
     `SIGNING_KEY_ALIAS`, `SIGNING_KEY_PASSWORD`.
3. Add `.github/workflows/release.yml`, triggered only by `push: tags: ['v*']`, with `environment: release`.
   It decodes the keystore to a temp file, runs `./gradlew bundleRelease assembleRelease`, deletes the keystore, and
   attaches the APK to a GitHub Release. `app/build.gradle.kts` reads signing config from environment variables
   (never from files in the repo).

Why this is safe: PR workflows can't reach environment secrets; only a tag push from someone with write access
starts the job, and it still waits for your approval.

## Phase 3 — Google Play

1. Create the app in Play Console. **Decide the final `applicationId` before the first upload** (it's permanent);
   today it's `com.devrelm.doit`.
2. Enroll in **Play App Signing**: Google holds the real app signing key; your key from Phase 2 becomes only the
   *upload key*. If it ever leaks, Google can reset it — much lower stakes.
3. Start with the **Internal testing** track (instant availability to listed testers, i.e. you).
4. Optional automation: a Play Console service account with release-manager access to just this app; store its JSON
   key as another `release` environment secret, and have `release.yml` upload the bundle (e.g. via Gradle Play
   Publisher).
5. New Play personal developer accounts must run a closed test with testers for a period before production access;
   check current Play Console requirements when you get here.

## Wear OS

The watch app will be a separate `:wear` module with the **same `applicationId`**, published in the same Play
listing, and signed with the same key, so the release workflow just builds both.
