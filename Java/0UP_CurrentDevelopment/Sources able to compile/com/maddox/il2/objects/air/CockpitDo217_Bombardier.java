package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

public class CockpitDo217_Bombardier extends CockpitPilot
{
    private class Variables
    {

        float elevTrim;
        float rudderTrim;
        float ailTrim;
        float cons;

        private Variables()
        {
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            float f = ((Do217)aircraft()).fSightCurForwardAngle;
            float f1 = ((Do217)aircraft()).fSightCurSideslip;
            CockpitDo217_Bombardier.calibrAngle = 360F - fm.Or.getPitch();
            mesh.chunkSetAngles("BlackBox", -10F * f1, 0.0F, CockpitDo217_Bombardier.calibrAngle + f);
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + 10F * f1, tAim + CockpitDo217_Bombardier.calibrAngle + f, 0.0F);
            }
            mesh.chunkSetAngles("Z_turret1A", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("Z_turret1B", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
            setNew.elevTrim = 0.85F * setOld.elevTrim + fm.CT.getTrimElevatorControl() * 0.15F;
            setNew.rudderTrim = 0.85F * setOld.rudderTrim + fm.CT.getTrimRudderControl() * 0.15F;
            setNew.ailTrim = 0.85F * setOld.ailTrim + fm.CT.getTrimAileronControl() * 0.15F;
            float f2 = prevFuel - fm.M.fuel;
            prevFuel = fm.M.fuel;
            f2 /= 0.72F;
            f2 /= com.maddox.rts.Time.tickLenFs();
            f2 *= 3600F;
            setNew.cons = 0.91F * setOld.cons + 0.09F * f2;
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((Do217)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Interior1_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_D0", false);
            aircraft().hierMesh().chunkVisible("Head1_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask1_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask2_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask3_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask4_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D1", false);
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
            if(aircraft() instanceof Do217_K1)
            {
                mesh.chunkVisible("k2-Box", false);
                mesh.chunkVisible("k2-Cable", false);
                mesh.chunkVisible("k2-cushion", false);
                mesh.chunkVisible("k2-FuG203", false);
                mesh.chunkVisible("k2-gunsight", false);
            } else
            {
                mesh.chunkVisible("StuviArm", false);
                mesh.chunkVisible("StuviPlate", false);
                mesh.chunkVisible("Revi_D0", false);
                mesh.chunkVisible("StuviHandle", false);
                mesh.chunkVisible("StuviLock", false);
            }
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
            ((Do217)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Interior1_D0", true);
            if(!fm.AS.isPilotParatrooper(0))
            {
                aircraft().hierMesh().chunkVisible("Pilot1_D0", !fm.AS.isPilotDead(0));
                aircraft().hierMesh().chunkVisible("Head1_D0", !fm.AS.isPilotDead(0));
                aircraft().hierMesh().chunkVisible("Pilot1_D1", fm.AS.isPilotDead(0));
            }
            if(!fm.AS.isPilotParatrooper(1))
            {
                aircraft().hierMesh().chunkVisible("Pilot2_D0", !fm.AS.isPilotDead(1));
                aircraft().hierMesh().chunkVisible("Pilot2_D1", fm.AS.isPilotDead(1));
            }
            if(!fm.AS.isPilotParatrooper(2))
            {
                aircraft().hierMesh().chunkVisible("Pilot3_D0", !fm.AS.isPilotDead(2));
                aircraft().hierMesh().chunkVisible("Pilot3_D1", fm.AS.isPilotDead(2));
            }
            if(!fm.AS.isPilotParatrooper(3))
            {
                aircraft().hierMesh().chunkVisible("Pilot4_D0", !fm.AS.isPilotDead(3));
                aircraft().hierMesh().chunkVisible("Pilot4_D1", fm.AS.isPilotDead(3));
            }
            aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
            if(aircraft() instanceof Do217_K1)
            {
                mesh.chunkVisible("k2-Box", true);
                mesh.chunkVisible("k2-Cable", true);
                mesh.chunkVisible("k2-cushion", true);
                mesh.chunkVisible("k2-FuG203", true);
                mesh.chunkVisible("k2-gunsight", true);
            } else
            {
                mesh.chunkVisible("StuviArm", true);
                mesh.chunkVisible("StuviPlate", true);
                mesh.chunkVisible("Revi_D0", true);
                mesh.chunkVisible("StuviHandle", true);
                mesh.chunkVisible("StuviLock", true);
            }
            leave();
            super.doFocusLeave();
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    private void leave()
    {
        if(bEntered)
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
        }
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused() && isToggleAim() != flag)
            if(flag)
                enter();
            else
                leave();
    }

    public CockpitDo217_Bombardier()
    {
        super("3DO/Cockpit/Do217k1/hierBombardier.him", "he111");
        bEntered = false;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        setOld = new Variables();
        setNew = new Variables();
        tmpLoc = new Loc();
        tmpP = new Point3d();
        tmpV = new Vector3d();
        prevFuel = 0.0F;
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "CAMERAAIM");
            hooknamed.computePos(this, pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
            com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(mesh, "LAMPHOOK1");
            com.maddox.il2.engine.Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed1.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc1);
            light1 = new LightPointActor(new LightPoint(), loc.getPoint());
            light1.light.setColor(218F, 143F, 128F);
            light1.light.setEmit(0.0F, 0.0F);
            pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cockpitNightMats = (new java.lang.String[] {
            "Peil1", "Peil2", "Instrument1", "Instrument2", "Instrument4", "Instrument5", "Instrument6", "Instrument7", "Instrument8", "Instrument9", 
            "Needles"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bEntered)
        {
            mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((Do217)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((Do217)aircraft()).fSightCurReadyness > 0.93F;
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zAngleMark", false);
            mesh.chunkSetAngles("ZWheel", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 40F, 0.0F);
            mesh.chunkSetAngles("ZColumn", 0.0F, -(pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F, 0.0F);
            mesh.chunkSetAngles("PedalR", 0.0F, -fm.CT.getRudder() * 10F, 0.0F);
            mesh.chunkSetAngles("PedalL", 0.0F, fm.CT.getRudder() * 10F, 0.0F);
            resetYPRmodifier();
            Cockpit.xyz[1] = cvt(fm.CT.getRudder(), -1F, 1.0F, 0.08F, -0.08F);
            mesh.chunkSetLocate("PedalRbar", Cockpit.xyz, Cockpit.ypr);
            Cockpit.xyz[1] = cvt(fm.CT.getRudder(), -1F, 1.0F, -0.08F, 0.08F);
            mesh.chunkSetLocate("PedalLbar", Cockpit.xyz, Cockpit.ypr);
            mesh.chunkSetAngles("z_ElevTrim", 0.0F, -cvt(interp(setNew.elevTrim, setOld.elevTrim, f), -0.5F, 0.5F, -750F, 750F), 0.0F);
            mesh.chunkSetAngles("z_RudderTrim", 0.0F, cvt(interp(setNew.rudderTrim, setOld.rudderTrim, f), -0.5F, 0.5F, -750F, 750F), 0.0F);
            mesh.chunkSetAngles("z_AileronTrim", 0.0F, cvt(interp(setNew.ailTrim, setOld.ailTrim, f), -0.5F, 0.5F, -750F, 750F), 0.0F);
            float f1 = setNew.cons;
            float f2 = fm.EI.engines[0].getRPM();
            float f3 = fm.EI.engines[1].getRPM();
            float f4 = (f1 * f2) / (f2 + f3);
            float f5 = (f1 * f3) / (f2 + f3);
            mesh.chunkSetAngles("z_FuelCons1", 0.0F, cvt(f4, 0.0F, 500F, 0.0F, 300F), 0.0F);
            mesh.chunkSetAngles("z_FuelCons2", 0.0F, cvt(f5, 0.0F, 500F, 0.0F, 300F), 0.0F);
            float f6 = fm.M.fuel / 0.72F;
            mesh.chunkSetAngles("z_Fuel3", 0.0F, cvt(f6, 0.0F, 1100F, 0.0F, 69F), 0.0F);
            mesh.chunkSetAngles("z_Fuel1", 0.0F, cvt(f6, 1100F, 2670F, 0.0F, 84F), 0.0F);
            mesh.chunkSetAngles("z_Fuel2", 0.0F, cvt(f6, 1100F, 2670F, 0.0F, 84F), 0.0F);
            mesh.chunkSetAngles("z_OilPres1", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 15F, 0.0F, -135F), 0.0F);
            mesh.chunkSetAngles("z_OilPres2", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 15F, 0.0F, -135F), 0.0F);
            mesh.chunkSetAngles("z_FuelPres1", 0.0F, cvt(fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F);
            mesh.chunkSetAngles("z_FuelPres2", 0.0F, cvt(fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F);
            mesh.chunkSetAngles("Z_TempCylL", 0.0F, cvt(fm.EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 75F), 0.0F);
            mesh.chunkSetAngles("Z_TempCylR", 0.0F, cvt(fm.EI.engines[1].tOilOut, 0.0F, 160F, 0.0F, 75F), 0.0F);
            mesh.chunkSetAngles("z_OAT", 0.0F, floatindex(cvt(com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.15F, -60F, 40F, 0.0F, 10F), OATscale), 0.0F);
            mesh.chunkSetAngles("Z_Turret1A", 0.0F, -fm.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("Z_Turret1B", 0.0F, fm.turret[0].tu[1], 0.0F);
            mesh.chunkSetAngles("Z_Turret5A", 0.0F, -fm.turret[4].tu[0], 0.0F);
            mesh.chunkSetAngles("Z_Turret5B", 0.0F, fm.turret[4].tu[1], 0.0F);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassHoles1", true);
            mesh.chunkVisible("XGlassHoles3", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassHoles7", true);
            mesh.chunkVisible("XGlassHoles4", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassHoles5", true);
            mesh.chunkVisible("XGlassHoles3", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassHoles1", true);
        mesh.chunkVisible("XGlassHoles6", true);
        mesh.chunkVisible("XGlassHoles4", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassHoles6", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("XGlassHoles7", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("XGlassHoles5", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
    }

    protected void mydebugcockpit(java.lang.String s)
    {
        java.lang.System.out.println(s);
    }

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private static final float speedometerScale[] = {
        0.0F, 0.1F, 19F, 37.25F, 63.5F, 91.5F, 112F, 135.5F, 159.5F, 186.5F, 
        213F, 238F, 264F, 289F, 314.5F, 339.5F, 359.5F, 360F, 360F, 360F
    };
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;
    private float pictAiler;
    private float pictElev;
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.Loc tmpLoc;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private float prevFuel;
    private static float calibrAngle = 3F;
    private static final float OATscale[] = {
        0.0F, 7F, 17F, 27F, 37F, 47F, 56F, 65F, 72F, 80F, 
        85F
    };

    static 
    {
        com.maddox.rts.Property.set(CockpitDo217_Bombardier.class, "astatePilotIndx", 0);
    }













}
