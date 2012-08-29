/*4.10.1 class*/
package com.maddox.il2.game;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.AirportGround;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Target;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiSupportMethods_AI;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.builder.ZutiSupportMethods_Builder;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.ZutiSupportMethods_Engine;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.gui.ZutiSupportMethods_GUI;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetFilesTrack;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.ZutiSupportMethods_Net;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.ZutiSupportMethods_Air;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.il2.objects.effects.Zip;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ZutiSupportMethods_Ships;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.artillery.RocketryGeneric;
import com.maddox.il2.objects.vehicles.radios.TypeHasBeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasHayRake;
import com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding;
import com.maddox.il2.objects.vehicles.radios.TypeHasMeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation;
import com.maddox.il2.objects.vehicles.stationary.Smoke;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Destroy;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.Joy;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelCallbackStream;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetChannelStream;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgDestroy;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CmdMusic;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import com.maddox.il2.game.ZutiAircraft;
import java.util.StringTokenizer;

public class Mission implements Destroy
{
	private int curYear;
    private int curMonth;
    private int curDay;
    private float curWindDirection;
    private float curWindVelocity;
    private float curGust;
    private float curTurbulence;
    private static java.util.ArrayList beaconsRed = new ArrayList();
    private static java.util.ArrayList beaconsBlue = new ArrayList();
    private static java.util.ArrayList meaconsRed = new ArrayList();
    private static java.util.ArrayList meaconsBlue = new ArrayList();
    private static java.util.Map hayrakeMap = new HashMap();
    public static boolean hasRadioStations = false;
    private static boolean radioStationsLoaded = false;
    private float bigShipHpDiv;
	
	public static final String DIR = "missions/";
	public static final String DIRNET = "missions/Net/Cache/";
	public static final float CLOUD_HEIGHT = 8000.0F;
	private String name = null;
	private SectFile sectFile;
	private long sectFinger = 0L;
	public ArrayList actors = new ArrayList();
	private int curActor = 0;
	private boolean bPlaying = false;
	private int curCloudsType = 0;
	private float curCloudsHeight = 1000.0F;
	protected static int viewSet = 0;
	protected static int iconTypes = 0;
	private static HashMap respawnMap = new HashMap();
	private String player;
	private boolean _loadPlayer = false;
	private int playerNum = 0;
	private HashMap mapWingTakeoff;
	private static SectFile chiefsIni;
	private static ActorSpawnArg spawnArg = new ActorSpawnArg();
	private static Point3d p = new Point3d();
	private static Orient o = new Orient();
	public static final int NET_MSG_ID_NAME = 0;
	public static final int NET_MSG_ID_BODY = 1;
	public static final int NET_MSG_ID_BODY_END = 2;
	public static final int NET_MSG_ID_ACTORS = 3;
	public static final int NET_MSG_ID_ACTORS_END = 4;
	public static final int NET_MSG_ID_LOADED = 5;
	public static final int NET_MSG_ID_BEGIN = 10;
	public static final int NET_MSG_ID_TOD = 11;
	public static final int NET_MSG_ID_START = 12;
	public static final int NET_MSG_ID_TIME = 13;
	public static final int NET_MSG_ID_END = 20;
	protected NetObj net;
	static Class class$java$lang$String;
	static Class class$com$maddox$rts$SectFile;
	static Class class$com$maddox$il2$game$Mission;
	
	static class SPAWN implements NetSpawn
	{
		public void netSpawn(int i, NetMsgInput netmsginput)
		{
			try
			{
				if (Main.cur().mission != null)
					Main.cur().mission.destroy();
				Mission mission = new Mission();
				if (cur() != null)
					cur().destroy();
				mission.clear();
				Main.cur().mission = mission;
				mission.createNetObject(netmsginput.channel(), i);
				Main.cur().missionLoading = mission;
			}
			catch (Exception exception)
			{
				printDebug(exception);
			}
		}
	}
	
	class Mirror extends NetMissionObj
	{
		public Mirror(Mission mission_0_, NetChannel netchannel, int i)
		{
			super((Object)mission_0_, netchannel, i);
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(0);
				postTo(netchannel, netmsgguaranted);
			}
			catch (Exception exception)
			{
				if (mission_0_ != null)
				{
					/* empty */
				}
				Mission.printDebug(exception);
			}
		}
	}
	
	class Master extends NetMissionObj
	{
		public Master(Mission mission_1_)
		{
			super((Object)mission_1_);
			mission_1_.sectFinger = mission_1_.sectFile.fingerExcludeSectPrefix("$$$");
		}
	}
	
	class NetMissionObj extends NetObj implements NetChannelCallbackStream
	{
		private void msgCallback(NetChannel netchannel, int i)
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
				netmsgguaranted.writeByte(i);
				NetMsgInput netmsginput = new NetMsgInput();
				netmsginput.setData(netchannel, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
				MsgNet.postReal(Time.currentReal(), this, netmsginput);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
		
		public boolean netChannelCallback(NetChannelOutStream netchanneloutstream, NetMsgGuaranted netmsgguaranted)
		{
			if (netmsgguaranted instanceof NetMsgSpawn)
				msgCallback(netchanneloutstream, 0);
			else if (!(netmsgguaranted instanceof NetMsgDestroy))
			{
				while_0_ : do
				{
					boolean bool;
					try
					{
						NetMsgInput netmsginput = new NetMsgInput();
						netmsginput.setData(netchanneloutstream, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
						int i = netmsginput.readUnsignedByte();
						switch (i)
						{
							case 0 :
								msgCallback(netchanneloutstream, 1);
								break while_0_;
							case 2 :
								msgCallback(netchanneloutstream, 3);
								break while_0_;
							case 4 :
								msgCallback(netchanneloutstream, 4);
								break while_0_;
							case 12 :
								Main3D.cur3D().gameTrackRecord().startKeyRecord(netmsgguaranted);
								bool = false;
								break;
							default :
								break while_0_;
						}
					}
					catch (Exception exception)
					{
						exception.printStackTrace();
						break;
					}
					return bool;
				}
				while (false);
			}
			return true;
		}
		
		public boolean netChannelCallback(NetChannelInStream netchannelinstream, NetMsgInput netmsginput)
		{
			try
			{
				int i = netmsginput.readUnsignedByte();
				if (i == 4)
					netchannelinstream.setPause(true);
				else if (i == 12)
				{
					netchannelinstream.setGameTime();
					// TODO: Edited by |ZUTI|
					// if (isCoop())
					if (Mission.isCoop() || Mission.isDogfight())
					{
						Main.cur().netServerParams.prepareHidenAircraft();
						Mission.doMissionStarting();
						
						Time.setPause(false);
					}
					Main3D.cur3D().gameTrackPlay().startKeyPlay();
				}
				netmsginput.reset();
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
			return true;
		}
		
		public void netChannelCallback(NetChannelInStream netchannelinstream, NetMsgGuaranted netmsgguaranted)
		{
			if (!(netmsgguaranted instanceof NetMsgSpawn) && !(netmsgguaranted instanceof NetMsgDestroy))
			{
				try
				{
					NetMsgInput netmsginput = new NetMsgInput();
					netmsginput.setData(netchannelinstream, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
					int i = netmsginput.readUnsignedByte();
					if (i == 4)
						netchannelinstream.setPause(false);
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}
		}
		
		public boolean netInput(NetMsgInput netmsginput) throws IOException
		{
			Mission mission = (Mission)superObj();
			mission.netInput(netmsginput);
			return true;
		}
		
		public void msgNetNewChannel(NetChannel netchannel)
		{
			if (Main.cur().missionLoading == null)
				tryReplicate(netchannel);
		}
		
		private void tryReplicate(NetChannel netchannel)
		{
			if (netchannel.isReady() && netchannel.isPublic() && netchannel != masterChannel && !netchannel.isMirrored(this) && netchannel.userState == 1)
			{
				try
				{
					postTo(netchannel, new NetMsgSpawn(this));
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}
		}
		
		public NetMissionObj(Object object)
		{
			super(object);
		}
		
		public NetMissionObj(Object object, NetChannel netchannel, int i)
		{
			super(object, netchannel, i);
		}
	}
	
	static class WingTakeoffPos
	{
		public int x;
		public int y;
		
		public WingTakeoffPos(double d, double d_2_)
		{
			x = (int)(d / 100.0) * 100;
			y = (int)(d_2_ / 100.0) * 100;
		}
		
		public boolean equals(Object object)
		{
			if (object == null)
				return false;
			if (!(object instanceof WingTakeoffPos))
				return false;
			WingTakeoffPos wingtakeoffpos_3_ = (WingTakeoffPos)object;
			return x == wingtakeoffpos_3_.x && y == wingtakeoffpos_3_.y;
		}
		
		public int hashCode()
		{
			return x + y;
		}
	}
	
	class TimeOutWing
	{
		String wingName;
		
		public void start()
		{
			if (!isDestroyed())
			{
				try
				{
					// TODO: Added by |ZUTI|: if wing is spawned on overrun home base, don't load it!
					// ----------------------------------------------------------------------------
					Point3d toPoint = ZutiSupportMethods_Air.getWingTakeoffLocation(sectFile, wingName);
					if (toPoint != null)
					{
						Squadron squadron = Squadron.New(wingName.substring(0, wingName.length() - 1));
						BornPlace bp = ZutiSupportMethods_Net.getNearestBornPlace_AnyArmy(toPoint.x, toPoint.y);
						
						if (bp != null && squadron.getArmy() != bp.army)
						{
							if (Math.sqrt(Math.pow(toPoint.x - bp.place.x, 2) + Math.pow(toPoint.y - bp.place.y, 2)) <= bp.r)
							{
								System.out.println("Mission: wing " + wingName + " not loaded because its TO point is located on enemy home base!");
								return;
							}
						}
					}
					// ----------------------------------------------------------------------------
					
					NetAircraft.loadingCoopPlane = false;
					Wing wing = new Wing();
					wing.load(sectFile, wingName, null);
					Mission.this.prepareSkinInWing(sectFile, wing);
					wing.setOnAirport();
				}
				catch (Exception exception)
				{
					printDebug(exception);
				}
			}
		}
		
		public TimeOutWing(String string)
		{
			wingName = string;
		}
	}
	
	public class BackgroundLoader extends BackgroundTask
	{
		private String _name;
		private SectFile _in;
		
		public void run() throws Exception
		{
			Mission.this._load(_name, _in, true);
		}
		
		public BackgroundLoader(String string, SectFile sectfile)
		{
			_name = string;
			_in = sectfile;
		}
	}
	
	public static float respawnTime(String string)
	{
		Object object = respawnMap.get(string);
		if (object == null)
			return 1800.0F;
		return ((Float)object).floatValue();
	}
	
	public static boolean isPlaying()
	{
		if (Main.cur() == null)
			return false;
		if (Main.cur().mission == null)
			return false;
		if (Main.cur().mission.isDestroyed())
			return false;
		return Main.cur().mission.bPlaying;
	}
	
	public static boolean isSingle()
	{
		if (Main.cur().mission == null)
			return false;
		if (Main.cur().mission.isDestroyed())
			return false;
		if (Main.cur().mission.net == null)
			return true;
		if (Main.cur().netServerParams == null)
			return true;
		return Main.cur().netServerParams.isSingle();
	}
	
	public static boolean isNet()
	{
		if (Main.cur().mission == null)
			return false;
		if (Main.cur().mission.isDestroyed())
			return false;
		if (Main.cur().mission.net == null)
			return false;
		if (Main.cur().netServerParams == null)
			return false;
		return !Main.cur().netServerParams.isSingle();
	}
	
	public NetChannel getNetMasterChannel()
	{
		if (net == null)
			return null;
		return net.masterChannel();
	}
	
	public static boolean isServer()
	{
		return NetEnv.isServer();
	}
	
	public static boolean isDeathmatch()
	{
		return isDogfight();
	}
	
	public static boolean isDogfight()
	{
		if (Main.cur().mission == null)
			return false;
		if (Main.cur().mission.isDestroyed())
			return false;
		if (Main.cur().mission.net == null)
			return false;
		if (Main.cur().netServerParams == null)
			return false;
		return Main.cur().netServerParams.isDogfight();
	}
	
	public static boolean isCoop()
	{
		if (Main.cur().mission == null)
			return false;
		if (Main.cur().mission.isDestroyed())
			return false;
		if (Main.cur().mission.net == null)
			return false;
		if (Main.cur().netServerParams == null)
			return false;
		return Main.cur().netServerParams.isCoop();
	}
	
	public static int curCloudsType()
	{
		if (Main.cur().mission == null)
			return 0;
		return Main.cur().mission.curCloudsType;
	}
	
	public static float curCloudsHeight()
	{
		if (Main.cur().mission == null)
			return 1000.0F;
		return Main.cur().mission.curCloudsHeight;
	}
	
	public static Mission cur()
	{
		return Main.cur().mission;
	}
	
	public static void BreakP()
	{
		System.out.print("");
	}
	
	public static void load(String string) throws Exception
	{
		load(string, false);
	}
	
	public static void load(String string, boolean bool) throws Exception
	{
		load("missions/", string, bool);
	}
	
	public static void load(String string, String string_4_) throws Exception
	{
		load(string, string_4_, false);
	}
	
	public static void load(String string, String string_5_, boolean bool) throws Exception
	{
		Mission mission = new Mission();
		if (cur() != null)
			cur().destroy();
		else
			mission.clear();
		mission.sectFile = new SectFile(string + string_5_);
		mission.load(string_5_, mission.sectFile, bool);
	}
	
	public static void loadFromSect(SectFile sectfile) throws Exception
	{
		loadFromSect(sectfile, false);
	}
	
	public static void loadFromSect(SectFile sectfile, boolean bool) throws Exception
	{
		Mission mission = new Mission();
		String string = sectfile.fileName();
		if (string != null && string.toLowerCase().startsWith("missions/"))
			string = string.substring("missions/".length());
		if (cur() != null)
			cur().destroy();
		else
			mission.clear();
		mission.sectFile = sectfile;
		mission.load(string, mission.sectFile, bool);
	}
	
	public String name()
	{
		return name;
	}
	
	public SectFile sectFile()
	{
		return sectFile;
	}
	
	public long finger()
	{
		return sectFinger;
	}
	
	public boolean isDestroyed()
	{
		return name == null;
	}
	
	public void destroy()
	{
		if (!isDestroyed())
		{
			// TODO: Added by |ZUTI|
			// -----------------------------------
			MDS_VARIABLES().resetVariables();
			// -----------------------------------
			
			if (bPlaying)
				doEnd();
			bPlaying = false;
			clear();
			name = null;
			Main.cur().mission = null;
			if (Main.cur().netMissionListener != null)
				Main.cur().netMissionListener.netMissionState(8, 0.0F, null);
			if (net != null && !net.isDestroyed())
				net.destroy();
			net = null;
		}
	}
	
	private void clear()
	{
		if (net != null)
		{
			doReplicateNotMissionActors(false);
			if (net.masterChannel() != null)
				doReplicateNotMissionActors(net.masterChannel(), false);
		}
		actors.clear();
		beaconsRed.clear();
		beaconsBlue.clear();
		meaconsRed.clear();
		meaconsBlue.clear();
		hayrakeMap.clear();
		curActor = 0;
		Main.cur().resetGame();
		if (GUI.pad != null)
			GUI.pad.zutiPadObjects.clear();
	}
	
	private void load(String string, SectFile sectfile, boolean bool) throws Exception
	{
		if (bool)
		{
			BackgroundTask.execute(new BackgroundLoader(string, sectfile));
		}
		else
		{
			_load(string, sectfile, bool);
		}
	}
	
	private void LOADING_STEP(float f, String string)
	{
		if (net != null && Main.cur().netMissionListener != null)
			Main.cur().netMissionListener.netMissionState(3, f, string);
		if (!BackgroundTask.step(f, string))
			throw new RuntimeException(BackgroundTask.executed().messageCancel());
	}
	
	private void _load(String string, SectFile sectfile, boolean bool) throws Exception
	{
		AudioDevice.soundsOff();
		if (string != null)
			System.out.println("Loading mission " + string + "...");
		else
			System.out.println("Loading mission ...");
		
		// TODO: Added by |ZUTI|
		// -----------------------------------
		MDS_VARIABLES().resetVariables();
		// -----------------------------------
		
		EventLog.checkState();
		Main.cur().missionLoading = this;
		RTSConf.cur.time.setEnableChangePause1(false);
		Actor.setSpawnFromMission(true);
		try
		{
			Main.cur().mission = this;
			name = string;
			if (net == null)
				createNetObject(null, 0);
			
			loadMain(sectfile);
			
			try
			{
				loadRespawnTime(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				Front.loadMission(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			List list = null;
			// TODO: Edited by |ZUTI|: enables AI objects on the map
			// if (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isSingle())
			if (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isDogfight() || Main.cur().netServerParams.isSingle())
			{
				// load AI planes
				try
				{
					list = loadWings(sectfile);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				
				// loads ships/cars/trains
				try
				{
					loadChiefs(sectfile);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
			try
			{
				loadHouses(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				loadNStationary(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				loadStationary(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				loadRocketry(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				loadViewPoint(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			// Load born places for all game types, just in case users will be using radars... :) BPs are only rendered for DF missions anyhow.
			// if (Main.cur().netServerParams.isDogfight())
			// {
			try
			{
				loadBornPlaces(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
            {
                populateBeacons();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            try
            {
                populateRunwayLights();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
			// }
			// if (Main.cur().netServerParams.isMaster() && (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isDogfight()))
			if (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isDogfight())
			{
				try
				{
					loadTargets(sectfile);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (list != null)
			{
				int i = list.size();
				for (int i_6_ = 0; i_6_ < i; i_6_++)
				{
					Wing wing = (Wing)list.get(i_6_);
					// Perhaps wings are no more, catch those exceptions!
					try
					{
						if (Actor.isValid(wing))
							wing.setOnAirport();
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			
			if (net != null && Main.cur().netMissionListener != null)
				Main.cur().netMissionListener.netMissionState(4, 0.0F, exception.getMessage());
			clear();
			if (net != null && !net.isDestroyed())
				net.destroy();
			net = null;
			Main.cur().mission = null;
			name = null;
			Actor.setSpawnFromMission(false);
			Main.cur().missionLoading = null;
			setTime(false);
			throw exception;
		}
		if (Config.isUSE_RENDER())
		{
			if (Actor.isValid(World.getPlayerAircraft()))
				World.land().cubeFullUpdate((float)World.getPlayerAircraft().pos.getAbsPoint().z);
			else
				World.land().cubeFullUpdate(1000.0F);
			GUI.pad.fillAirports();
		}
		Actor.setSpawnFromMission(false);
		Main.cur().missionLoading = null;
		Main.cur().missionCounter++;
		setTime(!Main.cur().netServerParams.isSingle());
		LOADING_STEP(90.0F, "task.Load_humans");
		Paratrooper.PRELOAD();
		LOADING_STEP(95.0F, "task.Load_humans");
		// TODO: Edited by |ZUTI|
		// if (Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isCoop())
		if (Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isDogfight())
		{
			try
			{
				Soldier.PRELOAD();
			}
			catch (Exception ex)
			{
				System.out.println("Mission error, ID_09: " + ex.toString());
			}
		}
		LOADING_STEP(100.0F, "");
		if (Main.cur().netMissionListener != null)
			Main.cur().netMissionListener.netMissionState(5, 0.0F, null);
		if (net.isMirror())
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(4);
				net.masterChannel().userState = 4;
				net.postTo(net.masterChannel(), netmsgguaranted);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
			((NetUser)NetEnv.host()).missionLoaded();
		}
		else if (Main.cur().netServerParams.isSingle())
		{
			if (Main.cur() instanceof Main3D)
				((Main3D)Main.cur()).ordersTree.missionLoaded();
			Main.cur().dotRangeFriendly.setDefault();
			Main.cur().dotRangeFoe.setDefault();
			Main.cur().dotRangeFoe.set(-1.0, -1.0, -1.0, 5.0, -1.0, -1.0);
		}
		else
			((NetUser)NetEnv.host()).replicateDotRange();
		
		NetObj.tryReplicate(net, false);
		
		// TODO: Added by |ZUTI|
		// -----------------------------------------------------
		// Assign actors to markers
		try
		{
			ZutiSupportMethods.zutiAssignMarkersToActors();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		// Original line
		War.cur().missionLoaded();
		
		// Reset player army
		try
		{
			((NetUser)NetEnv.host()).setArmy(0);
		}
		catch (Exception ex)
		{}
		
		// After both, mission and map files were read, load also born places and add them to friction plates, if they are set up like that
		ZutiSupportMethods.zutiAddHomeBasesAsFrictionDictators();
		
		// Load RRR objects to all defined airfields.
		if (World.cur().houseManager != null)
			ZutiSupportMethods.appendRRRObjectsToAirfields();
		
		// Save this mission as current mission even for SingleMission type. MDS needs it in order to work there too ;)
		if (bool)
			Main.cur().mission = this;
		
		// Set radars mission to this
		ZutiRadarRefresh.setCurrentMission(this);
		
		// Load resources
		ZutiMDSVariables.loadResources(sectfile, true);
		// -----------------------------------------------------
	}
	
	private void setTime(boolean bool)
	{
		if (RTSConf.cur.time != null)
		{
			/* empty */
		}
		Time.setSpeed(1.0F);
		if (RTSConf.cur.time != null)
		{
			/* empty */
		}
		Time.setSpeedReal(1.0F);
		if (bool)
		{
			RTSConf.cur.time.setEnableChangePause1(false);
			RTSConf.cur.time.setEnableChangeSpeed(false);
			RTSConf.cur.time.setEnableChangeTickLen(true);
		}
		else
		{
			RTSConf.cur.time.setEnableChangePause1(true);
			RTSConf.cur.time.setEnableChangeSpeed(true);
			RTSConf.cur.time.setEnableChangeTickLen(false);
		}
	}
	
	private void loadMain(SectFile sectfile) throws Exception
	{
		int i = sectfile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
		World.cur().setTimeOfDayConstant(i == 1);
		World.setTimeofDay(sectfile.get("MAIN", "TIME", 12.0F, 0.0F, 23.99F));
		
		// TODO: Added by |ZUTI|
		// ----------------------------------------------------------------------------------------
		try
		{
			MDS_VARIABLES().loadVariables(sectfile);
			// Load additional airfields/rearm places that mission maker set up
			System.out.println("Loading mission.mis defined airfields:");
			if (ZutiSupportMethods_Engine.AIRFIELDS != null)
				ZutiSupportMethods_Engine.AIRFIELDS.clear();
			else
				ZutiSupportMethods_Engine.AIRFIELDS = new ArrayList();
			
			int s = sectfile.sectionIndex("AlternativeAirfield");
			if (s > 0)
			{
				int j = sectfile.vars(s);
				for (int k = 0; k < j; k++)
				{
					try
					{
						String line = sectfile.line(s, k);
						
						if (line.length() <= 0)
							continue;
						
						ZutiSupportMethods_Engine.addAirfieldPoint_MisIni(line);
					}
					catch (Exception ex)
					{}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		// ----------------------------------------------------------------------------------------
		
		int i_7_ = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
		World.cur().setWeaponsConstant(i_7_ == 1);
		
		bigShipHpDiv = 1.0F / sectfile.get("MAIN", "ShipHP", 1.0F, 0.001F, 100F);
		
		String string = sectfile.get("MAIN", "MAP");
		if (string == null)
			throw new Exception("No MAP in mission file ");
		String string_8_ = null;
		int[] is = null;
		SectFile sectfile_9_ = new SectFile("maps/" + string);
		int i_10_ = sectfile_9_.sectionIndex("static");
		if (i_10_ >= 0 && sectfile_9_.vars(i_10_) > 0)
		{
			string_8_ = sectfile_9_.var(i_10_, 0);
			if (string_8_ == null || string_8_.length() <= 0)
				string_8_ = null;
			else
			{
				string_8_ = HomePath.concatNames("maps/" + string, string_8_);
				is = Statics.readBridgesEndPoints(string_8_);
			}
		}
		
		LOADING_STEP(0.0F, "task.Load_landscape");
		
		int l = sectfile.get("SEASON", "Year", 1940, 1930, 1960);
		int i1 = sectfile.get("SEASON", "Month", com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + string), 1, 12);
		int j1 = sectfile.get("SEASON", "Day", 15, 1, 31);
		com.maddox.il2.game.Mission.setDate(l, i1, j1);
		
		World.land().LoadMap(string, is);
		
		//TODO: Disabled as this is loaded elsewhere!
		//---------------------------------------------------------
		World.cur().setCamouflage(World.land().config.camouflage);
		//---------------------------------------------------------
		
		if (Config.isUSE_RENDER())
		{
			if (Main3D.cur3D().land2D != null)
			{
				if (!Main3D.cur3D().land2D.isDestroyed())
					Main3D.cur3D().land2D.destroy();
				Main3D.cur3D().land2D = null;
			}
			int i_11_ = sectfile_9_.sectionIndex("MAP2D");
			if (i_11_ >= 0)
			{
				int i_12_ = sectfile_9_.vars(i_11_);
				if (i_12_ > 0)
				{
					LOADING_STEP(20.0F, "task.Load_map");
					Main3D.cur3D().land2D = new Land2Dn(string, (double)World.land().getSizeX(), (double)World.land().getSizeY());
				}
			}
			if (Main3D.cur3D().land2DText == null)
				Main3D.cur3D().land2DText = new Land2DText();
			else
				Main3D.cur3D().land2DText.clear();
			int i_13_ = sectfile_9_.sectionIndex("text");
			if (i_13_ >= 0 && sectfile_9_.vars(i_13_) > 0)
			{
				LOADING_STEP(22.0F, "task.Load_landscape_texts");
				String string_14_ = sectfile_9_.var(i_13_, 0);
				Main3D.cur3D().land2DText.load(HomePath.concatNames("maps/" + string, string_14_));
			}
		}
		if (string_8_ != null)
		{
			LOADING_STEP(23.0F, "task.Load_static_objects");
			Statics.load(string_8_, World.cur().statics.bridges);
			Engine.drawEnv().staticTrimToSize();
		}
		Statics.trim();
		// TODO: Edited by |ZUTI|
		// if (Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isCoop())
		if (Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isDogfight())
		{
			try
			{
				World.cur().statics.loadStateBridges(sectfile, false);
				World.cur().statics.loadStateHouses(sectfile, false);
				World.cur().statics.loadStateBridges(sectfile, true);
				World.cur().statics.loadStateHouses(sectfile, true);
				checkBridgesAndHouses(sectfile);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if (Main.cur().netServerParams.isSingle())
		{
			player = sectfile.get("MAIN", "player");
			playerNum = sectfile.get("MAIN", "playerNum", 0, 0, 3);
		}
		else
			player = null;
		World.setMissionArmy(sectfile.get("MAIN", "army", 1, 1, 2));
		if (Config.isUSE_RENDER())
			Main3D.cur3D().ordersTree.setFrequency(new Boolean(true));
		if (Config.isUSE_RENDER())
		{
			LOADING_STEP(29.0F, "task.Load_landscape_effects");
			Main3D main3d = Main3D.cur3D();
			int i_15_ = sectfile.get("MAIN", "CloudType", 0, 0, 6);
			float f = sectfile.get("MAIN", "CloudHeight", 1000.0F, 300.0F, 5000.0F);
			createClouds(i_15_, f);
			if (!Config.cur.ini.get("game", "NoLensFlare", false))
			{
				main3d.sunFlareCreate();
				main3d.sunFlareShow(true);
			}
			else
				main3d.sunFlareShow(false);
			
			float f1 = (float)(string.charAt(0) - 64) * 3.141593F;
			f1 = sectfile.get("WEATHER", "WindDirection", f1, 0.0F, 359.99F);
			float f2 = 0.25F + (float)(i_15_ * i_15_) * 0.12F;
			f2 = sectfile.get("WEATHER", "WindSpeed", f2, 0.0F, 15F);
			float f3 = i_15_ > 3 ? (float)i_15_ * 2.0F : 0.0F;
			f3 = sectfile.get("WEATHER", "Gust", f3, 0.0F, 12F);
			float f4 = i_15_ > 2 ? i_15_ : 0.0F;
			f4 = sectfile.get("WEATHER", "Turbulence", f4, 0.0F, 6F);
			
			com.maddox.il2.ai.World.wind().set(f, f1, f2, f3, f4);
			for (int i_16_ = 0; i_16_ < 3; i_16_++)
			{
				Main3D.cur3D()._lightsGlare[i_16_].setShow(true);
				Main3D.cur3D()._sunGlare[i_16_].setShow(true);
			}
		}
	}
	
	public static void setDate(int i, int j, int k)
	{
		com.maddox.il2.game.Mission.setYear(i);
		com.maddox.il2.game.Mission.setMonth(j);
		com.maddox.il2.game.Mission.setDay(k);
	}
	
	public static void setYear(int i)
	{
		if (i < 1930)
			i = 1930;
		if (i > 1960)
			i = 1960;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curYear = i;
	}
	
	public static void setMonth(int i)
	{
		if (i < 1)
			i = 1;
		if (i > 12)
			i = 12;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curMonth = i;
	}
	
	public static void setDay(int i)
	{
		if (i < 1)
			i = 1;
		if (i > 31)
			i = 31;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curDay = i;
	}
	
	public static void setWindDirection(float f)
	{
		if (f < 0.0F)
			f = 0.0F;
		if (f > 359.99F)
			f = 0.0F;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curWindDirection = f;
	}
	
	public static void setWindVelocity(float f)
	{
		if (f < 0.0F)
			f = 0.0F;
		if (f > 15F)
			f = 15F;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curWindVelocity = f;
	}
	
	public static void setGust(float f)
	{
		if (f < 0.0F)
			f = 0.0F;
		if (f > 12F)
			f = 12F;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curGust = f;
	}
	
	public static void setTurbulence(float f)
	{
		if (f < 0.0F)
			f = 0.0F;
		if (f > 6F)
			f = 6F;
		if (com.maddox.il2.game.Mission.cur() != null)
			com.maddox.il2.game.Mission.cur().curTurbulence = f;
	}
	
	public static void initRadioSounds()
	{
		if (radioStationsLoaded)
			return;
		
		com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
		if (aircraft != null)
		{
			java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().mission.getBeacons(aircraft.FM.actor.getArmy());
			if (arraylist != null)
			{
				for (int i = 0; i < arraylist.size(); i++)
				{
					
					com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(i);
					if (actor instanceof com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation)
					{
						hasRadioStations = true;
						aircraft.FM.AS.preLoadRadioStation(actor);
						CmdMusic.setVolume(0.001F);
						radioStationsLoaded = true;
					}
				}
				
			}
		}
	}
	
	public static void createClouds(int i, float f)
	{
		if (i < 0)
			i = 0;
		if (i > 6)
			i = 6;
		if (cur() != null)
		{
			cur().curCloudsType = i;
			cur().curCloudsHeight = f;
		}
		if (Config.isUSE_RENDER())
		{
			Main3D main3d = Main3D.cur3D();
			Camera3D camera3d = (Camera3D)Actor.getByName("camera");
			if (main3d.clouds != null)
				main3d.clouds.destroy();
			main3d.clouds = new EffClouds(World.cur().diffCur.NewCloudsRender, i, f);
			if (i > 5)
			{
				try
				{
					if (main3d.zip != null)
						main3d.zip.destroy();
					main3d.zip = new Zip(f);
				}
				catch (Exception exception)
				{
					System.out.println("Zip load error: " + exception);
				}
			}
			int i_17_ = 5 - i;
			if (i == 6)
				i_17_ = 1;
			if (i_17_ > 4)
				i_17_ = 4;
			RenderContext.cfgLandFogHaze.set(i_17_);
			RenderContext.cfgLandFogHaze.apply();
			RenderContext.cfgLandFogHaze.reset();
			RenderContext.cfgLandFogLow.set(0);
			RenderContext.cfgLandFogLow.apply();
			RenderContext.cfgLandFogLow.reset();
			if (Actor.isValid(main3d.spritesFog))
				main3d.spritesFog.destroy();
			main3d.spritesFog = new SpritesFog(camera3d, 0.7F, 7000.0F, 7500.0F);
		}
	}
	
	public static void setCloudsType(int i)
	{
		if (i < 0)
			i = 0;
		if (i > 6)
			i = 6;
		if (cur() != null)
			cur().curCloudsType = i;
		if (Config.isUSE_RENDER())
		{
			Main3D main3d = Main3D.cur3D();
			if (main3d.clouds != null)
				main3d.clouds.setType(i);
			int i_18_ = 5 - i;
			if (i == 6)
				i_18_ = 1;
			if (i_18_ > 4)
				i_18_ = 4;
			RenderContext.cfgLandFogHaze.set(i_18_);
			RenderContext.cfgLandFogHaze.apply();
			RenderContext.cfgLandFogHaze.reset();
			RenderContext.cfgLandFogLow.set(0);
			RenderContext.cfgLandFogLow.apply();
			RenderContext.cfgLandFogLow.reset();
		}
	}
	
	public static void setCloudsHeight(float f)
	{
		if (cur() != null)
			cur().curCloudsHeight = f;
		if (Config.isUSE_RENDER())
		{
			Main3D main3d = Main3D.cur3D();
			if (main3d.clouds != null)
				main3d.clouds.setHeight(f);
		}
	}
	
	private void loadRespawnTime(SectFile sectfile)
	{
		respawnMap.clear();
		int i = sectfile.sectionIndex("RespawnTime");
		if (i >= 0)
		{
			int i_19_ = sectfile.vars(i);
			for (int i_20_ = 0; i_20_ < i_19_; i_20_++)
			{
				String string = sectfile.var(i, i_20_);
				float f = sectfile.get("RespawnTime", string, 1800.0F, 20.0F, 1200000.0F);
				respawnMap.put(string, new Float(f));
			}
		}
	}
	
	private List loadWings(SectFile sectfile) throws Exception
	{
		int i = sectfile.sectionIndex("Wing");
		if (i < 0)
			return null;
		
		if (!World.cur().diffCur.Takeoff_N_Landing)
			prepareTakeoff(sectfile, !Main.cur().netServerParams.isSingle());
		
		NetChannel netchannel = null;
		
		if (!isServer())
			netchannel = net.masterChannel();
		
		int i_21_ = sectfile.vars(i);
		ArrayList arraylist = null;
		if (i_21_ > 0)
			arraylist = new ArrayList(i_21_);
		
		for (int i_22_ = 0; i_22_ < i_21_; i_22_++)
		{
			LOADING_STEP((float)(30 + Math.round((float)i_22_ / (float)i_21_ * 30.0F)), "task.Load_aircraft");
			String string = sectfile.var(i, i_22_);
			_loadPlayer = string.equals(player);
			int i_23_ = sectfile.get(string, "StartTime", 0);
			if (i_23_ > 0 && !_loadPlayer)
			{
				if (netchannel == null)
				{
					double d = (double)((long)i_23_ * 60L);
					new MsgAction(0, d, new TimeOutWing(string))
					{
						public void doAction(Object object)
						{
							TimeOutWing timeoutwing = (TimeOutWing)object;
							timeoutwing.start();
						}
					};
				}
			}
			else
			{
				NetAircraft.loadingCoopPlane = (Main.cur().netServerParams != null && Main.cur().netServerParams.isCoop());
				
				Wing wing = new Wing();
				wing.load(sectfile, string, netchannel);
				
				if (netchannel != null && !Main.cur().netServerParams.isCoop())
				{
					Aircraft[] aircrafts = wing.airc;
					for (int i_27_ = 0; i_27_ < aircrafts.length; i_27_++)
					{
						if (Actor.isValid(aircrafts[i_27_]) && aircrafts[i_27_].net == null)
						{
							aircrafts[i_27_].destroy();
							aircrafts[i_27_] = null;
						}
					}
				}
				arraylist.add(wing);
				prepareSkinInWing(sectfile, wing);
			}
		}
		LOADING_STEP(60.0F, "task.Load_aircraft");
		return arraylist;
	}
	
	private void prepareSkinInWing(SectFile sectfile, Wing wing)
	{
		if (Config.isUSE_RENDER())
		{
			Aircraft[] aircrafts = wing.airc;
			for (int i = 0; i < aircrafts.length; i++)
			{
				if (Actor.isValid(aircrafts[i]))
				{
					Aircraft aircraft = aircrafts[i];
					prepareSkinInWing(sectfile, aircraft, wing.name(), i);
				}
			}
		}
	}
	
	private void prepareSkinInWing(SectFile sectfile, Aircraft aircraft, String string, int i)
	{
		if (Config.isUSE_RENDER())
		{
			if (World.getPlayerAircraft() == aircraft)
			{
				if (isSingle())
				{
					if (NetMissionTrack.isPlaying())
					{
						((NetUser)Main.cur().netServerParams.host()).tryPrepareSkin(aircraft);
						((NetUser)Main.cur().netServerParams.host()).tryPrepareNoseart(aircraft);
						((NetUser)Main.cur().netServerParams.host()).tryPreparePilot(aircraft);
					}
					else
					{
						String string_28_ = Property.stringValue(aircraft.getClass(), "keyName", null);
						String string_29_ = World.cur().userCfg.getSkin(string_28_);
						if (string_29_ != null)
						{
							String string_30_ = GUIAirArming.validateFileName(string_28_);
							((NetUser)NetEnv.host()).setSkin(string_30_ + "/" + string_29_);
							((NetUser)NetEnv.host()).tryPrepareSkin(aircraft);
						}
						else
							((NetUser)NetEnv.host()).setSkin(null);
						String string_31_ = World.cur().userCfg.getNoseart(string_28_);
						if (string_31_ != null)
						{
							((NetUser)NetEnv.host()).setNoseart(string_31_);
							((NetUser)NetEnv.host()).tryPrepareNoseart(aircraft);
						}
						else
							((NetUser)NetEnv.host()).setNoseart(null);
						String string_32_ = World.cur().userCfg.netPilot;
						((NetUser)NetEnv.host()).setPilot(string_32_);
						if (string_32_ != null)
							((NetUser)NetEnv.host()).tryPreparePilot(aircraft);
					}
				}
			}
			else
			{
				String string_33_ = sectfile.get(string, "skin" + i, (String)null);
				if (string_33_ != null)
				{
					String string_34_ = Aircraft.getPropertyMesh(aircraft.getClass(), aircraft.getRegiment().country());
					string_33_ = ((GUIAirArming.validateFileName(Property.stringValue(aircraft.getClass(), "keyName", null))) + "/" + string_33_);
					if (NetMissionTrack.isPlaying())
					{
						string_33_ = (NetFilesTrack.getLocalFileName(Main.cur().netFileServerSkin, Main.cur().netServerParams.host(), string_33_));
						if (string_33_ != null)
							string_33_ = Main.cur().netFileServerSkin.alternativePath() + "/" + string_33_;
					}
					else
						string_33_ = (Main.cur().netFileServerSkin.primaryPath() + "/" + string_33_);
					if (string_33_ != null)
					{
						String string_35_ = ("PaintSchemes/Cache/" + Finger.file(0L, string_33_, -1));
						Aircraft.prepareMeshSkin(string_34_, aircraft.hierMesh(), string_33_, string_35_);
					}
				}
				String string_36_ = sectfile.get(string, "noseart" + i, (String)null);
				if (string_36_ != null)
				{
					String string_37_ = (Main.cur().netFileServerNoseart.primaryPath() + "/" + string_36_);
					String string_38_ = string_36_.substring(0, string_36_.length() - 4);
					if (NetMissionTrack.isPlaying())
					{
						string_37_ = (NetFilesTrack.getLocalFileName(Main.cur().netFileServerNoseart, Main.cur().netServerParams.host(), string_36_));
						if (string_37_ != null)
						{
							string_38_ = string_37_.substring(0, string_37_.length() - 4);
							string_37_ = Main.cur().netFileServerNoseart.alternativePath() + "/" + string_37_;
						}
					}
					if (string_37_ != null)
					{
						String string_39_ = ("PaintSchemes/Cache/Noseart0" + string_38_ + ".tga");
						String string_40_ = ("PaintSchemes/Cache/Noseart0" + string_38_ + ".mat");
						String string_41_ = ("PaintSchemes/Cache/Noseart1" + string_38_ + ".tga");
						String string_42_ = ("PaintSchemes/Cache/Noseart1" + string_38_ + ".mat");
						if (BmpUtils.bmp8PalTo2TGA4(string_37_, string_39_, string_41_))
							Aircraft.prepareMeshNoseart(aircraft.hierMesh(), string_40_, string_42_, string_39_, string_41_, null);
					}
				}
				String string_43_ = sectfile.get(string, "pilot" + i, (String)null);
				if (string_43_ != null)
				{
					String string_44_ = (Main.cur().netFileServerPilot.primaryPath() + "/" + string_43_);
					String string_45_ = string_43_.substring(0, string_43_.length() - 4);
					if (NetMissionTrack.isPlaying())
					{
						string_44_ = (NetFilesTrack.getLocalFileName(Main.cur().netFileServerPilot, Main.cur().netServerParams.host(), string_43_));
						if (string_44_ != null)
						{
							string_45_ = string_44_.substring(0, string_44_.length() - 4);
							string_44_ = Main.cur().netFileServerPilot.alternativePath() + "/" + string_44_;
						}
					}
					if (string_44_ != null)
					{
						String string_46_ = "PaintSchemes/Cache/Pilot" + string_45_ + ".tga";
						String string_47_ = "PaintSchemes/Cache/Pilot" + string_45_ + ".mat";
						if (BmpUtils.bmp8PalToTGA3(string_44_, string_46_))
							Aircraft.prepareMeshPilot(aircraft.hierMesh(), 0, string_47_, string_46_, null);
					}
				}
			}
		}
	}
	
	public void prepareSkinAI(Aircraft aircraft)
	{
		String string = aircraft.name();
		if (string.length() >= 4)
		{
			String string_48_ = string.substring(0, string.length() - 1);
			int i;
			try
			{
				i = Integer.parseInt(string.substring(string.length() - 1, string.length()));
			}
			catch (Exception exception)
			{
				return;
			}
			prepareSkinInWing(sectFile, aircraft, string_48_, i);
		}
	}
	
	public void recordNetFiles()
	{
		if (!isDogfight())
		{
			int i = sectFile.sectionIndex("Wing");
			if (i >= 0)
			{
				int i_49_ = sectFile.vars(i);
				for (int i_50_ = 0; i_50_ < i_49_; i_50_++)
				{
					try
					{
						String string = sectFile.var(i, i_50_);
						String string_51_ = sectFile.get(string, "Class", (String)null);
						Class var_class = ObjIO.classForName(string_51_);
						String string_52_ = (GUIAirArming.validateFileName(Property.stringValue(var_class, "keyName", null)));
						for (int i_53_ = 0; i_53_ < 4; i_53_++)
						{
							String string_54_ = sectFile.get(string, "skin" + i_53_, (String)null);
							if (string_54_ != null)
								recordNetFile(Main.cur().netFileServerSkin, string_52_ + "/" + string_54_);
							recordNetFile(Main.cur().netFileServerNoseart, sectFile.get(string, "noseart" + i_53_, (String)null));
							recordNetFile(Main.cur().netFileServerPilot, sectFile.get(string, "pilot" + i_53_, (String)null));
						}
					}
					catch (Exception exception)
					{
						exception.printStackTrace();
					}
				}
			}
		}
	}
	
	private void recordNetFile(NetFileServerDef netfileserverdef, String string)
	{
		if (string != null)
		{
			String string_55_ = string;
			if (NetMissionTrack.isPlaying())
			{
				string_55_ = NetFilesTrack.getLocalFileName(netfileserverdef, Main.cur().netServerParams.host(), string);
				if (string_55_ == null)
					return;
			}
			NetFilesTrack.recordFile(netfileserverdef, ((NetUser)Main.cur().netServerParams.host()), string, string_55_);
		}
	}
	
	public Aircraft loadAir(SectFile sectfile, String string, String string_56_, String string_57_, int i) throws Exception
	{
		boolean bool = !isServer();
		Class var_class = ObjIO.classForName(string);
		Aircraft aircraft = (Aircraft)var_class.newInstance();
		if (Main.cur().netServerParams.isSingle() && _loadPlayer)
		{
			if (Property.value(var_class, "cockpitClass", null) == null)
				throw new Exception("One of selected aircraft has no cockpit.");
			if (playerNum == 0)
			{
				World.setPlayerAircraft(aircraft);
				_loadPlayer = false;
			}
			else
				playerNum--;
		}
		aircraft.setName(string_57_);
		int i_58_ = 0;
		if (bool)
		{
			i_58_ = ((Integer)actors.get(curActor)).intValue();
			if (i_58_ == 0)
				aircraft.load(sectfile, string_56_, i, null, 0);
			else
				aircraft.load(sectfile, string_56_, i, net.masterChannel(), i_58_);
		}
		else
			aircraft.load(sectfile, string_56_, i, null, 0);
		if (aircraft.isSpawnFromMission())
		{
			if (net.isMirror())
			{
				if (i_58_ == 0)
					actors.set(curActor++, null);
				else
					actors.set(curActor++, aircraft);
			}
			else
				actors.add(aircraft);
		}
		aircraft.pos.reset();
		return aircraft;
	}
	
	public static void preparePlayerNumberOn(SectFile sectfile)
	{
		UserCfg usercfg = World.cur().userCfg;
		String string = sectfile.get("MAIN", "player", "");
		if (!"".equals(string))
		{
			String string_59_ = sectfile.get("MAIN", "playerNum", "");
			sectfile.set(string, "numberOn" + string_59_, usercfg.netNumberOn ? "1" : "0");
		}
	}
	
	private void prepareTakeoff(SectFile sectfile, boolean bool)
	{
		if (bool)
		{
			int i = sectfile.sectionIndex("Wing");
			if (i < 0)
				return;
			int i_60_ = sectfile.vars(i);
			for (int i_61_ = 0; i_61_ < i_60_; i_61_++)
				prepareWingTakeoff(sectfile, sectfile.var(i, i_61_));
		}
		else
		{
			String string = sectfile.get("MAIN", "player", (String)null);
			if (string == null)
				return;
			prepareWingTakeoff(sectfile, string);
		}
		sectFinger = sectfile.fingerExcludeSectPrefix("$$$");
	}
	
	private void prepareWingTakeoff(SectFile sectfile, String string)
	{
		String string_62_ = string + "_Way";
		int i = sectfile.sectionIndex(string_62_);
		if (i >= 0)
		{
			int i_63_ = sectfile.vars(i);
			if (i_63_ != 0)
			{
				ArrayList arraylist = new ArrayList(i_63_);
				for (int i_64_ = 0; i_64_ < i_63_; i_64_++)
					arraylist.add(sectfile.line(i, i_64_));
				String string_65_ = (String)arraylist.get(0);
				if (string_65_.startsWith("TAKEOFF"))
				{
					StringBuffer stringbuffer = new StringBuffer("NORMFLY");
					NumberTokenizer numbertokenizer = new NumberTokenizer(string_65_);
					numbertokenizer.next((String)null);
					double d = numbertokenizer.next(1000.0);
					double d_66_ = numbertokenizer.next(1000.0);
					WingTakeoffPos wingtakeoffpos = new WingTakeoffPos(d, d_66_);
					if (mapWingTakeoff == null)
					{
						mapWingTakeoff = new HashMap();
						mapWingTakeoff.put(wingtakeoffpos, wingtakeoffpos);
					}
					else
					{
						for (;;)
						{
							WingTakeoffPos wingtakeoffpos_67_ = ((WingTakeoffPos)mapWingTakeoff.get(wingtakeoffpos));
							if (wingtakeoffpos_67_ == null)
							{
								mapWingTakeoff.put(wingtakeoffpos, wingtakeoffpos);
								break;
							}
							wingtakeoffpos.x += 200;
						}
					}
					d = (double)wingtakeoffpos.x;
					d_66_ = (double)wingtakeoffpos.y;
					stringbuffer.append(" ");
					stringbuffer.append(d);
					stringbuffer.append(" ");
					stringbuffer.append(d_66_);
					do
					{
						if (i_63_ > 1)
						{
							String string_68_ = (String)arraylist.get(1);
							if (!string_68_.startsWith("TAKEOFF") && !string_68_.startsWith("LANDING"))
							{
								numbertokenizer = new NumberTokenizer(string_68_);
								numbertokenizer.next((String)null);
								numbertokenizer.next((String)null);
								numbertokenizer.next((String)null);
								stringbuffer.append(" ");
								stringbuffer.append(numbertokenizer.next("1000.0"));
								stringbuffer.append(" ");
								stringbuffer.append(numbertokenizer.next("300.0"));
								arraylist.set(0, stringbuffer.toString());
								break;
							}
						}
						stringbuffer.append(" 1000 300");
						arraylist.set(0, stringbuffer.toString());
					}
					while (false);
					sectfile.sectionClear(i);
					for (int i_69_ = 0; i_69_ < i_63_; i_69_++)
						sectfile.lineAdd(i, (String)arraylist.get(i_69_));
				}
			}
		}
	}
	
	private void loadChiefs(SectFile sectfile) throws Exception
	{
		int i = sectfile.sectionIndex("Chiefs");
		if (i >= 0)
		{
			if (chiefsIni == null)
				chiefsIni = new SectFile("com/maddox/il2/objects/chief.ini");
			int i_70_ = sectfile.vars(i);
			for (int i_71_ = 0; i_71_ < i_70_; i_71_++)
			{
				LOADING_STEP((float)(60 + Math.round((float)i_71_ / (float)i_70_ * 20.0F)), "task.Load_tanks");
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_71_));
				String string = numbertokenizer.next();
				String string_72_ = numbertokenizer.next();
				int i_73_ = numbertokenizer.next(-1);
				if (i_73_ < 0)
					System.out.println("Mission: Wrong chief's army [" + i_73_ + "]");
				else
				{
					Chief.new_DELAY_WAKEUP = numbertokenizer.next(0.0F);
					Chief.new_SKILL_IDX = numbertokenizer.next(2);
					if (Chief.new_SKILL_IDX < 0 || Chief.new_SKILL_IDX > 3)
						System.out.println("Mission: Wrong chief's skill [" + Chief.new_SKILL_IDX + "]");
					else
					{
						Chief.new_SLOWFIRE_K = numbertokenizer.next(1.0F);
						if (Chief.new_SLOWFIRE_K < 0.5F || Chief.new_SLOWFIRE_K > 100.0F)
							System.out.println("Mission: Wrong chief's slowfire [" + Chief.new_SLOWFIRE_K + "]");
						else if (chiefsIni.sectionIndex(string_72_) < 0)
							System.out.println("Mission: Wrong chief's type [" + string_72_ + "]");
						else
						{
							int i_74_ = string_72_.indexOf('.');
							if (i_74_ <= 0)
								System.out.println("Mission: Wrong chief's type [" + string_72_ + "]");
							else
							{
								String string_75_ = string_72_.substring(0, i_74_);
								String string_76_ = string_72_.substring(i_74_ + 1);
								String string_77_ = chiefsIni.get(string_75_, string_76_);
								if (string_77_ == null)
									System.out.println("Mission: Wrong chief's type [" + string_72_ + "]");
								else
								{
									numbertokenizer = new NumberTokenizer(string_77_);
									string_77_ = numbertokenizer.nextToken();
									numbertokenizer.nextToken();
									String string_78_ = null;
									if (numbertokenizer.hasMoreTokens())
										string_78_ = numbertokenizer.nextToken();
									Class var_class = ObjIO.classForName(string_77_);
									if (var_class == null)
										System.out.println("Mission: Unknown chief's class [" + string_77_ + "]");
									else
									{
										Constructor constructor;
										try
										{
											Class[] var_classes = new Class[6];
											
											var_classes[0] = ((class$java$lang$String == null) ? (class$java$lang$String = (class$ZutiClass("java.lang.String"))) : class$java$lang$String);
											var_classes[1] = Integer.TYPE;
											var_classes[2] = ((class$com$maddox$rts$SectFile == null) ? (class$com$maddox$rts$SectFile = (class$ZutiClass("com.maddox.rts.SectFile"))) : class$com$maddox$rts$SectFile);
											var_classes[3] = ((class$java$lang$String == null) ? (class$java$lang$String = (class$ZutiClass("java.lang.String"))) : class$java$lang$String);
											var_classes[4] = ((class$com$maddox$rts$SectFile == null) ? (class$com$maddox$rts$SectFile = (class$ZutiClass("com.maddox.rts.SectFile"))) : class$com$maddox$rts$SectFile);
											var_classes[5] = ((class$java$lang$String == null) ? (class$java$lang$String = (class$ZutiClass("java.lang.String"))) : class$java$lang$String);
											constructor = (var_class.getConstructor(var_classes));
										}
										catch (Exception exception)
										{
											System.out.println("Mission: No required constructor in chief's class [" + string_77_ + "]");
											continue;
										}
										int i_79_ = curActor;
										Object object;
										try
										{
											Object[] objects = new Object[6];
											objects[0] = string;
											objects[1] = new Integer(i_73_);
											objects[2] = chiefsIni;
											objects[3] = string_72_;
											objects[4] = sectfile;
											objects[5] = string + "_Road";
											object = constructor.newInstance(objects);
										}
										catch (Exception exception)
										{
											System.out.println("Mission: Can't create chief '" + string + "' [class:" + string_77_ + "]");
											continue;
										}
										if (string_78_ != null)
											((Actor)object).icon = IconDraw.get(string_78_);
										if (i_79_ != curActor && net != null && net.isMirror())
										{
											for (int i_80_ = i_79_; i_80_ < curActor; i_80_++)
											{
												Actor actor = ((Actor)actors.get(i_80_));
												if (actor.net == null || actor.net.isMaster())
												{
													if (Actor.isValid(actor))
													{
														if (object instanceof ChiefGround)
															((ChiefGround)object).Detach(actor, actor);
														actor.destroy();
													}
													actors.set(i_80_, null);
												}
											}
										}
										if (object instanceof ChiefGround)
											((ChiefGround)object).dreamFire(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int getUnitNetIdRemote(Actor actor)
	{
		if (net.isMaster())
		{
			actors.add(actor);
			return 0;
		}
		Integer integer = (Integer)actors.get(curActor);
		actors.set(curActor, actor);
		curActor++;
		return integer.intValue();
	}
	
	private Actor loadStationaryActor(String string, String string_81_, int i, double d, double d_82_, float f, float f_83_, String string_84_, String string_85_, String string_86_)
	{
		Class var_class;
		try
		{
			var_class = ObjIO.classForName(string_81_);
		}
		catch (Exception exception)
		{
			System.out.println("Mission: class '" + string_81_ + "' not found");
			return null;
		}
		ActorSpawn actorspawn = (ActorSpawn)Spawn.get(var_class.getName(), false);
		if (actorspawn == null)
		{
			System.out.println("Mission: ActorSpawn for '" + string_81_ + "' not found");
			return null;
		}
		spawnArg.clear();
		if (string != null)
		{
			if ("NONAME".equals(string))
			{
				System.out.println("Mission: 'NONAME' - not valid actor name");
				return null;
			}
			if (Actor.getByName(string) != null)
			{
				System.out.println("Mission: actor '" + string + "' alredy exist");
				return null;
			}
			spawnArg.name = string;
		}
		spawnArg.army = i;
		spawnArg.armyExist = true;
		spawnArg.country = string_84_;
		Chief.new_DELAY_WAKEUP = 0.0F;
		ArtilleryGeneric.new_RADIUS_HIDE = 0.0F;
		if (string_84_ != null)
		{
			try
			{
				Chief.new_DELAY_WAKEUP = (float)Integer.parseInt(string_84_);
				ArtilleryGeneric.new_RADIUS_HIDE = Chief.new_DELAY_WAKEUP;
			}
			catch (Exception exception)
			{
				/* empty */
			}
		}
		Chief.new_SKILL_IDX = 2;
		if (string_85_ != null)
		{
			try
			{
				Chief.new_SKILL_IDX = Integer.parseInt(string_85_);
			}
			catch (Exception exception)
			{
				/* empty */
			}
			if (Chief.new_SKILL_IDX < 0 || Chief.new_SKILL_IDX > 3)
			{
				System.out.println("Mission: Wrong actor skill '" + Chief.new_SKILL_IDX + "'");
				return null;
			}
		}
		Chief.new_SLOWFIRE_K = 1.0F;
		if (string_86_ != null)
		{
			try
			{
				Chief.new_SLOWFIRE_K = Float.parseFloat(string_86_);
			}
			catch (Exception exception)
			{
				/* empty */
			}
			if (Chief.new_SLOWFIRE_K < 0.5F || Chief.new_SLOWFIRE_K > 100.0F)
			{
				System.out.println("Mission: Wrong actor slowfire '" + Chief.new_SLOWFIRE_K + "'");
				return null;
			}
		}
		p.set(d, d_82_, 0.0);
		spawnArg.point = p;
		o.set(f, 0.0F, 0.0F);
		spawnArg.orient = o;
		if (f_83_ > 0.0F)
		{
			spawnArg.timeLenExist = true;
			spawnArg.timeLen = f_83_;
		}
		spawnArg.netChannel = null;
		spawnArg.netIdRemote = 0;
		if (net.isMirror())
		{
			spawnArg.netChannel = net.masterChannel();
			spawnArg.netIdRemote = ((Integer)actors.get(curActor)).intValue();
			if (spawnArg.netIdRemote == 0)
			{
				actors.set(curActor++, null);
				return null;
			}
		}
		Actor actor = null;
		try
		{
			actor = actorspawn.actorSpawn(spawnArg);
		}
		catch (Exception exception)
		{
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		if (net.isMirror())
			actors.set(curActor++, actor);
		else
			actors.add(actor);
		
		return actor;
	}
	
	private void loadStationary(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("Stationary");
		if (i >= 0)
		{
			int i_87_ = sectfile.vars(i);
			for (int i_88_ = 0; i_88_ < i_87_; i_88_++)
			{
				try
				{
					LOADING_STEP((float)(80 + Math.round((float)i_88_ / (float)i_87_ * 5.0F)), "task.Load_stationary_objects");
					NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_88_));
					loadStationaryActor(null, numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0), numbertokenizer.next(0.0), numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), numbertokenizer.next((String)null),
							numbertokenizer.next((String)null), numbertokenizer.next((String)null));
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					System.out.println("Error when reading .mis Stationary section line " + i_88_ + " (" + ex.toString() + " )");
				}
			}
		}
	}
	
	private void loadNStationary(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("NStationary");
		if (i >= 0)
		{
			int i_89_ = sectfile.vars(i);
			for (int i_90_ = 0; i_90_ < i_89_; i_90_++)
			{
				try
				{
					LOADING_STEP((float)(85 + Math.round((float)i_90_ / (float)i_89_ * 5.0F)), "task.Load_stationary_objects");
					NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_90_));
					loadStationaryActor(numbertokenizer.next(""), numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0), numbertokenizer.next(0.0), numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), numbertokenizer
							.next((String)null), numbertokenizer.next((String)null), numbertokenizer.next((String)null));
				}
				catch (Exception ex)
				{
					System.out.println("Error when reading .mis NStationary section line " + i_90_ + " (" + ex.toString() + " )");
				}
			}
		}
	}
	
	private void loadRocketry(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("Rocket");
		if (i >= 0)
		{
			int i_91_ = sectfile.vars(i);
			for (int i_92_ = 0; i_92_ < i_91_; i_92_++)
			{
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_92_));
				if (numbertokenizer.hasMoreTokens())
				{
					String string = numbertokenizer.next("");
					if (numbertokenizer.hasMoreTokens())
					{
						String string_93_ = numbertokenizer.next("");
						if (numbertokenizer.hasMoreTokens())
						{
							int i_94_ = numbertokenizer.next(1, 1, 2);
							double d = numbertokenizer.next(0.0);
							if (numbertokenizer.hasMoreTokens())
							{
								double d_95_ = numbertokenizer.next(0.0);
								if (numbertokenizer.hasMoreTokens())
								{
									float f = numbertokenizer.next(0.0F);
									if (numbertokenizer.hasMoreTokens())
									{
										float f_96_ = numbertokenizer.next(0.0F);
										int i_97_ = numbertokenizer.next(1);
										float f_98_ = numbertokenizer.next(20.0F);
										Point2d point2d = null;
										if (numbertokenizer.hasMoreTokens())
											point2d = new Point2d(numbertokenizer.next(0.0), numbertokenizer.next(0.0));
										NetChannel netchannel = null;
										int i_99_ = 0;
										if (net.isMirror())
										{
											netchannel = net.masterChannel();
											i_99_ = ((Integer)actors.get(curActor)).intValue();
											if (i_99_ == 0)
											{
												actors.set(curActor++, null);
												continue;
											}
										}
										RocketryGeneric rocketrygeneric = null;
										try
										{
											rocketrygeneric = (RocketryGeneric.New(string, string_93_, netchannel, i_99_, i_94_, d, d_95_, f, f_96_, i_97_, f_98_, point2d));
										}
										catch (Exception exception)
										{
											System.out.println(exception.getMessage());
											exception.printStackTrace();
										}
										if (net.isMirror())
											actors.set(curActor++, rocketrygeneric);
										else
											actors.add(rocketrygeneric);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void loadHouses(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("Buildings");
		if (i >= 0)
		{
			int i_100_ = sectfile.vars(i);
			if (i_100_ != 0)
			{
				if (net.isMirror())
				{
					spawnArg.netChannel = net.masterChannel();
					spawnArg.netIdRemote = ((Integer)actors.get(curActor)).intValue();
					HouseManager housemanager = new HouseManager(sectfile, "Buildings", net.masterChannel(), ((Integer)actors.get(curActor)).intValue());
					actors.set(curActor++, housemanager);
				}
				else
				{
					HouseManager housemanager = new HouseManager(sectfile, "Buildings", null, 0);
					actors.add(housemanager);
				}
			}
		}
	}
	
	private void loadBornPlaces(SectFile sectfile)
	{
		// TODO: Added by |ZUTI|: precaution, check for missing branches for existing regiments because
		// Home bases at first load all countries, if no country limitation is enabled
		// -------------------------------------------------
		ZutiSupportMethods.checkForMissingBranches();
		boolean zutiMdsSectionIdExists = sectfile.sectionIndex("MDS") > -1;
		// System.out.println("number of stay places: " + World.cur().airdrome.stay.length);
		// -------------------------------------------------
		
		ArrayList myArray = new ArrayList();
		int i = sectfile.sectionIndex("BornPlace");
		if (i >= 0)
		{
			int foundBornPlaces = sectfile.vars(i);
			if (foundBornPlaces != 0 && World.cur().airdrome != null && World.cur().airdrome.stay != null)
			{
				World.cur().bornPlaces = new ArrayList(foundBornPlaces);
				
				//TODO: Added by |ZUTI|
				//---------------------------------------
				int zutiSpawnPlaceReductionCounter = 0;
				//---------------------------------------
				
				for (int bpIndex = 0; bpIndex < foundBornPlaces; bpIndex++)
				{
					try
					{
						NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, bpIndex));
						int army = numbertokenizer.next(0, 0, Army.amountNet() - 1);
						float radius = (float)numbertokenizer.next(1000, 500, 10000);
						double d = (double)(radius * radius);
						float xCoordinate = (float)numbertokenizer.next(0);
						float yCoordinate = (float)numbertokenizer.next(0);
						boolean parachute = numbertokenizer.next(1) == 1;
						
						// TODO: Added by |ZUTI|
						// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						int zutiSpawnHeight = 1000;
						int zutiSpawnSpeed = 200;
						int zutiSpawnOrient = 0;
						int zutiMaxPilots = 0;
						boolean zutiCanThisHomeBaseBeCaptured = false;
						int zutiRadarHeight_MIN = 0;
						int zutiRadarHeight_MAX = 5000;
						int zutiRadarRange = 50;
						boolean zutiAirspawnOnly = false;
						boolean zutiEnablePlaneLimits = false;
						boolean zutiDecreasingNumberOfPlanes = false;
						boolean zutiIncludeStaticPlanes = false;
						boolean zutiDisableSpawning = false;
						boolean zutiEnableFriction = false;
						boolean zutiStaticPositionOnly = false;
						double zutiFriction = 3.8D;
						int zutiCapturingRequiredParatroopers = 100;
						boolean zutiDisableRendering = false;
						boolean zutiSpawnAcWithFoldedWings = true;
						boolean zutiIsStandAloneBornPlace = false;
						boolean zutiEnableQueue = false;
						int zutiDeckClearTimeout = 30;
						boolean zutiAirspawnIfQueueFull = false;
						boolean zutiPilotInVulnerableWhileOnTheDeck = false;
						boolean zutiCaptureOnlyIfChiefPresent = false;
						
						int id = 0;
						try
						{
							zutiSpawnHeight = numbertokenizer.next(1000, 0, 10000);
							id++;
							zutiSpawnSpeed = numbertokenizer.next(200, 0, 500);
							id++;
							zutiSpawnOrient = numbertokenizer.next(0, 0, 360);
							id++;
							zutiMaxPilots = numbertokenizer.next(0, 0, 99999);
							id++;
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiCanThisHomeBaseBeCaptured = true;
								id++;
							}
							zutiRadarHeight_MIN = numbertokenizer.next(0, 0, 99999);
							id++;
							zutiRadarHeight_MAX = numbertokenizer.next(5000, 0, 99999);
							id++;
							zutiRadarRange = numbertokenizer.next(50, 1, 99999);
							id++;
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiAirspawnOnly = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiEnablePlaneLimits = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiDecreasingNumberOfPlanes = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiDisableSpawning = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiEnableFriction = true;
								id++;
							}
							zutiFriction = numbertokenizer.next(3.8D, 0.0D, 10.0D);
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiIncludeStaticPlanes = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiStaticPositionOnly = true;
								id++;
							}
							zutiCapturingRequiredParatroopers = numbertokenizer.next(100, 0, 99999);
							id++;
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiDisableRendering = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 0)
							{
								zutiSpawnAcWithFoldedWings = false;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiIsStandAloneBornPlace = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiEnableQueue = true;
								id++;
							}
							zutiDeckClearTimeout = numbertokenizer.next(30, 0, 99999);
							id++;
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiAirspawnIfQueueFull = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiPilotInVulnerableWhileOnTheDeck = true;
								id++;
							}
							if (numbertokenizer.next(0, 0, 1) == 1)
							{
								zutiCaptureOnlyIfChiefPresent = true;
								id++;
							}	
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
						// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						
						boolean hasSpawnPointsDefined = false;
						Point_Stay[][] point_stays = World.cur().airdrome.stay;
						
						// TODO: Added by |ZUTI|. The goal is to show all born places for coops too, not only dogfights
						if (!isDogfight())
							hasSpawnPointsDefined = true;
						else
						{
							Point_Stay point_stay = null;
							for (int spawnPointsCounter = 0; spawnPointsCounter < point_stays.length; spawnPointsCounter++)
							{
								if (point_stays[spawnPointsCounter] != null)
								{
									point_stay = point_stays[spawnPointsCounter][point_stays[spawnPointsCounter].length - 1];
									double distance = ((point_stay.x - xCoordinate) * (point_stay.x - xCoordinate)) + ((point_stay.y - yCoordinate) * (point_stay.y - yCoordinate));
									
									if( distance <= d )
									{
										/*
										System.out.println("=============================");
										System.out.println("Radius: " + (int)radius + " -> " + (int)d);
										System.out.println("Distance: " + (int)distance);
										System.out.println("x=" + (int)point_stay.x + ", y=" +(int) point_stay.y);
										System.out.println("=============================");
										*/
										hasSpawnPointsDefined = true;
										break;
									}
								}
							}
						}

						//TODO: Just load home bases, regardless. But note that it will only have air spawns if it has no spawn places
						//if (hasSpawnPointsDefined || zutiIsStandAloneBornPlace)
						{
							if( !hasSpawnPointsDefined )
								System.out.println("!!!Home base x=" + (int)xCoordinate + (int)yCoordinate + " WILL NOT BE SELECTABLE because no spawn places were found for it. Try increasing its radius!!!");
							
							BornPlace bornplace = new BornPlace((double)xCoordinate, (double)yCoordinate, army, radius);
							bornplace.zutiCanThisHomeBaseBeCaptured = zutiCanThisHomeBaseBeCaptured;
							bornplace.zutiRadarHeight_MIN = zutiRadarHeight_MIN;
							bornplace.zutiRadarHeight_MAX = zutiRadarHeight_MAX;
							bornplace.zutiRadarRange = zutiRadarRange;
							bornplace.zutiSpawnHeight = zutiSpawnHeight;
							bornplace.zutiSpawnSpeed = zutiSpawnSpeed;
							bornplace.zutiSpawnOrient = zutiSpawnOrient;
							bornplace.zutiMaxBasePilots = zutiMaxPilots;
							bornplace.zutiAirspawnOnly = zutiAirspawnOnly;
							bornplace.zutiDisableSpawning = zutiDisableSpawning;
							bornplace.zutiEnableFriction = zutiEnableFriction;
							bornplace.zutiFriction = zutiFriction;
							bornplace.zutiEnablePlaneLimits = zutiEnablePlaneLimits;
							bornplace.zutiDecreasingNumberOfPlanes = zutiDecreasingNumberOfPlanes;
							bornplace.zutiIncludeStaticPlanes = zutiIncludeStaticPlanes;
							bornplace.bParachute = parachute;
							bornplace.zutiBpIndex = bpIndex;
							bornplace.zutiStaticPositionOnly = zutiStaticPositionOnly;
							bornplace.zutiCapturingRequiredParatroopers = zutiCapturingRequiredParatroopers;
							bornplace.zutiDisableRendering = zutiDisableRendering;
							bornplace.zutiSpawnAcWithFoldedWings = zutiSpawnAcWithFoldedWings;
							bornplace.zutiIsStandAloneBornPlace = zutiIsStandAloneBornPlace;
							bornplace.zutiEnableQueue = zutiEnableQueue;
							bornplace.zutiDeckClearTimeout = zutiDeckClearTimeout;
							bornplace.zutiAirspawnIfQueueFull = zutiAirspawnIfQueueFull;
							bornplace.zutiPilotInVulnerableWhileOnTheDeck = zutiPilotInVulnerableWhileOnTheDeck;
							bornplace.zutiCaptureOnlyIfNoChiefPresent = zutiCaptureOnlyIfChiefPresent;
							World.cur().bornPlaces.add(bornplace);
							
							if (bornplace.zutiCanThisHomeBaseBeCaptured)
								ZutiMDSVariables.ZUTI_FRONT_ENABLE_HB_CAPTURING = true;
							
							myArray.add(bornplace);
							
							// Load born place captured planes list
							ZutiSupportMethods.loadCapturedPlanesList(bornplace, sectfile);
							// TODO: Comment by |ZUTI|: changed so that only static actors get converted. This should be fine, i hope
							// --------------------------------------------------------------------------------------------------------------------------
							if (actors != null)
							{
								int i_108_ = actors.size();
								for (int i_109_ = 0; i_109_ < i_108_; i_109_++)
								{
									Actor actor = (Actor)actors.get(i_109_);
									// Changed that this will convert only static actors to appropriate army
									// if (Actor.isValid(actor) && actor.pos != null)
									if (Actor.isValid(actor) && actor.pos != null && ZutiSupportMethods.isStaticActor(actor))
									{
										Point3d point3d = actor.pos.getAbsPoint();
										double d_110_ = (((point3d.x - (double)xCoordinate) * (point3d.x - (double)xCoordinate)) + ((point3d.y - (double)yCoordinate) * (point3d.y - (double)yCoordinate)));
										if (d_110_ <= d)
											actor.setArmy(bornplace.army);
									}
								}
							}
							// --------------------------------------------------------------------------------------------------------------------------
							int i_111_ = sectfile.sectionIndex("BornPlace" + bpIndex);
							if (i_111_ >= 0)
							{
								int i_112_ = sectfile.vars(i_111_);
								for (int i_113_ = 0; i_113_ < i_112_; i_113_++)
								{
									// Rewritten with usage of StringTokenizer
									/*
									 * String string = sectfile.var(i_111_, i_113_);
									 */

									String readLine = sectfile.line(i_111_, i_113_);
									StringTokenizer stringtokenizer = new StringTokenizer(readLine);
									
									ZutiAircraft zac = new ZutiAircraft();
									ZutiSupportMethods.fillZutiAircraft(zac, stringtokenizer, zutiMdsSectionIdExists);
									String string = zac.getAcName();
									if (string != null)
									{
										string = string.intern();
										Class var_class = ((Class)Property.value(string, "airClass", null));
										//if (var_class != null && (Property.containsValue(var_class, "cockpitClass")))
										if (var_class != null)
										{
											// Add this ac to modified table for this home base
											if (bornplace.zutiAircraft == null)
												bornplace.zutiAircraft = new ArrayList();
											
											bornplace.zutiAircraft.add(zac);
										}
									}
								}
							}
							// TODO: Added by |ZUTI|: once we're done loading born place, fill it's airNames array (we keep that one)
							ZutiSupportMethods_Net.fillBornPlaceAirNames(bornplace);
							
							// Load planes that are loaded when home base is captured
							ZutiSupportMethods.zutiLoadBornPlaceCapturedPlanes(bornplace, sectfile);
							
							// TODO: Added by |ZUTI|: when we are done loading planes, let's load countries for this home base. Do it here because if BP has ALL planes (none selected), above entries are not created for it!
							ZutiSupportMethods.zutiLoadBornPlaceCountries_oldMDS(bornplace, sectfile);
							ZutiSupportMethods.zutiLoadBornPlaceCountries_newMDS(bornplace, sectfile);
							
							// Load RRR settings for this born place, if it has any
							ZutiSupportMethods.zutiLoadBornPlaceRRR(bornplace, sectfile);
							
							// Delete existing MAP spawn places if born place is of stand alone type
							if (bornplace.zutiIsStandAloneBornPlace)
							{
								zutiSpawnPlaceReductionCounter += ZutiSupportMethods_AI.removeSpawnPlacesInsideArea(bornplace.place.x, bornplace.place.y, bornplace.r);
								//System.out.println(".................................X=" + bornplace.place.x + ", Y=" + bornplace.place.y);
								
								bornplace.zutiColor = Army.color(bornplace.army);
								ZutiSupportMethods_GUI.STD_HOME_BASE_AIRFIELDS.add(bornplace);
							}
							// System.out.println("number of stay places: " + World.cur().airdrome.stay.length);
						}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				
				//TODO: Added by |ZUTI|: create new amount of holding places
				//----------------------------------------------------------
				World.cur().airdrome.stayHold = new boolean[World.cur().airdrome.stayHold.length - zutiSpawnPlaceReductionCounter];
				//----------------------------------------------------------
				
				// TODO: Added by |ZUTI|: load dynamic airports - carriers
				//--------------------------------------------------------
				try
				{
					// Load extra spawn places
					ZutiSupportMethods_Builder.loadSpawnPointsForSTDBornPlace(sectfile, true);
					ZutiSupportMethods_Ships.zutiAssignShipToBornPlaces();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				//---------------------------------------------------------
			}
		}
	}
	
	private void loadTargets(SectFile sectfile)
	{
		// TODO: Added by |ZUTI|
		// -----------------------------
		Target.zutiResetTargetsCount();
		// -----------------------------
		
		// TODO: Edited by |ZUTI|
		// if (Main.cur().netServerParams.isSingle() || (Main.cur().netServerParams.isCoop() && Main.cur().netServerParams.isMaster()))
		if (Main.cur().netServerParams.isSingle() || Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isDogfight())
		{
			int i = sectfile.sectionIndex("Target");
			if (i >= 0)
			{
				int i_114_ = sectfile.vars(i);
				for (int i_115_ = 0; i_115_ < i_114_; i_115_++)
					Target.create(sectfile.line(i, i_115_));
			}
		}
	}
	
	private void loadViewPoint(SectFile sectfile)
	{
		int i = sectfile.sectionIndex("StaticCamera");
		if (i >= 0)
		{
			int i_116_ = sectfile.vars(i);
			for (int i_117_ = 0; i_117_ < i_116_; i_117_++)
			{
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_117_));
				float f = (float)numbertokenizer.next(0);
				float f_118_ = (float)numbertokenizer.next(0);
				float f_119_ = (float)numbertokenizer.next(100, 2, 10000);
				ActorViewPoint actorviewpoint = new ActorViewPoint();
				double d = (double)f;
				double d_120_ = (double)f_118_;
				float f_121_ = f_119_;
				World.land();
				Point3d point3d = new Point3d(d, d_120_, (double)(f_121_ + Landscape.HQ_Air(f, f_118_)));
				Point3d point3d_122_ = point3d;
				actorviewpoint.pos.setAbs(point3d_122_);
				actorviewpoint.pos.reset();
				actorviewpoint.dreamFire(true);
				actorviewpoint.setName("StaticCamera_" + i_117_);
				if (net.isMirror())
				{
					actorviewpoint.createNetObject(net.masterChannel(), ((Integer)actors.get(curActor)).intValue());
					actors.set(curActor++, actorviewpoint);
				}
				else
				{
					actorviewpoint.createNetObject(null, 0);
					actors.add(actorviewpoint);
				}
			}
		}
	}
	
	private void checkBridgesAndHouses(SectFile sectfile)
	{
		int i = sectfile.sections();
		for (int i_123_ = 0; i_123_ < i; i_123_++)
		{
			String string = sectfile.sectionName(i_123_);
			if (string.endsWith("_Way"))
			{
				int i_124_ = sectfile.vars(i_123_);
				for (int i_125_ = 0; i_125_ < i_124_; i_125_++)
				{
					String string_126_ = sectfile.var(i_123_, i_125_);
					if (string_126_.equals("GATTACK"))
					{
						SharedTokenizer.set(sectfile.value(i_123_, i_125_));
						SharedTokenizer.next((String)null);
						SharedTokenizer.next((String)null);
						SharedTokenizer.next((String)null);
						SharedTokenizer.next((String)null);
						String string_127_ = SharedTokenizer.next((String)null);
						if (string_127_ != null && string_127_.startsWith("Bridge"))
						{
							LongBridge longbridge = ((LongBridge)Actor.getByName(" " + string_127_));
							if (longbridge != null && !longbridge.isAlive())
								longbridge.BeLive();
						}
					}
				}
			}
			else if (string.endsWith("_Road"))
			{
				int i_128_ = sectfile.vars(i_123_);
				for (int i_129_ = 0; i_129_ < i_128_; i_129_++)
				{
					SharedTokenizer.set(sectfile.value(i_123_, i_129_));
					SharedTokenizer.next((String)null);
					int i_130_ = (int)SharedTokenizer.next(1.0);
					if (i_130_ < 0)
					{
						i_130_ = -i_130_ - 1;
						LongBridge longbridge = LongBridge.getByIdx(i_130_);
						if (longbridge != null && !longbridge.isAlive())
							longbridge.BeLive();
					}
				}
			}
		}
		int i_131_ = sectfile.sectionIndex("Target");
		if (i_131_ >= 0)
		{
			int i_132_ = sectfile.vars(i_131_);
			for (int i_133_ = 0; i_133_ < i_132_; i_133_++)
			{
				SharedTokenizer.set(sectfile.line(i_131_, i_133_));
				int i_134_ = SharedTokenizer.next(0, 0, 7);
				if (i_134_ == 1 || i_134_ == 2 || i_134_ == 6 || i_134_ == 7)
				{
					SharedTokenizer.next(0);
					SharedTokenizer.next(0);
					SharedTokenizer.next(0);
					SharedTokenizer.next(0);
					int i_135_ = SharedTokenizer.next(0);
					int i_136_ = SharedTokenizer.next(0);
					int i_137_ = SharedTokenizer.next(1000, 50, 3000);
					SharedTokenizer.next(0);
					String string = SharedTokenizer.next((String)null);
					if (string != null && string.startsWith("Bridge"))
						string = " " + string;
					switch (i_134_)
					{
						case 1 :
						case 6 :
							World.cur().statics.restoreAllHouses((float)i_135_, (float)i_136_, (float)i_137_);
							break;
						case 2 :
						case 7 :
							if (string != null)
							{
								LongBridge longbridge = (LongBridge)Actor.getByName(string);
								if (longbridge != null && !longbridge.isAlive())
									longbridge.BeLive();
							}
							break;
					}
				}
			}
		}
	}
	
	public static void doMissionStarting()
	{
		ArrayList arraylist = new ArrayList(Engine.targets());
		int i = arraylist.size();
		for (int i_139_ = 0; i_139_ < i; i_139_++)
		{
			Actor actor = (Actor)arraylist.get(i_139_);
			if (Actor.isValid(actor))
			{
				try
				{
					actor.missionStarting();
				}
				catch (Exception exception)
				{
					System.out.println(exception.getMessage());
					exception.printStackTrace();
				}
			}
		}
	}
	
	public void doBegin()
	{
		if (!bPlaying)
		{
			if (Config.isUSE_RENDER())
			{
				Main3D.cur3D().setDrawLand(true);
				if (World.cur().diffCur.Clouds)
				{
					Main3D.cur3D().bDrawClouds = true;
					if (RenderContext.cfgSky.get() == 0)
					{
						RenderContext.cfgSky.set(1);
						RenderContext.cfgSky.apply();
						RenderContext.cfgSky.reset();
					}
				}
				else
					Main3D.cur3D().bDrawClouds = false;
				CmdEnv.top().exec("fov 70");
				if (Main3D.cur3D().keyRecord != null)
				{
					Main3D.cur3D().keyRecord.clearPrevStates();
					Main3D.cur3D().keyRecord.clearRecorded();
					Main3D.cur3D().keyRecord.stopRecording(false);
					if (Main.cur().netServerParams.isSingle())
						Main3D.cur3D().keyRecord.startRecording();
				}
				NetMissionTrack.countRecorded = 0;
				if (Main3D.cur3D().guiManager != null)
				{
					Main3D.cur3D().guiManager.setTimeGameActive(true);
					GUIPad.bStartMission = true;
				}
				// TODO: Comment by |ZUTI|: this should be fine
				if (!Main.cur().netServerParams.isCoop())
					doMissionStarting();
				if (Main.cur().netServerParams.isDogfight())
				{
					Time.setPause(false);
					RTSConf.cur.time.setEnableChangePause1(false);
				}
				CmdEnv.top().exec("music PUSH");
				CmdEnv.top().exec("music STOP");
				if (!Main3D.cur3D().isDemoPlaying())
					ForceFeedback.startMission();
				Joy.adapter().rePostMoves();
				viewSet = Main3D.cur3D().viewSet_Get();
				iconTypes = Main3D.cur3D().iconTypes();
			}
			else
			{
				doMissionStarting();
				Time.setPause(false);
			}
			if (net.isMaster())
			{
				sendCmd(10);
				doReplicateNotMissionActors(true);
			}
			if (Main.cur().netServerParams.isSingle())
			{
				Main.cur().netServerParams.setExtraOcclusion(false);
				AudioDevice.soundsOn();
			}
			// TODO: Edited by |ZUTI|
			// if (Main.cur().netServerParams.isMaster() && (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isSingle()))
			if (Main.cur().netServerParams.isMaster() && (Main.cur().netServerParams.isCoop() || Main.cur().netServerParams.isDogfight() || Main.cur().netServerParams.isSingle()))
				World.cur().targetsGuard.activate();
			EventLog.type(true, "Mission: " + name() + " is Playing");
			EventLog.type("Mission BEGIN");
			
			// TODO: Added by |ZUTI|: reset server time!
			if (Main.cur().netServerParams != null)
				ZutiSupportMethods_Net.resetServerTime(Main.cur().netServerParams);
			
			bPlaying = true;
			if (Main.cur().netServerParams != null)
				Main.cur().netServerParams.USGSupdate();
		}
	}
	
	public void doEnd()
	{
		// TODO: Added by |ZUTI|
		try
		{
			if (bPlaying)
			{
				EventLog.type("Mission END");
				if (Config.isUSE_RENDER())
				{
					ForceFeedback.stopMission();
					if (Main3D.cur3D().guiManager != null)
						Main3D.cur3D().guiManager.setTimeGameActive(false);
					NetMissionTrack.stopRecording();
					if (Main3D.cur3D().keyRecord != null)
					{
						if (Main3D.cur3D().keyRecord.isPlaying())
						{
							Main3D.cur3D().keyRecord.stopPlay();
							Main3D.cur3D().guiManager.setKeyboardGameActive(true);
							Main3D.cur3D().guiManager.setMouseGameActive(true);
							Main3D.cur3D().guiManager.setJoyGameActive(true);
						}
						Main3D.cur3D().keyRecord.stopRecording(true);
					}
					CmdEnv.top().exec("music POP");
					CmdEnv.top().exec("music PLAY");
				}
				RTSConf.cur.time.setEnableChangePause1(true);
				Time.setPause(true);
				if (net.isMaster())
					sendCmd(20);
				AudioDevice.soundsOff();
				Voice.endSession();
				bPlaying = false;
				if (Main.cur().netServerParams != null)
					Main.cur().netServerParams.USGSupdate();
			}
		}
		catch (Exception ex)
		{
			System.out.println("Mission error, ID_16: " + ex.toString());
		}
	}
	
	public NetObj netObj()
	{
		return net;
	}
	
	private void sendCmd(int i)
	{
		if (net.isMirrored())
		{
			try
			{
				List list = NetEnv.channels();
				int i_140_ = list.size();
				for (int i_141_ = 0; i_141_ < i_140_; i_141_++)
				{
					NetChannel netchannel = (NetChannel)list.get(i_141_);
					if (netchannel != net.masterChannel() && netchannel.isReady() && netchannel.isMirrored(net) && (netchannel.userState == 4 || netchannel.userState == 0))
					{
						NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(i);
						net.postTo(netchannel, netmsgguaranted);
					}
				}
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	private void doReplicateNotMissionActors(boolean bool)
	{
		if (net.isMirrored())
		{
			List list = NetEnv.channels();
			int i = list.size();
			for (int i_142_ = 0; i_142_ < i; i_142_++)
			{
				NetChannel netchannel = (NetChannel)list.get(i_142_);
				if (netchannel != net.masterChannel() && netchannel.isReady() && netchannel.isMirrored(net))
				{
					if (bool)
					{
						if (netchannel.userState == 4)
							doReplicateNotMissionActors(netchannel, true);
					}
					else
						netchannel.userState = 1;
				}
			}
		}
	}
	
	private void doReplicateNotMissionActors(NetChannel netchannel, boolean bool)
	{
		if (bool)
		{
			netchannel.userState = 0;
			HashMapInt hashmapint = NetEnv.cur().objects;
			for (HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
			{
				NetObj netobj = (NetObj)hashmapintentry.getValue();
				if (netobj instanceof ActorNet && !netchannel.isMirrored(netobj))
				{
					ActorNet actornet = (ActorNet)netobj;
					if (Actor.isValid(actornet.actor()) && !actornet.actor().isSpawnFromMission())
						MsgNet.postRealNewChannel(netobj, netchannel);
				}
			}
		}
		else
			netchannel.userState = 1;
	}
	
	private void doResvMission(NetMsgInput netmsginput)
	{
		try
		{
			while (netmsginput.available() > 0)
			{
				int i = netmsginput.readInt();
				if (i < 0)
				{
					String string = netmsginput.read255();
					sectFile.sectionAdd(string);
				}
				else
					sectFile.lineAdd(i, netmsginput.read255(), netmsginput.read255());
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			System.out.println("Bad format reseived missiion");
		}
	}
	
	private void doSendMission(NetChannel netchannel, int i)
	{
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(i);
			int i_143_ = sectFile.sections();
			for (int i_144_ = 0; i_144_ < i_143_; i_144_++)
			{
				String string = sectFile.sectionName(i_144_);
				if (!string.startsWith("$$$"))
				{
					if (netmsgguaranted.size() >= 128)
					{
						net.postTo(netchannel, netmsgguaranted);
						netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(i);
					}
					netmsgguaranted.writeInt(-1);
					netmsgguaranted.write255(string);
					int i_145_ = sectFile.vars(i_144_);
					for (int i_146_ = 0; i_146_ < i_145_; i_146_++)
					{
						if (netmsgguaranted.size() >= 128)
						{
							net.postTo(netchannel, netmsgguaranted);
							netmsgguaranted = new NetMsgGuaranted();
							netmsgguaranted.writeByte(i);
						}
						netmsgguaranted.writeInt(i_144_);
						netmsgguaranted.write255(sectFile.var(i_144_, i_146_));
						netmsgguaranted.write255(sectFile.value(i_144_, i_146_));
					}
				}
			}
			if (netmsgguaranted.size() > 1)
				net.postTo(netchannel, netmsgguaranted);
			netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(i + 1);
			net.postTo(netchannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void replicateTimeofDay()
	{
		if (isServer())
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(11);
				netmsgguaranted.writeFloat(World.getTimeofDay());
				net.post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				/* empty */
			}
		}
	}
	
	private boolean isExistFile(String string)
	{
		boolean bool = false;
		try
		{
			SFSInputStream sfsinputstream = new SFSInputStream(string);
			sfsinputstream.close();
			bool = true;
		}
		catch (Exception exception)
		{
			/* empty */
		}
		return bool;
	}
	
	private void netInput(NetMsgInput netmsginput) throws IOException
	{
		boolean bool = false;
		if (net instanceof Master || netmsginput.channel() != net.masterChannel())
			bool = true;
		boolean bool_147_ = netmsginput.channel() instanceof NetChannelStream;
		NetMsgGuaranted netmsgguaranted = null;
		int i = netmsginput.readUnsignedByte();
		switch (i)
		{
			case 0 :
				netmsginput.channel().userState = 2;
				netmsgguaranted = new NetMsgGuaranted();
				if (bool)
				{
					if (bool_147_)
					{
						NetMsgGuaranted netmsgguaranted_148_ = new NetMsgGuaranted();
						netmsgguaranted_148_.writeByte(13);
						netmsgguaranted_148_.writeLong(Time.current());
						net.postTo(netmsginput.channel(), netmsgguaranted_148_);
					}
					netmsgguaranted.writeByte(0);
					netmsgguaranted.write255(name);
					netmsgguaranted.writeLong(sectFinger);
				}
				else
				{
					name = netmsginput.read255();
					sectFinger = netmsginput.readLong();
					Main.cur().netMissionListener.netMissionState(0, 0.0F, name);
					if (!bool_147_)
						((NetUser)NetEnv.host()).setMissProp("missions/" + name);
					String string = "missions/" + name;
					if (!bool_147_ && isExistFile(string))
					{
						sectFile = new SectFile(string, 0, false);
						if (sectFinger == sectFile.fingerExcludeSectPrefix("$$$"))
						{
							netmsgguaranted.writeByte(3);
							break;
						}
					}
					string = "missions/Net/Cache/" + sectFinger + ".mis";
					int[] is = getSwTbl(string, sectFinger);
					sectFile = new SectFile(string, 0, false, is);
					if (!bool_147_ && sectFinger == sectFile.fingerExcludeSectPrefix("$$$"))
						netmsgguaranted.writeByte(3);
					else
					{
						sectFile = new SectFile(string, 1, false, is);
						sectFile.clear();
						netmsgguaranted.writeByte(1);
					}
				}
				break;
			case 13 :
				if (!bool)
				{
					long l = netmsginput.readLong();
					RTSConf.cur.time.setCurrent(l);
					NetMissionTrack.playingStartTime = l;
				}
				break;
			case 1 :
				if (bool)
					doSendMission(netmsginput.channel(), 1);
				else
				{
					Main.cur().netMissionListener.netMissionState(1, 0.0F, null);
					doResvMission(netmsginput);
				}
				break;
			case 2 :
				if (!bool)
				{
					sectFile.saveFile();
					netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(3);
				}
				break;
			case 3 :
				if (bool)
				{
					int i_149_ = actors.size();
					int i_150_ = 0;
					while (i_150_ < i_149_)
					{
						netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(3);
						int i_151_ = 64;
						while (i_151_-- > 0 && i_150_ < i_149_)
						{
							Actor actor = (Actor)actors.get(i_150_++);
							if (Actor.isValid(actor))
								netmsgguaranted.writeShort(actor.net.idLocal());
							else
								netmsgguaranted.writeShort(0);
						}
						net.postTo(netmsginput.channel(), netmsgguaranted);
					}
					netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(4);
					netmsginput.channel().userState = 3;
				}
				else
				{
					Main.cur().netMissionListener.netMissionState(2, 0.0F, null);
					while (netmsginput.available() > 0)
						actors.add(new Integer(netmsginput.readUnsignedShort()));
				}
				break;
			case 4 :
				if (bool)
				{
					// TODO: Comment by |ZUTI| - this is fine
					if (isDogfight() || netmsginput.channel() instanceof NetChannelOutStream)
					{
						World.cur().statics.netBridgeSync(netmsginput.channel());
						World.cur().statics.netHouseSync(netmsginput.channel());
					}
					for (int i_152_ = 0; i_152_ < actors.size(); i_152_++)
					{
						Actor actor = (Actor)actors.get(i_152_);
						if (Actor.isValid(actor))
						{
							try
							{
								NetChannel netchannel = netmsginput.channel();
								netchannel.setMirrored(actor.net);
								actor.netFirstUpdate(netmsginput.channel());
							}
							catch (Exception exception)
							{
								exception.printStackTrace();
							}
						}
					}
					if (Actor.isValid(World.cur().houseManager))
						World.cur().houseManager.fullUpdateChannel(netmsginput.channel());
					netmsgguaranted = new NetMsgGuaranted();
					if (isPlaying())
					{
						netmsgguaranted.writeByte(10);
						net.postTo(netmsginput.channel(), netmsgguaranted);
						netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(11);
						netmsgguaranted.writeFloat(World.getTimeofDay());
						net.postTo(netmsginput.channel(), netmsgguaranted);
						netmsgguaranted = null;
						doReplicateNotMissionActors(netmsginput.channel(), true);
						trySendMsgStart(netmsginput.channel());
					}
					else
					{
						netmsgguaranted.writeByte(5);
						netmsginput.channel().userState = 4;
					}
				}
				else
				{
					netmsginput.channel().userState = 3;
					try
					{
						load(name, sectFile, true);
					}
					catch (Exception exception)
					{
						printDebug(exception);
						Main.cur().netMissionListener.netMissionState(4, 0.0F, exception.getMessage());
					}
				}
				break;
			case 5 :
				break;
			case 10 :
				if (!(net instanceof Master) && netmsginput.channel() == net.masterChannel())
				{
					if (net.isMirrored())
					{
						netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(10);
						net.post(netmsgguaranted);
						netmsgguaranted = null;
					}
					doReplicateNotMissionActors(true);
					doReplicateNotMissionActors(netmsginput.channel(), true);
					doBegin();
					Main.cur().netMissionListener.netMissionState(6, 0.0F, null);
				}
				break;
			case 11 :
				if (!(net instanceof Master) && netmsginput.channel() == net.masterChannel())
				{
					float f = netmsginput.readFloat();
					World.setTimeofDay(f);
					World.land().cubeFullUpdate();
				}
				break;
			case 20 :
				if (!(net instanceof Master) && netmsginput.channel() == net.masterChannel())
				{
					Main.cur().netMissionListener.netMissionState(7, 0.0F, null);
					doReplicateNotMissionActors(false);
					doReplicateNotMissionActors(netmsginput.channel(), false);
					doEnd();
					if (net.isMirrored())
					{
						netmsgguaranted = new NetMsgGuaranted();
						netmsgguaranted.writeByte(20);
						net.post(netmsgguaranted);
						netmsgguaranted = null;
					}
				}
				break;
			case 12 :
				if (!(net instanceof Master) && netmsginput.channel() == net.masterChannel())
					Main.cur().netMissionListener.netMissionState(9, 0.0F, null);
				break;
		}
		if (netmsgguaranted != null && netmsgguaranted.size() > 0)
			net.postTo(netmsginput.channel(), netmsgguaranted);
	}
	
	public void trySendMsgStart(Object object)
	{
		if (!isDestroyed())
		{
			NetChannel netchannel = (NetChannel)object;
			if (!netchannel.isDestroyed())
			{
				HashMapInt hashmapint = RTSConf.cur.netEnv.objects;
				HashMapIntEntry hashmapintentry = null;
				while ((hashmapintentry = hashmapint.nextEntry(hashmapintentry)) != null)
				{
					NetObj netobj = (NetObj)hashmapintentry.getValue();
					if (netobj != null && !netobj.isDestroyed() && !netobj.isCommon() && !netchannel.isMirrored(netobj) && netobj.masterChannel() != netchannel
							&& (!(netchannel instanceof NetChannelOutStream) || (!(netobj instanceof NetControl) && (!(netobj instanceof NetUser) || !netobj.isMaster() || !NetMissionTrack.isPlaying())))
							&& (!(netobj instanceof GameTrack) || !netobj.isMirror()))
					{
						Object object_153_ = netobj.superObj();
						if (!(object_153_ instanceof Destroy) || !((Destroy)object_153_).isDestroyed())
						{
							new MsgInvokeMethod_Object("trySendMsgStart", netchannel).post(72, this, 0.0);
							return;
						}
					}
				}
				try
				{
					NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(12);
					net.postTo(netchannel, netmsgguaranted);
				}
				catch (Exception exception)
				{
					printDebug(exception);
				}
			}
		}
	}
	
	private void createNetObject(NetChannel netchannel, int i)
	{
		setTime(true);
		if (netchannel == null)
		{
			net = new Master(this);
			doReplicateNotMissionActors(false);
		}
		else
		{
			net = new Mirror(this, netchannel, i);
			doReplicateNotMissionActors(netchannel, false);
		}
	}
	
	protected static void printDebug(Exception exception)
	{
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}
	
	private int[] getSwTbl(String string, long l)
	{
		int i = (int)l;
		int i_154_ = Finger.Int(string);
		if (i < 0)
			i = -i;
		if (i_154_ < 0)
			i_154_ = -i_154_;
		int i_155_ = (i_154_ + i / 7) % 16 + 15;
		int i_156_ = (i_154_ + i / 21) % Finger.kTable.length;
		if (i_155_ < 0)
			i_155_ = -i_155_ % 16;
		if (i_155_ < 10)
			i_155_ = 10;
		if (i_156_ < 0)
			i_156_ = -i_156_ % Finger.kTable.length;
		int[] is = new int[i_155_];
		for (int i_157_ = 0; i_157_ < i_155_; i_157_++)
			is[i_157_] = Finger.kTable[(i_156_ + i_157_) % Finger.kTable.length];
		return is;
	}
	
	/* synthetic */
	static Class class$ZutiClass(String string)
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
		Spawn.add((class$com$maddox$il2$game$Mission == null ? (class$com$maddox$il2$game$Mission = class$ZutiClass("com.maddox.il2.game.Mission")) : class$com$maddox$il2$game$Mission), new SPAWN());
	}
	
	public static int curYear()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0;
		else
			return com.maddox.il2.game.Main.cur().mission.curYear;
	}
	
	public static int curMonth()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0;
		else
			return com.maddox.il2.game.Main.cur().mission.curMonth;
	}
	
	public static int curDay()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0;
		else
			return com.maddox.il2.game.Main.cur().mission.curDay;
	}
	
	public static float curWindDirection()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0.0F;
		else
			return com.maddox.il2.game.Main.cur().mission.curWindDirection;
	}
	
	public static float curWindVelocity()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0.0F;
		else
			return com.maddox.il2.game.Main.cur().mission.curWindVelocity;
	}
	
	public static float curGust()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0.0F;
		else
			return com.maddox.il2.game.Main.cur().mission.curGust;
	}
	
	public static float curTurbulence()
	{
		if (com.maddox.il2.game.Main.cur().mission == null)
			return 0.0F;
		else
			return com.maddox.il2.game.Main.cur().mission.curTurbulence;
	}
	
	private String generateHayrakeCode(Point3d point3d)
	{
		double d = point3d.x;
		double d_190_ = point3d.y;
		long l = (long)(d + d_190_);
		Random random = new Random(l);
		byte[] is = new byte[12];
		for (int i = 0; i < is.length; i++)
		{
			boolean bool = false;
			while (!bool)
			{
				byte i_191_ = (byte)(random.nextInt(26) + 65);
				if (i_191_ != 74 && i_191_ != 81 && i_191_ != 89 && i_191_ <= 90)
				{
					for (int i_192_ = 0; i_192_ < is.length && i_191_ != is[i_192_]; i_192_++)
					{
						if (i_192_ == is.length - 1)
						{
							bool = true;
							is[i] = i_191_;
						}
					}
				}
			}
		}
		String string = new String(is);
		return string;
	}
	
	private void populateRunwayLights()
	{
		ArrayList arraylist = new ArrayList();
		World.getAirports(arraylist);
		for (int i = 0; i < arraylist.size(); i++)
		{
			if (arraylist.get(i) instanceof AirportGround)
			{
				for (int i_193_ = 0; i_193_ < actors.size(); i_193_++)
				{
					if (actors.get(i_193_) instanceof SmokeGeneric
							&& (actors.get(i_193_) instanceof Smoke.Smoke15 || actors.get(i_193_) instanceof Smoke.Smoke14 || actors.get(i_193_) instanceof Smoke.Smoke13 || actors.get(i_193_) instanceof Smoke.Smoke12))
					{
						AirportGround airportground = (AirportGround)arraylist.get(i);
						Actor actor = (Actor)actors.get(i_193_);
						double d = (airportground.pos.getAbsPoint().x - actor.pos.getAbsPoint().x);
						double d_194_ = (airportground.pos.getAbsPoint().y - actor.pos.getAbsPoint().y);
						if (Math.abs(d) < 2000.0 && Math.abs(d_194_) < 2000.0 && (actor.getArmy() == 1 || actor.getArmy() == 2))
						{
							SmokeGeneric smokegeneric = (SmokeGeneric)actor;
							smokegeneric.setVisible(false);
							airportground.addLights(smokegeneric);
						}
					}
				}
			}
		}
		for (int i = 0; i < actors.size(); i++)
		{
			if (actors.get(i) instanceof SmokeGeneric)
				((SmokeGeneric)actors.get(i)).setArmy(0);
		}
	}
	
	private void populateBeacons()
	{
		ArrayList arraylist = new ArrayList();
		ArrayList arraylist_195_ = new ArrayList();
		for (int i = 0; i < actors.size(); i++)
		{
			if (actors.get(i) instanceof TypeHasBeacon)
			{
				Point3d point3d = ((Actor)actors.get(i)).pos.getAbsPoint();
				arraylist.add(new Object[]{actors.get(i), point3d});
				if (actors.get(i) instanceof TypeHasLorenzBlindLanding)
					((Actor)actors.get(i)).missionStarting();
				if (actors.get(i) instanceof BigshipGeneric)
					hayrakeMap.put((Actor)actors.get(i), "NDB");
			}
			else if (actors.get(i) instanceof TypeHasMeacon)
			{
				Point3d point3d = ((Actor)actors.get(i)).pos.getAbsPoint();
				arraylist_195_.add(new Object[]{actors.get(i), point3d});
			}
			else if (actors.get(i) instanceof TypeHasHayRake)
			{
				Point3d point3d = ((Actor)actors.get(i)).pos.getAbsPoint();
				String string = generateHayrakeCode(point3d);
				arraylist.add(new Object[]{actors.get(i), point3d});
				hayrakeMap.put((Actor)actors.get(i), string);
			}
		}
		if (arraylist.size() != 0)
		{
			sortBeaconsList(arraylist);
			for (int i = 0; i < arraylist.size(); i++)
			{
				Object[] objects = (Object[])arraylist.get(i);
				Actor actor = (Actor)objects[0];
				if ((actor instanceof TypeHasRadioStation || actor.getArmy() == 1) && beaconsRed.size() < 32)
					beaconsRed.add(objects[0]);
				if ((actor instanceof TypeHasRadioStation || actor.getArmy() == 2) && beaconsBlue.size() < 32)
					beaconsBlue.add(objects[0]);
			}
			for (int i = 0; i < arraylist_195_.size(); i++)
			{
				Object[] objects = (Object[])arraylist_195_.get(i);
				Actor actor = (Actor)objects[0];
				if (actor.getArmy() == 1 && meaconsRed.size() < 32)
					meaconsRed.add(objects[0]);
				else if (actor.getArmy() == 2 && meaconsBlue.size() < 32)
					meaconsBlue.add(objects[0]);
			}
			arraylist.clear();
			arraylist_195_.clear();
		}
	}
	
	public static void addHayrakesToOrdersTree()
	{
		for (int i = 0; i < 10; i++)
			Main3D.cur3D().ordersTree.addShipIDs(i, -1, null, "", "");
		if (World.cur().diffCur.RealisticNavigationInstruments)
		{
			int i = 0;
			Iterator iterator = hayrakeMap.entrySet().iterator();
			while (iterator.hasNext() && i < 10)
			{
				Map.Entry entry = (Map.Entry)iterator.next();
				Actor actor = (Actor)entry.getKey();
				if (actor.getArmy() == World.getPlayerArmy())
				{
					String string = (String)entry.getValue();
					String string_197_ = Property.stringValue(actor.getClass(), "i18nName", "");
					
					if(string_197_.equals(""))
					{
		                try
		                {
		                    String s2 = actor.getClass().toString().substring(actor.getClass().toString().indexOf("$") + 1);
		                    string_197_ = I18N.technic(s2);
		                }
		                catch(Exception exception) { }
					}
					
					int i_198_ = -1;
					if (beaconsRed.contains(actor))
						i_198_ = beaconsRed.indexOf(actor);
					else if (beaconsBlue.contains(actor))
						i_198_ = beaconsBlue.indexOf(actor);
					if (string.equals("NDB"))
						Main3D.cur3D().ordersTree.addShipIDs(i, i_198_, actor, string_197_, "");
					else
					{
						boolean bool = (Aircraft.hasPlaneZBReceiver(World.getPlayerAircraft()));
						if (!bool)
							continue;
						String string_199_ = string;
						if (string.length() == 12)
							string_199_ = (string.substring(0, 3) + " / " + string.substring(3, 6) + " / " + string.substring(6, 9) + " / " + string.substring(9, 12));
						else if (string.length() == 24)
							string_199_ = (string.substring(0, 2) + "-" + string.substring(2, 4) + "-" + string.substring(4, 6) + " / " + string.substring(6, 8) + "-" + string.substring(8, 10) + "-" + string.substring(10, 12) + " / "
									+ string.substring(12, 14) + "-" + string.substring(14, 16) + "-" + string.substring(16, 18) + " / " + string.substring(18, 20) + "-" + string.substring(20, 22) + "-" + string.substring(22, 24));
						Main3D.cur3D().ordersTree.addShipIDs(i, i_198_, actor, string_197_, ("( " + string_199_ + " )"));
					}
					i++;
				}
			}
		}
	}
	
	private void sortBeaconsList(List list)
	{
		boolean bool = false;
		do
		{
			for (int i = 0; i < list.size() - 1; i++)
			{
				bool = false;
				Object[] objects = (Object[])list.get(i);
				Object[] objects_200_ = (Object[])list.get(i + 1);
				if ((objects[0] instanceof TypeHasHayRake && !(objects_200_[0] instanceof TypeHasHayRake)) || (objects[0] instanceof BigshipGeneric && !(objects_200_[0] instanceof BigshipGeneric)))
				{
					Object[] objects_201_ = objects;
					list.set(i, objects_200_);
					list.set(i + 1, objects_201_);
					bool = true;
				}
			}
		}
		while (bool);
	}
	
	public boolean hasBeacons(int i)
	{
		if (i == 1)
		{
			if (beaconsRed.size() > 0)
				return true;
			return false;
		}
		if (i == 2)
		{
			if (beaconsBlue.size() > 0)
				return true;
			return false;
		}
		return false;
	}
	
	public ArrayList getBeacons(int i)
	{
		if (i == 1)
			return beaconsRed;
		if (i == 2)
			return beaconsBlue;
		return null;
	}
	
	public ArrayList getMeacons(int i)
	{
		if (i == 1)
			return meaconsBlue;
		if (i == 2)
			return meaconsRed;
		return null;
	}
	
	public String getHayrakeCodeOfCarrier(Actor actor)
	{
		if (hayrakeMap.containsKey(actor))
			return (String)hayrakeMap.get(actor);
		return null;
	}
	
	public Mission()
	{
		name = null;
		sectFinger = 0L;
		actors = new ArrayList();
		curActor = 0;
		bPlaying = false;
		curCloudsType = 0;
		curCloudsHeight = 1000F;
		curYear = 0;
		curMonth = 0;
		curDay = 0;
		curWindDirection = 0.0F;
		curWindVelocity = 0.0F;
		curGust = 0.0F;
		curTurbulence = 0.0F;
		bigShipHpDiv = 1.0F;
		_loadPlayer = false;
		playerNum = 0;
	}
	
	public static int getMissionDate(boolean flag)
    {
        int i = 0;
        if(com.maddox.il2.game.Main.cur().mission == null)
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
            if(sectfile == null)
                return 0;
            java.lang.String s = sectfile.get("MAIN", "MAP");
            int l = com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + s);
            int j1 = sectfile.get("SEASON", "Year", 1940, 1930, 1960);
            int k1 = sectfile.get("SEASON", "Month", l, 1, 12);
            int l1 = sectfile.get("SEASON", "Day", 15, 1, 31);
            i = j1 * 10000 + k1 * 100 + l1;
            int j2 = 0x1280540 + l * 100 + 15;
            if(flag && i == j2)
                i = 0;
        } else
        {
            int j = com.maddox.il2.game.Mission.curYear();
            int k = com.maddox.il2.game.Mission.curMonth();
            int i1 = com.maddox.il2.game.Mission.curDay();
            i = j * 10000 + k * 100 + i1;
            if(flag)
            {
                com.maddox.rts.SectFile sectfile1 = com.maddox.il2.game.Main.cur().currentMissionFile;
                if(sectfile1 == null)
                    return 0;
                java.lang.String s1 = sectfile1.get("MAIN", "MAP");
                int i2 = com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + s1);
                int k2 = 0x1280540 + i2 * 100 + 15;
                if(i == k2)
                    i = 0;
            }
        }
        return i;
    }

    public static float BigShipHpDiv()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 1.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.bigShipHpDiv;
    }
	
	// TODO: Variables and Method created by |ZUTI|
	// -------------------------------------------------------------------------
	private static ZutiMDSVariables ZUTI_MDS_VARIABLES = null;
	public static ZutiMDSVariables MDS_VARIABLES()
	{
		if (ZUTI_MDS_VARIABLES == null)
		{
			ZUTI_MDS_VARIABLES = new ZutiMDSVariables();
		}
		
		return ZUTI_MDS_VARIABLES;
	}
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}