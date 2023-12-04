import {localAxios} from "@/util/http-commons";

const local = localAxios();

async function getChat(success, fail) {
    await local.get(`/chat`).then(success).catch(fail);
}

async function addChat(param, success, fail) {
    await local.post(`/chat`, param).then(success).catch(fail);
}

export {getChat, addChat};
