package com.maddox.rts;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cmd
{
  public static boolean INFO_HARD = true;
  public static boolean INFO_SOFT = true;
  public static boolean ERR_HARD = true;
  public static boolean ERR_SOFT = true;
  public static final String PROP_NAME = "NAME";
  public static final String PROP_HELP = "HELP";
  public static final String PROP_PARAMS = "PARAMS";
  public static final String TEMP = "_$$$$$";
  public static final String PREVPREV = "_$$2";
  public static final String PREV = "_$$1";
  public static final String CURRENT = "_$$0";
  public static final String ARGS = "_$$";
  protected HashMap _properties;
  protected TreeMap param;
  protected int _levelAccess;

  public Object exec(CmdEnv paramCmdEnv, String paramString)
  {
    return null;
  }
  public Object exec(CmdEnv paramCmdEnv, Map paramMap) {
    return null;
  }

  public static boolean exist(Map paramMap, String paramString)
  {
    return paramMap.containsKey(paramString);
  }

  public static int nargs(Map paramMap, String paramString)
  {
    if (paramMap.containsKey(paramString)) {
      List localList = (List)paramMap.get(paramString);
      if (localList != null)
        return localList.size();
    }
    return 0;
  }

  public static String arg(Map paramMap, String paramString, int paramInt)
  {
    if (paramMap.containsKey(paramString)) {
      List localList = (List)paramMap.get(paramString);
      if ((localList != null) && 
        (paramInt >= 0) && (paramInt < localList.size()))
        return (String)localList.get(paramInt);
    }
    return null;
  }

  public static int arg(Map paramMap, String paramString, int paramInt1, int paramInt2)
  {
    String str = arg(paramMap, paramString, paramInt1);
    if (str == null)
      return paramInt2;
    try {
      return Integer.parseInt(str); } catch (Exception localException) {
    }
    return paramInt2;
  }

  public static int arg(Map paramMap, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = arg(paramMap, paramString, paramInt1, paramInt2);
    if (i < paramInt3) i = paramInt3;
    if (i > paramInt4) i = paramInt4;
    return i;
  }

  public static long arg(Map paramMap, String paramString, int paramInt, long paramLong)
  {
    String str = arg(paramMap, paramString, paramInt);
    if (str == null)
      return paramLong;
    try {
      return Long.parseLong(str); } catch (Exception localException) {
    }
    return paramLong;
  }

  public static long arg(Map paramMap, String paramString, int paramInt, long paramLong1, long paramLong2, long paramLong3)
  {
    long l = arg(paramMap, paramString, paramInt, paramLong1);
    if (l < paramLong2) l = paramLong2;
    if (l > paramLong3) l = paramLong3;
    return l;
  }

  public static float arg(Map paramMap, String paramString, int paramInt, float paramFloat)
  {
    String str = arg(paramMap, paramString, paramInt);
    if (str == null)
      return paramFloat;
    try {
      return Float.parseFloat(str); } catch (Exception localException) {
    }
    return paramFloat;
  }

  public static float arg(Map paramMap, String paramString, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f = arg(paramMap, paramString, paramInt, paramFloat1);
    if (f < paramFloat2) f = paramFloat2;
    if (f > paramFloat3) f = paramFloat3;
    return f;
  }

  public static double arg(Map paramMap, String paramString, int paramInt, double paramDouble)
  {
    String str = arg(paramMap, paramString, paramInt);
    if (str == null)
      return paramDouble;
    try {
      return Double.parseDouble(str); } catch (Exception localException) {
    }
    return paramDouble;
  }

  public static double arg(Map paramMap, String paramString, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = arg(paramMap, paramString, paramInt, paramDouble1);
    if (d < paramDouble2) d = paramDouble2;
    if (d > paramDouble3) d = paramDouble3;
    return d;
  }

  public static String args(Map paramMap, String paramString)
  {
    if (paramMap.containsKey(paramString)) {
      List localList = (List)paramMap.get(paramString);
      if ((localList != null) && (localList.size() > 0)) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < localList.size(); i++) {
          if (i != 0) localStringBuffer.append(' ');
          localStringBuffer.append((String)localList.get(i));
        }
        return localStringBuffer.toString();
      }
    }
    return null;
  }

  protected void ERR_HARD(String paramString)
  {
    if (ERR_HARD) {
      String str = (String)this._properties.get("NAME");
      System.err.println("ERROR " + (str != null ? str + ": " + paramString : paramString));
    }
  }

  protected void ERR_SOFT(String paramString)
  {
    if (ERR_SOFT) {
      String str = (String)this._properties.get("NAME");
      System.err.println(str != null ? str + ": " + paramString : paramString);
    }
  }

  protected void INFO_HARD(String paramString)
  {
    if (INFO_HARD)
      System.out.println(paramString);
  }

  protected void INFO_SOFT(String paramString)
  {
    if (INFO_SOFT)
      System.out.println(paramString);
  }

  public boolean isRawFormat()
  {
    return false;
  }

  public Map properties()
  {
    return this._properties;
  }

  public int levelAccess()
  {
    return this._levelAccess;
  }

  public Cmd()
  {
    this._properties = new HashMap();
    this.param = new TreeMap();
    this.param.put("_$$", null);
    this._properties.put("PARAMS", this.param);
    this._levelAccess = 0;
  }
}