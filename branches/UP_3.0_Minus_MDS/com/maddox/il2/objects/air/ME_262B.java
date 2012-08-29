// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_262B.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeFighter, TypeBNZFighter, Aircraft, 
//            AircraftLH, PaintScheme

public class ME_262B extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public ME_262B()
    {
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
            // fall through

        case 1: // '\001'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D1", true);
            if(!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.bIsAboutToBailout)
            {
                if(((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Blister2_D0"))
                    ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Gore1_D0", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Gore2_D0", true);
            }
            // fall through

        default:
            return;
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
        com.maddox.il2.objects.air.ME_262B.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
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

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
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
        }
        return super.cutFM(i, j, actor);
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
        }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.isMaster())
        {
            for(int i = 0; i < 2; i++)
                if(curctl[i] == -1F)
                {
                    curctl[i] = oldctl[i] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getControlThrottle();
                } else
                {
                    curctl[i] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getControlThrottle();
                    if((curctl[i] - oldctl[i]) / f > 3F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() < 2400F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getStage() == 6 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(((com.maddox.il2.engine.Actor) (this)), i, 100);
                    if((curctl[i] - oldctl[i]) / f < -3F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getRPM() < 2400F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].getStage() == 6)
                    {
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setEngineStops(((com.maddox.il2.engine.Actor) (this)));
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.75F && (((com.maddox.il2.objects.sounds.SndAircraft)this).FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft)this).FM).isRealMode())
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[i].setKillCompressor(((com.maddox.il2.engine.Actor) (this)));
                    }
                    oldctl[i] = curctl[i];
                }

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
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    private float oldctl[] = {
        -1F, -1F
    };
    private float curctl[] = {
        -1F, -1F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262B.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
