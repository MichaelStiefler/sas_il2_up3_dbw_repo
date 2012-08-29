// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_38.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.fm.Squares;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2a, TypeFighter, TypeBNZFighter, TypeStormovik, 
//            Aircraft, PaintScheme

public abstract class P_38 extends com.maddox.il2.objects.air.Scheme2a
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public P_38()
    {
        bChangedPit = true;
        steera = 0.0F;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void moveFlap(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.3F * f;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.04835F * f;
        hierMesh().chunkSetLocate("Flap01_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        float f1 = -25F * f;
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f1, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveAirBrake(float f)
    {
        FM.setGCenter(0.0F - 0.2F * f);
        hierMesh().chunkSetAngles("Brake1_D0", 0.0F, -40F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake2_D0", 0.0F, -40F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake3_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake4_D0", 0.0F, -90F * f, 0.0F);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -102F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC6_D0", 0.0F, -105F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, -140F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearL9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearR9_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearL10_D0", 0.0F, -90F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        steera = 0.0F;
        moveWheelSink();
        com.maddox.il2.objects.air.P_38.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        steera = 0.9F * steera + 0.1F * com.maddox.il2.objects.air.Aircraft.cvt(f, -50F, 50F, 50F, -50F);
        moveWheelSink();
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2406F);
        com.maddox.il2.objects.air.Aircraft.ypr[1] = steera;
        hierMesh().chunkSetLocate("GearC3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -50F), 0.0F);
        hierMesh().chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -105F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -0.3206F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -60F), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.32F, 0.0F, -117.5F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, 0.3206F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -60F), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.32F, 0.0F, -117.5F), 0.0F);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(!FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_D0", false);
                hierMesh().chunkVisible("Head1_D0", false);
                hierMesh().chunkVisible("Pilot1_D1", true);
            }
            break;
        }
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Water1_D0", 0.0F, 18F - 36F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Water2_D0", 0.0F, 18F - 36F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Water3_D0", 0.0F, 18F - 36F * FM.EI.engines[1].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Water4_D0", 0.0F, 18F - 36F * FM.EI.engines[1].getControlRadiator(), 0.0F);
        if(FM.CT.getFlap() < 0.25F * FM.CT.getAirBrake())
            FM.setFlapsShift(0.25F * FM.CT.getAirBrake());
        super.update(f);
        if(FM.isPlayers() && FM.Sq.squareElevators > 0.0F)
        {
            com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)FM;
            if(realflightmodel.RealMode && realflightmodel.indSpeed > 135F)
            {
                float f1 = 1.0F + 0.005F * (135F - realflightmodel.indSpeed);
                if(f1 < 0.0F)
                    f1 = 0.0F;
                FM.SensPitch = 0.63F * f1;
                if(realflightmodel.indSpeed > 155F)
                    FM.producedAM.y -= 6000F * (155F - realflightmodel.indSpeed);
            } else
            {
                FM.SensPitch = 0.63F;
            }
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

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("1"))
                {
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(20F, 30F), shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
                    if(shot.power <= 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            doRicochetBack(shot);
                    }
                }
                if(s.endsWith("2"))
                    getEnergyPastArmor(12.7F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                if(s.endsWith("3"))
                    getEnergyPastArmor(12.7F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                if(s.endsWith("4"))
                    getEnergyPastArmor(8.9F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                if(s.endsWith("5"))
                    getEnergyPastArmor(8.9F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z)), shot);
                return;
            }
            if(s.startsWith("xxarcon"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxvatcon"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevators Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxrudcon"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                if(getEnergyPastArmor(4.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.75F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 6800F)));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine 0 Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(FM.AS.astateEngineStates[0] < 1)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 1);
                        FM.AS.doSetEngineState(shot.initiator, 0, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine 0 Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(25F, shot);
                }
                return;
            }
            if(s.startsWith("xxeng2"))
            {
                if(getEnergyPastArmor(4.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[1].getCylindersRatio() * 0.75F)
                {
                    FM.EI.engines[1].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 6800F)));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine 1 Cylinders Hit, " + FM.EI.engines[1].getCylindersOperable() + "/" + FM.EI.engines[1].getCylinders() + " Left..");
                    if(FM.AS.astateEngineStates[1] < 1)
                    {
                        FM.AS.hitEngine(shot.initiator, 1, 1);
                        FM.AS.doSetEngineState(shot.initiator, 1, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 1, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine 1 Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(25F, shot);
                }
                return;
            }
            if(s.startsWith("xxoilradiat1"))
            {
                if(getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 0: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxoilradiat2"))
            {
                if(getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    FM.AS.hitOil(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 1: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxoiltank1"))
            {
                if(getEnergyPastArmor(2.38F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 0: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxoiltank2"))
            {
                if(getEnergyPastArmor(2.38F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitOil(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 1: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxmagneto1"))
            {
                int i = com.maddox.il2.ai.World.Rnd().nextInt(0, 1);
                FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 0: Magneto " + i + " Destroyed..");
                return;
            }
            if(s.startsWith("xxmagneto2"))
            {
                int j = com.maddox.il2.ai.World.Rnd().nextInt(0, 1);
                FM.EI.engines[1].setMagnetoKnockOut(shot.initiator, j);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 1: Magneto " + j + " Destroyed..");
                return;
            }
            if(s.startsWith("xxturbo1"))
            {
                if(getEnergyPastArmor(1.23F, shot) > 0.0F)
                {
                    FM.EI.engines[0].setKillCompressor(shot.initiator);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 0: Supercharger Destroyed..");
                }
                return;
            }
            if(s.startsWith("xxturbo2"))
            {
                if(getEnergyPastArmor(1.23F, shot) > 0.0F)
                {
                    FM.EI.engines[1].setKillCompressor(shot.initiator);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module 1: Supercharger Destroyed..");
                }
                return;
            }
            if(s.startsWith("xxradiat"))
            {
                int k = 0;
                if(s.endsWith("3") || s.endsWith("4"))
                    k = 1;
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    if(FM.AS.astateEngineStates[k] == 0)
                    {
                        debuggunnery("Engine Module: Water Radiator Pierced..");
                        FM.AS.hitEngine(shot.initiator, k, 1);
                        FM.AS.doSetEngineState(shot.initiator, k, 1);
                    } else
                    if(FM.AS.astateEngineStates[k] == 1)
                    {
                        debuggunnery("Engine Module: Water Radiator Pierced..");
                        FM.AS.hitEngine(shot.initiator, k, 1);
                        FM.AS.doSetEngineState(shot.initiator, k, 2);
                    }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                byte byte0 = 0;
                int j1 = s.charAt(6) - 48;
                switch(j1)
                {
                case 1: // '\001'
                    byte0 = 1;
                    break;

                case 2: // '\002'
                    byte0 = 1;
                    break;

                case 3: // '\003'
                    byte0 = 0;
                    break;

                case 4: // '\004'
                    byte0 = 2;
                    break;

                case 5: // '\005'
                    byte0 = 2;
                    break;

                case 6: // '\006'
                    byte0 = 3;
                    break;
                }
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, byte0, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, byte0, 2);
                }
                return;
            }
            if(s.startsWith("xxgun"))
            {
                int l = s.charAt(5) - 49;
                FM.AS.setJamBullets(0, l);
                getEnergyPastArmor(23.5F, shot);
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                FM.AS.setJamBullets(1, 0);
                getEnergyPastArmor(48.6F, shot);
                return;
            }
            if(s.startsWith("xxammogun"))
            {
                int i1 = com.maddox.il2.ai.World.Rnd().nextInt(0, 3);
                FM.AS.setJamBullets(0, i1);
                getEnergyPastArmor(23.5F, shot);
                return;
            }
            if(s.startsWith("xxammocan"))
            {
                FM.AS.setJamBullets(1, 0);
                getEnergyPastArmor(23.5F, shot);
                return;
            }
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxpark1") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                }
                if(s.startsWith("xxpark2") && chunkDamageVisible("Keel2") > 1 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel2 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel2_D2", shot.initiator);
                }
                if(s.startsWith("xxsparsl") && chunkDamageVisible("StabL") > 1 && getEnergyPastArmor(12.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Stab Spars Damaged..");
                    nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
                if((s.startsWith("xxlockk1") || s.startsWith("xxlockk2")) && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if((s.startsWith("xxlockk3") || s.startsWith("xxlockk4")) && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder2 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                }
                if(s.startsWith("xxlocksl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Vator Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockal") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
            hitChunk("CF", shot);
        if(!s.startsWith("xblister"))
            if(s.startsWith("xengine1"))
                hitChunk("Engine1", shot);
            else
            if(s.startsWith("xengine2"))
                hitChunk("Engine2", shot);
            else
            if(s.startsWith("xtail1"))
                hitChunk("Tail1", shot);
            else
            if(s.startsWith("xtail2"))
                hitChunk("Tail2", shot);
            else
            if(s.startsWith("xkeel1"))
                hitChunk("Keel1", shot);
            else
            if(s.startsWith("xkeel2"))
                hitChunk("Keel2", shot);
            else
            if(s.startsWith("xrudder1"))
                hitChunk("Rudder1", shot);
            else
            if(s.startsWith("xrudder2"))
                hitChunk("Rudder2", shot);
            else
            if(s.startsWith("xstabl"))
                hitChunk("StabL", shot);
            else
            if(s.startsWith("xvatorl"))
            {
                if(chunkDamageVisible("VatorL") < 1)
                    hitChunk("VatorL", shot);
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
                if(s.startsWith("xaronel"))
                    hitChunk("AroneL", shot);
                if(s.startsWith("xaroner"))
                    hitChunk("AroneR", shot);
            } else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte1 = 0;
                int k1;
                if(s.endsWith("a"))
                {
                    byte1 = 1;
                    k1 = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte1 = 2;
                    k1 = s.charAt(6) - 49;
                } else
                {
                    k1 = s.charAt(5) - 49;
                }
                hitFlesh(k1, shot, byte1);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        switch(i)
        {
        case 11: // '\013'
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 12: // '\f'
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 17: // '\021'
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            break;

        case 31: // '\037'
            FM.cut(31, j, actor);
            FM.cut(32, j, actor);
            break;

        case 33: // '!'
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Flap03_D0"));
            wreckage.collide(false);
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
            hierMesh().chunkVisible("Flap03_D0", false);
            FM.cut(19, j, actor);
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            FM.cut(31, j, actor);
            FM.cut(32, j, actor);
            // fall through

        case 34: // '"'
            com.maddox.il2.objects.Wreckage wreckage1 = new Wreckage(this, hierMesh().chunkFind("Flap02_D0"));
            wreckage1.collide(false);
            vector3d.set(FM.Vwld);
            wreckage1.setSpeed(vector3d);
            hierMesh().chunkVisible("Flap02_D0", false);
            break;

        case 36: // '$'
            FM.cut(20, j, actor);
            com.maddox.il2.objects.Wreckage wreckage2 = new Wreckage(this, hierMesh().chunkFind("Flap05_D0"));
            wreckage2.collide(false);
            vector3d.set(FM.Vwld);
            wreckage2.setSpeed(vector3d);
            hierMesh().chunkVisible("Flap05_D0", false);
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            FM.cut(31, j, actor);
            FM.cut(32, j, actor);
            // fall through

        case 37: // '%'
            com.maddox.il2.objects.Wreckage wreckage3 = new Wreckage(this, hierMesh().chunkFind("Flap04_D0"));
            wreckage3.collide(false);
            vector3d.set(FM.Vwld);
            wreckage3.setSpeed(vector3d);
            hierMesh().chunkVisible("Flap04_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public boolean bChangedPit;
    private float steera;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_38.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
