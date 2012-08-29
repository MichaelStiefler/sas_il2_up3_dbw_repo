/*4.10.1 class*/
package com.maddox.il2.objects.ships;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.VisibilityLong;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiSupportMethods_ResourcesManagement;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.ZutiSupportMethods_NetSend;

public class ShipGeneric extends ActorHMesh implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Predator, ActorAlign, HunterInterface, VisibilityLong
{
	private ShipProperties prop;
	private FiringDevice[] arms;
	private ArrayList path;
	private int cachedSeg;
	private float bodyDepth;
	private float bodyYaw;
	private float bodyPitch;
	private float bodyRoll;
	private float seaDepth;
	private long timeOfDeath;
	private long timeForRotation;
	private float drownBodyPitch;
	private float drownBodyRoll;
	private float sinkingDepthSpeed;
	private float life;
	private int dying;
	static final int DYING_NONE = 0;
	static final int DYING_SINK = 1;
	static final int DYING_DEAD = 2;
	private long respawnDelay;
	private long wakeupTmr;
	public float DELAY_WAKEUP;
	public int SKILL_IDX;
	public float SLOWFIRE_K;
	private Eff3DActor pipe;
	private Eff3DActor[] wake;
	private Eff3DActor nose;
	private Eff3DActor tail;
	private static ShipProperties constr_arg1 = null;
	private static ActorSpawnArg constr_arg2 = null;
	private static Point3d p = new Point3d();
	private static Point3d p1 = new Point3d();
	private static Point3d p2 = new Point3d();
	private static Orient o = new Orient();
	private static Vector3d tmpv = new Vector3d();
	private NetMsgFiltered outCommand;
	
	public static class ShipGunProperties
	{
		public Class gunClass = null;
		public int WEAPONS_MASK = 4;
		public boolean TRACKING_ONLY = false;
		public float ATTACK_MAX_DISTANCE = 1.0F;
		public float ATTACK_MAX_RADIUS = 1.0F;
		public float ATTACK_MAX_HEIGHT = 1.0F;
		public int ATTACK_FAST_TARGETS = 1;
		public float FAST_TARGETS_ANGLE_ERROR = 0.0F;
		public AnglesRange HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
		public float HEAD_STD_YAW = 0.0F;
		public float GUN_MIN_PITCH = -20.0F;
		public float GUN_STD_PITCH = -18.0F;
		public float GUN_MAX_PITCH = -15.0F;
		public float HEAD_MAX_YAW_SPEED = 720.0F;
		public float GUN_MAX_PITCH_SPEED = 60.0F;
		public float DELAY_AFTER_SHOOT = 1.0F;
		public float CHAINFIRE_TIME = 0.0F;
		public Point3d fireOffset;
		public Orient fireOrient;
	}
	
	public static class ShipProperties
	{
		public String meshName = null;
		public String soundName = null;
		public StrengthProperties stre = new StrengthProperties();
		public int WEAPONS_MASK = 4;
		public int HITBY_MASK = -2;
		public float ATTACK_MAX_DISTANCE = 1.0F;
		public float SLIDER_DIST = 1.0F;
		public float SPEED = 1.0F;
		public float DELAY_RESPAWN_MIN = 15.0F;
		public float DELAY_RESPAWN_MAX = 30.0F;
		public ShipGunProperties[] guns = null;
		public int nGuns;
	}
	
	public class FiringDevice
	{
		private int id;
		private Gun gun;
		private Aim aime;
		private float headYaw;
		private float gunPitch;
	}
	
	private class Segment
	{
		public Point3d posIn;
		public Point3d posOut;
		public float length;
		public long timeIn;
		public long timeOut;
		public float speedIn;
		public float speedOut;
		
		private Segment()
		{
		/* empty */
		}
	}
	
	static class HookNamedZ0 extends HookNamed
	{
		public void computePos(Actor actor, Loc loc, Loc loc_0_)
		{
			super.computePos(actor, loc, loc_0_);
			loc_0_.getPoint().z = 0.25;
		}
		
		public HookNamedZ0(ActorMesh actormesh, String string)
		{
			super(actormesh, string);
		}
		
		public HookNamedZ0(Mesh mesh, String string)
		{
			super(mesh, string);
		}
	}
	
	class Move extends Interpolate
	{
		public boolean tick()
		{
			//TODO: Added by |ZUTI|: second check
			if (dying == 0)
			{
				if (path != null)
				{
					bodyDepth = 0.0F;
					bodyPitch = bodyRoll = 0.0F;
					ShipGeneric.this.setMovablePosition(NetServerParams.getServerTime());
				}
				if (wakeupTmr == 0L)
				{
					for (int i = 0; i < prop.nGuns; i++)
						arms[i].aime.tick_();
				}
				else if (wakeupTmr > 0L)
					access$1710(ShipGeneric.this);
				else if (access$1704(ShipGeneric.this) == 0L)
				{
					if (ShipGeneric.this.isAnyEnemyNear())
						wakeupTmr = SecsToTicks(Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
					else
						wakeupTmr = -SecsToTicks(Rnd(4.0F, 7.0F));
				}
				return true;
			}
			if (dying == 2)
			{
				//TODO: Edited by |ZUTI|
				// if (path != null || !Mission.isDeathmatch())
				if (path != null)
				{
					ShipGeneric.this.eraseGuns();
					return false;
				}
				if (access$2310(ShipGeneric.this) > 0L)
					return true;
				if (!ShipGeneric.this.isNetMaster())
				{
					respawnDelay = 10000L;
					return true;
				}
				life = 1.0F;
				dying = 0;
				wakeupTmr = 0L;
				ShipGeneric.this.setDiedFlag(false);
				ShipGeneric.this.forgetAllAiming();
				ShipGeneric.this.setDefaultLivePose();
				bodyDepth = 0.0F;
				bodyPitch = bodyRoll = 0.0F;
				ShipGeneric.this.setPosition();
				pos.reset();
				ShipGeneric.this.send_RespawnCommand();
				return true;
			}
			//TODO: Edited by |ZUTI|
			// long l = Time.tickNext() - timeOfDeath;
			long l = NetServerParams.getServerTime() - timeOfDeath;
			if (l <= 0L)
				l = 0L;
			if (l >= timeForRotation)
			{
				bodyPitch = drownBodyPitch;
				bodyRoll = drownBodyRoll;
			}
			else
			{
				bodyPitch = drownBodyPitch * ((float)l / (float)timeForRotation);
				bodyRoll = drownBodyRoll * ((float)l / (float)timeForRotation);
			}
			//TODO: Edited by |ZUTI|
			// these small suckers sink too slow for my taste!!! 10 times slower as big ships
			// bodyDepth = sinkingDepthSpeed * (float) l * 0.0010F;
			bodyDepth = sinkingDepthSpeed * (float)l * 0.050F;
			if (bodyDepth >= 5.0F)
			{
				float f = Math.abs(Geom.sinDeg(bodyPitch) * ShipGeneric.this.collisionR());
				f += bodyDepth;
				if (f + bodyDepth >= seaDepth)
					dying = 2;
				if (bodyDepth > ShipGeneric.this.mesh().visibilityR())
					dying = 2;
			}
			if (path != null)
				ShipGeneric.this.setMovablePosition(timeOfDeath);
			else
				ShipGeneric.this.setPosition();
			return true;
		}
	}
	
	class Master extends ActorNet
	{
		public boolean netInput(NetMsgInput netmsginput) throws IOException
		{
			if (netmsginput.isGuaranted())
				return true;
			if (netmsginput.readByte() != 68)
				return false;
			if (dying != 0)
				return true;
			com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
			Actor actor = netobj != null ? ((ActorNet)netobj).actor() : null;
			ShipGeneric.this.Die(actor, -1L, true);
			return true;
		}
		
		public Master(Actor actor)
		{
			super(actor);
		}
	}
	
	class Mirror extends ActorNet
	{
		NetMsgFiltered out = new NetMsgFiltered();
		
		public boolean netInput(NetMsgInput netmsginput) throws IOException
		{
			if (netmsginput.isGuaranted())
			{
				switch (netmsginput.readByte())
				{
					case 73 :
						if (isMirrored())
						{
							NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
							post(netmsgguaranted);
						}
						timeOfDeath = netmsginput.readLong();
						if (timeOfDeath < 0L)
						{
							if (dying == 0)
							{
								life = 1.0F;
								ShipGeneric.this.setDefaultLivePose();
								ShipGeneric.this.forgetAllAiming();
							}
						}
						else if (dying == 0)
							ShipGeneric.this.Die(null, timeOfDeath, false);
						return true;
					case 82 :
						if (isMirrored())
						{
							NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
							post(netmsgguaranted);
						}
						life = 1.0F;
						dying = 0;
						ShipGeneric.this.setDiedFlag(false);
						setMesh(prop.meshName);
						ShipGeneric.this.setDefaultLivePose();
						ShipGeneric.this.forgetAllAiming();
						bodyDepth = 0.0F;
						bodyPitch = bodyRoll = 0.0F;
						ShipGeneric.this.setPosition();
						pos.reset();
						return true;
					case 68 :
						if (isMirrored())
						{
							NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 1);
							post(netmsgguaranted);
						}
						timeOfDeath = netmsginput.readLong();
						if (timeOfDeath >= 0L && dying == 0)
						{
							com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
							Actor actor = (netobj != null ? ((ActorNet)netobj).actor() : null);
							ShipGeneric.this.Die(actor, timeOfDeath, true);
						}
						return true;
					default :
						return false;
				}
			}
			switch (netmsginput.readByte())
			{
				default :
					break;
				case 84 :
				{
					if (isMirrored())
					{
						out.unLockAndSet(netmsginput, 1);
						out.setIncludeTime(false);
						postReal(Message.currentRealTime(), out);
					}
					byte i = netmsginput.readByte();
					com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
					Actor actor = netobj != null ? ((ActorNet)netobj).actor() : null;
					int i_1_ = netmsginput.readUnsignedByte();
					ShipGeneric.this.Track_Mirror(i, actor, i_1_);
					break;
				}
				case 70 :
				{
					if (isMirrored())
					{
						out.unLockAndSet(netmsginput, 1);
						out.setIncludeTime(true);
						postReal(Message.currentRealTime(), out);
					}
					byte i = netmsginput.readByte();
					com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
					Actor actor = netobj != null ? ((ActorNet)netobj).actor() : null;
					float f = netmsginput.readFloat();
					float f_2_ = (0.0010F * (float)(Message.currentGameTime() - Time.current()) + f);
					int i_3_ = netmsginput.readUnsignedByte();
					ShipGeneric.this.Fire_Mirror(i, actor, i_3_, f_2_);
					break;
				}
				case 68 :
					out.unLockAndSet(netmsginput, 1);
					out.setIncludeTime(false);
					postRealTo(Message.currentRealTime(), masterChannel(), out);
			}
			return true;
		}
		
		public Mirror(Actor actor, NetChannel netchannel, int i)
		{
			super(actor, netchannel, i);
		}
	}
	
	public static class SPAWN implements ActorSpawn
	{
		public Class cls;
		
		public ShipProperties proper;
		
		private static float getF(SectFile sectfile, String string, String string_4_, float f, float f_5_)
		{
			float f_6_ = sectfile.get(string, string_4_, -9865.345F);
			if (f_6_ == -9865.345F || f_6_ < f || f_6_ > f_5_)
			{
				if (f_6_ == -9865.345F)
					System.out.println("Ship: Value of [" + string + "]:<" + string_4_ + "> " + "not found");
				else
					System.out.println("Ship: Value of [" + string + "]:<" + string_4_ + "> (" + f_6_ + ")" + " is out of range (" + f + ";" + f_5_ + ")");
				throw new RuntimeException("Can't set property");
			}
			return f_6_;
		}
		
		private static String getS(SectFile sectfile, String string, String string_7_)
		{
			String string_8_ = sectfile.get(string, string_7_);
			if (string_8_ == null || string_8_.length() <= 0)
			{
				System.out.print("Ship: Value of [" + string + "]:<" + string_7_ + "> not found");
				throw new RuntimeException("Can't set property");
			}
			return new String(string_8_);
		}
		
		private static ShipProperties LoadShipProperties(SectFile sectfile, String string, Class var_class)
		{
			ShipProperties shipproperties = new ShipProperties();
			shipproperties.meshName = getS(sectfile, string, "Mesh");
			shipproperties.soundName = getS(sectfile, string, "SoundMove");
			if (shipproperties.soundName.equalsIgnoreCase("none"))
				shipproperties.soundName = null;
			if (!shipproperties.stre.read("Ship", sectfile, null, string))
				throw new RuntimeException("Can't register Ship object");
			int i;
			for (i = 0; sectfile.sectionIndex(string + ":Gun" + i) >= 0; i++)
			{
				/* empty */
			}
			shipproperties.guns = new ShipGunProperties[i];
			shipproperties.WEAPONS_MASK = 0;
			shipproperties.ATTACK_MAX_DISTANCE = 1.0F;
			for (int i_12_ = 0; i_12_ < i; i_12_++)
			{
				shipproperties.guns[i_12_] = new ShipGunProperties();
				ShipGunProperties shipgunproperties = shipproperties.guns[i_12_];
				String string_13_ = string + ":Gun" + i_12_;
				String string_14_ = ("com.maddox.il2.objects.weapons." + getS(sectfile, string_13_, "Gun"));
				try
				{
					shipgunproperties.gunClass = Class.forName(string_14_);
				}
				catch (Exception exception)
				{
					System.out.println("Ship: Can't find gun class '" + string_14_ + "'");
					throw new RuntimeException("Can't register Ship object");
				}
				shipgunproperties.WEAPONS_MASK = Gun.getProperties(shipgunproperties.gunClass).weaponType;
				if (shipgunproperties.WEAPONS_MASK == 0)
				{
					System.out.println("Ship: Undefined weapon type in gun class '" + string_14_ + "'");
					throw new RuntimeException("Can't register Ship object");
				}
				shipproperties.WEAPONS_MASK |= shipgunproperties.WEAPONS_MASK;
				shipgunproperties.ATTACK_MAX_DISTANCE = getF(sectfile, string_13_, "AttackMaxDistance", 6.0F, 50000.0F);
				shipgunproperties.ATTACK_MAX_RADIUS = getF(sectfile, string_13_, "AttackMaxRadius", 6.0F, 50000.0F);
				shipgunproperties.ATTACK_MAX_HEIGHT = getF(sectfile, string_13_, "AttackMaxHeight", 6.0F, 15000.0F);
				if (shipgunproperties.ATTACK_MAX_DISTANCE > shipproperties.ATTACK_MAX_DISTANCE)
					shipproperties.ATTACK_MAX_DISTANCE = shipgunproperties.ATTACK_MAX_DISTANCE;
				shipgunproperties.TRACKING_ONLY = false;
				if (sectfile.exist(string_13_, "TrackingOnly"))
					shipgunproperties.TRACKING_ONLY = true;
				shipgunproperties.ATTACK_FAST_TARGETS = 1;
				if (sectfile.exist(string_13_, "FireFastTargets"))
				{
					float f = getF(sectfile, string_13_, "FireFastTargets", 0.0F, 2.0F);
					shipgunproperties.ATTACK_FAST_TARGETS = (int)(f + 0.5F);
					if (shipgunproperties.ATTACK_FAST_TARGETS > 2)
						shipgunproperties.ATTACK_FAST_TARGETS = 2;
				}
				shipgunproperties.FAST_TARGETS_ANGLE_ERROR = 0.0F;
				if (sectfile.exist(string_13_, "FastTargetsAngleError"))
				{
					float f = getF(sectfile, string_13_, "FastTargetsAngleError", 0.0F, 45.0F);
					shipgunproperties.FAST_TARGETS_ANGLE_ERROR = f;
				}
				float f = getF(sectfile, string_13_, "HeadMinYaw", -360.0F, 360.0F);
				float f_15_ = getF(sectfile, string_13_, "HeadStdYaw", -360.0F, 360.0F);
				float f_16_ = getF(sectfile, string_13_, "HeadMaxYaw", -360.0F, 360.0F);
				if (f > f_16_)
				{
					System.out.println("Ship: Wrong yaw angles in gun #" + i_12_ + " of '" + string + "'");
					throw new RuntimeException("Can't register Ship object");
				}
				if (f_15_ < f || f_15_ > f_16_)
					System.out.println("Ship: Wrong std yaw angle in gun #" + i_12_ + " of '" + string + "'");
				shipgunproperties.HEAD_YAW_RANGE.set(f, f_16_);
				shipgunproperties.HEAD_STD_YAW = f_15_;
				shipgunproperties.GUN_MIN_PITCH = getF(sectfile, string_13_, "GunMinPitch", -15.0F, 85.0F);
				shipgunproperties.GUN_STD_PITCH = getF(sectfile, string_13_, "GunStdPitch", -14.0F, 89.9F);
				shipgunproperties.GUN_MAX_PITCH = getF(sectfile, string_13_, "GunMaxPitch", 0.0F, 89.9F);
				shipgunproperties.HEAD_MAX_YAW_SPEED = getF(sectfile, string_13_, "HeadMaxYawSpeed", 0.1F, 999.0F);
				shipgunproperties.GUN_MAX_PITCH_SPEED = getF(sectfile, string_13_, "GunMaxPitchSpeed", 0.1F, 999.0F);
				shipgunproperties.DELAY_AFTER_SHOOT = getF(sectfile, string_13_, "DelayAfterShoot", 0.0F, 999.0F);
				shipgunproperties.CHAINFIRE_TIME = getF(sectfile, string_13_, "ChainfireTime", 0.0F, 600.0F);
			}
			shipproperties.nGuns = i;
			shipproperties.SLIDER_DIST = getF(sectfile, string, "SliderDistance", 5.0F, 1000.0F);
			shipproperties.SPEED = KmHourToMSec(getF(sectfile, string, "Speed", 0.5F, 200.0F));
			shipproperties.DELAY_RESPAWN_MIN = 15.0F;
			shipproperties.DELAY_RESPAWN_MAX = 30.0F;
			Property.set(var_class, "iconName", "icons/" + getS(sectfile, string, "Icon") + ".mat");
			Property.set(var_class, "meshName", shipproperties.meshName);
			Property.set(var_class, "speed", shipproperties.SPEED);
			return shipproperties;
		}
		
		public Actor actorSpawn(ActorSpawnArg actorspawnarg)
		{
			ShipGeneric shipgeneric;
			try
			{
				ShipGeneric.constr_arg1 = proper;
				ShipGeneric.constr_arg2 = actorspawnarg;
				shipgeneric = (ShipGeneric)cls.newInstance();
				ShipGeneric.constr_arg1 = null;
				ShipGeneric.constr_arg2 = null;
			}
			catch (Exception exception)
			{
				ShipGeneric.constr_arg1 = null;
				ShipGeneric.constr_arg2 = null;
				System.out.println(exception.getMessage());
				exception.printStackTrace();
				System.out.println("SPAWN: Can't create Ship object [class:" + cls.getName() + "]");
				return null;
			}
			return shipgeneric;
		}
		
		public SPAWN(Class var_class)
		{
			try
			{
				String string = var_class.getName();
				int i = string.lastIndexOf('.');
				int i_17_ = string.lastIndexOf('$');
				if (i < i_17_)
					i = i_17_;
				String string_18_ = string.substring(i + 1);
				proper = LoadShipProperties(Statics.getTechnicsFile(), string_18_, var_class);
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
				System.out.println("Problem in spawn: " + var_class.getName());
			}
			cls = var_class;
			Spawn.add(cls, this);
		}
	}
	
	public static final double Rnd(double d, double d_19_)
	{
		return World.Rnd().nextDouble(d, d_19_);
	}
	
	public static final float Rnd(float f, float f_20_)
	{
		return World.Rnd().nextFloat(f, f_20_);
	}
	
	public static final float KmHourToMSec(float f)
	{
		return f / 3.6F;
	}
	
	private static final long SecsToTicks(float f)
	{
		long l = (long)(0.5 + (double)(f / Time.tickLenFs()));
		return l >= 1L ? l : 1L;
	}
	
	protected final boolean Head360(FiringDevice firingdevice)
	{
		return prop.guns[firingdevice.id].HEAD_YAW_RANGE.fullcircle();
	}
	
	public void msgCollisionRequest(Actor actor, boolean[] bools)
	{
		if (actor instanceof BridgeSegment)
		{
			if (dying != 0)
				bools[0] = false;
		}
		else if (path == null && actor instanceof ActorMesh && ((ActorMesh)actor).isStaticPos())
			bools[0] = false;
	}
	
	public void msgCollision(Actor actor, String string, String string_21_)
	{
		if (dying == 0 && !isNetMirror() && !(actor instanceof WeakBody))
		{
			if (actor instanceof ShipGeneric || actor instanceof BigshipGeneric || actor instanceof BridgeSegment)
				Die(null, -1L, true);
		}
	}
	
	public void msgShot(Shot shot)
	{
		shot.bodyMaterial = 2;
		if (dying == 0 && !(shot.power <= 0.0F) && (!isNetMirror() || !shot.isMirage()))
		{
			if (wakeupTmr < 0L)
				wakeupTmr = SecsToTicks(Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
			float f;
			float f_22_;
			if (shot.powerType == 1)
			{
				f = prop.stre.EXPLHIT_MAX_TNT;
				f_22_ = prop.stre.EXPLHIT_MAX_TNT;
			}
			else
			{
				f = prop.stre.SHOT_MIN_ENERGY;
				f_22_ = prop.stre.SHOT_MAX_ENERGY;
			}
			float f_23_ = shot.power * Rnd(1.0F, 1.1F);
			if (!(f_23_ < f))
			{
				float f_24_ = f_23_ / f_22_;
				life -= f_24_;
				if (!(life > 0.0F))
					Die(shot.initiator, -1L, true);
			}
		}
	}
	
	public void msgExplosion(Explosion explosion)
	{
		if (dying == 0 && (!isNetMirror() || !explosion.isMirage()))
		{
			if (wakeupTmr < 0L)
				wakeupTmr = SecsToTicks(Rnd(DELAY_WAKEUP, DELAY_WAKEUP * 1.2F));
			float f = explosion.power;
			if (explosion.powerType == 2 && explosion.chunkName != null)
				f *= 0.45F;
			float f1;
			if (explosion.chunkName != null)
			{
				float f2 = f;
				f2 *= Rnd(1.0F, 1.1F);
				if (f2 < prop.stre.EXPLHIT_MIN_TNT)
					return;
				f1 = f2 / prop.stre.EXPLHIT_MAX_TNT;
			}
			else
			{
				float f3 = explosion.receivedTNT_1meter(this);
				f3 *= Rnd(1.0F, 1.1F);
				if (f3 < prop.stre.EXPLNEAR_MIN_TNT)
					return;
				f1 = f3 / prop.stre.EXPLNEAR_MAX_TNT;
			}
			life -= f1;
			if (life <= 0.0F)
				Die(explosion.initiator, -1L, true);
			
			//------------------------WildWillie---------------------------------
			//TODO: This mod gives a player points for damaging a ship and also records the event
			else
			{
				if( ZutiSupportMethods.isDamagerCurrentUser(explosion.initiator) )
				{
					if (explosion.initiator.getArmy() != this.getArmy())
					{
						World.cur().scoreCounter.enemyDamaged(this, explosion.power);
					}
					else
					{
						World.cur().scoreCounter.friendDamaged(this, explosion.power);
					}
					
					//Added by |ZUTI|: sync this with your crew!
					//--------------------------------------------------
					ZutiSupportMethods_NetSend.syncCrewScore_ToServer(explosion.initiator.name(), this.name(), true, explosion.power);
					//--------------------------------------------------
				}
				// Send "Damaged By" event.
				EventLog.type(21, World.getTimeofDay(), EventLog.name(this), ZutiSupportMethods.getAircraftCompleteName( explosion.initiator ), 0, (float) this.pos.getAbsPoint().x, (float) this.pos.getAbsPoint().y, true);
			}
			//-------------------------------------------------------------------
		}
	}
	
	private float computeSeaDepth(Point3d point3d)
	{
		for (float f = 5.0F; f <= 355.0F; f += 10.0F)
		{
			for (float f_29_ = 0.0F; f_29_ < 360.0F; f_29_ += 30.0F)
			{
				float f_30_ = f * Geom.cosDeg(f_29_);
				float f_31_ = f * Geom.sinDeg(f_29_);
				f_30_ += (float)point3d.x;
				f_31_ += (float)point3d.y;
				if (!World.land().isWater((double)f_30_, (double)f_31_))
					return 50.0F * (f / 355.0F);
			}
		}
		return 1000.0F;
	}
	
	private void computeSinkingParams(long l)
	{
		RangeRandom rangerandom = new RangeRandom(l % 11073L);
		timeForRotation = 40000L + (long)(rangerandom.nextFloat() * 0.0F);
		drownBodyPitch = 5.0F - rangerandom.nextFloat() * 2.0F;
		if (rangerandom.nextFloat() < 0.5F)
			drownBodyPitch = -drownBodyPitch;
		drownBodyRoll = 3.0F - rangerandom.nextFloat() * 6.0F;
		sinkingDepthSpeed = 0.0010F + rangerandom.nextFloat() * 0.01F;
		seaDepth = computeSeaDepth(pos.getAbsPoint());
		seaDepth *= 1.0F + rangerandom.nextFloat() * 0.2F;
	}
	
	private void showExplode()
	{
		if (mesh() instanceof HierMesh)
			Explosions.Antiaircraft_Explode(pos.getAbsPoint());
	}
	
	private void Die(Actor actor, long l, boolean bool)
	{
		if (dying == 0)
		{
			if (l < 0L)
			{
				if (isNetMirror())
				{
					send_DeathRequest(actor);
					return;
				}
				//TODO: Edited by |ZUTI|
				// l = Time.current();
				l = NetServerParams.getServerTime();
			}
			life = 0.0F;
			dying = 1;
			World.onActorDied(this, actor);
			forgetAllAiming();
			SetEffectsIntens(-1.0F);
			if (path != null)
			{
				bodyDepth = 0.0F;
				bodyPitch = bodyRoll = 0.0F;
				setMovablePosition(l);
			}
			else
			{
				bodyDepth = 0.0F;
				bodyPitch = bodyRoll = 0.0F;
				setPosition();
			}
			pos.reset();
			computeSinkingParams(l);
			
			if (Mission.isDeathmatch())
			{
				timeOfDeath = l;
				if (!bool)
					timeOfDeath = 0L;
			}
			else
				timeOfDeath = l;
			
			if (bool)
				showExplode();
			if (bool)
				send_DeathCommand(actor);
		}
	}
	
	public void destroy()
	{
		if (!isDestroyed())
		{
			eraseGuns();
			super.destroy();
		}
	}
	
	private boolean isAnyEnemyNear()
	{
		NearestEnemies.set(WeaponsMask());
		Actor actor = NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), 2000.0, getArmy());
		return actor != null;
	}
	
	private final FiringDevice GetFiringDevice(Aim aim)
	{
		for (int i = 0; i < prop.nGuns; i++)
		{
			if (arms[i] != null && arms[i].aime == aim)
				return arms[i];
		}
		System.out.println("Internal error 1: Can't find ship gun.");
		return null;
	}
	
	private final ShipGunProperties GetGunProperties(Aim aim)
	{
		for (int i = 0; i < prop.nGuns; i++)
		{
			if (arms[i].aime == aim)
				return prop.guns[arms[i].id];
		}
		System.out.println("Internal error 2: Can't find ship gun.");
		return null;
	}
	
	private void setGunAngles(FiringDevice firingdevice, float f, float f_32_)
	{
		firingdevice.headYaw = f;
		firingdevice.gunPitch = f_32_;
		hierMesh().chunkSetAngles("Head" + firingdevice.id, firingdevice.headYaw, 0.0F, 0.0F);
		hierMesh().chunkSetAngles("Gun" + firingdevice.id, -firingdevice.gunPitch, 0.0F, 0.0F);
	}
	
	private void eraseGuns()
	{
		if (arms != null)
		{
			for (int i = 0; i < prop.nGuns; i++)
			{
				if (arms[i] != null)
				{
					if (arms[i].aime != null)
					{
						arms[i].aime.forgetAll();
						arms[i].aime = null;
					}
					if (arms[i].gun != null)
					{
						ObjState.destroy(arms[i].gun);
						arms[i].gun = null;
					}
					arms[i] = null;
				}
			}
			arms = null;
		}
	}
	
	private void forgetAllAiming()
	{
		if (arms != null)
		{
			for (int i = 0; i < prop.nGuns; i++)
			{
				if (arms[i] != null && arms[i].aime != null)
					arms[i].aime.forgetAiming();
			}
		}
	}
	
	private void CreateGuns()
	{
		arms = new FiringDevice[prop.nGuns];
		for (int i = 0; i < prop.nGuns; i++)
		{
			arms[i] = new FiringDevice();
			arms[i].id = i;
			arms[i].gun = null;
			try
			{
				arms[i].gun = (Gun)prop.guns[i].gunClass.newInstance();
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
				System.out.println("Ship: Can't create gun '" + prop.guns[i].gunClass.getName() + "'");
			}
			arms[i].gun.set(this, "ShellStart" + i);
			arms[i].gun.loadBullets(-1);
			Loc loc = new Loc();
			hierMesh().setCurChunk("Head" + i);
			hierMesh().getChunkLocObj(loc);
			prop.guns[i].fireOffset = new Point3d();
			loc.get(prop.guns[i].fireOffset);
			prop.guns[i].fireOrient = new Orient();
			loc.get(prop.guns[i].fireOrient);
			arms[i].aime = new Aim(this, isNetMirror(), SLOWFIRE_K * prop.guns[i].DELAY_AFTER_SHOOT);
		}
	}
	
	public Object getSwitchListener(Message message)
	{
		return this;
	}
	
	private void setDefaultLivePose()
	{
		int i = mesh().hookFind("Ground_Level");
		if (i != -1)
		{
			Matrix4d matrix4d = new Matrix4d();
			hierMesh().hookMatrix(i, matrix4d);
		}
		if (mesh() instanceof HierMesh)
			hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
		for (int i_33_ = 0; i_33_ < prop.nGuns; i_33_++)
			setGunAngles(arms[i_33_], prop.guns[i_33_].HEAD_STD_YAW, prop.guns[i_33_].GUN_STD_PITCH);
		bodyDepth = 0.0F;
		align();
	}
	
	protected ShipGeneric()
	{
		this(constr_arg1, constr_arg2);
	}
	
	private ShipGeneric(ShipProperties shipproperties, ActorSpawnArg actorspawnarg)
	{
		super(shipproperties.meshName);
		wake = new Eff3DActor[]{null, null, null};
		prop = null;
		arms = null;
		cachedSeg = 0;
		life = 1.0F;
		dying = 0;
		respawnDelay = 0L;
		wakeupTmr = 0L;
		DELAY_WAKEUP = 0.0F;
		SKILL_IDX = 2;
		SLOWFIRE_K = 1.0F;
		pipe = null;
		nose = null;
		tail = null;
		outCommand = new NetMsgFiltered();
		prop = shipproperties;
		actorspawnarg.setStationary(this);
		path = null;
		collide(true);
		drawing(true);
		bodyDepth = 0.0F;
		bodyPitch = bodyRoll = 0.0F;
		bodyYaw = actorspawnarg.orient.getYaw();
		setPosition();
		pos.reset();
		createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
		SKILL_IDX = Chief.new_SKILL_IDX;
		SLOWFIRE_K = Chief.new_SLOWFIRE_K;
		DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
		wakeupTmr = 0L;
		CreateGuns();
		setDefaultLivePose();
		if (!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
			wakeupTmr = -SecsToTicks(Rnd(2.0F, 7.0F));
		if (!interpEnd("move"))
			interpPut(new Move(), "move", Time.current(), null);
	}
	
	public void setMesh(String string)
	{
		super.setMesh(string);
		if (!Config.cur.b3dgunners)
			mesh().materialReplaceToNull("Pilot1");
	}
	
	public ShipGeneric(String string, int i, SectFile sectfile, String string_34_, SectFile sectfile_35_, String string_36_)
	{
		wake = new Eff3DActor[]{null, null, null};
		prop = null;
		arms = null;
		cachedSeg = 0;
		life = 1.0F;
		dying = 0;
		respawnDelay = 0L;
		wakeupTmr = 0L;
		DELAY_WAKEUP = 0.0F;
		SKILL_IDX = 2;
		SLOWFIRE_K = 1.0F;
		pipe = null;
		nose = null;
		tail = null;
		outCommand = new NetMsgFiltered();
		try
		{
			int i_37_ = sectfile.sectionIndex(string_34_);
			String string_38_ = sectfile.var(i_37_, 0);
			Object object = Spawn.get(string_38_);
			if (object == null)
				throw new ActorException("Ship: Unknown class of ship (" + string_38_ + ")");
			prop = ((SPAWN)object).proper;
			try
			{
				setMesh(prop.meshName);
			}
			catch (RuntimeException runtimeexception)
			{
				super.destroy();
				throw runtimeexception;
			}
			if (prop.soundName != null)
				newSound(prop.soundName, true);
			setName(string);
			setArmy(i);
			LoadPath(sectfile_35_, string_36_);
			cachedSeg = 0;
			bodyDepth = 0.0F;
			bodyPitch = bodyRoll = 0.0F;
			//TODO: By |ZUTI|
			// setMovablePosition(Time.current());
			setMovablePosition(NetServerParams.getServerTime());
			// setPosition();
			pos.reset();
			collide(true);
			drawing(true);
			pipe = null;
			if (mesh().hookFind("Vapor") >= 0)
			{
				HookNamed hooknamed = new HookNamed(this, "Vapor");
				pipe = Eff3DActor.New(this, hooknamed, null, 1.0F, "Effects/Smokes/SmokePipeShip.eff", -1.0F);
			}
			wake[2] = wake[1] = wake[0] = null;
			tail = null;
			nose = null;
			boolean bool = prop.SLIDER_DIST / 2.5F < 90.0F;
			if (mesh().hookFind("_Prop") >= 0)
			{
				HookNamedZ0 hooknamedz0 = new HookNamedZ0(this, "_Prop");
				tail = (Eff3DActor.New(this, hooknamedz0, null, 1.0F, (bool ? "3DO/Effects/Tracers/ShipTrail/PropWakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/PropWake.eff"), -1.0F));
			}
			if (mesh().hookFind("_Centre") >= 0)
			{
				Loc loc = new Loc();
				Loc loc_39_ = new Loc();
				HookNamed hooknamed = new HookNamed(this, "_Left");
				hooknamed.computePos(this, new Loc(), loc);
				HookNamed hooknamed_40_ = new HookNamed(this, "_Right");
				hooknamed_40_.computePos(this, new Loc(), loc_39_);
				float f = (float)loc.getPoint().distance(loc_39_.getPoint());
				HookNamedZ0 hooknamedz0 = new HookNamedZ0(this, "_Centre");
				if (mesh().hookFind("_Prop") >= 0)
				{
					HookNamedZ0 hooknamedz0_41_ = new HookNamedZ0(this, "_Prop");
					Loc loc_42_ = new Loc();
					hooknamedz0.computePos(this, new Loc(), loc_42_);
					Loc loc_43_ = new Loc();
					hooknamedz0_41_.computePos(this, new Loc(), loc_43_);
					float f_44_ = (float)loc_42_.getPoint().distance(loc_43_.getPoint());
					wake[0] = (Eff3DActor.New(this, hooknamedz0_41_, new Loc((double)-f_44_ * 0.33, 0.0, 0.0, 0.0F, 30.0F, 0.0F), f, (bool ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff"), -1.0F));
					wake[1] = (Eff3DActor.New(this, hooknamedz0, new Loc((double)f_44_ * 0.15, 0.0, 0.0, 0.0F, 30.0F, 0.0F), f, (bool ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff"), -1.0F));
					wake[2] = (Eff3DActor.New(this, hooknamedz0, new Loc((double)-f_44_ * 0.15, 0.0, 0.0, 0.0F, 30.0F, 0.0F), f, (bool ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff"), -1.0F));
				}
				else
					wake[0] = (Eff3DActor.New(this, hooknamedz0, new Loc((double)-f * 0.3, 0.0, 0.0, 0.0F, 30.0F, 0.0F), f,
							((double)prop.SLIDER_DIST / 2.5 >= 50.0 ? "3DO/Effects/Tracers/ShipTrail/Wake.eff" : "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff"), -1.0F));
			}
			if (mesh().hookFind("_Nose") >= 0)
			{
				HookNamedZ0 hooknamedz0 = new HookNamedZ0(this, "_Nose");
				nose = (Eff3DActor.New(this, hooknamedz0, new Loc(0.0, 0.0, 0.0, 0.0F, 30.0F, 0.0F), 1.0F, "3DO/Effects/Tracers/ShipTrail/SideWave.eff", -1.0F));
			}
			SetEffectsIntens(0.0F);
			int i_45_ = Mission.cur().getUnitNetIdRemote(this);
			NetChannel netchannel = Mission.cur().getNetMasterChannel();
			if (netchannel == null)
				net = new Master(this);
			else if (i_45_ != 0)
				net = new Mirror(this, netchannel, i_45_);
			SKILL_IDX = Chief.new_SKILL_IDX;
			SLOWFIRE_K = Chief.new_SLOWFIRE_K;
			DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
			wakeupTmr = 0L;
			CreateGuns();
			setDefaultLivePose();
			if (!isNetMirror() && prop.nGuns > 0 && DELAY_WAKEUP > 0.0F)
				wakeupTmr = -SecsToTicks(Rnd(2.0F, 7.0F));
			if (!interpEnd("move"))
				interpPut(new Move(), "move", Time.current(), null);
		}
		catch (Exception exception)
		{
			System.out.println("Ship creation failure:");
			System.out.println(exception.getMessage());
			exception.printStackTrace();
			throw new ActorException();
		}
		
		//TODO: Added by |ZUTI|
		//----------------------
		zutiShipName = string_34_.substring(string_34_.indexOf(".")+1, string_34_.length());
		zutiReportFinalDestination = ZutiSupportMethods.isMovingRRRObject(zutiShipName, this);
		Segment lastSection = (Segment)path.get(path.size()-1);
		zutiLastPointX = (int)lastSection.posOut.x;
		zutiLastPointY = (int)lastSection.posOut.y;
		//System.out.println("Ship name: " + zutiShipName);
		//----------------------		
	}
	
	private void SetEffectsIntens(float f)
	{
		if (dying != 0)
			f = -1.0F;
		if (pipe != null)
		{
			if (f >= 0.0F)
				pipe._setIntesity(f);
			else
			{
				pipe._finish();
				pipe = null;
			}
		}
		
		//TODO: Added by |ZUTI|: if wake effects are disabled, set f to -1
		//-----------------------------------------
		if( !zutiDrawWakeEffects )
		{
			f = 0.0F;
			//System.out.println(" NOT DRAWING!");
			//System.out.println("=====================================");
		}
		//-----------------------------------------
		
		for (int i = 0; i < 3; i++)
		{
			if (wake[i] != null)
			{
				if (f >= 0.0F)
					wake[i]._setIntesity(f);
				else
				{
					wake[i]._finish();
					wake[i] = null;
				}
			}
		}
		if (nose != null)
		{
			if (f >= 0.0F)
				nose._setIntesity(f);
			else
			{
				nose._finish();
				nose = null;
			}
		}
		if (tail != null)
		{
			if (f >= 0.0F)
				tail._setIntesity(f);
			else
			{
				//TODO: Added by |ZUTI|
				//-----------------------------------------
				if( zutiReportResources && zutiReportFinalDestination && cachedSeg == path.size()-1 )
				{
					zutiHasShipReachedFinalDestination();
				}
				//-----------------------------------------
				
				tail._finish();
				tail = null;
			}
		}
	}
	
	private void LoadPath(SectFile sectfile, String string)
	{
		int i = sectfile.sectionIndex(string);
		if (i < 0)
			throw new ActorException("Ship path: Section [" + string + "] not found");
		int i_46_ = sectfile.vars(i);
		if (i_46_ < 1)
			throw new ActorException("Ship path must contain at least 2 nodes");
		path = new ArrayList();
		for (int i_47_ = 0; i_47_ < i_46_; i_47_++)
		{
			StringTokenizer stringtokenizer = new StringTokenizer(sectfile.line(i, i_47_));
			float f = Float.valueOf(stringtokenizer.nextToken()).floatValue();
			float f_48_ = Float.valueOf(stringtokenizer.nextToken()).floatValue();
			Float.valueOf(stringtokenizer.nextToken()).floatValue();
			double d = 0.0;
			float f_50_ = 0.0F;
			if (stringtokenizer.hasMoreTokens())
			{
				d = Double.valueOf(stringtokenizer.nextToken()).doubleValue();
				if (stringtokenizer.hasMoreTokens())
				{
					Double.valueOf(stringtokenizer.nextToken()).doubleValue();
					if (stringtokenizer.hasMoreTokens())
						f_50_ = Float.valueOf(stringtokenizer.nextToken()).floatValue();
				}
			}
			if (i_47_ >= i_46_ - 1)
				d = 1.0;
			Segment segment = new Segment();
			segment.posIn = new Point3d((double)f, (double)f_48_, 0.0);
			if (Math.abs(d) < 0.1)
				segment.timeIn = 0L;
			else
				segment.timeIn = (long)(d * 60.0 * 1000.0 + (d <= 0.0 ? -0.5 : 0.5));
			if (f_50_ <= 0.0F && (i_47_ == 0 || i_47_ == i_46_ - 1 || segment.timeIn == 0L))
				f_50_ = prop.SPEED;
			segment.speedIn = f_50_;
			path.add(segment);
		}
		for (int i_51_ = 0; i_51_ < path.size() - 1; i_51_++)
		{
			Segment segment = (Segment)path.get(i_51_);
			Segment segment_52_ = (Segment)path.get(i_51_ + 1);
			if (segment.timeIn > 0L && segment_52_.timeIn > 0L)
			{
				Segment segment_53_ = new Segment();
				segment_53_.posIn = new Point3d(segment.posIn);
				segment_53_.posIn.add(segment_52_.posIn);
				segment_53_.posIn.scale(0.5);
				segment_53_.timeIn = 0L;
				segment_53_.speedIn = (segment.speedIn + segment_52_.speedIn) * 0.5F;
				path.add(i_51_ + 1, segment_53_);
			}
		}
		int i_54_ = 0;
		float f = ((Segment)path.get(i_54_)).length;
		int i_55_;
		for (/**/; i_54_ < path.size() - 1; i_54_ = i_55_)
		{
			i_55_ = i_54_ + 1;
			for (;;)
			{
				Segment segment = (Segment)path.get(i_55_);
				if ((double)segment.speedIn > 0.0)
					break;
				f += segment.length;
				i_55_++;
			}
			if (i_55_ - i_54_ > 1)
			{
				float f_56_ = ((Segment)path.get(i_54_)).length;
				float f_57_ = ((Segment)path.get(i_54_)).speedIn;
				float f_58_ = ((Segment)path.get(i_55_)).speedIn;
				for (int i_59_ = i_54_ + 1; i_59_ < i_55_; i_59_++)
				{
					Segment segment = (Segment)path.get(i_59_);
					float f_60_ = f_56_ / f;
					segment.speedIn = f_57_ * (1.0F - f_60_) + f_58_ * f_60_;
					f += segment.length;
				}
			}
		}
		long l = 0L;
		for (int i_61_ = 0; i_61_ < path.size() - 1; i_61_++)
		{
			Segment segment = (Segment)path.get(i_61_);
			Segment segment_62_ = (Segment)path.get(i_61_ + 1);
			if (i_61_ == 0)
				l = segment.timeIn;
			segment.posOut = new Point3d(segment_62_.posIn);
			segment_62_.posIn = segment.posOut;
			segment.length = (float)segment.posIn.distance(segment_62_.posIn);
			float f_63_ = segment.speedIn;
			float f_64_ = segment_62_.speedIn;
			float f_65_ = (f_63_ + f_64_) * 0.5F;
			if (segment.timeIn > 0L)
			{
				if (segment.timeIn > l)
					segment.timeIn -= l;
				else
					segment.timeIn = 0L;
			}
			if (segment.timeIn == 0L && segment_62_.timeIn > 0L)
			{
				int i_66_ = (int)(2.0F * segment.length / f_63_ * 1000.0F + 0.5F);
				i_66_ += l;
				if (segment_62_.timeIn > (long)i_66_)
					segment_62_.timeIn -= (long)i_66_;
				else
					segment_62_.timeIn = 0L;
			}
			if (segment.timeIn > 0L)
			{
				segment.speedIn = 0.0F;
				segment.speedOut = f_64_;
				float f_67_ = 2.0F * segment.length / f_64_ * 1000.0F + 0.5F;
				segment.timeIn = l + segment.timeIn;
				segment.timeOut = segment.timeIn + (long)(int)f_67_;
				l = segment.timeOut;
			}
			else if (segment_62_.timeIn > 0L)
			{
				segment.speedIn = f_63_;
				segment.speedOut = 0.0F;
				float f_68_ = 2.0F * segment.length / f_63_ * 1000.0F + 0.5F;
				segment.timeIn = l + segment.timeIn;
				segment.timeOut = segment.timeIn + (long)(int)f_68_;
				l = segment.timeOut + segment_62_.timeIn;
			}
			else
			{
				segment.speedIn = f_63_;
				segment.speedOut = f_64_;
				float f_69_ = segment.length / f_65_ * 1000.0F + 0.5F;
				segment.timeIn = l;
				segment.timeOut = segment.timeIn + (long)(int)f_69_;
				l = segment.timeOut;
			}
		}
		path.remove(path.size() - 1);
	}
	
	public void align()
	{
		pos.getAbs(p);
		p.z = Engine.land().HQ(p.x, p.y) - (double)bodyDepth;
		pos.setAbs(p);
	}
	
	private void setMovablePosition(long l)
	{
		//TODO: Added by |ZUTI|: if new reported time is smaller than old one, set effects intensity to 0
		//because if they are set to 1, long long white lines are drawn when ship is positioned backwards.
		//---------------------------------------------------------
		if( l < zutiLastKnownServerTime )
		{
			zutiDrawWakeEffects = false;
			//System.out.println("  FALSE!");
		}
		else
		{
			zutiDrawWakeEffects = true;
			//System.out.println("  TRUE!");
		}
		
		zutiLastKnownServerTime = l;
		//System.out.println("  " + zutiLastKnownServerTime);
		//System.out.println("=====================================");
		//---------------------------------------------------------
		
		if (cachedSeg < 0)
			cachedSeg = 0;
		else if (cachedSeg >= path.size())
			cachedSeg = path.size() - 1;
		Segment segment = (Segment)path.get(cachedSeg);
		if (segment.timeIn <= l && l <= segment.timeOut)
		{
			SetEffectsIntens(1.0F);
			setMovablePosition((float)(l - segment.timeIn) / (float)(segment.timeOut - segment.timeIn));
		}
		else if (l > segment.timeOut)
		{
			while (cachedSeg + 1 < path.size())
			{
				Segment segment_70_ = (Segment)path.get(++cachedSeg);
				if (l <= segment_70_.timeIn)
				{
					SetEffectsIntens(0.0F);
					setMovablePosition(0.0F);
					return;
				}
				if (l <= segment_70_.timeOut)
				{
					SetEffectsIntens(1.0F);
					setMovablePosition((float)(l - segment_70_.timeIn) / (float)(segment_70_.timeOut - segment_70_.timeIn));
					return;
				}
			}
			SetEffectsIntens(-1.0F);
			setMovablePosition(1.0F);
		}
		else
		{
			while (cachedSeg > 0)
			{
				Segment segment_71_ = (Segment)path.get(--cachedSeg);
				if (l >= segment_71_.timeOut)
				{
					SetEffectsIntens(0.0F);
					setMovablePosition(1.0F);
					return;
				}
				if (l >= segment_71_.timeIn)
				{
					SetEffectsIntens(1.0F);
					setMovablePosition((float)(l - segment_71_.timeIn) / (float)(segment_71_.timeOut - segment_71_.timeIn));
					return;
				}
			}
			SetEffectsIntens(0.0F);
			setMovablePosition(0.0F);
		}
	}
	
	private void setMovablePosition(float f)
	{
		Segment segment = (Segment)path.get(cachedSeg);
		float f_72_ = (float)(segment.timeOut - segment.timeIn) * 0.0010F;
		float f_73_ = segment.speedIn;
		float f_74_ = segment.speedOut;
		float f_75_ = (f_74_ - f_73_) / f_72_;
		f *= f_72_;
		float f_76_ = f_73_ * f + f_75_ * f * f * 0.5F;
		int i = cachedSeg;
		float f_77_ = prop.SLIDER_DIST - (segment.length - f_76_);
		if (f_77_ <= 0.0F)
			p1.interpolate(segment.posIn, segment.posOut, (f_76_ + prop.SLIDER_DIST) / segment.length);
		else
		{
			for (;;)
			{
				if (i + 1 >= path.size())
				{
					p1.interpolate(segment.posIn, segment.posOut, 1.0F + f_77_ / segment.length);
					break;
				}
				segment = (Segment)path.get(++i);
				if (f_77_ <= segment.length)
				{
					p1.interpolate(segment.posIn, segment.posOut, f_77_ / segment.length);
					break;
				}
				f_77_ -= segment.length;
			}
		}
		i = cachedSeg;
		segment = (Segment)path.get(i);
		f_77_ = prop.SLIDER_DIST - f_76_;
		if (f_77_ <= 0.0F)
			p2.interpolate(segment.posIn, segment.posOut, (f_76_ - prop.SLIDER_DIST) / segment.length);
		else
		{
			for (;;)
			{
				if (i <= 0)
				{
					p2.interpolate(segment.posIn, segment.posOut, 0.0F - f_77_ / segment.length);
					break;
				}
				segment = (Segment)path.get(--i);
				if (f_77_ <= segment.length)
				{
					p2.interpolate(segment.posIn, segment.posOut, 1.0F - f_77_ / segment.length);
					break;
				}
				f_77_ -= segment.length;
			}
		}
		p.interpolate(p1, p2, 0.5F);
		tmpv.sub(p1, p2);
		if (tmpv.lengthSquared() < 0.0010000000474974513)
		{
			Segment segment_78_ = (Segment)path.get(cachedSeg);
			tmpv.sub(segment_78_.posOut, segment_78_.posIn);
		}
		float f_79_ = (float)(Math.atan2(tmpv.y, tmpv.x) * 57.29577951308232);
		setPosition(p, f_79_);
	}
	
	private void setPosition(Point3d point3d, float f)
	{
		bodyYaw = f;
		o.setYPR(bodyYaw, bodyPitch, bodyRoll);
		point3d.z = (double)-bodyDepth;
		pos.setAbs(point3d, o);
	}
	
	private void setPosition()
	{
		o.setYPR(bodyYaw, bodyPitch, bodyRoll);
		pos.setAbs(o);
		align();
	}
	
	public int WeaponsMask()
	{
		return prop.WEAPONS_MASK;
	}
	
	public int HitbyMask()
	{
		return prop.HITBY_MASK;
	}
	
	public int chooseBulletType(BulletProperties[] bulletpropertieses)
	{
		if (dying != 0)
			return -1;
		if (bulletpropertieses.length == 1)
			return 0;
		if (bulletpropertieses.length <= 0)
			return -1;
		if (bulletpropertieses[0].power <= 0.0F)
			return 0;
		if (bulletpropertieses[1].power <= 0.0F)
			return 1;
		if (bulletpropertieses[0].cumulativePower > 0.0F)
			return 0;
		if (bulletpropertieses[1].cumulativePower > 0.0F)
			return 1;
		if (bulletpropertieses[0].powerType == 0)
			return 0;
		if (bulletpropertieses[1].powerType == 0)
			return 1;
		return bulletpropertieses[0].powerType != 1 ? 0 : 1;
	}
	
	public int chooseShotpoint(BulletProperties bulletproperties)
	{
		return dying == 0 ? 0 : -1;
	}
	
	public boolean getShotpointOffset(int i, Point3d point3d)
	{
		if (dying != 0)
			return false;
		if (i != 0)
			return false;
		if (point3d != null)
			point3d.set(0.0, 0.0, 0.0);
		return true;
	}
	
	public float AttackMaxDistance()
	{
		return prop.ATTACK_MAX_DISTANCE;
	}
	
	private void send_DeathCommand(Actor actor)
	{
		if (isNetMaster())
		{
			if (Mission.isDeathmatch())
			{
				float f = Mission.respawnTime("Ship");
				respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
			}
			else
				respawnDelay = 0L;
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			try
			{
				netmsgguaranted.writeByte(68);
				netmsgguaranted.writeLong(timeOfDeath);
				netmsgguaranted.writeNetObj(actor != null ? actor.net : null);
				net.post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	private void send_RespawnCommand()
	{
		if (isNetMaster() && Mission.isDeathmatch())
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			try
			{
				netmsgguaranted.writeByte(82);
				net.post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	private void send_FireCommand(int i, Actor actor, int i_80_, float f)
	{
		if (isNetMaster() && net.isMirrored() && (Actor.isValid(actor) && actor.isNet()))
		{
			i_80_ &= 0xff;
			if (f < 0.0F)
			{
				try
				{
					outCommand.unLockAndClear();
					outCommand.writeByte(84);
					outCommand.writeByte(i);
					outCommand.writeNetObj(actor.net);
					outCommand.writeByte(i_80_);
					outCommand.setIncludeTime(false);
					net.post(Time.current(), outCommand);
				}
				catch (Exception exception)
				{
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
			}
			else
			{
				try
				{
					outCommand.unLockAndClear();
					outCommand.writeByte(70);
					outCommand.writeByte(i);
					outCommand.writeFloat(f);
					outCommand.writeNetObj(actor.net);
					outCommand.writeByte(i_80_);
					outCommand.setIncludeTime(true);
					net.post(Time.current(), outCommand);
				}
				catch (Exception exception)
				{
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
			}
		}
	}
	
	private void send_DeathRequest(Actor actor)
	{
		if (isNetMirror() && !(net.masterChannel() instanceof NetChannelInStream))
		{
			try
			{
				NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
				netmsgfiltered.writeByte(68);
				netmsgfiltered.writeNetObj(actor != null ? actor.net : null);
				netmsgfiltered.setIncludeTime(false);
				net.postTo(Time.current(), net.masterChannel(), netmsgfiltered);
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	public void createNetObject(NetChannel netchannel, int i)
	{
		if (netchannel == null)
			net = new Master(this);
		else
			net = new Mirror(this, netchannel, i);
	}
	
	public void netFirstUpdate(NetChannel netchannel) throws IOException
	{
		NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
		netmsgguaranted.writeByte(73);
		if (dying == 0)
			netmsgguaranted.writeLong(-1L);
		else
			netmsgguaranted.writeLong(timeOfDeath);
		net.postTo(netchannel, netmsgguaranted);
	}
	
	public float getReloadingTime(Aim aim)
	{
		return SLOWFIRE_K * GetGunProperties(aim).DELAY_AFTER_SHOOT;
	}
	
	public float chainFireTime(Aim aim)
	{
		float f = GetGunProperties(aim).CHAINFIRE_TIME;
		return f > 0.0F ? f * Rnd(0.75F, 1.25F) : 0.0F;
	}
	
	public float probabKeepSameEnemy(Actor actor)
	{
		return 0.75F;
	}
	
	public float minTimeRelaxAfterFight()
	{
		return 0.1F;
	}
	
	public void gunStartParking(Aim aim)
	{
		FiringDevice firingdevice = GetFiringDevice(aim);
		ShipGunProperties shipgunproperties = prop.guns[firingdevice.id];
		aim.setRotationForParking(firingdevice.headYaw, firingdevice.gunPitch, shipgunproperties.HEAD_STD_YAW, shipgunproperties.GUN_STD_PITCH, shipgunproperties.HEAD_YAW_RANGE, shipgunproperties.HEAD_MAX_YAW_SPEED, shipgunproperties.GUN_MAX_PITCH_SPEED);
	}
	
	public void gunInMove(boolean bool, Aim aim)
	{
		FiringDevice firingdevice = GetFiringDevice(aim);
		float f = aim.t();
		float f_81_ = aim.anglesYaw.getDeg(f);
		float f_82_ = aim.anglesPitch.getDeg(f);
		setGunAngles(firingdevice, f_81_, f_82_);
		pos.inValidate(false);
	}
	
	public Actor findEnemy(Aim aim)
	{
		if (isNetMirror())
			return null;
		ShipGunProperties shipgunproperties = GetGunProperties(aim);
		switch (shipgunproperties.ATTACK_FAST_TARGETS)
		{
			case 0 :
				NearestEnemies.set(shipgunproperties.WEAPONS_MASK, -9999.9F, KmHourToMSec(100.0F));
				break;
			case 1 :
				NearestEnemies.set(shipgunproperties.WEAPONS_MASK);
				break;
			default :
				NearestEnemies.set(shipgunproperties.WEAPONS_MASK, KmHourToMSec(100.0F), 9999.9F);
		}
		Actor actor = NearestEnemies.getAFoundEnemy(pos.getAbsPoint(), (double)(shipgunproperties.ATTACK_MAX_RADIUS), getArmy());
		if (actor == null)
			return null;
		if (!(actor instanceof Prey))
		{
			System.out.println("ship: nearest enemies: non-Prey");
			return null;
		}
		FiringDevice firingdevice = GetFiringDevice(aim);
		BulletProperties bulletproperties = null;
		if (firingdevice.gun.prop != null)
		{
			int i = ((Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
			if (i < 0)
				return null;
			bulletproperties = firingdevice.gun.prop.bullet[i];
		}
		int i = ((Prey)actor).chooseShotpoint(bulletproperties);
		if (i < 0)
			return null;
		aim.shotpoint_idx = i;
		return actor;
	}
	
	public boolean enterToFireMode(int i, Actor actor, float f, Aim aim)
	{
		if (!isNetMirror())
		{
			FiringDevice firingdevice = GetFiringDevice(aim);
			send_FireCommand(firingdevice.id, actor, aim.shotpoint_idx, i != 0 ? f : -1.0F);
		}
		return true;
	}
	
	private void Track_Mirror(int i, Actor actor, int i_83_)
	{
		if (actor != null && (arms != null && arms[i].aime != null))
			arms[i].aime.passive_StartFiring(0, actor, i_83_, 0.0F);
	}
	
	private void Fire_Mirror(int i, Actor actor, int i_84_, float f)
	{
		if (actor != null && (arms != null && arms[i].aime != null))
		{
			if (f <= 0.2F)
				f = 0.2F;
			if (f >= 15.0F)
				f = 15.0F;
			arms[i].aime.passive_StartFiring(1, actor, i_84_, f);
		}
	}
	
	public int targetGun(Aim aim, Actor actor, float f, boolean bool)
	{
		if (!Actor.isValid(actor) || !actor.isAlive() || actor.getArmy() == 0)
			return 0;
		FiringDevice firingdevice = GetFiringDevice(aim);
		if (firingdevice.gun instanceof CannonMidrangeGeneric)
		{
			int i = ((Prey)actor).chooseBulletType(firingdevice.gun.prop.bullet);
			if (i < 0)
				return 0;
			((CannonMidrangeGeneric)firingdevice.gun).setBulletType(i);
		}
		boolean bool_85_ = ((Prey)actor).getShotpointOffset(aim.shotpoint_idx, p1);
		if (!bool_85_)
			return 0;
		ShipGunProperties shipgunproperties = prop.guns[firingdevice.id];
		float f_86_ = f * Rnd(0.8F, 1.2F);
		if (!Aimer.Aim((BulletAimer)firingdevice.gun, actor, this, f_86_, p1, shipgunproperties.fireOffset))
			return 0;
		Point3d point3d = new Point3d();
		Aimer.GetPredictedTargetPosition(point3d);
		Point3d point3d_87_ = Aimer.GetHunterFirePoint();
		float f_88_ = 0.05F;
		double d = point3d.distance(point3d_87_);
		double d_89_ = point3d.z;
		point3d.sub(point3d_87_);
		point3d.scale(Rnd(0.995, 1.005));
		point3d.add(point3d_87_);
		if (f_86_ > 0.0010F)
		{
			Point3d point3d_90_ = new Point3d();
			actor.pos.getAbs(point3d_90_);
			tmpv.sub(point3d, point3d_90_);
			double d_91_ = tmpv.length();
			if (d_91_ > 0.0010)
			{
				float f_92_ = (float)d_91_ / f_86_;
				if (f_92_ > 200.0F)
					f_92_ = 200.0F;
				float f_93_ = f_92_ * 0.01F;
				point3d_90_.sub(point3d_87_);
				double d_94_ = (point3d_90_.x * point3d_90_.x + point3d_90_.y * point3d_90_.y + point3d_90_.z * point3d_90_.z);
				if (d_94_ > 0.01)
				{
					float f_95_ = (float)tmpv.dot(point3d_90_);
					f_95_ /= (float)(d_91_ * Math.sqrt(d_94_));
					f_95_ = (float)Math.sqrt((double)(1.0F - f_95_ * f_95_));
					f_93_ *= 0.4F + 0.6F * f_95_;
				}
				f_93_ *= 1.3F;
				f_93_ *= Aim.AngleErrorKoefForSkill[SKILL_IDX];
				int i = Mission.curCloudsType();
				if (i > 2)
				{
					float f_96_ = i <= 4 ? 800.0F : 400.0F;
					float f_97_ = (float)(d / (double)f_96_);
					if (f_97_ > 1.0F)
					{
						if (f_97_ > 10.0F)
							return 0;
						f_97_ = (f_97_ - 1.0F) / 9.0F;
						f_93_ *= f_97_ + 1.0F;
					}
				}
				if (i >= 3 && d_89_ > (double)Mission.curCloudsHeight())
					f_93_ *= 1.25F;
				f_88_ += f_93_;
			}
		}
		if (World.Sun().ToSun.z < -0.15F)
		{
			float f_98_ = (-World.Sun().ToSun.z - 0.15F) / 0.13F;
			if (f_98_ >= 1.0F)
				f_98_ = 1.0F;
			if (actor instanceof Aircraft && Time.current() - ((Aircraft)actor).tmSearchlighted < 1000L)
				f_98_ = 0.0F;
			f_88_ += 10.0F * f_98_;
		}
		float f_99_ = (float)actor.getSpeed(null) - 10.0F;
		if (f_99_ > 0.0F)
		{
			float f_100_ = 83.33334F;
			f_99_ = f_99_ < f_100_ ? f_99_ / f_100_ : 1.0F;
			f_88_ += f_99_ * shipgunproperties.FAST_TARGETS_ANGLE_ERROR;
		}
		Vector3d vector3d = new Vector3d();
		if (!((BulletAimer)firingdevice.gun).FireDirection(point3d_87_, point3d, vector3d))
			return 0;
		float f_101_;
		if (bool)
		{
			f_101_ = 99999.0F;
			d_89_ = 99999.0;
		}
		else
		{
			f_101_ = shipgunproperties.HEAD_MAX_YAW_SPEED;
			d_89_ = (double)shipgunproperties.GUN_MAX_PITCH_SPEED;
		}
		o.add(shipgunproperties.fireOrient, pos.getAbs().getOrient());
		int i = aim.setRotationForTargeting(this, o, point3d_87_, firingdevice.headYaw, firingdevice.gunPitch, vector3d, f_88_, f_86_, shipgunproperties.HEAD_YAW_RANGE, shipgunproperties.GUN_MIN_PITCH, shipgunproperties.GUN_MAX_PITCH, f_101_,
				(float)d_89_, 0.0F);
		return i;
	}
	
	public void singleShot(Aim aim)
	{
		FiringDevice firingdevice = GetFiringDevice(aim);
		if (!prop.guns[firingdevice.id].TRACKING_ONLY)
			firingdevice.gun.shots(1);
	}
	
	public void startFire(Aim aim)
	{
		FiringDevice firingdevice = GetFiringDevice(aim);
		if (!prop.guns[firingdevice.id].TRACKING_ONLY)
			firingdevice.gun.shots(-1);
	}
	
	public void continueFire(Aim aim)
	{
	/* empty */
	}
	
	public void stopFire(Aim aim)
	{
		FiringDevice firingdevice = GetFiringDevice(aim);
		if (!prop.guns[firingdevice.id].TRACKING_ONLY)
			firingdevice.gun.shots(0);
	}
	
	public boolean isVisibilityLong()
	{
		return true;
	}
	
	/* synthetic */static long access$1710(ShipGeneric shipgeneric)
	{
		return shipgeneric.wakeupTmr--;
	}
	
	/* synthetic */static long access$1704(ShipGeneric shipgeneric)
	{
		return ++shipgeneric.wakeupTmr;
	}
	
	/* synthetic */static long access$2310(ShipGeneric shipgeneric)
	{
		return shipgeneric.respawnDelay--;
	}
	
	// TODO: |ZUTI| methods
	// ----------------------------------------------------
	private boolean zutiReportResources = true;
	private String zutiShipName = null;
	private boolean zutiReportFinalDestination = false;
	private int zutiLastPointX = -1;
	private int zutiLastPointY = -1;
	private boolean zutiDrawWakeEffects = true;
	private long zutiLastKnownServerTime = 0;
	
	/**
	 * Method returns value of dying variable.
	 */
	public int zutiGetDying()
	{
		return dying;
	}
	
	/**
	 * This method checks object path variable. If it is null or it's size is 0
	 * then true is returned otherwise object is moving one.
	 * 
	 * @return
	 */
	public boolean zutiIsStatic()
	{
		if (path != null && path.size() > 0)
			return false;
		
		return true;
	}
	
	private void zutiHasShipReachedFinalDestination()
	{
		Point3d p = pos.getAbsPoint();
		//System.out.println("(" + (int)p.x + "," + (int)p.y + ") vs (" + (int)segment.posOut.x + "," + (int)segment.posOut.y + ")" );
		if( (int)p.x == zutiLastPointX && (int)p.y == zutiLastPointY )
		{
			zutiReportResources = false;
			//System.out.println(zutiShipName + " has reached it's final destination.");
			
			ZutiSupportMethods_ResourcesManagement.addResourcesFromMovingRRRObjects(zutiShipName, p, this.getArmy(), 1.0F, true);
		}
	}
	// ----------------------------------------------------
}