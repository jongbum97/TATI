<script setup>
import {ref, onMounted, watch} from "vue";
import {useAttractionStore} from "@/stores/attractions";
import {storeToRefs} from "pinia";
import {useRoute} from "vue-router";
import altImage from "@/assets/images/TATI_logo.jpg";

const route = useRoute();
const attStore = useAttractionStore();
const {getAttractionRank} = attStore;
const {attractionRank} = storeToRefs(attStore);
onMounted(async () => {
    await getAttractionRank();
});
</script>

<template>
    <div class="row mb-5 justify-content-center">
        <div class="col-lg-6 text-center">
            <h1 class="section-title text-center mb-3 mt-5">관광지 순위</h1>
            <p>좋아요한 순위가 많은 순서대로 표시됩니다.</p>
        </div>
    </div>
    <div>&nbsp;</div>
    <div>&nbsp;</div>
    <div class="table-wrapper">
        <table class="table table-striped mt-3">
            <thead style="font-size: x-large">
                <tr style="text-align: center">
                    <th class="icon fa-graduation-cap">순위</th>
                    <th>대표이미지</th>
                    <th>관광지명</th>
                    <th>주소</th>
                    <th>추천수</th>
                </tr>
            </thead>
            <tbody>
                <tr
                    v-for="(att, index) in attractionRank"
                    :key="att.content_id"
                    class="trip-info view"
                    @click="$router.push({name: 'map-detail', params: {id: att.content_id}})">
                    <td>
                        <h2 class="">&nbsp; {{ index + 1 }}</h2>
                    </td>
                    <td><img :src="att.first_image || altImage" :width="100" /></td>
                    <td>{{ att.title }}</td>
                    <td>{{ att.addr1 }} {{ att.addr2 }}</td>
                    <td>
                        {{ att.mlevel }}
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>
.view:hover {
    background-color: rgb(228, 225, 235);
    cursor: pointer;
}
</style>
