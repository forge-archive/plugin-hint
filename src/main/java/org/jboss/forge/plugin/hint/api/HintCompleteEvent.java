/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.plugin.hint.api;

/**
 * Fired when a hint has been already calculated and needs post-processing actions, like translation
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public class HintCompleteEvent
{
   private String hintKey;

   private String description;

   public HintCompleteEvent(String hintKey)
   {
      super();
      this.hintKey = hintKey;
      // Initializing description with hintKey
      this.description = hintKey;
   }

   public String getHintKey()
   {
      return hintKey;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getDescription()
   {
      return description;
   }
}
