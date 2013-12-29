// $(function(){
$(document).ready(function() {

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
      url: "<g:createLink controller='ajaxSupport' action='lookup'/>",
      dataType: 'json',
      data: function (term, page) {
        return {
          format:'json',
          q: term,
          baseClass:$(this).data('domain'),
          filter1:$(this).data('filter1')
        };
      },
      results: function (data, page) {
        // console.log("resultsFn");
        return {results: data.values};
      }
    },
    initSelection : function (element, callback) {
      var idv=$(element).val();
      console.log("initSelection..%o"+idv,element);
      var txt=$(element).context.dataset.displayvalue;
      var data = {id: idv, text: txt};
      callback(data);
    }
  });

});

