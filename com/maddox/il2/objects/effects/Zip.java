// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Zip.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.sounds.SfxZip;
import com.maddox.rts.Destroy;
import com.maddox.rts.MsgAction;

public class Zip
    implements com.maddox.rts.Destroy
{

    protected void zipPsss()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            light1.setEmit(0.0F, 0.0F);
            light2.setEmit(0.0F, 0.0F);
            zipMesh.drawing(false);
            new com.maddox.rts.MsgAction(rnd.nextFloat(4F, 8F)) {

                public void doAction()
                {
                    zipBuhhh();
                }

            }
;
            return;
        }
    }

    protected void zipBuhhh()
    {
        if(isDestroyed())
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(!com.maddox.il2.game.Main3D.cur3D().clouds.getRandomCloudPos(temp))
        {
            new com.maddox.rts.MsgAction(rnd.nextFloat(4F, 8F)) {

                public void doAction()
                {
                    zipBuhhh();
                }

            }
;
            return;
        } else
        {
            temp.z -= 100D;
            light2.setPos(temp);
            light2.setEmit(5F, 100F);
            light2.setColor(1.0F, 1.0F, 1.0F);
            temp.z = height * 0.3F;
            new SfxZip(temp);
            light1.setPos(temp);
            light1.setEmit(50F, 2000F);
            light1.setColor(1.0F, 1.0F, 1.0F);
            temp.z = 0.0D;
            zipMesh.pos.setAbs(temp);
            zipMesh.pos.reset();
            zipMesh.mesh().setScale(height * 0.001F);
            zipMesh.mesh().setFrame(rnd.nextInt(0, 4));
            zipMesh.drawing(true);
            com.maddox.il2.objects.air.Cockpit.lightningStrike(temp);
            new com.maddox.rts.MsgAction(0.5D) {

                public void doAction()
                {
                    zipPsss();
                }

            }
;
            return;
        }
    }

    public Zip(float f)
    {
        height = 1000F;
        temp = new Point3d();
        light1 = null;
        light2 = null;
        rnd = new RangeRandom();
        height = f;
        light1 = new LightPointWorld();
        light2 = new LightPointWorld();
        zipMesh = new ActorSimpleMesh("3do/effects/fireworks/hammerofthor/mono.sim");
        zipMesh.draw = new com.maddox.il2.engine.ActorMeshDraw() {

            public void render(com.maddox.il2.engine.Actor actor)
            {
                if(com.maddox.il2.game.Main3D.cur3D().bEnableFog)
                    com.maddox.il2.engine.Render.enableFog(false);
                super.render(actor);
                if(com.maddox.il2.game.Main3D.cur3D().bEnableFog)
                    com.maddox.il2.engine.Render.enableFog(true);
            }

        }
;
        zipMesh.drawing(false);
        new com.maddox.rts.MsgAction(15D) {

            public void doAction()
            {
                zipBuhhh();
            }

        }
;
    }

    public boolean isDestroyed()
    {
        return light1 == null;
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        light1.destroy();
        light1 = null;
        light2.destroy();
        light2 = null;
        if(com.maddox.il2.engine.Actor.isValid(zipMesh))
        {
            zipMesh.destroy();
            zipMesh = null;
        }
    }

    private float height;
    com.maddox.JGP.Point3d temp;
    com.maddox.il2.engine.LightPointWorld light1;
    com.maddox.il2.engine.LightPointWorld light2;
    private com.maddox.il2.ai.RangeRandom rnd;
    private com.maddox.il2.objects.ActorSimpleMesh zipMesh;
    private static final float dT = 4F;
}
