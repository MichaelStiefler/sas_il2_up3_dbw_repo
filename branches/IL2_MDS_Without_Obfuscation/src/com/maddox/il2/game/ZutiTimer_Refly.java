//***********************************
//This class is for client side players only
//***********************************
package com.maddox.il2.game;

import java.util.Timer;
import java.util.TimerTask;

public class ZutiTimer_Refly extends TimerTask
{
	private Timer timer = null;
	private int counter = 0;
	private float reflyDelay = 0.0F;

	public ZutiTimer_Refly(float delay)
	{
		reflyDelay = delay;
	
		System.out.println("Refly delay: " + reflyDelay + "s.");
	
		timer = new Timer();
		timer.schedule(this, 0, 1000);
	}

	public void run() 
	{		
		try
		{
			if( counter > reflyDelay-1 )
			{
				ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = true;
				this.stop();
			}
		}
		catch(Exception ex){System.out.println("ZutiTimer_Refly error, ID_01: " + ex.toString());}
		
		counter++;
	}
	
	public void stop()
	{
		System.out.println("Refly delay cleared.");
		this.cancel();
	}
}