// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_262.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, ME_262HGII, TypeFighter, TypeBNZFighter, 
//            Aircraft, PaintScheme

public class ME_262 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public ME_262()
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
            if(!FM.AS.bIsAboutToBailout)
            {
                if(hierMesh().isChunkVisible("Blister1_D0"))
                    hierMesh().chunkVisible("Gore1_D0", true);
                hierMesh().chunkVisible("Gore2_D0", true);
            }
            break;
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 111F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC21_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.11F, 0.0F, 90F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 73F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 88F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.ME_262.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        float f = FM.Gears.gWheelSinking[2];
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.19F, 0.0F, 0.19F);
        hierMesh().chunkSetLocate("GearC22_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 19F, 0.0F, 30F);
        hierMesh().chunkSetAngles("GearC7_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("GearC8_D0", 0.0F, 2.0F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        if(FM.CT.getGear() > 0.75F)
            hierMesh().chunkSetAngles("GearC21_D0", 0.0F, 40F * f, 0.0F);
    }

    protected void moveFan(float f)
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(point3d.x > 1.7D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                    FM.AS.setJamBullets(0, 0);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                    FM.AS.setJamBullets(0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setJamBullets(1, 0);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setJamBullets(1, 1);
            }
            if(point3d.x > -0.999D && point3d.x < 0.53500000000000003D && point3d.z > -0.224D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
            }
            if(point3d.x > 0.80000000000000004D && point3d.x < 1.5800000000000001D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && (shot.powerType == 3 && getEnergyPastArmor(0.4F, shot) > 0.0F || shot.powerType == 0))
                FM.AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4000F)));
            if(point3d.x > -2.4849999999999999D && point3d.x < -1.6000000000000001D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && (shot.powerType == 3 && getEnergyPastArmor(0.4F, shot) > 0.0F || shot.powerType == 0))
                FM.AS.hitTank(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4000F)));
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
            hitChunk("Keel1", shot);
        else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xwing"))
        {
            if(s.endsWith("lin") && chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(s.endsWith("rin") && chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(s.endsWith("lmid") && chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            if(s.endsWith("rmid") && chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
            if(s.endsWith("lout") && chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            if(s.endsWith("rout") && chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xengine"))
        {
            int i = s.charAt(7) - 49;
            if(point3d.x > 0.0D && point3d.x < 0.69699999999999995D)
                FM.EI.engines[i].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, 6));
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass)
                FM.AS.hitEngine(shot.initiator, i, 5);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int j;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j = s.charAt(6) - 49;
            } else
            {
                j = s.charAt(5) - 49;
            }
            hitFlesh(j, shot, byte0);
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

        case 11: // '\013'
            cutFM(17, j, actor);
            FM.cut(17, j, actor);
            cutFM(18, j, actor);
            FM.cut(18, j, actor);
            return super.cutFM(i, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
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

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL0_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            hierMesh().chunkSetAngles("SlatL1_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            hierMesh().chunkSetAngles("SlatR0_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            hierMesh().chunkSetAngles("SlatR1_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            if(!(this instanceof com.maddox.il2.objects.air.ME_262HGII))
            {
                hierMesh().chunkSetAngles("SlatL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
                hierMesh().chunkSetAngles("SlatR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, -1F), 0.0F);
            }
        }
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float oldctl[] = {
        -1F, -1F
    };
    private float curctl[] = {
        -1F, -1F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
