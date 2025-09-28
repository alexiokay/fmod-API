<template>
  <!-- Call to Action Section -->
  <div class="relative bg-gradient-to-br from-gray-800 via-gray-700 to-gray-900 text-white py-24 px-6 overflow-hidden">
    <!-- Background decoration -->
    <div class="absolute inset-0 bg-gradient-to-r from-transparent via-white/5 to-transparent transform -skew-y-12 scale-150"></div>

    <div class="relative max-w-5xl mx-auto text-center">
      <div class="text-6xl mb-6">💡</div>
      <h2 class="text-4xl md:text-6xl font-extrabold mb-6 text-white">
        Ready to Get Started?
      </h2>
      <p class="text-xl md:text-2xl mb-12 text-gray-200 leading-relaxed max-w-3xl mx-auto">
        Join the growing community of developers using FMOD API for professional audio in Minecraft mods.
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
          Get Started Now
        </UButton>

        <UButton
          to="https://modrinth.com/mod/fmod-api"
          target="_blank"
          color="white"
          variant="outline"
          size="xl"
          icon="i-heroicons-arrow-top-right-on-square"
          class="backdrop-blur-sm hover:bg-white/10 transform hover:scale-105 transition-all duration-200"
        >
          Download from Modrinth
        </UButton>
      </div>
    </div>
  </div>
</template>