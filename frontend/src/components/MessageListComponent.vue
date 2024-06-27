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
import { ref, watch, onMounted, onUnmounted } from 'vue';
import type { Message } from '@/model/message';
import axios from "axios";

const props = defineProps({
  messages: {
    type: Array as () => Message[],
    required: true,
  }
});

const messages = ref<Message[]>(props.messages);

watch(() => props.messages, (newMessages) => {
  messages.value = [...newMessages];
});

const newMessage = ref<Message>({ userName: '', message: '', timestamp: new Date().toISOString() });

const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

let ws: WebSocket | null = null;

// Basis-URL für das Backend
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
const endpoint = baseUrl + "/message";

onMounted(() => {
  // Nachrichten vom Server laden
  axios.get(endpoint).then((response) => {
    console.log('Fetched messages from server:', response.data);
    messages.value = response.data;
  }).catch((error) => {
    console.error('Error fetching messages', error);
  });

  // Initialisieren der WebSocket-Verbindung
  initWebSocket();
});

onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});

function initWebSocket() {
  const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
  const wsUrl = `wss://moglichatbackend-cbuw.onrender.com/ws?username=${currentUser.username}`;

  ws = new WebSocket(wsUrl);

  ws.onopen = () => {
    console.log('WebSocket connection established');
  };

  ws.onmessage = (event) => {
    const reader = new FileReader();
    reader.onload = function() {
      const message = JSON.parse(reader.result as string);
      console.log('Message received from server:', message);
      messages.value.push(message);
      console.log('Updated messages:', messages.value);
    };
    reader.readAsText(event.data);
  };

  ws.onclose = (event) => {
    console.log('WebSocket connection closed', event);
    setTimeout(() => {
      console.log('Reconnecting...');
      initWebSocket();
    }, 10000); // 10 Sekunden warten, bevor eine erneute Verbindung versucht wird
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
    console.log('Message sent to WebSocket server:', messageToSend);

    // Nachricht auch per HTTP-POST senden
    axios.post(endpoint, messageToSend)
        .then(() => {
          console.log('Message sent to server via HTTP:', messageToSend);
          messages.value.push(messageToSend);
          newMessage.value.message = '';
        })
        .catch((error) => {
          console.error('Error sending message via HTTP:', error);
        });
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
