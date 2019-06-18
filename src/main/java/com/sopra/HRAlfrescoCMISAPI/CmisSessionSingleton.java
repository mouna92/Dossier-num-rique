package com.sopra.HRAlfrescoCMISAPI;

import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.enums.BindingType;




public class CmisSessionSingleton
{
  private static volatile Session instance;
  
  private CmisSessionSingleton() {}
  
  public static Session getInstance()
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
  
  private static Map<String, String> getParameters() {
    String USERNAME = "admin";
    String PASSWORD = "admin";
    String BROWSER_BINDING_URL = "http://dlnxhradev01.ptx.fr.sopra:7080/alfresco/api/-default-/public/cmis/versions/1.1/browser";
    String REPOSITORY_ID = "-default-";
    Map<String, String> parameter = new HashMap();
    parameter.put("org.apache.chemistry.opencmis.user", "admin");
    parameter.put("org.apache.chemistry.opencmis.password", "alfresco");
    parameter.put("org.apache.chemistry.opencmis.binding.browser.url", "http://dlnxhradev01.ptx.fr.sopra:7080/alfresco/api/-default-/public/cmis/versions/1.1/browser");
    parameter.put("org.apache.chemistry.opencmis.binding.spi.type", BindingType.BROWSER.value());
    parameter.put("org.apache.chemistry.opencmis.session.repository.id", "-default-");
    parameter.put("org.apache.chemistry.opencmis.binding.webservices.jaxws.impl", "cxf");
    return parameter;
  }
  
  private static Session createOpenCmisSession() throws ClassNotFoundException, InstantiationException
  {
    SessionFactory factory = SessionFactoryImpl.newInstance();
    return factory.createSession(getParameters());
  }
}
