//Validation
let usernameEditOld = ''

function validFirstName(firstname){


    if(firstname === ''){
        return false
    }else{
        return true
    }

}

function validLastName(lastname){
    if(lastname === ''){
        return false
    }
    else{
        return true
    }
}

function validAge(age, min, max){

    if(age === 0 || age >max || age < min || age === ''){
        return false
    }
    else{
        return true
    }
}

function validEmail(email){
    const reg = /^[\w-\.]+@[\w-]+\.[a-z]{2,4}$/i

    if(email.match(reg)){
        return true
    }
    else{
        return false

    }
}

function validPassword(pass){
    const passformat = /^[A-Za-zА-Яа-я0-9]{3,16}$/;

    if(pass.match(passformat)){
        return true
    }
    else{
        return false
    }
}

function validateForm(name){
    let mark = true
    const firstname = $(`${name} [name = firstname]`).val()
    const lastname = $(`${name} [name = lastname]`).val()
    const age = $(`${name} [name = age]`).val()
    const email = $(`${name} [name = email]`).val()
    const pass = $(`${name} [name = password]`).val()

    if(validFirstName(firstname)){
        $(`${name} [name = firstnameSpan]`).text("")
        $(`${name} [name = firstname]`).addClass('border-success').removeClass('border-danger')
    }else{
        $(`${name} [name = firstnameSpan]`).text("First name should not is empty!")
        $(`${name} [name = firstname]`).addClass('border-danger').removeClass('border-success')
        mark = false
    }

    if(validLastName(lastname)){
        $(`${name} [name = lastnameSpan]`).text("")
        $(`${name} [name = lastname]`).removeClass('border-danger').addClass('border-success')
    }else{
        $(`${name} [name = lastnameSpan]`).text("Last name should not is empty!")
        $(`${name} [name = lastname]`).addClass('border-danger').removeClass('border-success')
        mark = false
    }

    if(validAge(age, 0, 150)){
        $(`${name} [name = ageSpan]`).text("")
        $(`${name} [name = age]`).addClass('border-success').removeClass('border-danger')
    }else{
        $(`${name} [name = ageSpan]`).text("Age must be from 0 to 150!")
        $(`${name} [name = age]`).addClass('border-danger').removeClass('border-success')
        mark = false
    }

    if(!responseEmail(name, email)){
        mark = false
    }

    if(validPassword(pass)){
        $(`${name} [name = passwordSpan]`).text("")
        $(`${name} [name = password]`).addClass('border-success').removeClass('border-danger')
    }else{
        $(`${name} [name = passwordSpan]`).text("Password must be between 3 and 16 characters!")
        $(`${name} [name = password]`).addClass('border-danger').removeClass('border-success')
        mark = false
    }

    if(mark){
        return true
    } else{
        return false
    }

}

// validation username
async function responseEmail(form, email){
    if(form === '#formNewUser'){
        usernameEditOld = ''
    }
    let arr = [usernameEditOld, email]

    let response = await fetch("/relevationUsername", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(arr)
    })
    let result = await response.json();

    if(result){
        $(`${form} [name = responseEmailSpan]`).text("E-mail already exists");
        $(`${form} [name = email]`).addClass('border-danger').removeClass('border-success');
        $(`${form} [name = releEmail]`).val('off');
        return false
    }else if(!validEmail(email)){
        $(`${form} [name = responseEmailSpan]`).text("You have entered an invalid email address!")
        $(`${form} [name = email]`).addClass('border-danger').removeClass('border-success')
        $(`${form} [name = releEmail]`).val('off');
        return false
    }else{
        $(`${form} [name = responseEmailSpan]`).text("")
        $(`${form} [name = email]`).addClass('border-success').removeClass('border-danger')
        $(`${form} [name = releEmail]`).val('on');
        return true
    }

}




// modal
function modalShow(form, id) {
        let href = '/findOne/'+ id

        fetch(href).then(result =>{
            return result.json()
        }, reject => alert("Ошибка HTTP: " + reject.status))

            .then(result =>{
            $(`${form} [name = idUser]`).val(result.id);
            $(`${form} [name = firstname]`).val(result.firstName);
            $(`${form} [name = lastname]`).val(result.lastName);
            $(`${form} [name = age]`).val(result.age);
            $(`${form} [name = email]`).val(result.username);
            $(`${form} [name = password]`).val(result.password);
            usernameEditOld = result.username

            $(`${form} [name = roles] option`).prop('selected', false);
            result.roles.find(function (role) {
                if (role.role === 'ROLE_ADMIN') {
                    $(`${form} [name = roles] option[value = '2']`).prop('selected', true);
                }
                if (role.role === 'ROLE_USER') {
                    $(`${form} [name = roles] option[value = '1']`).prop('selected', true);
                }
                var arr = $(`${form} [name = roles]`).val();
                var foo = arr.join(",");
                $(`${form} [name = roleList] option`).val(foo);
            })
        })

        $(`${form}`).modal();
}

// Select area
function selectSave(form){
    var arr = $(`${form} [name = roles]`).val();
    var foo = arr.join(",");
    $(`${form} [name = roleList]`).val(foo);
}




//Table function
function printTable(){
    getAllUsers().then(function (result){

        let table = document.getElementById('userTableBody');
        for(const object of result){
            printRecord(table, object)
        };

    });
}
printTable()

function newTr(object){
    let roles = ''
    let tr = document.createElement('tr');
    tr.id = object.id
    for(const role of object.roles){
        roles += role.role.substr(5) + ' '
    }
    tr.innerHTML = '<td>' + object.id + '</td>' +
        '<td>' + object.firstName + '</td>' +
        '<td>' + object.lastName + '</td>' +
        '<td>' + object.age + '</td>' +
        '<td>' + object.username + '</td>' +
        '<td>' + roles + '</td>' +
        '<td><a class="btn btn-info text-white" id="btnEdit" onclick="modalShow(\'#modalEdit\', ' + object.id + ')">Edit</a></td>' +
        '<td><a class="btn btn-danger text-white" id="btnDelete" onclick="modalShow(\'#modalDelete\', ' + object.id + ')">Delete</a></td>';

    return tr;
}

function printRecord(table, object){
    table.appendChild(newTr(object));
}

function changeRecord(object) {
    let trOld = document.getElementById(`${object.id}`);
    trOld.replaceWith(newTr(object));
}

function dropRecord(id){
    $(`#userTableBody #${id}`).remove();
}

function dropTable(){
    $("#userTableBody tr").remove();
}



//fetch Requests
async function getAllUsers(){
    let url = '/allUsers'
    let response = await fetch(url);

    if(response.ok){
        return await response.json();
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

async function saveUser(form){

    if(!validateForm(form)){
        return;
    }

    let user = {
        id: $(`${form} [name = idUser]`).val(),
        firstName: $(`${form} [name = firstname]`).val(),
        lastName: $(`${form} [name = lastname]`).val(),
        age: $(`${form} [name = age]`).val(),
        username: $(`${form} [name = email]`).val(),
        password: $(`${form} [name = password]`).val(),
        roles: []
    }
    let roleList = $(`${form} [name = roleList]`).val().split(',')
    roleList.find(function (role) {
        if (role === '2') {
            user.roles.push({id: 2, role: 'ROLE_ADMIN'})
        }
        if (role === '1' || role === '') {
            user.roles.push({id: 1, role: 'ROLE_USER'})
        }
    })

    if(form === '#formNewUser'){
        let response = await fetch('/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        })
        if(response.ok){
                $('#allusers-tab').addClass('active').prop('aria-selected', true)
                $('#new-tab').removeClass('active').prop('aria-selected', false)
                $('#allusers').addClass('show active')
                $('#new').removeClass('show active')

            let result = await response.json()
            printRecord(document.getElementById('userTableBody'), result)

            alert("User " + result.username + " was added")

        } else {
            alert("User not added")
        }


    } else if(form === '#modalEdit') {
        let response = await fetch('/edit', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        })

        if(response.ok){
            $(`${form}`).modal("hide");
            let result = await response.json()
            changeRecord(result)

            alert("User changed")
        } else {

            alert("User not changed")
        }
    }

}

async function deleteUser(){
    let userId = $(`#deleteForm [name = idUser]`).val()

    let response = await fetch('http://localhost:8080/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(userId)
    })
    if(response.ok){
        $(`#modalDelete`).modal("hide");
        dropRecord(userId)
        alert("User deleted")
    } else {
        alert("User not deleted")
    }
}


