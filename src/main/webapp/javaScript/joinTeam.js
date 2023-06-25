



function OneCheckBox(checkbox, name) {
    let checkboxes = document.getElementsByName(name)
    checkboxes.forEach((item) => {
        if (item !== checkbox) {
            item.checked = false;
        }
    })
}

window.onload = function (){
    let queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var teamName = urlParams.get('team');
    if (urlParams.has('team')) {
        document.getElementById("JoinTeamText").innerHTML = "Do you want to join " + teamName + "?";
    } else {
        document.getElementById("JoinTeamText").innerHTML = "Team not found.";
    }
}



function createData(){
    var name = document.getElementById("name").value
    var password = document.getElementById("password").value
    var email = document.getElementById("email").value
    let queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var teamname = urlParams.get('team');


    const data = '{' +
        '"name": "' + name + '",' +
        '"password": "' + password + '",' +
        '"email": "' + email + '",' +
        '"mobile_number": "' + document.getElementById("phone").value + '",' +
        '"is_participant": "true",'+
        '"allergies": "' + document.getElementById("allergies").value + '",' +
        '"team": "' + teamname + '",' +
        '"have_car": "false"'+
        '}'
    return data
}

function postData(){
    var teamConfirm = "no"
    var teamcheckbox1 = document.getElementById("teamNameYes")
    if (teamcheckbox1.checked !== true){
        return fetch("http://localhost:8080/InterActiefProject/api/person/notexistent").then(result => {
            return "incorrect"
        })
    }
    else {
        let jsonData = createData()
        return fetch("http://localhost:8080/InterActiefProject/api/people", {
            method: "POST",
            headers: {
                "accept": "application/json",
                "Content-Type": "application/json",
            },
            body: jsonData,
        }).then(response => {
            return response;
        })
    }
}

function createTeam(){
    postData().then(result => {
        if (result === "incorrect") {
            document.getElementById("errorMessage").innerText = "Make sure to confirm if you want to join the team"
        }
        else {
            window.location.href = "../index.html";
        }
    });


}

