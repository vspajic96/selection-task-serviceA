
$(document).ready(function() {

    $("#transaction-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        var $form = $(this),
            amount = $form.find("input[name='amount']").val(),
            currency = $form.find("select[name='currency']").val();

        // Compose the data in the format that the API is expecting
        var data = {amount: amount, currency: currency};

        // Send the data using post
        $.ajax({
            url: '/send',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false
        });

        // Get current balance and display it in a table
        $.ajax({
            url: '//' + window.location.hostname + ':8081/balance',
            dataType: 'json',
            async: false,
            data: {get_param: 'amount'},
            success: function (json) {
                $('#table td').parent().remove();
                var account_data = '';
                $.each(json, function (key, value) {
                    account_data += '<tr>';
                    // Format received balance string to display amount in euros as decimal value
                    // instead of cents
                    account_data += '<td>' + value.balance.substring(0, value.balance.length - 2) +
                        '.' + value.balance.substring(value.balance.length - 2, value.balance.length) + '</td>';
                    account_data += '<td>' + value.updatedAt.substring(0, 10) + " " +
                        value.updatedAt.substring(11, 19) + '</td>';

                    account_data += '</tr>';
                });
                $('#table').append(account_data);
            }
        });


    });

});
