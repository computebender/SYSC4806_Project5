const cartService = (function() {
    function handleAjaxError(jqXHR, textStatus, errorThrown) {
        console.log("AJAX error: " + textStatus, errorThrown);
    }

    return {
        createCart: function(id, successCallback) {
            $.ajax({
                url: `/api/carts/${id}`,
                method: 'POST',
                success: successCallback,
                error: handleAjaxError
            });
        },

        // Define the function to send an AJAX request to add an item to a shopping cart
        addItem: function(id, listing, successCallback) {
            $.ajax({
                url: "/api/carts/"+ id +"/add-item",
                method: 'POST',
                data: JSON.stringify(listing),
                contentType: 'application/json',
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to remove an item from a shopping cart
        removeItem: function(id, cartItemId,successCallback) {
        $.ajax({
                url: `/api/carts/${id}/remove-item`,
                method: 'DELETE',
                data: JSON.stringify(cartItemId),
                contentType: 'application/json',
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to remove an item from a shopping cart
        updateItem: function(id, cartId,cartItem,successCallback) {
            $.ajax({
                url: "/api/carts/" +id + "/update-item/" + cartId,
                method: 'PUT',
                data: JSON.stringify(cartItem),
                contentType: 'application/json',
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to get all shopping carts
        getAllShoppingCarts: function(successCallback) {
        $.ajax({
                url: "/api/carts",
                method: "GET",
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to get a specific shopping cart by user ID
        getShoppingCartById: function(id,successCallback) {
        $.ajax({
                method: "GET",
                url: "/api/carts/" + id,
                success: successCallback,
                error: handleAjaxError
        });
        },
        // Define the function to send an AJAX request to get a specific shopping cart by user ID
        getShoppingCartItemById: function(id,cartId,successCallback) {
            $.ajax({
                method: "GET",
                url: "/api/carts/" + id + "/" + cartId,
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to clear a shopping cart
        clearShoppingCart: function(id,successCallback) {
            $.ajax({
                method: "POST",
                url: `/api/carts/${id}/clear`,
                data: JSON.stringify(id),
                success: successCallback,
                error: handleAjaxError
            });
        },
        // Define the function to send an AJAX request to check out shopping cart
        checkoutShoppingCart: function(id,successCallback) {
            $.ajax({
            method: "POST",
            url: `/api/carts/${id}/checkout`,
            data: JSON.stringify(id),
            success: successCallback,
            error: handleAjaxError
        });
    }

    }
})();