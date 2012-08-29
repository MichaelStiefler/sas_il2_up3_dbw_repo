// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 14/12/2009 7:43:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   P_40L.java

package com.maddox.il2.objects.air;

import com.maddox.util.HashMapInt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40, PaintSchemeFMPar05, Aircraft, NetAircraft

public class P_40L extends com.maddox.il2.objects.air.P_40
{

    public P_40L()
    {
    }

    public void update(float f1)
    {
        super.update(f1);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 5F, -17F);
        hierMesh().chunkSetAngles("Water2_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("Water3_D0", 0.0F, f, 0.0F);
        f = java.lang.Math.min(f, 0.0F);
        hierMesh().chunkSetAngles("Water1_D0", 0.0F, f, 0.0F);
        hierMesh().chunkSetAngles("Water4_D0", 0.0F, f, 0.0F);
        if(FM.EI.engines[0].getControlAfterburner())
        {
            FM.EI.engines[0].setAfterburnerType(11);
            com.maddox.il2.game.HUD.logRightBottom("BOOST / WEP ENABLED!");
        }
    }

    private static float f;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40L.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-40L(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-40L(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40F.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitP_40M.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0692F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 3, 9, 9, 9, 
            9, 9, 2, 2, 2, 2, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalDev05", "_ExternalDev01", "_ExternalDev02", 
            "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev06"
        });
        try
        {
            java.util.ArrayList arraylist = new ArrayList();
            com.maddox.rts.Property.set(class1, "weaponsList", arraylist);
            com.maddox.util.HashMapInt hashmapint = new HashMapInt();
            com.maddox.rts.Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 17;
            com.maddox.il2.objects.air.Aircraft._WeaponSlot a_lweaponslot[] = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            java.lang.String s = "default";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
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
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "4x50cal";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "250lb";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "PylonP39PLN1", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "2x250lb";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
            a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "PylonF4FPLN2", 1);
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            a_lweaponslot[13] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "500lb";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "PylonP39PLN1", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "1000lb";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "PylonP39PLN1", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "droptank";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 240);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "PylonP39PLN1", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
            s = "none";
            a_lweaponslot = new com.maddox.il2.objects.air.Aircraft._WeaponSlot[byte0];
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
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            arraylist.add(s);
            hashmapint.put(com.maddox.rts.Finger.Int(s), a_lweaponslot);
        }
        catch(java.lang.Exception exception) { }
    }
}