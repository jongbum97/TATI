window.onload = function checkLogin() {
	if (localStorage.getItem("login") == 1) {
		document.getElementById("loginul").style.display = "none";
		document.getElementById("logoutul").style.display = "flex";
		var user = JSON.parse(localStorage.getItem("loginstatus"));
		console.log(user);
		document.querySelector("#id").value = user.id;
		document.querySelector("#name").value = user.name;
		document.querySelector("#email").value = user.email;
		document.querySelector("#address").value = user.address;
	} else {
		document.getElementById("loginul").style.display = "flex";
		document.getElementById("logoutul").style.display = "none";
	}
};