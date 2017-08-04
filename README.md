# khgbase
Custom database similar to MySQL and SQLite. Runs in Java. 

# khgcatalog
Will delete the db_test.tbl file and recreate the catalog tables.

# Building
	ant -Dnb.internal.action.name=build jar

# Running
	java -jar dist/khgbase.jar
	java -jar dist/khgcatalog.jar

# Areas of improvement
Folder path input for stored files.
B+ tree for record indicies.
Sorted insert.
Update command. Currently requires users to delete and insert to update.