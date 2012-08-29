// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   N1K3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.KuusenFlapControl;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, PaintScheme, Aircraft, 
//            Cockpit

public abstract class N1K3 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public N1K3()
    {
        IsInitFlapMgr = false;
        flapps = 0.0F;
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

    public void update(float f)
    {
        InitFlapMgr();
        super.update(f);
        float airSpeedPerSec = com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeed());
        if(FM.CT.FlapsControl <= 0.29F && FM.CT.FlapsControl >= 0.05F)
        {
            kuusen_flap = KuusenFlapCtrl.GetKuusenFlapValue(airSpeedPerSec, FM.getOverload(), 0.0F);
            float oldFlap = FM.CT.getFlap();
            if(0.6334F < kuusen_flap)
                kuusen_flap = 0.6334F;
            else
            if(0.0F > kuusen_flap)
                kuusen_flap = 0.0F;
            if(FM.CT.getFlap() != kuusen_flap)
                if(kuusen_flap == 0.0F && (double)FM.CT.getFlap() <= 0.050000000000000003D)
                {
                    sfxFlaps(false);
                    FM.CT.forceFlaps(0.0F);
                } else
                {
                    FM.CT.forceFlaps(0.05F * kuusen_flap + 0.95F * FM.CT.getFlap());
                }
            if((double)java.lang.Math.abs(oldFlap - FM.CT.getFlap()) <= 0.01D)
                sfxFlaps(false);
        }
        if(FM.CT.BrakeControl > 0.4F)
            FM.CT.BrakeControl = 0.4F;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 0.95F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -85F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.75F, 0.0F, -0.205F);
        hiermesh.chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 0.94F, 0.0F, -0.05F);
        hiermesh.chunkSetLocate("GearL4_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(f > 0.75F)
            hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 0.95F, -45F, -65F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.75F, 0.0F, -45F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -130F), 0.0F);
        if(f > 0.75F)
            hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.725F, 0.95F, -86F, -133F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.725F, 0.0F, -86F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -0.1F);
        hiermesh.chunkSetLocate("GearL8_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetAngles("GearL9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.115F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearL10_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.85F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -85F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.75F, 0.0F, -0.205F);
        hiermesh.chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 0.94F, 0.0F, -0.05F);
        hiermesh.chunkSetLocate("GearR4_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(f > 0.75F)
            hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.75F, 0.95F, -45F, -65F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.75F, 0.0F, -45F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -130F), 0.0F);
        if(f > 0.75F)
            hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.725F, 0.95F, -86F, -133F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.725F, 0.0F, -86F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.95F, 0.0F, -0.1F);
        hiermesh.chunkSetLocate("GearR8_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetAngles("GearR9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.115F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearR10_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.85F, 0.0F, -85F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.N1K3.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        if(f > 0.0F)
        {
            hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -16F * f, 0.0F);
            hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -28.5F * f, 0.0F);
        } else
        {
            hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -16F * f, 0.0F);
            hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -28.5F * f, 0.0F);
        }
    }

    protected void moveElevator(float f)
    {
        if(f > 0.0F)
        {
            hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35F * f, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35F * f, 0.0F);
        } else
        {
            hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -24.5F * f, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -24.5F * f, 0.0F);
        }
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -33F * f, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.99F, 0.0F, -0.61F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                {
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    if(getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(32.5F, 65F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) < 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(13.130000114440918D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(8.7F, 9.81F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                return;
            }
            if(s.startsWith("xxcanon0"))
            {
                int i = s.charAt(8) - 49;
                if(getEnergyPastArmor(6.29F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Cannon (" + i + ") Disabled..");
                    FM.AS.setJamBullets(1, i);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int j = s.charAt(10) - 48;
                switch(j)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.175F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(getEnergyPastArmor(0.22F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.275F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;

                case 7: // '\007'
                    if(getEnergyPastArmor(4.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.175F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
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
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - 0.002F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 0);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    }
                    getEnergyPastArmor(2.0F, shot);
                }
                if(s.endsWith("prop") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(2.5F, shot) > 0.0F)
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
                return;
            }
            if(s.startsWith("xxmgun0"))
            {
                int k = s.charAt(7) - 49;
                if(getEnergyPastArmor(0.75F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun (" + k + ") Disabled..");
                    FM.AS.setJamBullets(0, k);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                if(getEnergyPastArmor(0.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    debuggunnery("Engine Module: Oil Tank Pierced..");
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int l = s.charAt(6) - 49;
                if(l > 3)
                    return;
                if(getEnergyPastArmor(0.8F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    if(FM.AS.astateTankStates[l] == 0)
                    {
                        debuggunnery("Fuel Tank (" + l + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, l, 1);
                        FM.AS.doSetTankState(shot.initiator, l, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.008F || shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.6F)
                    {
                        FM.AS.hitTank(shot.initiator, l, 1);
                        debuggunnery("Fuel Tank (" + l + "): Hit..");
                    }
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcf") || s.startsWith("xcock"))
        {
            hitChunk("CF", shot);
            return;
        }
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2 && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 5000F) < shot.power)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder"))
        {
            if(chunkDamageVisible("Rudder1") < 1 && getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
            if(s.startsWith("xvatorl") && chunkDamageVisible("VatorL") < 1 && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("VatorL", shot);
            if(s.startsWith("xvatorr") && chunkDamageVisible("VatorR") < 1 && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("VatorR", shot);
        } else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin") && getEnergyPastArmor(2.5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 5000F) < shot.power)
                hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin") && getEnergyPastArmor(2.5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 5000F) < shot.power)
                hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid") && getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 4000F) < shot.power)
                hitChunk("WingLMid", shot);
            if(s.startsWith("xwingrmid") && getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 4000F) < shot.power)
                hitChunk("WingRMid", shot);
            if(s.startsWith("xwinglout") && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
                hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout") && getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3000F) < shot.power)
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
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    protected void InitFlapMgr()
    {
        if(IsInitFlapMgr)
        {
            return;
        } else
        {
            KuusenFlapCtrl = new KuusenFlapControl(FM, -5);
            IsInitFlapMgr = true;
            return;
        }
    }

    protected float flapps;
    protected float kuusen_flap;
    protected com.maddox.il2.fm.KuusenFlapControl KuusenFlapCtrl;
    protected boolean IsInitFlapMgr;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.N1K3.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
    }
}
