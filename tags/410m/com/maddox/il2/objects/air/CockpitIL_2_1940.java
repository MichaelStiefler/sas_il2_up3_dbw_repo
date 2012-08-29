// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitIL_2_1940.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
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
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitIL_2_1940 extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                if(fm.Gears.isHydroOperable())
                    setNew.undercarriage = (10F * setOld.undercarriage + fm.CT.GearControl) / 11F;
                setNew.flaps = (10F * setOld.flaps + (fm.CT.FlapsControl <= 0.0F ? 1.0F : 0.0F)) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + fm.Or.azimut()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(fm.CT.saveWeaponControl[2])
                {
                    roControl = true;
                    roControlTime = com.maddox.rts.Time.current();
                }
                if(roControl)
                {
                    setNew.roPos = (2.0F * setOld.roPos - 230F) / 3F;
                    if(roControlTime < com.maddox.rts.Time.current() - 2210L)
                        roControl = false;
                } else
                {
                    setNew.roPos = (14F * setOld.roPos - 0.0F) / 15F;
                }
                if(fm.CT.saveWeaponControl[3])
                {
                    bombControl = true;
                    bombControlTime = com.maddox.rts.Time.current();
                }
                if(bombControl)
                {
                    setNew.bombPos = (2.0F * setOld.bombPos - 220F) / 3F;
                    if(bombControlTime < com.maddox.rts.Time.current() - 2210L)
                        bombControl = false;
                } else
                {
                    setNew.bombPos = (14F * setOld.bombPos - 0.0F) / 15F;
                }
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle;
        float undercarriage;
        float flaps;
        float altimeter;
        float azimuth;
        float vspeed;
        float roPos;
        float bombPos;
        float xyz[] = {
            0.0F, 0.0F, 0.0F
        };
        float ypr[] = {
            0.0F, 0.0F, 0.0F
        };

        private Variables()
        {
            roPos = 0.0F;
            bombPos = 0.0F;
        }

    }


    public CockpitIL_2_1940()
    {
        super("3DO/Cockpit/Il-2-1940Early/hier.him", "il2");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        roControlTime = 0L;
        bombControlTime = 0L;
        previous = 0L;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "_LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(245F, 238F, 126F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("_LAMPHOOK1", light1);
        hooknamed = new HookNamed(mesh, "_LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(245F, 238F, 126F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("_LAMPHOOK2", light2);
        cockpitNightMats = (new java.lang.String[] {
            "prib_one", "prib_two", "prib_three", "prib_four", "prib_six", "prib_six_na", "shkala", "prib_one_dd", "prib_two_dd", "prib_three_dd"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("richag", 0.0F, 10F * (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl), 15F * (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl));
        mesh.chunkSetAngles("os_V", 0.0F, 0.0F, 10F * fm.CT.getElevator());
        mesh.chunkSetAngles("tyga_V", 0.0F, -12F * fm.CT.getElevator(), 0.0F);
        mesh.chunkSetAngles("Ped_Base", 0.0F, -15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, 15F * fm.CT.getRudder(), 0.0F);
        mesh.chunkSetAngles("ruchkaGaza", 0.0F, -60F + 120F * interp(setNew.throttle, setOld.throttle, f), 0.0F);
        mesh.chunkSetAngles("ruchkaShassi", 0.0F, 85F * interp(setNew.undercarriage, setOld.undercarriage, f), 0.0F);
        mesh.chunkSetAngles("ruchkaShitkov", 0.0F, 85F - 85F * interp(setNew.flaps, setOld.flaps, f), 0.0F);
        mesh.chunkSetAngles("r_one", 0.0F, -20F * (float)(fm.CT.saveWeaponControl[0] ? 1 : 0), 0.0F);
        mesh.chunkSetAngles("r_two", 0.0F, -20F * (float)(fm.CT.saveWeaponControl[1] ? 1 : 0), 0.0F);
        mesh.chunkSetAngles("r_RO", 0.0F, setNew.roPos, 0.0F);
        mesh.chunkSetAngles("r_bomb", 0.0F, setNew.bombPos, 0.0F);
        mesh.chunkSetAngles("r_turn", 0.0F, 20F * fm.CT.BrakeControl, 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -30F, 30F, -30F, 30F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        setOld.xyz[2] = cvt(fm.Or.getTangage(), -40F, 40F, 0.03F, -0.03F);
        mesh.chunkSetLocate("zHorizon1a", setOld.xyz, setOld.ypr);
        mesh.chunkSetAngles("zHorizon1b", 0.0F, -fm.Or.getKren(), 0.0F);
        mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.286F), 0.0F);
        mesh.chunkSetAngles("zGas1a", 0.0F, cvt(fm.M.fuel / 0.72F, 0.0F, 300F, 0.0F, 180F), 0.0F);
        if((fm.AS.astateCockpitState & 2) == 0 && (fm.AS.astateCockpitState & 4) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
        {
            mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F);
            mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 180F), 0.0F);
            mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 180F), 0.0F);
            mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8F, 0.0F, -180F), 0.0F);
        }
        w.set(fm.getW());
        mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
        mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, 24F, -24F), 0.0F);
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F);
        mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zTWater1a", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 120F, -0F, -86F), 0.0F);
        if((fm.AS.astateCockpitState & 1) == 0 && (fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0)
            mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(fm.EI.engines[0].tOilIn, 0.0F, 120F, -0F, -86F), 0.0F);
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Green2", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Red1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Red2", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Red2", fm.CT.getGear() == 0.0F);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.6F);
            light2.light.setEmit(0.005F, 0.6F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 2) != 0 || (fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_d1", true);
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("zManifold1a", false);
            mesh.chunkVisible("zHorizon1a", false);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
        }
        if((fm.AS.astateCockpitState & 1) != 0 || (fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_d2", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
            mesh.chunkVisible("zRPM1a", false);
            mesh.chunkVisible("zRPM1b", false);
            mesh.chunkVisible("zVariometer1a", false);
            mesh.chunkVisible("zTurn1a", false);
            mesh.chunkVisible("zSlide1a", false);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    boolean roControl;
    long roControlTime;
    boolean bombControl;
    long bombControlTime;
    long previous;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 15.5F, 50F, 95.5F, 137F, 182.5F, 212F, 230F, 242F, 
        254.5F, 267.5F, 279F, 292F, 304F, 317F, 329.5F, 330F
    };







}
