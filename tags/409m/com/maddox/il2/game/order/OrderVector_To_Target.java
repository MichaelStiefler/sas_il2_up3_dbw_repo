// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

// Referenced classes of package com.maddox.il2.game.order:
//            Order

class OrderVector_To_Target extends com.maddox.il2.game.order.Order
{

    public OrderVector_To_Target()
    {
        super("Vector_To_Target");
    }

    public void run()
    {
        com.maddox.il2.ai.Way way = Player().FM.AP.way;
        com.maddox.il2.ai.WayPoint waypoint = way.curr();
        waypoint.getP(V2);
        for(int i = way.Cur(); i < way.size(); i++)
        {
            com.maddox.il2.ai.WayPoint waypoint1 = way.get(i);
            if(waypoint1.Action == 3 || waypoint1.getTarget() != null && waypoint1.Action != 2)
                waypoint1.getP(V2);
        }

        Player().FM.actor.pos.getAbs(V1);
        V1.sub(V2);
        if(isEnableVoice())
            com.maddox.il2.objects.sounds.Voice.speakHeadingToTarget(Player(), V1);
    }

    private static com.maddox.JGP.Point3d V1 = new Point3d();
    private static com.maddox.JGP.Point3d V2 = new Point3d();

}
