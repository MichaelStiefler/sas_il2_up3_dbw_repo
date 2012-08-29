// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightsGlare.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.il2.objects.vehicles.lights.SearchlightGeneric;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Time;

public class LightsGlare extends com.maddox.il2.engine.Render
    implements com.maddox.opengl.MsgGLContextListener
{

    public LightsGlare(int i, float f)
    {
        super(f);
        glareData = null;
        curTexIsBW = false;
        wantBW = false;
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(i == 0)
            setName("renderLightsGlare");
        com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, viewPort);
    }

    public void resetGame()
    {
        prevTm = com.maddox.rts.Time.current();
        curGlare = 0.0F;
        glareData = null;
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

    public boolean isShow()
    {
        if(_indx == 0)
            return super.isShow();
        else
            return com.maddox.il2.engine.Config.cur.isUse3Renders() && com.maddox.il2.game.Main3D.cur3D().lightsGlare.isShow();
    }

    public void setShow(boolean flag)
    {
        if(_indx == 0)
            super.setShow(flag);
        prevTm = com.maddox.rts.Time.current();
        curGlare = 0.0F;
        glareData = null;
    }

    public void enterBWmode()
    {
        wantBW = true;
    }

    public void leaveBWmode()
    {
        wantBW = false;
    }

    public void preRender()
    {
        if(Tex[0] != 0 && curTexIsBW == wantBW)
            return;
        curTexIsBW = wantBW;
        if(Tex[0] != 0)
        {
            com.maddox.opengl.gl.DeleteTextures(1, Tex);
            Tex[0] = 0;
        }
        com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.GenTextures(1, Tex);
        com.maddox.TexImage.TexImage teximage = new TexImage();
        try
        {
            teximage.LoadTGA("effects/sunglare/glare02.tga_asis");
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        if(wantBW)
        {
            int i = teximage.sx * teximage.sy * 3;
            for(int j = 0; j < i; j += 3)
            {
                int k = (teximage.image[j + 0] & 0xff) + (teximage.image[j + 1] & 0xff) + (teximage.image[j + 2] & 0xff);
                byte byte0 = (byte)((k + 1) / 3);
                teximage.image[j + 0] = byte0;
                teximage.image[j + 1] = byte0;
                teximage.image[j + 2] = byte0;
            }

        }
        com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
        com.maddox.opengl.gl.TexParameteri(3553, 10242, 10497);
        com.maddox.opengl.gl.TexParameteri(3553, 10243, 10497);
        com.maddox.opengl.gl.TexParameteri(3553, 10240, 9729);
        com.maddox.opengl.gl.TexParameteri(3553, 10241, 9729);
        com.maddox.opengl.gl.TexImage2D(3553, 0, 32849, teximage.sx, teximage.sy, 0, 6407, 5121, teximage.image);
    }

    public float computeFlash(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3f point3f)
    {
        com.maddox.il2.game.VisibilityChecker.checkLandObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkCabinObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkPlaneObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkObjObstacle = true;
        com.maddox.il2.game.VisibilityChecker.targetPosInput = point3d;
        point3f.x = 0.0F;
        float f = com.maddox.il2.game.VisibilityChecker.computeVisibility(null, actor);
        point3f.x = com.maddox.il2.game.VisibilityChecker.resultAng;
        return f;
    }

    public void render()
    {
        if(com.maddox.il2.engine.RenderContext.cfgLandShading.get() < 2 && (!com.maddox.il2.game.Mission.isNet() || !com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts))
            return;
        int i = com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.possibleGlare();
        if(i == 0)
            return;
        if(i == 2)
            glareData = null;
        i = com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.numlightsGlare();
        if(glareData == null || i * 3 != glareData.length)
        {
            glareData = new float[i * 3];
            int j = glareData.length;
            for(int k = 0; k < j; k++)
                glareData[k] = 0.0F;

        }
        com.maddox.il2.game.Main3D.cur3D().setRenderIndx(_indx);
        com.maddox.il2.game.Main3D.cur3D()._camera3D[_indx].pos.getRender(SL_cameraPos);
        if(!com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.computeGlare(this, SL_cameraPos))
        {
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
            return;
        }
        float f = 0.0F;
        long l = com.maddox.rts.Time.current();
        long l1 = l - prevTm;
        boolean flag = false;
        for(int i1 = 0; i1 < i; i1++)
        {
            float f2 = glareData[i1 * 3 + 0];
            float f4 = glareData[i1 * 3 + 1];
            float f6 = glareData[i1 * 3 + 2];
            float f9;
            float f11;
            if(f2 < 0.0F)
            {
                f9 = 0.0F;
                f11 = 0.0F;
            } else
            {
                float f13 = 34F;
                if(f4 >= f13)
                {
                    f9 = 0.0F;
                } else
                {
                    float f16 = f4 / f13;
                    f9 = 0.5F * (com.maddox.JGP.Geom.cosDeg(f16 * 180F) + 1.0F);
                    if(f9 >= 1.0F)
                        f9 = 1.0F;
                    f9 *= f2;
                    if(f9 <= 0.0F)
                        f9 = 0.0F;
                }
                float f17 = 48F;
                if(f4 >= f17)
                {
                    f11 = 0.0F;
                } else
                {
                    float f20 = f4 / f17;
                    f11 = 0.5F * (com.maddox.JGP.Geom.cosDeg(f20 * 180F) + 1.0F);
                }
                if(f2 > 0.003921569F)
                {
                    f11 *= f2;
                    f11 = 0.2F + 0.8F * f11;
                    if(f11 >= 1.0F)
                        f11 = 1.0F;
                } else
                {
                    f11 = 0.0F;
                }
            }
            f += f9;
            if(l1 < 0L)
                f6 = f11;
            else
            if(l1 != 0L)
            {
                float f14 = f11 - f6;
                float f18 = f14 < 0.0F ? 2.5F : 5F;
                float f21 = ((float)l1 * f18) / 1000F;
                if(f21 >= java.lang.Math.abs(f14))
                    f6 = f11;
                else
                    f6 += f14 >= 0.0F ? f21 : -f21;
            }
            glareData[i1 * 3 + 2] = f6;
            if(f2 >= 0.0F && f6 >= 0.007843138F)
                flag = true;
        }

        float f1 = f;
        if(f1 >= 1.0F)
            f1 = 1.0F;
        if(l1 < 0L)
            curGlare = f1;
        else
        if(l1 != 0L)
        {
            float f3 = f1 - curGlare;
            float f5 = f3 < 0.0F ? 1.1F : 3.2F;
            float f7 = ((float)l1 * f5) / 1000F;
            if(f7 >= java.lang.Math.abs(f3))
                curGlare = f1;
            else
                curGlare += f3 >= 0.0F ? f7 : -f7;
        }
        prevTm = l;
        boolean flag1 = curGlare >= 0.007843138F;
        if(!flag && !flag1)
        {
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
            return;
        }
        com.maddox.opengl.gl.ShadeModel(7425);
        com.maddox.opengl.gl.Disable(2929);
        com.maddox.opengl.gl.Enable(3042);
        com.maddox.opengl.gl.AlphaFunc(516, 0.0F);
        com.maddox.opengl.gl.Enable(3553);
        boolean flag2 = false;
        if(flag1)
        {
            com.maddox.opengl.gl.Disable(3553);
            com.maddox.opengl.gl.BlendFunc(1, 770);
            com.maddox.opengl.gl.Begin(7);
            if(flag2)
            {
                com.maddox.opengl.gl.Color4f(0.0F, 0.0F, 0.0F, 1.0F - curGlare * 0.78F);
            } else
            {
                tmpp2.set(1.0D, 1.0D, 1.0D);
                tmpp2.scale(curGlare);
                tmpp2.scale(0.76999998092651367D);
                com.maddox.opengl.gl.Color4f((float)tmpp2.x, (float)tmpp2.y, (float)tmpp2.z, 1.0F - curGlare * 0.86F);
            }
            com.maddox.opengl.gl.Vertex2f(1.0F, 1.0F);
            com.maddox.opengl.gl.Vertex2f(1.0F, 0.0F);
            com.maddox.opengl.gl.Vertex2f(0.0F, 0.0F);
            com.maddox.opengl.gl.Vertex2f(0.0F, 1.0F);
            com.maddox.opengl.gl.End();
            com.maddox.opengl.gl.Enable(3553);
        }
        if(!flag)
        {
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
            return;
        }
        com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
        com.maddox.opengl.gl.BlendFunc(770, 1);
        com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.getnextposandcolorGlare(null, null);
        for(int j1 = 0; j1 < i; j1++)
        {
            com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.getnextposandcolorGlare(tmpp1, tmpcolor);
            float f8 = glareData[j1 * 3 + 2];
            if(f8 < 0.007843138F || glareData[j1 * 3 + 0] < 0.0F || !com.maddox.il2.game.Main3D.cur3D().project2d_norm(tmpp1.x, tmpp1.y, tmpp1.z, tmpp2))
                continue;
            float f10 = ((float)tmpp2.x + 1.0F) * 0.5F;
            float f12 = ((float)tmpp2.y + 1.0F) * 0.5F;
            float f15;
            if(flag2)
            {
                float f22 = f8 > 0.4F ? (f8 - 0.4F) / 0.6F : 0.0F;
                f15 = 0.05F * (1.0F - f22) + 0.15F * f22;
            } else
            {
                float f23 = f8 > 0.3F ? (f8 - 0.3F) / 0.7F : 0.0F;
                f15 = 0.03F * (1.0F - f23) + 0.14F * f23;
            }
            float f19 = f15;
            if(f10 - f15 <= 1.0F && f10 + f15 >= 0.0F && f12 - f19 <= 1.0F && f12 + f19 >= 0.0F)
            {
                float f24 = com.maddox.JGP.Geom.tanDeg(com.maddox.il2.game.Main3D.cur3D().camera3D.FOV() * 0.5F);
                float f25 = -90F * ((float)tmpp2.x * f24);
                float f26 = com.maddox.JGP.Geom.cosDeg(f25);
                float f27 = com.maddox.JGP.Geom.sinDeg(f25);
                com.maddox.opengl.gl.Begin(7);
                com.maddox.opengl.gl.Color4f(tmpcolor.x, tmpcolor.y, tmpcolor.z, f8);
                float f28 = 0.00390625F;
                com.maddox.opengl.gl.TexCoord2f(1.0F - f28, 0.0F + f28);
                com.maddox.il2.objects.effects.LightsGlare.glRotVertex2f(f10, f12, f26, f27, f15, f19);
                com.maddox.opengl.gl.TexCoord2f(0.0F + f28, 0.0F + f28);
                com.maddox.il2.objects.effects.LightsGlare.glRotVertex2f(f10, f12, f26, f27, -f15, f19);
                com.maddox.opengl.gl.TexCoord2f(0.0F + f28, 1.0F - f28);
                com.maddox.il2.objects.effects.LightsGlare.glRotVertex2f(f10, f12, f26, f27, -f15, -f19);
                com.maddox.opengl.gl.TexCoord2f(1.0F - f28, 1.0F - f28);
                com.maddox.il2.objects.effects.LightsGlare.glRotVertex2f(f10, f12, f26, f27, f15, -f19);
                com.maddox.opengl.gl.End();
            }
        }

        com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
    }

    private static void glRotVertex2f(float f, float f1, float f2, float f3, float f4, float f5)
    {
        com.maddox.opengl.gl.Vertex2f(f + (f4 * f2 - f5 * f3), f1 + (f4 * f3 + f5 * f2) * 1.333333F);
    }

    public static final int NELEMS_IN_GD = 3;
    public float glareData[];
    private int Tex[] = {
        0
    };
    private boolean curTexIsBW;
    private boolean wantBW;
    private int _indx;
    private long prevTm;
    private float curGlare;
    private static com.maddox.JGP.Point3d SL_cameraPos = new Point3d();
    private static com.maddox.JGP.Vector3d rayDir = new Vector3d();
    private static com.maddox.JGP.Vector3d axisX = new Vector3d();
    private static com.maddox.JGP.Vector3d axisY = new Vector3d();
    private static com.maddox.JGP.Vector3d axisZ = new Vector3d();
    private static com.maddox.JGP.Point3d tmpp0 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp1 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp2 = new Point3d();
    private static com.maddox.JGP.Vector3d tmpv0 = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpv1 = new Vector3d();
    private static com.maddox.JGP.Point3f tmpcolor = new Point3f();

}
