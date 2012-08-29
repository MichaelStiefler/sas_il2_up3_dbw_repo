// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Gear.java

package com.maddox.il2.fm;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.util.AbstractCollection;

// Referenced classes of package com.maddox.il2.fm:
//            RealFlightModel, FlightModel, Mass, Atmosphere, 
//            AircraftState, Controls, Arm, EnginesInterface, 
//            Motor

public class Gear
{
    private static class PlateFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d1)
        {
            if(!(actor instanceof com.maddox.il2.objects.buildings.Plate))
                return true;
            com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
            mesh.getBoundBox(com.maddox.il2.fm.Gear.plateBox);
            com.maddox.il2.fm.Gear.corn1.set(com.maddox.il2.fm.Gear.corn);
            com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
            loc.transformInv(com.maddox.il2.fm.Gear.corn1);
            if((double)(com.maddox.il2.fm.Gear.plateBox[0] - 2.5F) < com.maddox.il2.fm.Gear.corn1.x && com.maddox.il2.fm.Gear.corn1.x < (double)(com.maddox.il2.fm.Gear.plateBox[3] + 2.5F) && (double)(com.maddox.il2.fm.Gear.plateBox[1] - 2.5F) < com.maddox.il2.fm.Gear.corn1.y && com.maddox.il2.fm.Gear.corn1.y < (double)(com.maddox.il2.fm.Gear.plateBox[4] + 2.5F))
            {
                com.maddox.il2.fm.Gear.bPlateExist = true;
                com.maddox.il2.fm.Gear.bPlateGround = ((com.maddox.il2.objects.buildings.Plate)actor).isGround();
            }
            return true;
        }

        private PlateFilter()
        {
        }

    }

    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d1)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }


    public Gear()
    {
        onGround = false;
        nearGround = false;
        nOfGearsOnGr = 0;
        nOfPoiOnGr = 0;
        oldNOfGearsOnGr = 0;
        oldNOfPoiOnGr = 0;
        nP = 0;
        gearsChanged = false;
        clpEff = new com.maddox.il2.engine.Eff3DActor[64];
        clpEngineEff = new com.maddox.il2.engine.Eff3DActor[8][2];
        effectName = new String();
        bTheBabysGonnaBlow = false;
        lgear = true;
        rgear = true;
        cgear = true;
        bIsHydroOperable = true;
        bIsOperable = true;
        bTailwheelLocked = false;
        steerAngle = 0.0F;
        roughness = 0.5D;
        arrestorVAngle = 0.0F;
        arrestorVSink = 0.0F;
        arrestorHook = null;
        waterList = null;
        isGearColl = false;
        MassCoeff = 1.0D;
        bFrontWheel = false;
        bUnderDeck = false;
        bIsMaster = true;
        fatigue = new int[2];
        p0 = new Point3d();
        p1 = new Point3d();
        l0 = new Loc();
        v0 = new Vector3d();
        tmpV = new Vector3d();
        tmpV1 = new Vector3d();
        fric = 0.0D;
        fricF = 0.0D;
        fricR = 0.0D;
        maxFric = 0.0D;
        screenHQ = 0.0D;
        canDoEffect = true;
    }

    public boolean onGround()
    {
        return onGround;
    }

    public boolean nearGround()
    {
        return nearGround;
    }

    public boolean isHydroOperable()
    {
        return bIsHydroOperable;
    }

    public void setHydroOperable(boolean flag)
    {
        bIsHydroOperable = flag;
    }

    public boolean isOperable()
    {
        return bIsOperable;
    }

    public void setOperable(boolean flag)
    {
        bIsOperable = flag;
    }

    public float getSteeringAngle()
    {
        return steerAngle;
    }

    public boolean isUnderDeck()
    {
        return bUnderDeck;
    }

    public boolean getWheelsOnGround()
    {
        boolean flag = isGearColl;
        isGearColl = false;
        return flag;
    }

    public void set(com.maddox.il2.engine.HierMesh hiermesh)
    {
        HM = hiermesh;
        if(pnti == null)
        {
            int i;
            for(i = 0; i < 61; i++)
                if(HM.hookFind("_Clip" + s(i)) < 0)
                    break;

            pnti = new int[i + 3];
            pnti[0] = HM.hookFind("_ClipLGear");
            pnti[1] = HM.hookFind("_ClipRGear");
            pnti[2] = HM.hookFind("_ClipCGear");
            for(int j = 3; j < pnti.length; j++)
                pnti[j] = HM.hookFind("_Clip" + s(j - 3));

        }
        if(arrestorHook == null && hiermesh.hookFind("_ClipAGear") != -1)
            arrestorHook = new HookNamed(hiermesh, "_ClipAGear");
        int k = pnti[2];
        if(k > 0)
        {
            HM.hookMatrix(k, M4);
            if(M4.m03 > -1D)
                bFrontWheel = true;
        }
    }

    public void computePlaneLandPose(com.maddox.il2.fm.FlightModel flightmodel)
    {
        FM = flightmodel;
        if(H != 0.0F && Pitch != 0.0F)
            return;
        for(int i = 0; i < 3; i++)
            if(pnti[i] < 0)
                return;

        HM.hookMatrix(pnti[2], M4);
        double d1 = M4.m03;
        double d2 = M4.m23;
        HM.hookMatrix(pnti[0], M4);
        double d3 = M4.m03;
        double d4 = M4.m23;
        HM.hookMatrix(pnti[1], M4);
        d3 = (d3 + M4.m03) * 0.5D;
        d4 = (d4 + M4.m23) * 0.5D;
        double d5 = d3 - d1;
        double d6 = d4 - d2;
        Pitch = -com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.atan2(d6, d5));
        if(d5 < 0.0D)
            Pitch += 180F;
        com.maddox.JGP.Line2f line2f = new Line2f();
        line2f.set(new Point2f((float)d3, (float)d4), new Point2f((float)d1, (float)d2));
        H = line2f.distance(new Point2f(0.0F, 0.0F));
        H -= (double)((FM.M.massEmpty + FM.M.maxFuel + FM.M.maxNitro) * com.maddox.il2.fm.Atmosphere.g()) / 2700000D;
    }

    public void set(com.maddox.il2.fm.Gear gear)
    {
        if(gear.pnti == null)
            return;
        pnti = new int[gear.pnti.length];
        if(gear.waterList != null)
        {
            waterList = new int[gear.waterList.length];
            for(int i = 0; i < waterList.length; i++)
                waterList[i] = gear.waterList[i];

        }
        for(int j = 0; j < pnti.length; j++)
            pnti[j] = gear.pnti[j];

        bIsSail = gear.bIsSail;
        sinkFactor = gear.sinkFactor;
        springsStiffness = gear.springsStiffness;
        H = gear.H;
        Pitch = gear.Pitch;
        bFrontWheel = gear.bFrontWheel;
    }

    public void ground(com.maddox.il2.fm.FlightModel flightmodel, boolean flag)
    {
        ground(flightmodel, flag, false);
    }

    public void ground(com.maddox.il2.fm.FlightModel flightmodel, boolean flag, boolean flag1)
    {
        FM = flightmodel;
        bIsMaster = flag;
        onGround = flag1;
        FM.Vrel.x = -FM.Vwld.x;
        FM.Vrel.y = -FM.Vwld.y;
        FM.Vrel.z = -FM.Vwld.z;
        for(int i = 0; i < 2; i++)
            if(fatigue[i] > 0)
                fatigue[i]--;

        Pn.set(FM.Loc);
        Pn.z = com.maddox.il2.engine.Engine.cur.land.HQ(Pn.x, Pn.y);
        double d1 = Pn.z;
        screenHQ = d1;
        if(FM.Loc.z - d1 > 50D && !bFlatTopGearCheck)
        {
            turnOffEffects();
            arrestorVSink = -50F;
            return;
        }
        isWater = com.maddox.il2.engine.Engine.cur.land.isWater(Pn.x, Pn.y);
        if(isWater)
            roughness = 0.5D;
        D0 = com.maddox.il2.engine.Engine.cur.land.EQN(Pn.x, Pn.y, Normal);
        bUnderDeck = false;
        com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = null;
        if(bFlatTopGearCheck)
        {
            corn.set(FM.Loc);
            corn1.set(FM.Loc);
            corn1.z -= 20D;
            com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(corn, corn1, false, clipFilter, Pship);
            if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
            {
                Pn.z = Pship.z + 0.5D;
                d1 = Pn.z;
                isWater = false;
                bUnderDeck = true;
                actor.getSpeed(Vship);
                FM.Vrel.add(Vship);
                bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)actor;
                bigshipgeneric.addRockingSpeed(FM.Vrel, Normal, FM.Loc);
                if(flightmodel.AS.isMaster() && bigshipgeneric.getAirport() != null && flightmodel.CT.bHasArrestorControl)
                {
                    if(!bigshipgeneric.isTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor) && FM.Vrel.lengthSquared() > 10D && flightmodel.CT.getArrestor() > 0.1F)
                    {
                        bigshipgeneric.requestTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
                        if(bigshipgeneric.isTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor))
                        {
                            flightmodel.AS.setFlatTopString(bigshipgeneric, bigshipgeneric.towPortNum);
                            if((FM instanceof com.maddox.il2.fm.RealFlightModel) && bIsMaster && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                                ((com.maddox.il2.fm.RealFlightModel)FM).producedShakeLevel = 5F;
                            ((com.maddox.il2.objects.air.Aircraft)flightmodel.actor).sfxTow();
                        }
                    }
                    if(bigshipgeneric.isTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor) && FM.Vrel.lengthSquared() < 1.0D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.008F)
                    {
                        bigshipgeneric.requestDetowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
                        flightmodel.AS.setFlatTopString(bigshipgeneric, -1);
                    }
                }
                if(bigshipgeneric.isTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor))
                {
                    int k = bigshipgeneric.towPortNum;
                    com.maddox.JGP.Point3d apoint3d[] = bigshipgeneric.getShipProp().propAirport.towPRel;
                    bigshipgeneric.pos.getAbs(l0);
                    l0.transform(apoint3d[k * 2], p0);
                    l0.transform(apoint3d[k * 2 + 1], p1);
                    p0.x = 0.5D * (p0.x + p1.x);
                    p0.y = 0.5D * (p0.y + p1.y);
                    p0.z = 0.5D * (p0.z + p1.z);
                    flightmodel.actor.pos.getAbs(l0);
                    l0.transformInv(p0);
                    l0.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    bigshipgeneric.towHook.computePos(flightmodel.actor, new Loc(l0), l0);
                    v0.sub(p0, l0.getPoint());
                    if(v0.x > 0.0D)
                    {
                        if(bigshipgeneric.isTowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor))
                        {
                            bigshipgeneric.requestDetowAircraft((com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
                            flightmodel.AS.setFlatTopString(bigshipgeneric, -1);
                        }
                    } else
                    {
                        tmpV.set(FM.Vrel);
                        flightmodel.actor.pos.getAbsOrient().transformInv(tmpV);
                        if(tmpV.x < 0.0D)
                        {
                            double d3 = v0.length();
                            v0.normalize();
                            arrestorVAngle = (float)java.lang.Math.toDegrees(java.lang.Math.asin(v0.z));
                            v0.scale(1000D * d3);
                            flightmodel.GF.add(v0);
                            v0.scale(0.29999999999999999D);
                            v0.cross(l0.getPoint(), v0);
                            flightmodel.GM.add(v0);
                        }
                    }
                } else
                {
                    arrestorVAngle = 0.0F;
                }
            }
        }
        if(flightmodel.CT.bHasArrestorControl)
        {
            flightmodel.actor.pos.getAbs(com.maddox.il2.objects.air.Aircraft.tmpLoc1);
            com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            arrestorHook.computePos(flightmodel.actor, com.maddox.il2.objects.air.Aircraft.tmpLoc1, loc);
            arrestorVSink = (float)(Pn.z - loc.getPoint().z);
        }
        Fd.set(FM.Vrel);
        Vnf.set(Normal);
        FM.Or.transformInv(Normal);
        FM.Or.transformInv(Fd);
        Fd.normalize();
        Pn.x = 0.0D;
        Pn.y = 0.0D;
        Pn.z -= FM.Loc.z;
        FM.Or.transformInv(Pn);
        D = -Normal.dot(Pn);
        if(!bIsMaster)
            D -= 0.014999999999999999D;
        if(D > 50D)
        {
            nearGround = false;
            return;
        }
        nearGround = true;
        gWheelSinking[0] = gWheelSinking[1] = gWheelSinking[2] = 0.0F;
        for(int j = 0; j < pnti.length; j++)
        {
            int l = pnti[j];
            if(l <= 0)
            {
                Pnt[j].set(0.0F, 0.0F, 0.0F);
            } else
            {
                HM.hookMatrix(l, M4);
                Pnt[j].set((float)M4.m03, (float)M4.m13, (float)M4.m23);
            }
        }

        nP = 0;
        nOfGearsOnGr = 0;
        nOfPoiOnGr = 0;
        tmpV.set(0.0D, 1.0D, 0.0D);
        Forward.cross(tmpV, Normal);
        Forward.normalize();
        Right.cross(Normal, Forward);
        for(int i1 = 0; i1 < pnti.length; i1++)
        {
            clp[i1] = false;
            if(i1 <= 2)
            {
                bIsGear = true;
                if(i1 == 0 && (!lgear || FM.CT.getGear() < 0.01F) || i1 == 1 && (!rgear || FM.CT.getGear() < 0.01F) || i1 == 2 && !cgear)
                    continue;
            } else
            {
                bIsGear = false;
            }
            PnT.set(Pnt[i1]);
            d = Normal.dot(PnT) + D;
            Fx.set(Fd);
            MassCoeff = 0.00040000000000000002D * (double)FM.M.getFullMass();
            if(MassCoeff > 1.0D)
                MassCoeff = 1.0D;
            if(d < 0.0D)
            {
                if(!testPropellorCollision(i1) || (isWater ? !testWaterCollision(i1) : bIsGear ? !testGearCollision(i1) : !testNonGearCollision(i1)))
                    continue;
                clp[i1] = true;
                nP++;
            } else
            {
                if(d >= 0.10000000000000001D || isWater || bIsGear || !testNonGearCollision(i1))
                    continue;
                clp[i1] = true;
                nP++;
            }
            PnT.x += FM.Arms.GC_GEAR_SHIFT;
            Fx.cross(PnT, Tn);
            Fv.set(Fx);
            if(bIsSail && bInWaterList(i1))
            {
                tmpV.scale(fricF * 0.5D, Forward);
                Tn.add(tmpV);
                tmpV.scale(fricR * 0.5D, Right);
                Tn.add(tmpV);
            }
            if(bIsMaster)
            {
                FM.GF.add(Tn);
                FM.GM.add(Fx);
            }
        }

        if(oldNOfGearsOnGr != nOfGearsOnGr || oldNOfPoiOnGr != nOfPoiOnGr)
            gearsChanged = true;
        else
            gearsChanged = false;
        oldNOfGearsOnGr = nOfGearsOnGr;
        oldNOfPoiOnGr = nOfPoiOnGr;
        onGround = nP > 0;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            drawEffects();
        if(bIsMaster)
        {
            FM.canChangeBrakeShoe = false;
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric1 = bigshipgeneric;
            if(bigshipgeneric1 != null)
                FM.brakeShoeLastCarrier = bigshipgeneric1;
            else
            if(com.maddox.il2.game.Mission.isCoop() && !com.maddox.il2.game.Mission.isServer() && com.maddox.il2.engine.Actor.isAlive(FM.brakeShoeLastCarrier) && com.maddox.rts.Time.current() < 60000L)
                bigshipgeneric1 = (com.maddox.il2.objects.ships.BigshipGeneric)FM.brakeShoeLastCarrier;
            if(bigshipgeneric1 != null)
                if(FM.brakeShoe)
                {
                    if(!isAnyDamaged())
                    {
                        L.set(FM.brakeShoeLoc);
                        L.add(FM.brakeShoeLastCarrier.pos.getAbs());
                        FM.Loc.set(L.getPoint());
                        FM.Or.set(L.getOrient());
                        FM.brakeShoeLastCarrier.getSpeed(FM.Vwld);
                        FM.Vrel.set(0.0D, 0.0D, 0.0D);
                        for(int j1 = 0; j1 < 3; j1++)
                            gVelocity[j1] = 0.0D;

                        onGround = true;
                        FM.canChangeBrakeShoe = true;
                    } else
                    {
                        FM.brakeShoe = false;
                    }
                } else
                if(nOfGearsOnGr == 3 && nP == 3 && FM.Vrel.lengthSquared() < 1.0D)
                {
                    FM.brakeShoeLoc.set(FM.actor.pos.getCurrent());
                    FM.brakeShoeLoc.sub(FM.brakeShoeLastCarrier.pos.getCurrent());
                    FM.canChangeBrakeShoe = true;
                }
        }
        if(!bIsMaster)
            return;
        if(onGround && !isWater)
            processingCollisionEffect();
        double d2 = com.maddox.il2.engine.Engine.cur.land.HQ_ForestHeightHere(FM.Loc.x, FM.Loc.y);
        if(d2 > 0.0D && FM.Loc.z <= d1 + d2 && ((com.maddox.il2.objects.air.Aircraft)FM.actor).isEnablePostEndAction(0.0D))
            ((com.maddox.il2.objects.air.Aircraft)FM.actor).postEndAction(0.0D, com.maddox.il2.engine.Engine.actorLand(), 2, null);
    }

    private boolean testNonGearCollision(int i)
    {
        nOfPoiOnGr++;
        Vs.set(FM.Vrel);
        Vs.scale(-1D);
        FM.Or.transformInv(Vs);
        tmpV.set(Pnt[i]);
        tmpV.cross(FM.getW(), tmpV);
        Vs.add(tmpV);
        ForwardVPrj = Forward.dot(Vs);
        NormalVPrj = Normal.dot(Vs);
        RightVPrj = Right.dot(Vs);
        if(NormalVPrj > 0.0D)
        {
            NormalVPrj -= 3D;
            if(NormalVPrj < 0.0D)
                NormalVPrj = 0.0D;
        }
        double d1 = 1.0D;
        double d2 = d - 0.059999999999999998D;
        double d3 = d + 0.040000000000000008D;
        if(d2 > 0.0D)
            d2 = 0.0D;
        if(d2 < -2D)
            d2 = -2D;
        if(d3 > 0.0D)
            d3 = 0.0D;
        if(d3 < -0.25D)
            d3 = -0.25D;
        d1 = java.lang.Math.max(-120000D * d2, -360000D * d3);
        d1 *= MassCoeff;
        Tn.scale(d1, Normal);
        fric = -40000D * NormalVPrj;
        if(fric > 100000D)
            fric = 100000D;
        if(fric < -100000D)
            fric = -100000D;
        tmpV.scale(fric, Normal);
        Tn.add(tmpV);
        double d4 = 1.0D - (0.5D * (double)java.lang.Math.abs(Pnt[i].y)) / (double)FM.Arms.WING_END;
        fricF = -8000D * ForwardVPrj;
        fricR = -50000D * RightVPrj;
        fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
        if(fric > 20000D * d4)
        {
            fric = (20000D * d4) / fric;
            fricF *= fric;
            fricR *= fric;
        }
        tmpV.scale(fricF, Forward);
        Tn.add(tmpV);
        tmpV.scale(fricR, Right);
        Tn.add(tmpV);
        if(i > 6 && bIsMaster)
        {
            com.maddox.il2.ai.World.cur();
            if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft() && FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) < 2D && !bTheBabysGonnaBlow)
            {
                for(int j = 0; j < FM.CT.Weapons.length; j++)
                    if(FM.CT.Weapons[j] != null && FM.CT.Weapons[j].length > 0)
                    {
                        for(int k = 0; k < FM.CT.Weapons[j].length; k++)
                            if(((FM.CT.Weapons[j][k] instanceof com.maddox.il2.objects.weapons.BombGun) || (FM.CT.Weapons[j][k] instanceof com.maddox.il2.objects.weapons.RocketGun)) && FM.CT.Weapons[j][k].haveBullets() && FM.getSpeed() > 38F && FM.CT.Weapons[j][k].getHookName().startsWith("_External"))
                                bTheBabysGonnaBlow = true;

                    }

                if(bTheBabysGonnaBlow && (!FM.isPlayers() || com.maddox.il2.ai.World.cur().diffCur.Vulnerability) && ((com.maddox.il2.objects.air.Aircraft)FM.actor).isEnablePostEndAction(0.0D))
                {
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).postEndAction(0.0D, com.maddox.il2.engine.Engine.actorLand(), 2, null);
                    if(FM.isPlayers())
                        com.maddox.il2.game.HUD.log("FailedBombsDetonate");
                }
            }
        }
        if(bIsMaster && NormalVPrj < 0.0D)
        {
            double d5 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.00023668639053254438D + NormalVPrj * NormalVPrj * 0.020408163265306121D;
            if(d5 > 1.0D)
                landHit(i, (float)d5);
        }
        return true;
    }

    private boolean testGearCollision(int i)
    {
        if((double)FM.CT.getGear() < 0.01D)
            return false;
        double d1 = 1.0D;
        gWheelSinking[i] = (float)(-d);
        Vs.set(FM.Vrel);
        Vs.scale(-1D);
        FM.Or.transformInv(Vs);
        tmpV.set(Pnt[i]);
        tmpV.cross(FM.getW(), tmpV);
        Vs.add(tmpV);
        ForwardVPrj = Forward.dot(Vs);
        NormalVPrj = Normal.dot(Vs);
        RightVPrj = Right.dot(Vs);
        if(NormalVPrj > 0.0D)
            NormalVPrj = 0.0D;
        double d2 = (FM.Vrel.x * FM.Vrel.x + FM.Vrel.y * FM.Vrel.y) - 2D;
        if(d2 < 0.0D)
            d2 = 0.0D;
        double d3 = 0.01D * d2;
        if(d3 < 0.0D)
            d3 = 0.0D;
        if(d3 > 4.5D)
            d3 = 4.5D;
        double d4 = 0.40000000596046448D * java.lang.Math.max(roughness * roughness, roughness);
        if(d3 > d4)
            d3 = d4;
        if(roughness > d3)
            roughness = d3;
        if(roughness < 0.20000000298023224D)
            roughness = 0.20000000298023224D;
        if(i < 2)
        {
            d += (double)com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 1.0F) * 0.040000000000000001D * d3 * MassCoeff;
            d1 = java.lang.Math.max(-9500D * (d - 0.10000000000000001D), -950000D * d);
            d1 *= springsStiffness;
        } else
        {
            d += (double)(com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 1.0F) * 0.04F) * d3 * MassCoeff;
            d1 = java.lang.Math.max(-9500D * (d - 0.10000000000000001D), -950000D * d);
            if(Pnt[i].x > 0.0F && Fd.dot(Normal) >= 0.0D)
                d1 *= 0.44999998807907104D;
            else
                d1 *= tailStiffness;
        }
        d1 -= 40000D * NormalVPrj;
        Tn.scale(d1, Normal);
        double d5 = 0.0001D * d1;
        double d6 = FM.CT.getBrake();
        double d7 = FM.CT.getRudder();
        switch(i)
        {
        case 0: // '\0'
        case 1: // '\001'
            double d8 = 1.2D;
            if(i == 0)
                d8 = -1.2D;
            nOfGearsOnGr++;
            isGearColl = true;
            gVelocity[i] = ForwardVPrj;
            if(d6 > 0.10000000000000001D)
            {
                if(d7 > 0.10000000000000001D)
                    d6 += d8 * d6 * (d7 - 0.10000000000000001D);
                if(d7 < -0.10000000000000001D)
                    d6 += d8 * d6 * (d7 + 0.10000000000000001D);
                if(d6 > 1.0D)
                    d6 = 1.0D;
                if(d6 < 0.0D)
                    d6 = 0.0D;
            }
            double d9 = java.lang.Math.sqrt(ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj);
            if(d9 < 0.01D)
                d9 = 0.01D;
            double d11 = 1.0D / d9;
            double d13 = ForwardVPrj * d11;
            if(d13 < 0.0D)
                d13 *= -1D;
            double d14 = RightVPrj * d11;
            if(d14 < 0.0D)
                d14 *= -1D;
            double d15 = 5D;
            if(PnT.y * RightVPrj > 0.0D)
            {
                if(PnT.y > 0.0D)
                    d15 += 7D * RightVPrj;
                else
                    d15 -= 7D * RightVPrj;
                if(d15 > 20D)
                    d15 = 20D;
            }
            double d16 = 15000D;
            if(d9 < 3D)
            {
                double d17 = -0.33333299999999999D * (d9 - 3D);
                d16 += 3000D * d17 * d17;
            }
            fricR = -d15 * 100000D * RightVPrj * d5;
            maxFric = d16 * d5 * d14;
            if(fricR > maxFric)
                fricR = maxFric;
            if(fricR < -maxFric)
                fricR = -maxFric;
            fricF = -d15 * 600D * ForwardVPrj * d5;
            maxFric = d15 * java.lang.Math.max(200D * (1.0D - 0.040000000000000001D * d9), 5D) * d5 * d13;
            if(fricF > maxFric)
                fricF = maxFric;
            if(fricF < -maxFric)
                fricF = -maxFric;
            double d18 = 0.029999999999999999D;
            if(Pnt[2].x > 0.0F)
                d18 = 0.059999999999999998D;
            double d19 = java.lang.Math.abs(ForwardVPrj);
            if(d19 < 1.0D)
                d18 += 3D * (1.0D - d19);
            d18 *= 0.029999999999999999D * d6;
            fricF += -300000D * d18 * ForwardVPrj * d5;
            maxFric = d16 * d5 * d13;
            if(fricF > maxFric)
                fricF = maxFric;
            if(fricF < -maxFric)
                fricF = -maxFric;
            fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
            if(fric > maxFric)
            {
                fric = maxFric / fric;
                fricF *= fric;
                fricR *= fric;
            }
            tmpV.scale(fricF, Forward);
            Tn.add(tmpV);
            tmpV.scale(fricR, Right);
            Tn.add(tmpV);
            if(bIsMaster && NormalVPrj < 0.0D)
            {
                double d20 = ForwardVPrj * ForwardVPrj * 8.0000000000000007E-005D + RightVPrj * RightVPrj * 0.0067999999999999996D;
                if(FM.CT.bHasArrestorControl)
                    d20 += NormalVPrj * NormalVPrj * 0.025000000000000001D;
                else
                    d20 += NormalVPrj * NormalVPrj * 0.070000000000000007D;
                if(d20 > 1.0D)
                {
                    fatigue[i] += 10;
                    double d22 = 38000D + (double)FM.M.massEmpty * 6D;
                    double d23 = (Tn.x * Tn.x * 0.14999999999999999D + Tn.y * Tn.y * 0.14999999999999999D + Tn.z * Tn.z * 0.080000000000000002D) / (d22 * d22);
                    if(fatigue[i] > 100 || d23 > 1.0D)
                    {
                        landHit(i, (float)d20);
                        com.maddox.il2.objects.air.Aircraft aircraft1 = (com.maddox.il2.objects.air.Aircraft)FM.actor;
                        if(i == 0)
                            aircraft1.msgCollision(aircraft1, "GearL2_D0", "GearL2_D0");
                        if(i == 1)
                            aircraft1.msgCollision(aircraft1, "GearR2_D0", "GearR2_D0");
                    }
                }
            }
            break;

        case 2: // '\002'
            nOfGearsOnGr++;
            if(bTailwheelLocked && steerAngle > -5F && steerAngle < 5F)
            {
                gVelocity[i] = ForwardVPrj;
                steerAngle = 0.0F;
                fric = -400D * ForwardVPrj;
                maxFric = 400D;
                if(fric > maxFric)
                    fric = maxFric;
                if(fric < -maxFric)
                    fric = -maxFric;
                tmpV.scale(fric, Forward);
                Tn.add(tmpV);
                fric = -10000D * RightVPrj;
                maxFric = 40000D;
                if(fric > maxFric)
                    fric = maxFric;
                if(fric < -maxFric)
                    fric = -maxFric;
                tmpV.scale(fric, Right);
                Tn.add(tmpV);
            } else
            if(bFrontWheel)
            {
                gVelocity[i] = ForwardVPrj;
                tmpV.set(1.0D, -0.5D * (double)FM.CT.getRudder(), 0.0D);
                steerAngleFork.setDeg(steerAngle, (float)java.lang.Math.toDegrees(java.lang.Math.atan2(tmpV.y, tmpV.x)));
                steerAngle = steerAngleFork.getDeg(0.115F);
                nwRight.cross(Normal, tmpV);
                nwRight.normalize();
                nwForward.cross(nwRight, Normal);
                ForwardVPrj = nwForward.dot(Vs);
                RightVPrj = nwRight.dot(Vs);
                double d10 = java.lang.Math.sqrt(ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj);
                if(d10 < 0.01D)
                    d10 = 0.01D;
                double d12 = 1.0D / d10;
                fricF = -100D * ForwardVPrj;
                maxFric = 4000D;
                if(fricF > maxFric)
                    fricF = maxFric;
                if(fricF < -maxFric)
                    fricF = -maxFric;
                fricR = -500D * RightVPrj;
                maxFric = 4000D;
                if(fricR > maxFric)
                    fricR = maxFric;
                if(fricR < -maxFric)
                    fricR = -maxFric;
                maxFric = 1.0D - 0.02D * d10;
                if(maxFric < 0.10000000000000001D)
                    maxFric = 0.10000000000000001D;
                maxFric = 5000D * maxFric;
                fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
                if(fric > maxFric)
                {
                    fric = maxFric / fric;
                    fricF *= fric;
                    fricR *= fric;
                }
                tmpV.scale(fricF, Forward);
                Tn.add(tmpV);
                tmpV.scale(fricR, Right);
                Tn.add(tmpV);
            } else
            {
                gVelocity[i] = Vs.length();
                if(Vs.lengthSquared() > 0.040000000000000001D)
                {
                    steerAngleFork.setDeg(steerAngle, (float)java.lang.Math.toDegrees(java.lang.Math.atan2(Vs.y, Vs.x)));
                    steerAngle = steerAngleFork.getDeg(0.115F);
                }
                fricF = -1000D * ForwardVPrj;
                fricR = -1000D * RightVPrj;
                fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
                maxFric = 1500D;
                if(fric > maxFric)
                {
                    fric = maxFric / fric;
                    fricF *= fric;
                    fricR *= fric;
                }
                tmpV.scale(fricF, Forward);
                Tn.add(tmpV);
                tmpV.scale(fricR, Right);
                Tn.add(tmpV);
            }
            if(!bIsMaster || NormalVPrj >= 0.0D)
                break;
            double d21 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.0001D;
            if(FM.CT.bHasArrestorControl)
                d21 += NormalVPrj * NormalVPrj * 0.0040000000000000001D;
            else
                d21 += NormalVPrj * NormalVPrj * 0.02D;
            if(d21 > 1.0D)
            {
                landHit(i, (float)d21);
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)FM.actor;
                aircraft.msgCollision(aircraft, "GearC2_D0", "GearC2_D0");
            }
            break;

        default:
            fricF = -4000D * ForwardVPrj;
            fricR = -4000D * RightVPrj;
            fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
            if(fric > 10000D)
            {
                fric = 10000D / fric;
                fricF *= fric;
                fricR *= fric;
            }
            tmpV.scale(fricF, Forward);
            Tn.add(tmpV);
            tmpV.scale(fricR, Right);
            Tn.add(tmpV);
            break;
        }
        Tn.scale(MassCoeff);
        return true;
    }

    private boolean testWaterCollision(int i)
    {
        Vs.set(FM.Vrel);
        Vs.scale(-1D);
        FM.Or.transformInv(Vs);
        tmpV.set(Pnt[i]);
        tmpV.cross(FM.getW(), tmpV);
        Vs.add(tmpV);
        ForwardVPrj = Forward.dot(Vs);
        NormalVPrj = Normal.dot(Vs);
        RightVPrj = Right.dot(Vs);
        double d1 = ForwardVPrj;
        if(d1 < 0.0D)
            d1 = 0.0D;
        if((!bIsSail || !bInWaterList(i)) && d < -2D)
            d = -2D;
        double d2 = -(1.0D + 0.29999999999999999D * d1) * (double)sinkFactor * d * java.lang.Math.abs(d) * (1.0D + 0.29999999999999999D * java.lang.Math.sin((double)((long)(1 + i % 3) * com.maddox.rts.Time.current()) * 0.001D));
        double d3 = 0.0001D * d2;
        if(bIsSail && bInWaterList(i))
        {
            if(NormalVPrj > 0.0D)
                NormalVPrj = 0.0D;
            Tn.scale(d2, Normal);
            fric = -1000D * NormalVPrj;
            if(fric > 4000D)
                fric = 4000D;
            if(fric < -4000D)
                fric = -4000D;
            tmpV.scale(fric, Normal);
            Tn.add(tmpV);
            fricF = -40D * ForwardVPrj;
            fricR = -300D * RightVPrj;
            fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
            if(fric > 50000D)
            {
                fric = 50000D / fric;
                fricF *= fric;
                fricR *= fric;
            }
            tmpV.scale(fricF * 0.5D, Forward);
            Tn.add(tmpV);
            tmpV.scale(fricR * 0.5D, Right);
            Tn.add(tmpV);
        } else
        {
            Tn.scale(d2, Normal);
            fric = -1000D * NormalVPrj;
            if(fric > 4000D)
                fric = 4000D;
            if(fric < -4000D)
                fric = -4000D;
            tmpV.scale(fric, Normal);
            Tn.add(tmpV);
            fricF = -500D * ForwardVPrj;
            fricR = -800D * RightVPrj;
            fric = java.lang.Math.sqrt(fricF * fricF + fricR * fricR);
            if(fric > 50000D)
            {
                fric = 50000D / fric;
                fricF *= fric;
                fricR *= fric;
            }
            tmpV.scale(fricF, Forward);
            Tn.add(tmpV);
            tmpV.scale(fricR, Right);
            Tn.add(tmpV);
            if(sinkFactor > 1.0F && !bIsSail)
            {
                sinkFactor -= 0.4F * com.maddox.rts.Time.tickLenFs();
                if(sinkFactor < 1.0F)
                    sinkFactor = 1.0F;
            }
        }
        if(bIsMaster && NormalVPrj < 0.0D)
        {
            double d4 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.00040000000000000002D + NormalVPrj * NormalVPrj * 0.0011111111111111111D;
            if(d4 > 1.0D)
                landHit(i, (float)d4);
        }
        return true;
    }

    private boolean testPropellorCollision(int i)
    {
        if(bIsMaster && i >= 3 && i <= 6)
        {
            if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Realistic_Landings)
                return false;
            FM.setCapableOfTaxiing(false);
            switch(FM.Scheme)
            {
            default:
                break;

            case 1: // '\001'
                ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(0, 0, com.maddox.il2.engine.Engine.actorLand());
                break;

            case 2: // '\002'
            case 3: // '\003'
                if(i < 5)
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(0, 0, com.maddox.il2.engine.Engine.actorLand());
                else
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(1, 0, com.maddox.il2.engine.Engine.actorLand());
                break;

            case 4: // '\004'
            case 5: // '\005'
                ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(i - 3, 0, com.maddox.il2.engine.Engine.actorLand());
                break;

            case 6: // '\006'
                switch(i)
                {
                case 3: // '\003'
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(0, 0, com.maddox.il2.engine.Engine.actorLand());
                    break;

                case 4: // '\004'
                case 5: // '\005'
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(1, 0, com.maddox.il2.engine.Engine.actorLand());
                    break;

                case 6: // '\006'
                    ((com.maddox.il2.objects.air.Aircraft)FM.actor).hitProp(2, 0, com.maddox.il2.engine.Engine.actorLand());
                    break;
                }
                break;
            }
            return false;
        } else
        {
            return true;
        }
    }

    private void landHit(int i, double d1)
    {
        if(FM.Vrel.length() < 13D || pnti[i] < 0)
            return;
        if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Realistic_Landings)
            return;
        com.maddox.il2.engine.ActorHMesh actorhmesh = (com.maddox.il2.engine.ActorHMesh)FM.actor;
        if(!com.maddox.il2.engine.Actor.isValid(actorhmesh))
            return;
        com.maddox.il2.engine.Mesh mesh = actorhmesh.mesh();
        long l = com.maddox.rts.Time.tick();
        java.lang.String s1 = actorhmesh.findHook(mesh.hookName(pnti[i])).chunkName();
        if(s1.compareTo("CF_D0") == 0)
        {
            if(d1 > 2D)
                com.maddox.il2.engine.MsgCollision.post(l, actorhmesh, com.maddox.il2.engine.Engine.actorLand(), s1, "Body");
        } else
        if(s1.compareTo("Tail1_D0") == 0)
        {
            if(d1 > 1.3D)
                com.maddox.il2.engine.MsgCollision.post(l, actorhmesh, com.maddox.il2.engine.Engine.actorLand(), s1, "Body");
        } else
        if((FM.actor instanceof com.maddox.il2.objects.air.Scheme1) && s1.compareTo("Engine1_D0") == 0)
        {
            com.maddox.il2.engine.MsgCollision.post(l, actorhmesh, com.maddox.il2.engine.Engine.actorLand(), s1, "Body");
            if(d1 > 5D)
                com.maddox.il2.engine.MsgCollision.post(l, actorhmesh, com.maddox.il2.engine.Engine.actorLand(), "CF_D0", "Body");
        } else
        {
            com.maddox.il2.engine.MsgCollision.post(l, actorhmesh, com.maddox.il2.engine.Engine.actorLand(), s1, "Body");
        }
    }

    public void hitLeftGear()
    {
        lgear = false;
        FM.brakeShoe = false;
    }

    public void hitRightGear()
    {
        rgear = false;
        FM.brakeShoe = false;
    }

    public void hitCentreGear()
    {
        cgear = false;
        FM.brakeShoe = false;
    }

    public boolean isAnyDamaged()
    {
        return !lgear || !rgear || !cgear;
    }

    private void drawEffects()
    {
        boolean flag = FM.isTick(16, 0);
        for(int i = 0; i < 3; i++)
            if(bIsSail && flag && isWater && clp[i] && FM.getSpeedKMH() > 10F)
            {
                if(clpGearEff[i][0] == null)
                {
                    clpGearEff[i][0] = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(new Point3d(Pnt[i]), new Orient(0.0F, 0.0F, 0.0F)), 1.0F, "3DO/Effects/Tracers/ShipTrail/WakeSmaller.eff", -1F);
                    clpGearEff[i][1] = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(new Point3d(Pnt[i]), new Orient(0.0F, 0.0F, 0.0F)), 1.0F, "3DO/Effects/Tracers/ShipTrail/WaveSmaller.eff", -1F);
                }
            } else
            if(flag && clpGearEff[i][0] != null)
            {
                com.maddox.il2.engine.Eff3DActor.finish(clpGearEff[i][0]);
                com.maddox.il2.engine.Eff3DActor.finish(clpGearEff[i][1]);
                clpGearEff[i][0] = null;
                clpGearEff[i][1] = null;
            }

        for(int j = 0; j < pnti.length; j++)
            if(clp[j] && FM.Vrel.length() > 16.666666670000001D && !isUnderDeck())
            {
                if(clpEff[j] == null)
                {
                    if(isWater)
                        effectName = "EFFECTS/Smokes/SmokeAirSplat.eff";
                    else
                    if(com.maddox.il2.ai.World.cur().camouflage == 1)
                        effectName = "EFFECTS/Smokes/SmokeAirTouchW.eff";
                    else
                        effectName = "EFFECTS/Smokes/SmokeAirTouch.eff";
                    clpEff[j] = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(new Point3d(Pnt[j]), new Orient(0.0F, 90F, 0.0F)), 1.0F, effectName, -1F);
                }
            } else
            if(flag && clpEff[j] != null)
            {
                com.maddox.il2.engine.Eff3DActor.finish(clpEff[j]);
                clpEff[j] = null;
            }

        if(FM.EI.getNum() > 0)
        {
            for(int k = 0; k < FM.EI.getNum(); k++)
            {
                FM.actor.pos.getAbs(com.maddox.il2.objects.air.Aircraft.tmpLoc1);
                Pn.set(FM.EI.engines[k].getPropPos());
                com.maddox.il2.objects.air.Aircraft.tmpLoc1.transform(Pn, PnT);
                float f = (float)(PnT.z - com.maddox.il2.engine.Engine.cur.land.HQ(PnT.x, PnT.y));
                if(f < 16.2F && FM.EI.engines[k].getThrustOutput() > 0.5F)
                {
                    Pn.x -= f * com.maddox.il2.objects.air.Aircraft.cvt(FM.Or.getTangage(), -30F, 30F, 8F, 2.0F);
                    com.maddox.il2.objects.air.Aircraft.tmpLoc1.transform(Pn, PnT);
                    PnT.z = com.maddox.il2.engine.Engine.cur.land.HQ(PnT.x, PnT.y);
                    if(clpEngineEff[k][0] == null)
                    {
                        com.maddox.il2.objects.air.Aircraft.tmpLoc1.transformInv(PnT);
                        if(isWater)
                        {
                            clpEngineEff[k][0] = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/GrayGroundDust2.eff", -1F);
                            clpEngineEff[k][1] = com.maddox.il2.engine.Eff3DActor.New(new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/WhiteEngineWaveTSPD.eff", -1F);
                        } else
                        {
                            clpEngineEff[k][0] = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/GrayGroundDust" + (com.maddox.il2.ai.World.cur().camouflage != 1 ? "1" : "2") + ".eff", -1F);
                        }
                        continue;
                    }
                    if(isWater)
                    {
                        if(clpEngineEff[k][1] == null)
                        {
                            com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[k][0]);
                            clpEngineEff[k][0] = null;
                            continue;
                        }
                    } else
                    if(clpEngineEff[k][1] != null)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[k][0]);
                        clpEngineEff[k][0] = null;
                        com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[k][1]);
                        clpEngineEff[k][1] = null;
                        continue;
                    }
                    com.maddox.il2.objects.air.Aircraft.tmpOr.set(FM.Or.getAzimut() + 180F, 0.0F, 0.0F);
                    clpEngineEff[k][0].pos.setAbs(PnT);
                    clpEngineEff[k][0].pos.setAbs(com.maddox.il2.objects.air.Aircraft.tmpOr);
                    clpEngineEff[k][0].pos.resetAsBase();
                    if(clpEngineEff[k][1] != null)
                    {
                        PnT.z = 0.0D;
                        clpEngineEff[k][1].pos.setAbs(PnT);
                    }
                } else
                {
                    if(clpEngineEff[k][0] != null)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[k][0]);
                        clpEngineEff[k][0] = null;
                    }
                    if(clpEngineEff[k][1] != null)
                    {
                        com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[k][1]);
                        clpEngineEff[k][1] = null;
                    }
                }
            }

        }
    }

    private void turnOffEffects()
    {
        if(FM.isTick(69, 0))
        {
            for(int i = 0; i < pnti.length; i++)
                if(clpEff[i] != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(clpEff[i]);
                    clpEff[i] = null;
                }

            for(int j = 0; j < FM.EI.getNum(); j++)
            {
                if(clpEngineEff[j][0] != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[j][0]);
                    clpEngineEff[j][0] = null;
                }
                if(clpEngineEff[j][1] != null)
                {
                    com.maddox.il2.engine.Eff3DActor.finish(clpEngineEff[j][1]);
                    clpEngineEff[j][1] = null;
                }
            }

        }
    }

    private void processingCollisionEffect()
    {
        if(!canDoEffect)
            return;
        Vnorm = FM.Vwld.dot(Normal);
        if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().diffCur.Realistic_Landings && Vnorm < -20D && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02D)
        {
            canDoEffect = false;
            int i = 20 + (int)(30F * com.maddox.il2.ai.World.Rnd().nextFloat());
            if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0] != null && FM.CT.Weapons[3][0].countBullets() != 0)
                i = 0;
            if(((com.maddox.il2.objects.air.Aircraft)FM.actor).isEnablePostEndAction(i))
            {
                com.maddox.il2.engine.Eff3DActor eff3dactor = null;
                if(i > 0)
                {
                    com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(new Point3d(0.0D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F)), 1.0F, "3DO/Effects/Aircraft/FireGND.eff", i);
                    eff3dactor = com.maddox.il2.engine.Eff3DActor.New(FM.actor, null, new Loc(new Point3d(0.0D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F)), 1.0F, "3DO/Effects/Aircraft/BlackHeavyGND.eff", i + 10);
                    ((com.maddox.il2.objects.air.NetAircraft)FM.actor).sfxSmokeState(0, 0, true);
                }
                ((com.maddox.il2.objects.air.Aircraft)FM.actor).postEndAction(i, com.maddox.il2.engine.Engine.actorLand(), 2, eff3dactor);
            }
        }
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        bIsSail = sectfile.get("Aircraft", "Seaplane", 0) != 0;
        sinkFactor = sectfile.get("Gear", "SinkFactor", 1.0F);
        springsStiffness = sectfile.get("Gear", "SpringsStiffness", 1.0F);
        tailStiffness = sectfile.get("Gear", "TailStiffness", 0.6F);
        if(sectfile.get("Gear", "FromIni", 0) == 1)
        {
            H = sectfile.get("Gear", "H", 2.0F);
            Pitch = sectfile.get("Gear", "Pitch", 10F);
        } else
        {
            H = Pitch = 0.0F;
        }
        java.lang.String s1 = sectfile.get("Gear", "WaterClipList", "-");
        if(!s1.startsWith("-"))
        {
            waterList = new int[3 + s1.length() / 2];
            waterList[0] = 0;
            waterList[1] = 1;
            waterList[2] = 2;
            for(int i = 0; i < waterList.length - 3; i++)
            {
                waterList[3 + i] = 10 * (s1.charAt(i + i) - 48) + 1 * (s1.charAt(i + i + 1) - 48);
                waterList[3 + i] += 3;
            }

        }
    }

    public float getLandingState()
    {
        if(!onGround)
            return 0.0F;
        float f = 0.4F + ((float)roughness - 0.2F) * 0.5F;
        if(f > 1.0F)
            f = 1.0F;
        return f;
    }

    public double plateFriction(com.maddox.il2.fm.FlightModel flightmodel)
    {
        com.maddox.il2.engine.Actor actor = flightmodel.actor;
        if(bUnderDeck)
            return 0.0D;
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return 0.20000000000000001D;
        if(!com.maddox.il2.ai.World.cur().diffCur.Realistic_Landings)
            return 0.20000000000000001D;
        float f = 200F;
        actor.pos.getAbs(corn);
        bPlateExist = false;
        bPlateGround = false;
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, corn.x - (double)f, corn.y - (double)f, corn.x + (double)f, corn.y + (double)f, 1, plateFilter);
        if(bPlateExist)
            return !bPlateGround ? 0.0D : 0.80000000000000004D;
        int i = com.maddox.il2.engine.Engine.cur.land.HQ_RoadTypeHere(flightmodel.Loc.x, flightmodel.Loc.y);
        switch(i)
        {
        case 1: // '\001'
            return 0.80000000000000004D;

        case 2: // '\002'
            return 0.0D;

        case 3: // '\003'
            return 5D;
        }
        return 3.7999999999999998D;
    }

    private java.lang.String s(int i)
    {
        return i >= 10 ? "" + i : "0" + i;
    }

    private boolean bInWaterList(int i)
    {
        if(waterList != null)
        {
            for(int j = 0; j < waterList.length; j++)
                if(waterList[j] == i)
                    return true;

        }
        return false;
    }

    public int pnti[];
    boolean onGround;
    boolean nearGround;
    public float H;
    public float Pitch;
    public float sinkFactor;
    public float springsStiffness;
    public float tailStiffness;
    public boolean bIsSail;
    public int nOfGearsOnGr;
    public int nOfPoiOnGr;
    private int oldNOfGearsOnGr;
    private int oldNOfPoiOnGr;
    private int nP;
    public boolean gearsChanged;
    private static final double landSlot = 0.10000000000000001D;
    com.maddox.il2.engine.HierMesh HM;
    public boolean bFlatTopGearCheck;
    public static final int MP = 64;
    public static final double maxVf_gr = 65D;
    public static final double maxVn_gr = 7D;
    public static final double maxVf_wt = 50D;
    public static final double maxVn_wt = 30D;
    public static final double maxVf_wl = 110D;
    public static final double maxVn_wl = 7D;
    public static final double _1_maxVf_gr2 = 0.00023668639053254438D;
    public static final double _1_maxVn_gr2 = 0.020408163265306121D;
    public static final double _1_maxVf_wt2 = 0.00040000000000000002D;
    public static final double _1_maxVn_wt2 = 0.0011111111111111111D;
    public static final double _1_maxVf_wl2 = 8.264462809917356E-005D;
    public static final double _1_maxVn_wl2 = 0.020408163265306121D;
    private static com.maddox.JGP.Point3f Pnt[];
    private static boolean clp[] = new boolean[64];
    private com.maddox.il2.engine.Eff3DActor clpEff[];
    public com.maddox.il2.engine.Eff3DActor clpGearEff[][] = {
        {
            null, null
        }, {
            null, null
        }, {
            null, null
        }
    };
    public com.maddox.il2.engine.Eff3DActor clpEngineEff[][];
    private java.lang.String effectName;
    private boolean bTheBabysGonnaBlow;
    public boolean lgear;
    public boolean rgear;
    public boolean cgear;
    public boolean bIsHydroOperable;
    private boolean bIsOperable;
    public boolean bTailwheelLocked;
    public double gVelocity[] = {
        0.0D, 0.0D, 0.0D
    };
    public float gWheelAngles[] = {
        0.0F, 0.0F, 0.0F
    };
    public float gWheelSinking[] = {
        0.0F, 0.0F, 0.0F
    };
    public float steerAngle;
    public double roughness;
    public float arrestorVAngle;
    public float arrestorVSink;
    public com.maddox.il2.engine.HookNamed arrestorHook;
    public int waterList[];
    private boolean isGearColl;
    private double MassCoeff;
    public boolean bFrontWheel;
    private static com.maddox.il2.ai.AnglesFork steerAngleFork = new AnglesFork();
    private double d;
    private double poiDrag;
    private double D0;
    private double D;
    private double Vnorm;
    private boolean isWater;
    private boolean bUnderDeck;
    private boolean bIsGear;
    private com.maddox.il2.fm.FlightModel FM;
    private boolean bIsMaster;
    private int fatigue[];
    private com.maddox.JGP.Point3d p0;
    private com.maddox.JGP.Point3d p1;
    private com.maddox.il2.engine.Loc l0;
    private com.maddox.JGP.Vector3d v0;
    private com.maddox.JGP.Vector3d tmpV;
    private com.maddox.JGP.Vector3d tmpV1;
    private double fric;
    private double fricF;
    private double fricR;
    private double maxFric;
    public double screenHQ;
    static com.maddox.il2.fm.ClipFilter clipFilter = new ClipFilter();
    private boolean canDoEffect;
    private static com.maddox.JGP.Vector3d Normal = new Vector3d();
    private static com.maddox.JGP.Vector3d Forward = new Vector3d();
    private static com.maddox.JGP.Vector3d Right = new Vector3d();
    private static com.maddox.JGP.Vector3d nwForward = new Vector3d();
    private static com.maddox.JGP.Vector3d nwRight = new Vector3d();
    private static double NormalVPrj;
    private static double ForwardVPrj;
    private static double RightVPrj;
    private static com.maddox.JGP.Vector3d Vnf = new Vector3d();
    private static com.maddox.JGP.Vector3d Fd = new Vector3d();
    private static com.maddox.JGP.Vector3d Fx = new Vector3d();
    private static com.maddox.JGP.Vector3d Vship = new Vector3d();
    private static com.maddox.JGP.Vector3d Fv = new Vector3d();
    private static com.maddox.JGP.Vector3d Tn = new Vector3d();
    private static com.maddox.JGP.Point3d Pn = new Point3d();
    private static com.maddox.JGP.Point3d PnT = new Point3d();
    private static com.maddox.JGP.Point3d Pship = new Point3d();
    private static com.maddox.JGP.Vector3d Vs = new Vector3d();
    private static com.maddox.JGP.Vector3d normal = new Vector3d();
    private static com.maddox.JGP.Matrix4d M4 = new Matrix4d();
    private static com.maddox.il2.fm.PlateFilter plateFilter = new PlateFilter();
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static com.maddox.il2.engine.Loc L = new Loc();
    private static float plateBox[] = new float[6];
    private static boolean bPlateExist = false;
    private static boolean bPlateGround = false;

    static 
    {
        Pnt = new com.maddox.JGP.Point3f[64];
        for(int i = 0; i < Pnt.length; i++)
            Pnt[i] = new Point3f();

    }





}
