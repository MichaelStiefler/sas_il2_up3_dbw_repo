package com.maddox.il2.game;

import com.maddox.il2.ai.World;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ZutiNetReceiveMethods
{
  public static boolean processReceivedMessage(NetUser paramNetUser, NetMsgInput paramNetMsgInput, byte paramByte)
  {
    if (paramByte < 40) {
      return false;
    }

    switch (paramByte)
    {
    case 40:
      if (Main.cur().netServerParams.isMaster())
        ZutiNetSendMethods.testMessage(paramNetUser);
      else
        testMessage();
      return true;
    case 41:
      playerBanned();
      return true;
    case 42:
      aircraftNotAvailable();
      return true;
    case 43:
      if (Main.cur().netServerParams.isMaster())
        ZutiNetSendMethods.removeTInspectTargets(paramNetUser);
      else
        removeTInspectTarget(paramNetMsgInput);
      return true;
    case 45:
      if (Main.cur().netServerParams.isMaster())
        ZutiNetSendMethods.unavailablePlanesList(paramNetUser);
      else
        unavailableAircraftList(paramNetMsgInput);
      return true;
    case 44:
    }
    return false;
  }

  private static void testMessage()
  {
    System.out.println("RECEIVED TEST MESSAGE FROM SERVER!");
  }

  private static void playerBanned()
  {
    try
    {
      World.getPlayerAircraft().destroy(); } catch (Exception localException) {
      localException.printStackTrace();
    }
    HUD.log("mds.netCommand.banned");
  }

  private static void aircraftNotAvailable()
  {
    try
    {
      Aircraft localAircraft = World.getPlayerAircraft();
      localAircraft.destroy();
      NetAircraft.ZUTI_REFLY_OWERRIDE = true;

      HUD.log("mds.netCommand.aircraftNotAvailable", new Object[] { Property.stringValue(((Aircraft)localAircraft).getClass(), "keyName") });
    }
    catch (Exception localException)
    {
    }
  }

  private static void removeTInspectTarget(NetMsgInput paramNetMsgInput)
  {
    try
    {
      double d1 = paramNetMsgInput.readDouble();
      double d2 = paramNetMsgInput.readDouble();
      String str = paramNetMsgInput.read255();

      if ((str.equalsIgnoreCase("null")) || (str.trim().length() < 1))
        ZutiSupportMethods.removeTarget(d1, d2);
      else
        ZutiSupportMethods.removeTarget(str);
    }
    catch (Exception localException)
    {
    }
  }

  private static void unavailableAircraftList(NetMsgInput paramNetMsgInput)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      double d1 = paramNetMsgInput.readDouble();
      double d2 = paramNetMsgInput.readDouble();
      String str = paramNetMsgInput.read255();
      StringTokenizer localStringTokenizer = new StringTokenizer(str);
      while (localStringTokenizer.hasMoreTokens())
      {
        localArrayList.add(localStringTokenizer.nextToken());
      }
      ZutiSupportMethods.setAircraftAvailabilityForHomeBase(localArrayList, d1, d2);
    }
    catch (Exception localException)
    {
    }
  }
}