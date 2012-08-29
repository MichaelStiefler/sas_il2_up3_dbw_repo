package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import java.util.TreeSet;

public class Renders
  implements MsgGLContextListener
{
  private boolean bDraw = true;
  private boolean bSaveAspect = false;
  protected float[] aspectView = { 0.0F, 0.0F, 1.0F, 1.0F };
  private int frame = 0;
  private int width = 1;
  private int height = 1;
  protected TreeSet renderSet = new TreeSet(new RendersComparator());
  protected Object[] renderArray = new Object[1];
  protected Render renderFocus;
  private GLContext glContext;
  private RendersTicker ticker = null;

  private Color4f commonClearColor = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
  private boolean bCommonClearColor = false;

  protected static boolean bPreRendering = false;
  protected static boolean bRendering = false;

  protected static Renders currentRenders = null;
  protected static Render currentRender = null;
  protected static Camera currentCamera = null;
  protected static LightEnv currentLightEnv = null;
  protected static int[] tmpI4 = new int[4];

  protected float maxFps = -1.0F;
  protected long prevTimePaint;
  protected long stepTimePaint;

  public static Renders current()
  {
    return currentRenders;
  }

  public Render get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.renderSet.size()))
      return null;
    return (Render)this.renderArray[paramInt];
  }

  public boolean isShow() {
    return this.bDraw;
  }
  public void setShow(boolean paramBoolean) {
    this.bDraw = paramBoolean;
  }
  public int frame() {
    return this.frame;
  }
  public int width() {
    return this.width;
  }
  public int height() {
    return this.height;
  }
  public boolean isSaveAspect() {
    return this.bSaveAspect;
  }

  public void setSaveAspect(boolean paramBoolean) {
    if (this.bSaveAspect != paramBoolean) {
      this.bSaveAspect = paramBoolean;
      if (this.glContext != null)
        msgGLContext(4);
    }
  }

  public void getAspectViewPort(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.aspectView[0];
    paramArrayOfFloat[1] = this.aspectView[1];
    paramArrayOfFloat[2] = this.aspectView[2];
    paramArrayOfFloat[3] = this.aspectView[3];
  }

  public void getAspectViewPort(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = (int)(this.aspectView[0] * width() + 0.5F);
    paramArrayOfInt[1] = (int)(this.aspectView[1] * height() + 0.5F);
    paramArrayOfInt[2] = (int)(this.aspectView[2] * width() + 0.5F);
    paramArrayOfInt[3] = (int)(this.aspectView[3] * height() + 0.5F);
  }

  public int getAspectViewPortWidth() {
    return (int)(this.aspectView[2] * width() + 0.5F);
  }
  public int getAspectViewPortHeight() {
    return (int)(this.aspectView[3] * height() + 0.5F);
  }

  public void getViewPort(float[] paramArrayOfFloat) {
    if (this.bSaveAspect) {
      getAspectViewPort(paramArrayOfFloat);
    } else {
      paramArrayOfFloat[0] = 0.0F; paramArrayOfFloat[1] = 0.0F;
      paramArrayOfFloat[2] = 1.0F; paramArrayOfFloat[3] = 1.0F;
    }
  }

  public void getViewPort(int[] paramArrayOfInt)
  {
    if (this.bSaveAspect) {
      getAspectViewPort(paramArrayOfInt);
    } else {
      paramArrayOfInt[0] = 0; paramArrayOfInt[1] = 0;
      paramArrayOfInt[2] = width(); paramArrayOfInt[3] = height();
    }
  }

  public int getViewPortWidth()
  {
    if (this.bSaveAspect) return (int)(this.aspectView[2] * width() + 0.5F);
    return width();
  }

  public int getViewPortHeight()
  {
    if (this.bSaveAspect) return (int)(this.aspectView[3] * height() + 0.5F);
    return height();
  }

  public void setRenderFocus(Render paramRender) {
    this.renderFocus = paramRender;
  }

  public Render getRenderFocus() {
    return this.renderFocus;
  }

  public GLContext glContext() {
    return this.glContext;
  }

  public void setGlContext(GLContext paramGLContext) {
    if (this.glContext == paramGLContext) return;
    if (this.glContext != null) {
      this.glContext.msgRemoveListener(this, null);
    }
    this.glContext = paramGLContext;
    if (this.glContext != null) {
      if ((this.width != this.glContext.width()) || (this.height != this.glContext.height()))
        msgGLContext(4);
      this.glContext.msgAddListener(this, null);
    }
  }

  public void msgGLContext(int paramInt) {
    if ((paramInt == 1) || (paramInt == 4))
    {
      this.width = this.glContext.width();
      this.height = this.glContext.height();

      if (this.width * 3 / 4 == this.height) {
        this.aspectView[0] = 0.0F;
        this.aspectView[1] = 0.0F;
        this.aspectView[2] = 1.0F;
        this.aspectView[3] = 1.0F;
      }
      else
      {
        float f;
        if (this.width * 3 / 4 > this.height) {
          f = (float)((this.width - this.height * 4.0D / 3.0D) / 2.0D / this.width);
          this.aspectView[0] = f;
          this.aspectView[2] = (1.0F - 2.0F * f);
          this.aspectView[1] = 0.0F;
          this.aspectView[3] = 1.0F;
        } else {
          this.aspectView[0] = 0.0F;
          this.aspectView[2] = 1.0F;
          f = (float)((this.height - this.width * 3.0D / 4.0D) / 2.0D / this.height);
          this.aspectView[1] = f;
          this.aspectView[3] = (1.0F - 2.0F * f);
        }
      }
      if (this == Engine.rendersMain()) {
        if (this.bSaveAspect) TTFont.setContextWidth((int)(this.width * this.aspectView[2] + 0.5F)); else
          TTFont.setContextWidth(this.width);
        TTFont.reloadAllOnResize();
        Config.cur.windowWidth = this.width;
        Config.cur.windowHeight = this.height;
      }
      int j = this.renderSet.size();
      for (int i = 0; i < j; i++) {
        Render localRender = (Render)this.renderArray[i];
        localRender.contextResize(this.width, this.height);
      }
    } else if ((paramInt == 2) && (Engine.rendersMain() == this)) {
      Mat.enableDeleteTextureID(false);
    }
  }

  public boolean isTickPainting()
  {
    return this.ticker != null;
  }

  public void setTickPainting(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (this.ticker != null) return;
      this.ticker = new RendersTicker(this);
    } else {
      if (this.ticker == null) return;
      this.ticker.destroy();
      this.ticker = null;
    }
  }

  public Renders() {
  }

  public Renders(GLContext paramGLContext) {
    setGlContext(paramGLContext);
  }

  public void setCommonClearColor(Color4f paramColor4f)
  {
    this.commonClearColor.set(paramColor4f); } 
  public void setCommonClearColor(boolean paramBoolean) { this.bCommonClearColor = paramBoolean;
  }

  public void setMaxFps(float paramFloat)
  {
    this.maxFps = paramFloat;
    if (paramFloat > 0.0F) {
      this.stepTimePaint = ()(1000.0D / paramFloat);
      this.prevTimePaint = (Time.real() - this.stepTimePaint);
    }
  }

  public void paint()
  {
    if ((!GLContext.isValid(this.glContext)) || (!this.bDraw))
      return;
    synchronized (GLContext.lockObject()) {
      if (GLContext.makeCurrent(this.glContext))
        doPaint();
    }
  }

  private void doPaint() {
    Color4f localColor4f = null;
    int i = 0;
    float f = 1.0F;
    int j = 0;
    int k = 100000;
    int m = 0;
    boolean bool = Time.isPaused();

    currentRenders = this;
    if (this == Engine.rendersMain()) {
      RendersMain.bSwapBuffersResult = this.glContext.swapBuffers();
    }

    int i1 = this.renderSet.size();
    int i2 = 0;
    int i3 = 0;

    if (this.renderFocus != null) {
      for (n = 0; n < i1; n++) {
        if (this.renderFocus == (Render)this.renderArray[n]) {
          i2 = n;
          i1 = n + 1;
          break;
        }
      }

    }

    bPreRendering = true;
    PrePreRenders();
    for (int n = i2; n < i1; n++) {
      currentRender = (Render)this.renderArray[n];
      if (currentRender == null)
        break;
      currentRender.bSolidArea = false;
      currentCamera = currentRender.getCamera();
      if ((currentRender.isShow()) && (currentCamera != null)) {
        i3++;

        if ((localColor4f == null) && (currentRender.isClearColor())) {
          localColor4f = currentRender.getClearColor();
          if (n < k) k = n;
        }

        if (currentRender.bClearDepth) {
          if (i != 0) {
            if (currentRender.getClearDepth() > f)
              f = currentRender.getClearDepth();
          } else {
            f = currentRender.getClearDepth();
            i = 1;
          }
          if (n < k) k = n;
          if ((currentRender.isClearColor()) && (currentRender.getClearDepth() == 0.999999F) && (currentRender.getClearColor().w == 1.0F))
          {
            currentRender.bSolidArea = true;
            m = 1;
          }
        }

        if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
          j = 1;
          if (n < k) k = n;
        }

        if (RenderContext.bPreRenderEnable) {
          currentRender.getViewPort(tmpI4);

          if (!currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]))
          {
            continue;
          }

          currentLightEnv = currentRender.getLightEnv();
          if (currentLightEnv == null) currentLightEnv = Engine.cur.lightEnv;
          currentLightEnv.activate();
          currentRender.preRender();
        }
      }
    }

    currentRender = null;
    currentCamera = null;
    bPreRendering = false;

    PostPreRenders();

    if (i3 == 0) {
      gl.ClearColor(0.0F, 0.0F, 0.0F, 1.0F);
      gl.Clear(16384);
    }
    else {
      if ((localColor4f != null) || (i != 0) || (j != 0)) {
        int i4 = 0;
        if (localColor4f != null) {
          i4 = 16384;
          gl.ClearColor(localColor4f.x, localColor4f.y, localColor4f.z, localColor4f.w);
        }
        if (i != 0) {
          i4 |= 256;
          gl.ClearDepth(f);
          gl.DepthMask(true);
        } else {
          gl.DepthMask(false);
        }
        if (j != 0) {
          gl.ClearStencil(0);
          i4 |= 1024;
        }
        gl.Clear(i4);
      } else if (this.bCommonClearColor) {
        gl.ClearColor(this.commonClearColor.x, this.commonClearColor.y, this.commonClearColor.z, this.commonClearColor.w);
        gl.Clear(16384);
      }

      if (m != 0) {
        for (n = i2; n < i1; n++) {
          currentRender = (Render)this.renderArray[n];
          if (currentRender == null)
            break;
          if ((n > k) && (currentRender.bSolidArea)) {
            _fillSolidArea();
          }
        }
      }

      bRendering = true;
      for (n = i2; n < i1; n++) {
        currentRender = (Render)this.renderArray[n];
        if (currentRender == null)
          break;
        currentCamera = currentRender.getCamera();
        if ((currentRender.isShow()) && (currentCamera != null)) {
          if (n > k) {
            _clearViewPort();
          }
          currentRender.getViewPort(tmpI4);

          if (currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]))
          {
            currentLightEnv = currentRender.getLightEnv();
            if (currentLightEnv == null) currentLightEnv = Engine.cur.lightEnv;
            currentLightEnv.activate();
            Render.prepareStates();
            currentRender.render();
            Render.flush();
          }

          if (bool)
            currentCamera.pos.updateCurrent();
        }
      }
      currentRender = null;
      currentCamera = null;
      currentLightEnv = null;
      bRendering = false;
    }
    PostRenders();
    this.frame += 1;

    currentRenders = null;

    if ((Config.cur.isSoundUse()) && 
      (this == Engine.rendersMain()))
      AudioDevice.flush();
  }

  public void paint(Render paramRender)
  {
    if (!GLContext.isValid(this.glContext))
      return;
    synchronized (GLContext.lockObject()) {
      if (GLContext.makeCurrent(this.glContext))
        doPaint(paramRender);
    }
  }

  private void doPaint(Render paramRender) {
    if (!GLContext.isValid(this.glContext))
      return;
    Color4f localColor4f = null;
    float f = 1.0F;

    currentRenders = this;

    bPreRendering = true;
    PrePreRenders();
    currentRender = paramRender;
    currentRender.bSolidArea = true;
    currentCamera = currentRender.getCamera();
    localColor4f = currentRender.getClearColor();

    currentRender.getViewPort(tmpI4);

    currentCamera.activate(1.0F, width(), height(), tmpI4[0], tmpI4[1], tmpI4[2], tmpI4[3]);

    currentRender.preRender();

    bPreRendering = false;

    PostPreRenders();

    gl.ClearColor(localColor4f.x, localColor4f.y, localColor4f.z, localColor4f.w);
    gl.ClearDepth(f);
    gl.Clear(16640);

    bRendering = true;

    currentLightEnv = currentRender.getLightEnv();
    if (currentLightEnv != null)
      currentLightEnv.activate();
    currentRender.render();
    Render.flush();

    currentRender = null;
    currentCamera = null;
    currentLightEnv = null;
    bRendering = false;

    PostRenders();
    this.frame += 1;
    this.glContext.swapBuffers();
    currentRenders = null;
  }

  private void _clearViewPort() {
    if ((currentRender.isClearColor()) || (currentRender.isClearDepth()) || (currentRender.isClearStencil()))
    {
      Render.clearStates();
      gl.Disable(3553);

      int i = (RenderContext.texGetFlags() & 0x20) != 0 ? 1 : 0;
      if (i != 0) {
        gl.PolygonMode(1032, 6914);
      }
      gl.Viewport(0, 0, width(), height());
      gl.MatrixMode(5889);
      gl.LoadIdentity();
      gl.Ortho(0.0D, width(), 0.0D, height(), 0.0D, 1.0D);
      gl.MatrixMode(5888);
      gl.LoadIdentity();

      if (currentRender.isClearColor()) {
        Color4f localColor4f = currentRender.getClearColor();
        gl.Color4f(localColor4f.x, localColor4f.y, localColor4f.z, localColor4f.w);
        if (localColor4f.w == 0.0F) {
          gl.Enable(3042);
          gl.BlendFunc(0, 1);
        } else if (localColor4f.w != 1.0F) {
          gl.Enable(3042);
          gl.BlendFunc(770, 771);
        }
      } else {
        gl.Color4f(0.0F, 0.0F, 0.0F, 0.0F);
        gl.Enable(3042);
        gl.BlendFunc(0, 1);
      }
      float f;
      if (currentRender.isClearDepth()) {
        f = -currentRender.getClearDepth();
        gl.DepthFunc(519);
        gl.Enable(2929);
        gl.DepthMask(true);
      } else {
        f = -1.0E-006F;
        gl.Disable(2929);
        gl.DepthMask(false);
      }

      if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
        gl.Enable(2960);
        gl.StencilFunc(519, 0, -1);
        gl.StencilOp(0, 0, 0);
      }

      currentRender.getViewPort(tmpI4);
      gl.Begin(7);
      gl.Vertex3f(tmpI4[0], tmpI4[1], f);
      gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1], f);
      gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1] + tmpI4[3], f);
      gl.Vertex3f(tmpI4[0], tmpI4[1] + tmpI4[3], f);
      gl.End();

      if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
        gl.StencilOp(7680, 7680, 7680);
        gl.Disable(2960);
      }

      if (i != 0)
        gl.PolygonMode(1032, 6913);
    }
  }

  private void _fillSolidArea() {
    Render.clearStates();
    gl.Disable(3553);

    int i = (RenderContext.texGetFlags() & 0x20) != 0 ? 1 : 0;
    if (i != 0) {
      gl.PolygonMode(1032, 6914);
    }
    gl.Viewport(0, 0, width(), height());
    gl.MatrixMode(5889);
    gl.LoadIdentity();
    gl.Ortho(0.0D, width(), 0.0D, height(), 0.0D, 1.0D);
    gl.MatrixMode(5888);
    gl.LoadIdentity();

    Color4f localColor4f = currentRender.getClearColor();
    gl.Color4f(localColor4f.x, localColor4f.y, localColor4f.z, localColor4f.w);

    gl.DepthMask(true);

    if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
      gl.Enable(2960);
      gl.StencilFunc(519, 0, -1);
      gl.StencilOp(0, 0, 0);
    }

    currentRender.getViewPort(tmpI4);
    gl.Begin(7);
    gl.Vertex3f(tmpI4[0], tmpI4[1], -1.0E-006F);
    gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1], -1.0E-006F);
    gl.Vertex3f(tmpI4[0] + tmpI4[2], tmpI4[1] + tmpI4[3], -1.0E-006F);
    gl.Vertex3f(tmpI4[0], tmpI4[1] + tmpI4[3], -1.0E-006F);
    gl.End();

    if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
      gl.StencilOp(7680, 7680, 7680);
      gl.Disable(2960);
    }
    if (i != 0)
      gl.PolygonMode(1032, 6913); 
  }
  private static native void PrePreRenders();

  private static native void PostPreRenders();

  private static native void PostRenders();

  static { GObj.loadNative();
  }
}