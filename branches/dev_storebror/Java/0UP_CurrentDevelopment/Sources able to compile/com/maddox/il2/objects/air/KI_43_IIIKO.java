package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class KI_43_IIIKO extends KI_43
{

    public KI_43_IIIKO()
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
            for(int i = 1; i < 15; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 26F * f1, 0.0F, 0.0F);

        }
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = KI_43_IIIKO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-43");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-43-III(Ko)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-43-III(Ko)(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-43-III.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitKI_43_IIKAI.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.4252F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 9, 9, 3, 3, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN2 1", "PylonKI43PLN2 1", "BombGun60kgJ 1", "BombGun60kgJ 1", null
        });
        Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1 1", "PylonKI43PLN1 1", "BombGun100kgJ 1", "BombGun100kgJ 1", null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1 1", "PylonKI43PLN1 1", "BombGun250kgJ 1", "BombGun250kgJ 1", null
        });
        Aircraft.weaponsRegister(class1, "2xdt200", new java.lang.String[] {
            "MGunBrowning50s_jap 250", "MGunBrowning50s_jap 250", "PylonKI43PLN1 1", "PylonKI43PLN1 1", null, null, "FuelTankGun_TankKi43Underwing 1", "FuelTankGun_TankKi43Underwing 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}
