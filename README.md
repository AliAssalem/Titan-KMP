# Titan

Titan is a cross-platform movie and TV show discovery app, built entirely with **Kotlin Multiplatform** and **Compose Multiplatform** to share a single codebase across **Android, iOS, Desktop, and Web**.

## Platforms

| Platform | Status |
|---|---|
| Android | ✅ Supported |
| iOS | ✅ Supported |
| Desktop (JVM) | ✅ Supported |
| Web (Wasm) | ✅ Supported (Room 3.0 + OPFS-backed persistence) |

## Tech Stack

- **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)** — shared business logic (repositories, use cases, view models) across all platforms
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)** — shared, native UI rendering across Android, iOS, Desktop, and Web (via Kotlin/Wasm)
- **[Room](https://developer.android.com/kotlin/multiplatform/room)** — local persistence layer, including Room 3.0 support for the Web target, backed by [OPFS](https://developer.mozilla.org/en-US/docs/Web/API/File_System_API/Origin_private_file_system) for durable, cross-session storage in the browser
- **[Ktor](https://ktor.io/)** — multiplatform HTTP client for networking
- **[Koin](https://insert-koin.io/)** — dependency injection
- **[Firebase](https://firebase.google.com/)** — crash reporting (Crashlytics) and app integrity verification (App Check)
- **[Coil](https://coil-kt.github.io/coil/)** — multiplatform image loading
- **[Napier](https://github.com/AAkira/Napier)** — multiplatform logging
- **[multiplatform-settings](https://github.com/russhwolf/multiplatform-settings)** — key-value settings storage, abstracted per platform (SharedPreferences on Android, Preferences API on Desktop, localStorage on Web)

## Architecture

The project follows a shared-first architecture: as much logic as possible lives in `commonMain`, with platform-specific implementations provided only where a platform genuinely requires a different approach (file system access, database drivers, settings storage, etc.), using Kotlin's `expect`/`actual` mechanism.

### Project Structure

```
Titan/
├── androidApp/          # Android application module
├── desktopApp/          # Desktop (JVM) application module, entry point (main.kt)
├── shared/              # Kotlin Multiplatform library module
│   └── src/
│       ├── commonMain/      # Shared business logic, UI (Compose), and expect declarations
│       ├── androidMain/     # Android-specific actuals (Context-dependent code)
│       ├── desktopMain/     # Desktop (JVM)-specific actuals (file system access)
│       ├── iosMain/         # iOS-specific actuals
│       └── wasmJsMain/      # Web-specific actuals, including the app's entry point (main.kt)
│                             # for the web target, since there is no separate web app module
└── sqliteWasmWorker/    # Local npm module providing the Web Worker required by
                          # Room's WebWorkerSQLiteDriver on the Web target
```

Note: unlike Android and Desktop, the Web target does not have a separate application module. Its entry point and resources live directly inside `shared/src/wasmJsMain`, since `wasmJs` is a Kotlin Multiplatform target of the `shared` module rather than a standalone runnable module.

### Room on the Web

Room's Web support (introduced in Room 3.0) relies on a `WebWorkerSQLiteDriver`, which requires an actual Web Worker to handle database operations off the browser's main thread. This project uses the `sqliteWasmWorker` local npm module (based on the official [`@sqlite.org/sqlite-wasm`](https://sqlite.org/wasm) library) to provide persistent, OPFS-backed storage — as opposed to in-memory-only alternatives.

Because OPFS's synchronous write API depends on `SharedArrayBuffer`, which browsers only expose under [Cross-Origin Isolation](https://developer.mozilla.org/en-US/docs/Web/API/Window/crossOriginIsolated), the following headers are required and are configured via `webpack.config.d`:

```
Cross-Origin-Opener-Policy: same-origin
Cross-Origin-Embedder-Policy: require-corp
```

## Building & Running

### Android
Open the project in Android Studio and run the `androidApp` configuration, or:
```bash
./gradlew :androidApp:installDebug
```

### Desktop
```bash
./gradlew :desktopApp:run
```

### iOS
Open the generated Xcode project (via KMM plugin / Fleet) or build via Gradle tasks targeting `iosArm64` / `iosSimulatorArm64`.

### Web
```bash
./gradlew :shared:wasmJsBrowserDevelopmentRun
```
Then open `http://localhost:8080` in a browser.
