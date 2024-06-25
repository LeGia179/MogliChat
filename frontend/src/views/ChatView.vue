<!-- Funktion: import, verknüpfen für Navigation -->
<script setup lang="ts">
import MessengerListComponent from '@/components/MessageListComponent.vue';
import {useRouter} from 'vue-router'
import SeitenleisteComponent from "@/components/SeitenleisteComponent.vue"
import KontaktListComponent from "@/components/KontaktListComponent.vue";
import IrgendeineLeisteZumTesten from "@/components/IrgendeineLeisteZumTesten.vue";
import SupportComponent from '@/components/SupportComponent.vue'
import { ref } from 'vue'

// Router-Instanz
const router = useRouter();
// Navigation zur Startseite
function navigateSite() {
  router.push('/');
}
const messages = ref([]);
</script>

<!-- Struktur UI -->
<template>
  <div class="ui">
    <SeitenleisteComponent></SeitenleisteComponent>
    <KontaktListComponent></KontaktListComponent>
    <MessengerListComponent :messages="messages"></MessengerListComponent>
    <irgendeine-leiste-zum-testen></irgendeine-leiste-zum-testen>
  </div>
</template>

<!-- Styling UI -->
<style scoped>
.ui {
  display: grid;
  grid-template-columns: [col-start1] 100px [col-end1] 400px [col-start2] 3fr [col-end2];
  grid-template-rows:[row-start1] 100px [row-end1] 1fr [row-start2] 100px [row-end2];
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}
</style>