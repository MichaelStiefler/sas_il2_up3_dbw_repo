// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorNet.java

package com.maddox.il2.engine;

import com.maddox.rts.NetChannel;
import com.maddox.rts.NetObj;

// Referenced classes of package com.maddox.il2.engine:
//            Actor

public abstract class ActorNet extends com.maddox.rts.NetObj
{

    public com.maddox.il2.engine.Actor actor()
    {
        return (com.maddox.il2.engine.Actor)superObj;
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor()))
            return;
        if(actor().isSpawnFromMission())
            return;
        if(netchannel.isMirrored(this))
            return;
        try
        {
            if(netchannel.userState == 0)
            {
                com.maddox.rts.NetMsgSpawn netmsgspawn = actor().netReplicate(netchannel);
                if(netmsgspawn != null)
                {
                    postTo(netchannel, netmsgspawn);
                    actor().netFirstUpdate(netchannel);
                }
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.engine.ActorNet.printDebug(exception);
        }
        return;
    }

    public ActorNet(com.maddox.il2.engine.Actor actor1, int i)
    {
        super(actor1, i);
    }

    public ActorNet(com.maddox.il2.engine.Actor actor1)
    {
        super(actor1);
    }

    public ActorNet(com.maddox.il2.engine.Actor actor1, com.maddox.rts.NetChannel netchannel, int i)
    {
        super(actor1, netchannel, i);
    }
}
