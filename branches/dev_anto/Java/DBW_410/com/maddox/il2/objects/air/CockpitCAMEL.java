// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 20/03/2011 10:56:51 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CockpitCAMEL.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitCAMEL extends CockpitPilot
{
    private class Variables
    {

        float throttle;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;

        private Variables()
        {
        }

    }

    class Interpolater extends InterpolateRef
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
                setNew.throttle = (10F * setOld.throttle + ((FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(Math.abs(((FlightModelMain) (fm)).Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -((FlightModelMain) (fm)).Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + World.Rnd().nextFloat(-30F, 30F)) / 11F;
                setNew.vspeed = (499F * setOld.vspeed + fm.getVertSpeed()) / 500F;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitCAMEL()
    {
        super("3do/cockpit/Camel/hier.him", "p39");
        setOld = new Variables();
        setNew = new Variables();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
        light1 = new LightPointActor(new LightPoint(), new Point3d(0.59999999999999998D, 0.0D, 0.80000000000000004D));
        light1.light.setColor(232F, 75F, 44F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        interpPut(new Interpolater(), null, Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        super.mesh.chunkSetAngles("Stick", 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F, -(pictAiler = 0.85F * pictAiler + 0.15F * ((FlightModelMain) (super.fm)).CT.AileronControl) * 15F);
        super.mesh.chunkSetAngles("RudderPedals", 20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RudderWire1", -20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RudderWire2", -20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RudderWire3", -20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RudderWire4", -20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("RPMNeedle", 0.0F, 0.0F, -cvt(((FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 2600F, 0.0F, 306F));
        super.mesh.chunkSetAngles("OilTNeedle", 0.0F, 0.0F, -cvt(((FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 30F, 100F, 0.0F, 305.5F));
        float f1 = 240F - super.fm.getSpeedKMH() * 0.5F;
        super.mesh.chunkSetAngles("SpeedNeedle", 0.0F, 0.0F, -cvt(super.fm.getSpeedKMH(), 40F, f1, 0.0F, 321F));
        super.mesh.chunkSetAngles("AltNeedle", 0.0F, 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 1600F, 0.0F, 321F));
        super.mesh.chunkSetAngles("COMPASS_M", interp(setNew.azimuth, setOld.azimuth, f), 0.0F, 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[2] = cvt(((FlightModelMain) (super.fm)).M.fuel, 0.0F, 180F, 0.0F, 0.207F);
        super.mesh.chunkSetLocate("FuelFloat", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("ClockNeedleH", 0.0F, 0.0F, -cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F));
        super.mesh.chunkSetAngles("ClockNeedleMin", 0.0F, 0.0F, -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F));
        super.mesh.chunkSetAngles("ClockNeedleSec", 0.0F, 0.0F, -cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F));
        super.mesh.chunkSetAngles("ThrottleLever", 0.0F, 25F - interp(setNew.throttle, setOld.throttle, f) * 52F * 0.91F, 0.0F);
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - ((FlightModelMain) (super.fm)).Vwld.length() * 1.5444015264511108D) % 360F;
        super.mesh.chunkSetAngles("SpedometerProp", 0.0F, 0.0F, -dynamoOrient);
    }

    protected void reflectPlaneMats()
    {
        HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("1"));
        super.mesh.materialReplace("1", mat);
        com.maddox.il2.engine.Mat mat1 = hiermesh.material(hiermesh.materialFind("11"));
        super.mesh.materialReplace("11", mat1);
        com.maddox.il2.engine.Mat mat2 = hiermesh.material(hiermesh.materialFind("17"));
        super.mesh.materialReplace("17", mat2);
        com.maddox.il2.engine.Mat mat3 = hiermesh.material(hiermesh.materialFind("2"));
        super.mesh.materialReplace("2", mat3);
        com.maddox.il2.engine.Mat mat4 = hiermesh.material(hiermesh.materialFind("21"));
        super.mesh.materialReplace("21", mat4);
        com.maddox.il2.engine.Mat mat5 = hiermesh.material(hiermesh.materialFind("25"));
        super.mesh.materialReplace("25", mat5);
        com.maddox.il2.engine.Mat mat6 = hiermesh.material(hiermesh.materialFind("34"));
        super.mesh.materialReplace("34", mat6);
        com.maddox.il2.engine.Mat mat7 = hiermesh.material(hiermesh.materialFind("38"));
        super.mesh.materialReplace("38", mat7);
        com.maddox.il2.engine.Mat mat8 = hiermesh.material(hiermesh.materialFind("4"));
        super.mesh.materialReplace("4", mat8);
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
    private LightPointActor light1;
    private float pictAiler;
    private float pictElev;
    private boolean bNeedSetUp;
    private float dynamoOrient;
    private boolean bDynamoRotary;
}