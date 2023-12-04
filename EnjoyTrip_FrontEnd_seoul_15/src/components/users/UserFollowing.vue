<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import SlotComp from "../slot/SlotComp.vue";

import axios from "axios";
const store = useMemberStore();
const { VITE_VUE_API_URL } = import.meta.env;
const { getOtherUserProfile } = store;
const { followings, followingCount } = storeToRefs(store);
const route = useRoute();
const router = useRouter();
const userId = route.params.userid;
const thisUserFollowing = ref([
	{
		userId: "",
		isFollowing: true,
		filepath: "",
	},
]);
const thisUserFollowingCount = ref(0);

const getThisUserFollowing = async (id) => {
	await axios.get(url + "/user/followinglist/" + id).then((res) => {
		thisUserFollowing.value = res.data;
		thisUserFollowingCount.value = res.data.length;
		if (thisUserFollowing.value.length != 0) {
			thisUserFollowing.value = thisUserFollowing.value.map((followingId) => {
				var isFollowing = false;
				const filePath = ref("");
				const fileIdx = ref(0);
				getOtherUserProfile(followingId, fileIdx, filePath);
				if (followings.value.length != 0)
					isFollowing = followings.value.some((following) => following == followingId);
				return {
					userId: followingId,
					isFollowing,
					filepath: filePath,
				};
			});
		}
	});
};

const url = import.meta.env.VITE_VUE_API_URL;
onMounted(() => {
	getThisUserFollowing(userId);
});
watch(
	() => route.params.userid,
	() => {
		getThisUserFollowing(userId);
	}
);

watch(
	followings,
	() => {
		getThisUserFollowing(userId);
	},
	{
		immediate: true,
		deep: true,
	}
);

const follow = async (id) => {
	const follow = {
		follower_id: store.userInfo.userId,
		following_id: id.userId,
	};
	await axios.put(url + "/user/addfollow", follow);
	store.getFollowing(store.userInfo.userId);
};

const followCancel = async (id) => {
	await axios.delete(url + "/user/deletefollow/" + store.userInfo.userId + "/" + id.userId);
	store.getFollowing(store.userInfo.userId);
};
</script>

<template>
	<h1 style="text-align: center">{{ userId }}님의 Following : {{ thisUserFollowingCount }}</h1>
	<div id="grid">
		<slot-comp v-for="id in thisUserFollowing">
			<div
				class="row 50% uniform"
				style="align-content: center"
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
				<!-- {{ id }} -->
				{{ id.userId }}
			</h2>
			<div v-if="id.userId != store.userInfo.userId">
				<button v-if="id.isFollowing" style="font-size: small" @click="followCancel(id)">
					팔로우 취소
				</button>
				<button v-else style="font-size: small" @click="follow(id)">팔로우</button>
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
