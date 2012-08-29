// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombsightOPB.java

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            BombDescriptor

class BombsightOPB
{

    public BombsightOPB()
    {
        com.maddox.il2.objects.air.BombsightOPB.ResetAll();
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

    public static void ResetAll()
    {
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 400F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        nCurrentBombIndex = 0;
        nCurrentBombStringIndex = 0;
        com.maddox.il2.objects.air.BombsightOPB.SetCurrentBombIndex();
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void SetActiveBombNames(java.lang.String as[])
    {
        ActiveBombNames = as;
    }

    public static boolean ToggleAutomation()
    {
        nCurrentBombStringIndex++;
        com.maddox.il2.objects.air.BombsightOPB.SetCurrentBombIndex();
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Bomb type: " + BombDescs[nCurrentBombIndex].sBombName);
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
        return false;
    }

    public static void AdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public static void AdjDistancePlus()
    {
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public static void AdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public static void AdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public static void AdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public static void AdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public static void AdjAltitudeReset()
    {
        fSightCurAltitude = 3000F;
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void AdjAltitudePlus()
    {
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 10000F)
            fSightCurAltitude = 10000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void AdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 500F)
            fSightCurAltitude = 500F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void AdjSpeedReset()
    {
        fSightCurSpeed = 400F;
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void AdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 600F)
            fSightCurSpeed = 600F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void AdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 150F)
            fSightCurSpeed = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void RecalculateAngle()
    {
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        double d1 = BombDescs[nCurrentBombIndex].GetCorrectionCoeff(fSightCurAltitude);
        d += (d1 * (double)fSightCurAltitude * (double)fSightCurSpeed) / 3.6000000000000001D;
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public static void OnCCIP(float f, float f1)
    {
        fSightCurSpeed = f;
        fSightCurAltitude = f1;
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static void Update(float f)
    {
    }

    public static void ReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public static void ReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
        com.maddox.il2.objects.air.BombsightOPB.RecalculateAngle();
    }

    public static float fSightCurAltitude;
    public static float fSightCurSpeed;
    public static float fSightCurForwardAngle;
    public static float fSightSetForwardAngle;
    public static float fSightCurSideslip;
    private static com.maddox.il2.objects.air.BombDescriptor BombDescs[] = {
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
