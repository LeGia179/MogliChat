<script setup lang="ts">

</script>

<template>
  <div class="container" id="container">
    <div class="form-container sign-up">
      <form>
        <h1>Create Account</h1>
        <span> use your email for registration</span>
        <input type="text" placeholder="Name">
        <input type="email" placeholder="Email">
        <input type="password" placeholder="password">
        <button>Sign Up</button>
      </form>
    </div>

    <div class="form-container sign-in">
      <form>
        <h1>Sign In</h1>
        <span> use your email and password</span>
        <input type="email" placeholder="Email">
        <input type="password" placeholder="password">
        <button>Sign In</button>
      </form>
    </div>

    <div class="toggle-container">
      <div class="toggle">
        <div class="toggle-panel toggle-left">
          <h1>Welcome Back!</h1>
          <p>Enter your login data</p>
          <button class="hidden" id="login">Sign In</button>
        </div>
        <div class="toggle-panel toggle-right">
          <h1>Hello, Welcome Back!</h1>
          <p>Enter your login data </p>
          <button class="hidden" id="login">Sign Up</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Montserrat', sans-serif;
}



.container{
  background-color: #f5f6f7;
  border-radius: 30px;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  position: relative;
  overflow: hidden;
  width: 750px;
  max-width: 100%;
  min-height: 480px;
}

.container p{
  font-size: 14px;
  line-height: 20px;
  letter-spacing: 0.3px;
  margin: 20px 0;
  color: black;
}

.container span{
  font-size: 12px;
}

.container button{
  background-color: #746696;
  color: black;
  font-size:  12px;
  padding: 10px 45px;
  border: 1px solid transparent;
  border-radius: 8px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  margin-top: 10px;
}

.container button.hidden{
  background-color: transparent;
  border-color: #f5f6f7;
}

.container form{
  background-color: #f5f6f7;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 20px;
  height: 100%;
}

.container input{
  background-color: #aaacad;
  border: none;
  margin: 8px 0px;
  padding:10px 15px;
  font-size: 13px;
  border-radius: 8px;
  width: 100%;
  outline: none;
}

.form-container{
  position: absolute;
  top:0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in{
  left: 0px;
  width: 50%;
  color: black;
}

.container.active.sign-in{
  transform: translate(100%);
}

.sign-up{
  left: 0px;
  width: 50%;
  opacity: 0;
  color: black;
}

.container.active.sign-up{
  transform: translate(100%);
  opacity:1;
  animation: move 0.6s;
  color: black;
}

.toggle-container {
  position: absolute;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: all 0.6s ease-in-out;
  border-radius: 30px;
  z-index: 100;
  color: black;
}

.container.active .toggle-container {
  transform: translateX(-100%);
}

.toggle{

  height: 100%;
  background: linear-gradient(to right,#ced3c4, #7fa970);
  justify-content: center;
  align-items: center;
  display: flex;
  flex:1;

}
</style>
