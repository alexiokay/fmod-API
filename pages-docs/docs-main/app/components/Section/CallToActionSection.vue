<template>
  <!-- Call to Action Section - Modern Clean Style -->
  <div class="relative bg-gradient-to-b from-emerald-600 to-emerald-700 text-white py-32 px-6 overflow-hidden">

    <div class="relative max-w-5xl mx-auto text-center">
      <div class="text-6xl mb-6 animate-pulse">⭐</div>
      <h2 class="text-4xl md:text-6xl font-bold mb-6 text-white" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 3px 3px 0px rgba(0,0,0,0.5);">
        Ready to Get Started?
      </h2>
      <p class="text-xl md:text-2xl mb-12 text-gray-200 leading-relaxed max-w-3xl mx-auto font-medium" style="text-shadow: 1px 1px 2px rgba(0,0,0,0.7);">
        Join the growing community of developers using FMOD API for professional audio in Minecraft mods.
      </p>

      <div class="flex flex-col sm:flex-row gap-6 justify-center">
        <UButton
          to="getting-started"
          size="xl"
          icon="i-heroicons-sparkles"
          class="bg-violet-600 hover:bg-violet-500 text-white font-bold border-2 border-violet-700 hover:border-violet-600 shadow-[0_4px_0_0] shadow-violet-800 hover:shadow-[0_2px_0_0] hover:translate-y-[2px] transition-all duration-100 px-8 py-4"
          style="font-family: 'Minecraft', 'Courier New', monospace;"
        >
          Get Started Now
        </UButton>

        <UButton
          to="https://modrinth.com/mod/fmod-api"
          target="_blank"
          size="xl"
          icon="i-heroicons-arrow-down-tray"
          class="bg-emerald-700 hover:bg-emerald-600 text-white font-bold border-2 border-emerald-800 hover:border-emerald-700 shadow-[0_4px_0_0] shadow-emerald-900 hover:shadow-[0_2px_0_0] hover:translate-y-[2px] transition-all duration-100 px-8 py-4"
          style="font-family: 'Minecraft', 'Courier New', monospace;"
        >
          Download from Modrinth
        </UButton>
      </div>
    </div>
  </div>
</template>