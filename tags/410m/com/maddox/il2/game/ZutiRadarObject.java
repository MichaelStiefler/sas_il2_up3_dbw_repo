// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiRadarObject.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.game:
//            Main, Mission

public class ZutiRadarObject
{

    public ZutiRadarObject(com.maddox.il2.engine.Actor actor, int i)
    {
        owner = actor;
        type = i;
        position = actor.pos.getAbsPoint();
    }

    public int getType()
    {
        return type;
    }

    public double getRange()
    {
        if(type == 3)
            return java.lang.Math.pow((position.z * range) / 1000D, 2D);
        else
            return range;
    }

    public void setRange(double d)
    {
        if(type == 3)
            range = d;
        else
            range = java.lang.Math.pow(d, 2D);
    }

    public int getMinHeight()
    {
        if(type == 3)
            return (int)(position.z - (double)minHeight);
        else
            return minHeight;
    }

    public void setMinHeight(int i)
    {
        minHeight = i;
    }

    public int getMaxHeight()
    {
        if(type == 3)
            return (int)(position.z - (double)maxHeight);
        else
            return maxHeight;
    }

    public void setMaxHeight(int i)
    {
        maxHeight = i;
    }

    public com.maddox.JGP.Point3d getPosition()
    {
        return position;
    }

    public boolean isAlive()
    {
        if(owner == null)
            return false;
        else
            return !owner.getDiedFlag();
    }

    private com.maddox.il2.engine.Actor getOwner()
    {
        return owner;
    }

    public boolean equals(java.lang.Object obj)
    {
        if(!(obj instanceof com.maddox.il2.game.ZutiRadarObject))
            return false;
        com.maddox.il2.game.ZutiRadarObject zutiradarobject = (com.maddox.il2.game.ZutiRadarObject)obj;
        return owner.equals(zutiradarobject.getOwner());
    }

    public int hashCode()
    {
        return owner.hashCode();
    }

    public boolean isCoordinateCovered(com.maddox.JGP.Point3d point3d)
    {
        double d = getRange();
        double d1 = (java.lang.Math.pow(position.x - point3d.x, 2D) + java.lang.Math.pow(position.y - point3d.y, 2D)) / 1000000D;
        return d1 <= d && point3d.z >= (double)getMinHeight() && point3d.z <= (double)getMaxHeight();
    }

    public static boolean isPlayerArmyScout(com.maddox.il2.engine.Actor actor, int i)
    {
        if(actor == null || !(actor instanceof com.maddox.il2.objects.sounds.SndAircraft) || actor.getArmy() != i)
            return false;
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().mission.ScoutsRed;
        java.lang.String s = com.maddox.rts.Property.stringValue(((com.maddox.il2.objects.air.Aircraft)actor).getClass(), "keyName");
        int j = arraylist.size();
        for(int k = 0; k < j; k++)
        {
            java.lang.String s1 = (java.lang.String)arraylist.get(k);
            if(s.indexOf(s1) != -1)
                return true;
        }

        return false;
    }

    public static final java.lang.String ZUTI_RADAR_OBJECT_NAME = "Radar";
    private int type;
    private double range;
    private int minHeight;
    private int maxHeight;
    private com.maddox.JGP.Point3d position;
    private com.maddox.il2.engine.Actor owner;
}
