package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Mesh;
import com.maddox.rts.Property;

public class BombFatMan extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFatMan()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 20)
        {
            setMesh(com.maddox.rts.Property.stringValue(getClass(), "mesh"));
            mesh.materialReplace("Ordnance1", "alhambra" + com.maddox.il2.ai.World.Rnd().nextInt(1, 1));
        }
    }

    private static final int alhambras = 1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFatMan.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FatMan/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 3200F);
        com.maddox.rts.Property.set(class1, "power", 1.3E+007F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 1.0F);
        com.maddox.rts.Property.set(class1, "massa", 4630F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
        com.maddox.rts.Property.set(class1, "newEffect", 1);
        com.maddox.rts.Property.set(class1, "nuke", 1);
    }
}
