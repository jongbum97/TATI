import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function getAttraction(success, fail) {
	await local.get(`/atrraction`).then(success).catch(fail);
}

async function getDetail(param, success, fail) {
	await local.get(`/atrraction/detail/${param}`).then(success).catch(fail);
}

async function getRank(success, fail) {
	await local.get(`/atrraction/rank`).then(success).catch(fail);
}

async function getRandom(success, fail) {
	await local.get(`/atrraction/random`).then(success).catch(fail);
}
async function like(attractionLikeDto, success, fail) {
	await local.post(`/atrraction/like`, JSON.stringify(attractionLikeDto)).then(success).catch(fail);
}

async function likeCancel(userId, contentId, success, fail) {
	await local.delete(`/atrraction/likeCancel/${userId}/${contentId}`).then(success).catch(fail);
}

async function likeList(userId, success, fail) {
	await local.get(`/atrraction/like/${userId}`).then(success).catch(fail);
}

async function updateLike(contentId, success, fail) {
	await local.put(`/atrraction/like/updatelike/${contentId}`).then(success).catch(fail);
}

async function updateLikeCancel(contentId, success, fail) {
	await local.put(`/atrraction/like/updatelikecancel/${contentId}`).then(success).catch(fail);
}

export {
	getAttraction,
	getDetail,
	getRank,
	like,
	likeList,
	likeCancel,
	getRandom,
	updateLike,
	updateLikeCancel,
};
