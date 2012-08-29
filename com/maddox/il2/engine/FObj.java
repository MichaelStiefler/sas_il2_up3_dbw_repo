// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FObj.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            GObj, FObjInstance

public class FObj extends com.maddox.il2.engine.GObj
    implements com.maddox.il2.engine.FObjInstance
{

    public FObj(int i)
    {
        super(i);
    }

    public java.lang.String Name()
    {
        return com.maddox.il2.engine.FObj.Name(cppObj);
    }

    public void Rename(java.lang.String s)
    {
        com.maddox.il2.engine.FObj.Rename(cppObj, s);
    }

    public long Hash()
    {
        return com.maddox.il2.engine.FObj.Hash(cppObj);
    }

    public static native java.lang.String Name(int i);

    public static native void Rename(int i, java.lang.String s);

    public static native long Hash(int i);

    public static java.lang.Object Get(java.lang.String s)
    {
        int i = com.maddox.il2.engine.FObj.GetFObj(s);
        if(i != 0)
        {
            java.lang.Object obj = com.maddox.il2.engine.GObj.getJavaObject(i);
            com.maddox.il2.engine.GObj.Unlink(i);
            return obj;
        } else
        {
            return null;
        }
    }

    public static java.lang.Object Get(java.lang.String s, java.lang.String s1)
    {
        int i = com.maddox.il2.engine.FObj.GetFObj(s, s1);
        if(i != 0)
        {
            java.lang.Object obj = com.maddox.il2.engine.GObj.getJavaObject(i);
            com.maddox.il2.engine.GObj.Unlink(i);
            return obj;
        } else
        {
            return null;
        }
    }

    public static java.lang.Object Get(long l)
    {
        int i = com.maddox.il2.engine.FObj.GetFObj(l);
        if(i != 0)
        {
            java.lang.Object obj = com.maddox.il2.engine.GObj.getJavaObject(i);
            com.maddox.il2.engine.GObj.Unlink(i);
            return obj;
        } else
        {
            return null;
        }
    }

    public static native int GetFObj(java.lang.String s);

    public static native int GetFObj(java.lang.String s, java.lang.String s1);

    public static native int GetFObj(long l);

    public static native boolean Exist(java.lang.String s);

    public static native boolean Exist(long l);

    public static native int NextFObj(int i);

    public boolean ReLoad()
    {
        return com.maddox.il2.engine.FObj.ReLoad(cppObj) != 0;
    }

    public static native int ReLoad(int i);

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
