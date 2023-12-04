// window.onload = function () {
//     if (localStorage.getItem("login") == 0) {
//         return;
//     }

//     var user = JSON.parse(localStorage.getItem("loginstatus"));
//     console.log(user);
//     document.querySelector("#id").value = user.id;
//     document.querySelector("#name").value = user.name;
//     document.querySelector("#email").value = user.email;
//     // document.querySelector("#id").setAttribute("readonly", true);
//     // if (localStorage.getItem("login") == 1) {
//     //     let loginbtn = document.querySelector("#loginul");
//     //     let logoutbtn = document.querySelector("#logoutul");
//     //     loginbtn.style.display = "none";
//     //     logoutbtn.style.display = "flex";
//     // }
// };

function login() {
//    let idvalue = document.querySelector(".loginidvalue");
//    console.log(idvalue.value);
//    let pwvalue = document.querySelector(".loginpwvalue");
//    console.log(pwvalue.value);

    loginbtn.style.display = "none";
    logoutbtn.style.display = "flex";
//    var user = JSON.parse(localStorage.getItem("loginstatus"));
//    console.log(user);
//    if (idvalue.value == user.id && pwvalue.value == user.pw) {
//        let loginbtn = document.querySelector("#loginul");
//        let logoutbtn = document.querySelector("#logoutul");
//
//        loginbtn.style.display = "none";
//        logoutbtn.style.display = "flex";
//        localStorage.setItem("login", 1);
//        document.querySelector("#id").value = user.id;
//        document.querySelector("#name").value = user.name;
//        document.querySelector("#email").value = user.email;
//        document.querySelector("#address").value = user.address;
//    }else{
//        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
//        document.querySelector("#loginId").value = "";
//        document.querySelector("#loginPw").value = "";
//    }
}

function logout() {
    alert("로그아웃 성공!");

}

// function modify() {
//     var user = JSON.parse(localStorage.getItem("loginstatus"));
//     console.log(user);
//     document.querySelector("#id").value = user.id;
//     document.querySelector("#name").value = user.name;
//     document.querySelector("#email").value = user.email;
//     document.querySelector("#id").setAttribute("readonly", true);

//     console.log(sido);
//     let selectsido = sido.options[sido.selectedIndex].textContent;

//     if (pw != pw2) {
//         alert("비밀번호가 같지 않습니다.");
//         return;
//     }

//     let poll = {
//         name: name,
//         id: id,
//         pw: pw,
//         email: email + "@" + address,
//         sido: selectsido,
//     };

//     localStorage.setItem("loginstatus", JSON.stringify(poll));
//     alert("등록되었습니다");
// }

function loadLoc() {
    let loc = document.querySelectorAll(".accordion-collapse");
    let p = document.querySelector(".market_btn");

    if (p.classList.contains("open")) {
        p.innerHTML = "전국매장펼치기";
        p.classList.remove("open");
        p.classList.add("close");
        loc.forEach((item) => {
            item.classList.add("show");
        });
    } else if (p.classList.contains("close")) {
        p.innerHTML = "전국매장접기";
        p.classList.remove("close");
        p.classList.add("open");
        loc.forEach((item) => {
            item.classList.remove("show");
        });
    }
}

function loaddetail(event) {
    const clickedElement = event.target;
    const parentElement = clickedElement.parentElement;
    const ulElement = parentElement.querySelector("ul");

    if (ulElement) {
        if (ulElement.style.display === "none" || ulElement.style.display === "") {
            ulElement.style.display = "block";
        } else {
            ulElement.style.display = "none";
        }
    }
}

function popup() {
    var url = "makepoll.html";
    var name = "makepoll";
    var option = "width = 500, height = 350, top = 100, left = 200, location = no";
    window.open(url, name, option);
}

function addVoteValue() {
    let inputVal = querySelector(".input_vote").value;
    console.log(inputVal);
}
