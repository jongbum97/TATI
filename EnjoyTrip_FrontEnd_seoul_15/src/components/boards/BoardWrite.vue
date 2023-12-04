<script setup>
import {ref} from "vue";
import {useRouter, useRoute} from "vue-router";
import {useMemberStore} from "@/stores/member";
import {storeToRefs} from "pinia";

const {VITE_VUE_API_URL} = import.meta.env;
const router = useRouter();
const store = useMemberStore();
const {boardNo, userInfo} = storeToRefs(store);

const article = ref({
    subject: "",
    content: "",
    userId: userInfo.value.userId,
    userName: userInfo.value.userName,
    type: boardNo.value,
    hit: 0,
    registerTime: "",
});

function writeArticle() {
    if (!article.value.subject || !article.value.content) {
        alert("빈칸없이 입력 해주세요");
        return;
    }
    var inp = document.getElementById("upfile");
    if (inp.files.length > 4) {
        alert("4장 이하만 첨부 가능합니다");
        return;
    }
    var data = new FormData();
    data.append("userId", article.value.userId);
    data.append("userName", article.value.userName);
    data.append("subject", article.value.subject);
    data.append("content", article.value.content);
    data.append("hit", article.value.hit);
    data.append("registerTime", article.value.registerTime);
    data.append("type", article.value.type);
    for (const file of inp.files) {
        data.append("files", file, file.name);
    }
    console.log(data.get("userId"));
    console.log(data.get("files"));

    fetch(VITE_VUE_API_URL + "/board", {
        method: "POST",
        headers: {Authorization: sessionStorage.getItem("accessToken")},
        body: data,
    })
        .then((response) => {
            let msg = "작성에 실패했습니다.";
            console.log(response);
            console.log(response.status);
            if (response.status == 201) msg = "게시물이 작성되었습니다.";
            alert(msg);
            router.push({name: "list"});
        })
        .catch((error) => console.log(error));
}

const previewImages = ref([]);
const upload = (event) => {
    previewImages.value = [];
    if (event.target.files.length > 4) {
        alert("사진은 최대 4개 까지 첨부가능합니다.");
    } else {
        //file째로(DTO-X 뷰에 찍히는 정보대로) 저장
        // files.value.fileInfos = event.target.files;
        for (const file of event.target.files) {
            //프리뷰
            const reader = new FileReader();
            reader.onload = (e) => {
                previewImages.value.push(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    }
};
</script>

<template>
    <form method="post" enctype="multipart/form-data">
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
            <div class="3u 6u(small)" v-if="userInfo.userId == 'admin'">
                <input type="radio" id="공지사항" name="demo-priority" value="4" v-model="article.type" />
                <label for="공지사항">공지사항</label>
            </div>

            <!-- Break -->
            <div class="12u$">
                <input type="hidden" name="articleNo" id="no" value="" />
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
            <div class="12u$">
                <input type="file" id="upfile" multiple="multiple" accept="image/*, .gif" @change="upload" />
            </div>

            <!-- 사진미리보기 -->
            <div class="3u" v-for="fi in previewImages" :key="fi.saveFolder">
                <span class="image fit">
                    <img :src="fi" alt="게시물" />
                </span>
            </div>

            <!-- Break -->
            <div class="12u$">
                <ul class="actions">
                    <li>
                        <span class="button" @click="writeArticle">Write</span>
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
