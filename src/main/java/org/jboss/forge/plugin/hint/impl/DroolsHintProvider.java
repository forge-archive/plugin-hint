package org.jboss.forge.plugin.hint.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.forge.plugin.hint.api.HintRequestEvent;
import org.jboss.forge.plugin.hint.api.KnowledgeBaseHintBuildEvent;

/**
 * A Hint Provider that uses Drools as the rule engine
 *
 * @author George Gastaldi <ggastald@redhat.com>
 *
 */
@ApplicationScoped
public class DroolsHintProvider
{

   private KnowledgeBase base;

   @Inject
   private Event<KnowledgeBaseHintBuildEvent> event;

   public void onHintRequested(@Observes HintRequestEvent evt)
   {
      if (base == null)
      {
         base = readKnowledgeBase();
      }
      DroolsHintHolder holder = new DroolsHintHolder(evt.getProject());
      StatefulKnowledgeSession session = base.newStatefulKnowledgeSession();
      session.insert(holder);
      session.fireAllRules();
      evt.setHint(holder.getOutput());
   }

   public KnowledgeBase readKnowledgeBase()
   {
      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
      KnowledgeBaseHintBuildEvent evt = new KnowledgeBaseHintBuildEvent(kbuilder);
      event.fire(evt);
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
}
