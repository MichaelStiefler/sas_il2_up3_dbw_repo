// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 4/01/2011 1:14:53 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CockpitU2VSEX2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot

public class CockpitFokkerEIII extends CockpitPilot
{
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
                setNew.throttle = (10F * setOld.throttle + fm.CT.PowerControl) / 11F;
                setNew.prop = (10F * setOld.prop + fm.EI.engines[0].getControlProp()) / 11F;
                setNew.altimeter = fm.getAltitude();
                if(Math.abs(fm.Or.getKren()) < 30F)
                    setNew.azimuth = (35F * setOld.azimuth + -fm.Or.getYaw()) / 36F;
                if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                    setOld.azimuth -= 360F;
                if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                    setOld.azimuth += 360F;
                setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + World.Rnd().nextFloat(-10F, 10F)) / 11F;
                setNew.mix = (8F * setOld.mix + fm.EI.engines[0].getControlMix()) / 9F;
                setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
                boolean flag = false;
                if(setNew.gCrankAngle < fm.CT.getGear() - 0.005F)
                    if(Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.33F)
                    {
                        setNew.gCrankAngle += 0.0025F;
                        flag = true;
                    } else
                    {
                        setNew.gCrankAngle = fm.CT.getGear();
                        setOld.gCrankAngle = fm.CT.getGear();
                    }
                if(setNew.gCrankAngle > fm.CT.getGear() + 0.005F)
                    if(Math.abs(setNew.gCrankAngle - fm.CT.getGear()) < 0.33F)
                    {
                        setNew.gCrankAngle -= 0.0025F;
                        flag = true;
                    } else
                    {
                        setNew.gCrankAngle = fm.CT.getGear();
                        setOld.gCrankAngle = fm.CT.getGear();
                    }
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

    private class Variables
    {

        float throttle;
        float prop;
        float altimeter;
        float azimuth;
        float vspeed;
        float waypointAzimuth;
        float gCrankAngle;
        float mix;

        private Variables()
        {
            gCrankAngle = 0.0F;
        }

    }


    protected float waypointAzimuth()
    {
        WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(tmpP);
            tmpV.sub(tmpP, fm.Loc);
            return (float)(57.295779513082323D * Math.atan2(-tmpV.y, tmpV.x));
        }
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
        	for(int i = 0; i < 8; i++){
            aircraft().hierMesh().chunkVisible("WireL"+i+"_D0", false);
            aircraft().hierMesh().chunkVisible("WireR"+i+"_D0", false);
        	}
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
        	for(int i = 0; i < 8; i++){
                aircraft().hierMesh().chunkVisible("WireL"+i+"_D0", true);
                aircraft().hierMesh().chunkVisible("WireR"+i+"_D0", true);
            	}
            super.doFocusLeave();
            return;
        }
    }
    
    public CockpitFokkerEIII()
    {
        super("3DO/Cockpit/FokkerE3/CockpitU2VSEX2_NEW.him", "i16");
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        bNeedSetUp = true;
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        cockpitNightMats = (new String[] {
            "prib_one", "prib_one_dd", "prib_two", "prib_two_dd", "prib_three", "prib_three_dd", "prib_four", "prib_four_dd", "shkala", "oxigen"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        mesh.chunkSetAngles("Stick", 0.0F, (pictAiler = 0.85F * pictAiler + 0.15F * fm.CT.AileronControl) * 15F, (pictElev = 0.85F * pictElev + 0.15F * fm.CT.ElevatorControl) * 10F);
        mesh.chunkSetAngles("Rudder", fm.CT.getRudder() * 15F, 0.0F, 0.0F);
        this.resetYPRmodifier();
        Cockpit.xyz[0] = cvt(fm.CT.getRudder(), -1F, 1F, -0.01F, -0.01F);
        Cockpit.xyz[1] = cvt(fm.CT.getRudder(), -1F, 1F, -0.06F, 0.06F);
        mesh.chunkSetLocate("RudderL", Cockpit.xyz, Cockpit.ypr);
        Cockpit.xyz[0] = -Cockpit.xyz[0];
        Cockpit.xyz[1] = -Cockpit.xyz[1];
        mesh.chunkSetLocate("RudderR", Cockpit.xyz, Cockpit.ypr);
        super.mesh.chunkSetAngles("zKompas", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        mesh.chunkSetAngles("FuelLeveler",0.0F, 0F - 130F * interp(setNew.mix, setOld.mix, f), 0.0F);
        mesh.chunkSetAngles("IgnitionSwitcher",0.0F,  0F - 15F * (float)fm.EI.engines[0].getControlMagnetos(), 0.0F);
    }

    public void reflectCockpitState()
    {
    	/*
        if((fm.AS.astateCockpitState & 4) != 0 || (fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("pribors1", false);
            mesh.chunkVisible("pribors1_dd", true);
            mesh.chunkVisible("zAlt1a", false);
            mesh.chunkVisible("zAlt1b", false);
            mesh.chunkVisible("zVariometer1a", false);
        }
        if((fm.AS.astateCockpitState & 8) != 0 || (fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("pribors2", false);
            mesh.chunkVisible("pribors2_dd", true);
            mesh.chunkVisible("zGas1a", false);
        }
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("Z_OilSplats_D1", true);
            */
    }

    protected void reflectPlaneMats()
    {
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private Variables setOld;
    private Variables setNew;
    private Variables setTmp;
    public Vector3f w;
    private boolean bNeedSetUp;
    private float pictAiler;
    private float pictElev;
    private static final float engineRPMScale[] = {
        0.0F, 16F, 18F, 59.5F, 100.5F, 135.5F, 166.5F, 198.5F, 227F, 255F, 
        281.5F, 307F, 317F, 327F
    };
    private Point3d tmpP;
    private Vector3d tmpV;
}