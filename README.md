# FMOD API for Minecraft NeoForge

**A centralized FMOD Audio Engine API for Minecraft mods**

---

## 🎯 Mission Statement

The FMOD API mod provides a **centralized, conflict-free FMOD Audio Engine integration** for Minecraft NeoForge mods, enabling multiple mods to use advanced 3D spatial audio without library conflicts or resource competition.

## 🎮 Goals

### Primary Goals
- **Eliminate FMOD conflicts** between multiple mods
- **Provide simple, clean API** for other mods to integrate FMOD
- **Maintain backward compatibility** with OpenAL fallback systems
- **Ensure resource efficiency** through centralized management
- **Enable advanced 3D spatial audio** with minimal complexity

### Secondary Goals
- **Reduce mod file sizes** by sharing FMOD libraries
- **Improve audio performance** through optimized resource management
- **Provide debugging tools** for audio development
- **Support custom sound banks** from any mod

## 🎵 Core Values

### 1. **Reliability First**
- **Never break existing functionality** - all changes must maintain backward compatibility
- **Graceful degradation** - if FMOD fails, seamlessly fall back to OpenAL
- **No single-point-of-failure** - individual sound failures don't disable the entire system

### 2. **Simplicity & Accessibility**
- **Easy integration** - other mods can add FMOD support with minimal code changes
- **Clear documentation** - every API method has obvious purpose and usage
- **Minimal dependencies** - only require what's absolutely necessary

### 3. **Performance & Efficiency**
- **Shared resources** - one FMOD instance serves all mods
- **Automatic cleanup** - prevent memory leaks and resource exhaustion
- **Lazy loading** - only initialize FMOD when actually needed

### 4. **Flexibility & Extensibility**
- **Mod-agnostic** - works with any mod that needs audio
- **Custom bank support** - mods can load their own sound banks
- **Multiple audio formats** - support both FMOD events and traditional OGG files

## 🏛️ Core Principles

### API Design Principles

#### 1. **Fail-Safe Architecture**
```java
// ✅ GOOD: Always check availability
if (FMODAPI.isAvailable()) {
    FMODAPI.playEvent("event:/weapon/shot", position);
} else {
    // Fallback to traditional sound system
    playOGGSound("weapon_shot.ogg");
}

// ❌ BAD: Never assume FMOD is available
FMODAPI.playEvent("event:/weapon/shot", position); // Could fail
```

#### 2. **Resource Management**
```java
// ✅ GOOD: Automatic cleanup
String instanceId = FMODAPI.playEvent("event:/engine/start", position);
// API automatically cleans up when sound finishes

// ✅ GOOD: Manual cleanup when needed
FMODAPI.stopEvent(instanceId, true); // Allow fade-out

// ❌ BAD: Never leave resources hanging
// Manual tracking without cleanup leads to memory leaks
```

#### 3. **Bank Loading Strategy**
```java
// ✅ GOOD: Load from your mod's resources
FMODAPI.loadBankFromResource(YourMod.class, "/assets/yourmod/sounds/fmod/Custom.bank");

// ✅ GOOD: Load from external file
FMODAPI.loadBank("/path/to/external/sounds.bank");

// ❌ BAD: Don't hardcode paths from other mods
FMODAPI.loadBank("/assets/mts/sounds/fmod/Master.bank"); // Wrong!
```

### Coding Standards

#### 1. **Error Handling**
- **Never throw unchecked exceptions** from public API methods
- **Always log meaningful error messages** with context
- **Provide fallback behavior** for all failure cases
- **Use proper log levels** (INFO for normal operation, WARN for fallbacks, ERROR for failures)

#### 2. **Thread Safety**
- **All public API methods must be thread-safe**
- **Use concurrent collections** for shared state
- **Minimize synchronization overhead** where possible
- **Document thread safety guarantees**

#### 3. **Resource Lifecycle**
- **Automatic cleanup** for fire-and-forget sounds
- **Manual cleanup** available for long-running sounds
- **Proper initialization order** (preInit → init → bank loading)
- **Clean shutdown** with resource release

#### 4. **API Consistency**
- **Consistent naming conventions** (playEvent, stopEvent, loadBank)
- **Predictable parameter ordering** (name, position, volume, pitch)
- **Overloaded methods** for different use cases
- **Builder pattern** for complex configurations

## 📦 Setup & Installation

### ⚠️ Official Distribution Only

**IMPORTANT: This mod must be downloaded from official sources only:**

- **🟢 Modrinth**: [Official FMOD API page](https://modrinth.com/mod/fmod-api)
- **🟢 CurseForge**: [Official FMOD API page](https://www.curseforge.com/minecraft/mc-mods/fmod-api)
- **❌ Third-party redistribution is prohibited**

### Using FMOD API as a Dependency

**For Mod Developers:**

1. **Download from official source** (Modrinth/CurseForge)
2. **Add as dependency** in your `mods.toml`:
```toml
[[dependencies.yourmod]]
    modId="fmodapi"
    mandatory=true
    versionRange="[1.0,)"
    ordering="BEFORE"
```
3. **Use the API** in your mod code:
```java
if (FMODAPI.isAvailable()) {
    FMODAPI.playEventSimple("event:/yourmod/sound", x, y, z);
}
```

**For End Users:**
- Install FMOD API from Modrinth/CurseForge
- Install FMOD Engine separately (see below)
- Other mods will automatically use FMOD API when available

### FMOD Libraries Setup

**⚠️ IMPORTANT: FMOD DLL files are NOT included due to licensing restrictions.**

You must download and install the FMOD libraries yourself:

#### Step 1: Download FMOD Engine
- Visit: https://www.fmod.com/download#fmodengine
- Download **FMOD Engine version 2.02.16** (or compatible version)
- Create a free FMOD account if required

#### Step 2: Install & Locate Libraries
1. Run the FMOD Engine installer
2. Navigate to your installation folder
3. Find the following files:
   - `api/core/lib/x64/fmod.dll`
   - `api/studio/lib/x64/fmodstudio.dll`

#### Step 3: Automatic Detection
The FMOD API mod will automatically detect and use FMOD Engine from your system installation.

**No manual file copying required** - the mod uses smart detection to find FMOD libraries.

### 🔍 Smart Library Detection

The mod uses a **3-tier detection system**:

1. **🎯 JAR Resources** (if included): Loads DLLs from mod's `/libraries/` folder
2. **🔄 System Detection**: Automatically finds FMOD from standard locations
3. **🛡️ OpenAL Fallback**: Always works as backup

### ⚙️ Custom Installation Paths

**For non-standard FMOD installations:**

1. **Open FMOD API config** in-game (Mods → FMOD API → Config)
2. **Set "Custom FMOD Path"** to your installation directory
3. **Examples:**
   ```
   C:\MyCustomPath\FMOD\
   D:\PortableApps\FMOD\
   E:\Games\Audio\FMOD Engine\
   ```
4. **The mod automatically scans** up to 4 levels deep to find:
   - `fmod.dll`
   - `fmodstudio.dll`

### 📁 Detection Priority

**The mod searches in this order:**

1. **Custom path** (from config) - scans recursively
2. **System PATH** - standard library loading
3. **Common paths** - auto-detects standard installations:
   - `C:\Program Files (x86)\FMOD SoundSystem\`
   - `C:\Program Files\FMOD SoundSystem\`
   - Local directories (`.\fmod\`, `.\libraries\`)

### 🎯 User-Friendly Setup

**Most users:** Just install FMOD Engine - auto-detection works!

**Custom installs:** Set the root folder in config - recursive scanning finds the DLLs anywhere within 4 folder levels.

**Example:** Set path to `C:\MyFMOD\` and it will find DLLs at:
`C:\MyFMOD\Studio\API\Windows\api\core\lib\x64\fmod.dll`

#### Licensing Note
- FMOD Engine is free for non-commercial use and indie development
- Commercial projects need commercial licenses from FMOD
- See https://www.fmod.com/legal for full licensing terms
- **This mod does NOT redistribute FMOD libraries** - you install them separately
- **Compatible with FMOD Engine 2.02.16 or higher**

#### Fallback Behavior
If FMOD Engine is not found anywhere, the mod automatically falls back to Minecraft's OpenAL audio system. Mods using FMOD API will still work, but with reduced audio functionality.

#### Why This Approach?
- **Legal compliance**: Avoids FMOD redistribution licensing issues
- **User-friendly**: Works with any installation location
- **Always up-to-date**: Uses your latest FMOD Engine installation
- **Flexible**: Handles custom, portable, and standard installations
- **Safe for modpacks**: No licensing concerns for modpack creators

## 🔧 Technical Requirements

### Performance Requirements
- **Maximum 128 concurrent FMOD instances** to prevent resource exhaustion
- **Automatic cleanup** of finished instances within 1 second
- **Bank loading** must complete within 5 seconds or fail gracefully
- **API calls** should complete within 100ms under normal conditions

### Compatibility Requirements
- **NeoForge 1.21.1+** minimum version
- **Java 21+** runtime requirement
- **LWJGL 3.3.3+** for FMOD bindings
- **Client-side only** - no server-side dependencies

### Quality Requirements
- **Zero tolerance for crashes** - all exceptions must be caught and handled
- **Graceful degradation** - always provide OpenAL fallback
- **Memory leak prevention** - automatic resource cleanup
- **Configuration validation** - reject invalid parameters early

## 🚀 Integration Guidelines

### For Mod Developers

#### Quick Start
```java
// 1. Add FMOD API as dependency in your mods.toml
// 2. Check availability before use
if (FMODAPI.isAvailable()) {
    // 3. Load your sound banks
    FMODAPI.loadBankFromResource(MyMod.class, "/assets/mymod/sounds/fmod/Sounds.bank");

    // 4. Play sounds with 3D positioning
    String id = FMODAPI.playEventAt("event:/mymod/explosion", x, y, z, 1.0f, 1.0f);
}
```

#### Best Practices
- **Always check `FMODAPI.isAvailable()`** before making API calls
- **Load banks during mod initialization**, not during gameplay
- **Use descriptive event names** with your mod's namespace
- **Provide OpenAL fallbacks** for all FMOD sounds
- **Test with FMOD disabled** to ensure fallbacks work

#### Common Patterns
```java
// Pattern 1: Simple sound effect
public void playGunshot(Vec3D position) {
    if (FMODAPI.isAvailable()) {
        FMODAPI.playEventAt("event:/mymod/weapons/gunshot",
            position.x, position.y, position.z, 1.0f, 1.0f);
    } else {
        // Fallback to your existing sound system
        playOGGSound("gunshot.ogg", position);
    }
}

// Pattern 2: Looping engine sound with cleanup
private String engineSoundId = null;

public void startEngine(Vec3D position) {
    if (FMODAPI.isAvailable() && engineSoundId == null) {
        engineSoundId = FMODAPI.playEventAt("event:/mymod/engine/running",
            position.x, position.y, position.z, 1.0f, 1.0f);
    }
}

public void stopEngine() {
    if (engineSoundId != null) {
        FMODAPI.stopEvent(engineSoundId, true); // Allow fade-out
        engineSoundId = null;
    }
}
```

## 🛡️ Quality Assurance

### Mandatory Principles

#### 1. **Never Break the Game**
- **All API methods are fail-safe** - they catch exceptions internally
- **Invalid parameters are rejected gracefully** with clear error messages
- **System degradation is transparent** - users may not even notice FMOD is unavailable

#### 2. **Resource Responsibility**
- **Every allocated resource must be cleaned up** automatically or manually
- **Memory leaks are unacceptable** - use automatic cleanup wherever possible
- **Thread safety is mandatory** for all public API methods

#### 3. **Backward Compatibility**
- **Existing API signatures never change** without deprecation period
- **New features are additive only** - don't break existing functionality
- **Fallback behavior is consistent** across all versions

#### 4. **Performance Standards**
- **Audio latency < 50ms** for real-time events
- **Memory usage grows linearly** with active sounds, not exponentially
- **CPU overhead < 2%** during normal operation

## 📊 Success Metrics

### Technical Metrics
- **Zero crashes** related to audio system failures
- **< 1% performance overhead** compared to pure OpenAL
- **100% compatibility** with existing OpenAL-based mods
- **< 5 second startup time** for FMOD initialization

### User Experience Metrics
- **Seamless audio experience** across multiple FMOD-enabled mods
- **No audio conflicts** or resource competition
- **Consistent 3D spatial audio** quality
- **Reliable fallback behavior** when FMOD unavailable

## 🔮 Future Vision

### Roadmap
1. **Phase 1** (Current): Basic FMOD integration with bank loading
2. **Phase 2**: Advanced features (real-time parameter control, audio occlusion)
3. **Phase 3**: Audio streaming and dynamic loading
4. **Phase 4**: Advanced 3D audio effects and environmental audio

### Long-term Goals
- **Industry-standard audio quality** in Minecraft mods
- **Rich audio ecosystem** enabling immersive gameplay
- **Developer-friendly tools** for audio content creation
- **Performance optimization** for large-scale audio scenarios

---

## 📋 API Reference

### Core Methods
```java
// System Status
boolean FMODAPI.isAvailable()
FMODStatus FMODAPI.getStatus()

// Bank Management
boolean FMODAPI.loadBank(String bankPath)
boolean FMODAPI.loadBankFromResource(Class<?> modClass, String resourcePath)

// Event Playback
String FMODAPI.playEvent(String eventName, Vec3D position)
String FMODAPI.playEventAt(String eventName, double x, double y, double z, float volume, float pitch)
void FMODAPI.stopEvent(String instanceId, boolean allowFadeOut)

// 3D Audio
void FMODAPI.setListenerPosition(Vec3D position, Vec3D forward, Vec3D up, Vec3D velocity)
void FMODAPI.updateInstancePosition(String instanceId, Vec3D position)

// Utility
int FMODAPI.getActiveInstanceCount()
int FMODAPI.getMaxInstanceCount()
void FMODAPI.stopAllSounds()
```

### Integration Example
```java
@Mod("mymod")
public class MyMod {

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        // Load your mod's sound banks
        if (FMODAPI.isAvailable()) {
            FMODAPI.loadBankFromResource(MyMod.class, "/assets/mymod/sounds/fmod/Master.strings.bank");
            FMODAPI.loadBankFromResource(MyMod.class, "/assets/mymod/sounds/fmod/Master.bank");
            System.out.println("MyMod: FMOD banks loaded successfully");
        } else {
            System.out.println("MyMod: FMOD not available, using OpenAL fallback");
        }
    }

    public void playExplosion(Vec3D position, float intensity) {
        if (FMODAPI.isAvailable()) {
            // Use FMOD for rich 3D audio
            FMODAPI.playEventAt("event:/mymod/explosion",
                position.x, position.y, position.z, intensity, 1.0f);
        } else {
            // Fallback to simple sound
            playOGGSound("explosion.ogg", position, intensity);
        }
    }
}
```

---

## 📄 License & Attribution

### **FMOD Audio Engine Attribution**
This software uses FMOD Studio by Firelight Technologies Pty Ltd.
**FMOD Studio, copyright © Firelight Technologies Pty Ltd, 1994-2024**

### **License Requirements**
**Proprietary License - Official Distribution Only**

This mod may only be distributed through official channels. Redistribution by third parties is prohibited.

### **IMPORTANT LICENSING NOTES**
⚠️ **FMOD Redistribution License Required**: This mod redistributes FMOD Studio libraries, which requires proper licensing from Firelight Technologies for redistribution.

⚠️ **Commercial Use**: Any commercial use requires:
1. Written permission from alexiokay (mod author)
2. Valid FMOD commercial license from Firelight Technologies

⚠️ **Attribution Required**: Projects using this mod must display:
*"Audio powered by FMOD Studio by Firelight Technologies Pty Ltd"*

### **Contact for Licensing**
- **FMOD Redistribution License**: sales@fmod.com
- **Mod Commercial Use**: Contact alexiokay through official channels

## 🤝 Contributing

This project follows strict quality standards. All contributions must:
- Follow the coding principles outlined above
- Include comprehensive error handling
- Maintain backward compatibility
- Pass all existing tests
- Include documentation updates

---

*"Delivering professional-grade audio to the Minecraft modding community, one sound at a time."*

**Made by alexiokay - Official distribution only**