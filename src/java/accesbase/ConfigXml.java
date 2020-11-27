package accesbase; 
 
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.util.Hashtable;
import java.net.URL.*;
import javax.xml.parsers.FactoryConfigurationError;

public class ConfigXml extends DefaultHandler {
  Hashtable hashXML;
  String currentType="";
  String currentValue="";
  private int idx = 0;  

  private static int INDENT = 4;
  private static String attList = "";

  public ConfigXml(String uri){
    try {//getpath
      //String location = System.getProperty("user.dir");
      String location = this.getClass().getResource("").getFile();

      if (location.charAt(0) == '/')
      {
        uri = "file:" +location.substring(1) + uri;
      }
      else
      {
        uri = location + uri;
      }
      //System.out.println("uri="+uri);
      System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
      SAXParserFactory parserFactory = SAXParserFactory.newInstance();
      parserFactory.setValidating(false);
      parserFactory.setNamespaceAware(false);

      this.Init();

      SAXParser parser = parserFactory.newSAXParser();
      parser.parse(uri, this);

      this.readTockenXml_Config();
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
    catch(SAXException ex) {
      ex.printStackTrace();
    }
    catch(ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    catch(FactoryConfigurationError ex) {
      ex.printStackTrace();
    }

  }

  public static void main(String[] argv) {


      String uri = "config_Base_Access.xml";
      ConfigXml ConfigXmlInstance = new ConfigXml(uri);
/*
      uri = "config_Base_sqlServer_Prod.xml";
      ConfigXmlInstance = new ConfigXml(uri);

      uri = "config_Base_sqlServer_test.xml";
      ConfigXmlInstance = new ConfigXml(uri);
*/
  }

  public void characters(char[] ch, int start, int length) throws SAXException {

    String s = new String(ch, start, length);
    if (ch[0] == '\n')
      {
      return;
      }
    //System.out.println(getIndent() + " Value: " + s);
// xxx
        if (this.currentType != "")
        {
          this.hashXML.put(this.currentType,s);
          this.currentType = "";
         }
  }
  

  public void endDocument() throws SAXException {
    idx -= INDENT;
    //System.out.println(getIndent() + "end document");
    //System.out.println("...PARSING ends");
  }
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (!attList.equals(""))
      //System.out.println(getIndent() + " Attributes: " + attList);
    attList = "";
    //System.out.println(getIndent() + "end endElement");
    idx -= INDENT;

  }
  public void startDocument() throws SAXException {
    idx += INDENT;
    //System.out.println("PARSING begins...");
    //System.out.println(getIndent() + "start document: ");
  }
  public void startElement(String uri, String localName, String qName,
      Attributes attributes) throws SAXException {
    idx += INDENT;
    //System.out.println('\n' + getIndent() + "start element: " + qName);
    if (attributes.getLength() > 0) {
      idx += INDENT;
      for (int i = 0; i < attributes.getLength(); i++) {
        attList = attList + attributes.getLocalName(i) + " = " + attributes.getValue(i);
        if (i < (attributes.getLength() - 1))
          attList = attList + ", ";
      }
      idx-= INDENT;
    }
        this.currentType = qName;
  }
  private String getIndent() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < idx; i++)
      sb.append(" ");
    return sb.toString();
  }

  private void Init(){
   //Hashtable hashXML = new Hashtable();
   this.hashXML = new Hashtable (20);
  }

  public String getInfoFromConfig(String Key){
  String debug = (String)hashXML.get("debug");
  String KeyValue = (String)hashXML.get(Key);

  if (KeyValue == null)
  {
    KeyValue =  "";
  }

  if (KeyValue.equals("null"))
  {
    KeyValue =  "";
  }

  if (debug.equals("yes"))
    {
        System.out.println(Key + "=" + KeyValue);
    }


  return KeyValue;

  }

  private void readTockenXml_Config(){
    getInfoFromConfig("technologie");
    getInfoFromConfig("context");
    getInfoFromConfig("URL");
    getInfoFromConfig("driver");
    getInfoFromConfig("login");
    getInfoFromConfig("psw");
    getInfoFromConfig("nom");
    getInfoFromConfig("debug");

    }

}
