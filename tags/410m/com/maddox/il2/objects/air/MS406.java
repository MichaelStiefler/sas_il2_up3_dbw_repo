// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MS406.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MS400X, PaintSchemeFCSPar02, PaintSchemeFMPar00, NetAircraft

public class MS406 extends com.maddox.il2.objects.air.MS400X
{

    public MS406()
    {
    }

    public void update(float f)
    {
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.MS406.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.2F, 0.0F);
        hierMesh().chunkSetLocate("OilRad_D0", xyz, ypr);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.MS406.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Morane");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/MS406(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/MS406(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MS406(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1951.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MS406.fmd");
        com.maddox.il2.objects.air.MS406.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        com.maddox.il2.objects.air.MS406.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        com.maddox.il2.objects.air.MS406.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMAC1934 300", "MGunMAC1934 300", "MGunHispanoMkIki 60"
        });
        com.maddox.il2.objects.air.MS406.weaponsRegister(class1, "3xMAC1934", new java.lang.String[] {
            "MGunMAC1934 300", "MGunMAC1934 300", "MGunMAC1934i 300"
        });
        com.maddox.il2.objects.air.MS406.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}
