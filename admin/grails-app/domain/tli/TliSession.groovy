package tli

import tli.*;

class TliSession {

  static belongsTo = [owner:DirectoryEntry]

  // computed ical recurrence rule - see http://www.kanzaki.com/docs/ical/rrule.html and http://keith-wood.name/icalendar.html
  String rrule

  static constraints = {
    owner(nullable:false, blank:false)
    rrule(nullable:true, blank:false)
  }
}

