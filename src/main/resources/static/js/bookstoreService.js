const bookstoreService = (function () {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllBookstores: function(successCallback) {
            $.ajax({
                url: "/api/bookstores",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createBookstore: function(bookstore, successCallback) {
            $.ajax({
                url: "/api/bookstores",
                type: "POST",
                data: JSON.stringify(bookstore),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getBookstoreById: function(bookstoreId, successCallback) {
            $.ajax({
                url: "/api/bookstores/" + bookstoreId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateBookstoreById: function(bookstoreId, partialBookstore, successCallback) {
            $.ajax({
                url: "/api/bookstores/" + bookstoreId,
                type: "PUT",
                data: JSON.stringify(partialBookstore),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteBookstoreById: function(bookstoreId, successCallback) {
            $.ajax({
                url: "/api/bookstores/" + bookstoreId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})
