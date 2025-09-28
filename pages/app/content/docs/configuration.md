---
title: 'Configuration'
description: 'Complete guide to FMOD API configuration system and settings'
navigation:
  title: 'Configuration'
  icon: 'cog'
---

# Configuration System

FMOD API features a simplified, flat configuration system designed for ease of use and reliability. All settings are accessible through Minecraft's native configuration interface.

## 🎛️ Configuration Options

### Access Configuration

**In-Game:** Mods → FMOD API → Config
**Main Menu:** Mods → FMOD API → Config

### Core Settings

#### 🎵 Enable FMOD Audio
- **Type:** Boolean toggle
- **Default:** `true`
- **Description:** Controls whether FMOD audio system is active
- **Effect:** Takes effect immediately without restart

```java
// Access in code
boolean enabled = FMODConfig.FMOD_ENABLED.get();
```

#### 🐛 Enable Debug Logging
- **Type:** Boolean toggle
- **Default:** `false`
- **Description:** Enables detailed logging for troubleshooting
- **Effect:** Controls debug output verbosity

```java
// Access in code
boolean debug = FMODConfig.DEBUG_LOGGING.get();
```

#### 🎚️ Maximum Sound Instances
- **Type:** Integer with range validation
- **Default:** `512`
- **Range:** 32 to 4096
- **Description:** Maximum number of concurrent FMOD sound instances
- **Effect:** Controls memory usage and performance

```java
// Access in code
int maxInstances = FMODConfig.MAX_INSTANCES.get();
```

#### 📁 Custom FMOD Path
- **Type:** String path
- **Default:** *empty* (auto-detection)
- **Description:** Custom FMOD installation path
- **Effect:** Used when FMOD isn't auto-detected

```java
// Access in code
String customPath = FMODConfig.FMOD_CUSTOM_PATH.get();
```

## 🔄 Runtime Changes

### Immediate Effect System

Configuration changes apply **immediately** without requiring restart:

```java
// When config changes
@SubscribeEvent
static void onReload(final ModConfigEvent.Reloading event) {
    handleRuntimeConfigChange();
}

private static void handleRuntimeConfigChange() {
    boolean fmodEnabled = FMOD_ENABLED.get();
    boolean fmodCurrentlyRunning = FMODSystem.isInitialized();

    if (fmodEnabled && !fmodCurrentlyRunning) {
        // User enabled FMOD - initialize it
        FMODSystem.checkConfigAndInit();
    } else if (!fmodEnabled && fmodCurrentlyRunning) {
        // User disabled FMOD - shut it down
        FMODSystem.shutdown();
    }
}
```

### Change Response Times

| Setting | Detection | Application | Total Time |
|---------|-----------|-------------|------------|
| **FMOD Enabled** | Immediate | ~50-100ms | **<150ms** |
| **Debug Logging** | Immediate | Immediate | **<1ms** |
| **Max Instances** | Immediate | Next FMOD init | **Variable** |
| **Custom Path** | Immediate | Next restart | **Next session** |

::alert{type="info"}
**Instant feedback** - See changes immediately without restarting Minecraft!
::

## 🎯 Configuration Scenarios

### Scenario 1: First Time Setup

**Typical user with standard FMOD installation:**

1. Install FMOD Engine from FMOD.com
2. Install FMOD API mod
3. Start Minecraft
4. ✅ **Everything works automatically**

**Configuration needed:** None - auto-detection works

### Scenario 2: Custom Installation

**User with FMOD in non-standard location:**

1. FMOD installed to `D:\Audio\FMOD Engine\`
2. FMOD API can't auto-detect
3. Set **Custom FMOD Path** to `D:\Audio\FMOD Engine\`
4. ✅ **FMOD detected and working**

**Configuration needed:** Custom path only

### Scenario 3: Troubleshooting

**Developer debugging audio issues:**

1. Enable **Debug Logging**
2. Check console output for detailed information
3. Adjust **Max Instances** if performance issues
4. ✅ **Debug info helps identify problems**

**Configuration needed:** Debug logging

### Scenario 4: Performance Tuning

**Server with many players/mods:**

1. Reduce **Max Instances** from 512 to 256
2. Monitor performance improvement
3. Adjust further if needed
4. ✅ **Balanced performance and quality**

**Configuration needed:** Max instances adjustment

## 📁 Custom Path Configuration

### When You Need Custom Path

- FMOD installed in portable location
- Non-standard installation directory
- Multiple FMOD versions installed
- Corporate/restricted environment

### Setting Custom Path

::steps
1. **Open Configuration**
   - In-game: ESC → Mod Options → FMOD API
   - Main menu: Mods → FMOD API → Config

2. **Set Custom FMOD Path**
   - Enter the root directory of your FMOD installation
   - Examples:
     ```
     C:\Program Files (x86)\FMOD SoundSystem\
     D:\PortableApps\FMOD\
     E:\Games\Audio\FMOD Engine\
     ```

3. **Auto-Detection**
   - FMOD API scans up to 4 levels deep
   - Automatically finds `fmod.dll` and `fmodstudio.dll`
   - No need to specify exact file paths

4. **Restart Minecraft**
   - Custom path changes require restart
   - FMOD API will use new path on next launch
::

### Path Examples

| Installation Type | Example Path | Notes |
|-------------------|-------------|-------|
| **Standard** | `C:\Program Files (x86)\FMOD SoundSystem\` | Default installation |
| **Portable** | `D:\PortableApps\FMOD\` | Portable installation |
| **Custom** | `E:\Development\Audio\FMOD Engine\` | Developer setup |
| **Multiple Versions** | `C:\FMOD\2.02.16\` | Specific version |

### Required Files

FMOD API searches for these files in your custom path:

- `fmod.dll` (Core audio engine)
- `fmodstudio.dll` (Studio API)

**Automatic scanning** finds files in subdirectories like:
- `api/core/lib/x64/fmod.dll`
- `api/studio/lib/x64/fmodstudio.dll`

## 🌍 Multi-Language Support

### Supported Languages

| Language | Code | Status |
|----------|------|--------|
| **English** | `en_us` | ✅ Complete |
| **Polish** | `pl_pl` | ✅ Complete |

### English Translations

```json
{
  "config.fmodapi.fmod_enabled": "Enable FMOD Audio",
  "config.fmodapi.debug_logging": "Enable Debug Logging",
  "config.fmodapi.max_instances": "Maximum Sound Instances",
  "config.fmodapi.custom_path": "Custom FMOD Path"
}
```

### Polish Translations

```json
{
  "config.fmodapi.fmod_enabled": "Włącz Audio FMOD",
  "config.fmodapi.debug_logging": "Włącz Logowanie Debugowania",
  "config.fmodapi.max_instances": "Maksymalna Liczba Instancji Dźwięku",
  "config.fmodapi.custom_path": "Niestandardowa Ścieżka FMOD"
}
```

### Adding New Languages

To contribute a new language:

1. **Create language file:**
   ```
   src/main/resources/assets/fmodapi/lang/{locale}.json
   ```

2. **Add translations:**
   ```json
   {
     "config.fmodapi.fmod_enabled": "[Your Translation]",
     "config.fmodapi.debug_logging": "[Your Translation]",
     "config.fmodapi.max_instances": "[Your Translation]",
     "config.fmodapi.custom_path": "[Your Translation]"
   }
   ```

3. **Minecraft automatically uses it** based on user's language setting

## 🔧 Developer Configuration Access

### Reading Configuration Values

```java
// Get current configuration values
boolean fmodEnabled = FMODConfig.FMOD_ENABLED.get();
boolean debugMode = FMODConfig.DEBUG_LOGGING.get();
int maxInstances = FMODConfig.MAX_INSTANCES.get();
String customPath = FMODConfig.FMOD_CUSTOM_PATH.get();

// Example usage
if (FMODConfig.DEBUG_LOGGING.get()) {
    System.out.println("[FMOD API] Debug info: " + message);
}
```

### Config-Driven Features

```java
// Conditional behavior based on config
public static void debugLog(String message) {
    if (FMODConfig.DEBUG_LOGGING.get()) {
        System.out.println("[FMOD API Debug] " + message);
    }
}

// FMOD routing based on config
public static String playEvent(String eventName, Vec3D position, float volume, float pitch) {
    if (!FMODConfig.FMOD_ENABLED.get()) {
        return null; // Triggers OpenAL fallback
    }
    return FMODSystem.playEvent(eventName, position, volume, pitch);
}
```

### Listening for Config Changes

```java
// React to configuration changes
@SubscribeEvent
static void onConfigChange(final ModConfigEvent.Reloading event) {
    if (event.getConfig().getModId().equals("fmodapi")) {
        // Handle the change
        handleConfigurationUpdate();
    }
}

private static void handleConfigurationUpdate() {
    // Your custom logic here
    if (FMODConfig.DEBUG_LOGGING.get()) {
        System.out.println("Debug logging enabled");
    }
}
```

## 📊 Configuration File Structure

### Internal Structure

```java
// FMODConfig.java - Simplified structure
public class FMODConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Core configuration options
    public static final ModConfigSpec.BooleanValue FMOD_ENABLED;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;
    public static final ModConfigSpec.IntValue MAX_INSTANCES;
    public static final ModConfigSpec.ConfigValue<String> FMOD_CUSTOM_PATH;

    static {
        FMOD_ENABLED = BUILDER
            .comment("Enable FMOD audio system (takes effect immediately)")
            .translation("config.fmodapi.fmod_enabled")
            .define("fmodEnabled", true);

        DEBUG_LOGGING = BUILDER
            .comment("Enable debug logging for FMOD API")
            .translation("config.fmodapi.debug_logging")
            .define("debugLogging", false);

        MAX_INSTANCES = BUILDER
            .comment("Maximum number of concurrent FMOD sound instances (32-4096)")
            .translation("config.fmodapi.max_instances")
            .defineInRange("maxInstances", 512, 32, 4096);

        FMOD_CUSTOM_PATH = BUILDER
            .comment("Custom FMOD installation path (leave empty for auto-detection)")
            .translation("config.fmodapi.custom_path")
            .define("fmodCustomPath", "");
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}
```

### Configuration File Location

**NeoForge Config File:**
```
.minecraft/config/fmodapi-client.toml
```

**Example Config File:**
```toml
[fmodapi]
    # Enable FMOD audio system (takes effect immediately)
    fmodEnabled = true
    # Enable debug logging for FMOD API
    debugLogging = false
    # Maximum number of concurrent FMOD sound instances (32-4096)
    maxInstances = 512
    # Custom FMOD installation path (leave empty for auto-detection)
    fmodCustomPath = ""
```

## 🛠️ Advanced Configuration

### Performance Tuning

#### Low-End Systems
```toml
# Reduce resource usage
maxInstances = 128
debugLogging = false
```

#### High-End Systems
```toml
# Maximum quality
maxInstances = 2048
debugLogging = false
```

#### Development/Debug
```toml
# Full debugging
debugLogging = true
maxInstances = 512
```

### Custom Integration

```java
// Create custom config sections
public static final ModConfigSpec.BooleanValue CUSTOM_FEATURE;

static {
    CUSTOM_FEATURE = BUILDER
        .comment("Enable custom feature")
        .define("customFeature", false);
}
```

## 🔍 Troubleshooting Configuration

### Common Issues

::alert{type="error"}
**Config changes not applying**
- Ensure you're changing client config, not server
- Some changes require Minecraft restart
- Check for config file permissions
::

::alert{type="warning"}
**Custom path not working**
- Verify path exists and contains FMOD DLLs
- Use forward slashes or escaped backslashes
- Check that path is readable
::

::alert{type="info"}
**Debug logging overwhelming**
- Only enable when troubleshooting
- Disable after finding issues
- Consider using filtered log viewers
::

### Debug Configuration

```java
// Dump current configuration
public static void dumpConfig() {
    System.out.println("=== FMOD API Configuration ===");
    System.out.println("FMOD Enabled: " + FMOD_ENABLED.get());
    System.out.println("Debug Logging: " + DEBUG_LOGGING.get());
    System.out.println("Max Instances: " + MAX_INSTANCES.get());
    System.out.println("Custom Path: '" + FMOD_CUSTOM_PATH.get() + "'");
    System.out.println("==============================");
}
```

## 📚 Related Documentation

- 🚀 [**Getting Started**](/docs/getting-started) - Basic setup and installation
- 🎮 [**Native Integration**](/docs/integration) - Automatic Minecraft integration
- 📝 [**API Reference**](/api/reference) - Complete method documentation
- 🏗️ [**Advanced Guide**](/docs/advanced) - Technical deep dive

::alert{type="success"}
**Configuration is simple** - Most users need zero configuration. Advanced users have full control when needed.
::