// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SunGlare.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Time;

public class SunGlare extends com.maddox.il2.engine.Render
    implements com.maddox.opengl.MsgGLContextListener
{

    public SunGlare(int i, float f)
    {
        super(f);
        curTexIsBW = false;
        wantBW = false;
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(i == 0)
            setName("renderSunGlare");
        com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, viewPort);
    }

    public void resetGame()
    {
        prevTm = com.maddox.rts.Time.current();
        curGlare = 0.0F;
        curSun = 0.0F;
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
            return com.maddox.il2.engine.Config.cur.isUse3Renders() && com.maddox.il2.game.Main3D.cur3D().sunGlare.isShow();
    }

    public void setShow(boolean flag)
    {
        if(_indx == 0)
            super.setShow(flag);
        prevTm = com.maddox.rts.Time.current();
        curGlare = 0.0F;
        curSun = 0.0F;
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
            teximage.LoadTGA("effects/sunglare/glare01.tga_asis");
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

    private float computeIntegratedSunFlash(com.maddox.JGP.Point3f point3f)
    {
        rayDir.set(com.maddox.il2.ai.World.Sun().ToSun);
        axisZ.set(rayDir);
        if(java.lang.Math.abs(axisZ.z) > 0.5D)
            axisX.set(1.0D, 0.0D, 0.0D);
        else
            axisX.set(0.0D, 0.0D, 1.0D);
        axisY.cross(axisZ, axisX);
        axisY.normalize();
        axisX.cross(axisY, axisZ);
        axisX.normalize();
        float f = 0.009F;
        float f1 = 0.0F;
        point3f.x = 0.0F;
        com.maddox.il2.game.VisibilityChecker.checkLandObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkCabinObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkPlaneObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkObjObstacle = true;
        for(int i = 0; i < 4; i++)
        {
            rayDir.set(com.maddox.il2.ai.World.Sun().ToSun);
            if(i < 3)
            {
                tmpv0.set(axisX);
                tmpv1.set(axisY);
                switch(i)
                {
                case 0: // '\0'
                    tmpv0.set(tmpv1);
                    break;

                case 1: // '\001'
                    tmpv0.scale(com.maddox.JGP.Geom.cosDeg(30F));
                    tmpv1.scale(-com.maddox.JGP.Geom.sinDeg(30F));
                    tmpv0.add(tmpv1);
                    break;

                case 2: // '\002'
                    tmpv0.scale(-com.maddox.JGP.Geom.cosDeg(30F));
                    tmpv1.scale(-com.maddox.JGP.Geom.sinDeg(30F));
                    tmpv0.add(tmpv1);
                    break;
                }
                tmpv0.scale(f);
                rayDir.add(tmpv0);
                rayDir.normalize();
            }
            float f2 = com.maddox.il2.game.VisibilityChecker.computeVisibility(rayDir, null);
            if(f2 < 0.0F)
                return f2;
            if(f2 > f1)
                f1 = f2;
        }

        point3f.x = com.maddox.il2.game.VisibilityChecker.resultAng;
        return f1;
    }

    public void render()
    {
        if(com.maddox.il2.engine.RenderContext.cfgLandShading.get() < 2 && (!com.maddox.il2.game.Mission.isNet() || !com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts))
            return;
        com.maddox.il2.game.Main3D.cur3D().setRenderIndx(_indx);
        float f = computeIntegratedSunFlash(resAngle);
        if(f <= -2F)
        {
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
            return;
        }
        float f1;
        float f2;
        boolean flag;
        if(f < 0.0F)
        {
            flag = false;
            f1 = 0.0F;
            f2 = 0.0F;
        } else
        {
            flag = true;
            float f3 = 50F;
            if(resAngle.x >= f3)
            {
                f1 = 0.0F;
            } else
            {
                float f4 = resAngle.x / f3;
                f1 = 0.5F * (com.maddox.JGP.Geom.cosDeg(f4 * 180F) + 1.0F);
                if(f1 >= 1.0F)
                    f1 = 1.0F;
                f1 *= f;
                if(f1 < 0.007843138F)
                    f1 = 0.0F;
            }
            float f5 = 50F;
            if(resAngle.x >= f5)
            {
                f2 = 0.0F;
            } else
            {
                float f7 = resAngle.x / f5;
                f2 = 0.5F * (com.maddox.JGP.Geom.cosDeg(f7 * 180F) + 1.0F);
            }
            if(f > 0.003921569F)
            {
                f2 *= f;
                f2 = 0.2F + 0.8F * f2;
                if(f2 >= 1.0F)
                    f2 = 1.0F;
            } else
            {
                f2 = 0.0F;
            }
        }
        long l = com.maddox.rts.Time.current();
        long l1 = l - prevTm;
        if(l1 < 0L)
        {
            curGlare = f1;
            curSun = f2;
        } else
        if(l1 != 0L)
        {
            float f10 = f1 - curGlare;
            float f12 = f10 < 0.0F ? 1.5F : 3F;
            float f14 = ((float)l1 * f12) / 1000F;
            if(f14 >= java.lang.Math.abs(f10))
                curGlare = f1;
            else
                curGlare += f10 >= 0.0F ? f14 : -f14;
            f10 = f2 - curSun;
            f12 = f10 < 0.0F ? 4F : 6F;
            f14 = ((float)l1 * f12) / 1000F;
            if(f14 >= java.lang.Math.abs(f10))
                curSun = f2;
            else
                curSun += f10 >= 0.0F ? f14 : -f14;
        }
        prevTm = l;
        if(flag && curSun < 0.007843138F)
            flag = false;
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
        if(flag1)
        {
            com.maddox.opengl.gl.Disable(3553);
            com.maddox.opengl.gl.BlendFunc(1, 770);
            com.maddox.opengl.gl.Begin(7);
            tmpp0.set(com.maddox.il2.engine.Engine.lightEnv().sun().Red, com.maddox.il2.engine.Engine.lightEnv().sun().Green, com.maddox.il2.engine.Engine.lightEnv().sun().Blue);
            tmpp1.set(1.0D, 1.0D, 1.0D);
            tmpp2.interpolate(tmpp0, tmpp1, 0.75F);
            tmpp2.scale(curGlare);
            tmpp2.scale(0.625D);
            com.maddox.opengl.gl.Color4f((float)tmpp2.x, (float)tmpp2.y, (float)tmpp2.z, 1.0F - curGlare * 0.5F);
            com.maddox.opengl.gl.Vertex2f(1.0F, 1.0F);
            com.maddox.opengl.gl.Vertex2f(1.0F, 0.0F);
            com.maddox.opengl.gl.Vertex2f(0.0F, 0.0F);
            com.maddox.opengl.gl.Vertex2f(0.0F, 1.0F);
            com.maddox.opengl.gl.End();
            com.maddox.opengl.gl.Enable(3553);
        }
        if(flag)
        {
            tmpp1.set(rayDir);
            tmpp1.scale(30000D);
            tmpp1.add(com.maddox.il2.game.VisibilityChecker.nosePos);
            if(!com.maddox.il2.game.Main3D.cur3D().project2d_norm(tmpp1.x, tmpp1.y, tmpp1.z, tmpp2))
            {
                com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
                return;
            }
            float f6 = ((float)tmpp2.x + 1.0F) * 0.5F;
            float f8 = ((float)tmpp2.y + 1.0F) * 0.5F;
            float f9 = curSun > 0.4F ? 1.0F + 0.3F * ((curSun - 0.4F) / 0.6F) : 1.0F;
            float f11 = 0.25F * f9;
            float f13 = f11;
            if(f6 - f11 > 1.0F || f6 + f11 < 0.0F || f8 - f13 > 1.0F || f8 + f13 < 0.0F)
            {
                com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
                return;
            }
            float f15 = com.maddox.JGP.Geom.tanDeg(com.maddox.il2.game.Main3D.cur3D().camera3D.FOV() * 0.5F);
            float f16 = (float)tmpp2.x * f15;
            f16 *= -90F;
            com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
            com.maddox.opengl.gl.BlendFunc(770, 1);
            com.maddox.opengl.gl.Begin(7);
            com.maddox.opengl.gl.Color4f(1.0F, 1.0F, 1.0F, curSun);
            float f17 = 0.00390625F;
            com.maddox.opengl.gl.TexCoord2f(1.0F - f17, 0.0F + f17);
            com.maddox.il2.objects.effects.SunGlare.glRotVertex2f(f6, f8, f16, f11, f13);
            com.maddox.opengl.gl.TexCoord2f(0.0F + f17, 0.0F + f17);
            com.maddox.il2.objects.effects.SunGlare.glRotVertex2f(f6, f8, f16, -f11, f13);
            com.maddox.opengl.gl.TexCoord2f(0.0F + f17, 1.0F - f17);
            com.maddox.il2.objects.effects.SunGlare.glRotVertex2f(f6, f8, f16, -f11, -f13);
            com.maddox.opengl.gl.TexCoord2f(1.0F - f17, 1.0F - f17);
            com.maddox.il2.objects.effects.SunGlare.glRotVertex2f(f6, f8, f16, f11, -f13);
            com.maddox.opengl.gl.End();
        }
        com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
    }

    private static void glRotVertex2f(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = com.maddox.JGP.Geom.cosDeg(f2);
        float f6 = com.maddox.JGP.Geom.sinDeg(f2);
        com.maddox.opengl.gl.Vertex2f(f + (f3 * f5 - f4 * f6), f1 + (f3 * f6 + f4 * f5) * 1.333333F);
    }

    private int Tex[] = {
        0
    };
    private boolean curTexIsBW;
    private boolean wantBW;
    private int _indx;
    private long prevTm;
    private float curGlare;
    private float curSun;
    private static com.maddox.JGP.Vector3d rayDir = new Vector3d();
    private static com.maddox.JGP.Point3f resAngle = new Point3f();
    private static com.maddox.JGP.Vector3d axisX = new Vector3d();
    private static com.maddox.JGP.Vector3d axisY = new Vector3d();
    private static com.maddox.JGP.Vector3d axisZ = new Vector3d();
    private static com.maddox.JGP.Point3d tmpp0 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp1 = new Point3d();
    private static com.maddox.JGP.Point3d tmpp2 = new Point3d();
    private static com.maddox.JGP.Vector3d tmpv0 = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpv1 = new Vector3d();

}
