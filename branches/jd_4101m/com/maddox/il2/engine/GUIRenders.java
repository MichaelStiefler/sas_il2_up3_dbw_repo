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

public class GUIRenders extends GWindow
{
  public WinRenders renders;
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
  private int[] view = new int[4];
  protected int wViewX;
  protected int wViewY;
  protected int wViewDX;
  protected int wViewDY;
  protected int wClipX;
  protected int wClipY;
  protected int wClipDX;
  protected int wClipDY;
  protected boolean bWClipped;

  protected void computeRendersClip()
  {
    Render.current().getViewPort(this.view);
    this.rootViewX = this.view[0];
    this.rootViewY = this.view[1];
    this.rootViewDX = this.view[2];
    this.rootViewDY = this.view[3];
    float f1 = this.root.C.org.x;
    float f2 = this.root.C.org.y;
    float f3 = this.win.dx;
    float f4 = this.win.dy;
    float f5 = f1 - this.root.C.clip.x;
    this.bRendersClipped = true;
    if (f5 < 0.0F) {
      f3 += f5; if (f3 <= 0.0F) return;
      f1 = this.root.C.clip.x;
      f5 = 0.0F;
    }
    f5 = f3 + f5 - this.root.C.clip.dx;
    if (f5 > 0.0F) {
      f3 -= f5; if (f3 <= 0.0F) return;
    }

    f5 = f2 - this.root.C.clip.y;
    if (f5 < 0.0F) {
      f4 += f5; if (f4 <= 0.0F) return;
      f2 = this.root.C.clip.y;
      f5 = 0.0F;
    }
    f5 = f4 + f5 - this.root.C.clip.dy;
    if (f5 > 0.0F) {
      f4 -= f5; if (f4 <= 0.0F) return;
    }
    this.bRendersClipped = false;
    this.curX = (this.rootViewX + Math.round(this.root.C.org.x));
    this.curY = (this.rootViewY + this.rootViewDY - Math.round(this.root.C.org.y) - Math.round(this.win.dy));
    this.curDX = Math.round(this.win.dx);
    this.curDY = Math.round(this.win.dy);
    this.clipX = (this.rootViewX + Math.round(f1));
    this.clipY = (this.rootViewY + this.rootViewDY - Math.round(f2) - Math.round(f4));
    this.clipDX = Math.round(f3);
    this.clipDY = Math.round(f4);
  }

  protected void computeClip() {
    Render.current().getViewPort(this.view);
    this.wViewX = (this.view[0] + this.curX);
    this.wViewY = (this.view[1] + this.curY);
    this.wViewDX = this.view[2];
    this.wViewDY = this.view[3];
    int i = this.wViewX;
    int j = this.wViewY;
    int k = this.wViewDX;
    int m = this.wViewDY;
    int n = i - this.clipX;
    this.bWClipped = true;
    if (n < 0) {
      k += n; if (k <= 0) return;
      i = this.clipX;
      n = 0;
    }
    n = k + n - this.clipDX;
    if (n > 0) {
      k -= n; if (k <= 0) return;
    }

    n = j - this.clipY;
    if (n < 0.0F) {
      m += n; if (m <= 0) return;
      j = this.clipY;
      n = 0;
    }
    n = m + n - this.clipDY;
    if (n > 0) {
      m -= n; if (m <= 0) return;
    }
    this.bWClipped = false;
    this.wClipX = i;
    this.wClipY = j;
    this.wClipDX = k;
    this.wClipDY = m;
  }

  public void preRender() {
    computeRendersClip();
    if (this.bRendersClipped) return;
    this.renders.doPreRender();
  }

  public void render() {
    if (this.bRendersClipped) return;
    this.renders.doRender();
  }

  public void resized() {
    super.resized();
    this.renders.resized();
  }

  public GUIRenders(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean)
  {
    super(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
    this.renders = new WinRenders();
  }
  public GUIRenders(GWindow paramGWindow) {
    super(paramGWindow);
    this.renders = new WinRenders();
  }
  public GUIRenders() {
    this.renders = new WinRenders();
  }

  public class WinRenders extends Renders
  {
    public Render parentRender;
    public Camera parentCamera;
    public LightEnv parentLightEnv;

    public WinRenders()
    {
    }

    protected void pushRenders()
    {
      this.parentRender = currentRender;
      this.parentCamera = currentCamera;
      this.parentLightEnv = currentLightEnv;
      currentRenders = this;
    }
    protected void popRenders(boolean paramBoolean) {
      currentRender = this.parentRender;
      currentCamera = this.parentCamera;
      currentLightEnv = this.parentLightEnv;
      currentRenders = this.parentRender.renders();
      this.parentRender = null;
      currentCamera.activate(1.0F, RendersMain.width(), RendersMain.height(), GUIRenders.this.rootViewX, GUIRenders.this.rootViewY, GUIRenders.this.rootViewDX, GUIRenders.this.rootViewDY);

      if (!paramBoolean)
        currentLightEnv.activate(); 
    }

    protected void activateCamera() {
      currentCamera.activate(1.0F, RendersMain.width(), RendersMain.height(), GUIRenders.this.wViewX, GUIRenders.this.wViewY, GUIRenders.this.wViewDX, GUIRenders.this.wViewDY, GUIRenders.this.wClipX, GUIRenders.this.wClipY, GUIRenders.this.wClipDX, GUIRenders.this.wClipDY);
    }

    public void doPreRender()
    {
      pushRenders();

      int j = this.renderSet.size();
      for (int i = 0; i < j; i++) {
        currentRender = (Render)this.renderArray[i];
        if (currentRender == null)
          break;
        currentCamera = currentRender.getCamera();
        if ((!currentRender.isShow()) || (currentCamera == null) || 
          (!RenderContext.bPreRenderEnable)) continue;
        GUIRenders.this.computeClip();
        if (!GUIRenders.this.bWClipped) {
          activateCamera();
          currentRender.preRender();
        }

      }

      popRenders(true);
    }

    public void doRender() {
      pushRenders();
      int j = this.renderSet.size();
      for (int i = 0; i < j; i++) {
        currentRender = (Render)this.renderArray[i];
        if (currentRender == null)
          break;
        currentCamera = currentRender.getCamera();
        if ((currentRender.isShow()) && (currentCamera != null)) {
          GUIRenders.this.computeClip();
          if (!GUIRenders.this.bWClipped) {
            _clearViewPort();

            activateCamera();
            currentLightEnv = currentRender.getLightEnv();
            if (currentLightEnv == null) currentLightEnv = Engine.cur.lightEnv;
            currentLightEnv.activate();
            Render.prepareStates();
            currentRender.render();
            Render.flush();
          }
          if (Time.isPaused())
            currentCamera.pos.updateCurrent();
        }
      }
      popRenders(false);
    }

    public void resized() {
      if (width() * 3 / 4 == height()) {
        this.aspectView[0] = 0.0F;
        this.aspectView[1] = 0.0F;
        this.aspectView[2] = 1.0F;
        this.aspectView[3] = 1.0F;
      }
      else
      {
        float f;
        if (width() * 3 / 4 > height()) {
          f = (float)((width() - height() * 4.0D / 3.0D) / 2.0D / width());
          this.aspectView[0] = f;
          this.aspectView[2] = (1.0F - 2.0F * f);
          this.aspectView[1] = 0.0F;
          this.aspectView[3] = 1.0F;
        } else {
          this.aspectView[0] = 0.0F;
          this.aspectView[2] = 1.0F;
          f = (float)((height() - width() * 3.0D / 4.0D) / 2.0D / height());
          this.aspectView[1] = f;
          this.aspectView[3] = (1.0F - 2.0F * f);
        }
      }
      int j = this.renderSet.size();
      for (int i = 0; i < j; i++) {
        Render localRender = (Render)this.renderArray[i];
        localRender.contextResize(width(), height());
      }
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
        gl.Viewport(0, 0, RendersMain.width(), RendersMain.height());
        gl.MatrixMode(5889);
        gl.LoadIdentity();
        gl.Ortho(0.0D, RendersMain.width(), 0.0D, RendersMain.height(), 0.0D, 1.0D);
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

        gl.Begin(7);
        gl.Vertex3f(GUIRenders.this.wClipX, GUIRenders.this.wClipY, f);
        gl.Vertex3f(GUIRenders.this.wClipX + GUIRenders.this.wClipDX, GUIRenders.this.wClipY, f);
        gl.Vertex3f(GUIRenders.this.wClipX + GUIRenders.this.wClipDX, GUIRenders.this.wClipY + GUIRenders.this.wClipDY, f);
        gl.Vertex3f(GUIRenders.this.wClipX, GUIRenders.this.wClipY + GUIRenders.this.wClipDY, f);
        gl.End();

        if ((currentRender.bClearStencil) && (Config.cur.windowStencilBits != 0)) {
          gl.StencilOp(7680, 7680, 7680);
          gl.Disable(2960);
        }
        if (i != 0)
          gl.PolygonMode(1032, 6913); 
      }
    }

    public boolean isShow() {
      return GUIRenders.this.isVisible();
    }
    public void setShow(boolean paramBoolean) { if (paramBoolean) GUIRenders.this.showWindow(); else
        GUIRenders.this.hideWindow();  }

    public int frame() {
      return RendersMain.frame(); } 
    public int width() { return (int)GUIRenders.this.win.dx; } 
    public int height() { return (int)GUIRenders.this.win.dy; } 
    public GLContext glContext() { return RendersMain.glContext(); } 
    public void setGlContext(GLContext paramGLContext) {  }

    public void msgGLContext(int paramInt) {  }

    public boolean isTickPainting() { return RendersMain.isTickPainting();
    }

    public void setTickPainting(boolean paramBoolean)
    {
    }

    public void paint()
    {
    }

    public void paint(Render paramRender)
    {
    }
  }
}