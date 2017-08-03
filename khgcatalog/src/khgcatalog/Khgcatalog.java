/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khgcatalog;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author kgills
 */
public class Khgcatalog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        
        System.out.println("Enter y to recreate catalog files, this is permenant!");
        String input = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
        
        if(!input.equals("y")) {
            System.out.println("Leaving catalog intact");
            System.exit(0);
        }
        
        System.out.println("Recreating catalog");
        
        // Create the database catalog files tables and columns
        String tablesCatalogFile = "../data/catalog/khgbase_tables.tbl";
        String columnsCatalogFile = "../data/catalog/khgbase_columns.tbl";
        int pageSize = 512;


        try {
            RandomAccessFile tableFile = new RandomAccessFile(tablesCatalogFile, "rw");
            tableFile.setLength(0);
            tableFile.setLength(pageSize);

            tableFile.seek(0);

            // Set the file type
            tableFile.write(0x0D);

            // Set the number of records
            tableFile.write(0x02);

            // Write the start of content
            tableFile.write(0x01);
            tableFile.write(0xD3);
            
            // Initialize the right page pointer
            for (int i = 0; i < 4; i++) {
                tableFile.write(0xFF);
            }
            
            // Write the pointers to the content
            tableFile.write(0x01);
            tableFile.write(0xE9);
            tableFile.write(0x01);
            tableFile.write(0xD3);

            // Seek to the correct position
            tableFile.seek(pageSize - 45);

            // Write the payload length
            tableFile.write(0x00);
            tableFile.write(0x10);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x02);

            // Write the number of columns
            tableFile.write(0x01);

            // Write the data types of the columns
            tableFile.write(0x1A);

            tableFile.writeBytes("khgbase_tables");

            // Write the payload length
            tableFile.write(0x00);
            tableFile.write(0x11);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x01);

            // Write the number of columns
            tableFile.write(0x01);

            // Write the data types of the columns
            tableFile.write(0x1B);

            tableFile.writeBytes("khgbase_columns");

            tableFile.close();

            /* Write the header to the table file */
        } catch (IOException e) {
            System.out.println(e);
        }
        
        try {
            RandomAccessFile tableFile = new RandomAccessFile(columnsCatalogFile, "rw");
            tableFile.setLength(0);
            tableFile.setLength(pageSize);

            tableFile.seek(0);

            // Set the file type
            tableFile.write(0x0D);

            // Set the number of records
            tableFile.write(0x08);

            // Write the start of content
            tableFile.write(0x00);
            tableFile.write(0xA4);
            
            // Initialize the right page pointer
            for (int i = 0; i < 4; i++) {
                tableFile.write(0xFF);
            }
            
            // Write the pointers to the content
            tableFile.write(0x01);
            tableFile.write(0xDB);
            
            tableFile.write(0x01);
            tableFile.write(0xB0);
            
            tableFile.write(0x01);
            tableFile.write(0x8A);
            
            tableFile.write(0x01);
            tableFile.write(0x5E);
            
            tableFile.write(0x01);
            tableFile.write(0x31);
            
            tableFile.write(0x01);
            tableFile.write(0x06);
            
            tableFile.write(0x00);
            tableFile.write(0xD1);
            
            tableFile.write(0x00);
            tableFile.write(0xA4);

            // Seek to the correct position
            tableFile.seek(pageSize - 348);
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x27);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x08);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0xB);
            tableFile.write(0xC + 0x4);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("is_nullable");        // 0xB
            tableFile.writeBytes("TEXT");               // 4
            tableFile.write(0x06);                      // 1
            tableFile.writeBytes("NO");                 // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x2F);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x07);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0x10);
            tableFile.write(0xC + 0x7);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("ordinal_position");   // 0x10
            tableFile.writeBytes("TINYINT");            // 7
            tableFile.write(0x05);                      // 1
            tableFile.writeBytes("NO");                 // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x25);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x06);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0x9);
            tableFile.write(0xC + 0x4);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("data_type");          // 0x9
            tableFile.writeBytes("TEXT");               // 4
            tableFile.write(0x04);                      // 1
            tableFile.writeBytes("NO");                 // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x27);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x05);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0xB);
            tableFile.write(0xC + 0x4);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("column_name");        // 0xB
            tableFile.writeBytes("TEXT");               // 4
            tableFile.write(0x03);                      // 1
            tableFile.writeBytes("NO");                 // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x26);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x04);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0xA);
            tableFile.write(0xC + 0x4);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("table_name");         // 0xA
            tableFile.writeBytes("TEXT");               // 4
            tableFile.write(0x02);                      // 1
            tableFile.writeBytes("NO");                 // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x20);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x03);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xF);
            tableFile.write(0xC + 0x5);
            tableFile.write(0xC + 0x3);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_columns");    // 0xF
            tableFile.writeBytes("rowid");              // 0x5
            tableFile.writeBytes("INT");                // 3
            tableFile.write(0x01);                      // 1
            tableFile.writeBytes("NO");                 // 2

            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x25);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x02);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xE);
            tableFile.write(0xC + 0xA);
            tableFile.write(0xC + 0x4);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_tables"); // 0xE
            tableFile.writeBytes("table_name");     // 0xA
            tableFile.writeBytes("TEXT");           // 4
            tableFile.write(0x02);                  // 1
            tableFile.writeBytes("NO");             // 2
            
            // Write the payload length
            tableFile.write(0x00);     // Starts with the number of columns byte
            tableFile.write(0x1F);

            // Write the ROW ID
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x00);
            tableFile.write(0x01);

            // Write the number of columns
            tableFile.write(0x05);

            // Write the data types of the columns
            tableFile.write(0xC + 0xE);
            tableFile.write(0xC + 0x5);
            tableFile.write(0xC + 0x3);
            tableFile.write(0x04);
            tableFile.write(0xC + 0x2);

            tableFile.writeBytes("khgbase_tables"); // 0xE
            tableFile.writeBytes("rowid");          // 5
            tableFile.writeBytes("INT");            // 3
            tableFile.write(0x01);                  // 1
            tableFile.writeBytes("NO");             // 2
            
            tableFile.close();

            /* Write the header to the table file */
        } catch (IOException e) {
            System.out.println(e);
        }
        
       try {
            Files.delete(Paths.get("../data/user_data/db_test.tbl"));
        } catch (NoSuchFileException | DirectoryNotEmptyException x) {
            
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
}
