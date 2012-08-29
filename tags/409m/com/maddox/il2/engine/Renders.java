// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Renders.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import java.util.TreeSet;

// Referenced classes of package com.maddox.il2.engine:
//            Render, RendersTicker, RendersComparator, Engine, 
//            TTFont, Config, Mat, RendersMain, 
//            RenderContext, Camera, LightEnv, ActorPos, 
//            GObj

public class Renders
    implements com.maddox.opengl.MsgGLContextListener
{

    public static com.maddox.il2.engine.Renders current()
    {
        return currentRenders;
    }

    public com.maddox.il2.engine.Render get(int i)
    {
        if(i < 0 || i >= renderSet.size())
            return null;
        else
            return (com.maddox.il2.engine.Render)renderArray[i];
    }

    public boolean isShow()
    {
        return bDraw;
    }

    public void setShow(boolean flag)
    {
        bDraw = flag;
    }

    public int frame()
    {
        return frame;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }

    public boolean isSaveAspect()
    {
        return bSaveAspect;
    }

    public void setSaveAspect(boolean flag)
    {
        if(bSaveAspect != flag)
        {
            bSaveAspect = flag;
            if(glContext != null)
                msgGLContext(4);
        }
    }

    public void getAspectViewPort(float af[])
    {
        af[0] = aspectView[0];
        af[1] = aspectView[1];
        af[2] = aspectView[2];
        af[3] = aspectView[3];
    }

    public void getAspectViewPort(int ai[])
    {
        ai[0] = (int)(aspectView[0] * (float)width() + 0.5F);
        ai[1] = (int)(aspectView[1] * (float)height() + 0.5F);
        ai[2] = (int)(aspectView[2] * (float)width() + 0.5F);
        ai[3] = (int)(aspectView[3] * (float)height() + 0.5F);
    }

    public int getAspectViewPortWidth()
    {
        return (int)(aspectView[2] * (float)width() + 0.5F);
    }

    public int getAspectViewPortHeight()
    {
        return (int)(aspectView[3] * (float)height() + 0.5F);
    }

    public void getViewPort(float af[])
    {
        if(bSaveAspect)
        {
            getAspectViewPort(af);
        } else
        {
            af[0] = 0.0F;
            af[1] = 0.0F;
            af[2] = 1.0F;
            af[3] = 1.0F;
        }
    }

    public void getViewPort(int ai[])
    {
        if(bSaveAspect)
        {
            getAspectViewPort(ai);
        } else
        {
            ai[0] = 0;
            ai[1] = 0;
            ai[2] = width();
            ai[3] = height();
        }
    }

    public int getViewPortWidth()
    {
        if(bSaveAspect)
            return (int)(aspectView[2] * (float)width() + 0.5F);
        else
            return width();
    }

    public int getViewPortHeight()
    {
        if(bSaveAspect)
            return (int)(aspectView[3] * (float)height() + 0.5F);
        else
            return height();
    }

    public void setRenderFocus(com.maddox.il2.engine.Render render)
    {
        renderFocus = render;
    }

    public com.maddox.il2.engine.Render getRenderFocus()
    {
        return renderFocus;
    }

    public com.maddox.opengl.GLContext glContext()
    {
        return glContext;
    }

    public void setGlContext(com.maddox.opengl.GLContext glcontext)
    {
        if(glContext == glcontext)
            return;
        if(glContext != null)
            glContext.msgRemoveListener(this, null);
        glContext = glcontext;
        if(glContext != null)
        {
            if(width != glContext.width() || height != glContext.height())
                msgGLContext(4);
            glContext.msgAddListener(this, null);
        }
    }

    public void msgGLContext(int i)
    {
        if(i == 1 || i == 4)
        {
            width = glContext.width();
            height = glContext.height();
            if((width * 3) / 4 == height)
            {
                aspectView[0] = 0.0F;
                aspectView[1] = 0.0F;
                aspectView[2] = 1.0F;
                aspectView[3] = 1.0F;
            } else
            if((width * 3) / 4 > height)
            {
                float f = (float)(((double)width - ((double)height * 4D) / 3D) / 2D / (double)width);
                aspectView[0] = f;
                aspectView[2] = 1.0F - 2.0F * f;
                aspectView[1] = 0.0F;
                aspectView[3] = 1.0F;
            } else
            {
                aspectView[0] = 0.0F;
                aspectView[2] = 1.0F;
                float f1 = (float)(((double)height - ((double)width * 3D) / 4D) / 2D / (double)height);
                aspectView[1] = f1;
                aspectView[3] = 1.0F - 2.0F * f1;
            }
            if(this == com.maddox.il2.engine.Engine.rendersMain())
            {
                if(bSaveAspect)
                    com.maddox.il2.engine.TTFont.setContextWidth((int)((float)width * aspectView[2] + 0.5F));
                else
                    com.maddox.il2.engine.TTFont.setContextWidth(width);
                com.maddox.il2.engine.TTFont.reloadAllOnResize();
                com.maddox.il2.engine.Config.cur.windowWidth = width;
                com.maddox.il2.engine.Config.cur.windowHeight = height;
            }
            int k = renderSet.size();
            for(int j = 0; j < k; j++)
            {
                com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)renderArray[j];
                render.contextResize(width, height);
            }

        } else
        if(i == 2 && com.maddox.il2.engine.Engine.rendersMain() == this)
            com.maddox.il2.engine.Mat.enableDeleteTextureID(false);
    }

    public boolean isTickPainting()
    {
        return ticker != null;
    }

    public void setTickPainting(boolean flag)
    {
        if(flag)
        {
            if(ticker != null)
                return;
            ticker = new RendersTicker(this);
        } else
        {
            if(ticker == null)
                return;
            ticker.destroy();
            ticker = null;
        }
    }

    public Renders()
    {
        bDraw = true;
        bSaveAspect = false;
        frame = 0;
        width = 1;
        height = 1;
        renderSet = new TreeSet(new RendersComparator());
        renderArray = new java.lang.Object[1];
        ticker = null;
        commonClearColor = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
        bCommonClearColor = false;
        maxFps = -1F;
    }

    public Renders(com.maddox.opengl.GLContext glcontext)
    {
        bDraw = true;
        bSaveAspect = false;
        frame = 0;
        width = 1;
        height = 1;
        renderSet = new TreeSet(new RendersComparator());
        renderArray = new java.lang.Object[1];
        ticker = null;
        commonClearColor = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
        bCommonClearColor = false;
        maxFps = -1F;
        setGlContext(glcontext);
    }

    public void setCommonClearColor(com.maddox.JGP.Color4f color4f)
    {
        commonClearColor.set(color4f);
    }

    public void setCommonClearColor(boolean flag)
    {
        bCommonClearColor = flag;
    }

    public void setMaxFps(float f)
    {
        maxFps = f;
        if(f > 0.0F)
        {
            stepTimePaint = (long)(1000D / (double)f);
            prevTimePaint = com.maddox.rts.Time.real() - stepTimePaint;
        }
    }

    public void paint()
    {
        if(!com.maddox.opengl.GLContext.isValid(glContext) || !bDraw)
            return;
        synchronized(com.maddox.opengl.GLContext.lockObject())
        {
            if(com.maddox.opengl.GLContext.makeCurrent(glContext))
                doPaint();
        }
    }

    private void doPaint()
    {
        com.maddox.JGP.Color4f color4f = null;
        boolean flag = false;
        float f = 1.0F;
        boolean flag1 = false;
        int i = 0x186a0;
        boolean flag2 = false;
        boolean flag3 = com.maddox.rts.Time.isPaused();
        currentRenders = this;
        if(this == com.maddox.il2.engine.Engine.rendersMain())
            com.maddox.il2.engine.RendersMain.bSwapBuffersResult = glContext.swapBuffers();
        int j1 = renderSet.size();
        int k1 = 0;
        int l1 = 0;
        if(renderFocus != null)
        {
            for(int j = 0; j < j1; j++)
            {
                if(renderFocus != (com.maddox.il2.engine.Render)renderArray[j])
                    continue;
                k1 = j;
                j1 = j + 1;
                break;
            }

        }
        bPreRendering = true;
        com.maddox.il2.engine.Renders.PrePreRenders();
        for(int k = k1; k < j1; k++)
        {
            currentRender = (com.maddox.il2.engine.Render)renderArray[k];
            if(currentRender == null)
                break;
            currentRender.bSolidArea = false;
            currentCamera = currentRender.getCamera();
            if(currentRender.isShow() && currentCamera != null)
            {
                l1++;
                if(color4f == null && currentRender.isClearColor())
                {
                    color4f = currentRender.getClearColor();
                    if(k < i)
                        i = k;
                }
                if(currentRender.bClearDepth)
                {
                    if(flag)
                    {
                        if(currentRender.getClearDepth() > f)
                            f = currentRender.getClearDepth();
                    } else
                    {
                        f = currentRender.getClearDepth();
                        flag = true;
                    }
                    if(k < i)
                        i = k;
                    if(currentRender.isClearColor() && currentRender.getClearDepth() == 0.999999F && currentRender.getClearColor().w == 1.0F)
                    {
                        currentRender.bSolidArea = true;
                        flag2 = true;
                    }
                }
                if(currentRender.bClearStencil && com.maddox.il2.engine.Config.cur.windowStencilBits != 0)
                {
                    flag1 = true;
                    if(k < i)
                        i = k;
                }
                if(com.maddox.il2.engine.RenderContext.bPreRenderEnable)
                {
                    currentRender.getViewPort(tmpI4);
                    if(currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]))
                    {
                        currentLightEnv = currentRender.getLightEnv();
                        if(currentLightEnv == null)
                            currentLightEnv = com.maddox.il2.engine.Engine.cur.lightEnv;
                        currentLightEnv.activate();
                        currentRender.preRender();
                    }
                }
            }
        }

        currentRender = null;
        currentCamera = null;
        bPreRendering = false;
        com.maddox.il2.engine.Renders.PostPreRenders();
        if(l1 == 0)
        {
            com.maddox.opengl.gl.ClearColor(0.0F, 0.0F, 0.0F, 1.0F);
            com.maddox.opengl.gl.Clear(16384);
        } else
        {
            if(color4f != null || flag || flag1)
            {
                int i2 = 0;
                if(color4f != null)
                {
                    i2 = 16384;
                    com.maddox.opengl.gl.ClearColor(color4f.x, color4f.y, color4f.z, color4f.w);
                }
                if(flag)
                {
                    i2 |= 0x100;
                    com.maddox.opengl.gl.ClearDepth(f);
                    com.maddox.opengl.gl.DepthMask(true);
                } else
                {
                    com.maddox.opengl.gl.DepthMask(false);
                }
                if(flag1)
                {
                    com.maddox.opengl.gl.ClearStencil(0);
                    i2 |= 0x400;
                }
                com.maddox.opengl.gl.Clear(i2);
            } else
            if(bCommonClearColor)
            {
                com.maddox.opengl.gl.ClearColor(commonClearColor.x, commonClearColor.y, commonClearColor.z, commonClearColor.w);
                com.maddox.opengl.gl.Clear(16384);
            }
            if(flag2)
            {
                for(int l = k1; l < j1; l++)
                {
                    currentRender = (com.maddox.il2.engine.Render)renderArray[l];
                    if(currentRender == null)
                        break;
                    if(l > i && currentRender.bSolidArea)
                        _fillSolidArea();
                }

            }
            bRendering = true;
            for(int i1 = k1; i1 < j1; i1++)
            {
                currentRender = (com.maddox.il2.engine.Render)renderArray[i1];
                if(currentRender == null)
                    break;
                currentCamera = currentRender.getCamera();
                if(currentRender.isShow() && currentCamera != null)
                {
                    if(i1 > i)
                        _clearViewPort();
                    currentRender.getViewPort(tmpI4);
                    if(currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]))
                    {
                        currentLightEnv = currentRender.getLightEnv();
                        if(currentLightEnv == null)
                            currentLightEnv = com.maddox.il2.engine.Engine.cur.lightEnv;
                        currentLightEnv.activate();
                        com.maddox.il2.engine.Render.prepareStates();
                        currentRender.render();
                        com.maddox.il2.engine.Render.flush();
                    }
                    if(flag3)
                        currentCamera.pos.updateCurrent();
                }
            }

            currentRender = null;
            currentCamera = null;
            currentLightEnv = null;
            bRendering = false;
        }
        com.maddox.il2.engine.Renders.PostRenders();
        frame++;
        currentRenders = null;
        if(com.maddox.il2.engine.Config.cur.isSoundUse() && this == com.maddox.il2.engine.Engine.rendersMain())
            com.maddox.sound.AudioDevice.flush();
    }

    public void paint(com.maddox.il2.engine.Render render)
    {
        if(!com.maddox.opengl.GLContext.isValid(glContext))
            return;
        synchronized(com.maddox.opengl.GLContext.lockObject())
        {
            if(com.maddox.opengl.GLContext.makeCurrent(glContext))
                doPaint(render);
        }
    }

    private void doPaint(com.maddox.il2.engine.Render render)
    {
        if(!com.maddox.opengl.GLContext.isValid(glContext))
            return;
        com.maddox.JGP.Color4f color4f = null;
        float f = 1.0F;
        currentRenders = this;
        bPreRendering = true;
        com.maddox.il2.engine.Renders.PrePreRenders();
        currentRender = render;
        currentRender.bSolidArea = true;
        currentCamera = currentRender.getCamera();
        color4f = currentRender.getClearColor();
        currentRender.getViewPort(tmpI4);
        currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]);
        currentRender.preRender();
        bPreRendering = false;
        com.maddox.il2.engine.Renders.PostPreRenders();
        com.maddox.opengl.gl.ClearColor(color4f.x, color4f.y, color4f.z, color4f.w);
        com.maddox.opengl.gl.ClearDepth(f);
        com.maddox.opengl.gl.Clear(16640);
        bRendering = true;
        currentLightEnv = currentRender.getLightEnv();
        if(currentLightEnv != null)
            currentLightEnv.activate();
        currentRender.render();
        com.maddox.il2.engine.Render.flush();
        currentRender = null;
        currentCamera = null;
        currentLightEnv = null;
        bRendering = false;
        com.maddox.il2.engine.Renders.PostRenders();
        frame++;
        glContext.swapBuffers();
        currentRenders = null;
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
            com.maddox.opengl.gl.Viewport(0, 0, width(), height());
            com.maddox.opengl.gl.MatrixMode(5889);
            com.maddox.opengl.gl.LoadIdentity();
            com.maddox.opengl.gl.Ortho(0.0D, width(), 0.0D, height(), 0.0D, 1.0D);
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
            currentRender.getViewPort(tmpI4);
            com.maddox.opengl.gl.Begin(7);
            com.maddox.opengl.gl.Vertex3f(tmpI4[0], tmpI4[1], f);
            com.maddox.opengl.gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1], f);
            com.maddox.opengl.gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1] + tmpI4[3], f);
            com.maddox.opengl.gl.Vertex3f(tmpI4[0], tmpI4[1] + tmpI4[3], f);
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

    private void _fillSolidArea()
    {
        com.maddox.il2.engine.Render.clearStates();
        com.maddox.opengl.gl.Disable(3553);
        boolean flag = (com.maddox.il2.engine.RenderContext.texGetFlags() & 0x20) != 0;
        if(flag)
            com.maddox.opengl.gl.PolygonMode(1032, 6914);
        com.maddox.opengl.gl.Viewport(0, 0, width(), height());
        com.maddox.opengl.gl.MatrixMode(5889);
        com.maddox.opengl.gl.LoadIdentity();
        com.maddox.opengl.gl.Ortho(0.0D, width(), 0.0D, height(), 0.0D, 1.0D);
        com.maddox.opengl.gl.MatrixMode(5888);
        com.maddox.opengl.gl.LoadIdentity();
        com.maddox.JGP.Color4f color4f = currentRender.getClearColor();
        com.maddox.opengl.gl.Color4f(color4f.x, color4f.y, color4f.z, color4f.w);
        com.maddox.opengl.gl.DepthMask(true);
        if(currentRender.bClearStencil && com.maddox.il2.engine.Config.cur.windowStencilBits != 0)
        {
            com.maddox.opengl.gl.Enable(2960);
            com.maddox.opengl.gl.StencilFunc(519, 0, -1);
            com.maddox.opengl.gl.StencilOp(0, 0, 0);
        }
        currentRender.getViewPort(tmpI4);
        com.maddox.opengl.gl.Begin(7);
        com.maddox.opengl.gl.Vertex3f(tmpI4[0], tmpI4[1], -1E-006F);
        com.maddox.opengl.gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1], -1E-006F);
        com.maddox.opengl.gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1] + tmpI4[3], -1E-006F);
        com.maddox.opengl.gl.Vertex3f(tmpI4[0], tmpI4[1] + tmpI4[3], -1E-006F);
        com.maddox.opengl.gl.End();
        if(currentRender.bClearStencil && com.maddox.il2.engine.Config.cur.windowStencilBits != 0)
        {
            com.maddox.opengl.gl.StencilOp(7680, 7680, 7680);
            com.maddox.opengl.gl.Disable(2960);
        }
        if(flag)
            com.maddox.opengl.gl.PolygonMode(1032, 6913);
    }

    private static native void PrePreRenders();

    private static native void PostPreRenders();

    private static native void PostRenders();

    private boolean bDraw;
    private boolean bSaveAspect;
    protected float aspectView[] = {
        0.0F, 0.0F, 1.0F, 1.0F
    };
    private int frame;
    private int width;
    private int height;
    protected java.util.TreeSet renderSet;
    protected java.lang.Object renderArray[];
    protected com.maddox.il2.engine.Render renderFocus;
    private com.maddox.opengl.GLContext glContext;
    private com.maddox.il2.engine.RendersTicker ticker;
    private com.maddox.JGP.Color4f commonClearColor;
    private boolean bCommonClearColor;
    protected static boolean bPreRendering = false;
    protected static boolean bRendering = false;
    protected static com.maddox.il2.engine.Renders currentRenders = null;
    protected static com.maddox.il2.engine.Render currentRender = null;
    protected static com.maddox.il2.engine.Camera currentCamera = null;
    protected static com.maddox.il2.engine.LightEnv currentLightEnv = null;
    protected static int tmpI4[] = new int[4];
    protected float maxFps;
    protected long prevTimePaint;
    protected long stepTimePaint;

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
