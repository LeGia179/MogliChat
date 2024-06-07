<template>
  <div class="grid-container01">
    <div class="nachrichtenUi">
    <!-- User Nachrichten
    #2 Rendern der Nachrichten. Hier wird jedes Element in "messages" iteriert um die Nachrichten zu rendern
    -->
      <div class="chat" v-for="userMessage in messages" :key="userMessage.userName">
        <div>
          {{ userMessage.userName }}: {{ userMessage.message }}
        </div>
        <div class="timestamp">
          {{ formatTimestamp(userMessage.timestamp) }}
        </div>
      </div>
    </div>
  </div>

  <div class="grid-container02">
    <!-- User Input für den Chat -->
    <div class="userInput">
      <input type="text" v-model="newMessage.userName" placeholder="Name" class="inputField nameInput">
      <input type="text" v-model="newMessage.message" placeholder="Message" class="inputField" @keyup.enter="addNewMessage">
      <button @click="addNewMessage" class="sendButton">
        <img src="../assets/send.png" class="sendIcon" alt="Send">
      </button>
    </div>
  </div>

</template>
<script setup lang="ts">
import {onMounted, type Ref, ref} from 'vue';
import type { Message } from '@/model/message';
import axios from "axios";

// #1 initialisiert der "Message" durch ein leeren Array. Array dient für Nachrichtenobjekte zu speichern
// #ref ist eine reaktive Variable, die hier Message annimmt und bei änderungen wird diese Überwachte Variable Message automatisch an die Benutzeroberfläche aktualisiert.
let messages: Ref<Message[]> = ref([]);
//#5 initialisierung der neuen Nachricht
const newMessage = ref<Message>({ userName: '', message: '' , timestamp: new Date().toISOString() });
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
const endpoint = baseUrl + "/message"
//wenn die seite zum erstenmal geladen wird oder bei refresh
onMounted(() => {
  axios.get(endpoint).then((response) => {
    messages.value = response.data;
    console.log(messages.value)
  }).catch((error) => {
    console.error('Error fetching message', error);
  })
})

//#3 Funktion zum Hinzufüren einer neuen Nachricht
function addNewMessage() {
  const messageToSend = { userName: newMessage.value.userName, message: newMessage.value.message, timestamp: new Date().toISOString() };
  axios.post(endpoint, messageToSend)
      .then(() => {
        //
        messages.value.push(messageToSend);
        newMessage.value.userName = '';
        newMessage.value.message = '';
      })
      .catch((error) => {
        console.error('Error sending message:', error);
      });
}



/*
Meilenstein 3

let helloWorldMessage= ref("test")

function onClick() {
  axios.get("http://localhost:8080/mogli")
        .then((response) => {
          console.log(response.data);
          helloWorldMessage.value = response.data;
        })

}
*/
function formatTimestamp(timestamp: string): string {
  const date = new Date(timestamp);
  return date.toLocaleString();
}
</script>

<style scoped>
.grid-container01 {
  display: flex;
  grid-column-start: 3;
  grid-column-end: 6;
  grid-row-start: 2;
  grid-row-end: 3;
  background-color:#4a536b;
  align-items: center;
  justify-content: center;

}
.nachrichtenUi{
  height:75vh;
  overflow-y: auto;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  border: 1px solid #83deb0;
}
.chat {
  word-wrap: break-word;
  text-overflow: ellipsis; /* Abgeschnittener Inhalt mit Ellipsen anzeigen */
  padding: 5px 0; /* Innenabstand für jede Nachricht */
  border: 1px solid #83deb0;
  width: 50%;
  margin: 30px 0 0 20px;
  border-radius: 10px;


}

.grid-container02{
  display: flex;
  grid-column-start: 3;
  grid-column-end: 6;
  grid-row-start: 3;
  grid-row-end: 4;
  background-color:#4a536b;
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
  margin-right: 5px; /* Abstand zwischen den Eingabefeldern */
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
.sendIcon{
  height: 3vh;
}


.sendButton {
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin-top: auto;
}

.chat{
  padding: 20px;
  color:#83deb0;
}

.timestamp {
  font-size: 0.8em;
  color: gray;
}
</style>


