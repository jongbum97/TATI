<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, registComment, deleteComment } from "@/api/board.js";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const { VITE_VUE_API_URL } = import.meta.env;

const route = useRoute();
const router = useRouter();
const article = ref({ comments: [] });
const articleno = route.params.articleno;
const store = useMemberStore();
const { userInfo } = storeToRefs(store);

onMounted(() => {
	getArticle();
});

const getArticle = () => {
	detailArticle(
		articleno,
		({ data }) => {
			article.value = data;
		},
		(error) => {
			console.log(error);
		}
	);
};

function onDeleteArticle() {
	if (window.confirm("정말 삭제 하시겠습니까?")) {
		deleteArticle(
			articleno,
			(response) => {
				if (response.status == 200) {
					alert("삭제되었습니다.");
					router.push({ name: "list" });
				}
			},
			(error) => {
				console.log(error);
			}
		);
	}
}

const comm = ref({
	article_no: articleno,
	user_id: userInfo.value.userId,
	user_name: userInfo.value.userName,
	comment: "",
});

const deleteC = async (idx) => {
	await deleteComment(
		idx,
		(response) => {
			if (response.status == 200) {
				alert("댓글이 삭제되었습니다.");
				getArticle();
			}
		},
		(error) => {}
	);
};
const writeC = async () => {
	await registComment(
		JSON.stringify(comm.value),
		(response) => {
			if (response.status == 200) {
				alert("댓글이 등록되었습니다.");
				getArticle();
				comm.value.comment = "";
			}
		},
		(error) => {}
	);
};
</script>

<template>
	<hr />
	<div class="">
		<div class="">
			<h1 class="">{{ article.articleNo }}. {{ article.subject }}</h1>
			<div style="margin-left: 70%; width: 30%">
				<div
					class="view"
					@click="$router.push({ name: 'userinfo', params: { userid: article.userId } })">
					작성자 : {{ article.userName }}
				</div>
				<div>작성 시간 : {{ article.registerTime }}</div>
				<div>조회수 : {{ article.hit }}</div>
				<div>댓글 : {{ article.comments.length }}</div>
			</div>
		</div>
		<hr />
		<div class="">
			<div class="row">
				<template v-for="(file, index) in article.fileInfos" :key="file.saveFolder">
					<div :class="index % 2 == 0 ? '6u' : '6u$'">
						<span class="image fit">
							<img
								:src="VITE_VUE_API_URL + '/file/' + file.saveFolder + '/' + file.originalFile"
								alt="게시물" />
						</span>
					</div>
				</template>
			</div>
			<div class="box">
				<p>&nbsp;</p>
				<p style="font-size: xx-large">{{ article.content }}</p>
				<p>&nbsp;</p>
			</div>
			<ul class="actions" style="text-align: right">
				<li v-if="article.userId == userInfo.userId">
					<button
						type="button"
						class=""
						@click="$router.push({ name: 'modify', params: { articleno: article.articleNo } })">
						Modify
					</button>
				</li>
				<li v-if="article.userId == userInfo.userId">
					<button type="button" class="special" @click="onDeleteArticle">Delete</button>
				</li>
				<li>
					<button type="button" class="" @click="$router.push({ name: 'list' })">List</button>
				</li>
			</ul>
			<div>&nbsp;</div>
			<div>&nbsp;</div>
			<div>&nbsp;</div>
			<hr />
			<h3>댓글</h3>
			<ul class="contact">
				<template v-for="comment in article.comments">
					<div class="box" id="comment">
						<li class="icon fa-comments-o">
							{{ comment.user_name }} : {{ comment.comment }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span
								style="color: red"
								class="view"
								v-if="comment.user_id == userInfo.userId"
								@click="deleteC(comment.idx)"
								>삭제</span
							>
						</li>
					</div>
				</template>
				<div class="row">
					<span>&nbsp;&nbsp;</span>
					<input
						type="text"
						@keydown.enter="writeC"
						style="width: 50%"
						v-model="comm.comment"
						placeholder="댓글을 써주세요~" />
					<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<input type="button" value="작성" @click="writeC" class="special" style="width: 10%" />
				</div>
			</ul>
		</div>
	</div>
</template>

<style scoped>
.view:hover {
	background-color: rgb(221, 217, 231);
	cursor: pointer;
}

#comment {
	padding: 10px;
	margin: 10px;
	width: 70%;
}
</style>
