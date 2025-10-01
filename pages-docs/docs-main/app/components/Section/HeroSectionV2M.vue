<template>
    <div class="w-full h-[30rem] md:h-[35rem] 2xl:h-[44rem] flex items-center justify-center relative overflow-hidden bg-gray-50 dark:bg-gray-950">
        <!-- Background split with Minecraft image and dirt texture -->
        <div class="w-full flex absolute left-0 top-0 z-10 h-full">
            <NuxtImg
              src="https://i.redd.it/t8kjfl2b8r1b1.png"
              class="w-[100vw] lg:w-[70vw] h-full object-cover transition-all duration-300 brightness-80 contrast-115 saturate-95 grayscale-[0.08] dark:brightness-60 dark:saturate-85 dark:grayscale-[0.15]"
              style="filter: contrast(1.15);"
              alt="Minecraft landscape"
            />

            <div
              class="lg:w-[30vw] h-full transition-opacity duration-300 opacity-85 dark:opacity-70"
              style="background-image: url(/textures/dirt.png); background-size: 80px 80px; background-repeat: repeat; mix-blend-mode: multiply; image-rendering: pixelated;"
            />
        </div>

        <!-- Overlay gradient for better text readability -->
        <div class="absolute inset-0 z-10 transition-all duration-300 bg-gradient-to-r from-black/40 via-black/20 to-transparent dark:from-black/60 dark:via-black/40 dark:to-black/20" />

        <!-- Content -->
        <div class="flex flex-col lg:flex-row max-w-[115rem] px-4 lg:px-8  gap-x-[10vw] lg:gap-x-[15vw] w-full z-20 items-center justify-center">
            <!-- Left content -->
            <div class="flex flex-col w-full lg:w-3/4 my-auto">
                <h1 class="text-4xl lg:text-5xl xl:text-6xl font-bold leading-tight drop-shadow-lg text-white dark:text-gray-50">
                    FMOD <span class="text-orange-400 dark:text-orange-500">Audio</span> Integration for Minecraft
                </h1>
                <p class="mt-6 lg:mt-8 text-base lg:text-lg drop-shadow-md max-w-2xl text-gray-100 dark:text-gray-200">
                    Bring professional 3D audio to your Minecraft projects with seamless FMOD integration.
                    Advanced sound design made simple.
                </p>
                <div class="flex flex-col sm:flex-row w-full sm:w-3/4 gap-4 lg:gap-x-8 mt-6 lg:mt-8">
                    <NuxtLink
                      to="https://modrinth.com/mod/fmod-api"
                      target="_blank"
                      class="bg-orange-500 hover:bg-orange-600 dark:bg-orange-600 dark:hover:bg-orange-700 transition-colors h-[3rem] rounded-md w-full hover:cursor-pointer flex items-center justify-center gap-x-2 font-semibold shadow-lg hover:shadow-xl text-white"
                    >
                        Download
                        <UIcon name="i-heroicons-arrow-down-tray-20-solid" class="size-5" />
                    </NuxtLink>

                    <NuxtLink
                      to="/getting-started"
                      class="border-2 w-full h-[3rem] rounded-md transition-colors font-semibold flex items-center justify-center shadow-lg border-white dark:border-gray-300 bg-white/10 dark:bg-white/5 hover:bg-white/20 dark:hover:bg-white/10 backdrop-blur-sm text-white"
                    >
                        Documentation
                    </NuxtLink>
                </div>
            </div>

            <!-- Right content with image and social buttons -->
            <div class="hidden lg:flex w-1/4 h-full justify-end items-center relative gap-x-6">
                <NuxtImg
                  class="h-[80%] aspect-[20/21] object-cover rounded-lg shadow-2xl border-4 border-orange-500/30 dark:border-orange-600/40"
                  src="/images/steve_guitar.png"
                  alt="Minecraft portal"
                />
                <div class="flex flex-col items-center justify-center gap-y-8">
                    <NuxtLink
                      to="https://github.com/alexiokay/fmod-api"
                      target="_blank"
                      class="rounded-lg h-[2.5rem] bg-orange-500 hover:bg-orange-600 dark:bg-orange-600 dark:hover:bg-orange-700 transition-all aspect-square flex items-center justify-center shadow-lg hover:shadow-xl hover:scale-110 text-white"
                      aria-label="GitHub"
                    >
                      <UIcon name="i-simple-icons-github" class="size-6" />
                    </NuxtLink>

                    <NuxtLink
                      to="https://discord.gg/TUFXmBWYh2"
                      target="_blank"
                      class="rounded-lg h-[2.5rem] bg-orange-500 hover:bg-orange-600 dark:bg-orange-600 dark:hover:bg-orange-700 transition-all aspect-square flex items-center justify-center shadow-lg hover:shadow-xl hover:scale-110 text-white"
                      aria-label="Discord"
                    >
                      <UIcon name="i-simple-icons-discord" class="size-6" />
                    </NuxtLink>

                    <!-- <NuxtLink
                      to="https://youtube.com/@yourchannel"
                      target="_blank"
                      class="rounded-lg h-[2.5rem] bg-orange-500 hover:bg-orange-600 dark:bg-orange-600 dark:hover:bg-orange-700 transition-all aspect-square flex items-center justify-center shadow-lg hover:shadow-xl hover:scale-110 text-white"
                      aria-label="YouTube"
                    >
                      <UIcon name="i-simple-icons-youtube" class="size-6" />
                    </NuxtLink> -->
                </div>
            </div>
        </div>
    </div>
</template>

