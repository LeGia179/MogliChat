<!-- Struktur für Chat, Nachrichtenanzeige, Eingabefeld -->
<template>
<!-- Container für Nachrichtenanzeige -->
  <div class="grid-container01">
    <div class="nachrichtenUi">
<!-- Nachrichten werden gerendert -->
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
<!-- Container für das Eingabefeld -->
  <div class="grid-container02">
    <div class="userInput">
      <input type="text" v-model="newMessage.message" placeholder="Message" class="inputField" @keyup.enter="addNewMessage">
      <button @click="addNewMessage" class="sendButton">
        <img src="../assets/send.png" class="sendIcon" alt="Send">
      </button>
    </div>
  </div>
</template>

<!-- Funktion: Senden/Laden von Nachrichten -->
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Message } from '@/model/message';
import axios from "axios";

// Initialisiert das Nachrichten-Array
let messages = ref<Message[]>([]);

// Initialisiert die neue Nachricht
const newMessage = ref<Message>({ userName: '', message: '', timestamp: new Date().toISOString() });

// Basis-URL für das Backend
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
const endpoint = baseUrl + "/message";

// Lädt den aktuellen Benutzer aus dem lokalen Speicher
const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

// Lädt Nachrichten vom Server, wenn die Komponente gemountet wird
onMounted(() => {
  axios.get(endpoint).then((response) => {
    messages.value = response.data;
  }).catch((error) => {
    console.error('Error fetching message', error);
  });
});

// Fügt eine neue Nachricht hinzu
function addNewMessage() {
  if (!currentUser || !currentUser.username) {
    console.error('No user logged in');
    return;
  }
  const messageToSend = { userName: currentUser.username, message: newMessage.value.message, timestamp: new Date().toISOString() };
  axios.post(endpoint, messageToSend)
      .then(() => {
        messages.value.push(messageToSend);
        newMessage.value.message = '';
      })
      .catch((error) => {
        console.error('Error sending message:', error);
      });
}

// Formatiert den Zeitstempel
function formatTimestamp(timestamp: string): string {
  const date = new Date(timestamp);
  return date.toLocaleString();
}

// Gibt die Klasse für die Nachrichten-Seite zurück
function getMessageSide(userName: string): string {
  return currentUser.username === userName ? 'user-message' : 'other-message';
}
</script>

<!-- Styling -->
<style scoped>
/* Styling Nachrichtenanzeige */
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
/* Styling Nachrichten */
.chat {
  display: flex;
  flex-direction: column;
  word-wrap: break-word;
  padding: 10px;
  border-radius: 10px;
  margin: 10px 0;
  max-width: 70%; /* Maximale Breite der Nachricht */
}
/* Styling für Nachrichten aktueller Benutzer */
.chat.user-message {

  background-color: greenyellow;
  text-align: right;
  margin-left: auto;
}
/* Styling für Nachrichten anderer Benutzer */
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
/* Styling für Eingabefeld */
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
