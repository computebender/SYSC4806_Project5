$.fn.fulllistingCard = function (title, author, publisher, isbn, genres, price, coverUrl, listing, bookstore, copies) {
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
        'width': '100rem',
    }
    const imageContainerStyle = {
        'position': 'relative',
        'overflow': 'hidden',
        'height': '22rem',
        'width': '14rem',
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
        'gap': '5px',
    }

    $('.left-block').css({
        display: 'inline-block',
        width: '20%'
    });

    $('.right-block').css({
        display: 'inline-block',
        width: '80%',
    });


    const card = $('<div>').addClass('d-flex flex-row gap-3').css(cardStyle)
    const imageDiv = $('<div>').addClass('left-block')
    const imageContainer = $('<div>').addClass('image-container').css(imageContainerStyle);
    const image = $('<img>').addClass('card-img-top').attr('src', coverUrl || defaultCover).css(imageStyle);
    const bodyDiv = $('<div>').addClass('right-block')
    const body = $('<div>').addClass('card-body');

    const titleEl = $('<h3>').addClass('card-title').text(title);
    const authorEl = $('<p>').addClass('card-text').text('Author:  ' + author);
    const publisherEl = $('<p>').addClass('card-text').text('Publisher:  ' + publisher);
    const isbnEl = $('<p>').addClass('card-text').text('ISBN:  ' + isbn);
    const genresEl = $('<p>').addClass('card-text').text('Genres:  ' + genres);
    const priceEl = $('<p>').addClass('card-text').text('Price: $'  + price);
    const soldByEl = $('<p>').addClass('card-text').text('Sold by:  ' + bookstore.bookstoreName);
    const copiesEl = $('<p>').addClass('card-text').text('Copies:  ' + copies);

    const buttonContainer = $('<div>').addClass('button-container').css(buttonContainerStyle);
    const addCartButton = $('<button>').addClass('btn btn-primary').text('Add to Cart').attr('id', 'add-cart-btn').click(function() {
        addToCart(listing);
    });


    body.append(titleEl);
    body.append(authorEl);
    body.append(publisherEl);
    body.append(isbnEl);
    body.append(genresEl);
    body.append(priceEl);
    body.append(soldByEl);
    body.append(copiesEl);
    buttonContainer.append(addCartButton);
    body.append(buttonContainer);
    imageContainer.append(image);

    imageDiv.append(imageContainer)
    bodyDiv.append(body)

    card.append(imageDiv);
    card.append(bodyDiv);

    return card;
};


/*
$.fn.fulllistingCard = function (title, author, publisher, isbn, genres, price, coverUrl, listing, bookstore, copies) {
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
        'height': '14rem'
    }
    const imageContainerStyle = {
        'position': 'relative',
        'overflow': 'hidden',
        //'height': '16rem'
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

    const card = $('<div>').addClass('container mt-5');
    const book = $('<h1>').text(title);
    const listingDetails = $('<div>').addClass('row');
    const listingTemplate = $('<div>').addClass('d-flex flex-row gap-3');
    const imageCard = $('<div>').addClass('card h-100');

    const imageContainer = $('<div>').addClass('image-container').css(imageContainerStyle);
    const image = $('<img>').addClass('card-img-top').attr('src', coverUrl || defaultCover).css(imageStyle);

    const listingText = $('<div>').addClass('card flex-grow-1 h-100');
    const body = $('<div>').addClass('card-body');
    const body2 = $('<div>').addClass("d-flex flex-column justify-content-between");

    const textDiv = $('<div>');

    const authorLabel = $('<strong>').text("Author");
    const publisherLabel = $('<strong>').text("Publisher");
    const isbnLabel = $('<strong>').text("ISBN");
    const genresLabel = $('<strong>').text("Genres");
    const priceLabel = $('<strong>').text("Price");
    const locationLabel = $('<strong>').text("Location");
    const copiesLabel = $('<strong>').text("Copies");

    const authorVal = $('<span>').text(author);
    const publisherVal = $('<span>').text(publisher);
    const isbnVal = $('<span>').text(isbn);
    const genresVal = $('<span>').text(genres);
    const priceVal = $('<span>').text(price);
    const locationVal = $('<span>').text(bookstore.bookstoreName);
    const copiesVal = $('<span>').text(copies);

    const pAuthor = $('<p>');
    const pPublisher = $('<p>');
    const pIsbn = $('<p>');
    const pGenres = $('<p>');
    const pPrice = $('<p>');
    const pLocation = $('<p>');
    const pCopies = $('<p>');

    const buttonContainer = $('<div>').addClass('button-container').css(buttonContainerStyle);
    const addCartButton = $('<button>').addClass('btn btn-primary').text('Add to Cart').attr('id', 'add-cart-btn').click(function() {
        addToCart(listing);
    });

    imageContainer.append(image);
    imageCard.append(imageContainer)
    listingTemplate.append(imageCard)

    pAuthor.append(authorLabel)
    pAuthor.append(authorVal)
    textDiv.append(pAuthor)

    pPublisher.append(publisherLabel)
    pPublisher.append(publisherVal)
    textDiv.append(pPublisher)

    pIsbn.append(isbnLabel)
    pIsbn.append(isbnVal)
    textDiv.append(pIsbn)

    pGenres.append(genresLabel)
    pGenres.append(genresVal)
    textDiv.append(pGenres)

    pPrice.append(priceLabel)
    pPrice.append(priceVal)
    textDiv.append(pPrice)

    pLocation.append(locationLabel)
    pLocation.append(locationVal)
    textDiv.append(pLocation)

    pCopies.append(copiesLabel)
    pCopies.append(copiesVal)
    textDiv.append(pCopies)

    body2.append(textDiv)
    body.append(body2)

    buttonContainer.append(addCartButton);
    body.append(buttonContainer);

    listingText.append(body)
    listingTemplate.append(imageCard)
    listingTemplate.append(listingText)

    listingDetails.append(listingTemplate)

    card.append(book)
    card.append(listingDetails);

    return card;
};
*/