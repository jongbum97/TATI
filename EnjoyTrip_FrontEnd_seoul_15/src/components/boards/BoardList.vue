<script setup>
import VPageNavigation from "../common/VPageNavigation.vue";
import {ref, onMounted} from "vue";
import {RouterLink} from "vue-router";
import {listArticle} from "@/api/board.js";
import {useMemberStore} from "@/stores/member";
import {watch} from "@vue/runtime-core";
import {storeToRefs} from "pinia";

const store = useMemberStore();
const {boardNo, userInfo} = storeToRefs(store);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const {VITE_ARTICLE_LIST_SIZE} = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
    type: boardNo,
});

onMounted(() => {
    getArticleList();
});

watch(boardNo, () => {
    getArticleList();
});

const getArticleList = () => {
    listArticle(
        param.value,
        ({data}) => {
            articles.value = data.articles;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (error) => {}
    );
};

const onPageChange = (val) => {
    currentPage.value = val;
    param.value.pgno = val;
    getArticleList();
};

const alertt = () => {
    window.alert("관리자 권한이 없습니다");
};
</script>

<template>
    <div>
        <div class="row">
            <div class="6u">
                <router-link :to="{name: 'write'}" class="button" v-if="boardNo != 4 || userInfo.userId == 'admin'"
                    >write</router-link
                >
                <router-link to="" @click="alertt" class="button" v-if="boardNo == 4 && userInfo.userId != 'admin'"
                    >write</router-link
                >
            </div>
            <form class="6u" @submit.prevent>
                <ul class="actions row">
                    <li class="3u">
                        <select class="" id="demo-category" @change="onSelect" v-model="param.key">
                            <option value="">검색조건</option>
                            <option value="article_no">글번호</option>
                            <option value="user_name">이름</option>
                            <option value="subject">제목</option>
                        </select>
                    </li>
                    <li class="6u">
                        <input
                            class=""
                            type="text"
                            placeholder="검색어..."
                            v-model="param.word"
                            @keydown.enter="getArticleList" />
                    </li>
                    <li class="3u">
                        <a class="button" @click="getArticleList">Search</a>
                    </li>
                </ul>
            </form>
        </div>
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>Num</th>
                        <th>Subject</th>
                        <th>Name</th>
                        <th>Hit</th>
                        <th>RegisterTime</th>
                    </tr>
                </thead>
                <tbody>
                    <template v-for="article in articles" :key="article.articleNo">
                        <tr
                            class="view"
                            @click="
                                $router.push({
                                    name: 'detail',
                                    params: {articleno: article.articleNo},
                                })
                            ">
                            <td>{{ article.articleNo }}</td>
                            <td>{{ article.subject }}</td>
                            <td>{{ article.userName }}</td>
                            <td>{{ article.hit }}</td>
                            <td>{{ article.registerTime }}</td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
    </div>
    <VPageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange" />
</template>

<style scoped>
.view:hover {
    background-color: rgb(221, 217, 231);
    cursor: pointer;
}
</style>
