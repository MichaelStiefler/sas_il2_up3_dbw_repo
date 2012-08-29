// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cockpit_RanwersLetov.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, LetovS_328, Aircraft

public class Cockpit_RanwersLetov extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            if((fm.AS.astateCockpitState & 2) != 0 && setNew.stbyPosition < 1.0F)
            {
                setNew.stbyPosition = setOld.stbyPosition + 0.0125F;
                setOld.stbyPosition = setNew.stbyPosition;
            }
            setNew.altimeter = fm.getAltitude();
            setNew.throttle = (10F * setOld.throttle + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.radiator = (10F * setOld.radiator + fm.EI.engines[0].getControlRadiator()) / 11F;
            setNew.prop = setOld.prop;
            if(setNew.prop < fm.EI.engines[0].getControlProp() - 0.01F)
                setNew.prop += 0.0025F;
            if(setNew.prop > fm.EI.engines[0].getControlProp() + 0.01F)
                setNew.prop -= 0.0025F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (299F * setOld.vspeed + fm.getVertSpeed()) / 300F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)fm.EI.engines[0].getControlCompressor();
            if(cockpitDimControl)
            {
                if(setNew.dimPos < 1.0F)
                    setNew.dimPos = setOld.dimPos + 0.03F;
            } else
            if(setNew.dimPos > 0.0F)
                setNew.dimPos = setOld.dimPos - 0.03F;
            if(!fm.Gears.bTailwheelLocked && tailWheelLock < 1.0F)
                tailWheelLock = tailWheelLock + 0.05F;
            else
            if(fm.Gears.bTailwheelLocked && tailWheelLock > 0.0F)
                tailWheelLock = tailWheelLock - 0.05F;
            updateCompass();
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float altimeter;
        float throttle;
        float mix;
        float radiator;
        float prop;
        float turn;
        float vspeed;
        float stbyPosition;
        float dimPos;
        com.maddox.JGP.Point3d planeLoc;
        com.maddox.JGP.Point3d planeMove;
        com.maddox.JGP.Vector3d compassPoint[];
        com.maddox.JGP.Vector3d cP[];

        private Variables()
        {
            planeLoc = new Point3d();
            planeMove = new Point3d();
            compassPoint = new com.maddox.JGP.Vector3d[4];
            cP = new com.maddox.JGP.Vector3d[4];
            compassPoint[0] = new Vector3d(0.0D, java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            compassPoint[1] = new Vector3d(-java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), 0.0D, com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            compassPoint[2] = new Vector3d(0.0D, -java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            compassPoint[3] = new Vector3d(java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), 0.0D, com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            cP[0] = new Vector3d(0.0D, java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            cP[1] = new Vector3d(-java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), 0.0D, com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            cP[2] = new Vector3d(0.0D, -java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
            cP[3] = new Vector3d(java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ * com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ), 0.0D, com.maddox.il2.objects.air.Cockpit_RanwersLetov.compassZ);
        }

    }


    public Cockpit_RanwersLetov()
    {
        super("3DO/Cockpit/AviaB-534/letovhier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        tailWheelLock = 1.0F;
        ac = null;
        rpmGeneratedPressure = 0.0F;
        oilPressure = 0.0F;
        hIncrement = 0.0096F;
        vIncrement = 0.0F;
        hIncrementSize = 0.0024F;
        vIncrementSize = 0.0005F;
        previousCycle = 0;
        cycleCount = 5;
        turningCycle = false;
        cycleMultiplier = -1;
        gunLOK = true;
        gunROK = true;
        compassFirst = 0;
        ac = (com.maddox.il2.objects.air.LetovS_328)aircraft();
        cockpitNightMats = (new java.lang.String[] {
            "Compass", "gauge1", "gauge2", "gauge3", "gauge4", "gauge5", "DM_gauge1", "DM_gauge2"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    private void initCompass()
    {
        accel = new Vector3d();
        compassSpeed = new com.maddox.JGP.Vector3d[4];
        compassSpeed[0] = new Vector3d(0.0D, 0.0D, 0.0D);
        compassSpeed[1] = new Vector3d(0.0D, 0.0D, 0.0D);
        compassSpeed[2] = new Vector3d(0.0D, 0.0D, 0.0D);
        compassSpeed[3] = new Vector3d(0.0D, 0.0D, 0.0D);
        float af[] = {
            87F, 77.5F, 65.3F, 41.5F, -0.3F, -43.5F, -62.9F, -64F, -66.3F, -75.8F
        };
        float af1[] = {
            55.8F, 51.5F, 47F, 40.1F, 33.8F, 33.7F, 32.7F, 35.1F, 46.6F, 61F
        };
        float f = cvt(com.maddox.il2.engine.Engine.land().config.declin, -90F, 90F, 9F, 0.0F);
        float f1 = floatindex(f, af);
        compassNorth = new Vector3d(0.0D, java.lang.Math.cos(0.017452777777777779D * (double)f1), -java.lang.Math.sin(0.017452777777777779D * (double)f1));
        compassSouth = new Vector3d(0.0D, -java.lang.Math.cos(0.017452777777777779D * (double)f1), java.lang.Math.sin(0.017452777777777779D * (double)f1));
        float f2 = floatindex(f, af1);
        compassNorth.scale((f2 / 600F) * com.maddox.rts.Time.tickLenFs());
        compassSouth.scale((f2 / 600F) * com.maddox.rts.Time.tickLenFs());
        segLen1 = 2D * java.lang.Math.sqrt(1.0D - compassZ * compassZ);
        segLen2 = segLen1 / java.lang.Math.sqrt(2D);
        compassLimit = -1D * java.lang.Math.sin(0.01745328888888889D * compassLimitAngle);
        compassLimit *= compassLimit;
        compassAcc = 4.6666666599999997D * (double)com.maddox.rts.Time.tickLenFs();
        compassSc = 0.10193679899999999D / (double)com.maddox.rts.Time.tickLenFs() / (double)com.maddox.rts.Time.tickLenFs();
    }

    private void updateCompass()
    {
        if(compassFirst == 0)
        {
            initCompass();
            fm.getLoc(setOld.planeLoc);
        }
        fm.getLoc(setNew.planeLoc);
        setNew.planeMove.set(setNew.planeLoc);
        setNew.planeMove.sub(setOld.planeLoc);
        accel.set(setNew.planeMove);
        accel.sub(setOld.planeMove);
        accel.scale(compassSc);
        accel.x = -accel.x;
        accel.y = -accel.y;
        accel.z = -accel.z - 1.0D;
        accel.scale(compassAcc);
        if(accel.length() > -compassZ * 0.69999999999999996D)
            accel.scale((-compassZ * 0.69999999999999996D) / accel.length());
        for(int i = 0; i < 4; i++)
        {
            compassSpeed[i].set(setOld.compassPoint[i]);
            compassSpeed[i].sub(setNew.compassPoint[i]);
        }

        for(int j = 0; j < 4; j++)
        {
            double d = compassSpeed[j].length();
            d = 0.98499999999999999D / (1.0D + d * d * 15D);
            compassSpeed[j].scale(d);
        }

        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.set(setOld.compassPoint[0]);
        vector3d.add(setOld.compassPoint[1]);
        vector3d.add(setOld.compassPoint[2]);
        vector3d.add(setOld.compassPoint[3]);
        vector3d.normalize();
        for(int k = 0; k < 4; k++)
        {
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            double d1 = vector3d.dot(compassSpeed[k]);
            vector3d1.set(vector3d);
            d1 *= 0.28000000000000003D;
            vector3d1.scale(-d1);
            compassSpeed[k].add(vector3d1);
        }

        for(int l = 0; l < 4; l++)
            compassSpeed[l].add(accel);

        compassSpeed[0].add(compassNorth);
        compassSpeed[2].add(compassSouth);
        for(int i1 = 0; i1 < 4; i1++)
        {
            setNew.compassPoint[i1].set(setOld.compassPoint[i1]);
            setNew.compassPoint[i1].add(compassSpeed[i1]);
        }

        vector3d.set(setNew.compassPoint[0]);
        vector3d.add(setNew.compassPoint[1]);
        vector3d.add(setNew.compassPoint[2]);
        vector3d.add(setNew.compassPoint[3]);
        vector3d.scale(0.25D);
        com.maddox.JGP.Vector3d vector3d2 = new Vector3d(vector3d);
        vector3d2.normalize();
        vector3d2.scale(-compassZ);
        vector3d2.sub(vector3d);
        for(int j1 = 0; j1 < 4; j1++)
            setNew.compassPoint[j1].add(vector3d2);

        for(int k1 = 0; k1 < 4; k1++)
            setNew.compassPoint[k1].normalize();

        for(int l1 = 0; l1 < 2; l1++)
        {
            compassDist(setNew.compassPoint[0], setNew.compassPoint[2], segLen1);
            compassDist(setNew.compassPoint[1], setNew.compassPoint[3], segLen1);
            compassDist(setNew.compassPoint[0], setNew.compassPoint[1], segLen2);
            compassDist(setNew.compassPoint[2], setNew.compassPoint[3], segLen2);
            compassDist(setNew.compassPoint[1], setNew.compassPoint[2], segLen2);
            compassDist(setNew.compassPoint[3], setNew.compassPoint[0], segLen2);
            for(int i2 = 0; i2 < 4; i2++)
                setNew.compassPoint[i2].normalize();

            compassDist(setNew.compassPoint[3], setNew.compassPoint[0], segLen2);
            compassDist(setNew.compassPoint[1], setNew.compassPoint[2], segLen2);
            compassDist(setNew.compassPoint[2], setNew.compassPoint[3], segLen2);
            compassDist(setNew.compassPoint[0], setNew.compassPoint[1], segLen2);
            compassDist(setNew.compassPoint[1], setNew.compassPoint[3], segLen1);
            compassDist(setNew.compassPoint[0], setNew.compassPoint[2], segLen1);
            for(int j2 = 0; j2 < 4; j2++)
                setNew.compassPoint[j2].normalize();

        }

        com.maddox.il2.engine.Orientation orientation = new Orientation();
        fm.getOrient(orientation);
        for(int k2 = 0; k2 < 4; k2++)
        {
            setNew.cP[k2].set(setNew.compassPoint[k2]);
            orientation.transformInv(setNew.cP[k2]);
        }

        com.maddox.JGP.Vector3d vector3d3 = new Vector3d();
        vector3d3.set(setNew.cP[0]);
        vector3d3.add(setNew.cP[1]);
        vector3d3.add(setNew.cP[2]);
        vector3d3.add(setNew.cP[3]);
        vector3d3.scale(0.25D);
        com.maddox.JGP.Vector3d vector3d4 = new Vector3d();
        vector3d4.set(vector3d3);
        vector3d4.normalize();
        float f = (float)(vector3d4.x * vector3d4.x + vector3d4.y * vector3d4.y);
        if((double)f > compassLimit || vector3d3.z > 0.0D)
        {
            for(int l2 = 0; l2 < 4; l2++)
            {
                setNew.cP[l2].set(setOld.cP[l2]);
                setNew.compassPoint[l2].set(setOld.cP[l2]);
                orientation.transform(setNew.compassPoint[l2]);
            }

            vector3d3.set(setNew.cP[0]);
            vector3d3.add(setNew.cP[1]);
            vector3d3.add(setNew.cP[2]);
            vector3d3.add(setNew.cP[3]);
            vector3d3.scale(0.25D);
        }
        vector3d4.set(setNew.cP[0]);
        vector3d4.sub(vector3d3);
        double d2 = -java.lang.Math.atan2(vector3d3.y, -vector3d3.z);
        vectorRot2(vector3d3, d2);
        vectorRot2(vector3d4, d2);
        double d3 = java.lang.Math.atan2(vector3d3.x, -vector3d3.z);
        vectorRot1(vector3d4, -d3);
        double d4 = java.lang.Math.atan2(vector3d4.y, vector3d4.x);
        mesh.chunkSetAngles("NeedCompass_A", -(float)((d2 * 180D) / 3.1415926000000001D), -(float)((d3 * 180D) / 3.1415926000000001D), 0.0F);
        mesh.chunkSetAngles("NeedCompass_B", 0.0F, (float)(90D - (d4 * 180D) / 3.1415926000000001D), 0.0F);
        compassFirst++;
    }

    private void vectorRot1(com.maddox.JGP.Vector3d vector3d, double d)
    {
        double d1 = java.lang.Math.sin(d);
        double d2 = java.lang.Math.cos(d);
        double d3 = vector3d.x * d2 - vector3d.z * d1;
        vector3d.z = vector3d.x * d1 + vector3d.z * d2;
        vector3d.x = d3;
    }

    private void vectorRot2(com.maddox.JGP.Vector3d vector3d, double d)
    {
        double d1 = java.lang.Math.sin(d);
        double d2 = java.lang.Math.cos(d);
        double d3 = vector3d.y * d2 - vector3d.z * d1;
        vector3d.z = vector3d.y * d1 + vector3d.z * d2;
        vector3d.y = d3;
    }

    private void compassDist(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1, double d)
    {
        com.maddox.JGP.Vector3d vector3d2 = new Vector3d();
        vector3d2.set(vector3d);
        vector3d2.sub(vector3d1);
        double d1 = vector3d2.length();
        if(d1 < 9.9999999999999995E-007D)
            d1 = 9.9999999999999995E-007D;
        d1 = (d - d1) / d1 / 2D;
        vector3d2.scale(d1);
        vector3d.add(vector3d2);
        vector3d1.sub(vector3d2);
    }

    public void reflectWorldToInstruments(float f)
    {
        float f1 = 0.0F;
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("NeedManPress", 0.0F, pictManifold = 0.85F * pictManifold + 0.15F * cvt(fm.EI.engines[0].getManifoldPressure() * 760F, 260F, 1200F, 33.3F, 360F), 0.0F);
        f1 = -15F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl);
        float f2 = 14F * (pictElev = 0.85F * pictElev + 0.2F * fm.CT.ElevatorControl);
        mesh.chunkSetAngles("StickB", 0.0F, -f1, f2);
        f1 = fm.CT.getRudder();
        mesh.chunkSetAngles("Pedal_L", 0.0F, 18F * f1, 0.0F);
        mesh.chunkSetAngles("Pedal_R", 0.0F, 18F * f1, 0.0F);
        mesh.chunkSetAngles("TQHandle", 0.0F, 0.0F, 75F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("MixLvr", 0.0F, 0.0F, 65F * interp(setNew.mix, setOld.mix, f));
        mesh.chunkSetAngles("CowlFlapLvr", 0.0F, -70F * interp(setNew.radiator, setOld.radiator, f), 0.0F);
        mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 450F), 0.0F);
        float f3 = com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH());
        if(f3 < 100F)
            mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 0.0F, 100F, 0.0F, -28.4F), 0.0F);
        else
        if(f3 < 200F)
            mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 100F, 200F, -28.4F, -102F), 0.0F);
        else
        if(f3 < 300F)
            mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 200F, 300F, -102F, -191.5F), 0.0F);
        else
            mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 300F, 450F, -191.5F, -326F), 0.0F);
        mesh.chunkSetAngles("NeedFuel", 0.0F, cvt(fm.M.fuel, 0.0F, 250F, 0.0F, 255.5F), 0.0F);
        f1 = fm.EI.engines[0].tOilOut;
        if(f1 < 20F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 0.0F, 20F, 0.0F, 15F), 0.0F);
        else
        if(f1 < 40F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 20F, 40F, 15F, 50F), 0.0F);
        else
        if(f1 < 60F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 40F, 60F, 50F, 102.5F), 0.0F);
        else
        if(f1 < 80F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 60F, 80F, 102.5F, 186F), 0.0F);
        else
        if(f1 < 100F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 80F, 100F, 186F, 283F), 0.0F);
        else
        if(f1 < 120F)
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 100F, 120F, 283F, 314F), 0.0F);
        else
            mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 120F, 140F, 314F, 345F), 0.0F);
        f1 = fm.EI.engines[0].getRPM();
        mesh.chunkSetAngles("NeedRPM", 0.0F, cvt(f1, 0.0F, 3000F, 0.0F, 310F), 0.0F);
        if(fm.Or.getKren() < -110F || fm.Or.getKren() > 110F)
            rpmGeneratedPressure = rpmGeneratedPressure - 0.5F;
        else
        if(f1 < rpmGeneratedPressure)
            rpmGeneratedPressure = rpmGeneratedPressure - (rpmGeneratedPressure - f1) * 0.01F;
        else
            rpmGeneratedPressure = rpmGeneratedPressure + (f1 - rpmGeneratedPressure) * 0.001F;
        if(rpmGeneratedPressure < 600F)
            oilPressure = cvt(rpmGeneratedPressure, 0.0F, 600F, 0.0F, 4F);
        else
        if(rpmGeneratedPressure < 900F)
            oilPressure = cvt(rpmGeneratedPressure, 600F, 900F, 4F, 7F);
        else
            oilPressure = cvt(rpmGeneratedPressure, 900F, 1200F, 7F, 10F);
        float f4 = 0.0F;
        if(fm.EI.engines[0].tOilOut > 90F)
            f4 = cvt(fm.EI.engines[0].tOilOut, 90F, 120F, 1.1F, 1.5F);
        else
        if(fm.EI.engines[0].tOilOut < 50F)
            f4 = cvt(fm.EI.engines[0].tOilOut, 0.0F, 50F, 1.5F, 0.9F);
        else
            f4 = cvt(fm.EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f5 = f4 * fm.EI.engines[0].getReadyness() * oilPressure;
        if(f5 < 12F)
            mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 0.0F, 12F, 0.0F, 230F), 0.0F);
        else
        if(f5 < 16F)
            mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 12F, 16F, 230F, 285F), 0.0F);
        else
            mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 16F, 32F, 285F, 320F), 0.0F);
        f1 = fm.EI.engines[0].tWaterOut;
        if(f1 < 20F)
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 0.0F, 20F, 0.0F, 15F), 0.0F);
        else
        if(f1 < 40F)
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 20F, 40F, 15F, 50F), 0.0F);
        else
        if(f1 < 60F)
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 40F, 60F, 50F, 109F), 0.0F);
        else
        if(f1 < 80F)
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 60F, 80F, 109F, 192F), 0.0F);
        else
        if(f1 < 100F)
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 80F, 100F, 192F, 294F), 0.0F);
        else
            mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 100F, 111.5F, 294F, 356.1F), 0.0F);
        mesh.chunkSetAngles("NeedFuelPress", 0.0F, cvt(rpmGeneratedPressure, 0.0F, 1200F, 0.0F, 260F), 0.0F);
        mesh.chunkSetAngles("NeedBank", 0.0F, cvt(setNew.turn, -0.2F, 0.2F, 22.5F, -22.5F), 0.0F);
        mesh.chunkSetAngles("NeedTurn", 0.0F, -cvt(getBall(8D), -8F, 8F, 9.5F, -9.5F), 0.0F);
        mesh.chunkSetAngles("NeedClimb", 0.0F, -cvt(setNew.vspeed, -10F, 10F, 180F, -180F), 0.0F);
        mesh.chunkSetAngles("TrimIndicator", 120F * -fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
        mesh.chunkSetAngles("ElevTrim", 0.0F, 600F * fm.CT.getTrimElevatorControl(), 0.0F);
        mesh.chunkSetAngles("IgnitionSwitch", 0.0F, 90F * (float)(1 + fm.EI.engines[0].getControlMagnetos()), 0.0F);
        if(fm.CT.bHasBrakeControl)
        {
            float f6 = fm.CT.getBrake();
            mesh.chunkSetAngles("BrakeLever", f6 * 20F, 0.0F, 0.0F);
            mesh.chunkSetAngles("NeedAirPR", 0.0F, -cvt(f6 + f6 * fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148F), 0.0F);
            mesh.chunkSetAngles("NeedAirPL", 0.0F, cvt(f6 - f6 * fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148F), 0.0F);
            mesh.chunkSetAngles("NeedAirP", 0.0F, 110F - f6 * 20F, 0.0F);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("DamageGlass1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("Gages1_d0", false);
            mesh.chunkVisible("Gages1_d1", true);
            mesh.chunkVisible("NeedOilTemp", false);
            mesh.chunkVisible("NeedSpeed", false);
            mesh.chunkVisible("NeedWatTemp", false);
            mesh.chunkVisible("NeedAlt_Km", false);
            mesh.chunkVisible("DamageHull1", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Gages2_d0", false);
            mesh.chunkVisible("Gages2_d1", true);
            mesh.chunkVisible("NeedClimb", false);
            mesh.chunkVisible("NeedBank", false);
            mesh.chunkVisible("NeedTurn", false);
            mesh.chunkVisible("DamageHull1", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("DamageHull2", true);
            mesh.chunkVisible("DamageGlass3", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("DamageHull3", true);
            mesh.chunkVisible("DamageGlass3", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("DamageGlass2", true);
            mesh.chunkVisible("DamageHull4", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("OilSplats", true);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLMid_D0", hiermesh.isChunkVisible("WingLMid_D0"));
        mesh.chunkVisible("WingRMid_D0", hiermesh.isChunkVisible("WingRMid_D0"));
    }

    public void doToggleUp(boolean flag)
    {
        super.doToggleUp(flag);
    }

    public void destroy()
    {
        super.destroy();
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D1o"));
        mesh.materialReplace("Gloss2D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        mesh.materialReplace("Matt2D2o", mat);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    protected boolean doFocusEnter()
    {
        previousCycle = -1;
        cycleCount = 5;
        updateBullets();
        return super.doFocusEnter();
    }

    private void updateBullets()
    {
        boolean flag = false;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = fm.CT.Weapons[1];
        if(abulletemitter != null && (gunROK || gunLOK))
        {
            int i = 300;
            if(gunROK)
                i = 300 - abulletemitter[1].countBullets();
            else
            if(gunLOK)
                i = 300 - abulletemitter[0].countBullets();
            int j = i % 5 + 1;
            if(j != previousCycle)
            {
                hIncrementSize = 0.012F / (float)cycleCount;
                vIncrementSize = 0.004F / (float)(cycleCount * 2);
                hIncrement = hIncrementSize * (float)(cycleCount - 1);
                vIncrement = vIncrementSize * (float)(cycleCount - 1);
                cycleCount = 1;
                vIncrement = 0.0F;
            } else
            {
                cycleCount++;
                hIncrement = hIncrement - hIncrementSize;
                if(vIncrement >= 0.0025F)
                    vIncrement = vIncrement - vIncrementSize;
                else
                    vIncrement = vIncrement + vIncrementSize;
            }
            if(hIncrement < 0.0F)
                return;
            byte byte0 = 0;
            if(i >= 80)
                if(i < 95)
                    turningCycle = true;
                else
                if(i < 120)
                {
                    hideBullets(1);
                    byte0 = 2;
                    turningCycle = false;
                    cycleMultiplier = -1;
                } else
                if(i < 135)
                {
                    byte0 = 2;
                    turningCycle = true;
                } else
                if(i < 160)
                {
                    hideBullets(1);
                    hideBullets(2);
                    hideBullets(3);
                    byte0 = 4;
                    turningCycle = false;
                    cycleMultiplier = -1;
                } else
                if(i < 175)
                {
                    byte0 = 4;
                    turningCycle = true;
                } else
                if(i < 200)
                {
                    hideBullets(1);
                    hideBullets(2);
                    hideBullets(3);
                    hideBullets(4);
                    hideBullets(5);
                    byte0 = 6;
                    turningCycle = false;
                    cycleMultiplier = -1;
                    mesh.chunkVisible("RBullet21", true);
                    mesh.chunkVisible("LBullet21", true);
                } else
                if(i < 215)
                {
                    byte0 = 6;
                    turningCycle = true;
                    if(gunROK)
                        mesh.chunkVisible("RBullet21", false);
                    if(gunLOK)
                        mesh.chunkVisible("LBullet21", false);
                } else
                if(i < 240)
                {
                    hideBullets(1);
                    hideBullets(2);
                    hideBullets(3);
                    hideBullets(4);
                    hideBullets(5);
                    hideBullets(6);
                    hideBullets(7);
                    byte0 = 8;
                    turningCycle = false;
                    cycleMultiplier = -1;
                    mesh.chunkVisible("RBullet21", true);
                    mesh.chunkVisible("LBullet21", true);
                } else
                if(i < 255)
                {
                    byte0 = 8;
                    turningCycle = true;
                    if(gunROK)
                        mesh.chunkVisible("RBullet21", false);
                    if(gunLOK)
                        mesh.chunkVisible("LBullet21", false);
                } else
                {
                    hideBullets(1);
                    hideBullets(2);
                    hideBullets(3);
                    hideBullets(4);
                    hideBullets(5);
                    hideBullets(6);
                    hideBullets(7);
                    hideBullets(8);
                    hideBullets(9);
                    byte0 = 10;
                    turningCycle = false;
                    cycleMultiplier = -1;
                    if(i >= 280)
                    {
                        flag = true;
                        if(j == 5)
                        {
                            if(gunROK)
                                mesh.chunkVisible("RBullet21", false);
                            if(gunLOK)
                                mesh.chunkVisible("LBullet21", false);
                            hideBullets(10);
                        }
                        if(i >= 291)
                        {
                            hideBullets(10);
                            if(gunROK)
                            {
                                for(int k = 1; k <= i - 290; k++)
                                    mesh.chunkVisible("RBullet0" + k, false);

                            }
                            if(gunLOK)
                            {
                                for(int l = 1; l <= i - 290; l++)
                                    mesh.chunkVisible("LBullet0" + l, false);

                            }
                        }
                        if(i >= 300)
                            hideAllBullets();
                    } else
                    {
                        mesh.chunkVisible("RBullet21", true);
                        mesh.chunkVisible("LBullet21", true);
                    }
                }
            for(int i1 = 5; i1 >= 1; i1--)
            {
                com.maddox.il2.objects.air.Aircraft.xyz[2] = hIncrement;
                com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
                if(gunROK)
                    mesh.chunkSetLocate("RBullet0" + i1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(gunLOK)
                    mesh.chunkSetLocate("LBullet0" + i1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }

            for(int j1 = 9; j1 >= 6; j1--)
            {
                com.maddox.il2.objects.air.Aircraft.xyz[0] = hIncrement;
                com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
                if(gunROK)
                    mesh.chunkSetLocate("RBullet0" + j1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(gunLOK)
                    mesh.chunkSetLocate("LBullet0" + j1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }

            if(turningCycle)
            {
                if(j != previousCycle && j == 1)
                    cycleMultiplier++;
                int k1 = j + cycleMultiplier * 5;
                if(k1 > 10)
                {
                    hideBullets(byte0 + 1);
                    previousCycle = j;
                    return;
                }
                int i2 = 0;
                if(k1 % 2 != 0)
                    i2 = k1 / 2 + 1;
                int k2 = k1 / 2;
                for(int l2 = 1; l2 <= 6; l2++)
                {
                    if(l2 <= k2)
                    {
                        int i3 = k1 - (l2 + (l2 - 1));
                        com.maddox.il2.objects.air.Aircraft.xyz[0] = ((float)i3 * -0.012F + hIncrement) - 0.006F;
                        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.01F + vIncrement;
                        if(gunROK)
                            mesh.chunkSetLocate("RBullet" + (byte0 + 1) + l2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        if(gunLOK)
                            mesh.chunkSetLocate("LBullet" + (byte0 + 1) + l2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    }
                    if(l2 == i2)
                    {
                        com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.006F + hIncrement / 2.0F;
                        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.01F - hIncrement;
                        if(gunROK)
                            mesh.chunkSetLocate("RBullet" + (byte0 + 1) + i2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        if(gunLOK)
                            mesh.chunkSetLocate("LBullet" + (byte0 + 1) + i2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    }
                    if(k1 < 6 && l2 <= 6 && byte0 != 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.xyz[0] = (float)(k1 + 1) * -0.012F + hIncrement;
                        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F + vIncrement;
                        if(gunROK)
                            mesh.chunkSetLocate("RBullet" + byte0 + l2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        if(gunLOK)
                            mesh.chunkSetLocate("LBullet" + byte0 + l2, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    }
                }

                if(k1 >= 5 && byte0 != 0)
                    hideBullets(byte0);
            } else
            if(byte0 != 0)
            {
                for(int l1 = 6; l1 >= 1; l1--)
                    if(j + l1 >= 7 && !flag)
                    {
                        int j2 = 6 - l1 - j;
                        if(j2 == 0)
                        {
                            com.maddox.il2.objects.air.Aircraft.xyz[0] = (float)(l1 + j2) * 0.012F;
                            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
                            if(gunROK)
                                mesh.chunkSetLocate("RBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                            if(gunLOK)
                                mesh.chunkSetLocate("LBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        } else
                        {
                            com.maddox.il2.objects.air.Aircraft.xyz[0] = (float)(l1 + j2) * 0.012F + hIncrement;
                            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F + vIncrement;
                            if(gunROK)
                                mesh.chunkSetLocate("RBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                            if(gunLOK)
                                mesh.chunkSetLocate("LBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        }
                    } else
                    {
                        com.maddox.il2.objects.air.Aircraft.xyz[0] = (float)j * -0.012F + hIncrement;
                        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F + vIncrement;
                        if(gunROK)
                            mesh.chunkSetLocate("RBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                        if(gunLOK)
                            mesh.chunkSetLocate("LBullet" + byte0 + l1, com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    }

                if(byte0 >= 6)
                {
                    com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.072F + hIncrement;
                    com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.010742F * (float)(byte0 - 2) + vIncrement;
                    if(gunROK)
                        mesh.chunkSetLocate("RBullet21", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    if(gunLOK)
                        mesh.chunkSetLocate("LBullet21", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                }
            }
            previousCycle = j;
        }
    }

    private void hideBullets(int i)
    {
        if(gunLOK)
        {
            mesh.chunkVisible("LBullet" + i + 1, false);
            mesh.chunkVisible("LBullet" + i + 2, false);
            mesh.chunkVisible("LBullet" + i + 3, false);
            mesh.chunkVisible("LBullet" + i + 4, false);
            mesh.chunkVisible("LBullet" + i + 5, false);
            mesh.chunkVisible("LBullet" + i + 6, false);
        }
        if(gunROK)
        {
            mesh.chunkVisible("RBullet" + i + 1, false);
            mesh.chunkVisible("RBullet" + i + 2, false);
            mesh.chunkVisible("RBullet" + i + 3, false);
            mesh.chunkVisible("RBullet" + i + 4, false);
            mesh.chunkVisible("RBullet" + i + 5, false);
            mesh.chunkVisible("RBullet" + i + 6, false);
        }
    }

    public void jamLeftGun()
    {
        gunLOK = false;
    }

    public void jamRightGun()
    {
        gunROK = false;
    }

    public void hideAllBullets()
    {
        for(int i = 1; i <= 10; i++)
        {
            if(gunLOK)
            {
                mesh.chunkVisible("LBullet" + i + 1, false);
                mesh.chunkVisible("LBullet" + i + 2, false);
                mesh.chunkVisible("LBullet" + i + 3, false);
                mesh.chunkVisible("LBullet" + i + 4, false);
                mesh.chunkVisible("LBullet" + i + 5, false);
                mesh.chunkVisible("LBullet" + i + 6, false);
            }
            if(gunROK)
            {
                mesh.chunkVisible("RBullet" + i + 1, false);
                mesh.chunkVisible("RBullet" + i + 2, false);
                mesh.chunkVisible("RBullet" + i + 3, false);
                mesh.chunkVisible("RBullet" + i + 4, false);
                mesh.chunkVisible("RBullet" + i + 5, false);
                mesh.chunkVisible("RBullet" + i + 6, false);
            }
        }

        for(int j = 1; j <= 9; j++)
        {
            if(gunLOK)
                mesh.chunkVisible("LBullet0" + j, false);
            if(gunROK)
                mesh.chunkVisible("RBullet0" + j, false);
        }

    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float pictManifold;
    private float tailWheelLock;
    private com.maddox.il2.objects.air.LetovS_328 ac;
    private float rpmGeneratedPressure;
    private float oilPressure;
    private float hIncrement;
    private float vIncrement;
    private float hIncrementSize;
    private float vIncrementSize;
    private int previousCycle;
    private int cycleCount;
    private boolean turningCycle;
    private int cycleMultiplier;
    private boolean gunLOK;
    private boolean gunROK;
    private static double compassZ = -0.20000000000000001D;
    private double segLen1;
    private double segLen2;
    private double compassLimit;
    private static double compassLimitAngle = 25D;
    private com.maddox.JGP.Vector3d compassSpeed[];
    int compassFirst;
    private com.maddox.JGP.Vector3d accel;
    private com.maddox.JGP.Vector3d compassNorth;
    private com.maddox.JGP.Vector3d compassSouth;
    private double compassAcc;
    private double compassSc;
















}
