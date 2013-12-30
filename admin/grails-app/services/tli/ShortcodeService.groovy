package tli

class ShortcodeService {

  def grailsApplication

  def generate(baseclass,field,basevalue) {
    def base_shortcode = basevalue.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
    def shortcode = base_shortcode
    def domain_class = grailsApplication.getArtefact('Domain',baseclass)
    def located_record = domain_class.getClazz().executeQuery("select count(o) from ${baseclass} as o where o.${field} = ?",shortcode)[0]

    int ctr = 0;

    // See if initial_guess is already used
    while ( located_record > 0 ) {
      shortcode = base_shortcode+ctr
      located_record = domain_class.getClazz().executeQuery("select count(o) from ${baseclass} as o where o.${field} = ?",shortcode)[0]
    }

    return shortcode
  }
}

