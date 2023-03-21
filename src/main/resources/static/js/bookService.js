const bookService = (function() {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllBooks: function(successCallback) {
            $.ajax({
                url: "/api/books",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createBook: function(book, successCallback) {
            $.ajax({
                url: "/api/books",
                type: "POST",
                data: JSON.stringify(book),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getBookById: function(bookId, successCallback) {
            $.ajax({
                url: "/api/books/" + bookId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateBookById: function(bookId, partialBook, successCallback) {
            $.ajax({
                url: "/api/books/" + bookId,
                type: "PUT",
                data: JSON.stringify(partialBook),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteBookById: function(bookId, successCallback) {
            $.ajax({
                url: "/api/books/" + bookId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})();
