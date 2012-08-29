// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CW_21.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CW21xyz, PaintSchemeFMPar00du, Aircraft, NetAircraft

public class CW_21 extends com.maddox.il2.objects.air.CW21xyz
{

    public CW_21()
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

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.8F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.99F, 0.0F, -82F), 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("LLight_D0", 0.0F, f1, 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, 0.09F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 75F);
        hiermesh.chunkSetLocate("GearC3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.CW_21.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public void moveWheelSink()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.CW_21.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "CW-21");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/CW-21(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00du());
        com.maddox.rts.Property.set(class1, "meshName_du", "3DO/Plane/CW-21(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_du", new PaintSchemeFMPar00du());
        com.maddox.rts.Property.set(class1, "yearService", 1940.6F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/CW-21.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303ki 300", "MGunBrowning303ki 300", "MGunBrowning303ki 300", "MGunBrowning303ki 300"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
