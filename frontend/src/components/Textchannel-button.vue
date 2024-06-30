<template>
  <button
    @click="buttonClicked(channel)"
    class="channel-button"
    v-for="channel in channelsForUser"
    :class="{ active: activeChannelId === channel.id }"
    :key="channel.id"
  >
    <span class="chat-tag"># {{ channel.name }}</span>
  </button>
</template>

<script setup lang="ts">
import axios from 'axios'
import { onMounted, type Ref, ref } from 'vue'

type Message = {
  id:string;
  sender:User;
  created_at:string;
}
type Channel = {
  id: string
  name: string
  users: User[]
  messages: Message[]
}
type User = {
  id: string
  username: string
  email: string
  password: string
  messages: Message[]
  textChannels: Channel[]
}

const emit = defineEmits(['channelButtonClicked'])
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
let channelsForUser: Ref<Channel[]> = ref([])
const props = defineProps({
  userData: Object,
  activeChannelId: String
})
onMounted(() => {
  axios.get(`${baseUrl}/users/${props.userData?.id}/channels`)
    .then(response => {
      let channelIds = response.data.map((item: Channel | string) => {
        if (typeof item === 'object') {
          // Wenn Item ein Objekt ist, gib ID-Item
          return item.id;
        } else {
          // Wenn kein Objekt(String), gib Item
          return item;
        }
      });
      // Map each channelId to a fetch promise
      const fetchPromises = channelIds.map(async (channelId: string) => {
        try {
          const channelResponse = await axios.get(`${baseUrl}/channels/${channelId}`);
          return channelResponse.data;
        } catch (error) {
          console.error("Error fetching channel ${channelId}:", error);
          return null; // Return null in case of error
        }
      });
      // Use Promise.all to wait for all promises to resolve
      Promise.all(fetchPromises).then(channelsData => {
        // Filtert jeden Null-Wert und update channelsForUser
        channelsForUser.value = channelsData.filter(channel => channel !== null);
      });
    });
});
function buttonClicked(channel: Channel) {
  emit('channelButtonClicked', channel)
}
</script>

<style scoped>
.channel-button:hover {
  background-color: #83deb0;
  cursor: pointer;
}
.active {
  background-color: #83deb0;
}
</style>