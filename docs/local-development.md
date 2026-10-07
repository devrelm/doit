# Running DoIt locally

A walkthrough for running the app on a Mac, assuming no prior Android experience.

## Vocabulary

- **Android Studio** — the IDE. It bundles everything else below, so it's the only thing you install.
- **Android SDK** — the libraries and tools for building apps against each Android version ("API level").
  This app builds against **API 37**.
- **Gradle** — the build tool (think `npm` + `webpack`). `./gradlew` is a wrapper script that downloads the exact
  Gradle version this repo pins, like a lockfile for the build tool.
- **Emulator / AVD** — a virtual Android phone ("Android Virtual Device") that runs on your Mac.
- **APK** — the installable app package that a build produces.

## 1. Install Android Studio

```bash
brew install --cask android-studio
```

(or download it from <https://developer.android.com/studio>). Open it once installed.

## 2. First-launch setup

1. The Setup Wizard appears. Choose **Standard**, accept the SDK license agreements, and let it download
   (a few GB; takes a while).
2. From the Welcome screen: **More Actions → SDK Manager** (or, with a project open, *Settings → Languages &
   Frameworks → Android SDK*). On the **SDK Platforms** tab make sure **Android API 37** is checked; click
   **Apply**.

## 3. Open the project

1. **Open** → select the repository folder (the one containing `settings.gradle.kts`).
2. Trust the project when asked.
3. Wait for **Gradle sync** to finish (progress bar at the bottom). The first sync downloads Gradle and all
   dependencies, so it is slow; later ones are fast.

## 4. Create an emulator

1. Open **Device Manager** (phone icon in the right-hand toolbar, or *View → Tool Windows → Device Manager*).
2. **+** → **Create Virtual Device** → pick **Pixel 9** → **Next**.
3. Choose an **API 37** system image (on Apple-silicon Macs, the *arm64-v8a* one). Click the download icon next to
   it if needed → **Next** → **Finish**.

## 5. Run the app

1. In the top toolbar, make sure the run configuration says **app** and the device dropdown shows your emulator.
2. Click the green **▶ Run** button.
3. The emulator boots (first boot takes a minute) and DoIt launches. Code changes: click Run again.

## Trying out a pull request

```bash
gh pr checkout 12
```

(replace `12` with the PR number), or switch branches with the branch widget in Studio's top-left. Then click Run.
Studio may ask to sync Gradle again if build files changed.

## From the command line

Studio's bundled JDK can run the same build Claude and CI run:

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
```

```bash
./gradlew assembleDebug testDebugUnitTest lintDebug
```

With an emulator running, install the app onto it:

```bash
./gradlew installDebug
```

## Later: running on your own Pixel

1. On the phone: *Settings → About phone* → tap **Build number** seven times to unlock *Developer options*.
2. *Settings → System → Developer options* → enable **Wireless debugging**.
3. In Studio's device dropdown: **Pair Devices Using Wi-Fi**, then scan the QR code from the phone
   (*Wireless debugging → Pair device with QR code*). The phone (on the same Wi-Fi) now appears in the dropdown.
4. Select it and click Run.

Alternatively, download the `app-debug` artifact from a PR's CI run and install it directly; see
[releasing.md](releasing.md) for how proper releases will work.
