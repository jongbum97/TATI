<script setup>
import {RouterView} from "vue-router";
import {useMemberStore} from "@/stores/member";
import {storeToRefs} from "pinia";
const store = useMemberStore();
const {boardNo} = storeToRefs(store);
</script>

<template>
    <section class="content">
        <header class="">
            <h1 class="icon fa-paperclip" v-if="boardNo == 1">&nbsp;자유 게시판</h1>
            <h1 class="icon fa-paperclip" v-if="boardNo == 2">&nbsp;여행지 추천 게시판</h1>
            <h1 class="icon fa-paperclip" v-if="boardNo == 3">&nbsp;TATI 게시판</h1>
            <h1 class="icon fa-paperclip" v-if="boardNo == 4">&nbsp;공지사항</h1>
            <h3>List</h3>
            <div class="12u$">&nbsp;</div>
        </header>
        <RouterView />
    </section>
</template>

<style scoped></style>
