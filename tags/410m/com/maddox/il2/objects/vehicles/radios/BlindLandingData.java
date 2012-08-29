// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BlindLandingData.java

package com.maddox.il2.objects.vehicles.radios;


public class BlindLandingData
{

    public BlindLandingData()
    {
        isOnOuterMarker = false;
        isOnInnerMarker = false;
        blindLandingAzimuthPB = 0.0F;
        blindLandingAzimuthBP = 0.0F;
        blindLandingRange = 0.0F;
        signalStrength = 0.0F;
        runwayLength = 1700F;
    }

    public void reset()
    {
        isOnOuterMarker = false;
        isOnInnerMarker = false;
        blindLandingAzimuthPB = 0.0F;
        blindLandingAzimuthBP = 0.0F;
        blindLandingRange = 50000F;
        signalStrength = 0.0F;
    }

    public void addSignal(float f, float f1, float f2, boolean flag, float f3, float f4, float f5)
    {
        blindLandingAzimuthPB = f1;
        blindLandingAzimuthBP = f;
        blindLandingRange = f2;
        signalStrength = f3;
        if(flag)
        {
            runwayLength = 1700F;
            if(f5 > 500F)
                runwayLength = 2300F;
        } else
        {
            isOnInnerMarker = false;
            isOnOuterMarker = false;
            runwayLength = 0.0F;
            return;
        }
        if(java.lang.Math.abs(blindLandingAzimuthBP) < 10F && f2 > (f5 - 85F) + runwayLength && f2 < f5 + 85F + runwayLength)
            isOnInnerMarker = true;
        else
            isOnInnerMarker = false;
        if(java.lang.Math.abs(blindLandingAzimuthBP) < 10F && f2 > (f4 - 85F) + runwayLength && f2 < f4 + 85F + runwayLength)
            isOnOuterMarker = true;
        else
            isOnOuterMarker = false;
    }

    public boolean isOnOuterMarker;
    public boolean isOnInnerMarker;
    public float blindLandingAzimuthPB;
    public float blindLandingAzimuthBP;
    public float blindLandingRange;
    public float signalStrength;
    private final float markerFanLength = 85F;
    public float runwayLength;
}
