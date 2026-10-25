# Robaap

Native Android teleoperation controller and mission management application for educational and exploration mobile robotics, featuring low-latency WebRTC video streaming and real-time telemetry.

## Overview

Robaap provides a mobile interface to operate autonomous and remotely piloted ground robots. Built with modern Android architecture (Kotlin, Jetpack Navigation, Material 3), it integrates real-time WebRTC peer-to-peer video streaming from the robot's onboard camera system with Firebase Realtime Database signaling for ultra-low latency teleoperation.

## Features

- **Real-Time Video Teleoperation:** WebRTC peer-to-peer live camera feed streaming directly to `SurfaceViewRenderer` with Google STUN (`stun:stun.l.google.com:19302`) traversal.
- **Real-Time Signaling:** Firebase Realtime Database for exchanging SDP offers, answers, and ICE candidates between the Android client and the robot gateway.
- **Mission Management:** Interface for configuring robot exploration missions and tracking mission waypoints.
- **Image Capture & Inspection:** In-field snapshot capture and multi-image inspection review.
- **Telemetry Monitoring:** On-screen indicators for connection state, battery level, and environment sensors.

## Architecture & Technology Stack

- **Platform:** Android (minSdk 24, targetSdk 36)
- **Language:** Kotlin
- **Video Streaming:** Google WebRTC (`org.webrtc:google-webrtc`)
- **Signaling & State:** Firebase Realtime Database (KTX)
- **Architecture Components:** ViewModel, Navigation Component (`androidx.navigation`), ViewBinding
- **UI Framework:** Material Components for Android

## Project Structure

```
Robaap/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/robaap/
│   │   │   │   ├── MainActivity.kt               # Main application shell
│   │   │   │   ├── ControlFragment.kt            # WebRTC video renderer & teleoperation
│   │   │   │   ├── HomeFragment.kt               # Dashboard & mission launchpad
│   │   │   │   ├── MissionSelectionFragment.kt   # Exploration mission selector
│   │   │   │   ├── ImageSelectionFragment.kt     # Captured imagery review
│   │   │   │   ├── AnalysisFragment.kt           # Field telemetry analysis
│   │   │   │   └── ChatAiBottomSheetFragment.kt  # Interactive AI mission assistant
│   │   │   ├── res/                              # Layouts, vector drawables, nav graph
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio Ladybug | 2024.2+ or newer
- JDK 17+
- Android SDK Platform 36

### Build & Run

1. Clone repository:
   ```bash
   git clone https://github.com/muqsithanif/Robaap.git
   cd Robaap
   ```
2. Open the project in **Android Studio**.
3. Place your `google-services.json` inside the `app/` directory if connecting to a custom Firebase project.
4. Build and install on an Android device or emulator:
   ```bash
   ./gradlew assembleDebug
   ```
