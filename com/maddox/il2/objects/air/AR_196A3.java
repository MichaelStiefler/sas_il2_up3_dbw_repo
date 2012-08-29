// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AR_196A3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            AR_196, PaintSchemeBMPar00, Aircraft, NetAircraft

public class AR_196A3 extends com.maddox.il2.objects.air.AR_196
{

    public AR_196A3()
    {
    }

    public void update(float f)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.clpGearEff[i][j] != null)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpp)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.clpGearEff[i][j])).pos.getAbsPoint())));
                    tmpp.z = 0.01D;
                    ((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.clpGearEff[i][j])).pos.setAbs(tmpp);
                    ((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).Gears.clpGearEff[i][j])).pos.reset();
                }

        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return class1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.AR_196A3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Ar-196A-3.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Ar-196A-3/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Ar-196");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1938.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitAR_196A3.class, com.maddox.il2.objects.air.CockpitAR_196_Gunner.class
        })));
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
