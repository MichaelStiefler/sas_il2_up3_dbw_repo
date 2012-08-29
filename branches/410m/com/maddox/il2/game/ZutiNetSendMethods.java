// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiNetSendMethods.java

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

// Referenced classes of package com.maddox.il2.game:
//            ZutiSupportMethods

public class ZutiNetSendMethods
{

    public ZutiNetSendMethods()
    {
    }

    public static void testMessage(com.maddox.il2.net.NetUser netuser)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(40);
            com.maddox.rts.NetEnv.host().postTo(netuser.masterChannel(), netmsgguaranted);
        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static void removeTInspectTarget(com.maddox.il2.net.NetUser netuser, com.maddox.JGP.Point3d point3d, java.lang.String s)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(43);
            netmsgguaranted.writeDouble(point3d.x);
            netmsgguaranted.writeDouble(point3d.y);
            netmsgguaranted.writeChars(s);
            com.maddox.rts.NetEnv.host().postTo(netuser.masterChannel(), netmsgguaranted);
        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static void removeTInspectTargets(com.maddox.il2.net.NetUser netuser)
    {
        if(com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS != null)
        {
            Object obj = null;
            Object obj1 = null;
            int i = com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS.get(j);
                if(actor instanceof com.maddox.il2.ai.TInspect)
                {
                    com.maddox.il2.ai.TInspect tinspect = (com.maddox.il2.ai.TInspect)actor;
                    com.maddox.il2.game.ZutiNetSendMethods.removeTInspectTarget(netuser, tinspect.point, "null");
                }
            }

        }
    }

    public static void playerBanned(com.maddox.il2.net.NetUser netuser)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(41);
            com.maddox.rts.NetEnv.host().postTo(netuser.masterChannel(), netmsgguaranted);
        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static void aircraftNotAvailable(com.maddox.il2.net.NetUser netuser)
    {
        if(netuser == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(42);
            com.maddox.rts.NetEnv.host().postTo(netuser.masterChannel(), netmsgguaranted);
        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return;
    }

    public static void unavailablePlanesList(com.maddox.il2.net.NetUser netuser)
    {
        if(com.maddox.il2.ai.World.cur() == null || com.maddox.il2.ai.World.cur().bornPlaces == null)
            return;
        try
        {
            java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
            Object obj = null;
            Object obj1 = null;
            Object obj2 = null;
            for(int i = 0; i < arraylist.size(); i++)
            {
                com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(i);
                if(bornplace != null)
                {
                    java.util.List list = com.maddox.il2.game.ZutiSupportMethods.getUnavailableAircraftList(bornplace);
                    if(list != null)
                    {
                        for(int j = 0; j < list.size(); j++)
                        {
                            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                            netmsgguaranted.writeByte(45);
                            netmsgguaranted.writeDouble(bornplace.place.x);
                            netmsgguaranted.writeDouble(bornplace.place.y);
                            netmsgguaranted.write255((java.lang.String)list.get(j));
                            com.maddox.rts.NetEnv.host().postTo(netuser.masterChannel(), netmsgguaranted);
                        }

                    }
                }
            }

        }
        catch(java.io.IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return;
    }

    public static void requestTestMessage()
    {
        try
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser == null)
                netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(40);
            netuser.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void requestUnavailableAircraftList()
    {
        try
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser == null)
                netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(45);
            netuser.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void requestCompletedReconList()
    {
        try
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser == null)
                netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(43);
            netuser.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

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

}
