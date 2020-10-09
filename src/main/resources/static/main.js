// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

// validation username
function responseEmail(){
    $.ajax({
        type: "POST",
        url: "/relevationUsername",
        data: { olduser: '', newuser: document.formNewUser.email.value },
        cache: false,
        success: function(response){
            if(response == true){
                $("#responseEmailSpan").text("E-mail already exists");
                document.formNewUser.newReleEmail.value = 'off';
                $('#newUserBtn').prop('disabled', true);
            }else{
                $("#responseEmailSpan").text("");
                document.formNewUser.newReleEmail.value = 'on';
                $('#newUserBtn').prop('disabled', false);
            };
        }
    });
};

var usernameEditOld = ''
// edit username validation
function responseEditEmail(){
    $.ajax({
        type: "POST",
        url: "/relevationUsername",
        data: { olduser: usernameEditOld, newuser: document.editForm.emailEdit.value },
        cache: false,
        success: function(response){
            if(response == true){
                $("#responseEmailEditSpan").text("E-mail already exists");
                document.editForm.editReleEmail.value = 'off';
                $('#editUserBtn').prop('disabled', true);
            }else{
                $("#responseEmailEditSpan").text("");
                document.editForm.editReleEmail.value = 'on';
                $('#editUserBtn').prop('disabled', false);
            };
        }
    });
};


// modalEdit
$('.table .eBtn').on('click', function (event) {
    event.preventDefault();
    var href = $(this).attr('href');

    $.get(href, function(user, status){
        $('#editForm #iduserEdit').val(user.id);
        $('#editForm #firstnameEdit').val(user.firstName);
        $('#editForm #lastnameEdit').val(user.lastName);
        $('#editForm #ageEdit').val(user.age);
        $('#editForm #emailEdit').val(user.username);
        $('#editForm #passwordEdit').val(user.password);
        usernameEditOld = user.username

        $('#editForm #rolesEdit option').prop('selected', false);
        user.roles.find(function (role){
            if(role.role === 'ROLE_ADMIN'){
                $("#editForm #rolesEdit option[value='2']").prop('selected', true);
            }
            if(role.role === 'ROLE_USER'){
                $("#editForm #rolesEdit option[value='1']").prop('selected', true);
            }
            var arr = $("select[name='rolesEdit']").val();
            var foo = arr.join(",");
            $( "#roleListEdit" ).val(foo);
        })


    });

    $('#modalEdit').modal();
});

// modal delete
$('.table .dBtn').on('click', function (event) {
    event.preventDefault();
    var href = $(this).attr('href');

    $.get(href, function(user, status){
        $('#deleteForm #iduserDelete').val(user.id);
        $('#deleteForm #firstnameDelete').val(user.firstName);
        $('#deleteForm #lastnameDelete').val(user.lastName);
        $('#deleteForm #ageDelete').val(user.age);
        $('#deleteForm #emailDelete').val(user.username);
        $('#deleteForm #passwordDelete').val(user.password);

        $('#editForm #rolesDelete option').prop('selected', false);
        user.roles.find(function (role){
            if(role.role === 'ROLE_ADMIN'){
                $("#deleteForm #rolesDelete option[value='2']").prop('selected', true);
            }
            if(role.role === 'ROLE_USER'){
                $("#deleteForm #rolesDelete option[value='1']").prop('selected', true);
            }
        })

    });

    $('#modalDelete').modal();
});
// edit select
$("select[name='rolesEdit']").change(function() {
    var arr = $("select[name='rolesEdit']").val();
    var foo = arr.join(",");
    $( "#roleListEdit" ).val(foo);
});
// add select
$("select[name='rolesAdd']").change(function() {
    var arr = $("select[name='rolesAdd']").val();
    var foo = arr.join(",");
    $( "#roleListAdd" ).val(foo);
});