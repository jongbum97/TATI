<script setup>
import {RouterLink} from "vue-router";
</script>

<template>
    <section id="banner">
        <div class="content">
            <header>
                <h1>
                    Welcome<br />
                    To TATI!!
                </h1>
                <h3>Tourist Attraction Type Indicator</h3>
            </header>
            <div>&nbsp;</div>
            <div class="icon fa-paw">&nbsp; TATI(나의 여행 유형)을 검사하고, 관광지를 추천받으세요!</div>
            <div class="icon fa-paw">&nbsp; 같은 유형의 사람들과 소통해보세요!</div>
            <div class="icon fa-paw">&nbsp; 실시간 채팅방을 즐겨보세요!</div>
            <div class="icon fa-paw">&nbsp; 지도에서 관광지를 검색해 보세요!</div>
            <div class="icon fa-paw">&nbsp; 관광지 순위를 살펴보세요!</div>
            <div class="icon fa-paw">&nbsp; 나만의 여행지를 추천해보세요!</div>
            <div class="icon fa-paw">&nbsp; 친구를 팔로우 하고 정보를 공유하세요!</div>
            <p>&nbsp;</p>
            <ul class="actions">
                <li><router-link class="button big" :to="{name: 'test'}">TATI 검사하기</router-link></li>
            </ul>
        </div>

        <span class="image object">
            <img src="@/assets/images/main.png" alt="" />
        </span>
    </section>
</template>

<style scoped>
.icon {
    margin: 15px;
}
</style>
