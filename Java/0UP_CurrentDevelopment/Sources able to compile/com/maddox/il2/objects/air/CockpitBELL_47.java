package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitBELL_47 extends CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float mix;
        float vspeed;

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
            setNew.altimeter = fm.getAltitude();
            if(cockpitDimControl)
            {
                if(setNew.dimPosition > 0.0F)
                    setNew.dimPosition = setOld.dimPosition - 0.05F;
            } else
            if(setNew.dimPosition < 1.0F)
                setNew.dimPosition = setOld.dimPosition + 0.05F;
            setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
            setNew.mix = (8F * setOld.mix + ((com.maddox.il2.fm.FlightModelMain) (fm)).EI.engines[0].getControlMix()) / 9F;
            setNew.azimuth = (35F * setOld.azimuth + -((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw()) / 36F;
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
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
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, ((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitBELL_47()
    {
        super("3DO/Cockpit/Bell47/hier.him", "bf109");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        super.cockpitDimControl = !super.cockpitDimControl;
        super.cockpitNightMats = (new java.lang.String[] {
            "Textur9", "Textur7"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        super.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f) + super.fm.getOverload(), 0.0F, 9144F, 0.0F, -10800F), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, -cvt(interp(setNew.altimeter, setOld.altimeter, f) + super.fm.getOverload() * 10F, 0.0F, 9144F, 0.0F, -1080F), 0.0F);
        super.mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt((com.maddox.il2.fm.Atmosphere.pressure((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z) - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() * 50F) + super.fm.getOverload() * 2500F, 0.0F, 300000F, 0.0F, 343F), 0.0F);
        super.mesh.chunkSetAngles("Z_Compass2", 0.0F, 90F + setNew.azimuth + super.fm.getOverload(), 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", 0.0F, 90F + interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f) + super.fm.getOverload(), 0.0F);
        super.mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 840F, 0.0F, 4F, 0.0F, -180F) + super.fm.getOverload(), 0.0F);
        super.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut + ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 1300F + super.fm.getOverload() / 10F, 0.0F, 15F, 0.0F, 180F), 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() < 100F)
            super.mesh.chunkSetAngles("Z_Oil1", 0.0F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F) + super.fm.getOverload() * 1.5F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM() * 1.3F, 0.0F, 3500F, 0.0F, 280F), 0.0F);
        super.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 83.6859F, 0.0F, 13F), speedometerScale) + super.fm.getOverload(), 0.0F);
        super.mesh.chunkSetAngles("Z_Column", (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F);
        super.mesh.chunkSetAngles("Z_Column1", (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F);
        super.mesh.chunkSetAngles("Z_PedalStrut", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PedalStrut2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PedalStrut3", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PedalStrut4", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Collective", 0.0F, 0.0F, interp(setNew.throttle, setOld.throttle, f) * 25F);
        super.mesh.chunkSetAngles("Z_CollectiveSecond", 0.0F, 0.0F, interp(setNew.throttle, setOld.throttle, f) * 25F);
        super.mesh.chunkSetAngles("Z_FuelGauge", 0.0F, cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 280F, 0.0F, 310F), 0.0F);
        super.mesh.chunkSetAngles("Horizon1", 0.0F, -((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getKren(), 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F, 0.0321F, -0.0321F);
        super.mesh.chunkSetLocate("Horizon", Cockpit.xyz, Cockpit.ypr);
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void reflectCockpitState()
    {
    }

    private float tmp;
    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private float pictManifold;
    private boolean bNeedSetUp;
    private int type;
    private static final int TYPE_FI_156 = 0;
    private static final float speedometerScale[] = {
        0.0F, 17F, 56.5F, 107.5F, 157F, 204F, 220.5F, 238.5F, 256.5F, 274.5F, 
        293F, 311F, 330F, 342F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 9F, 21F, 29.5F, 37F, 48F, 61.5F, 75.5F, 92F, 92F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;







}
