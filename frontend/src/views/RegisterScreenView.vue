<template>
  <div class="RegisterScreenUI">
    <RegisterComponent />
  </div>
</template>

<script setup lang="ts">
import RegisterComponent from "@/components/RegisterComponent.vue";
</script>

<style scoped>
.RegisterScreenUI {
  box-sizing: border-box;
  display: grid;
  grid-template-columns: [col-start1] 2fr [col-end1] 1.5fr [col-start2]; /* Zwei Spalten: links kleiner, rechts größer */
  height: 100vh; /* Volle Höhe des Ansichtsfensters */
}

</style>