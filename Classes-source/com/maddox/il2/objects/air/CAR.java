// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CAR.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, Aircraft, PaintScheme

public abstract class CAR extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public CAR()
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

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(super.FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 87.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -87.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 15.5F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 15.5F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC6_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC9_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 100F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("LanLight_D0", 0.0F, 0.0F, 92.5F * f);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.CAR.moveGear(hierMesh(), f);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31F * f, 0.0F);
        hierMesh().chunkSetAngles("GearC2", 0.0F, 0.0F, 40F * f);
        hierMesh().chunkSetAngles("GearC1", 0.0F, 0.0F, 40F * f);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xxengine") && getEnergyPastArmor(2.4F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getType() == 0 ? 1.75F : 0.5F))
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getType() == 0)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
            else
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19200F)));
            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 96000F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getType() == 0)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 96000F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getType() == 1)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }
            getEnergyPastArmor(43.6F, shot);
        }
        if(s.startsWith("xxtank"))
        {
            int i = s.charAt(6) - 48;
            switch(i)
            {
            default:
                break;

            case 1: // '\001'
                if(getEnergyPastArmor(0.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[2] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, 2, 1);
                    } else
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.9F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                    if(shot.power > 200000F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 99);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");
                    }
                }
                break;

            case 2: // '\002'
                if(getEnergyPastArmor(1.2F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                    break;
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[1] == 0)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 1);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, 1, 1);
                } else
                if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                }
                if(shot.power > 200000F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 99);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");
                }
                break;

            case 3: // '\003'
                if(getEnergyPastArmor(1.2F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                    break;
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[0] == 0)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 1);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, 0, 1);
                } else
                if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                }
                if(shot.power > 200000F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 99);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Major Hit..");
                }
                break;
            }
            return;
        } else
        {
            return;
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("CF"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.3F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x20);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 8);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
        }
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.06F && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 0.47999998927116394D)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
        }
        if(shot.chunkName.startsWith("Oil") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
        if(shot.chunkName.startsWith("Pilot"))
        {
            if((double)shot.power * java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) > 12070D)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitPilot(shot.initiator, 0, (int)(shot.mass * 1000F * 0.5F));
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > 1.0117499828338623D)
            {
                killPilot(shot.initiator, 0);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            }
            return;
        } else
        {
            super.msgShot(shot);
            return;
        }
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.CAR.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
