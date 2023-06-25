



let hasCar = "no"
function OneCheckBox(checkbox, name) {
    let checkboxes = document.getElementsByName(name)
    checkboxes.forEach((item) => {
        if (item !== checkbox) {
            item.checked = false;
        }
    })
}

function getPersonData(){
    var teamName = document.getElementById("teamname").value
    var name = document.getElementById("name").value
    var password = document.getElementById("password").value
    var email = document.getElementById("email").value
    const data = '{' +
        '"name":"' + name + '",' +
        '"password":"' + password + '",' +
        '"email":"' + email + '",' +
        '"mobile_number":"' + document.getElementById("phone").value + '",' +
        '"is_participant": "true",' +
        '"allergies":"' + document.getElementById("allergies").value + '",' +
        '"team":"' + teamName + '",' +
        '"have_car":"' + document.getElementById("yesCar").checked +'"'+
        '}'
    return data
}

function postData(){
    let teamData = '{' +
        '"name":"' + document.getElementById("teamname").value + '",' +
        '"captain":"' + document.getElementById("email").value + '",' +
        '"score":"' + 0 + '"}'
    return fetch("http://localhost:8080/InterActiefProject/api/teams", {
        method: "POST",
        headers: {
            "accept": "application/json",
            "Content-Type": "application/json",
        },
        body: teamData,
    }).then (response => {
        let personData = getPersonData()
        fetch("http://localhost:8080/InterActiefProject/api/people", {
            method: "POST",
            headers: {
                "accept": "application/json",
                "Content-Type": "application/json",
            },
            body: personData,
        }).then(response => {
            return response;
        })
    })
}

function checkPassword(){
    return document.getElementById("password").value !== document.getElementById("passwordConfirm").value;
}

function checkEmail(){
    return fetch("http://localhost:8080/InterActiefProject/api/people/" + document.getElementById("email").value).
    then(response => response.text()).
    then(result => {
        return result;
    })
}

function checkTeam(){
    return fetch("http://localhost:8080/InterActiefProject/api/teams/" + document.getElementById("teamname").value).
    then(response => response.text()).
    then(result => {
        return result;
    })
}

function isOneChecked(){
    return (document.getElementById("yesCar").checked || document.getElementById("noCar").checked);
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
                checkTeam().then(response => {
                    if (response.length >= 1) {
                        popup("teamname already exists");
                    }
                    else if(!isOneChecked) {
                        popup("check all the necessary boxes");
                    }
                    else {
                        postData().then (response => {
                            setSessionStorage();
                            window.location.href = "../index.html";
                        });
                    }
                });
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