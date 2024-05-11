<template>
  <div>
    <div v-for="userMessage in messagePar" :key="userMessage.userName">
      <div>{{ userMessage.userName }}: {{ userMessage.message }}</div>
    </div>

    <div>
      <label>User Name</label>
      <input type="text" v-model="newMessage.userName">
      <label>User Message</label>
      <input type="text" v-model="newMessage.message">

      <button @click="addNewMessage">Senden</button>
    </div>
  </div>
  <Support/>
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

