package tli

import tli.*;

class TliSession {

  static belongsTo = [owner:DirectoryEntry]

  String name
  // computed ical recurrence rule - see http://www.kanzaki.com/docs/ical/rrule.html and http://keith-wood.name/icalendar.html
  String startTime
  String endTime
  String rrule
  String trrule
  String description
  TliLocation location

  static mapping = {
    description type:'text'
  }

  static constraints = {
    owner(nullable:false, blank:false)
    name(nullable:true, blank:false)
    startTime(nullable:true, blank:false)
    endTime(nullable:true, blank:false)
    rrule(nullable:true, blank:false, maxSize:256)
    trrule(nullable:true, blank:false, maxSize:512)
    description(nullable:true, blank:false)
    location(nullable:true, blank:false)
  }
}

