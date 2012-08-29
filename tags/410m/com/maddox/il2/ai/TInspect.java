// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TInspect.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiNetSendMethods;
import com.maddox.il2.game.ZutiRadarObject;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            Target, World

public class TInspect extends com.maddox.il2.ai.Target
{

    boolean checkActor()
    {
        if(com.maddox.il2.engine.Actor.isAlive(actor))
        {
            actor.pos.getAbs(point);
            return true;
        }
        if(actor != null)
            return false;
        actor = com.maddox.il2.engine.Actor.getByName(nameTarget);
        if(com.maddox.il2.engine.Actor.isAlive(actor))
        {
            actor.pos.getAbs(point);
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean checkPeriodic()
    {
        if(com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight())
        {
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int i = list.size();
            int j = com.maddox.il2.ai.World.getMissionArmy();
            for(int k = 0; k < i; k++)
            {
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)list.get(k);
                if((actor1 instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.engine.Actor.isAlive(actor1) && actor1.getArmy() == j && checkPeriodic(actor1))
                    return true;
            }

            return false;
        }
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
            return false;
        else
            return checkPeriodic(((com.maddox.il2.engine.Actor) (aircraft)));
    }

    protected boolean checkPeriodic(com.maddox.il2.engine.Actor actor1)
    {
        checkActor();
        actor1.pos.getAbs(p);
        double d = p.z;
        p.z = point.z;
        if(p.distance(point) <= r)
        {
            boolean flag = com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutCompleteRecon;
            if(flag && !com.maddox.il2.game.ZutiRadarObject.isPlayerArmyScout(actor1, com.maddox.il2.ai.World.getMissionArmy()))
                return false;
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor1;
            if(aircraft.FM.isWasAirborne())
            {
                if(bLanding)
                    if(com.maddox.il2.engine.Actor.isAlive(actor))
                    {
                        actor1.getSpeed(v0);
                        actor.getSpeed(v1);
                        v0.sub(v1);
                        if(v0.length() > 5D)
                            return false;
                    } else
                    if(d - com.maddox.il2.ai.World.land().HQ(p.x, p.y) > 10D || actor1.getSpeed(null) > 5D)
                        return false;
                setTaskCompleteFlag(true);
                setDiedFlag(true);
                zutiTargetDied();
                return true;
            }
        }
        return false;
    }

    protected boolean checkActorDied(com.maddox.il2.engine.Actor actor1)
    {
        if(com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight())
        {
            if(!(actor1 instanceof com.maddox.il2.objects.air.Aircraft))
                return false;
            int i = com.maddox.il2.ai.World.getMissionArmy();
            if(actor1.getArmy() != i)
                return false;
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int j = list.size();
            for(int k = 0; k < j; k++)
            {
                actor1 = (com.maddox.il2.engine.Actor)list.get(k);
                if((actor1 instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.engine.Actor.isAlive(actor1) && actor1.getArmy() == i)
                    return false;
            }

        } else
        if(actor1 != com.maddox.il2.ai.World.getPlayerAircraft())
            return false;
        setDiedFlag(true);
        zutiTargetDied();
        return true;
    }

    protected boolean checkTimeoutOff()
    {
        setDiedFlag(true);
        return true;
    }

    public TInspect(int i, int j, int k, int l, int i1, boolean flag, java.lang.String s)
    {
        super(i, j);
        bLanding = flag;
        nameTarget = s;
        com.maddox.il2.ai.World.land();
        point = new Point3d(k, l, com.maddox.il2.engine.Landscape.HQ(k, l));
        r = i1;
    }

    private void zutiTargetDied()
    {
        if(com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS == null)
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS = new ArrayList();
        if(!com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS.contains(this))
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS.add(this);
        zutiSendRemoveMessageToClients();
    }

    private void zutiSendRemoveMessageToClients()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(actor == null)
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(point.x, point.y);
            else
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(actor.name());
            java.util.List list = com.maddox.rts.NetEnv.hosts();
            int i = list.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(j);
                if(actor != null)
                    com.maddox.il2.game.ZutiNetSendMethods.removeTInspectTarget(netuser, point, actor.name());
                else
                    com.maddox.il2.game.ZutiNetSendMethods.removeTInspectTarget(netuser, point, "null");
            }

        }
    }

    java.lang.String nameTarget;
    com.maddox.il2.engine.Actor actor;
    boolean bLanding;
    public com.maddox.JGP.Point3d point;
    double r;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Vector3d v0 = new Vector3d();
    private static com.maddox.JGP.Vector3d v1 = new Vector3d();

}
