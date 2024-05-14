<template>
  <div class="kontakte">
    <div class="kontaktBox">
      <div class="chatFensterBox"></div>
    </div>
  </div>

</template>

<script setup lang="ts">


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
  height:95%;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  border: 1px solid #83deb0;
}

.chatFensterBox {
  display: flex;
  height:80%;
  width: 90%;
  background-color: #83deb0;
  border-radius: 20px;
  margin: 15vh 0 0 0;


}

.suchleiste{

}
</style>


