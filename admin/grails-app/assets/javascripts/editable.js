//= require jquery
//= require bootstrap-editable
//= require bootstrap-datetimepicker.min
//= require editable
//= require moment.min
//= require select2.min
//= require moment.min

$(document).ready(function() {

  if (window.location.hash !== '') 
    $('a[href="' + window.location.hash + '"]').tab('show');

  $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    e.preventDefault()
    window.location.hash = e.target.hash;
    $(this).tab('show')
  })

  $.fn.editable.defaults.mode = 'inline';

  $('.xEditableValue').editable();
  $(".xEditableManyToOne").editable();

  $(".simpleHiddenRefdata").editable({
    url: function(params) {
      var hidden_field_id = $(this).data('hidden-id');
      $("#"+hidden_field_id).val(params.value);
      // Element has a data-hidden-id which is the hidden form property that should be set to the appropriate value
    }
  });

  $(".simpleReferenceTypedown").select2({
    placeholder: "Search for...",
    allowClear: true,
    width:'resolve',
    minimumInputLength: 0,
    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
      url: olidBaseUrl, // Var defined at top of page..
      dataType: 'json',
      data: function (term, page) {
        return {
          format:'json',
          q: term,
          baseClass:$(this).data('domain'),
          filter1:$(this).data('filter1'),
          filter2:$(this).data('filter2'),
          filter3:$(this).data('filter3')
        };
      },
      results: function (data, page) {
        // console.log("resultsFn");
        return {results: data.values};
      }
    },
    initSelection : function (element, callback) {
      var idv=$(element).val();
      console.log("initSelection..%o",idv);
      var txt=$(element).context.dataset.displayvalue;
      var data = {id: idv, text: txt};
      callback(data);
    }
  });

  $('body').on('hidden.bs.modal', '.modal', function () {
    $(this).removeData('bs.modal');
  });

});

