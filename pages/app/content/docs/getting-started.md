---
title: 'Getting Started'
description: 'Quick setup guide for installing and using FMOD API in your Minecraft mods'
navigation:
  title: 'Getting Started'
  icon: 'rocket'
---

# Getting Started with FMOD API

Get professional 3D spatial audio working in your Minecraft mod in just a few minutes.

## 📋 Requirements

### System Requirements
- **NeoForge 1.21.1+** minimum version
- **Java 21+** runtime requirement
- **Client-side only** - no server-side dependencies

### FMOD Engine Requirements
- **FMOD Engine 2.02.16** (or compatible version)
- **Free for non-commercial use** and indie development
- **Commercial license required** for commercial projects

::alert{type="warning"}
**Important:** FMOD DLL files are NOT included due to licensing restrictions. You must download and install FMOD Engine separately.
::

## 🚀 Quick Setup

### For End Users

::steps
1. **Download FMOD API**
   - Download from [Modrinth](https://modrinth.com/mod/fmod-api) (recommended)
   - Or from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fmod-api)
   - ⚠️ **Only download from official sources**

2. **Install FMOD Engine**
   - Visit [FMOD.com Downloads](https://www.fmod.com/download#fmodengine)
   - Download **FMOD Engine version 2.02.16**
   - Run the installer (creates free account if required)

3. **Install Mod**
   - Drop `fmod-api-x.x.x.jar` into your mods folder
   - Restart Minecraft
   - FMOD API will auto-detect your FMOD installation

4. **Verify Installation**
   - Go to Mods → FMOD API → Config
   - Check that FMOD is enabled and detected
   - If not detected, set custom path (see below)
::

### For Mod Developers

::steps
1. **Add Dependency**
   Add FMOD API as a dependency in your `mods.toml`:
   ```toml
   [[dependencies.yourmod]]
       modId="fmodapi"
       mandatory=true
       versionRange="[1.0,)"
       ordering="BEFORE"
   ```

2. **Basic Usage**
   ```java
   // Simple positioned sound
   FMODAPI.playEventSimple("event:/yourmod/sound", x, y, z);

   // Advanced usage with control
   if (FMODAPI.isAvailable()) {
       String instanceId = FMODAPI.playEvent("event:/yourmod/engine",
                                           new Vec3D(x, y, z),
                                           1.0f, 1.2f);
   }
   ```

3. **Load Your Sound Banks**
   ```java
   // During mod initialization
   FMODAPI.loadBankFromResource(YourMod.class,
       "/assets/yourmod/sounds/fmod/master.bank");
   ```
::

## 🔍 FMOD Library Detection

FMOD API uses a **3-tier detection system** to find FMOD libraries:

### 1. 🎯 JAR Resources (Development)
- Loads DLLs from mod's `/libraries/` folder
- Used for development builds with `INCLUDE_LIBS=true`

### 2. 🔄 System Detection (Automatic)
- Checks standard FMOD installation paths
- Scans system PATH for libraries
- Works with default FMOD installations

### 3. 🛡️ Custom Path (Manual)
- User-configurable path in mod config
- Scans up to 4 levels deep for DLL files
- Handles portable and custom installations

::alert{type="info"}
**Auto-detection works for 95% of users.** Custom path is only needed for non-standard installations.
::

## ⚙️ Configuration

### Basic Settings

Access configuration via: **Mods → FMOD API → Config**

| Setting | Description | Default |
|---------|-------------|---------|
| **Enable FMOD Audio** | Toggle FMOD system on/off | `true` |
| **Debug Logging** | Enable detailed logging | `false` |
| **Max Sound Instances** | Concurrent sound limit | `512` |
| **Custom FMOD Path** | Manual library path | *empty* |

### Custom Installation Paths

If FMOD isn't auto-detected, set a custom path:

1. **Open config:** Mods → FMOD API → Config
2. **Set custom path** to your FMOD installation directory
3. **Examples:**
   ```
   C:\Program Files (x86)\FMOD SoundSystem\
   D:\PortableApps\FMOD\
   E:\Games\Audio\FMOD Engine\
   ```
4. **Auto-scanning:** Finds DLLs automatically within 4 folder levels

### Required Files
The mod needs these files from your FMOD installation:
- `fmod.dll` (Core audio engine)
- `fmodstudio.dll` (Studio API)

## 🎵 First Steps

### Test FMOD Integration

1. **Check Status**
   ```java
   if (FMODAPI.isAvailable()) {
       System.out.println("FMOD is ready!");
   } else {
       System.out.println("Using OpenAL fallback");
   }
   ```

2. **Play Test Sound**
   ```java
   // Test with a simple event
   boolean success = FMODAPI.playEventSimple("event:/test/sound", 0, 64, 0);
   ```

3. **Monitor Debug Output**
   - Enable debug logging in config
   - Check logs for FMOD initialization messages
   - Verify bank loading status

### Automatic Features

These work without any code changes:

- ✅ **Automatic pause/resume** when pressing ESC
- ✅ **Volume synchronization** with Minecraft sliders
- ✅ **3D listener tracking** follows player position
- ✅ **Resource cleanup** prevents memory leaks

## 🛠️ Development Workflow

### Setting Up FMOD Studio

1. **Download FMOD Studio** (free)
2. **Create project** with your mod's sounds
3. **Build banks** to your mod's resources
4. **Test in-game** with FMOD API

### Bank Organization

```
src/main/resources/assets/yourmod/sounds/fmod/
├── master.strings.bank      # String data
├── master.bank             # Master bank
├── vehicles.bank           # Vehicle sounds
└── weapons.bank            # Weapon sounds
```

### Loading Banks

```java
@Mod("yourmod")
public class YourMod {
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        // Load banks in order
        FMODAPI.loadBankFromResource(YourMod.class,
            "/assets/yourmod/sounds/fmod/master.strings.bank");
        FMODAPI.loadBankFromResource(YourMod.class,
            "/assets/yourmod/sounds/fmod/master.bank");
        FMODAPI.loadBankFromResource(YourMod.class,
            "/assets/yourmod/sounds/fmod/vehicles.bank");
    }
}
```

## 🔧 Troubleshooting

### Common Issues

::alert{type="error"}
**FMOD not detected**
- Verify FMOD Engine 2.02.16 is installed
- Check custom path setting if using non-standard location
- Enable debug logging to see detection attempts
::

::alert{type="warning"}
**Sounds not playing**
- Ensure banks are loaded successfully
- Check event names match FMOD Studio project
- Verify `FMODAPI.isAvailable()` returns `true`
::

::alert{type="info"}
**Performance issues**
- Reduce max instances if needed
- Use `playEventSimple()` for fire-and-forget sounds
- Enable debug logging to monitor active instances
::

### Debug Information

Enable debug logging for detailed information:

```java
// Check system status
System.out.println("FMOD Available: " + FMODAPI.isAvailable());
System.out.println("Active Instances: " + FMODAPI.getActiveInstanceCount());
System.out.println("Max Instances: " + FMODAPI.getMaxInstanceCount());
```

## 📚 Next Steps

- 📖 [**Native Integration**](/docs/integration) - Learn about automatic Minecraft integration
- 🔧 [**Configuration Guide**](/docs/configuration) - Detailed configuration options
- 📝 [**API Reference**](/api/reference) - Complete method documentation
- 🏗️ [**Advanced Guide**](/docs/advanced) - Deep technical details

::alert{type="success"}
**Ready to start building?** The FMOD API handles all the complex integration automatically. Just focus on creating amazing audio experiences!
::