// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GunProperties.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            BulletProperties

public class GunProperties
{

    public GunProperties()
    {
        weaponType = 0;
        bCannon = false;
        bUseHookAsRel = false;
        shotFreqDeviation = 0.0F;
        bEnablePause = false;
    }

    public void calculateSteps()
    {
        if(emitTime <= com.maddox.rts.Time.tickConstLenFs())
            emitTime = com.maddox.rts.Time.tickConstLenFs();
        if(aimMinDist < 0.0F)
            aimMinDist = 10F;
        if(aimMaxDist < 0.0F)
            aimMaxDist = 1000F;
        if(aimMinDist >= aimMaxDist)
        {
            aimMinDist = 10F;
            aimMinDist = 1000F;
        }
        if(bulletsCluster < 1)
            bulletsCluster = 1;
        bulletsCluster = 1;
        traceFreq /= bulletsCluster;
        if(traceFreq <= 0)
            traceFreq = 1;
    }

    public int weaponType;
    public boolean bCannon;
    public boolean bUseHookAsRel;
    public java.lang.String fireMesh;
    public java.lang.String fire;
    public java.lang.String sprite;
    public java.lang.String fireMeshDay;
    public java.lang.String fireDay;
    public java.lang.String spriteDay;
    public java.lang.String smoke;
    public java.lang.String shells;
    public java.lang.String sound;
    public com.maddox.JGP.Color3f emitColor;
    public float emitI;
    public float emitR;
    public float emitTime;
    public float aimMinDist;
    public float aimMaxDist;
    public float maxDeltaAngle;
    public float shotFreq;
    public float shotFreqDeviation;
    public int traceFreq;
    public boolean bEnablePause;
    public int bullets;
    public int bulletsCluster;
    public com.maddox.il2.engine.BulletProperties bullet[];
}
