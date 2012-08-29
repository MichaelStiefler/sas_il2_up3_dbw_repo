package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;

public class CockpitLanc extends CockpitPilot
{
    private class Variables
    {

        float throttle1;
        float throttle2;
        float prop1;
        float prop2;
        float throttle3;
        float throttle4;
        float prop3;
        float prop4;
        float altimeter;
        float vspeed;
        float azimuth;
        float waypointAzimuth;
        float wiper;

        private Variables()
        {
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlProp() * 0.15F;
                setNew.throttle3 = 0.85F * setOld.throttle3 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[2].getControlThrottle() * 0.15F;
                setNew.prop3 = 0.85F * setOld.prop3 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[3].getControlProp() * 0.15F;
                setNew.throttle4 = 0.85F * setOld.throttle4 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[3].getControlThrottle() * 0.15F;
                setNew.prop4 = 0.85F * setOld.prop4 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[3].getControlProp() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                setNew.azimuth = (35F * setOld.azimuth + -((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = 0.99F * setOld.vspeed + 0.01F * fm.getVertSpeed();
                switch(iWiper)
                {
                default:
                    break;

                case 0: // '\0'
                    if(com.maddox.il2.game.Mission.curCloudsType() > 4 && fm.getSpeedKMH() < 220F && fm.getAltitude() < com.maddox.il2.game.Mission.curCloudsHeight() + 300F)
                        iWiper = 1;
                    break;

                case 1: // '\001'
                    setNew.wiper = setOld.wiper - 0.05F;
                    if(setNew.wiper < -1.03F)
                        iWiper++;
                    if(wiState >= 2)
                        break;
                    if(wiState == 0)
                    {
                        if(fxw == null)
                        {
                            fxw = aircraft().newSound("aircraft.wiper", false);
                            if(fxw != null)
                            {
                                fxw.setParent(aircraft().getRootFX());
                                fxw.setPosition(sfxPos);
                            }
                        }
                        if(fxw != null)
                            fxw.play(wiStart);
                    }
                    if(fxw != null)
                    {
                        fxw.play(wiRun);
                        wiState = 2;
                    }
                    break;

                case 2: // '\002'
                    setNew.wiper = setOld.wiper + 0.05F;
                    if(setNew.wiper > 1.03F)
                        iWiper++;
                    if(wiState > 1)
                        wiState = 1;
                    break;

                case 3: // '\003'
                    setNew.wiper = setOld.wiper - 0.05F;
                    if(setNew.wiper >= 0.02F)
                        break;
                    if(fm.getSpeedKMH() > 250F || fm.getAltitude() > com.maddox.il2.game.Mission.curCloudsHeight() + 400F)
                        iWiper++;
                    else
                        iWiper = 1;
                    break;

                case 4: // '\004'
                    setNew.wiper = setOld.wiper;
                    iWiper = 0;
                    wiState = 0;
                    if(fxw != null)
                        fxw.cancel();
                    break;
                }
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(Cockpit.P1);
            Cockpit.V.sub(Cockpit.P1, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(-((com.maddox.JGP.Tuple3d) (Cockpit.V)).y, ((com.maddox.JGP.Tuple3d) (Cockpit.V)).x));
        }
    }

    public CockpitLanc()
    {
        super("3DO/Cockpit/Lancpit/hier.him", "he111");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictBrake = 0.0F;
        pictFlap = 0.0F;
        pictGear = 0.0F;
        pictBbay = 0.0F;
        pictSupc = 0.0F;
        pictLlit = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        pictManf3 = 1.0F;
        pictManf4 = 1.0F;
        bNeedSetUp = true;
        iWiper = 0;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        fxw = null;
        wiStart = new Sample("wip_002_s.wav", 256, 65535);
        wiRun = new Sample("wip_002.wav", 256, 65535);
        wiState = 0;
        super.cockpitNightMats = (new java.lang.String[] {
            "01", "02", "03", "04", "05", "12", "20", "23", "24", "26", 
            "27", "01_damage", "03_damage", "04_damage", "24_damage"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        super.mesh.chunkSetAngles("Z_Wiper1", cvt(interp(setNew.wiper, setOld.wiper, f), -1F, 1.0F, -61F, 61F), 0.0F, 0.0F);
        super.mesh.chunkVisible("XLampGearUpL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() < 0.01F);
        super.mesh.chunkVisible("XLampGearDownL", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F);
        super.mesh.chunkVisible("XLampFuel1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() < 300F);
        super.mesh.chunkVisible("XLampFuel2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM() < 300F);
        super.mesh.chunkSetAngles("Z_Columnbase", 12F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", 45F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ColumnSwitch", 20F * (pictBrake = 0.91F * pictBrake + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.BrakeControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle1", 50F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle2", 50F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle3", 50F * interp(setNew.throttle3, setOld.throttle3, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throtle4", 50F * interp(setNew.throttle4, setOld.throttle4, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", -20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RightPedal", 20F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gear1", -55F * (pictGear = 0.9F * pictGear + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl), 0.0F, 0.0F);
        float f1;
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap()) > 0.02F)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.FlapsControl - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap() > 0.0F)
                f1 = -0.0299F;
            else
                f1 = -0F;
        } else
        {
            f1 = -0.0144F;
        }
        pictFlap = 0.8F * pictFlap + 0.2F * f1;
        resetYPRmodifier();
        Cockpit.xyz[2] = pictFlap;
        super.mesh.chunkSetLocate("Z_Flaps1", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_Trim1", -1000F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim2", 1000F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimAileronControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim3", 90F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimRudderControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop1", 0.0F, 90F * setNew.prop1, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop2", 0.0F, 90F * setNew.prop2, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop3", 0.0F, 90F * setNew.prop3, 0.0F);
        super.mesh.chunkSetAngles("Z_Prop4", 0.0F, 90F * setNew.prop4, 0.0F);
        super.mesh.chunkSetAngles("Z_BombBay1", 70F * (pictBbay = 0.9F * pictBbay + 0.1F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.BayDoorControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("COMPASS_M", 90F + setNew.azimuth, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("SHKALA_DIRECTOR", setNew.azimuth, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", 90F + setNew.azimuth, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass2", 90F + interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 10800F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 9144F, 0.0F, 1080F), 0.0F, 0.0F);
        pictManf1 = 0.91F * pictManf1 + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure();
        f1 = pictManf1 - 1.0F;
        float f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        super.mesh.chunkSetAngles("STRELKA_BOOST1", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        pictManf2 = 0.91F * pictManf2 + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getManifoldPressure();
        f1 = pictManf2 - 1.0F;
        f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        super.mesh.chunkSetAngles("STRELKA_BOOST2", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        pictManf3 = 0.91F * pictManf3 + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].getManifoldPressure();
        f1 = pictManf3 - 1.0F;
        f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        super.mesh.chunkSetAngles("STRELKA_BOOST3", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        pictManf4 = 0.91F * pictManf4 + 0.09F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].getManifoldPressure();
        f1 = pictManf4 - 1.0F;
        f2 = 1.0F;
        if(f1 <= 0.0F)
            f2 = -1F;
        f1 = java.lang.Math.abs(f1);
        super.mesh.chunkSetAngles("STRELKA_BOOST4", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13F), boostScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 763F, 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL2", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 763F, 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL3", cvt((float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel), 0.0F, (float)java.lang.Math.sqrt(88.379997253417969D), 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL4", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 1022F, 1200F, 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL5", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL6", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL7", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 851F, 1123F, 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELKA_FUEL8", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 851F, 1123F, 0.0F, 301F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_SHORT1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_LONG1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_SHORT2", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_LONG2", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_SHORT3", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_LONG3", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_SHORT4", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].getRPM(), 0.0F, 10000F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM_LONG4", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].getRPM(), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_OIL1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_OIL2", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_OIL3", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_OIL4", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].tOilOut, 0.0F, 100F, 0.0F, 266F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_RAD1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_RAD2", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_RAD3", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TEMP_RAD4", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].tWaterOut, 0.0F, 140F, 0.0F, 14F), radScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STR_OIL_LB1", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, -31F), 0.0F);
        super.mesh.chunkSetAngles("STR_OIL_LB2", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut, 0.0F, 10F, 0.0F, -31F), 0.0F);
        super.mesh.chunkSetAngles("STR_OIL_LB3", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[2].tOilOut, 0.0F, 10F, 0.0F, -31F), 0.0F);
        super.mesh.chunkSetAngles("STR_OIL_LB4", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[3].tOilOut, 0.0F, 10F, 0.0F, -31F), 0.0F);
        super.mesh.chunkSetAngles("STRELK_TURN_UP", cvt(getBall(8D), -8F, 8F, 31F, -31F), 0.0F, 0.0F);
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("STREL_TURN_DOWN", cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, -50F, 50F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("STRELK_V_LONG", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeed()), 26.8224F, 214.5792F, 0.0F, 21F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkVisible("STRELK_V_SHORT", false);
        super.mesh.chunkSetAngles("STRELKA_GOS", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.02355F, -0.02355F);
        super.mesh.chunkSetLocate("STRELKA_GOR", Cockpit.xyz, Cockpit.ypr);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0)
            super.mesh.chunkSetAngles("STR_CLIMB", floatindex(cvt(setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F), variometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FlapPos", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getFlap(), 0.0F, 1.0F, 0.0F, 125F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim1Pos", -104F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimElevatorControl(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Trim2Pos", 208F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getTrimAileronControl(), 0.0F, 0.0F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
        {
            super.mesh.chunkVisible("HullDamage3", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0)
        {
            super.mesh.chunkVisible("HullDamage4", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("HullDamage1", true);
            super.mesh.chunkVisible("HullDamage2", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("Panel_D0", false);
            super.mesh.chunkVisible("Panel_D1", true);
            super.mesh.chunkVisible("STRELKA_FUEL2", false);
            super.mesh.chunkVisible("STRELKA_FUEL3", false);
            super.mesh.chunkVisible("STRELKA_FUEL4", false);
            super.mesh.chunkVisible("STRELKA_FUEL5", false);
            super.mesh.chunkVisible("STRELKA_FUEL6", false);
            super.mesh.chunkVisible("STRELKA_FUEL7", false);
            super.mesh.chunkVisible("Z_RPM_SHORT1", false);
            super.mesh.chunkVisible("Z_RPM_LONG1", false);
            super.mesh.chunkVisible("Z_RPM_SHORT2", false);
            super.mesh.chunkVisible("Z_RPM_LONG2", false);
            super.mesh.chunkVisible("STRELKA_BOOST1", false);
            super.mesh.chunkVisible("Z_TEMP_OIL1", false);
            super.mesh.chunkVisible("Z_TEMP_OIL2", false);
            super.mesh.chunkVisible("Z_TEMP_RAD1", false);
            super.mesh.chunkVisible("STRELK_V_LONG", false);
            super.mesh.chunkVisible("STRELK_V_SHORT", false);
            super.mesh.chunkVisible("STRELKA_GOR", false);
            super.mesh.chunkVisible("STRELKA_GOS", false);
            super.mesh.chunkVisible("STREL_ALT_LONG", false);
            super.mesh.chunkVisible("STREL_ALT_SHORT", false);
            super.mesh.chunkVisible("STRELK_TURN_UP", false);
            super.mesh.chunkVisible("Z_FlapPos", false);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage1", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("XGlassDamage2", true);
            super.mesh.chunkVisible("XGlassDamage3", true);
            super.mesh.chunkVisible("XGlassDamage4", true);
        }
        retoggleLight();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private void retoggleLight()
    {
        if(super.cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictBrake;
    private float pictFlap;
    private float pictGear;
    private float pictBbay;
    private float pictSupc;
    private float pictLlit;
    private float pictManf1;
    private float pictManf2;
    private float pictManf3;
    private float pictManf4;
    private boolean bNeedSetUp;
    private int iWiper;
    private static final float speedometerScale[] = {
        0.0F, 16.5F, 31F, 60.5F, 90F, 120.5F, 151.5F, 182F, 213.5F, 244F, 
        274F, 303F, 333.5F, 369.5F, 399F, 434.5F, 465.5F, 496.5F, 527.5F, 558.5F, 
        588.5F, 626.5F
    };
    private static final float radScale[] = {
        0.0F, 0.1F, 0.2F, 0.3F, 3.5F, 11F, 22F, 37.5F, 58.5F, 82.5F, 
        112.5F, 147F, 187F, 236F, 298.5F
    };
    private static final float boostScale[] = {
        0.0F, 21F, 39F, 56F, 90.5F, 109.5F, 129F, 146.5F, 163F, 179.5F, 
        196F, 212.5F, 231.5F, 250.5F
    };
    private static final float variometerScale[] = {
        -158F, -111F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111F, 158F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private com.maddox.sound.SoundFX fxw;
    private com.maddox.sound.Sample wiStart;
    private com.maddox.sound.Sample wiRun;
    private int wiState;
















}
