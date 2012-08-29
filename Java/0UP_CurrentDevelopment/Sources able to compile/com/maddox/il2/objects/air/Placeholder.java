package com.maddox.il2.objects.air;

import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class Placeholder extends Scheme1
    implements TypeScout
{

    public Placeholder()
    {
    }

    static 
    {
        java.lang.Class class1 = Placeholder.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SAS");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SASPlaceholder/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1910F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1970F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Generic.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBF_109F2.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
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
            Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = null;
            for(int i = 1; i < byte0; i++)
                a_lweaponslot[i] = null;

            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            for(int j = 0; j < byte0; j++)
                a_lweaponslot[j] = null;

            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
        }
        catch(java.lang.Exception exception) { }
    }
}
