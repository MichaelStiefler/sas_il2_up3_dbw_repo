// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F_86D.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.Property;
import java.io.IOException;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeSupersonic, TypeBNZFighter, TypeFighterAceMaker, 
//            TypeFighter, TypeGSuit, Aircraft, Cockpit, 
//            AircraftLH, PaintScheme, EjectionSeat

public class F_86D extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeSupersonic, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeFighterAceMaker, com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeGSuit
{
    private class ._cls0
    {

        public void rs(int ii)
        {
            if(ii == 0 || ii == 1)
                actl*= = 0.68000000000000005D;
            if(ii == 31 || ii == 32)
                ectl*= = 0.68000000000000005D;
            if(ii == 15 || ii == 16)
                rctl*= = 0.68000000000000005D;
        }

        private void $1()
        {
            if(ts)
            {
                float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.getAltitude(), lal, tal, bef, tef);
                float f2 = com.maddox.il2.objects.air.Aircraft.cvt(mn, mn >= com.maddox.il2.objects.air.F_86D.mteb ? com.maddox.il2.objects.air.F_86D.uteb : com.maddox.il2.objects.air.F_86D.lteb, mn >= com.maddox.il2.objects.air.F_86D.mteb ? com.maddox.il2.objects.air.F_86D.lteb : com.maddox.il2.objects.air.F_86D.uteb, mn >= com.maddox.il2.objects.air.F_86D.mteb ? thef : bhef, mn >= com.maddox.il2.objects.air.F_86D.mteb ? phef : thef);
                float f3 = com.maddox.il2.objects.air.Aircraft.cvt(mn, mn >= com.maddox.il2.objects.air.F_86D.mteb ? com.maddox.il2.objects.air.F_86D.uteb : com.maddox.il2.objects.air.F_86D.lteb, mn >= com.maddox.il2.objects.air.F_86D.mteb ? com.maddox.il2.objects.air.F_86D.lteb : com.maddox.il2.objects.air.F_86D.uteb, mn >= com.maddox.il2.objects.air.F_86D.mteb ? wef / f1 : mef, mn >= com.maddox.il2.objects.air.F_86D.mteb ? lef / f1 : wef / f1);
                ((com.maddox.il2.fm.RealFlightModel)FM).producedShakeLevel += 0.1125F * f2;
                FM.SensPitch = ectl * f3 * f3;
                FM.SensRoll = actl * f3;
                FM.SensYaw = rctl * f3;
                if(f2 > 0.6F)
                    ictl = true;
                else
                    ictl = false;
                if(ftl > 0.0F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() > 0.6F)
                        if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl > 0.0F)
                            ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl -= ftl * f2;
                        else
                        if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl < 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl += ftl * f2;
                        } else
                        {
                            com.maddox.il2.fm.Controls controls = ((com.maddox.il2.fm.FlightModelMain) (FM)).CT;
                            controls.RudderControl = controls.RudderControl + (com.maddox.il2.ai.World.Rnd().nextFloat() <= 0.5F ? -ftl * f2 : ftl * f2);
                        }
                    if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl > 1.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl = 1.0F;
                    if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl < -1F)
                        ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl = -1F;
                }
            } else
            {
                FM.SensPitch = ectl;
                FM.SensRoll = actl;
                FM.SensYaw = rctl;
            }
        }

        private float lal;
        private float tal;
        private float bef;
        private float tef;
        private float bhef;
        private float thef;
        private float phef;
        private float mef;
        private float wef;
        private float lef;
        private float ftl;


        private ._cls0(float f1, float f2, float f3, float f4, float f5, float f6, 
                float f7, float f8, float f9, float f10, float f11)
        {
            lal = f1;
            tal = f2;
            bef = f3;
            tef = f4;
            bhef = f5;
            thef = f6;
            phef = f7;
            mef = f8;
            wef = f9;
            lef = f10;
            ftl = f11;
        }

    }


    public float getDragForce(float arg0, float arg1, float arg2, float arg3)
    {
        throw new UnsupportedOperationException("getDragForce not supported anymore.");
    }

    public float getDragInGravity(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5)
    {
        throw new UnsupportedOperationException("getDragInGravity supported anymore.");
    }

    public float getForceInGravity(float arg0, float arg1, float arg2)
    {
        throw new UnsupportedOperationException("getForceInGravity supported anymore.");
    }

    public float getDegPerSec(float arg0, float arg1)
    {
        throw new UnsupportedOperationException("getDegPerSec supported anymore.");
    }

    public float getGForce(float arg0, float arg1)
    {
        throw new UnsupportedOperationException("getGForce supported anymore.");
    }

    public F_86D()
    {
        SonicBoom = 0.0F;
        BayDoor_ = 0.0F;
        bSlatsOff = false;
        oldctl = -1F;
        curctl = -1F;
        oldthrl = -1F;
        curthrl = -1F;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        AirBrakeControl = 0.0F;
        overrideBailout = false;
        ejectComplete = false;
        lightTime = 0.0F;
        ft = 0.0F;
        mn = 0.0F;
        ts = false;
        ictl = false;
        engineSurgeDamage = 0.0F;
        gearTargetAngle = -1F;
        gearCurrentAngle = -1F;
        hasHydraulicPressure = true;
    }

    public void getGFactors(com.maddox.il2.objects.air.TypeGSuit.GFactors theGFactors)
    {
        theGFactors.setGFactors(1.5F, 1.5F, 1.0F, 2.0F, 2.0F, 2.0F);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.wantBeaconsNet(true);
        actl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).SensRoll;
        ectl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).SensPitch;
        rctl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).SensYaw;
    }

    public void checkHydraulicStatus()
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() < 6 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.nOfGearsOnGr > 0)
        {
            gearTargetAngle = 90F;
            hasHydraulicPressure = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasAileronControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasElevatorControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
        } else
        if(!hasHydraulicPressure)
        {
            gearTargetAngle = 0.0F;
            hasHydraulicPressure = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasAileronControl = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasElevatorControl = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasAirBrakeControl = true;
        }
    }

    public void moveHydraulics(float f)
    {
        if(gearTargetAngle >= 0.0F)
        {
            if(gearCurrentAngle < gearTargetAngle)
            {
                gearCurrentAngle += 90F * f * 0.8F;
                if(gearCurrentAngle >= gearTargetAngle)
                {
                    gearCurrentAngle = gearTargetAngle;
                    gearTargetAngle = -1F;
                }
            } else
            {
                gearCurrentAngle -= 90F * f * 0.8F;
                if(gearCurrentAngle <= gearTargetAngle)
                {
                    gearCurrentAngle = gearTargetAngle;
                    gearTargetAngle = -1F;
                }
            }
            hierMesh().chunkSetAngles("GearL6_D0", 0.0F, -gearCurrentAngle, 0.0F);
            hierMesh().chunkSetAngles("GearR6_D0", 0.0F, -gearCurrentAngle, 0.0F);
            hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -gearCurrentAngle, 0.0F);
        }
    }

    public void updateLLights()
    {
        super.pos.getRender(com.maddox.il2.engine.Actor._tmpLoc);
        if(lLight == null)
        {
            if(com.maddox.il2.engine.Actor._tmpLoc.getX() >= 1.0D)
            {
                lLight = (new com.maddox.il2.engine.LightPointWorld[] {
                    null, null, null, null
                });
                for(int i = 0; i < 4; i++)
                {
                    lLight[i] = new LightPointWorld();
                    lLight[i].setColor(1.0F, 1.0F, 1.0F);
                    lLight[i].setEmit(0.0F, 0.0F);
                    try
                    {
                        lLightHook[i] = new HookNamed(this, "_LandingLight0" + i);
                    }
                    catch(java.lang.Exception exception) { }
                }

            }
        } else
        {
            for(int i = 0; i < 4; i++)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateLandingLightEffects[i] != null)
                {
                    lLightLoc1.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    lLightHook[i].computePos(this, com.maddox.il2.engine.Actor._tmpLoc, lLightLoc1);
                    lLightLoc1.get(lLightP1);
                    lLightLoc1.set(2000D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    lLightHook[i].computePos(this, com.maddox.il2.engine.Actor._tmpLoc, lLightLoc1);
                    lLightLoc1.get(lLightP2);
                    com.maddox.il2.engine.Engine.land();
                    if(com.maddox.il2.engine.Landscape.rayHitHQ(lLightP1, lLightP2, lLightPL))
                    {
                        lLightPL.z++;
                        lLightP2.interpolate(lLightP1, lLightPL, 0.95F);
                        lLight[i].setPos(lLightP2);
                        float f1 = (float)lLightP1.distance(lLightPL);
                        float f2 = f1 * 0.5F + 60F;
                        float f3 = 0.7F - (0.8F * f1 * lightTime) / 2000F;
                        lLight[i].setEmit(f3, f2);
                    } else
                    {
                        lLight[i].setEmit(0.0F, 0.0F);
                    }
                    continue;
                }
                if(lLight[i].getR() != 0.0F)
                    lLight[i].setEmit(0.0F, 0.0F);
            }

        }
    }

    protected void nextDMGLevel(java.lang.String paramString, int paramInt, com.maddox.il2.engine.Actor paramActor)
    {
        super.nextDMGLevel(paramString, paramInt, paramActor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String paramString, int paramInt, com.maddox.il2.engine.Actor paramActor)
    {
        super.nextCUTLevel(paramString, paramInt, paramActor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void rareAction(float paramFloat, boolean paramBoolean)
    {
        super.rareAction(paramFloat, paramBoolean);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.JGP.Vector3d vf1 = super.FM.getVflow();
            mn = (float)vf1.lengthSquared();
            mn = (float)java.lang.Math.sqrt(mn);
            com.maddox.il2.objects.air.F_86D F_86D = this;
            float f = mn;
            if(com.maddox.il2.ai.World.cur().Atm == null);
            F_86D.mn = f / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Loc)).z);
            if(mn >= 0.9F && (double)mn < 1.1000000000000001D)
                ts = true;
            else
                ts = false;
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.nearGround() || ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.onGround()) && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() == 1.0F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
        if((!super.FM.isPlayers() || !(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode()) && (super.FM instanceof com.maddox.il2.ai.air.Maneuver))
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.isLanding() && super.FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS && super.FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.curr().getV() * 1.4F)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 1.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 25 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.isLanding() && super.FM.getSpeed() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS * 1.16F)
            {
                if(super.FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (super.FM)).VminFLAPS * 0.5F && (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.nearGround() || ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.onGround()))
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 1.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
                } else
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 0.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 66)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 0.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 7)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 1.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
            } else
            if(hasHydraulicPressure && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl != 0.0F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 0.0F;
        ft = com.maddox.il2.ai.World.getTimeofDay() % 0.01F;
        if(ft == 0.0F)
            UpdateLightIntensity();
    }

    private final void UpdateLightIntensity()
    {
        if(com.maddox.il2.ai.World.getTimeofDay() >= 6F && com.maddox.il2.ai.World.getTimeofDay() < 7F)
            lightTime = com.maddox.il2.objects.air.Aircraft.cvt(com.maddox.il2.ai.World.getTimeofDay(), 6F, 7F, 1.0F, 0.1F);
        else
        if(com.maddox.il2.ai.World.getTimeofDay() >= 18F && com.maddox.il2.ai.World.getTimeofDay() < 19F)
            lightTime = com.maddox.il2.objects.air.Aircraft.cvt(com.maddox.il2.ai.World.getTimeofDay(), 18F, 19F, 0.1F, 1.0F);
        else
        if(com.maddox.il2.ai.World.getTimeofDay() >= 7F && com.maddox.il2.ai.World.getTimeofDay() < 18F)
            lightTime = 0.1F;
        else
            lightTime = 1.0F;
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        if(k14Mode == 0)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Sight Mode: Caged");
        } else
        if(k14Mode == 1)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Sight Mode: Uncaged");
        } else
        if(k14Mode == 2 && ((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Sight Off");
        return true;
    }

    public void typeFighterAceMakerAdjDistanceReset()
    {
    }

    public void typeFighterAceMakerAdjDistancePlus()
    {
        k14Distance += 10F;
        if(k14Distance > 800F)
            k14Distance = 800F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        if(k14WingspanType == 0)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: MiG-17/19/21");
        } else
        if(k14WingspanType == 1)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: MiG-15");
        } else
        if(k14WingspanType == 2)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Me-262");
        } else
        if(k14WingspanType == 3)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Pe-2");
        } else
        if(k14WingspanType == 4)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: 60ft");
        } else
        if(k14WingspanType == 5)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Canberra Bomber");
        } else
        if(k14WingspanType == 6)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Yak-28/Il-28");
        } else
        if(k14WingspanType == 7)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: C-47");
        } else
        if(k14WingspanType == 8)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Tu-16");
        } else
        if(k14WingspanType == 9 && ((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Tu-4");
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        if(k14WingspanType == 0)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: MiG-17/19/21");
        } else
        if(k14WingspanType == 1)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: MiG-15");
        } else
        if(k14WingspanType == 2)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Me-262");
        } else
        if(k14WingspanType == 3)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Pe-2");
        } else
        if(k14WingspanType == 4)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: 60ft");
        } else
        if(k14WingspanType == 5)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Canberra Bomber");
        } else
        if(k14WingspanType == 6)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Yak-28/Il-28");
        } else
        if(k14WingspanType == 7)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: C-47");
        } else
        if(k14WingspanType == 8)
        {
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Tu-16");
        } else
        if(k14WingspanType == 9 && ((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Wingspan Selected: Tu-4");
    }

    public void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted paramNetMsgGuaranted)
        throws java.io.IOException
    {
        paramNetMsgGuaranted.writeByte(k14Mode);
        paramNetMsgGuaranted.writeByte(k14WingspanType);
        paramNetMsgGuaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput paramNetMsgInput)
        throws java.io.IOException
    {
        k14Mode = paramNetMsgInput.readByte();
        k14WingspanType = paramNetMsgInput.readByte();
        k14Distance = paramNetMsgInput.readFloat();
    }

    public void doMurderPilot(int paramInt)
    {
        switch(paramInt)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, this) {

            public void doAction(java.lang.Object paramObject)
            {
                com.maddox.il2.objects.air.Aircraft localAircraft = (com.maddox.il2.objects.air.Aircraft)paramObject;
                if(com.maddox.il2.engine.Actor.isValid(localAircraft))
                {
                    com.maddox.il2.engine.Loc localLoc1 = new Loc();
                    com.maddox.il2.engine.Loc localLoc2 = new Loc();
                    com.maddox.JGP.Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
                    ((com.maddox.il2.engine.Actor) (localAircraft)).pos.getAbs(localLoc2);
                    localHookNamed.computePos(localAircraft, localLoc2, localLoc1);
                    localLoc1.transform(localVector3d);
                    localVector3d.x += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).x;
                    localVector3d.y += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).y;
                    localVector3d.z += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).z;
                    new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
                }
            }

        }
;
        hierMesh().chunkVisible("Seat_D0", false);
        super.FM.setTakenMortalDamage(true, null);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[0] = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[1] = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasAileronControl = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasRudderControl = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasElevatorControl = false;
    }

    public void moveCockpitDoor(float paramFloat)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.01F, 0.95F, 0.0F, 0.9F);
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 0.0F, 45F * paramFloat);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
            setDoorSnd(paramFloat);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh paramHierMesh, float paramFloat)
    {
        if(java.lang.Math.abs(paramFloat) < 0.27F)
        {
            paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.15F, 0.26F, 0.0F, -90F), 0.0F);
            paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.09F, 0.22F, 0.0F, -90F), 0.0F);
            paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.0F, 0.11F, 0.0F, -90F), 0.0F);
        } else
        {
            paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.65F, 0.74F, -90F, 0.0F), 0.0F);
            paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.67F, 0.78F, -90F, 0.0F), 0.0F);
            paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.89F, 0.99F, -90F, 0.0F), 0.0F);
        }
        paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.23F, 0.65F, 0.0F, -85F), 0.0F);
        paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.23F, 0.65F, 0.0F, -85F), 0.0F);
        paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.28F, 0.7F, 0.0F, -85F), 0.0F);
        paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.28F, 0.7F, 0.0F, -85F), 0.0F);
        paramHierMesh.chunkSetAngles("GearC10_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.69F, 0.74F, 0.0F, -90F), 0.0F);
        paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.63F, 0.99F, 0.0F, -105F), 0.0F);
        paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.63F, 0.99F, 0.0F, -95F), 0.0F);
        paramHierMesh.chunkSetAngles("Gear5e_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.63F, 0.99F, 0.0F, -90F), 0.0F);
    }

    protected void moveGear(float paramFloat)
    {
        com.maddox.il2.objects.air.F_86D.moveGear(hierMesh(), paramFloat);
    }

    public void moveWheelSink()
    {
        if(curctl == -1F)
        {
            curctl = oldctl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getBrake();
            H1 = 0.17F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.tailStiffness = 0.4F;
        } else
        {
            curctl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getBrake();
        }
        if(!super.FM.brakeShoe && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.cgear)
        {
            if(curctl - oldctl < -0.02F)
                curctl = oldctl - 0.02F;
            if(curctl < 0.0F)
                curctl = 0.0F;
            float tr = 0.25F * curctl * java.lang.Math.max(com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getThrustOutput(), 0.5F, 0.8F, 0.0F, 1.0F), com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getSpeedKMH(), 0.0F, 80F, 0.0F, 1.0F));
            super.FM.setGC_Gear_Shift(H1 - tr);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.4F * tr;
            float f = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[2] - com.maddox.il2.objects.air.Aircraft.xyz[0], 0.0F, 0.45F, 0.0F, 1.0F);
            hierMesh().chunkSetLocate("GearC6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            hierMesh().chunkSetAngles("GearC8_D0", 0.0F, -37.5F * f, 0.0F);
            hierMesh().chunkSetAngles("GearC9_D0", 0.0F, -75F * f, 0.0F);
        }
        oldctl = curctl;
    }

    protected void moveRudder(float paramFloat)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * paramFloat, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl > 0.5F)
            hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -50F * paramFloat, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -12F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -12F * f, 0.0F);
    }

    protected void moveFlap(float paramFloat)
    {
        float f = -45F * paramFloat;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    }

    protected void moveSlats(float paramFloat)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.15F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 0.1F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.065F);
        hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 8.5F), 0.0F);
        hierMesh().chunkSetLocate("SlatL_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.1F);
        hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 8.5F), 0.0F);
        hierMesh().chunkSetLocate("SlatR_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void slatsOff()
    {
        if(bSlatsOff)
        {
            return;
        } else
        {
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.15F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.1F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.065F;
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 8.5F, 0.0F);
            hierMesh().chunkSetLocate("SlatL_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.1F;
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 8.5F, 0.0F);
            hierMesh().chunkSetLocate("SlatR_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            bSlatsOff = true;
            return;
        }
    }

    protected void moveFan(float f)
    {
    }

    protected void hitBone(java.lang.String paramString, com.maddox.il2.ai.Shot paramShot, com.maddox.JGP.Point3d paramPoint3d)
    {
        int ii = part(paramString);
        $1.rs(ii);
        if(paramString.startsWith("xx"))
        {
            if(paramString.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(paramString.endsWith("p1"))
                {
                    getEnergyPastArmor(13.350000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                    if(paramShot.power <= 0.0F)
                        doRicochetBack(paramShot);
                } else
                if(paramString.endsWith("p2"))
                    getEnergyPastArmor(8.770001F, paramShot);
                else
                if(paramString.endsWith("g1"))
                {
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(40F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(paramShot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
                    if(paramShot.power <= 0.0F)
                        doRicochetBack(paramShot);
                }
            } else
            if(paramString.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int i = paramString.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(1.1F, paramShot) > 0.0F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(paramShot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(paramShot.initiator, 1);
                    }
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(paramShot.initiator, 2);
                    }
                    break;
                }
            } else
            if(paramString.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(paramString.endsWith("bloc"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                if(paramString.endsWith("cams") && getEnergyPastArmor(0.45F, paramShot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 20F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(paramShot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(paramShot.power / 4800F)));
                    debuggunnery("Engine Module: Engine Cams Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < paramShot.power / 24000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(paramShot.initiator, 0, 2);
                        debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(paramShot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(paramShot.initiator, 0, 1);
                        debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(paramString.endsWith("eqpt") && com.maddox.il2.ai.World.Rnd().nextFloat() < paramShot.power / 24000F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(paramShot.initiator, 0, 3);
                    debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                if(paramString.endsWith("exht"));
            } else
            if(paramString.startsWith("xxmgun0"))
            {
                int i = paramString.charAt(7) - 49;
                if(getEnergyPastArmor(1.5F, paramShot) > 0.0F)
                {
                    debuggunnery("Armament: mnine Gun (" + i + ") Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, i);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), paramShot);
                }
            } else
            if(paramString.startsWith("xxtank"))
            {
                int i = paramString.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, paramShot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[i] == 0)
                    {
                        debuggunnery("Fuel Tank (" + i + "): Pierced..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(paramShot.initiator, i, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(paramShot.initiator, i, 1);
                    }
                    if(paramShot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.075F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(paramShot.initiator, i, 2);
                        debuggunnery("Fuel Tank (" + i + "): Hit..");
                    }
                }
            } else
            if(paramString.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(paramString.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
                }
            } else
            if(paramString.startsWith("xxhyd"))
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(paramShot.initiator, 3);
            else
            if(paramString.startsWith("xxpnm"))
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(paramShot.initiator, 1);
        } else
        {
            if(paramString.startsWith("xcockpit"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(paramShot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
                getEnergyPastArmor(0.05F, paramShot);
            }
            if(paramString.startsWith("xcf"))
                hitChunk("CF", paramShot);
            else
            if(paramString.startsWith("xnose"))
                hitChunk("Nose", paramShot);
            else
            if(paramString.startsWith("xtail"))
            {
                if(chunkDamageVisible("Tail1") < 3)
                    hitChunk("Tail1", paramShot);
            } else
            if(paramString.startsWith("xkeel"))
            {
                if(chunkDamageVisible("Keel1") < 2)
                    hitChunk("Keel1", paramShot);
            } else
            if(paramString.startsWith("xrudder"))
                hitChunk("Rudder1", paramShot);
            else
            if(paramString.startsWith("xstab"))
            {
                if(paramString.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
                    hitChunk("StabL", paramShot);
                if(paramString.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
                    hitChunk("StabR", paramShot);
            } else
            if(paramString.startsWith("xvator"))
            {
                if(paramString.startsWith("xvatorl"))
                    hitChunk("VatorL", paramShot);
                if(paramString.startsWith("xvatorr"))
                    hitChunk("VatorR", paramShot);
            } else
            if(paramString.startsWith("xwing"))
            {
                if(paramString.startsWith("xwinglin") && chunkDamageVisible("WingLIn") < 3)
                    hitChunk("WingLIn", paramShot);
                if(paramString.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
                    hitChunk("WingRIn", paramShot);
                if(paramString.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
                    hitChunk("WingLMid", paramShot);
                if(paramString.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
                    hitChunk("WingRMid", paramShot);
                if(paramString.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
                    hitChunk("WingLOut", paramShot);
                if(paramString.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
                    hitChunk("WingROut", paramShot);
            } else
            if(paramString.startsWith("xarone"))
            {
                if(paramString.startsWith("xaronel"))
                    hitChunk("AroneL", paramShot);
                if(paramString.startsWith("xaroner"))
                    hitChunk("AroneR", paramShot);
            } else
            if(paramString.startsWith("xgear"))
            {
                if(paramString.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    debuggunnery("Hydro System: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(paramShot.initiator, 0);
                }
                if(paramString.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)
                {
                    debuggunnery("Undercarriage: Stuck..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(paramShot.initiator, 3);
                }
            } else
            if(paramString.startsWith("xpilot") || paramString.startsWith("xhead"))
            {
                int i = 0;
                int j;
                if(paramString.endsWith("a"))
                {
                    i = 1;
                    j = paramString.charAt(6) - 49;
                } else
                if(paramString.endsWith("b"))
                {
                    i = 2;
                    j = paramString.charAt(6) - 49;
                } else
                {
                    j = paramString.charAt(5) - 49;
                }
                hitFlesh(j, paramShot, i);
            }
        }
    }

    protected boolean cutFM(int paramInt1, int paramInt2, com.maddox.il2.engine.Actor paramActor)
    {
        switch(paramInt1)
        {
        case 13: // '\r'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.cgear = false;
            float t = com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F);
            if(t < 0.1F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(this, 0, 100);
                if((double)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.48999999999999999D)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(paramActor);
            } else
            if((double)t > 0.55000000000000004D)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(paramActor);
            return super.cutFM(paramInt1, paramInt2, paramActor);

        case 19: // '\023'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(paramActor);
            return super.cutFM(paramInt1, paramInt2, paramActor);
        }
        return super.cutFM(paramInt1, paramInt2, paramActor);
    }

    public void typeFighterAceMakerRangeFinder()
    {
        if(k14Mode == 2)
            return;
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        hunted = com.maddox.il2.game.Main3D.cur3D().getViewPadlockEnemy();
        if(hunted == null)
        {
            k14Distance = 200F;
            hunted = com.maddox.il2.ai.War.GetNearestEnemyAircraft(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, 2700F, 9);
        }
        if(hunted != null)
        {
            k14Distance = (float)((com.maddox.il2.engine.Interpolate) (super.FM)).actor.pos.getAbsPoint().distance(hunted.pos.getAbsPoint());
            if(k14Distance > 800F)
                k14Distance = 800F;
            else
            if(k14Distance < 200F)
                k14Distance = 200F;
        }
    }

    public float getAirPressure(float theAltitude)
    {
        float fBase = 1.0F - (0.0065F * theAltitude) / 288.15F;
        float fExponent = 5.255781F;
        return 101325F * (float)java.lang.Math.pow(fBase, fExponent);
    }

    public float getAirPressureFactor(float theAltitude)
    {
        return getAirPressure(theAltitude) / 101325F;
    }

    public float getAirDensity(float theAltitude)
    {
        return (getAirPressure(theAltitude) * 0.0289644F) / (8.31447F * (288.15F - 0.0065F * theAltitude));
    }

    public float getAirDensityFactor(float theAltitude)
    {
        return getAirDensity(theAltitude) / 1.225F;
    }

    public float getMachForAlt(float theAltValue)
    {
        theAltValue /= 1000F;
        int i = 0;
        for(i = 0; i < com.maddox.il2.objects.air.TypeSupersonic.fMachAltX.length && com.maddox.il2.objects.air.TypeSupersonic.fMachAltX[i] <= theAltValue; i++);
        if(i == 0)
        {
            return com.maddox.il2.objects.air.TypeSupersonic.fMachAltY[0];
        } else
        {
            float baseMach = com.maddox.il2.objects.air.TypeSupersonic.fMachAltY[i - 1];
            float spanMach = com.maddox.il2.objects.air.TypeSupersonic.fMachAltY[i] - baseMach;
            float baseAlt = com.maddox.il2.objects.air.TypeSupersonic.fMachAltX[i - 1];
            float spanAlt = com.maddox.il2.objects.air.TypeSupersonic.fMachAltX[i] - baseAlt;
            float spanMult = (theAltValue - baseAlt) / spanAlt;
            return baseMach + spanMach * spanMult;
        }
    }

    public float calculateMach()
    {
        return super.FM.getSpeedKMH() / getMachForAlt(super.FM.getAltitude());
    }

    public void soundbarier()
    {
        float f = getMachForAlt(super.FM.getAltitude()) - super.FM.getSpeedKMH();
        if(f < 0.5F)
            f = 0.5F;
        float f_0_ = super.FM.getSpeedKMH() - getMachForAlt(super.FM.getAltitude());
        if(f_0_ < 0.5F)
            f_0_ = 0.5F;
        if((double)calculateMach() <= 1.0D)
        {
            super.FM.VmaxAllowed = super.FM.getSpeedKMH() + f;
            SonicBoom = 0.0F;
            isSonic = false;
        }
        if((double)calculateMach() >= 1.0D)
        {
            super.FM.VmaxAllowed = super.FM.getSpeedKMH() + f_0_;
            isSonic = true;
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxAllowed > 1500F)
            super.FM.VmaxAllowed = 1500F;
        if(isSonic && SonicBoom < 1.0F)
        {
            super.playSound("aircraft.SonicBoom", true);
            super.playSound("aircraft.SonicBoomInternal", true);
            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "Mach 1 Exceeded!");
            if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextFloat() < getAirDensityFactor(super.FM.getAltitude()))
                shockwave = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Shockwave"), null, 1.0F, "3DO/Effects/Aircraft/Condensation.eff", -1F);
            SonicBoom = 1.0F;
        }
        if((double)calculateMach() > 1.01D || (double)calculateMach() < 1.0D)
            com.maddox.il2.engine.Eff3DActor.finish(shockwave);
    }

    public void engineSurge(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
            if(curthrl == -1F)
            {
                curthrl = oldthrl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
            } else
            {
                curthrl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
                if(curthrl < 1.05F)
                {
                    if((curthrl - oldthrl) / f > 20F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.4F)
                    {
                        if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                        super.playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += 0.01D * (double)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F && (super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(this, 0, 100);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F && (super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(this);
                    }
                    if((curthrl - oldthrl) / f < -20F && (curthrl - oldthrl) / f > -100F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
                    {
                        super.playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += 0.001D * (double)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.4F && (super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode())
                        {
                            if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Engine Flameout!");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineStops(this);
                        } else
                        if(((com.maddox.il2.engine.Interpolate) (super.FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                    }
                }
                oldthrl = curthrl;
            }
    }

    public void update(float paramFloat)
    {
        if(super.FM.getSpeed() > 5F)
        {
            moveSlats(paramFloat);
            bSlatsOff = false;
        } else
        {
            slatsOff();
        }
        if((((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout || overrideBailout) && !ejectComplete && super.FM.getSpeedKMH() > 15F)
        {
            overrideBailout = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout = false;
            bailout();
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.45F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 1.001F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 5);
                else
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.65F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() < 1.001F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 3);
                else
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 2);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
            }
            setExhaustFlame((int)com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getThrustOutput(), 0.7F, 0.87F, 0.0F, 12F), 0);
            if(super.FM instanceof com.maddox.il2.fm.RealFlightModel)
            {
                umn();
                $1._mth1();
            }
        }
        engineSurge(paramFloat);
        typeFighterAceMakerRangeFinder();
        checkHydraulicStatus();
        moveHydraulics(paramFloat);
        soundbarier();
        com.maddox.il2.fm.Controls controls = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT;
        float f6 = controls.getBayDoor();
        if(java.lang.Math.abs(BayDoor_ - f6) > 0.025F)
        {
            BayDoor_ = BayDoor_ + 0.025F * (f6 > BayDoor_ ? 2.0F : -1F);
            if(java.lang.Math.abs(BayDoor_ - 0.5F) >= 0.48F)
                sfxAirBrake();
        }
        super.update(paramFloat);
    }

    public void doSetSootState(int i, int j)
    {
        for(int k = 0; k < 2; k++)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] != null)
                com.maddox.il2.engine.Eff3DActor.finish(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][k]);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] = null;
        }

        switch(j)
        {
        case 1: // '\001'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "ES_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1F);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "ES_02"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1F);
            break;

        case 3: // '\003'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1F);
            // fall through

        case 2: // '\002'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 0.75F, "3DO/Effects/Aircraft/TurboZippo.eff", -1F);
            break;

        case 5: // '\005'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][0] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.5F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
            // fall through

        case 4: // '\004'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateSootEffects[i][1] = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1F);
            break;
        }
    }

    protected void moveAirBrake(float paramFloat)
    {
        resetYPRmodifier();
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -70F * paramFloat, 0.0F);
        hierMesh().chunkSetAngles("BrakeB01_D0", 0.0F, -30F * paramFloat, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -70F * paramFloat, 0.0F);
        hierMesh().chunkSetAngles("BrakeB02_D0", 0.0F, 30F * paramFloat, 0.0F);
        if((double)paramFloat < 0.20000000000000001D)
            com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.01F, 0.18F, 0.0F, -0.05F);
        else
            com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.22F, 0.99F, -0.05F, -0.22F);
        hierMesh().chunkSetLocate("BrakeB01e_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetLocate("BrakeB02e_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void setExhaustFlame(int stage, int i)
    {
        if(i == 0)
            switch(stage)
            {
            case 0: // '\0'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 1: // '\001'
                hierMesh().chunkVisible("Exhaust1", true);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 2: // '\002'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", true);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 3: // '\003'
                hierMesh().chunkVisible("Exhaust1", true);
                hierMesh().chunkVisible("Exhaust2", true);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                // fall through

            case 4: // '\004'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", true);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 5: // '\005'
                hierMesh().chunkVisible("Exhaust1", true);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", true);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 6: // '\006'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", true);
                hierMesh().chunkVisible("Exhaust3", true);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 7: // '\007'
                hierMesh().chunkVisible("Exhaust1", true);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", true);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 8: // '\b'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", true);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", true);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 9: // '\t'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", true);
                hierMesh().chunkVisible("Exhaust4", true);
                hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 10: // '\n'
                hierMesh().chunkVisible("Exhaust1", true);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", true);
                break;

            case 11: // '\013'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", true);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", true);
                break;

            case 12: // '\f'
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", true);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", true);
                break;

            default:
                hierMesh().chunkVisible("Exhaust1", false);
                hierMesh().chunkVisible("Exhaust2", false);
                hierMesh().chunkVisible("Exhaust3", false);
                hierMesh().chunkVisible("Exhaust4", false);
                hierMesh().chunkVisible("Exhaust5", false);
                break;
            }
    }

    private void bailout()
    {
        if(overrideBailout)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep >= 0 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep < 2)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.cockpitDoorControl > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getCockpitDoor() > 0.5F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep = 11;
                    doRemoveBlisters();
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep = 2;
                }
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep >= 2 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep <= 3)
            {
                switch(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep)
                {
                case 2: // '\002'
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.cockpitDoorControl < 0.5F)
                        doRemoveBlister1();
                    break;

                case 3: // '\003'
                    doRemoveBlisters();
                    break;
                }
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMirrors(20, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep, 1, null);
                com.maddox.il2.fm.AircraftState tmp178_177 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS;
                tmp178_177.astateBailoutStep = (byte)(tmp178_177.astateBailoutStep + 1);
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep == 4)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep = 11;
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep >= 11 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep <= 19)
            {
                int i = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep;
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster())
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.netToMirrors(20, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep, 1, null);
                com.maddox.il2.fm.AircraftState tmp383_382 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS;
                tmp383_382.astateBailoutStep = (byte)(tmp383_382.astateBailoutStep + 1);
                if(i == 11)
                {
                    super.FM.setTakenMortalDamage(true, null);
                    if((super.FM instanceof com.maddox.il2.ai.air.Maneuver) && ((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() != 44)
                    {
                        com.maddox.il2.ai.World.cur();
                        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.actor != com.maddox.il2.ai.World.getPlayerAircraft())
                            ((com.maddox.il2.ai.air.Maneuver)super.FM).set_maneuver(44);
                    }
                }
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astatePilotStates[i - 11] < 99)
                {
                    doRemoveBodyFromPlane(i - 10);
                    if(i == 11)
                    {
                        doEjectCatapult();
                        super.FM.setTakenMortalDamage(true, null);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[0] = false;
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[1] = false;
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep = -1;
                        overrideBailout = false;
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout = true;
                        ejectComplete = true;
                        if(i > 10 && i <= 19)
                            com.maddox.il2.ai.EventLog.onBailedOut(this, i - 11);
                    }
                }
            }
    }

    private final void doRemoveBlister1()
    {
        if(hierMesh().chunkFindCheck("Blister1_D0") != -1 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.getPilotHealth(0) > 0.0F)
        {
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            localWreckage.collide(false);
            com.maddox.JGP.Vector3d localVector3d = new Vector3d();
            localVector3d.set(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld);
            localWreckage.setSpeed(localVector3d);
        }
    }

    private final void doRemoveBlisters()
    {
        for(int i = 2; i < 10; i++)
            if(hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.getPilotHealth(i - 1) > 0.0F)
            {
                hierMesh().hideSubTrees("Blister" + i + "_D0");
                com.maddox.il2.objects.Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister" + i + "_D0"));
                localWreckage.collide(false);
                com.maddox.JGP.Vector3d localVector3d = new Vector3d();
                localVector3d.set(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld);
                localWreckage.setSpeed(localVector3d);
            }

    }

    private final void umn()
    {
        com.maddox.JGP.Vector3d vf1 = super.FM.getVflow();
        mn = (float)vf1.lengthSquared();
        mn = (float)java.lang.Math.sqrt(mn);
        com.maddox.il2.objects.air.F_86D F_86D = this;
        float f = mn;
        if(com.maddox.il2.ai.World.cur().Atm == null);
        F_86D.mn = f / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Loc)).z);
        if(mn >= lteb)
            ts = true;
        else
            ts = false;
    }

    public boolean ist()
    {
        return ts;
    }

    public float gmnr()
    {
        return mn;
    }

    public boolean inr()
    {
        return ictl;
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    private float BayDoor_;
    protected boolean bSlatsOff;
    private final com.maddox.il2.objects.air.._cls0 $1 = new ._cls0(0.0F, 13000F, 0.65F, 1.0F, 0.01F, 1.0F, 0.2F, 1.0F, 0.5F, 0.6F, 0.0F);
    private float oldctl;
    private float curctl;
    private float oldthrl;
    private float curthrl;
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    public float AirBrakeControl;
    private boolean overrideBailout;
    private boolean ejectComplete;
    private float lightTime;
    private float ft;
    private com.maddox.il2.engine.LightPointWorld lLight[];
    private com.maddox.il2.engine.Hook lLightHook[] = {
        null, null, null, null
    };
    private static com.maddox.il2.engine.Loc lLightLoc1 = new Loc();
    private static com.maddox.JGP.Point3d lLightP1 = new Point3d();
    private static com.maddox.JGP.Point3d lLightP2 = new Point3d();
    private static com.maddox.JGP.Point3d lLightPL = new Point3d();
    private boolean ictl;
    private static float mteb = 1.0F;
    private float mn;
    private static float uteb = 1.25F;
    private static float lteb = 0.92F;
    private float actl;
    private float rctl;
    private float ectl;
    private boolean ts;
    private float H1;
    public static boolean bChangedPit = false;
    private float SonicBoom;
    private com.maddox.il2.engine.Eff3DActor shockwave;
    private boolean isSonic;
    public static int LockState = 0;
    static com.maddox.il2.engine.Actor hunted = null;
    private float engineSurgeDamage;
    private float gearTargetAngle;
    private float gearCurrentAngle;
    public boolean hasHydraulicPressure;
    private static final float NEG_G_TOLERANCE_FACTOR = 1.5F;
    private static final float NEG_G_TIME_FACTOR = 1.5F;
    private static final float NEG_G_RECOVERY_FACTOR = 1F;
    private static final float POS_G_TOLERANCE_FACTOR = 2F;
    private static final float POS_G_TIME_FACTOR = 2F;
    private static final float POS_G_RECOVERY_FACTOR = 2F;

    static 
    {
        java.lang.Class localClass = com.maddox.il2.objects.air.F_86D.class;
        com.maddox.rts.Property.set(localClass, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }












}
