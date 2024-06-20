<!-- Struktur für Kontaktliste -->
<template>
<!-- Container für Kontaktliste -->
  <div class="kontakte">
<!-- Box für Kontaktliste -->
    <div class="kontaktBox">
<!-- Head für Name und Aktionselementen -->
      <div class="header">
        <h1> Chats </h1>
        <div class="actions">
<!-- Eingabefeld für den neuen Kanalnamen -->
          <input type="text" placeholder="Neuer Kanalname..." v-model="newChannelName" class="suchleiste"/>
<!-- Button zum Hinzufügen eines neuen Kanals -->
          <button @click="addChannel" class="addButton">
            <img src="../assets/addChannel.png" class="addChannelIcon" alt="Add Channel Icon"></button>
        </div>
      </div>
<!-- Box, die die Liste der Chat-Kanäle enthält -->
      <div class="chatFensterBox">
<!-- Schleife zum Rendern der Kanäle - stellt jedes "channel" als "chatFenster" dar-->
        <div v-for="(channel, index) in channels" :key="index" class="chatFenster">
          {{ channel}}
        </div>
      </div>
    </div>
  </div>
</template>

<!-- Funktion: Kanal hinzufügen -->
<script setup lang="ts">
import { ref } from 'vue';
import axios from "axios";

// Channel Liste initialisieren mit Bsp
const channels = ref(['Channel 1', 'Channel 2', 'Channel 3']);
// Variable für neuen Channel
const newChannelName = ref('');
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
// Methode zum Hinzufügen eines neuen Kanals
async function addChannel () {
  try {
    await axios.post(baseUrl + "/channel", {
      name: newChannelName.value,
    });}catch (error) {
    console.log(error);
    }
  console.log('addChannel aufgerufen');
  console.log('neuer Kanalname:', newChannelName.value);
// Überprüft, ob Channel nicht leer ist
  if (newChannelName.value.trim() !== '') {
    channels.value.push(newChannelName.value);
    console.log('Kanäle nach Hinzufügen:', channels.value);
    newChannelName.value = ''; // Zurücksetzen des Eingabefelds nach dem Hinzufügen
  } else {
    console.log('Kanalname ist leer oder nur Leerzeichen');
  }
  }
</script>

<!-- Styling -->
<style scoped>
/* Styling für die Kontaktliste */
.kontakte {
  display: flex;
  grid-column-start: 2;
  grid-row-start: 1;
  grid-row-end: 6;
  background-color: #4a536b;
  justify-content: center;
  align-items: center;
}
/* Styling für die Box, die die Kontaktliste enthält */
.kontaktBox {
  display: flex;
  flex-direction: column;
  height:95%;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  align-items: center;
  justify-content: flex-start;
  border: 1px solid #83deb0;
  padding: 10px;
}
/* Styling für den Header der Kontaktliste */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 10px;
}
.header h1 {
  color: #83deb0;
  margin: 0;
}
/* Styling für die Aktionselemente im Header */
.actions{
  display: flex;
  align-items: center;
}
.addButton{
  background-color: transparent;
  border: none;
  cursor: pointer;
}
.addButton img{
  width: 35px;
  height: auto;
  display: block;
}
.addChannelIcon{
  height: 3vh;
}
/* Styling für die Suchleiste */
.suchleiste {
  width: 200px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #83deb0;
  margin-left: 10px;
}
/* Styling für die Box, die die Chat-Kanäle enthält */
.chatFensterBox {
  display: flex;
  flex-direction: column; /* Kanäle untereinander anordnen */
  height: 80%;
  width: 90%;
  background-color: #83deb0;
  border-radius: 20px;
  margin: 15vh 0 0 0; /* Oberer Abstand */
  overflow-y: auto; /* Scrollen ermöglichen, falls die Liste lang wird */
}
/* Styling für jedes einzelne Chat-Fenster */
.chatFenster {
  padding: 10px;
  border-bottom: 1px solid #313332;
}
</style>
