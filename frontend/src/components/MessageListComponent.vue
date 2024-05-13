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
      </div>
    </div>
  </div>

  <div class="grid-container02">
    <!-- User Input für den Chat -->
    <div class="userInput">
      <label>User Name</label>
      <input type="text" v-model="newMessage.userName">
      <label>User Message</label>
      <input type="text" v-model="newMessage.message">
      <button @click="addNewMessage"><img src="../assets/send.png" class="sendIcon"></button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {type Ref, ref} from 'vue';
import type { Message } from '@/model/message';

// #1 initialisiert der "Message" durch ein leeren Array. Array dient für Nachrichtenobjekte zu speichern
// #ref ist eine reaktive Variable, die hier Message annimmt und bei änderungen wird diese Überwachte Variable Message automatisch an die Benutzeroberfläche aktualisiert.
let messages: Ref<Message[]> = ref([]);
//#5 initialisierung der neuen Nachricht
const newMessage = ref<Message>({ userName: '', message: '' });
//#3 Funktion zum Hinzufüren einer neuen Nachricht
function addNewMessage() {
  messages.value.push({ userName: newMessage.value.userName, message: newMessage.value.message });
  //#4 Eingabefelder zurücksetzen
  newMessage.value.userName = '';
  newMessage.value.message = '';
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
  height:95%;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;

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
.userInput{
  background-color: #313332;
  display: flex;
  height: 50%;
  width: 90%;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  gap:150px;
  background-color: #313332;


}
.sendIcon{
  height: 3vh;
;
}


.chat{
  padding: 20px;
  color:#83deb0;
}
</style>


