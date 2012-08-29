package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class SPITFIRE1vroeg extends SPITFIRE
{

    public SPITFIRE1vroeg()
    {
    }

    protected void nextDMGLevel(String s, int i, Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(String s, int i, Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(Config.isUSE_RENDER())
        {
            if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(this == World.getPlayerAircraft())
        {
            FM.Gears.setOperable(true);
            FM.Gears.setHydroOperable(false);
        }
    }


    public static boolean bChangedPit = false;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.SPITFIRE1vroeg.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Spit");
        Property.set(class1, "meshName", "3DO/Plane/SpitfireMk1vroeg/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMk1vroeg/hier.him");
        Property.set(class1, "PaintScheme_gb", new PaintSchemeSPIT1vroeg());
        Property.set(class1, "yearService", 1941F);
        Property.set(class1, "yearExpired", 1946.5F);
        Property.set(class1, "FlightModel", "FlightModels/SpitfireIvroeg.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitSpit1vroeg.class
        });
        Property.set(class1, "LOSElevation", 0.5926F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN07", "_MGUN08", "_MGUN10", "_MGUN11"
        });
        /*
        try
        {
            ArrayList arraylist = new ArrayList();
            Property.set(class1, "weaponsList", arraylist);
            HashMapInt hashmapint = new HashMapInt();
            Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 20;
            String s = "default";
            Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            a_lweaponslot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
            for(int i = 8; i < byte0; i++)
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
        */
    }
}