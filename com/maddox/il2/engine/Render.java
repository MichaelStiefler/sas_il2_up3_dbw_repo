// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Render.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.gl;
import com.maddox.rts.State;
import com.maddox.rts.States;
import java.util.List;
import java.util.TreeSet;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Camera3D, CameraOrtho2D, Renders, 
//            ActorDraw, MeshShared, RenderContext, LightEnv, 
//            Sun, Engine, Mat, GObj, 
//            Camera

public class Render extends com.maddox.il2.engine.Actor
{

    public static com.maddox.il2.engine.Render current()
    {
        return com.maddox.il2.engine.Renders.currentRender;
    }

    public static com.maddox.il2.engine.Camera currentCamera()
    {
        return com.maddox.il2.engine.Renders.currentCamera;
    }

    public static com.maddox.il2.engine.LightEnv currentLightEnv()
    {
        return com.maddox.il2.engine.Renders.currentLightEnv;
    }

    public static boolean isPreRendering()
    {
        return com.maddox.il2.engine.Renders.bPreRendering;
    }

    public static boolean isRendering()
    {
        return com.maddox.il2.engine.Renders.bRendering;
    }

    public boolean isShow()
    {
        return state() == 0;
    }

    public void setShow(boolean flag)
    {
        states.setState(flag ? 0 : 1);
    }

    public float getZOrder()
    {
        return zOrder;
    }

    public void setZOrder(float f)
    {
        if(zOrder != f)
        {
            renders.renderSet.remove(this);
            zOrder = f;
            renders.renderSet.add(this);
            renders.renderArray = renders.renderSet.toArray(renders.renderArray);
        }
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
            float af[] = {
                0.0F, 0.0F, 1.0F, 1.0F
            };
            if(bSaveAspect)
                getAspectViewPort(af);
            setViewPort(af);
            contextResize(contextWidth(), contextHeight());
        }
    }

    protected void clampViewPort(int ai[])
    {
    }

    public void getViewPort(float af[])
    {
        af[0] = viewPort[0];
        af[1] = viewPort[1];
        af[2] = viewPort[2];
        af[3] = viewPort[3];
    }

    public void getViewPort(int ai[])
    {
        ai[0] = (int)(viewPort[0] * (float)renders.width() + 0.5F);
        ai[1] = (int)(viewPort[1] * (float)renders.height() + 0.5F);
        ai[2] = (int)(viewPort[2] * (float)renders.width() + 0.5F);
        ai[3] = (int)(viewPort[3] * (float)renders.height() + 0.5F);
        clampViewPort(ai);
    }

    public int getViewPortX0()
    {
        return (int)(viewPort[0] * (float)renders.width() + 0.5F);
    }

    public int getViewPortY0()
    {
        return (int)(viewPort[1] * (float)renders.height() + 0.5F);
    }

    public int getViewPortWidth()
    {
        return (int)(viewPort[2] * (float)renders.width() + 0.5F);
    }

    public int getViewPortHeight()
    {
        return (int)(viewPort[3] * (float)renders.height() + 0.5F);
    }

    public void setViewPort(float af[])
    {
        viewPort[0] = af[0];
        viewPort[1] = af[1];
        viewPort[2] = af[2];
        viewPort[3] = af[3];
        getViewPort(tmpI4);
        setViewPort(tmpI4);
    }

    public void setViewPort(int ai[])
    {
        tmpI4[0] = ai[0];
        tmpI4[1] = ai[1];
        tmpI4[2] = ai[2];
        tmpI4[3] = ai[3];
        clampViewPort(tmpI4);
        viewPort[0] = (float)tmpI4[0] / (float)renders.width();
        viewPort[1] = (float)tmpI4[1] / (float)renders.height();
        viewPort[2] = (float)tmpI4[2] / (float)renders.width();
        viewPort[3] = (float)tmpI4[3] / (float)renders.height();
        if(camera != null && (camera instanceof com.maddox.il2.engine.Camera3D))
            ((com.maddox.il2.engine.Camera3D)camera).setViewPortWidth(tmpI4[2]);
    }

    public void getAspectViewPort(float af[])
    {
        renders.getAspectViewPort(af);
    }

    public void getAspectViewPort(int ai[])
    {
        renders.getAspectViewPort(ai);
    }

    public com.maddox.il2.engine.Camera getCamera()
    {
        return camera;
    }

    public void setCamera(com.maddox.il2.engine.Camera camera1)
    {
        camera = camera1;
        if(camera != null && (camera instanceof com.maddox.il2.engine.Camera3D))
            ((com.maddox.il2.engine.Camera3D)camera).setViewPortWidth((int)(viewPort[2] * (float)renders.width() + 0.5F));
    }

    public com.maddox.il2.engine.LightEnv getLightEnv()
    {
        return lightEnv;
    }

    public void setLightEnv(com.maddox.il2.engine.LightEnv lightenv)
    {
        lightEnv = lightenv;
    }

    public boolean isClearColor()
    {
        return bClearColor;
    }

    public void useClearColor(boolean flag)
    {
        bClearColor = flag;
    }

    public com.maddox.JGP.Color4f getClearColor()
    {
        return clearColor;
    }

    public void setClearColor(com.maddox.JGP.Color4f color4f)
    {
        clearColor.set(color4f);
        clearColor.clamp(0.0F, 1.0F);
    }

    public boolean isClearDepth()
    {
        return bClearDepth;
    }

    public void useClearDepth(boolean flag)
    {
        bClearDepth = flag;
    }

    public float getClearDepth()
    {
        return clearDepth;
    }

    public void setClearDepth(float f)
    {
        clearDepth = f;
        if(clearDepth <= 1E-006F)
            clearDepth = 1E-006F;
        if(clearDepth >= 0.999999F)
            clearDepth = 0.999999F;
    }

    public boolean isClearStencil()
    {
        return bClearStencil;
    }

    public void useClearStencil(boolean flag)
    {
        bClearStencil = flag;
    }

    protected void preRender()
    {
    }

    protected void render()
    {
    }

    public int contextWidth()
    {
        return renders.width();
    }

    public int contextHeight()
    {
        return renders.height();
    }

    public void contextResized()
    {
        contextResize(contextWidth(), contextHeight());
    }

    protected void contextResize(int i, int j)
    {
        if(isSaveAspect())
        {
            getAspectViewPort(tmpF4);
            setViewPort(tmpF4);
            if(camera != null && (camera instanceof com.maddox.il2.engine.CameraOrtho2D))
            {
                getAspectViewPort(tmpI4);
                ((com.maddox.il2.engine.CameraOrtho2D)camera).set(0.0F, tmpI4[2], 0.0F, tmpI4[3]);
            }
        } else
        {
            viewPort[0] = 0.0F;
            viewPort[1] = 0.0F;
            viewPort[2] = 1.0F;
            viewPort[3] = 1.0F;
            getViewPort(tmpI4);
            setViewPort(tmpI4);
            if(camera != null)
                if(camera instanceof com.maddox.il2.engine.CameraOrtho2D)
                    ((com.maddox.il2.engine.CameraOrtho2D)camera).set(0.0F, i, 0.0F, j);
                else
                if(camera instanceof com.maddox.il2.engine.Camera3D)
                    ((com.maddox.il2.engine.Camera3D)camera).set(((com.maddox.il2.engine.Camera3D)camera).FOV(), (float)i / (float)j);
        }
        if(camera != null && (camera instanceof com.maddox.il2.engine.Camera3D))
            ((com.maddox.il2.engine.Camera3D)camera).setViewPortWidth((int)(viewPort[2] * (float)i + 0.5F));
    }

    private void draw(java.util.List list, boolean flag)
    {
        if(list == null || list.size() == 0)
            return;
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            actor.draw.render(actor);
        }

        if(flag)
            list.clear();
        com.maddox.il2.engine.MeshShared.renderArray(false);
    }

    private void drawShadowProjective(java.util.List list, boolean flag)
    {
        if(list == null || list.size() == 0)
            return;
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            actor.draw.renderShadowProjective(actor);
        }

        if(flag)
            list.clear();
        com.maddox.il2.engine.MeshShared.renderArrayShadowProjective();
    }

    private void drawShadowVolume(java.util.List list, boolean flag)
    {
        if(list == null || list.size() == 0)
            return;
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            actor.draw.renderShadowVolume(actor);
        }

        if(flag)
            list.clear();
        com.maddox.il2.engine.MeshShared.renderArrayShadowVolume();
    }

    private void drawShadowVolumeHQ(java.util.List list, boolean flag)
    {
        if(list == null || list.size() == 0)
            return;
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            actor.draw.renderShadowVolumeHQ(actor);
        }

        if(flag)
            list.clear();
        com.maddox.il2.engine.MeshShared.renderArrayShadowVolumeHQ();
    }

    public void drawShadow(java.util.List list)
    {
        if(list == null)
            return;
        if(!com.maddox.il2.engine.RenderContext.bRenderEnable || com.maddox.il2.engine.RenderContext.shadowGet() == 0)
        {
            list.clear();
            return;
        } else
        {
            drawShadowProjective(list, true);
            return;
        }
    }

    public void draw(java.util.List list, java.util.List list1)
    {
        if(!com.maddox.il2.engine.RenderContext.bRenderEnable)
        {
            if(list != null)
                list.clear();
            if(list1 != null)
                list1.clear();
            return;
        } else
        {
            draw(list, true);
            draw(list1, true);
            return;
        }
    }

    public void draw(java.util.List list, java.util.List list1, java.util.List list2)
    {
        if(!com.maddox.il2.engine.RenderContext.bRenderEnable)
        {
            if(list != null)
                list.clear();
            if(list1 != null)
                list1.clear();
            if(list2 != null)
                list2.clear();
            return;
        }
        switch(com.maddox.il2.engine.RenderContext.shadowGet())
        {
        case 1: // '\001'
        case 2: // '\002'
            drawShadowProjective(list2, true);
            // fall through

        case 0: // '\0'
            draw(list, true);
            draw(list1, true);
            break;

        case 3: // '\003'
            draw(list, true);
            draw(list1, true);
            drawShadowVolume(list2, true);
            break;

        case 4: // '\004'
            draw(list, false);
            draw(list1, false);
            drawShadowVolumeHQ(list2, true);
            com.maddox.il2.engine.Sun sun = com.maddox.il2.engine.Render.currentLightEnv().sun();
            float f = sun.Diffuze;
            sun.Diffuze = 0.0F;
            float f1 = sun.Specular;
            sun.Specular = 0.0F;
            sun.activate();
            com.maddox.opengl.gl.Enable(2960);
            com.maddox.opengl.gl.StencilFunc(517, 0, -1);
            com.maddox.opengl.gl.StencilOp(7680, 7680, 7680);
            draw(list, true);
            draw(list1, true);
            sun.Diffuze = f;
            sun.Specular = f1;
            sun.activate();
            break;
        }
    }

    public void destroy()
    {
        renders.renderSet.remove(this);
        renders.renderArray = renders.renderSet.toArray(renders.renderArray);
        super.destroy();
    }

    public com.maddox.il2.engine.Renders renders()
    {
        return renders;
    }

    public Render(float f)
    {
        this(com.maddox.il2.engine.Engine.rendersMain(), f);
    }

    public Render(com.maddox.il2.engine.Renders renders1, float f)
    {
        camera = null;
        lightEnv = null;
        bClearColor = true;
        clearColor = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
        bClearDepth = true;
        clearDepth = 0.999999F;
        bClearStencil = false;
        bSolidArea = false;
        flags |= 0x6000;
        states = new States(new java.lang.Object[] {
            new State(this), new State(this)
        });
        states.setState(0);
        zOrder = f;
        renders = renders1;
        renders1.renderSet.add(this);
        renders1.renderArray = renders1.renderSet.toArray(renders1.renderArray);
        bSaveAspect = renders1.isSaveAspect();
        if(isSaveAspect())
            getAspectViewPort(viewPort);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public static native void flush();

    public static native void shadows();

    public static native void enableFog(boolean flag);

    public static native void transform3f(float af[], int i);

    public static native void transformVirt3f(float af[], int i);

    public static native void transform3fInv(float af[], int i);

    public static native void vertexArraysTransformAndLock(float af[], int i);

    public static native void vertexArraysUnLock();

    public static boolean drawSetMaterial(com.maddox.il2.engine.Mat mat, float f, float f1, float f2, float f3)
    {
        return com.maddox.il2.engine.Render.DrawSetMaterial(mat.cppObject(), f, f1, f2, f3);
    }

    public static boolean drawSetMaterial(com.maddox.il2.engine.Mat mat)
    {
        return com.maddox.il2.engine.Render.DrawSetMaterial0(mat.cppObject());
    }

    public static native void drawBeginSprites(int i);

    public static native void drawPushSprite(float f, float f1, float f2, float f3, int i, float f4);

    public static native void drawPushSprite(float f, float f1, float f2, float f3, int i, float f4, float f5);

    public static native void drawPushSprite(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7, 
            float f8);

    public static native void prepareStates();

    public static native void clearStates();

    public static native void drawBeginTriangleLists(int i);

    public static native void drawTriangleList(float af[], int ai[], int i, int ai1[], int j);

    public static native void drawTriangleList(float af[], int i, int j, int k, int ai[], int l);

    public static native void drawEnd();

    public static native void drawBeginLines(int i);

    public static native void drawLines(float af[], int i, float f, int j, int k, int l);

    private static native boolean DrawSetMaterial(int i, float f, float f1, float f2, float f3);

    private static native boolean DrawSetMaterial0(int i);

    public static void drawTile(float f, float f1, float f2, float f3, float f4, com.maddox.il2.engine.Mat mat, int i, float f5, 
            float f6, float f7, float f8)
    {
        com.maddox.il2.engine.Render.DrawTile(f, f1, f2, f3, f4, mat.cppObject(), i, f5, f6, f7, f8);
    }

    public static native void DrawTile(float f, float f1, float f2, float f3, float f4, int i, int j, float f5, 
            float f6, float f7, float f8);

    public static final int STATE_SHOW = 0;
    public static final int STATE_HIDDEN = 1;
    protected float zOrder;
    protected boolean bSaveAspect;
    public static final int VIEW_PORT_X0 = 0;
    public static final int VIEW_PORT_Y0 = 1;
    public static final int VIEW_PORT_DX = 2;
    public static final int VIEW_PORT_DY = 3;
    protected float viewPort[] = {
        0.0F, 0.0F, 1.0F, 1.0F
    };
    protected com.maddox.il2.engine.Camera camera;
    protected com.maddox.il2.engine.LightEnv lightEnv;
    public static final float CLEAR_DEPTH_MIN = 1E-006F;
    public static final float CLEAR_DEPTH_MAX = 0.999999F;
    protected boolean bClearColor;
    protected com.maddox.JGP.Color4f clearColor;
    protected boolean bClearDepth;
    protected float clearDepth;
    protected boolean bClearStencil;
    protected boolean bSolidArea;
    private com.maddox.il2.engine.Renders renders;
    private static int tmpI4[] = new int[4];
    private static float tmpF4[] = new float[4];
    public static final int DRAW_MODE_CAMERA = -1;
    public static final int DRAW_MODE_WORLD_REAL = 0;
    public static final int DRAW_MODE_WORLD_VIRT = 1;
    public static final int LineFlags;
    public static final int lmAntialise = 1;
    public static final int lmDrawStrip = 2;
    public static final int lmDrawLoop = 4;

    static 
    {
        LineFlags = com.maddox.il2.engine.Mat.BLEND | com.maddox.il2.engine.Mat.TESTZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE;
        com.maddox.il2.engine.GObj.loadNative();
    }
}
