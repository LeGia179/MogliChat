<!-- Funktion: import notwendige Module + CSS-Style -->
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import "normalize.css"
</script>

<!-- Struktur Form -->
<template>
  <div class="app-container">
    <RouterView />
  </div>
</template>

<!-- Styling Form -->
<style scoped>
</style>