<template>

  <Support/>
  <div class="flex-container2">
    <div class="kontakte">Kontakte</div>

    <!-- User Nachrichten -->

    <div class="chatverlauf">
      <div v-for="userMessage in messagePar" :key="userMessage.userName">
        <div>{{ userMessage.userName }}: {{ userMessage.message }}</div>
      </div>
    </div>

    <!--User Input für den Chat -->
    <div class="grid-item4"><label>User Name</label>
      <input type="text" v-model="newMessage.userName">
      <label>User Message</label>
      <input type="text" v-model="newMessage.message">
      <button @click="addNewMessage">Senden</button>
    </div>

  </div>
</template>





<script setup lang="ts">
import { ref } from 'vue';
import type { Message } from '@/model/message';
import Support from '@/components/SupportComponent.vue'

const props = defineProps({
  messagePar: { type: Array as () => Message[], required: true }
});

const message = ref<Message[]>(props.messagePar);
const newMessage = ref<Message>({ userName: '', message: '' });

function addNewMessage() {
  message.value.push({ userName: newMessage.value.userName, message: newMessage.value.message });
  newMessage.value.userName = '';
  newMessage.value.message = '';
}
</script>

<style scoped>
.flex-container2 {
  display: flex;
  flex-direction: column;
  margin:0 0 0 8vh;
  padding: 0;
  z-index: 2;
}


/* kontakte */
.kontakte {
  position: fixed;
    top: 0;
    left: 0;
    height: 100%;
  border: 1px solid #ccc;
  margin:0 0 0 8vh;
  min-width: 8vh;
  z-index: 2;
  width: 25vh;

}

/* Chatverlauf */
.chatverlauf {
  position: fixed;
    top: 0;
    left: 0;
    height: 100%;
  border: 1px solid #ccc;
  margin:0 0 0 33vh;
  max-height: 100%;
  max-width: 100%;
  height: 93vh;
  width:  100vh;
  z-index: 2;

}

/* Userinput */
.grid-item4 {
  position: absolute;
    bottom: 2vh;
    left:0;
  margin:0 0 0 33vh;
  padding: 0;
  border: 1px solid #ccc;
  max-height: 100%;
  max-width: 100%;
  height: 5vh;
  width:  100vh;
  z-index: 2;

}
</style>
