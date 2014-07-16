//= require jquery
//= require bootstrap
//= require bootstrap-editable.min
//= require bootstrap-datetimepicker.min
//= require editable
//= require moment.min
//= require select2.min
//= require oms.min
//= require nlp
//= require markerclusterer

if (typeof jQuery !== 'undefined') {
        (function($) {
                $('#spinner').ajaxStart(function() {
                        $(this).fadeIn();
                }).ajaxStop(function() {
                        $(this).fadeOut();
                });
        })(jQuery);
}
