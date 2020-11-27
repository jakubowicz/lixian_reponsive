package Composant;
 
/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;


public class Word {
  public XWPFDocument document = null;
  public XWPFTable table = null;
  public XWPFTableRow rows = null;
  public XWPFTableCell cell = null;

  public XWPFParagraph paragraph=null;
  public XWPFRun locRun =null;

  public XWPFParagraph paragraph2=null;
  public XWPFRun locRun2 =null;

  public CTTrPr trPr =null;
  public CTHeight ht =null;


  public String filename = "";
  public String directory = "";
  public String location = "";

  public FileOutputStream outStream = null;

public void open(){

}

public void create(){
  this.document = new XWPFDocument();
  this.location = this.getClass().getResource("").getFile();
  this.location=this.location.substring(1);
  int pos=-1;
  pos = this.location.indexOf("WEB-INF");
  if (pos > -1)
    this.location = this.location.substring(0,pos)+this.directory;

    this.location = this.location.replaceAll("%20","\\ ");
}

public void attach(String directory, String filename) {
  this.directory = directory;
  this.filename = filename;

  String fullPathName = this.location + this.directory + "/"+ this.filename;

  try {
  outStream = new FileOutputStream(new File(fullPathName));

  } catch (FileNotFoundException e) {
  e.printStackTrace();
}

}

public void read(){

}

public void write(){

}

public void close(){
  try {
  this.document.write(this.outStream);
    this.outStream.close();
  } catch (FileNotFoundException e) {
  e.printStackTrace();
  } catch (IOException e) {
  e.printStackTrace();
}
}
public static void main(String[] args) throws IOException {

Word myWord = new Word();
myWord.create();

myWord.attach("doc/interface/exportWord","poitest3.docx");

myWord.table = myWord.document.createTable(3, 3);
myWord.rows = null;
myWord.cell = null;

for(int i = 0;i < 3;i++) {
  myWord.rows = myWord.table.getRow(i);
System.out.println("rows:::"+myWord.rows+i);
for(int j = 0; j < 3; j++) {
  myWord.cell = myWord.rows.getCell(j);
  myWord.cell.setText("row "+i+" col "+j);

}
}



myWord.close();
}
}
