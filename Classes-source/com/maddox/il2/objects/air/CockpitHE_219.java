// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_219.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, AircraftLH, Cockpit

public class CockpitHE_219 extends com.maddox.il2.objects.air.CockpitPilot
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
                setNew.throttle1 = 0.85F * setOld.throttle1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlThrottle() * 0.15F;
                setNew.prop1 = 0.85F * setOld.prop1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlProp() * 0.15F;
                setNew.mix1 = 0.85F * setOld.mix1 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix() * 0.15F;
                setNew.throttle2 = 0.85F * setOld.throttle2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlThrottle() * 0.15F;
                setNew.prop2 = 0.85F * setOld.prop2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlProp() * 0.15F;
                setNew.mix2 = 0.85F * setOld.mix2 + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[1].getControlMix() * 0.15F;
                setNew.altimeter = fm.getAltitude();
                float f = waypointAzimuth();
                setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), (f - setOld.azimuth.getDeg(1.0F)) + com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 2.0F));
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut());
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                setNew.radioalt = 0.9F * setOld.radioalt + 0.1F * (fm.getAltitude() - com.maddox.il2.ai.World.land().HQ((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).Loc)).x, (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (fm)).Loc)).y));
            }
            return true;
        }

        Interpolater()
        {
        }
    }

    private class Variables
    {

        float throttle1;
        float prop1;
        float mix1;
        float throttle2;
        float prop2;
        float mix2;
        float altimeter;
        com.maddox.il2.ai.AnglesFork azimuth;
        com.maddox.il2.ai.AnglesFork waypointAzimuth;
        float vspeed;
        float dimPosition;
        float radioalt;
        float beaconDirection;
        float beaconRange;

        private Variables()
        {
            azimuth = new AnglesFork();
            waypointAzimuth = new AnglesFork();
        }

    }


    public CockpitHE_219()
    {
        super("3DO/Cockpit/He-219Uhu/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictTriA = 0.0F;
        pictTriE = 0.0F;
        pictTriR = 0.0F;
        pictManf1 = 1.0F;
        pictManf2 = 1.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        super.cockpitNightMats = (new java.lang.String[] {
            "1", "2", "3", "4", "5", "8", "9", "11", "alt_km", "kompass", 
            "ok42", "D1", "D2", "D3", "D4", "D5", "D8", "D9"
        });
        setNightMats(true);
        setNew.dimPosition = 1.0F;
        super.cockpitDimControl = !super.cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
        if(super.acoustics != null)
            super.acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(gun[0] == null)
        {
            gun[0] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN02");
            gun[1] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN03");
            gun[2] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN01");
            gun[3] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN04");
            gun[4] = ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.fm)).actor).getGunByHookName("_MGUN05");
        }
        super.mesh.chunkSetAngles("Z_Columnbase", 12F * (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", -45F * (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Columnbutton1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[0] ? -14.5F : 0.0F, 0.0F, 0.0F);
        resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[1])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.005F;
        super.mesh.chunkSetLocate("Z_Columnbutton2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.saveWeaponControl[3])
            com.maddox.il2.objects.air.Cockpit.xyz[2] = -0.0035F;
        super.mesh.chunkSetLocate("Z_Columnbutton3", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        super.mesh.chunkSetLocate("Z_Columnbutton4", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        super.mesh.chunkSetLocate("Z_Columnbutton5", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        resetYPRmodifier();
        xyz[2] = -0.05F * fm.CT.getRudder();
        mesh.chunkSetLocate("PedalL", xyz, ypr);
        xyz[2] = 0.05F * fm.CT.getRudder();
        mesh.chunkSetLocate("PedalR", xyz, ypr);
        super.mesh.chunkSetAngles("Z_Throttle1", 37.28F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle2", 37.28F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Gear", 50F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.GearControl, 0.0F, 0.0F);
        if(useRealisticNavigationInstruments())
        {
            super.mesh.chunkSetAngles("Z_Compass2", setNew.azimuth.getDeg(f) - setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_Compass1", -setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
        } else
        {
            super.mesh.chunkSetAngles("Z_Compass1", -setNew.azimuth.getDeg(f), 0.0F, 0.0F);
            super.mesh.chunkSetAngles("Z_Compass2", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
        }
        w.set(super.fm.getW());
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.transform(w);
        super.mesh.chunkSetAngles("Z_TurnBank1", cvt(((com.maddox.JGP.Tuple3f) (w)).z, -0.23562F, 0.23562F, 33F, -33F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank2", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F, 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.024F, -0.024F);
        super.mesh.chunkSetLocate("Z_TurnBank2a", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        super.mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8D), -8F, 8F, -15F, 15F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 1000F, 0.0F, 9F), speedometerScale), 0.0F, 0.0F);
        float f1 = setNew.vspeed <= 0.0F ? -1F : 1.0F;
        super.mesh.chunkSetAngles("Z_Climb1", f1 * floatindex(cvt(java.lang.Math.abs(setNew.vspeed), 0.0F, 30F, 0.0F, 6F), variometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RPM1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RPM2", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("ATA1", pictManf1 = 0.9F * pictManf1 + 0.1F * cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("ATA2", pictManf2 = 0.9F * pictManf2 + 0.1F * cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Fuel1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 864F, 0.0F, 90F), 0.0F, 0.0F);
        float p0 = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getReadyness();
        float p1 = ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].tOilOut * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[1].getReadyness();
        float pf = p0 <= p1 ? p0 : p1;
        super.mesh.chunkSetAngles("z_Alt1", cvt(interp(setNew.radioalt, setOld.radioalt, f), 0.0F, 750F, 0.0F, 232F), 0.0F, 0.0F);
        super.mesh.chunkVisible("Z_Gear", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() > 0.99F && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.lgear && ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Gears.rgear);
        super.mesh.chunkSetAngles("Z_AFN1", cvt(setNew.beaconDirection, -45F, 45F, -20F, 20F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AFN2", cvt(setNew.beaconRange, 0.0F, 1.0F, 20F, -20F), 0.0F, 0.0F);
        super.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
    }

    protected float waypointAzimuth()
    {
        return waypointAzimuthInvertMinus(30F);
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            super.mesh.chunkVisible("Z_OilSplats_D1", true);
        retoggleLight();
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

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictTriA;
    private float pictTriE;
    private float pictTriR;
    private float pictManf1;
    private float pictManf2;
    private com.maddox.il2.ai.BulletEmitter gun[] = {
        null, null, null, null, null
    };
    private static final float speedometerScale[] = {
        0.0F, 21F, 69.5F, 116F, 163F, 215.5F, 266.5F, 318.5F, 378F, 430.5F
    };
    private static final float variometerScale[] = {
        0.0F, 47F, 82F, 97F, 112F, 111.7F, 132F
    };
    private static final float rpmScale[] = {
        0.0F, 2.5F, 19F, 50.5F, 102.5F, 173F, 227F, 266.5F, 297F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
