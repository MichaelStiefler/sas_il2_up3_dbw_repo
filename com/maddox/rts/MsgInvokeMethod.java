// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgInvokeMethod.java

package com.maddox.rts;

import java.lang.reflect.Method;

// Referenced classes of package com.maddox.rts:
//            Message

public class MsgInvokeMethod extends com.maddox.rts.Message
{

    public MsgInvokeMethod(java.lang.String s)
    {
        nameMethod = s;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        try
        {
            java.lang.Class class1 = obj.getClass();
            java.lang.reflect.Method method = class1.getMethod(nameMethod, null);
            method.invoke(obj, null);
        }
        catch(java.lang.NoSuchMethodException nosuchmethodexception)
        {
            return false;
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
        return true;
    }

    public java.lang.String nameMethod;
}
