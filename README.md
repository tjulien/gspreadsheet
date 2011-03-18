A simpler api into google doc spreadsheets than the standard atom-feed centric api.

Example:
    Spreadsheet s = new Spreadsheet("foo@bar.com", "aw3somepassw0rd", "Spreadsheet title", "Worksheet title");
now you can do things like this:
    List<Map<String, String>> rows = s.getRowView();
which gives you a List of rows, and a Map for each of those rows that contain (column-name -> cell value) mappings.
Or you can use a column based view:
    Map<String, List<String>> columns = s.getColumnView()
which gives you a Map of (column-name -> list of cell values) mappings

Pre-reqs:
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet -Dversion=3.0 -Dfile=./lib/gdata-spreadsheet-3.0.jar -Dpackaging=jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet-meta -Dversion=3.0 -Dfile=./lib/gdata-spreadsheet-meta-3.0.jar -Dpackaging=jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-core -Dversion=1.0 -Dfile=./lib/gdata-core-1.0.jar -Dpackaging=jar -DgeneratePom=true