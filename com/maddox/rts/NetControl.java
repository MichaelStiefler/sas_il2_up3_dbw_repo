package com.maddox.rts;

import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.util.List;

public final class NetControl extends NetObj
{
  public static final int MSG_NEW_CLIENT = 0;
  public static final int MSG_ASK = 1;
  public static final int MSG_NAK = 2;
  public static final int MSG_REQUEST = 3;
  public static final int MSG_ANSWER = 4;

  public void doAnswer(String paramString)
  {
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(4);
      localNetMsgGuaranted.writeInt(-1);
      localNetMsgGuaranted.write255(paramString);
      localNetMsgGuaranted.writeNetObj(null);
      postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void doRequest(NetObj paramNetObj, int paramInt, String paramString) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(3);
      if (paramNetObj != null) {
        localNetMsgGuaranted.writeInt(paramInt);
        localNetMsgGuaranted.write255(paramString);
        localNetMsgGuaranted.writeNetObj(paramNetObj);
        postTo(paramNetObj.masterChannel(), localNetMsgGuaranted);
      } else if (paramInt != -1) {
        NetChannel localNetChannel = (NetChannel)NetEnv.cur().indxChannels.get(paramInt);
        localNetMsgGuaranted.writeInt(-1);
        localNetMsgGuaranted.writeNetObj(null);
        localNetMsgGuaranted.write255(paramString);
        postTo(localNetChannel, localNetMsgGuaranted);
      } else {
        NetEnv.cur().connect.msgRequest(paramString);
      }
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void doNak(NetObj paramNetObj, int paramInt, String paramString) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(2);
      if (paramNetObj != null) {
        localNetMsgGuaranted.writeInt(paramInt);
        localNetMsgGuaranted.write255(paramString);
        localNetMsgGuaranted.writeNetObj(paramNetObj);
        postTo(paramNetObj.masterChannel(), localNetMsgGuaranted);
      } else if (paramInt != -1) {
        NetChannel localNetChannel = (NetChannel)NetEnv.cur().indxChannels.get(paramInt);
        localNetMsgGuaranted.writeInt(-1);
        localNetMsgGuaranted.writeNetObj(null);
        localNetMsgGuaranted.write255(paramString);
        postTo(localNetChannel, localNetMsgGuaranted);
        localNetChannel.destroy(paramString);
      } else {
        this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel.destroy(paramString);
      }
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void doAsk(NetObj paramNetObj, int paramInt) {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(5);
      localNetMsgGuaranted.writeByte(1);
      if (paramNetObj != null) {
        localNetMsgGuaranted.writeInt(paramInt);
        localNetMsgGuaranted.writeNetObj(paramNetObj);
        postTo(paramNetObj.masterChannel(), localNetMsgGuaranted);
      } else if (paramInt != -1) {
        NetChannel localNetChannel = (NetChannel)NetEnv.cur().indxChannels.get(paramInt);
        localNetMsgGuaranted.writeInt(-1);
        localNetMsgGuaranted.writeNetObj(null);
        postTo(localNetChannel, localNetMsgGuaranted);
        localNetChannel.controlStartInit();
      } else {
        this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel.controlStartInit();
      }
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void msgNet(NetMsgInput paramNetMsgInput) {
    try {
      int i = paramNetMsgInput.readByte();
      int j = paramNetMsgInput.readInt();
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Object localObject;
      String str1;
      if (isMaster()) {
        if (localNetObj == null)
          j = paramNetMsgInput.channel().id();
        if (superObj() != null) {
          if ((superObj() instanceof NetControlReal)) {
            localObject = (NetControlReal)superObj();
            switch (i) {
            case 0:
              str1 = null;
              int k = 0;
              if (localNetObj == null) {
                str1 = paramNetMsgInput.channel().remoteAddress().getHostAddress();
                if (str1 == null) str1 = "0";
                k = paramNetMsgInput.channel().remotePort();
              } else {
                NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramNetMsgInput.read255());
                str1 = localNumberTokenizer.next("0");
                k = localNumberTokenizer.next(0);
              }
              ((NetControlReal)localObject).msgNewClient(localNetObj, j, str1, k);
              break;
            case 4:
              ((NetControlReal)localObject).msgAnswer(localNetObj, j, paramNetMsgInput.read255());
              break;
            default:
              break;
            }
          }
        }
        else switch (i) {
          case 0:
            doAsk(localNetObj, j);
            break;
          default:
            break;
          } 
      }
      else
      {
        switch (i) {
        case 0:
          if (localNetObj == null) {
            localObject = new NetMsgGuaranted();
            ((NetMsgGuaranted)localObject).writeByte(i);
            ((NetMsgGuaranted)localObject).writeInt(paramNetMsgInput.channel().id());
            ((NetMsgGuaranted)localObject).writeNetObj(NetEnv.host());
            str1 = paramNetMsgInput.channel().remoteAddress().getHostAddress();
            if (str1 == null)
              str1 = "0";
            String str2 = str1 + " " + paramNetMsgInput.channel().remotePort();

            ((NetMsgGuaranted)localObject).write255(str2);
            postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, (NetMsgGuaranted)localObject);
          } else {
            postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, new NetMsgGuaranted(paramNetMsgInput, 1));
          }
          break;
        case 4:
          if (localNetObj == null) {
            localObject = new NetMsgGuaranted(5);
            ((NetMsgGuaranted)localObject).writeByte(i);
            ((NetMsgGuaranted)localObject).writeInt(paramNetMsgInput.channel().id());
            ((NetMsgGuaranted)localObject).write255(paramNetMsgInput.read255());
            ((NetMsgGuaranted)localObject).writeNetObj(NetEnv.host());
            postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, (NetMsgGuaranted)localObject);
          } else {
            postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, new NetMsgGuaranted(paramNetMsgInput, 1));
          }
          break;
        case 1:
          if (localNetObj != null) { NetEnv.cur(); if (localNetObj != NetEnv.host()); } else { doAsk(null, j); break;
          }
          postTo(localNetObj.masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
          break;
        case 2:
          if (localNetObj != null) { NetEnv.cur(); if (localNetObj != NetEnv.host()); } else { doNak(null, j, paramNetMsgInput.read255()); break;
          }
          postTo(localNetObj.masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
          break;
        case 3:
          if (localNetObj != null) { NetEnv.cur(); if (localNetObj != NetEnv.host()); } else { doRequest(null, j, paramNetMsgInput.read255()); break;
          }
          postTo(localNetObj.masterChannel(), new NetMsgGuaranted(paramNetMsgInput, 1));
        }
      }
    }
    catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  private void startCheckChannel() {
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(0);
      localNetMsgGuaranted.writeInt(-1);
      localNetMsgGuaranted.writeNetObj(null);
      postTo(this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel, localNetMsgGuaranted); } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public void msgNetNewChannel(NetChannel paramNetChannel) {
    if ((isMirror()) && (this.jdField_masterChannel_of_type_ComMaddoxRtsNetChannel == paramNetChannel)) return;
    if ((paramNetChannel.isGlobal()) && (!paramNetChannel.isMirrored(this)))
      try {
        NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
        postTo(paramNetChannel, localNetMsgSpawn); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
  }

  public void destroy() {
    if (isMirror()) {
      NetEnv.cur(); List localList = NetEnv.channels();
      int i = localList.size();
      for (int j = 0; j < i; j++) {
        NetChannel localNetChannel = (NetChannel)localList.get(j);
        if (localNetChannel.isGlobal())
          localNetChannel.destroy("Server has left the game.");
      }
    }
    if (NetEnv.cur().control == this)
      NetEnv.cur().control = null;
    super.destroy();
  }

  public NetControl(Object paramObject) {
    super(paramObject);
    if (NetEnv.cur().control != null) {
      super.destroy();
      throw new NetException("net control slot alredy used");
    }
    NetEnv.cur().control = this;
  }

  public NetControl(NetChannel paramNetChannel, int paramInt) {
    super(null, paramNetChannel, paramInt);
    if (NetEnv.cur().control != null) {
      super.destroy();
      throw new NetException("net control slot alredy used");
    }
    NetEnv.cur().control = this;
    startCheckChannel();
  }
  static {
    Spawn.add(NetControl.class, new SPAWN());
  }

  static class SPAWN implements NetSpawn {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput) {
      try {
        NetChannel localNetChannel = paramNetMsgInput.channel();
        if (NetEnv.cur().control != null) {
          if ((NetEnv.cur().control instanceof NetControlLock)) {
            NetControlLock localNetControlLock = (NetControlLock)NetEnv.cur().control;
            if (localNetControlLock.channel() == localNetChannel) {
              localNetControlLock.destroy();
            } else {
              localNetChannel.destroy("Remote control slot is locked with other channel.");
              return;
            }
          } else {
            localNetChannel.destroy("Only TREE network structure is supported.");
            return;
          }
        } else {
          localNetChannel.destroy("Remote control slot is NOT locked with the channel created.");
          return;
        }
        new NetControl(localNetChannel, paramInt); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }
  }
}