//= require jquery
//= require bootstrap-editable.min
//= require bootstrap-datetimepicker.min
//= require editable
//= require moment.min
//= require select2.min

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
