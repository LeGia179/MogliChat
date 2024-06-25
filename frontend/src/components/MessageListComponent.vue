<template>
  <div class="grid-container01">
    <div class="nachrichtenUi">
      <div v-for="(userMessage, index) in messages" :key="index" :class="['chat', getMessageSide(userMessage.userName)]">
        <div class="message-content">
          {{ userMessage.userName }}: {{ userMessage.message }}
        </div>
        <div class="timestamp">
          {{ formatTimestamp(userMessage.timestamp) }}
        </div>
      </div>
    </div>
  </div>
  <div class="grid-container02">
    <div class="userInput">
      <input type="text" v-model="newMessage.message" placeholder="Message" class="inputField" @keyup.enter="addNewMessage">
      <button @click="addNewMessage" class="sendButton">
        <img src="../assets/send.png" class="sendIcon" alt="Send">
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { watch,defineProps,onMounted, ref, onUnmounted } from 'vue';
import type { Message } from '@/model/message';

//Props definieren
const props =defineProps({
  messages: {
    type: Array as () => Message[],
    required: true,
  }
});

// beobachtet Props und aktualisiert die lokale Variable wenn sie sich ändert
let messages = ref<Message[]>(props.messages);
watch(() => props.messages, (newMessages) => {
  messages.value = newMessages;
});

const newMessage = ref<Message>({ userName: '', message: '', timestamp: new Date().toISOString() });

const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

let ws: WebSocket | null = null;

onMounted(() => {
  initWebSocket();
});

onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});

function initWebSocket() {
  ws = new WebSocket('ws://localhost:3000/ws');

  ws.onopen = () => {
    console.log('WebSocket connection established');
  };

  ws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    console.log('Message received from server:', message); // Debugging
    messages.value.push(message);
  };

  ws.onclose = () => {
    console.log('WebSocket connection closed');
  };

  ws.onerror = (error) => {
    console.error('WebSocket error:', error);
  };
}

function addNewMessage() {
  if (!currentUser || !currentUser.username) {
    console.error('No user logged in');
    return;
  }
  const messageToSend = { userName: currentUser.username, message: newMessage.value.message, timestamp: new Date().toISOString() };
  if (ws && ws.readyState === WebSocket.OPEN) {
    console.log('Sending message to server:', messageToSend);
    ws.send(JSON.stringify(messageToSend));
    newMessage.value.message = '';
  } else {
    console.error('WebSocket is not open');
  }
}

function formatTimestamp(timestamp: string): string {
  const date = new Date(timestamp);
  return date.toLocaleString();
}

function getMessageSide(userName: string): string {
  return currentUser.username === userName ? 'user-message' : 'other-message';
}
</script>

<style scoped>
.grid-container01 {
  display: flex;
  grid-column-start: 3;
  grid-column-end: 6;
  grid-row-start: 2;
  grid-row-end: 3;
  background-color: #4a536b;
  align-items: center;
  justify-content: center;
}

.nachrichtenUi {
  height: 75vh;
  overflow-y: auto;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  border: 1px solid #83deb0;
  padding: 10px;
}

.chat {
  display: flex;
  flex-direction: column;
  word-wrap: break-word;
  padding: 10px;
  border-radius: 10px;
  margin: 10px 0;
  max-width: 70%;
}

.chat.user-message {
  background-color: greenyellow;
  text-align: right;
  margin-left: auto;
}

.chat.other-message {
  background-color: red;
  text-align: left;
  margin-right: auto;
}

.message-content {
  padding: 10px;
}

.timestamp {
  font-size: 0.8em;
  color: gray;
  text-align: right;
}

.grid-container02 {
  display: flex;
  grid-column-start: 3;
  grid-column-end: 6;
  grid-row-start: 3;
  grid-row-end: 4;
  background-color: #4a536b;
  align-items: center;
  justify-content: center;
}

.userInput {
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  margin-right: 5px;
  border: 1px solid #83deb0;
}

.inputField {
  flex-grow: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #83deb0;
  margin-right: 5px;
}

input[type="text"] {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #83deb0;
}

.sendIcon {
  height: 3vh;
}

.sendButton {
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin-top: auto;
}

.chat {
  padding: 20px;
  color: #83deb0;
}

.timestamp {
  font-size: 0.8em;
  color: gray;
}
</style>
