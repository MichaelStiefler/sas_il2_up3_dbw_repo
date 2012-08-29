// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AudioStream.java

package com.maddox.sound;


// Referenced classes of package com.maddox.sound:
//            BaseObject

public class AudioStream extends com.maddox.sound.BaseObject
{

    protected AudioStream()
    {
        handle = 0;
    }

    protected AudioStream(int i)
    {
        handle = 0;
        handle = i;
    }

    public boolean isInitialized()
    {
        return handle != 0;
    }

    public void play()
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniPlay(handle, 0.0F, false);
    }

    public void start()
    {
        if(handle != 0 && !com.maddox.sound.AudioStream.jniIsPlaying(handle))
            com.maddox.sound.AudioStream.jniPlay(handle, 0.0F, false);
    }

    public void setPlay(boolean flag)
    {
        if(handle != 0)
            if(flag)
            {
                if(!com.maddox.sound.AudioStream.jniIsPlaying(handle))
                    com.maddox.sound.AudioStream.jniPlay(handle, 0.0F, false);
            } else
            {
                com.maddox.sound.AudioStream.jniCancel(handle);
            }
    }

    public void play(float f, boolean flag)
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniPlay(handle, f, flag);
    }

    public void stop(float f)
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniStop(handle, f);
    }

    public void stop()
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniStop(handle, 0.0F);
    }

    public void cancel()
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniCancel(handle);
    }

    public boolean isPlaying()
    {
        if(handle == 0)
            return false;
        else
            return com.maddox.sound.AudioStream.jniIsPlaying(handle);
    }

    public float getControl(int i)
    {
        return handle == 0 ? 0.0F : com.maddox.sound.AudioStream.jniGetControl(handle, i);
    }

    public void setControl(int i, float f)
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniSetControl(handle, i, f);
    }

    public void setVolume(float f)
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniSetControl(handle, 1, f);
    }

    public void setPitch(float f)
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniSetControl(handle, 2, f);
    }

    public void release()
    {
        if(handle != 0)
        {
            com.maddox.sound.AudioStream.jniRelease(handle);
            handle = 0;
        }
    }

    protected void finalize()
        throws java.lang.Throwable
    {
        if(handle != 0)
            com.maddox.sound.AudioStream.jniRelease(handle);
        super.finalize();
    }

    public java.lang.String getName()
    {
        return com.maddox.sound.AudioStream.jniGetName(handle);
    }

    protected static native void jniPlay(int i, float f, boolean flag);

    protected static native void jniStop(int i, float f);

    protected static native void jniCancel(int i);

    protected static native boolean jniIsPlaying(int i);

    protected static native float jniGetControl(int i, int j);

    protected static native void jniSetControl(int i, int j, float f);

    protected static native void jniRelease(int i);

    protected static native java.lang.String jniGetName(int i);

    protected int handle;
    public static final int SC_GAIN = 1;
    public static final int SC_PITCH = 2;
    public static final int SC_DIRFACTOR = 3;
}
