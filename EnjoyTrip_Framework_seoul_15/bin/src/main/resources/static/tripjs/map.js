// html파일에서 아래 스크립트 추가
{
	/* <script
        type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a55f2a7d9703cdee565c4c05eca9def0&libraries=services,clusterer,drawing"></script>
    </script> */
}
// index page 로딩 후 전국의 시도 설정.
let areaUrl =
	"https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
	serviceKey +
	"&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

// fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
fetch(areaUrl, { method: "GET" })
	.then((response) => response.json())
	.then((data) => makeOption(data));

function makeOption(data) {
	let areas = data.response.body.items.item;
	// console.log(areas);
	let sel = document.getElementById("search-area");
	areas.forEach((area) => {
		let opt = document.createElement("option");
		opt.setAttribute("value", area.code);
		opt.appendChild(document.createTextNode(area.name));

		sel.appendChild(opt);
	});
}

// 검색 버튼을 누르면..
// 지역, 유형, 검색어 얻기.
// 위 데이터를 가지고 공공데이터에 요청.
// 받은 데이터를 이용하여 화면 구성.
document.getElementById("btn-search").addEventListener("click", () => {
	let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
	// let searchUrl = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
	// let searchUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
	var pageNo = 1;
	let queryString = `serviceKey=${serviceKey}&numOfRows=10000&pageNo=${pageNo}&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
	let areaCode = document.getElementById("search-area").value;
	let contentTypeId = document.getElementById("search-content-id").value;
	let keyword = document.getElementById("search-keyword").value;

	if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
	if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;
	// if (!keyword) {
	//   alert("검색어 입력 필수!!!");
	//   return;
	// } else searchUrl += `&keyword=${keyword}`;
	let service = ``;
	if (keyword) {
		service = `searchKeyword1`;
		queryString += `&keyword=${keyword}`;
	} else {
		service = `areaBasedList1`;
	}
	let searchUrl = baseUrl + service + "?" + queryString;

	fetch(searchUrl)
		.then((response) => response.json())
		.then((data) => makeList(data));
});

var trips; // 전체 여행지 데이터 저장
var currentPage = 0; // 현재 페이지 번호

function makeList(data) {
	document.querySelector("table").setAttribute("style", "display: ;");
	trips = data.response.body.items.item;
	// console.log(trips);
	displayPage(currentPage); // 첫 페이지 데이터 표시
}

function displayPage(pageNumber) {
	var start = pageNumber * 10; // 시작 인덱스 계산
	var end = Math.min(start + 10, trips.length); // 종료 인덱스 계산 (trips.length를 넘지 않도록)

	let tripList = ``;
	positions = [];

	for (let i = start; i < end; i++) {
		let area = trips[i];
		tripList += `
<tr class="trip-info" onclick="moveCenter(${area.mapy}, ${area.mapx});">
  <td><img src="${area.firstimage}" width="100px"></td>
  <td id="title">${area.title}</td>
  <td>${area.addr1} ${area.addr2}</td>
  <td name="distance" id="latitude">${area.mapy}</td>
  <td name="distance" id="longitude">${area.mapx}</td>
  <td><input type="checkbox" name="xxx" value="yyy"/></td>
</tr>
`

		let markerInfo = {
			title: area.title,
			latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
		};
		positions.push(markerInfo);
	}

	document.getElementById("trip-list").innerHTML = tripList;

	displayMarker();
}

// '다음 페이지' 버튼 클릭 이벤트 리스너 추가
document.getElementById("btn-next").addEventListener("click", () => {
	if (currentPage >= trips.length / 10 - 1) return; // 마지막 페이지면 종료 (trips.length를 넘지 않도록
	currentPage += 1;
	displayPage(currentPage);
});
// '이전 페이지' 버튼 클릭 이벤트 리스너 추가
document.getElementById("btn-prev").addEventListener("click", () => {
	if (currentPage <= 0) return;
	currentPage -= 1;
	displayPage(currentPage);
});

// 카카오지도
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
	mapOption = {
		center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
		level: 5, // 지도의 확대 레벨
	};

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

function displayMarker() {
	// 마커 이미지의 이미지 주소입니다
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

	for (var i = 0; i < positions.length; i++) {
		// 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(24, 35);

		// 마커 이미지를 생성합니다
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map: map, // 마커를 표시할 지도
			position: positions[i].latlng, // 마커를 표시할 위치
			title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
			image: markerImage, // 마커 이미지
		});
	}

	// 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
	map.setCenter(positions[0].latlng);
}

function moveCenter(lat, lng) {
	map.setCenter(new kakao.maps.LatLng(lat, lng));
}
