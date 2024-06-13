<template>
  <div class="kontakte">
    <div class="kontaktBox">
      <div class="header">
        <h1> Chats</h1>
        <div class="actions">
          <input type="text" placeholder="Neuer Kanalname..." v-model="newChannelName" class="suchleiste"/>
          <button @click="addChannel" class="addButton">
            <img src="../assets/addChannel.png" class="addChannelIcon" alt="Add Channel Icon"></button>
        </div>
      </div>
      <div class="chatFensterBox">
        <div v-for="(channel, index) in channels" :key="index" class="chatFenster">
          {{ channel}}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// Channel Liste initialisieren
const channels = ref(['Channel 1', 'Channel 2', 'Channel 3']);
const newChannelName = ref('');

// Methode zum Hinzufügen eines neuen Kanals
const addChannel = () => {
  console.log('addChannel aufgerufen');
  console.log('neuer Kanalname:', newChannelName.value);

  if (newChannelName.value.trim() !== '') {
    channels.value.push(newChannelName.value);
    console.log('Kanäle nach Hinzufügen:', channels.value);
    newChannelName.value = ''; // Zurücksetzen des Eingabefelds nach dem Hinzufügen
  } else {
    console.log('Kanalname ist leer oder nur Leerzeichen');
  }
};
</script>

<style scoped>

.kontakte {
  display: flex;
  grid-column-start: 2;
  grid-row-start: 1;
  grid-row-end: 6;
  background-color: #4a536b;
  justify-content: center;
  align-items: center;
}
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
.suchleiste {
  width: 200px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #83deb0;
  margin-left: 10px;
}

.chatFensterBox {
  display: flex;
  flex-direction: column; /* Kanäle untereinander anordnen */
  height: 80%;
  width: 90%;
  background-color: #83deb0;
  border-radius: 20px;
  margin: 15vh 0 0 0;
  overflow-y: auto; /* Scrollen ermöglichen, falls die Liste lang wird */
}

.chatFenster {
  padding: 10px;
  border-bottom: 1px solid #313332;
}
</style>
