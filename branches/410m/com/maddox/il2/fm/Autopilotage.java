// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Autopilotage.java

package com.maddox.il2.fm;

import com.maddox.il2.ai.Way;

public class Autopilotage
{

    public Autopilotage()
    {
        way = new Way();
    }

    public boolean getWayPoint()
    {
        return false;
    }

    public boolean getStabAltitude()
    {
        return false;
    }

    public boolean getStabSpeed()
    {
        return false;
    }

    public boolean getStabDirection()
    {
        return false;
    }

    public void setWayPoint(boolean flag)
    {
    }

    public void setStabAltitude(boolean flag)
    {
    }

    public void setStabAltitude(float f)
    {
    }

    public void setStabSpeed(boolean flag)
    {
    }

    public void setStabSpeed(float f)
    {
    }

    public void setStabDirection(boolean flag)
    {
    }

    public void setStabDirection(float f)
    {
    }

    public void setStabAll(boolean flag)
    {
    }

    public float getWayPointDistance()
    {
        return 1000F;
    }

    public void update(float f)
    {
    }

    public com.maddox.il2.ai.Way way;
}
