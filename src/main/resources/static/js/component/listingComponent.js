$.fn.listingCard = function (title, author, price, coverUrl, listing, bookstore) {
    function addToCart(listing) { // Change: Accept listing as an argument
        userService.getCurrentUser(function (user) {
            console.log(user)
            cartService.addItem(user.shoppingCart.id, listing, function () {
                notificationService.addSuccessNotification('Added to cart.');
            });
        });
    }

    const defaultCover = 'https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif';
    const cardStyle = {
        'width': '14rem'
    }
    const imageContainerStyle = {
        'position': 'relative',
        'overflow': 'hidden',
        'height': '16rem'
    }
    const imageStyle = {
        'position': 'absolute',
        'top': '0',
        'left': '0',
        'width': '100%',
        'min-height': '100%',
        'object-fit': 'cover',
    }
    const buttonContainerStyle = {
        'display': 'flex',
        'flex-direction': 'column',
        'justify-content': 'space-between',
        'gap': '5px'
    }

    const card = $('<div>').addClass('card').css(cardStyle);
    const imageContainer = $('<div>').addClass('image-container').css(imageContainerStyle);
    const image = $('<img>').addClass('card-img-top').attr('src', coverUrl || defaultCover).css(imageStyle);
    const body = $('<div>').addClass('card-body');
    const titleEl = $('<h5>').addClass('card-title').text(title);
    const authorEl = $('<p>').addClass('card-text').text(author);
    const priceEl = $('<p>').addClass('card-text').text('$' + price);
    const soldByEl = $('<p>').addClass('card-text').text('Sold by: ' + bookstore.bookstoreName);
    const buttonContainer = $('<div>').addClass('button-container').css(buttonContainerStyle);
    const buttonEl = $('<a>').addClass('btn btn-primary').text('View Listing').attr('href', '/listing?listingId=' + listing.id);
    const addCartButton = $('<button>').addClass('btn btn-primary').text('Add to Cart').attr('id', 'add-cart-btn').click(function() {
        addToCart(listing);
    });

    if(listing.copies === 0){
        addCartButton.prop('disabled',true).addClass('disabled');
        addCartButton.text('Out of Stock')
    }

    body.append(titleEl);
    body.append(authorEl);
    body.append(priceEl);
    body.append(soldByEl);
    buttonContainer.append(buttonEl);
    buttonContainer.append(addCartButton);
    body.append(buttonContainer);
    imageContainer.append(image);
    card.append(imageContainer);
    card.append(body);

    return card;
};
