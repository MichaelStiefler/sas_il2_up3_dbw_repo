package com.maddox.rts;

import java.lang.reflect.Method;

public class MsgInvokeMethod_Object extends Message
{
  public String nameMethod;
  public Object obj;
  private static Class[] _paramClass = { Object.class };

  public void clean()
  {
    super.clean();
    this.obj = null;
  }

  public MsgInvokeMethod_Object(String paramString, Object paramObject)
  {
    this.nameMethod = paramString;
    this.obj = paramObject;
  }

  public boolean invokeListener(Object paramObject) {
    try {
      Class localClass = paramObject.getClass();
      Method localMethod = localClass.getMethod(this.nameMethod, _paramClass);
      localMethod.invoke(paramObject, new Object[] { this.obj });
    } catch (NoSuchMethodException localNoSuchMethodException) {
      return false;
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return true;
  }
}