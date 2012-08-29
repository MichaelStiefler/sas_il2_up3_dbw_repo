// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   TypeThreatDetector.java

package com.maddox.il2.objects.air;


public interface TypeThreatDetector
{

    public abstract void setCommonThreatActive();

    public abstract void setRadarLockThreatActive();

    public abstract void setMissileLaunchThreatActive();
}
