package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class PylonMG extends com.maddox.il2.objects.weapons.Pylon
{

    public PylonMG()
    {
    }

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.weapons.PylonMG.class, "mesh", "3DO/Arms/MGun/mono.sim");
    }
}
