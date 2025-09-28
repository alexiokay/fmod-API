// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  modules: [
    '@nuxt/content',
    '@nuxt/eslint',
    '@nuxt/image',
    '@nuxt/scripts',
    '@nuxt/ui',
    '@nuxt/test-utils'
  ],

  css: ['~/assets/css/main.css'],

  // GitHub Pages configuration
  app: {
    baseURL: process.env.NUXT_APP_BASE_URL || '/',
  },

 

  // Nuxt Content configuration
  content: {
    highlight: {
      theme: 'github-dark'
    }
  },

  // Nuxt UI configuration
  ui: {
    global: true,
    icons: ['heroicons']
  },

  // Make Nuxt UI components global for use in markdown
  hooks: {
    'components:extend': (components) => {
      const globals = components.filter(c =>
        ['UButton', 'UCard', 'UIcon', 'UAlert', 'UBadge', 'UModal', 'UInput'].includes(c.pascalName)
      )
      globals.forEach(c => c.global = true)
    }
  },

  // Static site generation for GitHub Pages
  nitro: {
    prerender: {
      routes: ['/']
    }
  },

  // Ensure proper static generation
  ssr: false
})