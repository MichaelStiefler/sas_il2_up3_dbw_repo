// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OverLoad.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Tuple2f;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.sound.AudioDevice;

public class OverLoad extends com.maddox.il2.engine.Render
    implements com.maddox.opengl.MsgGLContextListener
{

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

    protected void contextResize(int k, int l)
    {
    }

    public void preRender()
    {
        if(Tex[0] == 0)
        {
            com.maddox.opengl.gl.Enable(3553);
            com.maddox.opengl.gl.GenTextures(1, Tex);
            com.maddox.TexImage.TexImage teximage = new TexImage();
            try
            {
                teximage.LoadTGA("3do/effects/overload/overload.tga");
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
            com.maddox.opengl.gl.TexImage2D(3553, 0, 6409, teximage.sx, teximage.sy, 0, 6409, 5121, teximage.image);
        }
    }

    public OverLoad(int i, float f)
    {
        super(f);
        Tex = new int[1];
        _indx = 0;
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(_indx == 0)
            setName("renderOverLoad");
        com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, super.viewPort);
    }

    private static final float clamp(float f, float f1, float f2)
    {
        return java.lang.Math.min(f2, java.lang.Math.max(f1, f));
    }

    public void render()
    {
        if(!com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts)
            return;
        if(!(com.maddox.il2.ai.World.getPlayerFM() instanceof com.maddox.il2.fm.RealFlightModel))
        {
            if(com.maddox.il2.ai.World.getPlayerFM() != null && com.maddox.il2.game.Main3D.cur3D().cockpitCur != null)
            {
                float f = 1.0F;
                f = ((com.maddox.il2.fm.FlightModelMain) (com.maddox.il2.ai.World.getPlayerFM())).AS.getPilotHealth(com.maddox.il2.game.Main3D.cur3D().cockpitCur.astatePilotIndx());
                float f1 = 1.0F - f;
                com.maddox.il2.engine.Render.clearStates();
                com.maddox.opengl.gl.ShadeModel(7425);
                com.maddox.opengl.gl.Disable(2929);
                com.maddox.opengl.gl.Enable(3553);
                com.maddox.opengl.gl.Enable(3042);
                com.maddox.opengl.gl.AlphaFunc(516, 0.0F);
                renderMinus(f1);
            }
            return;
        }
        com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM();
        if(realflightmodel == null)
            return;
        float f2 = 1.0F;
        if(com.maddox.il2.game.Main3D.cur3D().cockpitCur != null)
            f2 = ((com.maddox.il2.fm.FlightModelMain) (realflightmodel)).AS.getPilotHealth(com.maddox.il2.game.Main3D.cur3D().cockpitCur.astatePilotIndx());
        float f3 = 1.0F - f2;
        if(realflightmodel.saveDeep < 0.02F && realflightmodel.saveDeep > -0.02F && (double)f3 < 0.02D)
            return;
        com.maddox.il2.engine.Render.clearStates();
        com.maddox.opengl.gl.ShadeModel(7425);
        com.maddox.opengl.gl.Disable(2929);
        com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.Enable(3042);
        com.maddox.opengl.gl.AlphaFunc(516, 0.0F);
        if(realflightmodel.saveDeep >= 0.02F)
        {
            renderPlus(realflightmodel.saveDeep);
            renderSound(realflightmodel.saveDeep * 0.66F);
            if((double)f3 >= 0.02D)
                renderMinus(f3);
        } else
        if(realflightmodel.saveDeep <= -0.02F)
        {
            renderMinus(-realflightmodel.saveDeep + f3);
            renderSound(-realflightmodel.saveDeep * 1.35F);
        } else
        {
            renderMinus(f3);
        }
    }

    private void renderSound(float f)
    {
        if(_indx != 0)
            return;
        f = java.lang.Math.abs(f);
        if(f > 0.7F)
            f = 1.0F;
        else
        if(f < 0.2F)
            f = 0.0F;
        else
            f = (f - 0.2F) / 0.5F;
        com.maddox.sound.AudioDevice.setControl(2000, (int)(f * 100F));
    }

    public void setShow(boolean flag)
    {
        if(_indx != 0)
            return;
        super.setShow(flag);
        if(!flag)
            renderSound(0.0F);
    }

    public boolean isShow()
    {
        if(_indx == 0)
            return super.isShow();
        return com.maddox.il2.engine.Config.cur.isUse3Renders() && com.maddox.il2.game.Main3D.cur3D().overLoad.isShow();
    }

    private void renderPlus(float f)
    {
        com.maddox.opengl.gl.BindTexture(3553, Tex[0]);
        com.maddox.opengl.gl.BlendFunc(774, 770);
        if(f >= 0.97F)
            f = 0.97F;
        f *= 3F;
        float f1;
        float f2;
        float f3;
        float f4;
        if(f <= 1.0F)
        {
            f1 = 0.0F;
            f2 = 1.0F;
            f3 = f;
            f4 = 1.0F - f;
        } else
        if(f <= 2.0F)
        {
            f--;
            f1 = 1.0F * f;
            f2 = 1.0F - f * 0.5F;
            f3 = 1.0F - f;
            f4 = 0.0F;
        } else
        {
            f -= 2.0F;
            if(f > 1.0F)
                f = 1.0F;
            f1 = 1.0F - f;
            f2 = 0.5F - f * 0.5F;
            f3 = 0.0F;
            f4 = 0.0F;
        }
        if(_indx != 0)
        {
            f1 = f3;
            f2 = f4;
        }
        com.maddox.opengl.gl.Begin(6);
        com.maddox.opengl.gl.Color4f(f1, f1, f1, f2);
        com.maddox.opengl.gl.TexCoord2f(0.0F, 0.0F);
        Vertex2f(0.0F, 0.0F);
        com.maddox.opengl.gl.Color4f(f3, f3, f3, f4);
        for(int i = 0; i <= 16; i++)
        {
            com.maddox.opengl.gl.TexCoord2f(((com.maddox.JGP.Tuple2f) (tnts[i])).x, ((com.maddox.JGP.Tuple2f) (tnts[i])).y);
            Vertex2f(((com.maddox.JGP.Tuple2f) (pnts[i])).x, ((com.maddox.JGP.Tuple2f) (pnts[i])).y);
        }

        com.maddox.opengl.gl.End();
        com.maddox.opengl.gl.BlendFunc(770, 771);
    }

    private void renderMinus(float f)
    {
        f = com.maddox.il2.objects.effects.OverLoad.clamp(f, 0.0F, 1.0F);
        com.maddox.opengl.gl.Disable(3553);
        com.maddox.opengl.gl.BlendFunc(770, 771);
        com.maddox.opengl.gl.Begin(6);
        com.maddox.opengl.gl.Color4f(1.0F - f, 0.0F, 0.0F, f);
        Vertex2f(0.0F, 0.0F);
        for(int i = 0; i <= 16; i++)
            Vertex2f(((com.maddox.JGP.Tuple2f) (pnts[i])).x, ((com.maddox.JGP.Tuple2f) (pnts[i])).y);

        com.maddox.opengl.gl.End();
        com.maddox.opengl.gl.Enable(3553);
    }

    private void Vertex2f(float f, float f1)
    {
        com.maddox.opengl.gl.Vertex2f((f + 1.0F) * 0.5F, (f1 + 1.0F) * 0.5F);
    }

    int _indx;
    private int Tex[];
    private static final int N = 16;
    private static com.maddox.JGP.Point2f pnts[];
    private static com.maddox.JGP.Point2f tnts[];
    private static final float O_MIN = 0.2F;
    private static final float O_MAX = 0.7F;

    static 
    {
        pnts = new com.maddox.JGP.Point2f[17];
        tnts = new com.maddox.JGP.Point2f[17];
        for(int i = 0; i <= 16; i++)
        {
            pnts[i] = new Point2f();
            tnts[i] = new Point2f();
            double d = ((double)((float)i * 2.0F) * 3.1415926535897931D) / 16D;
            pnts[i].x = (float)java.lang.Math.cos(d) * 1.48F;
            pnts[i].y = (float)java.lang.Math.sin(d) * 1.48F;
            tnts[i].x = ((com.maddox.JGP.Tuple2f) (pnts[i])).x * 3.5F;
            tnts[i].y = ((com.maddox.JGP.Tuple2f) (pnts[i])).y * 3.5F;
        }

    }
}
