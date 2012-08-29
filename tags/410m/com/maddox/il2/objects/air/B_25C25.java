// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_25C25.java

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

// Referenced classes of package com.maddox.il2.objects.air:
//            B_25, PaintSchemeBMPar02, TypeBomber, NetAircraft

public class B_25C25 extends com.maddox.il2.objects.air.B_25
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_25C25()
    {
        bpos = 1.0F;
        bcurpos = 1.0F;
        btme = -1L;
    }

    public void update(float f)
    {
        super.update(f);
        if(!FM.AS.isMaster())
            return;
        if(bpos == 0.0F)
        {
            if(bcurpos > bpos)
            {
                bcurpos -= 0.2F * f;
                if(bcurpos < 0.0F)
                    bcurpos = 0.0F;
            }
            resetYPRmodifier();
            xyz[1] = -0.31F + 0.31F * bcurpos;
            hierMesh().chunkSetLocate("Turret3A_D0", xyz, ypr);
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
            xyz[1] = -0.3F + 0.3F * bcurpos;
            hierMesh().chunkSetLocate("Turret3A_D0", xyz, ypr);
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

    private float bpos;
    private float bcurpos;
    private long btme;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.B_25C25.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-25");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-25C-25(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/B-25C-25(ru)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-25C-25(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-25C.fmd");
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
            "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun50kg 6", "BombGun50kg 6"
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
