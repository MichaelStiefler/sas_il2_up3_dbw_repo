// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Fulmar.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeStormovik, TypeFighter, Aircraft, 
//            Cockpit, NetAircraft, PaintScheme

public abstract class Fulmar extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeFighter
{

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.startsWith("2x"))
        {
            hierMesh().chunkVisible("WingRackL_D0", true);
            hierMesh().chunkVisible("WingRackR_D0", true);
            return;
        }
        if(thisWeaponsName.startsWith("1x"))
        {
            hierMesh().chunkVisible("catapult_hook_D0", false);
            return;
        } else
        {
            return;
        }
    }

    public Fulmar()
    {
        bPitUnfocused = true;
        airBrakePos = 0.0F;
        suspension = 0.0F;
        arrestor = 0.0F;
        obsLookoutTimeLeft = 2.0F;
        obsLookoutAz = 0.0F;
        obsLookoutEl = 0.0F;
        obsLookoutPos = new float[3][129];
        wheel1 = 0.0F;
        wheel2 = 0.0F;
        slat = 0.0F;
        noenemy = 0;
        wait = 0;
        obsLookTime = 0;
        obsLookAzimuth = 0.0F;
        obsLookElevation = 0.0F;
        obsAzimuth = 0.0F;
        obsElevation = 0.0F;
        obsAzimuthOld = 0.0F;
        obsElevationOld = 0.0F;
        obsMove = 0.0F;
        obsMoveTot = 0.0F;
        bObserverKilled = false;
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
            bObserverKilled = true;
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(!bObserverKilled)
            if(obsLookTime == 0)
            {
                obsLookTime = 2 + com.maddox.il2.ai.World.Rnd().nextInt(1, 3);
                obsMoveTot = 1.0F + com.maddox.il2.ai.World.Rnd().nextFloat() * 1.5F;
                obsMove = 0.0F;
                obsAzimuthOld = obsAzimuth;
                obsElevationOld = obsElevation;
                if((double)com.maddox.il2.ai.World.Rnd().nextFloat() > 0.80000000000000004D)
                {
                    obsAzimuth = 0.0F;
                    obsElevation = 0.0F;
                } else
                {
                    obsAzimuth = com.maddox.il2.ai.World.Rnd().nextFloat() * 140F - 70F;
                    obsElevation = com.maddox.il2.ai.World.Rnd().nextFloat() * 50F - 20F;
                }
            } else
            {
                obsLookTime--;
            }
        if(FM.getAltitude() < 3000F)
        {
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
        } else
        {
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
        }
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 0, 1);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, ((com.maddox.il2.engine.Actor) (this)));
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, ((com.maddox.il2.engine.Actor) (this)));
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, ((com.maddox.il2.engine.Actor) (this)));
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, ((com.maddox.il2.engine.Actor) (this)));
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -84.5F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -140.5F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -180F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -84.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -140.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -180F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.Fulmar.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
        if(FM.Gears.onGround())
            suspension = suspension + 0.008F;
        else
            suspension = suspension - 0.008F;
        if(suspension < 0.0F)
            suspension = 0.0F;
        if(suspension > 0.1F)
            suspension = 0.1F;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = suspension / 10F;
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 0.0F, 25F, 0.0F, 1.0F);
        float f1 = FM.Gears.gWheelSinking[0] * f + suspension;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 0.24F, 0.0F, 0.24F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f1 = FM.Gears.gWheelSinking[1] * f + suspension;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 0.24F, 0.0F, 0.24F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveFlap(float f)
    {
        float f1 = -35F * f;
        hierMesh().chunkSetAngles("FlapL1_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("FlapR1_D0", 0.0F, f1, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.67F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if((double)f > 0.02D)
        {
            hierMesh().chunkVisible("Blister2move_D0", true);
            hierMesh().chunkVisible("Blister2_D0", false);
        } else
        {
            hierMesh().chunkVisible("Blister2move_D0", false);
            hierMesh().chunkVisible("Blister2_D0", true);
        }
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.68F);
        hierMesh().chunkSetLocate("Blister2_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void update(float f)
    {
        float f1 = 0.0F;
        if(FM.CT.getArrestor() > 0.2F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f2 = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.arrestorVAngle, -26F, 11F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f2;
                moveArrestorHook(arrestor);
            } else
            {
                float f3 = (-42F * FM.Gears.arrestorVSink) / 37F;
                if(f3 < 0.0F && FM.getSpeedKMH() > 50F)
                    com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (FM.Gears.arrestorHook)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f3 > 0.0F && FM.CT.getArrestor() < 0.95F)
                    f3 = 0.0F;
                if(f3 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f3);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f3);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
        if(obsMove < obsMoveTot && !bObserverKilled && !FM.AS.isPilotParatrooper(1))
        {
            if(obsMove < 0.2F || obsMove > obsMoveTot - 0.2F)
                obsMove += ((float) (0.29999999999999999D * (double)f));
            else
            if(obsMove < 0.1F || obsMove > obsMoveTot - 0.1F)
                obsMove += 0.15F;
            else
                obsMove += ((float) (1.2D * (double)f));
            obsLookAzimuth = com.maddox.il2.objects.air.Aircraft.cvt(obsMove, 0.0F, obsMoveTot, obsAzimuthOld, obsAzimuth);
            obsLookElevation = com.maddox.il2.objects.air.Aircraft.cvt(obsMove, 0.0F, obsMoveTot, obsElevationOld, obsElevation);
            hierMesh().chunkSetAngles("Head2_D0", 0.0F, obsLookAzimuth, obsLookElevation);
        }
        hierMesh().chunkSetAngles("Radiator_D0", 0.0F, -25F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        super.update(f);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -f * 30F, 0.0F);
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -f * 30F, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -25F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        if(f < 0.0F)
        {
            hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20F * f, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20F * f, 0.0F);
        } else
        {
            hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
        }
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -52F * f, 0.0F);
        arrestor = f;
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("Flap01_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, -170F), 0.0F);
        hiermesh.chunkSetAngles("Flap02_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, -170F), 0.0F);
        hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.28F, 1.0F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.28F, 1.0F, 0.0F, -80F), 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            FM.CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    protected void setControlDamage(com.maddox.il2.ai.Shot shot, int i)
    {
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.002F && getEnergyPastArmor(4F, shot) > 0.0F)
        {
            FM.AS.setControlsDamage(shot.initiator, i);
            mydebuggunnery(i + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        boolean flag = false;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                mydebuggunnery("Armor: Hit..");
                if(s.startsWith("xxarmorp"))
                {
                    int i = s.charAt(8) - 48;
                    switch(i)
                    {
                    case 1: // '\001'
                        getEnergyPastArmor(22.760000228881836D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                        if(shot.power <= 0.0F)
                            doRicochetBack(shot);
                        break;

                    case 3: // '\003'
                        getEnergyPastArmor(9.366F, shot);
                        break;

                    case 5: // '\005'
                        getEnergyPastArmor(12.699999809265137D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                        break;
                    }
                }
            } else
            if(s.startsWith("xxspar"))
            {
                mydebuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(5.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(5.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(4.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(4.9600000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    mydebuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    mydebuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
            } else
            if(s.startsWith("xxeng"))
            {
                if((s.endsWith("prop") || s.endsWith("pipe")) && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
                if(s.endsWith("case") || s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            mydebuggunnery("*** Engine Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            mydebuggunnery("*** Engine Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - 0.002F);
                        mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls1") || s.endsWith("cyls2"))
                {
                    if(getEnergyPastArmor(6.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        mydebuggunnery("*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            mydebuggunnery("*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setKillCompressor(shot.initiator);
                    getEnergyPastArmor(2.0F, shot);
                }
                if(s.endsWith("oil1"))
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
                }
                mydebuggunnery("*** Engine state = " + FM.AS.astateEngineStates[0]);
            } else
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.19F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    if(FM.AS.astateTankStates[j] == 0)
                    {
                        mydebuggunnery("Fuel System: Fuel Tank Pierced..");
                        FM.AS.hitTank(shot.initiator, j, 1);
                        FM.AS.doSetTankState(shot.initiator, j, 1);
                    } else
                    if(FM.AS.astateTankStates[j] == 1)
                    {
                        mydebuggunnery("Fuel System: Fuel Tank Pierced (2)..");
                        FM.AS.hitTank(shot.initiator, j, 1);
                        FM.AS.doSetTankState(shot.initiator, j, 2);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, j, 2);
                        mydebuggunnery("Fuel System: Fuel Tank Pierced, State Shifted..");
                    }
                }
                mydebuggunnery("Tank State: " + FM.AS.astateTankStates[j]);
            } else
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    mydebuggunnery("Armament System: Forward Machine Gun: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    mydebuggunnery("Armament System: Rear Machine Gun: Disabled..");
                    FM.AS.setJamBullets(1, 0);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.3F, 12.6F), shot);
            }
        } else
        if(s.startsWith("xcf"))
        {
            setControlDamage(shot, 0);
            setControlDamage(shot, 1);
            setControlDamage(shot, 2);
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(point3d.x > -2.2000000476837158D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.80000001192092896D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.89999997615814209D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) < 0.80000001192092896D)
                    if(point3d.y > 0.0D)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                    } else
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                    }
            }
        } else
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xtail"))
        {
            setControlDamage(shot, 1);
            setControlDamage(shot, 2);
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
            hitChunk("Keel1", shot);
        else
        if(s.startsWith("xrudder"))
        {
            setControlDamage(shot, 2);
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
            {
                setControlDamage(shot, 0);
                hitChunk("WingLIn", shot);
                hitChunk("Flap01", shot);
            }
            if(s.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
            {
                setControlDamage(shot, 0);
                hitChunk("WingRIn", shot);
                hitChunk("Flap02", shot);
            }
            if(s.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
            {
                setControlDamage(shot, 0);
                hitChunk("WingLMid", shot);
            }
            if(s.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
            {
                setControlDamage(shot, 0);
                hitChunk("WingRMid", shot);
            }
            if(s.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xflap01") && chunkDamageVisible("Flap01") < 3)
        {
            setControlDamage(shot, 0);
            hitChunk("WingLIn", shot);
            hitChunk("Flap01", shot);
        } else
        if(s.startsWith("xflap02") && chunkDamageVisible("Flap02") < 3)
        {
            setControlDamage(shot, 0);
            hitChunk("WingRIn", shot);
            hitChunk("Flap02", shot);
        } else
        if(s.startsWith("xarone"))
        {
            if(s.startsWith("xaronel") && chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
            if(s.startsWith("xaroner") && chunkDamageVisible("AroneR") < 1)
                hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xgearr"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                mydebuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
            hitChunk("GearR2", shot);
        } else
        if(s.startsWith("xgearl"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                mydebuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
            hitChunk("GearL2", shot);
        } else
        if(s.startsWith("xradiator"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
            {
                FM.AS.hitOil(shot.initiator, 0);
                mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
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
            hitFlesh(k, shot, ((int) (byte0)));
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -70F)
            {
                f = -70F;
                flag = false;
            }
            if(f > 70F)
            {
                f = 70F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 70F)
            {
                f1 = 70F;
                flag = false;
            }
            if((f > -30F || f < 30F) && f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            break;
        }
        af[0] = f;
        af[1] = f1;
        return flag;
    }

    protected void mydebuggunnery(java.lang.String s)
    {
    }

    public boolean bPitUnfocused;
    public float airBrakePos;
    private float suspension;
    protected float arrestor;
    float obsLookoutTimeLeft;
    float obsLookoutAz;
    float obsLookoutEl;
    float obsLookoutAnim;
    float obsLookoutMax;
    float obsLookoutAzSpd;
    float obsLookoutElSpd;
    int obsLookoutIndex;
    float obsLookoutPos[][];
    private float obsLookout;
    private float wheel1;
    private float wheel2;
    private float slat;
    private int noenemy;
    private int wait;
    private int obsLookTime;
    private float obsLookAzimuth;
    private float obsLookElevation;
    private float obsAzimuth;
    private float obsElevation;
    private float obsAzimuthOld;
    private float obsElevationOld;
    private float obsMove;
    private float obsMoveTot;
    boolean bTAGKilled;
    boolean bObserverKilled;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
    }
}
