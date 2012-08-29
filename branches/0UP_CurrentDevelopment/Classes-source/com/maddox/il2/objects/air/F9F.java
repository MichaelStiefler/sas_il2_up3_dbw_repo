// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F9F.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
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
//            Scheme1, TypeFighter, TypeBNZFighter, TypeFighterAceMaker, 
//            PaintScheme, Aircraft, NetAircraft, AircraftLH, 
//            Cockpit, EjectionSeat

public class F9F extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeFighterAceMaker
{

    public F9F()
    {
        oldthrl = -1F;
        curthrl = -1F;
        arrestor2 = 0.0F;
        AirBrakeControl = 0.0F;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        overrideBailout = false;
        ejectComplete = false;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(super.FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.isLanding() && (double)super.FM.getSpeed() > (double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS * 2D)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 1.0F;
        else
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AP.way.isLanding() && (double)super.FM.getSpeed() < (double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS * 1.5D)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.AirBrakeControl = 0.0F;
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
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        k14Mode = netmsginput.readByte();
        k14WingspanType = netmsginput.readByte();
        k14Distance = netmsginput.readFloat();
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 12.2F * f, 0.0F);
        hierMesh().chunkSetAngles("Hook2_D0", 0.0F, 0.0F, 0.0F);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 70F), 0.0F);
        hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -70F), 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, 80F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.04F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -70F * f, 0.0F);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.5F, 0.0F, -90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.72F, 0.98F, -90F, 0.0F), 0.0F);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.4F, 0.0F, 90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.72F, 0.98F, 90F, 0.0F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -70F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.F9F.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        float f = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[2], 0.0F, 0.19075F, 0.0F, 1.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.19075F * f;
        hierMesh().chunkSetLocate("GearC6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, 30F * f, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl > 0.5F)
            hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -60F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = 55F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, 0.0F, f1);
    }

    protected void moveFan(float f1)
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("p1"))
                {
                    getEnergyPastArmor(13.350000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(8.77F, shot);
                else
                if(s.endsWith("g1"))
                {
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(40F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                }
            } else
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(1.1F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                    }
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;
                }
            } else
            if(s.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("bloc"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 60F) / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("cams") && getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 20F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Engine Cams Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 2);
                        debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
                        debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(s.endsWith("eqpt") && com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                    debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                if(!s.endsWith("exht"));
            } else
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[j] == 0)
                    {
                        debuggunnery("Fuel Tank (" + j + "): Pierced..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, j, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.075F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j, 2);
                        debuggunnery("Fuel Tank (" + j + "): Hit..");
                    }
                }
            } else
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
            } else
            if(s.startsWith("xxhyd"))
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 3);
            else
            if(s.startsWith("xxpnm"))
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 1);
        } else
        {
            if(s.startsWith("xcockpit"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
                getEnergyPastArmor(0.05F, shot);
            }
            if(s.startsWith("xxhispa1") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
            if(s.startsWith("xxhispa2") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
            if(s.startsWith("xxhispa3") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 2);
            if(s.startsWith("xxhispa4") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 3);
            if(s.startsWith("xcf"))
                hitChunk("CF", shot);
            else
            if(s.startsWith("xnose"))
                hitChunk("Nose", shot);
            else
            if(s.startsWith("xtail"))
            {
                if(chunkDamageVisible("Tail1") < 3)
                    hitChunk("Tail1", shot);
            } else
            if(s.startsWith("xkeel"))
            {
                if(chunkDamageVisible("Keel1") < 2)
                    hitChunk("Keel1", shot);
            } else
            if(s.startsWith("xrudder"))
                hitChunk("Rudder1", shot);
            else
            if(s.startsWith("xstab"))
            {
                if(s.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
                    hitChunk("StabL", shot);
                if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
                    hitChunk("StabR", shot);
            } else
            if(s.startsWith("xvator"))
            {
                if(s.startsWith("xvatorl"))
                    hitChunk("VatorL", shot);
                if(s.startsWith("xvatorr"))
                    hitChunk("VatorR", shot);
            } else
            if(s.startsWith("xwing"))
            {
                if(s.startsWith("xwinglin") && chunkDamageVisible("WingLIn") < 3)
                    hitChunk("WingLIn", shot);
                if(s.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
                    hitChunk("WingRIn", shot);
                if(s.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
                    hitChunk("WingLMid", shot);
                if(s.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
                    hitChunk("WingRMid", shot);
                if(s.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
                    hitChunk("WingLOut", shot);
                if(s.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
                    hitChunk("WingROut", shot);
            } else
            if(s.startsWith("xarone"))
            {
                if(s.startsWith("xaronel"))
                    hitChunk("AroneL", shot);
                if(s.startsWith("xaroner"))
                    hitChunk("AroneR", shot);
            } else
            if(s.startsWith("xgear"))
            {
                if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    debuggunnery("Hydro System: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 0);
                }
                if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
                {
                    debuggunnery("Undercarriage: Stuck..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 3);
                }
            } else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte0 = 0;
                int k;
                if(s.endsWith("a"))
                {
                    byte0 = 1;
                    k = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte0 = 2;
                    k = s.charAt(6) - 49;
                } else
                {
                    k = s.charAt(5) - 49;
                }
                hitFlesh(k, shot, byte0);
            }
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(actor);
            return super.cutFM(i, j, actor);
        }
        return super.cutFM(i, j, actor);
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

    public void update(float f)
    {
        if((((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout || overrideBailout) && !ejectComplete && super.FM.getSpeedKMH() > 15F)
        {
            overrideBailout = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout = false;
            bailout();
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.75F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 3);
                else
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 2);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
            }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() > 0.9F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle != 0.0F)
            {
                arrestor2 = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle, -65F, 3F, 45F, -23F);
                hierMesh().chunkSetAngles("Hook2_D0", 0.0F, arrestor2, 0.0F);
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle < -35F);
            } else
            {
                float f1 = -41F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVSink;
                if(f1 < 0.0F && super.FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f1 > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() < 0.9F)
                    f1 = 0.0F;
                if(f1 > 6.2F)
                    f1 = 6.2F;
                arrestor2 += f1;
                if(arrestor2 < -23F)
                    arrestor2 = -23F;
                else
                if(arrestor2 > 45F)
                    arrestor2 = 45F;
                hierMesh().chunkSetAngles("Hook2_D0", 0.0F, arrestor2, 0.0F);
            }
        engineSurge(f);
        super.update(f);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -60F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -60F * f, 0.0F);
    }

    public void moveCockpitDoor(float paramFloat)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.01F, 0.95F, 0.0F, 0.9F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        float f = (float)java.lang.Math.sin(com.maddox.il2.objects.air.Aircraft.cvt(paramFloat, 0.4F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f);
        hierMesh().chunkSetAngles("Head1_D0", 14F * f, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
            setDoorSnd(paramFloat);
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

    private float oldthrl;
    private float curthrl;
    private float engineSurgeDamage;
    private boolean overrideBailout;
    private boolean ejectComplete;
    public static boolean bChangedPit = false;
    private float arrestor2;
    public float AirBrakeControl;
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F9F.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
