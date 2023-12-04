<script setup>
import {computed} from "vue";

const props = defineProps({currentPage: Number, totalPage: Number});
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
    return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
});

const endPage = computed(() => {
    let lastPage = parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;
    return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
    return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
    const list = [];
    for (let i = start; i <= end; i++) list.push(i);
    return list;
    //   return Array(end - start + 1)
    //     .fill()
    //     .map((val, i) => start + i);
}

function onPageChange(pg) {
    emit("pageChange", pg);
}
</script>
<template>
    <div class="12u$ row" style="display: flex; justify-content: center; margin: 30px 0">
        <ul class="pagination">
            <li>
                <span class="button" @click="onPageChange(startPage == 1 ? 1 : startPage - 1)">Prev</span>
            </li>
            <template v-for="pg in range(startPage, endPage)" :key="pg">
                <li>
                    <a :class="currentPage === pg ? 'page active' : 'page'" @click="onPageChange(pg)">{{ pg }}</a>
                </li>
            </template>
            <li>
                <span class="button" @click="onPageChange(endRange ? totalPage : endPage + 1)">Next</span>
            </li>
        </ul>
    </div>
</template>

<style scoped>
a {
    cursor: pointer;
}
</style>
