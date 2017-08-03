
package khgbase;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Khgbase {

    /* This can be changed to whatever you like */
    static String prompt = "khgq> ";
    static boolean isExit = false;
    /*
     * Page size for alll files is 512 bytes by default.
     * You may choose to make it user modifiable
     */
    static int pageSize = 512;

    static int numRecordOffset = 1;
    static int firstRecordOffset = 8;
    static int recordOffsetSize = 2;

    static final char RECORD_NULL_TINYINT = 0x00;
    static final char RECORD_NULL_SMALLINT = 0x01;
    static final char RECORD_NULL_INT = 0x02;
    static final char RECORD_NULL_DOUBLE = 0x03;
    static final char RECORD_TINYINT = 0x04;
    static final char RECORD_SMALLINT = 0x05;
    static final char RECORD_INT = 0x06;
    static final char RECORD_BIGINT = 0x07;
    static final char RECORD_REAL = 0x08;
    static final char RECORD_DOUBLE = 0x09;
    static final char RECORD_DATETIME = 0x0A;
    static final char RECORD_DATE = 0x0B;
    static final char RECORD_TEXT = 0x0C;

    static final String RECORD_TINYINT_STR = "TINYINT";
    static final String RECORD_SMALLINT_STR = "SMALLINT";
    static final String RECORD_INT_STR = "INT";
    static final String RECORD_BIGINT_STR = "BIGINT";
    static final String RECORD_REAL_STR = "REAL";
    static final String RECORD_DOUBLE_STR = "DOUBLE";
    static final String RECORD_DATETIME_STR = "DATETIME";
    static final String RECORD_DATE_STR = "DATE";
    static final String RECORD_TEXT_STR = "TEXT";
    static final String RECORD_NULL_STR = "null";

    static final int RECORD_NULL_TINYINT_LEN = 1;
    static final int RECORD_NULL_SMALLINT_LEN = 2;
    static final int RECORD_NULL_INT_LEN = 4;
    static final int RECORD_NULL_DOUBLE_LEN = 8;
    static final int RECORD_TINYINT_LEN = 1;
    static final int RECORD_SMALLINT_LEN = 2;
    static final int RECORD_INT_LEN = 4;
    static final int RECORD_BIGINT_LEN = 8;
    static final int RECORD_REAL_LEN = 4;
    static final int RECORD_DOUBLE_LEN = 8;
    static final int RECORD_DATETIME_LEN = 8;
    static final int RECORD_DATE_LEN = 8;

    static String tablesCatalogTable = "khgbase_tables";
    static String tablesCatalogFile = "../data/catalog/khgbase_tables.tbl";
    static String columnsCatalogTable = "khgbase_columns";
    static String columnsCatalogFile = "../data/catalog/khgbase_columns.tbl";

    /* 
     *  The Scanner class is used to collect user commands from the prompt
     *  There are many ways to do this. This is just one.
     *
     *  Each time the semicolon (;) delimiter is entered, the userCommand 
     *  String is re-populated.
     */
    static Scanner scanner = new Scanner(System.in).useDelimiter(";");

    // TODO: Assuming that we start with a fresh database
    static int tableCount = 2;
    static int columnCount = 8;
    
    public static void main(String[] args) {

        /* Display the welcome screen */
         splashScreen();
        
//        while (!isExit) {
//            System.out.print(prompt);
//            /* toLowerCase() renders command case insensitive */
//            userCommand = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
//            
//            System.out.println(userCommand);
//            parseUserCommand(userCommand);
//        }
        
        
//        test0();
        test1();

        System.out.println("Exiting...");

    }
    
    public static void test1() {
        String userCommand;
        
        userCommand = "show tables";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "create table db_test (rowid int NOT NULL, test_column text NOT NULL)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "show tables";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "drop table db_test";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "show tables";
        System.out.println(userCommand);
        parseUserCommand(userCommand);    
    }
    
    
    public static void test0() {
        String userCommand;
        
        userCommand = "create table db_test (rowid int NOT NULL, test_column text NOT NULL)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from db_test";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from khgbase_tables";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from khgbase_columns";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "insert into table db_test values (1, hello_mate)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "insert into table db_test values (2, suh dude!)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "insert into table db_test values (3, You are not prepared)";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from db_test";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "delete from table db_test where row_id = 2";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from db_test";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "drop table db_test";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from khgbase_tables";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
        userCommand = "select * from khgbase_columns";
        System.out.println(userCommand);
        parseUserCommand(userCommand);
        
    }

    public static void splashScreen() {
        System.out.println(line("-", 80));
        System.out.println("Welcome to KHGBaseLite"); // Display the string.
        System.out.println("\nType \"help;\" to display supported commands.");
        System.out.println(line("-", 80));
    }

    public static String line(String s, int num) {
        String a = "";
        for (int i = 0; i < num; i++) {
            a += s;
        }
        return a;
    }

    public static void help() {
        System.out.println(line("*", 80));
        System.out.println("SUPPORTED COMMANDS");
        System.out.println("All commands below are case insensitive");
        System.out.println();
        System.out.println("\tSELECT * FROM table_name;                        Display all records in the table.");
        System.out.println("\tSELECT * FROM table_name WHERE rowid = <value>;  Display records whose rowid is <id>.");
        System.out.println("\tDROP TABLE table_name;                           Remove table data and its schema.");
        System.out.println("\tHELP;                                            Show this help information");
        System.out.println("\tEXIT;                                            Exit the program");
        System.out.println();
        System.out.println();
        System.out.println(line("*", 80));
    }

    public static class Table {

        String name;
        String fileName;
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnTypes = new ArrayList<>();
        ArrayList<String> columnProps = new ArrayList<>();
    }

    public static class Record {

        String tableName;
        ArrayList<String> values = new ArrayList<>();
        
        // Used by insert to store the type of each value
        ArrayList<Character> types = new ArrayList<>();
    }

    public static class Query {

        String tableName = "";
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnWhere = new ArrayList<>();
        ArrayList<String> columnOps = new ArrayList<>();
        ArrayList<String> columnOpValues = new ArrayList<>();

        // Calculated for the column table in the query method
        ArrayList<Integer> columnOrd = new ArrayList<>();
    }

    public static String addPath(String tableName) {
        // Add the file path
        String tableFileName;
        switch (tableName) {
            case "khgbase_tables":
            case "khgbase_columns":
                tableFileName = "../data/catalog/" + tableName + ".tbl";
                break;
            default:
                tableFileName = "../data/user_data/" + tableName + ".tbl";
        }

        return tableFileName;
    }

    public static void insert(Record record) {
        // ASSUMPTION: The table given in this function actually exists
        // TODO: Going to do a non-sorted insert at first for simplicity
        // TODO: Not going to do any indexing or tree structure at first for simplicity
        //          Will be a unordered linked list of blocks

        String tableFileName = addPath(record.tableName);

        // Calculate the space required for this record
        int spaceRequired = 2 + 2 + 4 + 1; // 2 for the pointer, 2 for the length, 4 for the rowid, 1 for the datalength byte

        spaceRequired += record.values.size() - 1;  // One byte for each value besides the rowid

        // Open the columns table to get the element types
        try {
            // Open the columns table
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(columnsCatalogFile, "r");
            int lastPage = 0;
            int pageCount = 0;

            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int i = 0; i < numRecords; i++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + i * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.readByte();

                    // First byte after the number of columns byte stores the type for the table_name
                    int tableNameLen = tableFile.readByte() - 0xC;
                    int columnNameLen = tableFile.readByte() - 0xC;
                    int dataTypeLen = tableFile.readByte() - 0xC;
                    tableFile.skipBytes(2); // Skip the data types for the ordinal position and is_nullable

                    String tableName = "";
                    for (int j = 0; j < tableNameLen; j++) {
                        tableName += ((char) tableFile.readByte());
                    }
                    String columnName = "";
                    for (int j = 0; j < columnNameLen; j++) {
                        columnName += ((char) tableFile.readByte());
                    }

                    // Get the dataType
                    String dataType = "";
                    for (int j = 0; j < dataTypeLen; j++) {
                        dataType += ((char) tableFile.readByte());
                    }
                    
                    // Get the ordinality
                    int ordinal = tableFile.readByte();

                    // If this table matches add the length of the dataType
                    if (tableName.equals(record.tableName) && !columnName.equals("rowid")) {
                        switch (dataType) {
                            case RECORD_TINYINT_STR:
                                spaceRequired += RECORD_TINYINT_LEN;
                                record.types.add(RECORD_TINYINT);
                                break;
                            case RECORD_SMALLINT_STR:
                                spaceRequired += RECORD_SMALLINT_LEN;
                                record.types.add(RECORD_SMALLINT);
                                break;
                            case RECORD_INT_STR:
                                spaceRequired += RECORD_INT_LEN;
                                record.types.add(RECORD_INT);
                                break;
                            case RECORD_BIGINT_STR:
                                spaceRequired += RECORD_BIGINT_LEN;
                                record.types.add(RECORD_BIGINT);
                                break;
                            case RECORD_REAL_STR:
                                spaceRequired += RECORD_REAL_LEN;
                                record.types.add(RECORD_REAL);
                                break;
                            case RECORD_DOUBLE_STR:
                                spaceRequired += RECORD_DOUBLE_LEN;
                                record.types.add(RECORD_DOUBLE);
                                break;
                            case RECORD_DATETIME_STR:
                                spaceRequired += RECORD_DATETIME_LEN;
                                record.types.add(RECORD_DATETIME);
                                break;
                            case RECORD_DATE_STR:
                                spaceRequired += RECORD_DATE_LEN;
                                record.types.add(RECORD_DATE);
                                break;
                            default:
                                // This is a text type
                                spaceRequired += record.values.get(ordinal-1).length();
                                record.types.add((char)(0xC + record.values.get(ordinal-1).length()));
                                break;
                        }
                    }
                }
            }

            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(tableFileName, "rw");
            int lastPage = 0;
            int pageCount = 0;
            int allColumns = 0;

            while (true) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                } else {
                    lastPage = 0;
                }

                // Calculate the amount of available space in this page
                int spaceTaken = 1 + 1 + 2 + 4 + (2*numRecords);
                int spaceAvail;
                if(contentOffset > 0) {
                    spaceAvail = contentOffset - spaceTaken;
                } else {
                    spaceAvail = pageSize - spaceTaken;
                }
                
                if(spaceRequired <= spaceAvail) {
                    int newOffset;
                    spaceRequired -= 2; // Take off 2 for the record pointer
                    if(numRecords != 0) {
                        newOffset = contentOffset - spaceRequired;   
                    } else {
                        newOffset = pageSize - spaceRequired;
                    }
                    
                    // Add this to the record pointers
                    tableFile.seek(pageStartOffset + firstRecordOffset + numRecords*recordOffsetSize);
                    tableFile.writeShort(newOffset);

                    // Update the content Offset
                    tableFile.seek(numRecordOffset + pageStartOffset + 1);
                    tableFile.writeShort(newOffset);

                    // Increment the number of records
                    tableFile.seek(numRecordOffset + pageStartOffset);
                    tableFile.writeByte(numRecords + 1);
                    
                    // Seek to the start of content - spaceRequired
                    tableFile.seek(pageStartOffset + newOffset);
                    
                    tableFile.writeShort(spaceRequired - 4 - 2); // Record length
                    tableFile.writeInt(Integer.parseInt(record.values.get(0))); // rowid
                    tableFile.writeByte(record.values.size()-1); // number of parameters
                    
                    // Write the types, except for rowid
                    for(int i = 1; i < record.values.size(); i++) {
                        
                        if(record.values.get(i).equals("null")) {
                            // Write the null value for this type
                            switch (record.types.get(i)) {
                                case RECORD_TINYINT:
                                    tableFile.writeByte(RECORD_NULL_TINYINT);
                                    break;
                                case RECORD_SMALLINT:
                                    tableFile.writeByte(RECORD_NULL_SMALLINT);
                                    break;
                                case RECORD_INT:
                                case RECORD_REAL:
                                    tableFile.writeByte(RECORD_NULL_INT);
                                    break;
                                case RECORD_BIGINT:
                                case RECORD_DOUBLE:
                                case RECORD_DATETIME:
                                case RECORD_DATE:
                                    tableFile.writeByte(RECORD_NULL_DOUBLE);
                                    break;
                                default:
                                    // This is a text type
                                    record.types.add((char)(0xC));
                                    break;
                            }
                        } else {
                            tableFile.writeByte(record.types.get(i-1)); // Skip over the rowid    
                        }
                    }
                    
                    // Write the values, excpet for rowid
                    for(int i = 1; i < record.values.size(); i++) {
                        // Write all zeros if "null"
                        if(record.values.get(i).equals("null")) {
                            switch (record.types.get(i)) {
                                case RECORD_TINYINT:
                                    tableFile.writeByte(0);
                                    break;
                                case RECORD_SMALLINT:
                                    tableFile.writeShort(0);
                                    break;
                                case RECORD_INT:
                                case RECORD_REAL:
                                    tableFile.writeInt(0);
                                    break;
                                case RECORD_BIGINT:
                                case RECORD_DOUBLE:
                                case RECORD_DATETIME:
                                case RECORD_DATE:
                                    tableFile.writeLong(0);
                                    break;
                                default:
                                    // This is a text type
                                    break;
                            }
                        } else {
                            switch (record.types.get(i-1)) {
                                case RECORD_TINYINT:
                                    tableFile.writeByte(Integer.parseInt(record.values.get(i)));
                                    break;
                                case RECORD_SMALLINT:
                                    tableFile.writeShort(Integer.parseInt(record.values.get(i)));
                                    break;
                                case RECORD_INT:
                                    tableFile.writeInt(Integer.parseInt(record.values.get(i)));
                                case RECORD_REAL:
                                    tableFile.writeFloat(Float.valueOf(record.values.get(i)));
                                    break;
                                case RECORD_DOUBLE:
                                    tableFile.writeDouble(Double.valueOf(record.values.get(i)));
                                case RECORD_BIGINT:
                                case RECORD_DATETIME:
                                case RECORD_DATE:
                                    tableFile.writeLong(Long.getLong(record.values.get(i)));
                                    break;
                                default:
                                    // This is a text type
                                    tableFile.writeBytes(record.values.get(i));
                                    break;
                            }
                        }
                    }
                     
                    break;
                } else if(lastPage == 1) {
                    // Add another page, pageCount incremented previously in this method
                    tableFile.setLength(pageCount * pageSize);
                    tableFile.seek((pageCount-1)*pageSize);
                    
                    
                    tableFile.writeByte(0x0D); // Page type
                    tableFile.writeByte(0x00); // num records
                    tableFile.writeByte(0x00); // Start of content pointer
                    tableFile.writeByte(0x00);
                    tableFile.writeByte(0xFF); // Next page pointer
                    tableFile.writeByte(0xFF);
                    tableFile.writeByte(0xFF);
                    tableFile.writeByte(0xFF);
                    
                    break;
                } 
                
                // Try the next page
            }

            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void createTable(Table table) {

        byte pageType_leafTable = 0x0D;

        try {
            RandomAccessFile tableFile = new RandomAccessFile(table.fileName, "rw");
            tableFile.setLength(0);
            tableFile.setLength(pageSize);

            tableFile.seek(0);

            // Set the file type
            tableFile.write(pageType_leafTable);

            // Set the number of records
            tableFile.write(0x00);

            // Initialize the start of content and right page pointer
            for (int i = 0; i < 6; i++) {
                tableFile.write(0xFF);
            }

            tableFile.close();

            /* Add this table to the tables table in the catalog */
            Record tableRecord = new Record();
            tableRecord.tableName = "khgbase_tables";
            tableRecord.values.add(Integer.toString(++tableCount));
            tableRecord.values.add(table.name);
            insert(tableRecord);

            /* Add the columns to the column table in the catalog */
            int ord = 1;
            for (int i = 0; i < table.columnNames.size(); i++) {
                Record columnRecord = new Record();

                columnRecord.tableName = "khgbase_columns";
                columnRecord.values.add(Integer.toString(++columnCount));
                columnRecord.values.add(table.name);
                columnRecord.values.add(table.columnNames.get(i));
                columnRecord.values.add(table.columnTypes.get(i).toUpperCase());
                columnRecord.values.add(Integer.toString(ord++));
                if (table.columnProps.get(i).equals("NOT_NULL")) {
                    columnRecord.values.add("NO");
                } else {
                    columnRecord.values.add("YES");
                }

                insert(columnRecord);
            }

            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void select(Query query) {
        // TODO: Currently ignoring the where conditions

        String tableFileName = addPath(query.tableName);

        // Figure out which columns we need to retrieve by getting the 
        // ordinality from the columns table in the catalog file
        try {
            // Open the columns table
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(columnsCatalogFile, "r");
            int lastPage = 0;
            int pageCount = 0;
            int allColumns = 0;
            if (query.columnNames.get(0).equals("*")) {
                allColumns = 1;
                query.columnNames.clear();
            }
            int firstColumn = 1;
            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.readByte();

                    // First byte after the number of columns byte stores the type for the table_name
                    int tableNameLen = tableFile.readByte() - 0xC;
                    int columnNameLen = tableFile.readByte() - 0xC;
                    int dataTypeLen = tableFile.readByte() - 0xC;
                    tableFile.skipBytes(2); // Skip the data types for the ordinal position and is_nullable

                    String tableName = "";
                    for (int i = 0; i < tableNameLen; i++) {
                        tableName += ((char) tableFile.readByte());
                    }
                    String columnName = "";
                    for (int i = 0; i < columnNameLen; i++) {
                        columnName += ((char) tableFile.readByte());
                    }

                    // Skip over the dataType
                    tableFile.skipBytes(dataTypeLen);

                    // Get the ordinality
                    int ordinal = tableFile.readByte();

                    // If this table matches, get the column name and the ordinality
                    if (tableName.equals(query.tableName)) {

                        // Initialize the ordinality to all zeros once we find one of the columns
                        if (firstColumn == 1) {
                            query.columnOrd.clear();
                            for (int i = 0; i < numColumns; i++) {
                                query.columnOrd.add(i, 0);
                            }
                            firstColumn = 0;
                        }
                        if (allColumns == 1) {
                            query.columnNames.add(columnName);
                            query.columnOrd.add(ordinal);
                        } else {
                            // Search through the column names in the query to match the ordinality
                            for (int i = 0; i < query.columnNames.size(); i++) {
                                if (query.columnNames.get(i).equals(columnName)) {
                                    query.columnOrd.set(i, ordinal);
                                }
                            }
                        }
                    }
                }
            }

            // Search through the records in the columns table
            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i < query.columnNames.size(); i++) {

            System.out.print(query.columnNames.get(i));

            if (i != query.columnNames.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("");
        System.out.println(line("-", 80));

        // Open the table
        try {
            // Open the file
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(tableFileName, "r");

            int lastPage = 0;
            int pageCount = 0;
            while (lastPage == 0) {

                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.read();

                    // Save the record types
                    ArrayList<Byte> columnTypes = new ArrayList<>();
                    for (int i = 0; i < numColumns; i++) {
                        columnTypes.add(tableFile.readByte());
                    }

                    int numPrinted = 0;
                    if (query.columnOrd.contains(1)) {
                        // Print the rowid
                        System.out.print(rowid);
                        numPrinted += 1;
                        if (query.columnOrd.size() > 1) {
                            System.out.print(", ");

                        }
                    }

                    // Get the data
                    for (int i = 0; i < numColumns; i++) {

                        int print = 1;
                        int printComma = 0;
                        if (!query.columnOrd.contains(i + 2)) {
                            print = 0;
                        } else {
                            numPrinted += 1;
                        }

                        switch (columnTypes.get(i)) {
                            case RECORD_NULL_TINYINT:
                                tableFile.skipBytes(RECORD_NULL_TINYINT_LEN);
                                if (print == 1) {
                                    System.out.print("NULL");
                                }
                                break;
                            case RECORD_NULL_SMALLINT:
                                tableFile.skipBytes(RECORD_NULL_SMALLINT_LEN);
                                if (print == 1) {
                                    System.out.print("NULL");
                                }
                                break;
                            case RECORD_NULL_INT:
                                tableFile.skipBytes(RECORD_NULL_INT_LEN);
                                if (print == 1) {
                                    System.out.print("NULL");
                                }
                                break;
                            case RECORD_NULL_DOUBLE:
                                tableFile.skipBytes(RECORD_NULL_DOUBLE_LEN);
                                if (print == 1) {
                                    System.out.print("NULL");
                                }
                                break;
                            case RECORD_TINYINT:
                                if (print == 1) {
                                    System.out.print(tableFile.readByte());
                                } else {
                                    tableFile.skipBytes(RECORD_TINYINT_LEN);
                                }
                                break;
                            case RECORD_SMALLINT:
                                if (print == 1) {
                                    System.out.print(tableFile.readShort());
                                } else {
                                    tableFile.skipBytes(RECORD_SMALLINT_LEN);
                                }
                                break;
                            case RECORD_INT:
                                if (print == 1) {
                                    System.out.print(tableFile.readInt());
                                } else {
                                    tableFile.skipBytes(RECORD_INT_LEN);
                                }
                                break;
                            case RECORD_BIGINT:
                                if (print == 1) {
                                    System.out.print(tableFile.readLong());
                                } else {
                                    tableFile.skipBytes(RECORD_BIGINT_LEN);
                                }
                                break;
                            case RECORD_REAL:
                                if (print == 1) {
                                    System.out.print(tableFile.readFloat());
                                } else {
                                    tableFile.skipBytes(RECORD_REAL_LEN);
                                }
                                break;
                            case RECORD_DOUBLE:
                                if (print == 1) {
                                    System.out.print(tableFile.readDouble());
                                } else {
                                    tableFile.skipBytes(RECORD_DOUBLE_LEN);
                                }
                                break;
                            case RECORD_DATETIME:
                                if (print == 1) {
                                    System.out.print(tableFile.readLong());
                                } else {
                                    tableFile.skipBytes(RECORD_DATETIME_LEN);
                                }
                                break;
                            case RECORD_DATE:
                                if (print == 1) {
                                    System.out.print(tableFile.readLong());
                                } else {
                                    tableFile.skipBytes(RECORD_DATE_LEN);
                                }
                                break;
                            default:
                                // This is a text type
                                int dataLen = columnTypes.get(i) - RECORD_TEXT;
                                for (int j = 0; j < dataLen; j++) {
                                    if (print == 1) {
                                        System.out.print((char) tableFile.readByte());
                                    } else {
                                        tableFile.skipBytes(1);
                                    }
                                }
                                break;
                        }
                        if (print == 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println("");
                }
            }

            System.out.println("");
            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void delete(String tableName, int rowid) {
        // Going to delete the records and move down any records above it
        // This will pack this page but could lead to wasted space if this table is spread
        // Across multiple pages

        String tableFileName = addPath(tableName);

        try {
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(tableFileName, "rw");
            int lastPage = 0;
            int pageCount = 0;
            int allColumns = 0;

            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int thisRowid = tableFile.readInt();

                    if (thisRowid == rowid) {

                        // Shift the offsets
                        for (int offset = 0; offset < numRecords; offset++) {
                            tableFile.seek(pageStartOffset + firstRecordOffset + offset * recordOffsetSize);
                            int thisOffset = tableFile.readShort();

                            if (thisOffset == recordOffset) {
                                // Zero out this offset
                                tableFile.seek(pageStartOffset + firstRecordOffset + offset * recordOffsetSize);
                                tableFile.writeShort(0x0000);

                                // Zero out the data
                                tableFile.seek(pageStartOffset + thisOffset);
                                for (int i = 0; i < recordSize + 4 + 2; i++) {
                                    tableFile.writeByte(0x00);
                                }

                            } else if (thisOffset < recordOffset) {
                                // Move the data of the higher pointers

                                // Get the record size
                                tableFile.seek(pageStartOffset + thisOffset);
                                int thisRecordSize = tableFile.readShort();
                                thisRecordSize += 4 + 2;

                                // Copy the data
                                tableFile.seek(pageStartOffset + thisOffset);
                                ArrayList<Byte> tempData = new ArrayList<>();
                                for (int i = 0; i < thisRecordSize; i++) {
                                    tempData.add(tableFile.readByte());
                                }
                                
                                // Zero out the data
                                tableFile.seek(pageStartOffset + thisOffset);
                                for (int i = 0; i < thisRecordSize; i++) {
                                    tableFile.writeByte(0x00);
                                }

                                // Rewrite the data
                                tableFile.seek(pageStartOffset + thisOffset + (recordSize + 4 + 2));
                                for (int i = 0; i < thisRecordSize; i++) {
                                    tableFile.writeByte(tempData.get(i));
                                }

                                // Adjust the pointer
                                thisOffset += (recordSize + 4 + 2); // 2 bytes for the record length, 4 for the rowid
                                tableFile.seek(pageStartOffset + firstRecordOffset + (offset - 1) * recordOffsetSize);
                                tableFile.writeShort(thisOffset);
                                
                                // Zero out the old pointer
                                tableFile.seek(pageStartOffset + firstRecordOffset + offset * recordOffsetSize);
                                tableFile.writeShort(0x0000);
                                
                            }
                        }

                        // Adjust the start of content offset
                        if (numRecords > 1) {
                            // Adjust the content Offset to next offset
                            tableFile.seek(numRecordOffset + pageStartOffset + 1);
                            tableFile.writeShort(contentOffset + (recordSize + 4 + 2));
                        } else {
                            // This page will be empty
                            tableFile.seek(numRecordOffset + pageStartOffset + 1);
                            tableFile.writeShort(0xFFFF);
                        }

                        // Decrement the record counter
                        tableFile.seek(numRecordOffset + pageStartOffset);
                        tableFile.writeByte(numRecords - 1);
                        tableFile.close();
                        return;
                    }
                }
            }

            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void drop(String tableName) {

        // Attempt to delete the file for this table
        String tableFileName = addPath(tableName);
        try {
            Files.delete(Paths.get(tableFileName));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", tableFileName);
            return;
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", tableFileName);
            return;
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            return;
        }

        // Delte this table from the tables table
        try {
            // Open the columns table
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(tablesCatalogFile, "r");
            int lastPage = 0;
            int pageCount = 0;
            int allColumns = 0;

            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.readByte();

                    // First byte after the number of columns byte stores the type for the table_name
                    int tableNameLen = tableFile.readByte() - 0xC;

                    String thisTableName = "";
                    for (int i = 0; i < tableNameLen; i++) {
                        thisTableName += ((char) tableFile.readByte());
                    }

                    // If this record matches the tablename, call the delte function with this rowid
                    if (tableName.equals(thisTableName)) {
                        delete(tablesCatalogTable, rowid);
                        columnCount--;
                    }
                }
            }

            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        // Delete all of the columns of this table in the columns table
        try {
            // Open the columns table
            // ASSUMTION: Assuming that the file exists
            RandomAccessFile tableFile = new RandomAccessFile(columnsCatalogFile, "r");
            int lastPage = 0;
            int pageCount = 0;
            int allColumns = 0;

            ArrayList<Integer> deleteList = new ArrayList<>();
            while (lastPage == 0) {
                // Get the number of records
                int pageStartOffset = pageCount++ * pageSize;
                tableFile.seek(numRecordOffset + pageStartOffset);
                int numRecords = tableFile.readByte();
                int contentOffset = tableFile.readShort();
                int nextPage = tableFile.readInt();

                // Check if there is another block
                if (nextPage == -1) {
                    lastPage = 1;
                }

                // Print all of the records on this page
                for (int record = 0; record < numRecords; record++) {
                    tableFile.seek(pageStartOffset + firstRecordOffset + record * recordOffsetSize);
                    int recordOffset = tableFile.readShort();
                    tableFile.seek(recordOffset + pageStartOffset);

                    int recordSize = tableFile.readShort();
                    int rowid = tableFile.readInt();
                    int numColumns = tableFile.readByte();

                    // First byte after the number of columns byte stores the type for the table_name
                    int tableNameLen = tableFile.readByte() - 0xC;
                    tableFile.skipBytes(4); // Skip the data types for the ordinal position and is_nullable

                    String thisTableName = "";
                    for (int i = 0; i < tableNameLen; i++) {
                        thisTableName += ((char) tableFile.readByte());
                    }

                    // If this record matches the tablename, call the delte function with this rowid
                    if (tableName.equals(thisTableName)) {
                        deleteList.add(rowid);
                    }
                }
            }
            for(int i = 0; i < deleteList.size(); i++) {
                delete(columnsCatalogTable, deleteList.get(i));
            }
            
            tableFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
       
        
        tableCount--;
    }

    public static void parseUserCommand(String userCommand) {

        /* commandTokens is an array of Strings that contains one token per array element 
         * The first token can be used to determine the type of command 
         * The other tokens can be used to pass relevant parameters to each command-specific
         * method inside each case statement */
        ArrayList<String> commandTokens = new ArrayList<>(Arrays.asList(userCommand.split(" ")));

        switch (commandTokens.get(0)) {
            case "select":
                parseSelectString(userCommand);
                break;
            case "drop":
                parseDropString(userCommand);
                break;
            case "create":
                parseCreateString(userCommand);
                break;
            case "insert":
                parseInsertString(userCommand);
                break;
            case "delete":
                parseDeleteString(userCommand);
                break;
            case "show":
                parseShowString(userCommand);
                break;
            case "update":
                parseUpdateString(userCommand);
                break;
            case "help":
                help();
                break;
            case "exit":
                isExit = true;
                break;
            case "quit":
                isExit = true;
            default:
                System.out.println("I didn't understand the command: \"" + userCommand + "\"");
                break;
        }
    }

    public static void parseDropString(String commandString) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
        String tableName = tokens.get(2);
        drop(tableName);
    }

    public static void parseSelectString(String commandString) {

        // TODO: Assuming that we are not getting conditions
        Query query = new Query();

        // Split command string on from to get the column names
        commandString = commandString.replace("select ", "");
        String columnNames = commandString.split("from")[0];
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(columnNames.split(",")));
        String tableName = commandString.split("from")[1].trim();

        query.tableName = tableName;
        // Trim the column names and add to the query
        for (int i = 0; i < columns.size(); i++) {
            columns.set(i, columns.get(i).trim());
            query.columnNames.add(columns.get(i));
        }

        select(query);
    }

    public static void parseCreateString(String commandString) {

        int table_name_pos = 2;
        Table table = new Table();

        // Remove the parens
        commandString = commandString.replace("(", "");
        commandString = commandString.replace(")", "");

        // Convert "NOT NULL" to "NOT_NULL" to make the parsing easier
        commandString = commandString.replace("NOT NULL", "NOT_NULL");

        // Split the string into tokens
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));

        // Remove any tokens with just whitespace
        int token_len = tokens.size();
        for (int i = 0; i < token_len; i++) {
            if (tokens.get(i).toString().trim().length() == 0) {
                tokens.remove(i);
                i--;
                token_len--;
            }
        }

        // Add the file path
        table.name = tokens.get(table_name_pos).toString();
        table.fileName = addPath(table.name);

        // Parse the remainer of the tokens for the column names, types, and properties
        int columnPos = 0;
        for (int i = table_name_pos + 1; i < token_len; i++) {

            if (columnPos == 0) {
                // Add a new columnName
                table.columnNames.add(tokens.get(i).toString());
                columnPos = 1;
            } else if (columnPos == 1) {
                // Add the columnType
                table.columnTypes.add(tokens.get(i).toString());

                if (tokens.get(i).contains(",")) {
                    // The properties is NULL, add the default
                    table.columnProps.add("None");
                    columnPos = 0;
                } else {

                    if (i == token_len - 1) {
                        // Add the none if this is the last token
                        table.columnProps.add("None");
                    }
                    columnPos = 2;
                }

            } else {
                // Add the column Properties
                table.columnProps.add(tokens.get(i).toString().replace(",",""));
                columnPos = 0;
            }
        }

        createTable(table);
    }

    public static void parseInsertString(String commandString) {
         // Remove the parens
        commandString = commandString.replace("(", "");
        commandString = commandString.replace(")", "");

        // Convert "NOT NULL" to "NOT_NULL" to make the parsing easier
        commandString = commandString.replace("NOT NULL", "NOT_NULL");

        // Split the string into tokens
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));

        // Remove any tokens with just whitespace
        int token_len = tokens.size();
        for (int i = 0; i < token_len; i++) {
            if (tokens.get(i).toString().trim().length() == 0) {
                tokens.remove(i);
                i--;
                token_len--;
            }
        }
        
        Record record = new Record();
        record.tableName = tokens.get(3);

        String values = commandString.split("values")[1];
        record.values =  new ArrayList<>(Arrays.asList(values.split(",")));
        
        for(int i = 0; i < record.values.size(); i++) {
            record.values.set(i, record.values.get(i).trim());
        }
        
        // Insert the record
        insert(record);
    }

    public static void parseDeleteString(String commandString) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
        
        String tableName = tokens.get(3);
        int rowid = Integer.parseInt(tokens.get(7));
        
        delete(tableName, rowid);
    }

    public static void parseShowString(String commandString) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
        Query query = new Query();
        query.tableName = tablesCatalogTable;
        query.columnNames.add("table_name");
        
        select(query);
    }

    public static void parseUpdateString(String commandString) {
        System.out.println("STUB: Method for update");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(commandString.split(" ")));
    }
}
