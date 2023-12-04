import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
	userConfirm,
	findById,
	tokenRegeneration,
	logout,
	regist,
	//   modify,
	profileIdx,
	profileFilePath,
	withdrawal,
	follower,
	following,
	findOtherUserById,
	passwordFind,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore(
	"memberStore",
	() => {
		const router = useRouter();

		const isLogin = ref(false);
		const boardNo = ref(0);
		const isLoginError = ref(false);
		const userInfo = ref({
			userId: "",
			userName: "",
		});
		const followers = ref([]);
		const followings = ref([]);
		const followerCount = ref(0);
		const followingCount = ref(0);

		const isValidToken = ref(false);

		const userLogin = async (loginUser) => {
			await userConfirm(
				loginUser,
				(response) => {
					if (response.status === httpStatusCode.CREATE) {
						let { data } = response;
						let accessToken = data["access-token"];
						let refreshToken = data["refresh-token"];
						isLogin.value = true;
						isLoginError.value = false;
						isValidToken.value = true;
						sessionStorage.setItem("accessToken", accessToken);
						sessionStorage.setItem("refreshToken", refreshToken);
					} else {
						console.log("로그인 실패했다");
						isLogin.value = false;
						isLoginError.value = true;
						isValidToken.value = false;
					}
				},
				(error) => {
					alert("아이디와 비밀번호를 확인 해 주세요");
					console.error(error);
				}
			);
		};

		const getUserInfo = (token) => {
			let decodeToken = jwtDecode(token);
			findById(
				decodeToken.userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						userInfo.value = response.data.userInfo;
					} else {
						console.log("유저 정보 없음!!!!");
					}
				},
				async (error) => {
					console.error(
						"getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
						error.response.status
					);
					isValidToken.value = false;

					await tokenRegenerate();
				}
			);
		};

		const getOtherUserInfo = async (
			userId,
			thisUserInfo,
			thisUserFollowerCount,
			thisUserFollowingCount
		) => {
			await findOtherUserById(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						thisUserInfo.value = response.data.userInfo;
						// console.log("thisUserInfo : ", thisUserInfo.value);
					} else {
						console.log("유저 정보 없음!!!!");
					}
				},
				async (error) => {
					console.error("getOtherUserInfo error::: ", error.response.status);
				}
			);
			await follower(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						thisUserFollowerCount.value = response.data.length;
					} else {
						console.log("팔로워 리스트 실패", response.status);
					}
				},
				async (error) => {
					console.log(error);
				}
			);
			await following(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						thisUserFollowingCount.value = response.data.length;
					} else {
						console.log("팔로잉 리스트 실패", response.status);
					}
				},
				async (error) => {
					console.log(error);
				}
			);
		};

		const tokenRegenerate = async () => {
			await tokenRegeneration(
				JSON.stringify(userInfo.value),
				(response) => {
					if (response.status === httpStatusCode.CREATE) {
						let accessToken = response.data["access-token"];
						sessionStorage.setItem("accessToken", accessToken);
						isValidToken.value = true;
					}
				},
				async (error) => {
					// HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
					if (error.response.status === httpStatusCode.UNAUTHORIZED) {
						console.log("갱신 실패");
						// 다시 로그인 전 DB에 저장된 RefreshToken 제거.
						await logout(
							userInfo.value.userId,
							(response) => {
								if (response.status === httpStatusCode.OK) {
								} else {
									console.log("리프레시 토큰 제거 실패");
								}
								alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
								isLogin.value = false;
								userInfo.value = null;
								isValidToken.value = false;
								router.push({ name: "user-login" });
							},
							(error) => {
								console.error(error);
								isLogin.value = false;
								userInfo.value = null;
							}
						);
					}
				}
			);
		};

		const userLogout = async (userid) => {
			await logout(
				userid,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						isLogin.value = false;
						userInfo.value = {
							userId: "",
							userName: "",
						};
						isValidToken.value = false;
					} else {
						console.error("유저 정보 없음!!!!");
					}
				},
				(error) => {}
			);
		};

		const userRegist = async (userDto) => {
			await regist(
				JSON.stringify(userDto),
				(response) => {
					if (response.status === httpStatusCode.CREATE) {
						alert("회원가입에 성공했습니다.");
					} else {
						console.log("회원가입실패", response.status);
						alert("회원가입에 실패했습니다.");
					}
				},
				async (error) => {}
			);
		};

		const userPasswordFind = async (userDto) => {
			await passwordFind(
				JSON.stringify(userDto),
				(response) => {
					console.log(response);
					if (response.status === httpStatusCode.OK) {
						alert(`비밀번호 조회에 성공했습니다: ${response.data}`);
					} else {
						alert("비밀번호 조회에 실패했습니다.");
					}
				},
				async (error) => {
					alert("비밀번호 조회에 실패했습니다.");
				}
			);
		};

		const getProfileIdx = async (userId) => {
			await profileIdx(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						userInfo.value.fileIdx = response.data;
					} else {
						console.log("파일인덱스찾기 실패", response.status);
					}
				},
				async (error) => {}
			);
		};

		const getOtherUserProfile = async (userId, fileIdx, filePath) => {
			await profileIdx(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						fileIdx.value = response.data;
					} else {
						console.log("파일인덱스찾기 실패", response.status);
					}
				},
				async (error) => {}
			);
			await profileFilePath(
				fileIdx.value,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						filePath.value = response.data;
					} else {
						console.log("파일경로찾기 실패", response.status);
					}
				},
				async (error) => {}
			);
		};

		// const userModify = async (userDto) => {
		//   await modify(
		//     JSON.stringify(userDto),
		//     (response) => {
		//       if (response.status === httpStatusCode.CREATE) {
		//         alert("회원정보수정에 성공했습니다.");
		//         let token = sessionStorage.getItem("accessToken");
		//         getUserInfo(token);
		//         alert("modify 완료: ", userInfo.value);
		//       } else {
		//         console.log("회원정보수정 실패", response.status);
		//         alert("회원정보수정에 실패했습니다.");
		//       }
		//     },
		//     async (error) => {
		//
		//     }
		//   );
		// };

		const userWithdrawal = async (userId) => {
			await withdrawal(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						alert("회원탈퇴에 성공했습니다.");
						alert("delete 완료: ", userId);
						isLogin.value = false;
						userInfo.value = {
							userId: "",
							userName: "",
						};
						isValidToken.value = false;
						router.push({ name: "main" });
					} else {
						console.log("회원탈퇴 실패", response.status);
						alert("회원탈퇴에 실패했습니다.");
					}
				},
				async (error) => {}
			);
		};

		const getFollower = async (userId) => {
			await follower(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						followers.value = response.data;
						followerCount.value = response.data.length;
					} else {
						console.log("팔로워 리스트 실패", response.status);
					}
				},
				async (error) => {
					console.log(error);
				}
			);
		};

		const getFollowing = async (userId) => {
			await following(
				userId,
				(response) => {
					if (response.status === httpStatusCode.OK) {
						followings.value = response.data;
						followingCount.value = response.data.length;
					} else {
						console.log("팔로잉 리스트 실패", response.status);
					}
				},
				async (error) => {
					console.log(error);
				}
			);
		};

		return {
			isLogin,
			isLoginError,
			userInfo,
			isValidToken,
			userLogin,
			getUserInfo,
			tokenRegenerate,
			userLogout,
			userRegist,
			userPasswordFind,
			//   userModify,
			getProfileIdx,
			userWithdrawal,
			boardNo,
			getFollower,
			getFollowing,
			followers,
			followings,
			followerCount,
			followingCount,
			getOtherUserInfo,
			getOtherUserProfile,
		};
	},
	{
		persist: {
			storage: sessionStorage, //쓰고싶은 스토리지(세션 또는 로컬)
		},
	}
);
