function validateRRuleNlp(sourceRuleTextControlId, targetControlId, resultId) {
    console.log("Convert");
    var $source_rule=$(sourceRuleTextControlId);
    var target_control=$(targetControlId);
    var result_control=$(resultId);

    console.log("try");
    try {
      var rule = RRule.fromText($source_rule.val());
      var the_rule = rule.toString();
      target_control.val(the_rule);
      result_control.html(the_rule);
    } catch (_error) {
      e = _error;
      result_control.html($(String(e || null)));
      return;
    }
}

// Create an rrulr editor by enhancing an existing div containing the necessary sub elements and data props
function rruleEditor() {
}
