// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F2A2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F2A, PaintSchemeBMPar01, PaintSchemeFCSPar01, Cockpit, 
//            NetAircraft, PaintScheme

public class F2A2 extends com.maddox.il2.objects.air.F2A
{

    public F2A2()
    {
        bChangedExts = false;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        bChangedExts = true;
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        bChangedExts = true;
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public void update(float f)
    {
        super.update(f);
        if(bChangedExts)
            doFixBellyDoor();
    }

    public void doFixBellyDoor()
    {
        hierMesh().chunkVisible("CF1_D0", hierMesh().isChunkVisible("CF_D0"));
        hierMesh().chunkVisible("CF1_D1", hierMesh().isChunkVisible("CF_D1"));
        hierMesh().chunkVisible("CF1_D2", hierMesh().isChunkVisible("CF_D2"));
        hierMesh().chunkVisible("CF1_D3", hierMesh().isChunkVisible("CF_D3"));
        hierMesh().chunkVisible("Engine11_D0", hierMesh().isChunkVisible("Engine1_D0"));
        hierMesh().chunkVisible("Engine11_D1", hierMesh().isChunkVisible("Engine1_D1"));
        hierMesh().chunkVisible("Engine11_D2", hierMesh().isChunkVisible("Engine1_D2"));
        bChangedExts = false;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        xyz[0] = com.maddox.il2.objects.air.F2A2.cvt(f, 0.01F, 0.99F, 0.0F, 0.725F);
        hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public boolean bChangedExts;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F2A2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F2A");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/F2A-2(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/F2A-2(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F2A-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitF2A2.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.032F);
        com.maddox.il2.objects.air.F2A2.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        com.maddox.il2.objects.air.F2A2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.F2A2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning50k 250", "MGunBrowning50k 250"
        });
        com.maddox.il2.objects.air.F2A2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
