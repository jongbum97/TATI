import axios from "axios";

const {VITE_VUE_API_URL, VITE_ELECTRIC_CHARGING_STATION_URL} = import.meta.env;

// local vue api axios instance
function localAxios() {
    // accessToken을 자동으로 넣어준다.
    const instance = axios.create({
        baseURL: VITE_VUE_API_URL,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });

    // instance를 사용하는 request 직전에 실행되는 inteceptors.
    // 토큰이 있다면 헤더에 넣어준다. 본요청 전에 확인용으로 전송되는 options method에는
    // header에 토큰이 없으므로, 백엔드 서버 인터셉터에서 따로 처리를 해줘야 한다.
    instance.interceptors.request.use(
        (request) => {
            let token = sessionStorage.getItem("accessToken");
            if (token) request.headers.Authorization = token;
            return request;
        },
        (error) => {
            console.log(error);
            return Promise.reject(error);
        }
    );

    // instance를 사용하는 response를 받은 직후에 실행되는 inteceptors.
    // httpStatus가 200대가 아니면 error처리 된다.
    instance.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            if (error.response && error.response.status == 401) {
                console.log(error);
                alert("허용되지 않은 접근 입니다.");
            }
            return Promise.reject(error);
        }
    );
    return instance;
}

// station vue api axios instance
function stationAxios() {
    const instance = axios.create({
        baseURL: VITE_ELECTRIC_CHARGING_STATION_URL,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });
    return instance;
}

export {localAxios, stationAxios};
