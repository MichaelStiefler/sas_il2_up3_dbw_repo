// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Mig_17.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
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
//            Scheme1, TypeSupersonic, TypeFighter, TypeBNZFighter, 
//            TypeFighterAceMaker, PaintScheme, Aircraft, Cockpit, 
//            AircraftLH, EjectionSeat

public class Mig_17 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeSupersonic, com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeFighterAceMaker
{
    private class ._cls0
    {

        public void rs(int ii)
        {
            if(ii == 0 || ii == 1)
                actl*= = ((float) (0.68000000000000005D));
            if(ii == 31 || ii == 32)
                ectl*= = ((float) (0.68000000000000005D));
            if(ii == 15 || ii == 16)
                rctl*= = ((float) (0.68000000000000005D));
        }

        private void $1()
        {
            if(ts)
            {
                float f1 = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (FM)).getAltitude(), lal, tal, bef, tef);
                float f2 = com.maddox.il2.objects.air.Aircraft.cvt(mn, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? com.maddox.il2.objects.air.Mig_17.uteb : com.maddox.il2.objects.air.Mig_17.lteb, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? com.maddox.il2.objects.air.Mig_17.lteb : com.maddox.il2.objects.air.Mig_17.uteb, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? thef : bhef, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? phef : thef);
                float f3 = com.maddox.il2.objects.air.Aircraft.cvt(mn, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? com.maddox.il2.objects.air.Mig_17.uteb : com.maddox.il2.objects.air.Mig_17.lteb, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? com.maddox.il2.objects.air.Mig_17.lteb : com.maddox.il2.objects.air.Mig_17.uteb, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? wef / f1 : mef, mn >= com.maddox.il2.objects.air.Mig_17.mteb ? lef / f1 : wef / f1);
                ((com.maddox.il2.fm.RealFlightModel)FM).producedShakeLevel += 0.1125F * f2;
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() > 0.76F)
                    FM.SensPitch = ectl * f3 * f3;
                else
                    FM.SensPitch = ectl * f3;
                FM.SensRoll = actl * f3 * f3;
                FM.SensYaw = rctl * f3;
                if(f2 > 0.6F)
                    ictl = true;
                else
                    ictl = false;
                if(ftl > 0.0F)
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() > 0.6F)
                        if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl > 0.0F)
                            ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl -= ftl * f2;
                        else
                        if(((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl < 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (FM)).CT.RudderControl += ftl * f2;
                        } else
                        {
                            com.maddox.il2.fm.Controls controls = ((com.maddox.il2.fm.FlightModelMain) (FM)).CT;
                            controls.RudderControl = controls.RudderControl + (((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() <= 0.5F ? -ftl * f2 : ftl * f2);
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

        ._cls0(float f, float f1, float f2, float f3, float f4, float f5, 
                float f6, float f7, float f8, float f9, float f10, com.maddox.il2.objects.air.._cls0 _pcls0)
        {
            this(f, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10);
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

    public Mig_17()
    {
        mn = 0.0F;
        SonicBoom = 0.0F;
        ts = false;
        curst = false;
        oldctl = -1F;
        curctl = -1F;
        ictl = false;
        overrideBailout = false;
        ejectComplete = false;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        engineSurgeDamage = 0.0F;
    }

    public void typeFighterAceMakerRangeFinder()
    {
        if(k14Mode == 0)
            return;
        hunted = com.maddox.il2.game.Main3D.cur3D().getViewPadlockEnemy();
        if(hunted == null)
            hunted = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.War.GetNearestEnemyAircraft(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor, 2000F, 9)));
        if(hunted != null)
        {
            k14Distance = (float)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor.pos.getAbsPoint().distance(hunted.pos.getAbsPoint());
            if(k14Distance > 800F)
                k14Distance = 800F;
            else
            if(k14Distance < 200F)
                k14Distance = 200F;
        }
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
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
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        ((com.maddox.rts.NetMsgOutput) (netmsgguaranted)).writeByte(k14Mode);
        ((com.maddox.rts.NetMsgOutput) (netmsgguaranted)).writeByte(k14WingspanType);
        ((com.maddox.rts.NetMsgOutput) (netmsgguaranted)).writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        k14Mode = ((int) (netmsginput.readByte()));
        k14WingspanType = ((int) (netmsginput.readByte()));
        k14Distance = netmsginput.readFloat();
    }

    public void onAircraftLoaded()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).onAircraftLoaded();
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.wantBeaconsNet(true);
        actl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).SensRoll;
        ectl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).SensPitch;
        rctl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).SensYaw;
    }

    public void rareAction(float paramFloat, boolean paramBoolean)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(paramFloat, paramBoolean);
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.nearGround() || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.onGround()) && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getCockpitDoor() == 1.0F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot1_D0"));
        if((!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).isPlayers() || !(((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode()) && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.ai.air.Maneuver))
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AP.way.isLanding() && ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).VmaxFLAPS && ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AP.way.curr().getV() * 1.4F)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 1.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 1.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).get_maneuver() == 25 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AP.way.isLanding() && ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.getSpeed() < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).VmaxFLAPS * 1.16F)
            {
                if(((com.maddox.il2.objects.sounds.SndAircraft)this).FM.getSpeed() > ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).VminFLAPS * 0.5F && (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.nearGround() || ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.onGround()))
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 1.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 1.0F;
                } else
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 0.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).get_maneuver() == 66)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 0.0F;
            } else
            if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).get_maneuver() == 7)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 1.0F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 1.0F;
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl != 0.0F)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.AirBrakeControl = 0.0F;
    }

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, ((java.lang.Object) (this))) {

            public void doAction(java.lang.Object paramObject)
            {
                com.maddox.il2.objects.air.Aircraft localAircraft = (com.maddox.il2.objects.air.Aircraft)paramObject;
                if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (localAircraft))))
                {
                    com.maddox.il2.engine.Loc localLoc1 = new Loc();
                    com.maddox.il2.engine.Loc localLoc2 = new Loc();
                    com.maddox.JGP.Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed localHookNamed = new HookNamed(((com.maddox.il2.engine.ActorMesh) (localAircraft)), "_ExternalSeat01");
                    ((com.maddox.il2.engine.Actor) (localAircraft)).pos.getAbs(localLoc2);
                    localHookNamed.computePos(((com.maddox.il2.engine.Actor) (localAircraft)), localLoc2, localLoc1);
                    localLoc1.transform(localVector3d);
                    localVector3d.x += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).x;
                    localVector3d.y += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).y;
                    localVector3d.z += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (localAircraft)).FM)).Vwld)).z;
                    new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
                }
            }

        }
;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Seat_D0", false);
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setTakenMortalDamage(true, ((com.maddox.il2.engine.Actor) (null)));
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.WeaponControl[0] = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.WeaponControl[1] = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasAileronControl = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasRudderControl = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasElevatorControl = false;
    }

    public void doMurderPilot(int paramInt)
    {
        switch(paramInt)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    protected void nextDMGLevel(java.lang.String paramString, int paramInt, com.maddox.il2.engine.Actor paramActor)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevel(paramString, paramInt, paramActor);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String paramString, int paramInt, com.maddox.il2.engine.Actor paramActor)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).nextCUTLevel(paramString, paramInt, paramActor);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).isPlayers())
            bChangedPit = true;
    }

    public void moveCockpitDoor(float paramFloat)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        if(paramFloat < 0.1F)
            com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.01F, 0.08F, 0.0F, 0.1F);
        else
            com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.17F, 0.99F, 0.1F, 0.4F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
            ((com.maddox.il2.objects.sounds.SndAircraft)this).setDoorSnd(paramFloat);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh paramHierMesh, float paramFloat)
    {
        float f = java.lang.Math.max(-paramFloat * 1500F, -90F);
        paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -127F * paramFloat, 0.0F);
        paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.0F, 0.15F, 0.0F, -90F), 0.0F);
        paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.0F, 0.15F, 0.0F, -95F), 0.0F);
        paramHierMesh.chunkSetAngles("GearL2_D0", -90F * paramFloat, -38F * paramFloat, 90F * paramFloat);
        paramHierMesh.chunkSetAngles("GearR2_D0", 90F * paramFloat, -38F * paramFloat, -90F * paramFloat);
        paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -115F * paramFloat, 0.0F);
        paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -115F * paramFloat, 0.0F);
        paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
        paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
    }

    protected void moveGear(float paramFloat)
    {
        com.maddox.il2.objects.air.Mig_17.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), paramFloat);
    }

    public void moveWheelSink()
    {
        float f = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[2], 0.0F, 0.2F, 0.0F, 1.0F);
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.2F * f;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearC6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC8_D0", 0.0F, -15F * f, 0.0F);
    }

    protected void moveRudder(float paramFloat)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * paramFloat, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.GearControl > 0.5F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC7_D0", 0.0F, 40F * paramFloat, 0.0F);
    }

    protected void moveFlap(float paramFloat)
    {
        float f = -45F * paramFloat;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    }

    protected void moveFan(float f)
    {
    }

    protected void moveAirBrake(float paramFloat)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Brake01_D0", 10F * paramFloat, -55F * paramFloat, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Brake02_D0", 10F * paramFloat, -55F * paramFloat, 0.0F);
    }

    protected void hitBone(java.lang.String paramString, com.maddox.il2.ai.Shot paramShot, com.maddox.JGP.Point3d paramPoint3d)
    {
        int ii = ((com.maddox.il2.objects.air.Aircraft)this).part(paramString);
        $1.rs(ii);
        if(paramString.startsWith("xx"))
        {
            if(paramString.startsWith("xxammo"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 20000F) < paramShot.power && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, com.maddox.il2.ai.World.Rnd().nextInt(0, 2));
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(11.4F, paramShot);
            } else
            if(paramString.startsWith("xxarmor"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armor: Hit..");
                if(paramString.endsWith("p1"))
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(13.350000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                    if(paramShot.power <= 0.0F)
                        ((com.maddox.il2.objects.air.Aircraft)this).doRicochetBack(paramShot);
                } else
                if(paramString.endsWith("p2"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8.770001F, paramShot);
                else
                if(paramString.endsWith("P3"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8.770001F, paramShot);
                else
                if(paramString.endsWith("g1"))
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(40F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(paramShot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 2);
                    if(paramShot.power <= 0.0F)
                        ((com.maddox.il2.objects.air.Aircraft)this).doRicochetBack(paramShot);
                }
            } else
            if(paramString.startsWith("xxcontrols"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Hit..");
                int i = paramString.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.1F, paramShot) > 0.0F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Ailerones Controls: Out..");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(paramShot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(paramShot.initiator, 1);
                    }
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), paramShot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(paramShot.initiator, 2);
                    }
                    break;
                }
            } else
            if(paramString.startsWith("xxeng1"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Hit..");
                if(paramString.endsWith("bloc"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), paramShot);
                if(paramString.endsWith("cams") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.45F, paramShot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersRatio() * 20F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(paramShot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(paramShot.power / 4800F)));
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Engine Cams Hit, " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylinders() + " Left..");
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < paramShot.power / 24000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(paramShot.initiator, 0, 2);
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(paramShot.powerType == 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(paramShot.initiator, 0, 1);
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(paramString.endsWith("eqpt") && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < paramShot.power / 24000F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(paramShot.initiator, 0, 3);
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                paramString.endsWith("exht");
            } else
            if(paramString.startsWith("xxcannon0"))
            {
                int i = paramString.charAt(9) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.5F, paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon (" + i + ") Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, i);
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), paramShot);
                }
            } else
            if(paramString.startsWith("xxtank"))
            {
                int i = paramString.charAt(6) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, paramShot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateTankStates[i] == 0)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (" + i + "): Pierced..");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(paramShot.initiator, i, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.doSetTankState(paramShot.initiator, i, 1);
                    }
                    if(paramShot.powerType == 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.075F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(paramShot.initiator, i, 2);
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (" + i + "): Hit..");
                    }
                }
            } else
            if(paramString.startsWith("xxspar"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: Hit..");
                if(paramString.startsWith("xxsparlm") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: WingLMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparrm") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: WingRMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparlo") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: WingLOut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
                }
                if(paramString.startsWith("xxsparro") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: WingROut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
                }
            } else
            if(paramString.startsWith("xxhyd"))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setInternalDamage(paramShot.initiator, 3);
            else
            if(paramString.startsWith("xxpnm"))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setInternalDamage(paramShot.initiator, 1);
        } else
        {
            if(paramString.startsWith("xblister"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(paramShot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 1);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.05F, paramShot);
            }
            if(paramString.startsWith("xcf"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", paramShot);
            else
            if(paramString.startsWith("xnose"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Nose", paramShot);
            else
            if(paramString.startsWith("xTail"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", paramShot);
            } else
            if(paramString.startsWith("xkeel"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Keel1") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", paramShot);
            } else
            if(paramString.startsWith("xrudder"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Rudder1", paramShot);
            else
            if(paramString.startsWith("xstab"))
            {
                if(paramString.startsWith("xstabl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabL") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", paramShot);
                if(paramString.startsWith("xstabr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabR") < 1)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", paramShot);
            } else
            if(paramString.startsWith("xvator"))
            {
                if(paramString.startsWith("xvatorl"))
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorL", paramShot);
                if(paramString.startsWith("xvatorr"))
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorR", paramShot);
            } else
            if(paramString.startsWith("xwing"))
            {
                if(paramString.startsWith("xwinglin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", paramShot);
                if(paramString.startsWith("xwingrin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", paramShot);
                if(paramString.startsWith("xwinglmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", paramShot);
                if(paramString.startsWith("xwingrmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", paramShot);
                if(paramString.startsWith("xwinglout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", paramShot);
                if(paramString.startsWith("xwingrout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", paramShot);
            } else
            if(paramString.startsWith("xarone"))
            {
                if(paramString.startsWith("xaronel"))
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneL", paramShot);
                if(paramString.startsWith("xaroner"))
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneR", paramShot);
            } else
            if(paramString.startsWith("xgear"))
            {
                if(paramString.endsWith("1") && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Hydro System: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setInternalDamage(paramShot.initiator, 0);
                }
                if(paramString.endsWith("2") && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Undercarriage: Stuck..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setInternalDamage(paramShot.initiator, 3);
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
                ((com.maddox.il2.objects.air.AircraftLH)this).hitFlesh(j, paramShot, i);
            }
        }
    }

    protected boolean cutFM(int paramInt1, int paramInt2, com.maddox.il2.engine.Actor paramActor)
    {
        switch(paramInt1)
        {
        default:
            break;

        case 13: // '\r'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.cgear = false;
            float t = ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat();
            if(t < 0.1F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(((com.maddox.il2.engine.Actor) (this)), 0, 100);
                if((double)((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.48999999999999999D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineDies(paramActor);
                break;
            }
            if((double)t > 0.55000000000000004D)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineDies(paramActor);
            break;

        case 34: // '"'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.lgear = false;
            break;

        case 37: // '%'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.rgear = false;
            break;

        case 19: // '\023'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasAirBrakeControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineDies(paramActor);
            break;

        case 11: // '\013'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasElevatorControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasRudderControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasRudderTrim = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasElevatorTrim = false;
            break;
        }
        return super.cutFM(paramInt1, paramInt2, paramActor);
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
        for(i = 0; i < com.maddox.il2.objects.air.TypeSupersonic.fMachAltX.length; i++)
            if(com.maddox.il2.objects.air.TypeSupersonic.fMachAltX[i] > theAltValue)
                break;

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
        return ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() / getMachForAlt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude());
    }

    public void soundbarier()
    {
        float f = getMachForAlt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude()) - ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH();
        if(f < 0.5F)
            f = 0.5F;
        float f_0_ = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() - getMachForAlt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude());
        if(f_0_ < 0.5F)
            f_0_ = 0.5F;
        if((double)calculateMach() <= 1.0D)
        {
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.VmaxAllowed = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() + f;
            SonicBoom = 0.0F;
            isSonic = false;
        }
        if((double)calculateMach() >= 1.0D)
        {
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.VmaxAllowed = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() + f_0_;
            isSonic = true;
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).VmaxAllowed > 1500F)
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.VmaxAllowed = 1500F;
        if(isSonic && SonicBoom < 1.0F)
        {
            ((com.maddox.il2.engine.Actor)this).playSound("aircraft.SonicBoom", true);
            ((com.maddox.il2.engine.Actor)this).playSound("aircraft.SonicBoomInternal", true);
            if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "Mach 1 Exceeded!");
            if(com.maddox.il2.engine.Config.isUSE_RENDER() && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < getAirDensityFactor(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude()))
                shockwave = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.ActorMesh)this).findHook("_Shockwave"), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Aircraft/Condensation.eff", -1F);
            SonicBoom = 1.0F;
        }
        if((double)calculateMach() > 1.01D || (double)calculateMach() < 1.0D)
            com.maddox.il2.engine.Eff3DActor.finish(shockwave);
    }

    public void engineSurge(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster())
            if(curthrl == -1F)
            {
                curthrl = oldthrl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getControlThrottle();
            } else
            {
                curthrl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getControlThrottle();
                if(curthrl < 1.05F)
                {
                    if((curthrl - oldthrl) / f > 20F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getStage() == 6 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.4F)
                    {
                        if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                        ((com.maddox.il2.engine.Actor)this).playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += ((float) (0.01D * (double)(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getRPM() / 1000F)));
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(((com.maddox.il2.engine.Actor) (this)), 0, 100);
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineDies(((com.maddox.il2.engine.Actor) (this)));
                    }
                    if((curthrl - oldthrl) / f < -20F && (curthrl - oldthrl) / f > -100F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getStage() == 6)
                    {
                        ((com.maddox.il2.engine.Actor)this).playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += ((float) (0.001D * (double)(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getRPM() / 1000F)));
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.4F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                        {
                            if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Engine Flameout!");
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineStops(((com.maddox.il2.engine.Actor) (this)));
                        } else
                        if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                    }
                }
                oldthrl = curthrl;
            }
    }

    public void update(float paramFloat)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 0.5F)
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 1.001F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 5);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 3);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 0);
            }
            setExhaustFlame(java.lang.Math.round(com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getThrustOutput(), 0.7F, 0.87F, 0.0F, 12F)), 0);
            if(((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel)
            {
                umnr();
                $1._mth1();
            }
            if(curctl == -1F)
            {
                curctl = oldctl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getAirBrake();
            } else
            {
                curctl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getAirBrake();
                if(curctl > 0.05F)
                    if(curctl - oldctl > 0.0F)
                        curst = true;
                    else
                    if(curctl - oldctl == 0.0F && oldctl == 1.0F)
                        curst = true;
                    else
                        curst = false;
            }
            oldctl = curctl;
        }
        if((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout || overrideBailout) && !ejectComplete && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() > 15F)
        {
            overrideBailout = true;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout = false;
            bailout();
        }
        soundbarier();
        engineSurge(paramFloat);
        ((com.maddox.il2.objects.air.Aircraft)this).update(paramFloat);
    }

    public void setExhaustFlame(int stage, int i)
    {
        if(i == 0)
            switch(stage)
            {
            case 0: // '\0'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 1: // '\001'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 2: // '\002'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 3: // '\003'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                // fall through

            case 4: // '\004'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 5: // '\005'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 6: // '\006'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 7: // '\007'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 8: // '\b'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 9: // '\t'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;

            case 10: // '\n'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", true);
                break;

            case 11: // '\013'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", true);
                break;

            case 12: // '\f'
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", true);
                break;

            default:
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust1", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust2", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust3", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust4", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Exhaust5", false);
                break;
            }
    }

    private void bailout()
    {
        if(overrideBailout)
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep >= 0 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep < 2)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.cockpitDoorControl > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getCockpitDoor() > 0.5F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep = 11;
                    doRemoveBlisters();
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep = 2;
                }
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep >= 2 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep <= 3)
            {
                switch(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep)
                {
                case 2: // '\002'
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.cockpitDoorControl < 0.5F)
                        doRemoveBlister1();
                    break;

                case 3: // '\003'
                    doRemoveBlisters();
                    break;
                }
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster())
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.netToMirrors(20, ((int) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep)), 1, ((com.maddox.il2.engine.Actor) (null)));
                com.maddox.il2.fm.AircraftState tmp178_177 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS;
                tmp178_177.astateBailoutStep = (byte)(tmp178_177.astateBailoutStep + 1);
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep == 4)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep = 11;
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep >= 11 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep <= 19)
            {
                int i = ((int) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep));
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster())
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.netToMirrors(20, ((int) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep)), 1, ((com.maddox.il2.engine.Actor) (null)));
                com.maddox.il2.fm.AircraftState tmp383_382 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS;
                tmp383_382.astateBailoutStep = (byte)(tmp383_382.astateBailoutStep + 1);
                if(i == 11)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setTakenMortalDamage(true, ((com.maddox.il2.engine.Actor) (null)));
                    if((((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.ai.air.Maneuver) && ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).get_maneuver() != 44)
                    {
                        com.maddox.il2.ai.World.cur();
                        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.actor != com.maddox.il2.ai.World.getPlayerAircraft())
                            ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).set_maneuver(44);
                    }
                }
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astatePilotStates[i - 11] < 99)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).doRemoveBodyFromPlane(i - 10);
                    if(i == 11)
                    {
                        doEjectCatapult();
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setTakenMortalDamage(true, ((com.maddox.il2.engine.Actor) (null)));
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.WeaponControl[0] = false;
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.WeaponControl[1] = false;
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateBailoutStep = -1;
                        overrideBailout = false;
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout = true;
                        ejectComplete = true;
                    }
                }
            }
    }

    private final void doRemoveBlister1()
    {
        if(((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFindCheck("Blister1_D0") != -1 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.getPilotHealth(0) > 0.0F)
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage localWreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFind("Blister1_D0"));
            ((com.maddox.il2.engine.Actor) (localWreckage)).collide(false);
            com.maddox.JGP.Vector3d localVector3d = new Vector3d();
            ((com.maddox.JGP.Tuple3d) (localVector3d)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Vwld)));
            localWreckage.setSpeed(localVector3d);
        }
    }

    private final void doRemoveBlisters()
    {
        for(int i = 2; i < 10; i++)
            if(((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.getPilotHealth(i - 1) > 0.0F)
            {
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().hideSubTrees("Blister" + i + "_D0");
                com.maddox.il2.objects.Wreckage localWreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFind("Blister" + i + "_D0"));
                ((com.maddox.il2.engine.Actor) (localWreckage)).collide(false);
                com.maddox.JGP.Vector3d localVector3d = new Vector3d();
                ((com.maddox.JGP.Tuple3d) (localVector3d)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Vwld)));
                localWreckage.setSpeed(localVector3d);
            }

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

    private final void umnr()
    {
        com.maddox.JGP.Vector3d vf1 = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getVflow();
        mn = (float)vf1.lengthSquared();
        mn = (float)java.lang.Math.sqrt(mn);
        com.maddox.il2.objects.air.Mig_17 mig_17 = this;
        float f = mn;
        ((java.lang.Object) (com.maddox.il2.ai.World.cur())).getClass();
        mig_17.mn = f / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Loc)).z);
        if(mn >= lteb)
            ts = true;
        else
            ts = false;
    }

    public void doSetSootState(int paramInt1, int paramInt2)
    {
        for(int i = 0; i < 2; i++)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][i] != null)
                com.maddox.il2.engine.Eff3DActor.finish(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][i]);
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][i] = null;
        }

        switch(paramInt2)
        {
        case 3: // '\003'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][0] = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.ActorMesh)this).findHook("_Engine1EF_01"), ((com.maddox.il2.engine.Loc) (null)), 0.75F, "3DO/Effects/Aircraft/TurboZippo.eff", -1F);
            break;

        case 5: // '\005'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][0] = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.ActorMesh)this).findHook("_Engine1EF_01"), ((com.maddox.il2.engine.Loc) (null)), 0.75F, "3DO/Effects/Aircraft/TurboZippo.eff", -1F);
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateSootEffects[paramInt1][1] = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.ActorMesh)this).findHook("_Engine1EF_02"), ((com.maddox.il2.engine.Loc) (null)), 2.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        java.lang.Class var_class;
        try
        {
            var_class = java.lang.Class.forName(x0);
        }
        catch(java.lang.ClassNotFoundException x1)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (x1)).getMessage());
        }
        return var_class;
    }

    protected boolean curst;
    private boolean ts;
    private float oldctl;
    private float curctl;
    private float oldthrl;
    private float curthrl;
    private boolean overrideBailout;
    private boolean ejectComplete;
    public static boolean bChangedPit = false;
    private float mn;
    private static float uteb = 1.25F;
    private static float lteb = 0.9F;
    private static float mteb = 1.0F;
    private boolean ictl;
    private float actl;
    private float rctl;
    private float ectl;
    private float SonicBoom;
    private com.maddox.il2.engine.Eff3DActor shockwave;
    private boolean isSonic;
    private final com.maddox.il2.objects.air.._cls0 $1 = new ._cls0(0.0F, 14000F, 0.65F, 1.0F, 0.05F, 1.0F, 0.4F, 1.0F, 0.46F, 0.55F, 0.65F, ((com.maddox.il2.objects.air.._cls0) (null)));
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    private static com.maddox.il2.engine.Actor hunted = null;
    private float engineSurgeDamage;

    static 
    {
        java.lang.Class localClass = com.maddox.il2.objects.air.Mig_17.class;
        com.maddox.rts.Property.set(((java.lang.Object) (localClass)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }












}
