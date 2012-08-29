// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PE_2SERIES359.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            PE_2, PaintSchemeBMPar04, TypeBomber, TypeDiveBomber, 
//            TypeTransport, NetAircraft

public class PE_2SERIES359 extends com.maddox.il2.objects.air.PE_2
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeDiveBomber, com.maddox.il2.objects.air.TypeTransport
{

    public PE_2SERIES359()
    {
        tme = 0L;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.turret.length != 0)
        {
            FM.turret[1].bIsOperable = false;
            FM.turret[2].bIsOperable = false;
            FM.turret[3].bIsOperable = false;
        }
        gun3 = getGunByHookName("_MGUN03");
        gun4 = getGunByHookName("_MGUN04");
    }

    public void update(float f)
    {
        if(com.maddox.rts.Time.current() > tme)
        {
            tme = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(5000L, 20000L);
            if(FM.turret.length != 0)
            {
                gun3.loadBullets(java.lang.Math.min(gun3.countBullets(), gun4.countBullets()));
                gun4.loadBullets(gun3.countBullets());
                com.maddox.il2.engine.Actor actor = null;
                for(int i = 1; i < 4; i++)
                    if(FM.turret[i].bIsOperable)
                        actor = FM.turret[i].target;

                for(int j = 1; j < 4; j++)
                    FM.turret[j].target = actor;

                if(actor == null)
                    setRadist(0);
                else
                if(com.maddox.il2.engine.Actor.isValid(actor))
                {
                    pos.getAbs(tmpLoc2);
                    actor.pos.getAbs(tmpLoc3);
                    tmpLoc2.transformInv(tmpLoc3.getPoint());
                    if(tmpLoc3.getPoint().x < -java.lang.Math.abs(tmpLoc3.getPoint().y))
                        setRadist(1);
                    else
                    if(tmpLoc3.getPoint().y < 0.0D)
                        setRadist(2);
                    else
                        setRadist(3);
                }
            }
        }
        super.update(f);
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
            if(f < -110F)
            {
                f = -110F;
                flag = false;
            }
            if(f > 88F)
            {
                f = 88F;
                flag = false;
            }
            if(f1 < -1F)
            {
                f1 = -1F;
                flag = false;
            }
            if(f1 > 55F)
            {
                f1 = 55F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -30F)
            {
                f = -30F;
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
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -30F)
            {
                f = -30F;
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
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    private void setRadist(int i)
    {
        hierMesh().chunkVisible("Pilot3_D0", false);
        hierMesh().chunkVisible("Pilot3a_D0", false);
        hierMesh().chunkVisible("Pilot3b_D0", false);
        hierMesh().chunkVisible("Pilot3c_D0", false);
        FM.turret[1].bIsOperable = false;
        FM.turret[2].bIsOperable = false;
        FM.turret[3].bIsOperable = false;
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot3_D0", true);
            FM.turret[1].bIsOperable = true;
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot3a_D0", true);
            FM.turret[1].bIsOperable = true;
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3b_D0", true);
            FM.turret[2].bIsOperable = true;
            hierMesh().chunkVisible("Turret3B_D0", true);
            hierMesh().chunkVisible("Turret4B_D0", false);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot3c_D0", true);
            FM.turret[3].bIsOperable = true;
            hierMesh().chunkVisible("Turret3B_D0", false);
            hierMesh().chunkVisible("Turret4B_D0", true);
            break;
        }
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
            FM.turret[2].setHealth(f);
            FM.turret[3].setHealth(f);
            break;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 300F)
            fSightCurAltitude = 300F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 50F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 650F)
            fSightCurSpeed = 650F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 50F)
            fSightCurSpeed = 50F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        d -= (double)(fSightCurAltitude * fSightCurAltitude) * 1.419E-005D;
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private long tme;
    private com.maddox.il2.objects.weapons.Gun gun3;
    private com.maddox.il2.objects.weapons.Gun gun4;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_2SERIES359.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Pe-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Pe-2series359/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Pe-2series359.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitPE2_359.class, com.maddox.il2.objects.air.CockpitPE2_Bombardier.class, com.maddox.il2.objects.air.CockpitPE2_359_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.76315F);
        com.maddox.il2.objects.air.PE_2SERIES359.weaponTriggersRegister(class1, new int[] {
            0, 1, 10, 11, 12, 13, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON02", "_CANNON01", "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06"
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "6fab50", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "6fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "2fab2502fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null, "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "4fab250", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "2fab500", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "2fab5002fab250", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.PE_2SERIES359.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
