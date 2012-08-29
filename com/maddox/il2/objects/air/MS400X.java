// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MS400X.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
//            Scheme1, TypeFighter, PaintScheme

public abstract class MS400X extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public MS400X()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, -87F * f);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -34F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 180F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 87F * f);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -34F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 180F * f, 0.0F);
        hiermesh.chunkSetAngles("Antenna_D0", 0.0F, -90F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.MS400X.moveGear(hierMesh(), f);
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

    protected void moveFlap(float f)
    {
        float f1 = -60F * f;
        hierMesh().chunkSetAngles("FlapL_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("FlapR_D0", 0.0F, f1, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(14.2F / (1E-005F + (float)java.lang.Math.abs(v1.x)), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                if(s.endsWith("1"))
                {
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                } else
                if(s.endsWith("2") || s.endsWith("3"))
                {
                    if(getEnergyPastArmor(0.002F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                    }
                } else
                if((s.endsWith("4") || s.endsWith("5")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.MS400X.debugprintln(this, "*** Ailerones Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("prop") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.4F), shot) > 0.0F)
                {
                    debuggunnery("Engine Module: Prop Governor Failed..");
                    FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
                }
                if(s.endsWith("gear") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.1F), shot) > 0.0F)
                {
                    debuggunnery("Engine Module: Prop Governor Damaged..");
                    FM.EI.engines[0].setKillPropAngleDeviceSpeeds(shot.initiator);
                }
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6.8F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit - Engine Stucks..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit - Engine Damaged..");
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 28000F)
                        {
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                            debuggunnery("Engine Module: Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
                            FM.EI.engines[0].setEngineStuck(shot.initiator);
                        }
                        debuggunnery("Engine Module: Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        debuggunnery("Engine Module: Crank Case Hit - Engine Stalled..");
                        FM.EI.engines[0].setEngineStops(shot.initiator);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        debuggunnery("Engine Module: Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
                        FM.AS.hitEngine(shot.initiator, 0, 10);
                    }
                    getEnergyPastArmor(16F, shot);
                }
                if((s.endsWith("cyl1") || s.endsWith("cyl2")) && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.542F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.72F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        debuggunnery("Engine Module: Cylinder Case Broken - Engine Stuck..");
                        FM.EI.engines[0].setEngineStuck(shot.initiator);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3F, 46.7F), shot);
                }
                if(s.endsWith("supc") && getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    debuggunnery("Engine Module: Supercharger Out..");
                    FM.EI.engines[0].setKillCompressor(shot.initiator);
                }
                if(s.endsWith("eqpt") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.001F, 0.2F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                {
                    debuggunnery("Engine Module: Compressor Feed Out..");
                    FM.EI.engines[0].setKillCompressor(shot.initiator);
                }
                if(s.startsWith("xxeng1mag"))
                {
                    int i = s.charAt(9) - 49;
                    debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
                }
                if(s.endsWith("oil1") && getEnergyPastArmor(1.27F, shot) > 0.0F)
                {
                    debuggunnery("Engine Module: Oil Radiator Hit..");
                    FM.AS.hitOil(shot.initiator, 0);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, j, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, j, 2);
                }
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setJamBullets(1, 0);
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                int k = s.charAt(7) - 49;
                FM.AS.setJamBullets(0, k);
                return;
            }
            if(s.startsWith("xxammo"))
            {
                int l = s.charAt(7) - 48;
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 20000F) < shot.power)
                    switch(l)
                    {
                    case 1: // '\001'
                        FM.AS.setJamBullets(0, 0);
                        break;

                    case 2: // '\002'
                        FM.AS.setJamBullets(0, 1);
                        break;
                    }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("Rudder Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.endsWith("al") && getEnergyPastArmor(0.35F, shot) > 0.0F)
                {
                    debuggunnery("AroneL Lock Damaged..");
                    nextDMGLevels(1, 2, "AroneL_D0", shot.initiator);
                }
                if(s.endsWith("ar") && getEnergyPastArmor(0.35F, shot) > 0.0F)
                {
                    debuggunnery("AroneR Lock Damaged..");
                    nextDMGLevels(1, 2, "AroneR_D0", shot.initiator);
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
            {
                if(chunkDamageVisible("Tail1") < 3)
                    hitChunk("Tail1", shot);
            } else
            if(s.startsWith("xkeel"))
                hitChunk("Keel1", shot);
            else
            if(s.startsWith("xrudder"))
            {
                if(chunkDamageVisible("Rudder1") < 1)
                    hitChunk("Rudder1", shot);
            } else
            if(s.startsWith("xstabl"))
                hitChunk("StabL", shot);
            else
            if(s.startsWith("xstabr"))
                hitChunk("StabR", shot);
            else
            if(s.startsWith("xvator"))
            {
                if(s.startsWith("xvatorl") && chunkDamageVisible("VatorL") < 1)
                    hitChunk("VatorL", shot);
                if(s.startsWith("xvatorr") && chunkDamageVisible("VatorR") < 1)
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
                if(s.startsWith("xaronel") && chunkDamageVisible("AroneL") < 1)
                    hitChunk("AroneL", shot);
                if(s.startsWith("xaroner") && chunkDamageVisible("AroneL") < 1)
                    hitChunk("AroneR", shot);
            } else
            if(s.startsWith("xgear"))
            {
                if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    debuggunnery("Hydro System: Disabled..");
                    FM.AS.setInternalDamage(shot.initiator, 0);
                }
                if((s.endsWith("2a") || s.endsWith("2b")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
                {
                    debuggunnery("Undercarriage: Stuck..");
                    FM.AS.setInternalDamage(shot.initiator, 3);
                }
            } else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte0 = 0;
                int i1;
                if(s.endsWith("a"))
                {
                    byte0 = 1;
                    i1 = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte0 = 2;
                    i1 = s.charAt(6) - 49;
                } else
                {
                    i1 = s.charAt(5) - 49;
                }
                hitFlesh(i1, shot, byte0);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 9: // '\t'
        case 33: // '!'
            hierMesh().chunkVisible("GearL3_D0", false);
            break;

        case 10: // '\n'
        case 36: // '$'
            hierMesh().chunkVisible("GearR3_D0", false);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.MS400X.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryFrance);
    }
}
