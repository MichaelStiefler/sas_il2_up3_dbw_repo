// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   H8K1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            H8K, PaintSchemeBMPar04, PaintSchemeFCSPar05, TypeBomber, 
//            NetAircraft

public class H8K1 extends com.maddox.il2.objects.air.H8K
    implements com.maddox.il2.objects.air.TypeBomber
{

    public H8K1()
    {
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
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 35F)
            {
                f = 35F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 25F)
            {
                f1 = 25F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -22F)
            {
                f = -22F;
                flag = false;
            }
            if(f > 22F)
            {
                f = 22F;
                flag = false;
            }
            if(f1 < -57F)
            {
                f1 = -57F;
                flag = false;
            }
            if(f1 > 33F)
            {
                f1 = 33F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -22F)
            {
                f = -22F;
                flag = false;
            }
            if(f > 22F)
            {
                f = 22F;
                flag = false;
            }
            if(f1 < -57F)
            {
                f1 = -57F;
                flag = false;
            }
            if(f1 > 33F)
            {
                f1 = 33F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -25F)
            {
                f = -25F;
                flag = false;
            }
            if(f > 25F)
            {
                f = 25F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 25F)
            {
                f1 = 25F;
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
            FM.turret[2].setHealth(f);
            break;

        case 4: // '\004'
            FM.turret[3].setHealth(f);
            break;

        case 5: // '\005'
            FM.turret[4].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        if(i > 5)
        {
            return;
        } else
        {
            hierMesh().chunkVisible("Pilot" + (i + 1) + "_D0", false);
            hierMesh().chunkVisible("HMask" + (i + 1) + "_D0", false);
            hierMesh().chunkVisible("Pilot" + (i + 1) + "_D1", true);
            hierMesh().chunkVisible("Head" + (i + 1) + "_D0", false);
            return;
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.H8K1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "H8K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/H8K1(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/H8K1(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2048F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/H8K1.fmd");
        com.maddox.il2.objects.air.H8K1.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.H8K1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", 
            "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", 
            "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25", 
            "_ExternalBomb26"
        });
        com.maddox.il2.objects.air.H8K1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.H8K1.weaponsRegister(class1, "16x60", new java.lang.String[] {
            "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, 
            null, null, null, null, null, "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", 
            "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", 
            "BombGun60kgJ"
        });
        com.maddox.il2.objects.air.H8K1.weaponsRegister(class1, "8x250", new java.lang.String[] {
            "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", 
            "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.H8K1.weaponsRegister(class1, "2x4512", new java.lang.String[] {
            "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", "BombGun4512", "BombGun4512", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.H8K1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
