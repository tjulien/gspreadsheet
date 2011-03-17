Using the standard gdata spreadsheet api is absolutely brutal b/c somewhere along the line someone decided that the best api into a spreadsheet was an atom feed.  No, the best api into a spreadsheet is really a glorified 2-d array with some niceties for columns names.

Pre-reqs:
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet -Dversion=3.0 -Dfile=./lib/gdata-spreadsheet-3.0.jar -Dpackaging=jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet-meta -Dversion=3.0 -Dfile=./lib/gdata-spreadsheet-meta-3.0.jar -Dpackaging=jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-core -Dversion=1.0 -Dfile=./lib/gdata-core-1.0.jar -Dpackaging=jar -DgeneratePom=true