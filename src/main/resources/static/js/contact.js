$(document).ready(function () {
    function showFullContact(id) {
        if (getTokenFromCookie() === "") {
            deleteAuthenticationCookie();
            alert("Akcja dostępna dla zalogowanych użytkowników.");
        } else {
            $.ajax({
                url: siteRoot + `api/v1/contact/${id}`,
                contentType: "application/json",
                dataType: 'json',
                method: 'GET',
                headers: {'Authorization': 'Bearer ' + getTokenFromCookie()},
                success: function (result) {
                    var factory = new ApiResultContactViewModelFactory();
                    var model = factory.create(result);
                    $("#contact" + id).html(getContactView(model));
                },
                error: function (result) {
                    alert("Wystąpił błąd.")
                    alert(getErrorMessage(result));
                }
            });
        }
    }

    $(document).on("click", "[name ='showMoreButton']", function () {
        var id = $(this).attr('id').replace("showMoreButton", "");
        showFullContact(id);
    });

    $(document).on("click", "[name ='deleteContact']", function () {
        var id = $(this).attr('id').replace("deleteContact", "");
        if (getTokenFromCookie() === "") {
            deleteAuthenticationCookie();
            alert("Akcja dostępna dla zalogowanych użytkowników.")
        } else {
            $.ajax({
                url: siteRoot + `api/v1/contact/${id}`,
                contentType: "application/json",
                method: 'DELETE',
                headers: {'Authorization': 'Bearer ' + getTokenFromCookie()},
                success: function () {
                    $("#contact" + id).html(getDeletedContactView());
                },
                error: function (result) {
                    alert("Wystąpił błąd.")
                    alert(getErrorMessage(result));
                }
            });
        }
    });

    $(document).on("click", "#addContactButton", function () {
        if (getTokenFromCookie() === '') {
            alert("Akcja dostępna dla zalogowanych użytkowników.");
        } else {
            $("#addContactContainer").html(getAddContactView(new ContactViewModel()));
        }
    });

    $(document).on("click", "#returnAddButton", function () {
        window.location.reload();
    });

    $(document).on("click", "#confirmAddingContact", function () {
        if (getTokenFromCookie() === "") {
            deleteAuthenticationCookie();
            alert("Akcja dostępna dla zalogowanych użytkowników.");
            return;
        }
        var model = new InputsContactViewModelFactory().create(0);
        var validationResult = validateContact(model);
        if (!validationResult.isValid) {
            alert(validationResult.errorMessage);
        } else {
            $.ajax({
                url: siteRoot + 'api/v1/contact',
                contentType: "application/json",
                dataType: 'json',
                method: 'POST',
                headers: {'Authorization': 'Bearer ' + getTokenFromCookie()},
                data: JSON.stringify(model),
                success: function (result) {
                    alert("Utworzono użytkownika.");
                    window.location.reload();
                },
                error: function (result) {
                    alert('Doszło do błędu podczas dodawania.');
                    alert(getErrorMessage(result));
                }
            });
        }
    });

    $(document).on("click", "[name ='editContact']", function () {
        var id = $(this).attr('id').replace("editContact", "");
        $.ajax({
            url: siteRoot + `api/v1/contact/${id}`,
            contentType: "application/json",
            dataType: 'json',
            method: 'GET',
            headers: {'Authorization': 'Bearer ' + getTokenFromCookie()},
            success: function (result) {
                var factory = new ApiResultContactViewModelFactory();
                var model = factory.create(result);
                $("#contact" + id).html(getEditContactView(model));
            },
            error: function (result) {
                alert("Wystąpił błąd.")
                alert(getErrorMessage(result));
            }
        });
    });

    $(document).on("click", "[name ='confirmEditingContact']", function () {
        var id = $(this).attr('id').replace("confirmEditingContact", "");
        if (getTokenFromCookie() === '') {
            alert("Akcja dostępna dla zalogowanych użytkowników.");
        } else {
            var model = new InputsContactViewModelFactory().create(id);
            var validationResult = validateContact(model);
            if (!validationResult.isValid) {
                alert(validationResult.errorMessage);
            } else {
                $.ajax({
                    url: siteRoot + 'api/v1/contact/' + id,
                    contentType: "application/json",
                    dataType: 'json',
                    method: 'PUT',
                    headers: {'Authorization': 'Bearer ' + getTokenFromCookie()},
                    data: JSON.stringify(model),
                    success: function (result) {
                        alert("Edytowano użytkownika.");
                        window.location.reload();
                    },
                    error: function (result) {
                        alert('Doszło do błędu podczas dodawania.');
                        alert(getErrorMessage(result));
                    }
                });
            }
        }
    });

    $(document).on("click", "[name ='returnEditButton']", function () {
        var id = $(this).attr('id').replace("returnEditButton", "");
        showFullContact(id);
    });

    $(document).on("change", "[name = 'contactCategory']", function () {
        var id = $(this).attr('id').replace("contactCategory", "");
        var model = new InputsContactViewModelFactory().create(id);
        var categoryNode = document.getElementById('contactCategory' + id);
        var selectedCategory = categoryNode.options[categoryNode.selectedIndex].value;

        $.ajax({
            url: siteRoot + `api/v1/contact/business-subcategories`,
            contentType: "application/json",
            dataType: 'json',
            method: 'GET',
            success: function (result) {
                var subcategories = result.subcategories;
                var template = getSubcategoryInput(selectedCategory, subcategories, model);
                $("#contactSubcategoryTable" + id).html(template);
            },
            error: function () {
                alert("Wystąpił błąd.");
                window.location.replace("/");
            }
        });
    });
});