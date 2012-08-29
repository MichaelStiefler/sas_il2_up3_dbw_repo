// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorSnapToLand.java

package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class ActorSnapToLand extends com.maddox.il2.engine.ActorMesh
{
    class Scaler extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(dScale != 0.0F)
                {
                    scale+= = dScale;
                    mesh().setScale(scale);
                }
                if(adScale != 0.0F)
                {
                    ascale+= = adScale;
                    mat.set((byte)10, ascale);
                }
            }
            return true;
        }

        Scaler()
        {
        }
    }


    public boolean isStaticPos()
    {
        return true;
    }

    public ActorSnapToLand(java.lang.String s, boolean flag, com.maddox.il2.engine.Loc loc, float f, float f1, float f2, float f3, 
            float f4)
    {
        this(s, flag, loc, 1.0F, f, f1, f2, f3, f4);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorSnapToLand(java.lang.String s, boolean flag, com.maddox.il2.engine.Loc loc, float f, float f1, float f2, float f3, 
            float f4, float f5)
    {
        super(s, loc);
        scale = 1.0F;
        dScale = 0.0F;
        ascale = 1.0F;
        adScale = 0.0F;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            mat = mesh().material(0);
            if(flag)
            {
                com.maddox.il2.engine.Mat mat1 = (com.maddox.il2.engine.Mat)mat.Clone();
                if(mat1 != null)
                {
                    mat = mat1;
                    mesh().materialReplace(0, mat);
                }
            }
            mat.setLayer(0);
        }
        if(f5 > 0.0F)
        {
            postDestroy(com.maddox.rts.Time.current() + (long)(f5 * 1000F));
            com.maddox.il2.objects.Scaler scaler = null;
            if(f1 != f2)
            {
                int i = (int)(f5 / com.maddox.rts.Time.tickLenFs());
                scale = f1;
                if(i > 0)
                {
                    dScale = (f2 - f1) / (float)i;
                    scaler = new Scaler();
                    interpPut(scaler, "scaler", com.maddox.rts.Time.current(), null);
                }
            }
            if(f3 != f4)
            {
                int j = (int)(f5 / com.maddox.rts.Time.tickLenFs());
                ascale = f3;
                if(j > 0)
                {
                    adScale = (f4 - f3) / (float)j;
                    if(scaler == null)
                    {
                        com.maddox.il2.objects.Scaler scaler1 = new Scaler();
                        interpPut(scaler1, "scaler", com.maddox.rts.Time.current(), null);
                    }
                }
            }
        } else
        {
            scale = f1;
            ascale = f3;
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(scale != 1.0F)
                mesh().setScale(scale);
            if(ascale != 1.0F)
                mat.set((byte)10, ascale);
        }
        pos.getAbs(p, o);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + (double)f;
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, normal);
        o.orient(normal);
        pos.setAbs(p, o);
        pos.reset();
        drawing(true);
    }

    private float scale;
    private float dScale;
    private float ascale;
    private float adScale;
    private com.maddox.il2.engine.Mat mat;
    private static com.maddox.JGP.Vector3f normal = new Vector3f();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();








}
