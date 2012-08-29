// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GObj.java

package com.maddox.il2.engine;

import com.maddox.rts.RTS;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.engine:
//            GObjInstance, Config

public class GObj
    implements com.maddox.il2.engine.GObjInstance
{

    public GObj(int i)
    {
        cppObj = 0;
        cppObj = i;
    }

    public int cppObject()
    {
        return cppObj;
    }

    public int LinkCount()
    {
        return com.maddox.il2.engine.GObj.LinkCount(cppObj);
    }

    public com.maddox.il2.engine.GObj Clone()
    {
        return com.maddox.il2.engine.GObj.Clone(cppObj);
    }

    public boolean LinkLock()
    {
        return com.maddox.il2.engine.GObj.LinkLock(cppObj);
    }

    public void SetLinkLock(boolean flag)
    {
        com.maddox.il2.engine.GObj.SetLinkLock(cppObj, flag);
    }

    public java.lang.String getCppClassName()
    {
        return com.maddox.il2.engine.GObj.getCppClassName(cppObj);
    }

    protected void finalize()
    {
        if(cppObj != 0)
            com.maddox.il2.engine.GObj.Finalize(cppObj);
    }

    public static native void Finalize(int i);

    public static native int LinkCount(int i);

    public static native void Unlink(int i);

    public static native com.maddox.il2.engine.GObj Clone(int i);

    public static native boolean LinkLock(int i);

    public static native void SetLinkLock(int i, boolean flag);

    public static native java.lang.String getCppClassName(int i);

    public static native java.lang.Object getJavaObject(int i);

    public static native void DeleteCppObjects();

    public static native int version();

    private static native void setInterf(int i);

    public static final void loadNative()
    {
        if(!libLoaded)
        {
            java.lang.System.loadLibrary(com.maddox.il2.engine.Config.engineDllName());
            com.maddox.il2.engine.GObj.setInterf(com.maddox.rts.RTS.interf());
            libLoaded = true;
            int i = com.maddox.rts.RTS.version();
            java.lang.System.out.println("RTS Version " + (i >> 16) + "." + (i & 0xffff));
            i = com.maddox.il2.engine.GObj.version();
            java.lang.System.out.println("Core Version " + (i >> 16) + "." + (i & 0xffff));
        }
    }

    protected int cppObj;
    private static boolean libLoaded = false;

}
