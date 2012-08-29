// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitTU_2S_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft, Cockpit

public class CockpitTU_2S_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{
    private class Variables
    {

        float man1;
        float man2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        com.maddox.il2.ai.AnglesFork waypointDirection;
        float vspeed;
        float inert;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
            waypointDirection = new AnglesFork();
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                if(fm.CT.Weapons[3] != null)
                {
                    if(fm.CT.Weapons[3].length > 0)
                        bBombs[1] = !fm.CT.Weapons[3][0].haveBullets();
                    if(fm.CT.Weapons[3].length > 1)
                        bBombs[0] = !fm.CT.Weapons[3][1].haveBullets();
                    if(fm.CT.Weapons[3].length > 2)
                        bBombs[3] = !fm.CT.Weapons[3][2].haveBullets();
                    if(fm.CT.Weapons[3].length > 3)
                        bBombs[2] = !fm.CT.Weapons[3][3].haveBullets();
                    if(fm.CT.Weapons[3].length > 4)
                        bBombs[5] = !fm.CT.Weapons[3][4].haveBullets();
                    if(fm.CT.Weapons[3].length > 5)
                        bBombs[4] = !fm.CT.Weapons[3][5].haveBullets();
                    if(fm.CT.Weapons[3].length > 6)
                        bBombs[5] = bBombs[5] || !fm.CT.Weapons[3][6].haveBullets();
                    if(fm.CT.Weapons[3].length > 7)
                        bBombs[4] = bBombs[4] || !fm.CT.Weapons[3][7].haveBullets();
                    if(fm.CT.Weapons[3].length > 8)
                        bBombs[7] = !fm.CT.Weapons[3][8].haveBullets();
                    if(fm.CT.Weapons[3].length > 9)
                        bBombs[6] = !fm.CT.Weapons[3][9].haveBullets();
                }
                setNew.man1 = 0.92F * setOld.man1 + 0.08F * fm.EI.engines[0].getManifoldPressure();
                setNew.man2 = 0.92F * setOld.man2 + 0.08F * fm.EI.engines[1].getManifoldPressure();
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
                setNew.vspeed = (100F * setOld.vspeed + fm.getVertSpeed()) / 101F;
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F));
                setNew.waypointDirection.setDeg(setOld.waypointDirection.getDeg(1.0F), f);
                setNew.inert = 0.999F * setOld.inert + 0.001F * (fm.EI.engines[0].getStage() != 6 ? 0.0F : 0.867F);
            }
            return true;
        }

        Interpolater()
        {
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

    private void retoggleLight()
    {
        if(cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("NDetails_D0", false);
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("NDetails_D0", true);
        aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
        super.doFocusLeave();
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
            bNeedSetUp = false;
        mesh.chunkVisible("WireL_D0", aircraft().hierMesh().isChunkVisible("WireL_D0"));
        mesh.chunkVisible("WireR_D0", aircraft().hierMesh().isChunkVisible("WireR_D0"));
        mesh.chunkSetAngles("Z_Gear1", fm.CT.GearControl <= 0.5F ? 0.0F : -60F, 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.095F * fm.CT.getRudder();
        mesh.chunkSetLocate("Z_RightPedal", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Columnbase", -8F * (pictElev = 0.65F * pictElev + 0.35F * fm.CT.ElevatorControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Column", -45F * (pictAiler = 0.65F * pictAiler + 0.35F * fm.CT.AileronControl), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Brake", 21.5F * fm.CT.BrakeControl, 0.0F, 0.0F);
        resetYPRmodifier();
        resetYPRmodifier();
        if(fm.EI.engines[0].getControlRadiator() > 0.5F)
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.011F;
        mesh.chunkSetLocate("Z_RadL", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        if(fm.EI.engines[1].getControlRadiator() > 0.5F)
            com.maddox.il2.objects.air.Cockpit.xyz[1] = 0.011F;
        mesh.chunkSetLocate("Z_RadR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter3", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -720F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Altimeter4", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 20000F, 0.0F, -7200F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer2", -floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.018F, -0.018F);
        mesh.chunkSetLocate("Z_TurnBank1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("Z_TurnBank1Q", -fm.Or.getKren(), 0.0F, 0.0F);
        w.set(((com.maddox.JGP.Tuple3d) (fm.getW())));
        fm.Or.transform(((com.maddox.JGP.Tuple3f) (w)));
        mesh.chunkSetAngles("Z_TurnBank2", cvt(w.z, -0.23562F, 0.23562F, -27F, 27F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Climb1", cvt(setNew.vspeed, -30F, 30F, -180F, 180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass1", cvt(setNew.waypointAzimuth.getDeg(f), -90F, 90F, -55.5F, 55.5F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass2", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM1", cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, -360F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_RPM2", cvt(fm.EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, -3600F), 0.0F, 0.0F);
        if((fm.AS.astateCockpitState & 0x40) == 0)
        {
            mesh.chunkSetAngles("Z_RPM3", cvt(fm.EI.engines[1].getRPM(), 0.0F, 10000F, 0.0F, -360F), 0.0F, 0.0F);
            mesh.chunkSetAngles("Z_RPM4", cvt(fm.EI.engines[1].getRPM(), 0.0F, 10000F, 0.0F, -3600F), 0.0F, 0.0F);
        }
        mesh.chunkSetAngles("Z_Fuel1", cvt(fm.M.fuel, 0.0F, 360F, 0.0F, -198F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_InertGas", cvt(setNew.inert, 0.0F, 1.0F, 0.0F, -300F), 0.0F, 0.0F);
        float f1 = 0.0F;
        if(fm.M.fuel > 1.0F)
            f1 = cvt(fm.EI.engines[0].getRPM(), 0.0F, 570F, 0.0F, 0.26F);
        mesh.chunkSetAngles("Z_FuelPres1", cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
        f1 = 0.0F;
        if(fm.M.fuel > 1.0F)
            f1 = cvt(fm.EI.engines[1].getRPM(), 0.0F, 570F, 0.0F, 0.26F);
        mesh.chunkSetAngles("Z_FuelPres2", cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp1", cvt(fm.EI.engines[0].tWaterOut, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Temp2", cvt(fm.EI.engines[1].tWaterOut, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres1", cvt(setNew.man1, 0.399966F, 1.599864F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Pres2", cvt(setNew.man2, 0.399966F, 1.599864F, 0.0F, -300F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil1", cvt(fm.EI.engines[0].tOilIn, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oil2", cvt(fm.EI.engines[1].tOilIn, 0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Oilpres2", cvt(1.0F + 0.05F * fm.EI.engines[1].tOilOut * fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, -270F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_AirPres", -116F, 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_HPres", fm.Gears.isHydroOperable() ? -102F : 0.0F, 0.0F, 0.0F);
        f1 = com.maddox.il2.fm.Atmosphere.temperature((float)fm.Loc.z) - 273.09F;
        if(f1 < -40F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, -40F, -20F, 52F, 35F), 0.0F, 0.0F);
        else
        if(f1 < 0.0F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, -20F, 0.0F, 35F, 0.0F), 0.0F, 0.0F);
        else
        if(f1 < 20F)
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, 0.0F, 20F, 0.0F, -18.5F), 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Z_AirTemp", cvt(f1, 20F, 50F, -18.5F, -37F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_FlapPos", cvt(fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, -180F), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_Compass4", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
        mesh.chunkVisible("XRGearUp", fm.CT.getGear() < 0.01F || !fm.Gears.rgear);
        mesh.chunkVisible("XLGearUp", fm.CT.getGear() < 0.01F || !fm.Gears.lgear);
        mesh.chunkVisible("XCGearUp", fm.CT.getGear() < 0.01F);
        mesh.chunkVisible("XRGearDn", fm.CT.getGear() > 0.99F && fm.Gears.rgear);
        mesh.chunkVisible("XLGearDn", fm.CT.getGear() > 0.99F && fm.Gears.lgear);
        mesh.chunkVisible("XCGearDn", fm.CT.getGear() > 0.99F);
        mesh.chunkVisible("XFlapsUp", fm.CT.getFlap() > 0.5F);
        mesh.chunkVisible("XOverG1", fm.getOverload() > 3F);
        mesh.chunkVisible("XOverG2", fm.getOverload() < -1F);
        mesh.chunkVisible("XERimCenter", java.lang.Math.abs(fm.CT.getTrimElevatorControl()) < 0.02F);
        mesh.chunkVisible("XBomb1", bBombs[0]);
        mesh.chunkVisible("XBomb2", bBombs[1]);
        mesh.chunkVisible("XBomb3", bBombs[2]);
        mesh.chunkVisible("XBomb4", bBombs[3]);
        mesh.chunkVisible("XBomb5", bBombs[4]);
        mesh.chunkVisible("XBomb6", bBombs[5]);
        mesh.chunkVisible("XBomb7", bBombs[6]);
        mesh.chunkVisible("XBomb8", bBombs[7]);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurrelA", -orient.getYaw(), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurrelB", -orient.getTangage(), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurrelQ", -cvt(orient.getYaw(), -45F, 12F, -45F, 12F), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurrelC", -cvt(orient.getTangage(), -1F, 45F, -1F, 45F), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = cvt(orient.getYaw(), 0.0F, 12F, 0.0F, -0.14F);
        mesh.chunkSetLocate("TurrelD", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
            if(!aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = orient.getYaw();
                float f1 = orient.getTangage();
                if(f < -45F)
                    f = -45F;
                if(f > 45F)
                    f = 45F;
                if(f1 > 45F)
                    f1 = 45F;
                if(f1 < -1F)
                    f1 = -1F;
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN01")));
                doHitMasterAircraft(aircraft(), hook1, "_MGUN01");
            }
        }
    }

    public void doGunFire(boolean flag)
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            else
                bGunFire = flag;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 1) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", true);
            mesh.chunkVisible("Z_Fuel1", false);
            mesh.chunkVisible("Z_Pres1", false);
            mesh.chunkVisible("Z_Altimeter3", false);
            mesh.chunkVisible("Z_Altimeter4", false);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassDamage3", true);
            mesh.chunkVisible("XHullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage5", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage4", true);
            mesh.chunkVisible("XHullDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage6", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
        if((fm.AS.astateCockpitState & 2) != 0 && (fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("Panel_D0", false);
            mesh.chunkVisible("Panel_D1", false);
            mesh.chunkVisible("Panel_D2", true);
            mesh.chunkVisible("Z_Altimeter1", false);
            mesh.chunkVisible("Z_Altimeter2", false);
            mesh.chunkVisible("Z_Speedometer1", false);
            mesh.chunkVisible("Z_Speedometer2", false);
            mesh.chunkVisible("Z_AirTemp", false);
            mesh.chunkVisible("Z_Pres2", false);
            mesh.chunkVisible("Z_RPM1", false);
            mesh.chunkVisible("Z_RPM2", false);
            mesh.chunkVisible("Z_InertGas", false);
            mesh.chunkVisible("Z_FuelPres2", false);
            mesh.chunkVisible("Z_Oilpres1", false);
            mesh.chunkVisible("Z_Oilpres2", false);
        }
        retoggleLight();
    }

    public CockpitTU_2S_TGunner()
    {
        super("3DO/Cockpit/Pe-2series84-TGun/TGunnerTU2S.him", "he111");
        bNeedSetUp = true;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        hook1 = null;
        cockpitNightMats = (new java.lang.String[] {
            "GP_I", "GP_II", "GP_II_DM", "GP_III_DM", "GP_III", "GP_IV_DM", "GP_IV", "GP_V", "GP_VI", "GP_VII", 
            "compass", "Eqpt_II", "Trans_II", "Trans_VI_Pilot", "Trans_VII_Pilot"
        });
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private boolean bBombs[] = {
        false, false, false, false, false, false, false, false
    };
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 10.5F, 42.5F, 85F, 125F, 165.5F, 181F, 198F, 214.5F, 
        231F, 249F, 266.5F, 287.5F, 308F, 326.5F, 346F
    };
    private com.maddox.il2.engine.Hook hook1;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 1);
    }







}
