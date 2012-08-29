// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   I_16TYPE17.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_16, PaintSchemeFMPar01, PaintSchemeFCSPar01, TypeFighter, 
//            TypeTNBFighter, NetAircraft, Aircraft

public class I_16TYPE17 extends com.maddox.il2.objects.air.I_16
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public I_16TYPE17()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -55F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -55F * f, 0.0F);
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

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.I_16TYPE17.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/I-16type17/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ru", "3DO/Plane/I-16type17(ru)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ru", ((java.lang.Object) (new PaintSchemeFCSPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1938F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitI_16TYPE_Early.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/I-16type17.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
