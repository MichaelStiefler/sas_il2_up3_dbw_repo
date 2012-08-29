// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CodePage.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            FObj, GObj

public class CodePage extends com.maddox.il2.engine.FObj
{

    public CodePage(int i)
    {
        super(i);
    }

    public int translate(int i)
    {
        return Translate(cppObj, i);
    }

    public int size()
    {
        return Size(cppObj);
    }

    public java.lang.String nameCP()
    {
        return NameCP(cppObj);
    }

    public static native com.maddox.il2.engine.CodePage get(java.lang.String s);

    public static native com.maddox.il2.engine.CodePage getApp();

    public static native void setApp(java.lang.String s);

    public static native com.maddox.il2.engine.CodePage getSystemAnsi();

    public static native com.maddox.il2.engine.CodePage getSystemOem();

    private native int Translate(int i, int j);

    private native int Size(int i);

    private native java.lang.String NameCP(int i);

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
