/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.plugin.hint.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.event.Observes;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.jboss.forge.plugin.hint.api.HintCompleteEvent;
import org.jboss.forge.plugin.hint.api.KnowledgeBaseHintBuildEvent;

public class DefaultHintObserver
{
   private static final String HINTS_MESSAGES = "hint.messages.hint_messages";

   public void placeDefaultValues(@Observes KnowledgeBaseHintBuildEvent event)
   {
      KnowledgeBuilder knowledgeBuilder = event.getKnowledgeBuilder();
      knowledgeBuilder.add(ResourceFactory.newClassPathResource("hint/drools/builtin_hints.drl"), ResourceType.DRL);
   }

   public void translateValues(@Observes HintCompleteEvent completeEvent)
   {
      // Translate the response, if available
      ResourceBundle bundle = ResourceBundle.getBundle(HINTS_MESSAGES, Locale.getDefault(),
               getClass()
                        .getClassLoader());
      String hintKey = completeEvent.getHintKey();
      if (bundle.containsKey(hintKey))
      {
         completeEvent.setDescription(bundle.getString(hintKey));
      }

   }
}
