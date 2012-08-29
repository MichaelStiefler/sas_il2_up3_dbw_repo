package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class N1K1JA extends N1K
{

    public N1K1JA()
    {
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 11; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20F * f1, 0.0F);

        }
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.1F, 0.99F, 0.0F, 0.61F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
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
        if(FM.CT.Weapons[3] != null)
        {
            hierMesh().chunkVisible("RackL_D0", true);
            hierMesh().chunkVisible("RackR_D0", true);
        }
    }

    static 
    {
        java.lang.Class class1 = N1K1JA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "N1K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/N1K1-Ja(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/N1K1-Ja(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/N1K1-J.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitN1K2JA.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 3, 3, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1x400dt", new java.lang.String[] {
            "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", null, null, "PylonN1K1PLN1", "FuelTankGun_TankN1K1"
        });
        Aircraft.weaponsRegister(class1, "2x30", new java.lang.String[] {
            "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "BombGun30kgJ 1", "BombGun30kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2s 100", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
