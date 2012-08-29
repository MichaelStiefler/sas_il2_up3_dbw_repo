// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Placeholder.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeBMPar03, TypeScout, NetAircraft, 
//            Aircraft

public class Placeholder extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeScout
{

    public Placeholder()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.air.Placeholder.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SAS");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SASPlaceholder/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1910F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1970F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Generic.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109F2.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            ""
        });
        try
        {
            java.util.ArrayList arraylist = new ArrayList();
            com.maddox.rts.Property.set(class1, "weaponsList", arraylist);
            com.maddox.util.HashMapInt hashmapint = new HashMapInt();
            com.maddox.rts.Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 20;
            java.lang.String s = "SAS_Placeholder";
            com.maddox.il2.objects.air.Aircraft._WeaponSlot a_lweaponslot[] = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = null;
            for(int i = 1; i < byte0; i++)
                a_lweaponslot[i] = null;

            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            for(int j = 0; j < byte0; j++)
                a_lweaponslot[j] = null;

            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
        }
        catch(java.lang.Exception exception) { }
    }
}
