// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookViewEnemy.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorViewPoint;

// Referenced classes of package com.maddox.il2.engine.hotkey:
//            HookView

public class HookViewEnemy extends com.maddox.il2.engine.hotkey.HookView
{

    public void resetGame()
    {
        com.maddox.il2.engine.hotkey.HookView.lastBaseActor = null;
        enemy = null;
        Aenemy = null;
        Genemy = null;
    }

    public com.maddox.il2.engine.Actor enemy()
    {
        return enemy;
    }

    public void reset(float f, float f1, float f2)
    {
        com.maddox.il2.engine.hotkey.HookViewEnemy _tmp = this;
        len = f;
        if(camera != null)
        {
            com.maddox.il2.engine.Actor actor = camera.pos.base();
            if(bUse)
                camera.pos.inValidate(true);
        }
    }

    public boolean computeRenderPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        computePos(actor, loc, loc1, true);
        return true;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        computePos(actor, loc, loc1, false);
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1, boolean flag)
    {
        loc.get(pAbs, oAbs);
        if(bUse)
        {
            if(com.maddox.il2.engine.hotkey.HookView.lastBaseActor != actor)
            {
                com.maddox.il2.engine.hotkey.HookView.lastBaseActor = actor;
                com.maddox.il2.engine.hotkey.HookView._visibleR = -1F;
                if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.engine.ActorMesh))
                    com.maddox.il2.engine.hotkey.HookView._visibleR = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR();
            }
            boolean flag1 = actor instanceof com.maddox.il2.objects.ActorViewPoint;
            if(flag)
                enemy.pos.getRender(p);
            else
                enemy.pos.getAbs(p);
            Ve.sub(p, pAbs);
            float f = (float)Ve.length();
            Ve.normalize();
            if(!flag1)
                Ve.z -= pullVector(actor, pAbs);
            o.setAT0(Ve);
            if(com.maddox.il2.engine.hotkey.HookView.len < 0.0F && flag && -com.maddox.il2.engine.hotkey.HookView.len + com.maddox.il2.engine.hotkey.HookView.defaultLen() > f)
                com.maddox.il2.engine.hotkey.HookView.len = -f + com.maddox.il2.engine.hotkey.HookView.defaultLen();
            Ve.scale(-com.maddox.il2.engine.hotkey.HookView.len);
            pAbs.add(Ve);
            if(!flag1)
                pAbs.z += 0.20000000000000001D * (double)com.maddox.il2.engine.hotkey.HookView.len;
            double d = com.maddox.il2.ai.World.land().HQ_Air(pAbs.x, pAbs.y) + 2D;
            if(pAbs.z < d)
                pAbs.z = d;
            loc1.set(pAbs, o);
        } else
        {
            loc1.set(pAbs, oAbs);
        }
    }

    private float pullVector(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d)
    {
        float f1 = (float)(point3d.z - com.maddox.il2.ai.World.land().HQ(point3d.x, point3d.y));
        actor.getSpeed(Vt);
        float f = 10F;
        f = java.lang.Math.min(f1 * 0.1F, f);
        f = java.lang.Math.min(java.lang.Math.max(1.0F, f), 10F);
        return (10F - f) * 0.1F;
    }

    public boolean start(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, boolean flag, boolean flag1)
    {
        bGround = flag;
        com.maddox.il2.engine.hotkey.HookView._visibleR = -1F;
        if(com.maddox.il2.engine.Actor.isValid(actor))
        {
            if(actor instanceof com.maddox.il2.engine.ActorMesh)
                com.maddox.il2.engine.hotkey.HookView._visibleR = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR();
            actor.pos.inValidate(true);
        } else
        {
            stop();
            return false;
        }
        actor.pos.getAbs(p);
        enemy = actor1;
        if(enemy == null)
        {
            if(bGround)
                enemy = Genemy;
            else
                enemy = Aenemy;
            if(com.maddox.il2.engine.Actor.isValid(enemy) && (actor == enemy || flag1 == (actor.getArmy() == enemy.getArmy())))
            {
                stop();
                return false;
            }
        }
        bUse = camera != null && com.maddox.il2.engine.Actor.isValid(enemy);
        useCommon(2, bUse);
        if(bGround)
            Genemy = enemy;
        else
            Aenemy = enemy;
        clipLen(actor);
        return bUse;
    }

    public void stop()
    {
        useCommon(2, false);
        bUse = false;
    }

    public boolean isRun()
    {
        return bUse;
    }

    public HookViewEnemy()
    {
        current = this;
    }

    private static com.maddox.il2.engine.Actor enemy = null;
    private static com.maddox.il2.engine.Actor Aenemy = null;
    private static com.maddox.il2.engine.Actor Genemy = null;
    private static boolean bGround;
    protected static final int ENEMY = 2;
    public static com.maddox.il2.engine.hotkey.HookViewEnemy current = null;
    private static com.maddox.JGP.Vector3d Ve = new Vector3d();
    private static com.maddox.JGP.Vector3d Vt = new Vector3d();
    private static com.maddox.JGP.Point3d Pt = new Point3d();

}
