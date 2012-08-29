// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LDRres.java

package com.maddox.rts;

import java.io.InputStream;

// Referenced classes of package com.maddox.rts:
//            SFSInputStream, LDR

public final class LDRres extends java.lang.ClassLoader
{

    public LDRres()
    {
    }

    public static final java.lang.ClassLoader loader()
    {
        if(rldr != null)
            return rldr;
        rldr = com.maddox.rts.LDR.resLoader();
        if(rldr != null)
        {
            return rldr;
        } else
        {
            rldr = new LDRres();
            return rldr;
        }
    }

    public java.io.InputStream getResourceAsStream(java.lang.String s)
    {
        return new SFSInputStream(s);
        java.lang.Exception exception;
        exception;
        return super.getResourceAsStream(s);
    }

    private static java.lang.ClassLoader rldr = null;

}
