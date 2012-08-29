// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundFX.java

package com.maddox.sound;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.rts.Destroy;

// Referenced classes of package com.maddox.sound:
//            AudioStream, SoundPreset, SoundList, Acoustics, 
//            Sample, SamplePool

public class SoundFX extends com.maddox.sound.AudioStream
    implements com.maddox.rts.Destroy
{

    public SoundFX(com.maddox.sound.SoundPreset soundpreset)
    {
        firstInit(soundpreset);
    }

    public SoundFX(java.lang.String s)
    {
        firstInit(com.maddox.sound.SoundPreset.get(s));
    }

    protected void firstInit(com.maddox.sound.SoundPreset soundpreset)
    {
        list = null;
        prev = next = null;
        handle = soundpreset.createObject();
    }

    public com.maddox.sound.SoundFX next()
    {
        return next;
    }

    public void insert(com.maddox.sound.SoundList soundlist, boolean flag)
    {
        if(list == null)
        {
            list = soundlist;
            next = soundlist.first;
            if(soundlist.first != null)
                soundlist.first.prev = this;
            soundlist.first = this;
            if(flag)
                com.maddox.sound.SoundFX.jniSetAuto(handle);
        }
    }

    public void remove()
    {
        if(list != null)
        {
            if(list.first == this)
                list.first = next;
            if(prev != null)
                prev.next = next;
            if(next != null)
                next.prev = prev;
            list = null;
            prev = next = null;
        }
    }

    public void setAcoustics(com.maddox.sound.Acoustics acoustics)
    {
        if(handle != 0 && acoustics != null)
            com.maddox.sound.SoundFX.jniSetAcoustics(handle, acoustics.handle);
    }

    public void setParent(com.maddox.sound.SoundFX soundfx)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetParent(handle, soundfx == null ? 0 : soundfx.handle);
    }

    public int getCurDelay()
    {
        return handle != 0 ? com.maddox.sound.SoundFX.jniCurDelay(handle) : 0;
    }

    public void add(com.maddox.sound.AudioStream audiostream)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniAdd(handle, audiostream.handle);
    }

    public void remove(com.maddox.sound.AudioStream audiostream)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniRemove(handle, audiostream.handle);
    }

    public void clear()
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniClear(handle);
    }

    public void play(com.maddox.sound.Sample sample)
    {
        if(sample != null && handle != 0)
            com.maddox.sound.SoundFX.jniAddSample(handle, sample.handle, 0, 1.0F, 1.0F);
    }

    public void play(com.maddox.sound.Sample sample, int i, float f, float f1)
    {
        if(sample != null && handle != 0)
            com.maddox.sound.SoundFX.jniAddSample(handle, sample.handle, i, f, f1);
    }

    public void play(com.maddox.sound.SamplePool samplepool)
    {
        if(samplepool != null && handle != 0)
            com.maddox.sound.SoundFX.jniPlayPool(handle, samplepool.handle, 0, 1.0F, 1.0F);
    }

    public void play(com.maddox.sound.SamplePool samplepool, int i, float f, float f1)
    {
        if(samplepool != null && handle != 0)
            com.maddox.sound.SoundFX.jniPlayPool(handle, samplepool.handle, i, f, f1);
    }

    public void play(com.maddox.JGP.Point3d point3d)
    {
        if(handle != 0)
        {
            com.maddox.sound.SoundFX.jniSetPosition(handle, point3d.x, point3d.y, point3d.z);
            com.maddox.sound.AudioStream.jniPlay(handle, 0.0F, false);
        }
    }

    public int getCaps()
    {
        return handle == 0 ? -1 : com.maddox.sound.SoundFX.jniGetCaps(handle);
    }

    public void setPosition(double d, double d1, double d2)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetPosition(handle, d, d1, d2);
    }

    public void setVelocity(float f, float f1, float f2)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetVelocity(handle, f, f1, f2);
    }

    public void setOrientation(float f, float f1, float f2)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetOrientation(handle, f, f1, f2);
    }

    public void setTop(float f, float f1, float f2)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetTop(handle, f, f1, f2);
    }

    public void setPosition(com.maddox.JGP.Point3d point3d)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetPosition(handle, point3d.x, point3d.y, point3d.z);
    }

    public void setPosition(com.maddox.JGP.Point3f point3f)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetPosition(handle, point3f.x, point3f.y, point3f.z);
    }

    public void setUsrFlag(int i)
    {
        if(handle != 0)
            com.maddox.sound.SoundFX.jniSetUsrFlag(handle, i);
    }

    public boolean isDestroyed()
    {
        return handle == 0;
    }

    public void destroy()
    {
        if(handle != 0)
        {
            remove();
            com.maddox.sound.AudioStream.jniRelease(handle);
            handle = 0;
        }
    }

    protected static native int jniGetCaps(int i);

    protected static native void jniSetAuto(int i);

    protected static native void jniSetAcoustics(int i, int j);

    protected static native void jniSetParent(int i, int j);

    protected static native void jniSetUsrFlag(int i, int j);

    protected static native int jniAdd(int i, int j);

    protected static native int jniRemove(int i, int j);

    protected static native int jniClear(int i);

    protected static native void jniAddSample(int i, int j, int k, float f, float f1);

    protected static native void jniPlayPool(int i, int j, int k, float f, float f1);

    protected static native int jniCurDelay(int i);

    protected static native void jniSetPosition(int i, double d, double d1, double d2);

    protected static native void jniSetVelocity(int i, float f, float f1, float f2);

    protected static native void jniSetOrientation(int i, float f, float f1, float f2);

    protected static native void jniSetTop(int i, float f, float f1, float f2);

    public static final int CAPS_POSITION = 1;
    public static final int CAPS_VELOCITY = 2;
    public static final int CAPS_OR_FRONT = 4;
    public static final int CAPS_OR_TOP = 8;
    public static final int CAPS_UPDATE = 16;
    public static final int CAPS_MASK_3D = 7;
    public static final int CAPS_RELOBJ = 256;
    public static final int CAPS_RELIST = 512;
    public static final int CAPS_MUSIC = 4096;
    public static final int FLAG_INFINITE = 1;
    public static final int FLAG_NUMADJ = 4096;
    public static final int FLAG_UNDETUNE = 8192;
    public static final int FLAG_PERMANENT = 16384;
    public static final int FLAG_SEQ = 32768;
    public static final int FLAG_PMAX = 0x10000;
    protected com.maddox.sound.SoundList list;
    protected com.maddox.sound.SoundFX prev;
    protected com.maddox.sound.SoundFX next;
}
