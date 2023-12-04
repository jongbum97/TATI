function signUp() {
	let name = document.querySelector("#signupNameValue").value;
	let id = document.querySelector("#signupIdValue").value;
	let pw = document.querySelector("#signupPwValue").value;
	let pw2 = document.querySelector("#signupPw2Value").value;
	let email = document.querySelector("#signupEmailValue").value;
	let address = document.querySelector("#signupAddressValue").value;
	let sido = document.querySelector("#signupSidoValue");

	console.log(sido);
	let selectsido = sido.options[sido.selectedIndex].textContent;

	if (!name || !id || !pw || !pw2 || !email || !address || !selectsido) {
		alert("빈칸이 있습니다.");
		return;
	}

	if (pw != pw2) {
		alert("비밀번호가 같지 않습니다.");
		return;
	}

	let poll = {
		name: name,
		id: id,
		pw: pw,
		email : email,
		address: address,
		fullemail: email + "@" + address,
		sido: selectsido,
	};

	localStorage.setItem("loginstatus", JSON.stringify(poll));
	alert("등록되었습니다");
	window.location.href = "index.html";

}

function modify() {
    let name = document.querySelector("#name").value;
    let id = document.querySelector("#id").value;
    let pw = document.querySelector("#pw").value;
    let pw2 = document.querySelector("#pw2").value;
    let email = document.querySelector("#email").value;
    let address = document.querySelector("#address").value;
    let sido = document.querySelector("#sido");

    console.log(sido);
    let selectsido = sido.options[sido.selectedIndex].textContent;

	if (pw == "" || pw2 == "") {
		alert("비밀번호를 입력해주세요.");
		return;
	}
    if (pw != pw2) {
        alert("비밀번호가 같지 않습니다.");
        return;
    }

    let poll = {
		name: name,
		id: id,
		pw: pw,
		email : email,
		address: address,
		fullemail: email + "@" + address,
		sido: selectsido,
    };

    localStorage.setItem("loginstatus", JSON.stringify(poll));
    alert("등록되었습니다");
    document.querySelector("#pw").value = "";
    document.querySelector("#pw2").value = "";
	window.location.href = "index.html";
}

