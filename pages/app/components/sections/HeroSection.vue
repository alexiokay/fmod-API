<template>
  <!-- Hero Section -->
  <div class="relative bg-gradient-to-br from-slate-800 via-slate-700 to-slate-900 text-white py-24 px-6 overflow-hidden">
    <!-- Background Pattern -->
    <div class="absolute inset-0 opacity-10">
      <div class="absolute inset-0 bg-gradient-to-r from-transparent via-white to-transparent transform -skew-y-12 scale-150"></div>
    </div>

    <div class="relative max-w-5xl mx-auto text-center">
      <div class="mb-8">
        <div class="text-6xl mb-4">🎵</div>
        <h1 class="text-5xl md:text-7xl font-extrabold mb-2 text-white">
          FMOD API
        </h1>
        <p class="text-2xl md:text-3xl font-light text-gray-300">for Minecraft</p>
      </div>

      <p class="text-xl md:text-2xl mb-12 text-gray-200 leading-relaxed max-w-3xl mx-auto">
        Professional 3D spatial audio engine integration for Minecraft NeoForge mods. Seamless native integration with automatic pause/resume and volume synchronization.
      </p>

      <div class="flex flex-col sm:flex-row gap-6 justify-center">
        <UButton
          to="/docs/getting-started"
          color="white"
          variant="solid"
          size="xl"
          icon="i-heroicons-rocket-launch"
          class="shadow-2xl hover:shadow-white/20 transform hover:scale-105 transition-all duration-200"
        >
          Get Started
        </UButton>

        <UButton
          to="/docs/api-reference"
          color="white"
          variant="outline"
          size="xl"
          icon="i-heroicons-book-open"
          class="backdrop-blur-sm hover:bg-white/10 transform hover:scale-105 transition-all duration-200"
        >
          Documentation
        </UButton>
      </div>
    </div>
  </div>
</template>