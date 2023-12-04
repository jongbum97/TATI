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
const { followings, followingCount, followers } = storeToRefs(store);
const route = useRoute();
const router = useRouter();
const userId = route.params.userid;
const thisUserFollower = ref([
	{
		userId: "",
		isFollowing: true,
		filepath: "",
	},
]);
const thisUserFollowerCount = ref(0);

const getThisUserFollower = async (id) => {
	await axios.get(url + "/user/followerlist/" + id).then((res) => {
		thisUserFollower.value = res.data;
		thisUserFollowerCount.value = res.data.length;
		if (thisUserFollower.value.length != 0) {
			thisUserFollower.value = thisUserFollower.value.map((followerId) => {
				var isFollowing = false;
				const filePath = ref("");
				const fileIdx = ref(0);
				getOtherUserProfile(followerId, fileIdx, filePath);
				if (followings.value.length != 0)
					isFollowing = followings.value.some((following) => following == followerId);
				return {
					userId: followerId,
					isFollowing,
					filepath: filePath,
				};
			});
		}
	});
};

const url = import.meta.env.VITE_VUE_API_URL;
onMounted(async () => {
	await getThisUserFollower(userId);
});
watch(
	() => route.params.userid,
	() => {
		getThisUserFollower(userId);
	}
);

watch(
	followings,
	() => {
		getThisUserFollower(userId);
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
	<h1 style="text-align: center">
		{{ route.params.userid }}님의 Follower : {{ thisUserFollowerCount }}
	</h1>

	<div id="grid">
		<slot-comp v-for="id in thisUserFollower">
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
