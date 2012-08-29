// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_250.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintSchemeFMPar06, PaintSchemeFCSPar05, PaintSchemeBCSPar02, 
//            TypeFighter, TypeBNZFighter, Aircraft, Cockpit, 
//            NetAircraft, PaintScheme

public class I_250 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public I_250()
    {
        flapps = 0.0F;
        fdor = 0.0F;
        pdor = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            pdor = 0.9F * pdor + 0.1F * fdor;
            hierMesh().chunkSetAngles("Door1_D0", 0.0F, -20F + 20F * pdor, 0.0F);
            hierMesh().chunkSetAngles("Door2_D0", 0.0F, -20F + 20F * pdor, 0.0F);
            float f1 = FM.EI.engines[0].getControlRadiator();
            if(java.lang.Math.abs(flapps - f1) > 0.01F)
            {
                flapps = f1;
                for(int i = 1; i < 5; i++)
                    hierMesh().chunkSetAngles("Radiator" + i + "_D0", 0.0F, -20F * f1, 0.0F);

            }
            f1 = FM.EI.engines[1].getControlThrottle();
            if(java.lang.Math.abs(fdor - f1) > 0.01F)
                fdor = f1;
        }
        if(FM.AS.isMaster())
        {
            if(FM.EI.engines[1].getStage() == 1)
            {
                FM.EI.engines[1].setStage(this, 6);
                com.maddox.il2.game.HUD.log("EngineI" + (FM.EI.engines[1].getStage() != 6 ? 48 : '1'));
            }
            float f2 = FM.EI.engines[1].getThrustOutput() * FM.EI.engines[1].getControlThrottle();
            if(FM.EI.engines[1].getStage() == 6 && f2 > 0.32F)
            {
                if(f2 > 0.75F)
                    FM.AS.setSootState(this, 1, 3);
                else
                    FM.AS.setSootState(this, 1, 2);
            } else
            {
                FM.AS.setSootState(this, 1, 0);
            }
        }
        if(FM.EI.engines[1].getStage() == 6)
        {
            FM.EI.engines[0].setEngineMomentMax(4337.587F);
            float f3 = 1.01F * (0.12F * FM.EI.engines[0].getw() + 0.88F * FM.EI.engines[1].getw());
            FM.EI.engines[1].setw(f3);
            if(FM.EI.engines[0].getStage() != 6)
                FM.EI.engines[1].setw(0.7F * FM.EI.engines[1].getw());
        } else
        {
            FM.EI.engines[0].setEngineMomentMax(5224.102F);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.15F, 0.45F, 0.0F, -50F), 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.25F, 0.0F, -45F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.25F, 0.0F, 0.3F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.25F, 0.0F, 0.125F);
        hiermesh.chunkSetLocate("GearC3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetLocate("GearC4_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.4F, 0.0F, -90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.72F, 0.98F, -90F, 0.0F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -82F), 0.0F);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.4F, 0.0F, -90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.72F, 0.98F, -90F, 0.0F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.9F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.9F, 0.0F, -82F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.I_250.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -com.maddox.il2.objects.air.Aircraft.cvt(f, -78F, 78F, -78F, 78F), 0.0F);
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

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        boolean flag = false;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.startsWith("xxarmorp"))
                    getEnergyPastArmor(12.88F, shot);
                if(s.startsWith("xxarmorg"))
                {
                    getEnergyPastArmor(33.959999084472656D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        if(java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) > 0.86599999666213989D)
                            doRicochet(shot);
                        else
                            doRicochetBack(shot);
                }
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 7: // '\007'
                default:
                    break;

                case 1: // '\001'
                case 3: // '\003'
                    if(getEnergyPastArmor(4.1F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls Crank: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                case 4: // '\004'
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(getEnergyPastArmor(0.3F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;

                case 8: // '\b'
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 9: // '\t'
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxeng1"))
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
                if(s.startsWith("xxeng1cyl"))
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
                }
                return;
            }
            if(s.startsWith("xxeng2"))
            {
                if(s.startsWith("xxeng2supc") && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module(s): Supercharger Disabled..");
                    FM.AS.setEngineSpecificDamage(shot.initiator, 0, 0);
                    FM.AS.setEngineSpecificDamage(shot.initiator, 1, 0);
                }
                if(s.startsWith("xxeng2case"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass && FM.EI.engines[1].getStage() == 6)
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                    getEnergyPastArmor(14.296F, shot);
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
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
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.powerType == 3)
                    FM.AS.hitTank(shot.initiator, j, 1);
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Armament System: Centre Gun: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Armament System: Left Machine Gun: Disabled..");
                    FM.AS.setJamBullets(1, 0);
                }
                if(s.endsWith("03"))
                {
                    debuggunnery("Armament System: Right Machine Gun: Disabled..");
                    FM.AS.setJamBullets(1, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.3F, 12.6F), shot);
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
                if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.90000000000000002D && getEnergyPastArmor(12D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
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
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xeng"))
        {
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
            hitFlesh(k, shot, byte0);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.AS.setEngineDies(this, 1);
            return cut(com.maddox.il2.objects.air.Aircraft.partNames()[i]);

        case 3: // '\003'
        case 4: // '\004'
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -0.6F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float flapps;
    private float fdor;
    private float pdor;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-250");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-250(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-250(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/I-250(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeBCSPar02());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-250.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitI_250.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.61825F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunB20ki 100", "MGunB20s 100", "MGunB20s 100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}
