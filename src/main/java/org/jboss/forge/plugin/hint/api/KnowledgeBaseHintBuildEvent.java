/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.plugin.hint.api;

import org.drools.builder.KnowledgeBuilder;

public class KnowledgeBaseHintBuildEvent
{
   private KnowledgeBuilder knowledgeBuilder;

   public KnowledgeBaseHintBuildEvent(KnowledgeBuilder knowledgeBuilder)
   {
      super();
      this.knowledgeBuilder = knowledgeBuilder;
   }

   public KnowledgeBuilder getKnowledgeBuilder()
   {
      return knowledgeBuilder;
   }

}
