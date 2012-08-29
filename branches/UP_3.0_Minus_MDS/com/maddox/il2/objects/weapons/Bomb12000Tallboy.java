// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   12000Tallboy.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb12000Tallboy extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb12000Tallboy()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
        {
            ((com.maddox.il2.engine.ActorMesh)this).setMesh(com.maddox.rts.Property.stringValue(((java.lang.Object)this).getClass(), "mesh"));
            mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 1));
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
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private static final int alhambras = 1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb12000Tallboy.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Tallboy/Tallboy.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 1500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 4500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 2500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_big");
    }
}
