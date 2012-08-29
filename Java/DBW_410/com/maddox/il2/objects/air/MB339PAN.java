// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 20/01/2011 10:19:44 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MB339PAN.java

package com.maddox.il2.objects.air;

import java.util.ArrayList;

import com.maddox.rts.*;
import com.maddox.util.HashMapInt;

// Referenced classes of package com.maddox.il2.objects.air:
//            MB339, PaintSchemeFMPar05, Aircraft, NetAircraft

public class MB339PAN extends MB339
{

    public MB339PAN()
    {
    }

    static Class class$com$maddox$il2$objects$air$MB339PAN;

    static 
    {
        Class var_class = CLASS.THIS();
        new NetAircraft.SPAWN(var_class);
        Property.set(var_class, "iconFar_shortClassName", "MB-339");
        Property.set(var_class, "meshName", "3DO/Plane/MB339PAN/hier.him");
        Property.set(var_class, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(var_class, "yearService", 1979.9F);
        Property.set(var_class, "yearExpired", 2020.3F);
        Property.set(var_class, "FlightModel", "FlightModels/MB339.fmd");
        Property.set(var_class, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitMB339.class, com.maddox.il2.objects.air.CockpitMB339_RGunner.class
        });
        Property.set(var_class, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(var_class, new int[2]);
        Aircraft.weaponHooksRegister(var_class, new String[] {
            "_MGUN01", "_MGUN02"
        });
        try
        {
            ArrayList arraylist = new ArrayList();
            Property.set(var_class, "weaponsList", arraylist);
            HashMapInt hashmapint = new HashMapInt();
            Property.set(var_class, "weaponsMap", hashmapint);
            byte byte0 = 2;
            String s = "default";
            com.maddox.il2.objects.air.Aircraft._WeaponSlot a_lweaponslot[] = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            for(int i = 6; i < byte0; i++)
                a_lweaponslot[i] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            for(int j = 0; j < byte0; j++)
                a_lweaponslot[j] = null;

            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
        }
        catch(Exception exception) { }
    }
}