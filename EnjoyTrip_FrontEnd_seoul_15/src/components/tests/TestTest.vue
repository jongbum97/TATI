<script setup>
import {ref, watch, onMounted} from "vue";
import {useRouter, useRoute} from "vue-router";
import {useTripTestStore} from "@/stores/tripTest";
import {storeToRefs} from "pinia";

const router = useRouter();
const store = useTripTestStore();
const {startTest, endTest} = store;
const {typeA, typeB, typeC, resultType} = storeToRefs(store);
const cnt = ref(0);
const data = ref(typeA.value.question[0]);
const wait = ref(false);
const waitting = (time) => new Promise((resolve) => setTimeout(resolve, time));
const next = (n) => {
    if (cnt.value % 3 == 0) {
        typeA.value.count += n;
    } else if (cnt.value % 3 == 1) {
        typeB.value.count += n;
    } else if (cnt.value % 3 == 2) {
        typeC.value.count += n;
    }
    cnt.value++;
};
onMounted(() => {
    startTest();
});
watch(cnt, async () => {
    if (cnt.value == 9) {
        wait.value = true;
        endTest();
        await waitting(2000);
        wait.value = false;
        router.push({name: "test-result", params: {type: resultType.value}});
        return;
    }
    if (cnt.value % 3 == 0) {
        data.value = typeA.value.question[Math.floor(cnt.value / 3)];
    } else if (cnt.value % 3 == 1) {
        data.value = typeB.value.question[Math.floor(cnt.value / 3)];
    } else if (cnt.value % 3 == 2) {
        data.value = typeC.value.question[Math.floor(cnt.value / 3)];
    }
});
</script>

<template>
    <div v-if="wait" style="position: absolute; left: 40%">
        <img src="@/assets/images/loading.gif" />
    </div>
    <section style="text-align: center">
        <h2>자신의 생각과 더 비슷한 반응을 골라주세요!</h2>
        <div class="features row">
            <div class="12u$">
                <div class="box">
                    <h2><span class="icon fa-spinner"></span> &nbsp;{{ cnt }} / 9</h2>
                    <h2>
                        {{ data[0] }}
                    </h2>
                </div>
            </div>
            <hr class="12u$" />
            <div class="12u$">
                <ul class="actions">
                    <li>
                        <button class="big icon fa-hand-o-right" @click="next(0)">{{ data[1] }}</button>
                    </li>
                    <li>
                        <button class="big special icon fa-hand-o-right" @click="next(1)">{{ data[2] }}</button>
                    </li>
                </ul>
            </div>
        </div>
    </section>
</template>
<style scoped></style>
