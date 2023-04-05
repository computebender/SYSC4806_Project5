$.fn.listingNewCard = function (title, author, coverUrl, id) {

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
    const buttonContainer = $('<div>').addClass('button-container').css(buttonContainerStyle);
    const selectButton = $('<button>').addClass('btn btn-primary').text('Select').attr('id', 'select-btn-'+id).attr("value",id);


    body.append(titleEl);
    body.append(authorEl);
    buttonContainer.append(selectButton);
    body.append(buttonContainer);
    imageContainer.append(image);
    card.append(imageContainer);
    card.append(body);

    return card;
};
