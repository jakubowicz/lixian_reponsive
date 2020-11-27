var couleurNormal = "#DDE8EE";
var couleurWarning = "#FF0000";
var couleurEngage = "#FF9900";
var couleurClos = "lightgrey";

function getTextElementByColor(color) {
    if (color == 'transparent' || color.hex == "") {
    return $("<div style='text-shadow: none; position: relative; padding-bottom: 2px; margin-top: 2px;'>transparent</div>");
    }
    var element = $("<div style='text-shadow: none; position: relative; padding-bottom: 2px; margin-top: 2px;'>#" + color.hex + "</div>");
    var nThreshold = 105;
    var bgDelta = (color.r * 0.299) + (color.g * 0.587) + (color.b * 0.114);
    var foreColor = (255 - bgDelta < nThreshold) ? 'Black' : 'White';
    element.css('color', foreColor);
    element.css('background', "#" + color.hex);
    element.addClass('jqx-rc-all');
    return element;
}

//***************************
function showdetailSt(id, nom){
//***************************

     $( "#dialog-confirm" ).dialog({
                title:"Detail: "+nom,
                resizable: false,
                height:500,
                width: 500,
                modal: true,
                buttons: {
                     "Fermer": function() {
                        $( this ).dialog( "close" );
                    },
                     "Aller vers" : function() {
                        $(location).attr('href', 'produit_Consulter_jcanvas.jsp?idVersionSt='+id+"&Onglet=Graphe");
                        $( this ).dialog( "close" );
                    }                    
                }
    });

    jsp ="produit_description-a.jsp";
    jsp+="?date="+(new Date());

    $.ajax( jsp, {
        type: "POST",
        datatype: "text",
        data : {
        idVersionSt : id
        }
    
    } )
            .done(function(data) {
             data=extractFromTags( data, "@1@", "@2@");

              $( "#dialog-confirm" ).html(data);
              })

            .fail(function() {
                console.log("failed");
                });
            
}
function progress(titre, tempo){
            $( "#dialog-confirm" ).dialog({
                title: titre,
                resizable: false,
                height:170,
                modal: true
    }); 
    
    $( "#textProgress" ).html("Le temps d'attente est estim&eacute a: " + parseInt(tempo)/10 + " secondes" );
            var renderText = function (text, value)
            {
                if (value < 55)
                {
                    return "<span style='color: #333;'>" + text + "</span>";
                }
                return "<span style='color: #fff;'>" + text + "</span>";
            }
            $("#jqxProgressBar").jqxProgressBar({ animationDuration: 0, showText: true, renderText: renderText, template: "warning", width: 250, height: 30, value: 0 });    
            $("#jqxProgressBar").show();
            var values = {};
            var addInterval = function (id, intervalStep)
            {
                values[id] = {value: 0};
                values[id].interval = setInterval(function ()
                {
                    values[id].value++;
                    if (values[id].value == 100)
                    {
                        clearInterval(values[id].interval);
                        
                    }
                    $("#" + id).val(values[id].value);
                }, intervalStep);
            }

            addInterval("jqxProgressBar", tempo);        
}

function extractFromTags(data, start, end)
{
              pos = data.indexOf(start);
              str_out = data.substring(pos+start.length);
              pos = str_out.indexOf(end);
              str_out = str_out.substring(0,pos);   
     return str_out;         
              
}

function showDialog(titre, p1, p2)
{
    $('#errorP1').html(p1);
    $('#errorP2').html(p2);
    $( "#dialog-message" ).dialog({
      autoOpen:false,
      title: titre,
      modal: true,
      icon: "Mauvaise.png",
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
        }
      }
    });
    $( "#dialog-message" ).dialog("open");
    $( "#myIcon" ).show();
   
  }   
  

function isTelephon(e){
     var regex = new RegExp("[0-9 -()+]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;           
 }

function isEmail(email){
	var emailReg = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
	var valid = emailReg.test(email);

	if(!valid) {
        return false;
    } else {
    	return true;
    }
}

function isAlphanumericAccent(e){
    var regex = new RegExp("^((?:\\w|[\\-_ ](?![\\-_ ])|[\\u00C0\\u00C1\\u00C2\\u00C3\\u00C4\\u00C5\\u00C6\\u00C7\\u00C8\\u00C9\\u00CA\\u00CB\\u00CC\\u00CD\\u00CE\\u00CF\\u00D0\\u00D1\\u00D2\\u00D3\\u00D4\\u00D5\\u00D6\\u00D8\\u00D9\\u00DA\\u00DB\\u00DC\\u00DD\\u00DF\\u00E0\\u00E1\\u00E2\\u00E3\\u00E4\\u00E5\\u00E6\\u00E7\\u00E8\\u00E9\\u00EA\\u00EB\\u00EC\\u00ED\\u00EE\\u00EF\\u00F0\\u00F1\\u00F2\\u00F3\\u00F4\\u00F5\\u00F6\\u00F9\\u00FA\\u00FB\\u00FC\\u00FD\\u00FF\\u0153\\u0027\\u002E])+)$", "i");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;           
 }
 
function isAlphanumeric(e){
     var regex = new RegExp("^[a-zA-Z0-9_\-\\s\b]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;           
 }
 
function isDecimal(e){
     var regex = new RegExp("^[0-9-.\b]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;           
 }
 
 function isNumeric(e){
     var regex = new RegExp("^[0-9\b]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;           
 }
 
 function isFloat(e){
     var regex = new RegExp("^[0-9-\b]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }

    e.preventDefault();
    return false;       
 }
 
//***************************
function SelectionFichier(numLigne, TypeDoc,idObj,idEtat,TypeTxf) {
//***************************
window.open("gen_SelectionDocSuivi.jsp?numLigne="+numLigne+"&TypeDoc="+TypeDoc+"&idObj="+idObj+"&idEtat="+idEtat+"&TypeTxf="+TypeTxf,'','width=700,height=350');

}
//***************************
function isIE() {
  //***************************
  //
  //var ms = navigator.appVersion.indexOf("MSIE");
  var ms = navigator.appVersion.indexOf(".NET");
  //alert("navigator.appVersion="+navigator.appVersion);
  if (ms > 0)
      return true;
  else
      return false;
  
  }
//***************************
function checkNavigator() {
  //***************************
//alert("Le nom ou la marque du browser est " +navigator.appName);
//alert("Les informations sur la version sont "+navigator.appVersion);
//alert("Le browser a comme user-agent name "+navigator.userAgent);


var ms = navigator.appVersion.indexOf("MSIE")

ie7 = (ms>0) && (parseInt(navigator.appVersion.substring(ms+5, ms+6)) >= 7);
//alert ("ie8="+ie8);
if (ie7)
{
  //alert("Le navigateur est IE, de version > 8");
  return (true);
}
else
{
  //alert("Le navigateur n'est pas IE");
  return (false);
}
}

//***************************
function setTimeoutFrame(division, timer) {
  //***************************
  //setTimeout ("MajFrame('20%,0%,*')", 5000 );

  myFunct = "MajFrame('"+division+"')";
  //alert("myFunct="+myFunct);
  setTimeout (myFunct, timer );
}

//***************************
function MajFrame (division) {
//***************************
  //alert ("coucou");
        var frameSet = window.frameElement.parentNode;
		//alert (frameSet.rows);
		//frameSet.rows ="20%,0%,*";
                frameSet.rows =division;
        return frameSet.rows;
}

//***************************
function epure(champ) {
  //***************************
  epureChamp = "";
        for (i=0; i< champ.length ; i++)
        	{
                  c = champ.charAt(i);
                  //alert (c);
                  //alert("caractre:"+c+ " interdit!");
                  if (((c < 'a') || (c > 'z'))&& ((c < 'A')|| (c > 'Z')) && ((c < '0')|| (c > '9')) )
                  	{
                          //alert ("car "+c+" interdit");
                            continue;
                        }
                        else
                        epureChamp +=c;
                }
          return epureChamp;
}

//***************************
function epure2(champ) {
  //***************************
  epureChamp = "";
        for (i=0; i< champ.length ; i++)
        	{
                  c = champ.charAt(i);
                  //alert (c);
                  //alert("caractre:"+c+ " interdit!");
                  if (((c < 'a') || (c > 'z'))&& ((c < 'A')|| (c > 'Z')) && ((c < '0')|| (c > '9')) && (c != ' ')&& (c != ',') && (c != '.')&& (c != '_') && (c != '-') && (c != 'é')&& (c != 'è')&& (c != 'à')&& (c != '\''))
                  	{
                          //alert ("car "+c+" interdit");
                            continue;
                        }
                        else
                        epureChamp +=c;
                }
          return epureChamp;
}
//***************************
function isNumber(champ) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ+num);
    for(i=0;i < toCheck.value.length;i++) { if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9") {chkZ = -1;}}
    if(chkZ == -1) { alert("Le champ "+champ+" doit être un nombre"); toCheck.focus(); return false;}
    return true;
}

//***************************
function isInteger(champ) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ);
    for(i=0;i < toCheck.value.length;i++) { if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9") {chkZ = -1;}}
    if(chkZ == -1) { alert("Le champ "+champ+" doit être un nombre"); toCheck.focus(); return false;}
    return true;
}

//***************************
function isInteger_light(champ) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ);
    for(i=0;i < toCheck.value.length;i++) { if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9") {chkZ = -1;}}
    if(chkZ == -1) { return false;}
    return true;
}

//***************************
function isFloat(champ) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ+num);
    for(i=0;i < toCheck.value.length;i++) {
       if (toCheck.value.charAt(i) == ".") continue;
      if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9" ) {chkZ = -1;}}
    if(chkZ == -1) { alert("Le champ "+champ+" doit être un nombre décimal de la forme: a.x"); toCheck.focus(); return false;}
    return true;
}

//***************************
function isFloatTab(champ, Tab, numTab) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ+num);
    for(i=0;i < toCheck.value.length;i++) {
       if (toCheck.value.charAt(i) == ".") continue;
      if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9" ) {chkZ = -1;}}
    if(chkZ == -1) { alert("Le champ "+champ+" doit être un nombre décimal de la forme: a.x"); Tab.selectTab(numTab);toCheck.focus(); return false;}
    return true;
}

//***************************
function isFloatTab_index(champ, Tab, numTab, index) {
  //***************************
    var chkZ = 1;
    var toCheck = document.getElementById(champ+index);
    for(i=0;i < toCheck.value.length;i++) {
       if (toCheck.value.charAt(i) == ".") continue;
      if (toCheck.value.charAt(i) < "0" || toCheck.value.charAt(i) > "9" ) {chkZ = -1;}}
    if(chkZ == -1) { alert("Le champ "+champ+" doit être un nombre décimal de la forme: a.x"); Tab.selectTab(numTab);toCheck.focus(); return false;}
    return true;
}

//***************************
function AfficherBarreProgression(){
  //***************************
document.getElementById("progression").style.display="";
document.getElementById("boule").style.display="none";
//document.getElementById("logo").style.display="none";
}

//***************************
function LogoChange(){
  //***************************
document.getElementById("progression").style.display="";
document.getElementById("boule").style.display="none";
//document.getElementById("logo").style.display="none";
}

//***************************
function LogoReste(){
  //***************************
document.getElementById("progression").style.display="none";
document.getElementById("boule").style.display="";
document.getElementById("logo").style.display="";
}

//***************************
function AfficherBarreProgression2(){
  //***************************
  //alert ("parent.parent.pageHaut.progression="+parent.parent.pageHaut.progression);
if (parent.parent.pageHaut != undefined)
{
  parent.parent.pageHaut.progression.style.display="";
  parent.parent.pageHaut.boule.style.display="none";
  //parent.parent.pageHaut.logo.style.display="none";
}
else
{
  parent.parent.parent.pageHaut.progression.style.display="";
  parent.parent.parent.pageHaut.boule.style.display="none";
  //parent.parent.parent.pageHaut.logo.style.display="none";
}
}

//***************************
function MasquerBarreProgression3(){
  //***************************

  parent.pageHaut.progression.style.display="none";
  parent.pageHaut.boule.style.display="";
  //parent.pageHaut.logo.style.display="";

}
//***************************
function MasquerBarreProgression(){
//***************************
//alert("parent.pageHaut="+parent.pageHaut);
if (parent.pageHaut != undefined)
{
  parent.pageHaut.progression.style.display="none";
  parent.pageHaut.boule.style.display="";
  //parent.pageHaut.logo.style.display="";
}

else if (parent.parent.pageHaut != undefined)
{
  parent.parent.pageHaut.progression.style.display="none";
  parent.parent.pageHaut.boule.style.display="";
  //parent.parent.pageHaut.logo.style.display="";
}
else if (parent.parent.parent.pageHaut != undefined)
{
  parent.parent.parent.pageHaut.progression.style.display="none";
  parent.parent.parent.pageHaut.boule.style.display="";
  //parent.parent.parent.pageHaut.logo.style.display="";
}

}
//***************************
function MasquerBarreProgression2(){
//***************************

if (parent.parent.parent.pageHaut != undefined)
{
  parent.parent.parent.pageHaut.progression.style.display="none";
  parent.parent.parent.pageHaut.boule.style.display="";
  //parent.parent.parent.pageHaut.logo.style.display="";
}
else if (parent.parent.pageHaut != undefined)
{
  parent.parent.pageHaut.progression.style.display="none";
  parent.parent.pageHaut.boule.style.display="";
  //parent.parent.pageHaut.logo.style.display="";
}

}
//***************************
function BeginRepeat(Ligne, semaine, majTotal){
//***************************
  window.open('repeat.jsp?Ligne='+Ligne+"&semaine="+semaine+"&majTotal="+majTotal,'fen1','width=450,height=50,scrollbars=yes');
}

//***************************
function maximizeWindow() {
//***************************
//return;
  if (document.all) {

    top.window.resizeTo(screen.availWidth,screen.availHeight);
    //parent.window.resizeTo(screen.availWidth,screen.availHeight);

  } else if (document.layers||document.getElementById) {

    if (top.window.outerHeight < screen.availHeight || top.window.outerWidth < screen.availWidth) {

      top.window.outerHeight = screen.availHeight;
      top.window.outerWidth = screen.availWidth;

    }

  }
  top.window.moveTo(0, 0);
}

function setContainer(myContainer,Header,  Body, icon) {
    //alert("myContainer="+myContainer);
    //alert("Header="+Header);
    //alert("Body="+Body);
    //alert("icon="+icon);
          myContainer.setHeader(Header);
          myContainer.setBody(Body);
          myContainer.cfg.setProperty("icon", icon);
          myContainer.show();
}

function AffLegendeJalon()
{
  window.open('../../help/helpIndicateurs_jalons.htm','fen1','width=750,height=400,scrollbars=yes');
}

function AffLegendeToDo()
{
  window.open('../../help/helpIndicateurs_todoliste.htm','fen1','width=700,height=200,scrollbars=yes');
}

function AffLegendeAdmin()
{
  window.open('../../help/helpIndicateurs_administration.htm','fen1','width=650,height=160,scrollbars=yes');
}

function getSelectValue(selectId){
    //alert("---selectId="+selectId);
var elmt = document.getElementById(selectId);
//alert("---elmt="+elmt);
if (elmt == null) return "";
myListe="";
if (elmt.multiple == false)
	{
	 return elmt.options[elmt.selectedIndex].value;
	}
var values = new Array();
for(var i=0; i< elmt.options.length; i++)
	{
		if(elmt.options[i].selected == true)
		{
                  //alert(elmt.options[i].value);
                  myListe+=elmt.options[i].value+";"
		values[values.length] = elmt.options[i].value;
		}
	}
return myListe;
}

function getSelectValueByRef(selectId){
    //alert("---getSelectValueByRef="+selectId);
var elmt = selectId;
//alert("---elmt="+elmt);
if (elmt == null) return "";
myListe="";
if (elmt.multiple == false)
	{
	 return elmt.options[elmt.selectedIndex].value;
	}
var values = new Array();
for(var i=0; i< elmt.options.length; i++)
	{
		if(elmt.options[i].selected == true)
		{
                  //alert(elmt.options[i].value);
                  myListe+=elmt.options[i].value+";"
		values[values.length] = elmt.options[i].value;
		}
	}
        //alert("---myListe="+myListe);
return myListe;
}

//***************************
function AfficherPage(nomMenu, nomPage, nomSousMenu) {
//***************************

document.location="accueil.jsp?treeview=0&nomPage="+nomPage;

}


//***************************
function AfficherPageBas(nomMenu, numSousMenu, nomSousMenu, Menu1, Menu2, Menu3) {
//***************************
document.location="accueil.jsp?treeview=1&menu="+numSousMenu+"&Menu1="+Menu1+"&Menu2="+Menu2+"&Menu3="+Menu3;

}


//***************************
function checkContentConsulterOM() {
//***************************
var debut=3;
var ln = document.menu.rechercher.value.length;

if (ln >= 3)
{
  var nomOm = new Array();
  var callback = {
    success : function(o) {
      var data = o.responseText;
      pos = data.indexOf("@1@");
      data = data.substring(pos+3);
      //alert(data);
      pos = data.indexOf("@2@");
      data = data.substring(0,pos);

      // Recherche du nombre de separateurs
      var unOm = data.split(',');
      //alert("0--- unOm.length="+unOm.length+ "/"+unOm);
      //    1) si exactement 1
      if (unOm.length == 2)
      {
       //alert("1--- unOm.length="+unOm.length+ "/"+unOm);
        //       - On separe le nom et l'id
        nomOm = unOm[0].split(';');
        //       - On expande le nom
        document.menu.rechercher.value = nomOm[0];
        //       - On affiche la fiche du ST
        var param = "donnee_consulter.jsp?idObj="+nomOm[1];
        AfficherPage('Administration',param);
      }
      else if (unOm.length > 2)
      {
        //  alert("2--- unOm.length="+unOm.length+ "/"+unOm);
        //    2) si > 1
        //       - On appelle le browser Gauche
        AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','OM','Consulter');
      }
      else if (unOm.length == 1)
      {
          //alert("3--- unOm.length="+unOm.length+ "/"+unOm);
        //    3) = 0
        //       - On ne fait rien
        AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','OM','Consulter');
      }
      var part = data.split(',');
      //alert("data="+data);



    },
    failure : function(o) {
      alert("CONNECTION FAILED!"+o.responseText);
      return false;
    }

  }


  jsp="Ajax_OM-Like-req.jsp?nomOm="+document.menu.rechercher.value;
  //alert (jsp);

  var conn = YAHOO.util.Connect.asyncRequest("GET",jsp, callback);
}
}

//***************************
function checkContentModifierOM() {
//***************************
var debut=3;
var ln = document.menu.rechercher.value.length;

if (ln >= 3)
{
  var nomOm = new Array();
  var callback = {
    success : function(o) {
      var data = o.responseText;
      pos = data.indexOf("@1@");
      data = data.substring(pos+3);
      //alert(data);
      pos = data.indexOf("@2@");
      data = data.substring(0,pos);

      // Recherche du nombre de separateurs
      var unOm = data.split(',');
      //alert("0--- unOm.length="+unOm.length+ "/"+unOm);
      //    1) si exactement 1
      if (unOm.length == 2)
      {
       //alert("1--- unOm.length="+unOm.length+ "/"+unOm);
        //       - On separe le nom et l'id
        nomOm = unOm[0].split(';');
        //       - On expande le nom
        document.menu.rechercher.value = nomOm[0];
        //       - On affiche la fiche du ST
        var param = "CreerOM.jsp?idObjetMetier="+nomOm[1]+"&type=menu&typeEtatObjetMetier=1&Admin=no";
        AfficherPage('Administration',param);
      }
      else if (unOm.length > 2)
      {
        //  alert("2--- unOm.length="+unOm.length+ "/"+unOm);
        //    2) si > 1
        //       - On appelle le browser Gauche
        AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','OM','Modifier');
      }
      else if (unOm.length == 1)
      {
          //alert("3--- unOm.length="+unOm.length+ "/"+unOm);
        //    3) = 0
        //       - On ne fait rien
        AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','OM','Modifier');
      }
      var part = data.split(',');
      //alert("data="+data);



    },
    failure : function(o) {
      alert("CONNECTION FAILED!"+o.responseText);
      return false;
    }

  }


  jsp="donnee_checkContent-a.jsp?nomOm="+document.menu.rechercher.value;
  //alert (jsp);

  var conn = YAHOO.util.Connect.asyncRequest("GET",jsp, callback);
}
}

//***************************
function checkContent() {
//***************************
var myStr=parent.pageBas.location.toString();
//alert("myStr="+myStr+" :: "+myStr.indexOf("Menu2=Map"));
TypeRecherche = "";
if ((myStr.indexOf("Menu2=Map") >= 0) || (myStr.indexOf("new_map.jsp") >= 0)) TypeRecherche="Map";
if (((myStr.indexOf("Menu1=OM") >= 0) && (myStr.indexOf("Menu2=Consulter") >= 0)) || ((myStr.indexOf("Menu1=Recherche") >= 0) && (myStr.indexOf("Menu2=OM") >= 0) && (myStr.indexOf("Menu3=Consulter") >= 0))) 
{
    checkContentConsulterOM();
    return;
 }
if (((myStr.indexOf("Menu1=Administration") >= 0) && (myStr.indexOf("Menu2=OM") >= 0)) || ((myStr.indexOf("Menu1=OM") >= 0) && (myStr.indexOf("Menu2=Modifier") >= 0)) || ((myStr.indexOf("Menu1=Recherche") >= 0) && (myStr.indexOf("Menu2=OM") >= 0) && (myStr.indexOf("Menu3=Modifier") >= 0))) 
{
    checkContentModifierOM();
    return;
 } 

var debut=3;
var ln = document.menu.rechercher.value.length;

if (ln >= 3)
{
  var nomSt = new Array();
  var callback = {
    success : function(o) {
      var data = o.responseText;
      pos = data.indexOf("@1@");
      data = data.substring(pos+3);
      //alert(data);
      pos = data.indexOf("@2@");
      data = data.substring(0,pos);

      // Recherche du nombre de separateurs
      var unSt = data.split(',');
      //alert("unSt.length="+unSt.length+ "/"+unSt);
      //    1) si exactement 1
      if (unSt.length == 2)
      {
        //       - On separe le nom et l'id
        nomSt = unSt[0].split(';');
        //       - On expande le nom
        document.menu.rechercher.value = nomSt[0];
        //       - On affiche la fiche du ST
    if (((myStr.indexOf("Menu1=Administration") >= 0) && (myStr.indexOf("Menu2=ST") >= 0)) || ((myStr.indexOf("Menu1=ST") >= 0) && (myStr.indexOf("Menu2=Modifier") >= 0))) 
    {
        var param = "produit_Creer.jsp?typeForm=Modification&etatFicheVersionSt=1&Admin=no&TypeST=ST&idVersionSt="+nomSt[1];
    } 
    else
    {
        var param = "produit_Consulter.jsp?idVersionSt="+nomSt[1]+"&signet="+"&TypeCarto=&target=pageBas";
    }
    AfficherPage('Administration',param);
      }
      else if (unSt.length > 2)
      {
        //    2) si > 1
        //       - On appelle le browser Gauche
        if (((myStr.indexOf("Menu1=Administration") >= 0) && (myStr.indexOf("Menu2=ST") >= 0)) || ((myStr.indexOf("Menu1=ST") >= 0) && (myStr.indexOf("Menu2=Modifier") >= 0))) 
        {        
            AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','ST','Modifier');
        }
        else
        {        
            AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','ST','Consulter');
        }        
      }
      else if (unSt.length == 1)
      {
        //    3) = 0
        //       - On ne fait rien
        AfficherPageBas('Moteur',700,document.menu.rechercher.value,'Recherche','ST','');
      }
      var part = data.split(',');
      //alert("data="+data);



    },
    failure : function(o) {
      alert("CONNECTION FAILED!"+o.responseText);
      return false;
    }

  }


  jsp="produit_checkContent-a.jsp?nomSt="+document.menu.rechercher.value;
  //alert (jsp);

  var conn = YAHOO.util.Connect.asyncRequest("GET",jsp, callback);
}

}

function selectRechercher() {
document.menu.rechercher.focus();

}


function Capture() {

//if (window.event.keyCode == 13) AfficherPageBas2('Moteur',700,document.menu.rechercher.value);
//if (document.menu.rechercher.value == '\013') alert ("retour chariot");
//alert(window.event.keyCode);

if (event.keyCode==13)
{
event.keyCode=10;

}
}

  	function showHelp (myPanel, myHeader, myBody , myFooter){
          showTooltip(myPanel,myHeader,myBody, myFooter )
        }

	function showTooltip(myPanel,myHeader,myBody, myFooter ){

	myPanel.setHeader(myHeader);
	myPanel.setBody(myBody);
	myPanel.setFooter(myFooter);
	myPanel.show();
	}
        
//***************************
function AffDate (i,jour,mois,annee, typeDate) {
//***************************
//alert ("jour="+jour+"::mois="+(mois)+"::annee="+(annee));
var theJour = '';
var themois = '';

if (jour < 10) theJour = '0'+jour; else theJour = ''+jour;
if (mois < 10) themois = '0'+mois; else themois = ''+mois;

//alert ("typeDate="+typeDate+"::i="+i+"::annee="+(annee));
if (annee > 1995)
{
	document.getElementById(typeDate+i).value = theJour + '/' + themois+ '/' + annee;
}
else
{
	document.getElementById(typeDate+i).value ="";
}

}

//***************************
function AffOneDate (i,jour,mois,annee, typeDate) {
//***************************


}


//***************************
function changeCar(form,str,car) {
  //***************************
   //alert(str);
    newStr="";
        for (nbcar=0;nbcar< str.length ; nbcar++)
        	{
                  c = str.charAt(nbcar);
                  //alert("caractère:"+c);

                  if ( (c==car) )
                  	{
                            //alert("euro"); ;
                            if (car == '\u20AC')
                               newStr+="&euro;";
                            else
                               newStr+=car;
                        }
                        else
                        {
                          newStr+=c;
                        }
                }
   return newStr;
}

//***************************
function getDateFrench2English(myString) {
  //***************************
    pos = 0;
    choice = "";

    pos = myString.indexOf("/");
    choice = myString.substring(0,pos);

    if (choice.length !=4)
    {

      Day = choice;
      if ( (parseInt(Day) < 10) && (Day.length == 1))
        Day = "0" + Day;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("/");

      Month = myString.substring(0, pos);
      if ( (parseInt(Month) < 10) && (Month.length == 1))
        Month = "0" + Month;

      Year = myString.substring(pos + 1);
    }
    else
    {

      Year = choice;

      myString = myString.substring(pos + 1);
      pos = myString.indexOf("/");

      Month = myString.substring(0, pos);
      if ( (parseInt(Month) < 10) && (Month.length == 1))
        Month = "0" + Month;

      Day = myString.substring(pos + 1);
      if ( (parseInt(Day) < 10) && (Day.length == 1))
        Day = "0" + Day;
    }

    return Year+"/"+Month+"/"+Day;
}

        