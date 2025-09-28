# 📚 FMOD API Reference

> **Complete API documentation for FMOD API methods and integration features**

---

## 📋 Table of Contents

1. [🎯 Overview](#-overview)
2. [🎵 Core Audio Methods](#-core-audio-methods)
3. [🎮 Minecraft Integration Methods](#-minecraft-integration-methods)
4. [📦 Bank Management](#-bank-management)
5. [⚙️ Configuration API](#️-configuration-api)
6. [🔊 Audio Control](#-audio-control)
7. [📊 Status & Debugging](#-status--debugging)

---

## 🎯 Overview

The FMOD API provides a comprehensive interface for playing 3D spatial audio in Minecraft mods, with full integration into Minecraft's native audio system.

### 🏗️ API Structure

```
FMODAPI.java               // 🎵 Main public interface
├── Core Playback          // playEvent, playEventAt, playEventSimple
├── Audio Control          // pauseAllSounds, resumeAllSounds, setMasterVolume
├── Bank Management        // registerBank, loadBankFromResource
├── Status & Utility       // isAvailable, getActiveInstances, setListenerPosition
└── Integration Support    // Minecraft native integration hooks
```

---

## 🎵 Core Audio Methods

### 🎶 playEvent()
**Primary method for playing FMOD events with full control**

```java
public static String playEvent(String eventName, Vec3D position, float volume, float pitch)
```

| Parameter | Type | Description | Range |
|-----------|------|-------------|-------|
| `eventName` | String | FMOD event path (e.g., "event:/vehicles/engine") | Valid FMOD event |
| `position` | Vec3D | 3D world position (null for 2D) | Any coordinates |
| `volume` | float | Volume multiplier | 0.0 - ∞ (typically 0.0-2.0) |
| `pitch` | float | Pitch multiplier | 0.1 - 10.0 (1.0 = normal) |

**Returns:** `String` - Unique instance ID for further control, or `null` if failed/disabled

**Example:**
```java
// Play engine sound at vehicle position
String instanceId = FMODAPI.playEvent(
    "event:/vehicles/engine/idle",
    new Vec3D(x, y, z),
    1.0f,    // Normal volume
    1.2f     // Slightly higher pitch
);

if (instanceId != null) {
    // Sound playing via FMOD
} else {
    // Fallback to OpenAL
    playOpenALSound();
}
```

### 🎯 playEventAt()
**Simplified 3D positioned audio**

```java
public static String playEventAt(String eventName, double x, double y, double z, float volume, float pitch)
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `eventName` | String | FMOD event path |
| `x, y, z` | double | World coordinates |
| `volume` | float | Volume multiplier |
| `pitch` | float | Pitch multiplier |

**Example:**
```java
// Quick positioned sound
String id = FMODAPI.playEventAt("event:/weapons/gunshot", x, y, z, 1.0f, 1.0f);
```

### ⚡ playEventSimple()
**Simplified method for basic 3D audio**

```java
public static boolean playEventSimple(String eventName, double x, double y, double z)
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `eventName` | String | FMOD event path |
| `x, y, z` | double | World coordinates |

**Returns:** `boolean` - `true` if sound played successfully

**Example:**
```java
// Simple one-shot sound
boolean success = FMODAPI.playEventSimple("event:/effects/explosion", x, y, z);
```

---

## 🎮 Minecraft Integration Methods

### 🎯 pauseAllSounds()
**Instantly pause all active FMOD instances**

```java
public static void pauseAllSounds()
```

- **Purpose:** Used by Minecraft integration for ESC menu pause
- **Effect:** All active FMOD instances are paused
- **Performance:** <1ms execution time
- **Usage:** Typically called automatically by integration

**Example:**
```java
// Manual pause (usually handled automatically)
FMODAPI.pauseAllSounds();
```

### ⚡ resumeAllSounds()
**Resume all paused FMOD instances**

```java
public static void resumeAllSounds()
```

- **Purpose:** Used by Minecraft integration when returning to game
- **Effect:** All paused FMOD instances are resumed
- **Performance:** <1ms execution time
- **Usage:** Typically called automatically by integration

**Example:**
```java
// Manual resume (usually handled automatically)
FMODAPI.resumeAllSounds();
```

### 🔊 setMasterVolume()
**Control global FMOD volume**

```java
public static void setMasterVolume(float volume)
```

| Parameter | Type | Description | Range |
|-----------|------|-------------|-------|
| `volume` | float | Master volume level | 0.0 (mute) - 1.0 (full) |

- **Purpose:** Used by Minecraft integration for volume sync
- **Effect:** Affects all FMOD audio globally
- **Integration:** Automatically synced with Minecraft volume sliders

**Example:**
```java
// Set 50% volume (usually handled automatically)
FMODAPI.setMasterVolume(0.5f);
```

---

## 📦 Bank Management

### 📝 registerBank()
**Register a bank for automatic loading**

```java
public static void registerBank(Class<?> modClass, String resourcePath)
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `modClass` | Class<?> | Mod class for resource loading |
| `resourcePath` | String | Path to bank file in resources |

**Example:**
```java
// Register during mod initialization
FMODAPI.registerBank(MyMod.class, "/assets/mymod/sounds/fmod/master.bank");
```

### 📥 loadBankFromResource()
**Load a bank from mod resources**

```java
public static boolean loadBankFromResource(Class<?> modClass, String resourcePath)
```

**Returns:** `boolean` - `true` if loaded successfully

**Example:**
```java
boolean success = FMODAPI.loadBankFromResource(
    MyMod.class,
    "/assets/mymod/sounds/weapons.bank"
);
```

---

## ⚙️ Configuration API

### ✅ isAvailable()
**Check if FMOD system is ready**

```java
public static boolean isAvailable()
```

**Returns:** `boolean` - `true` if FMOD is initialized and enabled

**Example:**
```java
if (FMODAPI.isAvailable()) {
    FMODAPI.playEvent("event:/test", null, 1.0f, 1.0f);
} else {
    // Use OpenAL fallback
}
```

### 📊 getActiveInstances()
**Get map of currently playing instances**

```java
public static Map<String, Long> getActiveInstances()
```

**Returns:** `Map<String, Long>` - Instance ID to FMOD handle mapping

### 🔢 getActiveInstanceCount()
**Get number of currently playing sounds**

```java
public static int getActiveInstanceCount()
```

**Returns:** `int` - Number of active FMOD instances

### 🎚️ getMaxInstanceCount()
**Get maximum configured instances**

```java
public static int getMaxInstanceCount()
```

**Returns:** `int` - Maximum instances from config

---

## 🔊 Audio Control

### 🎧 setListenerPosition()
**Update 3D audio listener position**

```java
public static void setListenerPosition(double x, double y, double z,
                                     double forwardX, double forwardY, double forwardZ,
                                     double velX, double velY, double velZ)
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `x, y, z` | double | Listener position |
| `forwardX, forwardY, forwardZ` | double | Forward direction vector |
| `velX, velY, velZ` | double | Velocity vector |

**Note:** Automatically handled by `FMODListenerTracker` for player position

### 🛑 stopAllSounds()
**Stop all currently playing FMOD sounds**

```java
public static void stopAllSounds()
```

**⚠️ Warning:** This is for internal use (cleanup). Use `pauseAllSounds()` for pause functionality.

---

## 📊 Status & Debugging

### 🔧 Configuration Access

```java
// Access FMOD configuration
boolean enabled = FMODConfig.FMOD_ENABLED.get();
boolean debug = FMODConfig.DEBUG_LOGGING.get();
int maxInstances = FMODConfig.MAX_INSTANCES.get();
```

### 🐛 Debug Logging

```java
// Enable debug output
FMODConfig.DEBUG_LOGGING.set(true);

// Debug output will show:
// - Integration status messages
// - Volume change notifications
// - Pause/resume events
// - Bank loading information
```

### 📈 Performance Monitoring

```java
// Check system health
boolean systemReady = FMODAPI.isAvailable();
int activeSounds = FMODAPI.getActiveInstanceCount();
int maxSounds = FMODAPI.getMaxInstanceCount();

// Calculate usage percentage
float usage = (float) activeSounds / maxSounds * 100f;
```

---

## 🎯 Usage Patterns

### 🚀 Basic Sound Playback

```java
// Simple positioned sound
FMODAPI.playEventSimple("event:/ui/button_click", x, y, z);

// Advanced sound with control
String id = FMODAPI.playEvent("event:/ambient/wind",
                             new Vec3D(x, y, z),
                             0.8f, 1.0f);
```

### 🎮 Minecraft Integration

```java
// Integration is automatic - just use FMOD API normally
// Pause/resume happens automatically on ESC
// Volume sync happens automatically with Minecraft sliders

// Your code stays simple:
FMODAPI.playEvent("event:/vehicles/engine", position, 1.0f, 1.0f);
```

### 📦 Bank Setup

```java
// In mod initialization
public void init() {
    // Register banks for automatic loading
    FMODAPI.registerBank(MyMod.class, "/assets/mymod/sounds/master.strings.bank");
    FMODAPI.registerBank(MyMod.class, "/assets/mymod/sounds/master.bank");
    FMODAPI.registerBank(MyMod.class, "/assets/mymod/sounds/vehicles.bank");
}
```

### 🔧 Error Handling

```java
// Robust sound playback
public void playSound(String event, Vec3D pos) {
    String result = FMODAPI.playEvent(event, pos, 1.0f, 1.0f);
    if (result == null) {
        // FMOD failed or disabled - use OpenAL
        playOpenALFallback(event, pos);
    }
}
```

---

## 📋 Method Summary

### 🎵 Playback Methods
| Method | Purpose | Returns |
|--------|---------|---------|
| `playEvent()` | Full-featured audio playback | Instance ID or null |
| `playEventAt()` | Simple positioned audio | Instance ID or null |
| `playEventSimple()` | Basic 3D audio | boolean success |

### 🎮 Integration Methods
| Method | Purpose | Auto-Called |
|--------|---------|-------------|
| `pauseAllSounds()` | Pause all instances | ✅ On ESC menu |
| `resumeAllSounds()` | Resume all instances | ✅ On menu close |
| `setMasterVolume()` | Control global volume | ✅ On volume change |

### 📦 Management Methods
| Method | Purpose | When to Use |
|--------|---------|-------------|
| `registerBank()` | Register for auto-loading | Mod initialization |
| `loadBankFromResource()` | Manual bank loading | Runtime loading |
| `isAvailable()` | Check FMOD status | Before playback |

### 🔊 Control Methods
| Method | Purpose | Auto-Handled |
|--------|---------|-------------|
| `setListenerPosition()` | Update 3D listener | ✅ Player tracking |
| `stopAllSounds()` | Stop all sounds | Cleanup only |

---

*This API provides comprehensive audio control while maintaining simplicity and automatic integration with Minecraft's native audio system.*