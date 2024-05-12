<script setup lang="ts">
import MessengerListComponent from '@/components/MessageListComponent.vue';
import {useRouter} from 'vue-router'

const router = useRouter();
function navigateSite() {
  router.push('/home');
}
</script>

<template>
<div class="seitenleiste">
  <button class="settingsButton" @click="navigateSite">User Settings</button>
</div>

  <main>
    <MessengerListComponent :messagePar="[]" />
  </main>

</template>

<style scoped>
  .seitenleiste {
    display: flex;
    position: fixed;
      left: 0;
      top: 0;
      bottom: 0;
    background-color: greenyellow;
    padding: 10px;
    z-index: 1;
    min-width: 8vh;
    width: 8vh;
  }
  /* Profil */
  .settingsButton {
    position: absolute;
      bottom: 0;
      left: 0;
    padding: 10px;
    border: 1px solid black;
    color: black;
    width: 100%;
    height: 6vh;
  }

</style>