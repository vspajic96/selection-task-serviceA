
$(document).ready(function() {

    $("#transaction-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        var $form = $( this ),
            amount =$form.find( "input[name='amount']" ).val(),
            currency = $form.find( "select[name='currency']" ).val();

        // Compose the data in the format that the API is expecting
        var data = {  amount: amount, currency: currency };

        // Send the data using post
        $.ajax({
            url: '/send',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: "false"
        });

    });
});
