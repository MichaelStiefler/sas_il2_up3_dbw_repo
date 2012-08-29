package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GSize;
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

public class GUIWindowManager extends GWindowManager
  implements MsgKeyboardListener, MsgMouseListener, MsgJoyListener
{
  protected boolean bUseGMeshs;
  private int[] _startMousePos = new int[3];
  public _Render render;

  public boolean isUseGMeshs()
  {
    return this.bUseGMeshs;
  }
  public void setUseGMeshs(boolean paramBoolean) { if (this.bUseGMeshs == paramBoolean) return;
    this.bUseGMeshs = paramBoolean;
    this.render.useClearDepth(this.bUseGMeshs);
    ((GUICanvas)this.root.C).bClearZ = this.bUseGMeshs; }

  public void setTimeGameActive(boolean paramBoolean)
  {
    if (this.bTimeActive) {
      this.bTimeGameActive = paramBoolean;
    }
    else if (Time.isEnableChangePause())
      Time.setPause(!paramBoolean);
  }

  public void setKeyboardGameActive(boolean paramBoolean) {
    if (this.bKeyboardActive) this.bKeyboardGameActive = paramBoolean; else
      Keyboard.adapter().setEnable(paramBoolean); 
  }

  public void setMouseGameActive(boolean paramBoolean) {
    if (this.bMouseActive) this.bMouseGameActive = paramBoolean; else
      Mouse.adapter().setEnable(paramBoolean); 
  }

  public void setJoyGameActive(boolean paramBoolean) {
    if (this.bJoyActive) this.bJoyGameActive = paramBoolean; else
      Joy.adapter().setEnable(paramBoolean);
  }

  public void activateTime(boolean paramBoolean) {
    if (this.bTimeActive == paramBoolean) return;
    if (paramBoolean) {
      this.bTimeGameActive = (!Time.isPaused());
      RTSConf.cur.time.setEnableChangePause0(false);
    } else {
      RTSConf.cur.time.setEnableChangePause0(true);
      if (Time.isEnableChangePause())
        Time.setPause(!this.bTimeGameActive);
    }
    this.bTimeActive = paramBoolean;
  }

  public void activateKeyboard(boolean paramBoolean) {
    if (this.bKeyboardActive == paramBoolean) return;
    if (paramBoolean) {
      if (!Keyboard.adapter().isEnable()) {
        Keyboard.adapter().setEnable(true);
        this.bKeyboardGameActive = false;
      } else {
        this.bKeyboardGameActive = true;
      }
    }
    else Keyboard.adapter().setEnable(this.bKeyboardGameActive);

    this.bKeyboardActive = paramBoolean;
  }

  public void activateMouse(boolean paramBoolean) {
    if (this.bMouseActive == paramBoolean) return;
    if (paramBoolean) {
      if (!Mouse.adapter().isEnable()) {
        Mouse.adapter().setEnable(true);
        this.bMouseGameActive = false;
      } else {
        this.bMouseGameActive = true;
      }
      this.bMouseActive = true;
      Mouse.adapter().getPos(this._startMousePos);
      msgMouseAbsMove(this._startMousePos[0], this._startMousePos[1], this._startMousePos[2]);
    } else {
      Mouse.adapter().setEnable(this.bMouseGameActive);
      this.bMouseActive = false;
    }
  }

  public void activateJoy(boolean paramBoolean)
  {
    if (this.bJoyActive == paramBoolean) return;
    if (paramBoolean) {
      RTSConf.cur.joy.setFocus(this);
      if (!Joy.adapter().isEnable()) {
        Joy.adapter().setEnable(true);
        this.bJoyGameActive = false;
      } else {
        this.bJoyGameActive = true;
      }
    } else {
      RTSConf.cur.joy.setFocus(null);
      Joy.adapter().setEnable(this.bJoyGameActive);
    }

    this.bJoyActive = paramBoolean;
  }

  public void msgKeyboardKey(int paramInt, boolean paramBoolean) {
    doKeyboardKey(paramInt, paramBoolean);
  }

  public void msgKeyboardChar(char paramChar) {
    doKeyboardChar(paramChar);
  }

  public void msgMouseButton(int paramInt, boolean paramBoolean) {
    doMouseButton(paramInt, paramBoolean);
  }

  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3) {
    doMouseMove(paramInt1, -paramInt2, paramInt3);
  }
  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3) {
    doMouseAbsMove(paramInt1 - this.render.getViewPortX0(), (int)this.root.C.size.dy - paramInt2 - 1 + this.render.getViewPortY0(), paramInt3);
  }

  public void msgJoyButton(int paramInt1, int paramInt2, boolean paramBoolean) {
    doJoyButton(paramInt1, paramInt2, paramBoolean);
  }
  public void msgJoyMove(int paramInt1, int paramInt2, int paramInt3) {
    doJoyMove(paramInt1, paramInt2, paramInt3);
  }
  public void msgJoyPov(int paramInt1, int paramInt2) {
    doJoyPov(paramInt1, paramInt2);
  }
  public void msgJoyPoll() {
    doJoyPoll();
  }

  public GUIWindowManager(float paramFloat, GWindowRoot paramGWindowRoot, GWindowLookAndFeel paramGWindowLookAndFeel, String paramString)
  {
    GUITexture.loader = new GUITexture._Loader();
    GUIMesh.loader = new GUIMesh._Loader();
    GUIFont.loader = new GUIFont._Loader();

    CameraOrtho2D localCameraOrtho2D = new CameraOrtho2D();
    localCameraOrtho2D.set(-1000.0F, 1000.0F);
    localCameraOrtho2D.set(0.0F, RendersMain.getViewPortWidth(), 0.0F, RendersMain.getViewPortHeight());
    this.render = new _Render(paramFloat);
    this.render.setCamera(localCameraOrtho2D);
    this.render.setShow(true);
    this.render.setName(paramString);

    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.render.setLightEnv(localLightEnvXY);

    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(1.0F, -2.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);

    MsgAddListener.post(64, Mouse.adapter(), this, this);
    MsgAddListener.post(64, Joy.adapter(), this, this);
    MsgAddListener.post(64, Keyboard.adapter(), this, this);
    doCreate(new GUICanvas(this.render), paramGWindowRoot, paramGWindowLookAndFeel);
  }

  public class _Render extends Render
  {
    public long prevTime = Time.currentReal();

    protected void setCameraPos()
    {
      if (this.camera != null)
        this.camera.pos.setAbs(new Point3d(0.0D, 0.0D, 1000.0D), new Orient(0.0F, 90.0F, 0.0F)); 
    }

    protected void contextResize(int paramInt1, int paramInt2) {
      super.contextResize(paramInt1, paramInt2);
      GUIWindowManager.this.doCanvasResize(getViewPortWidth(), getViewPortHeight());
    }

    public void preRender() {
      long l = Time.currentReal();
      float f = (float)(l - this.prevTime) * 0.001F;
      this.prevTime = l;
      GUIWindowManager.this.doPreRender(f);
    }

    public void render()
    {
      GUIWindowManager.this.doRender();
    }
    public _Render(float arg2) {
      super();
      GUIWindowManager.this.bUseGMeshs = false;
      useClearDepth(false);
      setClearDepth(1.0E-006F);
      useClearColor(false);
    }
  }
}