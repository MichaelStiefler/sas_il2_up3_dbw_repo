// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BaseObject.java

package com.maddox.sound;

import com.maddox.rts.Cpu86ID;
import com.maddox.rts.RTS;
import java.io.PrintStream;

public class BaseObject
{

    protected BaseObject()
    {
    }

    public static boolean isEnabled()
    {
        return enabled;
    }

    public static void setEnabled(boolean flag)
    {
        enabled = flag;
    }

    private java.lang.String mkErrMsg(java.lang.String s)
    {
        return "ERROR [" + toString() + "] " + s;
    }

    protected static void printf(java.lang.String s)
    {
        java.lang.System.out.println(s);
    }

    protected void errmsg(java.lang.String s)
    {
        java.lang.System.out.println(mkErrMsg(s));
    }

    protected void raise(java.lang.String s)
        throws java.lang.Exception
    {
        java.lang.System.out.println(mkErrMsg(s));
        throw new Exception(s);
    }

    protected static native int libVersion(int i);

    protected static native void libInit(int i);

    protected static native void initRTS(int i);

    protected static void loadNative()
    {
        if(!loaded)
        {
            java.lang.String s = "standard";
            loaded = true;
            if(com.maddox.rts.Cpu86ID.isSSE())
            {
                java.lang.System.loadLibrary("mg_snd_sse");
                s = "P IV";
            } else
            {
                java.lang.System.loadLibrary("mg_snd");
            }
            int i = com.maddox.sound.BaseObject.libVersion(101);
            com.maddox.sound.BaseObject.libInit(com.maddox.rts.Cpu86ID.get());
            com.maddox.sound.BaseObject.initRTS(com.maddox.rts.RTS.interf());
            com.maddox.sound.BaseObject.printf("Sound: Native library (build " + i / 100 + "." + i % 100 + ", target - " + s + ") loaded.");
        }
    }

    private static final java.lang.String DLL_NAME = "mg_snd";
    protected static boolean enabled = true;
    protected static boolean loaded = false;

    static 
    {
        com.maddox.sound.BaseObject.loadNative();
    }
}
