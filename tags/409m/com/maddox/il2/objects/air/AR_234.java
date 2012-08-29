// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AR_234.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeBomber, TypeDiveBomber, Aircraft, 
//            PaintScheme, EjectionSeat

public abstract class AR_234 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeDiveBomber
{

    public AR_234()
    {
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

    private static final float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.65F, 0.0F, -92F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.65F, 0.0F, -62.5F), 0.0F);
        if(f < 0.525F)
        {
            hiermesh.chunkSetAngles("GearC6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.525F, 0.0F, -46F), 0.0F);
            hiermesh.chunkSetAngles("GearC7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.525F, 0.0F, -0.25F), 0.0F);
        } else
        {
            hiermesh.chunkSetAngles("GearC6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.525F, 0.65F, -46F, -73.5F), 0.0F);
            hiermesh.chunkSetAngles("GearC7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.525F, 0.65F, -0.25F, -7.5F), 0.0F);
        }
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.65F, 0.0F, -0.2935F);
        hiermesh.chunkSetLocate("GearC8_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetAngles("GearC9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.07F, 0.32F, 0.0F, -98F), 0.0F);
        hiermesh.chunkSetAngles("GearC10_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.07F, 0.32F, 0.0F, -98F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.AR_234.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, 3F), gear6), 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.AR_234.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, 3F), gear7);
        hiermesh.chunkSetLocate("GearL7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(f < 0.6F)
        {
            hiermesh.chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.6F, 0.0F, -76.5F), 0.0F);
            hiermesh.chunkSetAngles("GearL9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.3F, 0.0F, -44F), 0.0F);
        } else
        {
            hiermesh.chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.6F, 0.8F, -76.5F, -62F), 0.0F);
            hiermesh.chunkSetAngles("GearL9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.6F, 0.8F, -44F, 0.0F), 0.0F);
        }
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.AR_234.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, 3F), gear6), 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.AR_234.floatindex(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.8F, 0.0F, 3F), gear7);
        hiermesh.chunkSetLocate("GearR7_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(f < 0.6F)
        {
            hiermesh.chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.6F, 0.0F, -76.5F), 0.0F);
            hiermesh.chunkSetAngles("GearR9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.3F, 0.0F, -44F), 0.0F);
        } else
        {
            hiermesh.chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.6F, 0.8F, -76.5F, -62F), 0.0F);
            hiermesh.chunkSetAngles("GearR9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.6F, 0.8F, -44F, 0.0F), 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.AR_234.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -0.3274F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -46F), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -46F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -0.3274F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -46F), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -46F), 0.0F);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() > 0.8F)
            hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
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
                    getEnergyPastArmor(15.149999618530273D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Armament System: Left Rear Cannon: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Armament System: Right Rear Cannon: Disabled..");
                    FM.AS.setJamBullets(0, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                if(s.length() == 12)
                    i = 10 + (s.charAt(11) - 48);
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                    if(getEnergyPastArmor(0.1F, shot) <= 0.0F)
                        break;
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    else
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                    break;

                case 3: // '\003'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    break;

                case 7: // '\007'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    break;

                case 4: // '\004'
                case 6: // '\006'
                case 8: // '\b'
                case 10: // '\n'
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 5: // '\005'
                case 9: // '\t'
                    if(getEnergyPastArmor(4.1F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls Crank: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 11: // '\013'
                case 12: // '\f'
                    if(getEnergyPastArmor(0.3F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxEng"))
            {
                int j = s.charAt(5) - 49;
                if(point3d.x > 0.0D)
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module(s): Supercharger Disabled..");
                        FM.AS.setEngineSpecificDamage(shot.initiator, j, 0);
                    }
                } else
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass && FM.EI.engines[j].getStage() == 6)
                        FM.AS.hitEngine(shot.initiator, j, 1);
                    getEnergyPastArmor(14.296F, shot);
                }
                return;
            }
            if(s.startsWith("xxLock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxLockR") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxLockVL") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxLockVR") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.startsWith("xxLockAL") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxLockAR") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
            }
            if(s.startsWith("xxSpar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxSparLI") && chunkDamageVisible("WingLIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxSparrI") && chunkDamageVisible("WingRIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxSparlm") && chunkDamageVisible("WingLMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxSparrm") && chunkDamageVisible("WingRMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxSparlo") && chunkDamageVisible("WingLOut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxSparro") && chunkDamageVisible("WingROut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() > java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 0.11999999731779099D && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxSpart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
            }
            if(s.startsWith("xxTank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.powerType == 3)
                    FM.AS.hitTank(shot.initiator, k, 2);
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
            if(point3d.x > 0.5D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
            else
            if(point3d.y > 0.0D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
            else
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
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
        if(s.startsWith("xgear"))
        {
            if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
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
            hitFlesh(l, shot, byte0);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
            {
                FM.AS.explodeEngine(this, 0);
                msgCollision(this, "WingLIn_D0", "WingLIn_D0");
                if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                    FM.AS.hitTank(this, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
                else
                    FM.AS.hitTank(this, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
            }
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
            {
                FM.AS.explodeEngine(this, 1);
                msgCollision(this, "WingRIn_D0", "WingRIn_D0");
                if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                    FM.AS.hitTank(this, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
                else
                    FM.AS.hitTank(this, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
            }
        }
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)obj;
                if(!com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    return;
                } else
                {
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_ExternalSeat01");
                    aircraft.pos.getAbs(loc1);
                    hooknamed.computePos(aircraft, loc1, loc);
                    loc.transform(vector3d);
                    vector3d.x += aircraft.FM.Vwld.x;
                    vector3d.y += aircraft.FM.Vwld.y;
                    vector3d.z += aircraft.FM.Vwld.z;
                    new EjectionSeat(3, loc, vector3d, aircraft);
                    return;
                }
            }

        }
;
        hierMesh().chunkVisible("Seat_D0", false);
    }

    public void update(float f)
    {
        if(FM.AS.isMaster())
        {
            for(int i = 0; i < 2; i++)
                if(curctl[i] == -1F)
                {
                    curctl[i] = oldctl[i] = FM.EI.engines[i].getControlThrottle();
                } else
                {
                    curctl[i] = FM.EI.engines[i].getControlThrottle();
                    if((curctl[i] - oldctl[i]) / f > 3F && FM.EI.engines[i].getRPM() < 2400F && FM.EI.engines[i].getStage() == 6 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.hitEngine(this, i, 100);
                    if((curctl[i] - oldctl[i]) / f < -3F && FM.EI.engines[i].getRPM() < 2400F && FM.EI.engines[i].getStage() == 6)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && (FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                            FM.EI.engines[i].setEngineStops(this);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F && (FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                            FM.EI.engines[i].setKillCompressor(this);
                    }
                    oldctl[i] = curctl[i];
                }

            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(FM.EI.engines[0].getPowerOutput() > 0.8F && FM.EI.engines[0].getStage() == 6)
                {
                    if(FM.EI.engines[0].getPowerOutput() > 0.95F)
                        FM.AS.setSootState(this, 0, 3);
                    else
                        FM.AS.setSootState(this, 0, 2);
                } else
                {
                    FM.AS.setSootState(this, 0, 0);
                }
                if(FM.EI.engines[1].getPowerOutput() > 0.8F && FM.EI.engines[1].getStage() == 6)
                {
                    if(FM.EI.engines[1].getPowerOutput() > 0.95F)
                        FM.AS.setSootState(this, 1, 3);
                    else
                        FM.AS.setSootState(this, 1, 2);
                } else
                {
                    FM.AS.setSootState(this, 1, 0);
                }
            }
        }
        super.update(f);
    }

    private float oldctl[] = {
        -1F, -1F
    };
    private float curctl[] = {
        -1F, -1F
    };
    private static final float gear6[] = {
        0.0F, -3F, -3.5F, -1F, 7F
    };
    private static final float gear7[] = {
        0.0F, -0.09835F, -0.21265F, -0.3185F, -0.3917F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
