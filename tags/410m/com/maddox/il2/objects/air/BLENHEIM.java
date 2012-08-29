// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BLENHEIM.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeBomber, TypeTransport, PaintScheme

public abstract class BLENHEIM extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeTransport
{

    public BLENHEIM()
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 0.0F, 90F * f);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, 0.0F, -90F * f);
        hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 0.0F, -90F * f);
        hierMesh().chunkSetAngles("Bay4_D0", 0.0F, 0.0F, 90F * f);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hierMesh().chunkVisible("GearL1_D0", false);
            hierMesh().chunkVisible("GearL3_D0", false);
            hierMesh().chunkVisible("GearL4_D0", false);
            hierMesh().chunkVisible("GearL5_D0", false);
            hierMesh().chunkVisible("GearLS1_D0", true);
            hierMesh().chunkVisible("GearLS5_D0", true);
            hierMesh().chunkVisible("GearLS7_D0", true);
            hierMesh().chunkVisible("GearLS8_D0", true);
            hierMesh().chunkVisible("GearLS9_D0", true);
            hierMesh().chunkVisible("GearLS10_D0", true);
            hierMesh().chunkVisible("GearR1_D0", false);
            hierMesh().chunkVisible("GearR3_D0", false);
            hierMesh().chunkVisible("GearR4_D0", false);
            hierMesh().chunkVisible("GearR5_D0", false);
            hierMesh().chunkVisible("GearRS1_D0", true);
            hierMesh().chunkVisible("GearRS5_D0", true);
            hierMesh().chunkVisible("GearRS7_D0", true);
            hierMesh().chunkVisible("GearRS8_D0", true);
            hierMesh().chunkVisible("GearRS9_D0", true);
            hierMesh().chunkVisible("GearRS10_D0", true);
            hierMesh().chunkVisible("GearC1_D0", false);
            hierMesh().chunkVisible("GearCS1_D0", true);
            FM.CT.bHasBrakeControl = false;
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 10F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -150F * f, 0.0F);
        hiermesh.chunkSetAngles("GearLS1_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearLS8_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 10F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -150F * f, 0.0F);
        hiermesh.chunkSetAngles("GearRS1_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearRS8_D0", 0.0F, 80F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.BLENHEIM.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", -f, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[2] = com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.23F);
        hierMesh().chunkSetLocate("GearL99_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearLS7_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 15F), 0.0F);
        hierMesh().chunkSetAngles("GearLS8_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 80F, 57F), 0.0F);
        hierMesh().chunkSetAngles("GearLS9_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, -8F), 0.0F);
        xyz[2] = com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.25F, 0.0F, 0.2F);
        hierMesh().chunkSetLocate("GearLS10_D0", xyz, ypr);
        xyz[2] = com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.23F);
        hierMesh().chunkSetLocate("GearR99_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearRS7_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 15F), 0.0F);
        hierMesh().chunkSetAngles("GearRS8_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 80F, 57F), 0.0F);
        hierMesh().chunkSetAngles("GearRS9_D0", 0.0F, com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, -8F), 0.0F);
        xyz[2] = com.maddox.il2.objects.air.BLENHEIM.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.25F, 0.0F, 0.2F);
        hierMesh().chunkSetLocate("GearRS10_D0", xyz, ypr);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.035F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.035F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.035F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.035F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        for(int i = 1; i < 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            hitProp(0, j, actor);
            return super.cutFM(34, j, actor);

        case 36: // '$'
            hitProp(1, j, actor);
            return super.cutFM(37, j, actor);

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(5F, shot);
                else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(5F, shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor(5F, shot);
                else
                if(s.endsWith("e1") || s.endsWith("e2"))
                    getEnergyPastArmor(5F, shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F || shot.mass > 0.092F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    }
                    // fall through

                case 2: // '\002'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F || shot.mass > 0.092F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                    }
                    // fall through

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 1);
                            com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Evelator Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 2);
                            com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Rudder Controls Out..");
                        }
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Aileron Controls Out..");
                    }
                    break;
                }
            }
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("li1") || s.endsWith("li2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.08F) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.33F) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.33F) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if((s.endsWith("k1") || s.endsWith("k2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < com.maddox.il2.objects.air.BLENHEIM.cvt((float)java.lang.Math.abs(v1.x), 0.0F, 1.0F, 1.0F, 0.26F) && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(8.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                }
            }
            if(s.startsWith("xxstruts"))
            {
                if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(26F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine1 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
                }
                if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(26F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine2 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
                }
            }
            if(s.startsWith("xxbomb") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
            {
                com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Bomb Payload Detonates..");
                FM.AS.hitTank(shot.initiator, 0, 10);
                FM.AS.hitTank(shot.initiator, 1, 10);
                FM.AS.hitTank(shot.initiator, 2, 10);
                FM.AS.hitTank(shot.initiator, 3, 10);
                nextDMGLevels(3, 2, "CF_D0", shot.initiator);
            }
            if(s.startsWith("xxprop"))
            {
                int j = 0;
                if(s.endsWith("2"))
                    j = 1;
                FM.AS.setEngineSpecificDamage(shot.initiator, j, 4);
                com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine" + (j + 1) + " Governor Damaged..");
                if(getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.setEngineSpecificDamage(shot.initiator, j, 3);
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine" + (j + 1) + " Governor Failed..");
                }
            }
            if(s.startsWith("xxengine"))
            {
                int k = 0;
                if(s.startsWith("xxengine2"))
                    k = 1;
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 4.3F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[k].getCylindersRatio() * 1.12F)
                {
                    FM.EI.engines[k].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 9124F)));
                    com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine" + (k + 1) + " Cylindres Damaged, " + FM.EI.engines[k].getCylindersOperable() + "/" + FM.EI.engines[k].getCylinders() + " left..");
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 18000F) < shot.power)
                    FM.AS.hitEngine(shot.initiator, k, 1);
                FM.AS.hitOil(shot.initiator, k);
            }
            if(s.startsWith("xxoil"))
            {
                int l = 0;
                if(s.startsWith("xxoil2"))
                    l = 1;
                FM.AS.hitOil(shot.initiator, l);
            }
            if(s.startsWith("xxtank"))
            {
                int i1 = s.charAt(6) - 49;
                if(i1 < 4 && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(FM.AS.astateTankStates[i1] < 1)
                            FM.AS.hitTank(shot.initiator, i1, 2);
                        if(FM.AS.astateTankStates[i1] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.hitTank(shot.initiator, i1, 1);
                        if(shot.powerType == 3 && FM.AS.astateTankStates[i1] > 2 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                            FM.AS.hitTank(shot.initiator, i1, 10);
                    } else
                    {
                        FM.AS.hitTank(shot.initiator, i1, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 20000F)));
                    }
                if(i1 == 4 && getEnergyPastArmor(1.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.06F)
                {
                    for(int j1 = 0; j1 < 4; j1++)
                        FM.AS.hitOil(shot.initiator, j1);

                }
            }
            if(s.startsWith("xxammo") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
            {
                FM.AS.hitTank(shot.initiator, 2, 10);
                FM.AS.explodeTank(shot.initiator, 2);
            }
            if(s.startsWith("xxmgun1"))
                FM.AS.setJamBullets(0, 0);
            return;
        }
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xnose"))
        {
            hitChunk("Nose1", shot);
            if(shot.power > 33000F)
            {
                FM.AS.hitPilot(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(30, 192));
                FM.AS.hitPilot(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(30, 192));
            }
        } else
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
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
        else
        if(s.startsWith("xwinglin"))
        {
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
        } else
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
        } else
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
        else
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
            FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 96000F));
            com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine1 Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
        } else
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
            FM.EI.engines[1].setReadyness(shot.initiator, FM.EI.engines[1].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 96000F));
            com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Engine2 Hit - Readyness Reduced to " + FM.EI.engines[1].getReadyness() + "..");
        } else
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            {
                com.maddox.il2.objects.air.BLENHEIM.debugprintln(this, "*** Gear Actuator Failed..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
                FM.AS.setJamBullets(10, 0);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int k1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                k1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                k1 = s.charAt(6) - 49;
            } else
            {
                k1 = s.charAt(5) - 49;
            }
            hitFlesh(k1, shot, byte0);
        }
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("HMask2_D0", false);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("HMask3_D0", false);
            break;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BLENHEIM.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
    }
}
