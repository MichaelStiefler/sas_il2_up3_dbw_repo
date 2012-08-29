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
import com.maddox.rts.NetHost;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class OrdersTree
{
  static Orders GroundTargets = new Orders(new Order[] { new Order("GroundTargets"), new OrderGT()
  {
    public void run() {
      run(0);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(1);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(2);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(3);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(4);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(5);
    }
  }
  , new OrderGT()
  {
    public void run()
    {
      run(6);
    }
  }
  , null, new OrderBack() });

  static Orders ToWingman = new Orders(new Order[] { new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(), new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new OrderBreak(), new OrderRejoin(), null, new OrderBack() });

  static Orders Change_Formation = new Orders(new Order[] { new Order("C_F"), new OrderChange_Formation_To_1(), new OrderChange_Formation_To_2(), new OrderChange_Formation_To_3(), new OrderChange_Formation_To_4(), new OrderChange_Formation_To_5(), new OrderChange_Formation_To_6(), new OrderChange_Formation_To_7(), null, new OrderBack() });

  static Orders Tactical = new Orders(new Order[] { new Order("Tactical"), new OrderBreak(), new OrderRejoin(), new OrderTighten_Formation(), new OrderLoosen_Formation(), new Order("Change_Formation_Submenu", Change_Formation), null, new OrderBack() });

  static Orders Navigation = new Orders(new Order[] { new Order("Navigation"), new Order()
  {
    public void run()
    {
      Voice.setSyncMode(1);
      for (int i = 0; i < CommandSet().length; i++) {
        if ((Actor.isAlive(CommandSet()[i])) && ((CommandSet()[i].FM instanceof Pilot))) {
          Pilot localPilot = (Pilot)CommandSet()[i].FM;
          if (!localPilot.AP.way.isLanding()) localPilot.AP.way.next();
          if ((!isEnableVoice()) || (CommandSet()[i] == Player()) || (
            (CommandSet()[i].getWing() != Player().getWing()) && (CommandSet()[i].aircIndex() != 0))) continue;
          Voice.speakNextWayPoint(CommandSet()[i]);
        }
      }
      Voice.setSyncMode(0);
    }
  }
  , new Order()
  {
    public void run()
    {
      Voice.setSyncMode(1);
      for (int i = 0; i < CommandSet().length; i++) {
        if ((Actor.isAlive(CommandSet()[i])) && ((CommandSet()[i].FM instanceof Pilot))) {
          Pilot localPilot = (Pilot)CommandSet()[i].FM;
          if (!localPilot.AP.way.isLanding()) localPilot.AP.way.prev();
          if ((!isEnableVoice()) || (CommandSet()[i] == Player()) || (
            (CommandSet()[i].getWing() != Player().getWing()) && (CommandSet()[i].aircIndex() != 0))) continue;
          Voice.speakPrevWayPoint(CommandSet()[i]);
        }
      }
      Voice.setSyncMode(0);
    }
  }
  , new Order()
  {
    public void run()
    {
      Voice.setSyncMode(1);
      int i = 0;
      Maneuver localManeuver = (Maneuver)Player().FM;
      Object localObject;
      for (int j = 0; j < CommandSet().length; j++) {
        localObject = CommandSet()[j];
        if ((Actor.isAlive((Actor)localObject)) && ((((Aircraft)localObject).FM instanceof Pilot))) {
          Pilot localPilot = (Pilot)CommandSet()[j].FM;
          if (!localPilot.AP.way.isLanding()) {
            localPilot.AP.way.last();
            localPilot.AP.way.prev();
          }
          if (localPilot.Group != null) {
            localPilot.Group.setGroupTask(1);
            localPilot.Group.timeOutForTaskSwitch = 480;
          }
          if ((isEnableVoice()) && (localObject != Player()) && (
            (((Aircraft)localObject).getWing() == Player().getWing()) || (((Aircraft)localObject).aircIndex() == 0)))
            Voice.speakReturnToBase(CommandSet()[j]);
          if ((localObject == Player()) || (localPilot.Group != localManeuver.Group)) continue; i = 1;
        }
      }
      Voice.setSyncMode(0);
      if (i != 0) {
        AirGroup localAirGroup = localManeuver.Group;
        localObject = new AirGroup(localManeuver.Group);
        ((AirGroup)localObject).rejoinGroup = null;
        localManeuver.Group.delAircraft(Player());
        ((AirGroup)localObject).addAircraft(Player());
        localAirGroup.setGroupTask(1);
      }
    }
  }
  , new Order()
  {
    public void run()
    {
      for (int i = 0; i < CommandSet().length; i++) {
        Voice.setSyncMode(1);
        if ((Actor.isAlive(CommandSet()[i])) && ((CommandSet()[i].FM instanceof Pilot))) {
          Pilot localPilot1 = (Pilot)CommandSet()[i].FM;
          if ((localPilot1.Group != null) && 
            (localPilot1.Group.nOfAirc > 0) && (localPilot1 == localPilot1.Group.airc[0].FM) && (!localPilot1.isBusy()))
          {
            if ((Actor.isAlive(localPilot1.Group.airc[(localPilot1.Group.nOfAirc - 1)])) && ((localPilot1.Group.airc[(localPilot1.Group.nOfAirc - 1)].FM instanceof Pilot)))
            {
              Pilot localPilot2 = (Pilot)localPilot1.Group.airc[(localPilot1.Group.nOfAirc - 1)].FM;
              localPilot1.Group.setFormationAndScale(localPilot2.formationType, localPilot2.formationScale, false);
              localPilot1.Group.setGroupTask(1);
              localPilot1.clear_stack();
              localPilot1.push(45);
              localPilot1.push(45);
              localPilot1.pop();
            }
          }

          if ((!isEnableVoice()) || (CommandSet()[i] == Player()) || (
            (CommandSet()[i].getWing() != Player().getWing()) && (CommandSet()[i].aircIndex() != 0))) continue;
          Voice.speakHangOn(CommandSet()[i]);
        }
      }
      Voice.setSyncMode(0);
    }
  }
  , null, new OrderBack() });

  static Orders ToParaZveno = new Orders(new Order[] { new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(), new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new Order("Tactical_Submenu", Tactical), new Order("Navigation_Submenu", Navigation), null, new OrderBack() });

  static Orders ToGroupSquad = ToParaZveno;

  static Orders ToGroundControl = new Orders(new Order[] { new Order("GroundControl"), new OrderRequest_Assistance(), new OrderVector_To_Home_Base(), new OrderVector_To_Target(), new OrderRequest_For_Landing(), new OrderRequest_For_Takeoff(), new OrderRequest_For_RunwayLights(), null, new OrderBack() });

  static Orders Frequency = new Orders(new Order[] { new Order("Frequency"), new Order()
  {
    public void preRun()
    {
      Player().FM.AS.setBeacon(0);
      OrdersTree.curOrdersTree.frequency = OrdersTree.FREQ_FRIENDLY;
    }
  }
  , new Order()
  {
    public void preRun()
    {
      Player().FM.AS.setBeacon(0);
      OrdersTree.curOrdersTree.frequency = OrdersTree.FREQ_ENEMY;
    }
  }
  , null, new OrderBack() });

  static Orders SquadLeader = new Orders(new Order[] { new Order("MainMenu"), new Order("deWingman", ToWingman)
  {
    public void run() {
      cset(Wingman()); super.run();
    }
  }
  , new Order("dePara_1", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[0]); super.run();
    }
  }
  , new Order("dePara_2", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[1]); super.run();
    }
  }
  , new Order("dePara_3", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[2]); super.run();
    }
  }
  , new Order("dePara_4", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[3]); super.run();
    }
  }
  , new Order("deGroup", ToGroupSquad)
  {
    public void run()
    {
      cset(PlayerSquad()); super.run();
    }
  }
  , new OrderAnyone_Help_Me(), new Order("Ground_Control", ToGroundControl), new Order("Frequency", Frequency), null, new OrderBack() });

  static Orders GroupLeader = new Orders(new Order[] { new Order("MainMenu"), new Order("deWingman", ToWingman)
  {
    public void run() {
      cset(Wingman()); super.run();
    }
  }
  , new Order("deZveno_1", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[0]); super.run();
    }
  }
  , new Order("deZveno_2", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[1]); super.run();
    }
  }
  , new Order("deZveno_3", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[2]); super.run();
    }
  }
  , new Order("deZveno_4", ToParaZveno)
  {
    public void run()
    {
      cset(PlayerSquad().wing[3]); super.run();
    }
  }
  , new Order("deSquadron", ToGroupSquad)
  {
    public void run()
    {
      cset(PlayerSquad()); super.run();
    }
  }
  , new OrderAnyone_Help_Me(), new Order("Ground_Control", ToGroundControl), new Order("Frequency", Frequency), null, new OrderBack() });
  public static final String envName = "orders";
  public static OrdersTree curOrdersTree;
  protected Orders home;
  protected Orders cur;
  protected boolean bActive = false;
  protected HotKeyCmdEnv cmdEnv;
  protected Aircraft Player;
  protected Wing PlayerWing;
  protected Squadron PlayerSquad;
  protected Regiment PlayerRegiment;
  protected Aircraft[] CommandSet = new Aircraft[16];
  protected Boolean frequency = new Boolean(true);
  protected boolean alone = false;

  public static final Boolean FREQ_FRIENDLY = new Boolean(true);
  public static final Boolean FREQ_ENEMY = new Boolean(false);
  public static final int shipIDListSize = 10;
  private String[] shipIDList = new String[10];
  protected static final int CONTEXT_LOCAL_SERVER = 0;
  protected static final int CONTEXT_LOCAL_CLIENT = 1;
  protected static final int CONTEXT_REMOTE_SERVER = 2;
  protected int context;
  private static String[] hotKeyEnvNames = { "pilot", "aircraftView" };

  private HashMapInt[] disabledHotKeys = { new HashMapInt(), new HashMapInt() };
  private HotKeyCmdFire[] hotKeyCmd;
  static Orders ZutiDogfightOptions = new Orders(new Order[] { new Order("MainMenu"), new Order("deWingman", ToWingman)
  {
    public void run()
    {
      cset(Wingman());
      super.run();
    }
  }
  , new Order("deSquadron", ToGroupSquad)
  {
    public void run()
    {
      cset(PlayerSquad());
      super.run();
    }
  }
  , new OrderAnyone_Help_Me(), new Order("Frequency", Frequency), new Order("Ground_Control", ToGroundControl), null, new OrderBack() });

  protected boolean isLocalServer()
  {
    return this.context == 0; } 
  protected boolean isLocalClient() { return this.context == 1; } 
  protected boolean isRemoteServer() { return this.context == 2; } 
  protected boolean isRemote() { return this.context == 2; } 
  protected boolean isLocal() { return this.context != 2; } 
  protected boolean _isEnableVoice() {
    return (isLocalServer()) || (isRemoteServer());
  }
  private void chatLog(Order paramOrder) {
    if (!Actor.isValid(this.Player)) return;
    int i = this.Player.getArmy();
    ArrayList localArrayList1 = new ArrayList();
    while (paramOrder != null) {
      localArrayList1.add(0, paramOrder.name(i));
      if (paramOrder.orders.upOrders == null) break;
      if (paramOrder != paramOrder.orders.order[0]) {
        paramOrder = paramOrder.orders.order[0]; continue;
      }
      if (paramOrder.orders.upOrders.upOrders == null)
        break;
      paramOrder = paramOrder.orders.upOrders.order[0];
    }

    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < localArrayList1.size(); j++) {
      if (j > 0) localStringBuffer.append(" ");
      localStringBuffer.append((String)localArrayList1.get(j));
    }
    Object localObject = null;
    List localList = NetEnv.hosts();
    ArrayList localArrayList2 = new ArrayList();
    for (int k = 0; k < localList.size(); k++) {
      NetUser localNetUser = (NetUser)localList.get(k);
      if (this == localNetUser.ordersTree)
        localObject = localNetUser;
      else if (i == localNetUser.getArmy())
        localArrayList2.add(localNetUser);
    }
    if (localObject == null)
      localObject = (NetUser)NetEnv.host();
    else if (i == ((NetUser)NetEnv.host()).getArmy())
      localArrayList2.add(NetEnv.host());
    localArrayList2.add(localObject);
    Main.cur().chat.send((NetHost)localObject, localStringBuffer.toString(), localArrayList2, 1, i == World.getPlayerArmy());
  }
  public Boolean frequency() {
    return this.frequency; } 
  public void setFrequency(Boolean paramBoolean) { this.frequency = paramBoolean; } 
  public boolean alone() { return this.alone; }

  protected void _cset(Actor paramActor)
  {
    for (int i = 0; i < 16; i++) this.CommandSet[i] = null;
    if ((this.frequency == null) || (!this.frequency.booleanValue())) return;
    this.alone = false;

    if (!Actor.isAlive(paramActor)) return;

    if ((paramActor instanceof Aircraft)) {
      this.CommandSet[0] = ((Aircraft)paramActor);
      this.alone = true;
    }
    else
    {
      Object localObject;
      if ((paramActor instanceof Wing)) {
        localObject = (Wing)paramActor;
        for (i = 0; i < 4; i++)
          if (Actor.isAlive(localObject.airc[i]))
            this.CommandSet[i] = localObject.airc[i];
      }
      else if ((paramActor instanceof Squadron)) {
        localObject = (Squadron)paramActor;
        for (i = 0; i < 16; i++) {
          if ((localObject.wing[(i >> 2)] == null) || 
            (!Actor.isAlive(localObject.wing[(i >> 2)].airc[(i & 0x3)]))) continue;
          this.CommandSet[i] = localObject.wing[(i >> 2)].airc[(i & 0x3)];
        }
      }
    }
  }

  public boolean isActive() {
    return this.bActive;
  }

  public void missionLoaded() {
    this.context = 0;
    missionLoaded(World.getPlayerAircraft());
  }

  public void netMissionLoaded(Aircraft paramAircraft) {
    if (paramAircraft == null) {
      this.context = 2;
    } else {
      int i = (Mission.isNet()) && (paramAircraft.netUser() != NetEnv.host()) ? 1 : 0;
      if ((paramAircraft == World.getPlayerAircraft()) && (i == 0)) {
        if ((Mission.isSingle()) || (Mission.isServer()))
          this.context = 0;
        else
          this.context = 1;
      }
      else this.context = 2;
    }

    missionLoaded(paramAircraft);
  }

  private void missionLoaded(Aircraft paramAircraft) {
    this.Player = paramAircraft;
    if (this.Player == null) return;
    curOrdersTree = this;

    if (Mission.isDogfight())
    {
      setAsDogfight();
      curOrdersTree = null;
      return;
    }

    Wing localWing1 = this.Player.getWing();
    this.PlayerWing = localWing1;
    this.PlayerSquad = localWing1.squadron();
    this.PlayerRegiment = localWing1.regiment();
    int i = this.Player.aircIndex();
    int j = this.Player.aircNumber();
    int k = localWing1.indexInSquadron();
    int m = localWing1.squadron().getWingsNumber();

    if (j == 2) setAsSquadLeader(); else {
      setAsGroupLeader();
    }
    Wing localWing2 = null;
    for (int n = 0; n < this.PlayerSquad.wing.length; n++) {
      if (this.PlayerSquad.wing[n] != null) {
        localWing2 = this.PlayerSquad.wing[n];
        break;
      }
    }

    if ((this.PlayerWing != localWing2) || (m < 2)) {
      if ((i == 0) && (j > 1)) {
        setAsWingLeader(k);
      } else {
        setAsWingman(i);
        if ((!isRemote()) && ((this.Player instanceof TypeTransport)))
          this.home.order[1].attrib |= 1;
      }
    }
    else if (i != 0) {
      setAsWingman(i);
    }
    else if (!isRemote()) {
      for (n = 0; n < 4; n++) {
        if (this.PlayerSquad.wing[n] == null) {
          this.home.order[(2 + n)].attrib |= 1;
        }
      }

    }

    curOrdersTree = null;
  }

  public void resetGameClear() {
    this.Player = null;
    this.PlayerWing = null;
    this.PlayerSquad = null;
    this.PlayerRegiment = null;
    for (int i = 0; i < this.CommandSet.length; i++)
      this.CommandSet[i] = null;
  }

  private void setAsSquadLeader() {
    this.home = SquadLeader;
    if (isRemote()) return;
    for (int i = 0; i < this.home.order.length; i++)
      if (this.home.order[i] != null)
        this.home.order[i].attrib &= -2;
  }

  private void setAsGroupLeader()
  {
    this.home = GroupLeader;
    if (isRemote()) return;
    for (int i = 0; i < this.home.order.length; i++)
      if (this.home.order[i] != null)
        this.home.order[i].attrib &= -2;
  }

  private void setAsWingLeader(int paramInt)
  {
    if (isRemote()) return;
    for (int i = 0; i < this.home.order.length; i++) {
      if (this.home.order[i] != null)
        this.home.order[i].attrib &= -2;
    }
    this.home.order[2].attrib |= 1;
    this.home.order[3].attrib |= 1;
    this.home.order[4].attrib |= 1;
    this.home.order[5].attrib |= 1;
    this.home.order[6].attrib |= 1;
    if ((paramInt >= 0) && (paramInt < 4))
      this.home.order[(2 + paramInt)].attrib &= -2;
  }

  private void setAsWingman(int paramInt)
  {
    setAsWingLeader(-1);
    if (isRemote()) return;
    if ((paramInt & 0x1) == 0) this.home.order[1].attrib &= -2; else
      this.home.order[1].attrib |= 1;
  }

  public void activate() {
    if (isActive()) return;

    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isDogfight()) && (Main.cur().mission.zutiRadar_EnableTowerCommunications))
    {
      if (World.getPlayerAircraft() != null)
        ((Maneuver)World.getPlayerAircraft().FM).silence = false;
      missionLoaded();
    }

    if (isLocal()) {
      if ((!Actor.isAlive(this.Player)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
      {
        return;
      }activateHotKeyCmd(true);
      disableAircraftCmds();
      HotKeyCmdEnv.enable("gui", false);
    }
    if (isLocalClient())
    {
      ((NetUser)NetEnv.host()).orderCmd(-1);
    }
    this.bActive = true;
    curOrdersTree = this;

    this.home.run();

    curOrdersTree = null;
  }
  public void unactivate() {
    if (!isActive()) return;

    if (isLocal()) {
      activateHotKeyCmd(false);
      enableAircraftCmds();
      HotKeyCmdEnv.enable("gui", true);
      HUD.order(null);
    }
    if (isLocalClient())
    {
      ((NetUser)NetEnv.host()).orderCmd(-2);
    }

    this.bActive = false;
  }

  private void disableAircraftCmds()
  {
    for (int i = 0; i < hotKeyEnvNames.length; i++) {
      HashMapInt localHashMapInt1 = HotKeyEnv.env(hotKeyEnvNames[i]).all();
      HashMapInt localHashMapInt2 = HotKeyEnv.env("orders").all();
      HashMapIntEntry localHashMapIntEntry = localHashMapInt2.nextEntry(null);
      int j;
      while (localHashMapIntEntry != null) {
        j = localHashMapIntEntry.getKey();
        String str = (String)localHashMapInt1.get(j);
        if (str != null)
          this.disabledHotKeys[i].put(j, str);
        localHashMapIntEntry = localHashMapInt2.nextEntry(localHashMapIntEntry);
      }
      localHashMapIntEntry = this.disabledHotKeys[i].nextEntry(null);
      while (localHashMapIntEntry != null) {
        j = localHashMapIntEntry.getKey();
        localHashMapInt1.remove(j);
        localHashMapIntEntry = this.disabledHotKeys[i].nextEntry(localHashMapIntEntry);
      }
    }
  }

  private void enableAircraftCmds()
  {
    for (int i = 0; i < hotKeyEnvNames.length; i++) {
      HashMapInt localHashMapInt = HotKeyEnv.env(hotKeyEnvNames[i]).all();
      HashMapIntEntry localHashMapIntEntry = this.disabledHotKeys[i].nextEntry(null);
      while (localHashMapIntEntry != null) {
        localHashMapInt.put(localHashMapIntEntry.getKey(), localHashMapIntEntry.getValue());
        localHashMapIntEntry = this.disabledHotKeys[i].nextEntry(localHashMapIntEntry);
      }
      this.disabledHotKeys[i].clear();
    }
  }

  public void execCmd(int paramInt) {
    Order[] arrayOfOrder = this.cur.order;
    int i = paramInt;

    curOrdersTree = this;
    int j;
    if (paramInt == 0) {
      for (j = arrayOfOrder.length - 1; j >= 0; j--)
        if (arrayOfOrder[j] != null) {
          arrayOfOrder[j].preRun();
          curOrdersTree = null;
          if (isLocalClient())
            ((NetUser)NetEnv.host()).orderCmd(0);
          return;
        }
    }
    else {
      for (j = 0; j < arrayOfOrder.length; j++) {
        if ((arrayOfOrder[j] != null) && 
          (paramInt == 0)) {
          if ((isLocal()) && ((arrayOfOrder[j].attrib & 0x1) != 0)) {
            unactivate();
          } else {
            if (isLocalClient()) {
              if ("Attack_My_Target".equals(arrayOfOrder[j].name))
                ((NetUser)NetEnv.host()).orderCmd(i, Selector.getTarget());
              else {
                ((NetUser)NetEnv.host()).orderCmd(i);
              }
            }
            try
            {
              arrayOfOrder[j].preRun();
              if ((isLocalServer()) || (isRemoteServer()))
                arrayOfOrder[j].run();
            } catch (Exception localException) {
              System.out.println("User command failed: " + localException.getMessage());
              localException.printStackTrace();
            }
            if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()) && (Main.cur().netServerParams.isCoop()) && (arrayOfOrder[j].subOrders == null))
            {
              chatLog(arrayOfOrder[j]);
            }
            if (arrayOfOrder[j].subOrders == null) unactivate();
          }
          curOrdersTree = null;
          return;
        }

        paramInt--;
      }
    }
    curOrdersTree = null;
  }

  protected void doCmd(int paramInt, boolean paramBoolean) {
    if (paramBoolean) return;
    if ((paramInt != 0) && (
      (!Actor.isAlive(this.Player)) || (World.isPlayerDead()) || (World.isPlayerParatrooper())))
    {
      return;
    }

    execCmd(paramInt);
  }

  private void activateHotKeyCmd(boolean paramBoolean)
  {
    int i;
    if (paramBoolean)
      for (i = 0; i < this.hotKeyCmd.length; i++)
        this.hotKeyCmd[i].enable(true);
    else
      for (i = 0; i < this.hotKeyCmd.length; i++)
        this.hotKeyCmd[i].enable(false);
  }

  public OrdersTree(boolean paramBoolean)
  {
    if (!paramBoolean) return;
    HotKeyCmdEnv.setCurrentEnv("orders");
    HotKeyEnv.fromIni("orders", Config.cur.ini, "HotKey orders");
    this.cmdEnv = HotKeyCmdEnv.currentEnv();
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "deactivate", null) {
      public boolean isDisableIfTimePaused() { return true; } 
      public void end() {
        if (Main3D.cur3D().ordersTree.isActive()) Main3D.cur3D().ordersTree.unactivate(); 
      }
      public void created() {
        setRecordId(261);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "activate", "0") {
      public boolean isDisableIfTimePaused() { return true; } 
      public void begin() {
        if (Main3D.cur3D().ordersTree.isActive()) Main3D.cur3D().ordersTree.unactivate(); else
          Main3D.cur3D().ordersTree.activate(); 
      }
      public void created() {
        setRecordId(260);
      }
    });
    this.hotKeyCmd = new HotKeyCmdFire[10];
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[0] =  = new HotKeyCmdFire(null, "order0", 0, 250));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[1] =  = new HotKeyCmdFire(null, "order1", 1, 251));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[2] =  = new HotKeyCmdFire(null, "order2", 2, 252));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[3] =  = new HotKeyCmdFire(null, "order3", 3, 253));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[4] =  = new HotKeyCmdFire(null, "order4", 4, 254));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[5] =  = new HotKeyCmdFire(null, "order5", 5, 255));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[6] =  = new HotKeyCmdFire(null, "order6", 6, 256));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[7] =  = new HotKeyCmdFire(null, "order7", 7, 257));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[8] =  = new HotKeyCmdFire(null, "order8", 8, 258));
    HotKeyCmdEnv.addCmd(this.hotKeyCmd[9] =  = new HotKeyCmdFire(null, "order9", 9, 259));
    activateHotKeyCmd(false);
  }

  public String[] getShipIDs()
  {
    return this.shipIDList;
  }

  public void addShipIDs(int paramInt1, int paramInt2, Actor paramActor, String paramString1, String paramString2)
  {
    if (paramInt2 == -1)
      this.shipIDList[paramInt1] = null;
    else
      this.shipIDList[paramInt1] = ("ID:" + Beacon.getBeaconID(paramInt2) + "  " + paramString1 + " " + paramString2);
  }

  private void setAsDogfight()
  {
    this.home = GroupLeader;
    if (!isRemote())
    {
      for (int i = 0; i < this.home.order.length; i++)
      {
        if (this.home.order[i] != null)
          this.home.order[i].attrib &= -2;
      }
    }
  }

  class HotKeyCmdFire extends HotKeyCmd
  {
    int cmd;

    public void begin()
    {
      OrdersTree.this.doCmd(this.cmd, true); } 
    public void end() { OrdersTree.this.doCmd(this.cmd, false); } 
    public boolean isDisableIfTimePaused() { return true; } 
    public HotKeyCmdFire(String paramString1, String paramInt1, int paramInt2, int arg5) {
      super(paramInt1, paramString1);
      this.cmd = paramInt2;
      int i;
      setRecordId(i);
    }
  }
}