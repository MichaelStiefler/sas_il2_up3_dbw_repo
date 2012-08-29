// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AutopilotAI.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.ai.air:
//            Pilot, Maneuver

public class AutopilotAI extends com.maddox.il2.fm.Autopilotage
{

    public AutopilotAI(com.maddox.il2.fm.FlightModel flightmodel)
    {
        WPoint = new Point3d();
        Ve = new Vector3d();
        FM = (com.maddox.il2.ai.air.Pilot)flightmodel;
    }

    public boolean getWayPoint()
    {
        return bWayPoint;
    }

    public boolean getStabAltitude()
    {
        return bStabAltitude;
    }

    public boolean getStabSpeed()
    {
        return bStabSpeed;
    }

    public boolean getStabDirection()
    {
        return bStabDirection;
    }

    public void setWayPoint(boolean flag)
    {
        bWayPoint = flag;
        if(!flag)
            return;
        bStabSpeed = false;
        bStabAltitude = false;
        bStabDirection = false;
        if(WWPoint != null)
        {
            WWPoint.getP(WPoint);
            StabSpeed = WWPoint.Speed;
            StabAltitude = WPoint.z;
        } else
        {
            StabAltitude = 1000D;
            StabSpeed = 80D;
        }
        StabDirection = O.getAzimut();
    }

    public void setStabAltitude(boolean flag)
    {
        bStabAltitude = flag;
        if(!flag)
            return;
        bWayPoint = false;
        FM.getLoc(P);
        StabAltitude = P.z;
        if(!bStabSpeed)
            StabSpeed = FM.getSpeed();
        Pw = FM.CT.PowerControl;
    }

    public void setStabAltitude(float f)
    {
        bStabAltitude = true;
        bWayPoint = false;
        FM.getLoc(P);
        StabAltitude = f;
        if(!bStabSpeed)
            StabSpeed = FM.getSpeed();
        Pw = FM.CT.PowerControl;
    }

    public void setStabSpeed(boolean flag)
    {
        bStabSpeed = flag;
        if(!flag)
        {
            return;
        } else
        {
            bWayPoint = false;
            StabSpeed = FM.getSpeed();
            return;
        }
    }

    public void setStabSpeed(float f)
    {
        bStabSpeed = true;
        bWayPoint = false;
        StabSpeed = f / 3.6F;
    }

    public void setStabDirection(boolean flag)
    {
        bStabDirection = flag;
        if(!flag)
        {
            return;
        } else
        {
            bWayPoint = false;
            O.set(FM.Or);
            StabDirection = O.getAzimut();
            Ail = FM.CT.AileronControl;
            return;
        }
    }

    public void setStabDirection(float f)
    {
        bStabDirection = true;
        bWayPoint = false;
        StabDirection = (f + 3600F) % 360F;
        Ail = FM.CT.AileronControl;
    }

    public void setStabAll(boolean flag)
    {
        bWayPoint = false;
        setStabDirection(flag);
        setStabAltitude(flag);
        setStabSpeed(flag);
        setStabDirection(flag);
    }

    public float getWayPointDistance()
    {
        if(WPoint == null)
        {
            return 1000000F;
        } else
        {
            way.curr().getP(P);
            P.sub(FM.Loc);
            return (float)java.lang.Math.sqrt(P.x * P.x + P.y * P.y);
        }
    }

    private void voiceCommand(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        Ve.sub(point3d1, point3d);
        float f = 57.32484F * (float)java.lang.Math.atan2(Ve.x, Ve.y);
        int i = (int)f;
        i = (i + 180) % 360;
        com.maddox.il2.objects.sounds.Voice.speakHeading((com.maddox.il2.objects.air.Aircraft)FM.actor, i);
        com.maddox.il2.objects.sounds.Voice.speakAltitude((com.maddox.il2.objects.air.Aircraft)FM.actor, (int)point3d.z);
    }

    public void update(float f)
    {
        FM.getLoc(PlLoc);
        SA = (float)java.lang.Math.max(StabAltitude, com.maddox.il2.engine.Engine.land().HQ_Air(PlLoc.x, PlLoc.y) + 5D);
        if(bWayPoint)
        {
            if(WWPoint != way.auto(PlLoc) || way.isReached(PlLoc))
            {
                WWPoint = way.auto(PlLoc);
                WWPoint.getP(WPoint);
                if(((com.maddox.il2.objects.air.Aircraft)FM.actor).aircIndex() == 0 && !way.isLanding())
                    voiceCommand(WPoint, PlLoc);
                StabSpeed = WWPoint.Speed - 2.0F * (float)((com.maddox.il2.objects.air.Aircraft)FM.actor).aircIndex();
                StabAltitude = WPoint.z;
                if(WWPoint.Action == 3)
                {
                    com.maddox.il2.engine.Actor actor = WWPoint.getTarget();
                    if(actor != null)
                        FM.target_ground = null;
                    else
                    if(((com.maddox.il2.objects.air.Aircraft)FM.actor instanceof com.maddox.il2.objects.air.TypeBomber) && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets())
                    {
                        FM.CT.BayDoorControl = 1.0F;
                        for(com.maddox.il2.ai.air.Pilot pilot1 = FM; pilot1.Wingman != null;)
                        {
                            pilot1 = (com.maddox.il2.ai.air.Pilot)pilot1.Wingman;
                            pilot1.CT.BayDoorControl = 1.0F;
                        }

                    }
                } else
                {
                    if((com.maddox.il2.objects.air.Aircraft)FM.actor instanceof com.maddox.il2.objects.air.TypeBomber)
                    {
                        FM.CT.BayDoorControl = 0.0F;
                        for(com.maddox.il2.ai.air.Pilot pilot = FM; pilot.Wingman != null;)
                        {
                            pilot = (com.maddox.il2.ai.air.Pilot)pilot.Wingman;
                            pilot.CT.BayDoorControl = 0.0F;
                        }

                    }
                    com.maddox.il2.engine.Actor actor1 = WWPoint.getTarget();
                    if(actor1 instanceof com.maddox.il2.objects.air.Aircraft)
                        if(actor1.getArmy() == FM.actor.getArmy())
                            FM.airClient = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor1).FM;
                        else
                            FM.target = ((com.maddox.il2.objects.air.Aircraft)actor1).FM;
                }
                if(way.isLanding())
                {
                    FM.getLoc(P);
                    if(way.Cur() > 3 && P.z > WPoint.z + 500D)
                        way.setCur(1);
                    if(way.Cur() == 5)
                        com.maddox.il2.objects.sounds.Voice.speakLanding((com.maddox.il2.objects.air.Aircraft)FM.actor);
                    if(way.Cur() == 6 || way.Cur() == 7)
                    {
                        int i = 0;
                        if(com.maddox.il2.engine.Actor.isAlive(way.landingAirport))
                            i = way.landingAirport.landingFeedback(WPoint, (com.maddox.il2.objects.air.Aircraft)FM.actor);
                        if(i == 0)
                            com.maddox.il2.objects.sounds.Voice.speakLandingPermited((com.maddox.il2.objects.air.Aircraft)FM.actor);
                        if(i == 1)
                        {
                            com.maddox.il2.objects.sounds.Voice.speakLandingDenied((com.maddox.il2.objects.air.Aircraft)FM.actor);
                            way.first();
                            FM.push(2);
                            FM.push(2);
                            FM.push(2);
                            FM.push(2);
                            FM.pop();
                            com.maddox.il2.objects.sounds.Voice.speakGoAround((com.maddox.il2.objects.air.Aircraft)FM.actor);
                            FM.CT.FlapsControl = 0.4F;
                            FM.CT.GearControl = 0.0F;
                            return;
                        }
                        if(i == 2)
                        {
                            com.maddox.il2.objects.sounds.Voice.speakWaveOff((com.maddox.il2.objects.air.Aircraft)FM.actor);
                            if(FM.isReadyToReturn())
                            {
                                com.maddox.il2.objects.sounds.Voice.speakGoingIn((com.maddox.il2.objects.air.Aircraft)FM.actor);
                                FM.AS.setCockpitDoor(FM.actor, 1);
                                FM.CT.GearControl = 1.0F;
                                return;
                            } else
                            {
                                way.first();
                                FM.push(2);
                                FM.push(2);
                                FM.push(2);
                                FM.push(2);
                                FM.pop();
                                FM.CT.FlapsControl = 0.4F;
                                FM.CT.GearControl = 0.0F;
                                com.maddox.il2.objects.air.Aircraft.debugprintln(FM.actor, "Going around!.");
                                return;
                            }
                        }
                        FM.CT.GearControl = 1.0F;
                    }
                }
            }
            if(way.isLanding() && way.Cur() < 6 && way.getCurDist() < 800D)
                way.next();
            if((way.Cur() == way.size() - 1 && getWayPointDistance() < 2000F && way.curr().getTarget() == null && FM.M.fuel < 0.2F * FM.M.maxFuel || way.curr().Action == 2) && !way.isLanding())
            {
                com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.makeLandWay(FM);
                if(airport != null)
                {
                    WWPoint = null;
                    way.first();
                    update(f);
                    return;
                }
                FM.set_task(3);
                FM.set_maneuver(49);
                FM.setBusy(true);
            }
            StabDirection = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(WPoint.y - PlLoc.y, WPoint.x - PlLoc.x));
        }
        if(bStabSpeed || bWayPoint)
        {
            Pw = 0.3F - 0.04F * (FM.getSpeed() - (float)StabSpeed);
            if(Pw > 1.0F)
                Pw = 1.0F;
            else
            if(Pw < 0.0F)
                Pw = 0.0F;
        }
        if(bStabAltitude || bWayPoint)
        {
            Ev = FM.CT.ElevatorControl;
            double d = SA - FM.getAltitude();
            double d1 = 0.0D;
            double d2 = 0.0D;
            if(d > -50D)
            {
                float f4 = 5F + 0.00025F * FM.getAltitude();
                f4 = (float)((double)f4 + 0.02D * (250D - (double)FM.Vmax));
                if(f4 > 14F)
                    f4 = 14F;
                d1 = java.lang.Math.min(FM.getAOA() - f4, FM.Or.getTangage() - 1.0F) * 1.0F * f + 0.5F * FM.getForwAccel();
            }
            if(d < 50D)
            {
                float f5 = -15F + FM.M.mass * 0.00033F;
                if(f5 < -4F)
                    f5 = -4F;
                d2 = (FM.Or.getTangage() - f5) * 0.8F * f;
            }
            double d3 = 0.01D * (d + 50D);
            if(d3 > 1.0D)
                d3 = 1.0D;
            if(d3 < 0.0D)
                d3 = 0.0D;
            Ev -= d3 * d1 + (1.0D - d3) * d2;
            Ev += 1.0D * FM.getW().y + 0.5D * FM.getAW().y;
            if(FM.getSpeed() < 1.3F * FM.VminFLAPS)
                Ev -= 0.004F * f;
            float f6 = (9F * FM.getSpeed()) / FM.VminFLAPS;
            if(FM.VminFLAPS < 28F)
                f6 = 10F;
            if(f6 > 25F)
                f6 = 25F;
            float f7 = (f6 - FM.Or.getTangage()) * 0.1F;
            float f8 = -15F + FM.M.mass * 0.00033F;
            if(f8 < -4F)
                f8 = -4F;
            float f9 = (f8 - FM.Or.getTangage()) * 0.2F;
            if(Ev > f7)
                Ev = f7;
            if(Ev < f9)
                Ev = f9;
            FM.CT.ElevatorControl = 0.8F * FM.CT.ElevatorControl + 0.2F * Ev;
        }
        float f1 = 0.0F;
        if(bStabDirection || bWayPoint)
        {
            f1 = FM.Or.getAzimut();
            float f2 = FM.Or.getKren();
            f1 = (float)((double)f1 - StabDirection);
            f1 = (f1 + 3600F) % 360F;
            f2 = (f2 + 3600F) % 360F;
            if(f1 > 180F)
                f1 -= 360F;
            if(f2 > 180F)
                f2 -= 360F;
            float f3 = ((FM.getSpeed() - FM.VminFLAPS) * 3.6F + FM.getVertSpeed() * 40F) * 0.25F;
            if(way.isLanding())
                f3 = 65F;
            if(f3 < 15F)
                f3 = 15F;
            else
            if(f3 > 65F)
                f3 = 65F;
            if(f1 < -f3)
                f1 = -f3;
            else
            if(f1 > f3)
                f1 = f3;
            Ail = -0.01F * (f1 + f2 + 3F * (float)FM.getW().x + 0.5F * (float)FM.getAW().x);
            if(Ail > 1.0F)
                Ail = 1.0F;
            else
            if(Ail < -1F)
                Ail = -1F;
            WPoint.get(Ve);
            Ve.sub(FM.Loc);
            FM.Or.transformInv(Ve);
            if(java.lang.Math.abs(Ve.y) < 25D && java.lang.Math.abs(Ve.x) < 150D)
                FM.CT.AileronControl = -0.01F * FM.Or.getKren();
            else
                FM.CT.AileronControl = Ail;
            FM.CT.ElevatorControl += java.lang.Math.abs(f2) * 0.004F * f;
            FM.CT.RudderControl -= FM.getAOS() * 0.04F * f;
        }
        if(bWayPoint && way.isLanding())
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                FM.doDumpBombsPassively();
            if(way.Cur() > 5)
                FM.set_maneuver(25);
            FM.CT.RudderControl -= f1 * 0.04F * f;
            landUpdate(f);
        }
    }

    private void landUpdate(float f)
    {
        if(((FM.getAltitude() - 10F) + FM.getVertSpeed() * 5F) - SA > 0.0F)
        {
            if(FM.Vwld.z > -10D)
                FM.Vwld.z -= 1.0F * f;
        } else
        if(FM.Vwld.z < 10D)
            FM.Vwld.z += 1.0F * f;
        if(FM.getAOA() > 11F && FM.CT.ElevatorControl > -0.3F)
            FM.CT.ElevatorControl -= 0.3F * f;
    }

    public boolean bWayPoint;
    public boolean bStabAltitude;
    public boolean bStabSpeed;
    public boolean bStabDirection;
    protected double StabAltitude;
    protected double StabSpeed;
    protected double StabDirection;
    protected com.maddox.il2.ai.air.Pilot FM;
    protected com.maddox.il2.ai.WayPoint WWPoint;
    protected com.maddox.JGP.Point3d WPoint;
    private static com.maddox.JGP.Point3d P = new Point3d();
    private static com.maddox.JGP.Point3d PlLoc = new Point3d();
    private static com.maddox.il2.engine.Orientation O = new Orientation();
    private float Ail;
    private float Pw;
    private float Ru;
    private float Ev;
    private float SA;
    private com.maddox.JGP.Vector3d Ve;

}
