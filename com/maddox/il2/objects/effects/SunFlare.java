// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SunFlare.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.objects.effects:
//            SunFlareRender

public class SunFlare extends com.maddox.il2.engine.Actor
{
    class Draw extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            if(mat == null)
            {
                postDestroy();
                return 0;
            } else
            {
                pos.getAbs(pcoll, ocoll);
                com.maddox.il2.engine.Render.currentCamera().pos.getRender(p, o);
                return 2;
            }
        }

        public void render(com.maddox.il2.engine.Actor actor)
        {
            draw();
        }

        private void draw()
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                return;
            center.set(500D, 0.0D, 0.0D);
            o.transform(center);
            center.add(p, center);
            sun.x = com.maddox.il2.ai.World.Sun().ToSun.x * 500F;
            sun.y = com.maddox.il2.ai.World.Sun().ToSun.y * 500F;
            sun.z = com.maddox.il2.ai.World.Sun().ToSun.z * 500F;
            sun.add(p, sun);
            if(!com.maddox.il2.engine.Render.currentCamera().isSphereVisible(sun, 1.0F))
                return;
            com.maddox.il2.game.VisibilityChecker.checkLandObstacle = true;
            com.maddox.il2.game.VisibilityChecker.checkCabinObstacle = false;
            com.maddox.il2.game.VisibilityChecker.checkPlaneObstacle = true;
            com.maddox.il2.game.VisibilityChecker.checkObjObstacle = true;
            rayDir.set(com.maddox.il2.ai.World.Sun().ToSun);
            float f = com.maddox.il2.game.VisibilityChecker.computeVisibility(rayDir, null);
            if(f <= 0.0F)
                return;
            if(f >= 1.0F)
                f = 1.0F;
            for(int i = 0; i < 19; i++)
            {
                com.maddox.il2.engine.Render.drawSetMaterial(mat[material[i]]);
                com.maddox.il2.engine.Render.drawBeginSprites(0);
                com.maddox.il2.objects.effects.SunFlare.p1.x = center.x + (sun.x - center.x) * (double)frac[i];
                com.maddox.il2.objects.effects.SunFlare.p1.y = center.y + (sun.y - center.y) * (double)frac[i];
                com.maddox.il2.objects.effects.SunFlare.p1.z = center.z + (sun.z - center.z) * (double)frac[i];
                com.maddox.il2.engine.Render.drawPushSprite((float)com.maddox.il2.objects.effects.SunFlare.p1.x, (float)com.maddox.il2.objects.effects.SunFlare.p1.y, (float)com.maddox.il2.objects.effects.SunFlare.p1.z, radius[i] * 40F, rgb_r[i], rgb_g[i], rgb_b[i], alpha[i] * f, 0.0F);
                com.maddox.il2.engine.Render.drawEnd();
            }

        }

        private com.maddox.JGP.Point3d sun;
        private com.maddox.JGP.Point3d center;
        private com.maddox.JGP.Point3d startp;
        private com.maddox.JGP.Point3d endp;
        private com.maddox.JGP.Vector3d rayDir;
        int material[] = {
            3, 1, 1, 3, 0, 1, 3, 0, 0, 0, 
            0, 3, 0, 3, 2, 1, 2, 3, 3
        };
        float frac[] = {
            0.4F, 0.7F, 0.65F, 0.6F, 0.5F, -0.45F, -0.5F, -0.6F, -1.3F, -1.35F, 
            -1.5F, -1.6F, -1.7F, -1.9F, -2.3F, -2.5F, -2.9F, -3F, -5F
        };
        float radius[] = {
            0.4F, 1.2F, 1.55F, 0.45F, 1.4F, 2.2F, 1.25F, 1.4F, 0.8F, 0.2F, 
            0.7F, 0.5F, 1.35F, 0.4F, 1.35F, 1.25F, 1.6F, 1.8F, 0.4F
        };
        float alpha[] = {
            1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 
            1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F
        };
        float rgb_r[] = {
            0.13F, 0.06F, 0.16F, 0.24F, 0.2F, 0.06F, 0.24F, 0.2F, 0.06F, 0.13F, 
            0.2F, 0.06F, 0.06F, 0.32F, 0.06F, 0.06F, 0.2F, 0.06F, 0.13F
        };
        float rgb_g[] = {
            0.13F, 0.1F, 0.14F, 0.27F, 0.09F, 0.1F, 0.27F, 0.09F, 0.15F, 0.16F, 
            0.09F, 0.15F, 0.15F, 0.45F, 0.15F, 0.15F, 0.09F, 0.15F, 0.13F
        };
        float rgb_b[] = {
            0.19F, 0.1F, 0.25F, 0.34F, 0.31F, 0.1F, 0.34F, 0.31F, 0.1F, 0.2F, 
            0.32F, 0.1F, 0.1F, 0.45F, 0.1F, 0.1F, 0.32F, 0.1F, 0.19F
        };

        Draw()
        {
            sun = new Point3d();
            center = new Point3d();
            startp = new Point3d();
            endp = new Point3d();
            rayDir = new Vector3d();
        }
    }


    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public void destroy()
    {
        if(render != null)
            render.setActor(null);
        super.destroy();
    }

    public SunFlare(com.maddox.il2.engine.Render render1)
    {
        p = new Point3d();
        o = new Orient();
        pcoll = new Point3d();
        ocoll = new Orient();
        draw = new Draw();
        pos = new ActorPosMove(this, new Loc());
        render = (com.maddox.il2.objects.effects.SunFlareRender)render1;
        pos.setBase(render.getCamera(), null, false);
        drawing(false);
        render.setActor(this);
        mat = new com.maddox.il2.engine.Mat[4];
        mat[0] = com.maddox.il2.engine.Mat.New("Effects/SunFlare/Flare0.mat");
        mat[1] = com.maddox.il2.engine.Mat.New("Effects/SunFlare/Flare1.mat");
        mat[2] = com.maddox.il2.engine.Mat.New("Effects/SunFlare/Flare2.mat");
        mat[3] = com.maddox.il2.engine.Mat.New("Effects/SunFlare/Flare3.mat");
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public static com.maddox.il2.engine.Render newRender(int i, float f, com.maddox.il2.engine.Camera camera)
    {
        com.maddox.il2.objects.effects.SunFlareRender sunflarerender = new SunFlareRender(i, f);
        sunflarerender.setCamera(camera);
        return sunflarerender;
    }

    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.JGP.Point3d pcoll;
    private com.maddox.il2.engine.Orient ocoll;
    private com.maddox.il2.engine.Mat mat[];
    private com.maddox.il2.objects.effects.SunFlareRender render;
    private static com.maddox.JGP.Point3d p1 = new Point3d();







}
