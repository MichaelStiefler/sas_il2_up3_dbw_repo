/* FuelTank_TankSpit90 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.maddox.il2.objects.weapons;
import com.maddox.rts.Property;

public class FuelTank_TankSpit170 extends FuelTank
{
    /*synthetic*/ static Class class$com$maddox$il2$objects$weapons$FuelTank_TankSpit170;
    
    /*synthetic*/ static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
    
    static {
	Class var_class
	    = (class$com$maddox$il2$objects$weapons$FuelTank_TankSpit170 == null
	       ? (class$com$maddox$il2$objects$weapons$FuelTank_TankSpit170
		  = (class$
		     ("com.maddox.il2.objects.weapons.FuelTank_TankSpit170")))
	       : class$com$maddox$il2$objects$weapons$FuelTank_TankSpit170);
	Property.set(var_class, "mesh",
		     "3DO/Arms/Spit_DropTank_170gal/mono.sim");
	Property.set(var_class, "kalibr", 0.6F);
	Property.set(var_class, "massa", 645.0F);
    }
}
