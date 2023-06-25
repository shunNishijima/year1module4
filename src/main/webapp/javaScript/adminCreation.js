



function getPersonData(){
    let name = document.getElementById("name").value
    let password = document.getElementById("password").value
    let email = document.getElementById("email").value
    const data = '{' +
        '"name":"' + name + '",' +
        '"password":"' + password + '",' +
        '"email":"' + email + '",' +
        '"mobile_number":"' + document.getElementById("phone").value + '",' +
        '"is_participant": "false",' +
        '"allergies": "",' +
        '"team": "adminTeam" ,' +
        '"have_car": false'+
        '}'
    return data
}

function postData(){
    let personData = getPersonData()
    fetch("http://localhost:8080/InterActiefProject/api/people", {
        method: "POST",
        headers: {
            "accept": "application/json",
            "Content-Type": "application/json",
        },
        body: personData,
    }).then(response => {
        if (!response.ok){
            return response;
        };
    })
}

function checkPassword(){
    if (document.getElementById("password").value !== document.getElementById("passwordConfirm").value){
        return true;
    } else return document.getElementById("password").value === "";

}

function checkEmail(){
    return fetch("http://localhost:8080/InterActiefProject/api/people/" + document.getElementById("email").value).
    then(response => response.text()).
    then(result => {
        return result;
    })
}



function popup(message){
    document.getElementById("errorMessage").innerText = message;
}

function createTeam(){
    popup("");
    if (checkPassword()) {
        popup("passwords are not the same")
    }
    else {
        checkEmail().then(response => {
            if (response.length >= 1) {
                popup("there is already an account registered with this email");
            }
            else {
                postData()
                setSessionStorage();
                window.location.href = "../index.html";

            }});
    }
}
function setSessionStorage(){
    sessionStorage.setItem("sessionKey", "" + Math.floor(Math.random() * 1000000000));
    fetch("http://localhost:8080/InterActiefProject/api/people/" + document.getElementById("email").value + "/" + sessionStorage.getItem("sessionKey"), {
        method: "POST",
        headers: {
            "accept": "application/json",
            "Content-Type": "application/json",
        }})
}