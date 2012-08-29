// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_2_1941Late.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_2, PaintSchemeBMPar02, PaintSchemeBCSPar02, NetAircraft, 
//            Aircraft

public class IL_2_1941Late extends com.maddox.il2.objects.air.IL_2
{

    public IL_2_1941Late()
    {
    }

    public void doKillPilot(int i)
    {
        if(i == 1)
            FM.turret[0].bIsOperable = false;
    }

    public void doMurderPilot(int i)
    {
        if(i == 1)
        {
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("Turret1A_D0", false);
            hierMesh().chunkVisible("Turret1A_D1", false);
            hierMesh().chunkVisible("Turret1B_D0", false);
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        if(af[0] < -135F)
        {
            af[0] = -135F;
            flag = false;
        } else
        if(af[0] > 135F)
        {
            af[0] = 135F;
            flag = false;
        }
        float f = java.lang.Math.abs(af[0]);
        if(f < 20F)
        {
            if(af[1] < -10F)
            {
                af[1] = -10F;
                flag = false;
            }
        } else
        if(af[1] < -15F)
        {
            af[1] = -15F;
            flag = false;
        }
        if(af[1] > 45F)
        {
            af[1] = 45F;
            flag = false;
        }
        if(!flag)
            return false;
        float f1 = af[1];
        if(f < 2.0F && f1 < 17F)
            return false;
        if(f1 > -5F)
            return true;
        if(f1 > -12F)
        {
            f1 += 12F;
            return f > 12F + f1 * 2.571429F;
        } else
        {
            f1 = -f1;
            return f > f1;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_2_1941Late.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IL2");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Il-2-1941Late(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3do/plane/Il-2-1941Late/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941.2F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-2-1941.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitIL_2_1940.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81F);
        com.maddox.rts.Property.set(class1, "Handicap", 1.0F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 3, 3, 9, 9, 
            9, 9, 9, 9, 3, 3, 10, 10
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", 
            "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_BombSpawn01", "_BombSpawn02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xBRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", 
            "RocketGunBRS82", "RocketGunBRS82", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", 
            "RocketGunRS132", "RocketGunRS132", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xBRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", 
            "RocketGunBRS132", "RocketGunBRS132", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xM13", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", 
            "RocketGunM13", "RocketGunM13", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "216xAJ-2", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "PylonKMB", "PylonKMB", 
            "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "30xAO10", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", 
            "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "50xAO10", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, "BombGunAO10 12", "BombGunAO10 12", "BombGunAO10 13", "BombGunAO10 13", "PylonKMB", "PylonKMB", 
            "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB50_8xRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB50_8xRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", 
            "RocketGunRS132", "RocketGunRS132", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, 
            null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xVAP250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonVAP250", "PylonVAP250", "BombGunPhBall", "BombGunPhBall", "MGunShKASt 500", "MGunShKASt 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
