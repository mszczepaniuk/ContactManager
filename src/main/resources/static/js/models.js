class ValidationResult {
    isValid;
    errorMessage;
}

class RegisterBindingModel {
    username = '';
    password = '';
    confirmPassword = '';
}

class RegisterBindingModelFactory {
    create() {
        var model = new RegisterBindingModel();
        model.username = document.getElementsByName("username")[0].value;
        model.password = document.getElementsByName("password")[0].value;
        model.confirmPassword = document.getElementsByName("confirmPassword")[0].value;
        return model;
    }
}

class LoginBindingModel {
    username = '';
    password = '';
}

class LoginBindingModelFactory {
    create() {
        var model = new RegisterBindingModel();
        model.username = document.getElementsByName("username")[0].value;
        model.password = document.getElementsByName("password")[0].value;
        return model;
    }
}

class ContactViewModel {
    id = '0';
    firstName = '';
    lastName = '';
    email = '';
    password = '';
    category = 'prywatny';
    subcategory = '';
    telephoneNumber = '';
    dateOfBirth = 'RRRR-MM-DD';
}

class ApiResultContactViewModelFactory {
    create(result) {
        var model = new ContactViewModel();
        model.id = result.id;
        model.firstName = result.firstName;
        model.lastName = result.lastName;
        model.email = result.email;
        model.password = result.password;
        model.category = result.category;
        model.subcategory = result.subcategory;
        model.telephoneNumber = result.telephoneNumber;
        model.dateOfBirth = result.dateOfBirth.slice(0, 10);
        return model;
    }
}

class InputsContactViewModelFactory {
    create(id) {
        var model = new ContactViewModel();
        model.id = id;
        model.firstName = document.getElementById('contactFirstName' + id).value;
        if (!model.firstName) {
            model.firstName = document.getElementById('contactFirstName' + id).placeholder;
        }
        model.lastName = document.getElementById('contactLastName' + id).value;
        if (!model.lastName) {
            model.lastName = document.getElementById('contactLastName' + id).placeholder;
        }
        model.email = document.getElementById('contactEmail' + id).value;
        if (!model.email) {
            model.email = document.getElementById('contactEmail' + id).placeholder;
        }
        model.password = document.getElementById('contactPassword' + id).value;
        if (!model.password) {
            model.password = document.getElementById('contactPassword' + id).placeholder;
        }
        let categoryNode = document.getElementById('contactCategory' + id);
        model.category = categoryNode.options[categoryNode.selectedIndex].value;
        if (model.category === 'sluzbowy') {
            try {
                let subCategoryNode = document.getElementById('contactSubcategory' + id);
                model.subcategory = subCategoryNode.options[subCategoryNode.selectedIndex].value;
            } catch (e) {
                model.subcategory = '';
            }
        }
        if (model.category === 'inny') {
            model.subcategory = document.getElementById('contactSubcategory' + id).value;
        }
        if (model.category === 'prywatny') {
            model.subcategory = '';
        }
        model.telephoneNumber = document.getElementById('contactTelephoneNumber' + id).value;
        if (!model.telephoneNumber) {
            model.telephoneNumber = document.getElementById('contactTelephoneNumber' + id).placeholder;
        }
        model.dateOfBirth = document.getElementById('contactDateOfBirth' + id).value;
        if (!model.dateOfBirth) {
            model.dateOfBirth = document.getElementById('contactDateOfBirth' + id).placeholder;
        }
        return model;
    }
}
