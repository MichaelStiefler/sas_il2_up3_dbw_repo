/*4.10.1 class*/
package com.maddox.il2.net;

import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.*;
import com.maddox.il2.game.*;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.*;
import com.maddox.il2.objects.air.*;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.*;
import com.maddox.rts.net.NetFileClient;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.util.IntHashtable;
import java.io.*;
import java.util.*;

public class NetUser extends NetHost implements NetFileClient, NetUpdate
{
	public static final int MSG_READY = 1;
	public static final int MSG_BORNPLACE = 2;
	public static final int MSG_AIRDROMESTAY = 3;
	public static final int MSG_STAT = 4;
	public static final int MSG_STAT_INC = 5;
	public static final int MSG_CURSTAT = 6;
	public static final int MSG_CURSTAT_INC = 7;
	public static final int MSG_PING = 8;
	public static final int MSG_PING_INC = 9;
	public static final int MSG_REGIMENT = 10;
	public static final int MSG_SKIN = 11;
	public static final int MSG_PILOT = 12;
	public static final int MSG_REQUEST_PLACE = 13;
	public static final int MSG_PLACE = 14;
	public static final int MSG_REQUEST_WAIT_START = 15;
	public static final int MSG_WAIT_START = 16;
	public static final int MSG_KICK = 17;
	public static final int MSG_MISSION_COMPLETE = 18;
	public static final int MSG_CAMERA = 19;
	public static final int MSG_ORDER_CMD = 20;
	public static final int MSG_RADIO = 21;
	public static final int MSG_VOICE = 22;
	public static final int MSG_TASK_COMPLETE = 23;
	public static final int MSG_HOUSE_DIE = 24;
	public static final int MSG_HOUSE_SYNC = 25;
	public static final int MSG_BRIDGE_RDIE = 26;
	public static final int MSG_BRIDGE_DIE = 27;
	public static final int MSG_BRIDGE_SYNC = 28;
	public static final int MSG_DOT_RANGE_FRIENDLY = 29;
	public static final int MSG_DOT_RANGE_FOE = 30;
	public static final int MSG_EVENTLOG = 31;
	public static final int MSG_NOISEART = 32;
	private NetUserStat stat;
	private NetUserStat curstat;
	private static NetUserStat _st = new NetUserStat();
	private static NetUserStat __st = new NetUserStat();
	private int idChannelFirst;
	public int ping;
	private int army;
	private boolean bTrackWriter;
	private int bornPlace;
	private int airdromeStay;
	private String uniqueName;
	private int place;
	private boolean bWaitStartCoopMission;
	public int syncCoopStart;
	private int localRequestPlace;
	private static int armyCoopWinner = 0;
	public NetUserRegiment netUserRegiment;
	protected NetMaxLag netMaxLag;
	public OrdersTree ordersTree;
	private NetFileRequest netFileRequestMissProp;
	private NetFileRequest netFileRequestMissPropLocale;
	private String ownerSkinBmp;
	private String localSkinBmp;
	private String skinDir;
	private NetFileRequest netSkinRequest;
	private Mat cacheSkinMat[];
	private String ownerPilotBmp;
	private String localPilotBmp;
	private String localPilotTga;
	private NetFileRequest netPilotRequest;
	private Mat cachePilotMat[];
	private String ownerNoseartBmp;
	private String localNoseartBmp;
	private String localNoseartTga;
	private NetFileRequest netNoseartRequest;
	private Mat cacheNoseartMat[];
	private String radio;
	private int curCodec;
	private Actor viewActor;
	private AircraftNetFilter airNetFilter;
	private long lastTimeUpdate;
	private boolean bPingUpdateStarted;
	
	class AircraftNetFilter implements NetFilter
	{
		public float filterNetMessage(NetChannel netchannel, NetMsgFiltered netmsgfiltered)
		{
			Object obj = netmsgfiltered.filterArg();
			if (obj == null)
				return -1F;
			IntHashtable inthashtable = null;
			ActorPos actorpos = null;
			if (obj instanceof NetAircraft)
			{
				NetAircraft netaircraft = (NetAircraft)obj;
				inthashtable = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)netaircraft.net).filterTable;
				actorpos = netaircraft.pos;
			}
			else if (obj instanceof NetGunner)
			{
				NetGunner netgunner = (NetGunner)obj;
				inthashtable = netgunner.getFilterTable();
				actorpos = netgunner.pos;
			}
			else
			{
				return -1F;
			}
			if (Time.isPaused())
				return 0.0F;
			if (!Actor.isValid(viewActor))
				return 0.5F;
			int i = inthashtable.get(netchannel.id());
			if (i == -1)
			{
				inthashtable.put(netchannel.id(), (int)(Time.current() & 0x7ffffffL));
				return 1.0F;
			}
			double d = (int)(Time.current() & 0x7ffffffL) - i;
			if (d < 0.0D)
			{
				inthashtable.put(netchannel.id(), (int)(Time.current() & 0x7ffffffL));
				return 1.0F;
			}
			double d1 = viewActor.pos.getAbsPoint().distance(actorpos.getAbsPoint());
			if (d1 > 10000D)
				d1 = 10000D;
			if (d1 < 1.0D)
				d1 = 1.0D;
			double d2 = (d1 * 5000D) / 10000D;
			float f = (float)(d / d2);
			if (f >= 1.0F)
				return 1.0F;
			else
				return f * f;
		}
		
		public void filterNetMessagePosting(NetChannel netchannel, NetMsgFiltered netmsgfiltered)
		{
			Object obj = netmsgfiltered.filterArg();
			if (obj == null)
				return;
			IntHashtable inthashtable = null;
			if (obj instanceof NetAircraft)
			{
				NetAircraft netaircraft = (NetAircraft)obj;
				inthashtable = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)netaircraft.net).filterTable;
			}
			else if (obj instanceof NetGunner)
			{
				NetGunner netgunner = (NetGunner)obj;
				inthashtable = netgunner.getFilterTable();
			}
			else
			{
				return;
			}
			inthashtable.put(netchannel.id(), (int)(Time.current() & 0x7ffffffL));
		}
		
		public boolean filterEnableAdd(NetChannel netchannel, NetFilter netfilter)
		{
			return true;
		}
		
		AircraftNetFilter()
		{}
	}
	
	static class SPAWN implements NetSpawn
	{
		
		public void netSpawn(int i, NetMsgInput netmsginput)
		{
			try
			{
				String s = netmsginput.read255();
				int j = netmsginput.readUnsignedByte();
				int k = netmsginput.readUnsignedByte();
				if (k == 255)
					k = -1;
				int l = netmsginput.readUnsignedShort();
				boolean flag = netmsginput.readBoolean();
				NetHost anethost[] = null;
				int i1 = netmsginput.available() / NetMsgInput.netObjReferenceLen();
				if (i1 > 0)
				{
					anethost = new NetHost[i1];
					for (int j1 = 0; j1 < i1; j1++)
						anethost[j1] = (NetHost)netmsginput.readNetObj();
					
				}
				else
				{
					l = netmsginput.channel().id();
				}
				NetUser netuser = new NetUser(netmsginput.channel(), i, s, anethost);
				netuser.bornPlace = j;
				netuser.place = k;
				netuser.idChannelFirst = l;
				netuser.bTrackWriter = flag;
				netuser.bWaitStartCoopMission = false;
				if (Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() && Main.cur().netServerParams.isCoop())
					netuser.requestPlace(-1);
				if (i1 == 0 && (netmsginput.channel() instanceof NetChannelInStream))
					netuser.bTrackWriter = true;
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		}
		
		SPAWN()
		{}
	}
	
	public NetUserStat stat()
	{
		return stat;
	}
	
	public NetUserStat curstat()
	{
		return curstat;
	}
	
	public void reset()
	{
		army = 0;
		stat.clear();
		curstat.clear();
		netMaxLag = null;
	}
	
	public String uniqueName()
	{
		return uniqueName;
	}
	
	public boolean isTrackWriter()
	{
		return bTrackWriter;
	}
	
	public static NetUser findTrackWriter()
	{
		List list = NetEnv.hosts();
		int i = list.size();
		for (int j = 0; j < i; j++)
		{
			NetUser netuser = (NetUser)list.get(j);
			if (netuser.isTrackWriter())
				return netuser;
		}
		
		return null;
	}
	
	public void setShortName(String s)
	{
		if (s == null)
			s = "";
		super.setShortName(s);
		if (isMaster() && !isMirrored())
			makeUniqueName();
	}
	
	private void makeUniqueName()
	{
		String s = shortName();
		ArrayList arraylist = new ArrayList(NetEnv.hosts());
		arraylist.add(NetEnv.host());
		int i = arraylist.size();
		int j = 0;
		do
		{
			boolean flag = false;
			for (int k = 0; k < i; k++)
			{
				NetUser netuser = (NetUser)arraylist.get(k);
				String s1 = netuser.uniqueName();
				if (!s.equals(s1) || netuser == this)
					continue;
				flag = true;
				break;
			}
			
			if (flag)
			{
				s = shortName() + j;
				j++;
			}
			else
			{
				uniqueName = s;
				return;
			}
		}
		while (true);
	}
	
	private void pingUpdateInc()
	{
		new MsgAction(64, 10D, this)
		{
			
			public void doAction(Object obj)
			{
				NetUser netuser = (NetUser)obj;
				if (netuser.isDestroyed())
					return;
				if (Main.cur().netServerParams != null && !Main.cur().netServerParams.isDestroyed() && !Main.cur().netServerParams.isMaster() && !(Main.cur().netServerParams.masterChannel() instanceof NetChannelInStream))
					try
					{
						NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
						netmsgguaranted.writeByte(9);
						postTo(Main.cur().netServerParams.masterChannel(), netmsgguaranted);
					}
					catch (Exception exception)
					{
						NetObj.printDebug(exception);
					}
				pingUpdateInc();
			}
			
		};
	}
	
	public int getArmy()
	{
		return army;
	}
	
	public void setArmy(int i)
	{
		army = i;
		radio_onArmyChanged();
	}
	
	public void sendStatInc()
	{
		if (!isMaster())
			return;
		if (Main.cur().netServerParams == null)
			return;
		_st.clear();
		_st.fillFromScoreCounter(true);
		if (_st.isEmpty())
		{
			return;
		}
		else
		{
			_sendStatInc(false);
			return;
		}
	}
	
	private void sendCurStatInc()
	{
		if (!isMaster())
			return;
		if (Main.cur().netServerParams == null)
			return;
		_st.clear();
		_st.fillFromScoreCounter(false);
		if (_st.isEmpty())
			return;
		__st.set(stat);
		__st.inc(_st);
		if (__st.isEqualsCurrent(curstat))
		{
			return;
		}
		else
		{
			_sendStatInc(true);
			return;
		}
	}
	
	public void netUpdate()
	{
		if (!isMaster())
			return;
		checkCameraBaseChanged();
		long l = Time.real();
		if (lastTimeUpdate + 20000L > l)
			return;
		lastTimeUpdate = l;
		if (!Mission.isNet())
			return;
		if (!Mission.isPlaying())
			return;
		if (Main.cur().netServerParams == null)
			return;
		if (Main.cur().netServerParams.masterChannel() instanceof NetChannelInStream)
		{
			return;
		}
		else
		{
			sendCurStatInc();
			return;
		}
	}
	
	private void _sendStatInc(boolean flag)
	{
		if (Main.cur().netServerParams.isMaster())
		{
			if (Main.cur().netServerParams.isCoop())
			{
				if (flag)
				{
					curstat.set(_st);
				}
				else
				{
					stat.set(_st);
					curstat.set(_st);
				}
			}
			else if (flag)
			{
				curstat.set(stat);
				curstat.inc(_st);
				_st.set(curstat);
			}
			else
			{
				stat.inc(_st);
				curstat.set(stat);
				_st.set(stat);
			}
			((NetUser)NetEnv.host()).replicateStat(this, flag);
		}
		else
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(flag ? 7 : 5);
				_st.write(netmsgguaranted);
				postTo(Main.cur().netServerParams.masterChannel(), netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		}
	}
	
	private void replicateStat(NetUser netuser, boolean flag)
	{
		replicateStat(netuser, flag, null);
	}
	
	private void replicateStat(NetUser netuser, boolean flag, NetChannel netchannel)
	{
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(flag ? 6 : 4);
			netmsgguaranted.writeNetObj(netuser);
			_st.write(netmsgguaranted);
			if (netchannel != null)
				postTo(netchannel, netmsgguaranted);
			else
				post(netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	private void getIncStat(NetMsgInput netmsginput, boolean flag) throws IOException
	{
		_st.read(netmsginput);
		_sendStatInc(flag);
	}
	
	private void getStat(NetMsgInput netmsginput, boolean flag) throws IOException
	{
		_st.read(netmsginput);
		NetUser netuser = (NetUser)netmsginput.readNetObj();
		if (netuser == null)
			return;
		if (flag)
		{
			netuser.curstat.set(_st);
		}
		else
		{
			netuser.stat.set(_st);
			netuser.curstat.set(_st);
		}
		replicateStat(netuser, flag);
	}
	
	public int getAirdromeStay()
	{
		return airdromeStay;
	}
	
	public int getBornPlace()
	{
		return bornPlace;
	}
	
	public void setBornPlace(int i)
	{
		if (bornPlace == i)
			return;
		bornPlace = i;
		airdromeStay = -1;
		if (isMirrored())
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(2);
				netmsgguaranted.writeByte(2);
				netmsgguaranted.writeByte(bornPlace);
				post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		if (bornPlace >= 0 && World.cur().bornPlaces != null && bornPlace < World.cur().bornPlaces.size() && Main.cur().netServerParams != null)
		{
			BornPlace bornplace = (BornPlace)World.cur().bornPlaces.get(bornPlace);
			setArmy(bornplace.army);
			if (Main.cur().netServerParams.isMaster())
			{
				double d = bornplace.r * bornplace.r;
				Point_Stay apoint_stay[][] = World.cur().airdrome.stay;
				for (int j = 0; j < apoint_stay.length; j++)
				{
					//TODO: Comment by |ZUTI|: Here begin logic for assigning of free spawn place
					if (apoint_stay[j] != null)
					{
						Point_Stay point_stay = apoint_stay[j][apoint_stay[j].length - 1];
						double d1 = ((double)point_stay.x - bornplace.place.x) * ((double)point_stay.x - bornplace.place.x) + ((double)point_stay.y - bornplace.place.y) * ((double)point_stay.y - bornplace.place.y);
						if (d1 <= d && ((NetUser)NetEnv.host()).airdromeStay != j)
						{
							List list = NetEnv.hosts();
							boolean flag = false;
							for (int k = 0; k < list.size(); k++)
							{
								NetUser netuser = (NetUser)list.get(k);
								if (netuser.airdromeStay != j)
									continue;
								flag = true;
								break;
							}
							
							if (!flag)
							{
								airdromeStay = j;
								d = d1;
							}
						}
					}
				}
				if (isMirror())
					sendAirdromeStay(bornPlace, airdromeStay);
			}
		}
	}
	
	private void sendAirdromeStay(int i, int j)
	{
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(6);
			netmsgguaranted.writeByte(3);
			netmsgguaranted.writeByte(i);
			netmsgguaranted.writeInt(j);
			postTo(masterChannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void onConnectReady(NetChannel netchannel)
	{
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
			netmsgguaranted.writeByte(1);
			postTo(netchannel, netmsgguaranted);
			netchannel.userState = 1;
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public int getPlace()
	{
		if (isMaster() && localRequestPlace != place)
			return -1;
		else
			return place;
	}
	
	private int _getPlace()
	{
		return place;
	}
	
	public void requestPlace(int i)
	{
		armyCoopWinner = 0;
		if (isMaster())
		{
			if (localRequestPlace == i)
				return;
			localRequestPlace = i;
			place = -1;
		}
		NetServerParams netserverparams = Main.cur().netServerParams;
		
		//TODO: Added by |ZUTI|: failsafe check
		//--------------------------------------------
		if( netserverparams == null )
			return;
		//--------------------------------------------
		
		if (netserverparams.isMaster())
		{
			if (i != -1)
			{
				NetUser netuser = (NetUser)NetEnv.host();
				if (netuser._getPlace() == i)
				{
					i = -1;
				}
				else
				{
					List list1 = NetEnv.hosts();
					for (int k = 0; k < list1.size(); k++)
					{
						NetUser netuser1 = (NetUser)list1.get(k);
						if (netuser1._getPlace() != i)
							continue;
						i = -1;
						break;
					}
					
				}
			}
			place = i;
			if (place >= 0)
			{
				//TODO: Added by |ZUTI|: don't change army if user is changing position in own ac!
				if( !ZutiSupportMethods_Net.isInOwnAircraft(this, World.getPlayerAircraft()) )
				{
					try{setArmy(GUINetAircraft.getItem(place).reg.getArmy());}
					catch(Exception ex){}
				}
			}
			bWaitStartCoopMission = false;
			if (NetEnv.host().isMirrored())
			{
				List list = NetEnv.channels();
				for (int j = 0; j < list.size(); j++)
				{
					NetChannel netchannel = (NetChannel)list.get(j);
					if (netchannel.isMirrored(this) || netchannel == masterChannel())
						try
						{
							NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
							netmsgguaranted1.writeByte(14);
							netmsgguaranted1.writeByte(place);
							netmsgguaranted1.writeNetObj(this);
							NetEnv.host().postTo(netchannel, netmsgguaranted1);
						}
						catch (Exception exception1)
						{
							NetObj.printDebug(exception1);
						}
				}
				
			}
		}
		else
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(13);
				netmsgguaranted.writeByte(i);
				postTo(netserverparams.masterChannel(), netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		}
	}
	
	public void resetAllPlaces()
	{
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams == null || !netserverparams.isMaster())
			return;
		((NetUser)NetEnv.host()).requestPlace(-1);
		List list = NetEnv.hosts();
		if (list == null)
			return;
		for (int i = 0; i < list.size(); i++)
		{
			NetUser netuser = (NetUser)list.get(i);
			netuser.requestPlace(-1);
		}
		
	}
	
	public void missionLoaded()
	{
		if (!Mission.isCoop())
			return;
		if (!(Mission.cur().netObj().masterChannel() instanceof NetChannelInStream))
		{
			List list = NetEnv.hosts();
			if (list == null)
				return;
			for (int i = 0; i < list.size(); i++)
			{
				NetUser netuser = (NetUser)list.get(i);
				if (netuser.place >= 0)
					netuser.setArmy(GUINetAircraft.getItem(netuser.place).reg.getArmy());
			}
			
		}
	}
	
	public boolean isWaitStartCoopMission()
	{
		return bWaitStartCoopMission && getPlace() >= 0;
	}
	
	public void doWaitStartCoopMission()
	{
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams.isMaster())
		{
			bWaitStartCoopMission = true;
			if (NetEnv.host().isMirrored())
				try
				{
					NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
					netmsgguaranted.writeByte(16);
					netmsgguaranted.writeNetObj(this);
					NetEnv.host().post(netmsgguaranted);
				}
				catch (Exception exception)
				{
					NetObj.printDebug(exception);
				}
		}
		else
		{
			try
			{
				NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
				netmsgguaranted1.writeByte(15);
				postTo(netserverparams.masterChannel(), netmsgguaranted1);
			}
			catch (Exception exception1)
			{
				NetObj.printDebug(exception1);
			}
		}
	}
	
	public void kick(NetUser netuser)
	{
		if (netuser == null || netuser.isDestroyed())
			return;
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (!netserverparams.isMaster())
			return;
		if (netuser.isMaster())
		{
			return;
		}
		else
		{
			_kick(netuser);
			return;
		}
	}
	
	private void _kick(NetUser netuser)
	{
		if (netuser == null || netuser.isDestroyed())
		{
			return;
		}
		else
		{
			//TODO: Modified by |ZUTI|
			//---------------------------------------------
			ZutiSupportMethods_NetSend.releaseCarrierSpawnPlace(netuser);
			//---------------------------------------------
			
			new MsgAction(72, 0.0D, netuser)
			{
				
				public void doAction(Object obj)
				{
					NetUser netuser1 = (NetUser)obj;
					if (netuser1 == null || netuser1.isDestroyed())
						return;
					if (netuser1.path() == null)
					{
						netuser1.masterChannel().destroy("You have been kicked from the server.");
					}
					else
						try
						{
							NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
							netmsgguaranted.writeByte(17);
							netmsgguaranted.writeNetObj(netuser1);
							NetEnv.host().postTo(netuser1.masterChannel(), netmsgguaranted);
						}
						catch (Exception exception)
						{
							NetObj.printDebug(exception);
						}
				}
				
			};
			return;
		}
	}
	
	public void coopMissionComplete(boolean flag)
	{
		if (isMirrored())
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(18);
				netmsgguaranted.writeByte(flag ? 1 : 0);
				post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		setArmyCoopWinner(flag);
	}
	
	private void setClientMissionComplete(boolean flag)
	{
		coopMissionComplete(flag);
		World.cur().targetsGuard.doMissionComplete();
	}
	
	private void setArmyCoopWinner(boolean flag)
	{
		armyCoopWinner = World.getMissionArmy();
		if (!flag)
			armyCoopWinner = armyCoopWinner % 2 + 1;
	}
	
	public static int getArmyCoopWinner()
	{
		return armyCoopWinner;
	}
	
	public void speekVoice(int i, int j, int k, String s, int ai[], int l, boolean flag)
	{
		if (isMirrored())
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(22);
				netmsgguaranted.writeShort(i);
				netmsgguaranted.writeShort(j);
				if (s != null && s.length() == 2)
					k |= 0x8000;
				netmsgguaranted.writeShort(k);
				if (s != null && s.length() == 2)
					netmsgguaranted.write255(s);
				netmsgguaranted.writeBoolean(flag);
				int i1 = ai.length;
				for (int j1 = 0; j1 < i1; j1++)
				{
					int k1 = ai[j1];
					if (k1 == 0)
						break;
					netmsgguaranted.writeShort(k1);
				}
				
				post(netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
	}
	
	private void getVoice(NetMsgInput paramNetMsgInput)
	{
		try
		{
			int i = paramNetMsgInput.readUnsignedShort();
			int j = paramNetMsgInput.readUnsignedShort();
			int k = paramNetMsgInput.readUnsignedShort();
			String str = null;
			if ((k & 0x8000) != 0)
			{
				k &= -32769;
				str = paramNetMsgInput.read255();
			}
			boolean bool = paramNetMsgInput.readBoolean();
			int l = paramNetMsgInput.available() / 2;
			if (l == 0)
				return;
			int[] arrayOfInt = new int[l + 1];
			for (int i1 = 0; i1 < l; ++i1)
				arrayOfInt[i1] = paramNetMsgInput.readUnsignedShort();
			arrayOfInt[l] = 0;
			speekVoice(i, j, k, str, arrayOfInt, 1, bool);
			
			Voice.setSyncMode(i);
			Voice.speak(j, k, str, arrayOfInt, 1, false, bool);
		}
		catch (Exception localException)
		{
			NetObj.printDebug(localException);
		}
	}
	
	private void checkCameraBaseChanged()
	{
		if (!Config.isUSE_RENDER())
			return;
		if (!Mission.isNet())
			return;
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams == null || netserverparams.isMaster())
			return;
		Actor actor = Main3D.cur3D().viewActor();
		if (viewActor == actor)
			return;
		viewActor = actor;
		ActorNet actornet = null;
		if (Actor.isValid(actor))
			actornet = actor.net;
		replicateCameraBaseChanged(actornet);
	}
	
	private void replicateCameraBaseChanged(NetObj netobj)
	{
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams.isMaster())
		{
			if (netobj != null)
			{
				Object obj = netobj.superObj();
				if (obj != null && (obj instanceof Actor))
				{
					viewActor = (Actor)obj;
					return;
				}
			}
			viewActor = null;
		}
		else
		{
			doReplicateCameraBaseChanged(netobj);
		}
	}
	
	public void doReplicateCameraBaseChanged(Object obj)
	{
		if (isDestroyed())
			return;
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams == null)
			return;
		NetObj netobj = null;
		if (obj != null)
		{
			netobj = (NetObj)obj;
			if (netobj.isDestroyed())
				netobj = null;
			else if (netobj.masterChannel() != netserverparams.masterChannel() && !netserverparams.masterChannel().isMirrored(netobj))
			{
				(new MsgInvokeMethod_Object("doReplicateCameraBaseChanged", netobj)).post(72, this);
				return;
			}
		}
		if (netserverparams.masterChannel() instanceof NetChannelInStream)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(19);
			netmsgguaranted.writeNetObj(netobj);
			postTo(netserverparams.masterChannel(), netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void orderCmd(int i)
	{
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams.isMaster())
		{
			if (i == -1)
				ordersTree.activate();
			else if (i == -2)
				ordersTree.unactivate();
			else
				ordersTree.execCmd(i);
		}
		else
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(20);
				netmsgguaranted.writeByte(i);
				postTo(netserverparams.masterChannel(), netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		}
	}
	
	public void orderCmd(int i, Actor actor)
	{
		NetServerParams netserverparams = Main.cur().netServerParams;
		if (netserverparams.isMaster())
		{
			if (i == -1)
				ordersTree.activate();
			else if (i == -2)
			{
				ordersTree.unactivate();
			}
			else
			{
				Actor actor1 = Selector.getTarget();
				Selector.setTarget(actor);
				ordersTree.execCmd(i);
				Selector.setTarget(actor1);
			}
		}
		else
		{
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(20);
				netmsgguaranted.writeByte(i);
				netmsgguaranted.writeNetObj(actor.net);
				postTo(netserverparams.masterChannel(), netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
		}
	}
	
	public void postTaskComplete(Actor actor)
	{
		if (!Actor.isValid(actor))
			return;
		World.onTaskComplete(actor);
		if (actor.net.countMirrors() == 0)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(23);
			netmsgguaranted.writeNetObj(actor.net);
			post(netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void replicateDotRange()
	{
		replicateDotRange(true);
		replicateDotRange(false);
	}
	
	public void replicateDotRange(boolean flag)
	{
		replicateDotRange(flag, null);
	}
	
	private void replicateDotRange(NetChannel netchannel)
	{
		replicateDotRange(true, netchannel);
		replicateDotRange(false, netchannel);
	}
	
	private void replicateDotRange(boolean flag, NetChannel netchannel)
	{
		if (Main.cur().netServerParams == null)
			return;
		if (this != Main.cur().netServerParams.host())
			return;
		if (isMirrored() || netchannel != null)
			try
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(flag ? 29 : 30);
				if (flag)
					Main.cur().dotRangeFriendly.netOutput(netmsgguaranted);
				else
					Main.cur().dotRangeFoe.netOutput(netmsgguaranted);
				if (netchannel == null)
					post(netmsgguaranted);
				else
					postTo(netchannel, netmsgguaranted);
			}
			catch (Exception exception)
			{
				NetObj.printDebug(exception);
			}
	}
	
	public void replicateEventLog(int i, float f, String s, String s1, int j, float f1, float f2)
	{
		if (Main.cur().netServerParams == null)
			return;
		if (!isMirrored())
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(31);
			netmsgguaranted.writeByte(i);
			netmsgguaranted.writeFloat(f);
			netmsgguaranted.write255(s);
			netmsgguaranted.write255(s1);
			netmsgguaranted.writeByte(j);
			netmsgguaranted.writeFloat(f1);
			netmsgguaranted.writeFloat(f2);
			post(netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	private void getEventLog(NetMsgInput netmsginput)
	{
		try
		{
			byte byte0 = netmsginput.readByte();
			float f = netmsginput.readFloat();
			String s = netmsginput.read255();
			String s1 = netmsginput.read255();
			byte byte1 = netmsginput.readByte();
			float f1 = netmsginput.readFloat();
			float f2 = netmsginput.readFloat();
			EventLog.type(byte0, f, s, s1, byte1, f1, f2, false);
			replicateEventLog(byte0, f, s, s1, byte1, f1, f2);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public boolean netInput(NetMsgInput netmsginput) throws IOException
	{
		if (super.netInput(netmsginput))
			return true;
		
		netmsginput.reset();
		byte byte0 = netmsginput.readByte();

		//TODO: Added by |ZUTI|
		//-----------------------------------------------
		if( ZutiSupportMethods_NetReceive.processReceivedMessage(this, netmsginput, byte0) )
			return true;
		//-----------------------------------------------
		
		if (isMirror() && netmsginput.channel() == masterChannel)
		{
			switch (byte0)
			{
				case 1 : // '\001'
					if (netmsginput.channel().userState == -1)
					{
						netmsginput.channel().userState = 1;
						if (Mission.cur() != null && Mission.cur().netObj() != null)
							MsgNet.postRealNewChannel(Mission.cur().netObj(), masterChannel);
					}
					return true;
					
				case 14 : // '\016'
					int i = netmsginput.readUnsignedByte();
					if (i == 255)
						i = -1;
					NetUser netuser2 = (NetUser)netmsginput.readNetObj();
					if (netuser2 == null)
						return true;
					netuser2.place = i;
					if (i >= 0 && Mission.cur() != null && Main.cur().missionLoading == null)
					{
						try{netuser2.setArmy(GUINetAircraft.getItem(i).reg.getArmy());}
						catch(Exception ex){}
					}
					netuser2.bWaitStartCoopMission = false;
					if (isMirrored())
						try
						{
							NetMsgGuaranted netmsgguaranted4 = new NetMsgGuaranted();
							netmsgguaranted4.writeByte(14);
							netmsgguaranted4.writeByte(i);
							netmsgguaranted4.writeNetObj(netuser2);
							post(netmsgguaranted4);
						}
						catch (Exception exception2)
						{
							NetObj.printDebug(exception2);
						}
					return true;
					
				case 16 : // '\020'
					NetUser netuser = (NetUser)netmsginput.readNetObj();
					if (netuser == null)
						return true;
					netuser.bWaitStartCoopMission = true;
					if (isMirrored())
						try
						{
							NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
							netmsgguaranted.writeByte(16);
							netmsgguaranted.writeNetObj(netuser);
							post(netmsgguaranted);
						}
						catch (Exception exception)
						{
							NetObj.printDebug(exception);
						}
					return true;
					
				case 17 : // '\021'
					NetUser netuser1 = (NetUser)netmsginput.readNetObj();
					if (netuser1 == null)
					{
						return true;
					}
					else
					{
						_kick(netuser1);
						return true;
					}
					
				case 20 : // '\024'
					byte byte1 = netmsginput.readByte();
					if (netmsginput.available() > 0)
					{
						Actor actor = null;
						NetObj netobj1 = netmsginput.readNetObj();
						if (netobj1 != null)
							actor = (Actor)netobj1.superObj();
						orderCmd(byte1, actor);
					}
					else
					{
						orderCmd(byte1);
					}
					return true;
					
				case 18 : // '\022'
					boolean flag = netmsginput.readByte() != 0;
					if (isMirrored())
						try
						{
							NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
							netmsgguaranted1.writeByte(18);
							netmsgguaranted1.writeByte(flag ? 1 : 0);
							post(netmsgguaranted1);
						}
						catch (Exception exception1)
						{
							NetObj.printDebug(exception1);
						}
					setClientMissionComplete(flag);
					return true;
					
				case 2 : // '\002'
					int j = netmsginput.readUnsignedByte();
					if (j == 255)
						j = -1;
					setBornPlace(j);
					return true;
					
				case 10 : // '\n'
					String s = netmsginput.read255();
					char ac[] = new char[2];
					ac[0] = netmsginput.readChar();
					ac[1] = netmsginput.readChar();
					int i2 = netmsginput.readUnsignedByte();
					String s5 = netmsginput.read255();
					setUserRegiment(s, s5, ac, i2);
					replicateNetUserRegiment();
					return true;
					
				case 11 : // '\013'
					String s1 = netmsginput.read255();
					setSkin(s1);
					replicateSkin();
					return true;
					
				case 12 : // '\f'
					String s2 = netmsginput.read255();
					setPilot(s2);
					replicatePilot();
					return true;
					
				case 32 : // ' '
					String s3 = netmsginput.read255();
					setNoseart(s3);
					replicateNoseart();
					return true;
					
				case 21 : // '\025'
					String s4 = null;
					int k1 = 0;
					if (netmsginput.available() > 0)
					{
						s4 = netmsginput.read255();
						if (netmsginput.available() > 0)
						{
							k1 = netmsginput.readInt();
						}
						else
						{
							k1 = -1;
							System.out.println("ERROR: Radio channel message has old format");
						}
					}
					if (k1 != -1)
						replicateRadio(s4, k1);
					return true;
					
				case 22 : // '\026'
					getVoice(netmsginput);
					return true;
					
				case 19 : // '\023'
					replicateCameraBaseChanged(netmsginput.readNetObj());
					return true;
					
				case 23 : // '\027'
					NetObj netobj = netmsginput.readNetObj();
					if (netobj == null)
					{
						return true;
					}
					else
					{
						postTaskComplete((Actor)netobj.superObj());
						return true;
					}
					
				case 29 : // '\035'
					Main.cur().dotRangeFriendly.netInput(netmsginput);
					replicateDotRange(true);
					return true;
					
				case 30 : // '\036'
					Main.cur().dotRangeFoe.netInput(netmsginput);
					replicateDotRange(false);
					return true;
			}
		}
		switch (byte0)
		{
			case 13 : // '\r'
				int k = netmsginput.readUnsignedByte();
				if (k == 255)
					k = -1;
				requestPlace(k);
				return true;
				
			case 15 : // '\017'
				doWaitStartCoopMission();
				return true;
				
			case 3 : // '\003'
				int l = netmsginput.readUnsignedByte();
				if (l == 255)
					l = -1;
				int l1 = netmsginput.readInt();
				if (l == bornPlace)
					if (isMirror())
						sendAirdromeStay(l, l1);
					else
						airdromeStay = l1;
				return true;
				
			case 4 : // '\004'
			case 6 : // '\006'
				getStat(netmsginput, byte0 == 6);
				return true;
				
			case 5 : // '\005'
			case 7 : // '\007'
				getIncStat(netmsginput, byte0 == 7);
				return true;
				
			case 9 : // '\t'
				int i1 = 0;
				if (netmsginput.available() == 4)
					i1 = netmsginput.readInt();
				i1 += netmsginput.channel().ping();
				if (Main.cur().netServerParams.isMaster())
				{
					ping = i1;
					NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted();
					netmsgguaranted2.writeByte(8);
					netmsgguaranted2.writeInt(i1);
					netmsgguaranted2.writeNetObj(this);
					((NetUser)NetEnv.host()).post(netmsgguaranted2);
				}
				else
				{
					NetMsgGuaranted netmsgguaranted3 = new NetMsgGuaranted();
					netmsgguaranted3.writeByte(9);
					netmsgguaranted3.writeInt(i1);
					postTo(Main.cur().netServerParams.masterChannel(), netmsgguaranted3);
				}
				return true;
				
			case 8 : // '\b'
				int j1 = netmsginput.readInt();
				NetUser netuser3 = (NetUser)netmsginput.readNetObj();
				if (netuser3 != null)
				{
					netuser3.ping = j1;
					NetMsgGuaranted netmsgguaranted5 = new NetMsgGuaranted();
					netmsgguaranted5.writeByte(8);
					netmsgguaranted5.writeInt(j1);
					netmsgguaranted5.writeNetObj(netuser3);
					post(netmsgguaranted5);
				}
				return true;
				
			case 24 : // '\030'
				if (World.cur().statics != null)
					World.cur().statics.netMsgHouseDie(this, netmsginput);
				return true;
				
			case 25 : // '\031'
				if (World.cur().statics != null)
					World.cur().statics.netMsgHouseSync(netmsginput);
				return true;
				
			case 26 : // '\032'
				if (World.cur().statics != null)
					World.cur().statics.netMsgBridgeRDie(netmsginput);
				return true;
				
			case 27 : // '\033'
				if (World.cur().statics != null)
					World.cur().statics.netMsgBridgeDie(this, netmsginput);
				return true;
				
			case 28 : // '\034'
				if (World.cur().statics != null)
					World.cur().statics.netMsgBridgeSync(netmsginput);
				return true;
				
			case 31 : // '\037'
				getEventLog(netmsginput);
				return true;
				
			case 10 : // '\n'
			case 11 : // '\013'
			case 12 : // '\f'
			case 14 : // '\016'
			case 16 : // '\020'
			case 17 : // '\021'
			case 18 : // '\022'
			case 19 : // '\023'
			case 20 : // '\024'
			case 21 : // '\025'
			case 22 : // '\026'
			case 23 : // '\027'
			case 29 : // '\035'
			case 30 : // '\036'
			default :
				return false;
		}
	}
	
	public void netFileAnswer(NetFileRequest netfilerequest)
	{
		if (netUserRegiment.netFileRequest == netfilerequest)
		{
			netUserRegiment.netFileRequest = null;
			if (netfilerequest.state() != 0 && !NetFilesTrack.existFile(netfilerequest))
				return;
			netUserRegiment.setLocalFileNameBmp(netfilerequest.localFileName());
			if (netUserRegiment.localFileNameBmp != null)
				NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
			if (!netUserRegiment.isEmpty())
			{
				Aircraft aircraft = findAircraft();
				if (aircraft != null)
				{
					String s2 = aircraft.netName();
					int k = s2.length();
					int l = 0;
					try
					{
						l = Integer.parseInt(s2.substring(k - 2, k));
					}
					catch (Exception exception)
					{}
					aircraft.preparePaintScheme(l);
				}
			}
		}
		else if (netSkinRequest == netfilerequest)
		{
			netSkinRequest = null;
			if (netfilerequest.state() != 0 && !NetFilesTrack.existFile(netfilerequest))
				return;
			localSkinBmp = netfilerequest.localFileName();
			if (localSkinBmp.length() == 0)
			{
				localSkinBmp = null;
			}
			else
			{
				tryPrepareSkin(findAircraft());
				NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
			}
		}
		else if (netNoseartRequest == netfilerequest)
		{
			netNoseartRequest = null;
			if (netfilerequest.state() != 0 && !NetFilesTrack.existFile(netfilerequest))
				return;
			localNoseartBmp = netfilerequest.localFileName();
			if (localNoseartBmp.length() == 0)
			{
				localNoseartBmp = null;
			}
			else
			{
				tryPrepareNoseart(findAircraft());
				NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
			}
		}
		else if (netPilotRequest == netfilerequest)
		{
			netPilotRequest = null;
			if (netfilerequest.state() != 0 && !NetFilesTrack.existFile(netfilerequest))
				return;
			localPilotBmp = netfilerequest.localFileName();
			if (localPilotBmp.length() == 0)
			{
				localPilotBmp = null;
			}
			else
			{
				NetGunner netgunner = findGunner();
				Aircraft aircraft1 = findAircraft();
				if (netgunner == null)
					tryPreparePilot(aircraft1);
				else if (Actor.isValid(aircraft1))
					tryPreparePilot(aircraft1, aircraft1.netCockpitAstatePilotIndx(netgunner.getCockpitNum()));
				NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
			}
		}
		else if (netFileRequestMissProp == netfilerequest)
		{
			if (netfilerequest.state() != 0)
			{
				netFileRequestMissProp = null;
				return;
			}
			String s = netfilerequest.localFileName();
			if (s.equals(netfilerequest.ownerFileName()))
				s = Main.cur().netFileServerMissProp.primaryPath() + "/" + s;
			else
				s = Main.cur().netFileServerMissProp.alternativePath() + "/" + s;
			netFileRequestMissProp = null;
			int i = s.lastIndexOf(".properties");
			if (i < 0)
				return;
			s = s.substring(0, i);
			if (Main.cur().netServerParams.isCoop())
			{
				GUINetClientCBrief guinetclientcbrief = (GUINetClientCBrief)GameState.get(46);
				if (!guinetclientcbrief.isExistTextDescription())
					guinetclientcbrief.setTextDescription(s);
			}
			else if (Main.cur().netServerParams.isDogfight())
			{
				GUINetClientDBrief guinetclientdbrief = (GUINetClientDBrief)GameState.get(40);
				if (!guinetclientdbrief.isExistTextDescription())
					guinetclientdbrief.setTextDescription(s);
			}
		}
		else if (netFileRequestMissPropLocale == netfilerequest)
		{
			if (netfilerequest.state() != 0)
			{
				netFileRequestMissPropLocale = null;
				return;
			}
			String s1 = netfilerequest.localFileName();
			if (s1.equals(netfilerequest.ownerFileName()))
				s1 = Main.cur().netFileServerMissProp.primaryPath() + "/" + s1;
			else
				s1 = Main.cur().netFileServerMissProp.alternativePath() + "/" + s1;
			netFileRequestMissPropLocale = null;
			int j = s1.lastIndexOf(".properties");
			if (j < 0)
				return;
			s1 = s1.substring(0, j);
			j = s1.lastIndexOf("_" + RTSConf.cur.locale.getLanguage());
			if (j > 0)
				s1 = s1.substring(0, j);
			if (Main.cur().netServerParams.isCoop())
			{
				GUINetClientCBrief guinetclientcbrief1 = (GUINetClientCBrief)GameState.get(46);
				guinetclientcbrief1.setTextDescription(s1);
			}
			else if (Main.cur().netServerParams.isDogfight())
			{
				GUINetClientDBrief guinetclientdbrief1 = (GUINetClientDBrief)GameState.get(40);
				guinetclientdbrief1.setTextDescription(s1);
			}
		}
	}
	
	public void recordNetFiles()
	{
		if (netUserRegiment.localFileNameBmp != null)
			NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
		if (localSkinBmp != null)
			NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
		if (localPilotBmp != null)
			NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
		if (localNoseartBmp != null)
			NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
	}
	
	public void setMissProp(String s)
	{
		if (!Config.isUSE_RENDER())
			return;
		if (Main.cur().netServerParams.isMaster())
			return;
		if (Main.cur().netServerParams.isCoop())
		{
			GUINetClientCBrief guinetclientcbrief = (GUINetClientCBrief)GameState.get(46);
			guinetclientcbrief.clearTextDescription();
		}
		else if (Main.cur().netServerParams.isDogfight())
		{
			GUINetClientDBrief guinetclientdbrief = (GUINetClientDBrief)GameState.get(40);
			guinetclientdbrief.clearTextDescription();
		}
		if (!s.startsWith("missions/"))
			return;
		s = s.substring("missions/".length());
		for (int i = s.length() - 1; i > 0; i--)
		{
			char c = s.charAt(i);
			if (c == '\\' || c == '/')
				break;
			if (c != '.')
				continue;
			s = s.substring(0, i);
			break;
		}
		
		if (netFileRequestMissProp != null)
		{
			netFileRequestMissProp.doCancel();
			netFileRequestMissProp = null;
		}
		if (netFileRequestMissPropLocale != null)
		{
			netFileRequestMissPropLocale.doCancel();
			netFileRequestMissPropLocale = null;
		}
		if (!RTSConf.cur.locale.equals(Locale.US))
		{
			netFileRequestMissPropLocale = new NetFileRequest(this, Main.cur().netFileServerMissProp, 220, Main.cur().netServerParams, s + "_" + RTSConf.cur.locale.getLanguage() + ".properties");
			netFileRequestMissPropLocale.doRequest();
		}
		netFileRequestMissProp = new NetFileRequest(this, Main.cur().netFileServerMissProp, 210, Main.cur().netServerParams, s + ".properties");
		netFileRequestMissProp.doRequest();
	}
	
	public void setUserRegiment(String s, String s1, char ac[], int i)
	{
		if (netUserRegiment.equals(s, s1, ac, i))
			return;
		netUserRegiment.set(s, s1, ac, i);
		if (netUserRegiment.netFileRequest != null)
		{
			netUserRegiment.netFileRequest.doCancel();
			netUserRegiment.netFileRequest = null;
		}
		if (isMaster())
		{
			netUserRegiment.setLocalFileNameBmp(netUserRegiment.ownerFileNameBmp());
			if (NetMissionTrack.isRecording())
				NetFilesTrack.recordFile(Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
		}
		else if (netUserRegiment.ownerFileNameBmp().length() > 0 && (Config.cur.netSkinDownload || (masterChannel() instanceof NetChannelInStream)))
		{
			netUserRegiment.netFileRequest = new NetFileRequest(this, Main.cur().netFileServerReg, 200, this, netUserRegiment.ownerFileNameBmp());
			netUserRegiment.netFileRequest.doRequest();
		}
	}
	
	public void replicateNetUserRegiment()
	{
		replicateNetUserRegiment(null);
	}
	
	private void replicateNetUserRegiment(NetChannel netchannel)
	{
		if (!isMirrored() && netchannel == null)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
			netmsgguaranted.writeByte(10);
			netmsgguaranted.write255(netUserRegiment.branch());
			netmsgguaranted.writeChar(netUserRegiment.aid()[0]);
			netmsgguaranted.writeChar(netUserRegiment.aid()[1]);
			netmsgguaranted.writeByte(netUserRegiment.gruppeNumber());
			netmsgguaranted.write255(netUserRegiment.ownerFileNameBmp);
			if (netchannel == null)
				post(netmsgguaranted);
			else
				postTo(netchannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void tryPrepareSkin(NetAircraft netaircraft)
	{
		if (!Config.isUSE_RENDER())
			return;
		if (!Actor.isValid(netaircraft))
			return;
		if (localSkinBmp == null)
			return;
		Aircraft aircraft = (Aircraft)netaircraft;
		Class class1 = aircraft.getClass();
		Regiment regiment = aircraft.getRegiment();
		String s = regiment.country();
		String s1 = Aircraft.getPropertyMesh(class1, s);
		if (skinDir == null)
		{
			String s2 = s1;
			int i = s2.lastIndexOf('/');
			if (i >= 0)
				s2 = s2.substring(0, i + 1) + "summer";
			else
				s2 = s2 + "summer";
			NetFileServerSkin netfileserverskin = Main.cur().netFileServerSkin;
			String s3;
			if (ownerSkinBmp.equals(localSkinBmp))
			{
				s3 = netfileserverskin.primaryPath() + "/" + localSkinBmp;
				skinDir = "" + Finger.file(0L, s3, -1);
			}
			else
			{
				s3 = netfileserverskin.alternativePath() + "/" + localSkinBmp;
				int j = localSkinBmp.lastIndexOf('.');
				if (j >= 0)
					skinDir = localSkinBmp.substring(0, j);
				else
					skinDir = localSkinBmp;
			}
			skinDir = "PaintSchemes/Cache/" + skinDir;
			try
			{
				File file = new File(HomePath.toFileSystemName(skinDir, 0));
				if (!file.isDirectory())
					file.mkdir();
			}
			catch (Exception exception)
			{
				skinDir = null;
			}
			if (!BmpUtils.bmp8PalTo4TGA4(s3, s2, skinDir))
				skinDir = null;
		}
		if (skinDir == null)
		{
			return;
		}
		else
		{
			Aircraft.prepareMeshCamouflage(s1, aircraft.hierMesh(), skinDir, cacheSkinMat);
			return;
		}
	}
	
	public void setSkin(String s)
	{
		if (s == null)
			s = "";
		if (s.equals(ownerSkinBmp))
			return;
		ownerSkinBmp = s;
		localSkinBmp = null;
		skinDir = null;
		if (netSkinRequest != null)
		{
			netSkinRequest.doCancel();
			netSkinRequest = null;
		}
		if (isMaster())
		{
			if (s.length() > 0)
			{
				localSkinBmp = ownerSkinBmp;
				if (NetMissionTrack.isRecording())
					NetFilesTrack.recordFile(Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
			}
			else
			{
				localSkinBmp = null;
			}
		}
		else if (s.length() > 0 && (Config.cur.netSkinDownload || (masterChannel() instanceof NetChannelInStream)))
		{
			netSkinRequest = new NetFileRequest(this, Main.cur().netFileServerSkin, 100, this, ownerSkinBmp);
			netSkinRequest.doRequest();
		}
	}
	
	public void replicateSkin()
	{
		replicateSkin(null);
	}
	
	private void replicateSkin(NetChannel netchannel)
	{
		if (!isMirrored() && netchannel == null)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(11);
			netmsgguaranted.write255(ownerSkinBmp);
			if (netchannel == null)
				post(netmsgguaranted);
			else
				postTo(netchannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void checkReplicateSkin(String s)
	{
		if (!isMaster())
			return;
		if (!"".equals(ownerSkinBmp))
			return;
		UserCfg usercfg = World.cur().userCfg;
		String s1 = usercfg.getSkin(s);
		if (s1 == null)
		{
			return;
		}
		else
		{
			setSkin(GUIAirArming.validateFileName(s) + "/" + s1);
			replicateSkin();
			return;
		}
	}
	
	public void tryPreparePilot(NetAircraft netaircraft)
	{
		tryPreparePilotSkin(netaircraft, 0);
	}
	
	public void tryPreparePilot(NetAircraft netaircraft, int i)
	{
		tryPreparePilotSkin(netaircraft, i);
	}
	
	public void tryPreparePilot(Paratrooper paratrooper)
	{
		tryPreparePilotSkin(paratrooper, 0);
	}
	
	public void tryPreparePilotSkin(Actor actor, int i)
	{
		if (!Config.isUSE_RENDER())
			return;
		if (!Actor.isValid(actor))
			return;
		if (i < 0)
			return;
		if (localPilotBmp == null)
			return;
		if (localPilotTga == null)
		{
			NetFileServerPilot netfileserverpilot = Main.cur().netFileServerPilot;
			String s1;
			if (ownerPilotBmp.equals(localPilotBmp))
				s1 = netfileserverpilot.primaryPath() + "/" + localPilotBmp;
			else
				s1 = netfileserverpilot.alternativePath() + "/" + localPilotBmp;
			localPilotTga = localPilotBmp.substring(0, localPilotBmp.length() - 4);
			if (!BmpUtils.bmp8PalToTGA3(s1, "PaintSchemes/Cache/Pilot" + localPilotTga + ".tga"))
			{
				localPilotTga = null;
				return;
			}
		}
		if (localPilotTga == null)
			return;
		String s = "PaintSchemes/Cache/Pilot" + localPilotTga + ".mat";
		String s2 = "PaintSchemes/Cache/Pilot" + localPilotTga + ".tga";
		if (actor instanceof NetAircraft)
			Aircraft.prepareMeshPilot(((NetAircraft)actor).hierMesh(), i, s, s2, cachePilotMat);
		else if (actor instanceof Paratrooper)
			((Paratrooper)actor).prepareSkin(s, s2, cachePilotMat);
	}
	
	public void tryPreparePilotDefaultSkin(Aircraft aircraft, int i)
	{
		if (!Config.isUSE_RENDER())
			return;
		if (!Actor.isValid(aircraft))
			return;
		if (i < 0)
		{
			return;
		}
		else
		{
			String s = Aircraft.getPropertyMesh(aircraft.getClass(), aircraft.getRegiment().country());
			String s1 = HomePath.concatNames(s, "pilot" + (1 + i) + ".mat");
			Aircraft.prepareMeshPilot(aircraft.hierMesh(), i, s1, "3do/plane/textures/pilot" + (1 + i) + ".tga");
			return;
		}
	}
	
	public void setPilot(String s)
	{
		if (s == null)
			s = "";
		if (s.equals(ownerPilotBmp))
			return;
		ownerPilotBmp = s;
		localPilotBmp = null;
		localPilotTga = null;
		if (netPilotRequest != null)
		{
			netPilotRequest.doCancel();
			netPilotRequest = null;
		}
		if (isMaster())
		{
			if (s.length() > 0)
			{
				localPilotBmp = ownerPilotBmp;
				if (NetMissionTrack.isRecording())
					NetFilesTrack.recordFile(Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
			}
			else
			{
				localPilotBmp = null;
			}
		}
		else if (s.length() > 0 && (Config.cur.netSkinDownload || (masterChannel() instanceof NetChannelInStream)))
		{
			netPilotRequest = new NetFileRequest(this, Main.cur().netFileServerPilot, 150, this, ownerPilotBmp);
			netPilotRequest.doRequest();
		}
	}
	
	public void replicatePilot()
	{
		replicatePilot(null);
	}
	
	private void replicatePilot(NetChannel netchannel)
	{
		if (!isMirrored() && netchannel == null)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(12);
			netmsgguaranted.write255(ownerPilotBmp);
			if (netchannel == null)
				post(netmsgguaranted);
			else
				postTo(netchannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void checkReplicatePilot()
	{
		if (!isMaster())
			return;
		if (!"".equals(ownerPilotBmp))
			return;
		UserCfg usercfg = World.cur().userCfg;
		String s = usercfg.netPilot;
		if (s == null)
		{
			return;
		}
		else
		{
			setPilot(s);
			replicatePilot();
			return;
		}
	}
	
	public void tryPrepareNoseart(NetAircraft netaircraft)
	{
		if (!Config.isUSE_RENDER())
			return;
		if (!Actor.isValid(netaircraft))
			return;
		if (localNoseartBmp == null)
			return;
		if (localNoseartTga == null)
		{
			NetFileServerNoseart netfileservernoseart = Main.cur().netFileServerNoseart;
			String s;
			if (ownerNoseartBmp.equals(localNoseartBmp))
				s = netfileservernoseart.primaryPath() + "/" + localNoseartBmp;
			else
				s = netfileservernoseart.alternativePath() + "/" + localNoseartBmp;
			localNoseartTga = localNoseartBmp.substring(0, localNoseartBmp.length() - 4);
			if (!BmpUtils.bmp8PalTo2TGA4(s, "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".tga", "PaintSchemes/Cache/Noseart1" + localNoseartTga + ".tga"))
			{
				localNoseartTga = null;
				return;
			}
		}
		if (localNoseartTga == null)
		{
			return;
		}
		else
		{
			Aircraft.prepareMeshNoseart(netaircraft.hierMesh(), "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart1" + localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".tga",
					"PaintSchemes/Cache/Noseart1" + localNoseartTga + ".tga", cacheNoseartMat);
			return;
		}
	}
	
	public void setNoseart(String s)
	{
		if (s == null)
			s = "";
		if (s.equals(ownerNoseartBmp))
			return;
		ownerNoseartBmp = s;
		localNoseartBmp = null;
		localNoseartTga = null;
		if (netNoseartRequest != null)
		{
			netNoseartRequest.doCancel();
			netNoseartRequest = null;
		}
		if (isMaster())
		{
			if (s.length() > 0)
			{
				localNoseartBmp = ownerNoseartBmp;
				if (NetMissionTrack.isRecording())
					NetFilesTrack.recordFile(Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
			}
			else
			{
				localNoseartBmp = null;
			}
		}
		else if (s.length() > 0 && (Config.cur.netSkinDownload || (masterChannel() instanceof NetChannelInStream)))
		{
			netNoseartRequest = new NetFileRequest(this, Main.cur().netFileServerNoseart, 175, this, ownerNoseartBmp);
			netNoseartRequest.doRequest();
		}
	}
	
	public void replicateNoseart()
	{
		replicateNoseart(null);
	}
	
	private void replicateNoseart(NetChannel netchannel)
	{
		if (!isMirrored() && netchannel == null)
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(32);
			netmsgguaranted.write255(ownerNoseartBmp);
			if (netchannel == null)
				post(netmsgguaranted);
			else
				postTo(netchannel, netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public void checkReplicateNoseart(String s)
	{
		if (!isMaster())
			return;
		if (!"".equals(ownerNoseartBmp))
			return;
		UserCfg usercfg = World.cur().userCfg;
		String s1 = usercfg.getNoseart(s);
		if (s1 == null)
		{
			return;
		}
		else
		{
			setNoseart(s1);
			replicateNoseart();
			return;
		}
	}
	
	public String radio()
	{
		return radio;
	}
	
	public int curCodec()
	{
		return curCodec;
	}
	
	public boolean isRadioNone()
	{
		return radio == null;
	}
	
	public boolean isRadioCommon()
	{
		return " 0".equals(radio);
	}
	
	public boolean isRadioArmy()
	{
		if (radio == null)
			return false;
		if (radio.length() < 2)
			return false;
		if (radio.charAt(0) != ' ')
			return false;
		return radio.charAt(1) != '0';
	}
	
	public boolean isRadioPrivate()
	{
		return !isRadioNone() && !isRadioCommon() && !isRadioArmy();
	}
	
	public void setRadio(String s, int i)
	{
		replicateRadio(s, i);
	}
	
	public void radio_onCreated(String s)
	{
		if (!Chat.USE_NET_PHONE)
			return;
		if (radio != null && radio.equals(s))
			Chat.radioSpawn.set(radio);
	}
	
	private void radio_onArmyChanged()
	{
		if (isMirror())
			return;
		if (!isRadioArmy())
		{
			return;
		}
		else
		{
			replicateRadio(" " + getArmy(), 1);
			return;
		}
	}
	
	private void replicateRadio(String s, int i)
	{
		if (radio == s)
			return;
		if (s != null && s.equals(radio))
			return;
		radio = s;
		curCodec = i;
		if (!Chat.USE_NET_PHONE)
			return;
		if (Main.cur().netServerParams == null)
			return;
		if (Main.cur().netServerParams.isMaster() && radio != null && !Chat.radioSpawn.isExistChannel(radio))
			Chat.radioSpawn.create(radio, curCodec);
		if (isMaster())
			if (radio == null)
				Chat.radioSpawn.set(null);
			else if (Chat.radioSpawn.isExistChannel(radio))
				Chat.radioSpawn.set(radio);
		if (Main.cur().netServerParams.isMaster())
		{
			ArrayList arraylist = null;
			int j = Chat.radioSpawn.getNumChannels();
			for (int k = 0; k < j; k++)
			{
				String s1 = Chat.radioSpawn.getChannelName(k);
				if (!s1.equals(((NetUser)NetEnv.host()).radio()))
				{
					boolean flag = false;
					List list = NetEnv.hosts();
					for (int i1 = 0; i1 < list.size(); i1++)
					{
						NetUser netuser = (NetUser)list.get(i1);
						if (!s1.equals(netuser.radio))
							continue;
						flag = true;
						break;
					}
					
					if (!flag)
					{
						if (arraylist == null)
							arraylist = new ArrayList();
						arraylist.add(s1);
					}
				}
			}
			
			if (arraylist != null)
			{
				for (int l = 0; l < arraylist.size(); l++)
					Chat.radioSpawn.kill((String)arraylist.get(l));
				
			}
		}
		if (!isMirrored())
			return;
		try
		{
			NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
			netmsgguaranted.writeByte(21);
			if (radio != null)
			{
				netmsgguaranted.write255(radio);
				netmsgguaranted.writeInt(curCodec);
			}
			post(netmsgguaranted);
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	private void tryPostChatTimeSpeed()
	{
		if (!NetChannel.bCheckServerTimeSpeed)
			return;
		if (isDestroyed())
			return;
		if (Main.cur().chat == null)
			return;
		if (masterChannel() == null)
			return;
		try
		{
			if (masterChannel().isMirrored(Main.cur().chat))
			{
				ArrayList arraylist = new ArrayList(1);
				arraylist.add(this);
				Main.cur().chat.send(null, "checkTimeSpeed " + NetChannel.checkTimeSpeedInterval + "sec" + " " + (int)Math.round(NetChannel.checkTimeSpeedDifferense * 100D) + "%", arraylist, (byte)0, false);
			}
			else
			{
				new MsgAction(64, 1.0D, this)
				{
					
					public void doAction(Object obj)
					{
						NetUser netuser = (NetUser)obj;
						netuser.tryPostChatTimeSpeed();
					}
					
				};
			}
		}
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
		return;
	}
	
	public Aircraft findAircraft()
	{
		for (java.util.Map.Entry entry = Engine.name2Actor().nextEntry(null); entry != null; entry = Engine.name2Actor().nextEntry(entry))
		{
			Actor actor = (Actor)entry.getValue();
			if ((actor instanceof Aircraft) && ((Aircraft)actor).netUser() == this)
				return (Aircraft)actor;
		}
		
		return null;
	}
	
	public NetGunner findGunner()
	{
		for (java.util.Map.Entry entry = Engine.name2Actor().nextEntry(null); entry != null; entry = Engine.name2Actor().nextEntry(entry))
		{
			Actor actor = (Actor)entry.getValue();
			if ((actor instanceof NetGunner) && ((NetGunner)actor).getUser() == this)
				return (NetGunner)actor;
		}
		
		return null;
	}
	
	public void destroy()
	{
		super.destroy();
		if (isMirror() && NetEnv.isServer() && !((Connect)NetEnv.cur().connect).banned.isExist(shortName))
			Chat.sendLog(0, "user_leaves", shortName(), null);
		if (Actor.isValid(netUserRegiment))
			netUserRegiment.destroy();
		if (airNetFilter != null)
		{
			NetChannel netchannel = masterChannel();
			if (netchannel != null && !netchannel.isDestroying())
				netchannel.filterRemove(airNetFilter);
			airNetFilter = null;
		}
		if (Mission.isPlaying())
		{
			if (Mission.isCoop() && Time.current() > 1L)
				new NetUserLeft(uniqueName(), army, curstat);
			EventLog.onDisconnected(uniqueName());
		}
		
		//TODO: Modified by |ZUTI|
		//---------------------------------------------
		ZutiSupportMethods_NetSend.releaseCarrierSpawnPlace(this);
		//---------------------------------------------
	}
	
	public void msgNetNewChannel(NetChannel netchannel)
	{
		try
		{
			if (netchannel.isMirrored(this))
				return;
			int j = 0;
			if ((Main.cur() instanceof Main3D) && (Main3D.cur3D().isDemoPlaying()))
			{
				if (isMaster())
					if (NetMissionTrack.isPlaying())
						return;
					else
					{
						j = 1;
					}
			}
			
			NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
			netmsgspawn.write255(shortName);
			netmsgspawn.writeByte(bornPlace);
			netmsgspawn.writeByte(place);
			netmsgspawn.writeShort(idChannelFirst);
			if (isMirror())
			{
				if (path != null)
				{
					for (int i = 0; i < path.length; i++)
						netmsgspawn.writeNetObj(path[i]);
					
				}
				if (j == 0)
					netmsgspawn.writeNetObj(NetEnv.host());
			}
			else if (!NetEnv.isServer() && !bPingUpdateStarted)
			{
				bPingUpdateStarted = true;
				pingUpdateInc();
			}
			if ((netchannel instanceof NetChannelOutStream) && (Main.cur() instanceof Main3D) && Main3D.cur3D().isDemoPlaying() && bTrackWriter)
				netmsgspawn.writeBoolean(true);
			else
				netmsgspawn.writeBoolean(false);
			postTo(netchannel, netmsgspawn);
			if (!"".equals(netUserRegiment.ownerFileNameBmp))
				replicateNetUserRegiment(netchannel);
			if (!"".equals(ownerSkinBmp))
				replicateSkin(netchannel);
			if (!"".equals(ownerPilotBmp))
				replicatePilot(netchannel);
			if (!"".equals(ownerNoseartBmp))
				replicateNoseart(netchannel);
			if (radio != null)
			{
				NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
				netmsgguaranted.writeByte(21);
				netmsgguaranted.write255(radio);
				netmsgguaranted.writeInt(curCodec);
				postTo(netchannel, netmsgguaranted);
			}
			replicateDotRange(netchannel);
			
		}
		
		catch (Exception exception)
		{
			NetObj.printDebug(exception);
		}
	}
	
	public NetUser(String s)
	{
		super(s);
		stat = new NetUserStat();
		curstat = new NetUserStat();
		ping = 0;
		army = 0;
		bTrackWriter = false;
		bornPlace = -1;
		airdromeStay = -1;
		uniqueName = null;
		place = -1;
		bWaitStartCoopMission = false;
		localRequestPlace = -1;
		ownerSkinBmp = "";
		cacheSkinMat = new Mat[3];
		ownerPilotBmp = "";
		cachePilotMat = new Mat[1];
		ownerNoseartBmp = "";
		cacheNoseartMat = new Mat[2];
		radio = null;
		curCodec = 0;
		lastTimeUpdate = 0L;
		bPingUpdateStarted = false;
		makeUniqueName();
		netUserRegiment = new NetUserRegiment();
	}
	
	public NetUser(NetChannel netchannel, int i, String s, NetHost anethost[])
	{
		super(netchannel, i, s, anethost);
		stat = new NetUserStat();
		curstat = new NetUserStat();
		ping = 0;
		army = 0;
		bTrackWriter = false;
		bornPlace = -1;
		airdromeStay = -1;
		uniqueName = null;
		place = -1;
		bWaitStartCoopMission = false;
		localRequestPlace = -1;
		ownerSkinBmp = "";
		cacheSkinMat = new Mat[3];
		ownerPilotBmp = "";
		cachePilotMat = new Mat[1];
		ownerNoseartBmp = "";
		cacheNoseartMat = new Mat[2];
		radio = null;
		curCodec = 0;
		lastTimeUpdate = 0L;
		bPingUpdateStarted = false;
		if (NetEnv.isServer())
		{
			//TODO: Added by |ZUTI|: check if user has any refly time penalties...
			//----------------------------------------------------------------------
			String IP = netchannel.remoteAddress().getHostAddress();
			System.out.println("NetUser - Checking if user >" + s + " [" + IP + "]< has any refly/KIA time penalties...");
			if( ZutiSupportMethods.isUserPenalized(s, IP) )
			{
				kick(this);
				System.out.println("User '" + s + "' [" + netchannel.remoteAddress().getHostAddress() + "] has refly time penalties pending!");
			}
			//----------------------------------------------------------------------
			
			if (((Connect)NetEnv.cur().connect).banned.isExist(s))
			{
				kick(this);
				System.out.println("User '" + s + "' [" + netchannel.remoteAddress().getHostAddress() + "] banned");
			}
			else
			{
				Chat.sendLog(0, "user_joins", shortName(), null);
			}
			airNetFilter = new AircraftNetFilter();
			netchannel.filterAdd(airNetFilter);
			_st.clear();
			((NetUser)NetEnv.host()).replicateStat(this, false, netchannel);
		}
		makeUniqueName();
		netUserRegiment = new NetUserRegiment();
		if (NetEnv.isServer())
		{
			System.out.println("socket channel '" + netchannel.id() + "', ip " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort() + ", " + uniqueName() + ", is complete created");
			new MsgAction(64, 1.0D, this)
			{
				
				public void doAction(Object obj)
				{
					NetUser netuser = (NetUser)obj;
					netuser.tryPostChatTimeSpeed();
				}
				
			};
		}
		EventLog.onConnected(uniqueName());
	}
	
	static
	{
		Spawn.add(com.maddox.il2.net.NetUser.class, new SPAWN());
	}
	
	//TODO: |ZUTI| methods
	//-----------------------------------------
	public void zutiSetAirdomeStay(int value)
	{
		airdromeStay = value;
	}
}