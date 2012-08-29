/*4.10.1 class, altered by _ITAF_Radar for his needs for IL2 war.*/
package com.maddox.il2.ai;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.House;

public class EventLog
{
	public static class Action
	{
		public int event;
		public float time;
		public java.lang.String arg0;
		public int scoreItem0;
		public int army0;
		public java.lang.String arg1;
		public int scoreItem1;
		public int argi;
		public float x;
		public float y;

		public Action()
		{}

		public Action(int i, java.lang.String s, int j, java.lang.String s1, int k, int l, float f, float f1)
		{
			event = i;
			time = World.getTimeofDay();
			arg0 = s;
			scoreItem0 = j;
			army0 = 0;
			if (s != null)
			{
				Actor actor = Actor.getByName(s);
				if (actor != null)
					army0 = actor.getArmy();
			}
			arg1 = s1;
			scoreItem1 = k;
			argi = l;
			x = f;
			y = f1;
			if (Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() && Main.cur().netServerParams.isDedicated())
			{
				return;
			}
			else
			{
				EventLog.actions.add(this);
				return;
			}
		}
	}

	public static java.util.ArrayList actions = new ArrayList();
	public static EventLog.Action lastFly = null;
	public static final int OCCUPIED = 0;
	public static final int TRYOCCUPIED = 1;
	public static final int CRASHED = 2;
	public static final int SHOTDOWN = 3;
	public static final int DESTROYED = 4;
	public static final int BAILEDOUT = 5;
	public static final int KILLED = 6;
	public static final int LANDED = 7;
	public static final int CAPTURED = 8;
	public static final int WOUNDED = 9;
	public static final int HEAVILYWOUNDED = 10;
	public static final int FLY = 11;
	public static final int PARAKILLED = 12;
	public static final int CHUTEKILLED = 13;
	public static final int SMOKEON = 14;
	public static final int SMOKEOFF = 15;
	public static final int LANDINGLIGHTON = 16;
	public static final int LANDINGLIGHTOFF = 17;
	public static final int WEAPONSLOAD = 18;
	public static final int PARALANDED = 19;
	public static final int DAMAGEDGROUND = 20;
	public static final int DAMAGED = 21;
	public static final int DISCONNECTED = 22;
	public static final int CONNECTED = 23;
	public static final int INFLIGHT = 24;
	public static final int REFLY = 25;
	public static final int REMOVED = 26;
	private static java.io.PrintStream file = null;
	private static boolean bBuffering = true;
	private static java.lang.String fileName = null;
	private static boolean logKeep = false;
	private static java.text.DateFormat longDate = null;
	private static java.text.DateFormat shortDate = null;
	private static boolean bInited = false;
	
	public EventLog()
	{}

	public static void flyPlayer(com.maddox.JGP.Point3d point3d)
	{
		if (com.maddox.il2.game.Mission.isDogfight())
			return;
		if (lastFly != null)
		{
			double d = ((double) lastFly.x - point3d.x) * ((double) lastFly.x - point3d.x) + ((double) lastFly.y - point3d.y) * ((double) lastFly.y - point3d.y);
			if (d < 250000D)
				return;
		}
		lastFly = new Action(11, null, -1, null, -1, 0, (float) point3d.x, (float) point3d.y);
	}

	public static void resetGameClear()
	{
		actions.clear();
		lastFly = null;
	}

	public static void resetGameCreate()
	{}

	private static void checkInited()
	{
		if (!bInited)
		{
			logKeep = com.maddox.il2.engine.Config.cur.ini.get("game", "eventlogkeep", 1, 0, 1) == 1;
			fileName = com.maddox.il2.engine.Config.cur.ini.get("game", "eventlog", (java.lang.String) null);
			bInited = true;
		}
		if (longDate == null)
		{
			longDate = java.text.DateFormat.getDateTimeInstance(2, 2);
			shortDate = java.text.DateFormat.getTimeInstance(2);
		}
	}

	public static void checkState()
	{
		EventLog.checkInited();
		if (fileName == null && Main.cur().campaign != null && Main.cur().campaign.isDGen())
		{
			fileName = "eventlog.lst";
			com.maddox.il2.engine.Config.cur.ini.set("game", "eventlog", "eventlog.lst");
			com.maddox.il2.engine.Config.cur.ini.saveFile();
		}
		if (logKeep)
		{
			EventLog.checkBuffering();
		}
		else
		{
			if (file != null)
			{
				try
				{
					file.close();
				}
				catch (java.lang.Exception exception)
				{}
				file = null;
			}
			if (fileName != null)
				try
				{
					java.io.File file1 = new File(com.maddox.rts.HomePath.toFileSystemName(fileName, 0));
					file1.delete();
				}
				catch (java.lang.Exception exception1)
				{}
		}
	}

	private static void checkBuffering()
	{
		if (file == null)
			return;
		boolean flag = true;
		if ((Main.cur() instanceof com.maddox.il2.game.DServer) || Main.cur().netServerParams != null && !Main.cur().netServerParams.isSingle()
				&& Main.cur().netServerParams.isMaster())
			flag = false;
		if (flag != bBuffering)
		{
			EventLog.close();
			EventLog.open();
		}
	}

	public static boolean open()
	{
		if (file != null)
			return true;
		EventLog.checkInited();
		if (fileName == null)
			return false;
		try
		{
			bBuffering = true;
			if ((Main.cur() instanceof com.maddox.il2.game.DServer) || Main.cur().netServerParams != null && !Main.cur().netServerParams.isSingle()
					&& Main.cur().netServerParams.isMaster())
				bBuffering = false;
			if (bBuffering)
				file = new PrintStream(new BufferedOutputStream(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(fileName, 0), true), 2048));
			else
				file = new PrintStream(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(fileName, 0), true));
		}
		catch (java.lang.Exception exception)
		{
			return false;
		}
		return true;
	}

	public static void close()
	{
		if (file != null)
		{
			file.flush();
			file.close();
			file = null;
		}
	}

	public static java.lang.StringBuffer logOnTime(float f)
	{
		if (com.maddox.il2.game.Mission.isDogfight())
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			java.lang.StringBuffer stringbuffer = new StringBuffer();
			stringbuffer.append("[");
			stringbuffer.append(shortDate.format(calendar.getTime()));
			stringbuffer.append("] ");
			return stringbuffer;
		}
		int i = (int) f;
		int j = (int) (((f - (float) i) * 60F) % 60F);
		int k = (int) (((f - (float) i - (float) (j / 60)) * 3600F) % 60F);
		java.lang.StringBuffer stringbuffer1 = new StringBuffer();
		if (i < 10)
			stringbuffer1.append('0');
		stringbuffer1.append(i);
		stringbuffer1.append(':');
		if (j < 10)
			stringbuffer1.append('0');
		stringbuffer1.append(j);
		stringbuffer1.append(':');
		if (k < 10)
			stringbuffer1.append('0');
		stringbuffer1.append(k);
		stringbuffer1.append(' ');
		return stringbuffer1;
	}

	public static java.lang.String name(Actor actor)
	{
		if (!Actor.isValid(actor))
			return "";
		if (com.maddox.il2.game.Mission.isNet() && com.maddox.il2.game.Mission.isDogfight() && (actor instanceof Aircraft))
		{
			Aircraft aircraft = (Aircraft) actor;
			com.maddox.il2.net.NetUser netuser = aircraft.netUser();
			if (netuser != null)
				return netuser.shortName() + ":" + com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName", "");
			else
				return com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName", "");
		}
		else
		{
			return actor.name();
		}
	}

	private static boolean isTyping(int i)
	{
		if (Main.cur().netServerParams == null)
			return true;
		if (Main.cur().netServerParams.isMaster())
			return true;
		else
			return (Main.cur().netServerParams.eventlogClient() & 1 << i) != 0;
	}

	public static void type(int i, float f, java.lang.String s, java.lang.String s1, int j, float f1, float f2, boolean flag)
	{
		/*
		Actor victim = Actor.getByName(s);
		Actor aggressor = Actor.getByName(s1);
		
		if( victim == null )
			System.out.println("------------- can not be resolved as actor: " + s);
		if( aggressor == null )
			System.out.println("------------- can not be resolved as actor1: " + s1);
		
		if( victim != null && aggressor != null )
			System.out.println(" -------------------- " + victim.name() + " was fucked up by " + aggressor.name());
		*/
		
		java.lang.StringBuffer stringbuffer = EventLog.logOnTime(f);
		switch (i)
		{
			case 0: // '\0'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") seat occupied by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
				
				//TODO: Modified by |ZUTI|: make position at coordinates 0,0 so nobody can track you
				//----------------------------------------
				f1 = f2 = 0.0F;
				//----------------------------------------
			break;

			case 1: // '\001'
				stringbuffer.append(s1);
				stringbuffer.append(" is trying to occupy seat ");
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(")");
			break;

			case 2: // '\002'
				stringbuffer.append(s);
				stringbuffer.append(" crashed at ");
			break;

			case 3: // '\003'
				stringbuffer.append(s);
				stringbuffer.append(" shot down by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
			break;

			case 4: // '\004'
				stringbuffer.append(s);
				stringbuffer.append(" destroyed by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
			break;

			case 5: // '\005'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") bailed out at ");
			break;

			case 6: // '\006'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				if (s1 == null || "".equals(s1))
				{
					stringbuffer.append(") was killed at ");
				}
				else
				{
					stringbuffer.append(") was killed by ");
					stringbuffer.append(s1);
					stringbuffer.append(" at ");
				}
			break;

			case 7: // '\007'
				stringbuffer.append(s);
				stringbuffer.append(" landed at ");
			break;

			case 24: // '\030'
				stringbuffer.append(s);
				stringbuffer.append(" in flight at ");
			break;

			case 8: // '\b'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") was captured at ");
			break;

			case 9: // '\t'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") was wounded at ");
			break;

			case 10: // '\n'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") was heavily wounded at ");
			break;

			case 12: // '\f'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") was killed in his chute by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
			break;

			case 13: // '\r'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") has chute destroyed by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
			break;

			case 14: // '\016'
				stringbuffer.append(s);
				stringbuffer.append(" turned wingtip smokes on at ");
			break;

			case 15: // '\017'
				stringbuffer.append(s);
				stringbuffer.append(" turned wingtip smokes off at ");
			break;

			case 16: // '\020'
				stringbuffer.append(s);
				stringbuffer.append(" turned landing lights on at ");
			break;

			case 17: // '\021'
				stringbuffer.append(s);
				stringbuffer.append(" turned landing lights off at ");
			break;

			case 18: // '\022'
				stringbuffer.append(s);
				stringbuffer.append(" loaded weapons '");
				stringbuffer.append(s1);
				stringbuffer.append("' fuel ");
				stringbuffer.append(j);
				stringbuffer.append("%");
			break;

			case 19: // '\023'
				stringbuffer.append(s);
				stringbuffer.append("(");
				stringbuffer.append(j);
				stringbuffer.append(") successfully bailed out at ");
			break;

			case 20: // '\024'
				stringbuffer.append(s);
				stringbuffer.append(" damaged on the ground at ");
			break;

			case 21: // '\025'
				stringbuffer.append(s);
				stringbuffer.append(" damaged by ");
				stringbuffer.append(s1);
				stringbuffer.append(" at ");
			break;

			case 22: // '\026'
				stringbuffer.append(s);
				stringbuffer.append(" has disconnected");
			break;

			case 23: // '\027'
				stringbuffer.append(s);
				stringbuffer.append(" has connected");
			break;

			case 25: // '\031'
				stringbuffer.append(s);
				stringbuffer.append(" entered refly menu");
			break;

			case 26: // '\032'
				stringbuffer.append(s);
				stringbuffer.append(" removed at ");
			break;

			case 11: // '\013'
			default:
				return;
		}
		
		if ( i != 1 && i != 18 && i != 22 && i != 23 && i != 25)
		{
			stringbuffer.append(f1);
			stringbuffer.append(" ");
			stringbuffer.append(f2);
		}
		if (EventLog.open() && EventLog.isTyping(i))
			file.println(stringbuffer);
		if (flag)
			((com.maddox.il2.net.NetUser) com.maddox.rts.NetEnv.host()).replicateEventLog(i, f, s, s1, j, f1, f2);
	}

	public static void type(boolean flag, java.lang.String s)
	{
		if (EventLog.open())
		{
			if (flag)
			{
				java.util.Calendar calendar = java.util.Calendar.getInstance();
				file.println("[" + longDate.format(calendar.getTime()) + "] " + s);
			}
			else
			{
				file.println(s);
			}
			file.flush();
		}
	}

	public static void type(java.lang.String s)
	{
		java.lang.StringBuffer stringbuffer = EventLog.logOnTime(World.getTimeofDay());
		stringbuffer.append(s);
		if (EventLog.open())
		{
			file.println(stringbuffer);
			file.flush();
		}
	}

	public static void onOccupied(Aircraft aircraft, com.maddox.il2.net.NetUser netuser, int i)
	{
		if (!Actor.isValid(aircraft))
			return;
		java.lang.String s = null;
		if (netuser != null)
			s = netuser.uniqueName();
		else if (com.maddox.il2.game.Mission.isSingle())
			s = "Player";
		if (s == null)
			return;
		EventLog.type(0, World.getTimeofDay(), EventLog.name(aircraft), s, i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
		if (!com.maddox.il2.game.Mission.isDogfight())
			new Action(0, EventLog.name(aircraft), 0, s, -1, i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y);
	}

	public static void onTryOccupied(java.lang.String s, com.maddox.il2.net.NetUser netuser, int i)
	{
		java.lang.String s1 = null;
		if (netuser != null)
			s1 = netuser.uniqueName();
		else if (com.maddox.il2.game.Mission.isSingle())
			s1 = "Player";
		if (s1 == null)
		{
			return;
		}
		else
		{
			EventLog.type(1, World.getTimeofDay(), s, s1, i, 0.0F, 0.0F, true);
			return;
		}
	}

	public static void onActorDied(Actor victim, Actor aggressor)
	{
		if (!Mission.isPlaying())
			return;
		if (!Actor.isValid(victim))
			return;
		if ((victim instanceof House) && Main.cur().netServerParams != null && Main.cur().netServerParams.eventlogHouse())
		{
			House house = (House) victim;
			EventLog.type(4, World.getTimeofDay(), house.getMeshLiveName(), EventLog.name(aggressor), 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, false);
			return;
		}
		if (!victim.isNamed())
			return;
		if (victim.isNetMirror())
			return;
		if (World.cur().scoreCounter.getRegisteredType(victim) < 0)
			return;
		if (aggressor == World.remover)
			EventLog.type(26, World.getTimeofDay(), EventLog.name(victim), "", 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
		else if (victim == aggressor || !Actor.isValid(aggressor) || !aggressor.isNamed())
		{
			EventLog.type(2, World.getTimeofDay(), EventLog.name(victim), "", 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
			if (!Mission.isDogfight())
				new Action(2, EventLog.name(victim), World.cur().scoreCounter.getRegisteredType(victim), null, -1, 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y);
		}
		else if (victim instanceof Aircraft)
		{
			//TODO: Added by |ZUTI| fix aircraft naming problems
			//---------------------------------------------------------------------
			String strVictim = ZutiSupportMethods.getAircraftCompleteName(victim);
			String strAggressor = ZutiSupportMethods.getAircraftCompleteName(aggressor);
			//---------------------------------------------------------------------
			
			EventLog.type(3, World.getTimeofDay(), strVictim, strAggressor, 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
			
			if (!Mission.isDogfight())
				new Action(3, EventLog.name(victim), 0, EventLog.name(aggressor), World.cur().scoreCounter.getRegisteredType(aggressor), 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y);
		}
		else
		{
			//TODO: Added by |ZUTI| fix aircraft naming problems
			//---------------------------------------------------------------------
			String strVictim = ZutiSupportMethods.getAircraftCompleteName(victim);
			String strAggressor = ZutiSupportMethods.getAircraftCompleteName(aggressor);
			//---------------------------------------------------------------------
			
			EventLog.type(4, World.getTimeofDay(), strVictim, strAggressor, 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
			
			if (!Mission.isDogfight())
				new Action(4, EventLog.name(victim), World.cur().scoreCounter.getRegisteredType(victim), EventLog.name(aggressor), World.cur().scoreCounter.getRegisteredType(aggressor), 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y);
		}
	}

	public static void onBailedOut(Aircraft aircraft, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
			return;
		EventLog.type(5, World.getTimeofDay(), EventLog.name(aircraft), "", i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
		if (!Mission.isDogfight())
			new Action(5, EventLog.name(aircraft), 0, null, -1, i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y);
	}

	public static void onPilotKilled(Aircraft victim, int i, Actor aggressor)
	{
		if (!Mission.isPlaying())
			return;
		if (victim.isNetMirror())
			return;
		java.lang.String s = null;
		if (Actor.isValid(aggressor))
			s = EventLog.name(aggressor);

		//TODO: Added by |ZUTI| fix aircraft naming problems
		//---------------------------------------------------------------------
		String strVictim = ZutiSupportMethods.getAircraftCompleteName(victim);
		String strAggressor = ZutiSupportMethods.getAircraftCompleteName(aggressor);
		//---------------------------------------------------------------------
		
		EventLog.type(6, World.getTimeofDay(), strVictim, strAggressor, i, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
		
		if (!Mission.isDogfight())
			new Action(6, EventLog.name(victim), 0, s, -1, i, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y);
	}

	public static void onPilotKilled(Actor actor, java.lang.String s, int i)
	{
		if (!Mission.isPlaying())
			return;
		EventLog.type(6, World.getTimeofDay(), s, "", i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
		if (!Mission.isDogfight())
			new Action(6, s, 0, null, -1, i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y);
	}

	public static void onAirLanded(Aircraft aircraft)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
			return;
		
		//TODO: Added by |ZUTI| fix aircraft naming problems
		//---------------------------------------------------------------------
		String strAircraft = ZutiSupportMethods.getAircraftCompleteName(aircraft);
		//---------------------------------------------------------------------
		
		EventLog.type(7, World.getTimeofDay(), strAircraft, "", 0, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
		
		if (!Mission.isDogfight())
			new Action(7, EventLog.name(aircraft), 0, null, -1, 0, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y);
	}

	public static void onAirInflight(Aircraft aircraft)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(24, World.getTimeofDay(), EventLog.name(aircraft), "", 0, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onCaptured(Actor actor, java.lang.String s, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(8, World.getTimeofDay(), s, "", i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onCaptured(Aircraft aircraft, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(8, World.getTimeofDay(), EventLog.name(aircraft), "", i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onPilotWounded(Aircraft aircraft, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(9, World.getTimeofDay(), EventLog.name(aircraft), "", i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onPilotHeavilyWounded(Aircraft aircraft, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (aircraft.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(10, World.getTimeofDay(), EventLog.name(aircraft), "", i, (float) aircraft.pos.getAbsPoint().x, (float) aircraft.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onParaKilled(Actor actor, java.lang.String s, int i, Actor actor1)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
			return;
		EventLog.type(12, World.getTimeofDay(), s, EventLog.name(actor1), i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
		if (!Mission.isDogfight())
			new Action(6, s, 0, null, -1, i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y);
	}

	public static void onChuteKilled(Actor actor, java.lang.String s, int i, Actor actor1)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(13, World.getTimeofDay(), s, EventLog.name(actor1), i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onToggleSmoke(Actor actor, boolean flag)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(flag ? 14 : 15, World.getTimeofDay(), EventLog.name(actor), "", 0, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onToggleLandingLight(Actor actor, boolean flag)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(flag ? 16 : 17, World.getTimeofDay(), EventLog.name(actor), "", 0, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onWeaponsLoad(Actor actor, java.lang.String s, int i)
	{
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(18, World.getTimeofDay(), EventLog.name(actor), s, i, 0.0F, 0.0F, true);
			return;
		}
	}

	public static void onParaLanded(Actor actor, java.lang.String s, int i)
	{
		if (!Mission.isPlaying())
			return;
		if (actor.isNetMirror())
		{
			return;
		}
		else
		{
			EventLog.type(19, World.getTimeofDay(), s, "", i, (float) actor.pos.getAbsPoint().x, (float) actor.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onDamagedGround(Actor victim)
	{
		if (!Actor.isValid(victim))
			return;
		if (!Mission.isPlaying())
			return;
		if (victim.isNetMirror())
		{
			return;
		}
		else
		{
			//TODO: Added by |ZUTI| fix aircraft naming problems
			//---------------------------------------------------------------------
			String strVictim = ZutiSupportMethods.getAircraftCompleteName(victim);
			//---------------------------------------------------------------------
						
			EventLog.type(20, World.getTimeofDay(), strVictim, "", 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onDamaged(Actor victim, Actor aggressor)
	{
		if (!Actor.isValid(victim))
			return;
		if (!Mission.isPlaying())
			return;
		if (victim.isNetMirror())
		{
			return;
		}
		else
		{
			//TODO: Added by |ZUTI| fix aircraft naming problems
			//---------------------------------------------------------------------
			String strVictim = ZutiSupportMethods.getAircraftCompleteName(victim);
			String strAggressor = ZutiSupportMethods.getAircraftCompleteName(aggressor);
			//---------------------------------------------------------------------
			
			EventLog.type(21, World.getTimeofDay(), strVictim, strAggressor, 0, (float) victim.pos.getAbsPoint().x, (float) victim.pos.getAbsPoint().y, true);
			return;
		}
	}

	public static void onDisconnected(java.lang.String s)
	{
		if (!Mission.isPlaying())
		{
			return;
		}
		else
		{
			EventLog.type(22, World.getTimeofDay(), s, "", 0, 0.0F, 0.0F, false);
			return;
		}
	}

	public static void onConnected(java.lang.String s)
	{
		if (!Mission.isPlaying())
		{
			return;
		}
		else
		{
			EventLog.type(23, World.getTimeofDay(), s, "", 0, 0.0F, 0.0F, false);
			return;
		}
	}

	public static void onRefly(java.lang.String s)
	{
		if (!Mission.isPlaying())
		{
			return;
		}
		else
		{
			EventLog.type(25, World.getTimeofDay(), s, "", 0, 0.0F, 0.0F, true);
			return;
		}
	}
}