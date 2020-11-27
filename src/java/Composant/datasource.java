/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Composant;
import General.Utils;
import Organisation.Service;
import PO.LignePO;
import Projet.Brief;
import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Joël
 */
public class datasource extends item{
    public String table="";
    public String directory="";
    public String filename="";
    public Vector ListeFieldsColumn= new Vector(10);
    public Vector ListeRows= new Vector(10);
    public int idTemplate = -1;
    public String sens="";
    
    public Vector listeTypes = new Vector(10);      
    
    private ResultSet rs = null;
    
    public datasource(int id){
        this.id= id;
    }    
    
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT     nom, type, ordre, [table], directory, filename, [desc], idTemplate, sens FROM  datasource WHERE id = "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString("nom");
      this.type = rs.getString("type");
      this.ordre = rs.getInt("ordre");
      this.table = rs.getString("table");
      this.directory = rs.getString("directory");
      this.filename = rs.getString("filename");  
      this.desc = rs.getString("desc");
      this.idTemplate = rs.getInt("idTemplate");
      this.sens = rs.getString("sens");
      
    
      


      } catch (SQLException s) {s.getMessage(); }
  }
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT count(*) as nb";
    req+= " FROM         Field ";
    req+= " WHERE     idDatasource ="+ this.id;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      nb = rs.getInt(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  
  if (nb > 0) {
    myError.Detail = "Il existe des Fields";
    myError.cause = -2;
    return myError;
  }  
  
    req = "DELETE FROM datasource WHERE (id   = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [datasource]";
    req+= "           ([nom]";
    req+= "            ,[desc]";
    req+= "            ,[ordre]";
    req+= "            ,[type]";
    req+= "            ,[table]";
    req+= "            ,[directory]";
    req+= "            ,[filename]";
    req+= "            ,[idTemplate]";
    req+= "            ,[sens]";
    req+= ")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'";
    req+=",";
    req+= "'"+mydesc+"'";
    req+=",";
    req+= "'"+this.ordre+"'";
    req+=",";
    req+= "'"+this.type+"'";
    req+=",";    
    req+= "'"+this.table+"'";
    req+=",";
    req+= "'"+this.directory+"'";
    req+=",";
    req+= "'"+this.filename+"'";
    req+=",";
    req+= "'"+this.idTemplate+"'";
    req+=",";
    req+= "'"+this.sens+"'"; 
    req+= "            )";

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause ==  -1) return myError;

      req="SELECT  TOP (1) id FROM   datasource ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  } 
  
  
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.desc != null)
    {

      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
        if (c != 34)
          {
            description += this.desc.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
     }

    req = "UPDATE [datasource]";
    req+= "   SET [nom] = "+ "'"+this.nom+ "'";
    req+=",";    
    req+= "      [desc] = "+ "'"+description+ "'";
    req+=",";    
    req+= "      [ordre] = "+ "'"+this.ordre+ "'";
    req+=",";    
    req+= "      [table] = "+ "'"+this.table+ "'";
    req+=","; 
    req+= "      [directory] = "+ "'"+this.directory+ "'";
    req+=","; 
    req+= "      [filename] = "+ "'"+this.filename+ "'";
    req+=","; 
    req+= "      [type] = "+ "'"+this.type+ "'";
    req+=",";    
    req+= "      [idTemplate] = "+ "'"+this.idTemplate+ "'";
    req+=","; 
    req+= "      [sens] = "+ "'"+this.sens+ "'";   
    req+= "  WHERE id = "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  } 
  public void read(){
       

  }  
  public int read(String nomBase,Connexion myCnx, Statement st){
      //this.getListeRows( nomBase, myCnx,  st);
    int nb_read = 0;
      this.getListeField( nomBase, myCnx,  st);
      if (this.type.equals("Table"))
      {
          nb_read= this.readTable( nomBase, myCnx,  st);
      }
      else if (this.type.equals("document Excel"))
      {
          nb_read=this.readXLSX();
      }
      else if (this.type.equals("document Excel 97"))
      {
          nb_read=this.readXLS();
      }
      
      return nb_read;

  }
   
  
  public void getListeField(String nomBase,Connexion myCnx, Statement st)
  {
     
    ResultSet rs = null;
    req = "SELECT   id, nom, type, position, width, [desc], ordre, idDatasource, idLinkedField, ListRules, isDelete FROM  Field WHERE idDatasource = "+this.id;
    req+= " ORDER BY position";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { 
        while (rs.next()) {
        field theField = new field(rs.getInt("id"));
        theField.nom = rs.getString("nom");
        theField.type = rs.getString("type");
        theField.position = rs.getInt("position");
        theField.width = rs.getInt("width");           
        theField.desc = rs.getString("desc");
        theField.ordre = rs.getInt("ordre");
        theField.idDatasource = rs.getInt("idDatasource");
        theField.idLinkedField = rs.getInt("idLinkedField");
        theField.str_rules = rs.getString("ListRules");
        if (theField.str_rules == null) theField.str_rules = "";
        theField.isDelete = rs.getInt("isDelete");
        
        this.ListeFieldsColumn.addElement(theField);
        }

      } catch (SQLException s) {s.getMessage(); }
  }          
      
  
  private int readTable(String nomBase,Connexion myCnx, Statement st){
    int nb_read = 0;     
      req = "SELECT ";
      

      for (int i=0; i < this.ListeFieldsColumn.size(); i++)
      {
          field theField = (field)this.ListeFieldsColumn.elementAt(i);
          if (i > 0) req+=",";
          req+= theField.nom;
      }
      req += " FROM " + this.table;
      
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try {
    while (rs.next()) {
        
      row theRow = new row();      
      for (int i=0; i < this.ListeFieldsColumn.size(); i++)
      {
          field theField = (field)this.ListeFieldsColumn.elementAt(i);
          value theValue = new value();
          if (theField.type.equals("Integer"))
          {
             
              theValue.valueInt = rs.getInt(theField.nom);
          }
          else if (theField.type.equals("Float"))
          {
              theValue.valueFloat = rs.getFloat(theField.nom);
          }          
          else if (theField.type.equals("String"))
          {
              theValue.valueString = rs.getString(theField.nom);
          }
          else if (theField.type.equals("Date"))
          {
              theValue.valueDate = rs.getDate(theField.nom); 
          }
          theRow.ListeFieldsValue.addElement(theValue);
      } 
      this.ListeRows.addElement(theRow);
      
    }

  } catch (Exception s) {s.getMessage(); }
      return this.ListeRows.size();
  } 
  
  private int readXLSX(){
    if (this.ListeFieldsColumn.size() == 0) return 0;
    this.ListeRows.removeAllElements();
    int Ligne = 1;
    
    this.setBaseDirectory(); 
    String theFilename = this.directory +"/"+this.filename;    
   
  try {
        FileInputStream fileIn = new FileInputStream(theFilename);
        XSSFWorkbook wb = new XSSFWorkbook (fileIn); 
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow rowExcel = sheet.getRow(Ligne);      
    while (rowExcel != null) {
        
      row theRow = new row();      
      for (int i=0; i < this.ListeFieldsColumn.size(); i++)
      {
          field theField = (field)this.ListeFieldsColumn.elementAt(i);
          value theValue = new value();
          theValue.idField = theField.id;
          if (theField.type.equals("Integer"))
          {
            try{
                 theValue.valueInt = (int)Float.parseFloat(rowExcel.getCell(theField.position -1).toString()) ;
            }catch (Exception e){}               
          }
          else if (theField.type.equals("Float"))
          {
            try{
                 theValue.valueFloat = Float.parseFloat(rowExcel.getCell(theField.position -1).toString()) ;
            }catch (Exception e){} 
          }          
          else if (theField.type.equals("String"))
          {
            try{
                 theValue.valueString = rowExcel.getCell(theField.position -1).toString() ;
            }catch (Exception e){} 
          }
          else if (theField.type.equals("Date"))
          {
            try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
                String theDate = rowExcel.getCell(theField.position -1).toString();
                if (theDate.indexOf("-") != -1)
                {
                    String[] ligneDate = theDate.split("-");
                    if (theDate.indexOf("janv") != -1)
                    {
                       ligneDate[1] = "01" ;
                    }
                    else if (theDate.indexOf("fevr") != -1)
                    {
                       ligneDate[1] = "02" ;
                    }  
                    else if (theDate.indexOf("mars") != -1)
                    {
                       ligneDate[1] = "03" ;
                    } 
                    else if (theDate.indexOf("avri") != -1)
                    {
                       ligneDate[1] = "04" ;
                    } 
                    else if (theDate.indexOf("mai") != -1)
                    {
                       ligneDate[1] = "05" ;
                    } 
                    else if (theDate.indexOf("juin") != -1)
                    {
                       ligneDate[1] = "06" ;
                    } 
                    else if (theDate.indexOf("juil") != -1)
                    {
                       ligneDate[1] = "07" ;
                    } 
                    else if (theDate.indexOf("aout") != -1)
                    {
                       ligneDate[1] = "08" ;
                    } 
                    else if (theDate.indexOf("sept") != -1)
                    {
                       ligneDate[1] = "09" ;
                    } 
                    else if (theDate.indexOf("octo") != -1)
                    {
                       ligneDate[1] = "10" ;
                    } 
                    else if (theDate.indexOf("nove") != -1)
                    {
                       ligneDate[1] = "11" ;
                    } 
                    else if (theDate.indexOf("dece") != -1)
                    {
                       ligneDate[1] = "12" ;
                    }                     
                    theDate = ligneDate[0] + "/" + ligneDate[1] + "/" + ligneDate[2];
                    theValue.valueDate = simpleDateFormat.parse(theDate);                   
                }
                else
                {
                    theValue.valueDate = simpleDateFormat.parse(theDate);
                }
                //
                //theValue.valueDate = simpleDateFormat.parse("18/07/2017");
            }catch (Exception e){} 
          }
          theRow.ListeFieldsValue.addElement(theValue);
      } 
      this.ListeRows.addElement(theRow);
      Ligne++;
      rowExcel = sheet.getRow(Ligne); 
      
    }
      if (fileIn != null)
      fileIn.close();  
      
  } catch (Exception s) {s.getMessage(); }
  
    return this.ListeRows.size();
      
  }
  
  private int readXLS(){
      
    if (this.ListeFieldsColumn.size() == 0) return 0;
    this.ListeRows.removeAllElements();
    int Ligne = 1;
    
    this.setBaseDirectory(); 
    String theFilename = this.directory +"/"+this.filename;    
   
  try {
        FileInputStream fileIn = new FileInputStream(theFilename);        
        POIFSFileSystem fs = new POIFSFileSystem(fileIn);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow rowExcel = sheet.getRow(Ligne);        
    while (rowExcel != null) {
        
      row theRow = new row();      
      for (int i=0; i < this.ListeFieldsColumn.size(); i++)
      {
          field theField = (field)this.ListeFieldsColumn.elementAt(i);
          value theValue = new value();
          theValue.idField = theField.id;
          if (theField.type.equals("Integer"))
          {
            try{
                 theValue.valueInt = (int)Float.parseFloat(rowExcel.getCell(theField.position -1).toString()) ;
            }catch (Exception e){}               
          }
          else if (theField.type.equals("Float"))
          {
            try{
                 theValue.valueFloat = Float.parseFloat(rowExcel.getCell(theField.position -1).toString()) ;
            }catch (Exception e){} 
          }          
          else if (theField.type.equals("String"))
          {
            try{
                 theValue.valueString = rowExcel.getCell(theField.position -1).toString() ;
            }catch (Exception e){} 
          }
          else if (theField.type.equals("Date"))
          {
            try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
                theValue.valueDate = simpleDateFormat.parse(rowExcel.getCell(theField.position -1).toString());
            }catch (Exception e){} 
          }
          theRow.ListeFieldsValue.addElement(theValue);
      } 
      this.ListeRows.addElement(theRow);
      Ligne++;
      rowExcel = sheet.getRow(Ligne); 
      
    }
      if (fileIn != null)
      fileIn.close();  
      
  } catch (Exception s) {s.getMessage(); }
  
    return this.ListeRows.size();
      
}  
  
  public int write(String nomBase,Connexion myCnx, Statement st, String transaction, Vector inListeFieldsColumn, Vector inListeRows,  Vector outListeFieldsColumn){
    int nb_write = 0;      
      this.getListeField(nomBase, myCnx, st);
      if (this.type.equals("Table"))
      {
          nb_write= this.writeTable( nomBase, myCnx,  st,  transaction,inListeFieldsColumn, inListeRows);
      }
      else if (this.type.equals("document Excel"))
      {
          nb_write= this.writeXLSX(inListeFieldsColumn, inListeRows, outListeFieldsColumn);
      }
      else if (this.type.equals("document Excel 97"))
      {
          nb_write= this.writeXLS(inListeFieldsColumn, inListeRows, outListeFieldsColumn);
      }        
    return nb_write;
  } 
  
  private String getClauseDelete(){
      String clause = "";
      
        for (int j=0; j < this.ListeFieldsColumn.size(); j++)
        {
          field theField = (field)this.ListeFieldsColumn.elementAt(j);
          if (theField.isDelete == 1)
          {
              theField.getListRules();
              for (int i=0; i < theField.listRules.size(); i++)
              {
                  item theItem = (item) theField.listRules.elementAt(i);
                  if (clause.equals(""))
                  {
                     clause += " WHERE "; 
                  }
                  else
                  {
                     clause += " AND "; 
                  }                      
                  clause += theField.nom;
                  clause += theItem.type;
                  clause += theItem.valeur;
                  
              }
              
          }
        }
      
      return clause;
  }
  
  private int writeTable(String nomBase,Connexion myCnx, Statement st, String transaction,Vector inListeFieldsColumn, Vector inListeRows){
      int nb_write = 0;
      String clauseWhere= this.getClauseDelete();
      req = "DELETE FROM "+ this.table;;
      req += clauseWhere;
      
      if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);}
      
     
      //if (true) return 1;
      
    for (int i=0; i < inListeRows.size(); i++)
    
    //for (int i=0; i < 1; i++)
      {
        boolean ruleOK=true;
        int nbField = 0;
        int numField = -1;
        row theRow = (row)inListeRows.elementAt(i);   
        value theValue = null;
        req = "INSERT INTO "+ this.table;
        req += " (";

        for (int j=0; j < this.ListeFieldsColumn.size(); j++)
        {
          field theField = (field)this.ListeFieldsColumn.elementAt(j);
                
          numField = this.searchValueByIdField(inListeFieldsColumn, theField.idLinkedField);
          if (numField == -1) continue;
          nbField++;
          if (j> 0) req+=",";
          req+= theField.nom;
        }
        
        req += " )";  
        req+= "VALUES     (";  
      
        for (int j=0; j < this.ListeFieldsColumn.size(); j++)
        {         
          field theField = (field)this.ListeFieldsColumn.elementAt(j);
          if (theField.idLinkedField == -1) continue;
          
          numField = this.searchValueByIdField(inListeFieldsColumn, theField.idLinkedField);

          if (numField == -1) continue;
          
          if (!ruleOK)continue;

          if (j > 0) req+=",";
          theValue = (value)theRow.ListeFieldsValue.elementAt(numField);
          theField.theValue = theValue;
          theField.getListRules();
          ruleOK = theField.isRuleOk();          
          
         
          //Connexion.trace("i="+i+":: j="+j, "req", ""+req);
          if (theField.type.equals("Integer"))
          { 
              req += theValue.valueInt; 
          }
          else if (theField.type.equals("Float"))
          {
              req += theValue.valueFloat;
          }          
          else if (theField.type.equals("String"))
          {
              if (theValue.valueString != null) 
              {
                  if (theValue.valueString.length() > theField.width)
                  {
                      theValue.valueString = theValue.valueString.substring(0, theField.width -1 );
                  }
                  theValue.valueString = theValue.valueString.replaceAll("'", "''");
              }
              else 
                  theValue.valueString = "";
              req += "'"+ theValue.valueString + "'";
          }
          else if (theField.type.equals("Date"))
          {
              String str="";
              if ( theValue.valueDate != null)
              {
                str ="convert(datetime, '"+theValue.valueDate.getDate()+"/"+(theValue.valueDate.getMonth() +1)+"/"+(theValue.valueDate.getYear() +1900)+"', 103)";  
              }
              else
                  str = "NULL";
              req += str;
          }
        
      }       
      
      req += " )"; 
      
      //Connexion.trace("i="+i, "req", ""+req);
      
      if (nbField > 0 && ruleOK)
      {
        if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);}
      }
      nb_write++;
    }
    
    ResultSet rs = null;
    req = "SELECT   count(*) as nb FROM  "+this.table;
    req += clauseWhere;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
            nb_write = rs.getInt("nb");
      } catch (SQLException s) {s.getMessage(); }    
     
      return nb_write;
  } 
  
  private int writeXLS(Vector inListeFieldsColumn, Vector inListeRows, Vector outListeFieldsColumn){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";

    String[] entete = new String[this.ListeFieldsColumn.size()];
    for (int i=0; i < this.ListeFieldsColumn.size();i++)
    {
      field theField=(field)this.ListeFieldsColumn.elementAt(i);
      entete[i] = theField.nom;
    }
            
    Excel theExport = new Excel(this.directory,entete);
    theExport.xl_delete();

    // --------------------------------------------------------------------------------------------------//

    theExport.attach(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    theExport.xl_open_create("FEUILLE1"); // feuille de calcul
    theExport.setStyleDate();

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    Row rowXlss = theExport.sheet.createRow(0);
    theExport.xl_writeEntete(rowXlss,0,theExport.entete);
    
    for (int numRow=0; numRow < inListeRows.size();numRow++)
    {
      row theRowIn = (row)inListeRows.elementAt(numRow);     
      rowXlss = theExport.sheet.createRow(numRow+1);
          
        for (int numCol=0; numCol < theRowIn.ListeFieldsValue.size();numCol++)
        {
          value theValue=(value)theRowIn.ListeFieldsValue.elementAt(numCol);
          field theField_in=(field)inListeFieldsColumn.elementAt(numCol);
          field theField_out=(field)outListeFieldsColumn.elementAt(numCol);
          theExport.sheet.setColumnWidth(numCol, 256 * theField_out.width);
          if (theField_in.type.equals("Integer"))
          {
              theExport.xl_write(rowXlss, numCol, "" + theValue.valueInt);
          }
          else if (theField_in.type.equals("Float"))
          {
              theExport.xl_write(rowXlss, numCol, "" + theValue.valueFloat);
          }          
          else if (theField_in.type.equals("String"))
          {
              theExport.xl_write(rowXlss, numCol, "" + theValue.valueString);
          }
          else if (theField_in.type.equals("Date"))
          {
              theExport.xl_write(rowXlss, numCol, "" + theValue.valueDate);
          }
        }
                
    }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();       
    return inListeRows.size();
      
  }
  
  private int writeXLSX(Vector inListeFieldsColumn, Vector inListeRows, Vector outListeFieldsColumn){
      
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";

    String[] entete = new String[this.ListeFieldsColumn.size()];
    for (int i=0; i < this.ListeFieldsColumn.size();i++)
    {
      field theField=(field)this.ListeFieldsColumn.elementAt(i);
      entete[i] = theField.nom;
    }
            
    Excel theExport = new Excel(this.directory,entete);
    theExport.xl_delete();

    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    theExport.xl_open_create_xlsx("FEUILLE1"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    Row rowXlsx = theExport.sheet_xlsx.createRow(0);
    theExport.xl_writeEntete_xlsx(rowXlsx,0,theExport.entete);
    
    for (int numRow=0; numRow < inListeRows.size();numRow++)
    {
      row theRowIn = (row)inListeRows.elementAt(numRow);     
      rowXlsx = theExport.sheet_xlsx.createRow(numRow+1);
          
        for (int numCol=0; numCol < theRowIn.ListeFieldsValue.size();numCol++)
        {
          value theValue=(value)theRowIn.ListeFieldsValue.elementAt(numCol);
          field theField_in=(field)inListeFieldsColumn.elementAt(numCol);
          field theField_out=(field)outListeFieldsColumn.elementAt(numCol);
          theExport.sheet_xlsx.setColumnWidth(numCol, 256 * theField_out.width);
          if (theField_in.type.equals("Integer"))
          {
              theExport.xl_write_xlsx(rowXlsx, numCol, "" + theValue.valueInt);
          }
          else if (theField_in.type.equals("Float"))
          {
              theExport.xl_write_xlsx(rowXlsx, numCol, "" + theValue.valueFloat);
          }          
          else if (theField_in.type.equals("String"))
          {
              theExport.xl_write_xlsx(rowXlsx, numCol, "" + theValue.valueString);
          }
          else if (theField_in.type.equals("Date"))
          {
              theExport.xl_write_xlsx(rowXlsx, numCol, "" + theValue.valueDate);
          }
        }
                
    }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();       
    return inListeRows.size();
  }   
  
  public void setBaseDirectory(){
    String location = this.getClass().getResource("").getFile();
    
    int pos=-1;  
    pos = location.indexOf(":");
    if (pos > -1)    
        location=location.substring(1); // on est sur du windows
    
     pos=-1;
    pos = location.indexOf("WEB-INF");
    if (pos > -1)
      location = location.substring(0,pos)+this.directory;

    location = location.replaceAll("%20","\\ ");
    //Connexion.trace("xl_delete-1------------","location",""+location);
    this.directory = location;
  } 
  
  public void getListeSousTypeStByTypeSt(String nomBase,Connexion myCnx, Statement st, String typeField ){
    ResultSet rs = null;

   req="SELECT     Field.id, Field.nom";
    req+= " FROM         template INNER JOIN";
    req+= "                       datasource ON template.id = datasource.idTemplate INNER JOIN";
    req+= "                       Field ON datasource.id = Field.idDatasource INNER JOIN";
    req+= "                       datasource AS datasource_1 ON template.id = datasource_1.idTemplate";
    req+= " WHERE     (datasource.sens = 'in') AND (Field.type = '"+typeField+"') AND (datasource_1.id = "+this.id+")";
    req+= " ORDER BY Field.nom";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { 
        while (rs.next()){
            item theItem = new item(rs.getInt("id"));
            theItem.nom = rs.getString("nom");
            this.listeTypes.addElement(theItem);
        }

      } catch (SQLException s) {s.getMessage(); }
  }  
  
 public int searchValueByIdField(Vector inListeFieldsColumn, int idField)
    {
        for (int j=0; j < inListeFieldsColumn.size(); j++)
        {
             field theField = (field)inListeFieldsColumn.elementAt(j);
             if (theField.id ==idField )
             {
                 return j;
             }
            
        }
        return -1;
    }  
    
}
