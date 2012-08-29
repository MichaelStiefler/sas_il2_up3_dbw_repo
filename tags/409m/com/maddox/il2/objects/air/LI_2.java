// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LI_2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintSchemeBMPar02, TypeTransport, TypeBomber, 
//            Aircraft, NetAircraft, PaintScheme

public class LI_2 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeBomber
{

    public LI_2()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -120F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.LI_2.moveGear(hierMesh(), f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLOut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 6D)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingROut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 6D)
            FM.AS.hitTank(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 1.940000057220459D)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 1.940000057220459D)
            FM.AS.hitTank(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Nose") && com.maddox.il2.objects.air.Aircraft.Pd.x > 4.9000000953674316D && com.maddox.il2.objects.air.Aircraft.Pd.z > -0.090000003576278687D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            if(com.maddox.il2.objects.air.Aircraft.Pd.y > 0.0D)
            {
                killPilot(shot.initiator, 0);
                FM.setCapableOfBMP(false, shot.initiator);
            } else
            {
                killPilot(shot.initiator, 1);
            }
        if(shot.chunkName.startsWith("Turret1"))
        {
            killPilot(shot.initiator, 2);
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 1.3350000381469727D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            return;
        }
        if(shot.chunkName.startsWith("Turret2"))
        {
            FM.turret[1].bIsOperable = false;
            return;
        }
        if(shot.chunkName.startsWith("Turret3"))
        {
            FM.turret[2].bIsOperable = false;
            return;
        }
        if(shot.chunkName.startsWith("Tail") && com.maddox.il2.objects.air.Aircraft.Pd.x < -5.8000001907348633D && com.maddox.il2.objects.air.Aircraft.Pd.x > -6.7899999618530273D && com.maddox.il2.objects.air.Aircraft.Pd.z > -0.44900000000000001D && com.maddox.il2.objects.air.Aircraft.Pd.z < 0.12399999797344208D)
            FM.AS.hitPilot(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(3, 4), (int)(shot.mass * 1000F * com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.1F)));
        if(FM.AS.astateEngineStates[0] > 2 && FM.AS.astateEngineStates[1] > 2 && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
            FM.setCapableOfBMP(false, shot.initiator);
        super.msgShot(shot);
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            FM.turret[3].bIsOperable = false;
            break;

        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 3: // '\003'
            FM.turret[1].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[2].bIsOperable = false;
            break;
        }
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
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

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
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -5F)
            {
                f = -5F;
                flag = false;
            }
            if(f > 5F)
            {
                f = 5F;
                flag = false;
            }
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 5F)
            {
                f1 = 5F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            break;

        case 35: // '#'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                FM.AS.hitTank(this, 1, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;

        case 38: // '&'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                FM.AS.hitTank(this, 2, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;
        }
        return super.cutFM(i, j, actor);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.LI_2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Douglas");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Li-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Li-2.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB250", new java.lang.String[] {
            "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xPara", new java.lang.String[] {
            "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunPara 12"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "5xCargoA", new java.lang.String[] {
            "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunCargoA 5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
