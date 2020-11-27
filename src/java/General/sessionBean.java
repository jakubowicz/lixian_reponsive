package General;
 
/**
 * <p>Titre : Bean de Session de lixian</p>
 *
 * <p>Description : permet de r�cup�rer les principaux param�tres en cache</p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class sessionBean {
  private String login;
  private String machine;
  private String typeCarto;
  private String lookCarto;
  private String profil;
  private String admin;
  private String mailingList;
  private String mail;
  private String sessionId;
  private String oldLogin;
  private int width;
  private int height;
  private boolean jreIsOk;
  private boolean jreIsSet=false;
  private boolean isAuthenticated = false;
  private String manager;
  private String MOA;
  private String MOE;
  private String versionJreOk;
  private String currentPath;
  private String GOUVERNANCE;
  private String client;
  private String produit;
  private String catalogue;
  private boolean _isIE;
  
  private int jreVersion;
  private String navigateur;
  private int navigateurVersion;
  private boolean appletSupported;
  
  private String GraphesTechno;
  private String planDeChargeTechno;
  
  private int nbCnx=0;
  
  private String Couche_1;
  private String Couche_2;
  private String Couche_3;
  private String Couche_4;
  private String Couche_5;
  
  private String Jalon_0;  
  private String Jalon_1; 
  private String Jalon_2; 
  private String Jalon_3; 
  private String Jalon_4; 
  private String Jalon_5; 
  private String Jalon_6; 
  
  private int idJalon_0 = -1;
  private int idJalon_1 = -1;
  private int idJalon_2 = -1;
  private int idJalon_3 = -1;
  private int idJalon_4 = -1;
  private int idJalon_5 = -1;
  
  private String Periode_1; 
  private String Periode_2;
  private String Periode_3;
  private String Periode_4;
  
  public sessionBean() {
  }

  public void setCouche_1(String Couche_1) {
    this.Couche_1 = Couche_1;
  }
  public String getCouche_1() {
    return this.Couche_1;
  } 
  public void setCouche_2(String Couche_2) {
    this.Couche_2 = Couche_2;
  }
  public String getCouche_2() {
    return this.Couche_2;
  } 
  public void setCouche_3(String Couche_3) {
    this.Couche_3 = Couche_3;
  }
  public String getCouche_3() {
    return this.Couche_3;
  } 
  public void setCouche_4(String Couche_4) {
    this.Couche_4 = Couche_4;
  }
  public String getCouche_4() {
    return this.Couche_4;
  } 
  public void setCouche_5(String Couche_5) {
    this.Couche_5 = Couche_5;
  }
  public String getCouche_5() {
    return this.Couche_5;
  }   
 
  public void setJalon_0(String Jalon_0) {
    this.Jalon_0 = Jalon_0;
  }
  public String getJalon_0() {
    return this.Jalon_0;
  }
  
  public void setidJalon_0(int idJalon_0) {
    this.idJalon_0 = idJalon_0;
  }
  
  public int getidJalon_0() {
    return this.idJalon_0;
  }    
  
  public void setJalon_1(String Jalon_1) {
    this.Jalon_1 = Jalon_1;
  }
 
  public String getJalon_1() {
    return this.Jalon_1;
  }
  
  public void setidJalon_1(int idJalon_1) {
    this.idJalon_1 = idJalon_1;
  }
  
  public int getidJalon_1() {
    return this.idJalon_1;
  }  

  public void setJalon_2(String Jalon_2) {
    this.Jalon_2 = Jalon_2;
  }
  public String getJalon_2() {
    return this.Jalon_2;
  }
  
  public void setidJalon_2(int idJalon_2) {
    this.idJalon_2 = idJalon_2;
  }
  
  public int getidJalon_2() {
    return this.idJalon_2;
  }
  
  public void setJalon_3(String Jalon_3) {
    this.Jalon_3 = Jalon_3;
  }
  public String getJalon_3() {
    return this.Jalon_3;
  }  
  
  public void setidJalon_3(int idJalon_3) {
    this.idJalon_3 = idJalon_3;
  }
  
  public int getidJalon_3() {
    return this.idJalon_3;
  }
  
  public void setJalon_4(String Jalon_4) {
    this.Jalon_4 = Jalon_4;
  }
  public String getJalon_4() {
    return this.Jalon_4;
  } 
  
  public void setidJalon_4(int idJalon_4) {
    this.idJalon_4 = idJalon_4;
  }
  
  public int getidJalon_4() {
    return this.idJalon_4;
  }
  
  public void setJalon_5(String Jalon_5) {
    this.Jalon_5 = Jalon_5;
  }
  public String getJalon_5() {
    return this.Jalon_5;
  } 
  
  public void setidJalon_5(int idJalon_5) {
    this.idJalon_5 = idJalon_5;
  }
  
  public int getidJalon_5() {
    return this.idJalon_5;
  }
  
  public void setJalon_6(String Jalon_6) {
    this.Jalon_6 = Jalon_6;
  }
  public String getJalon_6() {
    return this.Jalon_6;
  }    
  
  public void setPeriode_1(String Periode_1) {
    this.Periode_1 = Periode_1;
  }
  public String getPeriode_1() {
    return this.Periode_1;
  } 
  
  public void setPeriode_2(String Periode_2) {
    this.Periode_2 = Periode_2;
  }
  public String getPeriode_2() {
    return this.Periode_2;
  }  
  
  public void setPeriode_3(String Periode_3) {
    this.Periode_3 = Periode_3;
  }
  public String getPeriode_3() {
    return this.Periode_3;
  }  
  
  public void setPeriode_4(String Periode_4) {
    this.Periode_4 = Periode_4;
  }
  public String getPeriode_4() {
    return this.Periode_4;
  }   
  

  // ------------------------------- Gestion des navigateurs -----------------------------------------//
  public int getJreVersion() {
    return this.jreVersion;
  }
  public void setJreVersion(int jreVersion) {
    this.jreVersion = jreVersion;
  }  
  
  public String getNavigateur() {
    return this.navigateur;
  }
  
  public void setNavigateur(String navigateur) {
    this.navigateur = navigateur;
  }  

  public int getNbCnx() {
    return this.nbCnx;
  }
  
  public int getNavigateurVersion() {
    return this.navigateurVersion;
  }
  
  public void setNavigateurVersion(String str_navigateurVersion) {
    try{
        this.navigateurVersion = Integer.parseInt(str_navigateurVersion);
    }
    catch(Exception e)
    {
       this.navigateurVersion = -1; 
    }
  }   
  
  public boolean getAppletSupported() {
    return appletSupported;
  }
  
  public void setAppletSupported(int AppletNotSupported) {
      if (AppletNotSupported == 1)
        this.appletSupported = false;
      else
        this.appletSupported = true;
      
      if (this.navigateur.equals("IE"))
      {
          if (this.navigateurVersion < 11)
          {
              this.planDeChargeTechno = "Html";
              if (this.appletSupported)
              {
                  this.GraphesTechno = "Applets";
              }
              else
              {
                  this.GraphesTechno = "Interdire";
              }                  
          }
          else
          {
              this.planDeChargeTechno = "Canvas";
              this.GraphesTechno = "Canvas";
          }
      }
      else if (this.navigateur.equals("Chrome") || this.navigateur.equals("Safari") || this.navigateur.equals("Firefox"))
      {
              this.planDeChargeTechno = "Canvas";
              this.GraphesTechno = "Canvas";          
      } 
      else
      {
              this.planDeChargeTechno = "Html";
              this.GraphesTechno = "Interdire";           
      }
  }  
  
  public String getGraphesTechno() {
    return this.GraphesTechno;
  }  
  
  public String getPlanDeChargeTechno() {
    return this.planDeChargeTechno;
  }   
  
// -------------------------------------------------------------------------------------------------//  
  public static void main(String[] args) {
    sessionBean mySessionBean = new sessionBean();
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public void setTypeCarto(String typeCarto) {
    this.typeCarto = typeCarto;
  }

  public void setLookCarto(String lookCarto) {
    this.lookCarto = lookCarto;
  }

  public String getLogin() {
    return login;
  }

  public String getMachine() {
    return machine;
  }

  public String getTypeCarto() {
    return typeCarto;
  }
  
  public String getClient() {
    return client;
  }  
  
  public String getProduit() {
    return produit;
  }  
  
  public String getCatalogue() {
    return catalogue;
  }   

  public String getLookCarto() {
    return lookCarto;
  }
  public String getProfil() {
    return profil;
  }
  public void setProfil(String profil) {
    this.profil = profil;
  }
  public String getAdmin() {
    return admin;
  }
  public void setAdmin(String admin) {
    this.admin = admin;
  }
  public String getMailingList() {
    return mailingList;
  }
  public String getMail() {
    return mail;
  }  
  public void setMailingList(String mailingList) {
    this.mailingList = mailingList;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }  
  public String getSessionId() {
    return sessionId;
  }

  public String getOldLogin() {
    return oldLogin;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isJreIsOk() {
    return jreIsOk;
  }

  public boolean isIsAuthenticated() {
    this.nbCnx++;
    //if ((this.nbCnx >1) ) this.isAuthenticated = false;
    return isAuthenticated;
  }

  public String getManager() {
    return manager;
  }

  public String getVersionJreOk() {
    return versionJreOk;
  }

  public String getCurrentPath() {
    return currentPath;
  }

  public String getGOUVERNANCE() {
    return GOUVERNANCE;
  }

  public boolean getNavigateurIsIE() {
    return this._isIE;

  }
  
  public String getMOE() {
    return MOE;
  }

  public String getMOA() {
    return MOA;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public void setOldLogin(String oldLogin) {
    this.oldLogin = oldLogin;
  }
  
  public void setClient(String client) {
    this.client = client;
  }  
  
  public void setProduit(String produit) {
    this.produit = produit;
  }  
  
  public void setCatalogue(String catalogue) {
    this.catalogue = catalogue;
  }  
  
  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }
  
  public void setNbCnx(int nbCnx) {
    this.nbCnx = nbCnx;
  }  

  public void setIsAuthenticated(boolean isAuthenticated) {
    this.isAuthenticated = isAuthenticated;
    
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public void setVersionJreOk(String versionJreOk) {
    if (versionJreOk == null)
    {
      this.versionJreOk = versionJreOk;
      return;
    }
    if (versionJreOk.equals("true"))
      versionJreOk = "yes";
    else
      versionJreOk = "no";

    this.versionJreOk = versionJreOk;
  }

  public void setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
  }

  public void setGOUVERNANCE(String GOUVERNANCE) {
    this.GOUVERNANCE = GOUVERNANCE;
  }

  public void setMOE(String MOE) {
    this.MOE = MOE;
  }

  public void setMOA(String MOA) {
    this.MOA = MOA;
  }

  public void setJreIsOk(String jreIsOk) {
    if (this.jreIsSet) return;
    if (jreIsOk.equals("true")) this.jreIsOk = true;
    else this.jreIsOk = false;
    this.jreIsSet = true;

  }
  
  public void setNavigateurIsIE(String isIE) {
    if (isIE.equals("true")) this._isIE = true;
    else this._isIE = false;

  }
  
}
