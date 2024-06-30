<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

async function goBack() {
  console.log('Navigating back to ChatUI...');
  try {
    // Beispiel: Zur vorherigen Ansicht zurückkehren
    await router.back();

    // Beispiel: Zu einer bestimmten Route navigieren
    // await router.push({ name: 'ChatUI', params: { userId: 'someUserId' } });
  } catch (error) {
    console.error('Error navigating back:', error);
  }
}



</script>


<template>
  <div class="container">
    <img src="../assets/fish.png" class="image" />
  </div>
  <div class="icon-bar-box">
    <button class="goBackButton" @click="goBack">
      <img src="../assets/leave.png" class="logout-icon" />
    </button>
  </div>
</template>


<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.image {
  width: 80%;
  justify-content: center;
  align-items: center;
}

.goBackButton {
  background-color: transparent;
  border: none;
  margin-right: 100px;
}
</style>