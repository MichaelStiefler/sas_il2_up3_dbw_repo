package com.maddox.rts;

public class CLASS extends SecurityManager
{
  static CLASS THIS = new CLASS();

  public static Class THIS() { Class[] arrayOfClass = THIS.getClassContext();
    if ((arrayOfClass != null) && (arrayOfClass.length >= 2)) {
      return arrayOfClass[1];
    }
    return null; } 
  public static final native int method(Class paramClass, int paramInt);

  public static final native Object field(Object paramObject1, Object paramObject2);

  public static final native int ser();

  static { RTS.loadNative();
  }
}