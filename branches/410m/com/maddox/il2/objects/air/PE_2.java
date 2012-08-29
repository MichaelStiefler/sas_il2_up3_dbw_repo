// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PE_2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2a, TypeDiveBomber, TypeBomber, PaintScheme

public abstract class PE_2 extends com.maddox.il2.objects.air.Scheme2a
{

    public PE_2()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, 75F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 112.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -27F * (float)java.lang.Math.sin((double)f * 3.1415926535897931D), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -170.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 112.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -27F * (float)java.lang.Math.sin((double)f * 3.1415926535897931D), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 170.5F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, 0.833333F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, -0.833333F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.PE_2.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 0.0F, -f);
            return;
        }
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

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Head2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxArmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("p1"))
                    getEnergyPastArmor(12.699999809265137D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(12.699999809265137D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor(12.699999809265137D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p4"))
                    getEnergyPastArmor(9D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 3: // '\003'
                    if(getEnergyPastArmor(4.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    if(getEnergyPastArmor(0.002F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;

                case 4: // '\004'
                case 5: // '\005'
                    if(getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxbomb"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
                {
                    debuggunnery("*** Bomb Payload Detonates..");
                    FM.AS.hitTank(shot.initiator, 0, 100);
                    FM.AS.hitTank(shot.initiator, 1, 100);
                    FM.AS.hitTank(shot.initiator, 2, 100);
                    FM.AS.hitTank(shot.initiator, 3, 100);
                    nextDMGLevels(3, 2, "CF_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("prop") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.4F), shot) > 0.0F)
                {
                    FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Prop Governor Failed..");
                }
                if(s.endsWith("gear") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.1F), shot) > 0.0F)
                {
                    FM.EI.engines[0].setKillPropAngleDeviceSpeeds(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Prop Governor Damaged..");
                }
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6.8F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Engine Damaged..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 28000F)
                        {
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                        {
                            FM.EI.engines[0].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
                        }
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setEngineStops(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Engine Stalled..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 10);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
                    }
                    getEnergyPastArmor(6F, shot);
                }
                if((s.endsWith("cyl1") || s.endsWith("cyl2")) && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.542F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.72F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setEngineStuck(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Cylinder Case Broken - Engine Stuck..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3F, 46.7F), shot);
                }
                if(s.endsWith("supc") && getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    FM.EI.engines[0].setKillCompressor(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Supercharger Out..");
                }
                if(s.endsWith("eqpt") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.001F, 0.2F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(0, 1));
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Magneto Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        FM.EI.engines[0].setKillCompressor(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine1 Compressor Feed Out..");
                    }
                }
                return;
            }
            if(s.startsWith("xxeng2"))
            {
                if(s.endsWith("prop") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.4F), shot) > 0.0F)
                {
                    FM.EI.engines[1].setKillPropAngleDevice(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Prop Governor Failed..");
                }
                if(s.endsWith("gear") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.1F), shot) > 0.0F)
                {
                    FM.EI.engines[1].setKillPropAngleDeviceSpeeds(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Prop Governor Damaged..");
                }
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6.8F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 1);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 1, 2);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Engine Damaged..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 28000F)
                        {
                            FM.EI.engines[1].setCyliderKnockOut(shot.initiator, 1);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[1].getCylindersOperable() + "/" + FM.EI.engines[1].getCylinders() + " Left..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                        {
                            FM.EI.engines[1].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
                        }
                        FM.EI.engines[1].setReadyness(shot.initiator, FM.EI.engines[1].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Readyness Reduced to " + FM.EI.engines[1].getReadyness() + "..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[1].setEngineStops(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Engine Stalled..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.AS.hitEngine(shot.initiator, 1, 10);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
                    }
                    getEnergyPastArmor(6F, shot);
                }
                if((s.endsWith("cyl1") || s.endsWith("cyl2")) && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.542F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[1].getCylindersRatio() * 1.72F)
                {
                    FM.EI.engines[1].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Cylinders Hit, " + FM.EI.engines[1].getCylindersOperable() + "/" + FM.EI.engines[1].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[1].setEngineStuck(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Cylinder Case Broken - Engine Stuck..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 1, 3);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3F, 46.7F), shot);
                }
                if(s.endsWith("supc") && getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    FM.EI.engines[1].setKillCompressor(shot.initiator);
                    com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Supercharger Out..");
                }
                if(s.endsWith("eqpt") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.001F, 0.2F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        FM.EI.engines[1].setMagnetoKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(0, 1));
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Magneto Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        FM.EI.engines[1].setKillCompressor(shot.initiator);
                        com.maddox.il2.objects.air.PE_2.debugprintln(this, "*** Engine2 Compressor Feed Out..");
                    }
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr"))
                {
                    if((s.startsWith("xxlockr1") || s.startsWith("xxlockr2")) && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    {
                        debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                    }
                    if((s.startsWith("xxlockr3") || s.startsWith("xxlockr4")) && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    {
                        debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                    }
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvR") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
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
                return;
            }
            if(s.startsWith("xxMgun0"))
            {
                int j = s.charAt(7) - 49;
                if(getEnergyPastArmor(0.5F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun (" + j + ") Disabled..");
                    FM.AS.setJamBullets(0, j);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxoil1"))
            {
                if(getEnergyPastArmor(0.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    debuggunnery("Engine Module: Oil Radiator 1 Pierced..");
                }
                return;
            }
            if(s.startsWith("xxoil2"))
            {
                if(getEnergyPastArmor(0.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitOil(shot.initiator, 1);
                    getEnergyPastArmor(0.22F, shot);
                    debuggunnery("Engine Module: Oil Radiator 2 Pierced..");
                }
                return;
            }
            if(s.startsWith("xxprib"))
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                getEnergyPastArmor(4.88F, shot);
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 48;
                switch(k)
                {
                case 1: // '\001'
                    doHitMeATank(shot, 1);
                    doHitMeATank(shot, 2);
                    break;

                case 2: // '\002'
                    doHitMeATank(shot, 1);
                    break;

                case 3: // '\003'
                    doHitMeATank(shot, 2);
                    break;

                case 4: // '\004'
                    doHitMeATank(shot, 0);
                    break;

                case 5: // '\005'
                    doHitMeATank(shot, 3);
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(7.5F / (float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                return;
            } else
            {
                if(!s.startsWith("xxwater"));
                return;
            }
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.x > 2.2000000000000002D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.73F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                } else
                if(point3d.y > 0.0D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
            }
            return;
        }
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose1") < 2)
            {
                hitChunk("Nose1", shot);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
            }
        } else
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel1"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xkeel2"))
        {
            if(chunkDamageVisible("Keel2") < 2)
                hitChunk("Keel2", shot);
        } else
        if(s.startsWith("xrudder1"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xrudder2"))
        {
            if(chunkDamageVisible("Rudder2") < 1)
                hitChunk("Rudder2", shot);
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
            if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                debuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l;
            if(s.startsWith("xhead"))
                l = s.charAt(5) - 49;
            else
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l = s.charAt(6) - 49;
            } else
            {
                byte0 = 2;
                l = s.charAt(6) - 49;
            }
            hitFlesh(l, shot, byte0);
        }
    }

    private final void doHitMeATank(com.maddox.il2.ai.Shot shot, int i)
    {
        if(getEnergyPastArmor(0.2F, shot) > 0.0F)
            if(shot.power < 14100F)
            {
                if(FM.AS.astateTankStates[i] == 0)
                {
                    FM.AS.hitTank(shot.initiator, i, 1);
                    FM.AS.doSetTankState(shot.initiator, i, 1);
                }
                if(FM.AS.astateTankStates[i] > 0 && (com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F || shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F))
                    FM.AS.hitTank(shot.initiator, i, 2);
            } else
            {
                FM.AS.hitTank(shot.initiator, i, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 56000F)));
            }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(FM.AS.astateTankStates[1] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    FM.AS.hitTank(this, 1, 1);
                if(FM.getSpeedKMH() > 200F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    nextDMGLevel("Keel1_D0", 0, this);
                if(FM.getSpeedKMH() > 200F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    nextDMGLevel("StabL_D0", 0, this);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    nextDMGLevel("WingLIn_D0", 0, this);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(FM.AS.astateTankStates[2] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    FM.AS.hitTank(this, 2, 1);
                if(FM.getSpeedKMH() > 200F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    nextDMGLevel("Keel2_D0", 0, this);
                if(FM.getSpeedKMH() > 200F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.025F)
                    nextDMGLevel("StabR_D0", 0, this);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    nextDMGLevel("WingRIn_D0", 0, this);
            }
        }
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask2_D0", false);
        else
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
        if(hierMesh().chunkFindCheck("HMask3_D0") > 0)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask3_D0", false);
            else
                hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
        if(hierMesh().chunkFindCheck("HMask3a_D0") > 0)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask3a_D0", false);
            else
                hierMesh().chunkVisible("HMask3a_D0", hierMesh().isChunkVisible("Pilot3a_D0"));
        if(hierMesh().chunkFindCheck("HMask3b_D0") > 0)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask3b_D0", false);
            else
                hierMesh().chunkVisible("HMask3b_D0", hierMesh().isChunkVisible("Pilot3b_D0"));
        if(hierMesh().chunkFindCheck("HMask3c_D0") > 0)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask3c_D0", false);
            else
                hierMesh().chunkVisible("HMask3c_D0", hierMesh().isChunkVisible("Pilot3c_D0"));
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            break;

        case 2: // '\002'
            FM.turret[1].bIsOperable = false;
            FM.turret[2].bIsOperable = false;
            FM.turret[3].bIsOperable = false;
            break;
        }
    }

    public void doRemoveBodyFromPlane(int i)
    {
        super.doRemoveBodyFromPlane(i);
        if(i >= 3)
        {
            doRemoveBodyChunkFromPlane("Pilot3a");
            doRemoveBodyChunkFromPlane("Head3a");
            doRemoveBodyChunkFromPlane("Pilot3b");
            doRemoveBodyChunkFromPlane("Head3b");
            doRemoveBodyChunkFromPlane("Pilot3c");
            doRemoveBodyChunkFromPlane("Head3c");
        }
    }

    protected void moveAirBrake(float f)
    {
        if(this instanceof com.maddox.il2.objects.air.TypeDiveBomber)
        {
            hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 90F * f, 0.0F);
            hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 90F * f, 0.0F);
        }
    }

    protected void moveBayDoor(float f)
    {
        if(this instanceof com.maddox.il2.objects.air.TypeBomber)
        {
            hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -85F * f, 0.0F);
            hierMesh().chunkSetAngles("Bay2_D0", 0.0F, 85F * f, 0.0F);
        }
        hierMesh().chunkSetAngles("BayL1_D0", 0.0F, -65F * f, 0.0F);
        hierMesh().chunkSetAngles("BayL2_D0", 0.0F, 65F * f, 0.0F);
        hierMesh().chunkSetAngles("BayR1_D0", 0.0F, 65F * f, 0.0F);
        hierMesh().chunkSetAngles("BayR2_D0", 0.0F, -65F * f, 0.0F);
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
            hierMesh().chunkVisible("WireL_D0", false);
            break;

        case 12: // '\f'
            hierMesh().chunkVisible("WireR_D0", false);
            break;

        case 3: // '\003'
            FM.AS.setEngineState(this, 0, 0);
            break;

        case 4: // '\004'
            FM.AS.setEngineState(this, 1, 0);
            break;

        case 13: // '\r'
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        if(FM.AS.bIsAboutToBailout)
        {
            hierMesh().chunkVisible("WireL_D0", false);
            hierMesh().chunkVisible("WireR_D0", false);
        }
        super.update(f);
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
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

    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_2.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }
}
