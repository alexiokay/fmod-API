<template>
    <div class="w-full py-20 lg:py-32 flex items-center justify-center relative overflow-hidden bg-gray-50 dark:bg-gray-950">
        <!-- Background with Minecraft texture -->
        <div class="w-full flex absolute left-0 top-0 z-10 h-full">
            <div
              class="w-full h-full transition-opacity duration-300 opacity-20 dark:opacity-15"
              style="background-image: url(/textures/dirt.png); background-size: 80px 80px; background-repeat: repeat; mix-blend-mode: multiply; image-rendering: pixelated;"
            />
        </div>

        <!-- Overlay gradient -->
        <div class="absolute inset-0 z-10 transition-all duration-300 bg-gradient-to-b from-gray-50/80 via-white/60 to-gray-50/80 dark:from-gray-950/80 dark:via-gray-900/60 dark:to-gray-950/80" />

        <!-- Content -->
        <div class="relative max-w-[115rem] px-4 lg:px-8 w-full z-20">
            <!-- Header -->
            <div class="text-center mb-12 lg:mb-16">
                <div class="inline-block p-4 bg-orange-500/10 dark:bg-orange-600/10 rounded-lg mb-6 shadow-lg">
                    <div class="text-4xl">📚</div>
                </div>
                <h2 class="text-4xl lg:text-5xl xl:text-6xl font-bold mb-6 text-gray-900 dark:text-gray-50 drop-shadow-lg">
                    <span class="text-orange-500 dark:text-orange-500">Documentation</span>
                </h2>
            </div>

            <!-- Documentation Cards Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 lg:gap-8">
                <!-- Getting Started -->
                <NuxtLink
                  to="/getting-started"
                  class="group p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50 flex flex-col items-center text-center"
                >
                    <div class="p-4 bg-indigo-500/10 dark:bg-indigo-600/10 rounded-lg mb-4 group-hover:scale-110 transition-transform duration-300">
                        <UIcon name="i-heroicons-rocket-launch" class="size-8 text-indigo-600 dark:text-indigo-500" />
                    </div>
                    <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                        Getting Started
                    </h3>
                    <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                        Quick setup guide for users and developers
                    </p>
                </NuxtLink>

                <!-- Native Integration -->
                <NuxtLink
                  to="/guides/integration-overview"
                  class="group p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50 flex flex-col items-center text-center"
                >
                    <div class="p-4 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg mb-4 group-hover:scale-110 transition-transform duration-300">
                        <UIcon name="i-heroicons-puzzle-piece" class="size-8 text-emerald-600 dark:text-emerald-500" />
                    </div>
                    <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                        Native Integration
                    </h3>
                    <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                        Automatic pause/resume and volume sync
                    </p>
                </NuxtLink>

                <!-- API Reference -->
                <NuxtLink
                  to="/api-reference"
                  class="group p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50 flex flex-col items-center text-center"
                >
                    <div class="p-4 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg mb-4 group-hover:scale-110 transition-transform duration-300">
                        <UIcon name="i-heroicons-book-open" class="size-8 text-violet-600 dark:text-violet-500" />
                    </div>
                    <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                        API Reference
                    </h3>
                    <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                        Complete method documentation
                    </p>
                </NuxtLink>

                <!-- Configuration -->
                <NuxtLink
                  to="/configuration"
                  class="group p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50 flex flex-col items-center text-center"
                >
                    <div class="p-4 bg-amber-500/10 dark:bg-amber-600/10 rounded-lg mb-4 group-hover:scale-110 transition-transform duration-300">
                        <UIcon name="i-heroicons-cog-6-tooth" class="size-8 text-amber-600 dark:text-amber-500" />
                    </div>
                    <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                        Configuration
                    </h3>
                    <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                        Settings and customization options
                    </p>
                </NuxtLink>
            </div>
        </div>
    </div>
</template>