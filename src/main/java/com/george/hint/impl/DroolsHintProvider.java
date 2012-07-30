package com.george.hint.impl;

import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.WebResourceFacet;

import com.george.hint.HintProvider;

/**
 * A Hint Provider that uses Drools as the rule engine
 *
 * @author George Gastaldi <ggastald@redhat.com>
 *
 */
public class DroolsHintProvider implements HintProvider
{

   @Override
   public String computeHint(Project project)
   {
      if (project == null)
      {
         return "start";
      }
      if (project.hasFacet(WebResourceFacet.class))
      {
         return "persistence";
      }
      return "";
   }
}
