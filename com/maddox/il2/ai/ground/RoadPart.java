// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RoadPart.java

package com.maddox.il2.ai.ground;


public class RoadPart
{

    public RoadPart()
    {
        begseg = endseg = 0;
        begt = endt = 0.0D;
        occupLen = 0.0D;
    }

    public int begseg;
    public int endseg;
    public double begt;
    public double endt;
    public double occupLen;
}
