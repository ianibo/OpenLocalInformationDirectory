import grails.converters.*

import tli.DirectoryEntry

class BootStrap {

    def init = { servletContext ->

      // log.info "Registering DirectoryEntry Marshaller ..."
      // JSON.registerObjectMarshaller (DirectoryEntry) { 
      //   def result = [:]
      //   it.domainClass.persistentProperties.each { property ->
      //     log.debug("Adding ${property.key}:${property.value}");
      //     result[property.key]=it[property.key]
      //     result['class: "tli.DirectoryEntry",
      //     result['id: 3,
      //     result['categories: [
      //     result['collections: [
      //     result['contactEmail: "info@theschoolrooms.co.uk",
      //     result['contactFax: null,
      //     result['contactName: null,
      //     result['contactTelephone: "0114 285 1920",
      //     result['dateCreated: "2014-05-27T09:02:09Z",
      //     result['defaultLocation: {
      //     result['description']
      //     result['facebook: null,
      //     result['ids: [ ],
      //     result['lastUpdated: "2014-05-27T09:02:09Z",
      //     result['owners: [ ],
      //     result['poilat: null,
      //     result['poilon: null,
      //     result['registeredCharityNo: null,
      //     result['sessions: [
      //     result['shortcodes: [
      //     result['sourceReference: null,
      //     result['status: {
      //     result['subjects: [
      //     result['title: "The School Rooms",
      //     result['twitter: null,
      //     result['type: {
      //     result['uid: "d4957893-84dc-42dd-9edd-6653f717e2a0",
      //     result['url: "http://www.theschoolrooms.co.uk"
      //   }
      //   return result;
      // }
      // log.info "Finished registering DirectoryEntry Marshaller"

    }
    def destroy = {
    }
}
