// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderRequest_For_Takeoff extends com.maddox.il2.game.order.Order
{

    public OrderRequest_For_Takeoff()
    {
        super("Request_For_Takeoff");
    }

    public void run()
    {
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Player().FM;
        if(maneuver.canITakeoff())
            com.maddox.il2.objects.sounds.Voice.speakTakeoffPermited((com.maddox.il2.objects.air.Aircraft)maneuver.actor);
        else
            com.maddox.il2.objects.sounds.Voice.speakTakeoffDenied((com.maddox.il2.objects.air.Aircraft)maneuver.actor);
    }
}
