package org.jboss.forge.plugin.hint.impl;

import org.jboss.forge.project.Facet;
import org.jboss.forge.project.Project;

/**
 * A Holder object to ease working with Drools
 *
 * @author ggastald
 *
 */
public class DroolsHintHolder
{
   private Project project;
   private String output;

   public DroolsHintHolder(Project project)
   {
      this.project = project;
   }

   public boolean isProjectAvailable()
   {
      return project != null;
   }

   /**
    * Returns if this project have this facet enabled
    *
    * @param facetClassName
    * @return
    */
   public boolean containsFile(String fileName)
   {
      return project.getProjectRoot().getChild(fileName).exists();
   }

   /**
    * Returns if this project have this facet enabled
    *
    * @param facetClassName
    * @return
    */
   @SuppressWarnings("unchecked")
   public boolean hasFacet(String facetClassName)
   {
      if (!isProjectAvailable())
      {
         return false;
      }
      else
      {
         Class<?> facetClass;
         try
         {
            facetClass = Class.forName(facetClassName, true, getClass().getClassLoader());
         }
         catch (ClassNotFoundException e)
         {
            System.err.println("Class not found in this forge installation: " + facetClassName);
            return false;
         }
         return project.hasFacet((Class<? extends Facet>) facetClass);
      }
   }

   public String getOutput()
   {
      return output;
   }

   public void setOutput(String output)
   {
      this.output = output;
   }

}