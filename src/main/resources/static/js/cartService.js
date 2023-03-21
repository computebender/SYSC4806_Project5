const shoppingCartService = (function() {
    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }
    return {
        // Define the function to send an AJAX request to create a new shopping cart
        createCart: function(userId, successCallback) {
        $.ajax({
            url: `/api/carts/${userId}`,
            method: 'POST',
            success: successCallback,
            error: handleAjaxError
        });
    },

        // Define the function to send an AJAX request to add an item to a shopping cart
        addItem: function(userId, listing, successCallback) {
        $.ajax({
            url: `/api/carts/${userId}/add-item`,
            method: 'POST',
            data: JSON.stringify(listing),
            contentType: 'application/json',
            success: successCallback,
            error: handleAjaxError
        });
    },
        // Define the function to send an AJAX request to remove an item from a shopping cart
        removeItem: function(userId, cartItemId,successCallback) {
        $.ajax({
            url: `/api/carts/${userId}/remove-item`,
            method: 'DELETE',
            data: JSON.stringify(cartItemId),
            contentType: 'application/json',
            success: successCallback,
            error: handleAjaxError
        });
    },
        // Define the function to send an AJAX request to get all shopping carts
        getAllShoppingCarts: function() {
        return $.ajax({
            url: "/api/carts",
            method: "GET",
            success: successCallback,
            error: handleAjaxError
        });
    },
        // Define the function to send an AJAX request to get a specific shopping cart by user ID
        getShoppingCartById: function(userId) {
        return $.ajax({
            method: "GET",
            url: "/api/carts/" + userId,
        });
    }

    }
})