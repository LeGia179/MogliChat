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
  ws = new WebSocket('wss://moglichatbackend-cbuw.onrender.com/ws');

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
