<script setup>
import {ref} from "vue";
import {storeToRefs} from "pinia";
import {useRouter} from "vue-router";
import {useMemberStore} from "@/stores/member";

const router = useRouter();
const store = useMemberStore();
const {userRegist} = store;
const user = ref({
    userId: "",
    userName: "",
    userPwd: "",
    emailId: "",
    emailDomain: "",
});
const passwordCheck = ref("");

const regist = async () => {
    if (user.userId || user.userName || user.userPwd || user.emailId || user.emailDomain) {
        alert("빈칸없이 입력 해주세요");
    } else if (user.value.userPwd != passwordCheck.value) {
        alert("비밀번호를 체크해 주세요");
    } else {
        await userRegist(user.value);
        router.push({name: "login"});
    }
};
</script>

<template>
    <section class="content">
        <header>
            <h1>Regist Page</h1>
            <h2></h2>
        </header>
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <form method="post" action="#">
            <div class="row uniform">
                <!-- Break -->
                <div class="2u"></div>
                <div class="4u 12u$(xsmall)">
                    <input type="text" class="form-control" placeholder="아이디" v-model="user.userId" />
                </div>
                <div class="4u 12u$(xsmall)">
                    <input type="text" class="form-control" placeholder="이름" v-model="user.userName" />
                </div>
                <div class="2u$"></div>

                <!-- Break -->
                <div class="2u"></div>
                <div class="4u 12u$(xsmall)">
                    <input type="password" class="form-control" placeholder="비밀번호" v-model="user.userPwd" />
                </div>
                <div class="4u 12u$(xsmall)">
                    <input
                        type="password"
                        class="form-control"
                        id="pwdcheck"
                        placeholder="비밀번호확인"
                        v-model="passwordCheck" />
                </div>
                <div class="2u$"></div>

                <!-- Break -->
                <div class="2u"></div>
                <div class="4u 12u$(xsmall)">
                    <input type="text" class="form-control" placeholder="이메일아이디" v-model="user.emailId" />
                </div>
                <span class="1u">@</span>
                <div class="3u 12u$(xsmall)">
                    <select name="demo-category" id="demo-category" v-model="user.emailDomain">
                        <option selected>도메인 선택</option>
                        <option value="ssafy.com">싸피</option>
                        <option value="google.com">구글</option>
                        <option value="naver.com">네이버</option>
                        <option value="kakao.com">카카오</option>
                    </select>
                </div>
                <div class="2u$"></div>

                <!-- Break -->
                <div class="2u"></div>
                <div class="10u$">
                    <ul class="actions">
                        <li>
                            <button type="button" @click="regist">Regist</button>
                        </li>
                        <li><button type="reset" class="special">Reset</button></li>
                    </ul>
                </div>
            </div>
        </form>
    </section>
</template>

<style scoped></style>
