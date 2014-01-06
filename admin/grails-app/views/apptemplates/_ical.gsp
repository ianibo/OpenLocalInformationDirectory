<div class="ical">
  <input id="sessionRRuleNLP" type="text" name="${tfname}" class="rruleinput input-xlarge"/>
  <input id="sessionRRule" type="hidden" name="${fname}"/>
  <button onClick="validateRRuleNlp('#sessionRRuleNLP','#sessionRRule')">Validate</button>
     
  <p>Examples:
    <ul>
      <li>Every weekday</li>
      <li>Every 2 weeks on Tuesday</li>
      <li>Every week on Monday, Wednesday</li>
      <li>Every month on the 2nd last Friday for 7 times</li>
      <li>Every 6 months</li>
    </ul>
  </p>
  <p id="Error"></p>
</div>


