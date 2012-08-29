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
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
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
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.ai:
//            Airport, Way, WayPoint, World, 
//            War

public class AirportCarrier extends com.maddox.il2.ai.Airport
{

    public com.maddox.il2.objects.ships.BigshipGeneric ship()
    {
        return ship;
    }

    public AirportCarrier(com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric, com.maddox.il2.engine.Loc aloc[])
    {
        tmpLoc = new Loc();
        tmpP3d = new Point3d();
        tmpP3f = new Point3f();
        tmpOr = new Orient();
        curPlaneShift = 0;
        oldTickCounter = 0;
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
        if(com.maddox.rts.Time.tickCounter() != oldTickCounter)
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

    public void setTakeoff(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aaircraft[])
    {
        com.maddox.il2.ai.air.CellAirField cellairfield = ship.getCellTO();
        if(cellairfield == null)
        {
            for(int i = 0; i < aaircraft.length; i++)
                setTakeoffOld(point3d, aaircraft[i]);

            return;
        }
        if(com.maddox.rts.Time.tickCounter() != oldTickCounter)
        {
            oldTickCounter = com.maddox.rts.Time.tickCounter();
            curPlaneShift = 0;
            cellairfield.freeCells();
        }
        ship.getSpeed(shipSpeed);
        for(int j = 0; j < aaircraft.length; j++)
            if(com.maddox.il2.engine.Actor.isValid(aaircraft[j]))
            {
                com.maddox.il2.ai.air.CellAirPlane cellairplane = aaircraft[j].getCellAirPlane();
                if(cellairfield.findPlaceForAirPlane(cellairplane))
                {
                    cellairfield.placeAirPlane(cellairplane, cellairfield.resX(), cellairfield.resY());
                    double d = -cellairfield.leftUpperCorner().x - (double)cellairfield.resX() * cellairfield.getCellSize() - cellairplane.ofsX;
                    double d1 = cellairfield.leftUpperCorner().y - (double)cellairfield.resY() * cellairfield.getCellSize() - cellairplane.ofsY;
                    double d2 = runway[0].getZ();
                    tmpLoc.set(d1, d, d2 + (double)aaircraft[j].FM.Gears.H, runway[0].getAzimut(), runway[0].getTangage(), runway[0].getKren());
                    tmpLoc.add(ship.pos.getAbs());
                    com.maddox.JGP.Point3d point3d1 = tmpLoc.getPoint();
                    com.maddox.il2.engine.Orient orient = tmpLoc.getOrient();
                    orient.increment(0.0F, aaircraft[j].FM.Gears.Pitch, 0.0F);
                    aaircraft[j].setOnGround(point3d1, orient, shipSpeed);
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
                    if(curPlaneShift++ > 1 && aaircraft[j].FM.CT.bHasWingControl)
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
            for(int l = 0; l < k; l++)
            {
                java.lang.String s = sectfile.value(j, l);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.value(j, l));
                if(stringtokenizer.hasMoreTokens())
                {
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
                        if(s2 != null)
                        {
                            com.maddox.rts.SectFile sectfile1 = com.maddox.il2.fm.FlightModelMain.sectFile(s2);
                            if(sectfile1.get("Controls", "CArrestorHook", 0) == 1)
                            {
                                _clsMapArrestorPlane.put(class1, null);
                                java.lang.String s3 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, null);
                                com.maddox.rts.SectFile sectfile2 = new SectFile(s3, 0);
                                java.lang.String s4 = sectfile2.get("_ROOT_", "CollisionObject", (java.lang.String)null);
                                if(s4 != null)
                                {
                                    com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s4);
                                    if(numbertokenizer.hasMoreTokens())
                                    {
                                        numbertokenizer.next();
                                        if(numbertokenizer.hasMoreTokens())
                                        {
                                            double d1 = numbertokenizer.next(-1D);
                                            if(d1 > 0.0D && d < d1)
                                            {
                                                d = d1;
                                                _clsBigArrestorPlane = class1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch(java.lang.Exception exception1)
                    {
                        java.lang.System.out.println(exception1.getMessage());
                        exception1.printStackTrace();
                    }
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
        com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = null;
        java.lang.Class class1 = ship.getClass();
        apoint_stay = (com.maddox.il2.ai.air.Point_Stay[][])com.maddox.rts.Property.value(class1, "StayPlaces", null);
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
            return null;
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

    public static final double cellSize = 1D;
    private com.maddox.il2.objects.ships.BigshipGeneric ship;
    private com.maddox.il2.engine.Loc runway[];
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
    private int curPlaneShift;
    private int oldTickCounter;
    private com.maddox.il2.engine.Loc r;
    private static com.maddox.JGP.Vector3d startSpeed = new Vector3d();
    private static com.maddox.JGP.Vector3d shipSpeed = new Vector3d();
    private static java.lang.Class _clsBigArrestorPlane = null;
    private static com.maddox.il2.ai.air.CellAirPlane _cellBigArrestorPlane = null;
    private static java.util.HashMap _clsMapArrestorPlane = new HashMap();

}
