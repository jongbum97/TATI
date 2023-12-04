<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute } from 'vue-router';
import { useTripTestStore } from '@/stores/tripTest';
import { useMemberStore} from '@/stores/member'
import { storeToRefs } from "pinia";
const { VITE_VUE_API_URL } = import.meta.env;

const route = useRoute();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const {getUserInfo} = memberStore;
let url = location.href;
const state = ref(false);

const store = useTripTestStore();
const { resultSet } = store;
const data = ref('');

resultSet.forEach(ele => {
    if (ele.no == route.params.type) {
        data.value = ele;
    }
});

const save = async () => {
    if (userInfo.value.userId == '') {
        alert('로그인 후 이용해 주세요')
    } else {
        var fom = new FormData();
        fom.append('userId', userInfo.value.userId);
        fom.append('tati', route.params.type);
        fetch(VITE_VUE_API_URL + "/user/modify", {
            method: "PUT",
            body: fom,
        })
            .then((response) => {
                let msg = "수정에 실패했습니다.";
                console.log(response);
                console.log(response.status);
                if (response.status == 201) {
                    msg = "수정 되었습니다.";
                    let token = sessionStorage.getItem("accessToken");
        getUserInfo(token);
                }
                alert(msg);
            })
            .catch((error) => console.log(error));
    }
}

const copy = () => {
    window.navigator.clipboard.writeText(url);
    alert('복사되었습니다');
}

console.log(route.params.type);
</script>

<template>
    <section id="banner">
            <div class="content">
                <header>
                    <h1 class="icon fa-tag">
                        &nbsp;{{ data.type }}<br />
                    </h1>
                    <h3>{{ data.tag }}</h3>
                </header>
                <hr>
                <p>
                    {{ data.explain }}
                </p>
                <hr>
                <p class="row" style="text-align: center;">
                    <div class="6u icon fa-thumbs-up">
                        잘어울리는 음식 <br>
                            {{ data.good }}
                    </div>
                    <div class="6u$ icon fa-thumbs-down">
                        안어울리는 음식 <br>
                            {{ data.bad }}
                    </div>
                </p>
                <hr>
                <ul class="actions row">
                    <li class="12u$">&nbsp;</li>
                    <template v-if="userInfo.userId != ''">
                        <li class="6u"><a class="button special" @click="save">TATI 저장</a></li>
                        <li class="6u$"><a class="button" @click="$router.push({name:'map-detail',params:{id:2384136}})">추천 여행지</a></li>
                    </template>
                    <li class="12u$">&nbsp;</li>
                    <li class="6u"><a class="button" @click="()=>{state = !state}">공유하기</a></li>
                    <li class="6u$"><a class="button special" @click="$router.push({name:'test-main'})">다시 검사하기</a></li>
                    <li class="12u$">&nbsp;</li>
                    <div class="12u$ box" v-if="state">{{ url }} &nbsp;&nbsp;&nbsp;<span id="coco" class="icon fa-clone" @click="copy">&nbsp;</span></div>
                </ul>
            </div>
            <span class="image object">
                <img src="../../assets/images/111.png" alt="" v-if="data.no =='111'">
                <img src="../../assets/images/112.png" alt="" v-if="data.no == '112'">
                <img src="../../assets/images/121.png" alt="" v-if="data.no == '121'">
                <img src="../../assets/images/122.jpeg" alt="" v-if="data.no == '122'">
                <img src="../../assets/images/211.png" alt="" v-if="data.no == '211'">
                <img src="../../assets/images/212.jpeg" alt="" v-if="data.no == '212'">
                <img src="../../assets/images/221.jpeg" alt="" v-if="data.no == '221'">
                <img src="../../assets/images/222.jpeg" alt="" v-if="data.no == '222'">
            </span>
        </section>
</template>

<style scoped>
#coco:hover{
    color: red;
    cursor: pointer;
}
</style>