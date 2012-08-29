// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AR_196A3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            AR_196, PaintSchemeBMPar00, NetAircraft, Aircraft

public class AR_196A3 extends com.maddox.il2.objects.air.AR_196
{

    public AR_196A3()
    {
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(FM.Gears.clpGearEff[i][j] != null)
                {
                    tmpp.set(FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
                    tmpp.z = 0.01D;
                    FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
                    FM.Gears.clpGearEff[i][j].pos.reset();
                }

        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.AR_196A3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ar-196A-3.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ar-196A-3/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ar-196");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1938.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 10, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_CANNON02", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2sc50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMGFFki 60", "MGunMGFFki 60", "MGunMG15t 525", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
