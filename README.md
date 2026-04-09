# MACD Pattern Scanner (Personal Beta)

Android app (Kotlin + Compose) that scans MACD patterns from **mock OHLCV candles only** and stores signal history locally.

## Includes (Phase 1 + 1.5)
- Single app module
- Clean-ish layers (`core`, `data`, `feature`, `worker`, `notification`)
- Watchlist management
- One global timeframe selector
- Manual "Scan now"
- MACD calc + basic pattern detection
- Room signal history
- Local notifications
- WorkManager periodic scan
- Gradle wrapper + Android Studio runnable structure

## Windows + Android Studio run steps
1. Install latest Android Studio.
2. In SDK Manager, install:
   - Android SDK Platform 35
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
3. Open this folder in Android Studio.
4. Let Gradle sync finish (using wrapper).
5. Start emulator (API 26+; API 33+ recommended for notification permission tests).
6. Run app configuration `app`.

### Physical phone
1. Enable Developer Options + USB Debugging.
2. Connect phone via USB and accept debugging prompt.
3. Select device and click Run.

## Notes
- No live API integration yet (intentionally deferred).
- Data is local-first and mock-only for this stage.

## Regenerate missing Gradle wrapper JAR
If `gradle/wrapper/gradle-wrapper.jar` is not in the repo, regenerate it locally:

1. Open the project in Android Studio and let Gradle sync once, or
2. Run from the project root:

```bash
gradle wrapper --gradle-version 8.14.3 --no-validate-url
```

This recreates `gradle/wrapper/gradle-wrapper.jar` and keeps wrapper scripts/properties in sync.

