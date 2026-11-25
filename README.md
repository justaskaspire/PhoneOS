# PHONEOS

Android-only local remote-control app. Initial goal: build an installable prototype APK, then add real remote-control features in tested stages.

## Current build

- Native Android app using Kotlin and Jetpack Compose.
- Host / Controller role selector.
- Connection preference selector: Automatic, same Wi-Fi, phone hotspot, Wi-Fi Direct.
- GitHub Actions workflow builds a debug APK on pushes to `main`.
- Pairing, device discovery, screen mirroring, remote touch/typing, file transfer and device actions are **not implemented yet**. The pairing button only displays a prototype notice.

## Get the APK

Open the **Actions** tab, select the latest **Build PHONEOS APK** run, and download the `PHONEOS-debug-APK` artifact if the workflow succeeds.

## Build requirements

- JDK 17
- Android SDK Platform 35
- Gradle 8.9

Run locally with `gradle assembleDebug` or open the project in Android Studio.

## Android constraints

- Screen capture uses Android MediaProjection and requires the host user to approve capture.
- Remote input is not generally available to ordinary apps; it may require an explicitly enabled Accessibility Service and user consent. Some system-level actions remain restricted.
- Local Wi-Fi, hotspot or Wi-Fi Direct can work without internet after both phones are connected.
- AI features are a later milestone and require an on-device model or internet access.

## Roadmap

1. Produce and install the first debug APK.
2. Implement secure local discovery and authenticated pairing.
3. Add host-approved screen sharing.
4. Add remote input through a transparent, user-enabled accessibility flow where supported.
5. Add local file transfer and device status.
6. Test with a Samsung A17 host and a second Android controller.

Never expose pairing secrets or permit unauthenticated remote control.
