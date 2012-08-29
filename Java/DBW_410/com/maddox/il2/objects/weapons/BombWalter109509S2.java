/* BombStarthilfe109500 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.maddox.il2.objects.weapons;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombWalter109509S2 extends Bomb
{
    private Chute chute = null;
    private boolean bOnChute = false;
    private static Orient or = new Orient();
    private static Orient or_ = new Orient(0.0F, 0.0F, 0.0F);
    private static Vector3d v3d = new Vector3d();
    private float ttcurTM;
    
    protected boolean haveSound() {
	return false;
    }
    
    public void start() {
	super.start();
	ttcurTM = World.Rnd().nextFloat(0.5F, 3.5F);
    }
    
    public void interpolateTick() {
	super.interpolateTick();
	if (bOnChute) {
	    pos.getAbs(or);
	    or.interpolate(or_, 0.4F);
	    pos.setAbs(or);
	    getSpeed(v3d);
	    v3d.scale(0.997);
	    if (v3d.z < -5.0)
		v3d.z += (double) (1.1F * Time.tickConstLenFs());
	    setSpeed(v3d);
	} else if (curTm > ttcurTM) {
	    bOnChute = true;
	    chute = new Chute(this);
	    chute.collide(false);
	    setMesh("3DO/Arms/Starthilfe109-500Chuted/mono.sim");
	}
    }
    
    public void msgCollision(Actor actor, String string, String string_0_) {
	if (actor instanceof ActorLand) {
	    if (chute != null)
		chute.landing();
	    postDestroy();
	} else
	    super.msgCollision(actor, string, string_0_);
    }
    
    static {
	Class var_class = CLASS.THIS();
	Property.set(var_class, "mesh", "3DO/Arms/Starthilfe109-500/mono.sim");
	Property.set(var_class, "radius", 0.1F);
	Property.set(var_class, "power", 0.0F);
	Property.set(var_class, "powerType", 0);
	Property.set(var_class, "kalibr", 0.7F);
	Property.set(var_class, "massa", 162F);
	Property.set(var_class, "sound", "weapon.bomb_phball");
    }
}
