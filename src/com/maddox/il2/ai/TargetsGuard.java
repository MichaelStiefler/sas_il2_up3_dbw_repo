/*4.10.1 class*/
package com.maddox.il2.ai;

import java.util.ArrayList;
import java.util.List;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.gui.ZutiSupportMethods_GUI;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.ZutiSupportMethods_NetSend;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;

public class TargetsGuard implements MsgTimeOutListener
{
	public static final long TIME_STEP = 1000L;
	private boolean bActive = false;
	private MsgTimeOut ticker;
	private boolean bDead = true;
	private boolean bTaskComplete = false;
	private int checkType;
	private ArrayList targets = new ArrayList();

	/**
	 * By |ZUTI|: only for single player missions?
	 * @return
	 */
	public boolean isAlive()
	{
		return !bDead;
	}

	/**
	 * By |ZUTI|: only for single player missions?
	 * @return
	 */
	public boolean isTaskComplete()
	{
		return bTaskComplete;
	}

	private void checkTask()
	{
		if (!bDead)
		{
			boolean isTaskComplete = true;
			
			if (checkType == 2)
				isTaskComplete = false;
			
			boolean isTargetDead = false;
			boolean isTargetDead_Type2 = true;
			int size = targets.size();
			Target target = null;
			//Point3d point = null;
			for (int i = 0; i < size; i++)
			{
				target = (Target)targets.get(i);
				
				//TODO: Added by |ZUTI|: is target completed?
				//-------------------------------------------
				if( target.getDiedFlag() || target.isTaskComplete() )
				{
					if( Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() )
					{
						//Server must send data to clients
						ZutiSupportMethods_NetSend.removeTarget( target );
						
						//We should also remove target from targetpoint array
						ZutiSupportMethods_GUI.removeTargetPoint( target.zutiGetTargetDesignation() );
					}
				}
				
				//ZutiSupportMethods.listTargetPoints();
				//-------------------------------------------
				
				switch(checkType)
				{
					case 0:
						if (target.importance() == 0 && !target.isTaskComplete())
						{
							if (!target.isAlive())
								isTargetDead = true;
							
							isTaskComplete = false;
						}
						break;
					case 1:
						if (target.importance() == 1 && !target.isTaskComplete())
						{
							if (!target.isAlive())
								isTargetDead = true;
							
							isTaskComplete = false;
						}
						break;
					case 2:
						if (target.importance() == 2)
						{
							if (target.isAlive())
								isTargetDead_Type2 = false;
							else if (target.isTaskComplete())
								isTaskComplete = true;
							else
								isTargetDead = true;
						}
						break;
				}
			}
			
			if (checkType == 2)
			{
				if (isTaskComplete)
					isTargetDead = false;
				else if (!isTargetDead_Type2)
					return;
			}
						
			if(isTargetDead)
			{
				bTaskComplete = false;
				bDead = true;
				doMissionComplete();
			}
			else if(isTaskComplete)
			{
				bTaskComplete = true;
				bDead = true;
				doMissionComplete();
			}
		}
	}

	private void doScores(Target target, int i)
	{
		World.cur().scoreCounter.targetOn(target, target.isTaskComplete());
		EventLog.type("Target " + i + (target.isTaskComplete() ? " Complete" : " Failed"));
	}

	public void msgTimeOut(Object object)
	{
		if (bActive && Mission.isPlaying())
		{
			//TODO: Added by |ZUTI|: because of new MDS objectives, check if they were completed
			//-------------------------------------------------
			if( zutiCheckMDSObjectives() )
			{
				//If above method returns true = we have a victorious army, stop timer
				return;
			}
			//-------------------------------------------------
			
			ticker.setTime(Time.current() + TIME_STEP);
			ticker.post();
			long l = Time.current();
			boolean bool = false;
			int size = targets.size();
			for (int i = 0; i < size; i++)
			{
				Target target = (Target) targets.get(i);
				if (target.isAlive())
				{
					if (target.checkPeriodic())
					{
						bool = true;
						doScores(target, i);
					}
					if (target.timeout > 0L && l > target.timeout)
					{
						target.timeout = -target.timeout;
						if (target.checkTimeoutOff())
						{
							doScores(target, i);
							bool = true;
						}
					}
				}
			}
			if (bool) 
				checkTask();
		}
	}

	protected void checkActorDied(Actor actor)
	{
		if (bActive && Mission.isPlaying())
		{
			boolean bool = false;
			int i = targets.size();
			for (int i_4_ = 0; i_4_ < i; i_4_++)
			{
				Target target = (Target) targets.get(i_4_);
				if (target.isAlive() && target.checkActorDied(actor))
				{
					bool = true;
					doScores(target, i_4_);
					
					//TODO: Added by |ZUTI|: is target completed?
					//-------------------------------------------
					if( target.getDiedFlag() || target.isTaskComplete() )
					{
						/*
						System.out.println("    Target that is completed: " + target.toString());
						System.out.println("    TargetsGuard isTaskComplete() = " + target.isTaskComplete());
						System.out.println("    TargetsGuard getDiedFlag() = " + target.getDiedFlag());
						System.out.println("    TargetsGuard name() = " + target.name());
						*/
						if( Main.cur() != null && Main.cur().netServerParams != null && Main.cur().netServerParams.isMaster() )
						{
							//Server must send data to clients
							ZutiSupportMethods_NetSend.removeTarget( target );
							
							//We should also remove target from targetpoint array
							ZutiSupportMethods_GUI.removeTargetPoint( target.zutiGetTargetDesignation() );
						}
					}
					
					//ZutiSupportMethods.listTargetPoints();
					//-------------------------------------------
				}
			}
			if (actor == World.getPlayerAircraft() && !World.isPlayerRemoved() && !bDead && Mission.isSingle())
			{
				bTaskComplete = false;
				bDead = true;
				doMissionComplete();
			}
			else if(bool)
				checkTask();
		}
	}

	protected void checkTaskComplete(Actor actor)
	{
		if (bActive && Mission.isPlaying())
		{
			boolean bool = false;
			int i = targets.size();
			for (int i_5_ = 0; i_5_ < i; i_5_++)
			{
				Target target = (Target) targets.get(i_5_);
				if (target.isAlive() && target.checkTaskComplete(actor))
				{
					bool = true;
					doScores(target, i_5_);
				}
			}
			if(bool)
				checkTask();
		}
	}

	protected void addTarget(Target target)
	{
		targets.add(target);
	}

	//TODO: |ZUTI|: when porting just copy complete method
	public void doMissionComplete()
	{
		//Added by |ZUTI|
		int zutiVictoriousArmy = NetUser.getArmyCoopWinner();
		//Comment by |ZUTI|: if this method is called then IL2 target objectives must have been completed
		//Check if MDS objectives are also set AND if they are, act accordingly. Once this method is called
		//it should not be called again as I also set zutiNoTargetsPresent variable to false which should
		//tell the program to execute zutiDoMissionComplete method.
		boolean zutiMdsObjectivesCompleted = false;
		if( zutiMDSObjectivesEnabled_Red && ZutiSupportMethods.isMDSObjectivesCompleted(1) )
		{
			zutiVictoriousArmy = 1;
			zutiMdsObjectivesCompleted = true;
		}
		else if( zutiMDSObjectivesEnabled_Blue && ZutiSupportMethods.isMDSObjectivesCompleted(2) )
		{
			zutiVictoriousArmy = 2;
			zutiMdsObjectivesCompleted = true;
		}
		else if( !zutiMDSObjectivesEnabled_Blue && !zutiMDSObjectivesEnabled_Red )
		{
			zutiMdsObjectivesCompleted = true;
		}
		
		if( !zutiMdsObjectivesCompleted )
		{
			//MDS objectives are not yet completed, checking for victorious army remains
			//System.out.println("Targets cleande, MDS objectives remain!!");
			zutiNoTargetsPresent = true;
			return;
		}
		
		//System.out.println("Mission complete!!!!!!!!!!!!!");
		//Added by |ZUTI|: First check for dogfight mission...
		//-------------------------------------------------------------
		if (Mission.isNet() && Main.cur().netServerParams.isDogfight())
		{
			try
			{
				if (Main.cur().netServerParams.isMaster())
					((NetUser) NetEnv.host()).coopMissionComplete(bTaskComplete);
			
				if(zutiVictoriousArmy == 1)
				{
					HUD.logCenter("mds.victory.redHBCondition");
					
					//Added by |ZUTI|: if mission has parameters about next mission, load it.
					int loadDelay = Mission.MDS_VARIABLES().zutiNextMission_LoadDelay;
					
					if(Mission.MDS_VARIABLES().zutiNextMission_Enable)
					{
						System.out.println("-=RED WON=-");
						StringBuffer sb = new StringBuffer();
						
						if( Mission.MDS_VARIABLES().zutiNextMission_RedWon != null && Mission.MDS_VARIABLES().zutiNextMission_RedWon.trim().length() > 0 )
						{
							sb.append("Mission: RED WON [");
							sb.append(Mission.MDS_VARIABLES().zutiNextMission_RedWon);
							sb.append(",");
							sb.append(Mission.MDS_VARIABLES().zutiNextMission_Difficulty);
							sb.append(",");
							sb.append(loadDelay);
							sb.append("]");
						}
						else
						{
							sb.append("Mission: RED WON [,,]");
						}

						EventLog.type(true, sb.toString());
						System.out.println(sb.toString());
						
						Mission.MDS_VARIABLES().zutiNextMission_Enable = false;
					}
				}
				else
				{
					HUD.logCenter("mds.victory.blueHBCondition");
					
					//Added by |ZUTI|: if mission has parameters about next mission, load it.
					int loadDelay = Mission.MDS_VARIABLES().zutiNextMission_LoadDelay;
					System.out.println("-=BLUE WON=-");
					StringBuffer sb = new StringBuffer();
					
					if(Mission.MDS_VARIABLES().zutiNextMission_Enable)
					{
						if( Mission.MDS_VARIABLES().zutiNextMission_BlueWon != null && Mission.MDS_VARIABLES().zutiNextMission_BlueWon.trim().length() > 0)
						{
							sb.append("Mission: BLUE WON [");
							sb.append(Mission.MDS_VARIABLES().zutiNextMission_BlueWon);
							sb.append(",");
							sb.append(Mission.MDS_VARIABLES().zutiNextMission_Difficulty);
							sb.append(",");
							sb.append(loadDelay);
							sb.append("]");
						}
						else
						{
							sb.append("Mission: BLUE WON [,,]");
						}
					}
					
					EventLog.type(true, sb.toString());
					System.out.println(sb.toString());
					
					Mission.MDS_VARIABLES().zutiNextMission_Enable = false;
				}
			}
			catch(Exception ex){ex.printStackTrace();}
		}
		//-------------------------------------------------------------
		else if (Mission.isNet() && Main.cur().netServerParams.isCoop())
		{
			if (Main.cur().netServerParams.isMaster())
				((NetUser) NetEnv.host()).coopMissionComplete(bTaskComplete);
				
			if (zutiVictoriousArmy == 1)
			{
				HUD.logCenter("RedWon");
				System.out.println("-------------------------------- RED WON ---------------------");
				EventLog.type(true, "Mission: RED WON");
			}
			else
			{
				HUD.logCenter("BlueWon");
				System.out.println("-------------------------------- BLUE WON ---------------------");
				EventLog.type(true, "Mission: BLUE WON");
			}
		}
		else if(bTaskComplete)
		{
			HUD.logCenter("MissionComplete");
			System.out.println("-------------------------------- MISSION COMPLETE ---------------------");
			EventLog.type(true, "Mission: COMPLETE");
		}
		else
		{
			HUD.logCenter("MissionFailed");
			System.out.println("-------------------------------- MISSION FAILED ---------------------");
			EventLog.type(true, "Mission: FAILED");
		}
	}

	public void activate()
	{
		//TODO: Changed by |ZUTI|: let's check for host type too. Targets must be activated only on master
		if (!bActive && (Main.cur().netServerParams == null || Main.cur().netServerParams.isMaster()))
		{
			//TODO: Added by |ZUTI|
			//-------------------------------------------------------------------------------
			zutiMDSObjectivesEnabled_Red = Mission.MDS_VARIABLES().zutiObjectives_enableRed;
			zutiMDSObjectivesEnabled_Blue = Mission.MDS_VARIABLES().zutiObjectives_enableBlue;
			//System.out.println("TargetsGuard - MDSObjectives enabled for red: " + zutiMDSObjectivesEnabled_Red + ", for blue: " + zutiMDSObjectivesEnabled_Blue);
			//-------------------------------------------------------------------------------
						
			bActive = true;
			bDead = false;
			bTaskComplete = false;
			
			try
			{
				if (ticker.busy())
					ticker.remove();
				//TODO: Added by |ZUTI|
				//if (targets.size() != 0)
				if( targets.size() != 0 || zutiMDSObjectivesEnabled_Red || zutiMDSObjectivesEnabled_Blue )
				{
					checkType = 2;
					int size = targets.size();
					for (int i = 0; i < size; i++)
					{
						Target target = (Target) targets.get(i);
						if (target.importance() < checkType) 
							checkType = target.importance();
					}
					
					//TODO: Added by |ZUTI|
					//--------------------------------
					if( size <= 0 )
						zutiNoTargetsPresent = true;
					else
						zutiNoTargetsPresent = false;
					//--------------------------------
					
					ticker.setTime(Time.current() + TIME_STEP);
					ticker.post();
				}
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	}

	public void resetGame()
	{
		bActive = false;
		bDead = true;
		bTaskComplete = false;
		int i = targets.size();
		for (int i_7_ = 0; i_7_ < i; i_7_++)
		{
			Target target = (Target) targets.get(i_7_);
			if (Actor.isValid(target)) target.destroy();
		}
		targets.clear();
	}

	protected TargetsGuard()
	{
		ticker = new MsgTimeOut(null);
		ticker.setNotCleanAfterSend();
		ticker.setListener(this);
	}
	
	//TODO: |ZUTI| variables and methods
	//--------------------------------------------------------------------------
	//deactivated targets temporary list
	private boolean zutiMDSObjectivesEnabled_Red;
	private boolean zutiMDSObjectivesEnabled_Blue;
	private boolean zutiNoTargetsPresent;
	
	private boolean zutiCheckMDSObjectives()
	{
		//this method is only used for when general targets are not used or have already been completed
		if( zutiNoTargetsPresent && zutiMDSObjectivesEnabled_Red && ZutiSupportMethods.isMDSObjectivesCompleted(1) )
		{
			ZutiSupportMethods.doMissionComplete(1);
			return true;
		}
		if( zutiNoTargetsPresent && zutiMDSObjectivesEnabled_Blue && ZutiSupportMethods.isMDSObjectivesCompleted(2) )
		{
			ZutiSupportMethods.doMissionComplete(2);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method returns list of all targets. Call with:
	 * World.cur().targetsGuard.zutiGetTargets();
	 * @return
	 */
	public List zutiGetTargets()
	{
		if( targets == null )
			targets = new ArrayList();
		
		return targets;
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}