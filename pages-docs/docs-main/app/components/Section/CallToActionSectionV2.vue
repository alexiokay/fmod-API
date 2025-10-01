<template>
    <div class="w-full py-20 lg:py-32 flex items-center justify-center relative overflow-hidden bg-gradient-to-b from-orange-500 to-orange-600 dark:from-orange-600 dark:to-orange-700">
        <!-- Background with Minecraft texture -->
        <div class="w-full flex absolute left-0 top-0 z-10 h-full">
            <div
              class="w-full h-full transition-opacity duration-300 opacity-10 dark:opacity-[0.08]"
              style="background-image: url(/textures/dirt.png); background-size: 80px 80px; background-repeat: repeat; mix-blend-mode: multiply; image-rendering: pixelated;"
            />
        </div>

        <!-- Overlay gradient -->
        <div class="absolute inset-0 z-10 transition-all duration-300 bg-gradient-to-b from-orange-500/40 via-orange-600/20 to-orange-500/40 dark:from-orange-600/40 dark:via-orange-700/20 dark:to-orange-600/40" />

        <!-- Content -->
        <div class="relative max-w-5xl px-4 lg:px-8 w-full z-20 text-center">
            <div class="text-6xl mb-6 animate-pulse">⭐</div>
            <h2 class="text-4xl lg:text-5xl xl:text-6xl font-bold mb-6 text-white drop-shadow-lg">
                Ready to Get <span class="text-yellow-300 dark:text-yellow-200">Started?</span>
            </h2>
            <p class="text-base lg:text-xl xl:text-2xl mb-12 text-white/90 dark:text-gray-100 leading-relaxed max-w-3xl mx-auto drop-shadow-md">
                Join the growing community of developers using FMOD API for professional audio in Minecraft mods.
            </p>

            <div class="flex flex-col sm:flex-row gap-6 justify-center">
                <NuxtLink
                  to="/getting-started"
                  class="bg-violet-600 hover:bg-violet-500 dark:bg-violet-700 dark:hover:bg-violet-600 text-white font-bold rounded-lg px-8 py-4 shadow-lg hover:shadow-xl transition-all duration-300 flex items-center justify-center gap-2 hover:scale-105 border-2 border-violet-700 dark:border-violet-800"
                >
                    <UIcon name="i-heroicons-sparkles" class="size-5" />
                    Get Started Now
                </NuxtLink>

                <NuxtLink
                  to="https://modrinth.com/mod/fmod-api"
                  target="_blank"
                  class="bg-white/10 hover:bg-white/20 dark:bg-white/10 dark:hover:bg-white/20 text-white font-bold rounded-lg px-8 py-4 shadow-lg hover:shadow-xl transition-all duration-300 flex items-center justify-center gap-2 hover:scale-105 border-2 border-white/30 dark:border-white/30 backdrop-blur-sm"
                >
                    <UIcon name="i-heroicons-arrow-down-tray" class="size-5" />
                    Download from Modrinth
                </NuxtLink>
            </div>
        </div>
    </div>
</template>

