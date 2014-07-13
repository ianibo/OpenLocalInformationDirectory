//= require jquery
//= require bootstrap
//= require bootstrap-editable.min
//= require bootstrap-datetimepicker.min
//= require editable
//= require moment.min
//= require select2.min
//= require oms.min.js
//= require markerclusterer.js

if (typeof jQuery !== 'undefined') {
        (function($) {
                $('#spinner').ajaxStart(function() {
                        $(this).fadeIn();
                }).ajaxStop(function() {
                        $(this).fadeOut();
                });
        })(jQuery);
}


if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
