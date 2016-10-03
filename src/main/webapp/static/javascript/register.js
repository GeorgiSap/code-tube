$(document).ready(function() {
    $('#found_site').on('change', function() {
       $(this).val() == "other" ? $('#specify').closest('.form-group').show() : $('#specify').closest('.form-group').hide();
    })
})