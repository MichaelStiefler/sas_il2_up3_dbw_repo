// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLUQuadric.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            gl

public class GLUQuadric
{

    public GLUQuadric()
    {
        pData = 0;
        New();
    }

    public native void Delete();

    public native void DrawStyle(int i);

    public native void Normals(int i);

    public native void Orientation(int i);

    public native void Texture(boolean flag);

    public native void Sphere(double d, int i, int j);

    public native void Cylinder(double d, double d1, double d2, int i, 
            int j);

    public native void Disk(double d, double d1, int i, int j);

    public native void PartialDisk(double d, double d1, int i, int j, double d2, double d3);

    private native void New();

    private int pData;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
