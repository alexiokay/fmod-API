<template>
  <div>
    <!-- Hero Section -->
    <div class="bg-gradient-to-br from-primary-600 via-purple-600 to-primary-800 text-white py-20 px-6">
      <div class="max-w-4xl mx-auto text-center">
        <h1 class="text-4xl md:text-6xl font-bold mb-6">
          🎵 FMOD API for Minecraft
        </h1>

        <p class="text-xl md:text-2xl mb-8 opacity-90">
          Professional 3D spatial audio engine integration for Minecraft NeoForge mods. Seamless native integration with automatic pause/resume and volume synchronization.
        </p>

        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <UButton
            to="/docs/getting-started"
            color="white"
            variant="solid"
            size="lg"
            icon="i-heroicons-rocket-launch"
          >
            Get Started
          </UButton>

          <UButton
            to="/api/reference"
            color="white"
            variant="outline"
            size="lg"
            icon="i-heroicons-book-open"
          >
            API Reference
          </UButton>
        </div>
      </div>
    </div>

    <!-- Key Features Section -->
    <div class="py-16 px-6">
      <div class="max-w-6xl mx-auto">
        <div class="text-center mb-12">
          <h2 class="text-3xl md:text-4xl font-bold mb-4 text-gray-900">
            ✨ Key Features
          </h2>
          <p class="text-lg text-gray-600">
            Experience professional-grade audio in your Minecraft mods
          </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-puzzle-piece-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  Native Integration
                </h3>
                <p class="text-gray-600 text-sm">
                  Automatically pauses and resumes with Minecraft menus. Respects all volume sliders. Behaves exactly like vanilla audio.
                </p>
              </div>
            </div>
          </UCard>

          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-speaker-wave-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  3D Spatial Audio
                </h3>
                <p class="text-gray-600 text-sm">
                  Professional FMOD Studio engine delivers rich, immersive soundscapes with accurate 3D positioning and environmental effects.
                </p>
              </div>
            </div>
          </UCard>

          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-shield-check-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  Graceful Fallback
                </h3>
                <p class="text-gray-600 text-sm">
                  Automatic OpenAL backup ensures audio never breaks. Toggle between FMOD and OpenAL without restarting the game.
                </p>
              </div>
            </div>
          </UCard>

          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-bolt-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  Instant Configuration
                </h3>
                <p class="text-gray-600 text-sm">
                  Runtime configuration changes take effect immediately. No restart required for enabling/disabling FMOD.
                </p>
              </div>
            </div>
          </UCard>

          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-users-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  Multi-Mod Compatible
                </h3>
                <p class="text-gray-600 text-sm">
                  Centralized FMOD management prevents conflicts between mods. Multiple mods can use FMOD simultaneously.
                </p>
              </div>
            </div>
          </UCard>

          <UCard class="h-full">
            <div class="flex items-start gap-4">
              <UIcon name="i-heroicons-code-bracket-20-solid" class="w-8 h-8 text-primary-600 mt-1 flex-shrink-0" />
              <div>
                <h3 class="text-lg font-semibold text-gray-900 mb-2">
                  Simple API
                </h3>
                <p class="text-gray-600 text-sm">
                  Clean, intuitive API with automatic resource management. Easy integration with just a few lines of code.
                </p>
              </div>
            </div>
          </UCard>
        </div>
      </div>
    </div>

    <!-- Quick Start Section -->
    <div class="bg-gray-50 py-16 px-6">
      <div class="max-w-6xl mx-auto text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-4 text-gray-900">
          🚀 Quick Start
        </h2>
        <p class="text-lg text-gray-600 mb-12">
          Get FMOD audio working in your mod in minutes
        </p>

        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
          <div class="text-left">
            <h3 class="text-xl font-semibold mb-4">For Mod Developers</h3>
            <div class="bg-gray-900 rounded-lg p-4 text-left">
              <pre class="text-green-400 text-sm"><code>// Basic usage - simple and clean
FMODAPI.playEventSimple("event:/vehicles/engine", x, y, z);

// Advanced usage with full control
String instanceId = FMODAPI.playEvent("event:/weapons/gunshot",
                                     new Vec3D(x, y, z),
                                     1.0f, 1.2f);</code></pre>
            </div>
            <p class="mt-4 text-gray-600">
              Integration is automatic - pause/resume and volume sync happen without any code changes!
            </p>
          </div>

          <div class="text-left">
            <h3 class="text-xl font-semibold mb-4">For Users</h3>
            <ol class="list-decimal list-inside space-y-2 text-gray-700">
              <li><strong>Download</strong> FMOD API from <a href="https://modrinth.com/mod/fmod-api" class="text-primary-600 hover:underline">Modrinth</a> or <a href="https://www.curseforge.com/minecraft/mc-mods/fmod-api" class="text-primary-600 hover:underline">CurseForge</a></li>
              <li><strong>Install</strong> FMOD Engine from <a href="https://www.fmod.com/download#fmodengine" class="text-primary-600 hover:underline">FMOD.com</a></li>
              <li><strong>Drop</strong> FMOD API JAR into your mods folder</li>
              <li><strong>Play</strong> - everything works automatically!</li>
            </ol>
            <p class="mt-4 text-gray-600">
              Optional: Configure via Mods → FMOD API → Config
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Documentation Section -->
    <div class="py-16 px-6">
      <div class="max-w-6xl mx-auto text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-12 text-gray-900">
          📚 Documentation
        </h2>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <UCard class="h-full hover:shadow-lg transition-shadow">
            <NuxtLink to="/docs/getting-started" class="block">
              <div class="text-center">
                <UIcon name="i-heroicons-rocket-launch" class="w-12 h-12 text-primary-600 mx-auto mb-4" />
                <h3 class="text-lg font-semibold text-gray-900 mb-2">Getting Started</h3>
                <p class="text-gray-600 text-sm">Quick setup guide for users and developers</p>
              </div>
            </NuxtLink>
          </UCard>

          <UCard class="h-full hover:shadow-lg transition-shadow">
            <NuxtLink to="/docs/integration" class="block">
              <div class="text-center">
                <UIcon name="i-heroicons-puzzle-piece-20-solid" class="w-12 h-12 text-primary-600 mx-auto mb-4" />
                <h3 class="text-lg font-semibold text-gray-900 mb-2">Native Integration</h3>
                <p class="text-gray-600 text-sm">Automatic pause/resume and volume sync</p>
              </div>
            </NuxtLink>
          </UCard>

          <UCard class="h-full hover:shadow-lg transition-shadow">
            <NuxtLink to="/api/reference" class="block">
              <div class="text-center">
                <UIcon name="i-heroicons-book-open" class="w-12 h-12 text-primary-600 mx-auto mb-4" />
                <h3 class="text-lg font-semibold text-gray-900 mb-2">API Reference</h3>
                <p class="text-gray-600 text-sm">Complete method documentation</p>
              </div>
            </NuxtLink>
          </UCard>

          <UCard class="h-full hover:shadow-lg transition-shadow">
            <NuxtLink to="/docs/configuration" class="block">
              <div class="text-center">
                <UIcon name="i-heroicons-cog-6-tooth" class="w-12 h-12 text-primary-600 mx-auto mb-4" />
                <h3 class="text-lg font-semibold text-gray-900 mb-2">Configuration</h3>
                <p class="text-gray-600 text-sm">Settings and customization options</p>
              </div>
            </NuxtLink>
          </UCard>
        </div>
      </div>
    </div>

    <!-- Call to Action Section -->
    <div class="bg-gray-900 text-white py-16 px-6">
      <div class="max-w-4xl mx-auto text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-4">
          💡 Ready to Get Started?
        </h2>
        <p class="text-lg mb-8 opacity-90">
          Join the growing community of developers using FMOD API for professional audio in Minecraft mods.
        </p>

        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <UButton
            to="/docs/getting-started"
            color="white"
            variant="solid"
            size="lg"
          >
            Get Started Now
          </UButton>

          <UButton
            to="https://modrinth.com/mod/fmod-api"
            target="_blank"
            color="white"
            variant="outline"
            size="lg"
            icon="i-heroicons-arrow-top-right-on-square"
          >
            Download from Modrinth
          </UButton>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// Configure SEO for homepage
useSeoMeta({
  title: 'FMOD API for Minecraft',
  description: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogTitle: 'FMOD API for Minecraft',
  ogDescription: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogType: 'website',
  twitterCard: 'summary_large_image'
})
</script>