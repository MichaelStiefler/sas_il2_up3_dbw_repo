// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIWindowManager.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowManager;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.rts.Joy;
import com.maddox.rts.Keyboard;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgJoyListener;
import com.maddox.rts.MsgKeyboardListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            GUICanvas, CameraOrtho2D, LightEnvXY, GUITexture, 
//            GUIMesh, GUIFont, RendersMain, LightEnv, 
//            Sun, Render, Orient, Camera, 
//            ActorPos

public class GUIWindowManager extends com.maddox.gwindow.GWindowManager
    implements com.maddox.rts.MsgKeyboardListener, com.maddox.rts.MsgMouseListener, com.maddox.rts.MsgJoyListener
{
    public class _Render extends com.maddox.il2.engine.Render
    {

        protected void setCameraPos()
        {
            if(camera != null)
                camera.pos.setAbs(new Point3d(0.0D, 0.0D, 1000D), new Orient(0.0F, 90F, 0.0F));
        }

        protected void contextResize(int i, int j)
        {
            super.contextResize(i, j);
            doCanvasResize(getViewPortWidth(), getViewPortHeight());
        }

        public void preRender()
        {
            long l = com.maddox.rts.Time.currentReal();
            float f = (float)(l - prevTime) * 0.001F;
            prevTime = l;
            doPreRender(f);
        }

        public void render()
        {
            doRender();
        }

        public long prevTime;

        public _Render(float f)
        {
            super(f);
            prevTime = com.maddox.rts.Time.currentReal();
            bUseGMeshs = false;
            useClearDepth(false);
            setClearDepth(1E-006F);
            useClearColor(false);
        }
    }


    public boolean isUseGMeshs()
    {
        return bUseGMeshs;
    }

    public void setUseGMeshs(boolean flag)
    {
        if(bUseGMeshs == flag)
        {
            return;
        } else
        {
            bUseGMeshs = flag;
            render.useClearDepth(bUseGMeshs);
            ((com.maddox.il2.engine.GUICanvas)root.C).bClearZ = bUseGMeshs;
            return;
        }
    }

    public void setTimeGameActive(boolean flag)
    {
        if(bTimeActive)
            bTimeGameActive = flag;
        else
        if(com.maddox.rts.Time.isEnableChangePause())
            com.maddox.rts.Time.setPause(!flag);
    }

    public void setKeyboardGameActive(boolean flag)
    {
        if(bKeyboardActive)
            bKeyboardGameActive = flag;
        else
            com.maddox.rts.Keyboard.adapter().setEnable(flag);
    }

    public void setMouseGameActive(boolean flag)
    {
        if(bMouseActive)
            bMouseGameActive = flag;
        else
            com.maddox.rts.Mouse.adapter().setEnable(flag);
    }

    public void setJoyGameActive(boolean flag)
    {
        if(bJoyActive)
            bJoyGameActive = flag;
        else
            com.maddox.rts.Joy.adapter().setEnable(flag);
    }

    public void activateTime(boolean flag)
    {
        if(bTimeActive == flag)
            return;
        if(flag)
        {
            bTimeGameActive = !com.maddox.rts.Time.isPaused();
            com.maddox.rts.RTSConf.cur.time.setEnableChangePause0(false);
        } else
        {
            com.maddox.rts.RTSConf.cur.time.setEnableChangePause0(true);
            if(com.maddox.rts.Time.isEnableChangePause())
                com.maddox.rts.Time.setPause(!bTimeGameActive);
        }
        bTimeActive = flag;
    }

    public void activateKeyboard(boolean flag)
    {
        if(bKeyboardActive == flag)
            return;
        if(flag)
        {
            if(!com.maddox.rts.Keyboard.adapter().isEnable())
            {
                com.maddox.rts.Keyboard.adapter().setEnable(true);
                bKeyboardGameActive = false;
            } else
            {
                bKeyboardGameActive = true;
            }
        } else
        {
            com.maddox.rts.Keyboard.adapter().setEnable(bKeyboardGameActive);
        }
        bKeyboardActive = flag;
    }

    public void activateMouse(boolean flag)
    {
        if(bMouseActive == flag)
            return;
        if(flag)
        {
            if(!com.maddox.rts.Mouse.adapter().isEnable())
            {
                com.maddox.rts.Mouse.adapter().setEnable(true);
                bMouseGameActive = false;
            } else
            {
                bMouseGameActive = true;
            }
            bMouseActive = true;
            com.maddox.rts.Mouse.adapter().getPos(_startMousePos);
            msgMouseAbsMove(_startMousePos[0], _startMousePos[1], _startMousePos[2]);
        } else
        {
            com.maddox.rts.Mouse.adapter().setEnable(bMouseGameActive);
            bMouseActive = false;
        }
    }

    public void activateJoy(boolean flag)
    {
        if(bJoyActive == flag)
            return;
        if(flag)
        {
            com.maddox.rts.RTSConf.cur.joy.setFocus(this);
            if(!com.maddox.rts.Joy.adapter().isEnable())
            {
                com.maddox.rts.Joy.adapter().setEnable(true);
                bJoyGameActive = false;
            } else
            {
                bJoyGameActive = true;
            }
        } else
        {
            com.maddox.rts.RTSConf.cur.joy.setFocus(null);
            com.maddox.rts.Joy.adapter().setEnable(bJoyGameActive);
        }
        bJoyActive = flag;
    }

    public void msgKeyboardKey(int i, boolean flag)
    {
        doKeyboardKey(i, flag);
    }

    public void msgKeyboardChar(char c)
    {
        doKeyboardChar(c);
    }

    public void msgMouseButton(int i, boolean flag)
    {
        doMouseButton(i, flag);
    }

    public void msgMouseMove(int i, int j, int k)
    {
        doMouseMove(i, -j, k);
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
        doMouseAbsMove(i - render.getViewPortX0(), ((int)root.C.size.dy - j - 1) + render.getViewPortY0(), k);
    }

    public void msgJoyButton(int i, int j, boolean flag)
    {
        doJoyButton(i, j, flag);
    }

    public void msgJoyMove(int i, int j, int k)
    {
        doJoyMove(i, j, k);
    }

    public void msgJoyPov(int i, int j)
    {
        doJoyPov(i, j);
    }

    public void msgJoyPoll()
    {
        doJoyPoll();
    }

    public GUIWindowManager(float f, com.maddox.gwindow.GWindowRoot gwindowroot, com.maddox.gwindow.GWindowLookAndFeel gwindowlookandfeel, java.lang.String s)
    {
        _startMousePos = new int[3];
        com.maddox.il2.engine.GUITexture.loader = new GUITexture._Loader();
        com.maddox.il2.engine.GUIMesh.loader = new GUIMesh._Loader();
        com.maddox.il2.engine.GUIFont.loader = new GUIFont._Loader();
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = new CameraOrtho2D();
        cameraortho2d.set(-1000F, 1000F);
        cameraortho2d.set(0.0F, com.maddox.il2.engine.RendersMain.getViewPortWidth(), 0.0F, com.maddox.il2.engine.RendersMain.getViewPortHeight());
        render = new _Render(f);
        render.setCamera(cameraortho2d);
        render.setShow(true);
        render.setName(s);
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        render.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(1.0F, -2F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.Mouse.adapter(), this, this);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.Joy.adapter(), this, this);
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.Keyboard.adapter(), this, this);
        doCreate(new GUICanvas(render), gwindowroot, gwindowlookandfeel);
    }

    protected boolean bUseGMeshs;
    private int _startMousePos[];
    public com.maddox.il2.engine._Render render;
}
