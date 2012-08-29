// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   LANCASTER.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme4, PaintSchemeBMPar00, TypeTransport, TypeBomber, 
//            Aircraft, NetAircraft, PaintScheme

public class LANCASTER extends com.maddox.il2.objects.air.Scheme4
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeBomber
{

    public LANCASTER()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 800F, -50F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, 14F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 55F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 55F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.LANCASTER.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", -f, 0.0F, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 25: // '\031'
            super.FM.turret[0].bIsOperable = false;
            break;

        case 26: // '\032'
            super.FM.turret[1].bIsOperable = false;
            break;

        case 27: // '\033'
            super.FM.turret[2].bIsOperable = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 65F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 65F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20F * f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        boolean flag = false;
        int i = 0;
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxammo"))
            {
                int j = s.charAt(6) - 48;
                if(s.length() > 7)
                    j = 10;
                if(getEnergyPastArmor(6.87F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                {
                    switch(j)
                    {
                    case 1: // '\001'
                        j = 10;
                        i = 0;
                        break;

                    case 2: // '\002'
                        j = 10;
                        i = 1;
                        break;

                    case 3: // '\003'
                        j = 11;
                        i = 0;
                        break;

                    case 4: // '\004'
                        j = 11;
                        i = 1;
                        break;

                    case 5: // '\005'
                        j = 12;
                        i = 0;
                        break;

                    case 6: // '\006'
                        j = 12;
                        i = 1;
                        break;

                    case 7: // '\007'
                        j = 13;
                        i = 0;
                        break;

                    case 8: // '\b'
                        j = 14;
                        i = 0;
                        break;

                    case 9: // '\t'
                        j = 15;
                        i = 0;
                        break;

                    case 10: // '\n'
                        j = 15;
                        i = 1;
                        break;
                    }
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(j, i);
                    return;
                }
            }
            if(s.startsWith("xxcontrols"))
            {
                int k = s.charAt(10) - 48;
                switch(k)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Controls Out..");
                    }
                    break;

                case 3: // '\003'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F && getEnergyPastArmor(5.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Control Column: Hit, Controls Destroyed..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                    }
                    getEnergyPastArmor(2.0F, shot);
                    break;

                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.252F && getEnergyPastArmor(5.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                    }
                    getEnergyPastArmor(2.0F, shot);
                    break;
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int l = s.charAt(5) - 49;
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, l);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, l, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].getReadyness() - 0.00082F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Crank Case Hit - Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].getCylindersRatio() * 0.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 18000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, l, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].setMagnetoKnockOut(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Module: Magneto #0 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag2"))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[l].setMagnetoKnockOut(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Module: Magneto #1 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("oil1") && getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setOilState(shot.initiator, l, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Module: Oil Filter Pierced..");
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr1") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockr2") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                if(s.startsWith("xxlockal") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(6.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                int i1 = s.charAt(5) - 49;
                if(getEnergyPastArmor(0.21F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2435F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, i1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + i1 + ") Module: Oil Tank Pierced..");
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 1.22F), shot) > 0.0F)
                {
                    debuggunnery("Pneumo System: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 1);
                }
                return;
            }
            if(s.startsWith("xxradio"))
            {
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(5F, 25F), shot);
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspark1") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), shot.initiator);
                }
                if(s.startsWith("xxspark2") && chunkDamageVisible("Keel2") > 1 && getEnergyPastArmor(16.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Keel2 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel2_D" + chunkDamageVisible("Keel2"), shot.initiator);
                }
                if(s.startsWith("xxsparsl") && chunkDamageVisible("StabL") > 1 && getEnergyPastArmor(11.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** StabL Spars Damaged..");
                    nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
                }
                if(s.startsWith("xxsparsr") && chunkDamageVisible("StabR") > 1 && getEnergyPastArmor(11.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** StabR Spars Damaged..");
                    nextDMGLevels(1, 2, "StabR_D2", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j1 = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.06F, shot) > 0.0F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[j1] == 0)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j1, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, j1, 1);
                    }
                    if(shot.powerType == 3)
                    {
                        if(shot.power < 16100F)
                        {
                            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[j1] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.21F)
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j1, 1);
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j1, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                        }
                    } else
                    if(shot.power > 16100F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, j1, com.maddox.il2.ai.World.Rnd().nextInt(1, 1 + (int)(shot.power / 16100F)));
                }
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
            return;
        }
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
            return;
        }
        if(s.startsWith("xkeel1"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
            return;
        }
        if(s.startsWith("xkeel2"))
        {
            if(chunkDamageVisible("Keel2") < 2)
                hitChunk("Keel2", shot);
            return;
        }
        if(s.startsWith("xrudder1"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
            return;
        }
        if(s.startsWith("xrudder2"))
        {
            if(chunkDamageVisible("Rudder2") < 1)
                hitChunk("Rudder2", shot);
            return;
        }
        if(s.startsWith("xstabl"))
        {
            if(chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
            return;
        }
        if(s.startsWith("xstabr"))
        {
            if(chunkDamageVisible("StabR") < 2)
                hitChunk("StabR", shot);
            return;
        }
        if(s.startsWith("xwinglin"))
        {
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            return;
        }
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            return;
        }
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            return;
        }
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
            return;
        }
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            return;
        }
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
            return;
        }
        if(s.startsWith("xaronel"))
        {
            if(chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
            return;
        }
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 1)
                hitChunk("AroneR", shot);
            return;
        }
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
            return;
        }
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
            return;
        }
        if(s.startsWith("xengine3"))
        {
            if(chunkDamageVisible("Engine3") < 2)
                hitChunk("Engine3", shot);
            return;
        }
        if(s.startsWith("xengine4"))
        {
            if(chunkDamageVisible("Engine4") < 2)
                hitChunk("Engine4", shot);
            return;
        }
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Gear Hydro Failed..");
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.setHydroOperable(false);
            }
            return;
        }
        if(s.startsWith("xturret"))
            return;
        if(s.startsWith("xmgun"))
        {
            int k1 = 10 * (s.charAt(5) - 48) + (s.charAt(6) - 48);
            if(getEnergyPastArmor(6.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.35F)
            {
                switch(k1)
                {
                case 1: // '\001'
                    k1 = 10;
                    i = 0;
                    break;

                case 2: // '\002'
                    k1 = 10;
                    i = 1;
                    break;

                case 3: // '\003'
                    k1 = 11;
                    i = 0;
                    break;

                case 4: // '\004'
                    k1 = 11;
                    i = 1;
                    break;

                case 5: // '\005'
                    k1 = 12;
                    i = 0;
                    break;

                case 6: // '\006'
                    k1 = 12;
                    i = 1;
                    break;

                case 7: // '\007'
                    k1 = 13;
                    i = 0;
                    break;

                case 8: // '\b'
                    k1 = 14;
                    i = 0;
                    break;

                case 9: // '\t'
                    k1 = 15;
                    i = 0;
                    break;

                case 10: // '\n'
                    k1 = 15;
                    i = 1;
                    break;
                }
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(k1, i);
            }
            return;
        }
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l1 = s.charAt(6) - 49;
            } else
            {
                l1 = s.charAt(5) - 49;
            }
            hitFlesh(l1, shot, ((int) (byte0)));
            return;
        } else
        {
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null && explosion.power > 0.0F)
        {
            if(explosion.chunkName.equals("Tail1_D3"))
                return;
            if(explosion.chunkName.equals("WingLIn_D3"))
                return;
            if(explosion.chunkName.equals("WingRIn_D3"))
                return;
            if(explosion.chunkName.equals("WingLMid_D3"))
                return;
            if(explosion.chunkName.equals("WingRMid_D3"))
                return;
            if(explosion.chunkName.equals("WingLOut_D3"))
                return;
            if(explosion.chunkName.equals("WingROut_D3"))
                return;
        }
        super.msgExplosion(explosion);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine3") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine4") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("CF"))
        {
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x > 4.5500001907348633D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x < 7.1500000953674316D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 0.57999998331069946D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.233F)
                    if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 1.2100000381469727D)
                    {
                        killPilot(shot.initiator, 0);
                        if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                            com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 0, (int)(shot.power * 0.004F));
                    }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.233F)
                    if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 1.2100000381469727D)
                    {
                        killPilot(shot.initiator, 1);
                        if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                            com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 1, (int)(shot.power * 0.004F));
                    }
            }
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x > 9.5299997329711914D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z < 0.14000000059604645D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > -0.62999999523162842D)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 2, (int)(shot.power * 0.002F));
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x > 2.4749999046325684D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x < 4.4899997711181641D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 0.61000001430511475D && (double)shot.power * java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z) > 11900D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
            {
                for(int i = 0; i < 4; i++)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, i, 0);

            }
        }
        if(shot.chunkName.startsWith("Turret1"))
        {
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 0.033449999988079071D)
            {
                killPilot(shot.initiator, 2);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 2, (int)(shot.power * 0.004F));
            }
            shot.chunkName = "CF_D" + chunkDamageVisible("CF");
        }
        if(shot.chunkName.startsWith("Turret2"))
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 4, (int)(shot.power * 0.004F));
            else
                super.FM.turret[1].bIsOperable = false;
        if(shot.chunkName.startsWith("Turret3"))
        {
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 0.30445000529289246D)
            {
                killPilot(shot.initiator, 7);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 7, (int)(shot.power * 0.002F));
            }
            shot.chunkName = "Tail1_D" + chunkDamageVisible("Tail1");
        }
        if(shot.chunkName.startsWith("Turret4"))
        {
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > -0.99540001153945923D)
            {
                killPilot(shot.initiator, 5);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 5, (int)(shot.power * 0.002F));
            }
        } else
        if(shot.chunkName.startsWith("Turret5"))
        {
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > -0.99540001153945923D)
            {
                killPilot(shot.initiator, 6);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 6, (int)(shot.power * 0.002F));
            }
        } else
        {
            super.msgShot(shot);
        }
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 4: // '\004'
            super.FM.turret[0].bIsOperable = false;
            break;

        case 3: // '\003'
            super.FM.turret[1].bIsOperable = false;
            break;

        case 2: // '\002'
            super.FM.turret[2].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("Head2_D0", false);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("Head3_D0", false);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            hierMesh().chunkVisible("Head4_D0", false);
            break;

        case 5: // '\005'
            hierMesh().chunkVisible("Pilot6_D0", false);
            hierMesh().chunkVisible("HMask6_D0", false);
            hierMesh().chunkVisible("Pilot6_D1", true);
            hierMesh().chunkVisible("Head5_D0", false);
            break;

        case 6: // '\006'
            hierMesh().chunkVisible("Pilot7_D0", false);
            hierMesh().chunkVisible("HMask7_D0", false);
            hierMesh().chunkVisible("Pilot7_D1", true);
            hierMesh().chunkVisible("Head6_D0", false);
            break;

        case 7: // '\007'
            hierMesh().chunkVisible("Pilot8_D0", false);
            hierMesh().chunkVisible("HMask8_D0", false);
            hierMesh().chunkVisible("Pilot8_D1", true);
            hierMesh().chunkVisible("Head7_D0", false);
            break;
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -76F)
            {
                f = -76F;
                flag = false;
            }
            if(f > 76F)
            {
                f = 76F;
                flag = false;
            }
            if(f1 < -47F)
            {
                f1 = -47F;
                flag = false;
            }
            if(f1 > 47F)
            {
                f1 = 47F;
                flag = false;
            }
            break;

        case 1: // '\001'
            float f2 = java.lang.Math.abs(f);
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            if(f2 < 1.0F)
            {
                if(f1 < 17F)
                {
                    f1 = 17F;
                    flag = false;
                }
                break;
            }
            if(f2 < 4.5F)
            {
                if(f1 < 0.71429F - 0.71429F * f2)
                {
                    f1 = 0.71429F - 0.71429F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 29.5F)
            {
                if(f1 < -2.5F)
                {
                    f1 = -2.5F;
                    flag = false;
                }
                break;
            }
            if(f2 < 46F)
            {
                if(f1 < 52.0303F - 1.84848F * f2)
                {
                    f1 = 52.0303F - 1.84848F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 89F)
            {
                if(f1 < -70.73518F + 0.80232F * f2)
                {
                    f1 = -70.73518F + 0.80232F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 147F)
            {
                if(f1 < 1.5F)
                {
                    f1 = 1.5F;
                    flag = false;
                }
                break;
            }
            if(f2 < 162F)
            {
                if(f1 < -292.5F + 2.0F * f2)
                {
                    f1 = -292.5F + 2.0F * f2;
                    flag = false;
                }
                break;
            }
            if(f1 < 31.5F)
            {
                f1 = 31.5F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -87F)
            {
                f = -87F;
                flag = false;
            }
            if(f > 87F)
            {
                f = 87F;
                flag = false;
            }
            if(f1 < -78F)
            {
                f1 = -78F;
                flag = false;
            }
            if(f1 > 67F)
            {
                f1 = 67F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 16F)
            {
                f1 = 16F;
                flag = false;
            }
            if(f < -60F)
            {
                f = -60F;
                flag = false;
                if(f1 > -11.5F)
                    f1 = -11.5F;
                break;
            }
            if(f < -13.5F)
            {
                if(f1 > 3.9836F + 0.25806F * f)
                {
                    f1 = 3.9836F + 0.25806F * f;
                    flag = false;
                }
                break;
            }
            if(f < -10.5F)
            {
                if(f1 > 16.25005F + 1.16667F * f)
                {
                    f1 = 16.25005F + 1.16667F * f;
                    flag = false;
                }
                break;
            }
            if(f < 14F)
            {
                if(f1 > 5F)
                    flag = false;
                break;
            }
            if(f < 80F)
            {
                if(f1 > 8F)
                    flag = false;
            } else
            {
                f = 80F;
                flag = false;
            }
            break;

        case 4: // '\004'
            f = -f;
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 16F)
            {
                f1 = 16F;
                flag = false;
            }
            if(f < -60F)
            {
                f = -60F;
                flag = false;
                if(f1 > -11.5F)
                    f1 = -11.5F;
            } else
            if(f < -13.5F)
            {
                if(f1 > 3.9836F + 0.25806F * f)
                {
                    f1 = 3.9836F + 0.25806F * f;
                    flag = false;
                }
            } else
            if(f < -10.5F)
            {
                if(f1 > 16.25005F + 1.16667F * f)
                {
                    f1 = 16.25005F + 1.16667F * f;
                    flag = false;
                }
            } else
            if(f < 14F)
            {
                if(f1 > 5F)
                    flag = false;
            } else
            if(f < 80F)
            {
                if(f1 > 8F)
                    flag = false;
            } else
            {
                f = 80F;
                flag = false;
            }
            f = -f;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 300F)
            fSightCurAltitude = 300F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 50F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 650F)
            fSightCurSpeed = 650F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 50F)
            fSightCurSpeed = 50F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        d -= (double)(fSightCurAltitude * fSightCurAltitude) * 1.419E-005D;
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.LANCASTER.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Lancaster");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Lancaster/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1940F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Lancaster.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitLanc.class, com.maddox.il2.objects.air.CockpitLanc_Bombardier.class, com.maddox.il2.objects.air.CockpitLanc_FGunner.class, com.maddox.il2.objects.air.CockpitLanc_TGunner.class, com.maddox.il2.objects.air.CockpitLanc_AGunner.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.73425F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 11, 12, 12, 10, 10, 3, 3, 
            3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN09", "_MGUN10", "_BombSpawn01", "_BombSpawn02", 
            "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16x250", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun250lbsE 2", "BombGun250lbsE 2", 
            "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24x250", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun250lbsE 4", "BombGun250lbsE 4", 
            "BombGun250lbsE 4", null, null, "BombGun250lbsE 4", "BombGun250lbsE 4", "BombGun250lbsE 4"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x500", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun500lbsE 2", null, 
            "BombGun500lbsE 2", null, null, "BombGun500lbsE 2", null, "BombGun500lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x500", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun500lbsE 2", "BombGun500lbsE 2", 
            "BombGun500lbsE 2", null, null, "BombGun500lbsE 2", "BombGun500lbsE 2", "BombGun500lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x1000", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun1000lbs 1", null, 
            "BombGun1000lbs 1", null, null, "BombGun1000lbs 1", null, "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x1000", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun1000lbs 1", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x4000", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, null, 
            null, null, "BombGunHC4000 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x12000", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, null, 
            null, null, "BombGun12000Tallboy 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderRed", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, "BombGunTIRed 1", 
            "BombGunTIRed 1", "BombGunTIRed 2", "BombGunTIRed 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderGreen", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, "BombGunTIGreen 1", 
            "BombGunTIGreen 1", "BombGunTIGreen 2", "BombGunTIGreen 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderYellow", new java.lang.String[] {
            "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", "MGunBrowning303t 2000", null, "BombGunTIYellow 1", 
            "BombGunTIYellow 1", "BombGunTIYellow 2", "BombGunTIYellow 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
