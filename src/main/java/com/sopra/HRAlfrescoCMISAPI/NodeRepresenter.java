package com.sopra.HRAlfrescoCMISAPI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;













@XmlRootElement
public class NodeRepresenter
{
  protected String id;
  protected String name;
  protected String description;
  protected Date createdAt;
  protected Date modifiedAt;
  protected String Type;
  protected List<NodeRepresenter> children = new ArrayList();
  

  public NodeRepresenter() {}
  

  public String getId()
  {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Date getCreatedAt()
  {
    return createdAt;
  }
  
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
  
  public Date getModifiedAt() {
    return modifiedAt;
  }
  
  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
  







  public List<NodeRepresenter> getChildren()
  {
    return children;
  }
  
  public void setChildren(List<NodeRepresenter> children) {
    this.children = children;
  }
  

  public String getType()
  {
    return Type;
  }
  
  public void setType(String type) {
    Type = type;
  }
  
  public static int calculateDepth(NodeRepresenter node) {
    int depth = 0;
    if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
      for (NodeRepresenter child : node.getChildren()) {
        depth = Math.max(depth, calculateDepth(child));
      }
      
      depth++;return depth;
    }
    
    return depth;
  }
}