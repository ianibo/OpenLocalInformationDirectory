function validateRRuleNlp(sourceRuleTextControlId, targetControlId) {
    console.log("Convert");
    var $source_rule=$(sourceRuleTextControlId);
    var target_control=$(targetControlId);

    console.log("try");
    try {
      console.log("1 from \""+$source_rule.val()+"\"");
      var rule = RRule.fromText($source_rule.val());
      console.log("2");
      target_control.value = rule.toString();
      console.log("3:"+target_control.value);
    } catch (_error) {
      e = _error;
      console.log(e.stack)
      $("#error").append($('<pre class="error"/>').text('=> ' + String(e || null)));
      return;
    }
}

// Create an rrulr editor by enhancing an existing div containing the necessary sub elements and data props
function rruleEditor() {
}
