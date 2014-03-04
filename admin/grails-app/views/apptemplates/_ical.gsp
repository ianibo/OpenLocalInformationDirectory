<div class="ical">
  <div class="input-group">
    <input id="sessionRRuleNLP" 
           type="text" 
           name="${tfname}" 
           class="form-control rruleinput" onkeyup="validateRRuleNlp('#sessionRRuleNLP','#sessionRRule','#Error')"/>
    <span id="Error" class="input-group-addon"></span>
    <input id="sessionRRule" type="hidden" name="${fname}"/>
  </div>


  <p>Examples:
    <ul>
      <li>Every weekday</li>
      <li>Every 2 weeks on Tuesday</li>
      <li>Every week on Monday, Wednesday</li>
      <li>Every month on the 2nd last Friday for 7 times</li>
      <li>Every month on the first friday and third wednesday</li>
    </ul>
  </p>
</div>


