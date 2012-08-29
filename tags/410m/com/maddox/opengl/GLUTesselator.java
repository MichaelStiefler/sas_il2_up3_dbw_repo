// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLUTesselator.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            gl

public class GLUTesselator
{

    public GLUTesselator()
    {
        pData = 0;
        New();
    }

    public native void Delete();

    public native void Vertex(double ad[], float af[]);

    public native void Vertex(double ad[], int ai[]);

    public native void Vertex(double ad[], double ad1[]);

    public native void BeginPolygon();

    public native void EndPolygon();

    public native void NextContour(int i);

    private native void New();

    private int pData;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
