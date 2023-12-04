import {createApp} from "vue";
import {createPinia} from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import "@/assets/css/main.css";

import App from "./App.vue";
import router from "./router";
const pinia = createPinia();
const app = createApp(App);

pinia.use(piniaPluginPersistedstate);
app.use(router);
app.use(pinia);

app.mount("#app");
