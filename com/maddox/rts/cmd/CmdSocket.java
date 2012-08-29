package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetControlLock;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdSocket extends Cmd
{
  public static final String CREATE = "CREATE";
  public static final String DESTROY = "DESTROY";
  public static final String MAXCHANNELS = "CHANNELS";
  public static final String MAXSPEED = "SPEED";
  public static final String LOCALHOST = "LOCALHOST";
  public static final String LOCALPORT = "LOCALPORT";
  public static final String HOST = "HOST";
  public static final String PORT = "PORT";
  public static final String LISTENER = "LISTENER";
  public static final String JOIN = "JOIN";
  public static final String BREAK = "BREAK";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Cmd.nargs(paramMap, "_$$") != 1)
    {
      if (Cmd.exist(paramMap, "LISTENER")) {
        if (NetEnv.cur().connect == null) {
          ERR_HARD("Listener object not available");
          return null;
        }
        if (Cmd.nargs(paramMap, "LISTENER") == 0)
          INFO_HARD(" Listener is " + (NetEnv.cur().connect.isBindEnable() ? "enable" : "disable"));
        else {
          NetEnv.cur().connect.bindEnable(Cmd.arg(paramMap, "LISTENER", 0, 0) != 0);
        }
        return NetEnv.cur().connect;
      }

      if ((Cmd.exist(paramMap, "JOIN")) && (paramCmdEnv.levelAccess() == 0)) {
        if (NetEnv.cur().connect == null) {
          ERR_HARD("Join object not available");
          return null;
        }
        if (Cmd.exist(paramMap, "BREAK"))
          NetEnv.cur().connect.joinBreak();
        else {
          INFO_HARD(" join " + (NetEnv.cur().connect.isJoinProcess() ? "is processed" : "not active"));
        }
        return NetEnv.cur().connect;
      }

      localObject1 = new ArrayList();
      if (Property.vars((List)localObject1, "netProtocol")) {
        INFO_HARD("The availablis protocols:");
        int i = ((ArrayList)localObject1).size();
        for (int j = 0; j < i; j++) {
          String str = (String)((ArrayList)localObject1).get(j);
          localObject3 = getProtocolClass(str);
          int k = Property.intValue((Class)localObject3, "maxChannels", 64);
          double d1 = Property.doubleValue((Class)localObject3, "maxSpeed", 10.0D);
          INFO_HARD("  " + str + " maxChannels: " + k + " maxSpeed: " + (int)(d1 * 1000.0D) + " bytes/sec");
        }

        return localObject1;
      }
      INFO_HARD("There are no availablis protocols");
      return null;
    }

    Object localObject1 = Cmd.arg(paramMap, "_$$", 0);
    if (localObject1 == null) {
      ERR_HARD("Unknown name of the protocol");
      return null;
    }

    Class localClass1 = getProtocolClass((String)localObject1);
    if (localClass1 == null) {
      return null;
    }
    Object localObject2 = null;
    try {
      localObject2 = (NetSocket)localClass1.newInstance();
    } catch (Exception localException1) {
      ERR_HARD("The class of the protocol is not 'NetSocket': " + localClass1.getName());
      return null;
    }

    Class localClass2 = ((NetSocket)localObject2).addressClass();

    Object localObject3 = null;
    if (Cmd.nargs(paramMap, "LOCALHOST") > 0)
      try {
        localObject3 = (NetAddress)localClass2.newInstance();

        ((NetAddress)localObject3).create(Cmd.arg(paramMap, "LOCALHOST", 0));
      }
      catch (Exception localException2)
      {
        ERR_HARD("Bad LOCALHOST: " + Cmd.arg(paramMap, "LOCALHOST", 0));
        return null;
      }
    else {
      try {
        localObject3 = (NetAddress)localClass2.newInstance();
        localObject3 = ((NetAddress)localObject3).getLocalHost();
      } catch (Exception localException3) {
        ERR_HARD("Bad LocalHost address");
        return null;
      }
    }

    NetAddress localNetAddress = null;
    if (Cmd.nargs(paramMap, "HOST") > 0) {
      try {
        localNetAddress = (NetAddress)localClass2.newInstance();
        localNetAddress.create(Cmd.arg(paramMap, "HOST", 0));
      } catch (Exception localException4) {
        ERR_HARD("Bad HOST: " + Cmd.arg(paramMap, "HOST", 0));
        return null;
      }
    }

    Exception localException5 = Cmd.arg(paramMap, "LOCALPORT", 0, -1);
    Exception localException6 = Cmd.arg(paramMap, "PORT", 0, -1);
    int m = Cmd.arg(paramMap, "CHANNELS", 0, Property.intValue(localClass1, "maxChannels", 64));
    double d2 = Property.doubleValue(localClass1, "maxSpeed", 10.0D);
    if (Cmd.exist(paramMap, "SPEED")) {
      d2 = Cmd.arg(paramMap, "SPEED", 0, d2 * 1000.0D) / 1000.0D;
    }
    if (Cmd.exist(paramMap, "CREATE")) {
      localObject4 = localObject3;
      if (localObject4 == null) localObject4 = localNetAddress;
      int n = localException5;
      if (n == -1) n = localException6;
      NetSocket localNetSocket1 = findSocket((NetAddress)localObject4, n);
      if (localNetSocket1 != null) {
        if ((localNetSocket1.maxChannels == 0) || (Cmd.exist(paramMap, "CHANNELS")))
          localNetSocket1.maxChannels = m;
        if (Cmd.exist(paramMap, "SPEED"))
          localNetSocket1.setMaxSpeed(d2);
        INFO_SOFT("Socket alredy exist");
        return localNetSocket1;
      }
      try {
        if (n == -1) ((NetSocket)localObject2).open(0, (NetAddress)localObject4); else
          ((NetSocket)localObject2).open(n, (NetAddress)localObject4);
      } catch (Exception localException8) {
        ERR_HARD(localException8.toString());
        return null;
      }
      ((NetSocket)localObject2).maxChannels = m;
      ((NetSocket)localObject2).setMaxSpeed(d2);
      NetEnv.addSocket((NetSocket)localObject2);
      return localObject2;
    }
    Object localObject5;
    if (Cmd.exist(paramMap, "DESTROY")) {
      localObject4 = findSocket((NetAddress)localObject3, localException5);
      if (localObject4 == null) {
        ERR_HARD("Socket not found");
        return null;
      }
      List localList1;
      int i2;
      int i4;
      if (Cmd.exist(paramMap, "HOST")) {
        localList1 = NetEnv.channels();
        i2 = localList1.size();
        for (i4 = 0; i4 < i2; i4++) {
          localObject5 = (NetChannel)localList1.get(i4);
          if ((!localObject4.equals(((NetChannel)localObject5).socket())) || (!((NetChannel)localObject5).remoteAddress().equals(localNetAddress)) || (((NetChannel)localObject5).remotePort() != localException6)) {
            continue;
          }
          ((NetChannel)localObject5).destroy();
        }
      }
      else {
        ((NetSocket)localObject4).maxChannels = 0;
        localList1 = NetEnv.channels();
        i2 = localList1.size();
        for (i4 = 0; i4 < i2; i4++) {
          localObject5 = (NetChannel)localList1.get(i4);
          if (localObject4.equals(((NetChannel)localObject5).socket()))
            ((NetChannel)localObject5).destroy();
        }
      }
      return localObject4;
    }
    if ((Cmd.exist(paramMap, "JOIN")) && (paramCmdEnv.levelAccess() == 0)) {
      if (NetEnv.cur().connect == null) {
        ERR_HARD("Join object not available");
        return null;
      }
      if ((NetEnv.cur().control != null) && ((NetEnv.cur().control instanceof NetControlLock)))
      {
        ERR_HARD("Previous join not ended");
        return null;
      }
      if (NetEnv.cur().connect.isJoinProcess()) {
        ERR_HARD("Previous join not ended");
        return null;
      }

      if (localException6 == -1) localException6 = localException5;
      if (localException6 == -1) {
        ERR_HARD("PORT not defined");
        return null;
      }

      if (Cmd.nargs(paramMap, "HOST") != 1) {
        ERR_HARD("HOST not defined");
        return null;
      }

      localObject4 = findSocket((NetAddress)localObject3, localException5);
      if (localObject4 != null) {
        localObject2 = localObject4;
        if (Cmd.exist(paramMap, "CHANNELS"))
          ((NetSocket)localObject4).maxChannels = m;
        if (Cmd.exist(paramMap, "SPEED"))
          ((NetSocket)localObject4).setMaxSpeed(d2);
      } else {
        try {
          ((NetSocket)localObject2).open(localException5 == -1 ? 0 : localException5, (NetAddress)localObject3);
        } catch (Exception localException7) {
          ERR_HARD(localException7.toString());
          return null;
        }
        ((NetSocket)localObject2).maxChannels = m;
        ((NetSocket)localObject2).setMaxSpeed(d2);
        NetEnv.addSocket((NetSocket)localObject2);
      }
      NetEnv.cur().connect.join((NetSocket)localObject2, localNetAddress, localException6);
      return localObject2;
    }
    if ((Cmd.exist(paramMap, "CHANNELS")) || (Cmd.exist(paramMap, "SPEED"))) {
      localObject4 = localObject3;
      if (localObject4 == null) localObject4 = localNetAddress;
      localException7 = localException5;
      if (localException7 == -1) localException7 = localException6;
      NetSocket localNetSocket2 = findSocket((NetAddress)localObject4, localException7);
      if (localNetSocket2 == null) {
        if (Cmd.exist(paramMap, "CHANNELS"))
          Property.set(localClass1, "maxChannels", m);
        if (Cmd.exist(paramMap, "SPEED"))
          Property.set(localClass1, "maxSpeed", d2);
        return null;
      }
      if (Cmd.exist(paramMap, "CHANNELS"))
        localNetSocket2.maxChannels = m;
      if (Cmd.exist(paramMap, "SPEED"))
        localNetSocket2.setMaxSpeed(d2);
      return localNetSocket2;
    }

    Object localObject4 = NetEnv.socketsBlock();
    int i1 = ((List)localObject4).size();
    int i7;
    for (int i3 = 0; i3 < i1; i3++) {
      NetSocket localNetSocket3 = (NetSocket)((List)localObject4).get(i3);
      INFO_HARD(" " + localNetSocket3.getLocalAddress() + ":" + localNetSocket3.getLocalPort() + " " + localNetSocket3.countChannels + "(" + localNetSocket3.maxChannels + ") " + (int)(localNetSocket3.getMaxSpeed() * 1000.0D) + " bytes/sec");

      localObject5 = NetEnv.channels();
      int i6 = ((List)localObject5).size();
      for (i7 = 0; i7 < i6; i7++) {
        NetChannel localNetChannel1 = (NetChannel)((List)localObject5).get(i7);
        if (localNetSocket3.equals(localNetChannel1.socket())) {
          INFO_HARD("  " + localNetChannel1.id() + ":" + (localNetChannel1.isInitRemote() ? " <- " : " -> ") + localNetChannel1.remoteAddress() + ":" + localNetChannel1.remotePort() + " " + (int)(localNetChannel1.getMaxSpeed() * 1000.0D) + " bytes/sec");
        }
      }
    }
    localObject4 = NetEnv.socketsNoBlock();
    i1 = ((List)localObject4).size();
    for (int i5 = 0; i5 < i1; i5++) {
      localObject5 = (NetSocket)((List)localObject4).get(i5);
      INFO_HARD(" " + ((NetSocket)localObject5).getLocalAddress() + ":" + ((NetSocket)localObject5).getLocalPort() + " " + ((NetSocket)localObject5).countChannels + "(" + ((NetSocket)localObject5).maxChannels + ") " + (int)(((NetSocket)localObject5).getMaxSpeed() * 1000.0D) + " bytes/sec");

      List localList2 = NetEnv.channels();
      i7 = localList2.size();
      for (int i8 = 0; i8 < i7; i8++) {
        NetChannel localNetChannel2 = (NetChannel)localList2.get(i8);
        if (localObject5.equals(localNetChannel2.socket())) {
          INFO_HARD("  " + localNetChannel2.id() + ":" + (localNetChannel2.isInitRemote() ? " <- " : " -> ") + localNetChannel2.remoteAddress() + ":" + localNetChannel2.remotePort() + " " + (int)(localNetChannel2.getMaxSpeed() * 1000.0D) + " bytes/sec");
        }
      }

    }

    return CmdEnv.RETURN_OK;
  }

  private NetSocket findSocket(NetAddress paramNetAddress, int paramInt) {
    List localList = NetEnv.socketsBlock();
    int i = localList.size();
    int j;
    NetSocket localNetSocket;
    if (paramNetAddress == null)
      for (j = 0; j < i; j++) {
        localNetSocket = (NetSocket)localList.get(j);
        if (paramInt == localNetSocket.getLocalPort())
          return localNetSocket;
      }
    else {
      for (j = 0; j < i; j++) {
        localNetSocket = (NetSocket)localList.get(j);
        if ((paramNetAddress.equals(localNetSocket.getLocalAddress())) && (paramInt == localNetSocket.getLocalPort()))
          return localNetSocket;
      }
    }
    localList = NetEnv.socketsNoBlock();
    i = localList.size();
    if (paramNetAddress == null)
      for (j = 0; j < i; j++) {
        localNetSocket = (NetSocket)localList.get(j);
        if (paramInt == localNetSocket.getLocalPort())
          return localNetSocket;
      }
    else {
      for (j = 0; j < i; j++) {
        localNetSocket = (NetSocket)localList.get(j);
        if ((paramNetAddress.equals(localNetSocket.getLocalAddress())) && (paramInt == localNetSocket.getLocalPort())) {
          return localNetSocket;
        }
      }
    }
    return null;
  }

  private Class getProtocolClass(String paramString) {
    String str = Property.stringValue(paramString, "className", null);
    if (str == null) {
      ERR_HARD("The class of the protocol is not found");
      return null;
    }

    str = "com.maddox.rts.net." + str;
    Class localClass = null;
    try {
      localClass = Class.forName(str);
    } catch (Exception localException) {
      ERR_HARD("The class of the protocol is not found: " + str);
      return null;
    }
    return localClass;
  }

  public CmdSocket()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("CREATE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CHANNELS", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SPEED", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("DESTROY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOCALHOST", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LOCALPORT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("HOST", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PORT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LISTENER", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("JOIN", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("BREAK", null);
    this._properties.put("NAME", "socket");
    this._levelAccess = 1;
  }
}