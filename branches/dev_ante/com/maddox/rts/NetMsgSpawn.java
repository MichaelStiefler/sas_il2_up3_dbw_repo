package com.maddox.rts;

public class NetMsgSpawn extends NetMsgGuaranted
{
  public NetMsgSpawn(NetObj paramNetObj)
  {
    setFingerClass(paramNetObj);
  }

  public NetMsgSpawn(byte[] paramArrayOfByte, NetObj paramNetObj) {
    super(paramArrayOfByte);
    setFingerClass(paramNetObj);
  }

  public NetMsgSpawn(int paramInt, NetObj paramNetObj)
  {
    super(paramInt);
    setFingerClass(paramNetObj);
  }

  private void setFingerClass(NetObj paramNetObj) {
    int i = 0;
    Object localObject1 = paramNetObj.superObj();
    Object localObject2;
    if (localObject1 != null) {
      i = Finger.Int(localObject1.getClass().getName());
      localObject2 = Spawn.get(i);
      if (localObject2 != null) {
        if (!(localObject2 instanceof NetSpawn)) i = 0; 
      }
      else {
        i = 0;
      }
    }
    if (i == 0) {
      i = Finger.Int(paramNetObj.getClass().getName());
      localObject2 = Spawn.get(i);
      if (localObject2 != null) {
        if (!(localObject2 instanceof NetSpawn)) i = 0; 
      }
      else {
        i = 0;
      }
    }
    if (i == 0)
      throw new NetException("NetSpawn interface NOT found");
    try {
      writeInt(i);
      writeShort(paramNetObj.idLocal & 0x7FFF);
    }
    catch (Exception localException)
    {
    }
  }
}