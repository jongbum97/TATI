<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useTripTestStore } from "@/stores/tripTest";
import { storeToRefs } from "pinia";
import { likeList } from "@/api/attraction";
import { useAttractionStore } from "@/stores/attractions";
import altImage from "@/assets/images/TATI_logo.jpg";

const store = useMemberStore();
const tripStore = useTripTestStore();
const attStore = useAttractionStore();
const router = useRouter();
const route = useRoute();

const { userInfo } = storeToRefs(store);
const { attractionLikeList } = storeToRefs(attStore);
const { VITE_VUE_API_URL } = import.meta.env;

console.log("UserAttractionLikeList.vue");
console.log("attractilnLikeLIst: ", attractionLikeList.value);
const thisUsersAttractionLikeList = ref([]);
const temp = ref([]);
const listLength = ref(0);

const thisUserGetLikeList = async (userId) => {
  console.log("thisUserGetLikeList");
  await likeList(
    userId,
    (response) => {
      console.log("response: ", response);
      if (response.status == "200") {
        temp.value = response.data;
        console.log("temp.value: ", temp.value);
        console.log("좋아요 리스트 성공");
      } else {
        console.log("좋아요 리스트 실패");
      }
    },
    (error) => {}
  );
};

const getThisUsersAttractionLikeList = async (id) => {
  await thisUserGetLikeList(id);
  const attractionDetailsPromises = temp.value.map(async (contentId) => {
    const otherAttraction = ref({});
    await attStore.getAttractionOtherDetail(contentId, otherAttraction);
    console.log("attraction: ", otherAttraction.value);
    return otherAttraction.value;
  });
  console.log("attractionDetailsPromises: ", attractionDetailsPromises);
  const attractionDetails = await Promise.all(attractionDetailsPromises);

  thisUsersAttractionLikeList.value = attractionDetails;
  // console.log(thisUsersAttractionLikeList.value);
  listLength.value = thisUsersAttractionLikeList.value.length;
};
getThisUsersAttractionLikeList(route.params.userid);
</script>

<template>
  <header class="main">
    <p>&nbsp;</p>
    <h1 class="icon fa-lightbulb-o" style="text-align: center">
      &nbsp; {{ route.params.userid }}의 좋아요 리스트 : {{ listLength }}
    </h1>
    <p>&nbsp;</p>
  </header>

  <div class="row" style="overflow: scroll; width: auto; height: 1000px">
    <table class="table table-striped mt-3">
      <thead style="font-size: large">
        <tr style="text-align: center">
          <th>대표이미지</th>
          <th>관광지명</th>
          <th>주소</th>
          <th>좋아요</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(area, index) in thisUsersAttractionLikeList"
          :key="area.content_id"
          class="trip-info view"
          @click="
            $router.push({
              name: 'map-detail',
              params: { id: area.content_id },
            })
          "
        >
          <td><img :src="area.first_image || altImage" :width="100" /></td>
          <td>{{ area.title }}</td>
          <td>{{ area.addr1 }} {{ area.addr2 }}</td>
          <td>{{ area.mlevel }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.view:hover {
  background-color: rgb(221, 217, 231);
  cursor: pointer;
}
</style>
