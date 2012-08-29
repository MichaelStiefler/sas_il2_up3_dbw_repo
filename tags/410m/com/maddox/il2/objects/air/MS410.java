// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MS410.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MS400X, PaintSchemeFCSPar02, PaintSchemeFMPar01, NetAircraft

public class MS410 extends com.maddox.il2.objects.air.MS400X
{

    public MS410()
    {
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("OilRad_D0", 0.0F, -20F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.MS410.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Morane");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/MS410(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/MS410(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MS410(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1951.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MS410.fmd");
        com.maddox.il2.objects.air.MS410.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1
        });
        com.maddox.il2.objects.air.MS410.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01"
        });
        com.maddox.il2.objects.air.MS410.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMAC1934 500", "MGunMAC1934 500", "MGunMAC1934 500", "MGunMAC1934 500", "MGunHispanoMkIki 60"
        });
        com.maddox.il2.objects.air.MS410.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}
