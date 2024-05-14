<template>
  <div class="randomLeiste">
    <div class="randomBox"> MOGLICHAT
  </div>
  </div>
</template>

<script setup lang="ts">

</script>
<style scoped>


.randomLeiste {
  display: flex;
  grid-column-start: 3;
  grid-column-end: 6;
  grid-row-start: 1;
  grid-row-end: 2;
  background-color: #4a536b;
  justify-content: center;
  align-items: center;
  padding:22px 0 0 0;
}
.randomBox{
  display: flex;
  height:90%;
  width: 90%;
  background-color: #313332;
  border-radius: 20px;
  padding:20px;
  color:#83deb0;
  font-size: 50px;
  align-items: center;
  border: 1px solid #83deb0;
}
</style>


