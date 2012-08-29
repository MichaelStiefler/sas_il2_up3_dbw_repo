// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   G50.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFMPar00, PaintSchemeFCSPar01, TypeFighter, 
//            TypeTNBFighter, Aircraft, NetAircraft, PaintScheme

public class G50 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public G50()
    {
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);
        }
        return super.cutFM(i, j, actor);
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
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(14.2F, shot);
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
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(0.004F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(0.004F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 6: // '\006'
                case 7: // '\007'
                case 8: // '\b'
                case 9: // '\t'
                    if(getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Controls: Aileron Controls: Disabled..");
                    }
                    break;
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
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6.8F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit - Engine Stucks..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.001F)
                        {
                            debuggunnery("Engine Module: Crank Case Hit - Ball Bearing Jammed - Engine Stuck..");
                            FM.EI.engines[0].setEngineStuck(shot.initiator);
                        }
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 256000F));
                        debuggunnery("Engine Module: Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.002F)
                    {
                        debuggunnery("Engine Module: Crank Case Hit - Fuel Feed Hit - Engine Flamed..");
                        FM.AS.hitEngine(shot.initiator, 0, 10);
                    }
                    getEnergyPastArmor(16F, shot);
                }
                if(s.endsWith("cyls") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.542F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.56F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        debuggunnery("Engine Module: Cylinder Case Broken - Engine Stuck..");
                        FM.EI.engines[0].setEngineStuck(shot.initiator);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
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
                    int j = s.charAt(9) - 49;
                    debuggunnery("Engine Module: Magneto " + j + " Destroyed..");
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, j);
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
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, k, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, k, 2);
                }
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                int l = s.charAt(7) - 49;
                FM.AS.setJamBullets(0, l);
                return;
            }
            if(s.startsWith("xxammo"))
            {
                int i1 = s.charAt(7) - 48;
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 20000F) < shot.power)
                    switch(i1)
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
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxhyd") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 12.39F), shot) > 0.0F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(s.startsWith("xxins"))
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            return;
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(point3d.y > 0.0D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
            }
        }
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
                if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
                {
                    debuggunnery("Undercarriage: Stuck..");
                    FM.AS.setInternalDamage(shot.initiator, 3);
                }
            } else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte0 = 0;
                int j1;
                if(s.endsWith("a"))
                {
                    byte0 = 1;
                    j1 = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte0 = 2;
                    j1 = s.charAt(6) - 49;
                } else
                {
                    j1 = s.charAt(5) - 49;
                }
                hitFlesh(j1, shot, byte0);
            }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -95F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -95F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -95F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -95F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -105F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -105F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.G50.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.2F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.2F, 0.0F, 0.2F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
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
        java.lang.Class class1 = com.maddox.il2.objects.air.G50.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "G.50");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/G-50(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/G-50(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryItaly);
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/G50.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitG50.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.98615F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127si 300", "MGunBredaSAFAT127si 300"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
