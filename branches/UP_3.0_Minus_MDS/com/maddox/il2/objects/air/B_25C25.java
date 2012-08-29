// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   B_25C25.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
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
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_25, PaintSchemeBMPar02, TypeBomber, Aircraft, 
//            NetAircraft

public class B_25C25 extends com.maddox.il2.objects.air.B_25
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_25C25()
    {
        bpos = 1.0F;
        bcurpos = 1.0F;
        btme = -1L;
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

    public void update(float f)
    {
        super.update(f);
        if(FM.AS.isMaster())
        {
            if(bpos == 0.0F)
            {
                if(bcurpos > bpos)
                {
                    bcurpos -= 0.2F * f;
                    if(bcurpos < 0.0F)
                        bcurpos = 0.0F;
                }
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.31F + 0.31F * bcurpos;
                hierMesh().chunkSetLocate("Turret3A_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            } else
            if(bpos == 1.0F)
            {
                if(bcurpos < bpos)
                {
                    bcurpos += 0.2F * f;
                    if(bcurpos > 1.0F)
                    {
                        bcurpos = 1.0F;
                        bpos = 0.5F;
                        FM.turret[2].bIsOperable = true;
                    }
                }
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.3F + 0.3F * bcurpos;
                hierMesh().chunkSetLocate("Turret3A_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }
            if(com.maddox.rts.Time.current() > btme)
            {
                btme = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(5000L, 12000L);
                if(FM.turret[2].target == null)
                {
                    FM.turret[2].bIsOperable = false;
                    bpos = 0.0F;
                }
                if(FM.turret[1].target != null && FM.AS.astatePilotStates[4] < 90)
                    bpos = 1.0F;
            }
        }
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

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 6; i++)
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
        fSightCurDistance = com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurDistance = com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurDistance = com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 1000F)
            fSightCurAltitude = 1000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
            fSightCurDistance -= com.maddox.il2.objects.air.B_25C25.toMetersPerSecond(fSightCurSpeed) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude)));
            calibDistance = com.maddox.il2.objects.air.B_25C25.toMetersPerSecond(fSightCurSpeed) * floatindex(com.maddox.il2.objects.air.Aircraft.cvt(com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude), 0.0F, 7000F, 0.0F, 7F), calibrationScale);
            if((double)fSightCurDistance < (double)calibDistance + (double)com.maddox.il2.objects.air.B_25C25.toMetersPerSecond(fSightCurSpeed) * java.lang.Math.sqrt(com.maddox.il2.objects.air.B_25C25.toMeters(fSightCurAltitude) * 0.2038736F))
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

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].setHealth(f);
            break;

        case 3: // '\003'
            FM.turret[1].setHealth(f);
            FM.turret[2].setHealth(f);
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
            if(f1 > 88F)
            {
                f1 = 88F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f1 < -88F)
            {
                f1 = -88F;
                flag = false;
            }
            if(f1 > 2.0F)
            {
                f1 = 2.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float bpos;
    private float bcurpos;
    private long btme;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.B_25C25.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "B-25");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/B-25C-25(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ru", "3DO/Plane/B-25C-25(ru)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ru", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/B-25C-25(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/B-25C.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitB25C25.class, com.maddox.il2.objects.air.CockpitB25C25_Bombardier.class, com.maddox.il2.objects.air.CockpitB25C25_FGunner.class, com.maddox.il2.objects.air.CockpitB25C25_TGunner.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.73425F);
        com.maddox.il2.objects.air.B_25C25.weaponTriggersRegister(class1, new int[] {
            0, 10, 11, 11, 12, 12, 3, 3, 3
        });
        com.maddox.il2.objects.air.B_25C25.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, null, null
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "12x100lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun100Lbs 6", "BombGun100Lbs 6"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "6x250lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun250lbs 3", "BombGun250lbs 3"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "4x500lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun500lbs 2", "BombGun500lbs 2"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "2x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun1000lbs 1", "BombGun1000lbs 1"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "1x2000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", "BombGun2000lbs 1", null, null
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "10x50kg", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGunFAB50 5", "BombGunFAB50 5"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "8x100kg", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGunFAB100 4", "BombGunFAB100 4"
        });
        com.maddox.il2.objects.air.B_25C25.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
