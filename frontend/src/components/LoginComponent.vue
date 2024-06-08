<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();
function anmelden() {
  router.push('/chat');
}
</script>

<template>
  <div class="startseite-links">
    <router-link to="/">
      <div class="logo-container">
        <img src="../assets/logo-transparent-png.png" class="logo">
      </div>
    </router-link>
    <div class="animation-container">
      <p class="animated-text">Willkommen bei MogliChat</p>
    </div>
  </div>

  <div class="startseite-rechts">
    <h1>Anmelden</h1>
    <div class="input-group">
      <span class="email-password">Email</span>
      <input type="text" class="emailbox">
    </div>
    <div class="input-group">
      <span class="email-password">Passwort</span>
      <input type="text" class="passwordbox">
    </div>
    <div class="startseite-rechts-innenbox">
      <button @click="anmelden" class="loginButton">Anmelden</button>
      <p>Noch kein Konto? <router-link to="/register">Registrieren</router-link ></p>
    </div>
  </div>
</template>

<style scoped>
.email-password {
  text-align: left;
  margin-right: 10px;
  align-self: flex-start;
}

.input-group {
  display: flex;
  flex-direction: column;
}

.input-group input {
  align-self: stretch;
}

.startseite-links {
  background-color: #4a536b;
  padding: 30px;
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* Logo oben rechts ausrichten */
  justify-content: center;
  grid-column-start: 1;
  grid-column-end: 2;
}

.logo-container {
  margin-bottom: auto; /* Logo am oberen Rand ausrichten */
}

.animation-container {
  display: flex;
  align-items: center; /* Text mittig ausrichten */
  justify-content: center;
  height: 100%;
}

.animated-text {
  font-size: 2em;
  color: #83deb0;
  animation: slideIn 3s ease infinite;
}

@keyframes slideIn {
  0% {
    transform: translateY(-100%);
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    transform: translateY(0%);
    opacity: 1;
  }
}

.startseite-links .logo {
  height: 5em;
  width: auto;
  vertical-align: middle;
}

.startseite-rechts {
  background-color: #313332;
  display: flex;
  grid-column-start: 2;
  grid-column-end: 3;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  color: #83deb0;
}

.loginButton{
  background-color: #4a536b;
  color: #83deb0;
  height: 5vh;
  width: 20vh;
  border-radius: 10px;
  margin: 15px 5px 10px 10px;
  border: 1px solid #83deb0;
}

.startseite-rechts-innenbox p {
  margin-top: 20px;
  font-size: 14px;
}
</style>


