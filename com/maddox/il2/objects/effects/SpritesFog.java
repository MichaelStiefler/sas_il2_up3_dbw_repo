// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SpritesFog.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.objects.effects:
//            SpritesFogRender

public class SpritesFog extends com.maddox.il2.engine.Actor
{
    class Draw extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor1)
        {
            if(mat == null)
            {
                postDestroy();
                return 0;
            } else
            {
                com.maddox.il2.engine.Render.currentCamera().pos.getRender(p, o);
                return mat.preRender();
            }
        }

        public void render(com.maddox.il2.engine.Actor actor1)
        {
            float f = 1.0F;
            f = com.maddox.il2.engine.Landscape.getDynamicFogAlpha();
            rgb = com.maddox.il2.engine.Landscape.getDynamicFogRGB() & 0xffffff;
            com.maddox.il2.objects.effects.SpritesFog.dynamicFogAlpha = f;
            if((double)f < 0.01D)
            {
                return;
            } else
            {
                com.maddox.il2.engine.Render.drawSetMaterial(mat, (float)p.x, (float)p.y, (float)p.z, 30F);
                com.maddox.il2.engine.Render.drawBeginSprites(0);
                draw(69, 83F, 1, f);
                com.maddox.il2.engine.Render.drawEnd();
                return;
            }
        }

        private void draw(int i, float f, int j, float f1)
        {
            float f2 = i * j + i / 2;
            float f3 = (int)(p.x / (double)i + 0.5D) * i;
            float f4 = (int)(p.y / (double)i + 0.5D) * i;
            float f5 = (int)(p.z / (double)i + 0.5D) * i;
            for(int k = -j; k <= j; k++)
            {
                for(int l = -j; l <= j; l++)
                {
                    for(int i1 = -j; i1 <= j; i1++)
                    {
                        float f6 = f3 + (float)(i * i1);
                        float f7 = f4 + (float)(i * l);
                        float f8 = f5 + (float)(i * k);
                        com.maddox.il2.objects.effects.SpritesFog.p1.set(f2, 0.0D, 0.0D);
                        o.transform(com.maddox.il2.objects.effects.SpritesFog.p1);
                        com.maddox.il2.objects.effects.SpritesFog.p1.add(p, com.maddox.il2.objects.effects.SpritesFog.p1);
                        float f9 = (float)(com.maddox.il2.objects.effects.SpritesFog.p1.x - p.x);
                        float f10 = (float)(com.maddox.il2.objects.effects.SpritesFog.p1.y - p.y);
                        float f11 = (float)(com.maddox.il2.objects.effects.SpritesFog.p1.z - p.z);
                        float f12 = f9 * f9 + f10 * f10 + f11 * f11;
                        float f13 = ((f6 - (float)p.x) * f9 + (f7 - (float)p.y) * f10 + (f8 - (float)p.z) * f11) / f12;
                        if(f13 >= 0.1F && f13 <= 1.0F)
                        {
                            float f14 = f1;
                            if(f13 < 0.2F)
                                f14 *= (f13 - 0.1F) / 0.1F;
                            else
                            if(f13 > 0.8F)
                                f14 *= 1.0F - (f13 - 0.8F) / 0.2F;
                            com.maddox.il2.engine.Render.drawPushSprite(f6, f7, f8, f, rgb, f14 * 0.5F, 0.0F);
                        }
                    }

                }

            }

        }

        Draw()
        {
        }
    }


    public boolean isShow()
    {
        return render.isShow();
    }

    public void setShow(boolean flag)
    {
        render.setShow(flag);
    }

    public void setHeight(float f, float f1)
    {
        zMin = f + 50F;
        zMax = f1 - 50F;
        if(zMin >= zMax)
        {
            zMin = (zMin + zMax) / 2.0F;
            zMax = zMin + 1.0F;
        }
    }

    public float getHeightMin()
    {
        return zMin - 50F;
    }

    public float getHeightMax()
    {
        return zMax + 50F;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public SpritesFog(com.maddox.il2.engine.Actor actor1, float f, float f1, float f2)
    {
        rgb = 0xffffff;
        p = new Point3d();
        o = new Orient();
        draw = new Draw();
        pos = new ActorPosMove(this, new Loc());
        pos.setBase(actor1, null, false);
        drawing(false);
        setHeight(f1, f2);
        mat = com.maddox.il2.engine.Mat.New("3do/Effects/SpritesFog/Fog.mat");
        actor = this;
        if(render == null)
            render = new SpritesFogRender(f);
        else
            render.setShow(true);
        render.setCamera((com.maddox.il2.engine.Camera)actor1);
        render.setName("renderSpritesFog");
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public static final boolean bUseAsGetAlpha = true;
    public static float dynamicFogAlpha = 0.0F;
    public static final float HEIGHT_MIN = 0F;
    public static final float HEIGHT_MAX = 4000F;
    private com.maddox.il2.engine.Mat mat;
    private float zMin;
    private float zMax;
    private static final float DZ = 50F;
    private static final float U0 = 0.1F;
    private static final float U1 = 0.2F;
    private static final float U2 = 0.8F;
    private int rgb;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient o;
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    protected static com.maddox.il2.objects.effects.SpritesFog actor = null;
    protected static com.maddox.il2.objects.effects.SpritesFogRender render = null;







}
