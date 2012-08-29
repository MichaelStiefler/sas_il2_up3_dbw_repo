package com.maddox.il2.net;

import com.maddox.il2.ai.World;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Spawn;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.RadioChannelSpawn;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Chat extends NetObj
{
  public static boolean USE_NET_PHONE = true;
  public static RadioChannelSpawn radioSpawn = new RadioChannelSpawn();
  public ResourceBundle resOrder;
  public ResourceBundle resLog;
  public List buf = new ArrayList();
  public int stampUpdate = 0;

  private int maxBufLen = 80;
  private Object[] params = new Object[2];

  public int getMaxBuflen() { return this.maxBufLen; } 
  public void setMaxBufLen(int paramInt) {
    if (paramInt < 1) paramInt = 1;
    this.maxBufLen = paramInt;
    if (this.buf.size() > this.maxBufLen)
      this.stampUpdate += 1;
    while (this.buf.size() > this.maxBufLen)
      this.buf.remove(this.buf.size() - 1); 
  }

  public void clear() {
    this.buf.clear();
    this.stampUpdate += 1;
  }

  private ChatMessage translateMsg(ChatMessage paramChatMessage)
  {
    Object localObject1;
    if (paramChatMessage.flags == 1) {
      if (this.resOrder == null)
        this.resOrder = ResourceBundle.getBundle("i18n/hud_order", RTSConf.cur.locale, LDRres.loader());
      localObject1 = new StringTokenizer(paramChatMessage.msg);
      StringBuffer localStringBuffer = new StringBuffer();
      int j = 1;
      while (((StringTokenizer)localObject1).hasMoreTokens()) {
        if (j == 0) localStringBuffer.append(" > ");
        j = 0;
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        Object localObject3 = null;
        String str2 = World.getPlayerLastCountry();
        if (str2 != null)
          try {
            localObject3 = this.resOrder.getString((String)localObject2 + "_" + str2);
          } catch (Exception localException2) {
          }
        if (localObject3 == null) {
          try {
            localObject3 = this.resOrder.getString((String)localObject2);
          } catch (Exception localException3) {
            localObject3 = localObject2;
          }
        }
        localStringBuffer.append((String)localObject3);
      }
      Object localObject2 = new ChatMessage();
      ((ChatMessage)localObject2).flags = paramChatMessage.flags;
      ((ChatMessage)localObject2).from = paramChatMessage.from;
      ((ChatMessage)localObject2).to = paramChatMessage.to;
      ((ChatMessage)localObject2).msg = localStringBuffer.toString();
      paramChatMessage = (ChatMessage)localObject2;
    }
    else if ((paramChatMessage.flags & 0xE) != 0) {
      if (this.resLog == null)
        this.resLog = ResourceBundle.getBundle("i18n/netmessages", RTSConf.cur.locale, LDRres.loader());
      localObject1 = new ChatMessage();
      ((ChatMessage)localObject1).flags = paramChatMessage.flags;
      ((ChatMessage)localObject1).from = paramChatMessage.from;
      ((ChatMessage)localObject1).to = paramChatMessage.to;
      int i = (paramChatMessage.flags & 0xE) >> 1;
      switch (i) {
      case 2:
        if (paramChatMessage.param0 == null) break;
        this.params[0] = ((NetUser)paramChatMessage.param0).shortName(); break;
      case 3:
        if (paramChatMessage.param0 != null)
          this.params[0] = ((NetUser)paramChatMessage.param0).shortName();
        if (paramChatMessage.param1 == null) break;
        this.params[1] = ((NetUser)paramChatMessage.param1).shortName(); break;
      case 6:
        this.params[0] = paramChatMessage.param0;
        break;
      case 7:
        this.params[0] = paramChatMessage.param0;
        this.params[1] = paramChatMessage.param1;
        break;
      case 4:
      case 5:
      }
      String str1 = null;
      try {
        str1 = this.resLog.getString(paramChatMessage.msg);
      } catch (Exception localException1) {
        str1 = paramChatMessage.msg;
      }
      ((ChatMessage)localObject1).msg = MessageFormat.format(str1, this.params);
      paramChatMessage = (ChatMessage)localObject1;
       tmp511_510 = null; this.params[1] = tmp511_510; this.params[0] = tmp511_510;
    }
    return (ChatMessage)(ChatMessage)(ChatMessage)paramChatMessage;
  }

  private void addMsg(ChatMessage paramChatMessage) {
    paramChatMessage = translateMsg(paramChatMessage);
    this.buf.add(0, paramChatMessage);
    this.stampUpdate += 1;
    while (this.buf.size() > this.maxBufLen) {
      this.buf.remove(this.buf.size() - 1);
    }
    if (paramChatMessage.from != null)
      System.out.println("Chat: " + paramChatMessage.from.shortName() + ": \t" + paramChatMessage.msg);
    else
      System.out.println("Chat: --- " + paramChatMessage.msg);
  }

  public static void sendLogRnd(int paramInt, String paramString, Aircraft paramAircraft1, Aircraft paramAircraft2) {
    sendLog(paramInt, paramString + (int)(Math.random() * 2.0D + 1.4D), paramAircraft1, paramAircraft2);
  }

  public static void sendLog(int paramInt, String paramString, Aircraft paramAircraft1, Aircraft paramAircraft2) {
    if (Main.cur().chat == null) return;
    if (Main.cur().netServerParams == null) return;
    if (paramInt > Main.cur().netServerParams.autoLogDetail()) return;
    NetUser localNetUser1 = null;
    if ((paramAircraft1 != null) && (paramAircraft1.isNetPlayer()))
      localNetUser1 = paramAircraft1.netUser();
    NetUser localNetUser2 = null;
    if ((paramAircraft2 != null) && (paramAircraft2.isNetPlayer()))
      localNetUser2 = paramAircraft2.netUser();
    sendLog(paramInt, paramString, localNetUser1, localNetUser2);
  }

  public static void sendLog(int paramInt, String paramString, NetUser paramNetUser1, NetUser paramNetUser2) {
    if (Main.cur().chat == null) return;
    if (Main.cur().netServerParams == null) return;
    if (paramInt > Main.cur().netServerParams.autoLogDetail()) return;

    int i = 2;
    if (paramNetUser1 != null)
      i = 4;
    if (paramNetUser2 != null)
      i = 6;
    Main.cur().chat.send(null, paramString, null, (byte)i, paramNetUser1, paramNetUser2, true);
  }

  public static void sendLog(int paramInt, String paramString1, String paramString2, String paramString3) {
    if (Main.cur().chat == null) return;
    if (Main.cur().netServerParams == null) return;
    if (paramInt > Main.cur().netServerParams.autoLogDetail()) return;
    int i = 2;
    if (paramString2 != null)
      i = 12;
    if (paramString3 != null)
      i = 14;
    Main.cur().chat.send(null, paramString1, null, (byte)i, paramString2, paramString3, true);
  }

  public void send(NetHost paramNetHost, String paramString, List paramList) {
    send(paramNetHost, paramString, paramList, 0);
  }

  public void send(NetHost paramNetHost, String paramString, List paramList, byte paramByte) {
    send(paramNetHost, paramString, paramList, paramByte, true);
  }

  public void send(NetHost paramNetHost, String paramString, List paramList, byte paramByte, boolean paramBoolean) {
    send(paramNetHost, paramString, paramList, paramByte, null, null, paramBoolean);
  }

  public void send(NetHost paramNetHost, String paramString, List paramList, byte paramByte, Object paramObject1, Object paramObject2, boolean paramBoolean) {
    if (NetMissionTrack.isPlaying()) return;
    ChatMessage localChatMessage = new ChatMessage();
    localChatMessage.flags = paramByte;
    localChatMessage.from = paramNetHost;
    localChatMessage.to = paramList;
    if (paramString.length() > 80)
      paramString = paramString.substring(0, 80);
    int i = NetMsgOutput.len255(paramString);
    if (paramList != null)
      i += paramList.size() * NetMsgOutput.netObjReferenceLen();
    if (i > 250) {
      i -= 250;
      paramString = paramString.substring(0, paramString.length() - i);
    }
    localChatMessage.msg = paramString;
    localChatMessage.param0 = paramObject1;
    localChatMessage.param1 = paramObject2;

    if (paramBoolean) {
      addMsg(localChatMessage);
    }
    if ((isMirror()) || (isMirrored()))
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeNetObj(localChatMessage.from);
        localNetMsgGuaranted.writeByte(localChatMessage.flags);
        localNetMsgGuaranted.write255(localChatMessage.msg);
        int j;
        if ((localChatMessage.flags & 0xE) != 0) {
          j = (localChatMessage.flags & 0xE) >> 1;
          switch (j) {
          case 2:
            localNetMsgGuaranted.writeNetObj((NetObj)paramObject1);
            break;
          case 3:
            localNetMsgGuaranted.writeNetObj((NetObj)paramObject1);
            localNetMsgGuaranted.writeNetObj((NetObj)paramObject2);
            break;
          case 6:
            localNetMsgGuaranted.write255((String)paramObject1);
            break;
          case 7:
            localNetMsgGuaranted.write255((String)paramObject1);
            localNetMsgGuaranted.write255((String)paramObject2);
            break;
          case 4:
          case 5:
          }
        }
        if (localChatMessage.to != null) {
          for (j = 0; j < localChatMessage.to.size(); j++)
            localNetMsgGuaranted.writeNetObj((NetObj)localChatMessage.to.get(j));
        }
        postExclude(null, localNetMsgGuaranted); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
    paramNetMsgInput.reset();
    ChatMessage localChatMessage = new ChatMessage();
    localChatMessage.from = ((NetHost)paramNetMsgInput.readNetObj());
    localChatMessage.flags = (byte)paramNetMsgInput.readUnsignedByte();
    localChatMessage.msg = paramNetMsgInput.read255();
    int i = 0;
    if ((localChatMessage.flags & 0xE) != 0) {
      j = (localChatMessage.flags & 0xE) >> 1;
      switch (j) {
      case 2:
        localChatMessage.param0 = paramNetMsgInput.readNetObj();
        i = 1;
        break;
      case 3:
        localChatMessage.param0 = paramNetMsgInput.readNetObj();
        localChatMessage.param1 = paramNetMsgInput.readNetObj();
        i = 2;
        break;
      case 6:
        localChatMessage.param0 = paramNetMsgInput.read255();
        break;
      case 7:
        localChatMessage.param0 = paramNetMsgInput.read255();
        localChatMessage.param1 = paramNetMsgInput.read255();
        break;
      case 4:
      case 5:
      }
    }
    int j = 0;
    int k = paramNetMsgInput.available() / NetMsgInput.netObjReferenceLen();
    if (k == 0) {
      j = 1;
    } else {
      localChatMessage.to = new ArrayList(k);
      for (m = 0; m < k; m++)
        localChatMessage.to.add(paramNetMsgInput.readNetObj());
      if ((paramNetMsgInput.channel() instanceof NetChannelInStream)) {
        NetUser localNetUser = NetUser.findTrackWriter();
        j = (localNetUser == localChatMessage.from) || (localChatMessage.to.indexOf(localNetUser) >= 0) ? 1 : 0;
      } else {
        j = localChatMessage.to.indexOf(NetEnv.host()) >= 0 ? 1 : 0;
      }
    }
    if (j != 0) {
      addMsg(localChatMessage);
    }
    int m = 0;
    if ((isMirror()) && (paramNetMsgInput.channel() != masterChannel()))
      m = 1;
    if (isMirrored()) {
      m += countMirrors();
      if (paramNetMsgInput.channel() != masterChannel())
        m--;
    }
    if (m > 0)
      postExclude(paramNetMsgInput.channel(), new NetMsgGuaranted(paramNetMsgInput, k + i + 1));
    return true;
  }

  public void destroy()
  {
    if (USE_NET_PHONE) {
      AudioDevice.endNetPhone();
      radioSpawn.killMasterChannels();
    }
    super.destroy();
    Main.cur().chat = null;
  }

  public Chat() {
    super(null);
    Main.cur().chat = this;
    if (USE_NET_PHONE) {
      AudioDevice.beginNetPhone();
      String str = ((NetUser)NetEnv.host()).radio();
      int i = ((NetUser)NetEnv.host()).curCodec();
      ((NetUser)NetEnv.host()).setRadio(null, 0);
      ((NetUser)NetEnv.host()).setRadio(str, i);
    }
  }

  public Chat(NetChannel paramNetChannel, int paramInt) {
    super(null, paramNetChannel, paramInt);
    Main.cur().chat = this;
    if ((USE_NET_PHONE) && 
      (!NetMissionTrack.isPlaying()))
      AudioDevice.beginNetPhone();
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException
  {
    return new NetMsgSpawn(this);
  }
  static {
    Spawn.add(Chat.class, new SPAWN());
  }
  static class SPAWN implements NetSpawn {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        new Chat(paramNetMsgInput.channel(), paramInt); } catch (Exception localException) {
        Chat.access$000(localException);
      }
    }
  }
}