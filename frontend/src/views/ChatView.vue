<script setup lang="ts">
import MessengerListComponent from '@/components/MessageListComponent.vue';
import {useRouter} from 'vue-router'
import SeitenleisteComponent from "@/components/SeitenleisteComponent.vue";
import KontaktListComponent from "@/components/KontaktListComponent.vue";
import IrgendeineLeisteZumTesten from "@/components/IrgendeineLeisteZumTesten.vue";
import SupportComponent from '@/components/SupportComponent.vue'

const router = useRouter();
function navigateSite() {
  router.push('/');
}
</script>

<template>
  <div class="ui">
    <SeitenleisteComponent></SeitenleisteComponent>
    <KontaktListComponent></KontaktListComponent>
    <MessengerListComponent message-par=Message[]></MessengerListComponent>
    <irgendeine-leiste-zum-testen></irgendeine-leiste-zum-testen>
  </div>


</template>


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