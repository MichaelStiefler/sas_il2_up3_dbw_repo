// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ReverbFX.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            BaseObject, Reverb

public class ReverbFX extends com.maddox.sound.BaseObject
{

    public ReverbFX(com.maddox.sound.Reverb reverb)
    {
        roomMin = 0.0F;
        bound = 1000F;
        owner = reverb;
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = "hcontrol." + owner.getEngineName();
        roomMin = sectfile.get(s, "min", 0.0F);
        bound = sectfile.get(s, "bound", 1000F);
    }

    public void save(com.maddox.rts.SectFile sectfile)
    {
    }

    public void tick(float f)
    {
        if(f < bound)
            owner.set(100, roomMin + ((1.0F - roomMin) * (bound - f)) / bound);
        else
            owner.set(100, roomMin);
    }

    com.maddox.sound.Reverb owner;
    float roomMin;
    float bound;
}
