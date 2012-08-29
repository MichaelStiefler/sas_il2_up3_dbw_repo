package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitRWD_10 extends CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float throttle;

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
            RWD_10 rwd_10 = (RWD_10)aircraft();
            if(RWD_10.bChangedPit)
            {
                reflectPlaneToModel();
                RWD_10 rwd_10_1 = (RWD_10)aircraft();
                RWD_10.bChangedPit = false;
            }
            setTmp = setOld;
            setOld = setNew;
            setNew = setTmp;
            setNew.altimeter = fm.getAltitude();
            if(java.lang.Math.abs(fm.Or.getKren()) < 30F)
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
            w.set(fm.getW());
            fm.Or.transform(w);
            return true;
        }

        Interpolater()
        {
        }
    }


    public CockpitRWD_10()
    {
        super("3DO/Cockpit/RWD-10/hier.him", "i16");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("zAlt", 0.0F, cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F);
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 350F, 0.0F, 14F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zRPM", 0.0F, floatindex(cvt(fm.EI.engines[0].getRPM(), 0.0F, 3000F, 0.0F, 5F), rpmScale), 0.0F);
        mesh.chunkSetAngles("Stick", 0.0F, 20F * (pictAiler = 0.75F * pictAiler + 0.15F * fm.CT.AileronControl), 20F * (pictElev = 0.75F * pictElev + 0.15F * fm.CT.ElevatorControl));
        mesh.chunkSetAngles("StickBase", 0.0F, 26F * pictAiler, 0.0F);
        mesh.chunkSetAngles("StickRodL", 0.0F, -26F * pictAiler, 0.0F);
        mesh.chunkSetAngles("StickRodR", 0.0F, -26F * pictAiler, 0.0F);
        mesh.chunkSetAngles("StickElevRod", 0.0F, pictElev * 26F, 0.0F);
        mesh.chunkSetAngles("Rudder", 26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RCableL", -26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("RCableR", -26F * fm.CT.getRudder(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Throttle", 30F - 57F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("ThrottleWire", -30F + 57F * interp(setNew.throttle, setOld.throttle, f), 0.0F, 0.0F);
        mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(fm.EI.engines[0].getControlMagnetos(), 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        mesh.chunkSetAngles("zOilPrs", 0.0F, cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F, 10F, 0.0F, 272F), 0.0F);
        mesh.chunkSetAngles("zFuel", 0.0F, cvt(fm.M.fuel, 0.0F, 60F, 0.0F, 272F), 0.0F);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Cart_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(com.maddox.il2.engine.Actor.isAlive(aircraft()))
            aircraft().hierMesh().chunkVisible("Cart_D0", true);
        super.doFocusLeave();
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("WingLIn_D0", hiermesh.isChunkVisible("WingLIn_D0"));
        mesh.chunkVisible("WingLIn_D1", hiermesh.isChunkVisible("WingLIn_D1"));
        mesh.chunkVisible("WingLIn_D2", hiermesh.isChunkVisible("WingLIn_D2"));
        mesh.chunkVisible("WingLIn_D3", hiermesh.isChunkVisible("WingLIn_D3"));
        mesh.chunkVisible("WingLIn_CAP", hiermesh.isChunkVisible("WingLIn_CAP"));
        mesh.chunkVisible("WingRIn_D0", hiermesh.isChunkVisible("WingRIn_D0"));
        mesh.chunkVisible("WingRIn_D1", hiermesh.isChunkVisible("WingRIn_D1"));
        mesh.chunkVisible("WingRIn_D2", hiermesh.isChunkVisible("WingRIn_D2"));
        mesh.chunkVisible("WingRIn_D3", hiermesh.isChunkVisible("WingRIn_D3"));
        mesh.chunkVisible("WingRIn_CAP", hiermesh.isChunkVisible("WingRIn_CAP"));
        mesh.chunkVisible("WingLOut_CAP", hiermesh.isChunkVisible("WingLOut_CAP"));
        mesh.chunkVisible("WingROut_CAP", hiermesh.isChunkVisible("WingROut_CAP"));
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    private com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 18F, 45F, 72F, 99F, 126F, 153F, 180F, 207F, 
        234F, 261F, 288F, 315F, 342F
    };
    private static final float rpmScale[] = {
        0.0F, 18F, 99F, 180F, 261F, 342F
    };










}
