/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.george.hint;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.forge.parser.java.util.Strings;
import org.jboss.forge.project.Project;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.DefaultCommand;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.project.ProjectScoped;

/**
 * A Hint plugin to aid developers while running Forge
 *
 * @author George Gastaldi <ggastald@redhat.com>
 */
@Alias("hint")
@ApplicationScoped
public class HintPlugin implements Plugin
{

   private static final String HINTS_MESSAGES = "hint.messages.hint_messages";

//   private static final String HINTS_ENABLED_FLAG = "HINTS.ENABLED";

   /**
    * We need to create this object after each executed command
    */
   @Inject
   private Instance<Project> projectInstance;

   @Inject
   private HintProvider hintProvider;

   @Inject
   private Shell shell;

   @Inject
   private BeanManager beanManager;


   /*
   @DefaultCommand
   public void displayHintStatus()
   {
      boolean enabled = isEnabled();
      shell.println("Hints are "
               + (enabled ? "enabled. To disable hints, type 'hint off'" : "disabled. To enable hints, type 'hint on'"));
   }

   @Command("on")
   public void enableHints()
   {
      setEnabled(true);
      displayHintStatus();
   }

   @Command("off")
   public void disableHints()
   {
      setEnabled(false);
      displayHintStatus();
   }

   private boolean isEnabled()
   {
      Object hintsEnabled = shell.getEnvironment().getProperty(HINTS_ENABLED_FLAG);
      return hintsEnabled == null ? false : Boolean.parseBoolean(hintsEnabled.toString());
   }

   private void setEnabled(boolean enabled)
   {
      shell.getEnvironment().setProperty(HINTS_ENABLED_FLAG, enabled);
   }

   public void observesAfterExecution(@Observes CommandExecuted event)
   {
      showProjectHint();
   }
   */

   @DefaultCommand
   public void showProjectHint()
   {
      // If this plugin is disabled, do nothing
//      if (!isEnabled())
//      {
//         return;
//      }
      Project project;
      // Check if ProjectScoped is active
      try
      {
         if (beanManager.getContext(ProjectScoped.class).isActive())
         {
            project = projectInstance.get();
         }
         else
         {
            project = null;
         }
      }
      catch (ContextNotActiveException cnae)
      {
         project = null;
      }
      String response = hintProvider.computeHint(project);
      // Translate the response, if available
      ResourceBundle bundle = ResourceBundle.getBundle(HINTS_MESSAGES, Locale.getDefault(),
               getClass()
                        .getClassLoader());
      String translatedReponse = bundle.containsKey(response) ? bundle.getString(response) : response;
      if (!Strings.isNullOrEmpty(translatedReponse))
      {
         shell.println();
         shell.println(translatedReponse);
      }
   }
}
