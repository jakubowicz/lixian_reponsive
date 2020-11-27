package Fichier; 
import java.io.*;
import OM.*;
import java.sql.Statement;
import accesbase.Connexion;
import java.sql.ResultSet;
import accesbase.transaction;
import Composant.*;
import java.util.*;

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
public class FichierTexte extends item {
  OM theOM=null;
  public String directory = "";
  public String filename = "";
  public String Basedirectory = "";
  FileWriter ffw=null;
    
  public FichierTexte() {
     this.setBaseDirectory();      
  }
  
  public FichierTexte(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;
      this.setBaseDirectory();
  }  


  public String getTockenOracle(String myStr)
    {
      int pos = -1;
      String myTocken="";
      pos = myStr.indexOf("  ");
      if (pos >=0)
      myStr = myStr.substring(pos+2);

      pos = myStr.indexOf(" ");
      if (pos >=0)
      {
        myTocken= myStr.substring(0,pos);
        myStr = myStr.substring(pos);
      }

      Attribut theAttribute = new Attribut();
      theAttribute.nom=myTocken.replaceAll("\"","");

      myTocken=myStr.replaceAll(" ","").replaceAll(",","");
      theAttribute.nomtypeAttribut=myTocken;


    System.out.println("## "+theAttribute.nom + " :: "+theAttribute.nomtypeAttribut);

    theAttribute.descModule="";
    this.theOM.ListeAttributs.addElement(theAttribute);
    return myTocken;

  }

  public String getTockenMySql(String myStr)
    {
      int pos = -1;
      String myTocken="";

      pos = myStr.indexOf("`");
      if (pos >=0)
      {
        myStr = myStr.substring(pos + 1);
      }

      pos = myStr.indexOf("`");
      if (pos >=0)
      {
        myTocken= myStr.substring(0,pos);
        myStr = myStr.substring(pos);
      }

      Attribut theAttribute = new Attribut();
      theAttribute.nom=myTocken;

      pos = myStr.indexOf(" ");
      if (pos >=0)
      {
        myStr = myStr.substring(pos + 1);
      }

      pos = myStr.indexOf(" ");
      if (pos >=0)
      {
        myTocken= myStr.substring(0,pos);
        myStr = myStr.substring(pos);
      }

      theAttribute.nomtypeAttribut=myTocken;


    System.out.println("## "+theAttribute.nom + " :: "+theAttribute.nomtypeAttribut);

    theAttribute.descModule="";
    this.theOM.ListeAttributs.addElement(theAttribute);
    return myTocken;

  }

public String getTocken(String myStr)
  {
    int pos = -1;
    String myTocken="";
    pos = myStr.indexOf("[");
    if (pos >=0)
    myStr = myStr.substring(pos);

  pos = myStr.indexOf("]");
  if (pos >=0)
  {
    myTocken= myStr.substring(1,pos);
    myStr = myStr.substring(pos);
  }

  Attribut theAttribute = new Attribut();
  theAttribute.nom=myTocken;

  pos = myStr.indexOf("[");
  if (pos >=0)
  myStr = myStr.substring(pos);

  pos = myStr.indexOf("]");
  if (pos >=0)
  {
    myTocken= myStr.substring(1,pos);
    myStr = myStr.substring(pos);
  }

  theAttribute.nomtypeAttribut=myTocken;

  myTocken="";
  pos = myStr.indexOf("(");
  if (pos >=0)
  myStr = myStr.substring(pos);

  pos = myStr.indexOf(")");
  if (pos >=0)
  {
    myTocken= myStr.substring(1,pos);
    myStr = myStr.substring(pos);
    theAttribute.nomtypeAttribut+="("+myTocken+")";
  }

  theAttribute.descModule="";
  this.theOM.ListeAttributs.addElement(theAttribute);

  return myTocken;

  }

  public boolean move(File fichier1, File fichier2) throws IOException
  {


    // D�placer le fichier vers le disque D:

    boolean resultat = fichier1.renameTo(new File(fichier2, fichier1.getName()));
    if (resultat)
     {
      System.out.println("Le fichier "+fichier1.getAbsolutePath()+" a �t� d�plac� vers==> "+fichier2.getName());
    }else
        System.out.println ("Impossible de d�placer "+fichier1.getAbsolutePath()+" ==> "+fichier2.getAbsolutePath());

      return resultat;
  }

public void listerRepertoire(File repertoire){

  String [] listefichiers;

  int i;
  listefichiers=repertoire.list();
  for(i=0;i<listefichiers.length;i++){
  //if(listefichiers[i].endsWith(".java")==true){
  if(true){

  System.out.println(listefichiers[i]);// on choisit la sous chaine - les 5 derniers caracteres ".java"
  }
  }
}

public void readOracle(String myFile){
  StringBuilder contents = new StringBuilder();

  try {

  BufferedReader input = new BufferedReader(new FileReader(myFile));
  try {
  String ligne = null;

  boolean beginTable=false;
  boolean endTable=false;

  while (( ligne = input.readLine()) != null){
    int pos = -1; //

    if (!beginTable)
    {
      pos = ligne.indexOf("CREATE TABLE");
      if (pos >= 0) {
        ligne = input.readLine();
        ligne = input.readLine();
        beginTable = true;

      }
    }
    if (beginTable)
    {

      if (ligne.equals(")"))
      {
        endTable=true;
        beginTable=false;
      }
      else
      {
        String myAttribute = getTockenOracle(ligne);
      }
    }
    if (endTable) continue;
  }
  }
  finally {
  input.close();
  }
  }
  catch (IOException ex){
  ex.printStackTrace();
}
}

  public void readMySql(String myFile){
    StringBuilder contents = new StringBuilder();

    try {

    BufferedReader input = new BufferedReader(new FileReader(myFile));
    try {
    String ligne = null;

    boolean beginTable=false;
    boolean endTable=false;

    while (( ligne = input.readLine()) != null){
      int pos = -1; //

      if (!beginTable)
      {
        pos = ligne.indexOf("CREATE TABLE");
        if (pos >= 0) {
          ligne = input.readLine();
          beginTable = true;

        }
      }
      if (beginTable)
      {

        if (ligne.indexOf("ENGINE") > 0)
        {
          endTable=true;
          beginTable=false;
        }
        else
        {
          String myAttribute = getTockenMySql(ligne);
        }
      }
      if (endTable) continue;
    }
    }
    finally {
    input.close();
    }
    }
    catch (IOException ex){
    ex.printStackTrace();
}
}

public void open(){
    try{
    File ff=new File(this.Basedirectory + "/" + this.filename); // définir l'arborescence
    ff.createNewFile();
    ffw=new FileWriter(ff);

} catch (Exception e) {
    e.printStackTrace();
}
   
} 

public void close(){
    try{
    ffw.close(); // fermer le fichier à la fin des traitements
} catch (Exception e) {}
   
} 

public void write(String ligne){
    try{
    ffw.write(ligne);  // écrire une ligne dans le fichier resultat.txt
    ffw.write("\n"); // forcer le passage à la ligne
} catch (Exception e) {}
   
}  
public void readSqlServer(String myFile){
  StringBuilder contents = new StringBuilder();

  try {

  BufferedReader input = new BufferedReader(new FileReader(myFile));
  try {
  String ligne = null;

  while (( ligne = input.readLine()) != null){
    int pos = -1; //
    System.out.println("ligne="+ligne);
    pos = ligne.indexOf("] [");
    if (pos >=0)
    {
      System.out.println(ligne);
      String myAttribute=getTocken(ligne);
      System.out.println("myAttribute="+myAttribute);

    }
    contents.append(ligne);
  //contents.append(System.getProperty("lign�"));
  }
  }
  finally {
  input.close();
  }
  }
  catch (IOException ex){
  ex.printStackTrace();
}
}

public String readTypeBd(String myFile){

  int nb = 0;
  String TypeBd="Oracle";
  StringBuilder contents = new StringBuilder();

  int isSQL=-1;

  try {

  BufferedReader input = new BufferedReader(new FileReader(myFile));
  try {
  String ligne = null;

  while (( ligne = input.readLine()) != null){
    System.out.println("## "+ligne);
    int pos = -1; //
    pos = ligne.indexOf("] [");
    if (pos >=0)
    {
      TypeBd="SqlServer";
    }
    pos = ligne.indexOf("CREATE TABLE");
    if (pos >=0)
    {
      isSQL = 1;
    }

    pos = ligne.indexOf("CREATE TABLE `");
    if (pos >=0)
    {
      isSQL = 2;
    }

    nb++;
  }
  }
  finally {
  input.close();
  }
  }
  catch (IOException ex){
  ex.printStackTrace();
}

  if (!TypeBd.equals("SqlServer") )
  {
    if ( isSQL == 1)
      TypeBd = "Oracle";
    else if ( isSQL == 2)
      TypeBd = "MySql";
    else
      TypeBd = "Echec de lecture du fichier: "+ myFile;

  }

  return TypeBd;
}

  public static void importOM(String nomBase, Connexion myCnx,  Statement st) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";





    String myFile ="";
    FichierTexte fichiertexte = new FichierTexte();

    System.out.println("1----------------------");

    File repertoire = new File(fichiertexte.Basedirectory);

    String [] listefichiers;

    int i;
    int pos=-1;

    System.out.println("4----------------------"+fichiertexte.Basedirectory);


    listefichiers=repertoire.list();
    for(i=0;i<listefichiers.length;i++){

      if(listefichiers[i].toLowerCase().endsWith(".sql")==true){
        pos = listefichiers[i].indexOf(".");
        System.out.println(listefichiers[i]);
        int idObjetMetier = Integer.parseInt(listefichiers[i].substring(0,pos));
        System.out.println(""+idObjetMetier);

        fichiertexte.theOM = new OM(idObjetMetier);
        String TypeBd=fichiertexte.readTypeBd(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        System.out.println("5----------------------"+TypeBd);
        if (TypeBd.equals("SqlServer"))
        {
          fichiertexte.readSqlServer(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        else if (TypeBd.equals("MySql"))
        {
          fichiertexte.readMySql(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        else if (TypeBd.equals("Oracle"))
        {
          fichiertexte.readOracle(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        System.out.println("------ TypeBd= "+TypeBd);

        System.out.println("-----------------------------------------");
        for (int j=0; j < fichiertexte.theOM.ListeAttributs.size();j++)
        {
          Attribut theAttribut = (Attribut)fichiertexte.theOM.ListeAttributs.elementAt(j);
          //theAttribut.dump();
        }

        String result;
        transaction theTransaction = new transaction ("EnregOM");
        theTransaction.begin(myCnx.nomBase,myCnx,st);
        fichiertexte.theOM.bd_InsertListeAttributs(myCnx.nomBase,myCnx,st,theTransaction.nom) ;

        boolean resultMove=false;
        try{
          boolean resultatDelete= new File(fichiertexte.Basedirectory+"/ok"+"/"+listefichiers[i]).delete();
          resultMove=fichiertexte.move(new File(fichiertexte.Basedirectory+"/"+listefichiers[i]), new File(fichiertexte.Basedirectory+"/ok"));
        }
        catch (Exception e)
        {

        }

      fichiertexte.theOM.dateImport = new Date().toString();
      fichiertexte.theOM.diagnostic = "TypeBd= "+TypeBd + " :: deplacement="+resultMove;
      fichiertexte.theOM.bd_UpdateCrImport(myCnx.nomBase,myCnx,st,theTransaction.nom);

      theTransaction.commit(nomBase,myCnx,st);
      }


    }

    //myCnx.Unconnect(st);
      
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
    this.Basedirectory = location;
  }
  
  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();




    String myFile ="";
    FichierTexte fichiertexte = new FichierTexte();

    System.out.println("1----------------------");


    File repertoire = new File(fichiertexte.Basedirectory);

    String [] listefichiers;

    int i;
    int pos = -1;

    System.out.println("4----------------------"+fichiertexte.Basedirectory);


    listefichiers=repertoire.list();
    for(i=0;i<listefichiers.length;i++){

      if(listefichiers[i].toLowerCase().endsWith(".sql")==true){
        pos = listefichiers[i].indexOf(".");
        System.out.println(listefichiers[i]);
        int idObjetMetier = Integer.parseInt(listefichiers[i].substring(0,pos));
        System.out.println(""+idObjetMetier);

        fichiertexte.theOM = new OM(idObjetMetier);
        String TypeBd=fichiertexte.readTypeBd(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        System.out.println("5----------------------"+TypeBd);
        if (TypeBd.equals("SqlServer"))
        {
          fichiertexte.readSqlServer(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        else if (TypeBd.equals("MySql"))
        {
          fichiertexte.readMySql(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        else if (TypeBd.equals("Oracle"))
        {
          fichiertexte.readOracle(fichiertexte.Basedirectory+"/"+listefichiers[i]);
        }
        System.out.println("------ TypeBd= "+TypeBd);

        System.out.println("-----------------------------------------");
        for (int j=0; j < fichiertexte.theOM.ListeAttributs.size();j++)
        {
          Attribut theAttribut = (Attribut)fichiertexte.theOM.ListeAttributs.elementAt(j);
          //theAttribut.dump();
        }

        String result;
        transaction theTransaction = new transaction ("EnregOM");
        theTransaction.begin(myCnx.nomBase,myCnx,st);
        fichiertexte.theOM.bd_InsertListeAttributs(myCnx.nomBase,myCnx,st,theTransaction.nom) ;

        boolean resultMove=false;
        try{
          boolean resultatDelete= new File(fichiertexte.Basedirectory+"/ok"+"/"+listefichiers[i]).delete();
          resultMove=fichiertexte.move(new File(fichiertexte.Basedirectory+"/"+listefichiers[i]), new File(fichiertexte.Basedirectory+"/ok"));
        }
        catch (Exception e)
        {

        }

      fichiertexte.theOM.dateImport = new Date().toString();
      fichiertexte.theOM.diagnostic = "TypeBd= "+TypeBd + " :: deplacement="+resultMove;
      fichiertexte.theOM.bd_UpdateCrImport(myCnx.nomBase,myCnx,st,theTransaction.nom);

      theTransaction.commit(base,myCnx,st);
      }



    }

    myCnx.Unconnect(st);
  }
}
