// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A6M2N.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A6M, PaintSchemeFMPar01, PaintSchemeFCSPar01, TypeSailPlane, 
//            NetAircraft

public class A6M2N extends com.maddox.il2.objects.air.A6M
    implements com.maddox.il2.objects.air.TypeSailPlane
{

    public A6M2N()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    public void moveSteering(float f)
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        super.hitBone(s, shot, point3d);
        if(s.startsWith("xgearc"))
            hitChunk("GearC2", shot);
        if(s.startsWith("xgearl"))
            hitChunk("GearL2", shot);
        if(s.startsWith("xgearr"))
            hitChunk("GearR2", shot);
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

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("GearC11_D0", 0.0F, -30F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.A6M2N.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M2-N(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M2-N(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M2N.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitA6M2N.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.A6M2N.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 
            3
        });
        com.maddox.il2.objects.air.A6M2N.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", 
            "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.A6M2N.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15sipzl 1000", "MGunMG15sipzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M2N.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG15sipzl 1000", "MGunMG15sipzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", 
            "BombGun50kg"
        });
        com.maddox.il2.objects.air.A6M2N.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
