export default defineAppConfig({
  ui: {
    colors: {
      primary: 'green',
      neutral: 'slate'
    },
    footer: {
      slots: {
        root: 'border-t border-default',
        left: 'text-sm text-muted'
      }
    }
  },
  seo: {
    siteName: 'Nuxt Docs Template'
  },
  header: {
    title: '',
    to: '/',
    logo: {
      alt: '',
      light: '',
      dark: ''
    },
    search: true,
    colorMode: true,
    links: [{
      'icon': 'i-simple-icons-github',
      'to': 'https://github.com/alexiokay/fmod-API',
      'target': '_blank',
      'aria-label': 'GitHub'
    },
    {
      'icon': 'i-simple-icons-discord',
      'to': 'https://discord.gg/TUFXmBWYh2',
      'target': '_blank',
      'aria-label': 'Discord'
    }]
  },
  footer: {
    credits: `FMOD API for Minecraft © ${new Date().getFullYear()} alexiokay • Audio powered by FMOD Studio by Firelight Technologies Pty Ltd`,
    colorMode: false,
    links: [{
      'icon': 'i-simple-icons-discord',
      'to': 'https://discord.gg/TUFXmBWYh2',
      'target': '_blank',
      'aria-label': 'FMOD API on Discord'
    }, {
      'icon': 'i-heroicons-arrow-down-tray-20-solid',
      'to': 'https://modrinth.com/mod/fmod-api',
      'target': '_blank',
      'aria-label': 'Download on Modrinth'
    }, {
      'icon': 'i-simple-icons-github',
      'to': 'https://github.com/alexiokay/fmod-API',
      'target': '_blank',
      'aria-label': 'FMOD API on GitHub'
    }, {
      'icon': 'i-heroicons-speaker-wave-20-solid',
      'to': 'https://www.fmod.com/download#fmodengine',
      'target': '_blank',
      'aria-label': 'FMOD Engine Download'
    }]
  },
  toc: {
    title: 'Table of Contents',
    bottom: {
      title: 'Links',
      edit: '',
      links: [{
        icon: 'i-lucide-rocket',
        label: 'Getting Started',
        to: 'https://mc.pawelec.net/fmod/getting-started',
        target: '_blank'
      },
      {
        icon: 'i-lucide-book-open',
        label: 'Guides',
        to: 'https://mc.pawelec.net/fmod/guides',
        target: '_blank'
      }, {
        icon: 'i-lucide-zap',
        label: 'Configuration',
        to: 'https://mc.pawelec.net/fmod/configuration',
        target: '_blank'
      },
      {
        icon: 'i-lucide-code',
        label: 'API Reference',
        to: 'https://mc.pawelec.net/fmod/api-reference',
        target: '_blank'
      }]
    }
  }
})
