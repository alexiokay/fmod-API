<template>
    <div class="w-full py-20 lg:py-32 flex items-center justify-center relative overflow-hidden bg-gray-900 dark:bg-black">
        <!-- Background with Minecraft texture -->
        <div class="w-full flex absolute left-0 top-0 z-10 h-full">
            <div
              class="w-full h-full transition-opacity duration-300 opacity-[0.12] dark:opacity-10"
              style="background-image: url(/textures/dirt.png); background-size: 80px 80px; background-repeat: repeat; mix-blend-mode: multiply; image-rendering: pixelated;"
            />
        </div>

        <!-- Overlay gradient -->
        <div class="absolute inset-0 z-10 transition-all duration-300 bg-gradient-to-b from-gray-900/80 via-gray-800/60 to-gray-900/80 dark:from-black/80 dark:via-gray-900/60 dark:to-black/80" />

        <!-- Content -->
        <div class="relative max-w-[115rem] px-4 lg:px-8 w-full z-20">
            <!-- Header -->
            <div class="text-center mb-12 lg:mb-16">
                <div class="inline-block p-4 bg-orange-500/10 dark:bg-orange-600/10 rounded-lg mb-6 shadow-lg">
                    <div class="text-4xl">🚀</div>
                </div>
                <h2 class="text-4xl lg:text-5xl xl:text-6xl font-bold mb-6 text-white dark:text-gray-50 drop-shadow-lg">
                    Quick <span class="text-orange-500 dark:text-orange-500">Start</span>
                </h2>
                <p class="text-base lg:text-lg max-w-2xl mx-auto text-gray-300 dark:text-gray-300">
                    Get FMOD audio working in your mod in minutes with our streamlined setup process
                </p>
            </div>

            <!-- Two Column Grid -->
            <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 lg:gap-16">
                <!-- For Mod Developers -->
                <div class="text-left">
                    <div class="flex items-center gap-3 mb-6">
                        <div class="p-3 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg shadow-lg">
                            <UIcon name="i-heroicons-command-line" class="size-6 text-emerald-500 dark:text-emerald-400" />
                        </div>
                        <h3 class="text-2xl lg:text-3xl font-bold text-white drop-shadow-lg">
                            For Mod Developers
                        </h3>
                    </div>
                    <div class="bg-black/60 dark:bg-black/80 backdrop-blur-sm rounded-lg p-6 lg:p-8 border-2 border-orange-500/30 dark:border-orange-600/40 shadow-lg">
                        <pre class="text-emerald-400 dark:text-emerald-300 text-sm font-mono overflow-x-auto"><code>// Basic usage - simple and clean
FMODAPI.playEventSimple("event:/vehicles/engine", x, y, z);

// Advanced usage with full control
String instanceId = FMODAPI.playEvent("event:/weapons/gunshot",
                                     new Vec3D(x, y, z),
                                     1.0f, 1.2f);</code></pre>
                    </div>
                    <p class="mt-6 text-gray-200 dark:text-gray-300 leading-relaxed">
                        Integration is automatic - pause/resume and volume sync happen without any code changes!
                    </p>
                </div>

                <!-- For Users -->
                <div class="text-left">
                    <div class="flex items-center gap-3 mb-6">
                        <div class="p-3 bg-cyan-500/10 dark:bg-cyan-600/10 rounded-lg shadow-lg">
                            <UIcon name="i-heroicons-cube" class="size-6 text-cyan-500 dark:text-cyan-400" />
                        </div>
                        <h3 class="text-2xl lg:text-3xl font-bold text-white drop-shadow-lg">
                            For Users
                        </h3>
                    </div>
                    <div class="space-y-6">
                        <div class="flex items-start gap-4 p-4 lg:p-6 bg-white/5 dark:bg-white/5 backdrop-blur-sm rounded-lg border-2 border-orange-500/20 dark:border-orange-600/30 shadow-lg hover:border-orange-500/40 dark:hover:border-orange-600/50 transition-all duration-300">
                            <div class="flex-shrink-0 w-8 h-8 rounded-full bg-orange-500 dark:bg-orange-600 flex items-center justify-center text-white font-bold shadow-lg">
                                1
                            </div>
                            <div>
                                <p class="text-gray-200 dark:text-gray-200">
                                    <strong class="text-emerald-400 dark:text-emerald-400">Download</strong> FMOD API from <a href="https://modrinth.com/mod/fmod-api" class="text-cyan-400 dark:text-cyan-400 hover:text-cyan-300 dark:hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">Modrinth</a> or <a href="https://www.curseforge.com/minecraft/mc-mods/fmod-api" class="text-cyan-400 dark:text-cyan-400 hover:text-cyan-300 dark:hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">CurseForge</a>
                                </p>
                            </div>
                        </div>

                        <div class="flex items-start gap-4 p-4 lg:p-6 bg-white/5 dark:bg-white/5 backdrop-blur-sm rounded-lg border-2 border-orange-500/20 dark:border-orange-600/30 shadow-lg hover:border-orange-500/40 dark:hover:border-orange-600/50 transition-all duration-300">
                            <div class="flex-shrink-0 w-8 h-8 rounded-full bg-orange-500 dark:bg-orange-600 flex items-center justify-center text-white font-bold shadow-lg">
                                2
                            </div>
                            <div>
                                <p class="text-gray-200 dark:text-gray-200">
                                    <strong class="text-emerald-400 dark:text-emerald-400">Install</strong> FMOD Engine from <a href="https://www.fmod.com/download#fmodengine" class="text-cyan-400 dark:text-cyan-400 hover:text-cyan-300 dark:hover:text-cyan-300 underline decoration-cyan-400/50 hover:decoration-cyan-300 transition-colors">FMOD.com</a>
                                </p>
                            </div>
                        </div>

                        <div class="flex items-start gap-4 p-4 lg:p-6 bg-white/5 dark:bg-white/5 backdrop-blur-sm rounded-lg border-2 border-orange-500/20 dark:border-orange-600/30 shadow-lg hover:border-orange-500/40 dark:hover:border-orange-600/50 transition-all duration-300">
                            <div class="flex-shrink-0 w-8 h-8 rounded-full bg-orange-500 dark:bg-orange-600 flex items-center justify-center text-white font-bold shadow-lg">
                                3
                            </div>
                            <div>
                                <p class="text-gray-200 dark:text-gray-200">
                                    <strong class="text-emerald-400 dark:text-emerald-400">Drop</strong> FMOD API JAR into your mods folder
                                </p>
                            </div>
                        </div>

                        <div class="flex items-start gap-4 p-4 lg:p-6 bg-white/5 dark:bg-white/5 backdrop-blur-sm rounded-lg border-2 border-orange-500/20 dark:border-orange-600/30 shadow-lg hover:border-orange-500/40 dark:hover:border-orange-600/50 transition-all duration-300">
                            <div class="flex-shrink-0 w-8 h-8 rounded-full bg-orange-500 dark:bg-orange-600 flex items-center justify-center text-white font-bold shadow-lg">
                                4
                            </div>
                            <div>
                                <p class="text-gray-200 dark:text-gray-200">
                                    <strong class="text-emerald-400 dark:text-emerald-400">Play</strong> - everything works automatically!
                                </p>
                            </div>
                        </div>
                    </div>
                    <p class="mt-6 text-gray-300 dark:text-gray-300 leading-relaxed">
                        <strong class="text-gray-200 dark:text-gray-200">Optional:</strong> Configure via Mods → FMOD API → Config
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

