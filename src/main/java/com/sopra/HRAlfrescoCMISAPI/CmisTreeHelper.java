package com.sopra.HRAlfrescoCMISAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Tree;

public class CmisTreeHelper<T>
{
  public CmisTreeHelper() {}
  
  public void treeWalker(List<Tree<T>> tree, Callable<T> function)
    throws Exception
  {
    for (Tree<T> node : tree) {
      treeWalker(node.getChildren(), function);
    }
    function.call();
  }
  
  public static List<NodeRepresenter> getItemsList(List<Tree<FileableCmisObject>> tree)
  {
    List<NodeRepresenter> result = new ArrayList();
    
    if (tree != null) {
      for (Tree<FileableCmisObject> node : tree) {
        result.addAll(getItemsList(node.getChildren()));
      }
      for (Tree<FileableCmisObject> node : tree) {
        result.add(GedObjectMapper.mapFileableObject((FileableCmisObject)node.getItem()));
      }
    }
    return result;
  }
  
  public static NodeRepresenter getItemsTree(Tree<FileableCmisObject> tree) {
    NodeRepresenter node = GedObjectMapper.mapFileableObject((FileableCmisObject)tree.getItem());
    
    List<Tree<FileableCmisObject>> children = tree.getChildren();
    if ((children != null) && (!children.isEmpty())) {
      for (Tree<FileableCmisObject> child : tree.getChildren()) {
        node.getChildren().add(getItemsTree(child));
      }
    }
    return node;
  }
}
   

 