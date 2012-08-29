package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.rts.Property;

public class J2M5 extends J2M
{

    public J2M5()
    {
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = -Aircraft.cvt(f, 0.1F, 0.99F, 0.0F, -0.61F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void replicateDropFuelTanks()
    {
        super.replicateDropFuelTanks();
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj == null)
            return;
        for(int i = 0; i < aobj.length; i++)
            if(aobj[i] instanceof com.maddox.il2.objects.weapons.FuelTank)
                hierMesh().chunkVisible("Pilon_D0", true);

    }

    static 
    {
        java.lang.Class class1 = J2M5.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "J2M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/J2M5(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/J2M5(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/J2M5_mod.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitJ2M5_mod.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.113F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 0, 0, 1, 3, 3, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunType99No1s 210", "MGunType99No2s 190", "MGunType99No2s 190", "MGunType99No1s 210", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunType99No1s 210", "MGunType99No2s 190", "MGunType99No2s 190", "MGunType99No1s 210", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "1x400dt", new java.lang.String[] {
            "MGunType99No1s 210", "MGunType99No2s 190", "MGunType99No2s 190", "MGunType99No1s 210", null, null, "FuelTankGun_TankN1K1 1", null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
