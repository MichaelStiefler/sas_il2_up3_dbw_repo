package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.il2.objects.vehicles.lights.SearchlightGeneric;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Time;

public class LightsGlare extends Render
  implements MsgGLContextListener
{
  public static final int NELEMS_IN_GD = 3;
  public float[] glareData = null;

  private int[] Tex = { 0 };
  private boolean curTexIsBW = false;
  private boolean wantBW = false;
  private int _indx;
  private long prevTm;
  private float curGlare;
  private static Point3d SL_cameraPos = new Point3d();

  private static Vector3d rayDir = new Vector3d();

  private static Vector3d axisX = new Vector3d();
  private static Vector3d axisY = new Vector3d();
  private static Vector3d axisZ = new Vector3d();

  private static Point3d tmpp0 = new Point3d();
  private static Point3d tmpp1 = new Point3d();
  private static Point3d tmpp2 = new Point3d();

  private static Vector3d tmpv0 = new Vector3d();
  private static Vector3d tmpv1 = new Vector3d();

  private static Point3f tmpcolor = new Point3f();

  public LightsGlare(int paramInt, float paramFloat) {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (paramInt == 0)
      setName("renderLightsGlare");
    GLContext.getCurrent().msgAddListener(this, null);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }

  public void resetGame() {
    this.prevTm = Time.current();
    this.curGlare = 0.0F;
    this.glareData = null;
  }

  public void destroy() {
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

  public boolean isShow()
  {
    if (this._indx == 0) return super.isShow();
    return (Config.cur.isUse3Renders()) && (Main3D.cur3D().lightsGlare.isShow());
  }

  public void setShow(boolean paramBoolean) {
    if (this._indx == 0)
      super.setShow(paramBoolean);
    this.prevTm = Time.current();
    this.curGlare = 0.0F;
    this.glareData = null;
  }

  public void enterBWmode() {
    this.wantBW = true;
  }

  public void leaveBWmode() {
    this.wantBW = false;
  }

  public void preRender() {
    if ((this.Tex[0] != 0) && (this.curTexIsBW == this.wantBW)) {
      return;
    }

    this.curTexIsBW = this.wantBW;

    if (this.Tex[0] != 0) {
      gl.DeleteTextures(1, this.Tex);
      this.Tex[0] = 0;
    }

    gl.Enable(3553);
    gl.GenTextures(1, this.Tex);
    TexImage localTexImage = new TexImage();
    try {
      localTexImage.LoadTGA("effects/sunglare/glare02.tga_asis");
    } catch (Exception localException) {
      return;
    }

    if (this.wantBW)
    {
      int i = localTexImage.sx * localTexImage.sy * 3;
      for (int j = 0; j < i; j += 3) {
        int k = (localTexImage.image[(j + 0)] & 0xFF) + (localTexImage.image[(j + 1)] & 0xFF) + (localTexImage.image[(j + 2)] & 0xFF);

        int m = (byte)((k + 1) / 3);
        localTexImage.image[(j + 0)] = m;
        localTexImage.image[(j + 1)] = m;
        localTexImage.image[(j + 2)] = m;
      }
    }

    gl.BindTexture(3553, this.Tex[0]);
    gl.TexParameteri(3553, 10242, 10497);
    gl.TexParameteri(3553, 10243, 10497);
    gl.TexParameteri(3553, 10240, 9729);
    gl.TexParameteri(3553, 10241, 9729);
    gl.TexImage2D(3553, 0, 32849, localTexImage.sx, localTexImage.sy, 0, 6407, 5121, localTexImage.image);
  }

  public float computeFlash(Actor paramActor, Point3d paramPoint3d, Point3f paramPoint3f)
  {
    VisibilityChecker.checkLandObstacle = true;
    VisibilityChecker.checkCabinObstacle = true;
    VisibilityChecker.checkPlaneObstacle = true;
    VisibilityChecker.checkObjObstacle = true;
    VisibilityChecker.targetPosInput = paramPoint3d;

    paramPoint3f.x = 0.0F;
    float f = VisibilityChecker.computeVisibility(null, paramActor);
    paramPoint3f.x = VisibilityChecker.resultAng;

    return f;
  }

  public void render()
  {
    if ((RenderContext.cfgLandShading.get() < 2) && ((!Mission.isNet()) || (!World.cur().diffCur.Blackouts_N_Redouts)))
    {
      return;
    }

    int i = SearchlightGeneric.possibleGlare();
    if (i == 0) {
      return;
    }
    if (i == 2) {
      this.glareData = null;
    }

    i = SearchlightGeneric.numlightsGlare();

    if ((this.glareData == null) || (i * 3 != this.glareData.length)) {
      this.glareData = new float[i * 3];
      int j = this.glareData.length;
      for (int k = 0; k < j; k++) {
        this.glareData[k] = 0.0F;
      }

    }

    Main3D.cur3D().setRenderIndx(this._indx);
    Main3D.cur3D()._camera3D[this._indx].pos.getRender(SL_cameraPos);
    if (!SearchlightGeneric.computeGlare(this, SL_cameraPos)) {
      Main3D.cur3D().setRenderIndx(0);
      return;
    }

    float f1 = 0.0F;
    long l1 = Time.current();
    long l2 = l1 - this.prevTm;

    int m = 0;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    float f10;
    for (int n = 0; n < i; n++) {
      f3 = this.glareData[(n * 3 + 0)];
      f4 = this.glareData[(n * 3 + 1)];
      f5 = this.glareData[(n * 3 + 2)];

      if (f3 < 0.0F)
      {
        f6 = 0.0F;
        f7 = 0.0F;
      }
      else {
        f8 = 34.0F;
        if (f4 >= f8) {
          f6 = 0.0F;
        } else {
          f9 = f4 / f8;
          f6 = 0.5F * (Geom.cosDeg(f9 * 180.0F) + 1.0F);
          if (f6 >= 1.0F) {
            f6 = 1.0F;
          }
          f6 *= f3;
          if (f6 <= 0.0F) {
            f6 = 0.0F;
          }
        }

        f9 = 48.0F;
        if (f4 >= f9) {
          f7 = 0.0F;
        } else {
          f10 = f4 / f9;
          f7 = 0.5F * (Geom.cosDeg(f10 * 180.0F) + 1.0F);
        }
        if (f3 > 0.003921569F) {
          f7 *= f3;
          f7 = 0.2F + 0.8F * f7;
          if (f7 >= 1.0F)
            f7 = 1.0F;
        }
        else {
          f7 = 0.0F;
        }
      }
      f1 += f6;

      if (l2 < 0L)
      {
        f5 = f7;
      } else if (l2 != 0L)
      {
        f8 = f7 - f5;
        f9 = f8 >= 0.0F ? 5.0F : 2.5F;
        f10 = (float)l2 * f9 / 1000.0F;
        if (f10 >= Math.abs(f8))
          f5 = f7;
        else {
          f5 += (f8 < 0.0F ? -f10 : f10);
        }
      }

      this.glareData[(n * 3 + 2)] = f5;
      if ((f3 >= 0.0F) && (f5 >= 0.007843138F)) {
        m = 1;
      }

    }

    float f2 = f1;
    if (f2 >= 1.0F) {
      f2 = 1.0F;
    }

    if (l2 < 0L)
    {
      this.curGlare = f2;
    } else if (l2 != 0L)
    {
      f3 = f2 - this.curGlare;
      f4 = f3 >= 0.0F ? 3.2F : 1.1F;
      f5 = (float)l2 * f4 / 1000.0F;
      if (f5 >= Math.abs(f3))
        this.curGlare = f2;
      else {
        this.curGlare += (f3 < 0.0F ? -f5 : f5);
      }
    }

    this.prevTm = l1;

    int i1 = this.curGlare >= 0.007843138F ? 1 : 0;

    if ((m == 0) && (i1 == 0)) {
      Main3D.cur3D().setRenderIndx(0);
      return;
    }

    gl.ShadeModel(7425);
    gl.Disable(2929);
    gl.Enable(3042);
    gl.AlphaFunc(516, 0.0F);
    gl.Enable(3553);

    int i2 = 0;

    if (i1 != 0) {
      gl.Disable(3553);
      gl.BlendFunc(1, 770);
      gl.Begin(7);

      if (i2 != 0)
      {
        gl.Color4f(0.0F, 0.0F, 0.0F, 1.0F - this.curGlare * 0.78F);
      } else {
        tmpp2.set(1.0D, 1.0D, 1.0D);
        tmpp2.scale(this.curGlare);

        tmpp2.scale(0.7699999809265137D);

        gl.Color4f((float)tmpp2.x, (float)tmpp2.y, (float)tmpp2.z, 1.0F - this.curGlare * 0.86F);
      }

      gl.Vertex2f(1.0F, 1.0F);
      gl.Vertex2f(1.0F, 0.0F);
      gl.Vertex2f(0.0F, 0.0F);
      gl.Vertex2f(0.0F, 1.0F);

      gl.End();
      gl.Enable(3553);
    }

    if (m == 0) {
      Main3D.cur3D().setRenderIndx(0);
      return;
    }

    gl.BindTexture(3553, this.Tex[0]);
    gl.BlendFunc(770, 1);

    SearchlightGeneric.getnextposandcolorGlare(null, null);
    for (int i3 = 0; i3 < i; i3++) {
      SearchlightGeneric.getnextposandcolorGlare(tmpp1, tmpcolor);

      f5 = this.glareData[(i3 * 3 + 2)];

      if (f5 < 0.007843138F)
      {
        continue;
      }
      if (this.glareData[(i3 * 3 + 0)] < 0.0F)
      {
        continue;
      }

      if (!Main3D.cur3D().project2d_norm(tmpp1.x, tmpp1.y, tmpp1.z, tmpp2))
      {
        continue;
      }

      f6 = ((float)tmpp2.x + 1.0F) * 0.5F;
      f7 = ((float)tmpp2.y + 1.0F) * 0.5F;

      if (i2 != 0) {
        f10 = f5 <= 0.4F ? 0.0F : (f5 - 0.4F) / 0.6F;

        f8 = 0.05F * (1.0F - f10) + 0.15F * f10;
      } else {
        f10 = f5 <= 0.3F ? 0.0F : (f5 - 0.3F) / 0.7F;

        f8 = 0.03F * (1.0F - f10) + 0.14F * f10;
      }
      f9 = f8;

      if ((f6 - f8 > 1.0F) || (f6 + f8 < 0.0F) || (f7 - f9 > 1.0F) || (f7 + f9 < 0.0F))
      {
        continue;
      }

      f10 = Geom.tanDeg(Main3D.cur3D().camera3D.FOV() * 0.5F);
      float f11 = -90.0F * ((float)tmpp2.x * f10);
      float f12 = Geom.cosDeg(f11);
      float f13 = Geom.sinDeg(f11);

      gl.Begin(7);
      gl.Color4f(tmpcolor.x, tmpcolor.y, tmpcolor.z, f5);

      float f14 = 0.0039063F;

      gl.TexCoord2f(1.0F - f14, 0.0F + f14);
      glRotVertex2f(f6, f7, f12, f13, f8, f9);

      gl.TexCoord2f(0.0F + f14, 0.0F + f14);
      glRotVertex2f(f6, f7, f12, f13, -f8, f9);

      gl.TexCoord2f(0.0F + f14, 1.0F - f14);
      glRotVertex2f(f6, f7, f12, f13, -f8, -f9);

      gl.TexCoord2f(1.0F - f14, 1.0F - f14);
      glRotVertex2f(f6, f7, f12, f13, f8, -f9);
      gl.End();
    }
    Main3D.cur3D().setRenderIndx(0);
  }

  private static void glRotVertex2f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    gl.Vertex2f(paramFloat1 + (paramFloat5 * paramFloat3 - paramFloat6 * paramFloat4), paramFloat2 + (paramFloat5 * paramFloat4 + paramFloat6 * paramFloat3) * 1.333333F);
  }
}