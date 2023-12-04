<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo, getFollower, getFollowing } = memberStore;

const loginUser = ref({
	userId: "",
	userPwd: "",
});

const login = async () => {
	await userLogin(loginUser.value);
	let token = sessionStorage.getItem("accessToken");
	if (isLogin) {
		getUserInfo(token);
		getFollower(loginUser.value.userId);
		getFollowing(loginUser.value.userId);
	}
	router.push("/");
};
</script>

<template>
	<section class="content">
		<header>
			<h1>Login Page</h1>
			<h2></h2>
		</header>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<form method="post" action="#">
			<div class="row uniform">
				<div class="2u"></div>
				<div class="4u 12u$(xsmall)">
					<h3>ID</h3>
				</div>
				<div class="4u 12u$(xsmall)">
					<h3>Password</h3>
				</div>
				<div class="2u$"></div>
				<div class="2u"></div>
				<div class="4u 12u$(xsmall)">
					<input type="text" class="" v-model="loginUser.userId" placeholder=" ID" />
				</div>
				<div class="4u 12u$(xsmall)">
					<input
						type="password"
						class="form-control"
						v-model="loginUser.userPwd"
						@keyup.enter="login"
						placeholder=" Password" />
				</div>
				<div class="2u$"></div>
				<!-- Break -->
				<div class="2u"></div>
				<div class="10u$">
					<ul class="actions">
						<li>
							<button type="button" @click="login">Login</button>
						</li>
						<li>
							<button type="reset" class="special" @click="$router.push({ name: 'regist' })">
								Regist
							</button>
						</li>
						<li>
							<button type="button" class="special" @click="$router.push({ name: 'findpassword' })">
								비밀번호 찾기
							</button>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</section>
</template>

<style scoped></style>
