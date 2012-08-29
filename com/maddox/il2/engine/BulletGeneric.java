// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletGeneric.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

// Referenced classes of package com.maddox.il2.engine:
//            Loc, GunGeneric, GunProperties, Eff3DActor, 
//            Engine, ActorPos, Render, Camera, 
//            MeshShared, BulletProperties, Orient, Mat, 
//            Actor

public class BulletGeneric
    implements com.maddox.rts.Destroy
{
    private static class Lines
    {

        public int color;
        public int n;
        public float coord[];

        private Lines()
        {
            n = 0;
            coord = new float[6];
        }

    }


    public int indx()
    {
        return flags & 0xff;
    }

    public com.maddox.il2.engine.BulletProperties properties()
    {
        return gun.prop.bullet[indx()];
    }

    public com.maddox.il2.engine.Actor owner()
    {
        return owner;
    }

    public com.maddox.il2.engine.GunGeneric gun()
    {
        return gun;
    }

    public com.maddox.il2.engine.Actor gunOwnerBody()
    {
        return gun.interpolater.actor;
    }

    public void destroy()
    {
        owner = gun = null;
        com.maddox.il2.engine.Eff3DActor.finish(effTrail);
        effTrail = null;
    }

    public boolean isDestroyed()
    {
        return gun == null;
    }

    public void move(float f)
    {
        if(gun == null)
        {
            return;
        } else
        {
            com.maddox.JGP.Point3d point3d = p1;
            p1 = p0;
            p0 = point3d;
            dspeed.scale((double)(gun.bulletKV[indx()] * f) * speed.length(), speed);
            dspeed.z += gun.bulletAG[indx()] * f;
            speed.add(dspeed);
            p1.scaleAdd(f, speed, p0);
            return;
        }
    }

    public boolean collided(com.maddox.il2.engine.Actor actor, java.lang.String s, double d)
    {
        return true;
    }

    public void timeOut()
    {
    }

    public BulletGeneric(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        bMoved = false;
        p0 = new Point3d();
        p1 = new Point3d();
        speed = new Vector3d();
        gun = gungeneric;
        owner = gungeneric.getOwner();
        p0.set(loc.getPoint());
        p1.set(p0);
        speed.set(vector3d);
        timeEnd = l;
        nextBullet = com.maddox.il2.engine.Engine.cur.bulletList;
        com.maddox.il2.engine.Engine.cur.bulletList = this;
        flags = i & 0xff;
    }

    public void preRender()
    {
        if(effTrail != null)
            effTrail.pos.setAbs(p0);
        flags &= 0xffff3fff;
        if((flags & 0x80000000) != 0)
        {
            com.maddox.JGP.Point3d point3d = com.maddox.il2.engine.Render.currentCamera().pos.getAbsPoint();
            if(gun._bulletTraceMesh[indx()] != null && point3d.distance(p0) < 100D)
            {
                if(gun._bulletTraceMesh[indx()].preRender(p0) != 0)
                    flags |= 0x8000;
            } else
            if(gun.prop.bullet[indx()].traceColor != 0)
                flags |= 0xc000;
        }
    }

    public void render()
    {
        if((flags & 0x1000) != 0)
            return;
        int i = indx();
        if((flags & 0x4000) != 0)
        {
            tmpVector.sub(p1, p0);
            tmpVector.normalize();
            tmpVector.scale(gun._bulletTraceMeshLen[i]);
            tmpP.interpolate(p0, p1, com.maddox.rts.Time.tickOffset());
            tmpVector.add(tmpP);
            com.maddox.il2.engine.BulletGeneric.drawLine(tmpP, tmpVector, gun.prop.bullet[i].traceColor);
        } else
        {
            tmpVector.sub(p1, p0);
            tmpVector.normalize();
            com.maddox.il2.engine.Orient orient = tmpLoc.getOrient();
            orient.setAT0(tmpVector);
            tmpP.interpolate(p0, p1, com.maddox.rts.Time.tickOffset());
            tmpLoc.set(tmpP);
            if(!gun._bulletTraceMesh[i].putToRenderArray(tmpLoc))
            {
                gun._bulletTraceMesh[i].setPos(tmpLoc);
                gun._bulletTraceMesh[i].render();
            }
        }
    }

    public static void preRenderAll()
    {
        for(com.maddox.il2.engine.BulletGeneric bulletgeneric = com.maddox.il2.engine.Engine.cur.bulletList; bulletgeneric != null; bulletgeneric = bulletgeneric.nextBullet)
            if((bulletgeneric.flags & 0x80000000) != 0 || bulletgeneric.effTrail != null)
                bulletgeneric.preRender();

    }

    public static void renderAll()
    {
        for(com.maddox.il2.engine.BulletGeneric bulletgeneric = com.maddox.il2.engine.Engine.cur.bulletList; bulletgeneric != null; bulletgeneric = bulletgeneric.nextBullet)
            if((bulletgeneric.flags & 0x8000) != 0)
                bulletgeneric.render();

        com.maddox.il2.engine.BulletGeneric.flushLines();
    }

    public static void drawLine(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1, int i)
    {
        com.maddox.il2.engine.Lines lines = (com.maddox.il2.engine.Lines)mapLines.get(i);
        if(lines == null)
        {
            lines = new Lines();
            lines.color = i;
            mapLines.put(i, lines);
        }
        if(lines.n >= lines.coord.length / 6)
        {
            float af[] = new float[lines.coord.length * 2];
            java.lang.System.arraycopy(lines.coord, 0, af, 0, lines.coord.length);
            lines.coord = af;
        }
        int j = lines.n * 6;
        lines.coord[j + 0] = (float)tuple3d.x;
        lines.coord[j + 1] = (float)tuple3d.y;
        lines.coord[j + 2] = (float)tuple3d.z;
        lines.coord[j + 0 + 3] = (float)tuple3d1.x;
        lines.coord[j + 1 + 3] = (float)tuple3d1.y;
        lines.coord[j + 2 + 3] = (float)tuple3d1.z;
        lines.n++;
    }

    public static void flushLines()
    {
        com.maddox.util.HashMapIntEntry hashmapintentry = mapLines.nextEntry(null);
        boolean flag = false;
        for(; hashmapintentry != null; hashmapintentry = mapLines.nextEntry(hashmapintentry))
        {
            com.maddox.il2.engine.Lines lines = (com.maddox.il2.engine.Lines)hashmapintentry.getValue();
            if(lines.n <= 0)
                continue;
            if(!flag)
            {
                com.maddox.il2.engine.Render.drawBeginLines(0);
                flag = true;
            }
            com.maddox.il2.engine.Render.drawLines(lines.coord, lines.n * 2, 1.0F, lines.color, com.maddox.il2.engine.Render.LineFlags | com.maddox.il2.engine.Mat.BLENDADD, 0);
            lines.n = 0;
        }

        if(flag)
            com.maddox.il2.engine.Render.drawEnd();
    }

    public static final int TRACE_EXIST = 0x80000000;
    public static final int ARCADE = 0x40000000;
    public static final int INDEX_MASK = 255;
    public static final int TRACE_VISIBLE = 32768;
    public static final int TRACE_LINE = 16384;
    public static final int ARCADED = 8192;
    public static final int FIRST_TICK = 4096;
    public int flags;
    public com.maddox.il2.engine.BulletGeneric nextBullet;
    protected com.maddox.il2.engine.Actor owner;
    protected com.maddox.il2.engine.GunGeneric gun;
    public com.maddox.il2.engine.Eff3DActor effTrail;
    public long timeEnd;
    public boolean bMoved;
    public com.maddox.JGP.Point3d p0;
    public com.maddox.JGP.Point3d p1;
    public com.maddox.JGP.Vector3d speed;
    protected static com.maddox.JGP.Vector3d dspeed = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpVector = new Vector3d();
    private static com.maddox.il2.engine.Loc tmpLoc = new Loc();
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.util.HashMapInt mapLines = new HashMapInt();

}
