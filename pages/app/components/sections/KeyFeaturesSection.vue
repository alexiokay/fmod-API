<template>
  <!-- Key Features Section -->
  <div class="py-24 px-6 relative bg-gradient-to-br from-purple-50 via-white to-indigo-50 overflow-hidden">
    <!-- Animated background decorations -->
    <div class="absolute inset-0">
      <div class="absolute top-20 left-10 w-72 h-72 bg-purple-200 rounded-full mix-blend-multiply filter blur-xl opacity-30 animate-pulse"></div>
      <div class="absolute bottom-20 right-10 w-72 h-72 bg-indigo-200 rounded-full mix-blend-multiply filter blur-xl opacity-30 animate-pulse" style="animation-delay: 2s;"></div>
    </div>

    <div class="relative max-w-6xl mx-auto">
      <div class="text-center mb-20">
        <div class="inline-block p-2 bg-primary-100 rounded-full mb-4">
          <div class="text-3xl">✨</div>
        </div>
        <h2 class="text-4xl md:text-5xl font-bold mb-6 text-gray-900">
          Key Features
        </h2>
        <p class="text-xl text-gray-700 max-w-3xl mx-auto leading-relaxed">
          Experience professional-grade audio in your Minecraft mods with industry-standard FMOD technology
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <FeatureCard
          title="Native Integration"
          description="Automatically pauses and resumes with Minecraft menus. Respects all volume sliders. Behaves exactly like vanilla audio."
          icon="i-heroicons-puzzle-piece-20-solid"
          color-theme="purple"
          variant="horizontal"
        />

        <FeatureCard
          title="3D Spatial Audio"
          description="Professional FMOD Studio engine delivers rich, immersive soundscapes with accurate 3D positioning and environmental effects."
          icon="i-heroicons-speaker-wave-20-solid"
          color-theme="emerald"
          variant="horizontal"
        />

        <FeatureCard
          title="Graceful Fallback"
          description="Automatic OpenAL backup ensures audio never breaks. Toggle between FMOD and OpenAL without restarting the game."
          icon="i-heroicons-shield-check-20-solid"
          color-theme="sky"
          variant="horizontal"
        />

        <FeatureCard
          title="Instant Configuration"
          description="Runtime configuration changes take effect immediately. No restart required for enabling/disabling FMOD."
          icon="i-heroicons-bolt-20-solid"
          color-theme="amber"
          variant="horizontal"
        />

        <FeatureCard
          title="Multi-Mod Compatible"
          description="Centralized FMOD management prevents conflicts between mods. Multiple mods can use FMOD simultaneously."
          icon="i-heroicons-users-20-solid"
          color-theme="pink"
          variant="horizontal"
        />

        <FeatureCard
          title="Simple API"
          description="Clean, intuitive API with automatic resource management. Easy integration with just a few lines of code."
          icon="i-heroicons-code-bracket-20-solid"
          color-theme="violet"
          variant="horizontal"
        />
      </div>
    </div>
  </div>
</template>