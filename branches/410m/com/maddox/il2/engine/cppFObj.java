// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   cppFObj.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            cppGObj

public class cppFObj extends com.maddox.il2.engine.cppGObj
{

    private static boolean InsertFObj(com.maddox.il2.engine.cppFObj cppfobj)
    {
        return false;
    }

    private static void RemoveFObj(com.maddox.il2.engine.cppFObj cppfobj)
    {
    }

    protected cppFObj()
    {
    }

    protected void virtual_FObj()
    {
    }

    protected boolean Load()
    {
        return false;
    }

    public java.lang.String Name()
    {
        return name;
    }

    public boolean Rename(java.lang.String s)
    {
        return false;
    }

    public long Hash()
    {
        return hash;
    }

    public com.maddox.il2.engine.cppGObj Clone()
    {
        return null;
    }

    public static com.maddox.il2.engine.cppFObj Get(java.lang.String s)
    {
        return null;
    }

    public static com.maddox.il2.engine.cppFObj Get(java.lang.String s, java.lang.String s1)
    {
        return null;
    }

    public boolean MakeFullName(java.lang.String s, java.lang.String s1, java.lang.String s2, int i)
    {
        return false;
    }

    public static com.maddox.il2.engine.cppFObj Get(long l)
    {
        return null;
    }

    public static boolean Exist(java.lang.String s)
    {
        return false;
    }

    public static boolean Exist(long l)
    {
        return false;
    }

    public static com.maddox.il2.engine.cppFObj NextFObj(com.maddox.il2.engine.cppFObj cppfobj)
    {
        return null;
    }

    public void DEFFCLASS()
    {
    }

    public void IMPFCLASS()
    {
    }

    public void IMPFCLASSGENERIC()
    {
    }

    public static final float GObjHASHloadFactor = 0.75F;
    public static final int GObjHASHinitialCapacity = 101;
    private long hash;
    private java.lang.String name;
    private com.maddox.il2.engine.cppFObj nextFObj;
}
