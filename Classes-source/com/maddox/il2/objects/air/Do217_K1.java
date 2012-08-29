// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Do217_K1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Do217, PaintSchemeBMPar02, Aircraft, NetAircraft

public class Do217_K1 extends com.maddox.il2.objects.air.Do217
{

    public Do217_K1()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
    }

    protected void moveBayDoor(float f)
    {
        if(f < 0.02F)
        {
            hierMesh().chunkVisible("Bay_D0", true);
            for(int i = 1; i <= 9; i++)
            {
                hierMesh().chunkVisible("BayL0" + i + "_D0", false);
                hierMesh().chunkVisible("BayR0" + i + "_D0", false);
            }

        } else
        {
            hierMesh().chunkVisible("Bay_D0", false);
            for(int j = 1; j <= 9; j++)
            {
                hierMesh().chunkVisible("BayL0" + j + "_D0", true);
                hierMesh().chunkVisible("BayR0" + j + "_D0", true);
            }

            boolean flag = f < 0.8F;
            hierMesh().chunkVisible("BayL03_D0", flag);
            hierMesh().chunkVisible("BayR03_D0", flag);
            hierMesh().chunkVisible("BayL06_D0", flag);
            hierMesh().chunkVisible("BayR06_D0", flag);
            hierMesh().chunkSetAngles("BayL01_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
            hierMesh().chunkSetAngles("BayL04_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR01_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR04_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);
            hierMesh().chunkSetAngles("BayL02_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
            hierMesh().chunkSetAngles("BayL05_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR02_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, 155.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR05_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, 155.5F), 0.0F);
            hierMesh().chunkSetAngles("BayL03_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);
            hierMesh().chunkSetAngles("BayL06_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR03_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);
            hierMesh().chunkSetAngles("BayR06_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);
            if(thisWeaponsName.endsWith("Torpedo"))
            {
                hierMesh().chunkVisible("BayL09_D0", flag);
                hierMesh().chunkVisible("BayR09_D0", flag);
                hierMesh().chunkSetAngles("BayL07_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, 120.5F), 0.0F);
                hierMesh().chunkSetAngles("BayL08_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, -155.5F), 0.0F);
                hierMesh().chunkSetAngles("BayL09_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, -150.5F), 0.0F);
                hierMesh().chunkSetAngles("BayR07_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.04F, 0.7F, 0.0F, -120.5F), 0.0F);
                hierMesh().chunkSetAngles("BayR08_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.98F, 0.0F, 150.5F), 0.0F);
                hierMesh().chunkSetAngles("BayR09_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.4F, 0.9F, 0.0F, 150.5F), 0.0F);
            }
        }
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

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Do-217");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Do217_K1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Do217K-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitDo217_K1.class, com.maddox.il2.objects.air.CockpitDo217_Bombardier.class, com.maddox.il2.objects.air.CockpitDo217_NGunner.class, com.maddox.il2.objects.air.CockpitDo217_TGunner.class, com.maddox.il2.objects.air.CockpitDo217_BGunner.class, com.maddox.il2.objects.air.CockpitDo217_LGunner.class, com.maddox.il2.objects.air.CockpitDo217_RGunner.class
        });
        com.maddox.il2.objects.air.Do217_K1.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 12, 13, 14, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Do217_K1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN10", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", 
            "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn05"
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "28xSC50", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC50 7", "BombGunSC50 7", "BombGunSC50 7", "BombGunSC50 7", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "4xSC250", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "4xSC500", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "4xSD500", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD500 1", "BombGunSD500 1", "BombGunSD500 1", "BombGunSD500 1", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "2xSC1000", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, 
            "BombGunSC1000 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC1000 1", null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "3xSD1000", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD1000 1", "BombGunNull 1", "BombGunNull 1", "BombGunSD1000 1", 
            null, "BombGunSD1000 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "4xSD1000", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSD1000 1", "BombGunSD1000 1", "BombGunSD1000 1", "BombGunSD1000 1", 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "1xSC1800", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, 
            null, null, null, null, "BombGunSC1800 1", null
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "1xTorpedo", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 500", "MGunMG131t 500", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, 
            null, null, null, null, null, "BombGunTorpF5Bheavy 1"
        });
        com.maddox.il2.objects.air.Do217_K1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
