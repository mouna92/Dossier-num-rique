package com.sopra.HRAlfrescoCMISAPI;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.exceptions.CmisBaseException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;



public class CmisHelper
{
  public CmisHelper() {}
  
  public static Folder getFolder(Session session, String id, OperationContext context)
    throws Exception
  {
    CmisObject cmisObject = getCmisObject(session, id, context);
    

    if (!(cmisObject instanceof Folder)) {
      throw new Exception("Object is not a folder!");
    }
    
    return (Folder)cmisObject;
  }
  
  public static CmisObject getCmisObject(Session session, String id, OperationContext context) throws Exception {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set!");
    }
    
    if ((id == null) || (id.length() == 0)) {
      throw new Exception("Invalid Id !");
    }
    
    OperationContext oc = context;
    if (oc == null) {
      oc = session.getDefaultContext();
    }
    try
    {
      return session.getObject(id, oc);
    } catch (CmisObjectNotFoundException onfe) {
      throw new Exception("The Object  does not exist!");
    } catch (CmisBaseException cbe) {
      throw new Exception("Could not retrieve the object :" + cbe.getMessage(), cbe);
    }
  }
  



  public static OperationContext initializeDefaultOperationContext(Session session)
  {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set");
    }
    
    OperationContext operationContext = session.createOperationContext();
    operationContext.setFilterString("cmis:name,cmis:path");
    operationContext.setIncludeAcls(false);
    operationContext.setIncludeAllowableActions(false);
    operationContext.setIncludePolicies(false);
    operationContext.setIncludeRelationships(IncludeRelationships.NONE);
    operationContext.setRenditionFilterString("cmis:none");
    operationContext.setIncludePathSegments(false);
    operationContext.setOrderBy(null);
    operationContext.setCacheEnabled(true);
    
    return operationContext;
  }
  
  public static Folder getFolderByPath(Session session, String path, OperationContext context)
  {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set");
    }
    CmisObject object = null;
    if (context != null) {
      object = session.getObjectByPath(path, context);
    } else {
      object = session.getObjectByPath(path);
    }
    

    return (object != null) && ((object instanceof Folder)) ? (Folder)object : null;
  }
  
  public static String getFolderId(Session session, String folderName) {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set");
    }
    String objectId = null;
    String queryString = "select cmis:objectId from cmis:folder where cmis:name='" + folderName + "'";
    ItemIterable<QueryResult> results = session.query(queryString, true);
    for (QueryResult qResult : results) {
      objectId = (String)qResult.getPropertyValueByQueryName("cmis:objectId");
    }
    return objectId;
  }
  
  public static void dataCleaner(Session session, String folderName) {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set");
    }
    String queryString = "select * from cmis:doc where in_folder('" + getFolderId(session, folderName) + "')";
    
    ItemIterable<QueryResult> results = session.query(queryString, false);
    int n = (int)results.getTotalNumItems();
    if (n >= 0)
    {

      for (QueryResult qResult : results) {
        PropertyData<?> propData = qResult.getPropertyById("cmis:objectId");
        String objectId = (String)propData.getFirstValue();
        CmisObject obj = session.getObject(session.createObjectId(objectId));
        obj.delete(true);
      }
    }
  }
  



  public static CmisObject getCmisObject(Session session, String id)
    throws Exception
  {
    if (session == null) {
      throw new IllegalArgumentException("Session must be set");
    }
    if ((id == null) || (id.length() == 0)) {
      throw new Exception("Invalid Id !");
    }
    
    CmisObject object = null;
    try {
      object = session.getObject(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return object;
  }
  





  public static final OperationContext LIGHT_OPERATION_CONTEXT = new OperationContextImpl();
  
  static {
    LIGHT_OPERATION_CONTEXT.setFilterString("cmis:objectId,cmis:objectTypeId,cmis:name,cmis:parentId,cmis:creationDate,cmis:objectTypeId,cmis:lastModificationDate");
    
    LIGHT_OPERATION_CONTEXT.setIncludeAcls(false);
    LIGHT_OPERATION_CONTEXT.setIncludeAllowableActions(false);
    LIGHT_OPERATION_CONTEXT.setIncludePolicies(false);
    LIGHT_OPERATION_CONTEXT.setIncludeRelationships(IncludeRelationships.NONE);
    
    LIGHT_OPERATION_CONTEXT.setRenditionFilterString("cmis:none");
    LIGHT_OPERATION_CONTEXT.setIncludePathSegments(true);
    LIGHT_OPERATION_CONTEXT.setOrderBy(null);
    LIGHT_OPERATION_CONTEXT.setCacheEnabled(true);
  }
}