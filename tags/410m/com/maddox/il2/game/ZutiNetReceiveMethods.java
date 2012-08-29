// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiNetReceiveMethods.java

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

// Referenced classes of package com.maddox.il2.game:
//            Main, ZutiNetSendMethods, HUD, ZutiSupportMethods

public class ZutiNetReceiveMethods
{

    public ZutiNetReceiveMethods()
    {
    }

    public static boolean processReceivedMessage(com.maddox.il2.net.NetUser netuser, com.maddox.rts.NetMsgInput netmsginput, byte byte0)
    {
        if(byte0 < 40)
            return false;
        switch(byte0)
        {
        case 40: // '('
            if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                com.maddox.il2.game.ZutiNetSendMethods.testMessage(netuser);
            else
                com.maddox.il2.game.ZutiNetReceiveMethods.testMessage();
            return true;

        case 41: // ')'
            com.maddox.il2.game.ZutiNetReceiveMethods.playerBanned();
            return true;

        case 42: // '*'
            com.maddox.il2.game.ZutiNetReceiveMethods.aircraftNotAvailable();
            return true;

        case 43: // '+'
            if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                com.maddox.il2.game.ZutiNetSendMethods.removeTInspectTargets(netuser);
            else
                com.maddox.il2.game.ZutiNetReceiveMethods.removeTInspectTarget(netmsginput);
            return true;

        case 45: // '-'
            if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                com.maddox.il2.game.ZutiNetSendMethods.unavailablePlanesList(netuser);
            else
                com.maddox.il2.game.ZutiNetReceiveMethods.unavailableAircraftList(netmsginput);
            return true;

        case 44: // ','
        default:
            return false;
        }
    }

    private static void testMessage()
    {
        java.lang.System.out.println("RECEIVED TEST MESSAGE FROM SERVER!");
    }

    private static void playerBanned()
    {
        try
        {
            com.maddox.il2.ai.World.getPlayerAircraft().destroy();
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
        com.maddox.il2.game.HUD.log("mds.netCommand.banned");
    }

    private static void aircraftNotAvailable()
    {
        try
        {
            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            aircraft.destroy();
            com.maddox.il2.objects.air.NetAircraft.ZUTI_REFLY_OWERRIDE = true;
            com.maddox.il2.game.HUD.log("mds.netCommand.aircraftNotAvailable", new java.lang.Object[] {
                com.maddox.rts.Property.stringValue(((com.maddox.il2.objects.air.Aircraft)aircraft).getClass(), "keyName")
            });
        }
        catch(java.lang.Exception exception) { }
    }

    private static void removeTInspectTarget(com.maddox.rts.NetMsgInput netmsginput)
    {
        try
        {
            double d = netmsginput.readDouble();
            double d1 = netmsginput.readDouble();
            java.lang.String s = netmsginput.read255();
            if(s.equalsIgnoreCase("null") || s.trim().length() < 1)
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(d, d1);
            else
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(s);
        }
        catch(java.lang.Exception exception) { }
    }

    private static void unavailableAircraftList(com.maddox.rts.NetMsgInput netmsginput)
    {
        try
        {
            java.util.ArrayList arraylist = new ArrayList();
            double d = netmsginput.readDouble();
            double d1 = netmsginput.readDouble();
            java.lang.String s = netmsginput.read255();
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s); stringtokenizer.hasMoreTokens(); arraylist.add(stringtokenizer.nextToken()));
            com.maddox.il2.game.ZutiSupportMethods.setAircraftAvailabilityForHomeBase(arraylist, d, d1);
        }
        catch(java.lang.Exception exception) { }
    }
}
