// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombFAB5000.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombFAB5000 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFAB5000()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
        {
            setMesh(com.maddox.rts.Property.stringValue(getClass(), "mesh"));
            mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 1));
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final int alhambras = 1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFAB5000.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FAB-5000/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 750F);
        com.maddox.rts.Property.set(class1, "power", 3260F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 1.0F);
        com.maddox.rts.Property.set(class1, "massa", 5000F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
