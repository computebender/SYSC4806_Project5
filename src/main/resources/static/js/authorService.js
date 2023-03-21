const authorService = (function() {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllAuthors: function(successCallback) {
            $.ajax({
                url: "/api/authors",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createAuthor: function(author, successCallback) {
            $.ajax({
                url: "/api/authors",
                type: "POST",
                data: JSON.stringify(author),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getAuthorById: function(authorId, successCallback) {
            $.ajax({
                url: "/api/authors/" + authorId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getAuthorBooksById: function(authorId, successCallback) {
            $.ajax({
                url: "/api/authors/" + authorId +"/books",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateAuthorById: function(authorId, partialAuthor, successCallback) {
            $.ajax({
                url: "/api/authors/" + authorId,
                type: "PUT",
                data: JSON.stringify(partialAuthor),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteAuthorById: function(authorId, successCallback) {
            $.ajax({
                url: "/api/authors/" + authorId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})();
