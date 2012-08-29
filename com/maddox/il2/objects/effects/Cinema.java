// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cinema.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.RandomVector;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.Provider;
import com.maddox.opengl.gl;
import com.maddox.rts.Time;

public class Cinema extends com.maddox.il2.engine.Render
    implements com.maddox.opengl.MsgGLContextListener
{
    private class VScratchBlock
    {

        public boolean active;
        public boolean white;
        public long timeStart;
        public long timeEnd;
        public float offs;
        private int nScratches;
        private com.maddox.JGP.Point3f scratches[];




        private VScratchBlock()
        {
            scratches = new com.maddox.JGP.Point3f[5];
        }

    }


    public void destroy()
    {
        com.maddox.il2.engine.Camera camera = getCamera();
        if(com.maddox.il2.engine.Actor.isValid(camera))
            camera.destroy();
        super.destroy();
    }

    public void msgGLContext(int i)
    {
        if(i == 8)
            Tex[0] = 0;
    }

    protected void contextResize(int i, int j)
    {
    }

    public void preRender()
    {
        if(Tex[0] == 0)
        {
            com.maddox.opengl.gl.Enable(3553);
            com.maddox.opengl.gl.GenTextures(1, Tex);
            byte abyte0[] = null;
            int i;
            int j;
            try
            {
                com.maddox.TexImage.TexImage teximage = new TexImage();
                teximage.LoadTGA("3do/effects/cinema/scratches.tga");
                i = teximage.sx;
                j = teximage.sy;
                int k = i * j * 4;
                abyte0 = new byte[k];
                int l = 0;
                for(int i1 = 0; i1 < k; i1 += 4)
                {
                    abyte0[i1 + 0] = -128;
                    abyte0[i1 + 1] = -128;
                    abyte0[i1 + 2] = -128;
                    abyte0[i1 + 3] = teximage.image[l++];
                }

            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
            com.maddox.opengl.gl.TexParameteri(3553, 10242, 10497);
            com.maddox.opengl.gl.TexParameteri(3553, 10243, 10497);
            com.maddox.opengl.gl.TexParameteri(3553, 10240, 9729);
            com.maddox.opengl.gl.TexParameteri(3553, 10241, 9729);
            com.maddox.opengl.gl.TexImage2D(3553, 0, 32856, i, j, 0, 6408, 5121, abyte0);
        }
    }

    public void resetGame()
    {
        com.maddox.il2.ai.RangeRandom rangerandom = new RangeRandom(java.lang.System.currentTimeMillis() + (long)_indx);
        rangerandom.nextFloat();
        initBlocks(0L, rangerandom);
    }

    public boolean isShow()
    {
        if(_indx == 0)
            return super.isShow();
        else
            return com.maddox.il2.engine.Config.cur.isUse3Renders() && super.isShow();
    }

    public Cinema(int i, float f)
    {
        super(f);
        blocks = new com.maddox.il2.objects.effects.VScratchBlock[4];
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(_indx == 0)
            setName("renderCinema");
        com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, viewPort);
    }

    private void initBlocks(long l, com.maddox.il2.ai.RangeRandom rangerandom)
    {
        rangerandom.nextFloat();
        for(int i = 0; i < 4; i++)
        {
            if(blocks[i] == null)
                blocks[i] = new VScratchBlock();
            makeBlockPassive(i, l, rangerandom, true);
        }

        for(int j = 0; j < 4; j++)
            if(rangerandom.nextInt(100) > 20)
                makeBlockActive(j, l, rangerandom);

        for(int k = 0; k < 4; k++)
        {
            long l1 = rangerandom.nextLong(0L, blocks[k].timeEnd - blocks[k].timeStart);
            blocks[k].timeStart -= l1;
            blocks[k].timeEnd -= l1;
        }

    }

    private void processBlocks(long l, com.maddox.il2.ai.RangeRandom rangerandom)
    {
        rangerandom.nextFloat();
        for(int i = 0; i < 4; i++)
        {
            if(l <= blocks[i].timeEnd)
                continue;
            if(blocks[i].active)
                makeBlockPassive(i, l, rangerandom, true);
            else
                makeBlockActive(i, l, rangerandom);
        }

    }

    private void drawBlocks(long l, com.maddox.il2.ai.RangeRandom rangerandom)
    {
        rangerandom.nextFloat();
        for(int i = 0; i < 4; i++)
        {
            if(!blocks[i].active || l > blocks[i].timeEnd || l < blocks[i].timeStart)
                continue;
            com.maddox.il2.ai.RandomVector.getTimed(l * 10L, tmp, i * 71);
            float f = blocks[i].offs + (float)tmp.x * 0.04F;
            float f1;
            if(l - blocks[i].timeStart < 400L)
                f1 = (float)(l - blocks[i].timeStart) / 400F;
            else
            if(blocks[i].timeEnd - l < 400L)
                f1 = (float)(blocks[i].timeEnd - l) / 400F;
            else
                f1 = 1.0F;
            com.maddox.il2.ai.RandomVector.getTimedStepped(2, l * 101L, tmp, 123 + i * 29);
            float f2 = ((float)tmp.y + 1.0F) * 0.5F;
            f2 = 0.6F * f2 + 1.0F * (1.0F - f2);
            f1 *= f2;
            com.maddox.opengl.gl.Begin(7);
            for(int j = 0; j < blocks[i].nScratches; j++)
            {
                com.maddox.JGP.Point3f point3f = blocks[i].scratches[j];
                float f3 = f + point3f.x;
                float f4 = f1 * point3f.y;
                float f5 = point3f.z * 0.5F;
                com.maddox.il2.ai.RandomVector.getTimedStepped(4, l * 67L, tmp, 12 + i * 17 + j * 7);
                f3 = (float)((double)f3 + 0.0030000000260770321D * tmp.z);
                float f6 = rangerandom.nextFloat(0.0F, 1.0F);
                float f7 = f6 + 1.0F;
                if(blocks[i].white)
                    com.maddox.opengl.gl.Color4f(0.9F, 0.9F, 0.9F, f4 * 0.6F);
                else
                    com.maddox.opengl.gl.Color4f(0.2F, 0.2F, 0.2F, f4);
                com.maddox.opengl.gl.TexCoord2f(f6, vVert[0]);
                com.maddox.opengl.gl.Vertex2f(f3 + f5, 0.0F);
                com.maddox.opengl.gl.TexCoord2f(f6, vVert[1]);
                com.maddox.opengl.gl.Vertex2f(f3 - f5, 0.0F);
                com.maddox.opengl.gl.TexCoord2f(f7, vVert[1]);
                com.maddox.opengl.gl.Vertex2f(f3 - f5, 1.0F);
                com.maddox.opengl.gl.TexCoord2f(f7, vVert[0]);
                com.maddox.opengl.gl.Vertex2f(f3 + f5, 1.0F);
            }

            com.maddox.opengl.gl.End();
        }

    }

    private void makeBlockActive(int i, long l, com.maddox.il2.ai.RangeRandom rangerandom)
    {
        blocks[i].active = false;
        float f = rangerandom.nextFloat(0.0F, 1.0F);
        float f1 = f - 0.04F;
        float f2 = f + 0.15F + 0.04F;
        if(f1 < 0.001F || f2 > 0.999F)
        {
            makeBlockPassive(i, l, rangerandom, false);
            return;
        }
        for(int j = 0; j < 4; j++)
        {
            if(!blocks[j].active)
                continue;
            float f4 = blocks[j].offs - 0.04F;
            float f5 = blocks[j].offs + 0.15F + 0.04F;
            if(f1 <= f5 && f2 >= f4)
            {
                makeBlockPassive(i, l, rangerandom, false);
                return;
            }
        }

        blocks[i].active = true;
        blocks[i].offs = f;
        blocks[i].timeStart = l;
        blocks[i].timeEnd = l + rangerandom.nextLong(4000L, 25000L);
        blocks[i].nScratches = rangerandom.nextInt(1, 5);
        float f3 = rangerandom.nextFloat(0.6F, 1.0F) * 0.15F;
        for(int k = 0; k < 5; k++)
        {
            if(blocks[i].scratches[k] == null)
                blocks[i].scratches[k] = new Point3f();
            blocks[i].scratches[k].set(rangerandom.nextFloat(0.0F, f3), rangerandom.nextFloat(0.3F, 0.6F), 0.006F * rangerandom.nextFloat(0.55F, 1.0F));
        }

        blocks[i].white = false;
    }

    private void makeBlockPassive(int i, long l, com.maddox.il2.ai.RangeRandom rangerandom, boolean flag)
    {
        blocks[i].active = false;
        if(flag)
        {
            blocks[i].timeStart = l;
            blocks[i].timeEnd = l + rangerandom.nextLong(4000L, 13000L);
        } else
        {
            blocks[i].timeStart = l - 2L;
            blocks[i].timeEnd = l - 1L;
        }
    }

    public void render()
    {
        com.maddox.opengl.gl.ShadeModel(7425);
        com.maddox.opengl.gl.Disable(2929);
        com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.Enable(3042);
        com.maddox.opengl.gl.AlphaFunc(516, 0.0F);
        com.maddox.opengl.gl.BlendFunc(770, 771);
        renderScratches();
    }

    public void setShow(boolean flag)
    {
        super.setShow(flag);
    }

    private static void glRotVertex2f(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = com.maddox.JGP.Geom.cosDeg(f2);
        float f6 = com.maddox.JGP.Geom.sinDeg(f2);
        com.maddox.opengl.gl.Vertex2f((f + f3 * f5) - f4 * f6, f1 + f3 * f6 + f4 * f5);
    }

    private void renderScratches()
    {
        long l = com.maddox.rts.Time.current();
        rnd.setSeed(11L + l / 62L);
        rnd.nextFloat();
        rnd.nextFloat();
        com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
        processBlocks(l, rnd);
        drawBlocks(l, rnd);
        int i = rnd.nextInt(4, 10);
        int j = rnd.nextInt(2, 6);
        com.maddox.opengl.gl.Begin(7);
        com.maddox.opengl.gl.Color4f(0.2F, 0.2F, 0.2F, 0.6F);
        while(i-- > 0) 
        {
            float f = rnd.nextFloat(0.0F, 1.0F);
            float f3 = rnd.nextFloat(0.0F, 1.0F);
            float f6 = rnd.nextFloat(0.0016F, 0.0053F);
            float f10 = f6 * 1.333333F;
            int k = rnd.nextInt(0, 47);
            com.maddox.opengl.gl.TexCoord2f(uv[k * 8 + 2 + 0], uv[k * 8 + 2 + 1]);
            com.maddox.opengl.gl.Vertex2f(f + f6, f3 + f10);
            com.maddox.opengl.gl.TexCoord2f(uv[k * 8 + 0 + 0], uv[k * 8 + 0 + 1]);
            com.maddox.opengl.gl.Vertex2f(f - f6, f3 + f10);
            com.maddox.opengl.gl.TexCoord2f(uv[k * 8 + 4 + 0], uv[k * 8 + 4 + 1]);
            com.maddox.opengl.gl.Vertex2f(f - f6, f3 - f10);
            com.maddox.opengl.gl.TexCoord2f(uv[k * 8 + 6 + 0], uv[k * 8 + 6 + 1]);
            com.maddox.opengl.gl.Vertex2f(f + f6, f3 - f10);
        }
        com.maddox.opengl.gl.End();
        com.maddox.opengl.gl.Begin(7);
        com.maddox.opengl.gl.Color4f(0.9F, 0.9F, 0.9F, 0.55F);
        while(j-- > 0) 
        {
            float f1 = rnd.nextFloat(0.0F, 1.0F);
            float f4 = rnd.nextFloat(0.0F, 1.0F);
            float f7 = rnd.nextFloat(0.0015F, 0.004F);
            float f11 = f7 * 1.333333F;
            int i1 = rnd.nextInt(0, 47);
            com.maddox.opengl.gl.TexCoord2f(uv[i1 * 8 + 2 + 0], uv[i1 * 8 + 2 + 1]);
            com.maddox.opengl.gl.Vertex2f(f1 + f7, f4 + f11);
            com.maddox.opengl.gl.TexCoord2f(uv[i1 * 8 + 0 + 0], uv[i1 * 8 + 0 + 1]);
            com.maddox.opengl.gl.Vertex2f(f1 - f7, f4 + f11);
            com.maddox.opengl.gl.TexCoord2f(uv[i1 * 8 + 4 + 0], uv[i1 * 8 + 4 + 1]);
            com.maddox.opengl.gl.Vertex2f(f1 - f7, f4 - f11);
            com.maddox.opengl.gl.TexCoord2f(uv[i1 * 8 + 6 + 0], uv[i1 * 8 + 6 + 1]);
            com.maddox.opengl.gl.Vertex2f(f1 + f7, f4 - f11);
        }
        com.maddox.opengl.gl.End();
        com.maddox.opengl.gl.Begin(7);
        com.maddox.opengl.gl.Color4f(0.2F, 0.2F, 0.2F, 0.6F);
        if(rnd.nextInt(0, 100) < 15)
        {
            float f2 = rnd.nextFloat(0.0F, 1.0F);
            float f5 = rnd.nextFloat(0.0F, 1.0F);
            float f8 = rnd.nextFloat(0.003F, 0.0035F);
            float f12 = f8 * 1.333333F;
            f8 *= 8F;
            int j1 = rnd.nextInt(0, 47);
            float f17 = vThread[0];
            float f21 = vThread[1];
            float f15;
            if(rnd.nextInt(0, 1000) > 500)
                f15 = 0.0F;
            else
                f15 = 0.5F;
            float f19 = f15 + 0.5F;
            float f23 = rnd.nextFloat(0.0F, 359.9F);
            com.maddox.opengl.gl.TexCoord2f(f19, f17);
            com.maddox.il2.objects.effects.Cinema.glRotVertex2f(f2, f5, f23, f8, f12);
            com.maddox.opengl.gl.TexCoord2f(f15, f17);
            com.maddox.il2.objects.effects.Cinema.glRotVertex2f(f2, f5, f23, -f8, f12);
            com.maddox.opengl.gl.TexCoord2f(f15, f21);
            com.maddox.il2.objects.effects.Cinema.glRotVertex2f(f2, f5, f23, -f8, -f12);
            com.maddox.opengl.gl.TexCoord2f(f19, f21);
            com.maddox.il2.objects.effects.Cinema.glRotVertex2f(f2, f5, f23, f8, -f12);
        }
        com.maddox.opengl.gl.End();
        com.maddox.opengl.gl.Disable(3553);
        com.maddox.opengl.Provider.setEnableBW(false);
        com.maddox.opengl.gl.BlendFunc(1, 770);
        long l1 = l / 62L;
        float f9 = (float)(l % 62L) / 62F;
        rnd.setSeed(l1);
        rnd.nextFloat();
        rnd.nextFloat();
        float f13 = rnd.nextFloat(0.0F, 1.0F);
        rnd.setSeed(l1 + 1L);
        rnd.nextFloat();
        rnd.nextFloat();
        float f14 = rnd.nextFloat(0.0F, 1.0F);
        float f16 = 1.0F + 0.1F * (f13 * (1.0F - f9) + f14 * f9);
        float f18 = 0.2F;
        float f20 = 0.8235294F;
        float f22 = 0.6156863F;
        float f24 = 0.172549F;
        float f25 = f20 * f18;
        float f26 = f22 * f18;
        float f27 = f24 * f18;
        float f28 = (1.0F - f18) * f16;
        com.maddox.opengl.gl.Begin(6);
        com.maddox.opengl.gl.Color4f(f25, f26, f27, f28 * 1.0F);
        com.maddox.opengl.gl.Vertex2f(0.5F, 0.5F);
        com.maddox.opengl.gl.Color4f(f25, f26, f27, f28 * 1.0F);
        for(int k1 = 0; k1 < 36; k1++)
            com.maddox.opengl.gl.Vertex2f(circleScr[k1].x, circleScr[k1].y);

        com.maddox.opengl.gl.Vertex2f(circleScr[0].x, circleScr[0].y);
        com.maddox.opengl.gl.End();
        for(int i2 = 0; i2 < 4; i2++)
        {
            com.maddox.opengl.gl.Begin(6);
            com.maddox.opengl.gl.Color4f(f25, f26, f27, f28 * 0.92F);
            com.maddox.opengl.gl.Vertex2f(cornerScr[i2].x, cornerScr[i2].y);
            com.maddox.opengl.gl.Color4f(f25, f26, f27, f28 * 1.0F);
            for(int j2 = 0; j2 < 10; j2++)
            {
                int k2 = (i2 * 9 + j2) % 36;
                com.maddox.opengl.gl.Vertex2f(circleScr[k2].x, circleScr[k2].y);
            }

            com.maddox.opengl.gl.End();
        }

        com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.BlendFunc(770, 771);
        com.maddox.opengl.Provider.setEnableBW(true);
    }

    private int Tex[] = {
        0
    };
    private int _indx;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    private static final int N_SCRATCH_COLS = 8;
    private static final int N_SCRATCH_ROWS = 6;
    private static final int N_SCRATCH_IMAGES = 48;
    private static float uv[];
    private static float vVert[];
    private static float vThread[];
    private static com.maddox.JGP.Vector3d tmp = new Vector3d();
    private static final int N_CIRCLE_POINTS = 36;
    private static com.maddox.JGP.Point2f circleScr[];
    private static com.maddox.JGP.Point2f cornerScr[];
    private static final int N_BLOCKS = 4;
    private static final int MAX_VSCRATCHES = 5;
    private static final long MIN_BLOCK_ON_TM = 4000L;
    private static final long INOUT_BLOCK_TM = 400L;
    private static final long MAX_BLOCK_ON_TM = 25000L;
    private static final long MIN_BLOCK_OFF_TM = 4000L;
    private static final long MAX_BLOCK_OFF_TM = 13000L;
    private static final float MAX_BLOCK_WD = 0.15F;
    private static final float MAX_BLOCK_OFFSR = 0.04F;
    private static final float MAX_SCRATCH_WD = 0.006F;
    private com.maddox.il2.objects.effects.VScratchBlock blocks[];

    static 
    {
        uv = new float[384];
        vVert = new float[2];
        vThread = new float[2];
        circleScr = new com.maddox.JGP.Point2f[36];
        cornerScr = new com.maddox.JGP.Point2f[4];
        for(int i = 0; i < 36; i++)
        {
            circleScr[i] = new Point2f();
            float f = 90F - ((float)i * 360F) / 36F;
            float f1 = com.maddox.JGP.Geom.cosDeg(f);
            float f2 = com.maddox.JGP.Geom.sinDeg(f);
            circleScr[i].set((f1 + 1.0F) * 0.5F, (f2 + 1.0F) * 0.5F);
        }

        cornerScr[0] = new Point2f(1.0F, 1.0F);
        cornerScr[1] = new Point2f(1.0F, 0.0F);
        cornerScr[2] = new Point2f(0.0F, 0.0F);
        cornerScr[3] = new Point2f(0.0F, 1.0F);
        int j1 = 0;
        float f3 = 0.00390625F;
        for(int k = 0; k < 6; k++)
        {
            for(int j = 0; j < 8; j++)
            {
                for(int i1 = 0; i1 < 2; i1++)
                {
                    for(int l = 0; l < 2; l++)
                    {
                        uv[j1 * 8 + (i1 * 2 + l) * 2 + 0] = (float)(j + l) / 8F + (l != 0 ? -f3 : f3);
                        uv[j1 * 8 + (i1 * 2 + l) * 2 + 1] = (float)(k + i1) / 8F + (i1 != 0 ? -f3 : f3);
                    }

                }

                j1++;
            }

        }

        vVert[0] = 0.875F + f3;
        vVert[1] = 1.0F - f3;
        vThread[0] = 0.75F + f3;
        vThread[1] = 0.875F - f3;
    }
}
