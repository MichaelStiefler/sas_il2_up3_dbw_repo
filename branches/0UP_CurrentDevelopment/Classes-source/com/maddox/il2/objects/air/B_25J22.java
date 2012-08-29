// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_25J22.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_25_Strafer, PaintSchemeBMPar03B25, TypeBomber, TypeStormovik, 
//            TypeStormovikArmored, NetAircraft

public class B_25J22 extends com.maddox.il2.objects.air.B_25_Strafer
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeStormovikArmored
{

    public B_25J22()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
        bChangedPit = false;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            killPilot(this, 4);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
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

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            break;

        case 4: // '\004'
            hierMesh().chunkVisible("Pilot5_D0", false);
            hierMesh().chunkVisible("HMask5_D0", false);
            hierMesh().chunkVisible("Pilot5_D1", true);
            break;

        case 5: // '\005'
            hierMesh().chunkVisible("Pilot6_D0", false);
            hierMesh().chunkVisible("HMask6_D0", false);
            hierMesh().chunkVisible("Pilot6_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
        {
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("HMask5_D0", false);
            hierMesh().chunkVisible("HMask6_D0", false);
        } else
        {
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
            hierMesh().chunkVisible("HMask4_D0", hierMesh().isChunkVisible("Pilot4_D0"));
            hierMesh().chunkVisible("HMask5_D0", hierMesh().isChunkVisible("Pilot5_D0"));
            hierMesh().chunkVisible("HMask6_D0", hierMesh().isChunkVisible("Pilot6_D0"));
        }
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
            return false;

        case 1: // '\001'
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f1 > 73F)
            {
                f1 = 73F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -38F)
            {
                f = -38F;
                flag = false;
            }
            if(f > 38F)
            {
                f = 38F;
                flag = false;
            }
            if(f1 < -41F)
            {
                f1 = -41F;
                flag = false;
            }
            if(f1 > 43F)
            {
                f1 = 43F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -85F)
            {
                f = -85F;
                flag = false;
            }
            if(f > 22F)
            {
                f = 22F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f1 > 32F)
            {
                f1 = 32F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -34F)
            {
                f = -34F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 32F)
            {
                f1 = 32F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].setHealth(f);
            break;

        case 3: // '\003'
            FM.turret[1].setHealth(f);
            break;

        case 4: // '\004'
            FM.turret[2].setHealth(f);
            break;

        case 5: // '\005'
            FM.turret[3].setHealth(f);
            FM.turret[4].setHealth(f);
            break;
        }
    }

    private static final float toMeters(float f)
    {
        return 0.3048F * f;
    }

    private static final float toMetersPerSecond(float f)
    {
        return 0.4470401F * f;
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
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public static boolean bChangedPit = false;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B_25J22.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-25");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-25J-22(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03B25());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-25J-22(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar03B25());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-25J-Strafer.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitB25J22.class, com.maddox.il2.objects.air.CockpitB25J_TGunner.class, com.maddox.il2.objects.air.CockpitB25J22_AGunner.class, com.maddox.il2.objects.air.CockpitB25J_RGunner.class, com.maddox.il2.objects.air.CockpitB25J_LGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        com.maddox.il2.objects.air.B_25J22.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 11, 11, 12, 12, 13, 14, 9, 9, 
            9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 
            2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.B_25J22.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN17", "_MGUN18", "_MGUN19", "_MGUN20", "_MGUN21", "_MGUN22", "_MGUN23", "_MGUN24", "_MGUN13", "_MGUN14", 
            "_MGUN15", "_MGUN16", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN11", "_MGUN12", "_ExternalDev01", "_ExternalDev02", 
            "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", 
            "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "40xParaF", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGunParafrag8 20", "BombGunParafrag8 20", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "12x100lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun50kg 6", "BombGun50kg 6", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "3x250lbs3x500lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "3x250lbs2x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "3x250lbs1x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGun1000lbs 1", null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "6x250lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun250lbs 3", "BombGun250lbs 3", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "8x250lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "PylonB25PLN2 1", "PylonB25PLN2 1", 
            "PylonB25PLN2 1", "PylonB25PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "4x500lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "4x500lbs1x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "6x500lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun500lbs 3", "BombGun500lbs 3", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "2x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "3x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "4x1000lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun1000lbs 2", "BombGun1000lbs 2", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "8xHVAR", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "8xHVARX6x500lbs", new java.lang.String[] {
            "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kiAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", 
            "MGunBrowning50kpzlAPI 400", "MGunBrowning50kpzlAPI 400", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 450", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", "MGunBrowning50tAPI 400", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "BombGun500lbs 3", "BombGun500lbs 3", 
            "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "PylonB25RAIL 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.B_25J22.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
