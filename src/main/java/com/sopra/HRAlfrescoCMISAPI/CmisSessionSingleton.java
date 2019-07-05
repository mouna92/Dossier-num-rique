package com.sopra.HRAlfrescoCMISAPI;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class CmisSessionSingleton
{
  private static volatile Session instance;
  
  private CmisSessionSingleton() {}
  
  public static Session getInstance() throws ParserConfigurationException, SAXException, IOException
  {
    if (instance == null) {
      synchronized (CmisSessionSingleton.class) {
        if (instance == null) {
          try
          {
            instance = createOpenCmisSession();
          }
          catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
          catch (InstantiationException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return instance;
  }
  
  private static Map<String, String> getParameters() throws ParserConfigurationException, SAXException, IOException {
	 File inputFile = new File("/dv46/param/parameters.xml");
	 // File inputFile = new File ("D:\\Profiles\\mousassi\\Desktop\\parameters.xml");
	    
	     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	     org.w3c.dom.Document doc = dBuilder.parse(inputFile);
	     doc.getDocumentElement().normalize();
	     System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     NodeList nList = doc.getElementsByTagName("Parameters");
	     String user = null;
	     String password = null;
	     String url = null;
	     NodeList nList1 = doc.getElementsByTagName("user");
	     NodeList nList2 = doc.getElementsByTagName("password");
	     NodeList nList3 = doc.getElementsByTagName("url");
	     for (int i = 0; i < nList.getLength(); i++) {
	         Node nNode = nList1.item(i);
	         Element eElement1 = (Element)nNode;
	         user = eElement1.getTextContent();
	         Node nNode1 = nList2.item(i);
	         Element eElement2 = (Element)nNode1;
	         password = eElement2.getTextContent();
	         Node nNode2 = nList3.item(i);
	         Element eElement3 = (Element)nNode2;
	         url = eElement3.getTextContent();
	     }

//    String USERNAME = "admin";
//    String PASSWORD = "admin";
//    String BROWSER_BINDING_URL = "http://dlnxhradev01.ptx.fr.sopra:7080/alfresco/api/-default-/public/cmis/versions/1.1/browser";
//    String REPOSITORY_ID = "-default-";
    Map<String, String> parameter = new HashMap();
    parameter.put("org.apache.chemistry.opencmis.user", user);
    parameter.put("org.apache.chemistry.opencmis.password", password);
    parameter.put("org.apache.chemistry.opencmis.binding.browser.url", url);
    parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.BROWSER.value());
    parameter.put("org.apache.chemistry.opencmis.session.repository.id", "-default-");
    parameter.put("org.apache.chemistry.opencmis.binding.webservices.jaxws.impl", "cxf");
    return parameter;
  }
  
  private static Session createOpenCmisSession() throws ClassNotFoundException, InstantiationException, ParserConfigurationException, SAXException, IOException
  {
    SessionFactory factory = SessionFactoryImpl.newInstance();
    return factory.createSession(getParameters());
  }
}
