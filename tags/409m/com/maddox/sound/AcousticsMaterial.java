// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AcousticsMaterial.java

package com.maddox.sound;


// Referenced classes of package com.maddox.sound:
//            BaseObject

public class AcousticsMaterial extends com.maddox.sound.BaseObject
{

    public AcousticsMaterial(java.lang.String s)
    {
        name = s;
        handle = com.maddox.sound.AcousticsMaterial.jniCreate();
    }

    public void setTransmittence(float f, float f1)
    {
        com.maddox.sound.AcousticsMaterial.jniTransmittence(handle, f, f1);
    }

    public void setReflectance(float f, float f1)
    {
        com.maddox.sound.AcousticsMaterial.jniReflectance(handle, f, f1);
    }

    protected static native int jniCreate();

    protected static native void jniTransmittence(int i, float f, float f1);

    protected static native void jniReflectance(int i, float f, float f1);

    protected java.lang.String name;
    protected int handle;
}
