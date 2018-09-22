/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_1;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Lab_1_File_Read_Write
{
    public static void main(String args[]) {
        writeToFile("myFile.txt", "1,1,8,13,89,144,21,34,55,2,3,5,");
        String data = readFromFile("myFile.txt");
        String[] dataBroken = data.split(",");

        int[] intData = new int[dataBroken.length];
        for(int i=0;i<dataBroken.length;i++) {
            intData[i] = Integer.parseInt(dataBroken[i]);
        }

        int max = 0;
        for(int i=0;i<intData.length;i++) {
            if(intData[i] > max) {
                max = intData[i];
            }
        }
        String msg = "The largest number found in the file was " + max;
        System.out.println("The largest number found in the file was " + max);
        
        HelloWorld h = new HelloWorld(msg);
        h.setVisible(true);
    }

    public static final class HelloWorld extends JFrame {
        public HelloWorld() {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().add(new JLabel("Hello, World!"));
            pack();
            setLocationRelativeTo(null);
        }

        public HelloWorld(String msg) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().add(new JLabel(msg));
            pack();
            setLocationRelativeTo(null);
        }
    }

    public static void writeToFile(String filename, String msg) {
        FileOutputStream out; // declare a file output object
        PrintStream p; // declare a print stream object

        try {
            // Create a new file output stream connected to "myfile.txt"
            out = new FileOutputStream(filename);

            // Connect print stream to the output stream
            p = new PrintStream( out );
            p.println(msg);
            p.close();
        } catch (Exception e) {
            String errMsg = "Error writing to the file " + filename;
            System.err.println (errMsg);
        }
    }

    public static String readFromFile(String filename) {
        String fileData = "";
        String thisLine;
        try {
            // Generate the file stream to the file
            FileInputStream fin =  new FileInputStream(filename);

            try {
                // Create data input stream to the file stream
                DataInputStream myInput = new DataInputStream(fin);
                try {
                    // Loop through the file one line at a time
                    while((thisLine = myInput.readLine()) != null) {  // while loop begins here
                        fileData += thisLine;
                    }
                } catch (Exception e) {
                    // If the file is unreadable output error message
                    System.out.println("Error: " + e);
                }
            } catch (Exception e) {
                // If the file stream is unaccessable
                System.out.println("Error: " + e);
            }
        } catch (Exception e) {
            // If the file is unopenable
            System.out.println("failed to open file " + filename);
            System.out.println("Error: " + e);
        }
        return fileData;
    }
}