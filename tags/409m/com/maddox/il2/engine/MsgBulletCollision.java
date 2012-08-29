// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgBulletCollision.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;

// Referenced classes of package com.maddox.il2.engine:
//            BulletGeneric, Actor, Engine

public class MsgBulletCollision extends com.maddox.rts.Message
{

    public MsgBulletCollision()
    {
        collided = null;
        collidedChunk = null;
    }

    public static void post(long l, double d, com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.BulletGeneric bulletgeneric)
    {
        com.maddox.il2.engine.MsgBulletCollision msgbulletcollision = (com.maddox.il2.engine.MsgBulletCollision)cache.get();
        msgbulletcollision.collided = actor;
        msgbulletcollision.collidedChunk = s;
        msgbulletcollision.tickOffset = d;
        msgbulletcollision.post(msgbulletcollision, l, bulletgeneric);
    }

    public void clean()
    {
        collided = null;
        collidedChunk = null;
        super.clean();
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        com.maddox.il2.engine.BulletGeneric bulletgeneric = (com.maddox.il2.engine.BulletGeneric)_sender;
        if(!bulletgeneric.isDestroyed() && com.maddox.il2.engine.Actor.isValid(collided))
            if(bulletgeneric.collided(collided, collidedChunk, tickOffset))
            {
                bulletgeneric.destroy();
            } else
            {
                bulletgeneric.nextBullet = com.maddox.il2.engine.Engine.cur.bulletList;
                com.maddox.il2.engine.Engine.cur.bulletList = bulletgeneric;
            }
        return true;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public com.maddox.il2.engine.Actor collided;
    public java.lang.String collidedChunk;
    public double tickOffset;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.il2.engine.MsgBulletCollision.class);
    }
}
