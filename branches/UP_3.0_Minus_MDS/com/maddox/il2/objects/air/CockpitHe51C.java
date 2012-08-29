// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitHe51C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, He51C, Aircraft, Cockpit, 
//            CR_42X

public class CockpitHe51C extends com.maddox.il2.objects.air.CockpitPilot
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
            if(((com.maddox.il2.objects.air.CR_42X) ((com.maddox.il2.objects.air.He51C)aircraft())).bChangedPit)
            {
                reflectPlaneToModel();
                ((com.maddox.il2.objects.air.He51C)aircraft()).bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = ((com.maddox.il2.fm.FlightModelMain) (fm)).getAltitude();
            if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).getKren()) < 30F)
                setNew.azimuth = (21F * setOld.azimuth + ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (fm)).Or)).azimut()) / 22F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix()) / 11F;
            setNew.prop = setOld.prop;
            if(setNew.prop < ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp() - 0.01F)
                setNew.prop += 0.0025F;
            if(setNew.prop > ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp() + 0.01F)
                setNew.prop -= 0.0025F;
            ((com.maddox.JGP.Tuple3f) (w)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).getW())));
            ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.transform(((com.maddox.JGP.Tuple3f) (w)));
            setNew.turn = (12F * setOld.turn + ((com.maddox.JGP.Tuple3f) (w)).z) / 13F;
            setNew.vspeed = (299F * setOld.vspeed + ((com.maddox.il2.fm.FlightModelMain) (fm)).getVertSpeed()) / 300F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlCompressor();
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
        float azimuth;
        float throttle;
        float mix;
        float prop;
        float turn;
        float vspeed;
        float radiator;
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
            compassPoint[0] = new Vector3d(0.0D, java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            compassPoint[1] = new Vector3d(-java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), 0.0D, com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            compassPoint[2] = new Vector3d(0.0D, -java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            compassPoint[3] = new Vector3d(java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), 0.0D, com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            cP[0] = new Vector3d(0.0D, java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            cP[1] = new Vector3d(-java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), 0.0D, com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            cP[2] = new Vector3d(0.0D, -java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), com.maddox.il2.objects.air.CockpitHe51C.compassZ);
            cP[3] = new Vector3d(java.lang.Math.sqrt(1.0D - com.maddox.il2.objects.air.CockpitHe51C.compassZ * com.maddox.il2.objects.air.CockpitHe51C.compassZ), 0.0D, com.maddox.il2.objects.air.CockpitHe51C.compassZ);
        }

    }


    public CockpitHe51C()
    {
        super("3DO/Cockpit/He51C/hier.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        pictFlap = 0.0F;
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
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitNightMats = (new java.lang.String[] {
            "Compass", "gauge1", "gauge2", "gauge3", "gauge4", "gauge5", "DM_gauge1", "DM_gauge2"
        });
        ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
            ((com.maddox.il2.engine.Actor)this).acoustics.globFX = new ReverbFXRoom(0.45F);
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
        float f = ((com.maddox.il2.objects.air.Cockpit)this).cvt(com.maddox.il2.engine.Engine.land().config.declin, -90F, 90F, 9F, 0.0F);
        float f1 = ((com.maddox.il2.objects.air.Cockpit)this).floatindex(f, af);
        compassNorth = new Vector3d(0.0D, java.lang.Math.cos(0.017452777777777779D * (double)f1), -java.lang.Math.sin(0.017452777777777779D * (double)f1));
        compassSouth = new Vector3d(0.0D, -java.lang.Math.cos(0.017452777777777779D * (double)f1), java.lang.Math.sin(0.017452777777777779D * (double)f1));
        float f2 = ((com.maddox.il2.objects.air.Cockpit)this).floatindex(f, af1);
        ((com.maddox.JGP.Tuple3d) (compassNorth)).scale((f2 / 600F) * com.maddox.rts.Time.tickLenFs());
        ((com.maddox.JGP.Tuple3d) (compassSouth)).scale((f2 / 600F) * com.maddox.rts.Time.tickLenFs());
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
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getLoc(setOld.planeLoc);
        }
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getLoc(setNew.planeLoc);
        ((com.maddox.JGP.Tuple3d) (setNew.planeMove)).set(((com.maddox.JGP.Tuple3d) (setNew.planeLoc)));
        ((com.maddox.JGP.Tuple3d) (setNew.planeMove)).sub(((com.maddox.JGP.Tuple3d) (setOld.planeLoc)));
        ((com.maddox.JGP.Tuple3d) (accel)).set(((com.maddox.JGP.Tuple3d) (setNew.planeMove)));
        ((com.maddox.JGP.Tuple3d) (accel)).sub(((com.maddox.JGP.Tuple3d) (setOld.planeMove)));
        ((com.maddox.JGP.Tuple3d) (accel)).scale(compassSc);
        accel.x = -((com.maddox.JGP.Tuple3d) (accel)).x;
        accel.y = -((com.maddox.JGP.Tuple3d) (accel)).y;
        accel.z = -((com.maddox.JGP.Tuple3d) (accel)).z - 1.0D;
        ((com.maddox.JGP.Tuple3d) (accel)).scale(compassAcc);
        if(accel.length() > -compassZ * 0.69999999999999996D)
            ((com.maddox.JGP.Tuple3d) (accel)).scale((-compassZ * 0.69999999999999996D) / accel.length());
        for(int i = 0; i < 4; i++)
        {
            ((com.maddox.JGP.Tuple3d) (compassSpeed[i])).set(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[i])));
            ((com.maddox.JGP.Tuple3d) (compassSpeed[i])).sub(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[i])));
        }

        for(int j = 0; j < 4; j++)
        {
            double d = compassSpeed[j].length();
            d = 0.98499999999999999D / (1.0D + d * d * 15D);
            ((com.maddox.JGP.Tuple3d) (compassSpeed[j])).scale(d);
        }

        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        ((com.maddox.JGP.Tuple3d) (vector3d)).set(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[0])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[1])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[2])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[3])));
        vector3d.normalize();
        for(int k = 0; k < 4; k++)
        {
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            double d1 = vector3d.dot(((com.maddox.JGP.Tuple3d) (compassSpeed[k])));
            ((com.maddox.JGP.Tuple3d) (vector3d1)).set(((com.maddox.JGP.Tuple3d) (vector3d)));
            d1 *= 0.28000000000000003D;
            ((com.maddox.JGP.Tuple3d) (vector3d1)).scale(-d1);
            ((com.maddox.JGP.Tuple3d) (compassSpeed[k])).add(((com.maddox.JGP.Tuple3d) (vector3d1)));
        }

        for(int l = 0; l < 4; l++)
            ((com.maddox.JGP.Tuple3d) (compassSpeed[l])).add(((com.maddox.JGP.Tuple3d) (accel)));

        ((com.maddox.JGP.Tuple3d) (compassSpeed[0])).add(((com.maddox.JGP.Tuple3d) (compassNorth)));
        ((com.maddox.JGP.Tuple3d) (compassSpeed[2])).add(((com.maddox.JGP.Tuple3d) (compassSouth)));
        for(int i1 = 0; i1 < 4; i1++)
        {
            ((com.maddox.JGP.Tuple3d) (setNew.compassPoint[i1])).set(((com.maddox.JGP.Tuple3d) (setOld.compassPoint[i1])));
            ((com.maddox.JGP.Tuple3d) (setNew.compassPoint[i1])).add(((com.maddox.JGP.Tuple3d) (compassSpeed[i1])));
        }

        ((com.maddox.JGP.Tuple3d) (vector3d)).set(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[0])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[1])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[2])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[3])));
        ((com.maddox.JGP.Tuple3d) (vector3d)).scale(0.25D);
        com.maddox.JGP.Vector3d vector3d2 = new Vector3d(((com.maddox.JGP.Tuple3d) (vector3d)));
        vector3d2.normalize();
        ((com.maddox.JGP.Tuple3d) (vector3d2)).scale(-compassZ);
        ((com.maddox.JGP.Tuple3d) (vector3d2)).sub(((com.maddox.JGP.Tuple3d) (vector3d)));
        for(int j1 = 0; j1 < 4; j1++)
            ((com.maddox.JGP.Tuple3d) (setNew.compassPoint[j1])).add(((com.maddox.JGP.Tuple3d) (vector3d2)));

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
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getOrient(orientation);
        for(int k2 = 0; k2 < 4; k2++)
        {
            ((com.maddox.JGP.Tuple3d) (setNew.cP[k2])).set(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[k2])));
            orientation.transformInv(((com.maddox.JGP.Tuple3d) (setNew.cP[k2])));
        }

        com.maddox.JGP.Vector3d vector3d3 = new Vector3d();
        ((com.maddox.JGP.Tuple3d) (vector3d3)).set(((com.maddox.JGP.Tuple3d) (setNew.cP[0])));
        ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[1])));
        ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[2])));
        ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[3])));
        ((com.maddox.JGP.Tuple3d) (vector3d3)).scale(0.25D);
        com.maddox.JGP.Vector3d vector3d4 = new Vector3d();
        ((com.maddox.JGP.Tuple3d) (vector3d4)).set(((com.maddox.JGP.Tuple3d) (vector3d3)));
        vector3d4.normalize();
        float f = (float)(((com.maddox.JGP.Tuple3d) (vector3d4)).x * ((com.maddox.JGP.Tuple3d) (vector3d4)).x + ((com.maddox.JGP.Tuple3d) (vector3d4)).y * ((com.maddox.JGP.Tuple3d) (vector3d4)).y);
        if((double)f > compassLimit || ((com.maddox.JGP.Tuple3d) (vector3d3)).z > 0.0D)
        {
            for(int l2 = 0; l2 < 4; l2++)
            {
                ((com.maddox.JGP.Tuple3d) (setNew.cP[l2])).set(((com.maddox.JGP.Tuple3d) (setOld.cP[l2])));
                ((com.maddox.JGP.Tuple3d) (setNew.compassPoint[l2])).set(((com.maddox.JGP.Tuple3d) (setOld.cP[l2])));
                orientation.transform(((com.maddox.JGP.Tuple3d) (setNew.compassPoint[l2])));
            }

            ((com.maddox.JGP.Tuple3d) (vector3d3)).set(((com.maddox.JGP.Tuple3d) (setNew.cP[0])));
            ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[1])));
            ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[2])));
            ((com.maddox.JGP.Tuple3d) (vector3d3)).add(((com.maddox.JGP.Tuple3d) (setNew.cP[3])));
            ((com.maddox.JGP.Tuple3d) (vector3d3)).scale(0.25D);
        }
        ((com.maddox.JGP.Tuple3d) (vector3d4)).set(((com.maddox.JGP.Tuple3d) (setNew.cP[0])));
        ((com.maddox.JGP.Tuple3d) (vector3d4)).sub(((com.maddox.JGP.Tuple3d) (vector3d3)));
        double d2 = -java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (vector3d3)).y, -((com.maddox.JGP.Tuple3d) (vector3d3)).z);
        vectorRot2(vector3d3, d2);
        vectorRot2(vector3d4, d2);
        double d3 = java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (vector3d3)).x, -((com.maddox.JGP.Tuple3d) (vector3d3)).z);
        vectorRot1(vector3d4, -d3);
        double d4 = java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (vector3d4)).y, ((com.maddox.JGP.Tuple3d) (vector3d4)).x);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedCompass_A", -(float)((d2 * 180D) / 3.1415926000000001D), -(float)((d3 * 180D) / 3.1415926000000001D), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedCompass_B", 0.0F, (float)(90D - (d4 * 180D) / 3.1415926000000001D), 0.0F);
        compassFirst++;
    }

    private void vectorRot1(com.maddox.JGP.Vector3d vector3d, double d)
    {
        double d1 = java.lang.Math.sin(d);
        double d2 = java.lang.Math.cos(d);
        double d3 = ((com.maddox.JGP.Tuple3d) (vector3d)).x * d2 - ((com.maddox.JGP.Tuple3d) (vector3d)).z * d1;
        vector3d.z = ((com.maddox.JGP.Tuple3d) (vector3d)).x * d1 + ((com.maddox.JGP.Tuple3d) (vector3d)).z * d2;
        vector3d.x = d3;
    }

    private void vectorRot2(com.maddox.JGP.Vector3d vector3d, double d)
    {
        double d1 = java.lang.Math.sin(d);
        double d2 = java.lang.Math.cos(d);
        double d3 = ((com.maddox.JGP.Tuple3d) (vector3d)).y * d2 - ((com.maddox.JGP.Tuple3d) (vector3d)).z * d1;
        vector3d.z = ((com.maddox.JGP.Tuple3d) (vector3d)).y * d1 + ((com.maddox.JGP.Tuple3d) (vector3d)).z * d2;
        vector3d.y = d3;
    }

    private void compassDist(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1, double d)
    {
        com.maddox.JGP.Vector3d vector3d2 = new Vector3d();
        ((com.maddox.JGP.Tuple3d) (vector3d2)).set(((com.maddox.JGP.Tuple3d) (vector3d)));
        ((com.maddox.JGP.Tuple3d) (vector3d2)).sub(((com.maddox.JGP.Tuple3d) (vector3d1)));
        double d1 = vector3d2.length();
        if(d1 < 9.9999999999999995E-007D)
            d1 = 9.9999999999999995E-007D;
        d1 = (d - d1) / d1 / 2D;
        ((com.maddox.JGP.Tuple3d) (vector3d2)).scale(d1);
        ((com.maddox.JGP.Tuple3d) (vector3d)).add(((com.maddox.JGP.Tuple3d) (vector3d2)));
        ((com.maddox.JGP.Tuple3d) (vector3d1)).sub(((com.maddox.JGP.Tuple3d) (vector3d2)));
    }

    public void reflectWorldToInstruments(float f)
    {
        float f1 = 0.0F;
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(((com.maddox.il2.objects.air.CR_42X) ((com.maddox.il2.objects.air.He51C)((com.maddox.il2.objects.air.Cockpit)this).aircraft())).bChangedPit)
        {
            reflectPlaneToModel();
            ((com.maddox.il2.objects.air.He51C)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).bChangedPit = false;
        }
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN01");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.air.Cockpit)this).fm)).actor).getGunByHookName("_MGUN02");
        }
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedManPress", 0.0F, pictManifold = 0.85F * pictManifold + 0.15F * ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getManifoldPressure() * 760F, 260F, 1200F, 33.3F, 360F), 0.0F);
        float f2 = -15F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.AileronControl);
        float f3 = 14F * (pictElev = 0.85F * pictElev + 0.2F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.ElevatorControl);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("StickB", 0.0F, -f2, f3);
        f2 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getRudder();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Pedal_L", 0.0F, 18F * f2, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Pedal_R", 0.0F, 18F * f2, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TQHandle", 0.0F, 0.0F, 75F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.throttle, setOld.throttle, f));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("MixLvr", 0.0F, 0.0F, 65F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.mix, setOld.mix, f));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("CowlFlapLvr", 0.0F, -70F * ((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.radiator, setOld.radiator, f), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedAlt_Km", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.Cockpit)this).interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 450F), 0.0F);
        float f4 = com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Loc)).z, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).getSpeedKMH());
        if(f4 < 100F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedSpeed", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(f4, 0.0F, 100F, 0.0F, -28.4F), 0.0F);
        else
        if(f4 < 200F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedSpeed", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(f4, 100F, 200F, -28.4F, -102F), 0.0F);
        else
        if(f4 < 300F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedSpeed", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(f4, 200F, 300F, -102F, -191.5F), 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedSpeed", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(f4, 300F, 450F, -191.5F, -326F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedFuel", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).M.fuel, 0.0F, 250F, 0.0F, 255.5F), 0.0F);
        f2 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut;
        if(f2 < 20F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 20F, 0.0F, 15F), 0.0F);
        else
        if(f2 < 40F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 20F, 40F, 15F, 50F), 0.0F);
        else
        if(f2 < 60F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 40F, 60F, 50F, 102.5F), 0.0F);
        else
        if(f2 < 80F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 60F, 80F, 102.5F, 186F), 0.0F);
        else
        if(f2 < 100F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 80F, 100F, 186F, 283F), 0.0F);
        else
        if(f2 < 120F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 100F, 120F, 283F, 314F), 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 120F, 140F, 314F, 345F), 0.0F);
        f2 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getRPM();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedRPM", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 3000F, 0.0F, 310F), 0.0F);
        if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren() < -110F || ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).Or)).getKren() > 110F)
            rpmGeneratedPressure = rpmGeneratedPressure - 0.5F;
        else
        if(f2 < rpmGeneratedPressure)
            rpmGeneratedPressure = rpmGeneratedPressure - (rpmGeneratedPressure - f2) * 0.01F;
        else
            rpmGeneratedPressure = rpmGeneratedPressure + (f2 - rpmGeneratedPressure) * 0.001F;
        if(rpmGeneratedPressure < 600F)
            oilPressure = ((com.maddox.il2.objects.air.Cockpit)this).cvt(rpmGeneratedPressure, 0.0F, 600F, 0.0F, 4F);
        else
        if(rpmGeneratedPressure < 900F)
            oilPressure = ((com.maddox.il2.objects.air.Cockpit)this).cvt(rpmGeneratedPressure, 600F, 900F, 4F, 7F);
        else
            oilPressure = ((com.maddox.il2.objects.air.Cockpit)this).cvt(rpmGeneratedPressure, 900F, 1200F, 7F, 10F);
        float f5 = 0.0F;
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut > 90F)
            f5 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 90F, 120F, 1.1F, 1.5F);
        else
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut < 50F)
            f5 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 0.0F, 50F, 1.5F, 0.9F);
        else
            f5 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tOilOut, 50F, 90F, 0.9F, 1.1F);
        float f6 = f5 * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getReadyness() * oilPressure;
        if(f6 < 12F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilPress", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f6, 0.0F, 12F, 0.0F, 230F), 0.0F);
        else
        if(f6 < 16F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilPress", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f6, 12F, 16F, 230F, 285F), 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedOilPress", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f6, 16F, 32F, 285F, 320F), 0.0F);
        f2 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].tWaterOut;
        if(f2 < 20F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 20F, 0.0F, 15F), 0.0F);
        else
        if(f2 < 40F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 20F, 40F, 15F, 50F), 0.0F);
        else
        if(f2 < 60F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 40F, 60F, 50F, 109F), 0.0F);
        else
        if(f2 < 80F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 60F, 80F, 109F, 192F), 0.0F);
        else
        if(f2 < 100F)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 80F, 100F, 192F, 294F), 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedWatTemp", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 100F, 111.5F, 294F, 356.1F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedFuelPress", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(rpmGeneratedPressure, 0.0F, 1200F, 0.0F, 260F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedBank", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.turn, -0.2F, 0.2F, 22.5F, -22.5F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedTurn", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.CockpitPilot)this).getBall(8D), -8F, 8F, 9.5F, -9.5F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("NeedClimb", 0.0F, -((com.maddox.il2.objects.air.Cockpit)this).cvt(setNew.vspeed, -10F, 10F, 180F, -180F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TrimIndicator", 120F * -((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("ElevTrim", 0.0F, 600F * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.getTrimElevatorControl(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("IgnitionSwitch", 0.0F, 90F * (float)(1 + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).EI.engines[0].getControlMagnetos()), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Trigger1", 10F * (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.saveWeaponControl[0] ? 1.0F : 0.0F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Trigger2", 10F * (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.saveWeaponControl[3] ? 1.0F : 0.0F), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 2) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageGlass1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 1) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Gages1_d0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Gages1_d1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedOilTemp", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedSpeed", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedWatTemp", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedAlt_Km", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageHull1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x40) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Gages2_d0", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("Gages2_d1", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedClimb", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedBank", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("NeedTurn", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageHull1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 4) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageHull2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageGlass3", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 8) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageHull3", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageGlass3", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x10) != 0)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageGlass2", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("DamageHull4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).AS.astateCockpitState & 0x80) != 0)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("OilSplats", true);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingLMid_D0", hiermesh.isChunkVisible("WingLMid_D0"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("WingRMid_D0", hiermesh.isChunkVisible("WingRMid_D0"));
    }

    public void doToggleUp(boolean flag)
    {
        super.doToggleUp(flag);
    }

    public void destroy()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).destroy();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Matt1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Matt2D2o", mat);
    }

    public void toggleLight()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl = !((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl;
        if(((com.maddox.il2.objects.air.Cockpit)this).cockpitLightControl)
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(true);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).setNightMats(false);
    }

    private com.maddox.il2.objects.weapons.Gun gun[] = {
        null, null
    };
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float pictFlap;
    private float pictManifold;
    private float tailWheelLock;
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
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 38F, 76.5F, 116F, 156F, 195F, 234F, 271F, 308.5F, 
        326F
    };
    private static final float oilScale[] = {
        0.0F, 36.5F, 53.5F, 103F, 194.5F, 332F
    };














}
