// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A6M2_21.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A6M, PaintSchemeFMPar01, PaintSchemeFCSPar01, NetAircraft

public class A6M2_21 extends com.maddox.il2.objects.air.A6M
{

    public A6M2_21()
    {
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLOut_D0", 0.0F, 110F * f, 0.0F);
        hiermesh.chunkSetAngles("WingROut_D0", 0.0F, -110F * f, 0.0F);
    }

    public void moveWingFold(float f)
    {
        moveWingFold(hierMesh(), f);
        FM.doRequestFMSFX(1, (int)com.maddox.il2.objects.air.A6M2_21.cvt(f, 0.1F, 1.0F, 0.0F, 40F));
    }

    public void update(float f)
    {
        super.update(f);
        if(FM.CT.getArrestor() > 0.2F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f1 = com.maddox.il2.objects.air.A6M2_21.cvt(FM.Gears.arrestorVAngle, -26F, 11F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-42F * FM.Gears.arrestorVSink) / 37F;
                if(f2 < 0.0F && FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && FM.CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.A6M2_21.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M2-21(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M2-21(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M2-21.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitA6M2.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.A6M2_21.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 
            3
        });
        com.maddox.il2.objects.air.A6M2_21.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", 
            "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.A6M2_21.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M2_21.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", "FuelTankGun_Tank0", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M2_21.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M2_21.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", 
            "BombGun50kg"
        });
        com.maddox.il2.objects.air.A6M2_21.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
