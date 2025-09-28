<template>
  <!-- Quick Start Section -->
  <div class="bg-gradient-to-br from-slate-900 via-gray-900 to-slate-800 py-20 px-6 relative overflow-hidden">
    <!-- Background decoration -->
    <div class="absolute inset-0 bg-gradient-to-r from-primary-500/10 to-purple-500/10"></div>

    <div class="relative max-w-6xl mx-auto text-center">
      <div class="inline-block p-2 bg-primary-500/20 rounded-full mb-6">
        <div class="text-4xl">🚀</div>
      </div>
      <h2 class="text-4xl md:text-5xl font-bold mb-6 text-white">
        Quick Start
      </h2>
      <p class="text-xl text-gray-300 mb-16 max-w-2xl mx-auto leading-relaxed">
        Get FMOD audio working in your mod in minutes with our streamlined setup process
      </p>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-16">
        <div class="text-left">
          <div class="flex items-center gap-3 mb-6">
            <div class="p-2 bg-green-500/20 rounded-lg">
              <UIcon name="i-heroicons-code-bracket" class="w-6 h-6 text-green-400" />
            </div>
            <h3 class="text-2xl font-bold text-white">For Mod Developers</h3>
          </div>
          <div class="bg-gray-800/50 backdrop-blur-sm rounded-xl p-6 border border-gray-700/50 text-left shadow-2xl">
            <pre class="text-green-400 text-sm"><code>// Basic usage - simple and clean
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
            <div class="p-2 bg-blue-500/20 rounded-lg">
              <UIcon name="i-heroicons-user" class="w-6 h-6 text-blue-400" />
            </div>
            <h3 class="text-2xl font-bold text-white">For Users</h3>
          </div>
          <ol class="list-decimal list-inside space-y-4 text-gray-200">
            <li><strong class="text-white">Download</strong> FMOD API from <a href="https://modrinth.com/mod/fmod-api" class="text-primary-400 hover:text-primary-300 underline decoration-primary-400/50 hover:decoration-primary-300">Modrinth</a> or <a href="https://www.curseforge.com/minecraft/mc-mods/fmod-api" class="text-primary-400 hover:text-primary-300 underline decoration-primary-400/50 hover:decoration-primary-300">CurseForge</a></li>
            <li><strong class="text-white">Install</strong> FMOD Engine from <a href="https://www.fmod.com/download#fmodengine" class="text-primary-400 hover:text-primary-300 underline decoration-primary-400/50 hover:decoration-primary-300">FMOD.com</a></li>
            <li><strong class="text-white">Drop</strong> FMOD API JAR into your mods folder</li>
            <li><strong class="text-white">Play</strong> - everything works automatically!</li>
          </ol>
          <p class="mt-6 text-gray-300 leading-relaxed">
            <strong class="text-gray-200">Optional:</strong> Configure via Mods → FMOD API → Config
          </p>
        </div>
      </div>
    </div>
  </div>
</template>