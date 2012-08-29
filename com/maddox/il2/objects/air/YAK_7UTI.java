// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   YAK_7UTI.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK_7A, PaintSchemeFMPar02, TypeFighter, TypeTNBFighter, 
//            TypeScout, Aircraft, Cockpit, NetAircraft

public class YAK_7UTI extends com.maddox.il2.objects.air.YAK_7A
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter, com.maddox.il2.objects.air.TypeScout
{

    public YAK_7UTI()
    {
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = -com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.58F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.bHasCockpitDoorControl = true;
        FM.CT.dvCockpitDoor = 0.75F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -60F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -85F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.YAK_7UTI.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Water_luk", 0.0F, 12F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        hierMesh().chunkSetAngles("Oil_luk", 0.0F, 24F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        super.update(f);
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_7UTI.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Yak-7UTI/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitYAK_7UTIper.class, com.maddox.il2.objects.air.CockpitYAK_7Vzad.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Yak-7uti.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
