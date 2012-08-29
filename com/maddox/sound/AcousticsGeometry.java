// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AcousticsGeometry.java

package com.maddox.sound;


// Referenced classes of package com.maddox.sound:
//            BaseObject, AcousticsMaterial

public class AcousticsGeometry extends com.maddox.sound.BaseObject
{

    public AcousticsGeometry(java.lang.String s)
    {
        name = s;
        handle = com.maddox.sound.AcousticsGeometry.jniCreate();
    }

    public void material(com.maddox.sound.AcousticsMaterial acousticsmaterial)
    {
        com.maddox.sound.AcousticsGeometry.jniMaterial(handle, acousticsmaterial.handle);
    }

    public void vertex(float f, float f1, float f2)
    {
        com.maddox.sound.AcousticsGeometry.jniVertex(handle, f, f1, f2);
    }

    public void triangle(int i, int j, int k)
    {
        com.maddox.sound.AcousticsGeometry.jniTriangle(handle, i, j, k);
    }

    public void end()
    {
        com.maddox.sound.AcousticsGeometry.jniEnd(handle);
    }

    public void enable(boolean flag)
    {
        com.maddox.sound.AcousticsGeometry.jniEnable(handle, flag);
    }

    protected static native int jniCreate();

    protected static native void jniMaterial(int i, int j);

    protected static native void jniVertex(int i, float f, float f1, float f2);

    protected static native void jniTriangle(int i, int j, int k, int l);

    protected static native void jniEnd(int i);

    protected static native void jniEnable(int i, boolean flag);

    protected int handle;
    protected java.lang.String name;
}
