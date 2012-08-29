package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class B_25G1 extends B_25
    implements TypeBomber, TypeStormovik, TypeStormovikArmored
{

    public B_25G1()
    {
        bpos = 1.0F;
        bcurpos = 1.0F;
        btme = -1L;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
        bChangedPit = false;
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
                Aircraft.xyz[1] = -0.31F + 0.31F * bcurpos;
                hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
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
                Aircraft.xyz[1] = -0.3F + 0.3F * bcurpos;
                hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
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
            return false;

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
        case 3: // '\003'
            FM.turret[1].setHealth(f);
            break;

        case 4: // '\004'
            FM.turret[2].setHealth(f);
            break;
        }
    }

    private float bpos;
    private float bcurpos;
    private long btme;
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
        java.lang.Class class1 = B_25G1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-25");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-25G-1(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-25G-1(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-25G.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitB25G1.class, CockpitB25G1_TGunner.class
        });
        B_25G1.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 11, 11, 12, 12, 9, 9, 9, 
            9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 
            9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2
        });
        B_25G1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN07", "_MGUN08", "_CANNON01", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_BombSpawn02", 
            "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalDev05", 
            "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", 
            "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        B_25G1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "12x100lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun100Lbs 6", "BombGun100Lbs 6", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "3x250lbs3x500lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "3x250lbs2x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", 
            "BombGun1000lbs 1", null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "3x250lbs1x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun1000lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "6x250lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun250lbs 3", "BombGun250lbs 3", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "8x250lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", 
            "PylonB25PLN2", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "4x500lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "4x500lbs1x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "6x500lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun500lbs 3", "BombGun500lbs 3", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "2x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "3x1000lbs", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "10x50kg", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGun100Lbs 5", "BombGun100Lbs 5", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "8x100kg", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGunFAB100 4", "BombGunFAB100 4", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "2x250kg6x100kg", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", 
            "PylonB25PLN2", null, null, null, null, null, "BombGunFAB250 1", "BombGunFAB250 1", null, null, 
            null, null, null, null, null, null, null, "BombGunFAB100 3", "BombGunFAB100 3", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        B_25G1.weaponsRegister(class1, "8xHVAR", new java.lang.String[] {
            "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonB25RAIL", 
            "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        B_25G1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
