// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Autopilot.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AutopilotAI;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orientation;

// Referenced classes of package com.maddox.il2.fm:
//            Stabilizer, RealFlightModel, Controls, Wind, 
//            FMMath, FlightModel

public class Autopilot extends com.maddox.il2.ai.air.AutopilotAI
{

    Autopilot(com.maddox.il2.fm.FlightModel flightmodel)
    {
        super(flightmodel);
        Hstab = new Stabilizer();
        Hvstab = new Stabilizer();
        Vstab = new Stabilizer();
        Sstab = new Stabilizer();
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
        super.setWayPoint(flag);
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
        Vstab.set((float)StabSpeed, -FM.CT.ElevatorControl);
        Hvstab.set((float)StabAltitude, FM.CT.PowerControl);
    }

    public void setStabAltitude(boolean flag)
    {
        super.setStabAltitude(flag);
        bStabAltitude = flag;
        if(!flag)
            return;
        bWayPoint = false;
        FM.getLoc(P);
        StabAltitude = P.z;
        if(!bStabSpeed)
            StabSpeed = FM.getSpeed();
        Pw = FM.CT.PowerControl;
        Hstab.set((float)StabAltitude, FM.CT.ElevatorControl);
        Hvstab.set((float)StabAltitude, FM.CT.PowerControl);
    }

    public void setStabAltitudeSimple(float f)
    {
        super.setStabAltitude(f);
    }

    public void setStabSpeed(boolean flag)
    {
        super.setStabSpeed(flag);
        bStabSpeed = flag;
        if(!flag)
        {
            return;
        } else
        {
            bWayPoint = false;
            StabSpeed = FM.getSpeed();
            Vstab.set((float)StabSpeed, -FM.CT.ElevatorControl);
            return;
        }
    }

    public void setStabSpeed(float f)
    {
        super.setStabSpeed(f);
        bStabSpeed = true;
        bWayPoint = false;
        StabSpeed = f / 3.6F;
        Vstab.set((float)StabSpeed, -FM.CT.ElevatorControl);
    }

    public void setStabDirection(boolean flag)
    {
        super.setStabDirection(flag);
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
            Sstab.set(0.0F, 0.0F);
            return;
        }
    }

    public void setStabAll(boolean flag)
    {
        super.setStabAll(flag);
        bWayPoint = false;
        setStabDirection(flag);
        setStabAltitude(flag);
        setStabSpeed(flag);
        setStabDirection(flag);
    }

    public float getWayPointDistance()
    {
        return super.getWayPointDistance();
    }

    public void update(float f)
    {
label0:
        {
            if(!((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
            {
                super.update(f);
                return;
            }
            FM.getLoc(P);
            if(bWayPoint)
            {
                if(WWPoint != way.auto(P) || way.isReached(P))
                {
                    WWPoint = way.auto(P);
                    WWPoint.getP(WPoint);
                    StabSpeed = WWPoint.Speed;
                    StabAltitude = WPoint.z;
                    Vstab.set((float)StabSpeed, -FM.CT.ElevatorControl);
                    Hvstab.set((float)StabAltitude, FM.CT.PowerControl);
                }
                if(com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence)
                {
                    com.maddox.il2.ai.World.cur();
                    if(!com.maddox.il2.ai.World.wind().noWind && FM.Skill > 0)
                    {
                        com.maddox.il2.ai.World.cur();
                        com.maddox.il2.ai.World.wind().getVectorAI(WPoint, windV);
                        windV.scale(-1D);
                        if(FM.Skill == 1)
                            windV.scale(0.75D);
                        courseV.set(WPoint.x - P.x, WPoint.y - P.y, 0.0D);
                        courseV.normalize();
                        courseV.scale(FM.getSpeed());
                        courseV.add(windV);
                        StabDirection = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(courseV.y, courseV.x));
                        break label0;
                    }
                }
                StabDirection = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(WPoint.y - P.y, WPoint.x - P.x));
            } else
            {
                way.auto(P);
            }
        }
        if(FM.isTick(256, 0) && !FM.actor.isTaskComplete() && (way.isLast() && getWayPointDistance() < 1500F || way.isLanding()))
            com.maddox.il2.ai.World.onTaskComplete(FM.actor);
        if(bStabSpeed || bWayPoint)
        {
            FM.CT.ElevatorControl = -Vstab.getOutput(FM.getSpeed());
            if(FM.Or.getTangage() > 10F && FM.CT.ElevatorControl > 0.0F)
                FM.CT.ElevatorControl = 0.0F;
            if(FM.Or.getTangage() < -10F && FM.CT.ElevatorControl < -0F)
                FM.CT.ElevatorControl = 0.0F;
            if(bStabAltitude || bWayPoint)
            {
                Pw = Hvstab.getOutput(FM.getAltitude());
                if(Pw < 0.0F)
                    Pw = 0.0F;
                FM.CT.setPowerControl(Pw);
            }
        } else
        if(bStabAltitude)
            FM.CT.ElevatorControl = Hstab.getOutput(FM.getAltitude());
        if(bStabDirection || bWayPoint)
        {
            float f2 = FM.Or.getAzimut();
            float f1 = FM.Or.getKren();
            f2 = (float)((double)f2 - StabDirection);
            f2 = (f2 + 3600F) % 360F;
            f1 = (f1 + 3600F) % 360F;
            if(f2 > 180F)
                f2 -= 360F;
            if(f1 > 180F)
                f1 -= 360F;
            float f3 = FM.getSpeedKMH() * 0.15F + FM.getVertSpeed();
            if(f3 > 70F)
                f3 = 70F;
            if(f2 < -f3)
                f2 = -f3;
            else
            if(f2 > f3)
                f2 = f3;
            Ail = -0.05F * (f2 + f1);
            if(Ail > 1.0F)
                Ail = 1.0F;
            else
            if(Ail < -1F)
                Ail = -1F;
            FM.CT.AileronControl = Ail;
            Ru = -FM.getAOS() * 0.2F;
            if(Ru > 1.0F)
                Ru = 1.0F;
            else
            if(Ru < -1F)
                Ru = -1F;
            FM.CT.RudderControl = FM.CT.RudderControl + Ru * 0.0003F;
        }
    }

    private static com.maddox.JGP.Point3d P = new Point3d();
    private static com.maddox.il2.engine.Orientation O = new Orientation();
    private com.maddox.il2.fm.Stabilizer Hstab;
    private com.maddox.il2.fm.Stabilizer Hvstab;
    private com.maddox.il2.fm.Stabilizer Vstab;
    private com.maddox.il2.fm.Stabilizer Sstab;
    private float Ail;
    private float Pw;
    private float Ru;

}
