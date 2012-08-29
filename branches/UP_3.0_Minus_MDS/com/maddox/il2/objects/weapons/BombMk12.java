// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombMk12.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombMk12 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk12()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
        {
            ((com.maddox.il2.engine.ActorMesh)this).setMesh(com.maddox.rts.Property.stringValue(((java.lang.Object)this).getClass(), "mesh"));
            ((com.maddox.il2.engine.ActorMesh)this).mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 1));
        }
    }

    private static final int alhambras = 1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk12.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/MK12/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 5000F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 1.4E+007F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 520F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_big");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "newEffect", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "nuke", 1);
    }
}
