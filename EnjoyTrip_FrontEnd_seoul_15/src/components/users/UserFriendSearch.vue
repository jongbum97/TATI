<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import SlotComp from "../slot/SlotComp.vue";

import axios from "axios";
const store = useMemberStore();
const { VITE_VUE_API_URL } = import.meta.env;

const { followings } = storeToRefs(store);
const { getOtherUserProfile } = store;
const route = useRoute();
const router = useRouter();

const searched = ref([
	{
		userId: "",
		isFollowing: false,
		filepath: "",
	},
]);

const getUserList = async () => {
	await axios.get(url + "/user/list").then((res) => {
		searched.value = res.data;
		if (searched.value.length != 0) {
			searched.value = searched.value.map((searchRef) => {
				var isFollowing = false;
				const filePath = ref("");
				const fileIdx = ref(0);
				getOtherUserProfile(searchRef.userId, fileIdx, filePath);
				if (followings.value.length != 0)
					isFollowing = followings.value.some((following) => following == searchRef.userId);
				return {
					userId: searchRef.userId,
					isFollowing,
					filepath: filePath,
				};
			});
		}
	});
};

const getSearchedList = async (keyword) => {
	await axios.get(url + "/user/searchfriend/" + keyword).then((res) => {
		searched.value = res.data;
		if (searched.value.length != 0) {
			searched.value = searched.value.map((searchId) => {
				var isFollowing = false;
				const filePath = ref("");
				const fileIdx = ref(0);
				getOtherUserProfile(searchId, fileIdx, filePath);
				if (followings.value.length != 0)
					isFollowing = followings.value.some((following) => following == searchId);
				return {
					userId: searchId,
					isFollowing,
					filepath: filePath,
				};
			});
		}
	});
};

const searchCount = ref(0);
const url = import.meta.env.VITE_VUE_API_URL;
const searchedInit = async () => {
	if (!route.params.keyword) {
		await getUserList();
		return;
	}
	await getSearchedList(route.params.keyword);
};

watch(
	() => route.params.keyword,
	async () => {
		await searchedInit();
	},
	{ immediate: true }
);
watch(
	searched,
	() => {
		// searchedInit();
	},
	{
		immediate: true,
		deep: true,
	}
);

const deleteMember = async (id) => {
	//확인창
	if (window.confirm("정말로 삭제하시겠습니까?")) {
		await axios.delete(url + "/user/delete/" + id);
		searchedInit();
	}
};

const follow = async (id) => {
	const follow = {
		follower_id: store.userInfo.userId,
		following_id: id,
	};
	await axios.put(url + "/user/addfollow", follow);
	store.getFollowing(store.userInfo.userId);
	searched.value.forEach((searchId) => {
		if (searchId.userId == id) {
			searchId.isFollowing = true;
		}
	});
};

const followCancel = async (id) => {
	await axios.delete(url + "/user/deletefollow/" + store.userInfo.userId + "/" + id);
	store.getFollowing(store.userInfo.userId);
	searched.value.forEach((searchId) => {
		if (searchId.userId == id) {
			searchId.isFollowing = false;
		}
	});
};

onMounted(() => {
	searchedInit();
});
</script>

<template>
	<h3>친구 찾기</h3>
	<p>검색결과 : {{ searchCount }}</p>
	<div id="grid">
		<slot-comp v-for="id in searched" :key="id" :id="id">
			<div
				class="row 50% uniform"
				@click="$router.push({ name: 'userinfo', params: { userid: id.userId } })">
				<div v-if="id.filepath">
					<img
						:src="VITE_VUE_API_URL + id.filepath"
						style="border-radius: 50%"
						width="200"
						height="200" />
				</div>
				<div v-else>
					<img src="@/assets/images/profile.png" width="200" height="200" />
				</div>
			</div>
			<h2>
				{{ id.userId }}
			</h2>
			<div v-if="id.userId != store.userInfo.userId">
				<button
					v-if="store.userInfo.userId == 'admin'"
					style="font-size: medium"
					@click="deleteMember(id.userId)">
					회원 삭제
				</button>
				<div v-if="store.userInfo.userId != 'admin'">
					<button v-if="id.isFollowing" style="font-size: medium" @click="followCancel(id.userId)">
						팔로우 취소
					</button>
					<button v-if="!id.isFollowing" style="font-size: medium" @click="follow(id.userId)">
						팔로우
					</button>
				</div>
			</div>
		</slot-comp>
	</div>
</template>

<style scoped>
#grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 20px;
}
</style>
