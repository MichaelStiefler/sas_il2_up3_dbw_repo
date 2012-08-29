package com.maddox.il2.game.order;

import com.maddox.il2.ai.World;
import com.maddox.il2.fm.ZutiSupportMethods_FM;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.objects.air.Aircraft;

class ZutiOrder_UnloadFuel extends com.maddox.il2.game.order.Order
{	
	public ZutiOrder_UnloadFuel()
	{
		super("mds.unloadFuel");
	}
	
	public void run()
	{
		try
		{
			Aircraft aircraft = World.getPlayerAircraft();
			if( !ZutiSupportMethods.allowRRR(aircraft) )
			{
				com.maddox.il2.game.HUD.log("mds.checkChocksPosition");
				return;
			}
			
			if( aircraft != null && aircraft.isAlive() )
			{
				ZutiSupportMethods_FM.startUnloadingFuel();
			}
		}
		catch(Exception ex){ex.printStackTrace();}
	}	
}