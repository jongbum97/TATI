import { ref, computed, watch } from "vue";
import { defineStore } from "pinia";
import {
	getAttraction,
	getDetail,
	getRank,
	like,
	likeList,
	likeCancel,
	getRandom,
	updateLike,
	updateLikeCancel,
} from "@/api/attraction";
import { httpStatusCode } from "@/util/http-status";

export const useAttractionStore = defineStore(
	"attraction",
	() => {
		const articles = ref([]);
		const attraction = ref({});
		const attractionLike = ref({});
		const attractionLikeList = ref([]);
		const attractionRank = ref([]);

		const getAttractions = async () => {
			await getAttraction(
				(response) => {
					if (response.status === httpStatusCode.OK) {
						articles.value = response.data;
					} else {
						console.error("관광지 로드 실패");
					}
				},
				(error) => {}
			);
		};

		const getAttractionDetail = async (id) => {
			await getDetail(
				id,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						attraction.value = response.data;
					} else {
						console.error("관광지 로드 실패");
					}
				},
				(error) => {}
			);
		};

		const getAttractionOtherDetail = async (id, otherAttraction) => {
			await getDetail(
				id,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						otherAttraction.value = response.data;
						// console.log("otherAttraction: ", otherAttraction.value, " ", response.data);
					} else {
						console.error("관광지 로드 실패");
					}
				},
				(error) => {}
			);
		};

		const getAttractionRank = async () => {
			await getRank(
				(response) => {
					if (response.status == httpStatusCode.OK) {
						attractionRank.value = response.data;
					} else {
						console.log("getRank 실패");
					}
				},
				(error) => {}
			);
		};

		const getRandomAttraction = async () => {
			await getRandom(
				(response) => {
					if (response.status == httpStatusCode.OK) {
						attraction.value = response.data;
					} else {
						console.log("getRank 실패");
					}
				},
				(error) => {}
			);
		};

		const likeAttraction = async (attractionLikeDto) => {
			await like(
				attractionLikeDto,
				(response) => {
					if (response.status == httpStatusCode.CREATE) {
						console.log("좋아요 성공");
					} else {
						console.log("좋아요 실패");
					}
				},
				(error) => {}
			);
		};

		const likeCancelAttraction = async (userId, contentId) => {
			await likeCancel(
				userId,
				contentId,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						console.log("좋아요취소 성공");
					} else {
						console.log("좋아요취소 실패");
					}
				},
				(error) => {}
			);
		};

		const updateAttractionLike = async (contentId) => {
			await updateLike(
				contentId,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						console.log("attraction mlevel: ", attraction.value.mlevel);
					} else {
						console.log("atraction mlevel 실패");
					}
				},
				(error) => {}
			);
		};
		const getLikeList = async (userId) => {
			await likeList(
				userId,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						attractionLikeList.value = response.data;
					} else {
						console.log("좋아요 리스트 실패");
					}
				},
				(error) => {}
			);
		};

		const updateAttractionLikeCancel = async (contentId) => {
			await updateLikeCancel(
				contentId,
				(response) => {
					if (response.status == httpStatusCode.OK) {
						console.log("attraction mlevel: ", attraction.value.mlevel);
					} else {
						console.log("atraction mlevel 실패");
					}
				},
				(error) => {}
			);
		};

		return {
			articles,
			attraction,
			attractionLike,
			attractionLikeList,
			getAttractions,
			getAttractionDetail,
			getAttractionRank,
			attractionRank,
			likeAttraction,
			likeCancelAttraction,
			getLikeList,
			getRandomAttraction,
			updateAttractionLike,
			updateAttractionLikeCancel,
			getAttractionOtherDetail,
		};
	},
	{
		persist: {
			storage: sessionStorage, //쓰고싶은 스토리지(세션 또는 로컬)
		},
	}
);
