package tli

class DirectoryEntryShortcode extends Shortcode {

  DirectoryEntry dirent

  
  static constraints = {
  }

  static generateShortcode(entry, base, canonical) {
    def base_shortcode = base.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
    def shortcode = base_shortcode
    def located_record = DirectoryEntryShortcode.executeQuery("select count(o) from tli.DirectoryEntryShortcode as o where o.shortcode = ?",shortcode)[0]
    def result = null

    int ctr = 0;

    // See if initial_guess is already used
    while ( located_record > 0 ) {
      shortcode = base_shortcode+'_'+ctr
      located_record = DirectoryEntryShortcode.executeQuery("select count(o) from tli.DirectoryEntryShortcode as o where o.shortcode = ?",shortcode)[0]
    }

    result = new DirectoryEntryShortcode(dirent:entry,shortcode:shortcode,canonical:canonical).save()

    return result
  }
}
