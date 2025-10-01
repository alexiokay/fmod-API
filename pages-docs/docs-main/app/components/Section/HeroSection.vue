<template>
  <!-- Hero Section - Modern Minecraft Style -->
  <div class="relative bg-gradient-to-b from-slate-900 to-black text-white py-32 px-6 overflow-hidden">
    <!-- Clean modern background -->
    <div class="absolute inset-0 bg-gradient-to-br from-slate-800/50 via-transparent to-emerald-900/20"></div>

    <div class="relative max-w-5xl mx-auto text-center">
      <div class="mb-8">
        <!-- Minecraft Note Block emoji -->
        <div class="text-6xl mb-4 animate-bounce">🎵</div>
        <h1 class="text-5xl md:text-7xl font-bold mb-2 text-white tracking-wider" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 4px 4px 0px rgba(0,0,0,0.5);">
          FMOD API
        </h1>
        <p class="text-2xl md:text-3xl font-bold text-emerald-400" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 2px 2px 0px rgba(0,0,0,0.5);">for Minecraft</p>
      </div>

      <p class="text-xl md:text-2xl mb-12 text-gray-100 leading-relaxed max-w-3xl mx-auto font-medium" style="text-shadow: 1px 1px 2px rgba(0,0,0,0.7);">
        Professional 3D spatial audio engine integration for Minecraft NeoForge mods. Seamless native integration with automatic pause/resume and volume synchronization.
      </p>

      <div class="flex flex-col sm:flex-row gap-6 justify-center">
        <UButton
          to="getting-started"
          size="xl"
          icon="i-heroicons-play"
          class="bg-emerald-600 hover:bg-emerald-500 text-white font-bold border-2 border-emerald-700 hover:border-emerald-600 shadow-[0_4px_0_0] shadow-emerald-800 hover:shadow-[0_2px_0_0] hover:translate-y-[2px] transition-all duration-100 px-8 py-4"
          style="font-family: 'Minecraft', 'Courier New', monospace;"
        >
          Get Started
        </UButton>

        <UButton
          to="api-reference"
          size="xl"
          icon="i-heroicons-book-open"
          class="bg-stone-700 hover:bg-stone-600 text-white font-bold border-2 border-stone-800 hover:border-stone-700 shadow-[0_4px_0_0] shadow-stone-900 hover:shadow-[0_2px_0_0] hover:translate-y-[2px] transition-all duration-100 px-8 py-4"
          style="font-family: 'Minecraft', 'Courier New', monospace;"
        >
          Documentation
        </UButton>
      </div>
    </div>
  </div>
</template>