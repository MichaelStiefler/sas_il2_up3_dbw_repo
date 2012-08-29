// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   HalifaxBMkIII.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
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
//            Halifax, PaintSchemeBMPar01, TypeBomber, NetAircraft, 
//            Aircraft

public class HalifaxBMkIII extends com.maddox.il2.objects.air.Halifax
    implements com.maddox.il2.objects.air.TypeBomber
{

    public HalifaxBMkIII()
    {
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
            killPilot(((com.maddox.il2.engine.Actor) (this)), 4);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 5; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

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
            if(f < -23F)
            {
                f = -23F;
                flag = false;
            }
            if(f > 23F)
            {
                f = 23F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 15F)
            {
                f1 = 15F;
                flag = false;
            }
            break;

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
            if(f1 > 38F)
            {
                f1 = 38F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
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
        fSightCurDistance = com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurDistance = com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurDistance = com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 1000F)
            fSightCurAltitude = 1000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
            fSightCurDistance -= com.maddox.il2.objects.air.HalifaxBMkIII.toMetersPerSecond(fSightCurSpeed) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude)));
            if((double)fSightCurDistance < (double)com.maddox.il2.objects.air.HalifaxBMkIII.toMetersPerSecond(fSightCurSpeed) * java.lang.Math.sqrt(com.maddox.il2.objects.air.HalifaxBMkIII.toMeters(fSightCurAltitude) * 0.2038736F))
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HalifaxBMkIII.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Halifax");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/HALIFAX-B-Mk3(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/HalifaxBMkIII.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHalifaxBMkIII.class, com.maddox.il2.objects.air.CockpitHalifaxBMkIII_Bombardier.class, com.maddox.il2.objects.air.CockpitHalifaxBMkIII_FGunner.class, com.maddox.il2.objects.air.CockpitHalifaxBMkIII_TGunner.class, com.maddox.il2.objects.air.CockpitHalifaxBMkIII_AGunner.class
        })));
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 11, 11, 11, 12, 12, 12, 12, 3, 
            3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN08", "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN09", "_MGUN10", "_BombSpawn01", 
            "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16x250", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun250lbsE 2", 
            "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2", "BombGun250lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24x250", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun250lbsE 4", 
            "BombGun250lbsE 4", "BombGun250lbsE 4", null, null, "BombGun250lbsE 4", "BombGun250lbsE 4", "BombGun250lbsE 4"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x500", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun500lbsE 2", 
            null, "BombGun500lbsE 2", null, null, "BombGun500lbsE 2", null, "BombGun500lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x500", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun500lbsE 2", 
            "BombGun500lbsE 2", "BombGun500lbsE 2", null, null, "BombGun500lbsE 2", "BombGun500lbsE 2", "BombGun500lbsE 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x1000", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun1000lbs 1", 
            null, "BombGun1000lbs 1", null, null, "BombGun1000lbs 1", null, "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x1000", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "BombGun1000lbs 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderRed", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", null, 
            "BombGunTIRed 1", "BombGunTIRed 1", "BombGunTIRed 2", "BombGunTIRed 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderGreen", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", null, 
            "BombGunTIGreen 1", "BombGunTIGreen 1", "BombGunTIGreen 2", "BombGunTIGreen 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PathfinderYellow", new java.lang.String[] {
            "MGunBrowning303t 300", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 600", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", "MGunBrowning303t 2500", null, 
            "BombGunTIYellow 1", "BombGunTIYellow 1", "BombGunTIYellow 2", "BombGunTIYellow 2", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
