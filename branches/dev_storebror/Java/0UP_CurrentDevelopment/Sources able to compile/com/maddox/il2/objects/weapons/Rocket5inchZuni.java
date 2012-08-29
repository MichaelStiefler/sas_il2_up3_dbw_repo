package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class Rocket5inchZuni extends com.maddox.il2.objects.weapons.RocketHVAR5
{

    public Rocket5inchZuni()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Rocket5inchZuni.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/5inchZuni/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Rocket/firesprite.eff");
        com.maddox.rts.Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 60F);
        com.maddox.rts.Property.set(class1, "timeLife", 999.999F);
        com.maddox.rts.Property.set(class1, "timeFire", 4F);
        com.maddox.rts.Property.set(class1, "force", 1300F);
        com.maddox.rts.Property.set(class1, "power", 25F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.132F);
        com.maddox.rts.Property.set(class1, "massa", 23.1F);
        com.maddox.rts.Property.set(class1, "massaEnd", 10.1F);
    }
}
