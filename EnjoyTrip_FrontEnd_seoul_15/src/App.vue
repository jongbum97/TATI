<script setup>
import { RouterView } from "vue-router";
import TheAsideNav from "./components/layout/TheAsideNav.vue";
import TheHeading from "./components/layout/TheHeading.vue";
</script>

<template>
  <div id="wrapper">
    <div id="main">
      <div class="inner">
        <TheHeading />
        <RouterView />
      </div>
    </div>
    <TheAsideNav />
  </div>
</template>

<style scoped></style>
