// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgShot.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;

// Referenced classes of package com.maddox.il2.ai:
//            MsgShotListener, Shot

public class MsgShot extends com.maddox.rts.Message
{

    public static com.maddox.il2.ai.Shot send(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3f vector3f, float f, com.maddox.il2.engine.Actor actor1, float f1, int i, 
            double d)
    {
        shot.chunkName = s;
        shot.p.set(point3d);
        shot.v.set(vector3f);
        shot.mass = f;
        shot.initiator = actor1;
        shot.power = f1;
        shot.powerType = i;
        shot.tickOffset = d;
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(com.maddox.il2.engine.Engine.land().isWater(point3d.x, point3d.y))
                shot.bodyMaterial = 1;
            else
                shot.bodyMaterial = 0;
        } else
        {
            shot.bodyMaterial = 2;
        }
        shot.bodyNormal.negate(vector3f);
        if(!com.maddox.il2.engine.Actor.isValid(actor1) && com.maddox.il2.game.Mission.isSingle() && (com.maddox.il2.game.Mission.cur().netObj() == null || com.maddox.il2.game.Mission.cur().netObj().isMaster()))
        {
            com.maddox.il2.engine.Engine _tmp = com.maddox.il2.engine.Engine.cur;
            shot.initiator = actor1 = com.maddox.il2.engine.Engine.actorLand();
        }
        if(com.maddox.il2.engine.Actor.isValid(actor1) && com.maddox.il2.engine.Actor.isValid(actor) && (!actor1.isNet() || !actor1.net.isMirror()))
            msg.send(actor);
        return shot;
    }

    public static void resetGame()
    {
        shot.chunkName = null;
        shot.initiator = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.ai.MsgShotListener)
        {
            ((com.maddox.il2.ai.MsgShotListener)obj).msgShot(shot);
            return true;
        } else
        {
            return false;
        }
    }

    public MsgShot()
    {
    }

    private static com.maddox.il2.ai.Shot shot = new Shot();
    private static com.maddox.il2.ai.MsgShot msg = new MsgShot();

}
