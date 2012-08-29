// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Wing.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.MsgOwnerListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.ai:
//            Chief, Squadron, Way, World, 
//            Regiment, Formation, War, WayPoint, 
//            Airport

public class Wing extends com.maddox.il2.ai.Chief
    implements com.maddox.il2.engine.MsgOwnerListener
{

    public int aircReady()
    {
        int i = 0;
        for(int j = 0; j < airc.length; j++)
            if(com.maddox.il2.engine.Actor.isValid(airc[j]))
                i++;

        return i;
    }

    public int aircIndex(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        java.lang.String s = aircraft.name();
        char c = s.charAt(s.length() - 1);
        return c - 48;
    }

    public com.maddox.il2.ai.Regiment regiment()
    {
        return squadron().regiment();
    }

    public com.maddox.il2.ai.Squadron squadron()
    {
        return (com.maddox.il2.ai.Squadron)getOwner();
    }

    public int indexInSquadron()
    {
        char c = name().charAt(name().length() - 1);
        return c - 48;
    }

    public void msgOwnerDied(com.maddox.il2.engine.Actor actor)
    {
        if(getDiedFlag())
            return;
        for(int i = 0; i < airc.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(airc[i]) && airc[i].isAlive())
                return;

        com.maddox.il2.ai.World.onActorDied(this, null);
    }

    public void msgOwnerTaskComplete(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < airc.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(airc[i]) && !airc[i].isTaskComplete())
                return;

        com.maddox.il2.ai.World.onTaskComplete(this);
    }

    public void msgOwnerAttach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void msgOwnerDetach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void msgOwnerChange(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
    }

    public void destroy()
    {
        for(int i = 0; i < airc.length; i++)
            airc[i] = null;

        super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Wing()
    {
        airc = new com.maddox.il2.objects.air.Aircraft[4];
        way = new Way();
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s, com.maddox.rts.NetChannel netchannel)
        throws java.lang.Exception
    {
        boolean flag = netchannel != null;
        if(sectfile.sectionIndex(s) < 0)
        {
            destroy();
            throw new Exception("Mission: section '" + s + "' not found");
        }
        java.lang.String s1 = sectfile.get(s, "Class", (java.lang.String)null);
        if(s1 == null)
        {
            destroy();
            throw new Exception("Mission: in section '" + s + "' class aircraft not defined");
        }
        java.lang.String s2 = s + "_Way";
        if(sectfile.sectionIndex(s2) < 0)
        {
            destroy();
            throw new Exception("Mission: section '" + s2 + "' not found");
        }
        if(com.maddox.il2.engine.Actor.getByName(s) != null)
        {
            destroy();
            throw new Exception("Mission: dublicate wing '" + s + "'");
        }
        setName(s);
        int i = indexInSquadron();
        if(i < 0 || i > 3)
            throw new RuntimeException("Wing '" + s + "' NOT valid");
        com.maddox.il2.ai.Squadron squadron1 = com.maddox.il2.ai.Squadron.New(s.substring(0, s.length() - 1));
        setOwner(squadron1);
        squadron1.wing[indexInSquadron()] = this;
        setArmy(squadron1.getArmy());
        com.maddox.il2.objects.air.Aircraft.loadingCountry = squadron1.regiment().country();
        int j = sectfile.get(s, "Planes", 1, 1, 4);
        try
        {
            way.load(sectfile, s2);
            for(int k = 0; k < j; k++)
            {
                airc[k] = com.maddox.il2.game.Mission.cur().loadAir(sectfile, s1, s, s + k, k);
                airc[k].setArmy(getArmy());
                airc[k].checkTurretSkill();
                airc[k].setOwner(this);
                if(airc[k] == com.maddox.il2.ai.World.getPlayerAircraft())
                    com.maddox.il2.ai.World.setPlayerRegiment();
                airc[k].preparePaintScheme();
                airc[k].prepareCamouflage();
                setPosAndSpeed(airc[k], way);
            }

            setArmy(airc[0].getArmy());
            com.maddox.il2.ai.Formation.generate(airc);
            for(int l = 0; l < j; l++)
                airc[l].FM.AP.way = new Way(way);

            if((com.maddox.il2.game.Mission.isSingle() || (com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight()) && com.maddox.il2.game.Mission.isServer()) && !com.maddox.il2.net.NetMissionTrack.isPlaying())
            {
                com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(airc[0].getSquadron(), way);
                for(int i1 = 0; i1 < j; i1++)
                    airgroup.addAircraft(airc[i1]);

                com.maddox.il2.ai.air.AirGroupList.addAirGroup(com.maddox.il2.ai.War.Groups, airc[0].getArmy() - 1 & 1, airgroup);
            }
            com.maddox.il2.objects.air.Aircraft.loadingCountry = null;
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.Aircraft.loadingCountry = null;
            destroy();
            throw exception;
        }
    }

    private void setPosAndSpeed(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.ai.Way way1)
    {
        if(way1.size() == 1)
        {
            com.maddox.il2.ai.WayPoint waypoint = way1.get(0);
            waypoint.getP(tmpPoint0);
            aircraft.pos.setAbs(tmpPoint0);
            tmpSpeed.set(waypoint.Speed, 0.0D, 0.0D);
            aircraft.setSpeed(tmpSpeed);
        } else
        {
            com.maddox.il2.ai.WayPoint waypoint1 = way1.get(0);
            com.maddox.il2.ai.WayPoint waypoint2 = way1.get(1);
            waypoint1.getP(tmpPoint0);
            waypoint2.getP(tmpPoint1);
            tmpSpeed.sub(tmpPoint1, tmpPoint0);
            tmpSpeed.normalize();
            _tmpOrient.setAT0(tmpSpeed);
            tmpSpeed.scale(waypoint1.Speed);
            aircraft.pos.setAbs(tmpPoint0, _tmpOrient);
            aircraft.setSpeed(tmpSpeed);
        }
        aircraft.pos.reset();
    }

    public void setOnAirport()
    {
        com.maddox.il2.ai.WayPoint waypoint = way.get(0);
        waypoint.getP(tmpPoint0);
        if(waypoint.Action == 1)
        {
            boolean flag = false;
            if(waypoint.sTarget != null)
            {
                com.maddox.il2.engine.Actor actor = waypoint.getTarget();
                if(actor != null && (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    com.maddox.il2.ai.AirportCarrier airportcarrier = ((com.maddox.il2.objects.ships.BigshipGeneric)actor).getAirport();
                    if(com.maddox.il2.engine.Actor.isValid(airportcarrier))
                    {
                        airportcarrier.setTakeoff(tmpPoint0, airc);
                        flag = true;
                    }
                }
            }
            if(!flag)
            {
                com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(tmpPoint0, getArmy(), 3);
                if(airport != null)
                {
                    double d = airport.pos.getAbsPoint().distance(tmpPoint0);
                    if(d < 1250D)
                        airport.setTakeoff(tmpPoint0, airc);
                    else
                        setonground();
                } else
                {
                    setonground();
                }
            }
        } else
        {
            for(int i = 0; i < 4; i++)
            {
                if(!com.maddox.il2.engine.Actor.isValid(airc[i]))
                    continue;
                if(airc[i] == com.maddox.il2.ai.World.getPlayerAircraft())
                {
                    airc[i].FM.EI.setCurControlAll(true);
                    airc[i].FM.EI.setEngineRunning();
                    airc[i].FM.CT.setPowerControl(0.75F);
                }
                airc[i].FM.setStationedOnGround(false);
                airc[i].FM.setWasAirborne(true);
            }

        }
    }

    private void setonground()
    {
        for(int i = 0; i < airc.length; i++)
            if(airc[i] != null)
            {
                airc[i].pos.getAbs(pGround, oGround);
                pGround.z = com.maddox.il2.ai.World.land().HQ(pGround.x, pGround.y) + (double)airc[i].FM.Gears.H;
                com.maddox.il2.engine.Engine.land().N(pGround.x, pGround.y, vGround);
                oGround.orient(vGround);
                oGround.increment(0.0F, airc[i].FM.Gears.Pitch, 0.0F);
                airc[i].setOnGround(pGround, oGround, zeroSpeed);
            }

    }

    public com.maddox.il2.objects.air.Aircraft airc[];
    public com.maddox.il2.ai.Way way;
    private static com.maddox.JGP.Vector3d tmpSpeed = new Vector3d();
    private static com.maddox.JGP.Point3d tmpPoint0 = new Point3d();
    private static com.maddox.JGP.Point3d tmpPoint1 = new Point3d();
    private static com.maddox.JGP.Vector3d zeroSpeed = new Vector3d();
    private static com.maddox.JGP.Point3d pGround = new Point3d();
    private static com.maddox.il2.engine.Orient oGround = new Orient();
    private static com.maddox.JGP.Vector3d vGround = new Vector3d();

}
