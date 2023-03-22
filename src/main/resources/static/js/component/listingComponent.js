$.fn.bookCard = function (title, author, price, coverUrl, listingId) {
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
    const buttonContainer = $('<div>').addClass('button-container').css(buttonContainerStyle);
    const buttonEl = $('<a>').addClass('btn btn-primary').text('View Listing').attr('href', '/listing?listing=' + listingId);
    const addCartButton = $('<a>').addClass('btn btn-primary').text('Add to Cart').attr('id', 'add-cart-btn');


    body.append(titleEl);
    body.append(authorEl);
    body.append(priceEl);
    buttonContainer.append(buttonEl);
    buttonContainer.append(addCartButton);
    body.append(buttonContainer);
    imageContainer.append(image);
    card.append(imageContainer);
    card.append(body);

    return card;
};
