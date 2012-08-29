package com.maddox.netphone;

import java.io.PrintStream;

public class MixBase
{
  public static boolean enMsg = false;

  protected void error(String paramString)
  {
    if (enMsg) System.out.println("ERROR : " + paramString);
  }

  protected void print(String paramString)
  {
    if (enMsg) System.out.println(paramString);
  }
}