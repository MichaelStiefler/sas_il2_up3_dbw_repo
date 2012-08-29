/* RocketSidewinderAH - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.maddox.il2.objects.weapons;
import java.io.IOException;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;

public class RocketSidewinderAH extends Rocket
{
    private FlightModel fm = null;
    private Eff3DActor fl1 = null;
    private Eff3DActor fl2 = null;
    private static Orient or = new Orient();
    private static Point3d p = new Point3d();
    private static Point3d pT = new Point3d();
    private static Vector3d v = new Vector3d();
    private static Actor hunted = null;
    private long tStart = 0L;
    private float prevd = 0.0F;
    private float deltaAzimuth = 0.0F;
    private float deltaTangage = 0.0F;
    private double d = 0.0;
    private Actor victim = null;
    private float baseSpeed = 0.0F;
    private float targetFactor = 0.0F;
    private float baseFactor = 0.0F;
    private double dPi = 3.141592653589793;
    /*synthetic*/ static Class class$com$maddox$il2$objects$weapons$RocketSidewinderAH;
    
    class Master extends ActorNet implements NetUpdate
    {
	NetMsgFiltered out = new NetMsgFiltered();
	
	public void msgNetNewChannel(NetChannel netchannel) {
	    if (Actor.isValid(actor()) && !netchannel.isMirrored(this)) {
		try {
		    if (netchannel.userState == 0) {
			NetMsgSpawn netmsgspawn
			    = actor().netReplicate(netchannel);
			if (netmsgspawn != null) {
			    postTo(netchannel, netmsgspawn);
			    actor().netFirstUpdate(netchannel);
			}
		    }
		} catch (Exception exception) {
		    NetObj.printDebug(exception);
		}
	    }
	}
	
	public boolean netInput(NetMsgInput netmsginput) throws IOException {
	    return false;
	}
	
	public void netUpdate() {
	    try {
		out.unLockAndClear();
		pos.getAbs(RocketSidewinderAH.p, RocketSidewinderAH.or);
		out.writeFloat((float) RocketSidewinderAH.p.x);
		out.writeFloat((float) RocketSidewinderAH.p.y);
		out.writeFloat((float) RocketSidewinderAH.p.z);
		RocketSidewinderAH.or.wrap();
		int i = (int) (RocketSidewinderAH.or.getYaw() * 32000.0F
			       / 180.0F);
		int i_0_ = (int) (RocketSidewinderAH.or.tangage() * 32000.0F
				  / 90.0F);
		out.writeShort(i);
		out.writeShort(i_0_);
		post(Time.current(), out);
	    } catch (Exception exception) {
		NetObj.printDebug(exception);
	    }
	}
	
	public Master(Actor actor) {
	    super(actor);
	}
    }
    
    class Mirror extends ActorNet
    {
	NetMsgFiltered out = new NetMsgFiltered();
	
	public void msgNetNewChannel(NetChannel netchannel) {
	    if (Actor.isValid(actor()) && !netchannel.isMirrored(this)) {
		try {
		    if (netchannel.userState == 0) {
			NetMsgSpawn netmsgspawn
			    = actor().netReplicate(netchannel);
			if (netmsgspawn != null) {
			    postTo(netchannel, netmsgspawn);
			    actor().netFirstUpdate(netchannel);
			}
		    }
		} catch (Exception exception) {
		    NetObj.printDebug(exception);
		}
	    }
	}
	
	public boolean netInput(NetMsgInput netmsginput) throws IOException {
	    if (netmsginput.isGuaranted())
		return false;
	    if (isMirrored()) {
		out.unLockAndSet(netmsginput, 0);
		postReal(Message.currentTime(true), out);
	    }
	    RocketSidewinderAH.p.x = (double) netmsginput.readFloat();
	    RocketSidewinderAH.p.y = (double) netmsginput.readFloat();
	    RocketSidewinderAH.p.z = (double) netmsginput.readFloat();
	    short i = netmsginput.readShort();
	    short i_1_ = netmsginput.readShort();
	    float f = -((float) i * 180.0F / 32000.0F);
	    float f_2_ = (float) i_1_ * 90.0F / 32000.0F;
	    RocketSidewinderAH.or.set(f, f_2_, 0.0F);
	    pos.setAbs(RocketSidewinderAH.p, RocketSidewinderAH.or);
	    return true;
	}
	
	public Mirror(Actor actor, NetChannel netchannel, int i) {
	    super(actor, netchannel, i);
	}
    }
    
    static class SPAWN implements NetSpawn
    {
	public void netSpawn(int i, NetMsgInput netmsginput) {
	    NetObj netobj = netmsginput.readNetObj();
	    if (netobj != null) {
		try {
		    Actor actor = (Actor) netobj.superObj();
		    Point3d point3d
			= new Point3d((double) netmsginput.readFloat(),
				      (double) netmsginput.readFloat(),
				      (double) netmsginput.readFloat());
		    Orient orient = new Orient(netmsginput.readFloat(),
					       netmsginput.readFloat(), 0.0F);
		    float f = netmsginput.readFloat();
		    RocketSidewinderAH rocketsidewinderah
			= new RocketSidewinderAH(actor, netmsginput.channel(),
						 i, point3d, orient, f);
		} catch (Exception exception) {
		    System.out.println(exception.getMessage());
		    exception.printStackTrace();
		}
	    }
	}
    }
    
    public boolean interpolateStep() {
	float f = Time.tickLenFs();
	float f_3_ = (float) getSpeed(null);
	if (Time.current() > tStart + 2200L)
	    flame.drawing(false);
	else {
	    long l = Time.current() - tStart;
	    double d = (double) l / 2200.0 * dPi + dPi;
	    double d_4_ = (Math.cos(d) + 1.0) / 2.0;
	    f_3_ = baseFactor + (targetFactor - baseFactor) * (float) d_4_;
	}
	pos.getAbs(p, or);
	v.set(1.0, 0.0, 0.0);
	or.transform(v);
	v.scale((double) f_3_);
	setSpeed(v);
	p.x += v.x * (double) f;
	p.y += v.y * (double) f;
	p.z += v.z * (double) f;
	if (isNet() && isNetMirror()) {
	    pos.setAbs(p, or);
	    return false;
	}
	if (!Actor.isValid(victim))
	    victim = War.GetNearestEnemyAircraft(this, 2500.0F, 9);
	if (victim.getArmy() == getOwner().getArmy())
	    victim = null;
	if (victim != null) {
	    if (victim instanceof Aircraft || victim instanceof BigshipGeneric)
		pT = victim.pos.getAbsPoint();
	    else
		victim.pos.getAbs(pT);
	    pT.sub(p);
	    or.transformInv(pT);
	    if (Time.current() > tStart + 1000L) {
		if (pT.x > -10.0) {
		    this.d = 0.1;
		    if (pT.y > this.d)
			deltaAzimuth = -1.0F;
		    if (pT.y < -this.d)
			deltaAzimuth = 1.0F;
		    if (pT.z < -this.d)
			deltaTangage = -1.0F;
		    if (pT.z > this.d)
			deltaTangage = 1.0F;
		}
		or.increment(12.0F * f * deltaAzimuth,
			     12.0F * f * deltaTangage, 0.0F);
		or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
	    } else
		or.setYPR(or.getYaw(), or.getPitch() + 0.4F, 0.0F);
	} else if (Time.current() < tStart + 1000L)
	    or.setYPR(or.getYaw(), or.getPitch() + 0.4F, 0.0F);
	deltaAzimuth = deltaTangage = 0.0F;
	pos.setAbs(p, or);
	if (Time.current() > tStart + 500L) {
	    if (Actor.isValid(victim)) {
		float f_5_ = (float) p.distance(victim.pos.getAbsPoint());
		if (victim instanceof Aircraft && f_5_ > prevd
		    && prevd != 1000.0F) {
		    if (f_5_ < 30.0F) {
			doExplosionAir();
			postDestroy();
			collide(false);
			drawing(false);
		    }
		    victim = null;
		}
		prevd = f_5_;
	    } else
		prevd = 1000.0F;
	}
	if (!Actor.isValid(getOwner()) || !(getOwner() instanceof Aircraft))
	    return false;
	return false;
    }
    
    public RocketSidewinderAH() {
	d = 0.1;
	victim = null;
	fm = null;
	tStart = 0L;
	prevd = 1000.0F;
	deltaAzimuth = 0.0F;
	deltaTangage = 0.0F;
	hunted = null;
    }
    
    public RocketSidewinderAH(Actor actor, NetChannel netchannel, int i,
			      Point3d point3d, Orient orient, float f) {
	d = 0.1;
	victim = null;
	hunted = null;
	fm = null;
	tStart = 0L;
	prevd = 1000.0F;
	net = new Mirror(this, netchannel, i);
	pos.setAbs(point3d, orient);
	pos.reset();
	pos.setBase(actor, null, true);
	doStart(-1.0F);
	v.set(1.0, 0.0, 0.0);
	orient.transform(v);
	v.scale((double) f);
	setSpeed(v);
	collide(false);
    }
    
    public void start(float f) {
	Actor actor = pos.base();
	if (Actor.isValid(actor) && actor instanceof Aircraft) {
	    if (actor.isNetMirror()) {
		destroy();
		return;
	    }
	    net = new Master(this);
	}
	doStart(f);
    }
    
    private void doStart(float f) {
	super.start(-1.0F);
	pos.getRelOrient().transformInv(speed);
	speed.y *= 3.0;
	speed.z *= 3.0;
	speed.x -= 198.0;
	pos.getRelOrient().transform(speed);
	fm = ((Aircraft) getOwner()).FM;
	baseSpeed = fm.getSpeedKMH();
	tStart = Time.current();
	if (Config.isUSE_RENDER())
	    flame.drawing(true);
	pos.getAbs(p, or);
	or.setYPR(or.getYaw(), or.getPitch() - 6.0F - fm.getAOA(), 0.0F);
	pos.setAbs(p, or);
	baseFactor = (float) getSpeed(null);
	targetFactor = 555.55554F;
	if (!isNet() || !isNetMirror()) {
	    if ((getOwner() != World.getPlayerAircraft()
		 || !((RealFlightModel) fm).isRealMode())
		&& fm instanceof Pilot) {
		if (getOwner() instanceof TypeFighter) {
		    Pilot pilot = (Pilot) fm;
		    victim = pilot.target.actor;
		}
	    } else {
		victim = Main3D.cur3D().getViewPadlockEnemy();
		if (victim == null)
		    victim
			= Selector.look(true, false,
					(Main3D.cur3D()._camera3D
					 [Main3D.cur3D().getRenderIndx()]),
					World.getPlayerAircraft().getArmy(),
					-1, World.getPlayerAircraft(), false);
	    }
	}
    }
    
    public void destroy() {
	if (Config.isUSE_RENDER()) {
	    Eff3DActor.finish(fl1);
	    Eff3DActor.finish(fl2);
	}
	victim = null;
	hunted = null;
	fm = null;
	tStart = 0L;
	prevd = 1000.0F;
	super.destroy();
    }
    
    protected void doExplosion(Actor actor, String string) {
	pos.getTime(Time.current(), p);
	MsgExplosion.send(actor, string, p, getOwner(), 45.0F, 1.0F, 1,
			  400.0F);
	super.doExplosion(actor, string);
    }
    
    protected void doExplosionAir() {
	pos.getTime(Time.current(), p);
	MsgExplosion.send(null, null, p, getOwner(), 45.0F, 1.0F, 1, 400.0F);
	super.doExplosionAir();
    }
    
    public NetMsgSpawn netReplicate(NetChannel netchannel) throws IOException {
	NetMsgSpawn netmsgspawn = super.netReplicate(netchannel);
	netmsgspawn.writeNetObj(getOwner().net);
	Point3d point3d = pos.getAbsPoint();
	netmsgspawn.writeFloat((float) point3d.x);
	netmsgspawn.writeFloat((float) point3d.y);
	netmsgspawn.writeFloat((float) point3d.z);
	Orient orient = pos.getAbsOrient();
	netmsgspawn.writeFloat(orient.azimut());
	netmsgspawn.writeFloat(orient.tangage());
	float f = (float) getSpeed(null);
	netmsgspawn.writeFloat(f);
	return netmsgspawn;
    }
    
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
	    = (class$com$maddox$il2$objects$weapons$RocketSidewinderAH == null
	       ? (class$com$maddox$il2$objects$weapons$RocketSidewinderAH
		  = (class$
		     ("com.maddox.il2.objects.weapons.RocketSidewinderAH")))
	       : class$com$maddox$il2$objects$weapons$RocketSidewinderAH);
	Property.set(var_class, "mesh",
		     "3do/arms/RocketSidewinderAAS/RocketSidewinderAAS.sim");
	Property.set
	    (var_class, "sprite",
	     "3do/effects/RocketSidewinderAAS/RocketSidewinderAASSpriteBlack.eff");
	Property.set
	    (var_class, "flame",
	     "3do/Effects/RocketSidewinderAAS/RocketSidewinderAASFlame.sim");
	Property.set
	    (var_class, "smoke",
	     "3do/Effects/RocketSidewinderAAS/RocketSidewinderAASSmoke.eff");
	Property.set(var_class, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
	Property.set(var_class, "emitLen", 50.0F);
	Property.set(var_class, "emitMax", 0.4F);
	Property.set(var_class, "sound", "weapon.rocket_132");
	Property.set(var_class, "radius", 10.0F);
	Property.set(var_class, "timeLife", 30.0F);
	Property.set(var_class, "timeFire", 20.0F);
	Property.set(var_class, "force", 18712.0F);
	Property.set(var_class, "power", 0.0020F);
	Property.set(var_class, "powerType", 0);
	Property.set(var_class, "kalibr", 1.049F);
	Property.set(var_class, "massa", 60.0F);
	Property.set(var_class, "massaEnd", 45.0F);
	Spawn.add(var_class, new SPAWN());
    }
}
