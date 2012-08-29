package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.TInspect;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgGuaranted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZutiNetSendMethods
{
  public static boolean REQUEST_PARA_CAPTURING_HB = false;
  public static final byte TEST_MESSAGE = 40;
  public static final byte PLAYER_BANNED = 41;
  public static final byte AIRCRAFT_NOT_AVAILABLE = 42;
  public static final byte REMOVE_TINSPECT = 43;
  public static final byte AWARDED_TINSPECT = 44;
  public static final byte UNAVAILABLE_AIRCRAFT_LIST = 45;
  public static final byte EJECT_GUNNER = 46;
  public static final byte PARA_CAPTURED_HB = 47;
  public static final byte PARA_CAPTURING_HB = 48;

  public static void testMessage(NetUser paramNetUser)
  {
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(40);
      NetEnv.host().postTo(paramNetUser.masterChannel(), localNetMsgGuaranted);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void removeTInspectTarget(NetUser paramNetUser, Point3d paramPoint3d, String paramString)
  {
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(43);

      localNetMsgGuaranted.writeDouble(paramPoint3d.x);
      localNetMsgGuaranted.writeDouble(paramPoint3d.y);
      localNetMsgGuaranted.writeChars(paramString);

      NetEnv.host().postTo(paramNetUser.masterChannel(), localNetMsgGuaranted);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void removeTInspectTargets(NetUser paramNetUser)
  {
    if (ZutiSupportMethods.ZUTI_DEAD_TARGETS != null)
    {
      TInspect localTInspect = null;
      Actor localActor = null;
      int i = ZutiSupportMethods.ZUTI_DEAD_TARGETS.size();

      for (int j = 0; j < i; j++)
      {
        localActor = (Actor)ZutiSupportMethods.ZUTI_DEAD_TARGETS.get(j);
        if (!(localActor instanceof TInspect))
          continue;
        localTInspect = (TInspect)localActor;
        removeTInspectTarget(paramNetUser, localTInspect.point, "null");
      }
    }
  }

  public static void playerBanned(NetUser paramNetUser)
  {
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(41);
      NetEnv.host().postTo(paramNetUser.masterChannel(), localNetMsgGuaranted);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void aircraftNotAvailable(NetUser paramNetUser)
  {
    try
    {
      if (paramNetUser == null)
        return;
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(42);
      NetEnv.host().postTo(paramNetUser.masterChannel(), localNetMsgGuaranted);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void unavailablePlanesList(NetUser paramNetUser)
  {
    try
    {
      if ((World.cur() == null) || (World.cur().bornPlaces == null)) {
        return;
      }
      ArrayList localArrayList = World.cur().bornPlaces;
      BornPlace localBornPlace = null;
      NetMsgGuaranted localNetMsgGuaranted = null;
      List localList = null;

      for (int i = 0; i < localArrayList.size(); i++)
      {
        localBornPlace = (BornPlace)localArrayList.get(i);
        if (localBornPlace == null) {
          continue;
        }
        localList = ZutiSupportMethods.getUnavailableAircraftList(localBornPlace);
        if (localList == null) {
          continue;
        }
        for (int j = 0; j < localList.size(); j++)
        {
          localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(45);
          localNetMsgGuaranted.writeDouble(localBornPlace.place.x);
          localNetMsgGuaranted.writeDouble(localBornPlace.place.y);
          localNetMsgGuaranted.write255((String)localList.get(j));

          NetEnv.host().postTo(paramNetUser.masterChannel(), localNetMsgGuaranted);
        }

      }

    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void requestTestMessage()
  {
    try
    {
      NetUser localNetUser = (NetUser)NetEnv.host();

      if (localNetUser == null) {
        localNetUser = (NetUser)NetEnv.host();
      }
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(40);
      localNetUser.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void requestUnavailableAircraftList()
  {
    try
    {
      NetUser localNetUser = (NetUser)NetEnv.host();

      if (localNetUser == null) {
        localNetUser = (NetUser)NetEnv.host();
      }
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(45);
      localNetUser.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void requestCompletedReconList()
  {
    try
    {
      NetUser localNetUser = (NetUser)NetEnv.host();

      if (localNetUser == null) {
        localNetUser = (NetUser)NetEnv.host();
      }
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(43);
      localNetUser.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}