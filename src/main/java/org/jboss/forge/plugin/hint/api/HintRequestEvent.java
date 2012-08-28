/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.plugin.hint.api;

import org.jboss.forge.project.Project;

public class HintRequestEvent
{
   private Project project;
   private String hint;

   public HintRequestEvent(Project project)
   {
      super();
      this.project = project;
   }

   public String getHint()
   {
      return hint;
   }

   public Project getProject()
   {
      return project;
   }

   public void setHint(String hint)
   {
      this.hint = hint;
   }
}
