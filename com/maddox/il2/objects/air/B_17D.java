// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   B_17D.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_17, PaintSchemeBMPar01, PaintSchemeFMPar06, TypeBomber, 
//            Aircraft, NetAircraft

public class B_17D extends com.maddox.il2.objects.air.B_17
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_17D()
    {
        calibDistance = 0.0F;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85F * f, 0.0F);
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].setHealth(f);
            FM.turret[1].setHealth(f);
            FM.turret[2].setHealth(f);
            break;

        case 6: // '\006'
            FM.turret[3].setHealth(f);
            break;

        case 7: // '\007'
            FM.turret[4].setHealth(f);
            break;

        case 8: // '\b'
            FM.turret[5].setHealth(f);
            break;
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
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
            if(f < -11F)
            {
                f = -11F;
                flag = false;
            }
            if(f > 11F)
            {
                f = 11F;
                flag = false;
            }
            if(f1 < -14F)
            {
                f1 = -14F;
                flag = false;
            }
            if(f1 > 14F)
            {
                f1 = 14F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -26F)
            {
                f = -26F;
                flag = false;
            }
            if(f > 0.0F)
            {
                f = 0.0F;
                flag = false;
            }
            if(f1 < -14F)
            {
                f1 = -14F;
                flag = false;
            }
            if(f1 > 14F)
            {
                f1 = 14F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -11F)
            {
                f = -11F;
                flag = false;
            }
            if(f > 11F)
            {
                f = 11F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -12F)
            {
                f = -12F;
                flag = false;
            }
            if(f > 12F)
            {
                f = 12F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 2.0F)
            {
                f1 = 2.0F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -41F)
            {
                f = -41F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 5: // '\005'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 53F)
            {
                f = 53F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
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
        bSightAutomation = !bSightAutomation;
        bSightBombDump = false;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (bSightAutomation ? "ON" : "OFF"));
        return bSightAutomation;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle++;
        if(fSightCurForwardAngle > 85F)
            fSightCurForwardAngle = 85F;
        fSightCurDistance = com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle--;
        if(fSightCurForwardAngle < 0.0F)
            fSightCurForwardAngle = 0.0F;
        fSightCurDistance = com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip += 0.1F;
        if(fSightCurSideslip > 3F)
            fSightCurSideslip = 3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip -= 0.1F;
        if(fSightCurSideslip < -3F)
            fSightCurSideslip = -3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 3000F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 50000F)
            fSightCurAltitude = 50000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 1000F)
            fSightCurAltitude = 1000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 200F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 10F;
        if(fSightCurSpeed > 450F)
            fSightCurSpeed = 450F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 10F;
        if(fSightCurSpeed < 100F)
            fSightCurSpeed = 100F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        if((double)java.lang.Math.abs(FM.Or.getKren()) > 4.5D)
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
            fSightCurDistance -= com.maddox.il2.objects.air.B_17D.toMetersPerSecond(fSightCurSpeed) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude)));
            calibDistance = com.maddox.il2.objects.air.B_17D.toMetersPerSecond(fSightCurSpeed) * floatindex(com.maddox.il2.objects.air.Aircraft.cvt(com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude), 0.0F, 7000F, 0.0F, 7F), calibrationScale);
            if((double)fSightCurDistance < (double)calibDistance + (double)com.maddox.il2.objects.air.B_17D.toMetersPerSecond(fSightCurSpeed) * java.lang.Math.sqrt(com.maddox.il2.objects.air.B_17D.toMeters(fSightCurAltitude) * 0.2038736F))
                bSightBombDump = true;
            if(bSightBombDump)
                if(FM.isTick(3, 0))
                {
                    if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets())
                    {
                        FM.CT.WeaponControl[3] = true;
                        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
                    }
                } else
                {
                    FM.CT.WeaponControl[3] = false;
                }
        }
    }

    protected float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
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

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
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
    private float calibDistance;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;
    static final float calibrationScale[] = {
        0.0F, 0.2F, 0.4F, 0.66F, 0.86F, 1.05F, 1.2F, 1.6F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B_17D.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "B-17");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/B-17D(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/B-17D(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 2800.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/B-17D.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitB17D.class, com.maddox.il2.objects.air.CockpitB17D_Bombardier.class
        })));
        com.maddox.il2.objects.air.B_17D.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 13, 14, 15, 3, 3
        });
        com.maddox.il2.objects.air.B_17D.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", null, null
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "20x100", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun100Lbs 10", "BombGun100Lbs 10"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "14x300", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun300lbs 7", "BombGun300lbs 7"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "4x1000", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun1000lbs 2", "BombGun1000lbs 2"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
