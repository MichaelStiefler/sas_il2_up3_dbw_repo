// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MIG_3UD.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MIG_3, PaintSchemeFMPar01, PaintSchemeFCSPar01, Aircraft, 
//            Cockpit, NetAircraft

public class MIG_3UD extends com.maddox.il2.objects.air.MIG_3
{

    public MIG_3UD()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F), 0.0F);
        }
        hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30F * kangle, 0.0F);
        hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = -com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.bHasCockpitDoorControl = true;
        FM.CT.dvCockpitDoor = 0.75F;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.MIG_3UD.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "MiG");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/MIG-3ud(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ru", "3do/plane/MIG-3ud/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ru", ((java.lang.Object) (new PaintSchemeFCSPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/MiG-3ud.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitMIG_3.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.906F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 3, 3, 3, 3, 9, 
            9, 9, 9, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xBK", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", "MGunUBk 145", "MGunUBk 145", null, null, null, null, "PylonMiG_3_BK", 
            "PylonMiG_3_BK", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6xRS-82", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", null, null, null, null, null, null, null, 
            null, "PylonRO_82_3", "PylonRO_82_3", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB-50", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB-100", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xAO-10", new java.lang.String[] {
            "MGunShKASs 750", "MGunShKASs 750", "MGunUBk 300", null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
