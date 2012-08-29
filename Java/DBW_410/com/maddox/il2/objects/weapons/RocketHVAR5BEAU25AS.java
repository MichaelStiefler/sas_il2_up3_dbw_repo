package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class RocketHVAR5BEAU25AS extends Rocket
{

    public RocketHVAR5BEAU25AS()
    {
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.weapons.RocketHVAR5BEAU25AS.class;
        Property.set(class1, "mesh", "3DO/Arms/Beau_rocket_25lbAS/mono.sim");
        Property.set(class1, "sprite", "3DO/Effects/Rocket/firesprite.eff");
        Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        Property.set(class1, "emitLen", 50F);
        Property.set(class1, "emitMax", 1.0F);
        Property.set(class1, "sound", "weapon.rocket_132");
        Property.set(class1, "radius", 5F);
        Property.set(class1, "timeLife", 999.999F);
        Property.set(class1, "timeFire", 3F);
        Property.set(class1, "force", 1656F);
        Property.set(class1, "power", 40F);
        Property.set(class1, "powerType", 0);
        Property.set(class1, "kalibr", 0.1F);
        Property.set(class1, "massa", 27.15F);
        Property.set(class1, "massaEnd", 21.43F);
    }
}