// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LDR.java

package com.maddox.rts;

import java.io.InputStream;

// Referenced classes of package com.maddox.rts:
//            LDRCallBack

public final class LDR extends java.lang.ClassLoader
{

    protected static final java.lang.ClassLoader resLoader()
    {
        return rldr;
    }

    protected synchronized java.lang.Class loadClass(java.lang.String s, boolean flag)
        throws java.lang.ClassNotFoundException
    {
        java.lang.Class class1 = findLoadedClass(s);
        if(class1 == null)
            if(ldr != null && s.startsWith("com.maddox"))
            {
                byte abyte0[] = ldr.load(s);
                if(abyte0 != null)
                    class1 = defineClass(null, abyte0, 0, abyte0.length);
                if(class1 == null)
                    throw new ClassNotFoundException(s);
            } else
            {
                class1 = super.loadClass(s, false);
            }
        if(flag)
            resolveClass(class1);
        return class1;
    }

    public java.io.InputStream getResourceAsStream(java.lang.String s)
    {
        java.io.InputStream inputstream = null;
        if(ldr != null)
            inputstream = ldr.open(s);
        if(inputstream == null)
            inputstream = super.getResourceAsStream(s);
        return inputstream;
    }

    protected java.lang.String findLibrary(java.lang.String s)
    {
        if("rts".equals(s))
            return rtsLib;
        else
            return null;
    }

    private void link(java.lang.String s)
    {
        rtsLib = s;
    }

    private void set(java.lang.Object obj)
    {
        ldr = (com.maddox.rts.LDRCallBack)obj;
        try
        {
            java.lang.Class.forName("com.maddox.il2.game.Main", true, this);
        }
        catch(java.lang.Exception exception) { }
    }

    private LDR()
    {
        rtsLib = null;
        rldr = this;
    }

    private java.lang.String rtsLib;
    private static java.lang.ClassLoader rldr = null;
    private static com.maddox.rts.LDRCallBack ldr = null;

}
