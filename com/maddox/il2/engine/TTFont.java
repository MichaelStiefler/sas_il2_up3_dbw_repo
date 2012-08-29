// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TTFont.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            FObj, GObj, TTFontTransform

public class TTFont extends com.maddox.il2.engine.FObj
{

    public void output(int i, float f, float f1, float f2, java.lang.String s)
    {
        Output(cppObj, i, f, f1, f2, s, 0, s.length());
    }

    public void output(int i, float f, float f1, float f2, java.lang.String s, int j, int k)
    {
        Output(cppObj, i, f, f1, f2, s, j, k);
    }

    public void output(int i, float f, float f1, float f2, char ac[], int j, int k)
    {
        Output(cppObj, i, f, f1, f2, ac, j, k);
    }

    public void outputClip(int i, float f, float f1, float f2, java.lang.String s, int j, int k, 
            float f3, float f4, float f5, float f6)
    {
        OutputClip(cppObj, i, f, f1, f2, s, j, k, f3, f4, f5, f6);
    }

    public void outputClip(int i, float f, float f1, float f2, char ac[], int j, int k, 
            float f3, float f4, float f5, float f6)
    {
        OutputClipArr(cppObj, i, f, f1, f2, ac, j, k, f3, f4, f5, f6);
    }

    public void transform(com.maddox.il2.engine.TTFontTransform ttfonttransform, int i, java.lang.String s)
    {
        Transform(cppObj, ttfonttransform, i, s, 0, s.length());
    }

    public void transform(com.maddox.il2.engine.TTFontTransform ttfonttransform, int i, java.lang.String s, int j, int k)
    {
        Transform(cppObj, ttfonttransform, i, s, j, k);
    }

    public void transform(com.maddox.il2.engine.TTFontTransform ttfonttransform, int i, char ac[], int j, int k)
    {
        Transform(cppObj, ttfonttransform, i, ac, j, k);
    }

    public float width(java.lang.String s)
    {
        return Width(cppObj, s, 0, s.length());
    }

    public float width(java.lang.String s, int i, int j)
    {
        return Width(cppObj, s, i, j);
    }

    public float width(char ac[], int i, int j)
    {
        return Width(cppObj, ac, i, j);
    }

    public int len(java.lang.String s, float f, boolean flag)
    {
        return Len(cppObj, s, 0, s.length(), f, flag ? 1 : 0);
    }

    public int len(java.lang.String s, int i, int j, float f, boolean flag)
    {
        return Len(cppObj, s, i, j, f, flag ? 1 : 0);
    }

    public int len(char ac[], int i, int j, float f, boolean flag)
    {
        return Len(cppObj, ac, i, j, f, flag ? 1 : 0);
    }

    public int lenEnd(java.lang.String s, float f, boolean flag)
    {
        return LenEnd(cppObj, s, 0, s.length(), f, flag ? 1 : 0);
    }

    public int lenEnd(java.lang.String s, int i, int j, float f, boolean flag)
    {
        return LenEnd(cppObj, s, i, j, f, flag ? 1 : 0);
    }

    public int lenEnd(char ac[], int i, int j, float f, boolean flag)
    {
        return LenEnd(cppObj, ac, i, j, f, flag ? 1 : 0);
    }

    public int height()
    {
        return Height(cppObj);
    }

    public int descender()
    {
        return Descender(cppObj);
    }

    public static native com.maddox.il2.engine.TTFont get(java.lang.String s);

    public static native void setContextWidth(int i);

    public void reloadOnResize()
    {
        ReloadOnResize(cppObj);
    }

    public static native void reloadAllOnResize();

    public TTFont(int i)
    {
        super(i);
    }

    private native void Output(int i, int j, float f, float f1, float f2, java.lang.String s, int k, 
            int l);

    private native void Output(int i, int j, float f, float f1, float f2, char ac[], int k, 
            int l);

    private native void OutputClip(int i, int j, float f, float f1, float f2, java.lang.String s, int k, 
            int l, float f3, float f4, float f5, float f6);

    private native void OutputClipArr(int i, int j, float f, float f1, float f2, char ac[], int k, 
            int l, float f3, float f4, float f5, float f6);

    private native void Transform(int i, java.lang.Object obj, int j, java.lang.String s, int k, int l);

    private native void Transform(int i, java.lang.Object obj, int j, char ac[], int k, int l);

    private native float Width(int i, java.lang.String s, int j, int k);

    private native float Width(int i, char ac[], int j, int k);

    private native int Len(int i, java.lang.String s, int j, int k, float f, int l);

    private native int Len(int i, char ac[], int j, int k, float f, int l);

    private native int LenEnd(int i, java.lang.String s, int j, int k, float f, int l);

    private native int LenEnd(int i, char ac[], int j, int k, float f, int l);

    private native int Height(int i);

    private native int Descender(int i);

    private native void ReloadOnResize(int i);

    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;
    public static final int FIX_SMALL = 3;
    public static com.maddox.il2.engine.TTFont font[] = new com.maddox.il2.engine.TTFont[4];

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
