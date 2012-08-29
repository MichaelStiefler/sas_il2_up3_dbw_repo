// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Sample.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.sound:
//            BaseObject, AudioStream, ControlInfo, SoundControl

public class Sample extends com.maddox.sound.BaseObject
{

    public Sample(java.lang.String s)
    {
        iniFlags = 0;
        envFlags = -1;
        engFlags = -1;
        usrFlags = -1;
        iniGain = 1.0F;
        iniPitch = 1.0F;
        iniSpl = 0.0F;
        autoPeriod = 0.0F;
        controls = null;
        name = s;
        handle = jniCreate("samples/" + s, 0);
        if(handle != 0)
            jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
    }

    public Sample(java.lang.String s, int i, int j)
    {
        iniFlags = 0;
        envFlags = -1;
        engFlags = -1;
        usrFlags = -1;
        iniGain = 1.0F;
        iniPitch = 1.0F;
        iniSpl = 0.0F;
        autoPeriod = 0.0F;
        controls = null;
        name = s;
        handle = jniCreate("samples/" + s, 0);
        envFlags = i;
        usrFlags = j;
        if(handle != 0)
            jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
    }

    public Sample(java.lang.String s, int i)
    {
        iniFlags = 0;
        envFlags = -1;
        engFlags = -1;
        usrFlags = -1;
        iniGain = 1.0F;
        iniPitch = 1.0F;
        iniSpl = 0.0F;
        autoPeriod = 0.0F;
        controls = null;
        name = s;
        handle = jniCreate("samples/" + s, i);
        if(handle != 0)
            jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
    }

    public Sample(java.lang.String s, java.lang.String s1)
    {
        iniFlags = 0;
        envFlags = -1;
        engFlags = -1;
        usrFlags = -1;
        iniGain = 1.0F;
        iniPitch = 1.0F;
        iniSpl = 0.0F;
        autoPeriod = 0.0F;
        controls = null;
        name = s1;
        if(s == null)
            handle = jniCreate(s1, 0);
        else
            handle = jniCreate(s + s1, 0);
        if(handle != 0)
            jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
    }

    public Sample(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        iniFlags = 0;
        envFlags = -1;
        engFlags = -1;
        usrFlags = -1;
        iniGain = 1.0F;
        iniPitch = 1.0F;
        iniSpl = 0.0F;
        autoPeriod = 0.0F;
        controls = null;
        try
        {
            name = sectfile.get(s, "file", (java.lang.String)null);
            if(name == null)
                throw new Exception();
            handle = jniCreate("samples/" + name, 0);
            load(sectfile, s);
        }
        catch(java.lang.Exception exception)
        {
            errmsg("cannot load sample: " + name);
            handle = 0;
        }
    }

    public boolean exists()
    {
        return handle != 0;
    }

    public boolean isInfinite()
    {
        return (iniFlags & 1) != 0;
    }

    public void setInfinite(boolean flag)
    {
        if(flag)
            iniFlags |= 1;
        else
            iniFlags &= -2;
        jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
    }

    public void addEnv(int i)
    {
        if(envFlags == -1)
            envFlags = 0;
        envFlags = envFlags | 1 << i;
    }

    public void remEnv(int i)
    {
        envFlags = envFlags & ~(1 << i);
    }

    public void addUsr(int i)
    {
        if(usrFlags == -1)
            usrFlags = 0;
        usrFlags = usrFlags | 1 << i;
    }

    public void remUsr(int i)
    {
        usrFlags = usrFlags & ~(1 << i);
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
        iniGain = sectfile.get(s, "gain", 1.0F);
        iniPitch = sectfile.get(s, "pitch", 1.0F);
        iniSpl = sectfile.get(s, "spl", 0.0F);
        autoPeriod = sectfile.get(s, "period", 0.0F);
        if(sectfile.get(s, "infinite", false))
            iniFlags = 1;
        java.lang.String s1 = sectfile.get(s, "env", (java.lang.String)null);
        if(s1 != null)
        {
            int i;
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s1); stringtokenizer.hasMoreTokens(); addEnv(i))
                i = java.lang.Integer.parseInt(stringtokenizer.nextToken());

        }
        s1 = sectfile.get(s, "usr", (java.lang.String)null);
        if(s1 != null)
        {
            int j;
            for(java.util.StringTokenizer stringtokenizer1 = new StringTokenizer(s1); stringtokenizer1.hasMoreTokens(); addUsr(j))
                j = java.lang.Integer.parseInt(stringtokenizer1.nextToken());

        }
        jniSet(handle, iniFlags, envFlags, engFlags, usrFlags, iniGain, iniPitch, iniSpl, autoPeriod);
        s1 = sectfile.get(s, "controls", (java.lang.String)null);
        if(s1 != null)
        {
            controls = new ArrayList();
            java.util.StringTokenizer stringtokenizer2 = new StringTokenizer(s1);
            do
            {
                if(!stringtokenizer2.hasMoreTokens())
                    break;
                java.lang.String s2 = stringtokenizer2.nextToken();
                com.maddox.sound.SoundControl soundcontrol = com.maddox.sound.ControlInfo.get(s2, jniGetControlList(handle));
                if(soundcontrol != null)
                    soundcontrol.load(sectfile, s + "." + s2);
            } while(true);
        }
    }

    public void save(com.maddox.rts.SectFile sectfile)
    {
    }

    public com.maddox.sound.AudioStream get()
    {
        return new AudioStream(jniCreateStream(handle));
    }

    protected native int jniCreate(java.lang.String s, int i);

    protected native void jniSet(int i, int j, int k, int l, int i1, float f, float f1, 
            float f2, float f3);

    protected native int jniGetControlList(int i);

    protected native int jniCreateStream(int i);

    protected java.lang.String name;
    protected int handle;
    protected int iniFlags;
    protected int envFlags;
    protected int engFlags;
    protected int usrFlags;
    protected float iniGain;
    protected float iniPitch;
    protected float iniSpl;
    protected float autoPeriod;
    protected java.util.ArrayList controls;
    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_STREAM = 1;
    public static final int TYPE_MUSIC = 2;
    public static final int LM_STANDARD = 0;
    public static final int LM_CACHE = 1;
}
