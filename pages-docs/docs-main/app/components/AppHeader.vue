<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation', ref([]))

const { header } = useAppConfig()
const route = useRoute()

// Scroll detection for transparent -> solid transition
const scrolled = ref(false)

onMounted(() => {
  const handleScroll = () => {
    scrolled.value = window.scrollY > 50
  }

  window.addEventListener('scroll', handleScroll)

  onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll)
  })
})
</script>

<template>
  <UHeader
    :ui="{ center: 'flex-1' }"
    :class="[
      'fixed top-0 left-0 right-0 z-50 transition-all duration-300 font-[Poppins] ',
      scrolled
        ? 'bg-white/95 dark:bg-gray-900/95 backdrop-blur-md shadow-lg'
        : 'bg-transparent backdrop-blur-none border-none text-white'
    ]"
  >
    <template
      v-if="header?.logo?.dark || header?.logo?.light || header?.title"
      #title
    >
      <NuxtLink :to="header?.to || '/'">
        <UColorModeImage
          v-if="header?.logo?.dark || header?.logo?.light"
          :light="header?.logo?.light!"
          :dark="header?.logo?.dark!"
          :alt="header?.logo?.alt"
          class="h-6 w-auto shrink-0"
        />

        <span v-else-if="header?.title">
          {{ header.title }}
        </span>
      </NuxtLink>
    </template>

    <template
      v-else
      #left
    >
      <NuxtLink :to="header?.to || '/'">
        <AppLogo class="w-auto h-6 shrink-0" />
      </NuxtLink>

    </template>

    <template #right>
      <UContentSearchButton
        v-if="header?.search"
        class="lg:hidden "
      />
      
      <UColorModeButton v-if="header?.colorMode"   :class="['bg-transparent shadow-[inset_0_0_0_2px] shadow-orange-500/80 dark:active:bg-orange-600/80  dark:shadow-orange-600/80 hover:bg-orange-500/80  dark:hover:bg-orange-600/80  active:bg-orange-500/80  py-2 px-4', scrolled? '' : 'text-white    ']" />
<!-- 
      <template v-if="header?.links">
        <UButton
          :class="['bg-orange-500 dark:bg-orange-600 py-2 px-4 text-white hover:bg-orange-500/80 active:bg-orange-500/80 dark:active:bg-orange-600/80  dark:hover:bg-orange-600/80 ', scrolled? '' : ' ']"
          v-for="(link, index) of header.links"
          :key="index"
          v-bind="{ color: 'neutral', variant: 'ghost', ...link }"
        />
      </template> -->
    </template>

    <template v-if="navigation?.length" #body>
      <UContentNavigation
        highlight
        :navigation="navigation"
      />
    </template>
  </UHeader>
</template>
