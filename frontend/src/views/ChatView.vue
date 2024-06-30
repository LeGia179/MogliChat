<template>
  <div class="chatUI">
    <div class="sidebar">
      <div class="logo">
        <img src="/src/assets/logo-transparent-png.png" class="logo_img" alt="logo-img" />
        <p class="app-name"> </p>
      </div>
      <h1></h1>
      <p class="side-tags">Channel beitreten</p>
      <input v-on:keyup.enter="addUserToChannel(channelNameInput)"
             v-model="channelNameInput"
             type="text" class="search-bar"
             placeholder="Textkanal suchen" />

      <p class="side-tags">Textkanäle</p>

      <TextChannelButton
        :key="userKey"
        @channel-button-clicked="onChannelButtonClicked"
        v-if="user"
        :activeChannelId="activeChannelId"
        :user-data="user">
      </TextChannelButton>

      <AddDirectchannelButton v-if="user" :user-data="user"></AddDirectchannelButton>

      <AddChannelButton v-if="user" :user-data="user" @channelAdded="updateUserInfo"></AddChannelButton>

      <div class="icon-bar">
        <div class="icon-bar-box">
          <!-- Button für das Profil -->
          <button class="setting-button" id="show-user-info" type="button" @click="openModal"> <img src="../assets/profile.png" class="userIcon"> </button>
          <form v-if="isOpen" class="modal">
            <div class="modal-content">
              <div class="header">
                <p class="header-title">User Infos:</p>
                <span @click="closeModal" class="closeModal">&#10005;</span>
              </div>
              <div class="user-description-structure">
                <div class="user-description">
                  <p class="info-tag"><strong>Name:</strong> {{ user.username }}</p>
                  <p class="info-tag"><strong>Email:</strong> {{ user.email }}</p>
                  <p class="info-tag"><strong>ID:</strong> {{ user.id }}</p>
                </div>
              </div>
            </div>
          </form>
          <!-- Button für die Einstellungen -->
          <button class="settingsButton2"><img src="../assets/setting.png" class="settingIcon" @click="showFish"> </button>
          <!-- Button für das Logout -->
          <button class="settingsButton3"> <img src="../assets/leave.png" class="logout-icon" @click="logout"></button>
        </div>
      </div>
    </div>

    <div class="channel-description" ></div>
    <div v-if="!isInputDisabled"  class="channel-description">
      <p  class="channel-name"> {{channelName}} </p>
      <button class="leave-button"> <img src="../assets/logout.png" class="logout-icon" @click="leaveChannel"></button>
    </div>

    <MessageHistory :show="showMessageHistory"
      :isInputDisabled="isInputDisabled"
      :userData="user"
      :active-channel-id="activeChannelId"
      ref="messageHistory"></MessageHistory>
  </div>
</template>

<script setup lang="ts">
import '../styles/sidebar.css'
import '../styles/channel-bar.css'
import '../components/Textchannel-button.vue'
import TextChannelButton from '@/components/Textchannel-button.vue'
import AddChannelButton from '@/components/Add-Textchannel-button.vue'
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import MessageHistory from "@/components/MessageHistory.vue";

type User = {
  id: string;
  username: string;
  email: string;
  password: string;
  messages: any[];
  textchannels: (Channel)[];
  directchannels: any[];
};
type Channel = {
  id: string;
  name: string;
  description: string;
  users: User[];
  messages: any[];
};

const user = ref();
const userId = ref("")
const route = useRoute();
const channelName = ref("");
const activeChannelId = ref<string | undefined>(undefined);
const isInputDisabled = ref(true);
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
const userKey = ref(0);
const channelNameInput = ref("");
const showMemberList = ref(true);
const showMessageHistory = ref(true);
const messageHistory = ref<any>(null);
const router =useRouter();

//für show User info
let isOpen = ref(false);
function openModal() {
  isOpen.value = true;
  console.log("Modal isOpen: " + isOpen.value);
}
function closeModal() {
  isOpen.value = false;
  console.log("Modal isOpen: " + isOpen.value);
}

watch(user, () => {
  userKey.value++;
}, { deep: true });

onMounted(() => {
  userId.value = route.params.userId as string;
  axios
    .get(`${baseUrl}/users/` + userId.value)
    .then((response) => {
      console.log(response.data);
      console.log(userId.value);
      user.value = response.data;
    })
})

const showPopup = ref(false);
function togglePopup() {
  showPopup.value = !showPopup.value;
}
function updateUserInfo(userOutput:User){
  axios
    .get(`${baseUrl}/users/` + userOutput.id)
    .then((response) => {
      console.log(response.data);
      console.log(userId.value);
      user.value = response.data;
    })
}

function leaveChannel(){
  axios
    .delete(`${baseUrl}/channels/` + activeChannelId.value + "/users/" + userId.value)
    .then(() => {
      console.log("user removed from channel")
      updateUserInfo(user.value)
      channelName.value = "";
      activeChannelId.value = undefined;
      isInputDisabled.value = true;
      showMemberList.value = false;
      showMessageHistory.value = false;
      console.log("disableInput")
    })
}

function onChannelButtonClicked(channel: Channel) {
  console.log("channel-button clicked, id: " + channel.id)
  channelName.value = channel.name;
  activeChannelId.value = channel.id;
  isInputDisabled.value = false;
  showMemberList.value = true;
  showMessageHistory.value = true;
  console.log("enableInput")
}

function addUserToChannel(channelName:string){
  axios
    .post(`${baseUrl}/channels/name/` + channelName + "/users/" + userId.value)
    .then(() => {
      console.log("user added to channel")
      updateUserInfo(user.value)
      channelNameInput.value = "";
    })
}

function showFish(){
  router.push('/showFish')
}

function logout() {
  router.push('/')
}
</script>

<style scoped>

.header-title{
  font-size: 30px;
  font-weight: bold;
  margin: 20px 0 20px 0;
  background: transparent;
  color: #83deb0;
}
.header{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background: transparent;
}
.chatUI{
  display: grid;
  grid-template-columns: 400px 1fr;
  grid-template-rows: [row1-start] 130px [row1-end] 1fr [line-2] 100px [last-line];
  height: 100vh;
}
.leave-button {
  display: flex;
  justify-content: end;
  background: transparent;
  border: none;
  text-align: center;
  width: 1vh;
}
.user-description-structure{
  width: 100%;
}
.info-tag{
  margin: 10px 0 0 0;
  font-size: 20px;
  font-weight: 500;
  color: #83deb0;
}

.modal {
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
  font-family: 'Poppins', sans-serif;
}
.modal-content {
  background-color: #313332;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 20%;
  border-radius: 6px;
  border-color: #83deb0;
}
.closeModal {
  width: 32px;
  height: 32px;
  opacity: 0.3;
  font-size: 20px;
  margin: 0;
  color:#83deb0;
}
.closeModal:hover {
  opacity: 1;
  cursor: pointer;
}
</style>