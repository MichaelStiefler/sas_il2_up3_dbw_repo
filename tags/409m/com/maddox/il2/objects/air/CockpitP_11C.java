// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitP_11C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft, P_11C

public class CockpitP_11C extends com.maddox.il2.objects.air.CockpitPilot
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
            com.maddox.il2.objects.air.P_11C _tmp = (com.maddox.il2.objects.air.P_11C)aircraft();
            if(com.maddox.il2.objects.air.P_11C.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.P_11C _tmp1 = (com.maddox.il2.objects.air.P_11C)aircraft();
                com.maddox.il2.objects.air.P_11C.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                setNew.azimuth = (35F * setOld.azimuth + fm.Or.azimut()) / 36F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (33F * setOld.turn + w.z) / 34F;
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
        float turn;

        private Variables()
        {
        }

    }


    public CockpitP_11C()
    {
        super("3DO/Cockpit/P-11c/hier.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("zAlt", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9000F, 0.0F, 338.5F), 0.0F);
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 500F, 0.0F, 25F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zBoost", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.72421F, 1.27579F, -160F, 160F), 0.0F);
        mesh.chunkSetAngles("zMinute", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zHour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zCompass", 0.0F, 90F + interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, 24F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 24F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Column_Cam", 0.0F, 24F * pictAiler, 0.0F);
        mesh.chunkSetAngles("Column_Rod", 0.0F, -24F * pictAiler, 0.0F);
        mesh.chunkSetAngles("zFuelPrs", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("zOilPrs", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 270F), 0.0F);
        mesh.chunkSetAngles("zOilIn", 0.0F, floatindex(cvt(fm.EI.engines[0].tOilIn, 0.0F, 140F, 0.0F, 7F), oilTempScale), 0.0F);
        mesh.chunkSetAngles("zOilOut", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 150F, 0.0F, 270F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.Or.getTangage(), -20F, 20F, 0.0385F, -0.0385F);
        mesh.chunkSetLocate("zPitch", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("zRPM", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, 317F), 0.0F);
        mesh.chunkSetAngles("Rudder", 26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RCableL", -26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RCableR", -26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("zTurn", 0.0F, cvt(setNew.turn, -0.6F, 0.6F, 1.8F, -1.8F), 0.0F);
        mesh.chunkSetAngles("zSlide", 0.0F, cvt(getBall(7D), -7F, 7F, -10F, 10F), 0.0F);
        mesh.chunkSetAngles("Boost", 0.0F, 0.0F, -90F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, -90F * interp(setNew.throttle, setOld.throttle, f));
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Cart_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(com.maddox.il2.engine.Actor.isAlive(aircraft()))
            aircraft().hierMesh().chunkVisible("Cart_D0", true);
        super.doFocusLeave();
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLIn_D0", hiermesh.isChunkVisible("WingLIn_D0"));
        mesh.chunkVisible("WingLIn_D1", hiermesh.isChunkVisible("WingLIn_D1"));
        mesh.chunkVisible("WingLIn_D2", hiermesh.isChunkVisible("WingLIn_D2"));
        mesh.chunkVisible("WingLIn_D3", hiermesh.isChunkVisible("WingLIn_D3"));
        mesh.chunkVisible("WingLIn_CAP", hiermesh.isChunkVisible("WingLIn_CAP"));
        mesh.chunkVisible("WingRIn_D0", hiermesh.isChunkVisible("WingRIn_D0"));
        mesh.chunkVisible("WingRIn_D1", hiermesh.isChunkVisible("WingRIn_D1"));
        mesh.chunkVisible("WingRIn_D2", hiermesh.isChunkVisible("WingRIn_D2"));
        mesh.chunkVisible("WingRIn_D3", hiermesh.isChunkVisible("WingRIn_D3"));
        mesh.chunkVisible("WingRIn_CAP", hiermesh.isChunkVisible("WingRIn_CAP"));
        mesh.chunkVisible("WingLOut_CAP", hiermesh.isChunkVisible("WingLOut_CAP"));
        mesh.chunkVisible("WingROut_CAP", hiermesh.isChunkVisible("WingROut_CAP"));
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 1.0F, 3F, 6.2F, 12F, 26.5F, 39F, 51F, 67.5F, 85.5F, 
        108F, 131.5F, 154F, 180F, 205.7F, 228.2F, 251F, 272.9F, 291.9F, 314.5F, 
        336.5F, 354F, 360F, 363F, 364F, 365F
    };
    private static final float oilTempScale[] = {
        0.0F, 3.5F, 33.5F, 60.3F, 112.5F, 180F, 142.2F, 315.5F
    };










}
