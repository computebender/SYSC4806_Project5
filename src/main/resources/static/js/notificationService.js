const notificationService = (function() {
    function addNotification(message, type) {
        const notificationContainer = $('#notification-container');
        const notification = $('<div></div>').addClass('alert').addClass(type).text(message);
        notificationContainer.append(notification);

        setTimeout(function() {
            notification.animate({opacity: 0}, 500, function() {
                $(this).css({opacity: 0});
                $(this).animate({height: 0}, 200, function() {
                    $(this).remove();
                });
            });
        }, 5000);
    }

    $(document).ready(function() {
        const notificationContainer = $('<div></div>').attr('id', 'notification-container');
        $('body').prepend(notificationContainer);

        notificationContainer.css({
            'position': 'fixed',
            'top': '20px',
            'left': '50%',
            'transform': 'translateX(-50%)',
            'z-index': '9999'
        });
    });

    return {
        addSuccessNotification: function(message) {
            addNotification(message, 'alert-success');
        },
        addErrorNotification: function(message) {
            addNotification(message, 'alert-danger');
        }
    };
})();
