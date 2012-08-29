// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PathChief.java

package com.maddox.il2.builder;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.lang.reflect.Method;

// Referenced classes of package com.maddox.il2.builder:
//            Path, PNodes, PlMisChief, Plugin, 
//            Builder, PPoint, Pathes

public class PathChief extends com.maddox.il2.builder.Path
{

    public void computeTimes()
    {
        int i = points();
        if(i == 0)
            return;
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)point(0);
        pnodes.time = 0.0D;
        if(com.maddox.il2.builder.PlMisChief.moveType(_iType) != 2)
        {
            double d = com.maddox.il2.builder.PlMisChief.speed(_iType, _iItem);
            for(int k = 1; k < i; k++)
            {
                double d2 = pnodes.len();
                double d3 = d2 / d;
                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)point(k);
                pnodes2.time = pnodes.time + pnodes.timeoutMin * 60D + d3;
                pnodes = pnodes2;
            }

        } else
        {
            for(int j = 1; j < i; j++)
            {
                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)point(j);
                double d1 = pnodes.computeTime(pnodes1);
                pnodes1.time = pnodes.time + pnodes.timeoutMin * 60D + d1;
                pnodes = pnodes1;
            }

        }
        com.maddox.il2.builder.Plugin.builder.doUpdateSelector();
    }

    public void computeTimesLoaded()
    {
        int i = points();
        if(i == 0)
            return;
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)point(0);
        pnodes.time = 0.0D;
        if(com.maddox.il2.builder.PlMisChief.moveType(_iType) != 2)
        {
            double d = com.maddox.il2.builder.PlMisChief.speed(_iType, _iItem);
            for(int k = 1; k < i; k++)
            {
                double d2 = pnodes.len();
                double d3 = d2 / d;
                com.maddox.il2.builder.PNodes pnodes2 = (com.maddox.il2.builder.PNodes)point(k);
                pnodes2.time = pnodes.time + pnodes.timeoutMin * 60D + d3;
                if(pnodes2.timeoutMin > 0.0D)
                    pnodes2.timeoutMin = java.lang.Math.round((pnodes2.timeoutMin * 60D - pnodes2.time) / 60D);
                if(pnodes2.timeoutMin < 0.0D)
                    pnodes2.timeoutMin = 0.0D;
                pnodes = pnodes2;
            }

        } else
        {
            for(int j = 1; j < i; j++)
            {
                com.maddox.il2.builder.PNodes pnodes1 = (com.maddox.il2.builder.PNodes)point(j);
                double d1 = pnodes.computeTime(pnodes1);
                pnodes1.time = pnodes.time + pnodes.timeoutMin * 60D + d1;
                if(pnodes1.timeoutMin > 0.0D)
                    pnodes1.timeoutMin = java.lang.Math.round((pnodes1.timeoutMin * 60D - pnodes1.time) / 60D);
                if(pnodes1.timeoutMin < 0.0D)
                    pnodes1.timeoutMin = 0.0D;
                pnodes = pnodes1;
            }

        }
    }

    public void placeUnits(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if(point3d1 == null || point3d.distance(point3d1) < 0.10000000000000001D)
        {
            point3d1 = new Point3d(point3d);
            point3d1.y += 100D;
        }
        if(point3d.equals(prevStart) && point3d1.equals(prevEnd))
            return;
        prevStart.set(point3d);
        prevEnd.set(point3d1);
        float f = 0.0F;
        float f1 = 0.0F;
        for(int i = 0; i < units.length; i++)
        {
            float f2 = units[i].mesh().collisionR();
            float f3 = f2;
            if(f2 > f)
                f = f2;
            if(f3 > f1)
                f1 = f3;
        }

        if(com.maddox.il2.builder.PlMisChief.moveType(_iType) != 3)
        {
            f *= 2.0F;
            f1 *= 2.0F;
        }
        if(f <= 0.001F)
            f = 0.001F;
        if(f1 <= 0.001F)
            f1 = 0.001F;
        int j = 1;
        int k = units.length;
        float f4 = (f1 * (float)j + f1 * 0.5F * (float)(j - 1)) / (f * (float)k + f * 0.5F * (float)(k - 1));
        if(com.maddox.il2.builder.PlMisChief.moveType(_iType) != 3)
        {
            for(int l = 2; l <= units.length; l++)
            {
                int i1 = ((units.length + l) - 1) / l;
                float f5 = (f1 * (float)l + f1 * 0.5F * (float)(l - 1)) / (f * (float)i1 + f * 0.5F * (float)(i1 - 1));
                if(java.lang.Math.abs(f5 - 0.5F) < java.lang.Math.abs(f4 - 0.5F))
                {
                    f4 = f5;
                    j = l;
                    k = i1;
                }
            }

        }
        double d = point3d1.x - point3d.x;
        double d1 = point3d1.y - point3d.y;
        double d2 = java.lang.Math.atan2(d1, d);
        double d3 = java.lang.Math.sin(-d2);
        double d4 = java.lang.Math.cos(-d2);
        d2 = com.maddox.JGP.Geom.RAD2DEG((float)d2);
        float f6 = (f1 * (float)j + f1 * 0.5F * (float)(j - 1)) * 0.5F;
        float f7 = (f * (float)k + f * 0.5F * (float)(k - 1)) * 0.5F;
        int j1 = 0;
        for(int k1 = 0; k1 < k; k1++)
        {
            for(int l1 = 0; l1 < j && j1 < units.length; j1++)
            {
                double d5 = -f7 + f * 0.5F + (float)k1 * 1.5F * f;
                double d6 = f6 - f1 * 0.5F - (float)l1 * 1.5F * f1;
                double d7 = d4 * d5 + d3 * d6;
                double d8 = -d3 * d5 + d4 * d6;
                double d9 = 0.0D;
                int i2 = units[units.length - 1 - j1].mesh().hookFind("Ground_Level");
                if(i2 != -1)
                {
                    com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                    units[units.length - 1 - j1].mesh().hookMatrix(i2, matrix4d);
                    d9 = -matrix4d.m23;
                }
                com.maddox.JGP.Point3d point3d2 = new Point3d(point3d);
                point3d2.x += d7;
                point3d2.y += d8;
                point3d2.z = com.maddox.il2.engine.Engine.land().HQ(point3d2.x, point3d2.y) + d9;
                com.maddox.il2.engine.Orient orient = new Orient();
                orient.setYPR((float)d2, 0.0F, 0.0F);
                com.maddox.JGP.Vector3f vector3f = new Vector3f();
                com.maddox.il2.engine.Engine.land().N(point3d2.x, point3d2.y, vector3f);
                orient.orient(vector3f);
                units[units.length - 1 - j1].pos.setAbs(point3d2, orient);
                units[units.length - 1 - j1].pos.reset();
                l1++;
            }

        }

    }

    public void drawing(boolean flag)
    {
        super.drawing(flag);
        if(units != null)
        {
            for(int i = 0; i < units.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(units[i]))
                    units[i].drawing(flag);

        }
    }

    public void clear()
    {
        if(units != null)
        {
            for(int i = 0; i < units.length; i++)
                if(com.maddox.il2.engine.Actor.isValid(units[i]))
                {
                    units[i].destroy();
                    units[i] = null;
                }

            units = null;
        }
        prevStart.set(0.0D, 0.0D, 0.0D);
        prevEnd.set(0.0D, 0.0D, 0.0D);
    }

    public void destroy()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            clear();
            super.destroy();
            return;
        }
    }

    public void setUnits(int i, int j, com.maddox.rts.SectFile sectfile, int k, com.maddox.JGP.Point3d point3d)
    {
        clear();
        try
        {
            int l = sectfile.vars(k);
            units = new com.maddox.il2.engine.ActorMesh[l];
            for(int i1 = 0; i1 < l; i1++)
            {
                java.lang.String s = sectfile.var(k, i1);
                java.lang.Class class1 = java.lang.Class.forName(s);
                java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "meshName", null);
                if(s1 == null)
                {
                    java.lang.reflect.Method method = class1.getMethod("getMeshNameForEditor", null);
                    s1 = (java.lang.String)method.invoke(class1, null);
                }
                if(s1.toLowerCase().endsWith(".sim"))
                    units[i1] = new ActorSimpleMesh(s1);
                else
                    units[i1] = new ActorSimpleHMesh(s1);
            }

            _iType = i;
            _iItem = j;
            placeUnits(point3d, null);
        }
        catch(java.lang.Exception exception)
        {
            destroy();
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException(exception.toString());
        }
    }

    public void updateUnitsPos()
    {
        if(units == null)
            return;
        if(points() == 0)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(point(0)))
            return;
        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)point(0);
        if(pnodes.posXYZ != null)
        {
            p2.set(pnodes.posXYZ[4], pnodes.posXYZ[5], pnodes.posXYZ[6]);
            placeUnits(point(0).pos.getAbsPoint(), p2);
        } else
        {
            placeUnits(point(0).pos.getAbsPoint(), null);
        }
    }

    public void pointMoved(com.maddox.il2.builder.PPoint ppoint)
    {
        computeTimes();
        int i = pointIndx(ppoint);
        if(i != 0 && i != 1)
        {
            return;
        } else
        {
            updateUnitsPos();
            return;
        }
    }

    public PathChief(com.maddox.il2.builder.Pathes pathes, int i, int j, int k, com.maddox.rts.SectFile sectfile, int l, com.maddox.JGP.Point3d point3d)
    {
        super(pathes);
        _sleep = 0;
        _skill = 2;
        _slowfire = 1.0F;
        prevStart = new Point3d();
        prevEnd = new Point3d();
        p2 = new Point3d();
        moveType = i;
        setUnits(j, k, sectfile, l, point3d);
    }

    public com.maddox.il2.engine.ActorMesh units[];
    public int _iType;
    public int _iItem;
    public int _sleep;
    public int _skill;
    public float _slowfire;
    private com.maddox.JGP.Point3d prevStart;
    private com.maddox.JGP.Point3d prevEnd;
    private com.maddox.JGP.Point3d p2;
}
