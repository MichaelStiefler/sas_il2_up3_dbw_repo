// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitBF_109Bx.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, BF_109B1, BF_109B2, BF_109C1, 
//            BF_109D1, BF_109D1L, Aircraft

public class CockpitBF_109Bx extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float throttle;
        float dimPosition;
        float azimuth;
        float waypointAzimuth;
        float mix;

        private Variables()
        {
        }

        Variables(com.maddox.il2.objects.air.Variables variables)
        {
            this();
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
            setNew.azimuth = ((com.maddox.il2.fm.FlightModelMain) (fm)).Or.getYaw();
            if(setOld.azimuth > 270F && setNew.azimuth < 90F)
                setOld.azimuth -= 360F;
            if(setOld.azimuth < 90F && setNew.azimuth > 270F)
                setOld.azimuth += 360F;
            setNew.waypointAzimuth = (10F * setOld.waypointAzimuth + (waypointAzimuth() - setOld.azimuth) + com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 30F)) / 11F;
            buzzerFX(((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getGear() < 0.999999F && ((com.maddox.il2.fm.FlightModelMain) (fm)).CT.getFlap() > 0.0F);
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
            tmpV.sub(((com.maddox.JGP.Tuple3d) (tmpP)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)));
            return (float)(57.295779513082323D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV)).y, ((com.maddox.JGP.Tuple3d) (tmpV)).x));
        }
    }

    public CockpitBF_109Bx()
    {
        super("3DO/Cockpit/Bf-109E-7/Bx_mod.him", "bf109");
        setOld = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        setNew = new Variables(((com.maddox.il2.objects.air.Variables) (null)));
        pictAiler = 0.0F;
        pictElev = 0.0F;
        pictManifold = 0.0F;
        bNeedSetUp = true;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        setNew.dimPosition = 1.0F;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (super.mesh)), "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(227F, 65F, 33F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", ((java.lang.Object) (light1)));
        hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (super.mesh)), "LAMPHOOK2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(227F, 65F, 33F);
        light2.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK2", ((java.lang.Object) (light2)));
        super.cockpitNightMats = (new java.lang.String[] {
            "ZClocks1", "ZClocks1DMG", "ZClocks2", "ZClocks3", "FW190A4Compass", "oxigen"
        });
        setNightMats(false);
        interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        loadBuzzerFX();
    }

    public void removeCanopy()
    {
        if(super.mesh.chunkFindCheck("Top") >= 0)
            super.mesh.chunkVisible("TopOpen", false);
        super.mesh.chunkVisible("TopE1", false);
        super.mesh.chunkVisible("Z_Holes1_D1", false);
        super.mesh.chunkVisible("Z_Holes2_D1", false);
        if(super.mesh.chunkFindCheck("Top2") >= 0)
            super.mesh.chunkVisible("Top2", false);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        resetYPRmodifier();
        super.mesh.chunkSetAngles("Top", 0.0F, 100F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 3600F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F, 10000F, 0.0F, 180F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, -30F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(setNew.dimPosition, setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 40F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_ATA1", 15.5F + cvt(pictManifold = 0.75F * pictManifold + 0.25F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.fm)).Loc)).z, super.fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F), speedometerScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F), rpmScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelQuantity1", -44.5F + floatindex(cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel / 0.72F, 0.0F, 400F, 0.0F, 8F), fuelScale), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Iengtemprad1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, 58.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_EngTemp1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F, 120F, 0.0F, 58.5F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_FuelPress1", cvt(((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3F, 0.0F, 135F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F, 15F, 0.0F, 135F), 0.0F, 0.0F);
        float f1;
        if(aircraft().isFMTrackMirror())
        {
            f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
        } else
        {
            f1 = cvt((setNew.azimuth - setOld.azimuth) / com.maddox.rts.Time.tickLenFs(), -3F, 3F, 30F, -30F);
            if(aircraft().fmTrack() != null)
                aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
        }
        super.mesh.chunkSetAngles("Z_TurnBank1", f1, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6D), -6F, 6F, -7F, 7F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Compass1", 0.0F, interp(setNew.azimuth, setOld.azimuth, f), 0.0F);
        super.mesh.chunkSetAngles("Z_Azimuth1", -interp(setNew.waypointAzimuth, setOld.waypointAzimuth, f), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PropPitch1", 270F - (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 60F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PropPitch2", 105F - (float)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhi() - ((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getPropPhiMin()) * 5F, 0.0F, 0.0F);
        super.mesh.chunkVisible("Z_FuelRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).M.fuel < 36F);
        super.mesh.chunkVisible("Z_GearLRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
        super.mesh.chunkVisible("Z_GearRRed1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 0.0F);
        super.mesh.chunkVisible("Z_GearLGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
        super.mesh.chunkVisible("Z_GearRGreen1", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getGear() == 1.0F);
        super.mesh.chunkSetAngles("Z_Hour1", cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Minute1", cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Second1", cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Column", (pictAiler = 0.85F * pictAiler + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.AileronControl) * 15F, 0.0F, (pictElev = 0.85F * pictElev + 0.15F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.ElevatorControl) * 10F);
        super.mesh.chunkSetAngles("Z_PedalStrut", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_PedalStrut2", ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_LeftPedal", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_RightPedal", -((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder() * 15F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle", interp(setNew.throttle, setOld.throttle, f) * 57.72727F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle_D1", interp(setNew.throttle, setOld.throttle, f) * 27F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Throttle_tube", -interp(setNew.throttle, setOld.throttle, f) * 57.72727F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Mix", interp(setNew.mix, setOld.mix, f) * 70F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_Mix_tros", -interp(setNew.mix, setOld.mix, f) * 70F, 0.0F, 0.0F);
        super.mesh.chunkSetAngles("Z_MagnetoSwitch", -45F + 28.333F * (float)((com.maddox.il2.fm.FlightModelMain) (super.fm)).EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.bIsAboutToBailout)
        {
            super.mesh.chunkVisible("TopOpen", false);
            super.mesh.chunkVisible("Z_Holes1_D1", false);
            super.mesh.chunkVisible("Z_Holes2_D1", false);
        }
    }

    public void toggleDim()
    {
        super.cockpitDimControl = !super.cockpitDimControl;
    }

    public void toggleLight()
    {
        super.cockpitLightControl = !super.cockpitLightControl;
        if(super.cockpitLightControl)
        {
            light1.light.setEmit(0.005F, 0.5F);
            light2.light.setEmit(0.005F, 0.5F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0)
        {
            super.mesh.chunkVisible("Z_Armor_D1", true);
            super.mesh.chunkVisible("Z_HullDamage1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0)
            if(type == 0 || type == 1)
                super.mesh.chunkVisible("Z_Holes1E4_D1", true);
            else
                super.mesh.chunkVisible("Z_Holes1_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0)
        {
            super.mesh.chunkVisible("Z_HullDamage1", true);
            super.mesh.chunkVisible("Z_HullDamage2", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0)
            super.mesh.chunkVisible("Z_HullDamage4", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0)
        {
            super.mesh.chunkVisible("Z_Throttle", false);
            super.mesh.chunkVisible("Z_Throttle_tube", false);
            super.mesh.chunkVisible("Z_Throttle_D1", true);
            super.mesh.chunkVisible("Z_Throttle_TD1", true);
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0)
            if(type == 0 || type == 1)
                super.mesh.chunkVisible("Z_OilSplatE4_D1", true);
            else
                super.mesh.chunkVisible("Z_OilSplats_D1", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
            super.mesh.chunkVisible("Z_HullDamage3", true);
        if((((com.maddox.il2.fm.FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
        {
            super.mesh.chunkVisible("Z_HullDamage2", true);
            if(type == 0 || type == 1)
                super.mesh.chunkVisible("Z_Holes2E4_D1", true);
            else
                super.mesh.chunkVisible("Z_Holes2_D1", true);
        }
    }

    protected void reflectPlaneMats()
    {
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (super.fm)).actor))
        {
            if(((com.maddox.il2.engine.Interpolate) (super.fm)).actor instanceof com.maddox.il2.objects.air.BF_109B1)
                type = 0;
            else
            if(((com.maddox.il2.engine.Interpolate) (super.fm)).actor instanceof com.maddox.il2.objects.air.BF_109B2)
                type = 1;
            else
            if(((com.maddox.il2.engine.Interpolate) (super.fm)).actor instanceof com.maddox.il2.objects.air.BF_109C1)
                type = 2;
            else
            if(((com.maddox.il2.engine.Interpolate) (super.fm)).actor instanceof com.maddox.il2.objects.air.BF_109D1)
                type = 3;
            else
            if(((com.maddox.il2.engine.Interpolate) (super.fm)).actor instanceof com.maddox.il2.objects.air.BF_109D1L)
                type = 4;
            switch(type)
            {
            case 0: // '\0'
                super.mesh.chunkVisible("Body", false);
                super.mesh.chunkVisible("BodyE4", true);
                super.mesh.chunkVisible("TopOpen", false);
                super.mesh.chunkVisible("TopE1", true);
                super.mesh.chunkVisible("PanelE4_D0", true);
                super.mesh.chunkVisible("PanelE4B_D0", false);
                super.mesh.chunkVisible("PanelE7_D0", false);
                super.mesh.chunkVisible("oxigen-7", true);
                super.mesh.chunkVisible("oxigen-7z", false);
                break;

            case 1: // '\001'
                super.mesh.chunkVisible("Body", false);
                super.mesh.chunkVisible("BodyE4", true);
                super.mesh.chunkVisible("TopOpen", false);
                super.mesh.chunkVisible("TopE1", true);
                super.mesh.chunkVisible("PanelE4_D0", true);
                super.mesh.chunkVisible("PanelE4B_D0", false);
                super.mesh.chunkVisible("PanelE7_D0", false);
                super.mesh.chunkVisible("oxigen-7", true);
                super.mesh.chunkVisible("oxigen-7z", false);
                break;

            case 2: // '\002'
                super.mesh.chunkVisible("Body", false);
                super.mesh.chunkVisible("BodyE4", true);
                super.mesh.chunkVisible("TopOpen", false);
                super.mesh.chunkVisible("TopE1", true);
                super.mesh.chunkVisible("PanelE4_D0", true);
                super.mesh.chunkVisible("PanelE4B_D0", false);
                super.mesh.chunkVisible("PanelE7_D0", false);
                super.mesh.chunkVisible("oxigen-7", true);
                super.mesh.chunkVisible("oxigen-7z", false);
                break;

            case 3: // '\003'
                super.mesh.chunkVisible("Body", false);
                super.mesh.chunkVisible("BodyE4", true);
                super.mesh.chunkVisible("TopOpen", false);
                super.mesh.chunkVisible("TopE1", true);
                super.mesh.chunkVisible("PanelE4_D0", true);
                super.mesh.chunkVisible("PanelE4B_D0", false);
                super.mesh.chunkVisible("PanelE7_D0", false);
                super.mesh.chunkVisible("oxigen-7", true);
                super.mesh.chunkVisible("oxigen-7z", false);
                break;

            case 4: // '\004'
                super.mesh.chunkVisible("Body", false);
                super.mesh.chunkVisible("BodyE4", true);
                super.mesh.chunkVisible("TopOpen", false);
                super.mesh.chunkVisible("TopE1", true);
                super.mesh.chunkVisible("PanelE4_D0", true);
                super.mesh.chunkVisible("PanelE4B_D0", false);
                super.mesh.chunkVisible("PanelE7_D0", false);
                super.mesh.chunkVisible("oxigen-7", true);
                super.mesh.chunkVisible("oxigen-7z", false);
                break;
            }
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private float pictAiler;
    private float pictElev;
    private float pictManifold;
    private boolean bNeedSetUp;
    private int type;
    private static final float speedometerScale[] = {
        0.0F, -12.33333F, 18.5F, 37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 
        212.5F, 238.5F, 265F, 289.5F, 315F, 339.5F, 346F, 346F
    };
    private static final float rpmScale[] = {
        0.0F, 11.25F, 54F, 111F, 171.5F, 229.5F, 282.5F, 334F, 342.5F, 342.5F
    };
    private static final float fuelScale[] = {
        0.0F, 9F, 21F, 29.5F, 37F, 48F, 61.5F, 75.5F, 92F, 92F
    };
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitBF_109Bx.class)))), "normZN", 0.43F);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitBF_109Bx.class)))), "gsZN", 0.43F);
    }








}
