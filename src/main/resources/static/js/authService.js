const authenticationService = (function () {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    function isTokenValid() {
        const token = localStorage.getItem('token');
        return !!token;
    }

    function logout() {
        localStorage.removeItem('token');
    }

    function addAuthHeader(xhr) {
        const token = localStorage.getItem('token')
        xhr.setRequestHeader('Authorization', 'Bearer ' + token)
    }

    return {
        authenticate: function (username, password, successCallback) {
            $.ajax({
                url: "/api/auth",
                type: "POST",
                data: JSON.stringify({username, password}),
                contentType: "application/json",
                success: function (data) {
                    console.log(data.token);
                    localStorage.setItem('token', data.token);
                    successCallback(data);
                },
                error: handleAjaxError
            });
        },
        isTokenValid: isTokenValid,
        logout: logout,
        addAuthHeader: addAuthHeader
    };
})();
