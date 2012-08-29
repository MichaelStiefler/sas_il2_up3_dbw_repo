package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombSB1000 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSB1000()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 25)
        {
            setMesh(com.maddox.rts.Property.stringValue(getClass(), "mesh"));
            mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 1));
        }
    }

    private static final int alhambras = 1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSB1000.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SB-1000/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 389F);
        com.maddox.rts.Property.set(class1, "power", 600F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6604F);
        com.maddox.rts.Property.set(class1, "massa", 1000F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
