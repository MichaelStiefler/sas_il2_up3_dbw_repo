package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.RandomVector;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.Provider;
import com.maddox.opengl.gl;
import com.maddox.rts.Time;

public class Cinema extends Render
  implements MsgGLContextListener
{
  private int[] Tex = { 0 };
  private int _indx;
  private static RangeRandom rnd = new RangeRandom();
  private static final int N_SCRATCH_COLS = 8;
  private static final int N_SCRATCH_ROWS = 6;
  private static final int N_SCRATCH_IMAGES = 48;
  private static float[] uv = new float[384];
  private static float[] vVert = new float[2];
  private static float[] vThread = new float[2];

  private static Vector3d tmp = new Vector3d();
  private static final int N_CIRCLE_POINTS = 36;
  private static Point2f[] circleScr = new Point2f[36];
  private static Point2f[] cornerScr = new Point2f[4];
  private static final int N_BLOCKS = 4;
  private static final int MAX_VSCRATCHES = 5;
  private static final long MIN_BLOCK_ON_TM = 4000L;
  private static final long INOUT_BLOCK_TM = 400L;
  private static final long MAX_BLOCK_ON_TM = 25000L;
  private static final long MIN_BLOCK_OFF_TM = 4000L;
  private static final long MAX_BLOCK_OFF_TM = 13000L;
  private static final float MAX_BLOCK_WD = 0.15F;
  private static final float MAX_BLOCK_OFFSR = 0.04F;
  private static final float MAX_SCRATCH_WD = 0.006F;
  private VScratchBlock[] blocks = new VScratchBlock[4];

  public void destroy()
  {
    Camera localCamera = getCamera();
    if (Actor.isValid(localCamera)) localCamera.destroy();
    super.destroy();
  }

  public void msgGLContext(int paramInt) {
    if (paramInt == 8)
      this.Tex[0] = 0;
  }

  protected void contextResize(int paramInt1, int paramInt2)
  {
  }

  public void preRender() {
    if (this.Tex[0] == 0) { gl.Enable(3553);
      gl.GenTextures(1, this.Tex);
      byte[] arrayOfByte = null;
      int i;
      int j;
      try { TexImage localTexImage = new TexImage();
        localTexImage.LoadTGA("3do/effects/cinema/scratches.tga");
        i = localTexImage.sx;
        j = localTexImage.sy;
        int k = i * j * 4;
        arrayOfByte = new byte[k];
        int m = 0;
        for (int n = 0; n < k; n += 4) {
          arrayOfByte[(n + 0)] = -128;
          arrayOfByte[(n + 1)] = -128;
          arrayOfByte[(n + 2)] = -128;
          arrayOfByte[(n + 3)] = localTexImage.image[(m++)];
        }
      } catch (Exception localException) {
        return;
      }
      gl.BindTexture(3553, this.Tex[0]);
      gl.TexParameteri(3553, 10242, 10497);
      gl.TexParameteri(3553, 10243, 10497);
      gl.TexParameteri(3553, 10240, 9729);
      gl.TexParameteri(3553, 10241, 9729);
      gl.TexImage2D(3553, 0, 32856, i, j, 0, 6408, 5121, arrayOfByte);
    }
  }

  public void resetGame()
  {
    RangeRandom localRangeRandom = new RangeRandom(System.currentTimeMillis() + this._indx);
    localRangeRandom.nextFloat();
    initBlocks(0L, localRangeRandom);
  }

  public boolean isShow() {
    if (this._indx == 0) return super.isShow();
    return (Config.cur.isUse3Renders()) && (super.isShow());
  }

  public Cinema(int paramInt, float paramFloat) {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (this._indx == 0)
      setName("renderCinema");
    GLContext.getCurrent().msgAddListener(this, null);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }

  private void initBlocks(long paramLong, RangeRandom paramRangeRandom)
  {
    paramRangeRandom.nextFloat();

    for (int i = 0; i < 4; i++) {
      if (this.blocks[i] == null) {
        this.blocks[i] = new VScratchBlock(null);
      }
      makeBlockPassive(i, paramLong, paramRangeRandom, true);
    }

    for (i = 0; i < 4; i++) {
      if (paramRangeRandom.nextInt(100) > 20) {
        makeBlockActive(i, paramLong, paramRangeRandom);
      }
    }

    for (i = 0; i < 4; i++) {
      long l = paramRangeRandom.nextLong(0L, this.blocks[i].timeEnd - this.blocks[i].timeStart);
      this.blocks[i].timeStart -= l;
      this.blocks[i].timeEnd -= l;
    }
  }

  private void processBlocks(long paramLong, RangeRandom paramRangeRandom)
  {
    paramRangeRandom.nextFloat();

    for (int i = 0; i < 4; i++) {
      if (paramLong <= this.blocks[i].timeEnd) {
        continue;
      }
      if (this.blocks[i].active)
        makeBlockPassive(i, paramLong, paramRangeRandom, true);
      else
        makeBlockActive(i, paramLong, paramRangeRandom);
    }
  }

  private void drawBlocks(long paramLong, RangeRandom paramRangeRandom)
  {
    paramRangeRandom.nextFloat();

    for (int i = 0; i < 4; i++) {
      if ((!this.blocks[i].active) || (paramLong > this.blocks[i].timeEnd) || (paramLong < this.blocks[i].timeStart))
      {
        continue;
      }

      RandomVector.getTimed(paramLong * 10L, tmp, i * 71);
      float f1 = this.blocks[i].offs + (float)tmp.x * 0.04F;
      float f2;
      if (paramLong - this.blocks[i].timeStart < 400L)
        f2 = (float)(paramLong - this.blocks[i].timeStart) / 400.0F;
      else if (this.blocks[i].timeEnd - paramLong < 400L)
        f2 = (float)(this.blocks[i].timeEnd - paramLong) / 400.0F;
      else {
        f2 = 1.0F;
      }

      RandomVector.getTimedStepped(2, paramLong * 101L, tmp, 123 + i * 29);
      float f3 = ((float)tmp.y + 1.0F) * 0.5F;
      f3 = 0.6F * f3 + 1.0F * (1.0F - f3);
      f2 *= f3;

      gl.Begin(7);

      for (int j = 0; j < this.blocks[i].nScratches; j++)
      {
        Point3f localPoint3f = this.blocks[i].scratches[j];

        float f4 = f1 + localPoint3f.x;
        float f5 = f2 * localPoint3f.y;
        float f6 = localPoint3f.z * 0.5F;

        RandomVector.getTimedStepped(4, paramLong * 67L, tmp, 12 + i * 17 + j * 7);
        f4 = (float)(f4 + 0.003000000026077032D * tmp.z);

        float f7 = paramRangeRandom.nextFloat(0.0F, 1.0F);
        float f8 = f7 + 1.0F;

        if (this.blocks[i].white)
          gl.Color4f(0.9F, 0.9F, 0.9F, f5 * 0.6F);
        else {
          gl.Color4f(0.2F, 0.2F, 0.2F, f5);
        }

        gl.TexCoord2f(f7, vVert[0]);
        gl.Vertex2f(f4 + f6, 0.0F);

        gl.TexCoord2f(f7, vVert[1]);
        gl.Vertex2f(f4 - f6, 0.0F);

        gl.TexCoord2f(f8, vVert[1]);
        gl.Vertex2f(f4 - f6, 1.0F);

        gl.TexCoord2f(f8, vVert[0]);
        gl.Vertex2f(f4 + f6, 1.0F);
      }
      gl.End();
    }
  }

  private void makeBlockActive(int paramInt, long paramLong, RangeRandom paramRangeRandom)
  {
    this.blocks[paramInt].active = false;

    float f1 = paramRangeRandom.nextFloat(0.0F, 1.0F);
    float f2 = f1 - 0.04F;
    float f3 = f1 + 0.15F + 0.04F;

    if ((f2 < 0.001F) || (f3 > 0.999F)) {
      makeBlockPassive(paramInt, paramLong, paramRangeRandom, false);
      return;
    }

    for (int i = 0; i < 4; i++) {
      if (!this.blocks[i].active) {
        continue;
      }
      float f5 = this.blocks[i].offs - 0.04F;
      float f6 = this.blocks[i].offs + 0.15F + 0.04F;
      if ((f2 > f6) || (f3 < f5)) {
        continue;
      }
      makeBlockPassive(paramInt, paramLong, paramRangeRandom, false);
      return;
    }

    this.blocks[paramInt].active = true;
    this.blocks[paramInt].offs = f1;
    this.blocks[paramInt].timeStart = paramLong;
    this.blocks[paramInt].timeEnd = (paramLong + paramRangeRandom.nextLong(4000L, 25000L));

    VScratchBlock.access$102(this.blocks[paramInt], paramRangeRandom.nextInt(1, 5));
    float f4 = paramRangeRandom.nextFloat(0.6F, 1.0F) * 0.15F;
    for (int j = 0; j < 5; j++) {
      if (this.blocks[paramInt].scratches[j] == null) {
        this.blocks[paramInt].scratches[j] = new Point3f();
      }

      this.blocks[paramInt].scratches[j].set(paramRangeRandom.nextFloat(0.0F, f4), paramRangeRandom.nextFloat(0.3F, 0.6F), 0.006F * paramRangeRandom.nextFloat(0.55F, 1.0F));
    }

    this.blocks[paramInt].white = false;
  }

  private void makeBlockPassive(int paramInt, long paramLong, RangeRandom paramRangeRandom, boolean paramBoolean)
  {
    this.blocks[paramInt].active = false;
    if (paramBoolean) {
      this.blocks[paramInt].timeStart = paramLong;
      this.blocks[paramInt].timeEnd = (paramLong + paramRangeRandom.nextLong(4000L, 13000L));
    } else {
      this.blocks[paramInt].timeStart = (paramLong - 2L);
      this.blocks[paramInt].timeEnd = (paramLong - 1L);
    }
  }

  public void render()
  {
    gl.ShadeModel(7425);
    gl.Disable(2929);
    gl.Enable(3553);
    gl.Enable(3042);
    gl.AlphaFunc(516, 0.0F);
    gl.BlendFunc(770, 771);

    renderScratches();
  }

  public void setShow(boolean paramBoolean)
  {
    super.setShow(paramBoolean);
  }

  private static void glRotVertex2f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    float f1 = Geom.cosDeg(paramFloat3);
    float f2 = Geom.sinDeg(paramFloat3);
    gl.Vertex2f(paramFloat1 + paramFloat4 * f1 - paramFloat5 * f2, paramFloat2 + paramFloat4 * f2 + paramFloat5 * f1);
  }

  private void renderScratches() {
    long l1 = Time.current();
    rnd.setSeed(11L + l1 / 62L);
    rnd.nextFloat();
    rnd.nextFloat();

    gl.BindTexture(3553, this.Tex[0]);

    processBlocks(l1, rnd);
    drawBlocks(l1, rnd);

    int i = rnd.nextInt(4, 10);

    int j = rnd.nextInt(2, 6);

    gl.Begin(7);
    gl.Color4f(0.2F, 0.2F, 0.2F, 0.6F);
    float f1;
    float f2;
    int k;
    while (i-- > 0) {
      f1 = rnd.nextFloat(0.0F, 1.0F);
      f2 = rnd.nextFloat(0.0F, 1.0F);
      f3 = rnd.nextFloat(0.0016F, 0.0053F);
      f4 = f3 * 1.333333F;

      k = rnd.nextInt(0, 47);

      gl.TexCoord2f(uv[(k * 8 + 2 + 0)], uv[(k * 8 + 2 + 1)]);
      gl.Vertex2f(f1 + f3, f2 + f4);

      gl.TexCoord2f(uv[(k * 8 + 0 + 0)], uv[(k * 8 + 0 + 1)]);
      gl.Vertex2f(f1 - f3, f2 + f4);

      gl.TexCoord2f(uv[(k * 8 + 4 + 0)], uv[(k * 8 + 4 + 1)]);
      gl.Vertex2f(f1 - f3, f2 - f4);

      gl.TexCoord2f(uv[(k * 8 + 6 + 0)], uv[(k * 8 + 6 + 1)]);
      gl.Vertex2f(f1 + f3, f2 - f4);
    }
    gl.End();

    gl.Begin(7);
    gl.Color4f(0.9F, 0.9F, 0.9F, 0.55F);
    while (j-- > 0) {
      f1 = rnd.nextFloat(0.0F, 1.0F);
      f2 = rnd.nextFloat(0.0F, 1.0F);
      f3 = rnd.nextFloat(0.0015F, 0.004F);
      f4 = f3 * 1.333333F;

      k = rnd.nextInt(0, 47);

      gl.TexCoord2f(uv[(k * 8 + 2 + 0)], uv[(k * 8 + 2 + 1)]);
      gl.Vertex2f(f1 + f3, f2 + f4);

      gl.TexCoord2f(uv[(k * 8 + 0 + 0)], uv[(k * 8 + 0 + 1)]);
      gl.Vertex2f(f1 - f3, f2 + f4);

      gl.TexCoord2f(uv[(k * 8 + 4 + 0)], uv[(k * 8 + 4 + 1)]);
      gl.Vertex2f(f1 - f3, f2 - f4);

      gl.TexCoord2f(uv[(k * 8 + 6 + 0)], uv[(k * 8 + 6 + 1)]);
      gl.Vertex2f(f1 + f3, f2 - f4);
    }
    gl.End();

    gl.Begin(7);
    gl.Color4f(0.2F, 0.2F, 0.2F, 0.6F);
    if (rnd.nextInt(0, 100) < 15) {
      f1 = rnd.nextFloat(0.0F, 1.0F);
      f2 = rnd.nextFloat(0.0F, 1.0F);
      f3 = rnd.nextFloat(0.003F, 0.0035F);
      f4 = f3 * 1.333333F;
      f3 *= 8.0F;

      k = rnd.nextInt(0, 47);

      f7 = vThread[0];
      f9 = vThread[1];
      if (rnd.nextInt(0, 1000) > 500)
        f6 = 0.0F;
      else {
        f6 = 0.5F;
      }
      f8 = f6 + 0.5F;

      f10 = rnd.nextFloat(0.0F, 359.89999F);

      gl.TexCoord2f(f8, f7);
      glRotVertex2f(f1, f2, f10, f3, f4);

      gl.TexCoord2f(f6, f7);
      glRotVertex2f(f1, f2, f10, -f3, f4);

      gl.TexCoord2f(f6, f9);
      glRotVertex2f(f1, f2, f10, -f3, -f4);

      gl.TexCoord2f(f8, f9);
      glRotVertex2f(f1, f2, f10, f3, -f4);
    }
    gl.End();

    gl.Disable(3553);
    Provider.setEnableBW(false);

    gl.BlendFunc(1, 770);

    long l2 = l1 / 62L;
    float f3 = (float)(l1 % 62L) / 62.0F;
    rnd.setSeed(l2);
    rnd.nextFloat();
    rnd.nextFloat();
    float f4 = rnd.nextFloat(0.0F, 1.0F);
    rnd.setSeed(l2 + 1L);
    rnd.nextFloat();
    rnd.nextFloat();
    float f5 = rnd.nextFloat(0.0F, 1.0F);
    float f6 = 1.0F + 0.1F * (f4 * (1.0F - f3) + f5 * f3);

    float f7 = 0.2F;
    float f8 = 0.8235294F;
    float f9 = 0.615686F;
    float f10 = 0.172549F;

    float f11 = f8 * f7;
    float f12 = f9 * f7;
    float f13 = f10 * f7;
    float f14 = (1.0F - f7) * f6;

    gl.Begin(6);
    gl.Color4f(f11, f12, f13, f14 * 1.0F);
    gl.Vertex2f(0.5F, 0.5F);
    gl.Color4f(f11, f12, f13, f14 * 1.0F);
    for (int m = 0; m < 36; m++) {
      gl.Vertex2f(circleScr[m].x, circleScr[m].y);
    }
    gl.Vertex2f(circleScr[0].x, circleScr[0].y);
    gl.End();

    for (m = 0; m < 4; m++) {
      gl.Begin(6);
      gl.Color4f(f11, f12, f13, f14 * 0.92F);
      gl.Vertex2f(cornerScr[m].x, cornerScr[m].y);
      gl.Color4f(f11, f12, f13, f14 * 1.0F);
      for (int n = 0; n < 10; n++) {
        int i1 = (m * 9 + n) % 36;
        gl.Vertex2f(circleScr[i1].x, circleScr[i1].y);
      }
      gl.End();
    }

    gl.Enable(3553);
    gl.BlendFunc(770, 771);
    Provider.setEnableBW(true);
  }

  static
  {
    for (int i = 0; i < 36; i++) {
      circleScr[i] = new Point2f();
      float f1 = 90.0F - i * 360.0F / 36.0F;
      float f2 = Geom.cosDeg(f1);
      float f3 = Geom.sinDeg(f1);
      circleScr[i].set((f2 + 1.0F) * 0.5F, (f3 + 1.0F) * 0.5F);
    }
    cornerScr[0] = new Point2f(1.0F, 1.0F);
    cornerScr[1] = new Point2f(1.0F, 0.0F);
    cornerScr[2] = new Point2f(0.0F, 0.0F);
    cornerScr[3] = new Point2f(0.0F, 1.0F);

    int n = 0;
    float f4 = 0.0039063F;
    for (int j = 0; j < 6; j++) {
      for (i = 0; i < 8; i++) {
        for (int m = 0; m < 2; m++) {
          for (int k = 0; k < 2; k++) {
            uv[(n * 8 + (m * 2 + k) * 2 + 0)] = ((i + k) / 8.0F + (k == 0 ? f4 : -f4));

            uv[(n * 8 + (m * 2 + k) * 2 + 1)] = ((j + m) / 8.0F + (m == 0 ? f4 : -f4));
          }
        }

        n++;
      }
    }
    vVert[0] = (0.875F + f4);
    vVert[1] = (1.0F - f4);
    vThread[0] = (0.75F + f4);
    vThread[1] = (0.875F - f4);
  }

  private class VScratchBlock
  {
    public boolean active;
    public boolean white;
    public long timeStart;
    public long timeEnd;
    public float offs;
    private int nScratches;
    private Point3f[] scratches;
    private final Cinema this$0;

    private VScratchBlock()
    {
      this.this$0 = this$1;

      this.scratches = new Point3f[5];
    }

    VScratchBlock(Cinema.1 arg2)
    {
      this();
    }
  }
}