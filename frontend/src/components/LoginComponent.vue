<template>
  <!-- Startseite links -->
  <div class="startview-left-side">
    <router-link to="/">
      <div class="logo-container">
        <img src="../assets/logo-transparent-png.png" class="logo">
      </div>
    </router-link>
    <div class="animation-container">
      <p class="animated-text">Willkommen bei MogliChat</p>
    </div>
  </div>
  <!-- Startseite rechts -->
  <div class="login-header">
    <h1>Anmelden</h1>
    <div class="input-group">
      <span class="email-password">Email</span>
      <input type="text" v-model="email" class="email-box">
    </div>
    <div class="input-group">
      <span class="email-password">Passwort</span>
      <input type="password" v-model="password" class="password-box">
    </div>
    <div class="register-box">
      <button @click="anmelden" class="login-button"><b>Anmelden</b></button>
    </div>
    <div class="router-register-box">
      <p>Noch kein Konto? <router-link to="/users" class="font-register">Registrieren</router-link></p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import axios from "axios";
import { ref } from "vue";

const router = useRouter();
const email = ref('');
const password = ref('');
const errorMessage = ref<string | null>(null);
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
// anmelden mit POST-Anfrage
async function anmelden() {
  try {
    const response = await axios.post(baseUrl + "/login", {
      email: email.value,
      password: password.value
    });
// Benutzerinformationen speichern
    localStorage.setItem('currentUser', JSON.stringify(response.data));
    console.log("User authenticated:", response.data);
    errorMessage.value = null; // Fehlermeldung bei Erfolg resetten
    await router.push({name: 'ChatUI', params: {userId: response.data.id,}})
  } catch (error) {
// Fehlerbehandlung bei fehlgeschlagener Anmeldung
    if (axios.isAxiosError(error) && error.response) {
      if (error.response.status === 401 || error.response.status === 404) {
        errorMessage.value = error.response.data;
      } else {
        errorMessage.value = 'User does not exist. Try again.';
      }
    } else {
      errorMessage.value = 'Ein Fehler ist aufgetreten. Bitte versuchen Sie es erneut.';
    }
  }
}
</script>

<style scoped>
/* box */
.email-password {
  text-align: left;
  margin-right: 10px;
  align-self: flex-start;
  font-size: 16px;
}
.register-box{
  margin-top: 10px;
  font-size: 14px;
}
/* Eingabefeld */
.input-group {
  display: flex;
  flex-direction: column;
  width: 25%;
}
.input-group input {
  align-self: stretch;
}

/* linke Startseite */
.startview-left-side {
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
  font-size: 5em;
  color: #83deb0;
  animation: slideIn 4s ease infinite;
}
/* für .animated-text */
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
/* Styling Logo */
.startview-left-side .logo {
  height: 5em;
  width: auto;
  vertical-align: middle;
}

/* rechte Startseite */
.login-header {
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
.login-button{
  background-color: #4a536b;
  color: #83deb0;
  height: 5vh;
  width: 20vh;
  border-radius: 10px;
  margin: 15px 5px 10px 10px;
  border: 1px solid #83deb0;
  font-size: 20px;
}
.router-register-box{
  margin-top: 10px;
  font-size: 17px;
}
.font-register{
  font-size: 17px;
  color: #83deb0;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>



