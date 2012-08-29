// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mig_17F.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Mig_17, PaintSchemeFCSPar1956, PaintSchemeFMPar1956, PaintSchemeFMPar06, 
//            TypeGSuit, NetAircraft, Aircraft

public class Mig_17F extends com.maddox.il2.objects.air.Mig_17
    implements com.maddox.il2.objects.air.TypeGSuit
{

    public Mig_17F()
    {
    }

    public void getGFactors(com.maddox.il2.objects.air.TypeGSuit.GFactors theGFactors)
    {
        theGFactors.setGFactors(1.0F, 1.0F, 1.0F, 1.8F, 1.5F, 1.0F);
    }

    public void update(float f1)
    {
        super.update(f1);
        typeFighterAceMakerRangeFinder();
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F)
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 1.001F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 5);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 3);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
            }
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    private static final float NEG_G_TOLERANCE_FACTOR = 1F;
    private static final float NEG_G_TIME_FACTOR = 1F;
    private static final float NEG_G_RECOVERY_FACTOR = 1F;
    private static final float POS_G_TOLERANCE_FACTOR = 1.8F;
    private static final float POS_G_TIME_FACTOR = 1.5F;
    private static final float POS_G_RECOVERY_FACTOR = 1F;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.Mig_17F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MiG-17");
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar1956());
        com.maddox.rts.Property.set(class1, "meshName_sk", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName_hu", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1952.11F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MiG-17F.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMig_17.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 0, 0, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDroptanks", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}
