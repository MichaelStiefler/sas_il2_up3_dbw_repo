// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SfxZip.java

package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.Engine;
import com.maddox.sound.SoundFX;

public class SfxZip extends com.maddox.sound.SoundFX
{

    public SfxZip(com.maddox.JGP.Point3d point3d)
    {
        super("objects.zip");
        com.maddox.il2.engine.ActorSoundListener actorsoundlistener = com.maddox.il2.engine.Engine.soundListener();
        if(actorsoundlistener != null)
        {
            double d = actorsoundlistener.getRelRhoSqr(point3d);
            double d1 = 1600D;
            double d2 = 2400D;
            if(d < d1 * d1)
                setUsrFlag(0);
            else
            if(d < d2 * d2)
                setUsrFlag(1);
            else
                setUsrFlag(2);
        }
        com.maddox.il2.objects.sounds.SfxZip.jniSetPosition(handle, point3d.x, point3d.y, point3d.z);
        play();
    }
}
