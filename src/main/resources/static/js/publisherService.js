const publisherService = (function() {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllPublishers: function(successCallback) {
            $.ajax({
                url: "/api/publishers",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createPublisher: function(publisher, successCallback) {
            $.ajax({
                url: "/api/publishers",
                type: "POST",
                data: JSON.stringify(publisher),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getPublisherById: function(publisherId, successCallback) {
            $.ajax({
                url: "/api/publishers/" + publisherId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getPublisherBooksById: function(publisherId, successCallback) {
            $.ajax({
                url: "/api/publishers/" + publisherId +"/books",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updatePublisherById: function(publisherId, partialPublisher, successCallback) {
            $.ajax({
                url: "/api/publishers/" + publisherId,
                type: "PUT",
                data: JSON.stringify(partialPublisher),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deletePublisherById: function(publisherId, successCallback) {
            $.ajax({
                url: "/api/publishers/" + publisherId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }
    };

})();
