function createAuthenticationCookie(token) {
    var name = "contactmanager_authentication";
    var token = token;
    var expirationDate = new Date();
    expirationDate.setFullYear(expirationDate.getFullYear() + 1);
    var cookieString = `${name}=${token}; expires=${expirationDate.toUTCString()}; path=/`;
    document.cookie = cookieString;
}

// https://www.w3schools.com/js/js_cookies.asp
function getTokenFromCookie() {
    var name = "contactmanager_authentication" + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteAuthenticationCookie() {
    var name = "contactmanager_authentication";
    var token = token;
    var expirationDate = new Date();
    expirationDate.setFullYear(expirationDate.getFullYear() - 20);
    var cookieString = `${name}=${token}; expires=${expirationDate.toUTCString()}; path=/`;
    document.cookie = cookieString;
}

function getErrorMessage(result) {
    var errorMessage = '';
    for (i = 0; i < result.responseJSON.errorMessages.length; i++) {
        errorMessage += (result.responseJSON.errorMessages[i] + "\n");
    }
    return errorMessage;
}

function validateContact(model) {
    var result = new ValidationResult();
    result.isValid = true;
    var errorMessage = "";

    if (!model.firstName) {
        errorMessage += "Imię nie może być puste. \n";
        result.isValid = false;
    }
    if (!model.lastName) {
        errorMessage += "Nazwisko nie może być puste. \n";
        result.isValid = false;
    }
    if (!model.email) {
        errorMessage += "Email nie może być pusty. \n";
        result.isValid = false;
    }
    var passwordValidation = validatePassword(model.password);
    if (!passwordValidation.isValid) {
        errorMessage += passwordValidation.errorMessage;
        result.isValid = false;
    }
    if (!model.telephoneNumber) {
        errorMessage += "Numer telefonu nie może być pusty. \n";
        result.isValid = false;
    }
    if (model.telephoneNumber.match(/^[0-9]*$/) === null) {
        errorMessage += "Numer telefonu powinien składać się wyłacznie z cyfr. \n";
        result.isValid = false;
    }
    if (model.dateOfBirth.match(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/) === null || !Date.parse(model.dateOfBirth)) {
        errorMessage += "Data urodzin musi być w formacie RRRR-MM-DD. \n";
        result.isValid = false;
    }
    result.errorMessage = errorMessage;
    return result;
}

function validatePassword(password) {
    var result = new ValidationResult();
    result.isValid = true;
    var errorMessage = "";
    if (password.length < 6) {
        errorMessage += "Hasło jest za krótkie \n";
        result.isValid = false;
    }
    if (password.match(/[0-9]/) === null) {
        errorMessage += "Hasło powinno posiadać jedną cyfre. \n";
        result.isValid = false;
    }
    result.errorMessage = errorMessage;
    return result;
}

function validateRegistration(model){
    var result = new ValidationResult();
    result.isValid = true;
    var errorMessage = "";

    if (!model.username){
        errorMessage = "Nazwa użytkownika nie może być pusta. \n";
        result.isValid = false;
    }
    var passwordValidation = validatePassword(model.password);
    if (!passwordValidation.isValid) {
        errorMessage += passwordValidation.errorMessage;
        result.isValid = false;
    }
    if (model.password !== model.confirmPassword){
        errorMessage +=  "Hasła różnią się od siebie. \n";
        result.isValid = false;
    }
    result.errorMessage = errorMessage;
    return result;
}