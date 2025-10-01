<template>
  <!-- Documentation Section - Modern Clean Style -->
  <div class="py-32 px-6 relative bg-gray-50 dark:bg-gray-800 overflow-hidden">

    <div class="relative max-w-6xl mx-auto text-center">
      <div class="inline-block p-4 bg-indigo-100 dark:bg-indigo-900/50 rounded-lg mb-6">
        <div class="text-4xl">📚</div>
      </div>
      <h2 class="text-4xl md:text-5xl font-bold mb-16 text-gray-900 dark:text-white" style="font-family: 'Minecraft', 'Courier New', monospace; text-shadow: 2px 2px 0px rgba(0,0,0,0.2);">
        Documentation
      </h2>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
        <UiFeatureCard
          title="Getting Started"
          description="Quick setup guide for users and developers"
          icon="i-heroicons-rocket-launch"
          to="getting-started"
          color-theme="indigo"
          variant="vertical"
        />

        <UiFeatureCard
          title="Native Integration"
          description="Automatic pause/resume and volume sync"
          icon="i-heroicons-puzzle-piece"
          to="guides/integration-overview"
          color-theme="emerald"
          variant="vertical"
        />

        <UiFeatureCard
          title="API Reference"
          description="Complete method documentation"
          icon="i-heroicons-book-open"
          to="api-reference"
          color-theme="violet"
          variant="vertical"
        />

        <UiFeatureCard
          title="Configuration"
          description="Settings and customization options"
          icon="i-heroicons-cog-6-tooth"
          to="configuration"
          color-theme="amber"
          variant="vertical"
        />
      </div>
    </div>
  </div>
</template>