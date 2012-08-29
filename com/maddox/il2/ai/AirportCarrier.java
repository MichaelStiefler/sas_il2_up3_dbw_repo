// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AirportCarrier.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.game.Mission;
import com.maddox.il2.gui.GUINetClientDBrief;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.ai:
//            Airport, Way, WayPoint, World, 
//            RangeRandom, War

public class AirportCarrier extends com.maddox.il2.ai.Airport
{
    class DeckUpdater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(ship().isAlive())
            {
                if(com.maddox.rts.Time.tickCounter() > oldTickCounter + 150 + rnd1)
                {
                    oldTickCounter = com.maddox.rts.Time.tickCounter();
                    checkIsDeckClear();
                }
                if(com.maddox.rts.Time.tickCounter() > oldIdleTickCounter + 2000 + rnd2)
                {
                    oldIdleTickCounter = com.maddox.rts.Time.tickCounter();
                    checkPlaneIdle();
                    skipCheck = false;
                }
            }
            return true;
        }

        DeckUpdater()
        {
        }
    }


    public com.maddox.il2.objects.ships.BigshipGeneric ship()
    {
        return ship;
    }

    public AirportCarrier(com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric, com.maddox.il2.engine.Loc aloc[])
    {
        lastCarrierUsers = new ArrayList();
        ui = null;
        clientLoc = null;
        lastAddedAC = null;
        lastAddedCells = null;
        rnd1 = com.maddox.il2.ai.World.Rnd().nextInt(0, 30);
        rnd2 = com.maddox.il2.ai.World.Rnd().nextInt(40, 60);
        skipCheck = false;
        tmpLoc = new Loc();
        tmpP3d = new Point3d();
        tmpP3f = new Point3f();
        tmpOr = new Orient();
        curPlaneShift = 0;
        oldTickCounter = 0;
        oldIdleTickCounter = 0;
        r = new Loc();
        ship = bigshipgeneric;
        pos = new ActorPosMove(this, new Loc());
        pos.setBase(bigshipgeneric, null, false);
        pos.reset();
        runway = aloc;
        if(com.maddox.il2.game.Mission.isDogfight())
        {
            com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = getStayPlaces();
            com.maddox.il2.ai.air.Point_Stay apoint_stay1[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
            com.maddox.il2.ai.air.Point_Stay apoint_stay2[][] = new com.maddox.il2.ai.air.Point_Stay[apoint_stay1.length + apoint_stay.length][];
            int i = 0;
            for(int j = 0; j < apoint_stay1.length; j++)
                apoint_stay2[i++] = apoint_stay1[j];

            for(int k = 0; k < apoint_stay.length; k++)
                apoint_stay2[i++] = apoint_stay[k];

            com.maddox.il2.ai.World.cur().airdrome.stay = apoint_stay2;
        }
        startDeckOperations();
    }

    public void disableBornPlace()
    {
        if(bornPlace2Move != null)
            bornPlace2Move.army = -2;
    }

    public void enableBornPlace()
    {
        bornPlace2Move.army = bornPlaceArmyBk;
    }

    public boolean isAlive()
    {
        return com.maddox.il2.engine.Actor.isAlive(ship);
    }

    public int getArmy()
    {
        if(com.maddox.il2.engine.Actor.isAlive(ship))
            return ship.getArmy();
        else
            return super.getArmy();
    }

    public boolean landWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        com.maddox.il2.ai.Way way = new Way();
        tmpLoc.set(runway[1]);
        tmpLoc.add(ship.initLoc);
        float f = flightmodel.M.massEmpty * 0.0003333F;
        if(f > 1.0F)
            f = 1.0F;
        if(f < 0.4F)
            f = 0.4F;
        for(int i = x.length - 1; i >= 0; i--)
        {
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint();
            tmpP3d.set(x[i] * f, y[i] * f, z[i] * f);
            waypoint.set(java.lang.Math.min(v[i] * 0.278F, flightmodel.Vmax * 0.6F));
            waypoint.Action = 2;
            waypoint.sTarget = ship.name();
            tmpLoc.transform(tmpP3d);
            tmpP3f.set(tmpP3d);
            waypoint.set(tmpP3f);
            way.add(waypoint);
        }

        way.setLanding(true);
        flightmodel.AP.way = way;
        return true;
    }

    public void rebuildLandWay(com.maddox.il2.fm.FlightModel flightmodel)
    {
        if(!ship.isAlive())
        {
            flightmodel.AP.way.setLanding(false);
            return;
        }
        tmpLoc.set(runway[1]);
        tmpLoc.add(ship.initLoc);
        float f = flightmodel.M.massEmpty * 0.0003333F;
        if(f > 1.0F)
            f = 1.0F;
        if(f < 0.4F)
            f = 0.4F;
        for(int i = 0; i < x.length; i++)
        {
            com.maddox.il2.ai.WayPoint waypoint = flightmodel.AP.way.get(i);
            tmpP3d.set(x[x.length - 1 - i] * f, y[x.length - 1 - i] * f, z[x.length - 1 - i] * f);
            tmpLoc.transform(tmpP3d);
            tmpP3f.set(tmpP3d);
            waypoint.set(tmpP3f);
        }

    }

    public void rebuildLastPoint(com.maddox.il2.fm.FlightModel flightmodel)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(ship))
            return;
        int i = flightmodel.AP.way.Cur();
        flightmodel.AP.way.last();
        if(flightmodel.AP.way.curr().Action == 2)
        {
            ship.pos.getAbs(tmpP3d);
            flightmodel.AP.way.curr().set(tmpP3d);
        }
        flightmodel.AP.way.setCur(i);
    }

    public double ShiftFromLine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        tmpLoc.set(flightmodel.Loc);
        r.set(runway[0]);
        r.add(ship.pos.getAbs());
        tmpLoc.sub(r);
        return tmpLoc.getY();
    }

    public boolean nearestRunway(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Loc loc)
    {
        loc.add(runway[1], pos.getAbs());
        return true;
    }

    public int landingFeedback(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        tmpLoc.set(runway[1]);
        tmpLoc.add(ship.initLoc);
        com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.War.getNearestFriendAtPoint(tmpLoc.getPoint(), aircraft, 50F);
        if(aircraft1 != null && aircraft1 != aircraft)
            return 1;
        if(aircraft.FM.CT.GearControl > 0.0F)
            return 0;
        if(landingRequest > 0)
        {
            return 1;
        } else
        {
            landingRequest = 3000;
            return 0;
        }
    }

    public void setTakeoffOld(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
            return;
        r.set(runway[0]);
        r.add(ship.pos.getAbs());
        if(!com.maddox.il2.game.Mission.isDogfight() && com.maddox.rts.Time.tickCounter() != oldTickCounter)
        {
            oldTickCounter = com.maddox.rts.Time.tickCounter();
            curPlaneShift = 0;
        }
        curPlaneShift++;
        aircraft.FM.setStationedOnGround(false);
        aircraft.FM.setWasAirborne(true);
        tmpLoc.set(-((float)curPlaneShift * 200F), -((float)curPlaneShift * 100F), 300D, 0.0F, 0.0F, 0.0F);
        tmpLoc.add(r);
        aircraft.pos.setAbs(tmpLoc);
        aircraft.pos.getAbs(tmpP3d, tmpOr);
        startSpeed.set(100D, 0.0D, 0.0D);
        tmpOr.transform(startSpeed);
        aircraft.setSpeed(startSpeed);
        aircraft.pos.reset();
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver)
            ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).direction = aircraft.pos.getAbsOrient().getAzimut();
        aircraft.FM.AP.way.takeoffAirport = this;
        if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
        {
            aircraft.FM.EI.setCurControlAll(true);
            aircraft.FM.EI.setEngineRunning();
            aircraft.FM.CT.setPowerControl(0.75F);
        }
    }

    public double speedLen()
    {
        ship.getSpeed(shipSpeed);
        return shipSpeed.length();
    }

    private void checkIsDeckClear()
    {
        if(skipCheck)
            return;
        if(lastAddedAC != null && lastAddedAC.isDestroyed())
        {
            com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
            boolean flag1 = cellairfield.removeAirPlane(lastAddedCells);
            if(flag1)
                curPlaneShift--;
            lastAddedAC = null;
            lastAddedCells = null;
        }
        boolean flag = true;
        for(int i = lastCarrierUsers.size() - 1; i >= 0; i--)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)lastCarrierUsers.get(i);
            if(aircraft != null && !aircraft.isDestroyed() && aircraft.isAlive() && (aircraft.FM.Gears.isUnderDeck() || com.maddox.il2.objects.air.NetAircraft.isOnCarrierDeck(this, aircraft.pos.getAbs())))
                flag = false;
            else
                lastCarrierUsers.remove(aircraft);
        }

        if(flag)
        {
            lastCarrierUsers.clear();
            deckCleared();
        }
    }

    private void checkPlaneIdle()
    {
        for(int i = lastCarrierUsers.size() - 1; i >= 0; i--)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)lastCarrierUsers.get(i);
            if(aircraft == null || aircraft.isDestroyed() || !aircraft.FM.Gears.isUnderDeck() && !com.maddox.il2.objects.air.NetAircraft.isOnCarrierDeck(this, aircraft.pos.getAbs()))
                continue;
            aircraft.idleTimeOnCarrier++;
            if(aircraft.idleTimeOnCarrier < 6)
                continue;
            if(aircraft.isNetPlayer() && !aircraft.isNetMaster())
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = aircraft;
                com.maddox.il2.net.NetUser netuser = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)((com.maddox.il2.objects.air.NetAircraft) (aircraft1)).net).netUser;
                netuser.kick(netuser);
            } else
            {
                ((com.maddox.il2.objects.air.NetAircraft) (aircraft)).net.destroy();
                aircraft.destroy();
                lastCarrierUsers.remove(aircraft);
            }
        }

    }

    public com.maddox.il2.engine.Loc setClientTakeOff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.engine.Loc loc = null;
        if(clientLoc != null)
            loc = new Loc(clientLoc);
        clientLoc = null;
        if(loc == null || !isLocValid(loc))
        {
            setTakeoffOld(point3d, aircraft);
            com.maddox.il2.engine.Loc loc1 = aircraft.pos.getAbs();
            double d = com.maddox.il2.ai.World.Rnd().nextDouble(400D, 800D);
            double d1 = com.maddox.il2.ai.World.Rnd().nextDouble(400D, 800D);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                d *= -1D;
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                d1 *= -1D;
            com.maddox.JGP.Point3d point3d2 = new Point3d(d, d1, 0.0D);
            loc1.add(point3d2);
            aircraft.pos.setAbs(loc1);
            return loc1;
        }
        loc.add(ship.pos.getAbs());
        com.maddox.JGP.Point3d point3d1 = loc.getPoint();
        com.maddox.il2.engine.Orient orient = loc.getOrient();
        orient.increment(0.0F, aircraft.FM.Gears.Pitch, 0.0F);
        ship.getSpeed(shipSpeed);
        aircraft.setOnGround(point3d1, orient, shipSpeed);
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver)
            ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).direction = aircraft.pos.getAbsOrient().getAzimut();
        aircraft.FM.AP.way.takeoffAirport = this;
        aircraft.FM.brakeShoe = true;
        aircraft.FM.turnOffCollisions = true;
        aircraft.FM.brakeShoeLoc.set(aircraft.pos.getAbs());
        aircraft.FM.brakeShoeLoc.sub(ship.pos.getAbs());
        aircraft.FM.brakeShoeLastCarrier = ship;
        aircraft.FM.Gears.bFlatTopGearCheck = true;
        aircraft.makeMirrorCarrierRelPos();
        if(aircraft.FM.CT.bHasWingControl)
        {
            aircraft.FM.CT.wingControl = 1.0F;
            aircraft.FM.CT.forceWing(1.0F);
        }
        return loc;
    }

    private com.maddox.JGP.Point3d reservePlaceForPlane(com.maddox.il2.ai.air.CellAirPlane cellairplane, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
        if(cellairfield.findPlaceForAirPlane(cellairplane))
        {
            cellairfield.placeAirPlane(cellairplane, cellairfield.resX(), cellairfield.resY());
            double d = -cellairfield.leftUpperCorner().x - (double)cellairfield.resX() * cellairfield.getCellSize() - cellairplane.ofsX;
            double d1 = cellairfield.leftUpperCorner().y - (double)cellairfield.resY() * cellairfield.getCellSize() - cellairplane.ofsY;
            double d2 = runway[0].getZ();
            curPlaneShift++;
            if(com.maddox.il2.game.Mission.isDogfight() && ship.net.isMaster())
            {
                if(aircraft != null)
                {
                    lastCarrierUsers.add(aircraft);
                    lastAddedAC = aircraft;
                }
                lastAddedCells = cellairplane;
            }
            return new Point3d(d1, d, d2);
        } else
        {
            return null;
        }
    }

    public void setTakeoff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aaircraft[])
    {
        com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
        if(cellairfield == null)
        {
            for(int i = 0; i < aaircraft.length; i++)
                setTakeoffOld(point3d, aaircraft[i]);

            return;
        }
        if(!com.maddox.il2.game.Mission.isDogfight() && com.maddox.rts.Time.tickCounter() != oldTickCounter)
        {
            oldTickCounter = com.maddox.rts.Time.tickCounter();
            curPlaneShift = 0;
            cellairfield.freeCells();
        }
        ship.getSpeed(shipSpeed);
        for(int j = 0; j < aaircraft.length; j++)
        {
            if(!com.maddox.il2.engine.Actor.isValid(aaircraft[j]))
                continue;
            com.maddox.il2.ai.air.CellAirPlane cellairplane = aaircraft[j].getCellAirPlane();
            com.maddox.JGP.Point3d point3d1 = reservePlaceForPlane(cellairplane, aaircraft[j]);
            if(point3d1 != null)
            {
                double d = point3d1.x;
                double d1 = point3d1.y;
                double d2 = point3d1.z;
                tmpLoc.set(d, d1, d2 + (double)aaircraft[j].FM.Gears.H, runway[0].getAzimut(), runway[0].getTangage(), runway[0].getKren());
                tmpLoc.add(ship.pos.getAbs());
                com.maddox.JGP.Point3d point3d2 = tmpLoc.getPoint();
                com.maddox.il2.engine.Orient orient = tmpLoc.getOrient();
                orient.increment(0.0F, aaircraft[j].FM.Gears.Pitch, 0.0F);
                aaircraft[j].setOnGround(point3d2, orient, shipSpeed);
                if(aaircraft[j].FM instanceof com.maddox.il2.ai.air.Maneuver)
                    ((com.maddox.il2.ai.air.Maneuver)aaircraft[j].FM).direction = aaircraft[j].pos.getAbsOrient().getAzimut();
                aaircraft[j].FM.AP.way.takeoffAirport = this;
                aaircraft[j].FM.brakeShoe = true;
                aaircraft[j].FM.turnOffCollisions = true;
                aaircraft[j].FM.brakeShoeLoc.set(aaircraft[j].pos.getAbs());
                aaircraft[j].FM.brakeShoeLoc.sub(ship.pos.getAbs());
                aaircraft[j].FM.brakeShoeLastCarrier = ship;
                aaircraft[j].FM.Gears.bFlatTopGearCheck = true;
                aaircraft[j].makeMirrorCarrierRelPos();
                if(curPlaneShift > 1 && aaircraft[j].FM.CT.bHasWingControl)
                {
                    aaircraft[j].FM.CT.wingControl = 1.0F;
                    aaircraft[j].FM.CT.forceWing(1.0F);
                }
            } else
            {
                setTakeoffOld(point3d, aaircraft[j]);
            }
        }

        if(com.maddox.il2.engine.Actor.isValid(aaircraft[0]) && (aaircraft[0].FM instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)aaircraft[0].FM;
            if(maneuver.Group != null && maneuver.Group.w != null)
                maneuver.Group.w.takeoffAirport = this;
        }
    }

    public void getTakeoff(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.engine.Loc loc)
    {
        tmpLoc.sub(loc, ship.initLoc);
        tmpLoc.set(tmpLoc.getPoint().x, tmpLoc.getPoint().y, runway[0].getZ() + (double)aircraft.FM.Gears.H, runway[0].getAzimut(), runway[0].getTangage(), runway[0].getKren());
        tmpLoc.add(ship.pos.getAbs());
        loc.set(tmpLoc);
        com.maddox.JGP.Point3d point3d = loc.getPoint();
        com.maddox.il2.engine.Orient orient = loc.getOrient();
        orient.increment(0.0F, aircraft.FM.Gears.Pitch, 0.0F);
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver)
            ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).direction = loc.getAzimut();
        aircraft.FM.AP.way.takeoffAirport = this;
        aircraft.FM.brakeShoe = true;
        aircraft.FM.turnOffCollisions = true;
        aircraft.FM.brakeShoeLoc.set(loc);
        aircraft.FM.brakeShoeLoc.sub(ship.pos.getAbs());
        aircraft.FM.brakeShoeLastCarrier = ship;
    }

    public float height()
    {
        return (float)(ship.pos.getAbs().getZ() + runway[0].getZ());
    }

    public static boolean isPlaneContainsArrestor(java.lang.Class class1)
    {
        com.maddox.il2.ai.AirportCarrier.clsBigArrestorPlane();
        return _clsMapArrestorPlane.containsKey(class1);
    }

    private static java.lang.Class clsBigArrestorPlane()
    {
        if(_clsBigArrestorPlane != null)
            return _clsBigArrestorPlane;
        double d = 0.0D;
        com.maddox.rts.SectFile sectfile = new SectFile("com/maddox/il2/objects/air.ini", 0);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
        {
            int k = sectfile.vars(j);
            for(int l = 0; l < k;)
            {
                java.lang.String s = sectfile.value(j, l);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.value(j, l));
                if(!stringtokenizer.hasMoreTokens())
                    continue;
                java.lang.String s1 = "com.maddox.il2.objects." + stringtokenizer.nextToken();
                java.lang.Class class1 = null;
                java.lang.String s2 = null;
                try
                {
                    class1 = java.lang.Class.forName(s1);
                    s2 = com.maddox.rts.Property.stringValue(class1, "FlightModel", null);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
                try
                {
                    if(s2 == null)
                        continue;
                    com.maddox.rts.SectFile sectfile1 = com.maddox.il2.fm.FlightModelMain.sectFile(s2);
                    if(sectfile1.get("Controls", "CArrestorHook", 0) != 1)
                        continue;
                    _clsMapArrestorPlane.put(class1, null);
                    java.lang.String s3 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, null);
                    com.maddox.rts.SectFile sectfile2 = new SectFile(s3, 0);
                    java.lang.String s4 = sectfile2.get("_ROOT_", "CollisionObject", (java.lang.String)null);
                    if(s4 == null)
                        continue;
                    com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s4);
                    if(!numbertokenizer.hasMoreTokens())
                        continue;
                    numbertokenizer.next();
                    if(!numbertokenizer.hasMoreTokens())
                        continue;
                    double d1 = numbertokenizer.next(-1D);
                    if(d1 > 0.0D && d < d1)
                    {
                        d = d1;
                        _clsBigArrestorPlane = class1;
                    }
                    continue;
                }
                catch(java.lang.Exception exception1)
                {
                    java.lang.System.out.println(exception1.getMessage());
                    exception1.printStackTrace();
                    l++;
                }
            }

        }

        return _clsBigArrestorPlane;
    }

    private static com.maddox.il2.ai.air.CellAirPlane cellBigArrestorPlane()
    {
        if(_cellBigArrestorPlane != null)
        {
            return _cellBigArrestorPlane;
        } else
        {
            _cellBigArrestorPlane = com.maddox.il2.objects.air.Aircraft.getCellAirPlane(com.maddox.il2.ai.AirportCarrier.clsBigArrestorPlane());
            return _cellBigArrestorPlane;
        }
    }

    private com.maddox.il2.ai.air.Point_Stay[][] getStayPlaces()
    {
        com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = (com.maddox.il2.ai.air.Point_Stay[][])null;
        java.lang.Class class1 = ship.getClass();
        apoint_stay = (com.maddox.il2.ai.air.Point_Stay[][])(com.maddox.il2.ai.air.Point_Stay[][])com.maddox.rts.Property.value(class1, "StayPlaces", null);
        if(apoint_stay == null)
        {
            com.maddox.il2.ai.air.CellAirPlane cellairplane = com.maddox.il2.ai.AirportCarrier.cellBigArrestorPlane();
            com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
            cellairfield = (com.maddox.il2.ai.air.CellAirField)cellairfield.getClone();
            java.util.ArrayList arraylist = new ArrayList();
            do
            {
                cellairplane = (com.maddox.il2.ai.air.CellAirPlane)cellairplane.getClone();
                if(!cellairfield.findPlaceForAirPlane(cellairplane))
                    break;
                cellairfield.placeAirPlane(cellairplane, cellairfield.resX(), cellairfield.resY());
                double d = -cellairfield.leftUpperCorner().x - (double)cellairfield.resX() * cellairfield.getCellSize() - cellairplane.ofsX;
                double d2 = cellairfield.leftUpperCorner().y - (double)cellairfield.resY() * cellairfield.getCellSize() - cellairplane.ofsY;
                arraylist.add(new Point2d(d2, d));
            } while(true);
            int j = arraylist.size();
            if(j > 0)
            {
                apoint_stay = new com.maddox.il2.ai.air.Point_Stay[j][1];
                for(int k = 0; k < j; k++)
                {
                    com.maddox.JGP.Point2d point2d = (com.maddox.JGP.Point2d)arraylist.get(k);
                    apoint_stay[k][0] = new Point_Stay((float)point2d.x, (float)point2d.y);
                }

                com.maddox.rts.Property.set(class1, "StayPlaces", apoint_stay);
            }
        }
        if(apoint_stay == null)
            return (com.maddox.il2.ai.air.Point_Stay[][])null;
        com.maddox.il2.ai.air.Point_Stay apoint_stay1[][] = new com.maddox.il2.ai.air.Point_Stay[apoint_stay.length][1];
        for(int i = 0; i < apoint_stay.length; i++)
        {
            com.maddox.il2.ai.air.Point_Stay point_stay = apoint_stay[i][0];
            double d1 = point_stay.x;
            double d3 = point_stay.y;
            double d4 = runway[0].getZ();
            tmpLoc.set(d1, d3, d4, runway[0].getAzimut(), runway[0].getTangage(), runway[0].getKren());
            tmpLoc.add(ship.pos.getAbs());
            com.maddox.JGP.Point3d point3d = tmpLoc.getPoint();
            apoint_stay1[i][0] = new Point_Stay((float)point3d.x, (float)point3d.y);
        }

        return apoint_stay1;
    }

    public void setCellUsed(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        skipCheck = false;
        if(ship.net.isMaster())
        {
            lastCarrierUsers.add(aircraft);
            lastAddedAC = aircraft;
        }
        if(aircraft.FM.CT.bHasWingControl && !aircraft.isNetPlayer())
            aircraft.FM.CT.wingControl = 0.0F;
    }

    public com.maddox.il2.engine.Loc requestCell(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!ship().isAlive())
            return invalidLoc;
        com.maddox.il2.ai.air.CellAirPlane cellairplane = aircraft.getCellAirPlane();
        com.maddox.JGP.Point3d point3d = reservePlaceForPlane(cellairplane, null);
        if(point3d != null)
        {
            skipCheck = true;
            double d = point3d.x;
            double d1 = point3d.y;
            double d2 = point3d.z;
            com.maddox.il2.engine.Loc loc = new Loc();
            loc.set(d, d1, d2 + (double)aircraft.FM.Gears.H, runway[0].getAzimut(), runway[0].getTangage(), runway[0].getKren());
            return loc;
        } else
        {
            return invalidLoc;
        }
    }

    private void deckCleared()
    {
        curPlaneShift = 0;
        com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
        cellairfield.freeCells();
    }

    public void setGuiCallback(com.maddox.il2.gui.GUINetClientDBrief guinetclientdbrief)
    {
        ui = guinetclientdbrief;
    }

    public void setClientLoc(com.maddox.il2.engine.Loc loc)
    {
        clientLoc = loc;
        boolean flag = isLocValid(loc);
        if(ui != null)
            ui.flyFromCarrier(flag);
    }

    private boolean isLocValid(com.maddox.il2.engine.Loc loc)
    {
        if(loc == null)
            return false;
        return (int)loc.getX() != 0 || (int)loc.getY() != 0 || (int)loc.getZ() != 0 || (int)loc.getTangage() != 0 || (int)loc.getKren() != 0;
    }

    public void startDeckOperations()
    {
        if(com.maddox.il2.game.Mission.isDogfight() && ship.net.isMaster())
            interpPut(new DeckUpdater(), "deck_operations", com.maddox.rts.Time.current(), null);
    }

    public static final double cellSize = 1D;
    private com.maddox.il2.objects.ships.BigshipGeneric ship;
    private com.maddox.il2.engine.Loc runway[];
    public com.maddox.il2.net.BornPlace bornPlace2Move;
    public int bornPlaceArmyBk;
    private java.util.List lastCarrierUsers;
    private com.maddox.il2.gui.GUINetClientDBrief ui;
    private com.maddox.il2.engine.Loc clientLoc;
    private static final com.maddox.il2.engine.Loc invalidLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    private com.maddox.il2.objects.air.Aircraft lastAddedAC;
    private com.maddox.il2.ai.air.CellAirPlane lastAddedCells;
    private int rnd1;
    private int rnd2;
    private boolean skipCheck;
    private static float x[] = {
        -100F, -20F, -10F, 1000F, 3000F, 4000F, 3000F, 0.0F, 0.0F
    };
    private static float y[] = {
        0.0F, 0.0F, 0.0F, 0.0F, -500F, -1500F, -3000F, -3000F, -3000F
    };
    private static float z[] = {
        -4F, 5F, 5F, 150F, 450F, 500F, 500F, 500F, 500F
    };
    private static float v[] = {
        0.0F, 80F, 100F, 180F, 250F, 270F, 280F, 300F, 300F
    };
    private com.maddox.il2.engine.Loc tmpLoc;
    private com.maddox.JGP.Point3d tmpP3d;
    private com.maddox.JGP.Point3f tmpP3f;
    private com.maddox.il2.engine.Orient tmpOr;
    public int curPlaneShift;
    private int oldTickCounter;
    private int oldIdleTickCounter;
    private com.maddox.il2.engine.Loc r;
    private static com.maddox.JGP.Vector3d startSpeed = new Vector3d();
    private static com.maddox.JGP.Vector3d shipSpeed = new Vector3d();
    private static java.lang.Class _clsBigArrestorPlane = null;
    private static com.maddox.il2.ai.air.CellAirPlane _cellBigArrestorPlane = null;
    private static java.util.HashMap _clsMapArrestorPlane = new HashMap();










}
