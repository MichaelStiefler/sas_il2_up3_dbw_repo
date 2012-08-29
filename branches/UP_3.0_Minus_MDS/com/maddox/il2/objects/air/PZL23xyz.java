// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   PZL23xyz.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeStormovik, Aircraft, AircraftLH, 
//            PaintScheme

public abstract class PZL23xyz extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public PZL23xyz()
    {
        flapps = 0.0F;
    }

    public void moveWheelSink()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[0], 0.0F, 0.35F, 0.0F, 0.3F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.gWheelSinking[1], 0.0F, 0.35F, 0.0F, 0.3F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = ((com.maddox.il2.objects.air.Aircraft)this).turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -135F)
            {
                f = -135F;
                flag = false;
            }
            if(f > 135F)
            {
                f = 135F;
                flag = false;
            }
            if(f1 < -15F)
            {
                f1 = -15F;
                flag = false;
            }
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -60F)
            {
                f1 = -60F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    protected void moveFlap(float f)
    {
        float f1 = -38F * f;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(f, flag);
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask3_D0", false);
        } else
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot1_D0"));
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot2_D0"));
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask3_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot3_D0"));
        }
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
        case 3: // '\003'
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.turret[0].bIsOperable = false;
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.turret[1].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 3: // '\003'
        default:
            break;

        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            break;

        case 1: // '\001'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
            break;

        case 2: // '\002'
            if(((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot3_D0"))
            {
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot3_D0", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot3_D1", true);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask3_D0", false);
            }
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxcontrols"))
            {
                if(s.endsWith("s1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.2F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Control Column: Hit, Controls Destroyed..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 2);
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                }
                if(s.endsWith("s2") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.2F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                }
                if(s.endsWith("s3") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Elevator Crank: Hit, Controls Destroyed..");
                }
                if((s.endsWith("s4") || s.endsWith("s5")) && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Evelator Controls Out..");
                }
                if((s.endsWith("s6") || s.endsWith("s7") || s.endsWith("s10") || s.endsWith("s11")) && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.35F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Controls Out..");
                }
                if((s.endsWith("s8") || s.endsWith("s9")) && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.75F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Cranks Destroyed..");
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("case"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 140000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Stucks..");
                        }
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 85000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.01F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() - 0.002F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() + "..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.85F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersRatio() * 0.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylinders() + " Left..");
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 48000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(25F, shot);
                }
                if(s.startsWith("xxeng1mag"))
                {
                    int i = s.charAt(9) - 49;
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
                }
                if(s.endsWith("oil1"))
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.25F, shot) > 0.0F)
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Oil Radiator Hit..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(shot.initiator, 0);
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("t1") || s.endsWith("t2") || s.endsWith("t3") || s.endsWith("t4")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Tail1 Spars Broken in Half..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                if((s.endsWith("li1") || s.endsWith("li2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, j, 1);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F || shot.powerType == 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.6F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, j, 2);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "Rudder1_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorL_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorR_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Cowling Gun: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 0);
                }
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 28.33F), shot);
            }
            return;
        }
        if(s.startsWith("xcf"))
        {
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > 0.30499999999999999D && ((com.maddox.JGP.Tuple3d) (point3d)).x < 1.597D)
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).x > 1.202D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x40);
                if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x < -0.80000001192092896D && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.2F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 2);
                if(((com.maddox.JGP.Tuple3d) (point3d)).z > 0.57699999999999996D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 1);
                else
                if(((com.maddox.JGP.Tuple3d) (point3d)).y > 0.0D)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 4);
                else
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 8);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x10);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x20);
            }
        } else
        if(!s.startsWith("xblister"))
            if(s.startsWith("xeng"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Engine1", shot);
            else
            if(s.startsWith("xtail"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", shot);
            } else
            if(s.startsWith("xkeel"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", shot);
            else
            if(s.startsWith("xrudder"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Rudder1") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Rudder1", shot);
            } else
            if(s.startsWith("xstab"))
            {
                if(s.startsWith("xstabl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabL") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
                if(s.startsWith("xstabr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabR") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", shot);
            } else
            if(s.startsWith("xvator"))
            {
                if(s.startsWith("xvatorl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorL") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorL", shot);
                if(s.startsWith("xvatorr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorR") < 2)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorR", shot);
            } else
            if(s.startsWith("xwing"))
            {
                if(s.startsWith("xWingLIn") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", shot);
                if(s.startsWith("xWingRIn") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", shot);
                if(s.startsWith("xWingLMid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", shot);
                if(s.startsWith("xWingRMid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", shot);
                if(s.startsWith("xWingLOut") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", shot);
                if(s.startsWith("xWingROut") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                    ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", shot);
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
                ((com.maddox.il2.objects.air.AircraftLH)this).hitFlesh(k, shot, ((int) (byte0)));
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
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PZL23xyz.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryPoland);
    }
}
