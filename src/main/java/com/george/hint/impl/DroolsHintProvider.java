package com.george.hint.impl;

import javax.enterprise.context.ApplicationScoped;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.forge.project.Project;

import com.george.hint.HintProvider;

/**
 * A Hint Provider that uses Drools as the rule engine
 *
 * @author George Gastaldi <ggastald@redhat.com>
 *
 */
@ApplicationScoped
public class DroolsHintProvider implements HintProvider
{

   private KnowledgeBase base = readKnowledgeBase();

   private static KnowledgeBase readKnowledgeBase()
   {
      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
      kbuilder.add(ResourceFactory.newClassPathResource("hint/drools/builtin_hints.drl"), ResourceType.DRL);
      KnowledgeBuilderErrors errors = kbuilder.getErrors();
      if (errors.size() > 0)
      {
         for (KnowledgeBuilderError error : errors)
         {
            System.err.println(error);
         }
         throw new IllegalArgumentException("Could not parse knowledge.");
      }
      KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
      kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
      return kbase;
   }

   @Override
   public String computeHint(Project project)
   {
      DroolsHintHolder holder = new DroolsHintHolder(project);
      StatefulKnowledgeSession session = base.newStatefulKnowledgeSession();
      session.insert(holder);
      session.fireAllRules();
      return holder.getOutput();
   }
}
