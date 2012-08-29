/*4.10.1 class + Radars il2war logging*/
package com.maddox.il2.objects.air;

import java.io.IOException;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiSupportMethods_AI;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiSupportMethods_Multicrew;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;

public class Paratrooper extends ActorMesh implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener
{
	private static final int FPS = 30;
	private static final int FREEFLY_START_FRAME = 0;
	private static final int FREEFLY_LAST_FRAME = 19;
	private static final int FREEFLY_N_FRAMES = 20;
	private static final int FREEFLY_CYCLE_TIME = 633;
	private static final int FREEFLY_ROT_TIME = 2500;
	private static final int PARAUP1_START_FRAME = 19;
	private static final int PARAUP1_LAST_FRAME = 34;
	private static final int PARAUP1_N_FRAMES = 16;
	private static final int PARAUP1_CYCLE_TIME = 500;
	private static final int PARAUP2_START_FRAME = 34;
	private static final int PARAUP2_LAST_FRAME = 54;
	private static final int PARAUP2_N_FRAMES = 21;
	private static final int PARAUP2_CYCLE_TIME = 666;
	private static final int RUN_START_FRAME = 55;
	private static final int RUN_LAST_FRAME = 77;
	private static final int RUN_N_FRAMES = 23;
	private static final int RUN_CYCLE_TIME = 733;
	private static final int FALL_START_FRAME = 77;
	private static final int FALL_LAST_FRAME = 109;
	private static final int FALL_N_FRAMES = 33;
	private static final int FALL_CYCLE_TIME = 1066;
	private static final int LIE_START_FRAME = 109;
	private static final int LIE_LAST_FRAME = 128;
	private static final int LIE_N_FRAMES = 20;
	private static final int LIE_CYCLE_TIME = 633;
	private static final int LIEDEAD_START_FRAME = 129;
	private static final int LIEDEAD_N_FRAMES = 4;
	private static final int PARADEAD_FRAME = 133;
	private static final int FREEFLYDEAD_FRAME = 134;
	private static final float FREE_SPEED = 50.0F;
	private static final float PARA_SPEED = 5.0F;
	private static final float RUN_SPEED = 6.5454545F;
	public static final String playerParaName = "_paraplayer_";
	private String logAircraftName = null;
	private int idxOfPilotPlace;
	private NetUser driver;
	private int swimMeshCode = -1;
	private Vector3d speed;
	private Orient startOrient;
	private Orient faceOrient;
	private static final int ST_FREEFLY = 0;
	private static final int ST_PARAUP1 = 1;
	private static final int ST_PARAUP2 = 2;
	private static final int ST_PARATANGLED = 3;
	private static final int ST_RUN = 4;
	private static final int ST_FALL = 5;
	private static final int ST_LIE = 6;
	private static final int ST_LIEDEAD = 7;
	private static final int ST_SWIM = 8;
	private static final int ST_DISAPPEAR = 9;
	private int st = 0;
	private int dying = 0;
	static final int DYING_NONE = 0;
	static final int DYING_DEAD = 1;
	private int idxOfDeadPose;
	private long animStartTime;
	private long disappearTime;
	private int nRunCycles;
	private float turn_para_on_height;
	private static int _counter = 0;
	private static Mesh preload1 = null;
	private static Mesh preload2 = null;
	private static Mesh preload3 = null;
	private static Mesh preload4 = null;
	private static Mesh preload5 = null;
	private static Mesh preload6 = null;
	private static Point3d p = new Point3d();
	private static Orient o = new Orient();
	private static Vector3f n = new Vector3f();
	private boolean bCheksCaptured = false;
	static Class class$com$maddox$il2$objects$air$Paratrooper;
	
	public static class SPAWN implements NetSpawn
	{
		public void netSpawn(int i, NetMsgInput netmsginput)
		{
			try
			{
				Loc loc = new Loc((double)netmsginput.readFloat(), (double)netmsginput.readFloat(), (double)netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
				
				Vector3d vector3d = new Vector3d((double)netmsginput.readFloat(), (double)netmsginput.readFloat(), (double)netmsginput.readFloat());
				Actor actor = null;
				NetObj netobj = netmsginput.readNetObj();
				if (netobj != null)
					actor = (Actor)netobj.superObj();
				
				// TODO: Added by |ZUTI|
				// -------------------------------------------------------------------
				int ii = netmsginput.readUnsignedByte();
				int paraIdPlace = netmsginput.readUnsignedByte();
				//Make sure that player is not created as para twice (server command - this and player command - lover constructor)
				if(  World.getPlayerGunner() != null && World.getPlayerGunner().getCockpitNum() == paraIdPlace )
					return;
				// -------------------------------------------------------------------
				
				new Paratrooper(actor, ii, paraIdPlace, loc, vector3d, netmsginput, i);
			}
			catch (Exception exception)
			{
				System.out.println(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}
	
	class Mirror extends ParaNet
	{
		public Mirror(Actor actor, NetMsgInput netmsginput, int i)
		{
			super(actor, netmsginput, i);
			try
			{
				turn_para_on_height = netmsginput.readFloat();
				nRunCycles = netmsginput.readByte();
				driver = (NetUser)netmsginput.readNetObj();
				Paratrooper.this.testDriver();
			}
			catch (Exception exception)
			{}
		}
	}
	
	class Master extends ParaNet
	{
		public Master(Actor actor)
		{
			super(actor);
			actor.pos.getAbs(Paratrooper.p);
			float f = (float)Paratrooper.p.z;
			Engine.land();
			float f_0_ = f - Landscape.HQ((float)Paratrooper.p.x, (float)Paratrooper.p.y);
			if (f_0_ <= 500.0F)
				turn_para_on_height = 500.0F;
			else if (f_0_ >= 4000.0F)
				turn_para_on_height = 2000.0F;
			else
				turn_para_on_height = 500.0F + 1500.0F * ((f_0_ - 500.0F) / 3500.0F);
			access$732(Paratrooper.this, World.Rnd().nextFloat(1.0F, 1.2F));
			nRunCycles = World.Rnd().nextInt(6, 12);
			Class var_class = actor.getOwner().getClass();
			Object object = Property.value(var_class, "cockpitClass");
			if (object != null)
			{
				Class[] var_classes;
				if (object instanceof Class)
				{
					var_classes = new Class[1];
					var_classes[0] = (Class)object;
				}
				else
					var_classes = (Class[])object;
				for (int i = 0; i < var_classes.length; i++)
				{
					int i_2_ = Property.intValue(var_classes[i], "astatePilotIndx", 0);
					if (i_2_ == idxOfPilotPlace)
					{
						Actor actor_3_ = ((Aircraft)actor.getOwner()).netCockpitGetDriver(i);
						if (actor_3_ != null)
						{
							if (Mission.isSingle())
								driver = (NetUser)NetEnv.host();
							else if (actor_3_ instanceof NetGunner)
								driver = ((NetGunner)actor_3_).getUser();
							else
								driver = ((NetAircraft)actor_3_).netUser();
							break;
						}
					}
				}
			}
			Paratrooper.this.testDriver();
		}
	}
	
	class ParaNet extends ActorNet
	{
		public boolean netInput(NetMsgInput netmsginput) throws IOException
		{
			if (!netmsginput.isGuaranted())
				return false;
			byte i = netmsginput.readByte();
			int i_4_ = -1;
			switch (i)
			{
				case 68 :
				{
					i_4_ = 1;
					NetObj netobj = netmsginput.readNetObj();
					Actor actor = null;
					if (netobj != null)
						actor = (Actor)netobj.superObj();
					Paratrooper.this.Die(actor, false);
					break;
				}
				case 83 :
				{
					i_4_ = 1;
					NetObj netobj = netmsginput.readNetObj();
					Actor actor = null;
					if (netobj != null)
						actor = (Actor)netobj.superObj();
					Object[] objects = Paratrooper.this.getOwnerAttached();
					for (int i_5_ = 0; i_5_ < objects.length; i_5_++)
					{
						Chute chute = (Chute)objects[i_5_];
						if (Actor.isValid(chute))
							chute.tangleChute(actor);
					}
					break;
				}
			}
			if (i_4_ >= 0)
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, i_4_);
				postExclude(netmsginput.channel(), netmsgguaranted);
				return true;
			}
			return false;
		}
		
		public ParaNet(Actor actor)
		{
			super(actor);
		}
		
		public ParaNet(Actor actor, NetMsgInput netmsginput, int i)
		{
			super(actor, netmsginput.channel(), i);
		}
	}
	
	class Move extends Interpolate
	{
		public boolean tick()
		{
			if (st == 9)
			{
				if (dying == 0)
					checkCaptured();
				Paratrooper.this.postDestroy();
				return false;
			}
			if ((st == 6 || st == 7 || st == 8) && Time.current() >= disappearTime)
			{
				Paratrooper.this.postDestroy();
				return false;
			}
			if (dying != 0)
			{
				switch (st)
				{
					case 4 :
						st = 5;
						animStartTime = Time.current();
						break;
					case 6 :
						st = 7;
						idxOfDeadPose = World.Rnd().nextInt(0, 3);
						break;
				}
			}
			long l = Time.tickNext() - animStartTime;
			switch (st)
			{
				case 0 :
				case 1 :
				case 2 :
				case 3 :
				{
					pos.getAbs(Paratrooper.p);
					Engine.land();
					float f = Landscape.HQ((float)Paratrooper.p.x, (float)Paratrooper.p.y);
					if (st == 0)
					{
						if (l >= 2500L)
						{
							pos.setAbs(faceOrient);
							if (dying == 0 && ((float)Paratrooper.p.z - f <= turn_para_on_height) && speed.z < -5.0)
							{
								st = 1;
								animStartTime = Time.current();
								l = Time.tickNext() - animStartTime;
								new Chute(actor);
							}
						}
						else
						{
							pos.getAbs(Paratrooper.o);
							float f_6_ = (float)l / 2500.0F;
							if (f_6_ <= 0.0F)
								f_6_ = 0.0F;
							if (f_6_ >= 1.0F)
								f_6_ = 1.0F;
							Paratrooper.o.interpolate(startOrient, faceOrient, f_6_);
							pos.setAbs(Paratrooper.o);
						}
					}
					if (st == 1 && l >= 500L)
					{
						st = 2;
						animStartTime = Time.current();
						l = Time.tickNext() - animStartTime;
					}
					Paratrooper.p.scaleAdd((double)Time.tickLenFs(), speed, Paratrooper.p);
					speed.z -= (double)(Time.tickLenFs() * World.g());
					if (st == 2)
					{
						if (speed.x != 0.0)
							speed.x -= (Math.abs(speed.x) / speed.x * 0.009999999776482582 * (speed.x * speed.x) * (double)Time.tickLenFs());
						if (speed.y != 0.0)
							speed.y -= (Math.abs(speed.y) / speed.y * 0.009999999776482582 * (speed.y * speed.y) * (double)Time.tickLenFs());
					}
					else
					{
						if (speed.x != 0.0)
							speed.x -= (Math.abs(speed.x) / speed.x * 0.0010000000474974513 * (speed.x * speed.x) * (double)Time.tickLenFs());
						if (speed.y != 0.0)
							speed.y -= (Math.abs(speed.y) / speed.y * 0.0010000000474974513 * (speed.y * speed.y) * (double)Time.tickLenFs());
					}
					double d = (double)(st == 2 ? 5.0F : 50.0F);
					if (-speed.z > d)
					{
						double d_7_ = -speed.z - d;
						if (d_7_ > (double)(Time.tickLenFs() * 20.0F))
							d_7_ = (double)(Time.tickLenFs() * 20.0F);
						speed.z += d_7_;
					}
					if (Paratrooper.p.z <= (double)f)
					{
						boolean bool = speed.length() > 10.0;
						Vector3d vector3d = new Vector3d();
						vector3d.set(1.0, 0.0, 0.0);
						faceOrient.transform(vector3d);
						speed.set(vector3d);
						speed.z = 0.0;
						speed.normalize();
						speed.scale(RUN_SPEED);
						Paratrooper.p.z = (double)f;
						if (bool || dying != 0)
						{
							st = 7;
							animStartTime = Time.current();
							disappearTime = (Time.tickNext() + (long)(1000 * World.Rnd().nextInt(25, 35)));
							idxOfDeadPose = World.Rnd().nextInt(0, 3);
							new MsgAction(0.0, actor)
							{
								public void doAction(Object object)
								{
									Paratrooper paratrooper = (Paratrooper)object;
									paratrooper.Die(Engine.actorLand());
								}
							};
						}
						else
						{
							st = 4;
							animStartTime = Time.current();
							if (Paratrooper.this.name().equals("_paraplayer_") && Mission.isNet() && World.getPlayerFM() != null && Actor.isValid(World.getPlayerAircraft()) && World.getPlayerAircraft().isNetPlayer())
							{
								FlightModel flightmodel = World.getPlayerFM();
								if (flightmodel.isWasAirborne() && flightmodel.isStationedOnGround() && !flightmodel.isNearAirdrome())
									Chat.sendLogRnd(2, "gore_walkaway", World.getPlayerAircraft(), null);
							}
						}
						pos.setAbs(faceOrient);
						Object[] objects = Paratrooper.this.getOwnerAttached();
						for (int i = 0; i < objects.length; i++)
						{
							Chute chute = (Chute)objects[i];
							if (Actor.isValid(chute))
								chute.landing();
						}
					}
					pos.setAbs(Paratrooper.p);
					break;
				}
				case 4 :
					pos.getAbs(Paratrooper.p);
					Paratrooper.p.scaleAdd((double)Time.tickLenFs(), speed, Paratrooper.p);
					Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
					pos.setAbs(Paratrooper.p);
					if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y))
					{
						if (swimMeshCode < 0)
						{
							st = 5;
							animStartTime = Time.current();
						}
						else
						{
							Paratrooper.this.setMesh(GetMeshName_Water(swimMeshCode));
							pos.getAbs(Paratrooper.p);
							Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
							pos.setAbs(Paratrooper.p);
							st = 8;
							animStartTime = Time.current();
							disappearTime = (Time.tickNext() + (long)(1000 * World.Rnd().nextInt(25, 35)));
							checkCaptured();
						}
					}
					else if (l / 733L >= (long)nRunCycles)
					{
						st = 5;
						animStartTime = Time.current();
					}
					break;
				case 5 :
					pos.getAbs(Paratrooper.p);
					Paratrooper.p.scaleAdd((double)Time.tickLenFs(), speed, Paratrooper.p);
					Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
					if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y))
					{
						if (swimMeshCode < 0)
							Paratrooper.p.z -= 0.5;
						else
						{
							Paratrooper.this.setMesh(GetMeshName_Water(swimMeshCode));
							pos.getAbs(Paratrooper.p);
							Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
							pos.setAbs(Paratrooper.p);
							st = 8;
							animStartTime = Time.current();
							disappearTime = (Time.tickNext() + (long)(1000 * World.Rnd().nextInt(25, 35)));
							checkCaptured();
							break;
						}
					}
					pos.setAbs(Paratrooper.p);
					if (l >= 1066L)
					{
						st = 6;
						animStartTime = Time.current();
						disappearTime = (Time.tickNext() + (long)(1000 * World.Rnd().nextInt(25, 35)));
						checkCaptured();
					}
					break;
				case 6 :
					// TODO: Added by |ZUTI|
					// -------------------------------------------------------------
					if (zutiIsMaster && !Paratrooper.this.zutiRecordedSafeLanding)
					{
						//System.out.println("Paratrooper - lie 1");
						Paratrooper.this.zutiRecordedSafeLanding = true;
						BornPlace bp = ZutiSupportMethods.getParatrooperAreaBornPlace(Paratrooper.this.pos.getAbsPoint().x, Paratrooper.this.pos.getAbsPoint().y, Paratrooper.this.getArmy());
						
						if (bp != null)
						{
							bp.zutiParatroopersInsideHomeBaseArea++;
							//System.out.println("Paratrooper - lie 1: number of hostile paratroopers inside home base at x=" + bp.place.x + " and y=" + bp.place.y + " is: " + bp.zutiParatroopersInsideHomeBaseArea +". Needed: " + bp.zutiCapturingRequiredParatroopers);
							
							ZutiSupportMethods.isBornPlaceOverrunByPara(bp, Paratrooper.this.getArmy());
						}
					}
					// -------------------------------------------------------------
				case 7 :
					pos.getAbs(Paratrooper.p);
					Paratrooper.p.z = Engine.land().HQ(Paratrooper.p.x, Paratrooper.p.y);
					if (World.land().isWater(Paratrooper.p.x, Paratrooper.p.y))
						Paratrooper.p.z -= 3.0;
					pos.setAbs(Paratrooper.p);
					break;
			}
			setAnimFrame((double)Time.tickNext());
			return true;
		}
	}
	
	private class SoldDraw extends ActorMeshDraw
	{
		private SoldDraw()
		{
		/* empty */
		}
		
		public int preRender(Actor actor)
		{
			setAnimFrame((double)Time.current());
			return super.preRender(actor);
		}
	}
	
	public static void resetGame()
	{
		_counter = 0;
		preload1 = preload2 = preload3 = null;
	}
	
	public static void PRELOAD()
	{
		preload1 = new Mesh(GetMeshName(1));
		preload2 = new Mesh(GetMeshName(2));
		preload3 = new Mesh(Chute.GetMeshName());
		preload4 = new Mesh(GetMeshName_Water(0));
		preload5 = new Mesh(GetMeshName_Water(1));
		preload6 = new Mesh(GetMeshName_Water(2));
	}
	
	public void msgCollisionRequest(Actor actor, boolean[] bools)
	{
		if (actor instanceof Aircraft && actor.isNet() && actor.isNetMirror())
			bools[0] = false;
		if ((actor == getOwner() || getOwner() == null) && Time.current() - animStartTime < 2800L)
			bools[0] = false;
		if (dying != 0 && (actor == null || (!(actor instanceof ShipGeneric) && !(actor instanceof BigshipGeneric))))
			bools[0] = false;
	}
	
	public void msgCollision(Actor actor, String string, String string_10_)
	{
		if (st != 9)
		{
			if (dying != 0)
			{
				if (actor != null && (actor instanceof ShipGeneric || actor instanceof BigshipGeneric))
					st = 9;
			}
			else if (actor != null && (actor instanceof ShipGeneric || actor instanceof BigshipGeneric))
			{
				boolean bool = Math.abs(speed.z) > 10.0;
				if (bool)
					Die(actor);
				st = 9;
			}
			else
			{
				Point3d point3d = p;
				pos.getAbs(p);
				Point3d point3d_11_ = actor.pos.getAbsPoint();
				Vector3d vector3d = new Vector3d();
				vector3d.set(point3d.x - point3d_11_.x, point3d.y - point3d_11_.y, 0.0);
				if (vector3d.length() < 0.0010)
				{
					float f = World.Rnd().nextFloat(0.0F, 359.99F);
					vector3d.set((double)Geom.sinDeg(f), (double)Geom.cosDeg(f), 0.0);
				}
				vector3d.normalize();
				float f = 0.2F;
				vector3d.add((double)World.Rnd().nextFloat(-f, f), (double)World.Rnd().nextFloat(-f, f), (double)World.Rnd().nextFloat(-f, f));
				vector3d.normalize();
				float f_12_ = 13.090909F * Time.tickLenFs();
				vector3d.scale((double)f_12_);
				speed.z *= 0.5;
				point3d.add(vector3d);
				pos.setAbs(point3d);
				if (st == 4)
				{
					st = 5;
					animStartTime = Time.current();
				}
				if (st == 6 && dying == 0 && actor instanceof UnitInterface && actor.getSpeed(null) > 0.5)
					Die(actor);
			}
		}
	}
	
	public void msgShot(Shot shot)
	{
		if (st != 9)
		{
			shot.bodyMaterial = 3;
			if (dying == 0 && !(shot.power <= 0.0F))
			{
				if (shot.powerType == 1)
					Die(shot.initiator);
				else if (!(shot.v.length() < 20.0))
					Die(shot.initiator);
			}
		}
	}
	
	public void msgExplosion(Explosion explosion)
	{
		if (st != 9 && dying == 0)
		{
			float f = 0.0050F;
			float f_13_ = 0.1F;
			if (explosion != null)
			{
				/* empty */
			}
			if (Explosion.killable(this, explosion.receivedTNT_1meter(this), f, f_13_, 0.0F))
				Die(explosion.initiator);
		}
	}
	
	public void checkCaptured()
	{
		//Added by _ITAF_Radar
		//-------------------------------------
		il2war_ParatrooperLogging(p);
		//-------------------------------------
		
		bCheksCaptured = true;
		if (logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
			EventLog.onParaLanded(this, logAircraftName, idxOfPilotPlace);
		if (Front.isCaptured(this))
		{
			if (name().equals("_paraplayer_"))
			{
				World.setPlayerCaptured();
				if (Config.isUSE_RENDER())
					HUD.log("PlayerCAPT");
				if (Mission.isNet())
					Chat.sendLog(1, "gore_captured", (NetUser)NetEnv.host(), null);
			}
			if (logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
				EventLog.onCaptured(this, logAircraftName, idxOfPilotPlace);
		}
	}
	
	public boolean isChecksCaptured()
	{
		if (dying != 0)
			return true;
		return bCheksCaptured;
	}
	
	private void Die(Actor actor)
	{
		Die(actor, true);
	}
	
	private void Die(Actor actor, boolean bool)
	{
		//TODO: Added by |ZUTI|
		//--------------------------------------------
		if( zutiBailedOnTheDeck )
			return;
		//--------------------------------------------
		
		if (dying == 0)
		{
			World.onActorDied(this, actor);
			if (actor != this)
			{
				if (name().equals("_paraplayer_"))
				{
					World.setPlayerDead();
					if (Config.isUSE_RENDER())
						HUD.log("Player_Killed");
					if (Mission.isNet())
					{
						if (actor instanceof Aircraft && ((Aircraft)actor).isNetPlayer() && Actor.isAlive(World.getPlayerAircraft()))
							Chat.sendLogRnd(1, "gore_pkonchute", (Aircraft)actor, World.getPlayerAircraft());
						Chat.sendLog(0, "gore_killed", (NetUser)NetEnv.host(), (NetUser)NetEnv.host());
					}
				}
				if (logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
				{
					if (Actor.isValid(actor) && actor != Engine.actorLand())
						EventLog.onParaKilled(this, logAircraftName, idxOfPilotPlace, actor);
					else
						EventLog.onPilotKilled(this, logAircraftName, idxOfPilotPlace);
				}
			}
			dying = 1;
			if (isNet() && bool)
			{
				try
				{
					NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(68);
					if (actor != null)
						netmsgguaranted.writeNetObj(actor.net);
					else
						netmsgguaranted.writeNetObj(null);
					net.postExclude(null, netmsgguaranted);
				}
				catch (Exception exception)
				{
					/* empty */
				}
			}
		}
	}
	
	public void destroy()
	{
		Object[] objects = getOwnerAttached();
		for (int i = 0; i < objects.length; i++)
		{
			Chute chute = (Chute)objects[i];
			if (Actor.isValid(chute))
				chute.destroy();
		}
		if (Mission.isPlaying() && World.cur() != null && driver != null && (driver.isMaster() || driver.isTrackWriter()))
			World.cur().checkViewOnPlayerDied(this);
		super.destroy();
	}
	
	public Object getSwitchListener(Message message)
	{
		return this;
	}
	
	void chuteTangled(Actor actor, boolean bool)
	{
		if (st == 1 || st == 2)
		{
			st = 3;
			animStartTime = Time.current();
			pos.setAbs(faceOrient);
			if (logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
				EventLog.onChuteKilled(this, logAircraftName, idxOfPilotPlace, actor);
			if (isNet() && bool)
			{
				try
				{
					NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(83);
					if (actor != null)
						netmsgguaranted.writeNetObj(actor.net);
					else
						netmsgguaranted.writeNetObj(null);
					net.postExclude(null, netmsgguaranted);
				}
				catch (Exception exception)
				{
					/* empty */
				}
			}
		}
	}
	
	void setAnimFrame(double d)
	{
		int i;
		int i_14_;
		float f;
		switch (st)
		{
			case 0 :
			{
				i = 0;
				i_14_ = 19;
				int i_15_ = 633;
				double d_16_ = d - (double)animStartTime;
				if (d_16_ <= 0.0)
					f = 0.0F;
				else if (d_16_ >= (double)i_15_)
					f = 1.0F;
				else
					f = (float)(d_16_ / (double)i_15_);
				if (f >= 1.0F && dying != 0)
				{
					i = i_14_ = 134;
					f = 0.0F;
				}
				break;
			}
			case 1 :
			{
				i = 19;
				i_14_ = 34;
				int i_17_ = 500;
				double d_18_ = d - (double)animStartTime;
				if (d_18_ <= 0.0)
					f = 0.0F;
				else if (d_18_ >= (double)i_17_)
					f = 1.0F;
				else
					f = (float)(d_18_ / (double)i_17_);
				break;
			}
			case 2 :
			case 3 :
			{
				i = 34;
				i_14_ = 54;
				int i_19_ = 666;
				double d_20_ = d - (double)animStartTime;
				if (d_20_ <= 0.0)
					f = 0.0F;
				else if (d_20_ >= (double)i_19_)
					f = 1.0F;
				else
					f = (float)(d_20_ / (double)i_19_);
				if (f >= 1.0F && dying != 0)
				{
					i = i_14_ = 133;
					f = 0.0F;
				}
				break;
			}
			case 4 :
			{
				i = 55;
				i_14_ = 77;
				int i_21_ = 733;
				double d_22_ = d - (double)animStartTime;
				d_22_ %= (double)i_21_;
				if (d_22_ < 0.0)
					d_22_ += (double)i_21_;
				f = (float)(d_22_ / (double)i_21_);
				break;
			}
			case 5 :
			{
				i = 77;
				i_14_ = 109;
				int i_23_ = 1066;
				double d_24_ = d - (double)animStartTime;
				if (d_24_ <= 0.0)
					f = 0.0F;
				else if (d_24_ >= (double)i_23_)
					f = 1.0F;
				else
					f = (float)(d_24_ / (double)i_23_);
				break;
			}
			case 6 :
			{
				i = 109;
				i_14_ = 128;
				int i_25_ = 633;
				double d_26_ = d - (double)animStartTime;
				if (d_26_ <= 0.0)
					f = 0.0F;
				else if (d_26_ >= (double)i_25_)
					f = 1.0F;
				else
					f = (float)(d_26_ / (double)i_25_);
				
				// TODO: Added by |ZUTI|
				// -------------------------------------------------------------
				if (zutiIsMaster && !zutiRecordedSafeLanding)
				{
					//System.out.println("Paratrooper - lie 2");
					zutiRecordedSafeLanding = true;
					BornPlace bp = ZutiSupportMethods.getParatrooperAreaBornPlace(pos.getAbsPoint().x, pos.getAbsPoint().y, getArmy());
					
					if (bp != null)
					{
						bp.zutiParatroopersInsideHomeBaseArea++;
						//System.out.println("Paratrooper - lie 2: numbe of hostile paratroopers inside home base at x=" + bp.place.x + " and y=" + bp.place.y + " is: " + bp.zutiParatroopersInsideHomeBaseArea +". Needed: " + bp.zutiCapturingRequiredParatroopers);
						
						ZutiSupportMethods.isBornPlaceOverrunByPara(bp, getArmy());
					}
				}
				// -------------------------------------------------------------
				break;
			}
			case 8 :
				return;
			case 9 :
				return;
			default :
				i = i_14_ = 129 + idxOfDeadPose;
				f = 0.0F;
		}
		mesh().setFrameFromRange(i, i_14_, f);
	}
	
	public int HitbyMask()
	{
		return -25;
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
			return 1;
		if (bulletpropertieses[0].powerType == 1)
			return 0;
		if (bulletpropertieses[1].powerType == 1)
			return 1;
		if (bulletpropertieses[0].cumulativePower > 0.0F)
			return 1;
		if (bulletpropertieses[0].powerType == 2)
			return 1;
		return 0;
	}
	
	public int chooseShotpoint(BulletProperties bulletproperties)
	{
		if (dying != 0)
			return -1;
		return 0;
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
	
	private static String GetMeshName(int i)
	{
		return ("3do/humans/Paratroopers/" + (i == 2 ? "Germany" : "Russia") + "/mono.sim");
	}
	
	private static String GetMeshName_Water(int i)
	{
		return ("3do/humans/Paratroopers/Water/" + (i == 0 ? "JN_Jacket" : i == 1 ? "US_Jacket" : "US_Dinghy") + "/live.sim");
	}
	
	public void prepareSkin(String string, String string_27_, Mat[] mats)
	{
		if (Config.isUSE_RENDER())
		{
			String string_28_ = "Pilot";
			int i = mesh().materialFind(string_28_);
			if (i >= 0)
			{
				Mat mat;
				if (FObj.Exist(string))
					mat = (Mat)FObj.Get(string);
				else
				{
					Mat mat_29_ = mesh().material(i);
					mat = (Mat)mat_29_.Clone();
					mat.Rename(string);
					mat.setLayer(0);
					mat.set('\0', string_27_);
				}
				if (mats != null)
					mats[0] = mat;
				mesh().materialReplace(string_28_, mat);
			}
		}
	}
	
	public Paratrooper(Actor actor, int i, int idParaPlace, Loc loc, Vector3d vector3d, NetMsgInput netmsginput, int i_31_)
	{
		super(GetMeshName(i));
		
		// TODO: Added by |ZUTI|
		// -------------------------------------------------------------------		
		NetServerParams params = Main.cur().netServerParams;
		if( idParaPlace == 255 && params != null )
		{
			//Process para drops separately. Game engine is working strange when it comes to that.
			//Sometimes there are duplicates... most likely this is due to the fact that paras are
			//primarely used for pilots and are distributed over the new. When AC drops paras, then
			//this thing should not be done the same way as eject sequence. So, eliminate possible
			//duplicates.
			if( params.isMaster() && netmsginput != null )
			{
				//Server side duplicates destroying
				this.destroy();
				//System.out.println(" DESTROYED 1!");
				return;
			}
			else if( !params.isMaster() && netmsginput == null )
			{
				//Client side duplicates destroying
				this.destroy();
				//System.out.println(" DESTROYED 2!");
				return;
			}
		}
		//-------------------------------------------------------------------
		
		startOrient = new Orient();
		loc.get(startOrient);
		faceOrient = new Orient();
		faceOrient.set(startOrient);
		faceOrient.setYPR(faceOrient.getYaw(), 0.0F, 0.0F);
		Vector3d vector3d_32_ = new Vector3d();
		vector3d_32_.set(1.0, 0.0, 0.0);
		faceOrient.transform(vector3d_32_);
		speed = new Vector3d();
		speed.set(vector3d);
		setOwner(actor);
		idxOfPilotPlace = idParaPlace;
		setArmy(i);
		swimMeshCode = -1;
		if (Actor.isValid(actor) && actor instanceof Aircraft)
		{
			String string = ((Aircraft)actor).getRegiment().country();
			if ("us".equals(string) || "gb".equals(string))
				swimMeshCode = idParaPlace == 0 ? 2 : 1;
			else if ("ja".equals(string))
				swimMeshCode = 0;
		}
		o.setAT0(speed);
		o.set(o.azimut(), 0.0F, 0.0F);
		pos.setAbs(loc);
		pos.reset();
		st = 0;
		animStartTime = Time.tick();
		dying = 0;
		setName("_para_" + _counter++);
		collide(true);
		draw = new SoldDraw();
		dreamFire(true);
		drawing(true);
		if (!interpEnd("move"))
			interpPut(new Move(), "move", Time.current(), null);
		if (Actor.isValid(actor))
			logAircraftName = EventLog.name(actor);
		if (netmsginput == null)
		{
			net = new Master(this);
		}
		else
		{
			net = new Mirror(this, netmsginput, i_31_);
		}
		
		//TODO: Added by |ZUTI|
		//--------------------------------------------
		if( Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() )
		{
			zutiIsMaster = true;
			//System.out.println(" Paratrooper created 1, status: " + zutiStatus);
		}
		else if( !Mission.isNet() )
		{
			zutiIsMaster = true;
			//System.out.println(" Paratrooper created 2, status: " + zutiStatus);
		}
		else
		{
			zutiIsMaster = false;
			//System.out.println(" Paratrooper created 3, status: " + zutiStatus);
		}
		
		zutiBailedOnTheDeck(actor);
		//--------------------------------------------
	}
	
	public Paratrooper(Actor actor, int i, int i_33_, Loc loc, Vector3d vector3d)
	{
		this(actor, i, i_33_, loc, vector3d, null, 0);
		
		//TODO: Added by |ZUTI|
		//--------------------------------------------
		zutiBailedOnTheDeck(actor);
		//--------------------------------------------
	}
	
	private void testDriver()
	{
		if (driver != null && (driver.isMaster() || driver.isTrackWriter()) && Actor.isValid(getOwner()))
		{
			if (World.isPlayerGunner())
				World.doGunnerParatrooper(this);
			else
				World.doPlayerParatrooper(this);
			setName("_paraplayer_");
			if (Mission.isNet())
				Chat.sendLog(1, "gore_bailedout", (NetUser)NetEnv.host(), null);
		}
		if (driver != null)
			driver.tryPreparePilot(this);
	}
	
	public NetMsgSpawn netReplicate(NetChannel netchannel) throws IOException
	{
		NetMsgSpawn netmsgspawn = new NetMsgSpawn(net);
		Point3d point3d = pos.getAbsPoint();
		netmsgspawn.writeFloat((float)point3d.x);
		netmsgspawn.writeFloat((float)point3d.y);
		netmsgspawn.writeFloat((float)point3d.z);
		Orient orient = pos.getAbsOrient();
		netmsgspawn.writeFloat(orient.getAzimut());
		netmsgspawn.writeFloat(orient.getTangage());
		netmsgspawn.writeFloat(orient.getKren());
		netmsgspawn.writeFloat((float)speed.x);
		netmsgspawn.writeFloat((float)speed.y);
		netmsgspawn.writeFloat((float)speed.z);
		netmsgspawn.writeByte(getArmy());
		if (getOwner() != null && netchannel != null && netchannel.isMirrored(getOwner().net))
			netmsgspawn.writeNetObj(getOwner().net);
		else
			netmsgspawn.writeNetObj(null);
		netmsgspawn.writeByte(idxOfPilotPlace);
		netmsgspawn.writeFloat(turn_para_on_height);
		netmsgspawn.writeByte(nRunCycles);
		netmsgspawn.writeNetObj(driver);
		return netmsgspawn;
	}
	
	static float access$732(Paratrooper paratrooper, float f)
	{
		return paratrooper.turn_para_on_height *= f;
	}
	
	static Class class$zutiParatrooper(String string)
	{
		Class var_class;
		try
		{
			var_class = Class.forName(string);
		}
		catch (ClassNotFoundException classnotfoundexception)
		{
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
		return var_class;
	}
	
	static
	{
		Spawn.add((class$com$maddox$il2$objects$air$Paratrooper == null ? (class$com$maddox$il2$objects$air$Paratrooper = class$zutiParatrooper("com.maddox.il2.objects.air.Paratrooper")) : class$com$maddox$il2$objects$air$Paratrooper), new SPAWN());
	}
	
	public boolean isChuteSafelyOpened()
    {
        return st == 2 || st == 6 || st == 8 || st == 9;
    }
	
	// TODO: |ZUTI| variables and methods
	// ----------------------------------------------------
	private boolean zutiRecordedSafeLanding = false;
	private boolean zutiIsMaster = false;
	private boolean zutiBailedOnTheDeck = false;
	
	// _ITAF_Radar////////////////////
	private void il2war_ParatrooperLogging(com.maddox.JGP.Point3d p)
	{
		try
		{
			String hitMap;
			if (com.maddox.il2.engine.Engine.land().isWater(p.x, p.y))
				hitMap = "water";
			else
				hitMap = "ground";
			// bombOwner = " " + bombOwner.substring(0,
			// bombOwner.lastIndexOf("_"));
			String coords = "X: " + Double.toString(p.x) + " Y: " + Double.toString(p.y);
			//String bombType = " " + this.toString().substring(this.toString().lastIndexOf(".") + 1);
			
			String s = "ParatrooperLogging " + this.animStartTime + "(" + this.idxOfPilotPlace + ")" + " touchdown in " + coords + " (" + hitMap + ") drop by ";
			if (this.getOwner() != null)
				s += this.getOwner().name();
			
			com.maddox.il2.ai.EventLog.type(s);
			//System.out.println(s);
		}
		catch (ActorException ex)
		{
			//System.out.println("ParatrooperLogging Exception Thrown " + ex.getMessage());	
		}
	}
	
	private void zutiBailedOnTheDeck(Actor actor)
	{
		if( name().equals("_paraplayer_") )
		{
			System.out.println("AC = " + actor + ", isAC=" + (actor instanceof Aircraft));
			Point3d zp = actor.pos.getAbsPoint();
			if( World.land().isWater(zp.x, zp.y) )
			{
				if( actor instanceof Aircraft )
				{
					Aircraft ac = (Aircraft)actor;
					if( ac.FM.getSpeedKMH() < ZutiSupportMethods_AI.MAX_SPEED_FOR_DECK_BAILOUT &&
						ac.FM.getAltitude() < ZutiSupportMethods_AI.MAX_HEIGHT_FOR_DECK_BAILOUT )
					{
						zutiBailedOnTheDeck = true;
					}
				}
			}
		}
	}
	// ----------------------------------------------------
}