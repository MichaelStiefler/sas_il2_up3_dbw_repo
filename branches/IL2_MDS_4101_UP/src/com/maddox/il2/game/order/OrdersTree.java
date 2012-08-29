/* 410 class */
package com.maddox.il2.game.order;

import java.util.ArrayList;
import java.util.List;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.Selector;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.NetEnv;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

public class OrdersTree
{
	private static String		hotKeyEnvNames[]		= { "pilot", "aircraftView" };
	private HashMapInt			disabledHotKeys[]		= { new HashMapInt(), new HashMapInt() };

	static Orders				GroundTargets			= new Orders(new Order[] { (new Order("GroundTargets")), new OrderGT("Attack_All")
														{
															public void run()
															{
																this.run(0);
															}
														}, new OrderGT("Attack_Tanks")
														{
															public void run()
															{
																this.run(1);
															}
														}, new OrderGT("Attack_Flak")
														{
															public void run()
															{
																this.run(2);
															}
														}, new OrderGT("Attack_Vehicles")
														{
															public void run()
															{
																this.run(3);
															}
														}, new OrderGT("Attack_Train")
														{
															public void run()
															{
																this.run(4);
															}
														}, new OrderGT("Attack_Bridge")
														{
															public void run()
															{
																this.run(5);
															}
														}, new OrderGT("Attack_Ships")
														{
															public void run()
															{
																this.run(6);
															}
														}, null, new OrderBack() });

	static Orders				ToWingman				= new Orders(new Order[] { new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(),
			new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new OrderBreak(), new OrderRejoin(), null, new OrderBack() });

	static Orders				Change_Formation		= new Orders(new Order[] { new Order("C_F"), new OrderChange_Formation_To_1(), new OrderChange_Formation_To_2(),
			new OrderChange_Formation_To_3(), new OrderChange_Formation_To_4(), new OrderChange_Formation_To_5(), new OrderChange_Formation_To_6(), new OrderChange_Formation_To_7(), null,
			new OrderBack()							});

	static Orders				Tactical				= new Orders(new Order[] { new Order("Tactical"), new OrderBreak(), new OrderRejoin(), new OrderTighten_Formation(),
			new OrderLoosen_Formation(), new Order("Change_Formation_Submenu", Change_Formation), null, new OrderBack() });

	static Orders				Navigation				= new Orders(new Order[] { (new Order("Navigation")), new Order("Next_Checkpoint")
														{
															public void run()
															{
																Voice.setSyncMode(1);
																for (int i = 0; i < CommandSet().length; i++)
																{
																	if (Actor.isAlive(CommandSet()[i]) && CommandSet()[i].FM instanceof Pilot)
																	{
																		Pilot pilot = (Pilot) CommandSet()[i].FM;
																		if (!pilot.AP.way.isLanding())
																			pilot.AP.way.next();
																		if (isEnableVoice() && CommandSet()[i] != Player()
																				&& (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
																			Voice.speakNextWayPoint(CommandSet()[i]);
																	}
																}
																Voice.setSyncMode(0);
															}
														}, new Order("Prev_Checkpoint")
														{
															public void run()
															{
																Voice.setSyncMode(1);
																for (int i = 0; i < CommandSet().length; i++)
																{
																	if (Actor.isAlive(CommandSet()[i]) && CommandSet()[i].FM instanceof Pilot)
																	{
																		Pilot pilot = (Pilot) CommandSet()[i].FM;
																		if (!pilot.AP.way.isLanding())
																			pilot.AP.way.prev();
																		if (isEnableVoice() && CommandSet()[i] != Player()
																				&& (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
																			Voice.speakPrevWayPoint(CommandSet()[i]);
																	}
																}
																Voice.setSyncMode(0);
															}
														}, new Order("Return_To_Base")
														{
															public void run()
															{
																Voice.setSyncMode(1);
																boolean bool = false;
																Maneuver maneuver = (Maneuver) Player().FM;
																for (int i = 0; i < CommandSet().length; i++)
																{
																	Aircraft aircraft = CommandSet()[i];
																	if (Actor.isAlive(aircraft) && aircraft.FM instanceof Pilot)
																	{
																		Pilot pilot = (Pilot) CommandSet()[i].FM;
																		if (!pilot.AP.way.isLanding())
																		{
																			pilot.AP.way.last();
																			pilot.AP.way.prev();
																		}
																		if (pilot.Group != null)
																		{
																			pilot.Group.setGroupTask(1);
																			pilot.Group.timeOutForTaskSwitch = 480;
																		}
																		if (isEnableVoice() && aircraft != Player() && (aircraft.getWing() == Player().getWing() || aircraft.aircIndex() == 0))
																			Voice.speakReturnToBase(CommandSet()[i]);
																		if (aircraft != Player() && pilot.Group == maneuver.Group)
																			bool = true;
																	}
																}
																Voice.setSyncMode(0);
																if (bool)
																{
																	AirGroup airgroup = maneuver.Group;
																	AirGroup airgroup_0_ = new AirGroup(maneuver.Group);
																	airgroup_0_.rejoinGroup = null;
																	maneuver.Group.delAircraft(Player());
																	airgroup_0_.addAircraft(Player());
																	airgroup.setGroupTask(1);
																}
															}
														}, new Order("Hang_On_Here")
														{
															public void run()
															{
																for (int i = 0; i < CommandSet().length; i++)
																{
																	Voice.setSyncMode(1);
																	if (Actor.isAlive(CommandSet()[i]) && CommandSet()[i].FM instanceof Pilot)
																	{
																		Pilot pilot = (Pilot) CommandSet()[i].FM;
																		if (pilot.Group != null && pilot.Group.nOfAirc > 0 && pilot == pilot.Group.airc[0].FM && !pilot.isBusy()
																				&& Actor.isAlive(pilot.Group.airc[pilot.Group.nOfAirc - 1])
																				&& (pilot.Group.airc[pilot.Group.nOfAirc - 1].FM instanceof Pilot))
																		{
																			Pilot pilot_1_ = ((Pilot) pilot.Group.airc[pilot.Group.nOfAirc - 1].FM);
																			pilot.Group.setFormationAndScale((pilot_1_.formationType), (pilot_1_.formationScale), false);
																			pilot.Group.setGroupTask(1);
																			pilot.clear_stack();
																			pilot.push(45);
																			pilot.push(45);
																			pilot.pop();
																		}
																		if (isEnableVoice() && CommandSet()[i] != Player()
																				&& (CommandSet()[i].getWing() == Player().getWing() || CommandSet()[i].aircIndex() == 0))
																			Voice.speakHangOn(CommandSet()[i]);
																	}
																}
																Voice.setSyncMode(0);
															}
														}, null, new OrderBack() });

	static Orders				ToParaZveno				= new Orders(new Order[] { new Order("Task"), new OrderCover_Me(), new OrderTarget_All(), new OrderAttack_Fighters(),
			new OrderAttack_Bombers(), new OrderAttack_My_Target(), new Order("Ground_Targets", GroundTargets), new OrderDrop_Tanks(), new Order("Tactical_Submenu", Tactical),
			new Order("Navigation_Submenu", Navigation), null, new OrderBack() });

	static Orders				ToGroupSquad			= ToParaZveno;

	static Orders				Frequency				= new Orders(new Order[] { (new Order("Frequency")), new Order("Friendly")
														{
															public void preRun()
															{
																OrdersTree.curOrdersTree.frequency = true;
															}
														}, new Order("Enemy")
														{
															public void preRun()
															{
																OrdersTree.curOrdersTree.frequency = false;
															}
														}, null, new OrderBack() });

	//TODO: Added by |ZUTI|
	//--------------------------------------------------------------------------------------------------------------------
	static Orders				ToLoadoutsTeam			= new Orders(new Order[] {
			//"only" 9 load options available... this is how many numbers we have at our disposal :)
			new Order("mds.loadout"), new ZutiOrder_Loadout("0"), new ZutiOrder_Loadout("1"), new ZutiOrder_Loadout("2"), new ZutiOrder_Loadout("3"), new ZutiOrder_Loadout("4"),
			new ZutiOrder_Loadout("5"), new ZutiOrder_Loadout("6"), new ZutiOrder_Loadout("7"), new ZutiOrder_Loadout("8"), new OrderBack() });

	static Orders				ToEnginesRepairTeam		= new Orders(new Order[] {
			//"only" 9 load options available... this is how many numbers we have at our disposal :)
			new Order("mds.engines"), new ZutiOrder_EngineRepair("0"), new ZutiOrder_EngineRepair("1"), new ZutiOrder_EngineRepair("2"), new ZutiOrder_EngineRepair("3"),
			new ZutiOrder_EngineRepair("4"), new ZutiOrder_EngineRepair("5"), new ZutiOrder_EngineRepair("6"), new ZutiOrder_EngineRepair("7"), new ZutiOrder_EngineRepair("8"), new OrderBack() });

	//TODO: Added by |ZUTI|: for kicking multicrew gunners
	static Orders				ToGunnersTeam			= new Orders(new Order[] {
			//"only" 9 options available... this is how many numbers we have at our disposal :)
			new Order("mds.kickGunner"), new ZutiOrder_EjectGunner("0"), new ZutiOrder_EjectGunner("1"), new ZutiOrder_EjectGunner("2"), new ZutiOrder_EjectGunner("3"),
			new ZutiOrder_EjectGunner("4"), new ZutiOrder_EjectGunner("5"), new ZutiOrder_EjectGunner("6"), new ZutiOrder_EjectGunner("7"), new ZutiOrder_EjectGunner("8"), new OrderBack() });

	//TODO: Added by |ZUTI|: for transferring controls 
	static Orders				ToTransferTeam			= new Orders(new Order[] {
			//"only" 9 options available... this is how many numbers we have at our disposal :)
			new Order("mds.transferControl"), new ZutiOrder_TransferControls("0"), new ZutiOrder_TransferControls("1"), new ZutiOrder_TransferControls("2"), new ZutiOrder_TransferControls("3"),
			new ZutiOrder_TransferControls("4"), new ZutiOrder_TransferControls("5"), new ZutiOrder_TransferControls("6"), new ZutiOrder_TransferControls("7"),
			new ZutiOrder_TransferControls("mds.retakeControl"), new OrderBack() });

	static Orders				ToRearmingTeam			= new Orders(new Order[] { new Order("mds.rearm"), new ZutiOrder_RearmAll(), new ZutiOrder_RearmGuns(), new ZutiOrder_RearmRockets(),
			new ZutiOrder_RearmBombs(), null, null, null, null, null, new OrderBack() });

	static Orders				ToUnloadingTeam			= new Orders(new Order[] { new ZutiOrder_UnloadAll(), new ZutiOrder_UnloadBullets(), new ZutiOrder_UnloadRockets(),
			new ZutiOrder_UnloadBombs(), null, new ZutiOrder_UnloadFuel(), null, null, null, new OrderBack() });

	static Orders				ToNavigationTeam		= new Orders(new Order[] { new Order("mds.vectoring"), new OrderVector_To_Home_Base(), new OrderVector_To_Target(),
			new ZutiOrder_VectorToNearestHB(), null, null, null, null, null, new OrderBack() });

	static Orders				ToReloadingTeam			= new Orders(new Order[] { new Order("mds.RRR2"), new ZutiOrder_StartAll(), (new Order("mds.rearm", ToRearmingTeam)),
			new ZutiOrder_RefuelAircraft(), new ZutiOrder_RepairAircraft(), new ZutiOrder_UnjamChocks(), null, (new Order("mds.repairEngines", ToEnginesRepairTeam)),
			(new Order("mds.loadoutOptions", ToLoadoutsTeam)), null, new OrderBack() });

	static Orders				ToTurrets				= new Orders(new Order[] { new Order("mds.turrets"), new ZutiOrder_DisableTurrets(), new ZutiOrder_EnableTurrets(), null, null, null, null,
			null, null, new OrderBack()				});
	//--------------------------------------------------------------------------------------------------------------------

	static Orders				ToGroundControl			= new Orders(new Order[] { new Order("GroundControl"), new OrderRequest_Assistance(), new OrderRequest_For_Landing(),
			new OrderRequest_For_Takeoff(),
			//TODO: Added by |ZUTI|
			new Order("mds.vectoring", ToNavigationTeam), new Order("mds.transferControl", ToTransferTeam), new Order("mds.kickGunner", ToGunnersTeam), new Order("mds.unload", ToUnloadingTeam),
			new Order("mds.RRR2", ToReloadingTeam), new Order("mds.turrets", ToTurrets), new OrderBack() });

	static Orders				SquadLeader				= new Orders(new Order[] { (new Order("MainMenu")), new Order("Wingman", "deWingman", ToWingman)
														{
															public void run()
															{
																cset(Wingman());
																super.run();
															}
														}, new Order("Para_1", "dePara_1", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[0]);
																super.run();
															}
														}, new Order("Para_2", "dePara_2", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[1]);
																super.run();
															}
														}, new Order("Para_3", "dePara_3", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[2]);
																super.run();
															}
														}, new Order("Para_4", "dePara_4", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[3]);
																super.run();
															}
														}, new Order("Group", "deGroup", ToGroupSquad)
														{
															public void run()
															{
																cset(PlayerSquad());
																super.run();
															}
														}, new OrderAnyone_Help_Me(), (new Order("Ground_Control", ToGroundControl)), new Order("Frequency", Frequency), null, new OrderBack() });

	//This Orders is, well, all orders in a group, make your own one for DS since half of theese are not awailable for dogfight anyway (ToParaZveno mostly)
	static Orders				GroupLeader				= new Orders(new Order[] { (new Order("MainMenu")), new Order("Wingman", "deWingman", ToWingman)
														{
															public void run()
															{
																cset(Wingman());
																super.run();
															}
														}, new Order("Zveno_1", "deZveno_1", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[0]);
																super.run();
															}
														}, new Order("Zveno_2", "deZveno_2", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[1]);
																super.run();
															}
														}, new Order("Zveno_3", "deZveno_3", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[2]);
																super.run();
															}
														}, new Order("Zveno_4", "deZveno_4", ToParaZveno)
														{
															public void run()
															{
																cset(PlayerSquad().wing[3]);
																super.run();
															}
														}, new Order("Squadron", "deSquadron", ToGroupSquad)
														{
															public void run()
															{
																cset(PlayerSquad());
																super.run();
															}
														}, new OrderAnyone_Help_Me(), (new Order("Ground_Control", ToGroundControl)), new Order("Frequency", Frequency), new OrderBack() });

	public static final String	envName					= "orders";
	public static OrdersTree	curOrdersTree;
	protected Orders			home;
	protected Orders			cur;
	protected boolean			bActive					= false;
	protected HotKeyCmdEnv		cmdEnv;
	protected Aircraft			Player;
	protected Wing				PlayerWing;
	protected Squadron			PlayerSquad;
	protected Regiment			PlayerRegiment;
	protected Aircraft[]		CommandSet				= new Aircraft[16];
	protected boolean			frequency				= true;
	protected boolean			alone					= false;
	protected static final int	CONTEXT_LOCAL_SERVER	= 0;
	protected static final int	CONTEXT_LOCAL_CLIENT	= 1;
	protected static final int	CONTEXT_REMOTE_SERVER	= 2;
	protected int				context;
	private HotKeyCmdFire[]		hotKeyCmd;

	class HotKeyCmdFire extends HotKeyCmd
	{
		int	cmd;

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

		public HotKeyCmdFire(String string, String string_14_, int i, int i_15_)
		{
			super(true, string_14_, string);
			cmd = i;
			setRecordId(i_15_);
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

	private void chatLog(Order order)
	{
		if (Actor.isValid(Player))
		{
			int i = Player.getArmy();
			ArrayList arraylist = new ArrayList();
			while (order != null)
			{
				arraylist.add(0, order.name(i));
				if (order.orders.upOrders == null)
					break;
				if (order != order.orders.order[0])
					order = order.orders.order[0];
				else
				{
					if (order.orders.upOrders.upOrders == null)
						break;
					order = order.orders.upOrders.order[0];
				}
			}
			StringBuffer stringbuffer = new StringBuffer();
			for (int i_16_ = 0; i_16_ < arraylist.size(); i_16_++)
			{
				if (i_16_ > 0)
					stringbuffer.append(" ");
				stringbuffer.append((String) arraylist.get(i_16_));
			}
			NetUser netuser = null;
			List list = NetEnv.hosts();
			ArrayList arraylist_17_ = new ArrayList();
			for (int i_18_ = 0; i_18_ < list.size(); i_18_++)
			{
				NetUser netuser_19_ = (NetUser) list.get(i_18_);
				if (this == netuser_19_.ordersTree)
					netuser = netuser_19_;
				else if (i == netuser_19_.getArmy())
					arraylist_17_.add(netuser_19_);
			}
			if (netuser == null)
				netuser = (NetUser) NetEnv.host();
			else if (i == ((NetUser) NetEnv.host()).getArmy())
				arraylist_17_.add(NetEnv.host());
			arraylist_17_.add(netuser);
			Main.cur().chat.send(netuser, stringbuffer.toString(), arraylist_17_, (byte) 1, i == World.getPlayerArmy());
		}
	}

	public Boolean frequency()
	{
		return new Boolean(frequency);
	}

	public void setFrequency(boolean bool)
	{
		frequency = bool;
	}

	public void setFrequency(Boolean bool)
	{
		if (bool != null)
			frequency = bool.booleanValue();
		else
			frequency = false;
	}

	public boolean alone()
	{
		return alone;
	}

	protected void _cset(Actor actor)
	{
		for (int i = 0; i < 16; i++)
			CommandSet[i] = null;
		if (frequency)
		{
			alone = false;
			if (Actor.isAlive(actor))
			{
				if (actor instanceof Aircraft)
				{
					CommandSet[0] = (Aircraft) actor;
					alone = true;
				}
				else if (actor instanceof Wing)
				{
					Wing wing = (Wing) actor;
					for (int i = 0; i < 4; i++)
					{
						if (Actor.isAlive(wing.airc[i]))
							CommandSet[i] = wing.airc[i];
					}
				}
				else if (actor instanceof Squadron)
				{
					Squadron squadron = (Squadron) actor;
					for (int i = 0; i < 16; i++)
					{
						if (squadron.wing[i >> 2] != null && Actor.isAlive(squadron.wing[i >> 2].airc[i & 0x3]))
							CommandSet[i] = squadron.wing[i >> 2].airc[i & 0x3];
					}
				}
			}
		}
	}

	public boolean isActive()
	{
		return bActive;
	}

	public void missionLoaded()
	{
		context = 0;
		missionLoaded(World.getPlayerAircraft());
	}

	public void netMissionLoaded(Aircraft aircraft)
	{
		if (aircraft == null)
			context = 2;
		else
		{
			boolean bool = Mission.isNet() && aircraft.netUser() != NetEnv.host();
			if (aircraft == World.getPlayerAircraft() && !bool)
			{
				if (Mission.isSingle() || Mission.isServer())
					context = 0;
				else
					context = 1;
			}
			else
				context = 2;
		}
		missionLoaded(aircraft);
	}

	private void missionLoaded(Aircraft aircraft)
	{
		Player = aircraft;
		if (Player != null)
		{
			curOrdersTree = this;

			//TODO: Added by |ZUTI|
			//--------------------------------------------
			if (Mission.isDogfight())
			{
				ZutiSupportMethods_GameOrder.setAsDogfight(this);
				curOrdersTree = null;
				return;
			}
			//--------------------------------------------

			Wing wing = Player.getWing();
			PlayerWing = wing;
			PlayerSquad = wing.squadron();
			PlayerRegiment = wing.regiment();
			int i = Player.aircIndex();
			int i_20_ = Player.aircNumber();
			int i_21_ = wing.indexInSquadron();
			int i_22_ = wing.squadron().getWingsNumber();
			if (i_20_ == 2)
				setAsSquadLeader();
			else
				setAsGroupLeader();
			Wing wing_23_ = null;
			for (int i_24_ = 0; i_24_ < PlayerSquad.wing.length; i_24_++)
			{
				if (PlayerSquad.wing[i_24_] != null)
				{
					wing_23_ = PlayerSquad.wing[i_24_];
					break;
				}
			}
			if (PlayerWing != wing_23_ || i_22_ < 2)
			{
				if (i == 0 && i_20_ > 1)
					setAsWingLeader(i_21_);
				else
				{
					setAsWingman(i);
					if (!isRemote() && Player instanceof TypeTransport)
						home.order[1].attrib |= 0x1;
				}
			}
			else if (i != 0)
				setAsWingman(i);
			else if (!isRemote())
			{
				for (int i_25_ = 0; i_25_ < 4; i_25_++)
				{
					if (PlayerSquad.wing[i_25_] == null)
						home.order[2 + i_25_].attrib |= 0x1;
				}
			}
			curOrdersTree = null;
		}
	}

	public void resetGameClear()
	{
		Player = null;
		PlayerWing = null;
		PlayerSquad = null;
		PlayerRegiment = null;
		for (int i = 0; i < CommandSet.length; i++)
			CommandSet[i] = null;
	}

	private void setAsSquadLeader()
	{
		home = SquadLeader;
		if (!isRemote())
		{
			for (int i = 0; i < home.order.length; i++)
			{
				if (home.order[i] != null)
					home.order[i].attrib &= ~0x1;
			}
		}
	}

	private void setAsGroupLeader()
	{
		home = GroupLeader;
		if (!isRemote())
		{
			for (int i = 0; i < home.order.length; i++)
			{
				if (home.order[i] != null)
					home.order[i].attrib &= ~0x1;
			}
		}
	}

	private void setAsWingLeader(int i)
	{
		if (!isRemote())
		{
			for (int i_26_ = 0; i_26_ < home.order.length; i_26_++)
			{
				if (home.order[i_26_] != null)
					home.order[i_26_].attrib &= ~0x1;
			}
			home.order[2].attrib |= 0x1;
			home.order[3].attrib |= 0x1;
			home.order[4].attrib |= 0x1;
			home.order[5].attrib |= 0x1;
			home.order[6].attrib |= 0x1;
			if (i >= 0 && i < 4)
				home.order[2 + i].attrib &= ~0x1;
		}
	}

	private void setAsWingman(int i)
	{
		setAsWingLeader(-1);
		if (!isRemote())
		{
			if ((i & 0x1) == 0)
				home.order[1].attrib &= ~0x1;
			else
				home.order[1].attrib |= 0x1;
		}
	}

	public void activate()
	{
		if (!isActive())
		{
			//TODO: Added by |ZUTI|
			if (Main.cur().netServerParams != null && Main.cur().netServerParams.isDogfight() && Mission.MDS_VARIABLES().zutiMisc_EnableTowerCommunications)
			{
				//Disable this or messages will not appear if you are joining DS
				if (World.getPlayerAircraft() != null && World.getPlayerAircraft().FM instanceof Maneuver)
					((Maneuver) World.getPlayerAircraft().FM).silence = false;
				missionLoaded();
			}

			if (isLocal())
			{
				if (!Actor.isAlive(Player) || World.isPlayerDead() || World.isPlayerParatrooper())
					return;
				activateHotKeyCmd(true);
				disableAircraftCmds();
				HotKeyCmdEnv.enable("gui", false);
			}
			if (isLocalClient())
				((NetUser) NetEnv.host()).orderCmd(-1);
			bActive = true;
			curOrdersTree = this;
			home.run();
			curOrdersTree = null;
		}
	}

	public void unactivate()
	{
		if (isActive())
		{
			if (isLocal())
			{
				activateHotKeyCmd(false);
				enableAircraftCmds();
				HotKeyCmdEnv.enable("gui", true);
				HUD.order(null);
			}
			if (isLocalClient())
				((NetUser) NetEnv.host()).orderCmd(-2);
			bActive = false;
		}
	}

	private void disableAircraftCmds()
	{
		for (int i = 0; i < hotKeyEnvNames.length; i++)
		{
			HashMapInt hashmapint = HotKeyEnv.env(hotKeyEnvNames[i]).all();
			HashMapInt hashmapint1 = HotKeyEnv.env("orders").all();
			for (HashMapIntEntry hashmapintentry = hashmapint1.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint1.nextEntry(hashmapintentry))
			{
				int j = hashmapintentry.getKey();
				String s = (String) hashmapint.get(j);
				if (s != null)
					disabledHotKeys[i].put(j, s);
			}

			for (HashMapIntEntry hashmapintentry1 = disabledHotKeys[i].nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = disabledHotKeys[i].nextEntry(hashmapintentry1))
			{
				int k = hashmapintentry1.getKey();
				hashmapint.remove(k);
			}
		}
	}

	private void enableAircraftCmds()
	{
		for (int i = 0; i < hotKeyEnvNames.length; i++)
		{
			HashMapInt hashmapint = HotKeyEnv.env(hotKeyEnvNames[i]).all();
			for (HashMapIntEntry hashmapintentry = disabledHotKeys[i].nextEntry(null); hashmapintentry != null; hashmapintentry = disabledHotKeys[i].nextEntry(hashmapintentry))
				hashmapint.put(hashmapintentry.getKey(), hashmapintentry.getValue());

			disabledHotKeys[i].clear();
		}
	}

	public void execCmd(int i)
	{
		boolean zutiIsRRROrder = false;
		Order[] orders = cur.order;
		int i_28_ = i;
		curOrdersTree = this;
		if (i == 0)
		{
			for (int i_29_ = orders.length - 1; i_29_ >= 0; i_29_--)
			{
				if (orders[i_29_] != null)
				{
					orders[i_29_].preRun();
					curOrdersTree = null;

					if (!ZutiSupportMethods_GameOrder.isRRROrder(orders[i_29_]) && isLocalClient())
					{
						((NetUser) NetEnv.host()).orderCmd(0);
					}
					return;
				}
			}
		}
		else
		{
			for (int i_30_ = 0; i_30_ < orders.length; i_30_++)
			{
				if (orders[i_30_] != null && i == 0)
				{
					if (isLocal() && (orders[i_30_].attrib & 0x1) != 0)
						unactivate();
					else
					{
						//TODO: Added by |ZUTI|
						zutiIsRRROrder = ZutiSupportMethods_GameOrder.isRRROrder(orders[i_30_]);

						if (isLocalClient() && !zutiIsRRROrder)
						{
							if ("Attack_My_Target".equals(orders[i_30_].name))
								((NetUser) NetEnv.host()).orderCmd(i_28_, Selector.getTarget());
							else
								((NetUser) NetEnv.host()).orderCmd(i_28_);
						}
						try
						{
							orders[i_30_].preRun();
							if (isLocalServer() || isRemoteServer() || zutiIsRRROrder)
								orders[i_30_].run();
						}
						catch (Exception exception)
						{
							System.out.println("User command failed: " + exception.getMessage());
							exception.printStackTrace();
						}
						if (Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() && Main.cur().netServerParams.isCoop() && orders[i_30_].subOrders == null)
						{
							chatLog(orders[i_30_]);
						}
						if (orders[i_30_].subOrders == null)
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

	protected void doCmd(int i, boolean bool)
	{
		if (!bool && (i == 0 || (Actor.isAlive(Player) && !World.isPlayerDead() && !World.isPlayerParatrooper())))
			execCmd(i);
	}

	private void activateHotKeyCmd(boolean bool)
	{
		if (bool)
		{
			for (int i = 0; i < hotKeyCmd.length; i++)
				hotKeyCmd[i].enable(true);
		}
		else
		{
			for (int i = 0; i < hotKeyCmd.length; i++)
				hotKeyCmd[i].enable(false);
		}
	}

	public OrdersTree(boolean bool)
	{
		if (bool)
		{
			shipIDList = new java.lang.String[10];

			HotKeyCmdEnv.setCurrentEnv("orders");
			HotKeyEnv.fromIni("orders", Config.cur.ini, "HotKey orders");
			cmdEnv = HotKeyCmdEnv.currentEnv();
			HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "deactivate", null)
			{
				public boolean isDisableIfTimePaused()
				{
					return true;
				}

				public void end()
				{
					if (Main3D.cur3D().ordersTree.isActive())
						Main3D.cur3D().ordersTree.unactivate();
				}

				public void created()
				{
					setRecordId(261);
				}
			});
			HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "activate", "0")
			{
				public boolean isDisableIfTimePaused()
				{
					return true;
				}

				public void begin()
				{
					if (Main3D.cur3D().ordersTree.isActive())
						Main3D.cur3D().ordersTree.unactivate();
					else
						Main3D.cur3D().ordersTree.activate();
				}

				public void created()
				{
					setRecordId(260);
				}
			});
			hotKeyCmd = new HotKeyCmdFire[10];
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[0] = new HotKeyCmdFire(null, "order0", 0, 250));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[1] = new HotKeyCmdFire(null, "order1", 1, 251));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[2] = new HotKeyCmdFire(null, "order2", 2, 252));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[3] = new HotKeyCmdFire(null, "order3", 3, 253));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[4] = new HotKeyCmdFire(null, "order4", 4, 254));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[5] = new HotKeyCmdFire(null, "order5", 5, 255));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[6] = new HotKeyCmdFire(null, "order6", 6, 256));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[7] = new HotKeyCmdFire(null, "order7", 7, 257));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[8] = new HotKeyCmdFire(null, "order8", 8, 258));
			if (cmdEnv != null)
			{
				/* empty */
			}
			HotKeyCmdEnv.addCmd(hotKeyCmd[9] = new HotKeyCmdFire(null, "order9", 9, 259));
			activateHotKeyCmd(false);
		}
	}

	public static final java.lang.Boolean	FREQ_FRIENDLY	= new Boolean(true);
	public static final java.lang.Boolean	FREQ_ENEMY		= new Boolean(false);
	public static final int					shipIDListSize	= 10;
	private java.lang.String				shipIDList[];

	public java.lang.String[] getShipIDs()
	{
		return shipIDList;
	}

	public void addShipIDs(int i, int j, com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
	{
		if (j == -1)
			shipIDList[i] = null;
		else
			shipIDList[i] = "ID:" + com.maddox.il2.objects.vehicles.radios.Beacon.getBeaconID(j) + "  " + s + " " + s1;
	}
}