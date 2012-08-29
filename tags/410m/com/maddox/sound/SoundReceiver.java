// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundReceiver.java

package com.maddox.sound;

import java.io.PrintStream;

// Referenced classes of package com.maddox.sound:
//            BaseObject, RadioChannel, AudioDevice

public class SoundReceiver extends com.maddox.sound.BaseObject
{

    protected static native int jniInit(int i);

    protected static native void jniDone(int i);

    protected static native void jniPut(int i, byte abyte0[], int j);

    protected static native void jniReset(int i);

    public SoundReceiver(int i)
    {
        handle = 0;
        if(enabled)
            handle = com.maddox.sound.SoundReceiver.jniInit(i);
    }

    public void destroy()
    {
        if(handle != 0)
        {
            com.maddox.sound.SoundReceiver.jniDone(handle);
            handle = 0;
            if(com.maddox.sound.RadioChannel.tstMode > 0)
                java.lang.System.out.println("Radio : receiver destroyed.");
        }
    }

    protected void finalize()
    {
        destroy();
    }

    public void put(byte abyte0[], int i)
    {
        if(handle != 0)
            com.maddox.sound.SoundReceiver.jniPut(handle, abyte0, i);
    }

    public void reset()
    {
        if(handle != 0)
            com.maddox.sound.SoundReceiver.jniReset(handle);
    }

    protected int handle;

    static 
    {
        com.maddox.sound.AudioDevice.loadNative();
    }
}
