import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
	await local.post(`/user/login`, param).then(success).catch(fail);
	console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
	// local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
	await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function findOtherUserById(userid, success, fail) {
	// local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
	await local.get(`/user/otheruserinfo/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
	local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
	await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
	await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function regist(param, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.post(`/user/regist`, param).then(success).catch(fail);
}

async function passwordFind(userDto, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.post(`/user/findpassword`, userDto).then(success).catch(fail);
}

async function modify(param, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.put(`/user/modify`, param).then(success).catch(fail);
}

async function withdrawal(userId, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.delete(`/user/delete/${param}`).then(success).catch(fail);
}

async function profileIdx(userId, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.get(`/user/profile/${userId}`).then(success).catch(fail);
}

async function profileFilePath(fileIdx, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.get(`/user/profilefilepath/${fileIdx}`).then(success).catch(fail);
}

async function follower(userId, success, fail) {
	await local.get(`/user/followerlist/${userId}`).then(success).catch(fail);
}

async function following(userId, success, fail) {
	await local.get(`/user/followinglist/${userId}`).then(success).catch(fail);
}

export {
	userConfirm,
	findById,
	tokenRegeneration,
	logout,
	regist,
	modify,
	profileIdx,
	withdrawal,
	follower,
	following,
	findOtherUserById,
	profileFilePath,
	passwordFind,
};
