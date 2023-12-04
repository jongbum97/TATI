<script setup>
import { ref, watch } from "vue";
import { useMemberStore } from "@/stores/member.js";
import { useTripTestStore } from "@/stores/tripTest";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useAttractionStore } from "@/stores/attractions";
const { VITE_VUE_API_URL } = import.meta.env;

const attstore = useAttractionStore();
const { articles } = storeToRefs(attstore);
const tripStore = useTripTestStore();
const router = useRouter();
const store = useMemberStore();
const { userInfo, followings } = storeToRefs(store);
const { userLogout } = store;

const logout = async () => {
	if (window.confirm("로그아웃 하시겠습니까?")) {
		await userLogout(userInfo.value.userId);
		sessionStorage.removeItem("accessToken");
		sessionStorage.removeItem("refreshToken");
		alert("로그아웃 되었습니다");
		router.push({ name: "main" });
	}
};

const git = (no) => {
	let url;
	if (no == 0) url = "https://github.com/JEGHTNER";
	else url = "https://github.com/jongbum97";
	window.open(url);
};

const searchKey = ref("");
const friendSearch = () => {
	const keyword = searchKey.value || ""; // searchKey.value가 없을 때는 공백으로 설정
	router.push({ name: "friend-search", params: { keyword } });
};

const myTati = ref("");

watch(
	userInfo,
	() => {
		if (userInfo.value.tati != null) {
			tripStore.resultSet.forEach((element) => {
				if (element.no == userInfo.value.tati) {
					myTati.value = element.type;
				}
			});
		}
	},
	{ immediate: true }
);
</script>

<template>
	<aside id="sidebar">
		<div class="inner">
			<!-- Search -->
			<section id="search" class="alt">
				<form method="post" action="" @submit.prevent>
					<input
						type="text"
						name="query"
						id="query"
						placeholder="친구 찾기"
						@keydown.enter="friendSearch"
						v-model="searchKey" />
				</form>
			</section>
			<!-- userinfo -->
			<section>
				<header class="major">
					<h2>내정보</h2>
				</header>
				<div class="row 50% uniform">
					<div class="5u" v-if="userInfo.filePath">
						<span class="image fit"
							><img
								:src="VITE_VUE_API_URL + userInfo.filePath"
								style="border-radius: 50%"
								width="150"
								height="150"
						/></span>
					</div>
					<div class="5u" v-else>
						<span class="image fit"
							><img src="@/assets/images/profile.png" width="150" height="150"
						/></span>
					</div>
					<div class="2u"></div>
					<div class="4u$" v-if="userInfo.userId">
						<ul class="actions" style="padding-top: 5px">
							<li>
								<button @click="logout" class="special">Logout&nbsp;</button>
							</li>
						</ul>
						<ul class="actions">
							<li>
								<button @click="$router.push({ name: 'mypage' })">MyPage</button>
							</li>
						</ul>
					</div>
					<div class="4u$" v-else>
						<ul class="actions" style="padding-top: 5px">
							<li>
								<button @click="$router.push({ name: 'login' })">Login&nbsp;</button>
							</li>
						</ul>
						<ul class="actions">
							<li>
								<button @click="$router.push({ name: 'regist' })" class="special">Regist</button>
							</li>
						</ul>
					</div>
				</div>
				<div>&nbsp;</div>
				<ul class="contact" v-if="userInfo.userId">
					<li></li>
					<li class="icon fa-hand-o-right">
						{{ store.userInfo.userName }} ({{ store.userInfo.userId }})
					</li>
					<li class="icon fa-heart">
						<router-link
							:to="{
								name: 'follower',
								params: { userid: store.userInfo.userId },
							}"
							>Follower : {{ store.followerCount }}</router-link
						>&nbsp;&nbsp;
						<router-link
							:to="{
								name: 'following',
								params: { userid: store.userInfo.userId },
							}"
							>Following : {{ store.followingCount }}</router-link
						>
					</li>
					<li class="fa-coffee">
						<div v-if="userInfo.tati">
							My TATI :&nbsp;
							<span class="icon fa-tag"></span>
							{{ myTati }}
						</div>
						<div v-else>
							My TATI :
							<router-link :to="{ name: 'test' }">검사하기!</router-link>
						</div>
					</li>
				</ul>
			</section>

			<!-- Menu -->
			<nav id="menu">
				<header class="major">
					<h2>메뉴</h2>
				</header>
				<ul>
					<li><RouterLink :to="{ name: 'main' }">홈</RouterLink></li>
					<li><RouterLink :to="{ name: 'test' }">TATI 검사하기</RouterLink></li>
					<li>
						<RouterLink :to="{ name: 'map-main', params: { no: '0' } }">관광지 검색</RouterLink>
					</li>
					<li>
						<RouterLink :to="{ name: 'map-rank' }">관광지 순위</RouterLink>
					</li>
					<li>
						<span class="opener">게시판</span>
						<ul>
							<li>
								<RouterLink
									:to="{ name: 'list' }"
									@click.native="
										() => {
											store.boardNo = 1;
										}
									"
									>자유게시판</RouterLink
								>
							</li>
							<li>
								<RouterLink
									:to="{ name: 'list' }"
									@click.native="
										() => {
											store.boardNo = 2;
										}
									"
									>여행지 추천 게시판</RouterLink
								>
							</li>
							<li>
								<RouterLink
									:to="{ name: 'list' }"
									@click.native="
										() => {
											store.boardNo = 3;
										}
									"
									>TATI 게시판</RouterLink
								>
							</li>
							<li>
								<RouterLink
									:to="{ name: 'list' }"
									@click.native="
										() => {
											store.boardNo = 4;
										}
									"
									>공지사항</RouterLink
								>
							</li>
							<!-- <li>
                                <RouterLink
                                    :to="{name: 'list'}"
                                    @click.native="
                                        () => {
                                            store.boardNo = 4;
                                        }
                                    "
                                    >Type D</RouterLink
                                >
                            </li> -->
						</ul>
					</li>
					<li><RouterLink :to="{ name: 'chat' }">실시간 채팅</RouterLink></li>
				</ul>
			</nav>

			<!-- Section -->
			<section>
				<header class="major">
					<h2>추천 관광지</h2>
				</header>
				<div class="mini-posts">
					<template v-for="(article, index) in articles" :key="article.content_id">
						<template v-if="index > 5">
							<article>
								<a
									class="image"
									@click="
										$router.push({
											name: 'map-detail',
											params: { id: article.content_id },
										})
									"
									><img :src="article.first_image" alt=""
								/></a>
								<h2>{{ article.title }}</h2>
								<p>{{ article.addr1 }}</p>
							</article>
						</template>
					</template>
				</div>
			</section>

			<!-- Section -->
			<section>
				<header class="major">
					<h2>제작자</h2>
				</header>
				<ul class="contact">
					<li class="icon fa-user">
						이제헌 / 컴퓨터공학 /
						<a class="icon fa-github" @click="git(0)">&nbsp;&nbsp;github.com/JEGHTNER</a>
					</li>
					<li class="fa-user">
						김종범 / 물리학과 /
						<a class="icon fa-github" @click="git(1)">&nbsp;&nbsp;github.com/jongbum97</a>
					</li>
					<li class="fa-credit-card-alt">카카오뱅크 / 3333093816830</li>
				</ul>
			</section>

			<!-- Footer -->
			<footer id="footer" style="font-size: xx-small">
				<p class="copyright">
					&copy; Untitled. All rights reserved. Demo Images:
					<a href="https://unsplash.com">Unsplash</a>. Design:
					<a href="https://html5up.net">HTML5 UP</a>.
				</p>
			</footer>
		</div>
	</aside>
</template>

<style scoped>
a,
span {
	font-family: "sideBar" !important;
	font-size: small !important;
}
</style>
