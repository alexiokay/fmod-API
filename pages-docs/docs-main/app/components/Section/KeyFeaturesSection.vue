<template>
  <!-- Key Features Section - Modern Clean Style -->
  <div class="py-32 px-6 relative bg-white dark:bg-gray-900 overflow-hidden">

    <div class="relative max-w-6xl mx-auto">
      <div class="text-center mb-20">
        <div class="inline-block p-4 bg-emerald-100 dark:bg-emerald-900/50 rounded-lg mb-6">
          <div class="text-4xl">⚡</div>
        </div>
        <h2 class="text-4xl md:text-5xl font-bold mb-6 text-gray-900 dark:text-white" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 2px 2px 0px rgba(0,0,0,0.2);">
          Key Features
        </h2>
        <p class="text-xl text-gray-700 dark:text-gray-200 max-w-3xl mx-auto leading-relaxed font-medium">
          Experience professional-grade audio in your Minecraft mods with industry-standard FMOD technology
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <UiFeatureCard
          title="Native Integration"
          description="Automatically pauses and resumes with Minecraft menus. Respects all volume sliders. Behaves exactly like vanilla audio."
          icon="i-heroicons-puzzle-piece"
          color-theme="purple"
          variant="horizontal"
        />

        <UiFeatureCard
          title="3D Spatial Audio"
          description="Professional FMOD Studio engine delivers rich, immersive soundscapes with accurate 3D positioning and environmental effects."
          icon="i-heroicons-speaker-wave"
          color-theme="emerald"
          variant="horizontal"
        />

        <UiFeatureCard
          title="Graceful Fallback"
          description="Automatic OpenAL backup ensures audio never breaks. Toggle between FMOD and OpenAL without restarting the game."
          icon="i-heroicons-shield-check"
          color-theme="sky"
          variant="horizontal"
        />

        <UiFeatureCard
          title="Instant Configuration"
          description="Runtime configuration changes take effect immediately. No restart required for enabling/disabling FMOD."
          icon="i-heroicons-bolt"
          color-theme="amber"
          variant="horizontal"
        />

        <UiFeatureCard
          title="Multi-Mod Compatible"
          description="Centralized FMOD management prevents conflicts between mods. Multiple mods can use FMOD simultaneously."
          icon="i-heroicons-users"
          color-theme="pink"
          variant="horizontal"
        />

        <UiFeatureCard
          title="Simple API"
          description="Clean, intuitive API with automatic resource management. Easy integration with just a few lines of code."
          icon="i-heroicons-code-bracket"
          color-theme="violet"
          variant="horizontal"
        />
      </div>
    </div>
  </div>
</template>