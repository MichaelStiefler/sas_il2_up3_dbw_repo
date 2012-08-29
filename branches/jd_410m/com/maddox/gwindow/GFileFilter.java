package com.maddox.gwindow;

import java.io.File;

public abstract interface GFileFilter
{
  public abstract boolean accept(File paramFile);

  public abstract String getDescription();
}