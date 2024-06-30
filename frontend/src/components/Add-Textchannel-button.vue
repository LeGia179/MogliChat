<template>
  <form class="create-channel-shape">
    <button id="add-channel-button" class="join-button" type="button" @click="openModal">Textkanal erstellen</button>
  </form>
  <form v-if="isOpen" class="modal">
    <div class="modal-content">
      <div class="header">
        <p class="header-name">Textkanal</p>
        <span @click="closeModal" class="closeModal">&#10005;</span>
      </div>
        <div class="name-description">
          <div class="input">
            <p class="name-tag">Name</p>
            <input v-model="name" type="text" class="user-input-field" id="name-input">
          </div>
        </div>
      <button class="create-channel-button" @click="createChannel" type="button">erstellen</button>
    </div>
  </form>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';

const id = ref("")
const name = ref("")
const description = ref("")
let isOpen = ref(false)
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
const emit = defineEmits(["channelAdded"])
const props = defineProps({
  userData: Object
});

function openModal(){
  isOpen.value = true
  console.log("Modal is Open: " + isOpen.value)
}
function closeModal(){
  isOpen.value = false
  console.log("Modal is Open: " + isOpen.value)
}
function createChannel(){
  if (name.value.trim() === '') {
    alert('Bitte gebe einen Namen ein!');
    return;
  }
  //erstellt Channel
  axios.post(`${baseUrl}/channels/users/${props.userData?.id}`, {
    name: name.value,
    description: description.value
  })
  .then(function (response) {
    id.value = response.data.id;
    name.value = "";
    description.value = response.data.description;
    console.log("id value: "+id.value);
    console.log(response);
    //fügt User zu Channel hinzu
    axios.post(`${baseUrl}/channels/${id.value}/users/${props.userData?.id}`).then(function (response) {
      isOpen.value = false;
      emit("channelAdded", props.userData)
    })
  })
  //schließt Modal
  isOpen.value = false;
}

</script>

<style scoped>
.header{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background: transparent;
}
.header-name{
  font-size: 30px;
  font-weight: bold;
  margin: 20px 0 20px 0;
  background: transparent;
  color: #83deb0;
}
.name-tag{
  margin: 10px 0 0 0;
  font-size: 20px;
  font-weight: 500;
  color: #83deb0;
}
.user-input-field{
  margin: 10px 0 10px 0;
  padding: 10px 0 10px 10px;
  width: 96%;
  font-family: 'Poppins', sans-serif;
  font-size: 18px;
  border-radius: 4px;
  border-style: solid;
  border-color: gray;
  border-width: 1px;
  height: 20px;
}
.name-description{
  width: 102%;
}
.create-channel-button{
  background-color: #83deb0;
  border: none;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  font-family: 'Poppins', sans-serif;
  margin-top: 30px;
  width: 100%;
  border-radius: 4px;
}
.create-channel-button:hover{
  cursor: pointer;
  color: black;
}
/* Modal (background) */
.modal {
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.4);
  font-family: 'Poppins', sans-serif;
}

.modal-content {
  background-color: #313332;
  margin: 15% auto; /* 15% von oben und zentriert */
  padding: 20px;
  border: 1px solid #888;
  width: 20%;
  border-radius: 6px;
  border-color: #83deb0;
  color: black;
}
.closeModal {
  width: 32px;
  height: 32px;
  opacity: 0.3;
  font-size: 20px;
  margin: 0;
  color: #83deb0;
}
.closeModal:hover {
  opacity: 1;
  cursor: pointer;
}
</style>