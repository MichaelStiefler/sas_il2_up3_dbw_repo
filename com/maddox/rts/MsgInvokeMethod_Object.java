// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgInvokeMethod_Object.java

package com.maddox.rts;

import java.lang.reflect.Method;

// Referenced classes of package com.maddox.rts:
//            Message

public class MsgInvokeMethod_Object extends com.maddox.rts.Message
{

    public void clean()
    {
        super.clean();
        obj = null;
    }

    public MsgInvokeMethod_Object(java.lang.String s, java.lang.Object obj1)
    {
        nameMethod = s;
        obj = obj1;
    }

    public boolean invokeListener(java.lang.Object obj1)
    {
        try
        {
            java.lang.Class class1 = obj1.getClass();
            java.lang.reflect.Method method = class1.getMethod(nameMethod, _paramClass);
            method.invoke(obj1, new java.lang.Object[] {
                obj
            });
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public java.lang.String nameMethod;
    public java.lang.Object obj;
    private static java.lang.Class _paramClass[];

    static 
    {
        _paramClass = (new java.lang.Class[] {
            java.lang.Object.class
        });
    }
}
