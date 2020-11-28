package General; 

import accesbase.*;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.Cookie;
import java.io.*;
import java.awt.*;

import java.util.zip.*;
import java.util.zip.Deflater;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class Utils {
  static Connexion myCnx;
  public static String Day;
  public static String Month;
  public static String Year;

  public static  int nbFichesCreees_mois = 0;
   public static  int  nbFichesModifiees_mois = 0;
   public static    String listeFichesModifiees_mois = "";
   public static String listeFichesCreees_mois = "";

  static String[][] tabMonth = {
      {"Janvier","31","1","2008"},{"F�vrier","29","6","2008"},{"Mars","31","10","2008"},
      {"Avril","30","14","2008"},{"Mai","31","19","2008"},{"Juin","30","23","2008"},
      {"Juillet","31","27","2008"},{"Aout","31","32","2008"},{"Septembre","30","36","2008"},
        {"Octobre","31","41","2008"},{"Novembre","30","45","2008"},{"Decembre","31","49","2008"},

        {"Janvier","31","1","2009"},{"F�vrier","29","6","2009"},{"Mars","31","10","2009"},
        {"Avril","30","14","2009"},{"Mai","31","19","2009"},{"Juin","30","23","2009"},
        {"Juillet","31","27","2009"},{"Aout","31","32","2009"},{"Septembre","30","36","2009"},
        {"Octobre","31","41","2009"},{"Novembre","30","45","2009"},{"Decembre","31","49","2009"},

        {"Janvier","31","1","2010"},{"F�vrier","28","5","2010"},{"Mars","31","9","2010"},
        {"Avril","30","13","2010"},{"Mai","31","18","2010"},{"Juin","30","22","2010"},
        {"Juillet","31","26","2010"},{"Aout","31","31","2010"},{"Septembre","30","35","2010"},
        {"Octobre","31","40","2010"},{"Novembre","30","44","2010"},{"Decembre","31","48","2010"}
};

    public static String[][] new_tabMonth = {
        {"Janvier","31",""},{"Fevrier","29",""},{"Mars","31",""},{"Avril","30",""},{"Mai","31",""},{"Juin","30",""},
        {"Juillet","31",""},{"Aout","31",""},{"Septembre","30",""}, {"Octobre","31",""},{"Novembre","30","45",""},{"Decembre","31",""}
      };

static int Offset = 1;
    public static int theDay=0;
   public static int  theMonth=0;
   public static int  theWeek=0;
  public static int  theYear=0;

  public static Vector ListeProjets = new Vector(10);
  public  static Vector ListeActions = new Vector(10);

  public Utils(int theYear) {

  }
  
   public static String truncate(String str,  int lg){
     if (str == null) 
     {
         str= ""; 
         return "";
     }
     
     if (str.length() <= lg)
         return str;
     else
         return str.substring(0, lg-1)+"...";

 } 

 public static String Abrev(String content){
     if ( content.length()>4) 
       content = content.substring(0,4); 
     return content;
 }
 
 public static String stripHtml(String content){
   content = content.replaceAll("<[^>]+>", "").replaceAll("\n", "").replaceAll("\r","").replaceAll("\u0092","'").replaceAll("\"","&quot;").replaceAll("&nbsp;"," ");
   return content;
 }
 
    public static String stringToHexa(String texte) { 
        int c;//int's equivalent to char 
        char s=' ';//separator 
        //To safe memory - limite gc requests 
        StringBuffer buff = new StringBuffer(texte.length()); 
        for (int i = 0; i < texte.length(); i++) { 
            c=texte.charAt(i); 
            buff.append(Integer.toHexString(c)).append(s); 
        } 
        return buff.toString(); 
    } 

 public static String stripSTI(String content, String strDebut,String strFin){
     if (content == null) return ""; 
     String str_description = "";
     str_description = Utils.stripHtml(content);
     int pos = str_description.indexOf(strDebut);
     if (pos >= 0)
           str_description = str_description.substring(strDebut.length());
     pos = str_description.indexOf(strFin);
     if (pos >= 0)
           str_description = str_description.substring(0,pos);
   return str_description;
 }
 
  public static void getListeStByMonthByYear(String nomBase,Connexion myCnx, Statement st, int month, int year, int lg_str){

    String req = "";
    ResultSet rs=null;

    listeFichesModifiees_mois = "";
    nbFichesCreees_mois = 0;
    nbFichesModifiees_mois = 0;
    listeFichesCreees_mois = "";


    req  = "SELECT DISTINCT idVersionSt,nomSt,versionRefVersionSt FROM VersionSt INNER JOIN St ON VersionSt.stVersionSt = St.idSt INNER JOIN SI ON VersionSt.siVersionSt = SI.idSI AND DATEPART(year,creationVersionSt)="+year+" AND DATEPART(month,creationVersionSt)="+month+" AND (SI.nomSI <> 'Acteurs')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next(); listeFichesCreees_mois += rs.getString(2); nbFichesCreees_mois++;
            while (rs.next()) {	listeFichesCreees_mois += ", "+rs.getString(2); nbFichesCreees_mois++; }
    listeFichesCreees_mois = myCnx.truncate(listeFichesCreees_mois,lg_str);
    } catch (SQLException s) {s.getMessage();}


    req  = "SELECT DISTINCT idVersionSt,nomSt,versionRefVersionSt FROM VersionSt INNER JOIN St ON VersionSt.stVersionSt = St.idSt INNER JOIN SI ON VersionSt.siVersionSt = SI.idSI AND creationVersionst<>derMajVersionSt AND DATEPART(year,derMajVersionSt)="+year+" AND DATEPART(month,derMajVersionSt)="+month+" AND (SI.nomSI <> 'Acteurs')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next(); listeFichesModifiees_mois += rs.getString(2); nbFichesModifiees_mois++;
            while (rs.next()) {
            String nomST=rs.getString(2);
                    //String FullnomST=nomST+versionST;
            String FullnomST=nomST;
            if (listeFichesCreees_mois.indexOf(FullnomST) <0){
            listeFichesModifiees_mois += ", "+FullnomST; nbFichesModifiees_mois++;}
              }
    listeFichesModifiees_mois = myCnx.truncate(listeFichesModifiees_mois,lg_str);
    } catch (SQLException s) {s.getMessage();}


 }

 public static String getToDay(String nomBase,Connexion myCnx, Statement st){

  String toDay = "";

    ResultSet rs = null;
   rs = myCnx.ExecReq(st, nomBase, "SELECT  DAY(GETDATE()) AS day,MONTH(GETDATE()) AS month,YEAR(GETDATE()) AS year");
   try { rs.next();
           theDay= rs.getInt(1);
           theMonth= rs.getInt(2);
           theYear= rs.getInt(3);
           toDay = theDay+"/"+theMonth+"/"+theYear;
} catch (SQLException s) {s.getMessage();}

return toDay;
 }

 public static int getStringWidth(String myString, String myFont, int sizeFont, int Epaisseur){

   BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
 Graphics2D g = image.createGraphics();
 FontMetrics fm = g.getFontMetrics(new Font(myFont, Epaisseur, sizeFont));
 int width = fm.stringWidth(myString);
   return width;

}

 public static int getStringHeight(String myString, String myFont, int sizeFont, int Epaisseur){

   BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
 Graphics2D g = image.createGraphics();
 FontMetrics fm = g.getFontMetrics(new Font(myFont, Epaisseur, sizeFont));
 int height = fm.getAscent();
   return height;

}

 public static String getCurrentDate(String nomBase,Connexion myCnx, Statement st){

  String toDay = "";

    ResultSet rs = null;
    rs = myCnx.ExecReq(st, myCnx.nomBase, "SELECT CONVERT(char(30), CURRENT_TIMESTAMP,111) as toDay");
    try { rs.next();
            toDay= rs.getString(1);

} catch (SQLException s) {s.getMessage();}

return toDay;
 }

 public static String getCurrentDateFrench(){

String myDate = "";
      Calendar calendar = Calendar.getInstance();
      int currentYear = calendar.get(Calendar.YEAR);
      int currentMonth = calendar.get(Calendar.MONTH) + 1;
      int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
      
      String str_currentDay = ""+currentDay;
      String str_currentMonth = "" +currentMonth;
      
      if (currentDay < 10) str_currentDay = "0" + currentDay;
      if (currentMonth < 10) str_currentMonth = "0" + currentMonth;
      
      myDate= ""+str_currentDay+"/"+str_currentMonth+"/"+currentYear;

return myDate;
 }

public static Date addDays (Date myDate, int theDays){
  GregorianCalendar calendar = new java.util.GregorianCalendar();
  calendar.setTime( myDate );
  calendar.add (Calendar.DATE, theDays);

  return calendar.getTime();
}

  public static String getMonth(int Week){
    String  theMonth = "";
    for (int i=0; i < 12; i++)
    {
      if (Week == Integer.parseInt(tabMonth[i][2])) theMonth = tabMonth[i][0];
    }

    return theMonth;
}

  public  String new_getMonth(int Week, int year){
    String  theMonth = "";

  for (int i=0; i < 12; i++)
  {
    if (Week == Integer.parseInt(new_tabMonth[i][2])) theMonth = new_tabMonth[i][0];
  }

  return theMonth;
}

public static String getMonth(int Week, int year){
String  theMonth = "";
if (year < 2008) year=2008;
if (year > 2010) year=2010;
int index = (year - 2008)*12;
for (int i=0; i < 12; i++)
{
  if (Week == Integer.parseInt(tabMonth[i+index][2])) theMonth = tabMonth[i+index][0];
}

return theMonth;
}

  static int getCheck(int year, int month, int day)
  {
    //System.out.println("@@ year="+year);
    //System.out.println("@@ month="+month);
    //System.out.println("@@ day="+day);
    //System.out.println("@@ day + month*12 + year * 365="+(day + month*12 + year * 365));
    
      return day + month*12 + year * 365;
  }
  
  public static int getOffset(int year){
      
    Date myDate = getDateByWeek(1, year);
    int myCheck = 0;
    myCheck = getCheck((myDate.getYear() + 1900), (myDate.getMonth() +1), myDate.getDate()); 


    if ((year == 2013) && (myCheck < getCheck(2012, 12, 31)))
    {     
       return  1;
    }
    if ((year == 2014) && (myCheck < getCheck(2013, 12, 30)))
    {      
       return  1;
    }    
    if ((year == 2015) && (myCheck < getCheck(2014, 12, 29)))
    {  
       return  1;
    }
    else if ((year == 2016) && (myCheck < getCheck(2016, 1, 4)))
    {     
       return  1;
    }
    
    else if ((year == 2017) && (myCheck < getCheck(2017, 1, 2)))
    {       
       return  1;
    }    
    else if ((year == 2018) && (myCheck < getCheck(2018, 1, 1)))
    {       
       return  1;
    }    
    else if ((year == 2019) && (myCheck < getCheck(2018, 12, 31)))
    {       
       return  1;
    }    
    else if ((year == 2020) && (myCheck < getCheck(2019, 12, 30)))
    {       
       return  1;
    }  
    else if ((year == 2021) && (myCheck < getCheck(2021, 1, 4)))
    {       
       return  1;
    }     
    else if ((year == 2022) && (myCheck < getCheck(2022, 1, 3)))
    {       
       return  1;
    }        
    else if ((year == 2023) && (myCheck < getCheck(2023, 1, 2)))
    {       
       return  1;
    }         
    else if ((year == 2024) && (myCheck < getCheck(2024, 1, 1)))
    {       
       return  1;
    }      
    else if ((year == 2025) && (myCheck < getCheck(2024, 12, 30)))
    {       
       return  1;
    }     
    else if ((year == 2026) && (myCheck < getCheck(2025, 12, 29)))
    {       
       return  1;
    }        
    else
        return 0;      
  }
  
  public static Date getDateByWeek(int theWeek,  int theYear){
    Calendar calendar = new GregorianCalendar();
 
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    
    calendar.set(Calendar.WEEK_OF_YEAR, theWeek);
    calendar.set(Calendar.YEAR, theYear);

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    
    return calendar.getTime();  
    
 
 
  
}
  
  public static Date getDateByWeek(int theWeek,  int theYear, int offset){
    theWeek+=offset;
    Calendar calendar = new GregorianCalendar();
 
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    
    calendar.set(Calendar.WEEK_OF_YEAR, theWeek);
    calendar.set(Calendar.YEAR, theYear);

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    
 
    return calendar.getTime();
  
  
}  

public static int getCurrentWeek(String nomBase,Connexion myCnx, Statement st){
  int CurrentWeek = -1;

  Calendar calendar = Calendar.getInstance();
  int Annee = calendar.get(Calendar.YEAR);
  int mois = calendar.get(Calendar.MONTH) +1;
  int jour = calendar.get(Calendar.DAY_OF_MONTH);

  CurrentWeek = getWeek( nomBase, myCnx,  st,jour, mois,Annee);

  return CurrentWeek;

}

public static int getCurrentWeek2(){
  int CurrentWeek = -1;

  Calendar calendar = Calendar.getInstance();
  int Annee = calendar.get(Calendar.YEAR);
  int mois = calendar.get(Calendar.MONTH) +1;
  int jour = calendar.get(Calendar.DAY_OF_MONTH);

  CurrentWeek = getWeek2(jour, mois,Annee);

  return CurrentWeek;

}

public static int getWeek(String nomBase,Connexion myCnx, Statement st, int theJour, int theMois, int theYear){
  int theWeek=0;

  String str_date = theJour + "-" + theMois + "-" + theYear;
  ResultSet rs = null;
  String anneeRef="";
  rs = myCnx.ExecReq(st, nomBase, "exec GET_NUMWEEK '"+str_date+"'");
  try { rs.next();
      theWeek= rs.getInt("numWeek");
    } catch (SQLException s) {s.getMessage();}

return theWeek;
}

public static int getWeek2(int theJour, int theMois, int theYear){
  int theWeek=0;

  // get the supported ids for GMT-08:00 (Pacific Standard Time)
  String[] ids = TimeZone.getAvailableIDs(-0 * 60 * 60 * 1000);
  // if no ids were returned, something is wrong. get out.
  if (ids.length == 0)
      System.exit(0);

   // begin output
  //System.out.println("Current Time");

  // create a Pacific Standard Time time zone
  SimpleTimeZone pdt1 = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
  SimpleTimeZone pdt = new SimpleTimeZone(3600000,
                 "Europe/Paris",
                 Calendar.MARCH, -1, Calendar.SUNDAY,
                 3600000, SimpleTimeZone.UTC_TIME,
                 Calendar.OCTOBER, -1, Calendar.SUNDAY,
                 3600000, SimpleTimeZone.UTC_TIME,
                 3600000);


  // set up rules for daylight savings time
  pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
  pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

  // create a GregorianCalendar with the Pacific Daylight time zone
  // and the current date and time
  Calendar calendar = new GregorianCalendar(pdt);

    Date trialTime = new Date (theYear +"/"+theMois+"/"+theJour);
    calendar.setTime(trialTime);

    theWeek = calendar.get(Calendar.WEEK_OF_YEAR);

    if (( (theWeek ==0) || (theWeek ==53) || (theWeek ==52) ) && (theMois==1)) theWeek = 1;
    if (( (theWeek ==0) || (theWeek ==53) ) && (theMois==12)) theWeek = 52;

    //System.out.println("01234=========theWeek::"+theWeek);

return theWeek;
}

  public static int getWeek3(int theJour, int theMois, int theYear){
    int theWeek=0;
    if (theYear < 2008) theYear=2008;
    if (theYear > 2010) theYear=2010;
    int index = (theYear - 2008)*12;

   //theWeek =  Integer.parseInt(tabMonth[theMois-1 + index][2]) + theJour/7;
   theWeek =  Integer.parseInt(tabMonth[theMois-1 + index][2]) + (theJour - 4)/7;

  return theWeek;
}


  public static String getDateFrench2English(String myString) {
    getDate(myString);
    return Year + "/" + Month + "/" + Day;
  }

  public static String getDateFrench2English2(String myString) {
    if ((myString == null) || (myString.equals("")) || (myString.equals("null")))
      return "convert(datetime, '"+"01"+"' + '/' + '"+"01"+"' + '/' + '"+"1900"+"', 103)";
    Utils.getDate(myString);
    String jourmepVersionSt = Utils.Day;
    String moismepVersionSt = Utils.Month;
    String anneemepVersionSt = Utils.Year;

  return "convert(datetime, '"+jourmepVersionSt+"' + '/' + '"+moismepVersionSt+"' + '/' + '"+anneemepVersionSt+"', 103)";
}


public static int parseInt(String str){
  int theInt=-1;
  try{
    theInt = Integer.parseInt(str);
  }
  catch (Exception s) {s.getMessage();};
  return theInt;
}

public static String parseString(String str){
  if ((str == null) || (str.equals("null"))) return "";
  return str;
}

  public static String getDateFrench(java.util.Date myDate) {
    if (myDate == null) return "";
    String theDate="";

    int theDAy = myDate.getDate();
    String str_theDAy;
    if (theDAy <10) str_theDAy = "0"+theDAy;else str_theDAy = ""+theDAy;

    int theMonth = myDate.getMonth() +1;
    String str_theMonth;
    if (theMonth <10) str_theMonth = "0"+theMonth;else str_theMonth = ""+theMonth;

    int theYear = myDate.getYear() + 1900;
    if (theYear == 1900) return "";

    theDate = str_theDAy+"/"+str_theMonth+"/"+ theYear;

  return theDate;
}
  
  public static String getDateEnglish(java.util.Date myDate) {
    if (myDate == null) return "";
    String theDate="";

    int theDAy = myDate.getDate();
    String str_theDAy;
    if (theDAy <10) str_theDAy = "0"+theDAy;else str_theDAy = ""+theDAy;

    int theMonth = myDate.getMonth() +1;
    String str_theMonth;
    if (theMonth <10) str_theMonth = "0"+theMonth;else str_theMonth = ""+theMonth;

    int theYear = myDate.getYear() + 1900;
    if (theYear == 1900) return "";

    theDate = theYear+"-"+str_theMonth+"-"+ str_theDAy;

  return theDate;
}  


public static String getYear(String nomBase,Connexion myCnx, Statement st) {
  ResultSet rs = null;
  String anneeRef="";
  rs = myCnx.ExecReq(st, nomBase, "SELECT  YEAR(GETDATE()) AS year");
  try { rs.next();
      anneeRef= rs.getString(1);
    } catch (SQLException s) {s.getMessage();}

  return anneeRef;
 }

  public static float getRound(float theFloat){
    float  arrondi = 0;
    if ((theFloat > 0) && (theFloat <= 0.5) )
        arrondi = (float)0.5;
    else if ((theFloat > 0.5) && (theFloat <= 1.0) )
        arrondi = (float)1;
    else if ((theFloat > 1) && (theFloat <= 1.5) )
        arrondi = (float)1.5;   
    else if ((theFloat > 1.5) && (theFloat <= 2) )
        arrondi = (float)2; 
    else if ((theFloat > 2) && (theFloat <= 2.5) )
        arrondi = (float)2.5; 
    else if ((theFloat > 2.5) && (theFloat <= 3) )
        arrondi = (float)3; 
    else if ((theFloat > 3) && (theFloat <= 3.5) )
        arrondi = (float)3.5; 
    else if ((theFloat > 3.5) && (theFloat <= 4) )
        arrondi = (float)4; 
    else if ((theFloat > 4) && (theFloat <= 4.5) )
        arrondi = (float)4.5; 
    else if ((theFloat > 4.5) && (theFloat <= 5) )
        arrondi = (float)5; 
    else if ((theFloat > 5) )
        arrondi = (float)5;  
    else
        arrondi = (float)0;
    
    return arrondi;
}

 public static String removeBR(String myString) {
   if (myString == null) return "";
   int pos = -1;
   pos = myString.indexOf("<BR>");
   if (pos == 0)
   {
     myString = myString.substring(4);
    }

    return myString;
 }

public static Date getNewDate(String theDate){
  Date Ddate_new = null;
  if ((theDate != null) &&(!theDate.equals("")))
  {
   Utils.getDate(theDate);
   Ddate_new = new Date (Integer.parseInt(Utils.Year) - 1900,Integer.parseInt(Utils.Month) - 1,Integer.parseInt(Utils.Day));

  }
  else
  {
    //Ddate_new = new Date (0,0,1);
    Ddate_new = null;
    }
    return Ddate_new;
}

public static int compareDate(Date Ddate_new, Date oldDate, String Type){
  // ---------- T0 ------------------------------------------------------//
      int compare = -1;
      compare = oldDate.compareTo(Ddate_new);
     // myCnx.trace("********************** "+Type+": ","Ddate_new",""+Ddate_new.toString());

      if (compare != 0) ;;
       //myCnx.trace("********************** "+Type+" differentes: ",oldDate.toString(),""+Ddate_new.toString());
  return compare;
}

  public static void getDate(String myString) {
    //Connexion.trace("@01234","myString",myString);
    int pos = 0;
    String choice = "";


    pos = myString.indexOf("-");
    if (pos >=0)
    {
      getDateWithLibelle(myString);
      return;
    }

    pos = myString.indexOf("/");
    choice = myString.substring(0,pos);

    if (choice.length() !=4)
    {

      Day = choice;
      if ( (Integer.parseInt(Day) < 10) && (Day.length() == 1))
        Day = "0" + Day;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("/");

      Month = myString.substring(0, pos);
      if ( (Integer.parseInt(Month) < 10) && (Month.length() == 1))
        Month = "0" + Month;

      Year = myString.substring(pos + 1);
    }
    else
    {

      Year = choice;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("/");

      Month = myString.substring(0, pos);
      if ( (Integer.parseInt(Month) < 10) && (Month.length() == 1))
        Month = "0" + Month;

      Day = myString.substring(pos + 1);
      if ( (Integer.parseInt(Day) < 10) && (Day.length() == 1))
        Day = "0" + Day;
    }

  }

  public static PrintWriter openTxtFile(String FileIn){

    PrintWriter ecrivain=null;
    int n = 5;

    try{
      ecrivain = new PrintWriter(new BufferedWriter
                                 (new FileWriter(FileIn)));
    }
    catch (Exception e){e.printStackTrace();}

    return ecrivain;

  }

  public static void CloseTxtFile(PrintWriter ecrivain){
    ecrivain.close();
  }

  public static void writeInTxtFile(PrintWriter ecrivain, String Line){
    ecrivain.println(Line);
  }

  public static void copyFileBuffered(final String currentFile, final String newFile) throws FileNotFoundException, IOException {
          FileInputStream in = new FileInputStream(currentFile);
          try {
              FileOutputStream out = new FileOutputStream(newFile);
              try {
                  byte[] byteBuffer = new byte[in.available()];
                  int s = in.read(byteBuffer);
                  out.write(byteBuffer);
                  out.flush();
              } finally {
                  out.close();
              }
          } finally {
              in.close();
          }
    }

  public static void getDateWithLibelle(String myString) {
    //Connexion.trace("@01234","myString",myString);
    int pos = 0;
    String choice = "";

    myString = myString.replace('.',' ');

    pos = myString.indexOf(" ");
    if (pos >= 0)
    myString = myString.substring(0,pos);

    //myString = myString.replaceAll(" ","");
    pos = myString.indexOf("-");
    choice = myString.substring(0,pos);

    if (choice.length() !=4)
    {

      Day = choice;
      if ( (Integer.parseInt(Day) < 10) && (Day.length() == 1))
        Day = "0" + Day;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("-");

      Month = myString.substring(0, pos);

      Year = myString.substring(pos + 1);
    }
    else
    {

      Year = choice;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("-");

      Month = myString.substring(0, pos);

      Day = myString.substring(pos + 1);
      if ( (Integer.parseInt(Day) < 10) && (Day.length() == 1))
        Day = "0" + Day;
    }

  }

  public static void getDateEnglish(String myString) {
    //Connexion.trace("@01234","myString",myString);
    int pos = 0;
    String choice = "";

    pos = myString.indexOf("-");
    choice = myString.substring(0,pos);


      Year = choice;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("-");

      Month = myString.substring(0, pos);

      Day = myString.substring(pos + 1);
      if ( (Integer.parseInt(Day) < 10) && (Day.length() == 1))
        Day = "0" + Day;


  }


  public static String getName(String prefixe, String FileIn) {

    String name = "";
    Date myDate = new Date();

    int Index = FileIn.lastIndexOf(".");
    String extension = FileIn.substring(Index);;

    if (extension.toLowerCase() == ".docx") extension = ".doc";
    else if (extension == ".pptx") extension = ".ppt";

    name = prefixe+myDate.getTime()+extension;

    return name;
  }


  public static String startExport(String nomBase,Connexion myCnx, Statement st, String export, String declenche){
      ResultSet rs = null;
      Date currentTime_1 = new Date();
      int theYear = currentTime_1.getYear()+ 1900;
      int theMonth = currentTime_1.getMonth()+1;
      int theDay = currentTime_1.getDate();
      int theHour = currentTime_1.getHours();
      int theMinute = currentTime_1.getMinutes();
      int theSecond = currentTime_1.getSeconds();
      String theDate =theYear+"-"+theMonth+"-"+theDay+"-"+theHour+"-"+theMinute+"-"+theSecond;

      String req = "update Config set valeur='"+theDate+"'";
      req+=" WHERE     (nom = '"+export+"') ";
      myCnx.ExecReq(st,nomBase,req);

      //req = "update "+declenche+" set nom='GO'";

      //myCnx.ExecReq(st,nomBase,req);




      return theDate;
  }


  public static String getCanonicalPath() {

    File dir1 = new File (".");

    String project = "";
    try {
      String fullPath = dir1.getCanonicalPath();
      project = fullPath;
      }
    catch(Exception e) {
      e.printStackTrace();
      }

    return project;
  }
  public static String getPathProjet() {

    File dir1 = new File (".");

    String project = "";
    try {
      String fullPath = dir1.getCanonicalPath();
      int pos = fullPath.indexOf("webapps");
      project = fullPath.substring(pos);
      System.out.println ("project : " + project);

      pos = project.indexOf("\\");
      project = project.substring(pos+1);

      pos = project.indexOf("\\");
      project = project.substring(0,pos);

      //System.out.println ("project : " + project);
      //System.out.println ("Current dir : " + dir1.getCanonicalPath());
      //System.out.println ("Parent  dir : " + dir2.getCanonicalPath());
      }
    catch(Exception e) {
      e.printStackTrace();
      }

    return project;
  }

  public static void migrationChangeNomStCommuns() {
    Statement st= null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    st = myCnx.Connect();

    req = "UPDATE  St SET  nomSt = 'CDC2'  WHERE     (nomSt = 'CDC')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DOR2'  WHERE     (nomSt = 'DOR')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DOR OPT2'  WHERE     (nomSt = 'DOR OPT')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DOR OSD2'  WHERE     (nomSt = 'DOR OSD')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DSI2'  WHERE     (nomSt = 'DSI')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DTE TCN2'  WHERE     (nomSt = 'DTE TCN')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DTE TRB2'  WHERE     (nomSt = 'DTE TRB')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'DTE TRN'  WHERE     (nomSt = 'DTE TRN2')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'PMO2'  WHERE     (nomSt = 'PMO')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  Service SET  nomService = 'BouygTel'  WHERE     (nomService = 'RH')";
    rs = myCnx.ExecReq(st, base, req);

    req = "UPDATE  St SET  nomSt = 'BouygTel2'  WHERE     (nomSt = 'BouygTel')";
    rs = myCnx.ExecReq(st, base, req);

   }



  public static String unCompress(byte[] output){
    int compressedDataLength = output.length;
    // Decompress the bytes
    Inflater decompresser = new Inflater();
    decompresser.setInput(output, 0, compressedDataLength);
    byte[] result = new byte[100];
    int resultLength = -1;
    try{
      resultLength = decompresser.inflate(result);
    }    catch (Exception e){}

    decompresser.end();

    // Decode the bytes into a String
String outputString = "";
try{
  outputString = new String(result, 0, resultLength, "UTF-8");
    }    catch (Exception e){}

    return outputString;

  }

   public static byte[] Compress(String inputString){
     // Encode a String into bytes
     //String inputString = "blahblahblah??";
     byte[] input=null;
     try{
       input = inputString.getBytes("UTF-8");
     }    catch (Exception e){}
     // Compress the bytes
     byte[] output = new byte[1000];
     Deflater compresser = new Deflater();
     compresser.setInput(input);
     compresser.finish();
     int compressedDataLength = compresser.deflate(output);

     return output;
   }

   public static void migrationFinale() {
     // Migration des directions -> macrost sous SI = Acteurs
     Statement st = null;
     Statement st2 = null;
     ResultSet rs = null;
     ResultSet rs2 = null;
     String req="";
     String base = myCnx.getDate();

     st = myCnx.Connect();
    st2 = myCnx.Connect();

     req="SELECT     idMacrost FROM MacroSt WHERE     (nomMacrost = 'Bytel')";
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     int idMacrost = -1;
     try {
       while (rs.next()) {

         idMacrost = rs.getInt("idMacrost");

         }
          } catch (SQLException s) {s.getMessage();}

     req = "UPDATE";
     req+=" VersionSt";
     req+=" set macrostVersionSt="+idMacrost;
     req+=" WHERE     (macrostVersionSt = 71)";

     rs = myCnx.ExecReq(st, base, req);

   }

   public static void migrationServices() {
     Statement st = null;
     Statement st2 = null;
     ResultSet rs = null;
     ResultSet rs2 = null;
     String req="";
     String base = myCnx.getDate();

     st = myCnx.Connect();
     st2 = myCnx.Connect();
     // Basculer tous les ST du Macro-ST Acteurs dans la bonne organisation
     // Ce sont les ST du SI Acteurs qui n'ont pas d'�quivalent en nom de service
     // Ces Services sont cr��s sous la direction Bytel
     req = "SELECT     St.idSt, St.nomSt, St.trigrammeSt, St.chronoSt, St.typeAppliSt, St.Criticite, St.ClefImport, St.diagramme, St.isST, St.isAppli, St.isRecurrent, St.isMeeting, ";
     req+="                 St.Logo, St.LogoBirt, VersionSt.etatFicheVersionSt, VersionSt.etatVersionSt, VersionSt.siVersionSt, VersionSt.descVersionSt";
     req+=" FROM         St INNER JOIN";
     req+="                  VersionSt ON St.idSt = VersionSt.stVersionSt";
     req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt = 3) AND (VersionSt.siVersionSt = 7) AND (St.nomSt NOT IN";
     req+="                      (SELECT     nomService";
     req+="                        FROM          Service))";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     int idMacrost = -1;
     try {
       while (rs.next()) {

         String nomService = rs.getString("nomSt");
         String descService = rs.getString("descVersionSt");
         String reqRefSt =  "INSERT INTO Service  (nomService, descService, dirService) ";
          reqRefSt +=" VALUES     ('"+nomService+"', '"+descService+"'"+", '34')";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);

         }
      } catch (SQLException s) {s.getMessage();}

   }
  public static void main20(String[] args) {

    //migrationChangeNomStCommuns();
    //migrationMacroST(); // Cr�ation des Macro-St � partir de l'organisation (directions)
    //migrationServices(); // cr�er tous les services qui n'existent pas dans l'organisation
    //migrationActeurs(); // Cr�er les ST qui sont des Services de l'organisation
    //migrationFinale(); // Basculer tous les ST du Macro-ST Acteurs dans la bonne organisation
    Date uDate = new Date();
    uDate = addDays (uDate, 21);
    System.out.println(uDate.toString());
  }
  public static void main24(String[] args) {

  }
  public static void main21(String[] args) {

    //Connexion.trace("@01234---------------------------------","Utils.getPathProjet()",""+Utils.getPathProjet());
    //Connexion.trace("@01234---------------------------------","Utils.getPathProjet()",""+Utils.getCanonicalPath());

  }


  public static String epure2 (String champ){

    String epureChamp = "";
          for (int i=0; i< champ.length(); i++)
                  {
                    char c = champ.charAt(i);
                    //alert (c);
                    //alert("caractre:"+c+ " interdit!");
                    if (((c < 'a') || (c > 'z'))&& ((c < 'A')|| (c > 'Z')) && ((c < '0')|| (c > '9')) && (c != ' ')&& (c != ',') && (c != '.')&& (c != '_') && (c != '-') && (c != '�')&& (c != '�')&& (c != '�')&& (c != '\''))
                          {
                            //alert ("car "+c+" interdit");
                              continue;
                          }
                          else
                          epureChamp +=c;
                  }
          return epureChamp;

}
  
  public static String clean (String champ){
      String str="";
      
        if (champ !=  null)
        {
         
            str=champ.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"", "''''");
        }
        else
            champ="";      
      
      return str;
      
  }  
  public static String epure3 (String champ){
    //System.out.println("##------------------------------");
    //System.out.println("champ="+champ);
    //System.out.println("stringToHexa="+stringToHexa(champ));
    
    
    //champ=champ.replaceAll("<div>","");
    //champ=champ.replaceAll("?","");
    //champ=champ.replaceAll("</div>","");
    char c_old=0;
    //System.out.println("stringToHexa2="+stringToHexa(champ));
    String epureChamp = "";
          for (int i=0; i< champ.length(); i++)
                  {
                    char c = champ.charAt(i);
                    //System.out.print(c);
                    //System.out.println("c="+Integer.toHexString(c));
                    
                    //alert (c);
                    //alert("caractre:"+c+ " interdit!");
                    if ((c == 63) && (c_old == '>'))
                          {
                            //System.out.println("c="+c + "c_old="+c_old);
                            continue;
                          }
                    if (Integer.toHexString(c).equals("200b")&& (c_old == '>'))
                          {
                            //System.out.println("c="+c + "c_old="+c_old);
                            continue;
                          }                    
                          else
                          epureChamp +=c;
                          c_old = c;
                  }
          //System.out.println("");
          //System.out.println("##------------------------------");
          return epureChamp;

}  
  
  public static String show (String champ){


    String epureChamp = ""; //&agrave;
          for (int i=0; i< champ.length(); i++)
                  {
                    char c = champ.charAt(i);
                    int int_c = c;
                    //alert (c);
                    //alert("caractre:"+c+ " interdit!");
                    if ( int_c == 63)
                          {
                            //alert ("car "+c+" interdit");
                              epureChamp += "&agrave;";
                          }
                    else if ( int_c == 167)
                          {
                            //alert ("car "+c+" interdit");
                              epureChamp += "&sect;";
                          }
                    else
                          epureChamp +=c;
                  }
          

          return epureChamp;

}  

 public static void main12(String[] args) {

     // Migration des directions -> macrost sous SI = Acteurs
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();


String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    req =  "delete from ObjetMetier WHERE     (typeOM = 2)";
    myCnx.ExecReq(st, base, req);


    req = "SELECT     Profil, Constructeur, Type, Onglet, Graphe, NomKPI, [KPI IPOD], [KPI constructeur], [XML LinkKey]";
    req+= "FROM         importKPI";

    rs = myCnx.ExecReq(st, base, req);

    try {
      while (rs.next()) {

        String Profil = rs.getString("Profil");
        String Constructeur = rs.getString("Constructeur");
        String Type = rs.getString("Type");
        String Onglet = rs.getString("Onglet");
        String Graphe = rs.getString("Graphe");
        String NomKPI = rs.getString("NomKPI");
        String KPI_IPOD = rs.getString("KPI IPOD");
        String KPI_constructeur = rs.getString("KPI constructeur");
        String XML_LinkKey = rs.getString("XML LinkKey");
        
        if (Profil != null) Profil = Profil.replaceAll("'","''");
        if (Constructeur != null) Constructeur = Constructeur.replaceAll("'","''");
        if (Type != null) Type = Type.replaceAll("'","''");
        if (Onglet != null) Onglet = Onglet.replaceAll("'","''");
        if (Graphe != null) Graphe = Graphe.replaceAll("'","''");
        //if (NomKPI != null) NomKPI = NomKPI.replaceAll("'","''").replaceAll("\u00e0 ","&agrave;").replaceAll("?","&agrave;");
        if (NomKPI != null) 
        {
            NomKPI = NomKPI.replaceAll("'","''");       
            NomKPI=show (NomKPI);
        }
        if (KPI_IPOD != null) 
        {
            KPI_IPOD = KPI_IPOD.replaceAll("'","''");  
            int pos = KPI_IPOD.indexOf("DECODE");
            if (pos >=0)
            {
                KPI_IPOD = KPI_IPOD.substring(0, pos -1) + "&sect;DECODE&sect;" + KPI_IPOD.substring(pos+1+6, KPI_IPOD.length()-1);
            }
        }
        if (KPI_constructeur != null) 
        {
            KPI_constructeur = KPI_constructeur.replaceAll("'","''");  
            int pos = KPI_constructeur.indexOf("DECODE");
            if (pos >=0)
            {
                KPI_constructeur = KPI_constructeur.substring(0, pos -1) + "&sect;DECODE&sect;" + KPI_constructeur.substring(pos+1+6, KPI_constructeur.length()-1);
            }
        }        

        if (XML_LinkKey != null) XML_LinkKey = XML_LinkKey.replaceAll("'","''");
        
        String sousFamille = "";
        if (Constructeur.equals("Ericsson") && Profil.equals("SuperUser.NEW"))
        {
            sousFamille = "369";
            NomKPI = "ERC"+"-"+"NEW"+":"+NomKPI;
        }
        else if (Constructeur.equals("Ericsson") && Profil.equals("SuperUser.RNC"))
        {
            sousFamille = "371";
            NomKPI = "ERC"+"-"+"RNC"+":"+NomKPI;
        } 
        else if (Constructeur.equals("Huawei") && Profil.equals("SuperUser.New"))
        {
            sousFamille = "370";
            NomKPI = "HUA"+"-"+"NEW"+":"+NomKPI;            
        }        
        else if (Constructeur.equals("Huawei") && Profil.equals("SuperUser.RNC"))
        {
            sousFamille = "372";
            NomKPI = "HUA"+"-"+"RNC"+":"+NomKPI;            
        }

        reqRefSt =  "INSERT INTO ObjetMetier  ( nomObjetMetier, package,famObjetMetier, typeOM, typeEtatObjetMetier, idAppliIcone) ";
        reqRefSt += " VALUES     ('"+NomKPI+"', '"+sousFamille+"', '27', '2', '3', '6')";
        myCnx.ExecReq(st2, base, reqRefSt);
        
        req = "SELECT idObjetMetier FROM ObjetMetier WHERE     (typeOM = 2) AND (nomObjetMetier = '"+NomKPI+"') AND (package = "+sousFamille+ ")";
        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

    int idObjetMetier = -1;
    try {
      while (rs2.next()) {

        idObjetMetier = rs2.getInt("idObjetMetier");
        
        }
    }
    catch (SQLException s) {s.getMessage();}
 
    req =  "delete from Attribut WHERE     (omAttribut = "+idObjetMetier+")";
    myCnx.ExecReq(st2, base, req);
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('Profil', '"+Profil+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);    
      
     req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('Constructeur', '"+Constructeur+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);     
      
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('Type', '"+Type+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);   

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('Graphe', '"+Graphe+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);  
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('Onglet', '"+Onglet+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);        
       
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('KPI IPOD', '"+KPI_IPOD+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);       
      
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('KPI constructeur', '"+KPI_constructeur+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);       

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut) ";
    req += " VALUES     ('XML LinkKey', '"+XML_LinkKey+"', '"+idObjetMetier+"')";
    myCnx.ExecReq(st2, base, req);  
    
      }
        
    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);

  }



  public static void updateInterfacesActeurs(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();



    //-- R�cup�ration de toutes les interface avec acteur --> � supprimer
    req = "delete FROM         Interface";
    req+=" WHERE     (typeInterface = 'I') ";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    // -- R�cup�ration de tous les st ayant un Client
    req = "SELECT     VersionSt.idVersionSt, VersionSt.stVersionSt, VersionSt_1.idVersionSt AS serviceMetier";
    req+="     FROM         St INNER JOIN";
    req+="                   Service ON St.nomSt = Service.nomService INNER JOIN";
    req+="                  VersionSt AS VersionSt_1 ON St.idSt = VersionSt_1.stVersionSt INNER JOIN";
    req+="                   VersionSt ON Service.idService = VersionSt.serviceMetier";
    req+=" WHERE     (VersionSt.serviceMetier > 0)";




    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {

        int idVersionSt = rs.getInt("idVersionSt");
        int stVersionSt = rs.getInt("stVersionSt");
        int serviceMetier = rs.getInt("serviceMetier");

        //-- Cr�ation   d'une vraie  interface
        req = "Insert into Interface ( origineInterface, sensInterface, extremiteInterface, typeInterface, implemInterface, frequenceInterface, descInterface)";
        req+=" values ("+serviceMetier+", '--->',"+stVersionSt+",'I',29,9,'migration')    ";
        rs2 = myCnx.ExecReq(st2, base, req);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }



  public static void main27(String args[]) {
    String inputString="joel jakubowicz 17 rue ditte 78470 saint r�my l�s chevreuse";
    byte[] input=Compress(inputString);

    for (int i=0; i < input.length; i++)
    {
      System.out.println(input[i]);
    }
    ;
    System.out.println("inputString="+String.valueOf(input).toString());
    String outputString = unCompress(input);

    System.out.println("outputString="+outputString+ " :: "+"lg="+outputString.length());
  }

  public static void main26(String args[]) {
    String data = new String(
    "ab cd ab cd ab ab cd ab cd ab ab cd ab cd ab ab cd ab cd ab");
    byte[] dataByte = data.getBytes();
    System.out.println("Compression Demo");
    System.out.println("Actual Size of String : " + dataByte.length);
    Deflater def = new Deflater();
    def.setLevel(Deflater.BEST_COMPRESSION);
    def.setInput(dataByte);
    def.finish();
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream(
        dataByte.length);
    byte[] buf = new byte[1024];
    while (!def.finished()) {
      int compByte = def.deflate(buf);
      byteArray.write(buf, 0, compByte);
    }
    try

    {
      byteArray.close();
    }

    catch (IOException ioe) {
      System.out.println("When we will close straem error : " + ioe);
    }

    byte[] comData = byteArray.toByteArray();

    System.out.println("Compressed  size of String : " + comData.length);
  }
  public static void main25(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    String NomProjet, previousActivite= "";
    float semaine =0;

String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    reqRefSt =  "DELETE FROM CollaborateurCompetence";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

    req = "SELECT  Membre.idMembre, Middleware.idMiddleware, ImportCompetences.Note";
    req+="    FROM         ImportCompetences INNER JOIN";
    req+="                   Membre ON ImportCompetences.Nom = Membre.nomMembre AND ImportCompetences.Prenom = Membre.prenomMembre INNER JOIN";
    req+="                   Middleware ON ImportCompetences.Competence = Middleware.nomMiddleware";
    req+=" ORDER BY ImportCompetences.Nom";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {

        int idMembre = rs.getInt("idMembre");
        int idMiddleware = rs.getInt("idMiddleware");
        int Note = rs.getInt("Note");

        reqRefSt =  "INSERT INTO CollaborateurCompetence  ( idCollaborateur, idCompetence, note) ";
        reqRefSt +=" VALUES     ('"+idMembre+"', '"+idMiddleware+"', '"+Note+"')";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);


        //if (idPoMacroSt.equals("7"))
        //Connexion.trace("===============>","reqRefSt",reqRefSt);
        /*
      */
      //System.out.println("idActivite="+idActivite+"::donneesEntree="+donneesEntree+"::previousActivite="+previousActivite);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);


          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

  public static void main18(String[] args) {

    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String Suivi= "";
    int idVersionSt;
    String descVersionSt= "";

    int Index = 0;

    st = myCnx.Connect();
    st2 = myCnx.Connect();


    //req = "SELECT     idVersionSt, descVersionSt FROM         VersionSt WHERE     (idVersionSt = 1319)";
    req = "SELECT     idVersionSt, descVersionSt FROM         VersionSt" ;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        idVersionSt= rs.getInt("idVersionSt");
        descVersionSt= rs.getString("descVersionSt");

        if (descVersionSt != null)
        {
          if ((descVersionSt.indexOf("\r") > 0) )
          {
            descVersionSt = descVersionSt.replaceAll("\r", "<br>");
          }
          if ( (descVersionSt.indexOf("\n") > 0))
          {
            descVersionSt = descVersionSt.replaceAll("\n", "<br>");
          }

          req = "UPDATE VersionSt SET ";
          req+="descVersionSt='"+descVersionSt.replaceAll("'","''")+"'";
          req+=" WHERE (idVersionSt = "+idVersionSt+")";
          rs2 = myCnx.ExecReq(st2, base, req);
        }

        }


    } catch (SQLException s) {s.getMessage();}



    myCnx.Unconnect(st);
  }

  public static void main17(String[] args) {

    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String Suivi= "";
    int idRoadmap;
    String description= "";

    int Index = 0;

    st = myCnx.Connect();
    st2 = myCnx.Connect();


    req = "SELECT     idRoadmap, Suivi, description FROM     Roadmap";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        idRoadmap= rs.getInt("idRoadmap");
        Suivi= rs.getString("Suivi");
        description= rs.getString("description");

        if (Suivi != null)
        {
          if (Suivi.indexOf("\r") > 0)
          {
            Suivi = Suivi.replaceAll("\r", "<br>");
            Suivi = Suivi.replaceAll("\n", "");
          }

          req = "UPDATE Roadmap SET ";
          req+="Suivi='"+Suivi.replaceAll("'","''")+"'";
          req+=" WHERE (idRoadmap = "+idRoadmap+")";
          rs2 = myCnx.ExecReq(st2, base, req);
        }

        if (description != null)
        {
          if (description.indexOf("\r") > 0)
          {
            description = description.replaceAll("\r", "<br>");
            description = description.replaceAll("\n", "");
          }

          req = "UPDATE Roadmap SET ";
          req+="description='"+description.replaceAll("'","''")+"'";
          req+=" WHERE (idRoadmap = "+idRoadmap+")";
          rs2 = myCnx.ExecReq(st2, base, req);
        }

        }


    } catch (SQLException s) {s.getMessage();}



    myCnx.Unconnect(st);
  }


  public static void main15(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String nomProjet= "";
    int idRoadmap;

    int Index = 0;

    st = myCnx.Connect();
    st2 = myCnx.Connect();


    req = "SELECT     ListeST.nomSt + '-' + Roadmap.version AS nomProjet, Roadmap.idRoadmap";
    req += " FROM         Roadmap INNER JOIN";
    req += "                   ListeST ON Roadmap.idVersionSt = ListeST.idVersionSt";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nomProjet= rs.getString("nomProjet");
        idRoadmap= rs.getInt("idRoadmap");

        req = "UPDATE PlanDeCharge SET ";

        req+="idRoadmap="+idRoadmap+"";
        req+=" WHERE nomProjet='"+nomProjet+"'";
        rs2 = myCnx.ExecReq(st2, base, req);
        //myCnx.ExecUpdate(st2,myCnx.nomBase,req,true,"EnregST") ;

        }


    } catch (SQLException s) {s.getMessage();}


    myCnx.Unconnect(st);
  }
  public static void main10(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String nomProjet= "";
    String nomSt= "";
    String nomVersion= "";
    int Index = 0;

    st = myCnx.Connect();
    st2 = myCnx.Connect();


    req = "SELECT DISTINCT nomProjet FROM   PlanDeCharge  WHERE   (nomProjet LIKE 'webres-%')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nomProjet= rs.getString(1);
        ListeProjets.addElement(nomProjet);



        }


    } catch (SQLException s) {s.getMessage();}

    for (int i=0; i < ListeProjets.size();i++)
    {
      String theProjet = (String)ListeProjets.elementAt(i);
      String new_theProjet= theProjet.toLowerCase().replaceAll("webres","webded");

      req = "UPDATE PlanDeCharge SET ";

      req+="nomProjet='"+new_theProjet+"'";
      req+=" WHERE nomProjet='"+theProjet+"'";
      myCnx.ExecUpdate(st,myCnx.nomBase,req,true,"EnregST") ;

      System.out.println("theProjet="+theProjet);
    }

    myCnx.Unconnect(st);
  }


  public static void main14(String[] args) {
    String myDate = "2009-10-08 00:00:00.000".replaceAll("-","/").substring(0,10);
    Date theDate = new Date(myDate);
    myDate = getDateFrench(theDate);
  }

  public static void main13(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String ST= "";
    int nbLigCodeVersionSt=0;
    int nbPtsFctVersionSt=0;
    int idVersionSt=0;


    st = myCnx.Connect();
    st2 = myCnx.Connect();


    req = "SELECT     LC_PF.ST, LC_PF.nbLigCodeVersionSt, LC_PF.nbPtsFctVersionSt, VersionSt.idVersionSt";
    req +=" FROM         LC_PF INNER JOIN";
    req +="                   St ON LC_PF.ST = St.nomSt INNER JOIN";
    req +="                   VersionSt ON St.idSt = VersionSt.stVersionSt";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        ST= rs.getString(1);
        nbLigCodeVersionSt= rs.getInt(2);
        nbPtsFctVersionSt= rs.getInt(3);
        idVersionSt= rs.getInt(4);
        System.out.println("ST="+ST+"::nbLigCodeVersionSt="+nbLigCodeVersionSt+"::nbPtsFctVersionSt="+nbPtsFctVersionSt+"::idVersionSt="+idVersionSt);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);

          String reqRefSt = "UPDATE    VersionSt SET nbLigCodeVersionSt ="+nbLigCodeVersionSt+", nbPtsFctVersionSt ="+nbPtsFctVersionSt+"  WHERE     (idVersionSt = "+idVersionSt+")";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }
  public static void main22(String[] args) {
     //create Calendar instance
     Calendar now = Calendar.getInstance();
     System.out.println("Current week of month is : " + now.get(Calendar.WEEK_OF_MONTH));
     System.out.println("Current week of year is : " +  now.get(Calendar.WEEK_OF_YEAR));
     now.add(Calendar.WEEK_OF_MONTH, 1);
     System.out.println("date after one year : " + (now.get(Calendar.MONTH) + 1)+ "-"+ now.get(Calendar.DATE)+ now.get(Calendar.YEAR));


  }

  
  public static void main(String[] args) {

    Hashtable ht = new Hashtable();
    ht.put(1, "printemps");
    ht.put(10, "été");
    ht.put(12, "automne");
    ht.put(45, "hiver");

    Enumeration e = ht.elements();

    while(e.hasMoreElements())
      System.out.println(e.nextElement());
    
    System.out.println(ht.get(12));
    System.out.println(ht.get(13));

  }
  
  public static void main1(String[] args) {
 
// Pour avoir la date complète
      Date myDate = getDateByWeek(29, 2014);
System.out.println(myDate.getDate() + "/"+(myDate.getMonth()+1) + "/"+(myDate.getYear()+1900));
 
// Affichage des dates


  }

  public static void main11(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    reqRefSt =  "DELETE FROM PlanOperationnel";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

    req = "SELECT     webpo.*, MacroSt.idMacrost, MacroSt.siMacrost FROM  webPo INNER JOIN   MacroSt ON webPo.[Macro-ST] = MacroSt.nomMacrostWebPo";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {

        String Enveloppe = rs.getString("Type de Financement");
        System.out.println("Enveloppe="+Enveloppe);

        int idWebPo = rs.getInt("ID MOE");
        String nomProjet = rs.getString("Libell� Fiche MOE");
        if (nomProjet != null) nomProjet = nomProjet.replaceAll("'","''"); else nomProjet="";

        String macroSt = rs.getString("Macro-ST");
        int Annee = rs.getInt("Exercice");

        int charge = rs.getInt("Mnts sur Exercice");

        String descProjet = rs.getString("Commentaires Fiche MOE");
        if (descProjet != null) descProjet = descProjet.replaceAll("'","''"); else descProjet="";
        String idPoMacroSt = rs.getString("idMacrost");
        String idPoSi = rs.getString("siMacrost");

System.out.println("Enveloppe="+Enveloppe+"::idWebPo="+nomProjet+"::Annee="+Annee);

          reqRefSt =  "INSERT INTO PlanOperationnel  (idWebPo, Enveloppe,nomProjet,descProjet,charge, Annee, idPoSi , idPoMacroSt) VALUES     ('"+idWebPo+"', '"+Enveloppe+"', '"+nomProjet+"', '"+descProjet+"', '"+charge+"', '"+Annee+"', '"+idPoSi+"', '"+idPoMacroSt+"')";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);


        //if (idPoMacroSt.equals("7"))
        //Connexion.trace("===============>","reqRefSt",reqRefSt);
        /*
      */
      //System.out.println("idActivite="+idActivite+"::donneesEntree="+donneesEntree+"::previousActivite="+previousActivite);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);


          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

    public static void xxxx() {
        System.out.println("coucou les papoux");
    }
    
  public static void importC3PO(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    reqRefSt =  "DELETE FROM PlanOperationnel";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

    req = "SELECT DISTINCT importPO.*, Service.idService, SI.idSI FROM  importPO INNER JOIN   Service ON importPO.MOA = Service.alias INNER JOIN   SI ON importPO.[SIR/SIC] = SI.nomSI";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {

        String Enveloppe = rs.getString("Type financement Reseau");
System.out.println("Enveloppe="+Enveloppe);
        int idWebPo = rs.getInt("ID MOE");
        String nomProjet = rs.getString("Libell� MOE");
        String descProjet = nomProjet;
        int Annee = rs.getInt("Exercice");
        int charge = rs.getInt("Mnts sur Exercice");
        String idPoMoa = rs.getString("idService");
        String idPoSi = rs.getString("idSI");

System.out.println("Enveloppe="+Enveloppe+"::idWebPo="+nomProjet+"::Annee="+Annee);

          reqRefSt =  "INSERT INTO PlanOperationnel  (idWebPo, Enveloppe,nomProjet,descProjet,charge, idPoMoa, Annee, idPoSi ) VALUES     ('"+idWebPo+"', '"+Enveloppe+"', '"+nomProjet+"', '"+descProjet+"', '"+charge+"', '"+idPoMoa+"', '"+Annee+"', '"+idPoSi+"')";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);


        //Connexion.trace(base,"reqRefSt",reqRefSt);
        //System.out.println("idActivite="+idActivite+"::donneesEntree="+donneesEntree+"::previousActivite="+previousActivite);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);


          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

  public static void main9(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    reqRefSt =  "DELETE FROM PlanDeCharge";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

    req = "SELECT  Membre.idMembre, * FROM         ImportChargeXl INNER JOIN  Membre ON ImportChargeXl.Ressource = Membre.LoginMembre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        idMembre= rs.getInt(1);
        NomProjet = rs.getString(2);
        for (int i=1; i <= 53; i++)
        {
          semaine= rs.getFloat("S"+i);
          if (semaine !=0)
          {
          reqRefSt =  "INSERT INTO PlanDeCharge  (nomProjet, projetMembre,semaine,Charge,anneeRef) VALUES     ('"+NomProjet+"', '"+idMembre+"', '"+i+"', '"+semaine+"', '"+"2008"+"')";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          }
        }
        //Connexion.trace(base,"reqRefSt",reqRefSt);
        //System.out.println("idActivite="+idActivite+"::donneesEntree="+donneesEntree+"::previousActivite="+previousActivite);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);


          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

  public static void main8(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String mail, login= "";
    int index = 0;

String reqRefSt="";
    String nomMembre="";
    String prenomMembre="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    //req = "SELECT  VersionSt.auteurVersionSt, Membre.prenomMembre, Membre.nomMembre FROM  VersionSt INNER JOIN  Membre ON VersionSt.auteurVersionSt = Membre.idMembre";
    req = "exec LoginNotInMembre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int num = 0;

    try {
      while (rs.next()) {
        login= rs.getString(1);
        mail = login+"@"+myCnx.mail;;
        prenomMembre=login.substring(0,1);
        nomMembre=login.substring(1, login.length());
        reqRefSt = "INSERT INTO Membre  (nomMembre, prenomMembre,fonctionMembre,serviceMembre,mail,LoginMembre,isAdmin) VALUES     ('"+nomMembre+"', '"+prenomMembre+"', '"+"-- Autre --"+"', '"+15+"', '"+mail+"', '"+login+"', '"+0+"')";


        //System.out.println("auteurVersionSt="+auteurVersionSt+"::prenomMembre="+prenomMembre+"::nomMembre="+nomMembre);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);

          //String reqRefSt = "UPDATE  VersionSt SET  createurVersionSt = '"+prenomMembre+nomMembre+"',modificateurVersionSt = '"+prenomMembre+nomMembre+"'  WHERE     (auteurVersionSt = "+auteurVersionSt+")";

          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          //if (num > 1 ) break;
          //System.out.println("reqRefSt="+reqRefSt);
num++;
        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }
  public static void main7(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String mail, login= "";
    int index = 0;

String reqRefSt="";
    String nomMembre="";
    String prenomMembre="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    //req = "SELECT  VersionSt.auteurVersionSt, Membre.prenomMembre, Membre.nomMembre FROM  VersionSt INNER JOIN  Membre ON VersionSt.auteurVersionSt = Membre.idMembre";
    req = "SELECT     idMembre, mail,nomMembre, prenomMembre FROM   Membre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        idMembre= rs.getInt(1);
        mail= rs.getString(2);
        nomMembre= rs.getString(3);
        prenomMembre=rs.getString(4);
        if (mail !=null)
        {
          index = mail.indexOf("@");
          login = mail.substring(0, index);
          reqRefSt = "UPDATE  Membre SET  LoginMembre = '"+login +"'  WHERE     (idMembre = "+idMembre+")";
        }
        else

        {
            System.out.println("prenomMembre="+prenomMembre+"::nomMembre="+nomMembre);
          login = prenomMembre.substring(0,1)+nomMembre.substring(0,Math.min(8,nomMembre.length()));
          mail = login+"@"+myCnx.mail;;
          reqRefSt = "UPDATE  Membre SET  LoginMembre = '"+login +"',mail = '"+mail+"',fonctionMembre ='MOA'   WHERE     (idMembre = "+idMembre+")";
        }

        //System.out.println("auteurVersionSt="+auteurVersionSt+"::prenomMembre="+prenomMembre+"::nomMembre="+nomMembre);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);

          //String reqRefSt = "UPDATE  VersionSt SET  createurVersionSt = '"+prenomMembre+nomMembre+"',modificateurVersionSt = '"+prenomMembre+nomMembre+"'  WHERE     (auteurVersionSt = "+auteurVersionSt+")";

          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

  public static void main6(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int auteurVersionSt;
    String prenomMembre, nomMembre= "";


    st = myCnx.Connect();
    st2 = myCnx.Connect();


    //req = "SELECT  VersionSt.auteurVersionSt, Membre.prenomMembre, Membre.nomMembre FROM  VersionSt INNER JOIN  Membre ON VersionSt.auteurVersionSt = Membre.idMembre";
    req = "SELECT   Membre.idMembre, Membre.prenomMembre, Membre.nomMembre FROM Anomalie INNER JOIN Membre ON Anomalie.AnoIdMembre = Membre.idMembre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        auteurVersionSt= rs.getInt(1);
        prenomMembre= rs.getString(2);
        nomMembre= rs.getString(3);
        if (prenomMembre != null) prenomMembre = prenomMembre.substring(0,1).toUpperCase();
        if (nomMembre != null) nomMembre = nomMembre.substring(0,Math.min(7,nomMembre.length())).toUpperCase();
        //System.out.println("auteurVersionSt="+auteurVersionSt+"::prenomMembre="+prenomMembre+"::nomMembre="+nomMembre);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);

          //String reqRefSt = "UPDATE  VersionSt SET  createurVersionSt = '"+prenomMembre+nomMembre+"',modificateurVersionSt = '"+prenomMembre+nomMembre+"'  WHERE     (auteurVersionSt = "+auteurVersionSt+")";
          String reqRefSt = "UPDATE  Anomalie SET  createurAnomalie = '"+prenomMembre+nomMembre +"'  WHERE     (AnoIdMembre = "+auteurVersionSt+")";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }
  public static void main5(String[] args) {
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int auteurVersionSt;
    String prenomMembre, nomMembre= "";


    st = myCnx.Connect();
    st2 = myCnx.Connect();


    req = "SELECT  VersionSt.auteurVersionSt, Membre.prenomMembre, Membre.nomMembre FROM  VersionSt INNER JOIN  Membre ON VersionSt.auteurVersionSt = Membre.idMembre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        auteurVersionSt= rs.getInt(1);
        prenomMembre= rs.getString(2);
        nomMembre= rs.getString(3);
        if (prenomMembre != null) prenomMembre = prenomMembre.substring(0,1).toUpperCase();
        if (nomMembre != null) nomMembre = nomMembre.substring(0,Math.min(7,nomMembre.length())).toUpperCase();
        //System.out.println("auteurVersionSt="+auteurVersionSt+"::prenomMembre="+prenomMembre+"::nomMembre="+nomMembre);
        //Connexion.trace(base,"nomObjetMetier",nomObjetMetier);

          String reqRefSt = "UPDATE  VersionSt SET  createurVersionSt = '"+prenomMembre+nomMembre+"',modificateurVersionSt = '"+prenomMembre+nomMembre+"'  WHERE     (auteurVersionSt = "+auteurVersionSt+")";
          rs2 = myCnx.ExecReq(st2, base, reqRefSt);
          //System.out.println("reqRefSt="+reqRefSt);

        }


    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);
  }

}
