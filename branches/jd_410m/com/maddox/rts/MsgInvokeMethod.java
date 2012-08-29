package com.maddox.rts;

import java.lang.reflect.Method;

public class MsgInvokeMethod extends Message
{
  public String nameMethod;

  public MsgInvokeMethod(String paramString)
  {
    this.nameMethod = paramString;
  }

  public boolean invokeListener(Object paramObject) {
    try {
      Class localClass = paramObject.getClass();
      Method localMethod = localClass.getMethod(this.nameMethod, null);
      localMethod.invoke(paramObject, null);
    } catch (NoSuchMethodException localNoSuchMethodException) {
      return false;
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return true;
  }
}