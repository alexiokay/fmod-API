# 📚 FMOD API Documentation

> **Complete documentation for the FMOD Audio API mod for Minecraft**

---

## 🎯 Quick Navigation

### 🆕 **New Features (Latest Updates)**
- 🎮 **[Minecraft Native Integration](MINECRAFT_NATIVE_INTEGRATION.md)** - Automatic pause/resume and volume sync
- ⚙️ **[Simplified Configuration System](CONFIGURATION_SYSTEM.md)** - Streamlined, user-friendly config
- 📚 **[Complete API Reference](API_REFERENCE.md)** - All methods and integration features

### 📖 **Advanced Documentation**
- 🏗️ **[Advanced Technical Guide](ADVANCED_TECHNICAL_GUIDE.md)** - Deep technical architecture and MTS integration

---

## 🎵 What is FMOD API?

The **FMOD API mod** provides high-quality 3D spatial audio for Minecraft mods using the professional FMOD Studio engine. It offers seamless integration with Minecraft's native audio system while maintaining full compatibility with OpenAL as a fallback.

### ✨ Key Features

| Feature | Description | Benefit |
|---------|-------------|---------|
| 🎮 **Native Integration** | Automatic pause/resume with ESC menu | Behaves like vanilla Minecraft |
| 🔊 **Volume Sync** | Respects all Minecraft volume sliders | Seamless user experience |
| 🎵 **3D Spatial Audio** | Professional FMOD Studio engine | Rich, immersive soundscapes |
| 🔀 **Hybrid System** | FMOD + OpenAL dual support | Best of both worlds |
| ⚡ **Instant Switching** | Toggle without restart | Real-time configuration |
| 🛡️ **Graceful Fallback** | Automatic OpenAL backup | Never lose audio |

---

## 🚀 Getting Started

### 👥 For Users

1. **Install the mod** - Drop FMOD API JAR into mods folder
2. **That's it!** - Everything works automatically
3. **Optional:** Configure via Mods → FMOD API → Config

### 🔧 For Mod Developers

```java
// Basic usage - simple and clean
FMODAPI.playEventSimple("event:/vehicles/engine", x, y, z);

// Advanced usage with full control
String instanceId = FMODAPI.playEvent("event:/weapons/gunshot",
                                     new Vec3D(x, y, z),
                                     1.0f, 1.2f);
```

**Integration is automatic** - pause/resume and volume sync happen without any code changes!

---

## 📋 Documentation Structure

### 🎮 User Guides
- **🆕 [Minecraft Native Integration](MINECRAFT_NATIVE_INTEGRATION.md)**
  - Instant pause/resume system
  - Real-time volume synchronization
  - Event-driven architecture
  - Zero-configuration setup

### ⚙️ Configuration
- **🆕 [Configuration System](CONFIGURATION_SYSTEM.md)**
  - Simplified flat structure
  - Runtime config changes
  - Multi-language support
  - Native NeoForge integration

### 👨‍💻 Developer Resources
- **🆕 [API Reference](API_REFERENCE.md)** - Complete method documentation and usage patterns
- **🏗️ [Advanced Technical Guide](ADVANCED_TECHNICAL_GUIDE.md)** - Deep technical architecture and integration details

---

*For detailed technical information, refer to the specific documentation files linked above.*