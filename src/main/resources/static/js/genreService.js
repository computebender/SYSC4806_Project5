const genreService = (function() {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllGenres: function(successCallback) {
            $.ajax({
                url: "/api/genres",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createGenre: function(genre, successCallback) {
            $.ajax({
                url: "/api/genres",
                type: "POST",
                data: JSON.stringify(genre),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getGenreById: function(genreId, successCallback) {
            $.ajax({
                url: "/api/genres/" + genreId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateGenreById: function(genreId, partialGenre, successCallback) {
            $.ajax({
                url: "/api/genres/" + genreId,
                type: "PUT",
                data: JSON.stringify(partialGenre),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteGenreById: function(genreId, successCallback) {
            $.ajax({
                url: "/api/genres/" + genreId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})();
