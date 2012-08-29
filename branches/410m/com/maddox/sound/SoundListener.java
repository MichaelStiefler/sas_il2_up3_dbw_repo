// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundListener.java

package com.maddox.sound;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;

// Referenced classes of package com.maddox.sound:
//            Acoustics

public class SoundListener
{

    public SoundListener()
    {
    }

    public static void setAcoustics(com.maddox.sound.Acoustics acoustics)
    {
        if(acc != acoustics)
        {
            acc = acoustics;
            com.maddox.sound.SoundListener.jniSetAcoustics(acc.handle);
        }
    }

    protected static void flush()
    {
        if(acc != null)
        {
            double d = pos.z - com.maddox.il2.engine.Engine.land().HQ(pos.x, pos.y);
            acc.flush((float)d);
        }
    }

    public static void setPosition(double d, double d1, double d2)
    {
        pos.set(d, d1, d2);
        com.maddox.sound.SoundListener.jniSetPosition(d, d1, d2);
    }

    public static void setVelocity(float f, float f1, float f2)
    {
        com.maddox.sound.SoundListener.jniSetVelocity(f, f1, f2);
    }

    public static void setOrientation(float f, float f1, float f2, float f3, float f4, float f5)
    {
        com.maddox.sound.SoundListener.jniSetOrientation(f, f1, f2, f3, f4, f5);
    }

    protected static native void jniSetPosition(double d, double d1, double d2);

    protected static native void jniSetVelocity(float f, float f1, float f2);

    protected static native void jniSetOrientation(float f, float f1, float f2, float f3, float f4, float f5);

    protected static native void jniSetAcoustics(int i);

    protected static com.maddox.sound.Acoustics acc = null;
    protected static com.maddox.JGP.Point3d pos = new Point3d();

}
