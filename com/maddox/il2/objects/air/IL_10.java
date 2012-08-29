// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_10.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeBMPar05, PaintSchemeFMPar03, TypeStormovikArmored, 
//            NetAircraft, PaintScheme

public class IL_10 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeStormovikArmored
{

    public IL_10()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        com.maddox.il2.ai.World.cur();
        if(this == com.maddox.il2.ai.World.getPlayerAircraft() && FM.turret.length > 0 && FM.AS.astatePilotStates[1] < 90 && FM.turret[0].bIsAIControlled && (FM.getOverload() > 7F || FM.getOverload() < -0.7F))
            com.maddox.il2.objects.sounds.Voice.speakRearGunShake();
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        hierMesh().chunkSetAngles("radiator1_D0", 0.0F, -23F * kangle, 0.0F);
        hierMesh().chunkSetAngles("radiator2_D0", 0.0F, -70F * kangle, 0.0F);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.47F, 0.0F, -45F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.6F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.6F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.6F, 0.0F, -69F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.11F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.01F, 0.11F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.44F, 0.99F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.44F, 0.99F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.44F, 0.99F, 0.0F, -69F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.44F, 0.54F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.IL_10.cvt(f, 0.44F, 0.54F, 0.0F, -70F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.IL_10.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        if(FM.CT.getGear() < 0.99F)
        {
            return;
        } else
        {
            resetYPRmodifier();
            ypr[1] = -90F;
            xyz[1] = com.maddox.il2.objects.air.IL_10.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.228F, 0.0F, -0.228F);
            hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
            xyz[1] = com.maddox.il2.objects.air.IL_10.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.228F, 0.0F, 0.228F);
            hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
            return;
        }
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        if(af[0] < -43F)
        {
            af[0] = -43F;
            flag = false;
        } else
        if(af[0] > 43F)
        {
            af[0] = 43F;
            flag = false;
        }
        if(af[1] < -2F)
        {
            af[1] = -2F;
            flag = false;
        }
        if(af[1] > 56F)
        {
            af[1] = 56F;
            flag = false;
        }
        return flag;
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            if(FM.turret.length == 0)
                return;
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
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        int i = 0;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.startsWith("xxarmorp"))
                {
                    i = s.charAt(8) - 48;
                    switch(i)
                    {
                    default:
                        break;

                    case 1: // '\001'
                        getEnergyPastArmor(12.880000114440918D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                        if(shot.power <= 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.23F)
                            doRicochet(shot);
                        break;

                    case 2: // '\002'
                    case 5: // '\005'
                    case 7: // '\007'
                    case 8: // '\b'
                        getEnergyPastArmor(8D / (java.lang.Math.abs(v1.y) + 9.9999997473787516E-005D), shot);
                        shot.powerType = 0;
                        if(shot.power <= 0.0F && java.lang.Math.abs(v1.x) > 0.86599999666213989D)
                            doRicochet(shot);
                        break;

                    case 3: // '\003'
                        getEnergyPastArmor(16D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                        break;

                    case 4: // '\004'
                        getEnergyPastArmor(20.200000762939453D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                        if(shot.power <= 0.0F)
                            doRicochetBack(shot);
                        break;

                    case 6: // '\006'
                        getEnergyPastArmor(8D / (java.lang.Math.abs(v1.z) + 9.9999997473787516E-005D), shot);
                        shot.powerType = 0;
                        if(shot.power <= 0.0F && java.lang.Math.abs(v1.z) < 0.43999999761581421D)
                            doRicochet(shot);
                        break;
                    }
                }
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                i = s.charAt(10) - 48;
                if(s.endsWith("10"))
                    i = 10;
                switch(i)
                {
                default:
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(0.25F / ((float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z) + 0.0001F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        {
                            debuggunnery("Controls: Elevator Wiring Hit, Elevator Controls Disabled..");
                            FM.AS.setControlsDamage(shot.initiator, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                        {
                            debuggunnery("Controls: Rudder Wiring Hit, Rudder Controls Disabled..");
                            FM.AS.setControlsDamage(shot.initiator, 2);
                        }
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(4D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;
                }
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.startsWith("xxlockal") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
            }
            if(s.startsWith("xxeng"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("prop"))
                {
                    if(getEnergyPastArmor(3.6F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            debuggunnery("Engine Module: Prop Governor Hit, Disabled..");
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                        } else
                        {
                            debuggunnery("Engine Module: Prop Governor Hit, Oil Pipes Damaged..");
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                        }
                } else
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Engine Module: Reductor Hit, Bullet Jams Reductor Gear..");
                        FM.EI.engines[0].setEngineStuck(shot.initiator);
                    }
                } else
                if(s.endsWith("feed"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
                            FM.EI.engines[0].setEngineStops(shot.initiator);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        {
                            debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 6);
                        }
                    }
                } else
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit, Bullet Jams Ball Bearings..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                        }
                    }
                    FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    debuggunnery("Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    getEnergyPastArmor(22.5F, shot);
                } else
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(1.3F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.75F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        debuggunnery("Engine Module: Cylinders Assembly Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Operating..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            debuggunnery("Engine Module: Cylinders Assembly Hit, Engine Fires..");
                            FM.AS.hitEngine(shot.initiator, 0, 3);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            debuggunnery("Engine Module: Cylinders Assembly Hit, Bullet Jams Piston Head..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        getEnergyPastArmor(22.5F, shot);
                    }
                    if(java.lang.Math.abs(point3d.y) < 0.1379999965429306D && getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            debuggunnery("Engine Module: Feed Lines Hit, Engine Stalled..");
                            FM.EI.engines[0].setEngineStops(shot.initiator);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        {
                            debuggunnery("Engine Module: Feed Gear Hit, Engine Jams..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            debuggunnery("Engine Module: Feed Gear Hit, Half Cylinder Feed Cut-Out..");
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 6);
                        }
                    }
                } else
                if(s.startsWith("xxeng1oil") && getEnergyPastArmor(0.5F, shot) > 0.0F)
                {
                    debuggunnery("Engine Module: Oil Radiator Hit, Oil Radiator Pierced..");
                    FM.AS.hitOil(shot.initiator, 0);
                }
            }
            if(s.startsWith("xxOil") && getEnergyPastArmor(3.5F, shot) > 0.0F)
            {
                debuggunnery("Engine Module: Oil Tank Hit..");
                FM.AS.hitOil(shot.initiator, 0);
            }
            if(s.startsWith("xxtank") && getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
            {
                if(FM.AS.astateTankStates[0] == 0)
                {
                    debuggunnery("Fuel System: Fuel Tank Pierced..");
                    FM.AS.hitTank(shot.initiator, 0, 1);
                    FM.AS.doSetTankState(shot.initiator, 0, 1);
                } else
                if(FM.AS.astateTankStates[i] == 1)
                {
                    debuggunnery("Fuel System: Fuel Tank Pierced..");
                    FM.AS.hitTank(shot.initiator, 0, 1);
                    FM.AS.doSetTankState(shot.initiator, 0, 2);
                }
                if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.AS.hitTank(shot.initiator, 0, 2);
                    debuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
                }
            }
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Armament System: Left Machine Gun: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Armament System: Right Machine Gun: Disabled..");
                    FM.AS.setJamBullets(0, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.3F, 12.6F), shot);
            }
            if(s.startsWith("xxcannon"))
            {
                if(s.endsWith("01") && getEnergyPastArmor(0.25F, shot) > 0.0F)
                {
                    debuggunnery("Armament System: Left Cannon: Disabled..");
                    FM.AS.setJamBullets(1, 0);
                }
                if(s.endsWith("02") && getEnergyPastArmor(0.25F, shot) > 0.0F)
                {
                    debuggunnery("Armament System: Right Cannon: Disabled..");
                    FM.AS.setJamBullets(1, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
            }
            if(s.startsWith("xxbomb"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.00345F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
                {
                    debuggunnery("Armament System: Bomb Payload Detonated..");
                    FM.AS.hitTank(shot.initiator, 0, 10);
                    FM.AS.hitTank(shot.initiator, 1, 10);
                    nextDMGLevels(3, 2, "CF_D0", shot.initiator);
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcockpit"))
        {
            if(point3d.z > 0.77500000000000002D)
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(v1.x < -0.90000000000000002D && getEnergyPastArmor(12D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
            } else
            if(point3d.y > 0.0D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
            else
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.067F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.067F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.067F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
        }
        if(s.startsWith("xcf"))
        {
            if(point3d.z < 0.67200000000000004D)
            {
                getEnergyPastArmor(6D / (java.lang.Math.abs(java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z)) + 9.9999997473787516E-005D), shot);
                if(shot.power <= 0.0F && java.lang.Math.abs(v1.x) > 0.86599999666213989D)
                    doRicochet(shot);
            }
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xeng"))
        {
            if(point3d.z > 0.54900000000000004D)
                getEnergyPastArmor(2D / (java.lang.Math.abs(v1.z) + 9.9999997473787516E-005D), shot);
            else
            if(point3d.x > 2.819D)
                getEnergyPastArmor(6D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
            else
                getEnergyPastArmor(4D / (java.lang.Math.abs(java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z)) + 9.9999997473787516E-005D), shot);
            if(java.lang.Math.abs(v1.x) > 0.86599999666213989D && (shot.power <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F))
                doRicochet(shot);
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
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
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl"))
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr"))
                hitChunk("StabR", shot);
        } else
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
            if(s.startsWith("xaroner") && chunkDamageVisible("AroneR") < 1)
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
        if(s.startsWith("xturret"))
        {
            if(getEnergyPastArmor(0.25F, shot) > 0.0F)
            {
                debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
                FM.AS.setJamBullets(10, 0);
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 26.35F), shot);
            }
        } else
        if(s.startsWith("xhelm"))
        {
            getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(2.0F, 3.56F), shot);
            if(shot.power <= 0.0F)
                doRicochetBack(shot);
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
            hitFlesh(j, shot, byte0);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
        {
            if(FM.getAltitude() < 3000F)
            {
                if(hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1)
                    hierMesh().chunkVisible("HMask" + i + "_D0", false);
                continue;
            }
            if(hierMesh().chunkFindCheck("HMask" + i + "_D0") != -1)
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Il-10");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Il-10(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/Il-10(ru)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_pl", "3DO/Plane/Il-10(ru)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_pl", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-10.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitIL_10.class, com.maddox.il2.objects.air.CockpitIL_10_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.93155F);
        com.maddox.il2.objects.air.IL_10.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 10, 2, 2, 2, 2, 9, 
            9, 9, 9, 3, 3, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.IL_10.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev03", 
            "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xRS82", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xBRS82", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xRS132", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xM13", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "176xAJ-2", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, null, null, "BombGunAmpoule 88", "BombGunAmpoule 88", "PylonKMB", "PylonKMB"
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "144xPTAB2_5", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, null, null, "BombGunPTAB25 72", "BombGunPTAB25 72", "PylonKMB", "PylonKMB"
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "12xAO10", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, null, null, "BombGunAO10 6", "BombGunAO10 6", "PylonKMB", "PylonKMB"
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xFAB50_4xRS82", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "2xFAB1004BRS82", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB100", "BombGunFAB100", null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "2xFAB1004BRS132", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "BombGunFAB100", "BombGunFAB100", null, null, null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "4xFAB100", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunVYak 150", "MGunVYak 150", "MGunUBt 150", null, null, null, null, null, 
            null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null
        });
        com.maddox.il2.objects.air.IL_10.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
