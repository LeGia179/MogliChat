<template>
<div class="seitenleiste">
  <div class="seitenleisteBox">
    <button class="settingsButton"> <img src="../assets/profile.png" class="userIcon"> </button>
    <button class="settingsButton2"><img src="../assets/setting.png" class="settingIcon" @click="supportSite"> </button>
    <button class="settingsButton3"> <img src="../assets/logout.png" class="logoutIcon" @click="logout"></button>
  </div>
</div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'

const router = useRouter()
function supportSite() {
  router.push('/support')
}
 function logout() {
  router.push('/')
}
</script>

<style scoped>


.seitenleiste {
  grid-row-start: 1;
  grid-row-end: 6;
  grid-column-start: 1;
  background-color:#4a536b;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;


}

.settingsButton{
  display: flex;
  justify-content: center;
  background-color: transparent;
  border: none;
  text-align:center;
  width: 100%;


}
.settingsButton2{
  display: flex;
  justify-content: center;
  background-color: transparent;
  border: none;
  text-align:center;
  width: 100%;

}

.settingsButton3{
  display: flex;
  justify-content: center;
  background: transparent;
  border: none;
  text-align: center;
  width: 100%;
}.logoutIcon{
  height: 3vh;
}
.userIcon{
  height: 3vh;
}
.settingIcon {
  height: 3vh;
}

.seitenleisteBox {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  background-color: #313332;
  height: 95%;
  width: 90%;
  border-radius: 20px;
  padding: 10px;
  border: 1px solid #83deb0;
}
</style>


