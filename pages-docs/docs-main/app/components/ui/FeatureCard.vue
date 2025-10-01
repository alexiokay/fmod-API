<template>
  <UCard :ui="{ base: cardClasses, body: { padding: '' } }">
    <div :class="overlayClasses"></div>
    <component :is="linkComponent" :to="to" :target="target" :class="linkClasses">
      <div :class="contentClasses">
        <div :class="iconContainerClasses">
          <UIcon :name="icon" :class="iconClasses" size="2rem" />
        </div>
        <div :class="textContainerClasses">
          <h3 :class="titleClasses">{{ title }}</h3>
          <p :class="descriptionClasses">{{ description }}</p>
        </div>
      </div>
    </component>
  </UCard>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    required: true
  },
  to: {
    type: String,
    default: null
  },
  target: {
    type: String,
    default: null
  },
  colorTheme: {
    type: String,
    default: 'purple',
    validator: (value) => ['purple', 'emerald', 'sky', 'amber', 'pink', 'violet', 'indigo', 'blue'].includes(value)
  },
  variant: {
    type: String,
    default: 'horizontal',
    validator: (value) => ['horizontal', 'vertical'].includes(value)
  }
})

// Minecraft-themed color mappings
const themeStyles = {
  // Grass/Nature theme
  emerald: {
    card: 'from-green-50 via-emerald-50/50 to-lime-50 dark:from-emerald-950/30 dark:via-green-900/20 dark:to-emerald-950/30',
    border: 'border-2 border-emerald-600/30 dark:border-emerald-500/40 hover:border-emerald-500 dark:hover:border-emerald-400',
    icon: 'from-emerald-500 to-green-600 dark:from-emerald-400 dark:to-green-500',
    iconBorder: 'ring-2 ring-emerald-100 dark:ring-emerald-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-emerald-700/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-emerald-700 dark:group-hover:text-emerald-400'
  },
  // Stone/Iron theme
  purple: {
    card: 'from-stone-50 via-gray-50/50 to-slate-50 dark:from-stone-900/30 dark:via-gray-900/20 dark:to-stone-950/30',
    border: 'border-2 border-stone-400/30 dark:border-stone-600/40 hover:border-stone-500 dark:hover:border-stone-500',
    icon: 'from-stone-600 to-gray-700 dark:from-stone-500 dark:to-gray-600',
    iconBorder: 'ring-2 ring-stone-100 dark:ring-stone-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-stone-800/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-stone-700 dark:group-hover:text-stone-400'
  },
  // Diamond/Water theme
  sky: {
    card: 'from-cyan-50 via-sky-50/50 to-blue-50 dark:from-cyan-950/30 dark:via-sky-900/20 dark:to-blue-950/30',
    border: 'border-2 border-cyan-500/30 dark:border-cyan-600/40 hover:border-cyan-500 dark:hover:border-cyan-400',
    icon: 'from-cyan-500 to-sky-600 dark:from-cyan-400 dark:to-sky-500',
    iconBorder: 'ring-2 ring-cyan-100 dark:ring-cyan-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-cyan-700/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-cyan-700 dark:group-hover:text-cyan-400'
  },
  // Gold/Glowstone theme
  amber: {
    card: 'from-amber-50 via-yellow-50/50 to-orange-50 dark:from-amber-950/30 dark:via-yellow-900/20 dark:to-orange-950/30',
    border: 'border-2 border-amber-500/30 dark:border-amber-600/40 hover:border-amber-500 dark:hover:border-amber-400',
    icon: 'from-amber-500 to-yellow-600 dark:from-amber-400 dark:to-yellow-500',
    iconBorder: 'ring-2 ring-amber-100 dark:ring-amber-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-amber-700/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-amber-700 dark:group-hover:text-amber-400'
  },
  // Redstone theme
  pink: {
    card: 'from-red-50 via-rose-50/50 to-pink-50 dark:from-red-950/30 dark:via-rose-900/20 dark:to-red-950/30',
    border: 'border-2 border-red-500/30 dark:border-red-600/40 hover:border-red-500 dark:hover:border-red-400',
    icon: 'from-red-500 to-rose-600 dark:from-red-400 dark:to-rose-500',
    iconBorder: 'ring-2 ring-red-100 dark:ring-red-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-red-700/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-red-700 dark:group-hover:text-red-400'
  },
  // Ender/End theme
  violet: {
    card: 'from-purple-50 via-violet-50/50 to-indigo-50 dark:from-purple-950/30 dark:via-violet-900/20 dark:to-purple-950/30',
    border: 'border-2 border-purple-600/30 dark:border-purple-500/40 hover:border-purple-500 dark:hover:border-purple-400',
    icon: 'from-purple-600 to-violet-700 dark:from-purple-500 dark:to-violet-600',
    iconBorder: 'ring-2 ring-purple-100 dark:ring-purple-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-purple-800/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-purple-700 dark:group-hover:text-purple-400'
  },
  // Lapis/Enchantment theme
  indigo: {
    card: 'from-blue-50 via-indigo-50/50 to-blue-100 dark:from-indigo-950/30 dark:via-blue-900/20 dark:to-indigo-950/30',
    border: 'border-2 border-indigo-600/30 dark:border-indigo-500/40 hover:border-indigo-500 dark:hover:border-indigo-400',
    icon: 'from-indigo-600 to-blue-700 dark:from-indigo-500 dark:to-blue-600',
    iconBorder: 'ring-2 ring-indigo-100 dark:ring-indigo-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-indigo-800/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-indigo-700 dark:group-hover:text-indigo-400'
  },
  // Ocean theme
  blue: {
    card: 'from-blue-50 via-cyan-50/50 to-teal-50 dark:from-blue-950/30 dark:via-cyan-900/20 dark:to-blue-950/30',
    border: 'border-2 border-blue-500/30 dark:border-blue-600/40 hover:border-blue-500 dark:hover:border-blue-400',
    icon: 'from-blue-500 to-cyan-600 dark:from-blue-400 dark:to-cyan-500',
    iconBorder: 'ring-2 ring-blue-100 dark:ring-blue-900/50',
    iconShadow: 'shadow-[0_4px_0_0] shadow-blue-700/50 group-hover:shadow-[0_2px_0_0]',
    titleHover: 'group-hover:text-blue-700 dark:group-hover:text-blue-400'
  }
}

const theme = computed(() => themeStyles[props.colorTheme] || themeStyles.purple)

// Computed classes - Minecraft style
const cardClasses = computed(() => [
  'h-full group relative bg-gradient-to-br backdrop-blur-sm transition-all duration-100 transform hover:translate-y-[-4px] shadow-[0_4px_0_0] shadow-black/20 hover:shadow-[0_8px_0_0] hover:shadow-black/30 rounded-sm dark:bg-stone-900',
  theme.value.card,
  theme.value.border
])

const overlayClasses = computed(() => [
  'absolute inset-0 bg-white/10 dark:bg-gradient-to-br dark:from-white/5 dark:to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-100 rounded-sm'
])

const linkComponent = computed(() => props.to ? 'NuxtLink' : 'div')

const linkClasses = computed(() => [
  'relative block',
  props.variant === 'vertical' ? 'p-6' : 'p-4'
])

const contentClasses = computed(() => [
  props.variant === 'vertical' ? 'text-center' : 'flex items-start gap-5'
])

const iconContainerClasses = computed(() => [
  'flex-shrink-0 bg-gradient-to-br rounded-sm group-hover:translate-y-[-2px] transition-all duration-100',
  theme.value.icon,
  theme.value.iconBorder,
  theme.value.iconShadow,
  props.variant === 'vertical'
    ? 'inline-flex items-center justify-center p-4 mb-6 w-20 h-20'
    : 'flex items-center justify-center w-16 h-16 p-3'
])

const iconClasses = computed(() => [
  'text-white'
])

const textContainerClasses = computed(() => [
  props.variant === 'vertical' ? '' : 'flex-1'
])

const titleClasses = computed(() => [
  'text-xl font-bold text-gray-900 dark:text-white mb-2 transition-colors duration-300',
  theme.value.titleHover
])

const descriptionClasses = 'text-gray-700 dark:text-gray-200 leading-relaxed text-sm'
</script>