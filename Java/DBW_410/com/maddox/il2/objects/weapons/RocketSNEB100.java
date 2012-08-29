package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class RocketSNEB100 extends Rocket {
	 public RocketSNEB100()
	    {
	    }
	 
	    public void start(float f, int i)
	    {
	        setMesh("3DO/Arms/SNEB_100mm/mono_open.sim");
	        super.start(f);
	        speed.normalize();
	        speed.scale(525D);
	        noGDelay = -1L;
	    }
	 
 static 
 {
     Class class1 = com.maddox.il2.objects.weapons.RocketSNEB100.class;
     Property.set(class1, "mesh", "3DO/Arms/SNEB100mm/mono.sim");
     Property.set(class1, "sprite", "3DO/Effects/Rocket/firesprite.eff");
     Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
     Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
     Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
     Property.set(class1, "emitLen", 50F);
     Property.set(class1, "emitMax", 1.0F);
     Property.set(class1, "sound", "weapon.rocket_132");
     Property.set(class1, "radius", 60F);
     Property.set(class1, "timeLife", 999.999F);
     Property.set(class1, "timeFire", 4F);
     Property.set(class1, "force", 1965F);
     Property.set(class1, "power", 20F);
     Property.set(class1, "powerType", 0);
     Property.set(class1, "kalibr", 0.100F);
     Property.set(class1, "massa", 42.0F);
     Property.set(class1, "massaEnd", 29.5F);
 }

}
