// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrdersTree.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.Selector;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.NetEnv;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.game.order:
//            Orders, Order, OrderBack, OrderCover_Me, 
//            OrderTarget_All, OrderAttack_Fighters, OrderAttack_Bombers, OrderAttack_My_Target, 
//            OrderDrop_Tanks, OrderBreak, OrderRejoin, OrderChange_Formation_To_1, 
//            OrderChange_Formation_To_2, OrderChange_Formation_To_3, OrderChange_Formation_To_4, OrderChange_Formation_To_5, 
//            OrderChange_Formation_To_6, OrderChange_Formation_To_7, OrderTighten_Formation, OrderLoosen_Formation, 
//            OrderRequest_Assistance, OrderVector_To_Home_Base, OrderVector_To_Target, OrderRequest_For_Landing, 
//            OrderRequest_For_Takeoff, OrderRequest_For_RunwayLights, OrderAnyone_Help_Me, OrderGT

public class OrdersTree
{
    class HotKeyCmdFire extends com.maddox.rts.HotKeyCmd
    {

        public void begin()
        {
            doCmd(cmd, true);
        }

        public void end()
        {
            doCmd(cmd, false);
        }

        public boolean isDisableIfTimePaused()
        {
            return true;
        }

        int cmd;

        public HotKeyCmdFire(java.lang.String s, java.lang.String s1, int i, int j)
        {
            super(true, s1, s);
            cmd = i;
            setRecordId(j);
        }
    }


    protected boolean isLocalServer()
    {
        return context == 0;
    }

    protected boolean isLocalClient()
    {
        return context == 1;
    }

    protected boolean isRemoteServer()
    {
        return context == 2;
    }

    protected boolean isRemote()
    {
        return context == 2;
    }

    protected boolean isLocal()
    {
        return context != 2;
    }

    protected boolean _isEnableVoice()
    {
        return isLocalServer() || isRemoteServer();
    }

    private void chatLog(com.maddox.il2.game.order.Order order)
    {
        if(!com.maddox.il2.engine.Actor.isValid(Player))
            return;
        int i = Player.getArmy();
        java.util.ArrayList arraylist = new ArrayList();
        do
        {
            if(order == null)
                break;
            arraylist.add(0, order.name(i));
            if(order.orders.upOrders == null)
                break;
            if(order != order.orders.order[0])
            {
                order = order.orders.order[0];
                continue;
            }
            if(order.orders.upOrders.upOrders == null)
                break;
            order = order.orders.upOrders.order[0];
        } while(true);
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(int j = 0; j < arraylist.size(); j++)
        {
            if(j > 0)
                stringbuffer.append(" ");
            stringbuffer.append((java.lang.String)arraylist.get(j));
        }

        com.maddox.il2.net.NetUser netuser = null;
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        java.util.ArrayList arraylist1 = new ArrayList();
        for(int k = 0; k < list.size(); k++)
        {
            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(k);
            if(this == netuser1.ordersTree)
            {
                netuser = netuser1;
                continue;
            }
            if(i == netuser1.getArmy())
                arraylist1.add(netuser1);
        }

        if(netuser == null)
            netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        else
        if(i == ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy())
            arraylist1.add(com.maddox.rts.NetEnv.host());
        arraylist1.add(netuser);
        com.maddox.il2.game.Main.cur().chat.send(netuser, stringbuffer.toString(), arraylist1, (byte)1, i == com.maddox.il2.ai.World.getPlayerArmy());
    }

    public java.lang.Boolean frequency()
    {
        return frequency;
    }

    public void setFrequency(java.lang.Boolean boolean1)
    {
        frequency = boolean1;
    }

    public boolean alone()
    {
        return alone;
    }

    protected void _cset(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < 16; i++)
            CommandSet[i] = null;

        if(frequency == null || !frequency.booleanValue())
            return;
        alone = false;
        if(!com.maddox.il2.engine.Actor.isAlive(actor))
            return;
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
        {
            CommandSet[0] = (com.maddox.il2.objects.air.Aircraft)actor;
            alone = true;
        } else
        if(actor instanceof com.maddox.il2.ai.Wing)
        {
            com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)actor;
            for(int j = 0; j < 4; j++)
                if(com.maddox.il2.engine.Actor.isAlive(wing.airc[j]))
                    CommandSet[j] = wing.airc[j];

        } else
        if(actor instanceof com.maddox.il2.ai.Squadron)
        {
            com.maddox.il2.ai.Squadron squadron = (com.maddox.il2.ai.Squadron)actor;
            for(int k = 0; k < 16; k++)
                if(squadron.wing[k >> 2] != null && com.maddox.il2.engine.Actor.isAlive(squadron.wing[k >> 2].airc[k & 3]))
                    CommandSet[k] = squadron.wing[k >> 2].airc[k & 3];

        }
    }

    public boolean isActive()
    {
        return bActive;
    }

    public void missionLoaded()
    {
        context = 0;
        missionLoaded(com.maddox.il2.ai.World.getPlayerAircraft());
    }

    public void netMissionLoaded(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == null)
        {
            context = 2;
        } else
        {
            boolean flag = com.maddox.il2.game.Mission.isNet() && aircraft.netUser() != com.maddox.rts.NetEnv.host();
            if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft() && !flag)
            {
                if(com.maddox.il2.game.Mission.isSingle() || com.maddox.il2.game.Mission.isServer())
                    context = 0;
                else
                    context = 1;
            } else
            {
                context = 2;
            }
        }
        missionLoaded(aircraft);
    }

    private void missionLoaded(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        Player = aircraft;
        if(Player == null)
            return;
        curOrdersTree = this;
        com.maddox.il2.game.Mission _tmp = com.maddox.il2.game.Main.cur().mission;
        if(com.maddox.il2.game.Mission.isDogfight())
        {
            setAsDogfight();
            curOrdersTree = null;
            return;
        }
        com.maddox.il2.ai.Wing wing = Player.getWing();
        PlayerWing = wing;
        PlayerSquad = wing.squadron();
        PlayerRegiment = wing.regiment();
        int i = Player.aircIndex();
        int j = Player.aircNumber();
        int k = wing.indexInSquadron();
        int l = wing.squadron().getWingsNumber();
        if(j == 2)
            setAsSquadLeader();
        else
            setAsGroupLeader();
        com.maddox.il2.ai.Wing wing1 = null;
        int i1 = 0;
        do
        {
            if(i1 >= PlayerSquad.wing.length)
                break;
            if(PlayerSquad.wing[i1] != null)
            {
                wing1 = PlayerSquad.wing[i1];
                break;
            }
            i1++;
        } while(true);
        if(PlayerWing != wing1 || l < 2)
        {
            if(i == 0 && j > 1)
            {
                setAsWingLeader(k);
            } else
            {
                setAsWingman(i);
                if(!isRemote() && (Player instanceof com.maddox.il2.objects.air.TypeTransport))
                    home.order[1].attrib |= 1;
            }
        } else
        if(i != 0)
            setAsWingman(i);
        else
        if(!isRemote())
        {
            for(int j1 = 0; j1 < 4; j1++)
                if(PlayerSquad.wing[j1] == null)
                    home.order[2 + j1].attrib |= 1;

        }
        curOrdersTree = null;
    }

    public void resetGameClear()
    {
        Player = null;
        PlayerWing = null;
        PlayerSquad = null;
        PlayerRegiment = null;
        for(int i = 0; i < CommandSet.length; i++)
            CommandSet[i] = null;

    }

    private void setAsSquadLeader()
    {
        home = SquadLeader;
        if(isRemote())
            return;
        for(int i = 0; i < home.order.length; i++)
            if(home.order[i] != null)
                home.order[i].attrib &= -2;

    }

    private void setAsGroupLeader()
    {
        home = GroupLeader;
        if(isRemote())
            return;
        for(int i = 0; i < home.order.length; i++)
            if(home.order[i] != null)
                home.order[i].attrib &= -2;

    }

    private void setAsWingLeader(int i)
    {
        if(isRemote())
            return;
        for(int j = 0; j < home.order.length; j++)
            if(home.order[j] != null)
                home.order[j].attrib &= -2;

        home.order[2].attrib |= 1;
        home.order[3].attrib |= 1;
        home.order[4].attrib |= 1;
        home.order[5].attrib |= 1;
        home.order[6].attrib |= 1;
        if(i >= 0 && i < 4)
            home.order[2 + i].attrib &= -2;
    }

    private void setAsWingman(int i)
    {
        setAsWingLeader(-1);
        if(isRemote())
            return;
        if((i & 1) == 0)
            home.order[1].attrib &= -2;
        else
            home.order[1].attrib |= 1;
    }

    public void activate()
    {
        if(isActive())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isDogfight() && com.maddox.il2.game.Main.cur().mission.zutiRadar_EnableTowerCommunications)
        {
            if(com.maddox.il2.ai.World.getPlayerAircraft() != null)
                ((com.maddox.il2.ai.air.Maneuver)com.maddox.il2.ai.World.getPlayerAircraft().FM).silence = false;
            missionLoaded();
        }
        if(isLocal())
        {
            if(!com.maddox.il2.engine.Actor.isAlive(Player) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper())
                return;
            activateHotKeyCmd(true);
            disableAircraftCmds();
            com.maddox.rts.HotKeyCmdEnv.enable("gui", false);
        }
        if(isLocalClient())
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).orderCmd(-1);
        bActive = true;
        curOrdersTree = this;
        home.run();
        curOrdersTree = null;
    }

    public void unactivate()
    {
        if(!isActive())
            return;
        if(isLocal())
        {
            activateHotKeyCmd(false);
            enableAircraftCmds();
            com.maddox.rts.HotKeyCmdEnv.enable("gui", true);
            com.maddox.il2.game.HUD.order(null);
        }
        if(isLocalClient())
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).orderCmd(-2);
        bActive = false;
    }

    private void disableAircraftCmds()
    {
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.HotKeyEnv.env("pilot").all();
        com.maddox.util.HashMapInt hashmapint1 = com.maddox.rts.HotKeyEnv.env("orders").all();
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint1.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint1.nextEntry(hashmapintentry))
        {
            int i = hashmapintentry.getKey();
            java.lang.String s = (java.lang.String)hashmapint.get(i);
            if(s != null)
                disabledHotKeys.put(i, s);
        }

        for(com.maddox.util.HashMapIntEntry hashmapintentry1 = disabledHotKeys.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = disabledHotKeys.nextEntry(hashmapintentry1))
        {
            int j = hashmapintentry1.getKey();
            hashmapint.remove(j);
        }

    }

    private void enableAircraftCmds()
    {
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.HotKeyEnv.env("pilot").all();
        for(com.maddox.util.HashMapIntEntry hashmapintentry = disabledHotKeys.nextEntry(null); hashmapintentry != null; hashmapintentry = disabledHotKeys.nextEntry(hashmapintentry))
            hashmapint.put(hashmapintentry.getKey(), hashmapintentry.getValue());

        disabledHotKeys.clear();
    }

    public void execCmd(int i)
    {
        com.maddox.il2.game.order.Order aorder[] = cur.order;
        int j = i;
        curOrdersTree = this;
        if(i == 0)
        {
            for(int k = aorder.length - 1; k >= 0; k--)
                if(aorder[k] != null)
                {
                    aorder[k].preRun();
                    curOrdersTree = null;
                    if(isLocalClient())
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).orderCmd(0);
                    return;
                }

        } else
        {
            for(int l = 0; l < aorder.length; l++)
            {
                if(aorder[l] != null && i == 0)
                {
                    if(isLocal() && (aorder[l].attrib & 1) != 0)
                    {
                        unactivate();
                    } else
                    {
                        if(isLocalClient())
                            if("Attack_My_Target".equals(aorder[l].name))
                                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).orderCmd(j, com.maddox.il2.game.Selector.getTarget());
                            else
                                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).orderCmd(j);
                        try
                        {
                            aorder[l].preRun();
                            if(isLocalServer() || isRemoteServer())
                                aorder[l].run();
                        }
                        catch(java.lang.Exception exception)
                        {
                            java.lang.System.out.println("User command failed: " + exception.getMessage());
                            exception.printStackTrace();
                        }
                        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster() && com.maddox.il2.game.Main.cur().netServerParams.isCoop() && aorder[l].subOrders == null)
                            chatLog(aorder[l]);
                        if(aorder[l].subOrders == null)
                            unactivate();
                    }
                    curOrdersTree = null;
                    return;
                }
                i--;
            }

        }
        curOrdersTree = null;
    }

    protected void doCmd(int i, boolean flag)
    {
        if(flag)
            return;
        if(i != 0 && (!com.maddox.il2.engine.Actor.isAlive(Player) || com.maddox.il2.ai.World.isPlayerDead() || com.maddox.il2.ai.World.isPlayerParatrooper()))
        {
            return;
        } else
        {
            execCmd(i);
            return;
        }
    }

    private void activateHotKeyCmd(boolean flag)
    {
        if(flag)
        {
            for(int i = 0; i < hotKeyCmd.length; i++)
                hotKeyCmd[i].enable(true);

        } else
        {
            for(int j = 0; j < hotKeyCmd.length; j++)
                hotKeyCmd[j].enable(false);

        }
    }

    public OrdersTree(boolean flag)
    {
        bActive = false;
        CommandSet = new com.maddox.il2.objects.air.Aircraft[16];
        frequency = new Boolean(true);
        alone = false;
        shipIDList = new java.lang.String[10];
        disabledHotKeys = new HashMapInt();
        if(!flag)
        {
            return;
        } else
        {
            com.maddox.rts.HotKeyCmdEnv.setCurrentEnv("orders");
            com.maddox.rts.HotKeyEnv.fromIni("orders", com.maddox.il2.engine.Config.cur.ini, "HotKey orders");
            cmdEnv = com.maddox.rts.HotKeyCmdEnv.currentEnv();
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "deactivate", null) {

                public boolean isDisableIfTimePaused()
                {
                    return true;
                }

                public void end()
                {
                    if(com.maddox.il2.game.Main3D.cur3D().ordersTree.isActive())
                        com.maddox.il2.game.Main3D.cur3D().ordersTree.unactivate();
                }

                public void created()
                {
                    setRecordId(261);
                }

            }
);
            com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "activate", "0") {

                public boolean isDisableIfTimePaused()
                {
                    return true;
                }

                public void begin()
                {
                    if(com.maddox.il2.game.Main3D.cur3D().ordersTree.isActive())
                        com.maddox.il2.game.Main3D.cur3D().ordersTree.unactivate();
                    else
                        com.maddox.il2.game.Main3D.cur3D().ordersTree.activate();
                }

                public void created()
                {
                    setRecordId(260);
                }

            }
);
            hotKeyCmd = new com.maddox.il2.game.order.HotKeyCmdFire[10];
            com.maddox.rts.HotKeyCmdEnv _tmp = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[0] = new HotKeyCmdFire(null, "order0", 0, 250));
            com.maddox.rts.HotKeyCmdEnv _tmp1 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[1] = new HotKeyCmdFire(null, "order1", 1, 251));
            com.maddox.rts.HotKeyCmdEnv _tmp2 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[2] = new HotKeyCmdFire(null, "order2", 2, 252));
            com.maddox.rts.HotKeyCmdEnv _tmp3 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[3] = new HotKeyCmdFire(null, "order3", 3, 253));
            com.maddox.rts.HotKeyCmdEnv _tmp4 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[4] = new HotKeyCmdFire(null, "order4", 4, 254));
            com.maddox.rts.HotKeyCmdEnv _tmp5 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[5] = new HotKeyCmdFire(null, "order5", 5, 255));
            com.maddox.rts.HotKeyCmdEnv _tmp6 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[6] = new HotKeyCmdFire(null, "order6", 6, 256));
            com.maddox.rts.HotKeyCmdEnv _tmp7 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[7] = new HotKeyCmdFire(null, "order7", 7, 257));
            com.maddox.rts.HotKeyCmdEnv _tmp8 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[8] = new HotKeyCmdFire(null, "order8", 8, 258));
            com.maddox.rts.HotKeyCmdEnv _tmp9 = cmdEnv;
            com.maddox.rts.HotKeyCmdEnv.addCmd(hotKeyCmd[9] = new HotKeyCmdFire(null, "order9", 9, 259));
            activateHotKeyCmd(false);
            return;
        }
    }

    public java.lang.String[] getShipIDs()
    {
        return shipIDList;
    }

    public void addShipIDs(int i, int j, com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(j == -1)
            shipIDList[i] = null;
        else
            shipIDList[i] = "ID:" + com.maddox.il2.objects.vehicles.radios.Beacon.getBeaconID(j) + "  " + s + " " + s1;
    }

    private void setAsDogfight()
    {
        home = GroupLeader;
        if(!isRemote())
        {
            for(int i = 0; i < home.order.length; i++)
                if(home.order[i] != null)
                    home.order[i].attrib &= -2;

        }
    }

    static com.maddox.il2.game.order.Orders GroundTargets;
    static com.maddox.il2.game.order.Orders ToWingman;
    static com.maddox.il2.game.order.Orders Change_Formation;
    static com.maddox.il2.game.order.Orders Tactical;
    static com.maddox.il2.game.order.Orders Navigation;
    static com.maddox.il2.game.order.Orders ToParaZveno;
    static com.maddox.il2.game.order.Orders ToGroupSquad;
    static com.maddox.il2.game.order.Orders ToGroundControl;
    static com.maddox.il2.game.order.Orders Frequency;
    static com.maddox.il2.game.order.Orders SquadLeader;
    static com.maddox.il2.game.order.Orders GroupLeader;
    public static final java.lang.String envName = "orders";
    public static com.maddox.il2.game.order.OrdersTree curOrdersTree;
    protected com.maddox.il2.game.order.Orders home;
    protected com.maddox.il2.game.order.Orders cur;
    protected boolean bActive;
    protected com.maddox.rts.HotKeyCmdEnv cmdEnv;
    protected com.maddox.il2.objects.air.Aircraft Player;
    protected com.maddox.il2.ai.Wing PlayerWing;
    protected com.maddox.il2.ai.Squadron PlayerSquad;
    protected com.maddox.il2.ai.Regiment PlayerRegiment;
    protected com.maddox.il2.objects.air.Aircraft CommandSet[];
    protected java.lang.Boolean frequency;
    protected boolean alone;
    public static final java.lang.Boolean FREQ_FRIENDLY = new Boolean(true);
    public static final java.lang.Boolean FREQ_ENEMY = new Boolean(true);
    public static final int shipIDListSize = 10;
    private java.lang.String shipIDList[];
    protected static final int CONTEXT_LOCAL_SERVER = 0;
    protected static final int CONTEXT_LOCAL_CLIENT = 1;
    protected static final int CONTEXT_REMOTE_SERVER = 2;
    protected int context;
    private com.maddox.util.HashMapInt disabledHotKeys;
    private com.maddox.il2.game.order.HotKeyCmdFire hotKeyCmd[];
    static com.maddox.il2.game.order.Orders ZutiDogfightOptions;

    static 
    {
        GroundTargets = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("GroundTargets"), new com.maddox.il2.game.order.OrderGT("Attack_All") {

                public void run()
                {
                    run(0);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Tanks") {

                public void run()
                {
                    run(1);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Flak") {

                public void run()
                {
                    run(2);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Vehicles") {

                public void run()
                {
                    run(3);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Train") {

                public void run()
                {
                    run(4);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Bridge") {

                public void run()
                {
                    run(5);
                }

            }
, new com.maddox.il2.game.order.OrderGT("Attack_Ships") {

                public void run()
                {
                    run(6);
                }

            }
, null, new OrderBack()
        });
        ToWingman = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(), new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new OrderBreak(), new OrderRejoin(), 
            null, new OrderBack()
        });
        Change_Formation = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("C_F"), new OrderChange_Formation_To_1(), new OrderChange_Formation_To_2(), new OrderChange_Formation_To_3(), new OrderChange_Formation_To_4(), new OrderChange_Formation_To_5(), new OrderChange_Formation_To_6(), new OrderChange_Formation_To_7(), null, new OrderBack()
        });
        Tactical = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("Tactical"), new OrderBreak(), new OrderRejoin(), new OrderTighten_Formation(), new OrderLoosen_Formation(), new Order("Change_Formation_Submenu", Change_Formation), null, new OrderBack()
        });
        Navigation = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("Navigation"), new com.maddox.il2.game.order.Order("Next_Checkpoint") {

                public void run()
                {
                    com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
                    for(int i = 0; i < CommandSet().length; i++)
                    {
                        if(!com.maddox.il2.engine.Actor.isAlive(CommandSet()[i]) || !(CommandSet()[i].FM instanceof com.maddox.il2.ai.air.Pilot))
                            continue;
                        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)CommandSet()[i].FM;
                        if(!pilot.AP.way.isLanding())
                            pilot.AP.way.next();
                        if(isEnableVoice() && CommandSet()[i] != Player() && (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
                            com.maddox.il2.objects.sounds.Voice.speakNextWayPoint(CommandSet()[i]);
                    }

                    com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
                }

            }
, new com.maddox.il2.game.order.Order("Prev_Checkpoint") {

                public void run()
                {
                    com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
                    for(int i = 0; i < CommandSet().length; i++)
                    {
                        if(!com.maddox.il2.engine.Actor.isAlive(CommandSet()[i]) || !(CommandSet()[i].FM instanceof com.maddox.il2.ai.air.Pilot))
                            continue;
                        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)CommandSet()[i].FM;
                        if(!pilot.AP.way.isLanding())
                            pilot.AP.way.prev();
                        if(isEnableVoice() && CommandSet()[i] != Player() && (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
                            com.maddox.il2.objects.sounds.Voice.speakPrevWayPoint(CommandSet()[i]);
                    }

                    com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
                }

            }
, new com.maddox.il2.game.order.Order("Return_To_Base") {

                public void run()
                {
                    com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
                    boolean flag = false;
                    com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
                    for(int i = 0; i < CommandSet().length; i++)
                    {
                        com.maddox.il2.objects.air.Aircraft aircraft = CommandSet()[i];
                        if(!com.maddox.il2.engine.Actor.isAlive(aircraft) || !(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot))
                            continue;
                        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)CommandSet()[i].FM;
                        if(!pilot.AP.way.isLanding())
                        {
                            pilot.AP.way.last();
                            pilot.AP.way.prev();
                        }
                        if(pilot.Group != null)
                        {
                            pilot.Group.setGroupTask(1);
                            pilot.Group.timeOutForTaskSwitch = 480;
                        }
                        if(isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
                            com.maddox.il2.objects.sounds.Voice.speakReturnToBase(CommandSet()[i]);
                        if(aircraft != Player() && pilot.Group == maneuver.Group)
                            flag = true;
                    }

                    com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
                    if(flag)
                    {
                        com.maddox.il2.ai.air.AirGroup airgroup = maneuver.Group;
                        com.maddox.il2.ai.air.AirGroup airgroup1 = new AirGroup(maneuver.Group);
                        airgroup1.rejoinGroup = null;
                        maneuver.Group.delAircraft(Player());
                        airgroup1.addAircraft(Player());
                        airgroup.setGroupTask(1);
                    }
                }

            }
, new com.maddox.il2.game.order.Order("Hang_On_Here") {

                public void run()
                {
                    for(int i = 0; i < CommandSet().length; i++)
                    {
                        com.maddox.il2.objects.sounds.Voice.setSyncMode(1);
                        if(!com.maddox.il2.engine.Actor.isAlive(CommandSet()[i]) || !(CommandSet()[i].FM instanceof com.maddox.il2.ai.air.Pilot))
                            continue;
                        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)CommandSet()[i].FM;
                        if(pilot.Group != null && pilot.Group.nOfAirc > 0 && pilot == pilot.Group.airc[0].FM && !pilot.isBusy() && com.maddox.il2.engine.Actor.isAlive(pilot.Group.airc[pilot.Group.nOfAirc - 1]) && (pilot.Group.airc[pilot.Group.nOfAirc - 1].FM instanceof com.maddox.il2.ai.air.Pilot))
                        {
                            com.maddox.il2.ai.air.Pilot pilot1 = (com.maddox.il2.ai.air.Pilot)pilot.Group.airc[pilot.Group.nOfAirc - 1].FM;
                            pilot.Group.setFormationAndScale(pilot1.formationType, pilot1.formationScale, false);
                            pilot.Group.setGroupTask(1);
                            pilot.clear_stack();
                            pilot.push(45);
                            pilot.push(45);
                            pilot.pop();
                        }
                        if(isEnableVoice() && CommandSet()[i] != Player() && (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
                            com.maddox.il2.objects.sounds.Voice.speakHangOn(CommandSet()[i]);
                    }

                    com.maddox.il2.objects.sounds.Voice.setSyncMode(0);
                }

            }
, null, new OrderBack()
        });
        ToParaZveno = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(), new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new Order("Tactical_Submenu", Tactical), new Order("Navigation_Submenu", Navigation), 
            null, new OrderBack()
        });
        ToGroupSquad = ToParaZveno;
        ToGroundControl = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("GroundControl"), new OrderRequest_Assistance(), new OrderVector_To_Home_Base(), new OrderVector_To_Target(), new OrderRequest_For_Landing(), new OrderRequest_For_Takeoff(), new OrderRequest_For_RunwayLights(), new OrderBack()
        });
        Frequency = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("Frequency"), new com.maddox.il2.game.order.Order("Friendly") {

                public void preRun()
                {
                    Player().FM.AS.setBeacon(0);
                    com.maddox.il2.game.order.OrdersTree.curOrdersTree.frequency = com.maddox.il2.game.order.OrdersTree.FREQ_FRIENDLY;
                }

            }
, new com.maddox.il2.game.order.Order("Enemy") {

                public void preRun()
                {
                    Player().FM.AS.setBeacon(0);
                    com.maddox.il2.game.order.OrdersTree.curOrdersTree.frequency = com.maddox.il2.game.order.OrdersTree.FREQ_ENEMY;
                }

            }
, null, new OrderBack()
        });
        SquadLeader = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("MainMenu"), new com.maddox.il2.game.order.Order("Wingman", "deWingman", ToWingman) {

                public void run()
                {
                    cset(Wingman());
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Para_1", "dePara_1", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[0]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Para_2", "dePara_2", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[1]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Para_3", "dePara_3", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[2]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Para_4", "dePara_4", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[3]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Group", "deGroup", ToGroupSquad) {

                public void run()
                {
                    cset(PlayerSquad());
                    super.run();
                }

            }
, new OrderAnyone_Help_Me(), new Order("Ground_Control", ToGroundControl), new Order("Frequency", Frequency), 
            null, new OrderBack()
        });
        GroupLeader = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("MainMenu"), new com.maddox.il2.game.order.Order("Wingman", "deWingman", ToWingman) {

                public void run()
                {
                    cset(Wingman());
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Zveno_1", "deZveno_1", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[0]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Zveno_2", "deZveno_2", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[1]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Zveno_3", "deZveno_3", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[2]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Zveno_4", "deZveno_4", ToParaZveno) {

                public void run()
                {
                    cset(PlayerSquad().wing[3]);
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Squadron", "deSquadron", ToGroupSquad) {

                public void run()
                {
                    cset(PlayerSquad());
                    super.run();
                }

            }
, new OrderAnyone_Help_Me(), new Order("Ground_Control", ToGroundControl), new Order("Frequency", Frequency), 
            null, new OrderBack()
        });
        ZutiDogfightOptions = new Orders(new com.maddox.il2.game.order.Order[] {
            new Order("MainMenu"), new com.maddox.il2.game.order.Order("Wingman", "deWingman", ToWingman) {

                public void run()
                {
                    cset(Wingman());
                    super.run();
                }

            }
, new com.maddox.il2.game.order.Order("Squadron", "deSquadron", ToGroupSquad) {

                public void run()
                {
                    cset(PlayerSquad());
                    super.run();
                }

            }
, new OrderAnyone_Help_Me(), new Order("Frequency", Frequency), new Order("Ground_Control", ToGroundControl), null, new OrderBack()
        });
    }
}
