import grails.converters.*

import tli.DirectoryEntry

class BootStrap {

    def init = { servletContext ->

      // log.info "Registering DirectoryEntry Marshaller ..."
      JSON.registerObjectMarshaller (DirectoryEntry) { 
        def result = [:]
      //   it.domainClass.persistentProperties.each { property ->
      //     log.debug("Adding ${property.key}:${property.value}");
      //     result[property.key]=it[property.key]
           result['class'] = it.class.name
           result['id'] = it.id
           result['categories'] = it.categories
           result['collections'] = it.collections
           result['contactEmail'] = it.contactEmail
           result['contactFax'] = it.contactFax
           result['contactName'] = it.contactName
           result['contactTelephone'] = it.contactTelephone
           result['dateCreated'] = it.dateCreated
           result['defaultLocation'] = it.defaultLocation
           result['description'] = it.description
           result['facebook'] = it.facebook
           result['ids'] = it.ids
           result['lastUpdated'] = it.lastUpdated
           result['owners'] = it.owners
           result['poilat'] = it.poilat
           result['poilon'] = it.poilon
           result['registeredCharityNo'] = it.registeredCharityNo
           result['sessions'] = it.sessions
           result['shortcodes'] = it.shortcodes
           result['sourceReference'] = it.sourceReference
           result['status'] = it.status
           result['subjects'] = it.subjects
           result['title'] = it.title
           result['twitter'] = it.twitter
           result['type'] = it.type
           result['uid'] = it.uid
           result['url'] = it.url
         return result;
      }
      // log.info "Finished registering DirectoryEntry Marshaller"

    }
    def destroy = {
    }
}
