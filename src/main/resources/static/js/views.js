// HTML sa wklejone jako jeden string.

function getRegisterView() {
    var template = " <div id='registerForm' class='m-4'> <div class='form-group'> <label>Nazwa użytkownika</label> <input name='username' class='form-control'/> </div><div class='form-group'> <label>Hasło</label> <input name='password' type='password' class='form-control'/> </div><div class='form-group'> <label>Potwierdz hasło</label> <input name='confirmPassword' type='password' class='form-control'/> </div><button id='registerButton' type='submit' class='btn btn-primary'>Zarejestruj się</button> </div>";
    return template;
}

function getLoginView() {
    var template = "<div id='loginForm' class='m-4'> <div id='loginForm' class='form-group'> <label>Nazwa użytkownika</label> <input name='username' class='form-control'/> </div><div class='form-group'> <label>Hasło</label> <input name='password' type='password' class='form-control'/> </div><button id='loginButton' type='submit' class='btn btn-primary'>Zaloguj się</button> </div>";
    return template;
}

function getNavbarLoggedOut() {
    var template = " <ul class='navbar-nav'> <li class='nav-item'> <a class='nav-link' href='/'>ContactManager</a> </li><li id='registerViewButton' class='nav-item'> <a class='nav-link' style='user-select: none; color: dodgerblue; cursor: pointer;'>Rejestracja</a> </li><li id='loginViewButton' class='nav-item'> <a class='nav-link' style='user-select: none; color: dodgerblue; cursor: pointer;'>Logowanie</a> </li></ul>";
    return template;
}

function getNavbarLoggedIn() {
    var template = " <ul class='navbar-nav'> <li class='nav-item'> <a class='nav-link' href='/'>ContactManager</a> </li><li class='nav-item'> <a id='logoutViewButton' class='nav-link' style='user-select: none; color: dodgerblue; cursor: pointer;'>Wyloguj</a> </li></ul>";
    return template;
}

function getContactView(model) {
    var template = ` <table class='table table-bordered'> <thead> <tr> <th style='width: 25%'>Kontakt</th> <th style='width: 75%'>${model.id}</th> </tr></thead> <tbody> <tr> <th scope='row'>Imię</th> <td>${model.firstName}</td></tr><tr> <th scope='row'>Nazwisko</th> <td>${model.lastName}</td></tr><tr> <th scope='row'>Email</th> <td>${model.email}</td></tr><tr> <th scope='row'>Hasło</th> <td>${model.password}</td></tr><tr> <th scope='row'>Kategoria</th> <td>${model.category}</td></tr><tr> <th scope='row'>Podkategoria</th> <td>${model.subcategory}</td></tr><tr> <th scope='row'>Numer tel.</th> <td>${model.telephoneNumber}</td></tr><tr> <th scope='row'>Urodziny</th> <td>${model.dateOfBirth}</td></tr><tr> <td><button id='editContact${model.id}' name='editContact' class='btn btn-primary btn-block text-center mt-2 mb-2'>Edytuj</button></th> <td><button id='deleteContact${model.id}' name='deleteContact' class='btn btn-danger btn-block text-center mt-2 mb-2' onclick="return confirm('Czy na pewno chcesz usunąć ten kontakt?')">Usuń</button></td></tr></tbody> </table>`;
    return template;
}

function getDeletedContactView(){
    var template = "<table class='table table-bordered'> <thead> <tr> <th>Kontakt usunięto</th> </tr></thead></table>";
    return template;
}

function getAddContactView(model){
    var template = `<table class='table table-bordered'> <thead> <tr> <th style='width: 25%'>Nowy</th> <th style='width: 75%'>Kontakt</th> </tr></thead> <tbody> <tr> <th scope='row'>Imię</th> <td><input class='form-control' id='contactFirstName${model.id}' placeholder='${model.firstName}'></td></tr><tr> <th scope='row'>Nazwisko</th> <td><input class='form-control' id='contactLastName${model.id}' placeholder='${model.lastName}'></td></tr><tr> <th scope='row'>Email</th> <td><input type='email' class='form-control' id='contactEmail${model.id}' placeholder='${model.email}'></td></tr><tr> <th scope='row'>Hasło</th> <td><input type='password' class='form-control' id='contactPassword${model.id}' placeholder='${model.password}'></td></tr><tr> <th scope='row'>Kategoria</th> <td id='categoryTable${model.id}'> <select class='custom-select my-1 mr-sm-2' name='contactCategory' id='contactCategory${model.id}'> <option value='prywatny'>Prywatny</option> <option value='sluzbowy'>Służbowy</option> <option value='inny'>Inny</option> </select> </td></tr><tr> <th scope='row'>Podkategoria</th> <td id='contactSubcategoryTable${model.id}' disabled><input class='form-control' id='contactSubcategory${model.id}' placeholder='' disabled='disabled'></td></tr><tr> <th scope='row'>Numer tel.</th> <td><input class='form-control' id='contactTelephoneNumber${model.id}' placeholder='${model.telephoneNumber}'></td></tr><tr> <th scope='row'>Urodziny</th> <td><input class='form-control' id='contactDateOfBirth${model.id}' placeholder='${model.dateOfBirth}'></td></tr><tr> <td><button id='confirmAddingContact' class='btn btn-success btn-block text-center mt-2 mb-2'>Potwiedź</button></th> <td><button id='returnAddButton' class='btn btn-primary btn-block text-center mt-2 mb-2'>Cofnij </button> </td></tr></tbody> </table>`;
    return template;
}

function getSubcategoryInput(category, businessSubcategories, model){
    var template;
    switch (category) {
        case 'prywatny':
            template = `<input class='form-control' id='contactSubcategory${model.id}' placeholder='' disabled>`;
            break;
        case 'sluzbowy':
            template = `<select class='custom-select my-1 mr-sm-2' id='contactSubcategory${model.id}'>`;
            businessSubcategories.forEach(function (element){
                template += `<option value='${element}'>${element}</option>`;
        });
            template += "</select>";
            break;
        case 'inny':
            template = `<input class='form-control' id='contactSubcategory${model.id}' placeholder='${model.subcategory}'>`;
            break;
        default :
            template = `<input class='form-control' id='contactSubcategory${model.id}' placeholder='' disabled>`;
            break;
    }
    return template;
}

function getEditContactView(model) {
    var template = `<table class='table table-bordered'> <thead> <tr> <th style='width: 25%'>Kontakt</th> <th style='width: 75%'>${model.id}</th> </tr></thead> <tbody> <tr> <th scope='row'>Imię</th> <td><input class='form-control' id='contactFirstName${model.id}' placeholder='${model.firstName}'></td></tr><tr> <th scope='row'>Nazwisko</th> <td><input class='form-control' id='contactLastName${model.id}' placeholder='${model.lastName}'></td></tr><tr> <th scope='row'>Email</th> <td><input type='email' class='form-control' id='contactEmail${model.id}' placeholder='${model.email}'></td></tr><tr> <th scope='row'>Hasło</th> <td><input type='password' class='form-control' id='contactPassword${model.id}' placeholder='${model.password}'></td></tr><tr> <th scope='row'>Kategoria</th> <td id='categoryTable${model.id}'> <select class='custom-select my-1 mr-sm-2' name='contactCategory' id='contactCategory${model.id}'> <option value='prywatny'>Prywatny</option> <option value='sluzbowy'>Służbowy</option> <option value='inny'>Inny</option> </select> </td></tr><tr> <th scope='row'>Podkategoria</th> <td id='contactSubcategoryTable${model.id}'><input class='form-control' id='contactSubcategory${model.id}' placeholder='' disabled='disabled'></td></tr><tr> <th scope='row'>Numer tel.</th> <td><input class='form-control' id='contactTelephoneNumber${model.id}' placeholder='${model.telephoneNumber}'></td></tr><tr> <th scope='row'>Urodziny</th> <td><input class='form-control' id='contactDateOfBirth${model.id}' placeholder='${model.dateOfBirth}'></td></tr><tr> <td><button id='confirmEditingContact${model.id}' name='confirmEditingContact' class='btn btn-success btn-block text-center mt-2 mb-2'>Potwiedź</button></th> <td><button id='returnEditButton${model.id}' name='returnEditButton' class='btn btn-primary btn-block text-center mt-2 mb-2'>Cofnij </button> </td></tr></tbody> </table>`;
    return template;
}
