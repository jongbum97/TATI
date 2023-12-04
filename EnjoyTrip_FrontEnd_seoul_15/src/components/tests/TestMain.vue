<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useTripTestStore } from "@/stores/tripTest";

const route = useRoute();

const store = useTripTestStore();
const { resultSet } = store;
</script>

<template>
  <section>
    <h2 class="icon fa-hand-peace-o">&nbsp; 8가지의 TATI를 소개합니다~~</h2>
    <div class="features">
      <article v-for="result in resultSet" :key="result.no">
        <span class="icon">
          <img
            src="../../assets/images/111.png"
            alt=""
            v-if="result.no == '111'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/112.png"
            alt=""
            v-if="result.no == '112'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/121.png"
            alt=""
            v-if="result.no == '121'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/122.jpeg"
            alt=""
            v-if="result.no == '122'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/211.jpg"
            alt=""
            v-if="result.no == '211'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/212.jpeg"
            alt=""
            v-if="result.no == '212'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/221.jpeg"
            alt=""
            v-if="result.no == '221'"
            style="width: 140px; height: 140px"
          />
          <img
            src="../../assets/images/222.jpeg"
            alt=""
            v-if="result.no == '222'"
            style="width: 140px; height: 140px"
          />
        </span>
        <div class="content">
          <h3>{{ result.type }}</h3>
          <h4>
            {{ result.tag }}
          </h4>
        </div>
      </article>
    </div>
    <article>
      <ul class="actions" style="text-align: center">
        <li>
          <button class="big" @click="$router.push({ name: 'test-test' })">
            검사하기!
          </button>
        </li>
        <li>&nbsp;</li>
        <li>&nbsp;</li>
        <li>&nbsp;</li>
        <li>
          <button
            class="big special"
            @click="$router.push({ name: 'test-test' })"
          >
            검사하기!
          </button>
        </li>
      </ul>
    </article>
  </section>
</template>

<style scoped></style>
