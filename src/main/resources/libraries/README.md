# FMOD Libraries Setup

## Important Licensing Notice

**The FMOD DLL files are NOT included in this repository due to licensing restrictions.**

According to FMOD's licensing terms, we cannot distribute the FMOD runtime libraries (DLLs) with our mod. You must download and install them yourself.

## Required Files

You need to place the following files in this directory (`src/main/resources/libraries/`):

- `fmod.dll` - FMOD Core Engine library
- `fmodstudio.dll` - FMOD Studio API library

## Download Instructions

1. **Download FMOD Engine**
   - Go to: https://www.fmod.com/download#fmodengine
   - Download **FMOD Engine version 2.02.16** (or compatible version)
   - You may need to create a free FMOD account

2. **Install FMOD Engine**
   - Run the installer
   - Complete the installation process

3. **Locate the DLL files**
   - Navigate to your FMOD Engine installation folder
   - Look for the `api/core/lib/x64/` and `api/studio/lib/x64/` directories
   - Copy `fmod.dll` and `fmodstudio.dll` from these locations

4. **Place the files**
   - Copy both DLL files to this directory: `src/main/resources/libraries/`
   - The mod will automatically load these libraries at runtime

## File Structure

After setup, this directory should contain:
```
src/main/resources/libraries/
├── README.md (this file)
├── fmod.dll
└── fmodstudio.dll
```

## Licensing Information

- FMOD is a commercial audio engine by Firelight Technologies
- Free for non-commercial use and indie development
- Commercial licenses required for commercial projects
- Visit https://www.fmod.com/legal for full licensing terms

## Troubleshooting

If you encounter issues:

1. **Missing DLL errors**: Ensure both `fmod.dll` and `fmodstudio.dll` are present in this directory
2. **Version mismatch**: Make sure you downloaded FMOD Engine version 2.02.16
3. **Architecture mismatch**: Ensure you're using the x64 (64-bit) versions of the DLLs
4. **Permission errors**: Make sure the DLL files are not read-only

## Alternative Audio Fallback

If FMOD libraries are not available or fail to load, the mod will automatically fall back to Minecraft's built-in OpenAL audio system. The mod will continue to function, but without FMOD's advanced 3D spatial audio features.