// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_24.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme4, TypeTransport, Aircraft, PaintScheme

public abstract class B_24 extends com.maddox.il2.objects.air.Scheme4
    implements com.maddox.il2.objects.air.TypeTransport
{

    public B_24()
    {
        fCSink = 0.0F;
        fCSteer = 0.0F;
        fGunPos = 1.0F;
        iGunPos = 1;
        btme = -1L;
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
        fCSteer = 22.2F * f;
    }

    protected void moveFlap(float f)
    {
        float f1 = -35.5F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        boolean flag = false;
        int k1 = 0;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxammo"))
            {
                int i = s.charAt(6) - 48;
                if(s.length() > 7)
                    i = 10;
                if(getEnergyPastArmor(6.87F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    switch(i)
                    {
                    case 1: // '\001'
                        i = 10;
                        k1 = 0;
                        break;

                    case 2: // '\002'
                        i = 10;
                        k1 = 1;
                        break;

                    case 3: // '\003'
                        i = 11;
                        k1 = 0;
                        break;

                    case 4: // '\004'
                        i = 11;
                        k1 = 1;
                        break;

                    case 5: // '\005'
                        i = 12;
                        k1 = 0;
                        break;

                    case 6: // '\006'
                        i = 12;
                        k1 = 1;
                        break;

                    case 7: // '\007'
                        i = 13;
                        k1 = 0;
                        break;

                    case 8: // '\b'
                        i = 14;
                        k1 = 0;
                        break;

                    case 9: // '\t'
                        i = 15;
                        k1 = 0;
                        break;

                    case 10: // '\n'
                        i = 15;
                        k1 = 1;
                        break;
                    }
                    FM.AS.setJamBullets(i, k1);
                    return;
                }
            }
            if(s.startsWith("xxcontrols"))
            {
                int j = s.charAt(10) - 48;
                switch(j)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls Out..");
                    }
                    break;

                case 3: // '\003'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F && getEnergyPastArmor(5.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    getEnergyPastArmor(2.0F, shot);
                    break;

                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.252F && getEnergyPastArmor(5.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                            FM.AS.setControlsDamage(shot.initiator, 2);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                            FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    getEnergyPastArmor(2.0F, shot);
                    break;
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int k = s.charAt(5) - 49;
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, k);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                    {
                        FM.EI.engines[k].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[k].setReadyness(shot.initiator, FM.EI.engines[k].getReadyness() - 0.00082F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Crank Case Hit - Readyness Reduced to " + FM.EI.engines[k].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[k].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[k].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Cylinders Hit, " + FM.EI.engines[k].getCylindersOperable() + "/" + FM.EI.engines[k].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 18000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    FM.EI.engines[k].setMagnetoKnockOut(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Module: Magneto #0 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag2"))
                {
                    FM.EI.engines[k].setMagnetoKnockOut(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Module: Magneto #1 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("oil1") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.AS.setOilState(shot.initiator, k, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k + ") Module: Oil Filter Pierced..");
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr1") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockr2") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.startsWith("xxlockal") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                int l = s.charAt(5) - 49;
                if(getEnergyPastArmor(0.21F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2435F)
                    FM.AS.hitOil(shot.initiator, l);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + l + ") Module: Oil Tank Pierced..");
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 1.22F), shot) > 0.0F)
                {
                    debuggunnery("Pneumo System: Disabled..");
                    FM.AS.setInternalDamage(shot.initiator, 1);
                }
                return;
            }
            if(s.startsWith("xxradio"))
            {
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(5F, 25F), shot);
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspark1") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), shot.initiator);
                }
                if(s.startsWith("xxspark2") && chunkDamageVisible("Keel2") > 1 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel2 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel2_D" + chunkDamageVisible("Keel2"), shot.initiator);
                }
                if(s.startsWith("xxsparsl") && chunkDamageVisible("StabL") > 1 && getEnergyPastArmor(11.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** StabL Spars Damaged..");
                    nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
                }
                if(s.startsWith("xxsparsr") && chunkDamageVisible("StabR") > 1 && getEnergyPastArmor(11.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** StabR Spars Damaged..");
                    nextDMGLevels(1, 2, "StabR_D2", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int i1 = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.06F, shot) > 0.0F)
                {
                    if(FM.AS.astateTankStates[i1] == 0)
                    {
                        FM.AS.hitTank(shot.initiator, i1, 1);
                        FM.AS.doSetTankState(shot.initiator, i1, 1);
                    }
                    if(shot.powerType == 3)
                    {
                        if(shot.power < 16100F)
                        {
                            if(FM.AS.astateTankStates[i1] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.21F)
                                FM.AS.hitTank(shot.initiator, i1, 1);
                        } else
                        {
                            FM.AS.hitTank(shot.initiator, i1, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                        }
                    } else
                    if(shot.power > 16100F)
                        FM.AS.hitTank(shot.initiator, i1, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            return;
        }
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
            return;
        }
        if(s.startsWith("xkeel1"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
            return;
        }
        if(s.startsWith("xkeel2"))
        {
            if(chunkDamageVisible("Keel2") < 2)
                hitChunk("Keel2", shot);
            return;
        }
        if(s.startsWith("xrudder1"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
            return;
        }
        if(s.startsWith("xrudder2"))
        {
            if(chunkDamageVisible("Rudder2") < 1)
                hitChunk("Rudder2", shot);
            return;
        }
        if(s.startsWith("xstabl"))
        {
            if(chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
            return;
        }
        if(s.startsWith("xstabr"))
        {
            if(chunkDamageVisible("StabR") < 2)
                hitChunk("StabR", shot);
            return;
        }
        if(s.startsWith("xwinglin"))
        {
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            return;
        }
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            return;
        }
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            return;
        }
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
            return;
        }
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            return;
        }
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
            return;
        }
        if(s.startsWith("xaronel"))
        {
            if(chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
            return;
        }
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 1)
                hitChunk("AroneR", shot);
            return;
        }
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
            return;
        }
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
            return;
        }
        if(s.startsWith("xengine3"))
        {
            if(chunkDamageVisible("Engine3") < 2)
                hitChunk("Engine3", shot);
            return;
        }
        if(s.startsWith("xengine4"))
        {
            if(chunkDamageVisible("Engine4") < 2)
                hitChunk("Engine4", shot);
            return;
        }
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Gear Hydro Failed..");
                FM.Gears.setHydroOperable(false);
            }
            return;
        }
        if(s.startsWith("xturret"))
            return;
        if(s.startsWith("xmgun"))
        {
            int j1 = 10 * (s.charAt(5) - 48) + (s.charAt(6) - 48);
            if(getEnergyPastArmor(6.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.35F)
            {
                switch(j1)
                {
                case 1: // '\001'
                    j1 = 10;
                    k1 = 0;
                    break;

                case 2: // '\002'
                    j1 = 10;
                    k1 = 1;
                    break;

                case 3: // '\003'
                    j1 = 11;
                    k1 = 0;
                    break;

                case 4: // '\004'
                    j1 = 11;
                    k1 = 1;
                    break;

                case 5: // '\005'
                    j1 = 12;
                    k1 = 0;
                    break;

                case 6: // '\006'
                    j1 = 12;
                    k1 = 1;
                    break;

                case 7: // '\007'
                    j1 = 13;
                    k1 = 0;
                    break;

                case 8: // '\b'
                    j1 = 14;
                    k1 = 0;
                    break;

                case 9: // '\t'
                    j1 = 15;
                    k1 = 0;
                    break;

                case 10: // '\n'
                    j1 = 15;
                    k1 = 1;
                    break;
                }
                FM.AS.setJamBullets(j1, k1);
            }
            return;
        }
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l1 = s.charAt(6) - 49;
            } else
            {
                l1 = s.charAt(5) - 49;
            }
            hitFlesh(l1, shot, byte0);
            return;
        } else
        {
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null && explosion.power > 0.0F)
        {
            if(explosion.chunkName.equals("Tail1_D3"))
                return;
            if(explosion.chunkName.equals("WingLIn_D3"))
                return;
            if(explosion.chunkName.equals("WingRIn_D3"))
                return;
            if(explosion.chunkName.equals("WingLMid_D3"))
                return;
            if(explosion.chunkName.equals("WingRMid_D3"))
                return;
            if(explosion.chunkName.equals("WingLOut_D3"))
                return;
            if(explosion.chunkName.equals("WingROut_D3"))
                return;
        }
        super.msgExplosion(explosion);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -55F), 0.0F);
        hiermesh.chunkSetAngles("GearC10_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, -140F), 0.0F);
        hiermesh.chunkSetAngles("GearC9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, -140F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -30F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, 180F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -98F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -20F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -30F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, 180F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -98F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -20F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.B_24.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        fCSink = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.5F, 0.0F, 0.5F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.456F, 0.0F, 0.2821F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.456F, 0.0F, 0.2821F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            hitProp(1, j, actor);
            FM.EI.engines[1].setEngineStuck(actor);
            FM.AS.hitTank(actor, 1, com.maddox.il2.ai.World.Rnd().nextInt(0, 9));
            // fall through

        case 34: // '"'
            hitProp(0, j, actor);
            FM.EI.engines[0].setEngineStuck(actor);
            FM.AS.hitTank(actor, 0, com.maddox.il2.ai.World.Rnd().nextInt(2, 8));
            FM.AS.hitTank(actor, 1, com.maddox.il2.ai.World.Rnd().nextInt(0, 5));
            // fall through

        case 35: // '#'
            FM.AS.hitTank(actor, 0, com.maddox.il2.ai.World.Rnd().nextInt(0, 4));
            break;

        case 36: // '$'
            hitProp(2, j, actor);
            FM.EI.engines[2].setEngineStuck(actor);
            FM.AS.hitTank(actor, 2, com.maddox.il2.ai.World.Rnd().nextInt(0, 9));
            // fall through

        case 37: // '%'
            hitProp(3, j, actor);
            FM.EI.engines[3].setEngineStuck(actor);
            FM.AS.hitTank(actor, 2, com.maddox.il2.ai.World.Rnd().nextInt(0, 5));
            FM.AS.hitTank(actor, 3, com.maddox.il2.ai.World.Rnd().nextInt(2, 8));
            // fall through

        case 38: // '&'
            FM.AS.hitTank(actor, 3, com.maddox.il2.ai.World.Rnd().nextInt(0, 4));
            break;

        case 25: // '\031'
            FM.turret[0].bIsOperable = false;
            return false;

        case 26: // '\032'
            FM.turret[1].bIsOperable = false;
            return false;

        case 27: // '\033'
            FM.turret[2].bIsOperable = false;
            return false;

        case 28: // '\034'
            FM.turret[3].bIsOperable = false;
            return false;

        case 29: // '\035'
            FM.turret[4].bIsOperable = false;
            return false;

        case 30: // '\036'
            FM.turret[5].bIsOperable = false;
            return false;

        case 19: // '\023'
            killPilot(this, 5);
            killPilot(this, 6);
            killPilot(this, 7);
            killPilot(this, 8);
            cut("StabL");
            cut("StabR");
            break;

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            killPilot(this, 2);
            killPilot(this, 3);
            break;

        case 17: // '\021'
            cut("Keel1");
            hierMesh().chunkVisible("Keel1_CAP", false);
            break;

        case 18: // '\022'
            cut("Keel2");
            hierMesh().chunkVisible("Keel2_CAP", false);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveBayDoor(float f)
    {
        for(int i = 1; i < 10; i++)
            hierMesh().chunkSetAngles("Bay0" + i + "_D0", 0.0F, -30F * f, 0.0F);

        for(int j = 10; j < 13; j++)
            hierMesh().chunkSetAngles("Bay" + j + "_D0", 0.0F, -30F * f, 0.0F);

    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
            if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
            {
                for(int i = 0; i < FM.EI.getNum(); i++)
                    if(FM.AS.astateEngineStates[i] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                        FM.EI.engines[i].setExtinguisherFire();

            }
        }
        for(int j = 1; j < 10; j++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + j + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + j + "_D0", hierMesh().isChunkVisible("Pilot" + j + "_D0"));

    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[1].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[2].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[3].bIsOperable = false;
            break;

        case 7: // '\007'
            FM.turret[4].bIsOperable = false;
            break;

        case 8: // '\b'
            FM.turret[5].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        hierMesh().chunkVisible("Pilot" + (i + 1) + "_D0", false);
        hierMesh().chunkVisible("HMask" + (i + 1) + "_D0", false);
        hierMesh().chunkVisible("Pilot" + (i + 1) + "_D1", true);
        hierMesh().chunkVisible("Head" + (i + 1) + "_D0", false);
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 4; i++)
        {
            float f1 = FM.EI.engines[i].getControlRadiator();
            if(java.lang.Math.abs(flapps[i] - f1) > 0.01F)
            {
                flapps[i] = f1;
                for(int j = 1; j < 9; j++)
                    hierMesh().chunkSetAngles("Water" + (i * 8 + j) + "_D0", 0.0F, -20F * f1, 0.0F);

            }
        }

        if(FM.CT.getGear() > 0.9F)
        {
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[1] = fCSink;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = fCSteer;
            hierMesh().chunkSetLocate("GearC3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        }
        if(iGunPos == 0)
        {
            if(fGunPos > 0.0F)
            {
                fGunPos -= 0.2F * f;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.635F + 0.635F * fGunPos;
                hierMesh().chunkSetLocate("Turret3C_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }
        } else
        if(iGunPos == 1 && fGunPos < 1.0F)
        {
            fGunPos += 0.2F * f;
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.635F + 0.635F * fGunPos;
            hierMesh().chunkSetLocate("Turret3C_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            if(fGunPos > 0.8F)
                if(fGunPos >= 0.9F);
        }
        if(FM.AS.isMaster())
        {
            if(FM.turret[2].target != null && FM.AS.astatePilotStates[5] < 90)
                iGunPos = 1;
            if(com.maddox.rts.Time.current() > btme)
            {
                btme = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(5000L, 12000L);
                if(FM.turret[2].target == null)
                    iGunPos = 0;
            }
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float fCSink;
    private float fCSteer;
    private float flapps[] = {
        0.0F, 0.0F, 0.0F, 0.0F
    };
    private float fGunPos;
    private int iGunPos;
    private long btme;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B_24.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
