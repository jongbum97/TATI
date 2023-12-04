<script setup>
import {ref, onMounted} from "vue";
import {useRouter, useRoute, RouterLink} from "vue-router";
import {getModifyArticle, modifyArticle} from "@/api/board";
import {useMemberStore} from "@/stores/member.js";
import {storeToRefs} from "pinia";

const {VITE_VUE_API_URL} = import.meta.env;
const store = useMemberStore();
const {boardNo, userInfo} = storeToRefs(store);
const route = useRoute();
const router = useRouter();
const articleno = route.params.articleno;

const article = ref({
    subject: "",
    content: "",
    userId: "",
    userName: "",
    hit: 0,
    registerTime: "",
    type: "",
});

onMounted(() => {
    getModifyArticle(
        articleno,
        ({data}) => {
            article.value = data;
        },
        (error) => {
            console.log(error);
        }
    );
});

const updateArticle = () => {
    modifyArticle(
        article.value,
        (response) => {
            let msg = "글수정 처리시 문제 발생했습니다.";
            if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
            alert(msg);
            router.push({name: "detail", params: {articleno: articleno}});
        },
        (error) => console.log(error)
    );
};
</script>

<template>
    <form method="post" action="#">
        <div class="row uniform">
            <div class="6u 12u$(xsmall)">
                <input
                    type="text"
                    name="demo-email"
                    id="demo-email"
                    placeholder="Id"
                    v-model="article.userId"
                    readonly />
            </div>
            <div class="6u$ 12u$(xsmall)">
                <input
                    type="text"
                    name="demo-name"
                    id="demo-name"
                    placeholder="Name"
                    v-model="article.userName"
                    readonly />
            </div>

            <!-- Break -->
            <template v-for="(value, index) in ['자유게시판', '여행지 추천 게시판', 'TATI게시판']" :key="value">
                <div class="3u 6u(small)">
                    <input type="radio" :id="value" name="demo-priority" :value="index + 1" v-model="article.type" />
                    <label :for="value">{{ value }}</label>
                </div>
            </template>

            <!-- Break -->
            <div class="12u$">
                <input type="text" name="demo-name" id="demo-name" placeholder="제목" v-model="article.subject" />
            </div>
            <!-- Break -->
            <div class="12u$">
                <textarea
                    name="demo-message"
                    id="demo-message"
                    placeholder="내용"
                    rows="6"
                    v-model="article.content"></textarea>
            </div>

            <!-- Break -->
            <template v-for="file in article.fileInfos" :key="file.saveFolder">
                <div class="3u">
                    <span class="image fit">
                        <img
                            :src="VITE_VUE_API_URL + '/file/' + file.saveFolder + '/' + file.originalFile"
                            alt="게시물" />
                    </span>
                </div>
            </template>

            <!-- Break -->
            <div class="12u$">
                <ul class="actions">
                    <li>
                        <span class="button" @click="updateArticle">Modify</span>
                    </li>
                    <li>
                        <span class="button special" @click="$router.push({name: 'list'})">List</span>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</template>

<style scoped></style>
