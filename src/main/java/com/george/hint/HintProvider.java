package com.george.hint;

import org.jboss.forge.project.Project;

public interface HintProvider
{
   public abstract String computeHint(Project project);
}
