// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TB_3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme4, TypeTransport, TypeBomber, Aircraft, 
//            PaintScheme

public abstract class TB_3 extends com.maddox.il2.objects.air.Scheme4
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeBomber
{

    public TB_3()
    {
        bDynamoOperational = true;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    protected void moveFan(float f)
    {
        if(bDynamoOperational)
        {
            pk = java.lang.Math.abs((int)(FM.Vwld.length() / 14D));
            if(pk >= 1)
                pk = 1;
        }
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            hierMesh().chunkVisible("Gener_D0", !bDynamoRotary);
            hierMesh().chunkVisible("Generrot_D0", bDynamoRotary);
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - FM.Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("Gener_D0", 0.0F, dynamoOrient, 0.0F);
        super.moveFan(f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 0.5F);
        hierMesh().chunkSetAngles("GearL2_D0", 0.0F, 25F * f, 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.21F * f;
        hierMesh().chunkSetLocate("GearL6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.69F * f;
        hierMesh().chunkSetLocate("GearL7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.87F * f;
        hierMesh().chunkSetLocate("GearL8_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
        hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -25F * f, 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.21F * f;
        hierMesh().chunkSetLocate("GearR6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.69F * f;
        hierMesh().chunkSetLocate("GearR7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.87F * f;
        hierMesh().chunkSetLocate("GearR8_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = -FM.Or.getTangage() + 10.42765F;
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
                if(s.endsWith("p1"))
                    getEnergyPastArmor(0.78F, shot);
                else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(0.78F, shot);
                else
                if(s.endsWith("g1") || s.endsWith("g2"))
                    getEnergyPastArmor(2.0F * com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.21F), shot);
            if(s.startsWith("xxcontrols"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Evelator Controls Out..");
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                }
            }
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("t1") || s.endsWith("t2") || s.endsWith("t3") || s.endsWith("t4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(12.9F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
                    msgCollision(this, "Tail1_D0", "Tail1_D0");
                }
                if((s.endsWith("li1") || s.endsWith("li2") || s.endsWith("li3") || s.endsWith("li4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2") || s.endsWith("ri3") || s.endsWith("ri4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2") || s.endsWith("lm3") || s.endsWith("lm4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2") || s.endsWith("rm3") || s.endsWith("rm4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2") || s.endsWith("lo3") || s.endsWith("lo4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2") || s.endsWith("ro3") || s.endsWith("ro4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.endsWith("e1") && getEnergyPastArmor(28F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine1 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
                }
                if(s.endsWith("e2") && getEnergyPastArmor(28F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine2 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
                }
                if(s.endsWith("e3") && getEnergyPastArmor(28F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine3 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine3_D0", shot.initiator);
                }
                if(s.endsWith("e4") && getEnergyPastArmor(28F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine4 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine4_D0", shot.initiator);
                }
            }
            if(s.startsWith("xxbomb") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Bomb Payload Detonates..");
                FM.AS.hitTank(shot.initiator, 0, 10);
                FM.AS.hitTank(shot.initiator, 1, 10);
                FM.AS.hitTank(shot.initiator, 2, 10);
                FM.AS.hitTank(shot.initiator, 3, 10);
                msgCollision(this, "CF_D0", "CF_D0");
            }
            if(s.startsWith("xxeng"))
            {
                int i = s.charAt(5) - 49;
                if(s.endsWith("base"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, i);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, i, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 14000F)
                        {
                            FM.EI.engines[i].setCyliderKnockOut(shot.initiator, 1);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[i].getCylindersOperable() + "/" + FM.EI.engines[i].getCylinders() + " Left..");
                        }
                        FM.EI.engines[i].setReadyness(shot.initiator, FM.EI.engines[i].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Crank Case Hit - Readyness Reduced to " + FM.EI.engines[i].getReadyness() + "..");
                    }
                } else
                if(s.endsWith("cyl"))
                {
                    if(getEnergyPastArmor(0.5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[i].getCylindersRatio())
                    {
                        FM.EI.engines[i].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit, " + FM.EI.engines[i].getCylindersOperable() + "/" + FM.EI.engines[i].getCylinders() + " Left..");
                        if(FM.AS.astateEngineStates[i] < 1)
                            FM.AS.hitEngine(shot.initiator, i, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            FM.AS.hitEngine(shot.initiator, i, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
                        }
                        getEnergyPastArmor(25F, shot);
                    }
                } else
                if(s.endsWith("wat"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    {
                        FM.EI.engines[i].setMagnetoKnockOut(shot.initiator, 0);
                        FM.EI.engines[i].setMagnetoKnockOut(shot.initiator, 1);
                    }
                    FM.AS.hitOil(shot.initiator, i);
                }
            }
            if(s.startsWith("xxoil"))
            {
                int j = s.charAt(5) - 49;
                if(getEnergyPastArmor(0.023F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, j);
                    getEnergyPastArmor(0.12F, shot);
                }
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                k /= 2;
                if(getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, k, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, k, 2);
                }
            }
            return;
        }
        if(s.startsWith("xcf"))
        {
            hitChunk("CF", shot);
            if(point3d.x > 1.0D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.80000001192092896D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.89999997615814209D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) < 0.80000001192092896D)
                    if(point3d.y > 0.0D)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                    } else
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                    }
            }
        }
        if(s.startsWith("xtail") && chunkDamageVisible("Tail1") < 3)
            hitChunk("Tail1", shot);
        if(s.startsWith("xkeel") && chunkDamageVisible("Keel1") < 3)
            hitChunk("Keel1", shot);
        if(s.startsWith("xrudder") && chunkDamageVisible("Rudder1") < 1)
            hitChunk("Rudder1", shot);
        if(s.startsWith("xstabl") && chunkDamageVisible("StabL") < 3)
            hitChunk("StabL", shot);
        if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 3)
            hitChunk("StabR", shot);
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
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
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
        if(s.startsWith("xgearl"))
            hitChunk("GearL2", shot);
        if(s.startsWith("xgearr"))
            hitChunk("GearR2", shot);
        if(s.startsWith("xengine1") && chunkDamageVisible("Engine1") < 3)
            hitChunk("Engine1", shot);
        if(s.startsWith("xengine2") && chunkDamageVisible("Engine2") < 3)
            hitChunk("Engine2", shot);
        if(s.startsWith("xengine3") && chunkDamageVisible("Engine3") < 3)
            hitChunk("Engine3", shot);
        if(s.startsWith("xengine4") && chunkDamageVisible("Engine4") < 3)
            hitChunk("Engine4", shot);
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
            {
                FM.AS.setJamBullets(10, 0);
                FM.AS.setJamBullets(10, 1);
            }
            if(s.startsWith("xturret2"))
            {
                FM.AS.setJamBullets(11, 0);
                FM.AS.setJamBullets(11, 1);
            }
            if(s.startsWith("xturret3"))
            {
                FM.AS.setJamBullets(11, 0);
                FM.AS.setJamBullets(11, 1);
            }
        }
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l = s.charAt(6) - 49;
            } else
            {
                l = s.charAt(5) - 49;
            }
            hitFlesh(l, shot, byte0);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            hitProp(0, j, actor);
            hitProp(1, j, actor);
            break;

        case 36: // '$'
            hitProp(2, j, actor);
            hitProp(3, j, actor);
            break;

        case 19: // '\023'
            killPilot(this, 5);
            killPilot(this, 6);
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveFlap(float f)
    {
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[1].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[2].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        if(i != 3 && i != 4 && i != 7)
            hierMesh().chunkSetAngles("Pilot" + (i + 1) + "_D0", 0.0F, 10F, -25F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && (!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode()))
        {
            for(int i = 0; i < FM.EI.getNum(); i++)
                if(FM.AS.astateEngineStates[i] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.EI.engines[i].setExtinguisherFire();

        }
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay3_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -90F * f, 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bDynamoOperational;
    private float dynamoOrient;
    private boolean bDynamoRotary;
    private int pk;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.TB_3.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }
}
