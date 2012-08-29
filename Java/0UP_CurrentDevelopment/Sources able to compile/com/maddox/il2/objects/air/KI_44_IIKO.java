package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class KI_44_IIKO extends KI_44_II
{

    public KI_44_IIKO()
    {
        flapps = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 12; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", -26F * f1, 0.0F, 0.0F);

        }
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = KI_44_IIKO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-44");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-44-II(Ko)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-44-II(Ko)(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-44-IIko.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitKI_44_II_ko.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.4252F);
        KI_44_IIKO.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 3, 9, 9
        });
        KI_44_IIKO.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        KI_44_IIKO.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303s_jap 500", "MGunBrowning303s_jap 500", "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", null, null, null, null, null, null
        });
        KI_44_IIKO.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunBrowning303s_jap 500", "MGunBrowning303s_jap 500", "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1 1", "PylonKI43PLN1 1", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        KI_44_IIKO.weaponsRegister(class1, "2xdt130", new java.lang.String[] {
            "MGunBrowning303s_jap 500", "MGunBrowning303s_jap 500", "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", null, null, null, null, "FuelTankGun_TankKi44Underwing 1", "FuelTankGun_TankKi44Underwing 1"
        });
        KI_44_IIKO.weaponsRegister(class1, "2xdt130+60", new java.lang.String[] {
            "MGunBrowning303s_jap 500", "MGunBrowning303s_jap 500", "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1 1", "PylonKI43PLN1 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "FuelTankGun_TankKi44Underwing 1", "FuelTankGun_TankKi44Underwing 1"
        });
        KI_44_IIKO.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
