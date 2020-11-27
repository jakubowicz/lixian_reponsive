package ST; 
 
import General.Utils;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import java.util.Date;
import java.util.*;
import Graphe.Node;
import accesbase.ErrorSpecific;

public class MacroSt {
  public int id=-1;
  public String nom="";
  public String desc="";
  public String AccesRapide="no";
  public String SI;
  public int idSi;
  public int x;
  public int y;
  public final static int L=26;
  public final static int H=26;

  public String nodes = "";

  private String req="";

  public int nbStRouge;
  public int nbAppliRouge;
  public int nbStBleu;
  
  public int isReferentiel=0;
  public int isTechnique=0;

   public static Vector ListeMacrtoSt = new Vector(10);
   public  Vector ListeSt = new Vector(10);
   public Vector ListeInterfaces = new Vector(20);

  public MacroSt(int id,String nom,String SI,int x,int y) {
    this.id=id;
    this.nom=nom;
    this.SI=SI;
    this.x=x;
    this.y=y;
  }

  public MacroSt(String nom) {
    this.nom=nom;
  }

  public MacroSt(int id) {
  this.id=id;
}

public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

if (this.id != -1)
  {
    req = "SELECT   idMacrost, nomMacrost, descMacrost, siMacrost, CartoBulle, idCouche, offset_X  FROM     MacroSt WHERE  idMacrost = " +
        this.id;
  }
  else
  {
    req = "SELECT   idMacrost, nomMacrost, descMacrost, siMacrost, CartoBulle, idCouche, offset_X  FROM     MacroSt WHERE  nomMacrost = '" +
        this.nom+"'";
  }
  rs = myCnx.ExecReq(st, nomBase, req);
  String nomMacrost="";
  String descMacrost="";
  String siMacrost="";

     try {
          rs.next();
          this.id=rs.getInt("idMacrost");
          this.nom=rs.getString("nomMacrost");
          if ((this.nom == null) || this.nom.equals("null")) this.nom ="";
          this.desc=rs.getString("descMacrost");
          if ((this.desc == null) || this.desc.equals("null")) this.desc ="";
          this.SI=rs.getString("siMacrost");
          this.AccesRapide=rs.getString("CartoBulle");
          this.isReferentiel=rs.getInt("idCouche");
          this.isTechnique=rs.getInt("offset_X");
         }

         catch (SQLException s) {
           s.getMessage();
         }

    
}

  public static int getIdRef(String nomBase,Connexion myCnx, Statement st){
      
    int idRef=-1;
    String req = "SELECT     idMacrost, idCouche FROM  MacroSt WHERE     (idCouche = 1)";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
	try {
          rs.next(); idRef=rs.getInt(1);
        }
      catch (SQLException s) {s.getMessage(); }
        
     return idRef;   
  }
  
  public int getId(String nomBase,Connexion myCnx, Statement st){
    int idRef=-1;
    req = "SELECT idMacrost FROM    MacroSt WHERE     (nomMacrost  LIKE '%Referentiel%') AND siMacrost = "+this.SI+"";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
	try {
          rs.next(); idRef=rs.getInt(1);
        }
      catch (SQLException s) {s.getMessage(); }
        return idRef;
  }

  public void deleteStFromGraph(String nomBase,Connexion myCnx, Statement st,int idMacroStRef ){

    int [] idStToDel=new int[10];
    int nbToDel=0;
    req = "exec [ST_notInMacroSt] "+this.id+","+idMacroStRef;
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
            //myCnx.trace("*********************************","rs",""+rs);
            try { while(rs.next()) {
             // myCnx.trace("2*********************************","nbToDel",""+nbToDel);
              idStToDel[nbToDel]= rs.getInt(1);
              //myCnx.trace("*********************************","idStToDel["+nbToDel+"]",""+idStToDel[nbToDel]);
              nbToDel++;
            } }catch (Exception e){};

    for (int i=0; i < nbToDel; i++)
    {
            req = "DELETE FROM   GrapMacroSt WHERE     (idSt ="+ idStToDel[i]+")";
            rs = myCnx.ExecReq(st, nomBase, req);
    }
  }

  public String getListeInterfaces(String nomBase,Connexion myCnx, Statement st, int idMacroSt){
    ResultSet rs;
    String param_edges = "";
    
    if (this.isTechnique == 0)
        req = "EXEC MACROST_SelectInterfaceByListe "+this.id+","+idMacroSt;
    else
    {
        req="SELECT     Interface.idInterface, Interface.origineInterface, St.nomSt AS nomStOrigine, VersionSt.versionRefVersionSt AS versionRefVersionStOrigine, ";
        req+=" TypeAppli.formeTypeAppli AS formeTypeAppliOrigine, TypeSi.couleurSI AS couleurSIOrigine, Interface.sensInterface, Interface.extremiteInterface, ";
        req+=" St_1.nomSt AS nomStExtremite, VersionSt_1.versionRefVersionSt AS versionRefVersionStExtremite, TypeAppli_1.formeTypeAppli AS formeTypeAppliExtremite, ";
        req+=" TypeSi_1.couleurSI AS couleurSIExtremite, Interface.typeInterface, VersionSt.etatVersionSt AS etatVersionStOrigine, ";
        req+=" VersionSt_1.etatVersionSt AS etatVersionStExtremite, VersionSt.idVersionSt AS idVersionSt_origine, VersionSt_1.idVersionSt AS idVersionSt_destination, ";
        req+=" VersionSt.macrostVersionSt AS macrostVersionSt_origine, VersionSt_1.macrostVersionSt AS macrostVersionSt_destination";
        req+=" FROM         Interface INNER JOIN";
        req+=" St ON Interface.origineInterface = St.idSt INNER JOIN";
        req+=" St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
        req+=" VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
        req+=" VersionSt AS VersionSt_1 ON St_1.idSt = VersionSt_1.stVersionSt INNER JOIN";
        req+=" TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
        req+=" TypeAppli AS TypeAppli_1 ON St_1.typeAppliSt = TypeAppli_1.idTypeAppli INNER JOIN";
        req+=" TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
        req+=" TypeSi AS TypeSi_1 ON VersionSt_1.typeSiVersionSt = TypeSi_1.idTypeSi";
        req+=" WHERE   ";
        req+="    (VersionSt.serviceMetier = "+this.id+") AND (VersionSt_1.serviceMetier = "+this.id+") AND (VersionSt.etatVersionSt <> 4) AND ";
        req+="    (VersionSt_1.etatVersionSt <> 4)";
    }

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while (rs.next()) {
        
        Interface theInterface = new Interface(rs.getInt(1));
               
            theInterface.StOrigine = new ST(rs.getInt(2),"idSt");
            theInterface.StOrigine.nomSt = rs.getString(3); //nomOrigine-version
            theInterface.StOrigine.TypeApplication = rs.getString(5); //formeTypeAppli_origine
            theInterface.StOrigine.couleur = rs.getString(6); //couleurSI_origine
            
            theInterface.sensInterface = rs.getString(7); //sensInterface

            theInterface.StDestination = new ST(rs.getInt(8),"idSt");
            theInterface.StDestination.nomSt = rs.getString(9); //nomOrigine-version
            theInterface.StDestination.TypeApplication = rs.getString(11); //formeTypeAppli_origine
            theInterface.StDestination.couleur = rs.getString(12); //couleurSI_origine
                       
            String ListeOM = ""; //liste des OM de cette interface
            theInterface.typeInterface = rs.getString(13); //Type d'interface
            
            theInterface.StOrigine.idVersionSt = rs.getInt("idVersionSt_origine");
            theInterface.StDestination.idVersionSt = rs.getInt("idVersionSt_destination");
            //nbInterfaces++;
/*
            if (theInterface.typeInterface.equals("ST"))
              theInterface.typeInterface="St2Appli";
            else if (theInterface.typeInterface.equals("Heb"))
              theInterface.typeInterface="Heb";
            else if (theInterface.typeInterface.equals("Mod"))
              theInterface.typeInterface="Mod";
            else if (theInterface.typeInterface.equals("M"))
              theInterface.typeInterface="manuel";
            else if (theInterface.typeInterface.equals("F"))
              theInterface.typeInterface="F";
            else if (theInterface.typeInterface.equals("I"))
              theInterface.typeInterface="I";            
            else
              theInterface.typeInterface="normal";
*/
        theInterface.StOrigine.NodeSt = new Node( theInterface.StOrigine.nomSt);
        theInterface.StOrigine.NodeSt.idGraphe=this.id;
        theInterface.StOrigine.NodeSt.nomGraphe="GrapMacroSt";
        theInterface.StOrigine.NodeSt.idNode =  theInterface.StOrigine.idSt;      
        theInterface.StOrigine.NodeSt.couleur =  theInterface.StOrigine.couleur;
        theInterface.StOrigine.NodeSt.forme =  theInterface.StOrigine.TypeApplication;

        theInterface.StDestination.NodeSt = new Node( theInterface.StDestination.nomSt);
        theInterface.StDestination.NodeSt.idGraphe=this.id;
        theInterface.StOrigine.NodeSt.nomGraphe="GrapMacroSt";
        theInterface.StDestination.NodeSt.idNode =  theInterface.StDestination.idSt;  
        theInterface.StDestination.NodeSt.couleur =  theInterface.StDestination.couleur;
        theInterface.StDestination.NodeSt.forme =  theInterface.StDestination.TypeApplication;  
        
        theInterface.descInterface = rs.getString("descInterface");
        if (theInterface.descInterface == null) theInterface.descInterface = "";
        
            this.ListeInterfaces.addElement(theInterface);
        param_edges+=theInterface.StOrigine.nomSt+"#"+theInterface.StDestination.nomSt+"!"+ListeOM+"!"+theInterface.sensInterface+"!"+theInterface.typeInterface+",";
}	} catch (SQLException s) {s.getMessage(); }
      return param_edges;

  }

  public String getListeInterfaces2(String nomBase,Connexion myCnx, Statement st, int idMacroSt){
    ResultSet rs;
    String param_edges = "";
    req = "EXEC MACROST_SelectInterfaceByListe "+this.id+","+idMacroSt;

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while (rs.next()) {
            String idInterface = rs.getString(1); //idInterface
            String idStOrigine = rs.getString(2); //idStOrigine
            String nomOrigine = rs.getString(3); //nomOrigine-version
            String formeTypeAppli_origine = rs.getString(5); //formeTypeAppli_origine
            String couleurSI_origine = rs.getString(6); //couleurSI_origine
            String sensInterface = rs.getString(7); //sensInterface
            String idStExtremite = rs.getString(8); //idStExtremite
            String nomExtremite = rs.getString(9); //nomExtremite-version
            String formeTypeAppli_extremite = rs.getString(11); //formeTypeAppli_extremite
            String couleurSI_extremite = rs.getString(12); //couleurSI_extremite
            String ListeOM = ""; //liste des OM de cette interface
            String Type = rs.getString(13); //Type d'interface
            //nbInterfaces++;

            if (Type.equals("ST"))
              Type="St2Appli";
            else if (Type.equals("Heb"))
              Type="Heb";
            else if (Type.equals("Mod"))
              Type="Mod";
            else if (Type.equals("M"))
              Type="manuel";
            else if (Type.equals("F"))
              Type="F";
            else
              Type="normal";

        param_edges+=nomOrigine+"#"+nomExtremite+"!"+ListeOM+"!"+sensInterface+"!"+Type+",";
}	} catch (SQLException s) {s.getMessage(); }
      return param_edges;

  }
  public String getListeSt(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String param_nodes = "";
    req = "exec MACROST_SelectStByMacroSt '"+this.nom+"'";

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while(rs.next()) {
      String nomSt = rs.getString(1);
      int idVersionSt = rs.getInt(2);
      if (nomSt != null)
      {
        ST theST = new ST(nomSt);
        theST.idVersionSt = idVersionSt;
        this.ListeSt.addElement(theST);
      }

        } }catch (Exception e){};

      return param_nodes;

  }

  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
    String description="";

    if (this.desc != null)
    {
      //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
      for (int j=0; j < this.desc.length();j++)
      {
        int c = (int)this.desc.charAt(j);
        //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
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

    req = "UPDATE [MacroSt]";
    req+="    SET ";
    req+="    [nomMacrost] = '"+ this.nom+ "'";
    req+="   ,[siMacrost] = '"+ this.SI+ "'";
    req+="   ,[descMacrost] = '"+ description+ "'";
    req+="   ,[CartoBulle] = '"+ this.AccesRapide+ "'";
    req+="   ,[idCouche] = '"+ this.isReferentiel+ "'";
    req+="   ,[offset_X] = '"+ this.isTechnique+ "'";
    req+=" WHERE idMacrost=" + this.id;
    
    

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);


    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT     St.nomSt";
    req+= " FROM         MacroSt INNER JOIN";
    req+= "                       VersionSt ON MacroSt.idMacrost = VersionSt.macrostVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt";
    req+= " WHERE     MacroSt.idMacrost ="+ this.id;
    req+= " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";else ListeSt += " // Produits: ";
      ListeSt += rs.getString(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  
  req = "SELECT     Processus.nomProcessus";
  req+=  " FROM         MacroSt INNER JOIN";
  req+=  "                      Processus ON MacroSt.idMacrost = Processus.ProcessusMacroSt";
  req+=  " WHERE     MacroSt.idMacrost = "+ this.id;  
  req+=  " ORDER BY Processus.nomProcessus";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  nb=0;

  try {
    while (rs.next()) {
      if (nb!=0) ListeSt += ", ";else ListeSt += " // Processus: ";
      ListeSt += rs.getString(1) ;
      nb++;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  } 
  
  
  if (!ListeSt.equals("")) {
    myError.Detail = ListeSt;
    myError.cause = -2;
    return myError;
  }    
    
    req = "DELETE FROM MacroSt WHERE (idMacrost  = '"+this.id+"')";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.desc != null)
    mydesc=this.desc.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [MacroSt]";
    req+= "           ([nomMacrost]";
    req+= "            ,[aliasMacrost]";
    req+= "            ,[siMacrost]";
    req+= "            ,[descMacrost]";
    req+= "            ,[CartoBulle]";
    req+= "            ,[nomMacrostSpinoza]";
    req+= "            ,[nomMacrostWebPo]";
    req+= "            ,[idCouche]";
    req+= "            ,[offset_X]";
    req+= "            ,[offset_Y]";
    req+= "            ,[map_X]";
    req+= "            ,[map_Y])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+this.idSi+"'" + ",";
    req+= "'"+mydesc+"'" + ",";
    req+= "'"+this.AccesRapide+"'"+ ",";
    req+= "'"+""+"'"+ ",";
    req+= "'"+""+"'"+ ",";
    req+= "'"+ this.isReferentiel+"'"+ ",";
    req+= "'"+ this.isTechnique+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'";
    req+= "            )";

      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;

      req="SELECT  idMacrost FROM   MacroSt ORDER BY idMacrost DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idMacrost");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }
  
  public String bd_Enreg(String nomBase,Connexion myCnx, Statement st, String transaction){

    String nomOm;
    int numVersionSt;
    String theVersion = "";
    int idOm=0;int idOtherOm = 0;
    int idGraph = 0;
    int j=0; int k=0;

    ResultSet rs=null;


// la versionSt a-t-elle d�j� un graph ?
    req = "delete FROM  GrapMacroSt  WHERE idMacroSt = "+this.id;
    rs = myCnx.ExecReq(st, nomBase, req);

//R�cup�ration des nodes et cr�ation des GraphSt

//out.println(nodes+"<br><br>");
    String nom = "";
//int num = 0;
    int abscisse, ordonnee;
    String str=""; String str1=""; String str2="";
    String idSt = "";
   // myCnx.trace(nomBase,"<<<<<<<<<<<<<<<<<<<<<<<nodes=",this.nodes);
    for (StringTokenizer t = new StringTokenizer(this.nodes, "$"); t.hasMoreTokens();) {

            str = t.nextToken(); // format de str : [nomOm] v[numVersionSt]*[abscisse]*[ordonn�e]
            //myCnx.trace(nomBase,"********************str=",str);
            k = str.indexOf('*');
            str1 = str.substring(0,k);
            //c1.trace(base,"********************str1=",str1);
            str2 = str.substring(k+1);
            //c1.trace(base,"********************str2=",str2);
            //Traitements sur str1
            j = -1;
            //nom = str1.substring(0,j-1);

            if (j == -1)
            {
              nom = str1;
            }
            else
            {
              nom = str1.substring(0,j-1);
              //c1.trace(base,"********************nom=",nom);
            }


            //num = Integer.parseInt(str1.substring(j+1));
            //Traitements sur str2 : abscisse*ordonnee
            //myCnx.trace(nomBase,"********************str2=",""+str2);
            j = str2.indexOf('*');
            //myCnx.trace(nomBase,"********************j=",""+j);
            abscisse = Integer.parseInt(str2.substring(0,j));
            //myCnx.trace(nomBase,"********************abscisse=",""+abscisse);
            ordonnee = Integer.parseInt(str2.substring(j+1));
            //myCnx.trace(nomBase,"********************ordonnee=",""+ordonnee);

            //R�cup�ration de l'idOm
            //req = "SELECT     idSt FROM    St WHERE  nomSt = '"+nom+"'";
            //rs = myCnx.ExecReq(st, nomBase, req);
            //try { rs.next(); idSt = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}

            idSt = nom;
            //Cr�ation des GraphSt
            req = "INSERT GrapMacroSt (idMacroSt, idSt, x, y) VALUES ("+this.id+", "+idSt+", "+abscisse+", "+ordonnee+")";
            if (idSt != null && !idSt.equals(""))
            {
              if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
                myCnx.trace(nomBase, "*** ERREUR *** req", req);
                return req;
              }
            }
          } //end for
          return "OK";
  }

  public int getAppliRouge(String nomBase,Connexion myCnx, Statement st){
    this.nbAppliRouge=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.macrostVersionSt = "+this.id+") order by ListeST.nomSt asc";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbAppliRouge++;
        }
       } catch (SQLException s) {s.getMessage();}
     return this.nbAppliRouge;
}

  public int getSiRouge(String nomBase,Connexion myCnx, Statement st){
    this.nbStRouge=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isST = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.macrostVersionSt = "+this.id+") order by ListeST.nomSt asc";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbStRouge++;
        }
       } catch (SQLException s) {s.getMessage();}
     return this.nbStRouge;
}

   public int getSiBleu(String nomBase,Connexion myCnx, Statement st){
     this.nbStBleu=0;
     req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
     req+= "   FROM         ListeST INNER JOIN";
     req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
     req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req+= " WHERE     (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.etatVersionSt = 3) AND (ListeST.macrostVersionSt = "+this.id+") order by ListeST.nomSt asc";


      ResultSet rs = myCnx.ExecReq(st, nomBase, req);

      try {
         while (rs.next()) {
           int idSt = rs.getInt("idSt");
           String nomSt = rs.getString("nomSt");
           int idVersionSt = rs.getInt("idVersionSt");
           if (nomSt != null)
           {
             ST theST = new ST(nomSt);
             theST.idVersionSt = idVersionSt;
             theST.idSt = idSt;
             this.ListeSt.addElement(theST);
           }

        this.nbStBleu++;
         }
       } catch (SQLException s) {s.getMessage();}

      return this.nbStBleu;
}

  public String getListeStNotInGraph(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String param_nodes = "";
    //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
    req = "SELECT     ListeST.nomSt, TypeAppli.formeTypeAppli, TypeSi.couleurSI, ListeST.idVersionSt, GrapMacroSt.x, GrapMacroSt.y, MacroSt.idMacrost,ListeST.idSt FROM GrapMacroSt LEFT OUTER JOIN MacroSt ON GrapMacroSt.idMacroSt = MacroSt.idMacrost LEFT OUTER JOIN                      TypeAppli INNER JOIN                      ListeST ON TypeAppli.idTypeAppli = ListeST.typeAppliSt INNER JOIN TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi ON GrapMacroSt.idSt = ListeST.idSt";
    req+= " WHERE     (MacroSt.idMacrost = "+this.id+")" ;

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while(rs.next()) {
      String nomSt = rs.getString(1);
      if (nomSt != null)
      {
        ST theST = new ST(nomSt);
        theST.TypeApplication = rs.getString(2);
        theST.couleur = rs.getString(3);
        theST.idVersionSt = rs.getInt(4);
        theST.x = rs.getInt(5);
        theST.y = rs.getInt(6);
        theST.idSt = rs.getInt("idSt");

        param_nodes+=nomSt + "!" +theST.idSt+"!"+theST.TypeApplication+"!"+theST.couleur+"!"+theST.x+"!"+theST.y+"!"+"rien"+"!"+theST.idVersionSt+",";
      }

        } }catch (Exception e){};

      return param_nodes;

  }

  public static void getListeMacroSt(String nomBase,Connexion myCnx, Statement st){

   String req="EXEC LISTEMACROST_SelectMacrost";
   ListeMacrtoSt.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   MacroSt theMacroSt = null;
   try {
      while (rs.next()) {
        String nomSI = rs.getString(1);
        int idMacroSt = rs.getInt(2);
        String nomMacroSt = rs.getString(3);

        theMacroSt = new MacroSt(idMacroSt,nomMacroSt,nomSI,0,0);
        ListeMacrtoSt.addElement(theMacroSt);
      }
       } catch (SQLException s) {s.getMessage();}

}
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("SI="+this.SI);
    System.out.println("nbStRouge="+this.nbStRouge);
    System.out.println("nbStBleu="+this.nbStBleu);
    System.out.println("nbStRouge="+this.nbStRouge);
    System.out.println("nbStBleu="+this.nbStBleu);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {

  }
}
