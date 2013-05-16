package groovyx.gaelyk.spock

import spock.util.mop.Use

import com.google.appengine.api.datastore.Entity

@Use(ReverseCategory)
class CategoriesSpec extends GaelykUnitSpec {
	
	
	def setup(){
		groovlet 'pkg/inPkg.groovy'
	}
	
	def "Gaelyk Category works"(){
		Entity en = new Entity("test")
		en.setProperty("test", "test")
		
		expect:
		en.save()
	}
	
	def "Reverse category works"(){
		expect:
		"Hello".myReverse() == "olleH"
	}

}


class ReverseCategory {
	static String myReverse(String string){
		string.reverse()
	}
}