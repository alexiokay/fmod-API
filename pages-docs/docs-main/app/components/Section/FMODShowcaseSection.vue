<template>
    <div class="w-full py-20 lg:py-32 flex items-center justify-center relative overflow-hidden bg-white dark:bg-gray-900">
        <!-- Background with Minecraft texture -->
        <div class="w-full flex absolute left-0 top-0 z-10 h-full">
            <div
              class="w-full h-full transition-opacity duration-300 opacity-20 dark:opacity-15"
              style="background-image: url(/textures/dirt.png); background-size: 80px 80px; background-repeat: repeat; mix-blend-mode: multiply; image-rendering: pixelated;"
            />
        </div>

        <!-- Gradient overlay -->
        <div class="absolute inset-0 z-10 transition-all duration-300 bg-gradient-to-b from-white/80 via-gray-50/60 to-white/80 dark:from-gray-900/80 dark:via-gray-950/60 dark:to-gray-900/80" />

        <!-- Content -->
        <div class="relative max-w-[115rem] px-4 lg:px-8 w-full z-20">
            <!-- Header -->
            <div class="text-center mb-16 lg:mb-20">
                <div class="inline-flex items-center gap-3 mb-6">
                    <div class="h-px w-12 bg-gradient-to-r from-transparent to-orange-500 dark:to-orange-500"></div>
                    <div class="px-4 py-2 bg-orange-500/10 dark:bg-orange-600/10 border-2 border-orange-500/30 dark:border-orange-600/30 rounded-lg backdrop-blur-sm shadow-lg">
                        <span class="text-orange-600 dark:text-orange-400 text-sm font-bold tracking-wider uppercase">Next-Gen Audio</span>
                    </div>
                    <div class="h-px w-12 bg-gradient-to-l from-transparent to-orange-500 dark:to-orange-500"></div>
                </div>
                <h2 class="text-4xl lg:text-5xl xl:text-6xl font-bold mb-6 text-gray-900 dark:text-gray-50 drop-shadow-lg">
                    Why <span class="text-orange-500 dark:text-orange-500">FMOD</span>?
                </h2>
                <p class="text-base lg:text-lg max-w-3xl mx-auto text-gray-700 dark:text-gray-300">
                    Industry-standard audio middleware trusted by AAA game studios worldwide
                </p>
            </div>

            <!-- Main Content Grid -->
            <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 lg:gap-12 mb-16">
                <!-- What is FMOD -->
                <div class="p-8 lg:p-10 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm rounded-lg border-2 border-orange-500/30 dark:border-orange-600/40 shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-[1.02]">
                    <div class="flex items-center gap-4 mb-6">
                        <div class="p-3 bg-orange-500/10 dark:bg-orange-600/10 rounded-lg shadow-lg">
                            <UIcon name="i-heroicons-speaker-wave" class="size-8 text-orange-600 dark:text-orange-500" />
                        </div>
                        <h3 class="text-2xl lg:text-3xl font-bold text-gray-900 dark:text-gray-50">
                            What is FMOD?
                        </h3>
                    </div>
                    <div class="space-y-4 text-gray-700 dark:text-gray-300">
                        <p class="leading-relaxed">
                            <span class="text-orange-600 dark:text-orange-400 font-semibold">FMOD Studio</span> is a professional audio engine used in thousands of games including <span class="text-orange-600 dark:text-orange-400 font-semibold">Fortnite, Apex Legends, The Witcher 3,</span> and <span class="text-orange-600 dark:text-orange-400 font-semibold">Minecraft Bedrock Edition</span>.
                        </p>
                        <p class="leading-relaxed">
                            It provides a complete audio solution with real-time mixing, 3D spatialization, interactive music systems, and advanced DSP effects—all optimized for maximum performance.
                        </p>
                        <div class="pt-4 flex flex-wrap gap-2">
                            <span class="px-3 py-1 bg-orange-500/10 dark:bg-orange-600/10 border-2 border-orange-500/30 dark:border-orange-600/30 rounded-lg text-sm text-orange-600 dark:text-orange-400 font-semibold shadow-sm">AAA Quality</span>
                            <span class="px-3 py-1 bg-orange-500/10 dark:bg-orange-600/10 border-2 border-orange-500/30 dark:border-orange-600/30 rounded-lg text-sm text-orange-600 dark:text-orange-400 font-semibold shadow-sm">Industry Standard</span>
                            <span class="px-3 py-1 bg-orange-500/10 dark:bg-orange-600/10 border-2 border-orange-500/30 dark:border-orange-600/30 rounded-lg text-sm text-orange-600 dark:text-orange-400 font-semibold shadow-sm">Battle-Tested</span>
                        </div>
                    </div>
                </div>

                <!-- Key Features -->
                <div class="p-8 lg:p-10 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm rounded-lg border-2 border-violet-500/30 dark:border-violet-600/40 shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-[1.02]">
                    <div class="flex items-center gap-4 mb-6">
                        <div class="p-3 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg shadow-lg">
                            <UIcon name="i-heroicons-star" class="size-8 text-violet-600 dark:text-violet-500" />
                        </div>
                        <h3 class="text-2xl lg:text-3xl font-bold text-gray-900 dark:text-gray-50">
                            Core Features
                        </h3>
                    </div>
                    <div class="space-y-4">
                        <div class="flex items-start gap-3">
                            <div class="mt-1 p-1.5 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg">
                                <UIcon name="i-heroicons-check" class="size-5 text-violet-600 dark:text-violet-500" />
                            </div>
                            <div>
                                <h4 class="text-gray-900 dark:text-gray-50 font-bold">3D Spatial Audio</h4>
                                <p class="text-gray-600 dark:text-gray-400 text-sm">Accurate distance attenuation, doppler effect, and environmental reverb</p>
                            </div>
                        </div>
                        <div class="flex items-start gap-3">
                            <div class="mt-1 p-1.5 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg">
                                <UIcon name="i-heroicons-check" class="size-5 text-violet-600 dark:text-violet-500" />
                            </div>
                            <div>
                                <h4 class="text-gray-900 dark:text-gray-50 font-bold">Real-Time DSP Effects</h4>
                                <p class="text-gray-600 dark:text-gray-400 text-sm">Reverb, delay, compression, EQ, and 50+ professional effects</p>
                            </div>
                        </div>
                        <div class="flex items-start gap-3">
                            <div class="mt-1 p-1.5 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg">
                                <UIcon name="i-heroicons-check" class="size-5 text-violet-600 dark:text-violet-500" />
                            </div>
                            <div>
                                <h4 class="text-gray-900 dark:text-gray-50 font-bold">Adaptive Audio</h4>
                                <p class="text-gray-600 dark:text-gray-400 text-sm">Dynamic music and parameter-driven sound design</p>
                            </div>
                        </div>
                        <div class="flex items-start gap-3">
                            <div class="mt-1 p-1.5 bg-violet-500/10 dark:bg-violet-600/10 rounded-lg">
                                <UIcon name="i-heroicons-check" class="size-5 text-violet-600 dark:text-violet-500" />
                            </div>
                            <div>
                                <h4 class="text-gray-900 dark:text-gray-50 font-bold">Low Latency</h4>
                                <p class="text-gray-600 dark:text-gray-400 text-sm">Optimized for real-time performance with minimal overhead</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- FMOD vs OpenAL Comparison -->
            <div class="p-8 lg:p-10 bg-white/80 dark:bg-gray-900/80 backdrop-blur-sm rounded-lg border-2 border-cyan-500/30 dark:border-cyan-600/40 shadow-lg hover:shadow-xl transition-all duration-300">
                <div class="text-center mb-8">
                    <div class="inline-flex items-center gap-4 mb-4">
                        <div class="p-3 bg-cyan-500/10 dark:bg-cyan-600/10 rounded-lg shadow-lg">
                            <UIcon name="i-heroicons-chart-bar" class="size-8 text-cyan-600 dark:text-cyan-500" />
                        </div>
                        <h3 class="text-2xl lg:text-3xl font-bold text-gray-900 dark:text-gray-50">
                            FMOD vs OpenAL
                        </h3>
                    </div>
                    <p class="text-gray-700 dark:text-gray-300">Why upgrade to professional audio middleware?</p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <!-- Advanced DSP -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-adjustments-horizontal" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Advanced DSP</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">FMOD includes 50+ professional effects out of the box</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Basic effects only</p>
                    </div>

                    <!-- Better 3D Audio -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-cube" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Superior 3D Audio</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">Advanced spatialization with realistic occlusion and reflection</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Basic positioning</p>
                    </div>

                    <!-- Studio Integration -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-window" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Visual Editor</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">FMOD Studio provides a complete authoring environment</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Code-only workflow</p>
                    </div>

                    <!-- Performance -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-bolt" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Optimized Performance</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">Highly optimized for modern hardware with efficient CPU usage</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Limited optimization</p>
                    </div>

                    <!-- Interactive Music -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-musical-note" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Adaptive Music</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">Built-in systems for dynamic, parameter-driven music</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Manual implementation required</p>
                    </div>

                    <!-- Industry Support -->
                    <div class="p-6 bg-gray-50 dark:bg-gray-800/50 rounded-lg border-2 border-emerald-500/20 dark:border-emerald-600/30 hover:border-emerald-500/40 dark:hover:border-emerald-600/50 transition-all shadow-sm hover:shadow-md">
                        <div class="flex items-center gap-3 mb-3">
                            <div class="p-2 bg-emerald-500/10 dark:bg-emerald-600/10 rounded-lg">
                                <UIcon name="i-heroicons-shield-check" class="size-6 text-emerald-600 dark:text-emerald-500" />
                            </div>
                            <h4 class="text-gray-900 dark:text-gray-50 font-bold">Professional Support</h4>
                        </div>
                        <p class="text-gray-700 dark:text-gray-300 text-sm mb-2">Active development and extensive documentation</p>
                        <p class="text-xs text-orange-600 dark:text-orange-400 font-semibold">OpenAL: Community support</p>
                    </div>
                </div>

                <!-- Bottom note -->
                <div class="mt-8 p-4 lg:p-6 bg-cyan-500/10 dark:bg-cyan-600/10 border-2 border-cyan-500/30 dark:border-cyan-600/40 rounded-lg shadow-sm">
                    <p class="text-center text-gray-700 dark:text-gray-300">
                        <span class="text-cyan-600 dark:text-cyan-400 font-bold">Best of both worlds:</span> Our implementation includes automatic OpenAL fallback for maximum compatibility
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>