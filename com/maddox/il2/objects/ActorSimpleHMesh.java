// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorSimpleHMesh.java

package com.maddox.il2.objects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.Spawn;

public class ActorSimpleHMesh extends com.maddox.il2.engine.ActorHMesh
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            if(actorspawnarg.isNoExistHARD(actorspawnarg.meshName, "mesh name not present"))
                return null;
            else
                return actorspawnarg.set(new ActorSimpleHMesh(actorspawnarg.meshName));
        }

        public SPAWN()
        {
        }
    }


    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorSimpleHMesh(java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
        super(s, loc);
        drawing(true);
    }

    public ActorSimpleHMesh(java.lang.String s, com.maddox.il2.engine.ActorPos actorpos)
    {
        super(s, actorpos);
        drawing(true);
    }

    public ActorSimpleHMesh(java.lang.String s)
    {
        super(s);
        drawing(true);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.objects.ActorSimpleHMesh.class, new SPAWN());
    }
}
