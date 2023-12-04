import {createRouter, createWebHistory} from "vue-router";
import MainView from "../views/MainView.vue";
import MapView from "../views/MapView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
            meta: {withoutLogin: true},
        },
        {
            path: "/chat",
            name: "chat",
            component: () => import("@/views/ChatView.vue"),
        },
        {
            path: "/map",
            name: "map",
            component: MapView,
            redirect: {name: "map-main"},
            children: [
                {
                    path: "main/:no",
                    name: "map-main",
                    component: () => import("@/components/maps/MapMain.vue"),
                },
                {
                    path: "detail/:id",
                    name: "map-detail",
                    component: () => import("@/components/maps/MapDetail.vue"),
                },
                {
                    path: "rank",
                    name: "map-rank",
                    component: () => import("@/components/maps/MapRank.vue"),
                },
            ],
        },
        {
            path: "/test",
            name: "test",
            redirect: {name: "test-main"},
            component: () => import("@/views/TestView.vue"),
            meta: {withoutLogin: true},
            children: [
                {
                    path: "main",
                    name: "test-main",
                    component: () => import("@/components/tests/TestMain.vue"),
                },
                {
                    path: "test",
                    name: "test-test",
                    component: () => import("@/components/tests/TestTest.vue"),
                },
                {
                    path: "result/:type",
                    name: "test-result",
                    component: () => import("@/components/tests/TestResult.vue"),
                },
            ],
        },
        {
            path: "/board",
            name: "board",
            // component: TheBoardView,
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import("../views/BoardView.vue"),
            redirect: {name: "list"},
            children: [
                {
                    path: "list",
                    name: "list",
                    component: () => import("@/components/boards/BoardList.vue"),
                },
                {
                    path: "detail/:articleno",
                    name: "detail",
                    component: () => import("@/components/boards/BoardDetail.vue"),
                },
                {
                    path: "write",
                    name: "write",
                    component: () => import("@/components/boards/BoardWrite.vue"),
                },
                {
                    path: "modify/:articleno",
                    name: "modify",
                    component: () => import("@/components/boards/BoardModify.vue"),
                },
            ],
        },
        {
            path: "/user",
            name: "user",
            component: () => import("../views/UserView.vue"),
            redirect: {name: "login"},
            children: [
                {
                    path: "login",
                    name: "login",
                    meta: {withoutLogin: true},
                    component: () => import("@/components/users/UserLogin.vue"),
                },
                {
                    path: "regist",
                    name: "regist",
                    meta: {withoutLogin: true},
                    component: () => import("@/components/users/UserRegist.vue"),
                },
                {
                    path: "findpassword",
                    name: "findpassword",
                    meta: {withoutLogin: true},
                    component: () => import("@/components/users/UserPasswordFind.vue"),
                },
                {
                    path: "mypage",
                    name: "mypage",
                    component: () => import("@/components/users/UserMypage.vue"),
                },
                {
                    path: "follower/:userid",
                    name: "follower",
                    component: () => import("@/components/users/UserFollower.vue"),
                },
                {
                    path: "following/:userid",
                    name: "following",
                    component: () => import("@/components/users/UserFollowing.vue"),
                },
                {
                    path: "userinfo/:userid",
                    name: "userinfo",
                    component: () => import("@/components/users/UserInfo.vue"),
                },
                {
                    path: "friends/:keyword?",
                    name: "friend-search",
                    component: () => import("@/components/users/UserFriendSearch.vue"),
                },
                {
                    path: "attractionlikelist/:userid",
                    name: "attractionlikelist",
                    component: () => import("@/components/users/UserAttractionLikeList.vue"),
                },
            ],
        },
    ],
});

// beforEach는 전역 네비게이션 가드로, 라우터를 통한 모든 이동 전에 호출됩니다.
// to에는 목적지의 정보, from에는 출발지의 정보가 담겨있으며,
// return false; 를 하면 이동이 취소됩니다.  by 종범
router.beforeEach(async (to, from) => {
    window.scrollTo(0, 0);
    if (!to.meta.withoutLogin) {
        let store = sessionStorage.getItem("memberStore");
        if (!store || !JSON.parse(store).isLogin) {
            alert("로그인 후 이용해 주세요");
            return {name: "login"};
        }
    }
});

export default router;
