package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40M, PaintSchemeFMPar05, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class P_40N extends P_40
{

    public P_40N()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.endsWith("light"))
        {
            ((FlightModelMain) (super.FM)).M.massEmpty = 3159F;
            return;
        } else
        {
            return;
        }
    }

    private static float f;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.P_40N.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "P-40");
        Property.set(class1, "meshName", "3DO/Plane/P-40N(Multi1)/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(class1, "noseart", 1);
        Property.set(class1, "yearService", 1942F);
        Property.set(class1, "yearExpired", 1945.5F);
        Property.set(class1, "FlightModel", "FlightModels/P-40N.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitP_40M.class
        });
        Property.set(class1, "LOSElevation", 1.0692F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 3, 9, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01", "_ExternalRock01", "_ExternalRock02"
        });
        try
        {
            ArrayList arraylist = new ArrayList();
            Property.set(class1, "weaponsList", arraylist);
            HashMapInt hashmapint = new HashMapInt();
            Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 10;
            Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
            String s = "default";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "2x250lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            a_lweaponslot[9] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "500lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "1000lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "droptank";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "light";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "light-2x250lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            a_lweaponslot[9] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "light-500lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "light-1000lb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "light-droptank";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = null;
            a_lweaponslot[1] = null;
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = null;
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
        }
        catch(Exception exception) { }
    }
}