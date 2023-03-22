const listingService = (function() {

    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        getAllListings: function(successCallback) {
            $.ajax({
                url: "/api/listings",
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        createListing: function(listing, successCallback) {
            $.ajax({
                url: "/api/listings",
                type: "POST",
                data: JSON.stringify(listing),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        getListingById: function(listingId,successCallback) {
            $.ajax({
                url: "/api/listings/" + listingId,
                type: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },

        updateListingById: function(listingId, partialListing, successCallback) {
            $.ajax({
                url: "/api/listings/" + listingId,
                type: "PUT",
                data: JSON.stringify(partialListing),
                contentType: "application/json",
                success: successCallback,
                error: handleAjaxError
            });
        },

        deleteListingById: function(listingId, successCallback) {
            $.ajax({
                url: "/api/listings/" + listingId,
                type: "DELETE",
                success: successCallback,
                error: handleAjaxError
            });
        }

    };

})();
