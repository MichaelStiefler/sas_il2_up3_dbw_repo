// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Turret.java

package com.maddox.il2.fm;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Loc;

public class Turret
{

    public Turret()
    {
        Lstart = new Loc();
        tu = new float[3];
        tuLim = new float[3];
        bIsAIControlled = true;
        bIsNetMirror = false;
        bIsOperable = true;
        timeNext = 0L;
        bIsShooting = false;
        tMode = 0;
    }

    public int indexA;
    public int indexB;
    public com.maddox.il2.engine.Loc Lstart;
    public com.maddox.il2.engine.Actor target;
    public float tu[];
    public float tuLim[];
    public boolean bIsAIControlled;
    public boolean bIsNetMirror;
    public boolean bIsOperable;
    public long timeNext;
    public boolean bIsShooting;
    public int tMode;
    public static final int TU_MO_SLEEP = 0;
    public static final int TU_MO_TRACKING = 1;
    public static final int TU_MO_FIRING_TRACKING = 2;
    public static final int TU_MO_FIRING_STOPPED = 3;
    public static final int TU_MO_PANIC = 4;
    public static final int TU_MO_STOPPED = 5;
}
