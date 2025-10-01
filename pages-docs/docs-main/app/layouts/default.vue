<template>
  <div>
    <!-- Fixed header that overlays content -->
    <AppHeader />

    <!-- Main content with no top padding since header is fixed -->
    <UMain class="pt-0 font-[Poppins]">
      <slot />
    </UMain>

    <AppFooter />
  </div>
</template>

<style >

</style>