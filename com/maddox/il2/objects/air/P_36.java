// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_36.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, Aircraft, PaintScheme

public abstract class P_36 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public P_36()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.17F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.17F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL21_D0", 0.0F, 94F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR21_D0", 0.0F, -94F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 100F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.P_36.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(!FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_D0", false);
                hierMesh().chunkVisible("Head1_D0", false);
                hierMesh().chunkVisible("HMask1_D0", false);
                hierMesh().chunkVisible("Pilot1_D1", true);
            }
            break;
        }
    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null)
        {
            if(explosion.chunkName.startsWith("WingL"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 3);
            }
            if(explosion.chunkName.startsWith("WingR"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 4);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 5);
            }
        }
        super.msgExplosion(explosion);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(15F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                if(s.endsWith("1"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                } else
                if(s.endsWith("2"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                } else
                if(s.endsWith("3") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                if(s.startsWith("xxengkart") && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                    {
                        FM.AS.setEngineStuck(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 28000F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    }
                    FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                }
                if(s.startsWith("xxengcy") && getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.75F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(FM.AS.astateEngineStates[0] < 1)
                        FM.AS.hitEngine(shot.initiator, 0, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(25F, shot);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int i = s.charAt(6) - 49;
                if(i == 1)
                    i = 2;
                else
                if(i == 2)
                    i = 1;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, i, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, i, 2);
                }
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                byte byte0 = 0;
                int k = s.charAt(6) - 48;
                switch(k)
                {
                case 1: // '\001'
                    byte0 = 2;
                    break;

                case 2: // '\002'
                    byte0 = 1;
                    break;

                case 3: // '\003'
                    byte0 = 3;
                    break;

                case 4: // '\004'
                    byte0 = 0;
                    break;

                case 5: // '\005'
                    byte0 = 4;
                    break;

                case 6: // '\006'
                    byte0 = 5;
                    break;
                }
                FM.AS.setJamBullets(0, byte0);
                getEnergyPastArmor(25.1F, shot);
                return;
            }
            if(s.startsWith("xxammo"))
            {
                int j = s.charAt(6) - 48;
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 2000F) < shot.power)
                    switch(j)
                    {
                    default:
                        break;

                    case 1: // '\001'
                        FM.AS.setJamBullets(0, 1);
                        break;

                    case 2: // '\002'
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            FM.AS.setJamBullets(0, 2);
                        else
                            FM.AS.setJamBullets(0, 3);
                        break;

                    case 3: // '\003'
                        FM.AS.setJamBullets(0, 0);
                        break;

                    case 4: // '\004'
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            FM.AS.setJamBullets(0, 4);
                        else
                            FM.AS.setJamBullets(0, 5);
                        break;
                    }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.endsWith("li1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.endsWith("ri1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.endsWith("lm1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.endsWith("rm1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.endsWith("lo1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(4.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.endsWith("ro1") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(4.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxpar"))
            {
                if((s.endsWith("sl1") || s.endsWith("sl2")) && chunkDamageVisible("StabL") > 1 && getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
                    nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
                }
                if((s.endsWith("sr1") || s.endsWith("sr2")) && chunkDamageVisible("StabR") > 1 && getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
                    nextDMGLevels(1, 2, "StabR_D2", shot.initiator);
                }
                if((s.endsWith("k1") || s.endsWith("k2")) && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                if(s.endsWith("al"))
                {
                    if(getEnergyPastArmor(0.35F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneL Lock Damaged..");
                        nextDMGLevels(1, 2, "AroneL_D0", shot.initiator);
                    }
                } else
                if(s.endsWith("ar"))
                {
                    if(getEnergyPastArmor(0.35F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneR Lock Damaged..");
                        nextDMGLevels(1, 2, "AroneR_D0", shot.initiator);
                    }
                } else
                if(s.endsWith("sl1") || s.endsWith("sl2"))
                {
                    if(getEnergyPastArmor(0.35F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorL Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorL_D0", shot.initiator);
                    }
                } else
                if(s.endsWith("sr1") || s.endsWith("sr2"))
                {
                    if(getEnergyPastArmor(0.35F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorR Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorR_D0", shot.initiator);
                    }
                } else
                if((s.endsWith("k1") || s.endsWith("k2")) && getEnergyPastArmor(0.35F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder1 Lock Damaged..");
                    nextDMGLevels(1, 2, "Rudder1_D0", shot.initiator);
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
            hitChunk("CF", shot);
        if(!s.startsWith("xcockpit"))
            if(s.startsWith("xeng"))
                hitChunk("Engine1", shot);
            else
            if(s.startsWith("xtail"))
                hitChunk("Tail1", shot);
            else
            if(s.startsWith("xkeel"))
                hitChunk("Keel1", shot);
            else
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
            if(s.startsWith("xwing"))
            {
                if(s.startsWith("xwinglin"))
                    hitChunk("WingLIn", shot);
                if(s.startsWith("xwingrin"))
                    hitChunk("WingRIn", shot);
                if(s.startsWith("xwinglmid"))
                    hitChunk("WingLMid", shot);
                if(s.startsWith("xwingrmid"))
                    hitChunk("WingRMid", shot);
                if(s.startsWith("xwinglout"))
                    hitChunk("WingLOut", shot);
                if(s.startsWith("xwingrout"))
                    hitChunk("WingROut", shot);
            } else
            if(s.startsWith("xarone"))
            {
                if(s.startsWith("xaronel"))
                    hitChunk("AroneL", shot);
                if(s.startsWith("xaroner"))
                    hitChunk("AroneR", shot);
            } else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte1 = 0;
                int l;
                if(s.endsWith("a"))
                {
                    byte1 = 1;
                    l = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte1 = 2;
                    l = s.charAt(6) - 49;
                } else
                {
                    l = s.charAt(5) - 49;
                }
                hitFlesh(l, shot, byte1);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 11: // '\013'
        case 33: // '!'
        case 34: // '"'
        case 35: // '#'
        case 36: // '$'
        case 37: // '%'
        case 38: // '&'
            hierMesh().chunkVisible("Wire_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_36.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
