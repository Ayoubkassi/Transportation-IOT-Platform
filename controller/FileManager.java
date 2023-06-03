package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * @author Adriano Tiotsop Fogue
 */
public class FileManager{
    public static final char SEP = File.separatorChar;
    public static final String OUTPUT = "output" + SEP;
    public static final String FILE_ERROR = OUTPUT + "log.txt";
    public static final String FILE = OUTPUT + "data.csv";

    public static void write(String filename, String text){
        File file = new File(filename);
        try{
            if ( !file.exists() )
                file.createNewFile();
                    
            FileWriter fileWriter = new FileWriter(filename,true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(text);
        
            bufferWriter.close();
        }catch(IOException ioe){
            writeError(ioe.getMessage());
        }
    }

    public static void writeError(String error){
        try {
            File file = new File(FILE_ERROR);
        	if ( !file.exists() )
 				file.createNewFile();
                
            FileWriter fileWriter = new FileWriter(FILE_ERROR,true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(error);
            bufWriter.newLine();
            bufWriter.close();
        }catch (IOException ioe){
            System.out.println( ioe.getMessage() );
        }
    }

    public static void delete(String filename){
        File file = new File(filename);
        if( file.exists() )
            file.delete();
    }

}