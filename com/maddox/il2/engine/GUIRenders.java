// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRenders.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.gl;
import com.maddox.rts.Time;
import java.util.TreeSet;

// Referenced classes of package com.maddox.il2.engine:
//            Render, Renders, RendersMain, Camera, 
//            LightEnv, RenderContext, Engine, ActorPos, 
//            Config

public class GUIRenders extends com.maddox.gwindow.GWindow
{
    public class WinRenders extends com.maddox.il2.engine.Renders
    {

        protected void pushRenders()
        {
            parentRender = currentRender;
            parentCamera = currentCamera;
            parentLightEnv = currentLightEnv;
            currentRenders = this;
        }

        protected void popRenders(boolean flag)
        {
            currentRender = parentRender;
            currentCamera = parentCamera;
            currentLightEnv = parentLightEnv;
            currentRenders = parentRender.renders();
            parentRender = null;
            currentCamera.activate(1.0F, com.maddox.il2.engine.RendersMain.width(), com.maddox.il2.engine.RendersMain.height(), rootViewX, rootViewY, rootViewDX, rootViewDY);
            if(!flag)
                currentLightEnv.activate();
        }

        protected void activateCamera()
        {
            currentCamera.activate(1.0F, com.maddox.il2.engine.RendersMain.width(), com.maddox.il2.engine.RendersMain.height(), wViewX, wViewY, wViewDX, wViewDY, wClipX, wClipY, wClipDX, wClipDY);
        }

        public void doPreRender()
        {
            pushRenders();
            int j = renderSet.size();
            for(int i = 0; i < j; i++)
            {
                currentRender = (com.maddox.il2.engine.Render)renderArray[i];
                if(currentRender == null)
                    break;
                currentCamera = currentRender.getCamera();
                if(!currentRender.isShow() || currentCamera == null || !com.maddox.il2.engine.RenderContext.bPreRenderEnable)
                    continue;
                computeClip();
                if(!bWClipped)
                {
                    activateCamera();
                    currentRender.preRender();
                }
            }

            popRenders(true);
        }

        public void doRender()
        {
            pushRenders();
            int j = renderSet.size();
            for(int i = 0; i < j; i++)
            {
                currentRender = (com.maddox.il2.engine.Render)renderArray[i];
                if(currentRender == null)
                    break;
                currentCamera = currentRender.getCamera();
                if(!currentRender.isShow() || currentCamera == null)
                    continue;
                computeClip();
                if(!bWClipped)
                {
                    _clearViewPort();
                    activateCamera();
                    currentLightEnv = currentRender.getLightEnv();
                    if(currentLightEnv == null)
                        currentLightEnv = com.maddox.il2.engine.Engine.cur.lightEnv;
                    currentLightEnv.activate();
                    com.maddox.il2.engine.Render.prepareStates();
                    currentRender.render();
                    com.maddox.il2.engine.Render.flush();
                }
                if(com.maddox.rts.Time.isPaused())
                    currentCamera.pos.updateCurrent();
            }

            popRenders(false);
        }

        public void resized()
        {
            if((width() * 3) / 4 == height())
            {
                aspectView[0] = 0.0F;
                aspectView[1] = 0.0F;
                aspectView[2] = 1.0F;
                aspectView[3] = 1.0F;
            } else
            if((width() * 3) / 4 > height())
            {
                float f = (float)(((double)width() - ((double)height() * 4D) / 3D) / 2D / (double)width());
                aspectView[0] = f;
                aspectView[2] = 1.0F - 2.0F * f;
                aspectView[1] = 0.0F;
                aspectView[3] = 1.0F;
            } else
            {
                aspectView[0] = 0.0F;
                aspectView[2] = 1.0F;
                float f1 = (float)(((double)height() - ((double)width() * 3D) / 4D) / 2D / (double)height());
                aspectView[1] = f1;
                aspectView[3] = 1.0F - 2.0F * f1;
            }
            int j = renderSet.size();
            for(int i = 0; i < j; i++)
            {
                com.maddox.il2.engine.Render render1 = (com.maddox.il2.engine.Render)renderArray[i];
                render1.contextResize(width(), height());
            }

        }

        private void _clearViewPort()
        {
            if(currentRender.isClearColor() || currentRender.isClearDepth() || currentRender.isClearStencil())
            {
                com.maddox.il2.engine.Render.clearStates();
                com.maddox.opengl.gl.Disable(3553);
                boolean flag = (com.maddox.il2.engine.RenderContext.texGetFlags() & 0x20) != 0;
                if(flag)
                    com.maddox.opengl.gl.PolygonMode(1032, 6914);
                com.maddox.opengl.gl.Viewport(0, 0, com.maddox.il2.engine.RendersMain.width(), com.maddox.il2.engine.RendersMain.height());
                com.maddox.opengl.gl.MatrixMode(5889);
                com.maddox.opengl.gl.LoadIdentity();
                com.maddox.opengl.gl.Ortho(0.0D, com.maddox.il2.engine.RendersMain.width(), 0.0D, com.maddox.il2.engine.RendersMain.height(), 0.0D, 1.0D);
                com.maddox.opengl.gl.MatrixMode(5888);
                com.maddox.opengl.gl.LoadIdentity();
                if(currentRender.isClearColor())
                {
                    com.maddox.JGP.Color4f color4f = currentRender.getClearColor();
                    com.maddox.opengl.gl.Color4f(color4f.x, color4f.y, color4f.z, color4f.w);
                    if(color4f.w == 0.0F)
                    {
                        com.maddox.opengl.gl.Enable(3042);
                        com.maddox.opengl.gl.BlendFunc(0, 1);
                    } else
                    if(color4f.w != 1.0F)
                    {
                        com.maddox.opengl.gl.Enable(3042);
                        com.maddox.opengl.gl.BlendFunc(770, 771);
                    }
                } else
                {
                    com.maddox.opengl.gl.Color4f(0.0F, 0.0F, 0.0F, 0.0F);
                    com.maddox.opengl.gl.Enable(3042);
                    com.maddox.opengl.gl.BlendFunc(0, 1);
                }
                float f;
                if(currentRender.isClearDepth())
                {
                    f = -currentRender.getClearDepth();
                    com.maddox.opengl.gl.DepthFunc(519);
                    com.maddox.opengl.gl.Enable(2929);
                    com.maddox.opengl.gl.DepthMask(true);
                } else
                {
                    f = -1E-006F;
                    com.maddox.opengl.gl.Disable(2929);
                    com.maddox.opengl.gl.DepthMask(false);
                }
                if(currentRender.bClearStencil && com.maddox.il2.engine.Config.cur.windowStencilBits != 0)
                {
                    com.maddox.opengl.gl.Enable(2960);
                    com.maddox.opengl.gl.StencilFunc(519, 0, -1);
                    com.maddox.opengl.gl.StencilOp(0, 0, 0);
                }
                com.maddox.opengl.gl.Begin(7);
                com.maddox.opengl.gl.Vertex3f(wClipX, wClipY, f);
                com.maddox.opengl.gl.Vertex3f(wClipX + wClipDX, wClipY, f);
                com.maddox.opengl.gl.Vertex3f(wClipX + wClipDX, wClipY + wClipDY, f);
                com.maddox.opengl.gl.Vertex3f(wClipX, wClipY + wClipDY, f);
                com.maddox.opengl.gl.End();
                if(currentRender.bClearStencil && com.maddox.il2.engine.Config.cur.windowStencilBits != 0)
                {
                    com.maddox.opengl.gl.StencilOp(7680, 7680, 7680);
                    com.maddox.opengl.gl.Disable(2960);
                }
                if(flag)
                    com.maddox.opengl.gl.PolygonMode(1032, 6913);
            }
        }

        public boolean isShow()
        {
            return isVisible();
        }

        public void setShow(boolean flag)
        {
            if(flag)
                showWindow();
            else
                hideWindow();
        }

        public int frame()
        {
            return com.maddox.il2.engine.RendersMain.frame();
        }

        public int width()
        {
            return (int)win.dx;
        }

        public int height()
        {
            return (int)win.dy;
        }

        public com.maddox.opengl.GLContext glContext()
        {
            return com.maddox.il2.engine.RendersMain.glContext();
        }

        public void setGlContext(com.maddox.opengl.GLContext glcontext)
        {
        }

        public void msgGLContext(int i)
        {
        }

        public boolean isTickPainting()
        {
            return com.maddox.il2.engine.RendersMain.isTickPainting();
        }

        public void setTickPainting(boolean flag)
        {
        }

        public void paint()
        {
        }

        public void paint(com.maddox.il2.engine.Render render1)
        {
        }

        public com.maddox.il2.engine.Render parentRender;
        public com.maddox.il2.engine.Camera parentCamera;
        public com.maddox.il2.engine.LightEnv parentLightEnv;

        public WinRenders()
        {
        }
    }


    protected void computeRendersClip()
    {
        com.maddox.il2.engine.Render.current().getViewPort(view);
        rootViewX = view[0];
        rootViewY = view[1];
        rootViewDX = view[2];
        rootViewDY = view[3];
        float f = root.C.org.x;
        float f1 = root.C.org.y;
        float f2 = win.dx;
        float f3 = win.dy;
        float f4 = f - root.C.clip.x;
        bRendersClipped = true;
        if(f4 < 0.0F)
        {
            f2 += f4;
            if(f2 <= 0.0F)
                return;
            f = root.C.clip.x;
            f4 = 0.0F;
        }
        f4 = (f2 + f4) - root.C.clip.dx;
        if(f4 > 0.0F)
        {
            f2 -= f4;
            if(f2 <= 0.0F)
                return;
        }
        f4 = f1 - root.C.clip.y;
        if(f4 < 0.0F)
        {
            f3 += f4;
            if(f3 <= 0.0F)
                return;
            f1 = root.C.clip.y;
            f4 = 0.0F;
        }
        f4 = (f3 + f4) - root.C.clip.dy;
        if(f4 > 0.0F)
        {
            f3 -= f4;
            if(f3 <= 0.0F)
                return;
        }
        bRendersClipped = false;
        curX = rootViewX + java.lang.Math.round(root.C.org.x);
        curY = (rootViewY + rootViewDY) - java.lang.Math.round(root.C.org.y) - java.lang.Math.round(win.dy);
        curDX = java.lang.Math.round(win.dx);
        curDY = java.lang.Math.round(win.dy);
        clipX = rootViewX + java.lang.Math.round(f);
        clipY = (rootViewY + rootViewDY) - java.lang.Math.round(f1) - java.lang.Math.round(f3);
        clipDX = java.lang.Math.round(f2);
        clipDY = java.lang.Math.round(f3);
    }

    protected void computeClip()
    {
        com.maddox.il2.engine.Render.current().getViewPort(view);
        wViewX = view[0] + curX;
        wViewY = view[1] + curY;
        wViewDX = view[2];
        wViewDY = view[3];
        int i = wViewX;
        int j = wViewY;
        int k = wViewDX;
        int l = wViewDY;
        int i1 = i - clipX;
        bWClipped = true;
        if(i1 < 0)
        {
            k += i1;
            if(k <= 0)
                return;
            i = clipX;
            i1 = 0;
        }
        i1 = (k + i1) - clipDX;
        if(i1 > 0)
        {
            k -= i1;
            if(k <= 0)
                return;
        }
        i1 = j - clipY;
        if((float)i1 < 0.0F)
        {
            l += i1;
            if(l <= 0)
                return;
            j = clipY;
            i1 = 0;
        }
        i1 = (l + i1) - clipDY;
        if(i1 > 0)
        {
            l -= i1;
            if(l <= 0)
                return;
        }
        bWClipped = false;
        wClipX = i;
        wClipY = j;
        wClipDX = k;
        wClipDY = l;
    }

    public void preRender()
    {
        computeRendersClip();
        if(bRendersClipped)
        {
            return;
        } else
        {
            renders.doPreRender();
            return;
        }
    }

    public void render()
    {
        if(bRendersClipped)
        {
            return;
        } else
        {
            renders.doRender();
            return;
        }
    }

    public void resized()
    {
        super.resized();
        renders.resized();
    }

    public GUIRenders(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, boolean flag)
    {
        super(gwindow, f, f1, f2, f3, flag);
        view = new int[4];
        renders = new WinRenders();
    }

    public GUIRenders(com.maddox.gwindow.GWindow gwindow)
    {
        super(gwindow);
        view = new int[4];
        renders = new WinRenders();
    }

    public GUIRenders()
    {
        view = new int[4];
        renders = new WinRenders();
    }

    public com.maddox.il2.engine.WinRenders renders;
    protected int rootViewX;
    protected int rootViewY;
    protected int rootViewDX;
    protected int rootViewDY;
    protected int curX;
    protected int curY;
    protected int curDX;
    protected int curDY;
    protected int clipX;
    protected int clipY;
    protected int clipDX;
    protected int clipDY;
    protected boolean bRendersClipped;
    private int view[];
    protected int wViewX;
    protected int wViewY;
    protected int wViewDX;
    protected int wViewDY;
    protected int wClipX;
    protected int wClipY;
    protected int wClipDX;
    protected int wClipDY;
    protected boolean bWClipped;
}
