package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public class BombsightNorden
{

    public BombsightNorden()
    {
        BombsightNorden.ResetAll(0, null);
    }

    private static final float toMeters(float f)
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            return f;

        case 1: // '\001'
        case 2: // '\002'
            return 0.3048F * f;
        }
        return 0.0F;
    }

    private static final float toMetersPerSecond(float f)
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            return f / 3.7F;

        case 1: // '\001'
            return 0.4470401F * f;

        case 2: // '\002'
            return 0.514F * f;
        }
        return 0.0F;
    }

    private static final float fromMeters(float f)
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            return f;

        case 1: // '\001'
        case 2: // '\002'
            return 3.2808F * f;
        }
        return 0.0F;
    }

    private static final float fromMetersPerSecond(float f)
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            return f * 3.6F;

        case 1: // '\001'
            return f * 2.237F;

        case 2: // '\002'
            return f * 1.946F;
        }
        return 0.0F;
    }

    private static void SetCurrentBombIndex()
    {
        nCurrentBombIndex = 0;
        if(null == ActiveBombNames)
            return;
        if(nCurrentBombStringIndex >= ActiveBombNames.length)
            nCurrentBombStringIndex = 0;
        for(int i = 0; i < BombDescs.length; i++)
        {
            if(!BombDescs[i].sBombName.equals(ActiveBombNames[nCurrentBombStringIndex]))
                continue;
            nCurrentBombIndex = i;
            break;
        }

    }

    public static void ResetAll(int i, Aircraft aircraft)
    {
        TypeCurrent = i;
        switch(TypeCurrent)
        {
        case 0: // '\0'
            fSightCurAltitude = 3000F;
            fSightCurSpeed = 400F;
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurAltitude = 9000F;
            fSightCurSpeed = 250F;
            break;

        default:
            fSightCurAltitude = 0.0F;
            fSightCurSpeed = 0.0F;
            break;
        }
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurDistance = 0.0F;
        fSightCurReadyness = 0.0F;
        bSightAutomation = false;
        bSightBombDump = false;
        currentCraft = aircraft;
        nNumBombsToRelease = 0;
        nNumBombsReleased = 0;
        fBombDropDelay = 0.25F;
        fDelayLeft = 0.0F;
        nCurrentBombIndex = 0;
        nCurrentBombStringIndex = 0;
        BombsightNorden.SetCurrentBombIndex();
        BombsightNorden.RecalculateDistance();
    }

    public static void SetActiveBombNames(java.lang.String as[])
    {
        ActiveBombNames = as;
    }

    public static boolean ToggleAutomation()
    {
        bSightAutomation = !bSightAutomation;
        bSightBombDump = false;
        nNumBombsReleased = 0;
        fDelayLeft = 0.0F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (bSightAutomation ? "ON" : "OFF"));
        return bSightAutomation;
    }

    public static void AdjDistanceReset()
    {
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
    }

    public static void AdjDistancePlus()
    {
        fSightCurForwardAngle++;
        if(fSightCurForwardAngle > 85F)
            fSightCurForwardAngle = 85F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        BombsightNorden.RecalculateDistance();
        if(bSightAutomation)
            BombsightNorden.ToggleAutomation();
    }

    public static void AdjDistanceMinus()
    {
        fSightCurForwardAngle--;
        if(fSightCurForwardAngle < 0.0F)
            fSightCurForwardAngle = 0.0F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        BombsightNorden.RecalculateDistance();
        if(bSightAutomation)
            BombsightNorden.ToggleAutomation();
    }

    public static void AdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public static void AdjSideslipPlus()
    {
        nNumBombsToRelease++;
        if(nNumBombsToRelease > 10)
            nNumBombsToRelease = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Number of bomb drops: " + nNumBombsToRelease);
    }

    public static void AdjSideslipMinus()
    {
        fBombDropDelay += 0.25F;
        if(fBombDropDelay > 5F)
            fBombDropDelay = 0.25F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Bomb drop delay (sec): " + fBombDropDelay);
    }

    public static void AdjAltitudeReset()
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            fSightCurAltitude = 3000F;
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurAltitude = 9000F;
            break;

        default:
            fSightCurAltitude = 0.0F;
            break;
        }
        BombsightNorden.RecalculateDistance();
    }

    public static void AdjAltitudePlus()
    {
        switch(TypeCurrent)
        {
        default:
            break;

        case 0: // '\0'
            fSightCurAltitude += 50F;
            if(fSightCurAltitude > 10000F)
                fSightCurAltitude = 10000F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurAltitude += 50F;
            if(fSightCurAltitude > 30000F)
                fSightCurAltitude = 30000F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            break;
        }
        BombsightNorden.RecalculateDistance();
    }

    public static void AdjAltitudeMinus()
    {
        switch(TypeCurrent)
        {
        default:
            break;

        case 0: // '\0'
            fSightCurAltitude -= 50F;
            if(fSightCurAltitude < 500F)
                fSightCurAltitude = 500F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurAltitude -= 50F;
            if(fSightCurAltitude < 1500F)
                fSightCurAltitude = 1500F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            break;
        }
        BombsightNorden.RecalculateDistance();
    }

    public static void AdjSpeedReset()
    {
        switch(TypeCurrent)
        {
        case 0: // '\0'
            fSightCurSpeed = 400F;
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurSpeed = 250F;
            break;

        default:
            fSightCurSpeed = 0.0F;
            break;
        }
    }

    public static void AdjSpeedPlus()
    {
        switch(TypeCurrent)
        {
        default:
            break;

        case 0: // '\0'
            fSightCurSpeed += 5F;
            if(fSightCurSpeed > 600F)
                fSightCurSpeed = 600F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
                new Integer((int)fSightCurSpeed)
            });
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurSpeed += 5F;
            if(fSightCurSpeed > 450F)
                fSightCurSpeed = 450F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
                new Integer((int)fSightCurSpeed)
            });
            break;
        }
    }

    public static void AdjSpeedMinus()
    {
        switch(TypeCurrent)
        {
        default:
            break;

        case 0: // '\0'
            fSightCurSpeed -= 5F;
            if(fSightCurSpeed < 150F)
                fSightCurSpeed = 150F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
                new Integer((int)fSightCurSpeed)
            });
            break;

        case 1: // '\001'
        case 2: // '\002'
            fSightCurSpeed -= 5F;
            if(fSightCurSpeed < 100F)
                fSightCurSpeed = 100F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
                new Integer((int)fSightCurSpeed)
            });
            break;
        }
    }

    public static void RecalculateDistance()
    {
        fSightCurDistance = BombsightNorden.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public static void OnCCIP(float f, float f1)
    {
        fSightCurSpeed = BombsightNorden.fromMetersPerSecond(f);
        fSightCurAltitude = BombsightNorden.fromMeters(f1);
        BombsightNorden.RecalculateDistance();
    }

    public static void Update(float f)
    {
        if(null == currentCraft)
            return;
        if((double)java.lang.Math.abs(currentCraft.FM.Or.getKren()) > 4.5D)
        {
            fSightCurReadyness -= 0.0666666F * f;
            if(fSightCurReadyness < 0.0F)
                fSightCurReadyness = 0.0F;
        }
        if(fSightCurReadyness < 1.0F)
            fSightCurReadyness += 0.0333333F * f;
        else
        if(bSightAutomation)
        {
            double d = BombsightNorden.toMetersPerSecond(fSightCurSpeed);
            double d1 = BombsightNorden.toMeters(fSightCurAltitude);
            fSightCurDistance = (float)((double)fSightCurDistance - d * (double)f);
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                BombsightNorden.ToggleAutomation();
                return;
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan((double)fSightCurDistance / d1));
            double d2 = d * java.lang.Math.sqrt(d1 * 0.20387359799999999D);
            double d3 = BombDescs[nCurrentBombIndex].GetCorrectionCoeff(d1);
            d2 += d3 * d1 * d;
            if((double)fSightCurDistance < d2)
                bSightBombDump = true;
            if(bSightBombDump)
                if(currentCraft.FM.CT.Weapons[3] != null && currentCraft.FM.CT.Weapons[3][currentCraft.FM.CT.Weapons[3].length - 1] != null && currentCraft.FM.CT.Weapons[3][currentCraft.FM.CT.Weapons[3].length - 1].haveBullets())
                {
                    if(fDelayLeft <= 0.0F)
                    {
                        fDelayLeft = fBombDropDelay;
                        if(nNumBombsReleased < nNumBombsToRelease || 0 == nNumBombsToRelease)
                        {
                            currentCraft.FM.CT.WeaponControl[3] = true;
                            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
                            nNumBombsReleased++;
                        } else
                        {
                            currentCraft.FM.CT.WeaponControl[3] = false;
                            BombsightNorden.ToggleAutomation();
                        }
                    } else
                    {
                        fDelayLeft -= f;
                        currentCraft.FM.CT.WeaponControl[3] = false;
                    }
                } else
                {
                    currentCraft.FM.CT.WeaponControl[3] = false;
                    BombsightNorden.ToggleAutomation();
                }
        }
    }

    public static void ReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte((bSightAutomation ? 1 : 0) | (bSightBombDump ? 2 : 0));
        netmsgguaranted.writeFloat(fSightCurDistance);
        netmsgguaranted.writeByte((int)fSightCurForwardAngle);
        netmsgguaranted.writeByte((int)((fSightCurSideslip + 3F) * 33.33333F));
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeByte((int)(fSightCurSpeed / 2.5F));
        netmsgguaranted.writeByte((int)(fSightCurReadyness * 200F));
    }

    public static void ReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        bSightAutomation = (i & 1) != 0;
        bSightBombDump = (i & 2) != 0;
        fSightCurDistance = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readUnsignedByte();
        fSightCurSideslip = -3F + (float)netmsginput.readUnsignedByte() / 33.33333F;
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = (float)netmsginput.readUnsignedByte() * 2.5F;
        fSightCurReadyness = (float)netmsginput.readUnsignedByte() / 200F;
    }

    public static int TYPE_METRICK = 0;
    public static int TYPE_ENGLISH = 1;
    public static int TYPE_NAVAL = 2;
    public static int TypeCurrent = 0;
    public static float fSightCurAltitude;
    public static float fSightCurSpeed;
    public static float fSightCurForwardAngle;
    public static float fSightCurSideslip;
    public static float fSightCurReadyness;
    private static int nNumBombsToRelease;
    private static int nNumBombsReleased;
    private static float fBombDropDelay;
    private static float fDelayLeft;
    private static boolean bSightAutomation;
    private static boolean bSightBombDump;
    private static float fSightCurDistance;
    private static Aircraft currentCraft;
    private static BombDescriptor BombDescs[] = {
        new BombDescriptor("FAB-100", new double[] {
            0.00020719999999999999D, 0.0001216D, 0.0001417D, 0.0001036D, 0.0001295D, 8.967E-005D, 0.00011069999999999999D, 0.0001108D, 9.1299999999999997E-005D, 9.4489999999999998E-005D, 
            9.3469999999999998E-005D, 0.0001042D, 9.1840000000000002E-005D, 0.00013180000000000001D, 0.0001329D, 0.00012679999999999999D, 0.0001261D
        }), new BombDescriptor("FAB-250", new double[] {
            0.00020719999999999999D, 0.00022159999999999999D, 0.0002084D, 0.00020359999999999999D, 0.0002095D, 0.0001897D, 0.0001964D, 0.00018579999999999999D, 0.00018019999999999999D, 0.00019450000000000001D, 
            0.00020259999999999999D, 0.0002042D, 0.00021489999999999999D, 0.00020330000000000001D, 0.0002262D, 0.00025179999999999999D, 0.00026719999999999999D
        }), new BombDescriptor("FAB-500", new double[] {
            0.00020719999999999999D, 0.00032160000000000001D, 0.00040840000000000001D, 0.00045360000000000002D, 0.00048950000000000003D, 0.00052300000000000003D, 0.0005107D, 0.00051079999999999995D, 0.00051349999999999996D, 0.00049450000000000004D, 
            0.00051170000000000002D, 0.0005375D, 0.00055340000000000001D, 0.00057470000000000004D, 0.00059960000000000005D, 0.00063929999999999998D, 0.00065550000000000005D
        }), new BombDescriptor("FAB-1000", new double[] {
            0.00020719999999999999D, 0.0001216D, 0.0001417D, 0.00015359999999999999D, 0.0001695D, 0.0001897D, 0.0001964D, 0.00018579999999999999D, 0.00020239999999999999D, 0.00021450000000000001D, 
            0.00023890000000000001D, 0.0002542D, 0.0002765D, 0.00028899999999999998D, 0.00030620000000000002D, 0.0003143D, 0.00032610000000000001D
        }), new BombDescriptor("FAB-2000", new double[] {
            7.2289999999999997E-006D, 2.1549999999999999E-005D, 7.5030000000000005E-005D, 0.0001036D, 8.9519999999999997E-005D, 0.00012300000000000001D, 0.00016780000000000001D, 0.00018579999999999999D, 0.00020239999999999999D, 0.00021450000000000001D, 
            0.00023890000000000001D, 0.00027080000000000002D, 0.00030719999999999999D, 0.00033179999999999999D, 0.00038620000000000001D, 0.00042680000000000002D, 0.00046720000000000003D
        }), new BombDescriptor("FAB-5000", new double[] {
            7.2289999999999997E-006D, 2.1549999999999999E-005D, 8.3650000000000004E-006D, 3.6150000000000001E-006D, 4.952E-005D, 5.6339999999999999E-005D, 8.2139999999999996E-005D, 0.0001108D, 0.00011349999999999999D, 0.00013449999999999999D, 
            0.0001662D, 0.0001875D, 0.00021489999999999999D, 0.0002318D, 0.00030620000000000002D, 0.00038929999999999998D, 0.00046720000000000003D
        })
    };
    private static int nCurrentBombIndex;
    private static int nCurrentBombStringIndex;
    private static java.lang.String ActiveBombNames[] = null;

}
