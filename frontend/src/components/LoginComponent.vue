<!-- Funktion: Benutzer anmelden -->
<script setup lang="ts">
import { useRouter } from "vue-router";
import axios from "axios";
import { ref } from "vue";

// Router-Instanz für die Navigation
const router = useRouter();
// Variablen
const email = ref('');
const password = ref('');
const errorMessage = ref<string | null>(null);
// Basis-URL des Backends
const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
// Funktion zur Anmeldung mit POST-Anfrage
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
    router.push('/chat'); // Navigation zu /chat
  } catch (error) {
// Fehlerbehandlung bei fehlgeschlagener Anmeldung
    if (axios.isAxiosError(error) && error.response) {
      if (error.response.status === 401 || error.response.status === 404) {
        errorMessage.value = error.response.data;
      } else {
        errorMessage.value = 'Ein Fehler ist aufgetreten. Bitte versuchen Sie es erneut.';
      }
    } else {
      errorMessage.value = 'Ein Fehler ist aufgetreten. Bitte versuchen Sie es erneut.';
    }
  }
}
</script>

<!-- Struktur Seite -->
<template>
<!-- Startseite links -->
  <div class="startseite-links">
    <router-link to="/">
      <div class="logo-container">
        <img src="../assets/logo-transparent-png.png" class="logo"> <!-- Logo -->
      </div>
    </router-link>
    <div class="animation-container">
      <p class="animated-text">Willkommen bei MogliChat</p> <!-- Nachrichtenanzeige -->
    </div>
  </div>
<!-- Startseite rechts Anmeldeformular -->
  <div class="startseite-rechts">
    <h1>Anmelden</h1>
    <div class="input-group">
      <span class="email-password">Email</span>
      <input type="text" v-model="email" class="emailbox">
    </div>
    <div class="input-group">
      <span class="email-password">Passwort</span>
      <input type="password" v-model="password" class="passwordbox">
    </div>
    <div class="startseite-rechts-innenbox">
      <button @click="anmelden" class="loginButton">Anmelden</button>
      <p>Noch kein Konto? <router-link to="/register">Registrieren</router-link></p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<!-- Styling -->
<style scoped>
/* Styling für Labels */
.email-password {
  text-align: left;
  margin-right: 10px;
  align-self: flex-start;
}
/* Layout Eingabefeld*/
.input-group {
  display: flex;
  flex-direction: column;
}
.input-group input {
  align-self: stretch;
}
/* Styling für linke Startseite */
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
/* Kriegt die Animation von keyframes */
.animated-text {
  font-size: 2em;
  color: #83deb0;
  animation: slideIn 3s ease infinite;
}
/* Styling Animation */
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
.startseite-links .logo {
  height: 5em;
  width: auto;
  vertical-align: middle;
}
/* Styling für rechte Startseite */
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
/* Styling Login-Button */
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
/* Styling für Fehlermeldung */
.error {
  color: red;
  margin-top: 10px;
}
</style>



