// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLUNurbs.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            gl

public class GLUNurbs
{

    public GLUNurbs()
    {
        pData = 0;
        New();
    }

    public native void Delete();

    public native void Property(int i, float f);

    public native void GetProperty(int i, float af[]);

    public native void LoadSamplingMatrices(float af[], float af1[], int ai[]);

    public native void BeginSurface();

    public native void EndSurface();

    public native void Surface(int i, float af[], int j, float af1[], int k, int l, float af2[], 
            int i1, int j1, int k1);

    public native void BeginCurve();

    public native void EndCurve();

    public native void Curve(int i, float af[], int j, float af1[], int k, int l);

    public native void BeginTrim();

    public native void EndTrim();

    public native void PwlCurve(int i, float af[], int j, int k);

    private native void New();

    private int pData;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
