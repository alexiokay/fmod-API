<template>
  <!-- Documentation Section -->
  <div class="py-24 px-6 relative bg-gradient-to-br from-blue-50 via-white to-cyan-50 overflow-hidden">
    <!-- Animated background decorations -->
    <div class="absolute inset-0">
      <div class="absolute top-10 right-10 w-72 h-72 bg-blue-200 rounded-full mix-blend-multiply filter blur-xl opacity-30 animate-pulse"></div>
      <div class="absolute bottom-10 left-10 w-72 h-72 bg-cyan-200 rounded-full mix-blend-multiply filter blur-xl opacity-30 animate-pulse" style="animation-delay: 3s;"></div>
    </div>

    <div class="relative max-w-6xl mx-auto text-center">
      <div class="inline-block p-2 bg-primary-100 rounded-full mb-6">
        <div class="text-4xl">📚</div>
      </div>
      <h2 class="text-4xl md:text-5xl font-bold mb-16 text-gray-900">
        Documentation
      </h2>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
        <FeatureCard
          title="Getting Started"
          description="Quick setup guide for users and developers"
          icon="i-heroicons-rocket-launch"
          to="/docs/getting-started"
          color-theme="indigo"
          variant="vertical"
        />

        <FeatureCard
          title="Native Integration"
          description="Automatic pause/resume and volume sync"
          icon="i-heroicons-puzzle-piece-20-solid"
          to="/docs/integration"
          color-theme="emerald"
          variant="vertical"
        />

        <FeatureCard
          title="API Reference"
          description="Complete method documentation"
          icon="i-heroicons-book-open"
          to="/docs/api-reference"
          color-theme="violet"
          variant="vertical"
        />

        <FeatureCard
          title="Configuration"
          description="Settings and customization options"
          icon="i-heroicons-cog-6-tooth"
          to="/docs/configuration"
          color-theme="amber"
          variant="vertical"
        />
      </div>
    </div>
  </div>
</template>