package tli

class DirectoryEntryShortcode {

  DirectoryEntry dirent

  def getItem() {
    return dirent
  }

  static constraints = {
  }

  static generateShortcode(entry, base) {
    def base_shortcode = base.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
    def shortcode = base_shortcode
    def located_record = domain_class.getClazz().executeQuery("select count(o) from DirectoryEntry as o where o.title = ?",shortcode)[0]
    def result = null

    int ctr = 0;

    // See if initial_guess is already used
    while ( located_record > 0 ) {
      shortcode = base_shortcode+'_'+ctr
      located_record = domain_class.getClazz().executeQuery("select count(o) from ${baseclass} as o where o.${field} = ?",shortcode)[0]
    }

    result = new DirectoryEntryShortcode(dirent:entry,shortcode:shortcode).save()

    return result
  }
}
