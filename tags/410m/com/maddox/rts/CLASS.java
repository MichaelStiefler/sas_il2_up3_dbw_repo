// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CLASS.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            RTS

public class CLASS extends java.lang.SecurityManager
{

    public CLASS()
    {
    }

    public static java.lang.Class THIS()
    {
        java.lang.Class aclass[] = THIS.getClassContext();
        if(aclass != null && aclass.length >= 2)
            return aclass[1];
        else
            return null;
    }

    public static final native int method(java.lang.Class class1, int i);

    public static final native java.lang.Object field(java.lang.Object obj, java.lang.Object obj1);

    public static final native int ser();

    static com.maddox.rts.CLASS THIS = new CLASS();

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
