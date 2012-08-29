// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunN37ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import java.security.SecureRandom;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunNS37s, MGunAircraftGeneric

public class MGunN37ki extends com.maddox.il2.objects.weapons.MGunNS37s
{

    public MGunN37ki()
    {
    }

    private void initRandom()
    {
        if(theRangeRandom != null)
        {
            return;
        } else
        {
            long lTime = java.lang.System.currentTimeMillis();
            java.security.SecureRandom secRandom = new SecureRandom();
            secRandom.setSeed(lTime);
            long lSeed1 = secRandom.nextInt();
            long lSeed2 = secRandom.nextInt();
            long lSeed = (lSeed1 << 32) + lSeed2;
            theRangeRandom = new RangeRandom(lSeed);
            return;
        }
    }

    private int nextRandomInt(int iMin, int iMax)
    {
        initRandom();
        return theRangeRandom.nextInt(iMin, iMax);
    }

    public void init()
    {
        super.init();
        int iRandBullet = nextRandomInt(0, 0x3fffffff) % super.prop.bullet.length + 1;
        for(int i = 0; i < iRandBullet; i++)
            nextIndexBulletType();

    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.shotFreq = 6.66F;
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 5.183333F;
        gunproperties.shotFreqDeviation = 0.03F;
        return gunproperties;
    }

    private static com.maddox.il2.ai.RangeRandom theRangeRandom;
}
