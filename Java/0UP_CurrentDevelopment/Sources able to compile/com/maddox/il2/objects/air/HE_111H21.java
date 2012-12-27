package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class HE_111H21 extends HE_111
{

    public HE_111H21()
    {
    }

    public void update(float f)
    {
        if(FM.turret[5].tMode == 2)
            FM.turret[5].tMode = 4;
        super.update(f);
    }

    static 
    {
        java.lang.Class class1 = HE_111H21.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-6/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111H-21.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHE_111H6.class, CockpitHE_111H6_Bombardier.class, CockpitHE_111H6_NGunner.class, CockpitHE_111H2_TGunner.class, CockpitHE_111H2_BGunner.class, CockpitHE_111H2_LGunner.class, CockpitHE_111H2_RGunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xSD250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSD500 1", "BombGunNull 1", "BombGunSD500 1", "BombGunNull 1", 
            "BombGunSD500 1", "BombGunNull 1", "BombGunSD500 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "4xSC500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSC500 1", "BombGunNull 1", "BombGunSC500 1", "BombGunNull 1", 
            "BombGunSC500 1", "BombGunNull 1", "BombGunSC500 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "4xAB500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunAB500 1", "BombGunNull 1", "BombGunAB500 1", "BombGunNull 1", 
            "BombGunAB500 1", "BombGunNull 1", "BombGunAB500 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "2SC1000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSC1000 1", "BombGunNull 1", "BombGunSC1000 1", "BombGunNull 1", 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1SC1000_2SC500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSC500 1", "BombGunNull 1", "BombGunSC500 1", "BombGunNull 1", 
            "BombGunSC1000 1", "BombGunNull 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2SC1000_2SC250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSC1000 1", "BombGunNull 1", "BombGunSC1000 1", "BombGunNull 1", 
            "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "2PC1600", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunPC1600 1", "BombGunNull 1", "BombGunPC1600 1", "BombGunNull 1", 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1SC2000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", null, null, null, null, 
            "BombGunSC2000 1", "BombGunNull 1", null, null
        });
        Aircraft.weaponsRegister(class1, "1SC2000_2SC250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1", 
            "BombGunSC2000 1", "BombGunNull 1", null, null
        });
        Aircraft.weaponsRegister(class1, "1xPC1000RS", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", null, null, null, null, 
            "RocketGunPC1000RS 1", "BombGunNull 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2xPC1000RS", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "RocketGunPC1000RS 1", "BombGunNull 1", "RocketGunPC1000RS 1", "BombGunNull 1", 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "3xPC1000RS", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 750", "MGunMG131t 500", "MGunMG15120MGki 200", "MGunMG81t 500", "MGunMG81t 500", "RocketGunPC1000RS 1", "BombGunNull 1", "RocketGunPC1000RS 1", "BombGunNull 1", 
            "RocketGunPC1000RS 1", "BombGunNull 1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}