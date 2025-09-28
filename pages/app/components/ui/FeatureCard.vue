<template>
  <UCard :class="cardClasses">
    <div :class="overlayClasses"></div>
    <component :is="linkComponent" :to="to" :target="target" :class="linkClasses">
      <div :class="contentClasses">
        <div :class="iconContainerClasses">
          <UIcon :name="icon" :class="iconClasses" />
        </div>
        <div>
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

// Color theme mappings
const colorThemes = {
  purple: {
    border: 'border-purple-100 hover:border-purple-300',
    overlay: 'from-purple-500 to-indigo-600',
    icon: 'from-purple-500 to-indigo-600',
    iconShadow: 'group-hover:shadow-purple-500/25',
    titleHover: 'group-hover:text-purple-600'
  },
  emerald: {
    border: 'border-emerald-100 hover:border-emerald-300',
    overlay: 'from-emerald-500 to-green-600',
    icon: 'from-emerald-500 to-green-600',
    iconShadow: 'group-hover:shadow-emerald-500/25',
    titleHover: 'group-hover:text-emerald-600'
  },
  sky: {
    border: 'border-sky-100 hover:border-sky-300',
    overlay: 'from-sky-500 to-blue-600',
    icon: 'from-sky-500 to-blue-600',
    iconShadow: 'group-hover:shadow-sky-500/25',
    titleHover: 'group-hover:text-sky-600'
  },
  amber: {
    border: 'border-amber-100 hover:border-amber-300',
    overlay: 'from-amber-500 to-orange-600',
    icon: 'from-amber-500 to-orange-600',
    iconShadow: 'group-hover:shadow-amber-500/25',
    titleHover: 'group-hover:text-amber-600'
  },
  pink: {
    border: 'border-pink-100 hover:border-pink-300',
    overlay: 'from-pink-500 to-rose-600',
    icon: 'from-pink-500 to-rose-600',
    iconShadow: 'group-hover:shadow-pink-500/25',
    titleHover: 'group-hover:text-pink-600'
  },
  violet: {
    border: 'border-violet-100 hover:border-violet-300',
    overlay: 'from-violet-500 to-purple-600',
    icon: 'from-violet-500 to-purple-600',
    iconShadow: 'group-hover:shadow-violet-500/25',
    titleHover: 'group-hover:text-violet-600'
  },
  indigo: {
    border: 'border-indigo-100 hover:border-indigo-300',
    overlay: 'from-indigo-500 to-blue-600',
    icon: 'from-indigo-500 to-blue-600',
    iconShadow: 'group-hover:shadow-indigo-500/25',
    titleHover: 'group-hover:text-indigo-600'
  },
  blue: {
    border: 'border-blue-100 hover:border-blue-300',
    overlay: 'from-blue-500 to-cyan-600',
    icon: 'from-blue-500 to-cyan-600',
    iconShadow: 'group-hover:shadow-blue-500/25',
    titleHover: 'group-hover:text-blue-600'
  }
}

const theme = colorThemes[props.colorTheme]

// Computed classes
const cardClasses = computed(() => [
  'h-full group relative bg-white/90 backdrop-blur-sm hover:shadow-2xl transition-all duration-500 transform hover:-translate-y-3 shadow-xl',
  theme.border
])

const overlayClasses = computed(() => [
  'absolute inset-0 bg-gradient-to-br opacity-0 group-hover:opacity-5 transition-opacity duration-300 rounded-lg',
  theme.overlay
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
  'bg-gradient-to-br rounded-xl shadow-lg group-hover:scale-110 transition-all duration-300',
  theme.icon,
  theme.iconShadow,
  props.variant === 'vertical'
    ? 'inline-flex items-center justify-center p-4 mb-6'
    : 'aspect-square h-full p-4'
])

const iconClasses = computed(() => [
  'text-white',
  props.variant === 'vertical' ? 'w-8 h-8' : 'w-7 h-7'
])

const titleClasses = computed(() => [
  'text-2xl font-bold text-gray-900 mb-3 transition-colors duration-300',
  theme.titleHover
])

const descriptionClasses = 'text-gray-700 leading-relaxed'
</script>