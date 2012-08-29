//***********************************
//This class is for client side players only
//***********************************
package com.maddox.il2.game;

import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiSupportMethods_AI;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.ZutiSupportMethods_NetSend;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.ZutiSupportMethods_Air;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;

public class ZutiRearm_Bombs
{
	private long startTime = 0;
	private float bombsRearmTime = 0;
	private Aircraft playerAC = null;
	
	private int[] bombs = null;
	private BornPlace bornPlace = null;
	
	public ZutiRearm_Bombs(float rearmPenalty, int[] bombs)
	{
		this.bombs = bombs;
		this.playerAC = World.getPlayerAircraft();
		this.bornPlace = ZutiSupportMethods.isPilotLandedOnBPWithOwnRRRSettings(playerAC.FM);
		
		if( playerAC.FM.CT.wingControl > 0 )
		{
			com.maddox.il2.game.HUD.log("mds.unfoldWings");
			return;
		}
	
		calculateRearmingTimeConsumption(rearmPenalty);
		
		HUD.log( "mds.rearmingBombsTime", new java.lang.Object[]{new Integer(Math.round(bombsRearmTime))} );
		
		bombsRearmTime 		*= 1000;
		startTime = Time.current();
	}

	/**
	 * if -1 is returned abort execution
	 * @return
	 */
	public int updateTimer() 
	{
		if( !ZutiSupportMethods.allowRRR(playerAC) )
		{
			this.cancelTimer();
			return -1;
		}
		
		try
		{		
			if( bombsRearmTime > -1 && Time.current()-startTime > bombsRearmTime )
			{
				ZutiWeaponsManagement.rearmBombsFTanksTorpedoes(playerAC, this.bombs);

				if( playerAC instanceof NetAircraft )
					ZutiSupportMethods_Air.sendNetAircraftRearmOrdinance(playerAC, 2, -1, this.bombs);
				
				String userData = ZutiSupportMethods.getAircraftCompleteName(World.getPlayerAircraft());
				String userLocation = ZutiSupportMethods.getPlayerLocation();
				ZutiSupportMethods_NetSend.logMessage((NetUser)NetEnv.host(), userData + " rearmed bombs at " + userLocation);
				
				bombsRearmTime = -1;
				
				//Collect earned points
				ZutiSupportMethods_AI.collectPoints();
				//Reset processing of cargo drops since player rearmed the plane and had to land first (and survive :D)
				ZutiWeaponsManagement.ZUTI_PROCESS_CARGO_DROPS = true;
				
				stopTimer();
				
				return -1;
			}
		}
		catch(Exception ex){ex.printStackTrace();}

		return 0;
	}
	
	private void calculateRearmingTimeConsumption(float rearmPenalty)
	{
		/*
		com.maddox.il2.ai.BulletEmitter[][] weapons = World.getPlayerAircraft().FM.CT.Weapons;
		Property bombsCount = null;
		
		for( int i=0; i<weapons.length; i++ )
		{
			try
			{
				for( int j=0; j<weapons[i].length; j++ )
				{
					//Covers bombs, fuel tanks, torpedoes
					if( weapons[i][j] instanceof BombGun )
					{
						bombsCount = Property.get(weapons[i][j], "_count");
						if( bombsCount != null && bombsCount.intValue() < 1 )
							bombsCount.set(1);
						
						if( bornPlace == null )
							bombsRearmTime += (Mission.MDS_VARIABLES().zutiReload_OneBombFTankTorpedoeRearmSeconds * bombsCount.longValue());
						else
							bombsRearmTime += (bornPlace.zutiOneBombFTankTorpedoeRearmSeconds * bombsCount.longValue());
					}
				}
			}
			catch(Exception ex){}
		}
		*/
		int bombsCount = 0;
		for( int i=0; i<this.bombs.length; i++ )
		{
			bombsCount += bombs[i];
		}
		
		if( bornPlace == null )
			bombsRearmTime = (Mission.MDS_VARIABLES().zutiReload_OneBombFTankTorpedoeRearmSeconds * bombsCount);
		else
			bombsRearmTime = (bornPlace.zutiOneBombFTankTorpedoeRearmSeconds * bombsCount);
		
		System.out.println("Received bombs by weight: ");
		System.out.println("   250kg: " + bombs[0]);
		System.out.println("   500kg: " + bombs[1]);
		System.out.println("  1000kg: " + bombs[2]);
		System.out.println("  2000kg: " + bombs[3]);
		System.out.println("  5000kg: " + bombs[4]);
		System.out.println("    more: " + bombs[5]);
		System.out.println("  Bombs Rearm time: " + bombsRearmTime);
		System.out.println("  Rearming Penalty: " + rearmPenalty);

		bombsRearmTime = bombsRearmTime * rearmPenalty;
		
		System.out.println("--------------------------------------");
		System.out.println("Calculated Rearm Time: " + bombsRearmTime);
	}
	
	private int countLoadedBombs()
	{
		int counter = 0;
		com.maddox.il2.ai.BulletEmitter[][] weapons = World.getPlayerAircraft().FM.CT.Weapons;
		
		for( int i=0; i<weapons.length; i++ )
		{
			try
			{
				for( int j=0; j<weapons[i].length; j++ )
				{
					//Covers bombs, fuel tanks, torpedoes
					if( weapons[i][j] instanceof BombGun )
					{
						counter += ((BombGun)weapons[i][j]).countBullets();
					}
				}
			}
			catch(Exception ex){}
		}
		
		return counter;
	}
	
	private void stopTimer()
	{
		//Return unused bombs
		ZutiSupportMethods_NetSend.returnRRRResources_Bombs(this.bombs, playerAC.pos.getAbsPoint());
		
		int loadedBombs = countLoadedBombs();
		HUD.log("mds.rearmingBombsDone", new java.lang.Object[]{new Integer(loadedBombs)} );
		System.out.println("Bomb Rearming done! Loaded >" + loadedBombs +"< bombs.");
	}
	
	public void cancelTimer()
	{
		//Return unused bombs
		ZutiSupportMethods_NetSend.returnRRRResources_Bombs(this.bombs, playerAC.pos.getAbsPoint());
		
		com.maddox.il2.game.HUD.log("mds.rearmingBombsAborted");
		System.out.println("Bomb Rearming aborted!!!");
	}
}