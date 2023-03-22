const userService = (function () {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getCurrentUser: function (successCallback) {
            $.ajax({
                url: "/api/users",
                type: "GET",
                success: successCallback,
                beforeSend: authenticationService.addAuthHeader,
                error: handleAjaxError
            });
        },

        createUser: function (user, successCallback) {
            $.ajax({
                url: "/api/users",
                type: "POST",
                data: JSON.stringify(user),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getUserById: function (userId, successCallback) {
            $.ajax({
                url: "/api/users/" + userId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateUserById: function (userId, partialUser, successCallback) {
            $.ajax({
                url: "/api/users/" + userId,
                type: "PUT",
                data: JSON.stringify(partialUser),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteUserById: function (userId, successCallback) {
            $.ajax({
                url: "/api/users/" + userId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})();
