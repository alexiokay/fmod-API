<template>
  <!-- Quick Start Section - Modern Clean Style -->
  <div class="bg-gray-900 py-32 px-6 relative overflow-hidden">

    <div class="relative max-w-6xl mx-auto text-center">
      <div class="inline-block p-4 bg-gray-800 rounded-lg mb-6">
        <div class="text-4xl">🚀</div>
      </div>
      <h2 class="text-4xl md:text-5xl font-bold mb-6 text-white" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 2px 2px 0px rgba(0,0,0,0.5);">
        Quick Start
      </h2>
      <p class="text-xl text-gray-300 mb-16 max-w-2xl mx-auto leading-relaxed font-medium">
        Get FMOD audio working in your mod in minutes with our streamlined setup process
      </p>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-16">
        <div class="text-left">
          <div class="flex items-center gap-3 mb-6">
            <div class="p-2 bg-emerald-900/50 rounded-sm shadow-[0_2px_0_0] shadow-emerald-950">
              <UIcon name="i-heroicons-command-line" class="w-6 h-6 text-emerald-400" />
            </div>
            <h3 class="text-2xl font-bold text-white" style="font-family: 'Minecraft', 'Courier New', monospace;">For Mod Developers</h3>
          </div>
          <div class="bg-black/50 backdrop-blur-sm rounded-sm p-6 border-2 border-emerald-900/50 text-left shadow-[0_4px_0_0] shadow-black">
            <pre class="text-emerald-400 text-sm font-mono"><code>// Basic usage - simple and clean
FMODAPI.playEventSimple("event:/vehicles/engine", x, y, z);

// Advanced usage with full control
String instanceId = FMODAPI.playEvent("event:/weapons/gunshot",
                                     new Vec3D(x, y, z),
                                     1.0f, 1.2f);</code></pre>
          </div>
          <p class="mt-6 text-gray-200 leading-relaxed">
            Integration is automatic - pause/resume and volume sync happen without any code changes!
          </p>
        </div>

        <div class="text-left">
          <div class="flex items-center gap-3 mb-6">
            <div class="p-2 bg-cyan-900/50 rounded-sm shadow-[0_2px_0_0] shadow-cyan-950">
              <UIcon name="i-heroicons-cube" class="w-6 h-6 text-cyan-400" />
            </div>
            <h3 class="text-2xl font-bold text-white" style="font-family: 'Minecraft', 'Courier New', monospace;">For Users</h3>
          </div>
          <ol class="list-decimal list-inside space-y-4 text-gray-200">
            <li><strong class="text-emerald-400">Download</strong> FMOD API from <a href="https://modrinth.com/mod/fmod-api" class="text-cyan-400 hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">Modrinth</a> or <a href="https://www.curseforge.com/minecraft/mc-mods/fmod-api" class="text-cyan-400 hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">CurseForge</a></li>
            <li><strong class="text-emerald-400">Install</strong> FMOD Engine from <a href="https://www.fmod.com/download#fmodengine" class="text-cyan-400 hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">FMOD.com</a></li>
            <li><strong class="text-emerald-400">Drop</strong> FMOD API JAR into your mods folder</li>
            <li><strong class="text-emerald-400">Play</strong> - everything works automatically!</li>
          </ol>
          <p class="mt-6 text-gray-300 leading-relaxed">
            <strong class="text-gray-200">Optional:</strong> Configure via Mods → FMOD API → Config
          </p>
        </div>
      </div>
    </div>
  </div>
</template>