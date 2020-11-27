<%@ page import="accesbase.Connexion"%>
<%-- 
    Document   : index
    Created on : 4 nov. 2018, 13:57:38
    Author     : Joël
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" import="java.sql.*" errorPage="" %>
<jsp:useBean id="mySessionBean" scope="session" class="General.sessionBean"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="icon" href="assets/images/logo.png" type="image/gif" sizes="16x16">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta charset="UTF-8">
    <title>liXian technology&copy; </title>
    <meta name="description" content="liXian technology&copy; est une startup  intervenant en Consulting de Syst&egrave;mes d'information et en Int&eacute;grateur de  solutions de gestion de projets.<br>
Lixian Technology&copy; &eacute;dite le logiciel de  gestion de portefeuille projets liXian&copy;."/>

    <!-- ====== Google Fonts ====== -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600" rel="stylesheet">

    <!-- ====== ALL CSS ====== -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/lightbox.min.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    
    <link rel="stylesheet" href="assets/css/login.css">

    <style>
        
@media screen and (max-width: 640px) {
   
    #title_accueil{
        margin-top: -70px;
        margin-bottom: 20px;
    } 
    
}   

@media screen and (min-width: 1024px) {
    
    #counter {
        padding-top: 10px;
        padding-bottom: 0px;
        margin-top: -10px;
    }

}
    

.contact {
    color: #ffffff;
} 
.contact:hover {
    color: #000;
}
#Summary{
    margin-top: 5px;
    width : 100%;
    color: #000;
}
#more{
    margin-top: 10px;
}
#titre{
    margin-top: -30px;
}

    </style>
</head>
<%

//------------------------------ Connexion a la base ----------------------------------------------------
Connexion c1 = null ;
c1.readConfigWeb(this.getServletContext(), "");

String displayConnexion = "";
if (c1.isStandalone == 1) displayConnexion = "none";

// ---------------------------- authentification --------------------------------------------------------
String username= "";
String psw= "";
Cookie[] listCookie;
listCookie=request.getCookies();
try{
    username= c1.findCookieValue("","username",listCookie);
    psw= c1.findCookieValue("","psw",listCookie);
}
catch (Exception e)
{
    
}
%>

<body data-spy="scroll" data-target=".navbar-nav">
     
 <input type="hidden" id="idContact" value="-1">   
     <!-- Preloader -->
    <div class="preloader">
        <div class="spinner">
            <div class="cube1"></div>
            <div class="cube2"></div>
        </div>
    </div>
    <!-- // Preloader -->
    

    <!-- ====== Header ====== -->
    <header id="header" class="header header_style_01">
        <!-- ====== Navbar ====== -->
        <nav class="navbar navbar-expand-lg fixed-top bg-primari">
            <div class="container">
                <!-- Logo -->
                <a class="navbar-brand logo" href="index.jsp" id="logo">
                    <img src="assets/images/logo.png" alt="liXian Technology&copy;" width="50" height="60">
                    liXian Technology&copy;
                </a>
                <!-- // Logo -->

                <!-- Mobile Menu -->
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-expanded="false"><span><i class="fa fa-bars"></i></span></button>
                <!-- Mobile Menu -->

                <div class="collapse navbar-collapse main-menu" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active"><a class="nav-link" href="#home">ACCUEIL</a></li>                      
                        <li class="nav-item"><a class="nav-link" href="#produit">PRODUITS</a></li>
                        <li class="nav-item"><a class="nav-link" href="#demo">DEMO</a></li>
                        <li class="nav-item"><a class="nav-link" href="#faq">FAQ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#about">A PROPOS</a></li>
                        <li class="nav-item"><a class="nav-link pr0" href="#contact">CONTACT</a></li>
                        <li class="nav-item"  style="display:<%=displayConnexion%>"><a href="#" class="nav-link pr0" data-toggle="modal" data-target="#login-modal">CONNEXION</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- ====== // Navbar ====== -->
    </header>
    <!-- ====== // Header ====== -->

    <!-- ====== Hero Area ====== -->
    <!-- ====== //Hero Area ====== -->






    <!-- ====== Service Section ====== -->
    <section id="home" class="section-padding bg-light">
        <div class="container">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center" id="title_accueil">
                        <h2>Lixian Technology&copy</h2>
                        <p>Conseil en Syst&egrave;mes d'Information, &Eacute;dition logicielle & Intégration</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/hero-area/img-1.jpg)"></div>
                        <h4 class="blog-title"><a href="architecture.html">Conseil</a></h4>
                        <p class="blog-meta">Banques, T&eacute;l&eacute;com, D&eacute;fense, A&eacute;ronautique, ...</p>
                        <p>liXian Technology est sp&eacute;cialis&eacute;e dans le conseil aux  Directions Informatiques (DSI) sur l'Architecture de leur Syst&egrave;me d'Information et sur la conduite de  projets d'envergure.</p> 
                    </div>
                </div>
                <!-- Single Blog -->
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/hero-area/img-2.jpg)"></div>
                        <h4 class="blog-title"><a href="Gouvernance.html">édition logicielle</a></h4>
                        <p class="blog-meta">Gestion de portefeuille Projets, Architecture SI, ...</p>
                        <p>liXian  Technology commercialise le produit de Gestion de portefeuille projets liXian&copy;, bas&eacute; sur un socle de cartographie applicative et un dictionnaire de donn&eacute;es m&eacute;tiers.</p></p>
                    </div>
                </div>
                <!-- Single Blog -->
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/hero-area/img-3.jpg)"></div>
                        <h4 class="blog-title"><a href="Projets.html">Intégration</a></h4>
                        <p class="blog-meta">liXian&copy; deploy&eacute; en Version Saas ou On Premise, ...</p>
                        <p>Le produit liXian&copy; est int&eacute;gr&eacute; par nos soins ou via un partenaire pour &nbsp;s'interfacer avec votre Syst&egrave;me d'information.  Une &eacute;tude pr&eacute;alable avec votre DSI est indispensable.</p> 
                    </div>
                </div>
                <!-- Single Blog -->                
            </div>
        </div>

        </div>
    </section>
    <!-- ====== //Service Section ====== -->


    <!-- ====== Fact Counter Section ====== -->
    <!-- ====================================================================
            NOTE: You need to change  'data-count="10"' and 'p' Eliments 
        ===================================================================== -->
    <section class="section-padding bg-img fact-counter" id="counter" style="background-image: url(assets/images/fan-fact-bg.jpg)">
        <div class="container">
            <div class="row">
                <!-- Single Fact Counter -->
                <div class="col-lg-3 co col-md-6 l-md-6 text-center">
                    <div class="single-fun-fact">
                        <h2><span class="counter-value" data-count="30">0</span>+</h2>
                        <p>Ann&eacute;es Experience</p>
                    </div>
                </div>
                <!-- // Single Fact Counter -->
                <!-- Single Fact Counter -->
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="single-fun-fact">
                        <h2><span class="counter-value" data-count="2500">0</span>+</h2>
                        <p>Projets liXian&copy;</p>
                    </div>
                </div>
                <!-- // Single Fact Counter -->
                <!-- Single Fact Counter -->
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="single-fun-fact">
                        <h2><span class="counter-value" data-count="5000">0</span>+</h2>
                        <p>Utilisateurs liXian&copy;</p>
                    </div>
                </div>
                <!-- // Single Fact Counter -->
                <!-- Single Fact Counter -->
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="single-fun-fact">
                        <h2><span class="counter-value" data-count="2000">0</span>+</h2>
                        <p>Cartographies liXian&copy;</p>
                    </div>
                </div>
                <!-- // Single Fact Counter -->
            </div>
        </div>
    </section>
    <!-- ====== //Fact Counter Section ====== -->

    



    <!-- ====== produit Section ====== -->
    <section id="produit" class="section-padding pb-70 blog-section bg-light">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Le Produit liXian</h2>
                        <p>Architecture, Projet, Gouvernance</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6"  id="architecture">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/blog/img-1.jpg)"></div>
                        <h4 class="blog-title"><a href="architecture.html">Architecture</a></h4>
                        <p class="blog-meta">Fiches Produit, Analyse d'impact, ...</p>
                        <p>liXian permet de d&eacute;finir la cartographie des produits de l'entreprise afin de communiquer sur son patrimoine, de faire des &eacute;tudes d'impact, de documenter les Appels d'Offres.....</p>
                        <a href="architecture.html" class="button">En Savoir Plus</a>
                    </div>
                </div>
                <!-- Single Blog -->
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6"  id="projet">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/blog/img-2.jpg)"></div>
                        <h4 class="blog-title"><a href="Projets.html">Projet</a></h4>
                        <p class="blog-meta">Suivi, Gantt, Plan de charges, ..</p>
                        <p>liXian permet un pilotage efficace des projets en offrant des outils de type Gantt et plan de charges ainsi que des alerteurs en cas de d&eacute;rives sur les jalons essentiels..</p>
	                        <a href="Projets.html" class="button">En Savoir Plus</a>
                    </div>
                </div>
                <!-- Single Blog -->
                <!-- Single Blog -->
                <div class="col-lg-4 col-md-6"  id="gouvernance">
                    <div class="single-blog">
                        <div class="blog-thumb" style="background-image: url(assets/images/blog/img-3.jpg)"></div>
                        <h4 class="blog-title"><a href="Gouvernance.html">Gouvernance</a></h4>
                        <p class="blog-meta">Budget, Devis, ...</p>
                        <p>liXian permet de g&eacute;rer le portefeuille projet pour r&eacute;pondre &agrave; la promesse client. Indispensable pour l'actualisation budg&eacute;taire et le chiffrage des futurs exercices.</p>
	                        <a href="Gouvernance.html" class="button">En Savoir Plus</a>
                    </div>
                </div>
                <!-- Single Blog -->
            </div>
        </div>
    </section>
    <!-- ====== // produit Section ====== -->

    
    <!-- ====== Portfolio Section ====== -->
    <section id="demo" class="section-padding bg-secondary pb-85 portfolio-area bg-light">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Apper&ccedil;u du produit liXian</h2>
                        <p>Quelques snapshots pour se projeter</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row justify-content-center">
                <!-- Work List Menu-->
                <div class="col-lg-8">
                    <div class="work-list text-center">
                        <ul>
                            <li class="filter" class="active" data-filter="all">TOUS</li>
                            <li class="filter" data-filter=".Produits">Architecture</li>
                            <li class="filter" data-filter=".Projets">Projets</li>
                            <li class="filter" data-filter=".Gouvernance">Gouvernance</li>
                            <li class="filter" data-filter=".Tests">Tests</li>
                            <li class="filter" data-filter=".other">Autres</li>
                            <li class="filter" data-filter=".tutorial">Tutoriels</li>
                        </ul>
                    </div>
                </div>
                <!-- // Work List Menu -->
            </div>
            <div class="row portfolio">
                <!-- Single Portfolio ** DEBUT ARCITECTURE ** -->
                <div class="col-lg-4 col-md-6 mix Produits">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/OmCirculation2.PNG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/OmCirculation2.PNG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Analyse Impact</span></h4>
                        </div>
                    </div>
                </div>                
                <div class="col-lg-4 col-md-6 mix Produits">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/GrapheSt.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/GrapheSt.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Eco-Systeme Applicatif</span></h4>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 mix Produits">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/OmRelation2.PNG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/OmRelation2.PNG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Objets M&eacute;tier</span></h4>
                        </div>
                    </div>
                </div>
              
   
                <!-- Single Portfolio ** FIN ARCITECTURE ** -->                
                <!-- Single Portfolio ** DEBUT GOUVERNANCE ** -->
                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/BILAN_SI2.PNG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/BILAN_SI2.PNG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Bilan par Domaine</span></h4>
                        </div>
                    </div>
                </div>                               
                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Ressources2.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Ressources2.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Indicateur Charges</span></h4>
                        </div>
                    </div>
                </div> 
                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/IndicateurSt.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/IndicateurSt.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Evolution SI</span></h4>
                        </div>
                    </div>
                </div>                 
                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Jalons5.PNG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Jalons5.PNG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Indicateur D&eacute;lais</span></h4>
                        </div>
                    </div>
                </div>  


                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Tendances.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Tendances.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Indicateur M&eacute;t&eacute;o</span></h4>
                        </div>
                    </div>
                </div> 
                <div class="col-lg-4 col-md-6 mix Gouvernance">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Utilisation.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Utilisation.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Indicateur Utilisation</span></h4>
                        </div>
                    </div>
                </div>                 
                <!-- Single Portfolio ** FIN GOUVERNANCE ** -->  
                 
                <!-- Single Portfolio  ** DEBUT TESTS ** -->
                <div class="col-lg-4 col-md-6 mix Tests">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Campagne2.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Campagne2.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Synthese Tests</span></h4>
                        </div>
                    </div>
                </div>                 
                <div class="col-lg-4 col-md-6 mix Tests">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/TESTS.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/TESTS.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Cahier Tests</span></h4>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 mix Tests">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Execution.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Execution.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Execution Tests</span></h4>
                        </div>
                    </div>
                </div>               
                <!-- Single Portfolio  ** FIN TESTS ** -->             

            
                <!-- // Single Portfolio  ** DEBUT PROJETS ** -->
                <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Suivi.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Suivi.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Suivi de Projet</span></h4>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Gantt-min.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Gantt.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover other">
                            <h4><span>Gantts</span></h4>
                        </div>
                    </div>
                </div>    

                <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Pdc-min.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Pdc.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4><span>Plan de Charges</span></h4>
                        </div>
                    </div>
                </div>  
                <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/KANBAN.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/KANBAN.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Mode Kanban</span></h4>
                        </div>
                    </div>
                </div>    
                <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/DrillDown2.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/DrillDown2.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Drill Down</span></h4>
                        </div>
                    </div>
                </div>    
                 <div class="col-lg-4 col-md-6 mix Projets">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Commnuication.PNG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Commnuication.PNG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Communication</span></h4>
                        </div>
                    </div>
                </div>                
                <!-- // Single Portfolio  ** FIN PROJETS ** -->
                <!-- // Single Portfolio  ** DEBUT AUTRES ** -->                
                <div class="col-lg-4 col-md-6 mix other">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Accueil.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Accueil.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Dashboard</span></h4>
                        </div>
                    </div>
                </div>  
                <div class="col-lg-4 col-md-6 mix other">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/Processus.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/Processus.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Processus</span></h4>
                        </div>
                    </div>
                </div>  
    
                <div class="col-lg-4 col-md-6 mix other">
                    <div class="single-portfolio" style="background-image: url(assets/images/portfolio/procDevis5.JPG)">
                        <div class="portfolio-icon text-center">
                            <a data-lightbox='lightbox' href="assets/images/portfolio/procDevis5.JPG"><i class="fas fa-expand-arrows-alt"></i></a>
                        </div>
                        <div class="portfolio-hover">
                            <h4> <span>Processus</span></h4>
                        </div>
                    </div>
                </div>                 
                <!-- // Single Portfolio  ** FIN AUTRES ** -->       
                <!-- // Single Portfolio  ** DEBUT TUTORIAL ** -->                
                <div class="col-lg-4 col-md-6 mix tutorial">
                    <video width="320" height="240" controls poster="assets/videos/Gerer mon planning.JPG">
                        <source src="assets/videos/lixian.mp4" type="video/mp4"> 
                        <source src="assets/videos/lixian.webm" type="video/webm">                        
                        Your browser does not support the video tag.
                    </video>                   
                </div>  
                <div class="col-lg-4 col-md-6 mix tutorial">
                    <video width="320" height="240" controls poster="assets/videos/Gerer mon plan de charges.JPG">
                        <source src="assets/videos/lixian Plan de Charge.mp4" type="video/mp4"> 
                        <source src="assets/videos/lixian.webm" type="video/webm">    
                        Your browser does not support the video tag.
                    </video>      
                </div>   
                <div class="col-lg-4 col-md-6 mix tutorial">
                    <video width="320" height="240" controls poster="assets/videos/Faire Mon Suivi.JPG">
                        <source src="assets/videos/Suivre mon Projet.mp4" type="video/mp4"> 
                        <source src="assets/videos/lixian.webm" type="video/webm">                        
                        Your browser does not support the video tag.
                    </video>                   
                </div>  
                <div class="col-lg-4 col-md-6 mix tutorial">
                    <video width="320" height="240" controls poster="assets/videos/Architecture.JPG">
                        <source src="assets/videos/Architecture.mp4" type="video/mp4"> 
                        <source src="assets/videos/lixian.webm" type="video/webm">                        
                        Your browser does not support the video tag.
                    </video>                   
                </div>      
                
                <div class="col-lg-4 col-md-6 mix tutorial">
                    <video width="320" height="240" controls poster="assets/videos/tutorielLixian.jpg">
                        <source src="assets/videos/TutorielLixian -V2.mp4" type="video/mp4"> 
                        <source src="assets/videos/lixian.webm" type="video/webm">                        
                        Your browser does not support the video tag.
                    </video>                   
                </div>                 
                <!-- // Single Portfolio  ** FIN TUTORIAL ** -->                   
            </div>
        </div>
    </section>
    <!-- ====== // Portfolio Section ====== -->
    
    <!-- ====== Why choose Me Section ====== -->
    <section id="" class="section-padding why-choose-us pb-70">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Pourquoi choisir liXian</h2>
                        <p>La gestion de projet simple, basée sur l'expérience.</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <!-- Single Why choose me -->
                <div class="col-md-6">
                    <div class="single-why-me why-me-left">
                        <div class="why-me-icon">
                            <div class="d-table">
                                <div class="d-table-cell">
                                    <i class="fa fa-clock"></i>
                                </div>
                            </div>
                        </div>
                        <h4>30 ans d'expérience</h4>
                        <p>A travers le produit liXian, vous pourrez juger de la capacité de liXian technology à vous accompagner pour vos projets.</p>
                    </div>
                </div>
                <!-- // Single Why choose me -->

                <!-- Single Why choose me -->
                <div class="col-md-6">
                    <div class="single-why-me why-me-right">
                        <div class="why-me-icon">
                            <div class="d-table">
                                <div class="d-table-cell">
                                    <i class="fa fa-home"></i>
                                </div>
                            </div>
                        </div>
                        <h4>Approche patrimoniale</h4>
                        <p>La gestion de projet se base sur la connaissance des produits embarquée dans la partie cartographie de liXian.</p>
                    </div>
                </div>
                <!-- // Single Why choose me -->

                <!-- Single Why choose me -->
                <div class="col-md-6">
                    <div class="single-why-me why-me-left">
                        <div class="why-me-icon">
                            <div class="d-table">
                                <div class="d-table-cell">
                                    <i class="fa fa-link"></i>
                                </div>
                            </div>
                        </div>
                        <h4>Fédérer différents métiers</h4>
                        <p>Que l'on soit Manager, Chef de projet MOA ou MOE, architecte fonctionnel, on doit pouvoir partager un langage commun.</p>
                    </div>
                </div>
                <!-- // Single Why choose me -->

                <!-- Single Why choose me -->
                <div class="col-md-6">
                    <div class="single-why-me why-me-right">
                        <div class="why-me-icon">
                            <div class="d-table">
                                <div class="d-table-cell">
                                    <i class="fa fa-keyboard"></i>
                                </div>
                            </div>
                        </div>
                        <h4>Eviter les doubles saisie</h4>
                        <p>Cartographier les produits, gérer les projets ou faire des tests pourquoi ressaisir les mêmes données ?</p>
                    </div>
                </div>
                <!-- // Single Why choose me -->
            </div>
        </div>
    </section>
    <!-- ====== //Why choose Me Section ====== -->
    
     <!-- ====== Frequently asked questions ====== -->
    <section id="faq" class="section-padding faq-area bg-secondary">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center faq-title">
                        <h2>Questions Fréquemment posées</h2>
                        <p>Nos Clients nous ont remont&eacute; ..............</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <div class="col-lg-5">
                    <div class="faq-bg bg-img" style="background-image: url(assets/images/faq.jpg)"></div>
                </div>
                <div class="col-lg-7">
                    <!-- FAQ Content -->
                    <div class="faq-content" id="accordion">
                        <!-- Single FAQ -->
                        <div class="single-faq">

                            <!-- FAQ Header -->
                            <h4 class="collapsed" data-toggle="collapse" data-target="#faq-1">Qu'apporte liXian ?</h4>
                            <!-- // FAQ Header -->

                            <!-- FAQ Content -->
                            <div id="faq-1" class="collapse show" data-parent="#accordion">
                                <div class="faq-body">
                                    liXian est le seul outil de gestion de projet du March&eacute; bas&eacute;  sur la cartographie des produits de l'Entreprise. Il int&egrave;gre aussi la gestion  des tests, des processus, des objets m&eacute;tiers ce qui permet aux entreprises de  r&eacute;aliser des &eacute;conomies et &eacute;vite aux utilisateurs des doubles saisie p&eacute;nibles ce  qui am&eacute;liore d'autant leur productivit&eacute;.                                 </div>
                            <!-- FAQ Content -->
                        </div>
                        <!-- // Single FAQ -->

                        <!-- Single FAQ -->
                        <div class="single-faq">

                            <!-- FAQ Header -->
                            <h4 class="collapsed" data-toggle="collapse" data-target="#faq-2">Quelles sont vos references ?</h4>
                            <!-- // FAQ Header -->

                            <!-- FAQ Content -->
                            <div id="faq-2" class="collapse" data-parent="#accordion">
                                <div class="faq-body">
                                    liXian techonology est une start up qui a d&eacute;ja su s&eacute;duire un  grand acteur des telecoms (Bouygues Telecom). Aujourd'hui c'est plusieurs  centaines d'utilisateurs de liXian de m&eacute;tiers diff&eacute;rents qui g&egrave;rent les projets  en tant que Maitrise d'ouvrage ou de Maitrise d'Oeuvre.                                
                                </div>
                            </div>
                            <!-- FAQ Content -->
                        </div>
                        <!-- // Single FAQ -->

                        <!-- Single FAQ -->
                        <div class="single-faq">

                            <!-- FAQ Header -->
                            <h4 class="collapsed" data-toggle="collapse" data-target="#faq-3">Quelles sont les solutions d'hebergement ?</h4>
                            <!-- // FAQ Header -->

                            <!-- FAQ Content -->
                            <div id="faq-3" class="collapse" data-parent="#accordion">
                                <div class="faq-body">
                                    liXian technology est propos&eacute; aussi bien en version &quot;on  premise&quot; que &quot;Saas&quot; avec un h&eacute;bergement d&eacute;di&eacute; ou mutualis&eacute;.                                
                                </div>
                            </div>
                            <!-- FAQ Content -->
                        </div>
                        <!-- // Single FAQ -->

                    </div>
                    <!-- FAQ Content -->
                </div>
            </div>
        </div>
    </section>
    <!-- ====== // Frequently asked questions ====== -->   

    <!-- ====== Team Section ====== -->
    <section class="section-padding pb-70 team-area">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>l'Equipe</h2>
                        <p>Nous sommes en pleine croissance !</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <!-- Single Team -->
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="single-team">
                        <div class="team-thumb" style="background-image: url(assets/images/team/img-1.JPG)">
                            <div class="team-social">
                                <a target="_blank" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-twitter"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-linkedin"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-pinterest"></i></a>
                            </div>
                        </div>
                        <div class="team-content">
                            <h4>Joel Jakubowicz</h4>
                            <span>Fondateur</span>
                        </div>
                    </div>
                </div>
                <!-- // Single Team -->
                <!-- Single Team -->
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="single-team">
                        <div class="team-thumb" style="background-image: url(assets/images/team/img-2.jpg)">
                            <div class="team-social">
                                <a target="_blank" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-twitter"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-linkedin"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-pinterest"></i></a>
                            </div>
                        </div>
                        <div class="team-content">
                            <h4>1ere Recrue</h4>
                            <span>futur Commercial</span>
                        </div>
                    </div>
                </div>
                <!-- // Single Team -->
                <!-- Single Team -->
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="single-team">
                        <div class="team-thumb" style="background-image: url(assets/images/team/img-3.jpg)">
                            <div class="team-social">
                                <a target="_blank" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-twitter"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-linkedin"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-pinterest"></i></a>
                            </div>
                        </div>
                        <div class="team-content">
                            <h4>2eme Recrue</h4>
                            <span>futur Developpeur</span>
                        </div>
                    </div>
                </div>
                <!-- // Single Team -->
                <!-- Single Team -->
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="single-team">
                        <div class="team-thumb" style="background-image: url(assets/images/team/img-4.jpg)">
                            <div class="team-social">
                                <a target="_blank" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-twitter"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-linkedin"></i></a>
                                <a target="_blank" href="#"><i class="fab fa-pinterest"></i></a>
                            </div>
                        </div>
                        <div class="team-content">
                            <h4>3eme Recrue</h4>
                            <span>futur Developpeur</span>
                        </div>
                    </div>
                </div>
                <!-- // Single Team -->
            </div>
        </div>
    </section>
    <!-- ====== // Team Section ====== -->

        <!-- ====== Testimonial Section ====== -->
    <section id="testimonial" class="section-padding bg-secondary testimonial-area">
        <div class="container bg-white">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Profession de Foi</h2>
                        <p>La Gestion de projet bas&eacute;e sur l'Exp&eacute;rience</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="testimonials owl-carousel" data-ride="carousel">
                        <!-- Single Testimonial -->
                        <div class="single-testimonial text-center">
                            <div class="testimonial-quote">
                                <i class="fa fa-quote-right"></i>
                            </div>
                            <p>La Gestion de projet s'inscrit  dans un &eacute;cosyst&egrave;me complet: un projet s'appuie sur les produits de l'entreprise  et ses collaborateurs qui les font vivre.</p>                            <h4>Joel Jakubowicz<span>Fondateur - liXian technology</span></h4>

                        </div>
                        <!-- // Single Testimonial -->
                        <!-- Single Testimonial -->
                        <div class="single-testimonial text-center">
                            <div class="testimonial-quote">
                                <i class="fa fa-quote-right"></i>
                            </div>
                            <p>Architecture,  Projets, Gouvernance, c'est le tryptique gagnant de l'entreprise.</p>                            <h4>Joel Jakubowicz<span>Fondateur - liXian technology</span></h4>

                        </div>
                        <!-- // Single Testimonial -->
                        <!-- Single Testimonial -->
                        <div class="single-testimonial text-center">
                            <div class="testimonial-quote">
                                <i class="fa fa-quote-right"></i>
                            </div>
                            <p>L'Architecture  pour ma&icirc;triser, les Projets pour croitre, la gouvernance pour piloter au plus  juste.</p>
                        </div>
                        <!-- // Single Testimonial -->
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- ====== // Testimonial Section ====== -->
    



    <!-- ====== Pricing Area ====== -->
    <section class="section-padding pb-70 pricing-area">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Nos Prix</h2>
                        <p>3 Offres: Architecture, Projet, Gouvernance</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <!-- Single Price Box -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-price-box text-center">
                        <div class="price-head">
                            <h2>Architecture</h2>
                            <h3>30€<span>/ par Mois/ licence</span></h3>
                        </div>
                        <ul>
                            <li>Core</li>
                            <li>Gestion des Produits</li>
                            <li>Gestion des Modules</li>
                            <li>Gestion des Interface</li>
                            <li>Gestion des Technologies</li>
                            <li>Gestion des Objets M&eacute;tiers</li>
                            <li>Gestion de l'Organisation</li>
                            <li>Gestion des Problemes</li>
                        </ul>
                        <a href="#contact" class="button contact">NOUS CONTACTER</a>
                    </div>
                </div>
                <!-- // Single Price Box -->
                <!-- Single Price Box -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-price-box text-center">
                        <div class="price-head">
                            <h2>Projet</h2>
                            <h3>35€<span>/ par Mois/ licence</span></h3>
                        </div>
                        <ul>
                            <li>Core + Architecture</li>
                            <li>Diagramme de Gantt</li>
                            <li>Plan de Charge</li>
                            <li>Gestion des taches</li>
                            <li>Suivi avancement</li>
                            <li>Gestion des imputations</li>
                            <li>Gestion du Reste a  faire</li>
                            <li>Alertes sur retards (Dashboard)</li>
                        </ul>
                        <a href="#contact" class="button contact">NOUS CONTACTER</a>
                    </div>
                </div>
                <!-- // Single Price Box -->
                <!-- Single Price Box -->
                <div class="col-lg-4 col-md-6">
                    <div class="single-price-box text-center">
                        <div class="price-head">
                            <h2>Gouvernance</h2>
                            <h3>40€<span>/ par Mois/ licence</span></h3>
                        </div>
                        <ul>
                            <li>Core + Architecture + Projet</li>
                            <li>Gestion de projets candidats</li>
                            <li>Gestion des Budgets</li>
                            <li>Allocation du budget</li>
                            <li>Vision Centre de Couts</li>
                            <li>Gestion des Baselines</li>
                            <li>Engagement projets (Devis)</li>
                            <li>Gestion des Processus</li>
                        </ul>
                        <a href="#contact" class="button contact">NOUS CONTACTER</a>
                    </div>
                </div>
                <!-- // Single Price Box -->
                <!-- Single Price Box -->

                <!-- // Single Price Box -->
            </div>
        </div>
    </section>
    <!-- ====== // Pricing Area ====== -->

    <!-- ====== Call to Action Area ====== -->
    <section class="section-padding call-to-action-aria bg-secondary">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h2>Laissez vous tenter par l'exp&eacute;rience liXian !</h2>
                    <p>Nous sommes &agrave; votre disposition pour d&eacute;finir avec vous un POC (Proof of Concept) collant au plus pr&egrave;s de votre organisation et de vos processus m&eacute;tier.</p>
                </div>

            </div>
        </div>
    </section>
    <!-- ====== // Call to Action Area ====== -->
 
    <!-- ====== About Area ====== -->
    <section id="about" class="section-padding about-area bg-light">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Un petit mot du Fondateur</h2>
                        <p>J'aurais pu écrire un livre sur mes 30 ans d'expérience dans les systèmes d'information ...... j'ai préférer créer liXian !</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->
            <div class="row">
                <div class="col-lg-6">
                    <div class="about-bg" style="background-image:url(assets/images/team/img-1.JPG)">
                        <!-- Social Link -->
                        <div class="social-aria">
                            <a target="_blank" href="#"><i class="fab fa-facebook-f"></i></a>
                            <a target="_blank" href="#"><i class="fab fa-twitter"></i></a>
                            <a target="_blank" href="#"><i class="fab fa-instagram"></i></a>
                            <a target="_blank" href="#"><i class="fab fa-pinterest"></i></a>
                            <a target="_blank" href="#"><i class="fab fa-youtube"></i></a>
                        </div>
                        <!-- // Social Link -->
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="about-content">
                        <h2>Hello, Je suis <span>Joel Jakubowicz</span></h2>
                        <h4>Fondateur de la Soci&eacute;t&eacute; liXian Technology</h4>
<p>De formation Ing&eacute;nieur, fort d&rsquo;une exp&eacute;rience de 15 ans dans  les plus grandes SSII Informatiques (SEMA, ALTRAN, ATOS) et pr&egrave;s de 15 ans chez  un grand acteur des t&eacute;l&eacute;coms, j&rsquo;ai retranscrit toute mon exp&eacute;rience en gestion  de projet vu du prisme d&rsquo;un Architecte SI, d&rsquo;un Ma&icirc;tre d&rsquo;Ouvrage ou d&rsquo;un  Ma&icirc;tre d&rsquo;Oeuvre<br>
J&rsquo;ai ainsi la satisfaction de vous proposer enfin un outil  de gestion de projet 100% utilisable en milieu op&eacute;rationnel.</p>                        <h5>Mon Profil</h5>

                        <!-- Skill Area -->
                        <div id="skills" class="skill-area">

                            <!-- Single skill -->
                            <div class="single-skill">
                                <div class="skillbar" data-percent="100%">
                                    <div class="skillbar-title"><span>Consulting</span></div>
                                    <div class="skillbar-bar"></div>
                                    <div class="skill-bar-percent">100%</div>
                                </div>
                            </div>
                            <!-- //Single skill -->

                            <!-- Single skill -->
                            <div class="single-skill">
                                <div class="skillbar" data-percent="85%">
                                    <div class="skillbar-title"><span>Architecture</span></div>
                                    <div class="skillbar-bar"></div>
                                    <div class="skill-bar-percent">85%</div>
                                </div>
                            </div>
                            <!-- //Single skill -->

                            <!-- Single skill -->
                            <div class="single-skill">
                                <div class="skillbar" data-percent="65%">
                                    <div class="skillbar-title"><span>Web</span></div>
                                    <div class="skillbar-bar"></div>
                                    <div class="skill-bar-percent">65%</div>
                                </div>
                            </div>
                            <!-- //Single skill -->

                        </div>
                        <!-- //Skill Area -->
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- ====== // About Area ====== -->
    
    <!-- ====== Contact Area ====== -->
    <section id="contact" class="section-padding contact-section bg-light">
        <div class="container">
            <!-- Section Title -->
            <div class="row justify-content-center">
                <div class="col-lg-6 ">
                    <div class="section-title text-center">
                        <h2>Contactez nous</h2>
                        <p>Nous revenons vers vous aussi vite que possible pour vous apporter les meilleures solutions.</p>
                    </div>
                </div>
            </div>
            <!-- //Section Title -->

            <!-- Contact Form -->
            <div class="row justify-content-center">
                <div class="col-lg-10">
                    <!-- Form -->
                    <div id="contact-form" class="contact-form bg-white">
                        <div class="row">
                            <div class="col-lg-4 form-group">
                                <input type="text" class="form-control" name="Nom" id="Nom"  placeholder="Nom (*)">
                            </div>
                            <div class="col-lg-4 form-group">
                                <input type="text" class="form-control" name="Prenom" id="Prenom"  placeholder="Prenom">
                            </div>
                            <div class="col-lg-4 form-group">
                                <input type="text" class="form-control" name="Telephone" id="Telephone"  placeholder="Telephone">
                            </div>                            
                        </div>
                        <div class="row">
                            <div class="col-lg-4 form-group">
                                <input type="text" class="form-control" name="Entreprise" id="Entreprise"  placeholder="Entreprise">
                            </div>
                            <div class="col-lg-4 form-group">
                                <input type="text" class="form-control" name="Adresse" id="Adresse"  placeholder="Adresse">
                            </div>                            
                            <div class="col-lg-4 form-group">
                                <input type="email" class="form-control" name="Mail" id="Mail"  placeholder="Email (*)">
                            </div>
                        </div>                        

                        <div class="form-group">
                            <textarea name="Commentaires" id="Commentaires" class="form-control"  placeholder="Vos Besoins (*)"></textarea>
                        </div>
                        <div class="form-btn text-center">
                            <button class="button" id="buttonEnvoyer">Envoyer Message</button>
                            
                        </div>
                        <br>
                        <p id="crMail">En attente d'Envoi</p>
                    </div>
                    <!-- // Form -->
                </div>
            </div>
            <!-- // Contact Form -->
        </div>
    </section>
    <!-- ====== // Contact Area ====== -->

<!-- BEGIN # MODAL LOGIN -->
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<img class="img-circle" id="img_logo" src="assets/images/logo.png">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
				</div>
                
                <!-- Begin # DIV Form -->
                <div id="div-forms">
                
                    <!-- Begin # Login Form -->
                    <form id="login-form">
		                <div class="modal-body">
				    		<div id="div-login-msg">
                                <div id="icon-login-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-login-msg">Type your username and password.</span>
                            </div>
				    		<input id="username" class="form-control" type="text" placeholder="Username" value="<%=username%>" required>
				    		<input id="password" class="form-control" type="password" placeholder="Password" value="<%=psw%>" required>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Remember me
                                </label>
                            </div>
        		    	</div>
				        <div class="modal-footer">
                            <div>
                                <button id="buttonSignIn" type="submit" class="btn btn-primary btn-lg btn-block">Login</button>
                            </div>
				    	    <div>
                                <button id="login_lost_btn" type="button" class="btn btn-link">Lost Password?</button>
                                <button id="login_register_btn" type="button" class="btn btn-link">Register</button>
                            </div>
				        </div>
                    </form>
                    <!-- End # Login Form -->
                    
                    <!-- Begin | Lost Password Form -->
                    <form id="lost-form" style="display:none;">
    	    		    <div class="modal-body">
		    				<div id="div-lost-msg">
                                <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-lost-msg">Type your e-mail.</span>
                            </div>
		    				<input id="lost_email" class="form-control" type="text" placeholder="E-Mail" required>
            			</div>
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">Send</button>
                            </div>
                            <div>
                                <button id="lost_login_btn" type="button" class="btn btn-link">Log In</button>
                                <button id="lost_register_btn" type="button" class="btn btn-link">Register</button>
                            </div>
		    		    </div>
                    </form>
                    <!-- End | Lost Password Form -->
                    
                    <!-- Begin | Register Form -->
                    <form id="register-form" style="display:none;">
            		    <div class="modal-body">
		    				<div id="div-register-msg">
                                <div id="icon-register-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-register-msg">Register an account.</span>
                            </div>
		    				<input id="register_username" class="form-control" type="text" placeholder="Username" required>
                            <input id="register_email" class="form-control" type="text" placeholder="E-Mail" required>
                            <input id="register_password" class="form-control" type="password" placeholder="Password" required>
            			</div>
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">Register</button>
                            </div>
                            <div>
                                <button id="register_login_btn" type="button" class="btn btn-link">Log In</button>
                                <button id="register_lost_btn" type="button" class="btn btn-link">Lost Password?</button>
                            </div>
		    		    </div>
                    </form>
                    <!-- End | Register Form -->
                    
                </div>
                <!-- End # DIV Form -->
                
			</div>
		</div>
	</div>
   
<!-- END # MODAL LOGIN -->
 
    <!-- ====== Footer Area ====== -->
    <footer class="footer-area">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="copyright-text">
                        <p class="text-white"><a href="https://www.lixian.fr/">lixian technology&copy;</a></p>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <!-- ====== // Footer Area ====== -->

    <a href="#" id="scroll-to-top" class="dmtop global-radius"><i class="fa fa-angle-up"></i></a>




     <!-- ====== ALL JS ====== -->
<script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/lightbox.min.js"></script>
    <script src="assets/js/owl.carousel.min.js"></script>
    <script src="assets/js/jquery.mixitup.js"></script>
    <script src="assets/js/wow.min.js"></script>
    <script src="assets/js/typed.js"></script>
    <script src="assets/js/skill.bar.js"></script>
    <script src="assets/js/fact.counter.js"></script>
    <script src="assets/js/main.js"></script>
    
    <script src="javascript/gen_functions.js"></script>

</body>

<script type="text/javascript">
    var myVar;

$(document).ready(function () {
 
$('.nav-item').click(function( event ) {
    $('#navbarSupportedContent').collapse('toggle');
})    
$(window).on('scroll', function () {
 
if ($(window).scrollTop() == 0)
{
    console.log('TOP !!');
    $('.navbar').addClass('bg-primari');
}
    if ($(window).scrollTop() > 50) 
    {
	$('.header_style_01').addClass('fixed-menu');
        //console.log('scroll1');
    } else 
    {
	$('.header_style_01').removeClass('fixed-menu');
        //console.log('scroll2');
    }    
    
});  

   /* ==============================================
		Scroll to top  
	============================================== */
		
	if ($('#scroll-to-top').length) {
 		var scrollTrigger = 100, // px
			backToTop = function () {
				var scrollTop = $(window).scrollTop();
				if (scrollTop > scrollTrigger) {
					$('#scroll-to-top').addClass('show');
				} else {
					$('#scroll-to-top').removeClass('show');
				}
			};
		backToTop();
		$(window).on('scroll', function () {
			backToTop();
		});
		$('#scroll-to-top').on('click', function (e) {
			e.preventDefault();
			$('html,body').animate({
				scrollTop: 0
			}, 700);
		});
	}
// ------------------------ check login --------------------------------------------------------//
$("#buttonSignIn").click(function( event ) { 
    if ($('#username').val() == '')
    {
        $('#text-login-msg').html('&nbsp;&nbsp;Username Vide !&nbsp;&nbsp;').css("background-color", "red");
        //$('#ErrorPassword').html('');
        return;
    }
    
     if ($('#password').val() == '')
    {
        $('#text-login-msg').html('&nbsp;&nbsp;Password Vide !&nbsp;&nbsp;').css("background-color", "red");
        //$('#ErrorUsername').html('');
        return;
    }    
    
  $('body').css({
     "background-color":"grey"
    }) 
    
 var username_codeClient = $('#username').val().split('@');
 
jsp="gen_login-a.jsp";
jsp+="?date="+(new Date());    

$.ajax( jsp, {
    type: "POST",
    data : {
            username : username_codeClient[1],
            password :  $('#password').val() ,
            codeClient : username_codeClient[0]
    }
    
} )
   .done(function(data) {
              pos = data.indexOf("@1@");
              data = data.substring(pos+3);
              pos = data.indexOf("@2@");
              data = data.substring(0,pos);
              //alert(data);
              if (data == "OK")
              {
                  $('#text-login-msg').html('Connexion en cours ........').css("color", "black");
                  
                  $(location).attr('href', "../lixian_css3/accueil.jsp?Login="+username_codeClient[1]);
                
              }
              else if (data == "KO:CNX")
              {
                  $('#text-login-msg').html('&nbsp;&nbsp;deja connecte !&nbsp;&nbsp;').css("color", "red");
                  //$('#ErrorPassword').html('');
              }  
               else if (data == "KO:KEY")
              {
                  $('#text-login-msg').html('&nbsp;Clef de Licence Invalide !&nbsp;').css("color", "red");
                  //$('#ErrorPassword').html('&nbsp;Clef de Licence Invalide !&nbsp;&nbsp;').css("background-color", "red");
              } 
               else if (data == "KO:EVAL")
              {
                  $('#text-login-msg').html('&nbsp;&nbsp;evaluation terminee !&nbsp;&nbsp;').css("color", "red");
                  //$('#ErrorPassword').html('&nbsp;&nbsp;evaluation terminee !&nbsp;&nbsp;').css("background-color", "red");
              }                
              else
              {
                  $('#text-login-msg').html('&nbsp;&nbsp;authentication failed !&nbsp;&nbsp;').css("color", "red");
                  //$('#ErrorPassword').html('&nbsp;&nbsp;authentication failed !&nbsp;&nbsp;').css("background-color", "red");
              }                
              console.log("success !");
              $('body').css({
                "background-color":"white"
              });
              
              

  })
  .fail(function() {
    console.log("fail !");
  })
  .always(function() {
    console.log("always !");
  });     
    //$('#wrapper').fadeTo(1000, 1);     
})    
    // ------------------------------------------------------------------------ //
    
$('#crMail').hide();
$("input[id^='Mail']").blur(function (e) {
   if (!isEmail($(this).val()))
   {
            $('#crMail').show();
            $('#crMail').html('format de mail invalide');          
            $('#Mail').focus();
      }
      else
      {
          $('#crMail').html("");
      }      
}); 

$("input[id^='Telephone']").keypress(function (e) {
    return isTelephon(e);
});
// -------------------------- Gestion des actions ------------------------------------//   
$('#buttonEnvoyer').click(function(){
    
    if (!verifier ('demande')) return;
    enregistrer ('demande');
  } );  
  
$('#buttonLogin').click(function(){
    //alert ('Login');
    $(location).attr('href', "../lixian_css3/index.jsp");
  } ); 
  
$( "#contact-form" ).submit(function( event ) {
  //alert( "Handler for .submit() called." );
  event.preventDefault();
});

$( "#login-form" ).submit(function( event ) {
  //alert( "Handler for .submit() called." );
  event.preventDefault();
});
myVar = setTimeout(majCounters, 3000);
})


function send(Mode){
$('#Result').html("Attente retour .............");  
var Passerelle = '<%=c1.serverSMTP%>';
var Login = '<%=c1.loginSMTP%>';
var Password = '<%=c1.passwordSMTP%>';
var Port = '<%=c1.portSMTP%>';
var Emetteur = '<%=c1.mailAdministrateur%>';
var Recepteur = Emetteur;

var Objet = "";
Objet+="Prise de Contact ("+ $('#Mail').val() + ")";

var Corps = "";
Corps+="Nom: " + $('#Nom').val();
Corps+="<br>";
Corps+="Prenom: " + $('#Prenom').val();
Corps+="<br>";
Corps+="Entreprise: " + $('#Entreprise').val();
Corps+="<br>";
Corps+="Telephone: " + $('#Telephone').val();
Corps+="<br>";
Corps+="Adresse: " + $('#Adresse').val();
Corps+="<br>";
Corps+="Mail: " + $('#Mail').val();
Corps+="<br>";
Corps+="<br>";
Corps+="Besoins: ";
Corps+="<br>";
Corps+=$('#Commentaires').val();

jsp="gen_mail-a.jsp";
jsp+="?date="+(new Date());
      
  $('body').css({
     "background-color":"grey"
    }) 
// ----------------------------------- Enregistrement -------------------------------//    
$.ajax( jsp, {
    type: "POST",
    data : {
            Mode: Mode,
            Passerelle : Passerelle,
            Login : Login,
            Password : Password,
            Port : Port,
            Emetteur : Emetteur,
            Recepteur : Recepteur,
            Objet : Objet,
            Corps : Corps
    },
    
} )    


.done(function(data) {
            pos = data.indexOf("@1@");
            data = data.substring(pos+3);
            pos = data.indexOf("@2@");
            data = data.substring(0,pos);                           
            
            var CR='';
            $('#Nom').val('');
            $('#Prenom').val('');
            $('#Entreprise').val('');
            $('#Telephone').val('');
            $('#Adresse').val('');
            $('#Mail').val('');
            $('#Commentaires').val('');              
            
            $('#contact').fadeTo(1000, 0.2); 
            if (data == 'OK')
            {
                    $('#crMail').html("Message envoye avec succes !");
             
            }
            else
            {

                    $('#crMail').html("Echec de l'envoi du mail, contactez nous directement a lixian.technology@gmail.com");             
            }       

            $('#contact').fadeTo(1000, 1);
            
 
            
   $('body').css({
                "background-color":"white"
             })    
    console.log("success !");
})
  .fail(function() {
    console.log("fail !");
  })
  .always(function() {
    console.log("always !");
  });         
}

function enregistrer (action){ 
$('#crMail').hide();
  $('body').css({
     "background-color":"grey"
    })  
    
jsp="vitrine_contactEnregistrer-a.jsp";
jsp+="?date="+(new Date());    

// ----------------------------------- Enregistrement -------------------------------//    
$.ajax( jsp, {
    type: "POST",
    data : {
            action : action,
            idContact: $('#idContact').val(),
            nom : $('#Nom').val(),
            prenom :  $('#Prenom').val(),
            entreprise :  $('#Entreprise').val(),
            telephone :  $('#Telephone').val(),
            adresse :  $('#Adresse').val(),
            mail :  $('#Mail').val(),
            demande :  $('#Commentaires').val()
    }
    
} )
   .done(function(data) {
              pos = data.indexOf("@1@");
              data = data.substring(pos+3);
              pos = data.indexOf("@2@");
              data = data.substring(0,pos);
            if (data == 'KO')
              {   
                
                  $('#crMail').html("Echec de la creation du contact");
                  $('body').css({"background-color":"white"});                  
                  return false;
              }  
              
              var id_etat = data.split('#');
              $('#idContact').val(id_etat[0]);
              $('#idContact').val(-1);
              //var new_etat=id_etat[1];

              
    console.log("success !");
    $('body').css({
     "background-color":"white"
    });
    $('#crMail').show();
    $('#crMail').html('Demande enregistree avec succes !');

    if ('<%=c1.sendMail%>' == '1')
    {
        send('AWS-Configurable');
    }
    else
    {           
                $('#crMail').html('Votre demande a bien &eacute;t&eacute; enregistr&eacute;e, nous vous contacterons tr&egrave;s bientot');
    }
    
  })
  .fail(function() {
                $('#crMail').html("Echec de l'envoi du mail, contacter nous directement a lixian.technology@gmail.com");
    console.log("fail !");
  })
  .always(function() {
    console.log("always !");
  }); 
}

//***************************
function verifier(action) {
//***************************
$('#crMail').hide();

if (action == "Supprimer")
{
  if ( !window.confirm("Cette action va supprimer le domaine ") )
  {
    return false;
  }
}
      
    if ($('#Nom').val() == '') {
        $('#crMail').show();
          $('#crMail').html("vous devez entrer votre nom !");
          $('#Nom').focus();
          return false; 
      }
            
     if (!isEmail($('#Mail').val())) {
        $('#crMail').show();
          $('#crMail').html("Adresse Mail invalide !");
          $('#Mail').focus();
          return false; 
      }
      else
      {
          $('#crMail').html("");
      }  
      
    if ($('#Commentaires').val() == '') {
        $('#crMail').show();
          $('#crMail').html("vous devez entrer la description de votre besoin !");
          $('#Commentaires').focus();
          return false; 
      }         
      
return (true);
} //end Verifier()


</script>
</html>

