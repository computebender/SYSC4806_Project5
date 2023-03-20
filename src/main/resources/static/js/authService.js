const authenticationService = (function () {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    function setAuthorizationHeader(xhr, token) {
        if (token) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        }
    }

    function createAuthenticatedRequest(config, customErrorHandler) {
        const token = localStorage.getItem('token');
        const extendedConfig = {
            beforeSend: function (xhr) {
                setAuthorizationHeader(xhr, token);
            },
            error: customErrorHandler || handleAjaxError,
            ...config
        };
        return $.ajax(extendedConfig);
    }

    function withAuthentication(serviceFunction) {
        return function (config, customErrorHandler) {
            const authenticatedConfig = {
                ...config,
                beforeSend: function (xhr) {
                    const token = localStorage.getItem('token');
                    setAuthorizationHeader(xhr, token);
                },
                error: customErrorHandler || handleAjaxError
            };
            return serviceFunction(authenticatedConfig);
        };
    }

    function isTokenValid() {
        const token = localStorage.getItem('token');
        return !!token;
    }

    function logout() {
        localStorage.removeItem('token');
    }

    return {
        authenticate: function (username, password, successCallback) {
            $.ajax({
                url: "/authenticate",
                type: "POST",
                data: JSON.stringify({username, password}),
                contentType: "application/json",
                success: function (data) {
                    localStorage.setItem('token', data.token);
                    successCallback(data);
                },
                error: handleAjaxError
            });
        },

        createAuthenticatedRequest: createAuthenticatedRequest,
        withAuthentication: withAuthentication,
        isTokenValid: isTokenValid,
        logout: logout
    };
})();
