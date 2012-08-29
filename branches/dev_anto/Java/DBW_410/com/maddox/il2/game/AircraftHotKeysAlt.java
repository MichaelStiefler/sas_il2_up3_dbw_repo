// DBW  source  12Build250711
// Source File Name:   AircraftHotKeysAlt.java

// Added hirarchy to allow modification of methods

package com.maddox.il2.game;

import com.maddox.il2.fm.*;
import com.maddox.il2.objects.air.*;

public class AircraftHotKeysAlt
{

//   E X T E R N A L   M E T H O D   for  AircraftHotKeys

    public boolean SetFlapsHotKeys (int direction, RealFlightModel RFM)
    {
// TODO : dummy
    return false;
    }

// to be used with FI_156C, MS_500, MS__502, MS_505, OKA_38 and L-5 present in the game
    public boolean SetFlapsHotKeys_ (int direction, RealFlightModel RFM)
    {
        boolean bReturn = false;
        int i = direction % 2;	// even number >> 0 - flaps up, odd number >> 1 - flaps down
//System.out.println( "*** Moving Flaps " + (i == 0 ? "Up" : "Down"));
        switch (i) {
            case 0:
                        if((RFM.actor instanceof FI_156C) || (RFM.actor instanceof MS_500) || (RFM.actor instanceof MS__502) || (RFM.actor instanceof MS_505) || (RFM.actor instanceof OKA_38))
                        {
                            bReturn = true;
                            if(!RFM.CT.bHasFlapsControlRed)
                            {
                                if(RFM.CT.FlapsControl > 0.56F)
                                {
                                    RFM.CT.FlapsControl = 0.56F;
                                    HUD.log("Flaps2");
                                    break;
                                }
                                if(RFM.CT.FlapsControl > 0.28F)
                                {
                                    RFM.CT.FlapsControl = 0.28F;
                                    HUD.log("Flaps1");
                                    break;
                                }
                                if(RFM.CT.FlapsControl > 0.0F)
                                {
                                    RFM.CT.FlapsControl = 0.0F;
                                    HUD.log("FlapsRaised");
                                }
                                break;
                            }
                            if(RFM.CT.FlapsControl > 0.6F)
                            {
                                RFM.CT.FlapsControl = 0.0F;
                                HUD.log("FlapsRaised");
                            }
                            break;
                        }
                        if(RFM.actor instanceof L_5)
                        {
                            bReturn = true;
                            if(!RFM.CT.bHasFlapsControlRed)
                            {
                                if(RFM.CT.FlapsControl > 0.66F)
                                {
                                    RFM.CT.FlapsControl = 0.66F;
                                    HUD.log("Flaps5");
                                    break;
                                }
                                if(RFM.CT.FlapsControl > 0.33F)
                                {
                                    RFM.CT.FlapsControl = 0.33F;
                                    HUD.log("Flaps4");
                                    break;
                                }
                                if(RFM.CT.FlapsControl > 0.0F)
                                {
                                    RFM.CT.FlapsControl = 0.0F;
                                    HUD.log("FlapsRaised");
                                }
                                break;
                            }
                            if(RFM.CT.FlapsControl > 0.7F)
                            {
                                RFM.CT.FlapsControl = 0.0F;
                                HUD.log("FlapsRaised");
                            }
                            break;
                        }
            case 1:
                       if((RFM.actor instanceof FI_156C) || (RFM.actor instanceof MS_500) || (RFM.actor instanceof MS__502) || (RFM.actor instanceof MS_505) || (RFM.actor instanceof OKA_38))
                        {
                            bReturn = true;
                            if(!RFM.CT.bHasFlapsControlRed)
                            {
                                if(RFM.CT.FlapsControl < 0.28F)
                                {
                                    RFM.CT.FlapsControl = 0.28F;
                                    HUD.log("Flaps1");
                                    break;
                                }
                                if(RFM.CT.FlapsControl < 0.56F)
                                {
                                    RFM.CT.FlapsControl = 0.56F;
                                    HUD.log("Flaps2");
                                    break;
                                }
                                if(RFM.CT.FlapsControl < 1.0F)
                                {
                                    RFM.CT.FlapsControl = 1.0F;
                                    HUD.log("Flaps3");
                                }
                                break;
                            }
                            if(RFM.CT.FlapsControl < 0.6F)
                            {
                                RFM.CT.FlapsControl = 1.0F;
                                HUD.log("Flaps3");
                            }
                            break;
                        }
                        if(RFM.actor instanceof L_5)
                        {
                            bReturn = true;
                            if(!RFM.CT.bHasFlapsControlRed)
                            {
                                if(RFM.CT.FlapsControl < 0.33F)
                                {
                                    RFM.CT.FlapsControl = 0.33F;
                                    HUD.log("Flaps4");
                                    break;
                                }
                                if(RFM.CT.FlapsControl < 0.66F)
                                {
                                    RFM.CT.FlapsControl = 0.66F;
                                    HUD.log("Flaps5");
                                    break;
                                }
                                if(RFM.CT.FlapsControl < 1.0F)
                                {
                                    RFM.CT.FlapsControl = 1.0F;
                                    HUD.log("Flaps6");
                                }
                                break;
                            }
                            if(RFM.CT.FlapsControl < 0.7F)
                            {
                                RFM.CT.FlapsControl = 1.0F;
                                HUD.log("Flaps6");
                            }
                            break;
                        }
            default:
                break;
        }
    return bReturn;
    }

    public AircraftHotKeysAlt()
    {
    }

}