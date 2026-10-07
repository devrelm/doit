# CLAUDE.md

DoIt is a personal TODO app for Android (Pixel phone first, Pixel Watch later). All data lives on the device.
Most changes are made by the Claude GitHub Action from issues and reviewed by the maintainer (@devrelm).

## Stack

- Kotlin, Jetpack Compose, Material 3 (dynamic color). Single `:app` module for now.
- AGP 9 with **built-in Kotlin**: do not apply `org.jetbrains.kotlin.android` in module build files.
- `minSdk 31`, `compileSdk`/`targetSdk 37`. JDK 21 to run Gradle; bytecode targets Java 17.
- All dependency versions live in `gradle/libs.versions.toml`. Never hard-code a version in a `build.gradle.kts`.

## Commands

Run this before every commit/push and fix anything it reports:

```
./gradlew assembleDebug testDebugUnitTest lintDebug
```

- `./gradlew testDebugUnitTest --tests 'com.devrelm.doit.SomeTest'` runs one test class.
- `./gradlew installDebug` installs on a running emulator/device (local only).

## Architecture conventions

- Package root `com.devrelm.doit`. Group by feature (`tasks/`, `lists/`, …), with `ui/theme/` shared.
- MVVM: a screen is a stateless `@Composable` that takes UI state + callbacks; a `ViewModel` exposes
  `StateFlow<UiState>` and handles events. Collect with `collectAsStateWithLifecycle()`.
- Persistence: Room (add it with the first feature that needs storage), accessed through a repository class;
  ViewModels never touch DAOs directly. Schema changes need a Room migration and an exported schema.
- Keep business logic in plain Kotlin classes so it can be covered by JVM unit tests (`app/src/test`).
  Every logic change comes with tests.
- User-visible strings go in `res/values/strings.xml`.
- When Wear OS work starts, shared model/data code moves to a `:core` module and a `:wear` app module is added.

## Hard rules

- **No network.** Do not add the `INTERNET` permission, analytics, crash reporting, ads, or any SDK that phones home.
- **Dependencies are a security boundary.** Prefer AndroidX/Kotlin libraries. Any new dependency must be justified
  in the PR description (what it does, why the platform can't, maintenance status).
- **You cannot edit `.github/workflows/`** (the Claude GitHub App has no `workflows` permission). If CI needs a change,
  describe it in the PR body for the maintainer to make.
- Never commit signing material (`*.jks`, `*.keystore`, `keystore.properties`) or anything secret.
- Keep PRs focused on one issue. Don't reformat unrelated code.
