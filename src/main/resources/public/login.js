  const loginForm = document.getElementById("login-form");
  const loginButton = document.getElementById("login-form-submit");

loginButton.addEventListener("click", (e) => {
      e.preventDefault();
      const username = loginForm.username.value;
      const password = loginForm.password.value;

      if (!!username && !!password ) {
       var jsonRequest = {};
       jsonRequest.userId = username;
       jsonRequest.password = password;

       var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
            setCookie("token", JSON.parse(this.responseText).data.token, 1);
            setCookie("userId", JSON.parse(this.responseText).data.userId, 1);
            location.replace("http://localhost:8080/dinehouse/api/v1/main.html")
          }
        };

      httpRequest.open("POST", "http://localhost:8080/dinehouse/api/v1/login", true);
      httpRequest.setRequestHeader("Content-type", "application/json");
      var data = JSON.stringify(jsonRequest);
      httpRequest.send(data);
     }
})

function setCookie(cname, cvalue, exdays) {
  const d = new Date();
  d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
  let expires = "expires="+d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
  let name = cname + "=";
  let ca = document.cookie.split(';');
  for(let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function checkCookie() {
  let token = getCookie("token");
  if (token != "") {
    alert("token " + token);
  } else {
    token = prompt("Please enter user-token:", "");
    if (token != "" && token != null) {
      setCookie("token", token, 1);
    }
  }
}