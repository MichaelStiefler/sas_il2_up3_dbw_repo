// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeTNBFighter, PaintScheme, 
//            Aircraft, Cockpit

public abstract class JC_A6M extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public JC_A6M()
    {
        arrestor = 0.0F;
    }

    public void moveArrestorHook(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -37F * f, 0.0F);
        arrestor = f;
    }

    public void moveCockpitDoor(float f)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.65F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            ((com.maddox.il2.objects.sounds.SndAircraft)this).setDoorSnd(f);
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    public void update(float f)
    {
        for(int i = 1; i < 9; i++)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -32F * FM.EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
        else
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 46.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, -84.1F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, -135F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, 69F), 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.55F, 0.0F, -4.42F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, 84.1F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, 135F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.45F, 0.5F, 0.0F, -69F), 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.45F, 1.0F, 0.0F, 4.42F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.JC_A6M.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    public void moveWheelSink()
    {
        ((com.maddox.il2.objects.air.Aircraft)this).resetYPRmodifier();
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.16015F, 0.0F, 0.16015F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = f;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.16015F, 0.0F, 0.16015F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = f;
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveSteering(float f)
    {
        if(f > 77.5F)
        {
            f = 77.5F;
            FM.Gears.steerAngle = f;
        }
        if(f < -77.5F)
        {
            f = -77.5F;
            FM.Gears.steerAngle = f;
        }
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -60F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -60F * f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxcontrols"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Hit..");
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.675F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                    }
                    break;

                case 4: // '\004'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.13F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Elevator Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    break;

                case 5: // '\005'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;
                }
            } else
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("case"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - 0.002F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto #0 Destroyed..");
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag2"))
                {
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto #1 Destroyed..");
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("oil1"))
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Oil Radiator Hit..");
                }
            } else
            if(s.startsWith("xxlock"))
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "Rudder1_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorL_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "VatorR_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.startsWith("xxlockal") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "AroneL_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "AroneR_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("AroneR"), shot.initiator);
                }
            } else
            if(s.startsWith("xxmgun0"))
            {
                int j = s.charAt(7) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.75F, shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Machine Gun (" + j + ") Disabled..");
                    FM.AS.setJamBullets(0, j);
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
            } else
            if(s.startsWith("xxcannon0"))
            {
                int k = s.charAt(9) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.29F, shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon (" + k + ") Disabled..");
                    FM.AS.setJamBullets(1, k);
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
            } else
            if(s.startsWith("xxammo"))
            {
                if(s.startsWith("xxammol") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Machine Gun (0) Chain Broken..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.startsWith("xxammor") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Machine Gun (1) Chain Broken..");
                    FM.AS.setJamBullets(0, 1);
                }
                if(s.startsWith("xxammowl"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon Gun (0) Chain Broken..");
                        FM.AS.setJamBullets(1, 0);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon Gun (0) Payload Detonates..");
                        FM.AS.hitTank(shot.initiator, 0, 99);
                        ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "WingLIn_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn"), shot.initiator);
                    }
                }
                if(s.startsWith("xxammowr"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon Gun (1) Chain Broken..");
                        FM.AS.setJamBullets(1, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Armament: Cannon Gun (1) Payload Detonates..");
                        FM.AS.hitTank(shot.initiator, 1, 99);
                        ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(3, 2, "WingRIn_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                }
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(16F, shot);
            } else
            if(s.startsWith("xxoil"))
            {
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.22F, shot);
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Engine Module: Oil Tank Pierced..");
                }
            } else
            if(s.startsWith("xxtank"))
            {
                int l = s.charAt(6) - 49;
                if(l < 3 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    if(FM.AS.astateTankStates[l] == 0)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (" + l + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, l, 2);
                        FM.AS.doSetTankState(shot.initiator, l, 2);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.995F)
                    {
                        FM.AS.hitTank(shot.initiator, l, 4);
                        ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (" + l + "): Hit..");
                    }
                }
            } else
            if(s.startsWith("xxpnm"))
                FM.AS.setInternalDamage(shot.initiator, 1);
            else
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLIn_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn"), shot.initiator);
                }
                if(s.startsWith("xxsparri") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRIn_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn"), shot.initiator);
                }
                if(s.startsWith("xxsparlm") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid"), shot.initiator);
                }
                if(s.startsWith("xxsparrm") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid"), shot.initiator);
                }
                if(s.startsWith("xxsparlo") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut"), shot.initiator);
                }
                if(s.startsWith("xxsparro") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spar Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut"), shot.initiator);
                }
                if(s.startsWith("xxspark") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor((double)(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F)) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Keel Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "Keel1_D" + ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Keel1"), shot.initiator);
                }
                if(s.startsWith("xxspart") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(3.8599998950958252D / java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
            } else
            {
                s.startsWith("xxradio");
            }
        } else
        if(s.startsWith("xcf") || s.startsWith("xblister"))
        {
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
            if(point3d.x > -2.4199999999999999D && point3d.x < 0.32000000000000001D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    if(point3d.y > 0.0D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    if(point3d.y > 0.0D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                if(point3d.x < -0.27000000000000002D && point3d.z > 0.69999999999999996D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(point3d.x > -0.27000000000000002D && point3d.z > 0.69999999999999996D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
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
            if(s.startsWith("xstabl"))
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
            if(s.startsWith("xstabr"))
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
            if(s.startsWith("xaronel") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("AroneL") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneL", shot);
            if(s.startsWith("xaroner") && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("AroneR") < 1)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
        if(s.startsWith("xtank1"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.05F, shot) > 0.0F)
            {
                if(FM.AS.astateTankStates[3] == 0)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (E): Pierced..");
                    FM.AS.hitTank(shot.initiator, 3, 2);
                    FM.AS.doSetTankState(shot.initiator, 3, 2);
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.37F && shot.powerType == 3)
                {
                    FM.AS.hitTank(shot.initiator, 3, 2);
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("Fuel Tank (E): Hit..");
                }
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
            ((com.maddox.il2.objects.air.Aircraft)this).hitFlesh(i1, shot, ((int) (byte0)));
        }
    }

    public void replicateDropFuelTanks()
    {
        super.replicateDropFuelTanks();
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("ETank_D0", false);
        FM.AS.doSetTankState(((com.maddox.il2.engine.Actor) (null)), 3, 0);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.FuelTank)
                    ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("ETank_D0", true);

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

    protected float arrestor;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JC_A6M.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
    }
}
