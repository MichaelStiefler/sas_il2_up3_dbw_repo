// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Chute.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft, Paratrooper

public class Chute extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(st == 1 || st == 3)
            {
                long l = com.maddox.rts.Time.tickNext() - animStartTime;
                if(com.maddox.rts.Time.current() >= disappearTime)
                {
                    postDestroy();
                    return false;
                }
            }
            setAnimFrame(com.maddox.rts.Time.tickNext());
            return true;
        }

        Move()
        {
        }
    }

    private class ChuteDraw extends com.maddox.il2.engine.ActorMeshDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            setAnimFrame(com.maddox.rts.Time.current());
            return super.preRender(actor);
        }

        private ChuteDraw()
        {
        }

    }


    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.isNet() && actor.isNetMirror())
            aflag[0] = false;
        if(actor == getOwner())
            aflag[0] = false;
        if(actor != null && (actor instanceof com.maddox.il2.objects.air.Paratrooper) && (st == 1 || st == 3))
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(st != 0)
        {
            return;
        } else
        {
            TangleChute(actor);
            return;
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 3;
        if(st != 0)
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
        {
            DieChute(shot.initiator);
            return;
        }
        if(shot.v.length() < 40D)
        {
            return;
        } else
        {
            DieChute(shot.initiator);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(st != 0)
            return;
        float f = 0.01F;
        float f1 = 0.09F;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedTNT_1meter(this), f, f1, 0.0F))
            DieChute(explosion.initiator);
    }

    private void DieChute(com.maddox.il2.engine.Actor actor)
    {
        TangleChute(actor);
    }

    private void TangleChute(com.maddox.il2.engine.Actor actor)
    {
        TangleChute(actor, true);
    }

    private void TangleChute(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        tangling();
        if(getOwner() instanceof com.maddox.il2.objects.air.Paratrooper)
        {
            com.maddox.il2.objects.air.Paratrooper paratrooper = (com.maddox.il2.objects.air.Paratrooper)getOwner();
            if(com.maddox.il2.engine.Actor.isValid(paratrooper))
                paratrooper.chuteTangled(actor, flag);
        }
    }

    void tangleChute(com.maddox.il2.engine.Actor actor)
    {
        TangleChute(actor, false);
    }

    public void landing()
    {
        if(st == 1 || st == 3)
        {
            return;
        } else
        {
            pos.getAbs(p);
            com.maddox.il2.engine.Engine.land();
            float f = com.maddox.il2.engine.Landscape.HQ((float)p.x, (float)p.y);
            p.z = f;
            st = st != 2 ? 1 : 3;
            animStartTime = com.maddox.rts.Time.current();
            pos.getAbs(o);
            com.maddox.JGP.Vector3f vector3f = new Vector3f();
            com.maddox.il2.engine.Engine.land().N((float)p.x, (float)p.y, vector3f);
            o.orient(vector3f);
            pos.setAbs(p, o);
            setOwner(null);
            pos.setBase(null, null, true);
            disappearTime = com.maddox.rts.Time.tickNext() + (long)com.maddox.il2.ai.World.Rnd().nextInt(25000, 35000);
            return;
        }
    }

    private void tangling()
    {
        if(st != 0)
        {
            return;
        } else
        {
            st = 2;
            animStartTime = com.maddox.rts.Time.current() - (long)com.maddox.il2.ai.World.Rnd().nextInt(0, 5000);
            return;
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    void setAnimFrame(double d)
    {
        byte byte0;
        byte byte1;
        float f;
        switch(st)
        {
        case 0: // '\0'
            byte0 = 0;
            byte1 = 35;
            int i = 1166;
            double d1 = d - (double)animStartTime;
            if(d1 <= 0.0D)
                f = 0.0F;
            else
            if(d1 >= (double)i)
                f = 1.0F;
            else
                f = (float)(d1 / (double)i);
            break;

        case 1: // '\001'
            byte0 = 35;
            byte1 = 76;
            int j = 1366;
            double d2 = d - (double)animStartTime;
            if(d2 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d2 >= (double)j)
                f = 1.0F;
            else
                f = (float)(d2 / (double)j);
            break;

        case 2: // '\002'
            byte0 = 77;
            byte1 = 122;
            int k = 1500;
            double d3 = d - (double)animStartTime;
            d3 %= k;
            if(d3 < 0.0D)
                d3 += k;
            f = (float)(d3 / (double)k);
            break;

        default:
            byte0 = 45;
            byte1 = 76;
            int l = 1033;
            double d4 = d - (double)animStartTime;
            if(d4 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d4 >= (double)l)
                f = 1.0F;
            else
                f = (float)(d4 / (double)l);
            break;
        }
        mesh().setFrameFromRange(byte0, byte1, f);
    }

    static java.lang.String GetMeshName()
    {
        return "3do/humans/Paratroopers/Chute/mono.sim";
    }

    public Chute(com.maddox.il2.engine.Actor actor)
    {
        super(com.maddox.il2.objects.air.Chute.GetMeshName());
        st = 0;
        setOwner(actor);
        pos.setBase(actor, null, false);
        pos.resetAsBase();
        setArmy(0);
        st = 0;
        animStartTime = com.maddox.rts.Time.tick();
        collide(true);
        draw = new ChuteDraw();
        drawing(true);
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    private static final int FPS = 30;
    private static final int PARAUP_START_FRAME = 0;
    private static final int PARAUP_LAST_FRAME = 35;
    private static final int PARAUP_N_FRAMES = 36;
    private static final int PARAUP_CYCLE_TIME = 1166;
    private static final int FALL_START_FRAME = 35;
    private static final int FALL_LAST_FRAME = 76;
    private static final int FALL_N_FRAMES = 42;
    private static final int FALL_CYCLE_TIME = 1366;
    private static final int TANGLE_START_FRAME = 77;
    private static final int TANGLE_LAST_FRAME = 122;
    private static final int TANGLE_N_FRAMES = 46;
    private static final int TANGLE_CYCLE_TIME = 1500;
    private static final int FALLTANGLE_START_FRAME = 45;
    private static final int FALLTANGLE_LAST_FRAME = 76;
    private static final int FALLTANGLE_N_FRAMES = 32;
    private static final int FALLTANGLE_CYCLE_TIME = 1033;
    private static final int ST_PARAUP = 0;
    private static final int ST_FALL = 1;
    private static final int ST_TANGLE = 2;
    private static final int ST_FALLTANGLE = 3;
    private int st;
    private long animStartTime;
    private long disappearTime;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();




}
