$(document).ready(function () {
    // Pokazuje formularz rejestracji.
    $(document).on("click", "#registerViewButton", function () {
        $("#accountContainer").html(getRegisterView());
    });

    // Pokazuje formularz logowania.
    $(document).on("click", "#loginViewButton", function () {
        $("#accountContainer").html(getLoginView());
    });

    // Wylogowuje użytkownika.
    $(document).on("click", "#logoutViewButton", function () {
        document.getElementById("accountContainer").innerHtml = "";
        deleteAuthenticationCookie();
        window.location.reload();
    });

    $(document).on("click", "#registerButton", function () {
        var factory = new RegisterBindingModelFactory();
        var model = factory.create();
        var validationResult = validateRegistration(model);
        if (!validationResult.isValid) {
            alert(validationResult.errorMessage);
        } else {
            $.ajax({
                url: siteRoot + 'api/v1/user/register',
                contentType: "application/json",
                dataType: 'json',
                method: 'POST',
                data: JSON.stringify(model),
                success: function (result) {
                    $(".navbar").html(getNavbarLoggedIn());
                    createAuthenticationCookie(result.accessToken);
                    window.location.reload();
                },
                error: function (result) {
                    alert('Doszło do błędu podczas rejestracji');
                    alert(getErrorMessage(result));
                }
            });
        }
    });

    $(document).on("click", "#loginButton", function () {
        var factory = new LoginBindingModelFactory();
        var model = factory.create();

        $.ajax({
            url: siteRoot + 'api/v1/user/authenticate',
            contentType: "application/json",
            dataType: 'json',
            method: 'POST',
            data: JSON.stringify(model),
            success: function (result) {
                $(".navbar").html(getNavbarLoggedIn());
                createAuthenticationCookie(result.accessToken);
                window.location.reload();
            },
            error: function (result) {
                alert('Doszło do błędu podczas logowania');
                alert(getErrorMessage(result));
            }
        });
    });

});