// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_262.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeFighter, TypeBNZFighter, PaintScheme, 
//            ME_262HGII, Aircraft, Cockpit, AircraftLH, 
//            CockpitME_262

public class ME_262 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public ME_262()
    {
        trimElevator = 0.0F;
        bHasElevatorControl = true;
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 200F;
        bHasBlister = true;
        X = 1.0F;
        s17 = s18 = 0.1F;
        s31 = s32 = 0.4F;
    }

    protected void moveElevator(float f)
    {
        f -= trimElevator;
        if(!(this instanceof com.maddox.il2.objects.air.ME_262HGII))
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
        } else
        {
            super.moveElevator(f);
        }
    }

    private void cutOp(int i)
    {
        ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.Operate &= ~(1L << i);
    }

    protected boolean getOp(int i)
    {
        return (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Operate & 1L << i) != 0L;
    }

    private float Op(int i)
    {
        return getOp(i) ? 1.0F : 0.0F;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(!getOp(i))
            return false;
        if(!(this instanceof com.maddox.il2.objects.air.ME_262HGII))
            switch(i)
            {
            case 33: // '!'
                return super.cutFM(34, j, actor);

            case 36: // '$'
                return super.cutFM(37, j, actor);

            case 11: // '\013'
                cutFM(17, j, actor);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).cut(17, j, actor);
                cutFM(18, j, actor);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).cut(18, j, actor);
                return super.cutFM(i, j, actor);

            case 17: // '\021'
                ((com.maddox.il2.objects.air.Aircraft)this).cut("StabL");
                cutOp(17);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfACM(false);
                if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Skill)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToReturn(true);
                if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 16) < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Skill)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToDie(true);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftStab *= 0.5F * Op(18) + 0.1F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftWingLIn *= 1.05F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftWingRIn *= 0.95F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.dragProducedCx -= 0.06F;
                if(Op(18) == 0.0F)
                {
                    ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setGCenter(0.2F);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setGCenter(0.15F);
                    s17 = 0.0F;
                    ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch *= s17 + s18 + s31 + s32;
                    X = 1.0F / (s17 + s18 + s31 + s32);
                    s18 *= X;
                    s31 *= X;
                    s32 *= X;
                }
                // fall through

            case 31: // '\037'
                if(Op(31) == 0.0F)
                    return false;
                ((com.maddox.il2.objects.air.Aircraft)this).cut("VatorL");
                cutOp(31);
                if(Op(32) == 0.0F)
                {
                    bHasElevatorControl = false;
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfACM(false);
                    if(Op(18) == 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToDie(true);
                }
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.squareElevators *= 0.5F * Op(32);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.dragProducedCx += 0.06F;
                s31 = 0.0F;
                ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch *= s17 + s18 + s31 + s32;
                X = 1.0F / (s17 + s18 + s31 + s32);
                s17 *= X;
                s18 *= X;
                s32 *= X;
                return false;

            case 18: // '\022'
                ((com.maddox.il2.objects.air.Aircraft)this).cut("StabR");
                cutOp(18);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfACM(false);
                if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Skill)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToReturn(true);
                if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 16) < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Skill)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToDie(true);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftStab *= 0.5F * Op(17) + 0.1F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftWingLIn *= 0.95F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.liftWingRIn *= 1.05F;
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.dragProducedCx -= 0.06F;
                if(Op(17) == 0.0F)
                {
                    ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setGCenter(0.2F);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setGCenter(0.15F);
                    s18 = 0.0F;
                    ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch *= s17 + s18 + s31 + s32;
                    X = 1.0F / (s17 + s18 + s31 + s32);
                    s17 *= X;
                    s31 *= X;
                    s32 *= X;
                }
                // fall through

            case 32: // ' '
                if(Op(32) == 0.0F)
                    return false;
                ((com.maddox.il2.objects.air.Aircraft)this).cut("VatorR");
                cutOp(32);
                if(Op(31) == 0.0F)
                {
                    bHasElevatorControl = false;
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfACM(false);
                    if(Op(17) == 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setReadyToDie(true);
                }
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.squareElevators *= 0.5F * Op(31);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Sq.dragProducedCx += 0.06F;
                s32 = 0.0F;
                ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.SensPitch *= s17 + s18 + s31 + s32;
                X = 1.0F / (s17 + s18 + s31 + s32);
                s17 *= X;
                s18 *= X;
                s31 *= X;
                return false;
            }
        return super.cutFM(i, j, actor);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            if(!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout)
            {
                if(((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Blister1_D0"))
                    ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Gore1_D0", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Gore2_D0", true);
            }
            break;
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 111F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC21_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, 90F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 88F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.ME_262.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    public void moveWheelSink()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        float f = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[2];
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.19F, 0.0F, 0.19F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearC22_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 19F, 0.0F, 30F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC7_D0", 0.0F, f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC8_D0", 0.0F, 2.0F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getGear() > 0.75F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC21_D0", 0.0F, 40F * f, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        if(bHasBlister)
        {
            ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
                ((com.maddox.il2.objects.sounds.SndAircraft)this).setDoorSnd(f);
            }
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xcf"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("CF") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > 1.7D)
            {
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.07F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 0);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.07F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 1);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 0);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 1);
            }
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > -0.999D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 0.53500000000000003D && ((com.maddox.JGP.Tuple3d) (point3d)).z > -0.224D)
            {
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x10);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x20);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 4);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 8);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x40);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 1);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 2);
            }
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > 0.80000000000000004D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 1.5800000000000001D && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F && (shot.powerType == 3 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.4F, shot) > 0.0F || shot.powerType == 0))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4000F)));
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > -2.4849999999999999D && ((com.maddox.JGP.Tuple3d) (point3d)).x < -1.6000000000000001D && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F && (shot.powerType == 3 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.4F, shot) > 0.0F || shot.powerType == 0))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4000F)));
        } else
        if(s.startsWith("xtail"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", shot);
        else
        if(s.startsWith("xstabl"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", shot);
        else
        if(s.startsWith("xwing"))
        {
            if(s.endsWith("lin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", shot);
            if(s.endsWith("rin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", shot);
            if(s.endsWith("lmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", shot);
            if(s.endsWith("rmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", shot);
            if(s.endsWith("lout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", shot);
            if(s.endsWith("rout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xengine"))
        {
            int i = s.charAt(7) - 49;
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > 0.0D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 0.69699999999999995D)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, 6));
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, i, 5);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int j;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j = s.charAt(6) - 49;
            } else
            {
                j = s.charAt(5) - 49;
            }
            ((com.maddox.il2.objects.air.AircraftLH)this).hitFlesh(j, shot, ((int) (byte0)));
        }
    }

    public void rareAction(float f, boolean flag)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(f, flag);
        if(flag && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.2F)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[0] > 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.explodeEngine(((com.maddox.il2.engine.Actor) (this)), 0);
                ((com.maddox.il2.objects.air.Aircraft)this).msgCollision(((com.maddox.il2.engine.Actor) (this)), "WingLIn_D0", "WingLIn_D0");
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextBoolean())
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 0, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
                else
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 1, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
            }
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[1] > 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.12F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.explodeEngine(((com.maddox.il2.engine.Actor) (this)), 1);
                ((com.maddox.il2.objects.air.Aircraft)this).msgCollision(((com.maddox.il2.engine.Actor) (this)), "WingRIn_D0", "WingRIn_D0");
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextBoolean())
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 0, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
                else
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 1, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
            }
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Head1_D0"));
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Head2_D0"));
    }

    public void engineSurge(float f)
    {
        for(int i = 0; i < 2; i++)
            if(curthrl[i] == -1F)
            {
                curthrl[i] = oldthrl[i] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getControlThrottle();
            } else
            {
                curthrl[i] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getControlThrottle();
                if((curthrl[i] - oldthrl[i]) / f > 8F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getStage() == 6 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F)
                {
                    if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                        com.maddox.il2.game.HUD.log("Compressor Stall!");
                    ((com.maddox.il2.engine.Actor)this).playSound("weapon.MGunMk108s", true);
                    engineSurgeDamage[i] += ((float) (0.01D * (double)(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() / 1000F)));
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getReadyness() - engineSurgeDamage[i]);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.2F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(((com.maddox.il2.engine.Actor) (this)), i, 100);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.2F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setEngineDies(((com.maddox.il2.engine.Actor) (this)));
                }
                if((curthrl[i] - oldthrl[i]) / f < -8F && (curthrl[i] - oldthrl[i]) / f > -100F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() < 3200F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getStage() == 6)
                {
                    ((com.maddox.il2.engine.Actor)this).playSound("weapon.MGunMk108s", true);
                    engineSurgeDamage[i] += ((float) (0.001D * (double)(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() / 1000F)));
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].doSetReadyness(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getReadyness() - engineSurgeDamage[i]);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                    {
                        if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log("Engine Flameout!");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setEngineStops(((com.maddox.il2.engine.Actor) (this)));
                    } else
                    {
                        if(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            com.maddox.il2.game.HUD.log("Compressor Stall!");
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setKillCompressor(((com.maddox.il2.engine.Actor) (this)));
                    }
                }
                oldthrl[i] = curthrl[i];
            }

    }

    public void update(float f)
    {
        if(((com.maddox.il2.objects.sounds.SndAircraft)this).FM.getSpeed() > 5F)
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatL0_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatL1_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatR0_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatR1_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            if(!(this instanceof com.maddox.il2.objects.air.ME_262HGII))
            {
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("SlatR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            }
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster())
        {
            engineSurge(f);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 0.8F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getStage() == 6)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 0.95F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 3);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 2);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 0);
                }
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[1].getPowerOutput() > 0.8F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[1].getStage() == 6)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[1].getPowerOutput() > 0.95F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 1, 3);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 1, 2);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 1, 0);
                }
            }
        }
        ((com.maddox.il2.objects.air.Aircraft)this).update(f);
        if(!(this instanceof com.maddox.il2.objects.air.ME_262HGII))
        {
            if(!getOp(31) || !getOp(32))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.trimAileron = ((((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.ElevatorControl * (s32 - s31) + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.trimElevator * (s18 - s17)) * ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).SensPitch) / 3F;
            if(!bHasElevatorControl)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.ElevatorControl = 0.0F;
            if(trimElevator != ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.trimElevator)
            {
                trimElevator = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.trimElevator;
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("StabL_D0", 0.0F, -14F * trimElevator, 0.0F);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("StabR_D0", 0.0F, -14F * trimElevator, 0.0F);
            }
        }
        if((double)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFindCheck("Blister1_D0") != -1 && !((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitME_262)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkFind("Blister1_D0"));
            ((com.maddox.il2.engine.Actor) (wreckage)).collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            ((com.maddox.JGP.Tuple3d) (vector3d)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Vwld)));
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.bHasCockpitDoorControl = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setGCenter(-0.5F);
        }
    }

    private float trimElevator;
    private boolean bHasElevatorControl;
    public float cockpitDoor_;
    private float fMaxKMHSpeedForOpenCanopy;
    public boolean bHasBlister;
    private float X;
    private float s17;
    private float s18;
    private float s31;
    private float s32;
    private float oldthrl[] = {
        -1F, -1F
    };
    private float curthrl[] = {
        -1F, -1F
    };
    private float engineSurgeDamage[] = {
        0.0F, 0.0F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
