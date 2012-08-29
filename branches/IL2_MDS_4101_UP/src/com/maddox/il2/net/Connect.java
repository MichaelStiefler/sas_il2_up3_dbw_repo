/*4.10.1 compatible class*/
package com.maddox.il2.net;

import java.util.List;
import java.util.StringTokenizer;

import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Finger;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgNet;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

public class Connect implements NetConnect, MsgNetExtListener, MsgTimeOutListener
{
	static final boolean bLog = false;
	static final long TIME_OUT = 500L;
	static final long FULL_TIME_OUT = 30000L;
	public static final String PROMPT = "socket";
	public static final String VERSION = "IL2_UP3_RC_MDS_v1.2_v4";
	static final String CONNECT = "connect";
	static final String CONNECTED = "connected";
	static final String REJECT = "reject";
	static final String REQUESTINFO = "rinfo";
	static final String ANSWERINFO = "ainfo";
	public NetBanned banned = new NetBanned();
	boolean bBindEnable = false;
	boolean bJoin = false;
	int joinId;
	long joinTimeOut;
	NetSocket joinSocket;
	NetAddress joinAddr;
	int joinPort;
	int joinStamp;
	private static NetMsgInput _netMsgInput = new NetMsgInput();
	private MsgTimeOut ticker;

	public void bindEnable(boolean bool)
	{
		bBindEnable = bool;
	}

	public boolean isBindEnable()
	{
		return bBindEnable;
	}

	private static String badVersionMessage()
	{
		return "Server uses a different version of the game (4.10.1m + MDS v1.2 for UP v1.2RC2).";
	}

	private void bindReceiveConnect(StringTokenizer stringtokenizer, NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (Main.cur().netServerParams != null && stringtokenizer.hasMoreTokens())
		{
			String string = stringtokenizer.nextToken();
			if (stringtokenizer.hasMoreTokens())
			{
				String string_0_ = stringtokenizer.nextToken();
				if (stringtokenizer.hasMoreTokens())
				{
					String string_1_ = stringtokenizer.nextToken();
					if (!Connect.VERSION.equals(string) && !"il2_r01_0f".equals(string))
					{
						String string_2_ = ("reject " + string_0_ + " " + string_1_ + " " + badVersionMessage());
						NetEnv.cur().postExtUTF((byte) 32, string_2_, netsocket, netaddress, i);
					}
					else if (!banned.isExist(netaddress))
					{
						int i_3_;
						try
						{
							i_3_ = Integer.parseInt(string_0_);
						}
						catch (Exception exception)
						{
							return;
						}
						int i_5_;
						try
						{
							i_5_ = Integer.parseInt(string_1_);
						}
						catch (Exception exception)
						{
							return;
						}
						NetChannel netchannel = null;
						List list = NetEnv.channels();
						int i_6_ = list.size();
						for (int i_7_ = 0; i_7_ < i_6_; i_7_++)
						{
							NetChannel netchannel_8_ = (NetChannel) list.get(i_7_);
							if (netchannel_8_.socket().equals(netsocket) && netchannel_8_.remoteId() == i_3_ && netchannel_8_.remoteAddress().equals(netaddress) && netchannel_8_.remotePort() == i)
							{
								if (netchannel_8_.state() == 1 && netchannel_8_.getInitStamp() == i_5_)
									netchannel = netchannel_8_;
								else
								{
									netchannel_8_.destroy("Reconnect user");
									return;
								}
							}
						}
						if (netchannel == null)
						{
							if (!isBindEnable() || netsocket.maxChannels == 0)
							{
								String string_9_ = ("reject " + string_0_ + " " + i_5_ + " connect disabled");
								NetEnv.cur().postExtUTF((byte) 32, string_9_, netsocket, netaddress, i);
								return;
							}
							if (netsocket.maxChannels <= netsocket.countChannels)
							{
								String string_10_ = ("reject " + string_0_ + " " + i_5_ + " limit connections = " + netsocket.maxChannels);
								NetEnv.cur().postExtUTF((byte) 32, string_10_, netsocket, netaddress, i);
								return;
							}
							int i_11_ = NetEnv.hosts().size();
							if (!Main.cur().netServerParams.isDedicated()) i_11_++;
							if (i_11_ >= Main.cur().netServerParams.getMaxUsers())
							{
								String string_12_ = ("reject " + string_0_ + " " + i_5_ + " limit users = " + Main.cur().netServerParams.getMaxUsers());
								NetEnv.cur().postExtUTF((byte) 32, string_12_, netsocket, netaddress, i);
								return;
							}
							int i_13_ = NetEnv.cur().nextIdChannel(true);
							netchannel = NetEnv.cur().createChannel(1, i_13_, i_3_, netsocket, netaddress, i, this);
							netchannel.setInitStamp(i_5_);
							setChannel(netchannel, i_13_, i_3_, i_5_);
							netsocket.countChannels++;
							if (!Connect.VERSION.equals(string)) kickChannel(netchannel);
						}
						String string_14_ = ("connected " + string + " " + i_3_ + " " + i_5_ + " " + netchannel.id());
						NetEnv.cur().postExtUTF((byte) 32, string_14_, netsocket, netaddress, i);
					}
				}
			}
		}
	}

	private void kickChannel(Object object)
	{
		if (object instanceof NetChannel)
		{
			NetChannel netchannel = (NetChannel) object;
			if (!netchannel.isDestroying())
			{
				if (netchannel.isReady())
					netchannel.destroy(badVersionMessage());
				else if (netchannel.isIniting()) new MsgAction(64, 0.5, netchannel)
				{
					public void doAction(Object object_17_)
					{
						Connect.this.kickChannel(object_17_);
					}
				};
			}
		}
	}

	public void join(NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (!bJoin)
		{
			joinSocket = netsocket;
			joinAddr = netaddress;
			joinPort = i;
			joinTimeOut = 30000L;
			joinId = NetEnv.cur().nextIdChannel(false);
			joinStamp = Time.raw();
			joinSocket.countChannels++;
			joinSend();
			bJoin = true;
			if (!ticker.busy()) ticker.post(Time.currentReal() + 500L);
		}
	}

	public void msgTimeOut(Object object)
	{
		if (object != null && object instanceof NetChannel)
			msgTimeOutStep((NetChannel) object);
		else if (bJoin)
		{
			joinTimeOut -= 500L;
			if (joinTimeOut < 0L)
			{
				System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " failed: timeout");
				if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: Timeout.");
				joinSocket.countChannels--;
				bJoin = false;
			}
			else
			{
				joinSend();
				ticker.post(Time.currentReal() + 500L);
			}
		}
	}

	public void joinBreak()
	{
		if (bJoin)
		{
			System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " breaked");
			if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: User Cancel.");
			joinSocket.countChannels--;
			bJoin = false;
		}
	}

	public boolean isJoinProcess()
	{
		return bJoin;
	}

	private void joinSend()
	{
		String string = "connect " + Connect.VERSION + " " + joinId + " " + joinStamp;
		NetEnv.cur().postExtUTF((byte) 32, string, joinSocket, joinAddr, joinPort);
	}

	private void joinReceiveConnected(StringTokenizer stringtokenizer, NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (bJoin && netsocket.equals(joinSocket) && netaddress.equals(joinAddr) && i == joinPort && stringtokenizer.hasMoreTokens())
		{
			String string = stringtokenizer.nextToken();
			if (Connect.VERSION.equals(string) && stringtokenizer.hasMoreTokens())
			{
				String string_18_ = stringtokenizer.nextToken();
				int i_19_;
				try
				{
					i_19_ = Integer.parseInt(string_18_);
				}
				catch (Exception exception)
				{
					return;
				}
				if (i_19_ == joinId && stringtokenizer.hasMoreTokens())
				{
					String string_20_ = stringtokenizer.nextToken();
					int i_22_;
					try
					{
						i_22_ = Integer.parseInt(string_20_);
					}
					catch (Exception exception)
					{
						return;
					}
					if (i_22_ == joinStamp && stringtokenizer.hasMoreTokens())
					{
						String string_23_ = stringtokenizer.nextToken();
						int i_25_;
						try
						{
							i_25_ = Integer.parseInt(string_23_);
						}
						catch (Exception exception)
						{
							return;
						}
						System.out.println("socket start connecting to " + joinAddr.getHostAddress() + ":" + joinPort);
						NetChannel netchannel = NetEnv.cur().createChannel(7, joinId, i_25_, joinSocket, joinAddr, joinPort, this);
						netchannel.setInitStamp(i_22_);
						setChannel(netchannel, i_25_, joinId, i_22_);
						bJoin = false;
					}
				}
			}
		}
	}

	private void joinReceiveReject(StringTokenizer stringtokenizer, NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (bJoin && netsocket.equals(joinSocket) && netaddress.equals(joinAddr) && i == joinPort && stringtokenizer.hasMoreTokens())
		{
			String string = stringtokenizer.nextToken();
			int i_26_;
			try
			{
				i_26_ = Integer.parseInt(string);
			}
			catch (Exception exception)
			{
				return;
			}
			if (i_26_ == joinId && stringtokenizer.hasMoreTokens())
			{
				String string_27_ = stringtokenizer.nextToken();
				String string_28_ = "???";
				StringBuffer stringbuffer = new StringBuffer();
				do
				{
					try
					{
						int i_30_ = Integer.parseInt(string_27_);
						if (i_30_ == joinStamp) break;
					}
					catch (Exception exception)
					{
						stringbuffer.append(string_27_);
						stringbuffer.append(' ');
						string_28_ = string_27_;
						break;
					}
					return;
				}
				while (false);
				if (stringtokenizer.hasMoreTokens())
				{
					while (stringtokenizer.hasMoreTokens())
					{
						stringbuffer.append(stringtokenizer.nextToken());
						stringbuffer.append(' ');
					}
					string_28_ = stringbuffer.toString();
				}
				System.out.println("socket join to " + joinAddr.getHostAddress() + ":" + joinPort + " regect (" + string_28_ + ")");
				if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host rejected.  Reason: " + string_28_);
				joinSocket.countChannels--;
				bJoin = false;
			}
		}
	}

	public void msgNetExt(byte[] is, NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (is != null && is.length >= 2 && is[0] == 32)
		{
			String string = "";
			try
			{
				_netMsgInput.setData(null, false, is, 1, is.length - 1);
				string = _netMsgInput.readUTF();
			}
			catch (Exception exception)
			{
				return;
			}
			StringTokenizer stringtokenizer = new StringTokenizer(string, " ");
			if (stringtokenizer.hasMoreTokens())
			{
				String string_31_ = stringtokenizer.nextToken();
				if (string_31_.equals("connect"))
					bindReceiveConnect(stringtokenizer, netsocket, netaddress, i);
				else if (string_31_.equals("connected"))
					joinReceiveConnected(stringtokenizer, netsocket, netaddress, i);
				else if (string_31_.equals("reject"))
					joinReceiveReject(stringtokenizer, netsocket, netaddress, i);
				else if (string_31_.equals("rinfo")) receiveRequestInfo(stringtokenizer, netsocket, netaddress, i);
			}
		}
	}

	public void msgRequest(String string)
	{
		if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelRequest(string);
	}

	public void channelCreated(NetChannel netchannel)
	{
		if (!netchannel.isPublic())
			System.out.println("socket channel '" + netchannel.id() + "' created: " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
		else
		{
			System.out.println("socket channel '" + netchannel.id() + "' start creating: " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
			netchannel.startSortGuaranted();
			HashMapInt hashmapint = NetEnv.cur().objects;
			for (HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
			{
				NetObj netobj = (NetObj) hashmapintentry.getValue();
				if (!netchannel.isMirrored(netobj)) MsgNet.postRealNewChannel(netobj, netchannel);
			}
			netchannel.setStateInit(2);
			MsgTimeOut.post(64, Time.currentReal() + 1L, this, netchannel);
		}
	}

	private void msgTimeOutStep(NetChannel netchannel)
	{
		if (!netchannel.isDestroying())
		{
			int i = netchannel.state();
			switch (i)
			{
			case 2:
				try
				{
					netchannel.stopSortGuaranted();
				}
				catch (Exception exception)
				{
					netchannel.destroy("Cycle inits");
					System.out.println(exception.getMessage());
					exception.printStackTrace();
					break;
				}
				netchannel.setStateInit(3);
				/* fall through */
			case 3:
				if (Main.cur().netServerParams == null)
					MsgTimeOut.post(64, Time.currentReal() + 200L, this, netchannel);
				else
				{
					netchannel.setStateInit(0);
					if (!NetEnv.isServer()) 
						System.out.println("socket channel '" + netchannel.id() + "', ip " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort() + ", is complete created");
					if (Main.cur().netChannelListener != null)
						Main.cur().netChannelListener.netChannelCreated(netchannel);
					break;
				}
				break;
			}
		}
	}

	public void channelNotCreated(NetChannel netchannel, String string)
	{
		System.out.println("socket channel NOT created (" + string + "): " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort());
		if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelCanceled("Connection attempt to remote host failed.  Reason: " + string);
	}

	public void channelDestroying(NetChannel netchannel, String string)
	{
		System.out.println("socketConnection with " + netchannel.remoteAddress() + ":" + netchannel.remotePort() + " on channel " + netchannel.id() + " lost.  Reason: " + string);
		if (Main.cur().netChannelListener != null) Main.cur().netChannelListener.netChannelDestroying(netchannel, ("The communication with the remote host is lost. Reason: " + string));
	}

	private void receiveRequestInfo(StringTokenizer stringtokenizer, NetSocket netsocket, NetAddress netaddress, int i)
	{
		if (stringtokenizer.hasMoreTokens() && Main.cur().netServerParams != null && isBindEnable() && !banned.isExist(netaddress) && (Main.cur().netServerParams.isMaster() || (Main.cur().netServerParams.masterChannel().userState != -1)))
		{
			String string = stringtokenizer.nextToken();
			StringBuffer stringbuffer = new StringBuffer();
			stringbuffer.append("ainfo");
			stringbuffer.append(' ');
			stringbuffer.append(string);
			stringbuffer.append(' ');
			stringbuffer.append(Connect.VERSION);
			stringbuffer.append(' ');
			stringbuffer.append(Main.cur().netServerParams.isMaster() ? "1 " : "0 ");
			stringbuffer.append("" + (Main.cur().netServerParams.getType() >> 4 & 0x7) + " ");
			stringbuffer.append(Main.cur().netServerParams.isProtected() ? "1 " : "0 ");
			stringbuffer.append(Main.cur().netServerParams.isDedicated() ? "1 " : "0 ");
			stringbuffer.append(Main.cur().netServerParams.isCoop() ? "1 " : "0 ");
			stringbuffer.append(Mission.isPlaying() ? "1 " : "0 ");
			stringbuffer.append(netsocket.maxChannels);
			stringbuffer.append(' ');
			stringbuffer.append(netsocket.countChannels);
			stringbuffer.append(' ');
			stringbuffer.append(Main.cur().netServerParams.getMaxUsers());
			stringbuffer.append(' ');
			int i_32_ = NetEnv.hosts().size();
			if (!Main.cur().netServerParams.isDedicated()) i_32_++;
			stringbuffer.append(i_32_);
			stringbuffer.append(' ');
			stringbuffer.append(Main.cur().netServerParams.serverName());
			String string_33_ = stringbuffer.toString();
			NetEnv.cur().postExtUTF((byte) 32, string_33_, netsocket, netaddress, i);
		}
	}

	public Connect()
	{
		MsgAddListener.post(64, NetEnv.cur(), this, null);
		ticker = new MsgTimeOut();
		ticker.setNotCleanAfterSend();
		ticker.setFlags(64);
		ticker.setListener(this);
	}

	private void setChannel(NetChannel netchannel, int i, int i_34_, int i_35_)
	{
		int i_36_ = i_35_ + i + i_34_;
		if (i_36_ < 0) i_36_ = -i_36_;
		int i_37_ = i_36_ % 16 + 12;
		int i_38_ = i_36_ % Finger.kTable.length;
		if (i_37_ < 0) i_37_ = -i_37_ % 16;
		if (i_37_ < 10) i_37_ = 10;
		if (i_38_ < 0) i_38_ = -i_38_ % Finger.kTable.length;
		byte[] is = new byte[i_37_];
		for (int i_39_ = 0; i_39_ < i_37_; i_39_++)
			is[i_39_] = Finger.kTable[(i_38_ + i_39_) % Finger.kTable.length];
		netchannel.swTbl = is;
		for (int i_40_ = 0; i_40_ < 2; i_40_++)
			netchannel.crcInit[i_40_] = (Finger.kTable[(i_38_ + i_37_ + i_40_) % Finger.kTable.length]);
	}
}