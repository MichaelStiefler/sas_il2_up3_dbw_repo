// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FW_190STURM.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.PylonETC501FW190;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeBNZFighter, PaintScheme, 
//            Aircraft

public abstract class FW_190STURM extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public FW_190STURM()
    {
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("GearL5_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("GearR5_D0", false);
        }
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj == null)
            return;
        for(int i = 0; i < aobj.length; i++)
            if(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonETC501FW190)
            {
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("GearL5_D0", false);
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("GearR5_D0", false);
                return;
            }

    }

    public void update(float f)
    {
        if(FM.AS.bIsAboutToBailout)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Wire_D0", false);
        super.update(f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 20F * f, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_190STURM.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    public boolean cut(java.lang.String s)
    {
        if(s.startsWith("Tail1"))
            FM.AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 2, 4);
        return super.cut(s);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);

        case 34: // '"'
        case 35: // '#'
        default:
            return super.cutFM(i, j, actor);
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Armor: Hit..");
                if(s.endsWith("p1"))
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(50F, 50F), shot);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Armor Glass: Hit..");
                    if(shot.power <= 0.5F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Armor Glass: Bullet Stopped..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.91F)
                            ((com.maddox.il2.objects.air.Aircraft)this).doRicochetBack(shot);
                    }
                } else
                if(s.endsWith("p3"))
                {
                    if(point3d.z < -0.27000000000000002D)
                        ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.0999999046325684D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-006D), shot);
                    else
                        ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
                } else
                if(s.endsWith("p6"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
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
                case 4: // '\004'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Controls: Control Crank Destroyed..");
                    }
                    break;

                case 2: // '\002'
                case 3: // '\003'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Controls: Disabled..");
                    }
                    break;

                case 5: // '\005'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Elevator Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 6: // '\006'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 8: // '\b'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Spar Construction: Hit..");
                if(s.startsWith("xxspart") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.4F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Tail1 Spars Broken in Half..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                if(s.startsWith("xxsparli") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(23.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "Rudder1_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** VatorL Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorL_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** VatorR Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorR_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Hit..");
                if(s.endsWith("pipe"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && FM.EI.engines[0].getType() == 0 && FM.CT.Weapons[1] != null && FM.CT.Weapons[1].length != 2)
                    {
                        FM.AS.setJamBullets(1, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Nose Nozzle Pipe Bent..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.3F, shot);
                } else
                if(s.endsWith("prop"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.9F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Governor Hit, Disabled..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("gear"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.EI.engines[0].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Reductor Gear..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Supercharger Disabled..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.5F, shot);
                } else
                if(s.endsWith("feed"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8.9F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && FM.EI.engines[0].getPowerOutput() > 0.7F && FM.EI.engines[0].getType() == 0)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 100);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.0F, shot);
                } else
                if(s.endsWith("fuel"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.1F, shot) > 0.0F && FM.EI.engines[0].getType() == 0)
                    {
                        FM.EI.engines[0].setEngineStops(shot.initiator);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.0F, shot);
                } else
                if(s.endsWith("case"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F && FM.EI.engines[0].getType() == 0)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                        }
                        if(FM.EI.engines[0].getType() == 0)
                            FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(27.5F, shot);
                } else
                if(s.startsWith("xxeng1cyl"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.4F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * (FM.EI.engines[0].getType() == 0 ? 1.75F : 0.5F))
                    {
                        if(FM.EI.engines[0].getType() == 0)
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        else
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19200F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F && FM.EI.engines[0].getType() == 0)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 35000F && FM.EI.engines[0].getType() == 1)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 1);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Piston Head..");
                        }
                        ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(43.6F, shot);
                    }
                } else
                if(s.startsWith("xxeng1mag"))
                {
                    int j = s.charAt(9) - 49;
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, j);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto " + j + " Destroyed..");
                } else
                if(s.endsWith("sync"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
                } else
                if(s.endsWith("oil1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.4F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 48;
                switch(k)
                {
                default:
                    break;

                case 1: // '\001'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        if(FM.AS.astateTankStates[2] == 0)
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Pierced..");
                            FM.AS.hitTank(shot.initiator, 2, 1);
                            FM.AS.doSetTankState(shot.initiator, 2, 1);
                        } else
                        if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.9F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                        {
                            FM.AS.hitTank(shot.initiator, 2, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Hit..");
                        }
                        if(shot.power > 200000F)
                        {
                            FM.AS.hitTank(shot.initiator, 2, 99);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Major Hit..");
                        }
                    }
                    break;

                case 2: // '\002'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.2F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                        break;
                    if(FM.AS.astateTankStates[1] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 1, 1);
                        FM.AS.doSetTankState(shot.initiator, 1, 1);
                    } else
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    {
                        FM.AS.hitTank(shot.initiator, 1, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Hit..");
                    }
                    if(shot.power > 200000F)
                    {
                        FM.AS.hitTank(shot.initiator, 1, 99);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Major Hit..");
                    }
                    break;

                case 3: // '\003'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.2F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                        break;
                    if(FM.AS.astateTankStates[0] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 0, 1);
                        FM.AS.doSetTankState(shot.initiator, 0, 1);
                    } else
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    {
                        FM.AS.hitTank(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Hit..");
                    }
                    if(shot.power > 200000F)
                    {
                        FM.AS.hitTank(shot.initiator, 0, 99);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Major Hit..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxmw50"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** MW50 Tank: Pierced..");
                    FM.AS.setInternalDamage(shot.initiator, 2);
                }
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                    FM.AS.setJamBullets(1, 0);
                if(s.endsWith("02"))
                    FM.AS.setJamBullets(1, 1);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Nose Cannon: Disabled..");
                FM.AS.setJamBullets(0, 0);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
                return;
            }
            if(s.startsWith("xxradiat"))
            {
                FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.05F));
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Radiator Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
            }
            return;
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("CF") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.z > 0.40000000000000002D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                } else
                if(point3d.y > 0.0D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                } else
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(point3d.x > 0.20000000000000001D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xeng"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Engine1") < 2)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Keel1") < 2)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Rudder1") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabL") < 2)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("StabR") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
            if(s.startsWith("xvatorl") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorL") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorL", shot);
            if(s.startsWith("xvatorr") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorR") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorR", shot);
        } else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", shot);
            if(s.startsWith("xwingrmid") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", shot);
            if(s.startsWith("xwinglout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xarone"))
        {
            if(s.startsWith("xaronel"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneL", shot);
            if(s.startsWith("xaroner"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
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
            ((com.maddox.il2.objects.air.Aircraft)this).hitFlesh(l, shot, ((int) (byte0)));
        }
        if(s.startsWith("xxammo"))
        {
            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Ammo: Hit..");
            if(s.endsWith("05"))
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(50F, 50F), shot);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon Gun (1) Payload Detonates..");
                FM.AS.hitTank(shot.initiator, 1, 99);
                ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "WingLMid_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid"), shot.initiator);
            }
        }
        if(s.endsWith("06"))
            ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(50F, 50F), shot);
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
            ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "WingRMid_D", shot.initiator);
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190NEW.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
