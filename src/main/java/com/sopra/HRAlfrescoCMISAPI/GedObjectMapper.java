package com.sopra.HRAlfrescoCMISAPI;

import java.util.GregorianCalendar;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;

public class GedObjectMapper
{
  public GedObjectMapper() {}
  
  public static NodeRepresenter mapObject(CmisObject object)
  {
    NodeRepresenter node = new NodeRepresenter();
    node.setCreatedAt(object.getCreationDate().getTime());
    node.setId(object.getId());
    node.setName(object.getName());
    
    return node;
  }
  




  public static NodeRepresenter mapFileableObject(FileableCmisObject object)
  {
    NodeRepresenter node = new NodeRepresenter();
    if ((object instanceof Folder)) {
      String parentId = ((Folder)object).getParentId();
      node.setType("folder");

    }
    else if ((object instanceof Document)) {
      String parentId = ((Folder)((Document)object).getParents().get(0)).getId();
      node.setType("document");
    } else {
      return null;
    }
    
    String parentId;
    node.setId(object.getId());
    node.setName(object.getName());
    node.setCreatedAt(object.getCreationDate().getTime());
    node.setModifiedAt(object.getLastModificationDate().getTime());
    node.setType(object.getType().getLocalName());
    


    return node;
  }
}