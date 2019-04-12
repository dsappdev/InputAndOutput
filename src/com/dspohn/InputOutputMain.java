/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dspohn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;


/**
 *
 * @author douglas.spohn
 */
public class InputOutputMain {
    
      public static void main(String[] args) throws IOException, ClassNotFoundException {
          FileReader fr = null;
          FileWriter fw = null;
          FileInputStream fis = null;
          FileOutputStream fos = null;
          
          // The FileInputStream and FileOutputStream (Byte Streams) - used for handling raw binary data
          try {
              fis = new FileInputStream("src/acntech.png");
              fos = new FileOutputStream("src/acntech2.png");
              int c;
                                                                  
              while((c = fis.read()) != -1) {
                  System.out.print(c);
                  fos.write(c);                  
              }          
              
              
          } finally {
              if(fis != null) {
                  fis.close();                  
              }
              if(fos != null) {
                  fos.close();
              }
          }
         System.out.println("");
          // FileReader (Character Streams) - used for reading text
         try {
             fr = new FileReader("src/input.txt");
             fw = new FileWriter("src/output.txt");
             int temp;
             while((temp = fr.read()) != -1) {
                 System.out.print((char)temp);
                 fw.write(temp);
             }
         } finally {
             if(fr != null) {
                 fr.close();
             }
             if(fw != null) {
                 fw.close();
             }
         }         
         System.out.println("");
         
         // Buffered Streams - Read and write to a memory area known as the buffer.
         // Only calls the native API when the buffer is empty on a read or full on a write.
         // We can convert binary and character streams into buffered streams by passing them to buffered object.
         // This is the only way to create Buffered Streams. They are looking for an input stream/reader or output stream/writer in the constructor.
         BufferedReader inputStream = new BufferedReader(new FileReader("src/input.txt"));
         BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src/acntech.png"));
         
         //BufferedInputStream bis = new BufferedInputStream("src/acntech.png"); // does not work. Needs to be like above.
         
         
        // Scanner uses white space to separate tokens. This will list each word on its own line.
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("src/input.txt")));
            while(s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if(s != null) {
                s.close();
            }
        }
        
        s = null;
        double sum = 0;
        try {
            s = new Scanner(new BufferedReader(new FileReader("src/usnumbers.txt")));
            while(s.hasNext()) {
                if(s.hasNextDouble()) {
                    sum += s.nextDouble();
                } else {
                    s.next();
                }
            }
        } finally {
            s.close();
        }
        System.out.println(sum);
        
        int i = 2;
        double r = Math.sqrt(i);
        
        System.out.print("The square root of ");
        System.out.print(i);
        System.out.print(" is ");
        System.out.print(r);
        System.out.println(".");
        
        i = 5;
        r = Math.sqrt(i);
        System.out.println("The square root of " + i + " is " + r + ".");
        
        i = 2;
        r = Math.sqrt(i);
        System.out.format("The square root of %d is %f.%n", i,r);
        
        // %   - Begin format specifier
        // 1$  - Argument Index: explicitly match a designated argument or < to match the previous specifier 
        // +0  - Flags: + always format with a sign, 0 is thge padding character,  - pad on the right, , format number with locale-specific thousands separators. Certian flags cannot be used together.
        // 20  - Width: the minimum width of the formated value.(Looks like it includes the plus sign and the decimal characters)
        // .10 - Precision: For floating point values, max width of the formatted value. The number of decimal places.
        // f   - The conversion. 
        System.out.format("%f, %1$+020.10f %n", Math.PI);
        System.out.format("%1$+020.3f", Math.PI);
        System.out.format("%1$020.3f", Math.PI);
        
        /* // No Console in NetBeans or just on this system? Will have to look into that later.
        Console c = System.console();
        if(c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        String login = c.readLine("Enter your login: ");
            char[] oldPassword = c.readPassword("Enter your old password: ");
        if(verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char[] newPassword1 = c.readPassword("Enter your new password: ");
                char[] newPassword2 = c.readPassword("Enter new password again: ");
                noMatch = ! Arrays.equals(newPassword1, newPassword2);
                if(noMatch) {
                    c.format("Passwords don't match. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while(noMatch);
        }
        Arrays.fill(oldPassword, ' ');
        */
        
        // Data Streams
        
        // Create constants for the data.
        final String dataFile = "src/invoicedata.txt";
        final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99};
        final int[] units = {12, 8, 13, 29, 50 };
        final String[] descs = {
            "Java T-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"
        };
        
        DataOutputStream out = null;
        
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
            
            for(int j = 0; j < prices.length; j++) {
                out.writeDouble(prices[j]);
                out.writeInt(units[j]);
                out.writeUTF(descs[j]);
            }
        } finally {
            out.close();
        }
        
        DataInputStream in = null;
        double total = 0.0;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
            double price;
            int unit;
            String desc;
            
            try {
                while(true) {
                    price = in.readDouble();
                    unit = in.readInt();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n", unit, desc, price);
                    total += unit * price;
                }
            } catch(EOFException e) { }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        } finally {
            in.close();
        }
        
        //Object Streams
        final String dataFile2 = "src/invoicedata2.txt";
        final BigDecimal[] prices2 = { 
            new BigDecimal("19.99"), 
            new BigDecimal("9.99"), 
            new BigDecimal("15.99"), 
            new BigDecimal("3.99"), 
            new BigDecimal("4.99")
        };
        final int[] units2 = {12, 8, 13, 29, 50 };
        final String[] descs2 = {
            "Java T-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"
        };
        ObjectOutputStream out2 = null;
        
        try {
            out2 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile2)));
            out2.writeObject(Calendar.getInstance());
            for(int k = 0; k < prices.length; k++) {
                out2.writeObject(prices2[k]);
                out2.writeInt(units2[k]);
                out2.writeUTF(descs2[k]);
            }
        } finally {
            out2.close();
        }
        
        ObjectInputStream in2 = null;
        BigDecimal total2 = new BigDecimal(0);
                
        try {
            in2 = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile2)));
            Calendar date = null;
            BigDecimal price2;
            int unit2;
            String desc2;
            
            date = (Calendar) in2.readObject();
        
            System.out.format("On %tA, %<tB %<te, %<tY:%n", date);
            
            try {
                while(true) {
                    price2 = (BigDecimal)in2.readObject();
                    unit2 = in2.readInt();
                    desc2 = in2.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n", unit2, desc2, price2);
                    total2 = total2.add(price2.multiply(new BigDecimal(unit2)));
                }
            } catch(EOFException e) { }
            System.out.format("For a TOTAL of: $%.2f%n", total2);
        } finally {
            in2.close();
        }
        
        
      // End main()  
      }      
      
      // Dummy verify method. For this example it will always return true.
      static boolean verify(String login, char[] password) {
          return true;
      }
      
      // Dummy change method. For this example it will be void.
      static void change(String login, char[] password) {
          // Modify this method to change the password based on own rules.
          // NOTE: a char[] array is used to get the password out of memory quicker. A string would hold it too long on the heap.
      }
                      
}
