// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SU_2.java

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
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeBMPar01, TypeScout, TypeBomber, 
//            TypeStormovik, NetAircraft, PaintScheme

public class SU_2 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeStormovik
{

    public SU_2()
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -90F * f, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL2a_D0", 0.0F, -42F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -30F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -140F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2a_D0", 0.0F, -42F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -30F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -140F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -40F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.SU_2.moveGear(hierMesh(), f);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
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
        if(s.startsWith("xwinglin"))
        {
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 0, 1);
        } else
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 1, 1);
        } else
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
        else
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
            if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.5F)
            {
                FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                if(FM.AS.astateEngineStates[0] < 1)
                {
                    FM.AS.hitEngine(shot.initiator, 0, 1);
                    FM.AS.doSetEngineState(shot.initiator, 0, 1);
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 960000F)
                    FM.AS.hitEngine(shot.initiator, 0, 3);
                getEnergyPastArmor(25F, shot);
            }
        } else
        if(s.startsWith("xgearl"))
            hitChunk("GearL2", shot);
        else
        if(s.startsWith("xgearr"))
            hitChunk("GearR2", shot);
        else
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
                FM.AS.setJamBullets(10, 0);
            if(s.startsWith("xturret2"))
                FM.AS.setJamBullets(11, 0);
            if(s.startsWith("xturret3"))
                FM.AS.setJamBullets(12, 0);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            hitFlesh(i, shot, byte0);
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -90F)
            {
                f = -90F;
                flag = false;
            }
            if(f > 90F)
            {
                f = 90F;
                flag = false;
            }
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
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
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
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
            break;
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 11: // '\013'
        case 19: // '\023'
            hierMesh().chunkVisible("Wire_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        float f1 = com.maddox.il2.objects.air.SU_2.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -10F);
        for(int i = 1; i < 17; i++)
            hierMesh().chunkSetAngles("cowlflap" + i + "_D0", 0.0F, f1, 0.0F);

        super.update(f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SU_2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Su-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Su-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Su-2.fmd");
        com.maddox.il2.objects.air.SU_2.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.SU_2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, null, null
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "30ao10", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunAO10 15", "BombGunAO10 15"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "4fab50", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunFAB50 2", "BombGunFAB50 2"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "6fab50", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 2", "BombGunFAB50 2"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "4fab100", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunFAB100 2", "BombGunFAB100 2"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "6fab100", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 2", "BombGunFAB100 2"
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB250 1", "BombGunFAB250 1", null, null
        });
        com.maddox.il2.objects.air.SU_2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}