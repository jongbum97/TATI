<script setup>
import {useAttractionStore} from "@/stores/attractions";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";

const store = useAttractionStore();
const {getAttractions} = store;

const {articles} = storeToRefs(store);
onMounted(async () => {
    await getAttractions();
});
</script>

<template>
    <section>
        <header class="major">
            <h2>추천 관광지</h2>
        </header>
        <div class="posts">
            <template v-for="(article, index) in articles" :key="article.content_id">
                <template v-if="index < 6">
                    <article>
                        <a
                            class="image"
                            @click="
                                $router.push({
                                    name: 'map-detail',
                                    params: {id: article.content_id},
                                })
                            "
                            ><img :src="article.first_image" alt=""
                        /></a>
                        <h3>{{ article.title }}</h3>
                        <p>
                            {{ article.addr1 + " " + article.addr2 }}
                        </p>
                        <ul class="actions">
                            <li>
                                <a
                                    @click="
                                        $router.push({
                                            name: 'map-detail',
                                            params: {id: article.content_id},
                                        })
                                    "
                                    class="button"
                                    >상세보기</a
                                >
                            </li>
                        </ul>
                    </article>
                </template>
            </template>

            <!-- <article>
                <a href="#" class="image"><img src="../../images/pic01.jpg" alt="" /></a>
                <h3>Interdum aenean</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article>
            <article>
                <a href="#" class="image"><img src="../../images/pic02.jpg" alt="" /></a>
                <h3>Nulla amet dolore</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article>
            <article>
                <a href="#" class="image"><img src="../../images/pic03.jpg" alt="" /></a>
                <h3>Tempus ullamcorper</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article>
            <article>
                <a href="#" class="image"><img src="../../images/pic04.jpg" alt="" /></a>
                <h3>Sed etiam facilis</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article>
            <article>
                <a href="#" class="image"><img src="../../images/pic05.jpg" alt="" /></a>
                <h3>Feugiat lorem aenean</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article>
            <article>
                <a href="#" class="image"><img src="../../images/pic06.jpg" alt="" /></a>
                <h3>Amet varius aliquam</h3>
                <p>
                    Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante
                    interdum. Sed nulla amet lorem feugiat tempus aliquam.
                </p>
                <ul class="actions">
                    <li><a href="#" class="button">More</a></li>
                </ul>
            </article> -->
        </div>
    </section>
</template>

<style scoped></style>
