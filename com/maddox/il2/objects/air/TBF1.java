// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TBF1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            TBF, PaintSchemeBMPar01, NetAircraft

public class TBF1 extends com.maddox.il2.objects.air.TBF
{

    public TBF1()
    {
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, 100F * f, 0.0F);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -33F)
            {
                f = -33F;
                flag = false;
            }
            if(f > 33F)
            {
                f = 33F;
                flag = false;
            }
            if(f1 < -3F)
            {
                f1 = -3F;
                flag = false;
            }
            if(f1 > 62F)
            {
                f1 = 62F;
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
            FM.turret[1].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", hierMesh().isChunkVisible("Pilot3_D0"));
            hierMesh().chunkVisible("Pilot4_D1", hierMesh().isChunkVisible("Pilot4_D0"));
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot4_D0", false);
            break;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.TBF1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "TBF");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/TBF-1(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/TBF-1(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/TBF-1C.fmd");
        com.maddox.il2.objects.air.TBF1.weaponTriggersRegister(class1, new int[] {
            0, 10, 11, 2, 2, 2, 2, 2, 2, 2, 
            2, 3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.TBF1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", 
            "_ExternalRock08", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07"
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "4x100", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1"
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "4x250", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1"
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1"
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "1x1600", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, "BombGun1600lbs", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "1x2000", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, "BombGun2000lbs", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "1xmk13", new java.lang.String[] {
            "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, 
            null, "BombGunTorpMk13", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TBF1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
