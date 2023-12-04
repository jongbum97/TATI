<script setup>
import {useChatStore} from "@/stores/chats";
import {useMemberStore} from "@/stores/member";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";

const store = useChatStore();
const memberStore = useMemberStore();
const {chats} = storeToRefs(store);
const contents = ref({
    id: memberStore.userInfo.userId,
    name: memberStore.userInfo.userName,
    content: "",
});

const send = async () => {
    await store.addchats(contents.value);
    contents.value.content = "";
};

const down = () => {
    document.getElementById("scrollBox").scrollTo(0, 999999);
};

onMounted(() => {
    setInterval(async () => {
        await store.getChats(down);
    }, 1500);
});
</script>

<template>
    <section class="content">
        <header class="main">
            <h1 class="icon fa-microphone">&nbsp; 실시간 채팅</h1>
            <p>사용자들과 정보를 교환해보세요</p>
        </header>
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div class="row">
            <div class="2u">&nbsp;</div>
            <div class="box 8u" style="height: 500px; overflow: auto" id="scrollBox">
                <ul class="contact">
                    <template v-for="chat in chats">
                        <li class="" v-if="chat.id == memberStore.userInfo.userId" style="text-align: right">
                            {{ chat.content }}&nbsp;&nbsp;&nbsp;
                            <span class="icon fa-comment">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </li>
                        <li class="icon fa-comment" v-if="chat.id != memberStore.userInfo.userId">
                            {{ chat.name }} : {{ chat.content }}
                        </li>
                    </template>
                </ul>
            </div>
            <div class="2u$">&nbsp;</div>
        </div>
        <div class="row">
            <div class="2u">&nbsp;</div>
            <div class="8u row">
                <span>&nbsp;</span>
                <input type="text" v-model="contents.content" @keydown.enter="send" style="width: 70%" />
                <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="button" value="전송" @click="send" class="special" style="width: 15%" />
            </div>
            <div class="2u$">&nbsp;</div>
        </div>
    </section>
</template>

<style scoped></style>
