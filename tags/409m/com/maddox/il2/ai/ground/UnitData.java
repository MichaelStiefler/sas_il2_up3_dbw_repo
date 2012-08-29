// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitData.java

package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public class UnitData
{

    public UnitData()
    {
        leader = null;
        leaderDist = 0.0F;
        sideOffset = 0.0F;
        segmentIdx = -1;
    }

    public com.maddox.il2.engine.Actor leader;
    public float leaderDist;
    public float sideOffset;
    public int segmentIdx;
}
