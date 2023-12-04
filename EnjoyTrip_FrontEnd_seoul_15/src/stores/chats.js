import {ref, watch, computed} from "vue";
import {getChat, addChat} from "@/api/chat";
import {httpStatusCode} from "@/util/http-status";
import {defineStore} from "pinia";

export const useChatStore = defineStore(
    "chatStore",
    () => {
        const chats = ref([]);
        const size = ref(0);

        const getChats = async (down) => {
            await getChat(
                (response) => {
                    if (response.status === httpStatusCode.OK) {
                        chats.value = response.data;
                    } else {
                        console.log("채팅 가져오기 실패");
                    }
                },
                (error) => {}
            );
            if (chats.value.length != size.value) {
                down();
                console.log("down호출");
            }
            size.value = chats.value.length;
        };

        const addchats = async (chatDto) => {
            await addChat(
                JSON.stringify(chatDto),
                (response) => {
                    if (response.status === httpStatusCode.OK) {
                    } else {
                        console.log("채팅전송실패");
                    }
                },
                (error) => {}
            );
        };

        return {chats, getChats, addchats};
    },
    {
        persist: {
            storage: sessionStorage, //쓰고싶은 스토리지(세션 또는 로컬)
        },
    }
);
