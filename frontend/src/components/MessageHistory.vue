<template>
  <div v-if="show" class="container">
    <div class="message-history">
      <div class="message" v-for="message in messages" :key="message.id"
           :class="{ 'sent-message': isSentByCurrentUser(message.sender.id) }">
        <img class="user-img" src="../assets/user.png" alt="user-img" />
        <div class="message-info">
          <div class="user-date">
            <p class="sender">{{ message.sender.username }}</p>
            <p class="timestamp">{{ message.date }}</p>
          </div>
          <p class="content">{{ message.content }}</p>
        </div>
      </div>
    </div>
    <button @click="postMessage(message_content)" class="send-button" type="button"><img src="/src/assets/send.png" class="send-img" alt="send-img" />  </button>
  </div>
  <input v-model="message_content"
         :disabled="props.isInputDisabled"
         v-on:keyup.enter="postMessage(message_content)"
         type="text"
         id="message-input"
         placeholder="Nachricht eingeben"/>
</template>

<script setup lang="ts">
import { nextTick, onMounted, ref, watch } from 'vue';
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
import type { Client, Frame } from 'webstomp-client';
import { onBeforeUnmount } from 'vue';

type Message = {
  id: string;
  content: string;
  date: string;
  sender: Sender;
};
type Sender = {
  id: string;
  username: string;
};
const props = defineProps({
  activeChannelId: String,
  userData: Object,
  isInputDisabled: Boolean,
  show: Boolean,
});

let messages = ref<Message[]>([]);
let message_content = ref("");
let stompClient: Client | null = null;
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
let currentSubscription: any | null = null;

const connectWebSocket = (): void => {
  const sock = new SockJS(`${baseUrl}/ws`);
  stompClient = Stomp.over(sock);
  stompClient.connect(
      {},
      (frame: Frame | undefined) => {
        console.log('Connected:', frame);
      },
      (error: Frame | CloseEvent) => {
        console.error('Connection error:', error);
      }
  );
};

const subscribeToChannel = (channelId: string): void => {
  if (!stompClient || !channelId) return;

  if (currentSubscription) {
    currentSubscription.unsubscribe();
    currentSubscription = null;
  }
  const topic = `/topic/channel/${channelId}`;
  currentSubscription = stompClient.subscribe(topic, (message: Frame) => {
    const newMessage: Message = JSON.parse(message.body);
    if (newMessage && newMessage.date && newMessage.content && newMessage.sender) {
      messages.value.push(newMessage);
      scrollToBottom();
    } else {
      console.error("Received message does not match 'Message' structure:", newMessage);
    }
  });
};

const postMessage = async (messageContent: string): Promise<void> => {
  if (messageContent.trim() === '') {
    alert('Bitte gebe eine Nachricht ein!');
    return;
  }
  try {
    const savedMessage = {
      content: messageContent,
      date: getCurrentDateTime(),
      sender: props.userData?.username,
    };
    await axios.post(`${baseUrl}/channels/${props.activeChannelId}/users/${props.userData?.id}/messages`, {
      content: messageContent,
    });
    messages.value.push({
      id: Date.now().toString(),
      content: messageContent,
      date: getCurrentDateTime(),
      sender: {
        id: props.userData?.id || '',
        username: props.userData?.username || '',
      },
    });
    scrollToBottom();
    // Clear the input field
    message_content.value = '';
  } catch (error) {
    console.error('Error posting message:', error);
  }
};

const fetchMessages = async (channelId: string): Promise<void> => {
  try {
    const response = await axios.get(`${baseUrl}/channels/${channelId}/messages`);
    messages.value = response.data;
    scrollToBottom();
  } catch (error) {
    console.error('Error fetching messages:', error);
  }
};

const getCurrentDateTime = (): string => {
  const now = new Date();
  const day = now.getDate().toString().padStart(2, '0');
  const month = (now.getMonth() + 1).toString().padStart(2, '0');
  const year = now.getFullYear();
  const hours = now.getHours().toString().padStart(2, '0');
  const minutes = now.getMinutes().toString().padStart(2, '0');
  return `${day}.${month}.${year} ${hours}:${minutes}`;
};

const scrollToBottom = (): void => {
  nextTick(() => {
    const messageHistoryDiv = document.querySelector('.message-history');
    if (messageHistoryDiv) {
      messageHistoryDiv.scrollTop = messageHistoryDiv.scrollHeight;
    }
  });
};
const isSentByCurrentUser = (userId: string): boolean => {
  return userId === props.userData?.id;
};

watch(() => props.activeChannelId, (newChannelId, oldChannelId) => {
  if (newChannelId) {
    fetchMessages(newChannelId);
    if (oldChannelId && currentSubscription) {
      currentSubscription.unsubscribe();
      currentSubscription = null;
    }
    subscribeToChannel(newChannelId);
  }
}, { immediate: true });

onMounted(() => {
  if (props.activeChannelId) {
    fetchMessages(props.activeChannelId);
  }
  connectWebSocket();
});

onBeforeUnmount(() => {
  if (currentSubscription) {
    currentSubscription.unsubscribe();
  }
  if (stompClient && stompClient.connected) {
    stompClient.disconnect(() => {
      console.log('Disconnected from WebSocket');
    });
  }
});

</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}
.message {
  margin-left: 20px;
  margin-top: 10px; /* Abstand zwischen den Nachrichten anpassen */
  display: flex;
  align-items: flex-start; /* Nachrichten links ausrichten */
}
.message.sent-message {
  justify-content: flex-end; /* Nachrichten des aktuellen Benutzers nach rechts ausrichten */
  margin-right: 20px;
}
.content {
  color: white;
  font-size: 20px;
  margin-top: 0;
  margin-bottom: 0;
}
.user-img {
  height: 55px;
  width: 55px;
  border-radius: 4px;
  margin-right: 10px;
}
.sender {
  color: #83deb0;
  font-size: 20px;
  margin-right: 8px;
  margin-bottom: 0;
  margin-top: 0;
}
.timestamp {
  margin: 0;
  color: gray;
}
.message-history {
  overflow-y: auto;
  max-height: calc(100vh - 240px);
  width: 100%;
}
.user-date {
  display: flex;
  align-items: center;
}
#message-input {
  background-color: #141419;
  border-width: 1px;
  border-style: solid;
  border-color: white;
  color: white;
  font-size: 20px;
  padding-left: 16px;
  font-family: 'Poppins', sans-serif;
  position: fixed;
  bottom: 20px;
  width: calc(100% - 570px);
  left: 420px;
  height: 50px;
}
.send-button{
  background-color: transparent;
  border: none;
  color: white;
  padding: 16px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  margin:0 30px 21px 0;
  cursor: pointer;
  right:0;
  bottom: 0;
  position: fixed;
  height: 65px;
}
.send-img {
  height: 4vh;
}
</style>
