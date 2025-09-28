<template>
  <div>
    <HeroSection />
    <KeyFeaturesSection />
    <QuickStartSection />
    <DocumentationSection />
    <CallToActionSection />
  </div>
</template>

<script setup>
// Configure SEO for homepage
useSeoMeta({
  title: 'FMOD API for Minecraft',
  description: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogTitle: 'FMOD API for Minecraft',
  ogDescription: 'Professional 3D spatial audio engine integration for Minecraft NeoForge mods with seamless native integration.',
  ogType: 'website',
  twitterCard: 'summary_large_image'
})
</script>