// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_11C.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_11, PaintSchemeFMPar00, PaintSchemeFCSPar01, NetAircraft, 
//            PaintScheme

public class P_11C extends com.maddox.il2.objects.air.P_11
{

    public P_11C()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.Bomb)
                {
                    hierMesh().chunkVisible("RackL_D0", true);
                    hierMesh().chunkVisible("RackR_D0", true);
                }

        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_11C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P.11");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-11c(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_pl", "3DO/Plane/P-11c/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_pl", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/P-11c(Romanian)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryPoland);
        com.maddox.rts.Property.set(class1, "yearService", 1934F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1939.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-11c.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_11C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7956F);
        com.maddox.il2.objects.air.P_11C.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        com.maddox.il2.objects.air.P_11C.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.P_11C.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", null, null
        });
        com.maddox.il2.objects.air.P_11C.weaponsRegister(class1, "2puw125", new java.lang.String[] {
            "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", "BombGunPuW125", "BombGunPuW125"
        });
        com.maddox.il2.objects.air.P_11C.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
