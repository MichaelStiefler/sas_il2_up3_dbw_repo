// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiPadObject.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.game:
//            ZutiSupportMethods, ZutiRadarObject

public class ZutiPadObject
{

    public ZutiPadObject(com.maddox.il2.engine.Actor actor1, boolean flag)
    {
        livePos = null;
        oldPos = null;
        deadPos = new Point3d(-99999.999989999997D, -99999.999989999997D, 0.0D);
        azimut = 0.0F;
        isPlayerPlane = false;
        visibleForPlayerArmy = false;
        refreshIntervalSet = false;
        name = null;
        type = -1;
        if(actor1 == null)
            return;
        try
        {
            if(com.maddox.il2.ai.World.getPlayerAircraft() == actor1)
                isPlayerPlane = true;
            refreshIntervalSet = flag;
            actor = actor1;
            livePos = actor.pos.getAbsPoint();
            oldPos = new Point3d(livePos.x, livePos.y, livePos.z);
            azimut = actor.pos.getAbsOrient().azimut();
            name = actor.name();
            isPlayerArmyScout = com.maddox.il2.game.ZutiRadarObject.isPlayerArmyScout(actor1, com.maddox.il2.game.ZutiSupportMethods.getPlayerArmy());
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
        return;
    }

    public void refreshPosition()
    {
        if(actor == null || livePos == null)
        {
            return;
        } else
        {
            oldPos = new Point3d(livePos.x, livePos.y, livePos.z);
            azimut = actor.pos.getAbsOrient().azimut();
            return;
        }
    }

    public java.lang.String getName()
    {
        return name;
    }

    public com.maddox.JGP.Point3d getPosition()
    {
        if(!isAlive())
            return deadPos;
        if(refreshIntervalSet)
            return oldPos;
        else
            return livePos;
    }

    public float getAzimut()
    {
        return azimut;
    }

    public boolean isAlive()
    {
        if(actor == null)
            return false;
        if(actor instanceof com.maddox.il2.objects.vehicles.artillery.RocketryRocket)
            return !((com.maddox.il2.objects.vehicles.artillery.RocketryRocket)actor).isDamaged();
        else
            return !actor.getDiedFlag();
    }

    public com.maddox.il2.engine.Actor getOwner()
    {
        return actor;
    }

    public boolean equals(java.lang.Object obj)
    {
        if(!(obj instanceof com.maddox.il2.game.ZutiPadObject))
            return false;
        com.maddox.il2.game.ZutiPadObject zutipadobject = (com.maddox.il2.game.ZutiPadObject)obj;
        return actor.equals(zutipadobject.getOwner());
    }

    public int hashCode()
    {
        return actor.hashCode();
    }

    public int getArmy()
    {
        if(isAlive())
            return actor.getArmy();
        else
            return -1;
    }

    public boolean isGroundUnit()
    {
        if(actor == null)
            return false;
        else
            return com.maddox.il2.game.ZutiPadObject.isGroundUnit(actor);
    }

    public com.maddox.il2.engine.Mat getIcon()
    {
        if(actor.icon != null)
            return actor.icon;
        java.lang.Class class1 = actor.getClass();
        com.maddox.il2.engine.Mat mat = (com.maddox.il2.engine.Mat)com.maddox.rts.Property.value(class1, "iconMat", null);
        if(mat == null)
        {
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "iconName", null);
            if(s != null)
                try
                {
                    mat = com.maddox.il2.engine.Mat.New(s);
                }
                catch(java.lang.Exception exception)
                {
                    exception.printStackTrace();
                }
        }
        return mat;
    }

    public boolean isPlayerPlane()
    {
        return isPlayerPlane;
    }

    public static boolean isGroundUnit(com.maddox.il2.engine.Actor actor1)
    {
        return !(actor1 instanceof com.maddox.il2.objects.sounds.SndAircraft) && !(actor1 instanceof com.maddox.il2.objects.vehicles.artillery.RocketryRocket);
    }

    public boolean isVisibleForPlayerArmy()
    {
        if(isPlayerArmyScout)
            return true;
        else
            return visibleForPlayerArmy;
    }

    public void setVisibleForPlayerArmy(boolean flag)
    {
        visibleForPlayerArmy = flag;
    }

    public boolean isPlayerArmyScout()
    {
        return isPlayerArmyScout;
    }

    private com.maddox.il2.engine.Actor actor;
    private com.maddox.JGP.Point3d livePos;
    private com.maddox.JGP.Point3d oldPos;
    private com.maddox.JGP.Point3d deadPos;
    private float azimut;
    private boolean isPlayerPlane;
    private boolean isPlayerArmyScout;
    private boolean visibleForPlayerArmy;
    private boolean refreshIntervalSet;
    private java.lang.String name;
    public int type;
}
