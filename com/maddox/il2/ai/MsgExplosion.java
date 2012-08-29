// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MsgExplosion.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai:
//            Explosion, MsgExplosionListener

public class MsgExplosion extends com.maddox.rts.Message
{

    public static void send(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Actor actor1, float f, float f1, int i, float f2)
    {
        com.maddox.il2.ai.MsgExplosion.send(actor, s, point3d, actor1, f, f1, i, f2, 0);
    }

    public static void send(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Actor actor1, float f, float f1, int i, float f2, 
            int j)
    {
        explosion.chunkName = s;
        ((com.maddox.JGP.Tuple3d) (explosion.p)).set(((com.maddox.JGP.Tuple3d) (point3d)));
        explosion.radius = f2;
        explosion.initiator = actor1;
        explosion.power = f1;
        explosion.powerType = i;
        if(j == 1)
            explosion.bNuke = true;
        else
            explosion.bNuke = false;
        if(i == 1)
            explosion.computeSplinterParams(f);
        if(!com.maddox.il2.engine.Actor.isValid(actor1) && com.maddox.il2.game.Mission.isSingle() && (com.maddox.il2.game.Mission.cur().netObj() == null || com.maddox.il2.game.Mission.cur().netObj().isMaster()))
        {
            com.maddox.il2.engine.Engine _tmp = com.maddox.il2.engine.Engine.cur;
            explosion.initiator = actor1 = com.maddox.il2.engine.Engine.actorLand();
        }
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        if(actor1.isNet() && ((com.maddox.rts.NetObj) (actor1.net)).isMirror())
            return;
        if(com.maddox.il2.engine.Actor.isValid(actor))
        {
            ((com.maddox.rts.Message) (msg)).setListener(((java.lang.Object) (actor)));
            ((com.maddox.rts.Message) (msg)).send();
        }
        if(f2 <= 0.0F)
            return;
        com.maddox.il2.engine.Engine.collideEnv().getSphere(((java.util.AbstractCollection) (lst)), point3d, f2);
        int j1 = lst.size();
        if(j1 <= 0)
            return;
        explosion.chunkName = null;
        for(int k = 0; k < j1; k++)
        {
            com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)lst.get(k);
            if(com.maddox.il2.engine.Actor.isValid(actor2) && actor != actor2)
            {
                ((com.maddox.rts.Message) (msg)).setListener(((java.lang.Object) (actor2)));
                ((com.maddox.rts.Message) (msg)).send();
            }
        }

        lst.clear();
    }

    public static void resetGame()
    {
        explosion.chunkName = null;
        explosion.initiator = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.ai.MsgExplosionListener)
        {
            ((com.maddox.il2.ai.MsgExplosionListener)obj).msgExplosion(explosion);
            return true;
        } else
        {
            return false;
        }
    }

    public MsgExplosion()
    {
    }

    private static com.maddox.il2.ai.MsgExplosion msg = new MsgExplosion();
    private static com.maddox.il2.ai.Explosion explosion = new Explosion();
    private static java.util.ArrayList lst = new ArrayList();

}
