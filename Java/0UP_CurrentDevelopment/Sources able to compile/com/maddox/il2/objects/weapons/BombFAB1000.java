package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombFAB1000 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFAB1000()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 10)
        {
            setMesh(com.maddox.rts.Property.stringValue(getClass(), "mesh"));
            mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 8));
        }
    }

    private static final int alhambras = 8;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFAB1000.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FAB-1000/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 150F);
        com.maddox.rts.Property.set(class1, "power", 555F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.57F);
        com.maddox.rts.Property.set(class1, "massa", 1000F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
