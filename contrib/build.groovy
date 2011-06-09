new AntBuilder().sequential {
	
	//setup
	source = "src"
	
	webinfDir = "war/WEB-INF"
	webinfClasses = "${webinfDir}/classes"
	webinfLib = "${webinfDir}/lib"
	
	testsDir = "tests"	
	testsGroovy = "${testsDir}/groovy"
	testsResources = "${testsDir}/resources" 
	
	libDir = "lib"
	buildDir = "build"

    gaeHome = System.getenv("APPENGINE_HOME")
    if(!gaeHome) {
        println "To build your file you have to set 'APPENGINE_HOME' env variable pointing to your GAE SDK."
        System.exit(1)
    }

	//clean
	delete dir:buildDir
	delete dir:webinfClasses

	//create directories
	mkdir dir:buildDir
    mkdir dir:webinfClasses

	//copy resources from src
	copy todir: webinfClasses, {
		fileset dir: source, {
			exclude name: "**/*.groovy"
		}
	}

	//compile
	taskdef name: "groovyc", classname: "org.codehaus.groovy.ant.Groovyc"
	
	groovyc srcdir: source, destdir: webinfClasses, {
		exclude name:'GroovletUnderSpec.groovy'
		exclude name:'GroovletMockLogger.groovy'
		exclude name:'GaelykUnitSpec.groovy'
		classpath {
			fileset dir: webinfLib, {
		    	include name: "*.jar"
			}
			fileset dir: libDir, {
				include name: "*.jar"
			}
            fileset dir: "${gaeHome}/lib/", {
                include name: "**/*.jar"
            }
			pathelement path: source
		}
		javac source: "1.5", target: "1.5", debug: "on"
	}
	
	groovyc srcdir: testsGroovy, destdir: buildDir, {
	    classpath {
            fileset dir: libDir, {
                include name: "**/*.jar"
            }
			fileset dir: webinfLib, {
		    	include name: "*.jar"
			}
			pathelement path: webinfClasses
			pathelement path: source
	    }
		javac source: "1.5", target: "1.5", debug: "on"
	}
	
	copy todir: buildDir, {
	    fileset dir: testsResources
	}
	
	//run spock specs
	path(id:'spock.classpath'){
	    fileset(dir:libDir){
	        include(name:"**/*.jar")
	    }
	}
	
	junit fork:"true", forkmode:"once", {
	    classpath {
			fileset dir: webinfLib, {
		    	include name: "*.jar"
			}
            fileset dir: libDir, {
                include name: "**/*.jar"
            }
			pathelement path: webinfClasses
			pathelement path: buildDir
	    }
	    batchtest {
	        fileset dir: buildDir,{
	            custom classname:"org.spockframework.buildsupport.ant.SpecClassFileSelector", 
					classpathref:"spock.classpath"
	        }
	    }
	    formatter type:"brief", usefile:"false"
	}
	
}

