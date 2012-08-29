package com.maddox.il2.net;

import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.MsgInvokeMethod;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.rts.net.IPAddress;
import java.io.PrintStream;
import java.util.List;

public class GameSpy
  implements MsgNetExtListener, MsgTimeOutListener
{
  public String userName = null;
  public String serverIP = null;
  public String roomName = null;
  public String gameType = "dogfight";
  public int maxClients = 16;
  private static final String gamename = "il2sturmovikfb";
  private static final String gamever = "4.10m";
  private static final String secret_key = "h53Ew8";
  public static int MASTER_PORT = 27900;
  public static String MASTER_ADDR = "master.gamespy.com";
  private static final int MAX_DATA_SIZE = 1400;
  private static final int FIRST_HB_TIME = 30000;
  private static final int HB_TIME = 300000;
  private static final int MAX_FIRST_COUNT = 10;
  private int queryid = 1;
  private int packetnumber = 0;
  private int qport = 0;
  private int no_query = 1;
  private String room;
  private StringBuffer strBuf = new StringBuffer();

  private boolean _bGameModeWait = true;

  private int[] armyScore = new int[Army.amountNet()];

  private char[] _encrypted_val = new char[''];
  private char[] _encoded_val = new char['È'];
  private MsgTimeOut ticker;
  private NetSocket master_socket;
  private NetAddress master_address;
  private NetSocket in_socket;
  private NetAddress in_address;
  private int in_port;
  private StringBuffer outBuf = new StringBuffer();
  private byte[] _outBuf = new byte[1500];

  private static final String[] queries = { "basic", "info", "rules", "players", "status", "packets", "echo", "secure" };
  private static final int qtbasic = 0;
  private static final int qtinfo = 1;
  private static final int qtrules = 2;
  private static final int qtplayers = 3;
  private static final int qtstatus = 4;
  private static final int qtpackets = 5;
  private static final int qtecho = 6;
  private static final int qtsecure = 7;
  private StringBuffer queryBuf = new StringBuffer();
  private int queryVal_0;
  private int queryVal_1;
  int[] _trip = new int[3];
  int[] _kwart = new int[4];

  private char[] _state = new char[256];

  public boolean isServer()
  {
    return this.serverIP == null;
  }

  public void sendStatechanged()
  {
    send_heartbeat(1);
  }

  public void sendExiting() {
    send_heartbeat(2);
  }

  private void correctASCII()
  {
    int i = this.strBuf.length();
    for (int j = 0; j < i; j++) {
      int k = this.strBuf.charAt(j);
      if ((k & 0x7F) != k)
        this.strBuf.setCharAt(j, '?');
    }
  }

  private void send_basic()
  {
    this.strBuf.append("\\gamename\\"); this.strBuf.append("il2sturmovikfb");
    this.strBuf.append("\\gamever\\"); this.strBuf.append("4.10m");
    this.strBuf.append("\\location\\"); this.strBuf.append(1);
    buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
  }

  private void send_info()
  {
    this.strBuf.append("\\hostname\\"); this.strBuf.append(Main.cur().netServerParams.serverName());
    correctASCII();
    this.strBuf.append("\\hostport\\");
    if (isListenerOnly()) this.strBuf.append(Config.cur.netLocalPort); else
      this.strBuf.append(this.qport);
    if ((Mission.cur() != null) && (Mission.cur().name() != null)) {
      this.strBuf.append("\\mapname\\"); this.strBuf.append(Mission.cur().name());
    }this.strBuf.append("\\gametype\\");
    if (Main.cur().netServerParams.isCoop())
      this.strBuf.append("coop");
    else if (Main.cur().netServerParams.isDogfight())
      this.strBuf.append("dogfight");
    else if (Main.cur().netServerParams.isSingle())
      this.strBuf.append("single");
    int i = NetEnv.hosts().size();
    if (!Main.cur().netServerParams.isDedicated())
      i++;
    this.strBuf.append("\\numplayers\\"); this.strBuf.append(i);
    this.strBuf.append("\\maxplayers\\"); this.strBuf.append(Main.cur().netServerParams.getMaxUsers());
    String str = "unknown";
    if (RTSConf.isRequestExitApp()) {
      str = "exiting";
    }
    else if (Main.cur().netServerParams.isCoop()) {
      if (NetEnv.cur().connect.isBindEnable()) { str = "wait"; this._bGameModeWait = true; } else {
        str = "closedplaying"; this._bGameModeWait = false;
      }
    } else if (Main.cur().netServerParams.isDogfight()) {
      if (Mission.isPlaying()) { str = "openplaying"; this._bGameModeWait = false; } else {
        str = "wait"; this._bGameModeWait = true;
      }
    } else if (Main.cur().netServerParams.isSingle()) {
      str = "closedplaying"; this._bGameModeWait = false;
    }

    this.strBuf.append("\\gamemode\\"); this.strBuf.append(str);
    if (this.room != null) {
      this.strBuf.append("\\groupid\\"); this.strBuf.append(this.room);
    }
    buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
  }

  private void send_rules()
  {
    DifficultySettings localDifficultySettings = World.cur().diffCur;
    this.strBuf.append("\\SeparateEStart\\"); this.strBuf.append(localDifficultySettings.SeparateEStart ? "1" : "0");
    this.strBuf.append("\\ComplexEManagement\\"); this.strBuf.append(localDifficultySettings.ComplexEManagement ? "1" : "0");
    this.strBuf.append("\\EngineOverheat\\"); this.strBuf.append(localDifficultySettings.Engine_Overheat ? "1" : "0");
    this.strBuf.append("\\TorqueGyroEffects\\"); this.strBuf.append(localDifficultySettings.Torque_N_Gyro_Effects ? "1" : "0");
    this.strBuf.append("\\FlutterEffect\\"); this.strBuf.append(localDifficultySettings.Flutter_Effect ? "1" : "0");
    this.strBuf.append("\\WindTurbulence\\"); this.strBuf.append(localDifficultySettings.Wind_N_Turbulence ? "1" : "0");
    this.strBuf.append("\\StallsSpins\\"); this.strBuf.append(localDifficultySettings.Stalls_N_Spins ? "1" : "0");
    this.strBuf.append("\\Vulnerability\\"); this.strBuf.append(localDifficultySettings.Vulnerability ? "1" : "0");
    this.strBuf.append("\\BlackoutsRedouts\\"); this.strBuf.append(localDifficultySettings.Blackouts_N_Redouts ? "1" : "0");
    this.strBuf.append("\\RealisticGunnery\\"); this.strBuf.append(localDifficultySettings.Realistic_Gunnery ? "1" : "0");
    this.strBuf.append("\\LimitedAmmo\\"); this.strBuf.append(localDifficultySettings.Limited_Ammo ? "1" : "0");
    this.strBuf.append("\\LimitedFuel\\"); this.strBuf.append(localDifficultySettings.Limited_Fuel ? "1" : "0");
    this.strBuf.append("\\CockpitAlwaysOn\\"); this.strBuf.append(localDifficultySettings.Cockpit_Always_On ? "1" : "0");
    this.strBuf.append("\\NoOutsideViews\\"); this.strBuf.append(localDifficultySettings.No_Outside_Views ? "1" : "0");
    this.strBuf.append("\\HeadShake\\"); this.strBuf.append(localDifficultySettings.Head_Shake ? "1" : "0");
    this.strBuf.append("\\NoIcons\\"); this.strBuf.append(localDifficultySettings.No_Icons ? "1" : "0");
    this.strBuf.append("\\NoPadlock\\"); this.strBuf.append(localDifficultySettings.No_Padlock ? "1" : "0");
    this.strBuf.append("\\Clouds\\"); this.strBuf.append(localDifficultySettings.Clouds ? "1" : "0");
    this.strBuf.append("\\NoInstantSuccess\\"); this.strBuf.append(localDifficultySettings.NoInstantSuccess ? "1" : "0");
    this.strBuf.append("\\TakeoffLanding\\"); this.strBuf.append(localDifficultySettings.Takeoff_N_Landing ? "1" : "0");
    this.strBuf.append("\\RealisticLandings\\"); this.strBuf.append(localDifficultySettings.Realistic_Landings ? "1" : "0");
    this.strBuf.append("\\NoMapIcons\\"); this.strBuf.append(localDifficultySettings.No_Map_Icons ? "1" : "0");
    this.strBuf.append("\\NoMinimapPath\\"); this.strBuf.append(localDifficultySettings.NoMinimapPath ? "1" : "0");
    this.strBuf.append("\\NoSpeedBar\\"); this.strBuf.append(localDifficultySettings.NoSpeedBar ? "1" : "0");

    this.strBuf.append("\\Reliability\\"); this.strBuf.append(localDifficultySettings.Reliability ? "1" : "0");
    this.strBuf.append("\\GLimits\\"); this.strBuf.append(localDifficultySettings.G_Limits ? "1" : "0");
    this.strBuf.append("\\RealisticPilotVulnerability\\"); this.strBuf.append(localDifficultySettings.RealisticPilotVulnerability ? "1" : "0");
    this.strBuf.append("\\RealisticNavigationInstruments\\"); this.strBuf.append(localDifficultySettings.RealisticNavigationInstruments ? "1" : "0");
    this.strBuf.append("\\NoPlayerIcon\\"); this.strBuf.append(localDifficultySettings.No_Player_Icon ? "1" : "0");
    this.strBuf.append("\\NoFogOfWarIcons\\"); this.strBuf.append(localDifficultySettings.No_Fog_Of_War_Icons ? "1" : "0");

    if (Main.cur().netServerParams.isProtected())
      this.strBuf.append("\\password\\1");
    buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
  }

  private void send_player(int paramInt, NetUser paramNetUser) {
    this.strBuf.append("\\player_" + paramInt + "\\"); this.strBuf.append(paramNetUser.shortName());
    this.strBuf.append("\\score_" + paramInt + "\\"); this.strBuf.append((int)paramNetUser.stat().score);
    this.strBuf.append("\\ping_" + paramInt + "\\"); this.strBuf.append(paramNetUser.ping);
    this.strBuf.append("\\team_" + paramInt + "\\"); this.strBuf.append(paramNetUser.getArmy());
    int i = paramNetUser.getArmy();
    if (this.armyScore[i] < 0)
      this.armyScore[i] = 0;
    this.armyScore[i] += (int)paramNetUser.stat().score;
  }

  private void send_players()
  {
    for (int i = 0; i < this.armyScore.length; i++) {
      this.armyScore[i] = -1;
    }
    i = 0;
    if (!Main.cur().netServerParams.isDedicated())
      send_player(i++, (NetUser)NetEnv.host());
    List localList = NetEnv.hosts();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      send_player(i++, (NetUser)localList.get(k));
    }
    for (k = 0; k < this.armyScore.length; k++) {
      if (this.armyScore[k] >= 0) {
        this.strBuf.append("\\team_t" + k + "\\" + Army.name(k));
        this.strBuf.append("\\score_t" + k + "\\" + this.armyScore[k]);
      }
    }
    correctASCII();
    buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
  }

  private void send_echo(String paramString)
  {
    if (paramString.length() > 1350)
      return;
    this.strBuf.append("\\echo\\"); this.strBuf.append(paramString);
    buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
  }

  private void send_final(String paramString)
  {
    if (paramString != null) {
      int i = paramString.length();
      if (i > 128) return;
      paramString.getChars(0, i, this._encrypted_val, 0);
      gs_encrypt("h53Ew8", this._encrypted_val, i);
      int j = gs_encode(this._encrypted_val, i, this._encoded_val);
      this.strBuf.append("\\validate\\"); this.strBuf.append(this._encoded_val, 0, j);
      buffer_send(this.strBuf);
      this.strBuf.delete(0, this.strBuf.length());
    }
    this.strBuf.append("\\final\\"); buffer_send(this.strBuf);
    this.strBuf.delete(0, this.strBuf.length());
    packet_send();
  }

  public void msgTimeOut(Object paramObject)
  {
    int i = 300000;
    if ((this.master_socket != null) && (this.master_address != null) && (MASTER_PORT != 0)) {
      send_heartbeat(0);
      if (this.no_query > 0) {
        this.no_query += 1;
        if (this.no_query > 10)
          this.no_query = 0;
        else
          i = 30000;
      }
    }
    else {
      i = 30000;
    }
    this.ticker.post(Time.currentReal() + i);
  }

  public void set(String paramString, NetSocket paramNetSocket, int paramInt)
  {
    if (paramString != null)
      this.room = paramString;
    this.master_socket = paramNetSocket;
    this.qport = paramInt;
    if ((this.master_socket != null) && (this.master_address == null)) {
      try {
        IPAddress localIPAddress = new IPAddress();
        localIPAddress.create(MASTER_ADDR);
        this.master_address = localIPAddress;
      } catch (Exception localException) {
        System.out.println("Unknown master address: " + MASTER_ADDR);
      }

      if (this.ticker == null) {
        this.ticker = new MsgTimeOut();
        this.ticker.setNotCleanAfterSend();
        this.ticker.setListener(this);
        this.ticker.setFlags(64);
      }
      if (!this.ticker.busy()) {
        this.ticker.post();
      }
      NetEnv.cur().msgAddListener(this, null);
      onMsgTimeout();
    }
  }

  public void setListenerOnly(String paramString) {
    this.room = paramString;
    NetEnv.cur().msgAddListener(this, null);
  }

  public boolean isListenerOnly() {
    return Main.cur().netGameSpyListener == this;
  }

  public void onMsgTimeout() {
    new MsgInvokeMethod("onMsgTimeout").post(64, this, 5.0D);
    if ((Main.cur() != null) && (Main.cur().netServerParams != null) && (NetEnv.cur() != null) && (NetEnv.cur().connect != null))
    {
      int i;
      if (Main.cur().netServerParams.isCoop()) {
        if (NetEnv.cur().connect.isBindEnable()) i = 1; else
          i = 0;
      }
      else if (Mission.isPlaying()) i = 0; else {
        i = 1;
      }
      if (i != this._bGameModeWait)
        sendStatechanged();
    }
  }

  private void send_heartbeat(int paramInt)
  {
    if ((this.master_socket == null) || (this.master_address == null) || (MASTER_PORT == 0))
      return;
    this.outBuf.append("\\heartbeat\\"); this.outBuf.append(this.qport);
    this.outBuf.append("\\gamename\\"); this.outBuf.append("il2sturmovikfb");
    if (paramInt != 0) {
      this.outBuf.append("\\statechanged\\"); this.outBuf.append(paramInt);
    }

    int i = this.outBuf.length();
    for (int j = 0; j < i; j++)
      this._outBuf[j] = (byte)(this.outBuf.charAt(j) & 0xFF);
    this.outBuf.delete(0, i);

    RTSConf.cur.netEnv.postExt(this._outBuf, 0, i, this.master_socket, this.master_address, MASTER_PORT);
  }

  private void packet_send()
  {
    if (this.outBuf.length() == 0)
      return;
    this.packetnumber += 1;
    this.outBuf.append("\\queryid\\"); this.outBuf.append(this.queryid); this.outBuf.append("."); this.outBuf.append(this.packetnumber);

    int i = this.outBuf.length();
    for (int j = 0; j < i; j++)
      this._outBuf[j] = (byte)(this.outBuf.charAt(j) & 0xFF);
    this.outBuf.delete(0, i);

    RTSConf.cur.netEnv.postExt(this._outBuf, 0, i, this.in_socket, this.in_address, this.in_port);
  }

  private void strcat(StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) {
    strcat(paramStringBuffer1, paramStringBuffer2, 0, paramStringBuffer2.length());
  }
  private void strcat(StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2, int paramInt1, int paramInt2) {
    for (int i = 0; i < paramInt2; i++)
      paramStringBuffer1.append(paramStringBuffer2.charAt(paramInt1 + i));
  }

  private void buffer_send(StringBuffer paramStringBuffer)
  {
    buffer_send(paramStringBuffer, 0, paramStringBuffer.length());
  }
  private void buffer_send(StringBuffer paramStringBuffer, int paramInt1, int paramInt2) {
    if (this.outBuf.length() + paramInt2 < 1350) {
      strcat(this.outBuf, paramStringBuffer, paramInt1, paramInt2);
    }
    else if (paramInt2 > 1350) {
      int i = 0;
      int j = 0;
      int k = 0;
      while (i < 1350) {
        if ('\\' == paramStringBuffer.charAt(paramInt1 + i)) {
          if (k % 2 == 0)
            j = i;
          k++;
        }
        i++;
      }
      if (j == 0)
        return;
      buffer_send(paramStringBuffer, 0, j);
      buffer_send(paramStringBuffer, j, paramInt2 - j);
    } else {
      packet_send();
      strcat(this.outBuf, paramStringBuffer, paramInt1, paramInt2);
    }
  }

  private void setQuery(byte[] paramArrayOfByte)
  {
    this.queryBuf.delete(0, this.queryBuf.length());
    for (int i = 0; i < paramArrayOfByte.length; i++)
      this.queryBuf.append((char)paramArrayOfByte[i]);
  }

  private boolean isExistQueryKey(int paramInt) {
    String str = queries[paramInt];
    int i = this.queryBuf.length();
    int j = str.length();
    int k = i - j;
    int m = 0;
    while (m <= k) {
      int n = this.queryBuf.charAt(m++);
      if (n == 92) {
        int i1 = 0;
        while (i1 < j) {
          int i2 = str.charAt(i1);
          n = this.queryBuf.charAt(m++);
          if (n == 92) {
            m--;
            break;
          }
          if (n != i2)
            break;
          i1++;
        }
        if (i1 == j) {
          this.queryVal_0 = (this.queryVal_1 = 0);
          if (m == i) return true;
          n = this.queryBuf.charAt(m++);
          if (n != 92) break;
          if (m == i) return true;
          this.queryVal_0 = m;
          while (m < i) {
            n = this.queryBuf.charAt(m++);
            if (n != 92) continue;
          }
          this.queryVal_1 = (m - 1);
          return true;
        }
      }
    }
    return false;
  }
  private String getQueryValue() {
    return this.queryBuf.substring(this.queryVal_0, this.queryVal_1);
  }

  public void msgNetExt(byte[] paramArrayOfByte, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt) {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 1)) return;
    if ((char)paramArrayOfByte[0] != '\\') return;
    if (Main.cur().netServerParams == null) return;
    if (((Connect)NetEnv.cur().connect).banned.isExist(paramNetAddress)) {
      return;
    }
    this.in_socket = paramNetSocket;
    this.in_address = paramNetAddress;
    this.in_port = paramInt;
    String str = null;

    this.queryid += 1;
    this.packetnumber = 0;
    if (this.no_query > 0) {
      this.no_query = 0;
    }
    this.outBuf.delete(0, this.outBuf.length());
    setQuery(paramArrayOfByte);

    for (int i = 0; i <= 7; i++) {
      if (isExistQueryKey(i)) {
        switch (i) {
        case 0:
          send_basic();
          break;
        case 1:
          send_info();
          break;
        case 2:
          send_rules();
          break;
        case 3:
          send_players();
          break;
        case 4:
          send_basic();
          send_info();
          send_rules();
          send_players();
          break;
        case 5:
          send_basic(); packet_send();
          send_info(); packet_send();
          send_rules(); packet_send();
          send_players();
          break;
        case 6:
          send_echo(getQueryValue());
          break;
        case 7:
          str = getQueryValue();
        }
      }

    }

    send_final(str);
    this.in_socket = null;
    this.in_address = null;
  }

  private int encode_ct(int paramInt)
  {
    if (paramInt < 26) return 65 + paramInt;
    if (paramInt < 52) return 97 + paramInt - 26;
    if (paramInt < 62) return 48 + paramInt - 52;
    if (paramInt == 62) return 43;
    if (paramInt == 63) return 47;
    return 0;
  }

  private int gs_encode(char[] paramArrayOfChar1, int paramInt, char[] paramArrayOfChar2)
  {
    int k = 0;
    int m = 0;

    int i = 0;
    while (i < paramInt) {
      for (int j = 0; j <= 2; i++) {
        if (i < paramInt) this._trip[j] = paramArrayOfChar1[(k++)]; else
          this._trip[j] = 0;
        j++;
      }

      this._kwart[0] = (this._trip[0] >> 2);
      this._kwart[1] = (((this._trip[0] & 0x3) << 4) + (this._trip[1] >> 4));
      this._kwart[2] = (((this._trip[1] & 0xF) << 2) + (this._trip[2] >> 6));
      this._kwart[3] = (this._trip[2] & 0x3F);
      for (j = 0; j <= 3; j++)
        paramArrayOfChar2[(m++)] = (char)(encode_ct(this._kwart[j]) & 0xFF);
    }
    return m;
  }

  private void gs_encrypt(String paramString, char[] paramArrayOfChar, int paramInt)
  {
    int i = paramString.length();

    for (int j = 0; j < 256; j++) this._state[j] = (char)j;

    int k = 0; int m = 0;
    int i1;
    for (j = 0; j < 256; j++) {
      m = (paramString.charAt(k) + this._state[j] + m) % 256;
      k = (k + 1) % i;
      i1 = this._state[m];
      this._state[m] = this._state[j];
      this._state[j] = i1;
    }

    k = 0; m = 0;
    for (j = 0; j < paramInt; j++) {
      k = (k + paramArrayOfChar[j] + 1) % 256;
      m = (this._state[k] + m) % 256;
      i1 = this._state[m];
      this._state[m] = this._state[k];
      this._state[k] = i1;
      int n = (this._state[k] + this._state[m]) % 256;
      int tmp222_220 = j;
      char[] tmp222_219 = paramArrayOfChar; tmp222_219[tmp222_220] = (char)(tmp222_219[tmp222_220] ^ this._state[n]);
      int tmp237_235 = j;
      char[] tmp237_234 = paramArrayOfChar; tmp237_234[tmp237_235] = (char)(tmp237_234[tmp237_235] & 0xFF);
    }
  }
}