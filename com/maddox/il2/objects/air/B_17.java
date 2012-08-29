// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_17.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
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

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme4, TypeTransport, Aircraft, PaintScheme

public abstract class B_17 extends com.maddox.il2.objects.air.Scheme4
    implements com.maddox.il2.objects.air.TypeTransport
{

    public B_17()
    {
    }

    private static final float getAngleValue(float f, float af[])
    {
        if(f <= 0.0F)
            return af[1];
        if(f >= 1.0F)
            return af[af.length - 1];
        for(int i = 0; (float)i < 0.5F * (float)af.length; i++)
            if(af[i + i] < f && f < af[i + i + 2])
            {
                float f1 = (f - af[i + i]) / (af[i + i + 2] - af[i + i]);
                return af[i + i + 1] + f1 * (af[i + i + 3] - af[i + i + 1]);
            }

        return 0.0F;
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        boolean flag = false;
        int k1 = 0;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1") || s.endsWith("p1"))
                    getEnergyPastArmor(2.4F, shot);
                else
                if(s.endsWith("p9"))
                    getEnergyPastArmor(16.870000839233398D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                if(s.length() == 12)
                    i = 10 + (s.charAt(11) - 48);
                switch(i)
                {
                case 3: // '\003'
                case 4: // '\004'
                case 17: // '\021'
                case 18: // '\022'
                case 19: // '\023'
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F && getEnergyPastArmor(5.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    getEnergyPastArmor(2.0F, shot);
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.001F)
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    break;

                case 7: // '\007'
                case 8: // '\b'
                case 9: // '\t'
                case 10: // '\n'
                    k1 = i - 7;
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, k1, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, k1, 6);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, k1, 7);
                    }
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + k1 + ") Controls: Hit, Engine Controls (Partially) Disabled..");
                    break;

                case 11: // '\013'
                case 12: // '\f'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls Out..");
                    }
                    break;

                case 13: // '\r'
                case 14: // '\016'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Controls Out..");
                    }
                    break;

                case 15: // '\017'
                case 16: // '\020'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                    }
                    break;
                }
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
                if(s.startsWith("xxspark") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxbomb"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.001F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Bomb Payload Detonates..");
                    FM.AS.hitTank(shot.initiator, 0, 10);
                    FM.AS.hitTank(shot.initiator, 1, 10);
                    FM.AS.hitTank(shot.initiator, 2, 10);
                    FM.AS.hitTank(shot.initiator, 3, 10);
                    nextDMGLevels(3, 2, "CF_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
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
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(5) - 49;
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, j);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[j].setReadyness(shot.initiator, FM.EI.engines[j].getReadyness() - 0.00082F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Crank Case Hit - Readyness Reduced to " + FM.EI.engines[j].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[j].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Cylinders Hit, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 18000F)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Module: Magneto #0 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag2"))
                {
                    FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Module: Magneto #1 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("prop") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        FM.EI.engines[j].setKillPropAngleDevice(shot.initiator);
                    else
                        FM.EI.engines[j].setKillPropAngleDeviceSpeeds(shot.initiator);
                    getEnergyPastArmor(15.1F, shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Module: Prop Governor Fails..");
                }
                if(s.endsWith("oil1") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.AS.setOilState(shot.initiator, j, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Module: Oil Filter Pierced..");
                }
                if(s.endsWith("supc") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.EI.engines[j].setKillCompressor(shot.initiator);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + j + ") Module: Compressor Stops..");
                    getEnergyPastArmor(2.6F, shot);
                }
                return;
            }
            if(s.startsWith("xxoiltank"))
            {
                byte byte0 = 1;
                if(s.endsWith("2"))
                    byte0 = 2;
                if(getEnergyPastArmor(0.21F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2435F)
                    FM.AS.hitOil(shot.initiator, byte0);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine (" + byte0 + ") Module: Oil Tank Pierced..");
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.06F, shot) > 0.0F)
                {
                    if(FM.AS.astateTankStates[k] == 0)
                    {
                        FM.AS.hitTank(shot.initiator, k, 1);
                        FM.AS.doSetTankState(shot.initiator, k, 1);
                    }
                    if(shot.powerType == 3)
                    {
                        if(shot.power < 16100F)
                        {
                            if(FM.AS.astateTankStates[k] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.21F)
                                FM.AS.hitTank(shot.initiator, k, 1);
                        } else
                        {
                            FM.AS.hitTank(shot.initiator, k, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                        }
                    } else
                    if(shot.power > 16100F)
                        FM.AS.hitTank(shot.initiator, k, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                }
                return;
            }
            if(s.startsWith("xxactu0"))
            {
                int l = s.charAt(7) - 49;
                if(getEnergyPastArmor(6.3F, shot) > 0.0F && FM.AS.isMaster())
                    FM.turret[l].bIsOperable = false;
                return;
            }
            if(s.startsWith("xxammo"))
            {
                int i1 = 10 * (s.charAt(6) - 48) + (s.charAt(7) - 48);
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    switch(i1)
                    {
                    case 1: // '\001'
                        i1 = 10;
                        k1 = 0;
                        break;

                    case 2: // '\002'
                        i1 = 10;
                        k1 = 1;
                        break;

                    case 3: // '\003'
                        i1 = 11;
                        k1 = 0;
                        break;

                    case 4: // '\004'
                        i1 = 12;
                        k1 = 0;
                        break;

                    case 5: // '\005'
                        i1 = 13;
                        k1 = 0;
                        break;

                    case 6: // '\006'
                        i1 = 13;
                        k1 = 1;
                        break;

                    case 7: // '\007'
                        i1 = 14;
                        k1 = 0;
                        break;

                    case 8: // '\b'
                        i1 = 14;
                        k1 = 1;
                        break;

                    case 9: // '\t'
                        i1 = 15;
                        k1 = 0;
                        break;

                    case 10: // '\n'
                        i1 = 16;
                        k1 = 0;
                        break;

                    case 11: // '\013'
                        i1 = 17;
                        k1 = 0;
                        break;

                    case 12: // '\f'
                        i1 = 17;
                        k1 = 1;
                        break;
                    }
                    FM.AS.setJamBullets(i1, k1);
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
        if(s.startsWith("xkeel"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
            return;
        }
        if(s.startsWith("xrudder"))
        {
            if(chunkDamageVisible("Rudder1") < 2)
                hitChunk("Rudder1", shot);
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
        if(s.startsWith("xvatorl"))
        {
            if(chunkDamageVisible("VatorL") < 2)
                hitChunk("VatorL", shot);
            return;
        }
        if(s.startsWith("xvatorr"))
        {
            if(chunkDamageVisible("VatorR") < 2)
                hitChunk("VatorR", shot);
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
            if(chunkDamageVisible("AroneL") < 2)
                hitChunk("AroneL", shot);
            return;
        }
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 2)
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
        if(s.startsWith("xnose"))
            return;
        if(s.startsWith("xmgun"))
        {
            int j1 = 10 * (s.charAt(5) - 48) + (s.charAt(6) - 48);
            if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.35F)
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
                    j1 = 12;
                    k1 = 0;
                    break;

                case 5: // '\005'
                    j1 = 13;
                    k1 = 0;
                    break;

                case 6: // '\006'
                    j1 = 13;
                    k1 = 1;
                    break;

                case 7: // '\007'
                    j1 = 14;
                    k1 = 0;
                    break;

                case 8: // '\b'
                    j1 = 14;
                    k1 = 1;
                    break;

                case 9: // '\t'
                    j1 = 15;
                    k1 = 0;
                    break;

                case 10: // '\n'
                    j1 = 16;
                    k1 = 0;
                    break;

                case 11: // '\013'
                    j1 = 17;
                    k1 = 0;
                    break;

                case 12: // '\f'
                    j1 = 17;
                    k1 = 1;
                    break;
                }
                FM.AS.setJamBullets(j1, k1);
            }
            return;
        }
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte1 = 0;
            int l1;
            if(s.endsWith("a"))
            {
                byte1 = 1;
                l1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte1 = 2;
                l1 = s.charAt(6) - 49;
            } else
            {
                l1 = s.charAt(5) - 49;
            }
            hitFlesh(l1, shot, byte1);
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
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 27F * f);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL2));
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL3));
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL4));
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 0.0F, -0.85F * f);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 0.0F, 45F * f);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL2));
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL3));
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.B_17.getAngleValue(f, anglesL4));
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 0.0F, -0.85F * f);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 0.0F, 45F * f);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.B_17.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
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
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 85F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85F * f, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateEngineStates[2] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateEngineStates[3] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 3, 1);
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

        case 3: // '\003'
            FM.turret[1].bIsOperable = false;
            FM.turret[2].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[3].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[4].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[6].bIsOperable = false;
            break;

        case 7: // '\007'
            FM.turret[5].bIsOperable = false;
            break;

        case 8: // '\b'
            FM.turret[7].bIsOperable = false;
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final float anglesL2[] = {
        0.0F, 0.0F, 0.2833333F, -19.025F, 0.6666666F, -55.467F, 0.8166666F, -71.339F, 1.0F, -79.5F
    };
    private static final float anglesL3[] = {
        0.0F, 0.0F, 0.3333333F, 2.333F, 0.6666666F, -36.833F, 0.8166666F, -70.944F, 1.0F, -110F
    };
    private static final float anglesL4[] = {
        0.0F, 0.0F, 0.1666667F, 8.5F, 0.5F, 27.5F, 0.8333333F, 49.5F, 1.0F, 61F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B_17.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
