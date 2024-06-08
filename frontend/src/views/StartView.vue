<script setup lang="ts">
import StartViewComponent from "@/components/StartViewComponent.vue";

</script>

<template>
  <div class="startseiteUI">
    <StartViewComponent />
  </div>
</template>

<style scoped>
.startseiteUI {
  box-sizing: border-box;
  display: grid;
  grid-template-columns: [col-start1] 2fr [col-end1] 1.5fr [col-start2]; /* Zwei Spalten: links kleiner, rechts größer */
  height: 100vh; /* Volle Höhe des Ansichtsfensters */
}

</style>
