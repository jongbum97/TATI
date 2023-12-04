<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useTripTestStore } from "@/stores/tripTest";
import { storeToRefs } from "pinia";

const store = useMemberStore();
const tripStore = useTripTestStore();
const router = useRouter();
const { userModify, getProfileIdx, getUserInfo, userWithdrawal } = store;
const { userInfo } = storeToRefs(store);
const previewImages = ref([]);
const { VITE_VUE_API_URL } = import.meta.env;
const passwordCheck = ref("");
const myTati = ref("");
const user = ref({
	userId: userInfo.value.userId,
	userName: userInfo.value.userName,
	userPwd: "",
	emailId: userInfo.value.emailId,
	emailDomain: userInfo.value.emailDomain,
	fileIdx: userInfo.value.fileIdx,
});

const getTati = async () => {
	if (userInfo.value.tati == null) return;
	tripStore.resultSet.forEach((element) => {
		if (element.no == userInfo.value.tati) {
			myTati.value = element.type;
		}
	});
};
getTati();
const upload = (event) => {
	if (event.target.files.length > 1) {
		alert("사진은 최대 1개 까지 첨부가능합니다.");
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
	// 사용자가 올린 이미지
	// URL.createObjectURL로 사용자가 올린 이미지를 URL로 만들어서 화면에 표시할 수 있게 한다. img 태그의 src값에 바인딩해준다
	//   this.imageUploaded = URL.createObjectURL(this.image)
};
const modify = async () => {
	var inp = document.getElementById("upfile");
	if (inp.files.length > 1) {
		alert("1장만 첨부 가능합니다");
		return;
	}
	var data = new FormData();
	data.append("userId", user.value.userId);
	data.append("userName", user.value.userName);
	data.append("userPwd", user.value.userPwd);
	data.append("emailId", user.value.emailId);
	data.append("emailDomain", user.value.emailDomain);
	for (const file of inp.files) {
		await getProfileIdx(user.value.userId);
		user.value.fileIdx = userInfo.value.fileIdx;
		data.append("fileIdx", user.value.fileIdx);

		data.append("files", file, file.name);
	}

	if (user.userId || user.userName || user.userPwd || user.emailId || user.emailDomain) {
		alert("빈칸없이 입력 해주세요");
	} else if (user.value.userPwd != passwordCheck.value || user.value.userPwd == "") {
		alert("비밀번호를 체크해 주세요");
	} else {
		// await userModify(user.value);
		fetch(VITE_VUE_API_URL + "/user/modify", {
			method: "PUT",
			body: data,
		})
			.then((response) => {
				let msg = "정보수정에 실패했습니다.";
				if (response.status == 201) msg = "정보수정이 완료되었습니다.";
				alert(msg);
				let token = sessionStorage.getItem("accessToken");
				getUserInfo(token);
				router.push({ name: "main" });
			})
			.catch((error) => console.log(error));
	}
};

const withdrawal = async () => {
	if (window.confirm("정말 탈퇴하시겠습니까?")) {
		await userWithdrawal(user.value.userId);
	}
};

const attractionLikeList = () => {
	router.push({
		name: "attractionlikelist",
		params: { userid: userInfo.value.userId },
	});
};
</script>

<template>
	<div>
		<section class="section">
			<div class="container mt-3">
				<div class="col col-lg-3 offset-lg-2">
					<h1 class="mx-3" style="display: flex; justify-content: center; margin: 30px 0">
						내 정보
					</h1>
				</div>
				<div class="table-wrapper">
					<!-- <div class="col-lg-3 text-center border border-dark mx-auto my-auto"></div> -->
					<div class="col-lg-9 text-center border border-dark mx-auto my-auto">
						<div class="12u" v-if="userInfo.filePath">
							<span
								class="image fit"
								style="display: flex justify-content: center; align-self: center; margin: 0 auto; width: 200px;">
								<img
									:src="VITE_VUE_API_URL + userInfo.filePath"
									style="border-radius: 50%"
									width="200"
									height="200" />
								<div class="12u$">
									<input
										type="file"
										id="upfile"
										multiple="multiple"
										accept="image/*, .gif"
										@change="upload" />
								</div>
								<img
									class="m-3 col-auto"
									v-for="(previewImage, index) in previewImages"
									:key="index"
									:src="previewImage"
									style="height: 200px; width: 300px; object-fit: cover"
							/></span>
						</div>
						<div class="12u" v-else>
							<span
								class="image fit"
								style="display: flex justify-content: center; align-self: center; margin: 0 auto; width: 200px;"
								><img src="@/assets/images/profile.png" width="200" height="200" />
								<div class="12u$">
									<input
										type="file"
										id="upfile"
										multiple="multiple"
										accept="image/*, .gif"
										@change="upload" />
								</div>
								<img
									class="m-3 col-auto"
									v-for="(previewImage, index) in previewImages"
									:key="index"
									:src="previewImage"
									style="height: 200px; width: 300px; object-fit: cover"
							/></span>
						</div>

						<table class="table">
							<tbody>
								<tr>
									<td>아이디</td>
									<td>{{ user.userId }}</td>
								</tr>
								<tr>
									<td>이름</td>
									<input type="text" name="userName" v-model="user.userName" style="width: 300px" />
									<!-- <td>{{ userInfo.userName }}</td> -->
								</tr>
								<tr>
									<td>비밀번호</td>
									<input
										type="password"
										name="userPwd"
										v-model="user.userPwd"
										style="width: 300px" />
								</tr>
								<tr>
									<td>비밀번호 확인</td>
									<input
										type="password"
										name="userPwdCheck"
										v-model="passwordCheck"
										style="width: 300px" />
								</tr>
								<tr>
									<td>이메일</td>
									<div style="display: flex">
										<input
											type="text"
											name="userEmail"
											v-model="user.emailId"
											style="width: 300px" />
										<td>@</td>
										<select
											style="width: 200px !important; margin-left: 10px"
											name="demo-category"
											id="demo-category"
											v-model="user.emailDomain">
											<option selected>{{ user.emailDomain }}</option>
											<option value="ssafy.com">싸피</option>
											<option value="google.com">구글</option>
											<option value="naver.com">네이버</option>
											<option value="kakao.com">카카오</option>
										</select>
									</div>
								</tr>
								<tr>
									<td>가입일</td>
									<td>{{ userInfo.joinDate }}</td>
								</tr>
								<tr>
									<td>tati</td>
									<div class="12u" v-if="userInfo.tati">
										<td class="icon fa-tag">&nbsp; {{ myTati }}</td>
									</div>
									<div class="12u" v-else>
										<td>
											<button @click="$router.push({ name: 'test' })">검사하기</button>
										</td>
									</div>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="row justify-content-center" style="display: flex; justify-content: center">
						<div class="col col-lg-3 text-center">
							<div class="6u">
								<button @click="modify">회원정보수정</button>
							</div>
						</div>
						<div class="col col-lg-3 text-center">
							<div class="6u">
								<button @click="withdrawal">회원탈퇴</button>
							</div>
						</div>
						<div class="col col-lg-3 text-center">
							<button @click="attractionLikeList">좋아요한 여행지</button>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</template>

<style scoped></style>
