package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitJeep extends CockpitPilot
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
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(cockpitDimControl)
                {
                    if(setNew.dimPosition > 0.0F)
                        setNew.dimPosition = setOld.dimPosition - 0.05F;
                } else
                if(setNew.dimPosition < 1.0F)
                    setNew.dimPosition = setOld.dimPosition + 0.05F;
                if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getKren()) < 45F)
                    setNew.azimuthMag = (349F * setOld.azimuthMag + ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut()) / 350F;
                if(setOld.azimuthMag > 270F && setNew.azimuthMag < 90F)
                    setOld.azimuthMag -= 360F;
                if(setOld.azimuthMag < 90F && setNew.azimuthMag > 270F)
                    setOld.azimuthMag += 360F;
                setNew.azimuth = ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.azimut();
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + waypointAzimuth() + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
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
        float altimeter;
        float dimPosition;
        float azimuth;
        float azimuthMag;
        float vspeed;
        float waypointAzimuth;

        private Variables()
        {
        }

    }


    public CockpitJeep()
    {
        super("3do/cockpit/Jeep/hier.him", "p39");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        setNew.dimPosition = 1.0F;
        light1 = new LightPointActor(new LightPoint(), new Point3d(0.59999999999999998D, 0.0D, 0.80000000000000004D));
        light1.light.setColor(232F, 75F, 44F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        super.cockpitDimControl = !super.cockpitDimControl;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        super.mesh.chunkSetAngles("AirSpeed", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH() / 1.6F), 0.0F, 150F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Oil", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 160F, 0.0F, 116.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("CoolantTemp", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 120F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Fuel", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel, 0.0F, 155F, 0.0F, 120F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_AroneL", 0.0F, -180F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
        super.mesh.chunkSetAngles("Z_Wiper1", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 100F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Wiper3", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 100F), 0.0F, 0.0F);
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void reflectCockpitState()
    {
    }

    public void toggleLight()
    {
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 17F, 80.5F, 143.5F, 205F, 227F, 249F, 271.5F, 294F, 317F, 
        339.5F, 341.5F
    };
    private static final float variometerScale[] = {
        0.0F, 25F, 49.5F, 64F, 78.5F, 89.5F, 101F, 112F, 123F, 134.5F, 
        145.5F, 157F, 168F, 168F
    };







}
