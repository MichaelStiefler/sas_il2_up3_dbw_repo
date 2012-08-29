// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Emitter.java

package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

// Referenced classes of package com.maddox.sound:
//            BaseObject, Sample

public class Emitter extends com.maddox.sound.BaseObject
{

    public Emitter(java.lang.String s, int i)
    {
        name = null;
        handle = 0;
        samples = null;
        name = s;
        handle = com.maddox.sound.Emitter.jniCreate(i);
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
        speed = sectfile.get(s, "speed", 1.0F);
        sRand = sectfile.get(s, "srand", 0.0F);
        vRand = sectfile.get(s, "vrand", 0.0F);
        pRand = sectfile.get(s, "prand", 0.0F);
        spl = sectfile.get(s, "spl", 0.0F);
        int i = sectfile.get(s, "num", 0);
        if(i > 0)
        {
            samples = new ArrayList(i);
            for(int j = 0; j < i; j++)
            {
                java.lang.String s1 = sectfile.get(s, "sample" + j, (java.lang.String)null);
                if(s1 == null)
                    throw new Exception("No name: " + s);
                com.maddox.sound.Sample sample = new Sample(s1);
                sample.load(sectfile, s + "." + s1);
                samples.add(sample);
                com.maddox.sound.Emitter.jniAddSample(handle, sample.handle);
            }

        }
        com.maddox.sound.Emitter.jniSet(handle, speed, sRand, vRand, pRand, spl);
    }

    protected static native int jniCreate(int i);

    protected static native void jniSet(int i, float f, float f1, float f2, float f3, float f4);

    protected static native void jniAddSample(int i, int j);

    protected java.lang.String name;
    protected int handle;
    protected java.util.ArrayList samples;
    protected float speed;
    protected float sRand;
    protected float vRand;
    protected float pRand;
    protected float spl;
}
