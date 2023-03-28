package Skripsi_IhtiarAlfath;

import java.io.FileInputStream;
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

//package com.mkyong.poi.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.commons.compress.archivers.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.awt.font.NumericShaper.Range;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class crFile {
    
    public static String readFile(String path) throws IOException{
        String data = "";
        String key[] = {};
        try{
            File file=new File(path);    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
            String line;  
            while((line=br.readLine())!=null)  
            {  
                sb.append(line);      //appends line to string buffer  
                sb.append("\n");     //line feed   
            }  
            fr.close();    //closes the stream and release the resources  
            return sb.toString();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
          return "An error occurred.";
        }
    }
        
    public static int saveFile(String fName, String extension, String Text){
        try{
            File myFile = new File(fName + "." +extension);
            if(myFile.createNewFile()){
                try (FileWriter myWriter = new FileWriter(fName + "." +extension)) {
                    String content = Text;
                    myWriter.write(content);
                } catch(Exception exc){
                    System.out.println(exc);
                    return -1;
                }
                System.out.println("Public key saved");          
            } else {
                System.out.println("File already exists.");
                return -1;
            }
           
        }catch(Exception exc){
            System.out.println(exc);
            return -1;
        }
        return 1;
    }
      
    public String readPDF(String src) throws IOException{
        String content = "";
        String text = "";

        try{
            PdfReader reader = new PdfReader(src);
            int numPages = reader.getNumberOfPages();
            int page = 1;
            while (page <= numPages) {
                content = PdfTextExtractor.getTextFromPage(reader, page);
                text += content;
                page++;
            }   
            
            //Check if the document is scanned pdf
            if(text.isEmpty()){
              System.out.println("File is Empty");
              text = "File is Empty";
            }
            System.out.println(text);
            return text;
        } catch(Exception exc){
            System.out.println(exc);
            return exc.toString();
        }
        
        
    }
    
    public int makePDF(String fileName, String extension, String content) throws IOException{
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(fileName + "." + extension)));
            //open
            document.open();
            
            BaseFont bf = BaseFont.createFont("D:\\DataSet\\Font\\ARIALUNI.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Paragraph p = new Paragraph(content, new Font(bf, 12));
            document.add(p);

            //close
            document.close();
            return 1;
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
