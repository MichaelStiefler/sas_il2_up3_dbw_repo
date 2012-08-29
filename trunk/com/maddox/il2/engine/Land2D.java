package com.maddox.il2.engine;

import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Destroy;

public class Land2D
  implements Destroy, MsgGLContextListener
{
  public static final int TILE = 64;
  public static final int BORDER0 = 2;
  private int BORDER = 2;
  private float ZERO = this.BORDER / 64.0F;
  private float ONE = 1.0F - this.ZERO;
  private String tgaName;
  private Mat tileMat = null;
  private double worldSizeX;
  private double worldSizeY;
  private double mapSizeX;
  private double mapSizeY;
  private double worldOfsX;
  private double worldOfsY;
  private double worldDx;
  private double worldDy;
  private double worldFillY;
  private int nx;
  private int ny;
  private int fillX;
  private int fillY;
  private int[] glId = null;
  private boolean bShow = true;

  public String tgaName() { return this.tgaName; } 
  public double worldSizeX() { return this.worldSizeX; } 
  public double worldSizeY() { return this.worldSizeY; } 
  public double mapSizeX() { return this.mapSizeX; } 
  public double mapSizeY() { return this.mapSizeY; } 
  public double worldOfsX() { return this.worldOfsX; } 
  public double worldOfsY() { return this.worldOfsY; } 
  protected int pixelsX() {
    return this.nx * (64 - 2 * this.BORDER);
  }
  public boolean isShow() { return this.bShow; } 
  public void show(boolean paramBoolean) { this.bShow = paramBoolean; }

  public void render() {
    render(1.0F, 1.0F, 1.0F, 1.0F);
  }
  public void render(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if ((!this.bShow) || (this.glId == null) || (!(Render.currentCamera() instanceof CameraOrtho2D)))
    {
      return;
    }if (this.tileMat != null) {
      renderTile(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
    renderWorld(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  private void renderTile(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (paramFloat1 < 0.0F) paramFloat1 = 0.0F; if (paramFloat1 > 1.0F) paramFloat1 = 1.0F;
    if (paramFloat2 < 0.0F) paramFloat2 = 0.0F; if (paramFloat2 > 1.0F) paramFloat2 = 1.0F;
    if (paramFloat3 < 0.0F) paramFloat3 = 0.0F; if (paramFloat3 > 1.0F) paramFloat3 = 1.0F;
    if (paramFloat4 < 0.0F) paramFloat4 = 0.0F; if (paramFloat4 > 1.0F) paramFloat4 = 1.0F;
    int i = (int)(255.0F * paramFloat1 + 0.5D) | (int)(255.0F * paramFloat2 + 0.5D) << 8 | (int)(255.0F * paramFloat3 + 0.5D) << 16 | (int)(255.0F * paramFloat4 + 0.5D) << 24;

    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    Render.drawTile(localCameraOrtho2D.left, localCameraOrtho2D.bottom, localCameraOrtho2D.right - localCameraOrtho2D.left, localCameraOrtho2D.top - localCameraOrtho2D.bottom, 0.0F, this.tileMat, i, 0.0F, 1.0F, 1.0F, -1.0F);
  }

  private void renderWorld(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    Render.clearStates();
    gl.Color4f(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    gl.DepthMask(false);
    gl.Enable(3553);
    gl.Disable(3008);
    gl.Disable(3042);
    gl.Disable(2929);
    gl.TexEnvi(8960, 8704, 8448);
    float f1 = (float)(this.worldDx * localCameraOrtho2D.worldScale);
    float f2 = (float)(this.worldDy * localCameraOrtho2D.worldScale);
    for (int i = this.ny - 1; i >= 0; i--) {
      float f3 = (float)((i * this.worldDy - this.worldFillY - localCameraOrtho2D.worldYOffset) * localCameraOrtho2D.worldScale);
      if ((f3 > localCameraOrtho2D.top) || 
        (f3 + f2 < localCameraOrtho2D.bottom)) continue;
      for (int j = 0; j < this.nx; j++) {
        float f4 = (float)((j * this.worldDx - localCameraOrtho2D.worldXOffset) * localCameraOrtho2D.worldScale);
        if ((f4 > localCameraOrtho2D.right) || 
          (f4 + f1 < localCameraOrtho2D.left)) continue;
        gl.BindTexture(3553, this.glId[((this.ny - i - 1) * this.nx + j)]);
        gl.Begin(7);
        gl.TexCoord2f(this.ZERO, this.ONE); gl.Vertex3f(f4, f3, 0.0F);
        gl.TexCoord2f(this.ONE, this.ONE); gl.Vertex3f(f4 + f1, f3, 0.0F);
        gl.TexCoord2f(this.ONE, this.ZERO); gl.Vertex3f(f4 + f1, f3 + f2, 0.0F);
        gl.TexCoord2f(this.ZERO, this.ZERO); gl.Vertex3f(f4, f3 + f2, 0.0F);
        gl.End();
      }
    }
    Render.prepareStates();
  }

  public boolean load(String paramString, double paramDouble1, double paramDouble2) {
    this.tgaName = paramString;
    this.worldSizeX = paramDouble1;
    this.worldSizeY = paramDouble2;
    this.mapSizeX = paramDouble1;
    this.mapSizeY = paramDouble2;
    this.worldOfsX = 0.0D;
    this.worldOfsY = 0.0D;
    return reload();
  }

  public boolean reload() {
    freeGlIds();
    boolean bool = load();
    if (bool) {
      int i = 64 - 2 * this.BORDER;
      this.worldDx = (this.worldSizeX / (this.nx * i - this.fillX) * i);
      this.worldDy = (this.worldSizeY / (this.ny * i - this.fillY) * i);
      this.worldFillY = (this.worldSizeY / (this.ny * i - this.fillY) * this.fillY);
      GLContext.getCurrent().msgAddListener(this, null);
    } else {
      this.worldDx = (this.worldDy = 1.0D);
      this.worldFillY = 0.0D;
      GLContext.getCurrent().msgRemoveListener(this, null);
    }
    return bool;
  }

  private boolean load() {
    int i = RenderContext.cfgTxrQual.get();
    i -= RenderContext.cfgTxrQual.countStates() - RenderContext.cfgTxrQual.firstState() - 1;
    int j = 1;
    while (i-- > 0)
      j *= 2;
    this.BORDER = (2 * j);
    if (this.BORDER * 2 >= 64)
      this.BORDER = 16;
    this.ZERO = (this.BORDER / 64.0F);
    this.ONE = (1.0F - this.ZERO);

    int[] arrayOfInt = new int[2];
    this.glId = Mat.loadTextureAsArrayFromTgaB(this.tgaName, Mat.MINLINEAR | Mat.MAGLINEAR | Mat.WRAPUV, 64, 64, this.BORDER, arrayOfInt);
    if (this.glId != null) {
      int k = 64 - 2 * this.BORDER;
      this.nx = ((arrayOfInt[0] + k - 1) / k);
      this.ny = ((arrayOfInt[1] + k - 1) / k);
      this.fillX = (arrayOfInt[0] % k);
      this.fillY = (arrayOfInt[1] % k);
      if (this.fillX != 0) this.fillX = (k - this.fillX);
      if (this.fillY != 0) this.fillY = (k - this.fillY);
      return true;
    }
    return false;
  }

  private void freeGlIds() {
    if (this.glId == null) return;
    gl.DeleteTextures(this.nx * this.ny, this.glId);
    this.glId = null;
  }

  public void msgGLContext(int paramInt) {
    if (paramInt == 2) {
      this.glId = null;
    } else if (paramInt == 8) {
      this.glId = null;
      load();
    }
  }

  public void setBigMap(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.mapSizeX = paramDouble1;
    this.mapSizeY = paramDouble2;
    this.worldOfsX = paramDouble3;
    this.worldOfsY = paramDouble4;
    this.tileMat = null;
    if (paramString != null) try {
        this.tileMat = Mat.New(paramString); } catch (Exception localException) {
      } 
  }
  public Land2D() {
  }

  public Land2D(String paramString, double paramDouble1, double paramDouble2) {
    load(paramString, paramDouble1, paramDouble2);
  }

  public void destroy() {
    if (GLContext.isValid(GLContext.getCurrent())) freeGlIds(); else
      this.glId = null;
    if (GLContext.getCurrent() != null)
      GLContext.getCurrent().msgRemoveListener(this, null);
  }

  public boolean isDestroyed() {
    return this.glId == null;
  }
}