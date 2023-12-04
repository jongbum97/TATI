<script setup>
import { onMounted, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useTripTestStore } from "@/stores/tripTest";
import { storeToRefs } from "pinia";

const store = useMemberStore();
const tripStore = useTripTestStore();
const router = useRouter();
const route = useRoute();
const { userModify, getProfileIdx, getUserInfo, userWithdrawal, getOtherUserInfo } = store;
const { userInfo } = storeToRefs(store);
const previewImages = ref([]);
const { VITE_VUE_API_URL } = import.meta.env;
const thisUserFollowerCount = ref(0);
const thisUserFollowingCount = ref(0);

const thisUserInfo = ref({
	//   userId: "",
	//   userName: "",
	//   emailId: "",
	//   emailDomain: "",
	//   joinDate: "",
	//   fileIdx: "",
	//   filePath: "",
	//   tati: "",
});

const getTati = async () => {
	if (thisUserInfo.value.tati == null) {
		thisUserInfo.value.tati = "X";
		return;
	}
	tripStore.resultSet.forEach((element) => {
		if (element.no == thisUserInfo.value.tati) {
			thisUserInfo.value.tati = element.type;
		}
	});
};

const userId = route.params.userid;

const attractionLikeList = () => {
	router.push({
		name: "attractionlikelist",
		params: { userid: userInfo.value.userId },
	});
};

onMounted(async () => {
	await getOtherUserInfo(userId, thisUserInfo, thisUserFollowerCount, thisUserFollowingCount);
	await getTati();
});
</script>

<template>
	<div>
		<section class="section">
			<div class="container mt-3">
				<div class="col col-lg-3 offset-lg-2">
					<h1 class="mx-3" style="display: flex; justify-content: center; margin: 30px 0">
						{{ route.params.userid }} 정보
					</h1>
				</div>
				<div class="table-wrapper">
					<!-- <div class="col-lg-3 text-center border border-dark mx-auto my-auto"></div> -->
					<div class="col-lg-9 text-center border border-dark mx-auto my-auto">
						<div class="12u" v-if="thisUserInfo.filePath">
							<span
								class="image fit"
								style="display: flex justify-content: center; align-self: center; margin: 0 auto; width: 200px;">
								<img
									:src="VITE_VUE_API_URL + thisUserInfo.filePath"
									style="border-radius: 50%"
									width="200"
									height="200" />
							</span>
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
									<td>{{ thisUserInfo.userId }}</td>
								</tr>
								<tr>
									<td>이름</td>
									<td>{{ thisUserInfo.userName }}</td>
									<!-- <td>{{ userInfo.userName }}</td> -->
								</tr>
								<tr>
									<td>이메일</td>
									<div style="display: flex">
										<td>
											{{ thisUserInfo.emailId }} @
											{{ thisUserInfo.emailDomain }}
										</td>
									</div>
								</tr>
								<tr>
									<td>가입일</td>
									<td>{{ thisUserInfo.joinDate }}</td>
								</tr>
								<tr>
									<td>tati</td>
									<td class="icon fa-tag">&nbsp; {{ thisUserInfo.tati }}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div
						class="row justify-content-center"
						style="display: flex; justify-content: center; margin: 30px 0">
						<div class="col col-lg-3 text-center">
							<button
								@click="
									$router.push({
										name: 'follower',
										params: { userid: thisUserInfo.userId },
									})
								">
								Follower : {{ thisUserFollowerCount }}
							</button>
						</div>
						<div class="col col-lg-3 text-center">
							<button
								@click="
									$router.push({
										name: 'following',
										params: { userid: thisUserInfo.userId },
									})
								">
								Following : {{ thisUserFollowingCount }}
							</button>
						</div>
						<div class="col col-lg-3 text-center">
							<button
								@click="
									$router.push({
										name: 'attractionlikelist',
										params: { userid: thisUserInfo.userId },
									})
								">
								좋아요한 여행지
							</button>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</template>

<style scoped></style>
