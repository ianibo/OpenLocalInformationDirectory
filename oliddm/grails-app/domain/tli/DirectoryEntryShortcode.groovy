package tli

import me.ianibbo.common.*


class DirectoryEntryShortcode extends Shortcode {

  DirectoryEntry dirent
  
  static constraints = {
  }

  static generateShortcode(entry, base, canonical) {

    log.debug("generateShortcode(${entry},${base},${canonical})");

    def result = null

    try {
      DirectoryEntryShortcode.withTransaction { status ->
        def base_shortcode = base.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
        def shortcode = base_shortcode
        def located_record = DirectoryEntryShortcode.executeQuery("select count(o) from tli.DirectoryEntryShortcode as o where o.shortcode = ?",shortcode)[0]
  
        int ctr = 0;
        log.debug("Attempt ${shortcode}");

        // See if initial_guess is already used
        while ( located_record > 0 ) {
          shortcode = base_shortcode+'_'+(++ctr)
          log.debug("No good, Attempt ${shortcode}");
          located_record = DirectoryEntryShortcode.executeQuery("select count(o) from tli.DirectoryEntryShortcode as o where o.shortcode = ?",shortcode)[0]
        }

        result = new DirectoryEntryShortcode(dirent:entry,shortcode:shortcode,canonical:canonical)

        if ( result.save(flush:true) ) {
          log.debug("Saved ${result.id}");
        }
        else {
          log.debug("*** Problem saving directory entry shortcode : ${result.errors} ***");
        }
      }
    }
    catch ( Exception e ) {
      System.err.println("Problem:${e}");
      log.error("Problem",e);
    }

    return result
  }
}
