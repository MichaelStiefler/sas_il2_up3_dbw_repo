// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitBattleMkII.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, BattleMkII, Cockpit, Aircraft

public class CockpitBattleMkII extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float azimuth;
        float throttle;
        float mix;
        float prop;
        float turn;
        float vspeed;
        float stbyPosition;
        float waypointAzimuth;

        private Variables()
        {
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            if(((com.maddox.il2.objects.air.BattleMkII)aircraft()).bChangedPit)
            {
                reflectPlaneToModel();
                ((com.maddox.il2.objects.air.BattleMkII)aircraft()).bChangedPit = false;
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
            if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                setNew.azimuth = (21F * setOld.azimuth + fm.Or.azimut()) / 22F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.throttle = (10F * setOld.throttle + fm.EI.engines[0].getControlThrottle()) / 11F;
            setNew.mix = (10F * setOld.mix + fm.EI.engines[0].getControlMix()) / 11F;
            setNew.prop = setOld.prop;
            if(setNew.prop < fm.EI.engines[0].getControlProp() - 0.01F)
                setNew.prop += 0.0025F;
            if(setNew.prop > fm.EI.engines[0].getControlProp() + 0.01F)
                setNew.prop -= 0.0025F;
            w.set(fm.getW());
            fm.Or.transform(w);
            setNew.turn = (12F * setOld.turn + w.z) / 13F;
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
            pictSupc = 0.8F * pictSupc + 0.2F * (float)fm.EI.engines[0].getControlCompressor();
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitBattleMkII()
    {
        super("3DO/Cockpit/Gladiator/BattleMKII.him", "u2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictSupc = 0.0F;
        pictFlap = 0.0F;
        cockpitNightMats = (new java.lang.String[] {
            "PRIB_01", "PRIB_02", "PRIB_03", "PRIB_04", "PRIB_05", "PRIB_06", "PRIB_07"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.3F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(((com.maddox.il2.objects.air.BattleMkII)aircraft()).bChangedPit)
        {
            reflectPlaneToModel();
            ((com.maddox.il2.objects.air.BattleMkII)aircraft()).bChangedPit = false;
        }
        mesh.chunkSetAngles("Z_ManPrs1", 0.0F, 0.0F, 4F - cvt(pictManifold = 0.85F * pictManifold + 0.15F * fm.EI.engines[0].getManifoldPressure(), 0.600034F, 1.66661F, -124.8F, 208F));
        mesh.chunkSetAngles("Z_Column", 0.0F, 14F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl), 14F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("Z_PedalStrut", 15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_LeftPedal", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RightPedal", -15F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Throttle", 0.0F, 0.0F, -51.8F * interp(setNew.throttle, setOld.throttle, f));
        mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, -52.3F * interp(setNew.mix, setOld.mix, f));
        mesh.chunkSetAngles("Z_Altimeter1", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -10800F));
        mesh.chunkSetAngles("Z_Altimeter2", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, -1080F));
        float f1 = com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH());
        mesh.chunkSetAngles("Z_Speedometer1", 0.0F, 0.0F, -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeed()), 0.0F, 180.0555F, 0.0F, 35F), speedometerScale));
        mesh.chunkSetAngles("Z_Compass1", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", 0.0F, 0.0F, cvt(fm.EI.engines[0].getRPM(), 1200F, 3400F, 0.0F, -328F));
        mesh.chunkSetAngles("Z_OilPress1", 0.0F, 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 12F, 0.0F, -309F));
        mesh.chunkSetAngles("Z_Hour1", 0.0F, 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, -720F));
        mesh.chunkSetAngles("Z_Minute1", 0.0F, 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F));
        mesh.chunkSetAngles("Z_Second1", 0.0F, 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, -360F));
        mesh.chunkSetAngles("Z_FuelPress1", 0.0F, 0.0F, cvt(fm.M.fuel, 0.0F, 282F, 0.0F, 71F));
        mesh.chunkSetAngles("Z_OilTemp1", 0.0F, 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 140F, 0.0F, -315.5F));
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(fm.getAOA(), -20F, 20F, 0.042F, -0.042F);
        mesh.chunkSetLocate("Z_AoA1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Turn1", 0.0F, 0.0F, cvt(setNew.turn, -0.2F, 0.2F, -22.5F, 22.5F));
        mesh.chunkSetAngles("Turn2", 0.0F, 0.0F, cvt(getBall(8D), -8F, 8F, 16.9F, -16.9F));
        mesh.chunkSetAngles("Z_Climb1", 0.0F, 0.0F, cvt(setNew.vspeed, -15F, 15F, 180F, -180F));
        mesh.chunkSetAngles("Z_Oxypres1", 0.0F, 0.0F, -260F);
        mesh.chunkSetAngles("Z_Oxypres2", 0.0F, 0.0F, -320F);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0)
            if((fm.AS.astateCockpitState & 1) != 0);
        if((fm.AS.astateCockpitState & 0x40) != 0);
        if((fm.AS.astateCockpitState & 4) != 0);
        if((fm.AS.astateCockpitState & 8) != 0);
        if((fm.AS.astateCockpitState & 0x80) != 0);
        if((fm.AS.astateCockpitState & 0x10) != 0);
        if((fm.AS.astateCockpitState & 0x20) != 0);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private float pictSupc;
    private float pictFlap;
    private float pictManifold;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 1.0F, 3F, 7.5F, 34.5F, 46F, 63F, 76F, 94F, 
        112.5F, 131F, 150F, 168.5F, 187F, 203F, 222F, 242.5F, 258.5F, 277F, 
        297F, 315.5F, 340F, 360F, 376.5F, 392F, 407F, 423.5F, 442F, 459F, 
        476F, 492.5F, 513F, 534.5F, 552F, 569.5F
    };
    private com.maddox.JGP.Vector3f w;












}
