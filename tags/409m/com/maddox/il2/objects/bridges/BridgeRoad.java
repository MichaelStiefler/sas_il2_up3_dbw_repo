// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BridgeRoad.java

package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.LandPlate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.rts.Message;

public class BridgeRoad extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.LandPlate
{
    private class BridgeRoadDraw extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            pos.getRender(com.maddox.il2.objects.bridges.BridgeRoad.p);
            if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(com.maddox.il2.objects.bridges.BridgeRoad.p, R))
                return 0;
            else
                return mat.preRender();
        }

        public void render(com.maddox.il2.engine.Actor actor)
        {
            com.maddox.il2.ai.World.cur();
            com.maddox.il2.ai.World.land().renderBridgeRoad(mat, tp, begX, begY, incX, incY, offsetKoef);
        }

        private BridgeRoadDraw()
        {
        }

    }


    public boolean isStaticPos()
    {
        return true;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public BridgeRoad(com.maddox.JGP.Point3d point3d, float f, com.maddox.il2.engine.Mat mat1, int i, int j, int k, int l, 
            int i1, float f1)
    {
        pos = new ActorPosStatic(this);
        pos.setAbs(point3d);
        pos.reset();
        draw = new BridgeRoadDraw();
        drawing(true);
        R = f;
        mat = mat1;
        switch(i)
        {
        case 0: // '\0'
            tp = 128;
            break;

        case 1: // '\001'
            tp = 32;
            break;

        default:
            tp = 64;
            break;
        }
        begX = j;
        begY = k;
        incX = l;
        incY = i1;
        offsetKoef = f1;
    }

    private com.maddox.il2.engine.Mat mat;
    private int tp;
    private int begX;
    private int begY;
    private int incX;
    private int incY;
    private float offsetKoef;
    private float R;
    private static com.maddox.JGP.Point3d p = new Point3d();










}
