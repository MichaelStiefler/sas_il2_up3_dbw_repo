// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Mig_17A.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Mig_17, PaintSchemeFCSPar1956, PaintSchemeFMPar1956, PaintSchemeFMPar06, 
//            NetAircraft, Aircraft

public class Mig_17A extends com.maddox.il2.objects.air.Mig_17
{

    public Mig_17A()
    {
    }

    public void typeFighterAceMakerRangeFinder()
    {
        if(super.k14Mode == 0)
            return;
        hunted = com.maddox.il2.game.Main3D.cur3D().getViewPadlockEnemy();
        if(hunted == null)
            hunted = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.War.GetNearestEnemyAircraft(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, 2000F, 9)));
        if(hunted != null)
        {
            super.k14Distance = (float)((com.maddox.il2.engine.Interpolate) (super.FM)).actor.pos.getAbsPoint().distance(hunted.pos.getAbsPoint());
            if(super.k14Distance > 1700F)
                super.k14Distance = 1700F;
            else
            if(super.k14Distance < 200F)
                super.k14Distance = 200F;
        }
    }

    public void update(float f)
    {
        super.update(f);
        typeFighterAceMakerRangeFinder();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static com.maddox.il2.engine.Actor hunted = null;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.Mig_17A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "MiG-17");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ru", "3DO/Plane/MiG-17A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ru", ((java.lang.Object) (new PaintSchemeFCSPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_sk", "3DO/Plane/MiG-17A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_sk", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ro", "3DO/Plane/MiG-17A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ro", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_hu", "3DO/Plane/MiG-17A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_hu", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/MiG-17A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1952.11F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/MiG-17.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMig_17.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.725F);
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
