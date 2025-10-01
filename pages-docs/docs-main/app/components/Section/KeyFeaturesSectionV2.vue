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
            <div class="text-center mb-12 lg:mb-20">
                <div class="inline-block p-4 bg-orange-500/10 dark:bg-orange-600/10 rounded-lg mb-6 shadow-lg">
                    <div class="text-4xl">⚡</div>
                </div>
                <h2 class="text-4xl lg:text-5xl xl:text-6xl font-bold mb-6 text-gray-900 dark:text-gray-50 drop-shadow-lg">
                    Key <span class="text-orange-500 dark:text-orange-500">Features</span>
                </h2>
                <p class="text-base lg:text-lg max-w-3xl mx-auto text-gray-700 dark:text-gray-300">
                    Experience professional-grade audio in your Minecraft mods with industry-standard FMOD technology
                </p>
            </div>

            <!-- Features Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 lg:gap-8">
                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-purple-500/10 dark:bg-purple-600/10 rounded-lg">
                            <UIcon name="i-heroicons-puzzle-piece" class="size-6 text-purple-600 dark:text-purple-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                Native Integration
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Automatically pauses and resumes with Minecraft menus. Respects all volume sliders. Behaves exactly like vanilla audio.
                            </p>
                        </div>
                    </div>
                </div>

                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                            <UIcon name="i-heroicons-speaker-wave" class="size-6 text-emerald-600 dark:text-emerald-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                3D Spatial Audio
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Professional FMOD Studio engine delivers rich, immersive soundscapes with accurate 3D positioning and environmental effects.
                            </p>
                        </div>
                    </div>
                </div>

                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-sky-500/10 dark:bg-sky-600/10 rounded-lg">
                            <UIcon name="i-heroicons-shield-check" class="size-6 text-sky-600 dark:text-sky-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                Graceful Fallback
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Automatic OpenAL backup ensures audio never breaks. Toggle between FMOD and OpenAL without restarting the game.
                            </p>
                        </div>
                    </div>
                </div>

                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-amber-500/10 dark:bg-amber-600/10 rounded-lg">
                            <UIcon name="i-heroicons-bolt" class="size-6 text-amber-600 dark:text-amber-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                Instant Configuration
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Runtime configuration changes take effect immediately. No restart required for enabling/disabling FMOD.
                            </p>
                        </div>
                    </div>
                </div>

                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-pink-500/10 dark:bg-pink-600/10 rounded-lg">
                            <UIcon name="i-heroicons-users" class="size-6 text-pink-600 dark:text-pink-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                Multi-Mod Compatible
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Centralized FMOD management prevents conflicts between mods. Multiple mods can use FMOD simultaneously.
                            </p>
                        </div>
                    </div>
                </div>

                <div
                  class="p-6 lg:p-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 border-2 border-orange-500/20 dark:border-orange-600/30 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm hover:scale-105 hover:border-orange-500/40 dark:hover:border-orange-600/50"
                >
                    <div class="flex items-start gap-4">
                        <div class="flex-shrink-0 p-3 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg">
                            <UIcon name="i-heroicons-code-bracket" class="size-6 text-violet-600 dark:text-violet-500" />
                        </div>
                        <div>
                            <h3 class="text-xl font-bold mb-2 text-gray-900 dark:text-gray-50">
                                Simple API
                            </h3>
                            <p class="text-sm lg:text-base text-gray-700 dark:text-gray-300">
                                Clean, intuitive API with automatic resource management. Easy integration with just a few lines of code.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>