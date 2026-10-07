# DoIt

My personal TODO app for Android — one I actually use to manage my time and tasks.

- Native Kotlin + Jetpack Compose.
- Everything is stored on the device. No account, no network access, no tracking.
- Pixel Watch support is planned.

## Running it

See [docs/local-development.md](docs/local-development.md) for a step-by-step guide to running the app in an
emulator on your computer (no prior Android experience assumed).

Every pull request also builds a debug APK, downloadable from the PR's **CI** run under *Artifacts*.

## How development works

Most changes are written by the [Claude GitHub Action](https://github.com/anthropics/claude-code-action):

1. Open an issue describing what you want, mentioning `@claude` (only the repo owner can trigger it).
2. Claude writes the code on a branch, builds and tests it, and opens a pull request.
3. CI and an automated review run on the PR; the maintainer reviews, approves and merges.

Project conventions for contributors (human or AI) are in [CLAUDE.md](CLAUDE.md).

## License

[Apache-2.0](LICENSE)
