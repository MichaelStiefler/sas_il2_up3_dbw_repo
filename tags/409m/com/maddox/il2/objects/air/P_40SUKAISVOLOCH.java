// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40SUKAISVOLOCH.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, Aircraft, Cockpit, 
//            PaintScheme

public abstract class P_40SUKAISVOLOCH extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public P_40SUKAISVOLOCH()
    {
        flapps = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.5F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.09F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.02F, 0.09F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.79F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL21_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.79F, 0.0F, 94F), 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.39F, 0.0F, -53F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.79F, 0.0F, -30F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.79F, 0.0F, 100F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR21_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, -94F), 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.59F, 0.0F, -53F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, -30F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, 100F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, 0.26F);
        com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.CT.getGear(), 0.01F, 0.79F, 0.0F, 94F);
        hierMesh().chunkSetLocate("GearL21_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, -25F), 0.0F);
        hierMesh().chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.26F, 0.0F, -68.5F), 0.0F);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, 0.26F);
        com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.CT.getGear(), 0.01F, 0.79F, 0.0F, -94F);
        hierMesh().chunkSetLocate("GearR21_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, -25F), 0.0F);
        hierMesh().chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.26F, 0.0F, -68.5F), 0.0F);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.63F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
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
                hierMesh().chunkVisible("HMask1_D0", false);
                hierMesh().chunkVisible("Pilot1_D1", true);
            }
            break;
        }
    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null)
        {
            if(explosion.chunkName.startsWith("WingL"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 0);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 2);
            }
            if(explosion.chunkName.startsWith("WingR"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 3);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 4);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    FM.AS.setJamBullets(0, 5);
            }
        }
        super.msgExplosion(explosion);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
                if(s.endsWith("p1"))
                {
                    getEnergyPastArmor(15F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(4F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor(2.0F / (1E-005F + (float)java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)), shot);
            if(s.startsWith("xxcontrols"))
                if(s.endsWith("1"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                } else
                if(s.endsWith("2"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Controls Out..");
                    }
                } else
                if(s.endsWith("3"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls Out..");
                    }
                } else
                if((s.endsWith("4") || s.endsWith("5")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Ailerones Controls Out..");
                }
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("prp") && getEnergyPastArmor(0.1F, shot) > 0.0F)
                    FM.EI.engines[0].setKillPropAngleDevice(shot.initiator);
                if(s.endsWith("cas") && getEnergyPastArmor(0.7F, shot) > 0.0F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(20000F, 200000F) < shot.power)
                    {
                        FM.AS.setEngineStuck(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(10000F, 50000F) < shot.power)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(8000F, 28000F) < shot.power)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    }
                    FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                }
                if(s.endsWith("cyl") && getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.75F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(FM.AS.astateEngineStates[0] < 1)
                        FM.AS.hitEngine(shot.initiator, 0, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("sup") && getEnergyPastArmor(0.05F, shot) > 0.0F)
                    FM.EI.engines[0].setKillCompressor(shot.initiator);
                if(s.endsWith("sup"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setKillPropAngleDeviceSpeeds(shot.initiator);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setEngineStuck(shot.initiator);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[0].setEngineStops(shot.initiator);
                }
                if(s.endsWith("oil"))
                    FM.AS.hitOil(shot.initiator, 0);
            }
            if(s.startsWith("xxtank"))
            {
                int i = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitTank(shot.initiator, i, 1);
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                        FM.AS.hitTank(shot.initiator, i, 2);
                }
            }
            if(s.startsWith("xxmgun"))
            {
                int j = s.charAt(6) - 49;
                FM.AS.setJamBullets(0, j);
            }
            if(s.startsWith("xxammo"))
            {
                int k = s.charAt(6) - 48;
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 20000F) < shot.power)
                    switch(k)
                    {
                    case 1: // '\001'
                        FM.AS.setJamBullets(0, 2);
                        break;

                    case 2: // '\002'
                        FM.AS.setJamBullets(0, 3);
                        break;

                    case 3: // '\003'
                        FM.AS.setJamBullets(0, 4);
                        break;

                    case 4: // '\004'
                        FM.AS.setJamBullets(0, 5);
                        break;
                    }
            }
            return;
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
            hitChunk("CF", shot);
        if(s.startsWith("xcockpit"))
        {
            if(point3d.z > 0.75D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
            if(point3d.x > -1.1000000238418579D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                if(point3d.y > 0.0D)
                {
                    if(point3d.x > -1.1000000238418579D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                } else
                if(point3d.x > -1.1000000238418579D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
        } else
        if(s.startsWith("xeng"))
            hitChunk("Engine1", shot);
        else
        if(s.startsWith("xtail"))
            hitChunk("Tail1", shot);
        else
        if(s.startsWith("xkeel"))
            hitChunk("Keel1", shot);
        else
        if(s.startsWith("xrudder"))
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
        else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin"))
                hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin"))
                hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid"))
                hitChunk("WingLMid", shot);
            if(s.startsWith("xwingrmid"))
                hitChunk("WingRMid", shot);
            if(s.startsWith("xwinglout"))
                hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout"))
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

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 35: // '#'
            FM.AS.setJamBullets(0, 2);
            FM.AS.setJamBullets(0, 3);
            break;

        case 38: // '&'
            FM.AS.setJamBullets(0, 4);
            FM.AS.setJamBullets(0, 5);
            break;
        }
        switch(i)
        {
        case 11: // '\013'
        case 19: // '\023'
        case 33: // '!'
        case 34: // '"'
        case 35: // '#'
        case 36: // '$'
        case 37: // '%'
        case 38: // '&'
            hierMesh().chunkVisible("Wire_D0", false);
            // fall through

        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
        case 20: // '\024'
        case 21: // '\025'
        case 22: // '\026'
        case 23: // '\027'
        case 24: // '\030'
        case 25: // '\031'
        case 26: // '\032'
        case 27: // '\033'
        case 28: // '\034'
        case 29: // '\035'
        case 30: // '\036'
        case 31: // '\037'
        case 32: // ' '
        default:
            return super.cutFM(i, j, actor);
        }
    }

    public void update(float f)
    {
        float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 27F, -11F);
        if(java.lang.Math.abs(flapps - f1) > 0.2F)
        {
            flapps = f1;
            hierMesh().chunkSetAngles("Water2_D0", 0.0F, f1, 0.0F);
            hierMesh().chunkSetAngles("Water3_D0", 0.0F, f1, 0.0F);
            f1 = java.lang.Math.min(f1, 10.5F);
            hierMesh().chunkSetAngles("Water1_D0", 0.0F, f1, 0.0F);
            hierMesh().chunkSetAngles("Water4_D0", 0.0F, f1, 0.0F);
        }
        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40SUKAISVOLOCH.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
