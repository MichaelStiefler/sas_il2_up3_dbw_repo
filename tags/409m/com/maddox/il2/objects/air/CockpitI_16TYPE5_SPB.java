// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitI_16TYPE5_SPB.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, TypeDockable, I_16TYPE5_SPB, Aircraft

public class CockpitI_16TYPE5_SPB extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float prop;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;
        float gCrankAngle;
        float dimPos;

        private Variables()
        {
            gCrankAngle = 0.0F;
            dimPos = 1.0F;
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                if(bNeedSetUp)
                {
                    reflectPlaneMats();
                    bNeedSetUp = false;
                }
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.prop = (10F * setOld.prop + fm.EI.engines[0].getControlProp()) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F)) / 11F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                boolean flag = false;
                if(setNew.gCrankAngle < fm.CT.getGear() - 0.005F)
                    if(java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.33F)
                    {
                        setNew.gCrankAngle += 0.0025F;
                        flag = true;
                    } else
                    {
                        setNew.gCrankAngle = fm.CT.getGear();
                        setOld.gCrankAngle = fm.CT.getGear();
                    }
                if(setNew.gCrankAngle > fm.CT.getGear() + 0.005F)
                    if(java.lang.Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.33F)
                    {
                        setNew.gCrankAngle -= 0.0025F;
                        flag = true;
                    } else
                    {
                        setNew.gCrankAngle = fm.CT.getGear();
                        setOld.gCrankAngle = fm.CT.getGear();
                    }
                if(cockpitDimControl)
                {
                    if(setNew.dimPos < 1.0F)
                        setNew.dimPos = setOld.dimPos + 0.03F;
                } else
                if(setNew.dimPos > 0.0F)
                    setNew.dimPos = setOld.dimPos - 0.03F;
                if(flag != sfxPlaying)
                {
                    if(flag)
                        sfxStart(16);
                    else
                        sfxStop(16);
                    sfxPlaying = flag;
                }
            }
            return true;
        }

        boolean sfxPlaying;

        Interpolater()
        {
            sfxPlaying = false;
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-tmpV.y, tmpV.x));
        }
    }

    public CockpitI_16TYPE5_SPB()
    {
        super("3DO/Cockpit/I-16/hier_type5.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        bEntered = false;
        bDocked = false;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new java.lang.String[] {
            "prib_one", "prib_one_dd", "prib_two", "prib_two_dd", "prib_three", "prib_three_dd", "prib_four", "prib_four_dd", "shkala", "oxigen"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(((com.maddox.il2.objects.air.TypeDockable)aircraft()).typeDockableIsDocked() != bDocked)
        {
            bDocked = !bDocked;
            if(bDocked)
            {
                com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh)((com.maddox.il2.objects.air.I_16TYPE5_SPB)aircraft()).typeDockableGetQueen()).hierMesh();
                com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
                mesh.materialReplace("Gloss1D0o", mat);
                mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
                mesh.materialReplace("Matt1D0o", mat);
                mat = hiermesh.material(hiermesh.materialFind("Overlay7"));
                mesh.materialReplace("Overlay7", mat);
                if(((com.maddox.il2.objects.air.I_16TYPE5_SPB)aircraft()).typeDockableGetDockport() == 0)
                    mesh.chunkVisible("WingLIn_D0_00", true);
                else
                    mesh.chunkVisible("WingRIn_D0_00", true);
            } else
            {
                mesh.chunkVisible("WingLIn_D0_00", false);
                mesh.chunkVisible("WingRIn_D0_00", false);
            }
        }
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Ped_Base", 0.0F, -fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalL", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("PedalR", 0.0F, fm.CT.getRudder() * 15F, 0.0F);
        mesh.chunkSetAngles("Fire1", 0.0F, -20F * (float)(fm.CT.WeaponControl[0] ? 1 : 0), 0.0F);
        mesh.chunkSetAngles("Thtl", 30F - 57F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Thtl_Rod", -30F + 57F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Gear_Crank", (15840F * interp(setNew.gCrankAngle, setOld.gCrankAngle, f)) % 360F, 0.0F, 0.0F);
        mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(fm.Or.getTangage(), -40F, 40F, -40F, 40F), 0.0F);
        mesh.chunkSetAngles("zAzimuth1b", 0.0F, -90F - interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("zGas1a", 0.0F, floatindex(cvt(fm.M.fuel, 0.0F, 190F, 0.0F, 14F), fuelQuantityScale), 0.0F);
        mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 550F, 0.0F, 11F), speedometerScale), 0.0F);
        if((fm.AS.astateCockpitState & 4) == 0 && (fm.AS.astateCockpitState & 0x10) == 0)
        {
            w.set(fm.getW());
            fm.Or.transform(w);
            mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(w.z, -0.23562F, 0.23562F, 30F, -30F), 0.0F);
            mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8D), -8F, 8F, 24F, -24F), 0.0F);
            mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(fm.EI.engines[0].tOilIn, 0.0F, 125F, 0.0F, 275F), 0.0F);
            mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 125F, 0.0F, 275F), 0.0F);
            mesh.chunkSetAngles("zPressAir1a", 0.0F, cvt(fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 10F, 0.0F, 275F), 0.0F);
        }
        mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F);
        if((fm.AS.astateCockpitState & 8) == 0 && (fm.AS.astateCockpitState & 0x20) == 0)
        {
            if(fm.EI.engines[0].getRPM() > 400F)
                mesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(fm.EI.engines[0].getRPM(), 400F, 2200F, 0.0F, 18F), engineRPMScale), 0.0F);
            else
                mesh.chunkSetAngles("zRPS1a", 0.0F, cvt(fm.EI.engines[0].getRPM(), 0.0F, 400F, 0.0F, 18F), 0.0F);
            mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.286F), 0.0F);
            mesh.chunkSetAngles("zTCil1a", 0.0F, cvt(fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, -95F), 0.0F);
        }
        mesh.chunkSetAngles("zClock1a", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zClock1b", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zPressOil1a", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 270F), 0.0F);
        mesh.chunkVisible("Z_Red1", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Red2", fm.CT.getGear() == 0.0F);
        mesh.chunkVisible("Z_Green1", fm.CT.getGear() == 1.0F);
        mesh.chunkVisible("Z_Green2", fm.CT.getGear() == 1.0F);
        float f1 = cvt(interp(setNew.dimPos, setOld.dimPos, f), 0.0F, 1.0F, 0.0F, -90F);
        mesh.chunkSetAngles("Z_sight_cap", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Z_sight_cap_big", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Z_sight_cap_inside", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Z_sight_cap_inside", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("SightCapSwitch", f1 * 0.75F, 0.0F, 0.0F);
        if(f1 <= -88F)
            mesh.chunkVisible("Z_sight_cap_inside", false);
        else
            mesh.chunkVisible("Z_sight_cap_inside", true);
        com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = fm.CT.getCockpitDoor() * -0.548F;
        mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        mesh.chunkSetLocate("Glass", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        mesh.chunkSetLocate("Z_OilSplats_D1", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_dd", true);
            mesh.chunkVisible("zSpeed1a", false);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zPressOil1a", false);
            mesh.chunkVisible("zVariometer1a", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_dd", true);
            mesh.chunkVisible("zAzimuth1a", false);
            mesh.chunkVisible("zAzimuth1b", false);
            mesh.chunkVisible("zManifold1a", false);
            mesh.chunkVisible("zGas1a", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
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
    }

    public void toggleDim()
    {
        cockpitDimControl = !cockpitDimControl;
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused() && isToggleAim() != flag)
            if(flag)
                enter();
            else
                leave();
    }

    private void leave()
    {
        if(bEntered)
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            bEntered = false;
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            mesh.chunkVisible("superretic", false);
            mesh.chunkVisible("Z_sight_cap_big", false);
        }
    }

    private void enter()
    {
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.doAim(true);
        bEntered = true;
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 31");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        mesh.chunkVisible("superretic", true);
        mesh.chunkVisible("Z_sight_cap_big", true);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
        {
            leave();
            super.doFocusLeave();
        }
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
    public com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float saveFov;
    private boolean bEntered;
    private boolean bDocked;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 18F, 44F, 74.5F, 106F, 136.3F, 169.5F, 207.5F, 245F, 
        287.5F, 330F
    };
    private static final float fuelQuantityScale[] = {
        0.0F, 38.5F, 74.5F, 98.5F, 122F, 143F, 163F, 182.5F, 203F, 221F, 
        239.5F, 256F, 274F, 295F, 295F, 295F
    };
    private static final float engineRPMScale[] = {
        18F, 38F, 61F, 81F, 102F, 120F, 137F, 152F, 167F, 183F, 
        198F, 213F, 227F, 241F, 254F, 267F, 280F, 292F, 306F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;









}
