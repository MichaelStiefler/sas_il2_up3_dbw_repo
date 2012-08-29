// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitWagen.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit

public class CockpitWagen extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float throttle;
        float altimeter;
        float azimuth;
        float azimuthMag;
        float vspeed;
        float waypointAzimuth;

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
                setNew.throttle = (10F * setOld.throttle + ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.PowerControl) / 11F;
                setNew.altimeter = fm.getAltitude();
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


    public CockpitWagen()
    {
        super("3do/cockpit/Wagen/hier.him", "p39");
        setOld = new Variables();
        setNew = new Variables();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        light1 = new LightPointActor(new LightPoint(), new Point3d(0.59999999999999998D, 0.0D, 0.80000000000000004D));
        light1.light.setColor(232F, 75F, 44F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void reflectWorldToInstruments(float f)
    {
    }

    public void reflectCockpitState()
    {
    }

    public void toggleLight()
    {
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
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
