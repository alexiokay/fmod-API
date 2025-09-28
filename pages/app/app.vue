<template>
  <UApp>
      
    <NuxtRouteAnnouncer />

    <UHeader>
      <template #logo>
        <NuxtLink to="/" class="flex items-center gap-2 text-xl font-bold">
          🎵 FMOD API
        </NuxtLink>
      </template>

      <template #right>
        <UButton
          to="https://modrinth.com/mod/fmod-api"
          target="_blank"
          color="primary"
          variant="solid"
          icon="download"
          label="Download"
        />
      </template>
    </UHeader>

    <UMain>
      <!-- Check if we're on homepage to render differently -->
      <div v-if="$route.path === '/'">
        <NuxtPage />
      </div>

      <!-- Use sidebar layout for documentation pages -->
      <UContainer v-else>
        <UPage>
          <template #left>
            <UAside>
              <UNavigationTree
                :links="[
                  {
                    label: 'Getting Started',
                    icon: 'rocket',
                    to: '/docs/getting-started'
                  },
                  {
                    label: 'Guides',
                    icon: 'book-open',
                    children: [
                      {
                        label: 'Native Integration',
                        icon: 'game',
                        to: '/docs/integration'
                      },
                      {
                        label: 'Configuration',
                        icon: 'cog',
                        to: '/docs/configuration'
                      },
                      {
                        label: 'Advanced Guide',
                        icon: 'cpu',
                        to: '/docs/advanced'
                      }
                    ]
                  },
                  {
                    label: 'API Reference',
                    icon: 'code',
                    to: '/api/reference'
                  },
                  {
                    label: 'External Links',
                    icon: 'external-link',
                    children: [
                      {
                        label: 'Download (Modrinth)',
                        icon: 'download',
                        to: 'https://modrinth.com/mod/fmod-api',
                        target: '_blank'
                      },
                      {
                        label: 'FMOD Engine',
                        icon: 'volume',
                        to: 'https://www.fmod.com/download#fmodengine',
                        target: '_blank'
                      },
                      {
                        label: 'GitHub Repository',
                        icon: 'github',
                        to: 'https://github.com/alexiokay/fmod-API',
                        target: '_blank'
                      }
                    ]
                  }
                ]"
              />
            </UAside>
          </template>

          <NuxtPage />
        </UPage>
      </UContainer>
    </UMain>

    <UFooter>
      <template #left>
        <p class="text-sm text-gray-500">
          FMOD API for Minecraft &copy; 2024 alexiokay
        </p>
      </template>

      <template #right>
        <p class="text-sm text-gray-500">
          Audio powered by FMOD Studio by Firelight Technologies Pty Ltd
        </p>
      </template>
    </UFooter>
  </UApp>
</template>

<script setup>
// Configure SEO
useSeoMeta({
  title: 'FMOD API for Minecraft',
  description: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogTitle: 'FMOD API for Minecraft',
  ogDescription: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogType: 'website',
  twitterCard: 'summary_large_image'
})

// Configure app head
useHead({
  titleTemplate: '%s | FMOD API',
  link: [
    { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
  ]
})
</script>
