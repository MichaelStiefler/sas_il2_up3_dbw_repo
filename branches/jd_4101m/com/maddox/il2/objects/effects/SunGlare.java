package com.maddox.il2.objects.effects;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Time;

public class SunGlare extends Render
  implements MsgGLContextListener
{
  private int[] Tex = { 0 };
  private boolean curTexIsBW = false;
  private boolean wantBW = false;
  private int _indx;
  private long prevTm;
  private float curGlare;
  private float curSun;
  private static Vector3d rayDir = new Vector3d();

  private static Point3f resAngle = new Point3f();

  private static Vector3d axisX = new Vector3d();
  private static Vector3d axisY = new Vector3d();
  private static Vector3d axisZ = new Vector3d();

  private static Point3d tmpp0 = new Point3d();
  private static Point3d tmpp1 = new Point3d();
  private static Point3d tmpp2 = new Point3d();

  private static Vector3d tmpv0 = new Vector3d();
  private static Vector3d tmpv1 = new Vector3d();

  public SunGlare(int paramInt, float paramFloat)
  {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (paramInt == 0)
      setName("renderSunGlare");
    GLContext.getCurrent().msgAddListener(this, null);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }

  public void resetGame()
  {
    this.prevTm = Time.current();
    this.curGlare = 0.0F;
    this.curSun = 0.0F;
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

  public boolean isShow() {
    if (this._indx == 0) return super.isShow();
    return (Config.cur.isUse3Renders()) && (Main3D.cur3D().sunGlare.isShow());
  }

  public void setShow(boolean paramBoolean) {
    if (this._indx == 0)
      super.setShow(paramBoolean);
    this.prevTm = Time.current();
    this.curGlare = 0.0F;
    this.curSun = 0.0F;
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
      localTexImage.LoadTGA("effects/sunglare/glare01.tga_asis");
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

  private float computeIntegratedSunFlash(Point3f paramPoint3f)
  {
    rayDir.set(World.Sun().ToSun);
    axisZ.set(rayDir);
    if (Math.abs(axisZ.z) > 0.5D)
      axisX.set(1.0D, 0.0D, 0.0D);
    else {
      axisX.set(0.0D, 0.0D, 1.0D);
    }
    axisY.cross(axisZ, axisX);
    axisY.normalize();
    axisX.cross(axisY, axisZ);
    axisX.normalize();

    float f1 = 0.009F;
    float f2 = 0.0F;

    paramPoint3f.x = 0.0F;

    VisibilityChecker.checkLandObstacle = true;
    VisibilityChecker.checkCabinObstacle = true;
    VisibilityChecker.checkPlaneObstacle = true;
    VisibilityChecker.checkObjObstacle = true;

    for (int i = 0; i < 4; i++)
    {
      rayDir.set(World.Sun().ToSun);

      if (i < 3) {
        tmpv0.set(axisX);
        tmpv1.set(axisY);
        switch (i) { case 0:
          tmpv0.set(tmpv1);
          break;
        case 1:
          tmpv0.scale(Geom.cosDeg(30.0F));
          tmpv1.scale(-Geom.sinDeg(30.0F));
          tmpv0.add(tmpv1);
          break;
        case 2:
          tmpv0.scale(-Geom.cosDeg(30.0F));
          tmpv1.scale(-Geom.sinDeg(30.0F));
          tmpv0.add(tmpv1);
        }

        tmpv0.scale(f1);
        rayDir.add(tmpv0);
        rayDir.normalize();
      }

      float f3 = VisibilityChecker.computeVisibility(rayDir, null);
      if (f3 < 0.0F) {
        return f3;
      }
      if (f3 > f2) {
        f2 = f3;
      }
    }

    paramPoint3f.x = VisibilityChecker.resultAng;
    return f2;
  }

  public void render()
  {
    if ((RenderContext.cfgLandShading.get() < 2) && ((!Mission.isNet()) || (!World.cur().diffCur.Blackouts_N_Redouts)))
    {
      return;
    }

    Main3D.cur3D().setRenderIndx(this._indx);
    float f1 = computeIntegratedSunFlash(resAngle);
    if (f1 <= -2.0F) {
      Main3D.cur3D().setRenderIndx(0);
      return;
    }
    int i;
    float f2;
    float f3;
    float f5;
    if (f1 < 0.0F)
    {
      i = 0;
      f2 = 0.0F;
      f3 = 0.0F;
    } else {
      i = 1;

      float f4 = 50.0F;
      if (resAngle.x >= f4) {
        f2 = 0.0F;
      } else {
        f5 = resAngle.x / f4;
        f2 = 0.5F * (Geom.cosDeg(f5 * 180.0F) + 1.0F);
        if (f2 >= 1.0F) {
          f2 = 1.0F;
        }
        f2 *= f1;
        if (f2 < 0.007843138F) {
          f2 = 0.0F;
        }

      }

      f5 = 50.0F;
      if (resAngle.x >= f5) {
        f3 = 0.0F;
      } else {
        float f6 = resAngle.x / f5;
        f3 = 0.5F * (Geom.cosDeg(f6 * 180.0F) + 1.0F);
      }
      if (f1 > 0.003921569F) {
        f3 *= f1;

        f3 = 0.2F + 0.8F * f3;
        if (f3 >= 1.0F)
          f3 = 1.0F;
      }
      else {
        f3 = 0.0F;
      }

    }

    long l1 = Time.current();
    long l2 = l1 - this.prevTm;
    float f9;
    float f10;
    float f11;
    if (l2 < 0L)
    {
      this.curGlare = f2;
      this.curSun = f3;
    } else if (l2 != 0L)
    {
      f9 = f2 - this.curGlare;
      f10 = f9 >= 0.0F ? 3.0F : 1.5F;
      f11 = (float)l2 * f10 / 1000.0F;
      if (f11 >= Math.abs(f9))
        this.curGlare = f2;
      else {
        this.curGlare += (f9 < 0.0F ? -f11 : f11);
      }

      f9 = f3 - this.curSun;
      f10 = f9 >= 0.0F ? 6.0F : 4.0F;
      f11 = (float)l2 * f10 / 1000.0F;
      if (f11 >= Math.abs(f9))
        this.curSun = f3;
      else {
        this.curSun += (f9 < 0.0F ? -f11 : f11);
      }
    }

    this.prevTm = l1;

    if ((i != 0) && (this.curSun < 0.007843138F)) {
      i = 0;
    }

    int j = this.curGlare >= 0.007843138F ? 1 : 0;

    if ((i == 0) && (j == 0)) {
      Main3D.cur3D().setRenderIndx(0);
      return;
    }

    gl.ShadeModel(7425);
    gl.Disable(2929);
    gl.Enable(3042);
    gl.AlphaFunc(516, 0.0F);
    gl.Enable(3553);

    if (j != 0) {
      gl.Disable(3553);
      gl.BlendFunc(1, 770);
      gl.Begin(7);

      tmpp0.set(Engine.lightEnv().sun().Red, Engine.lightEnv().sun().Green, Engine.lightEnv().sun().Blue);

      tmpp1.set(1.0D, 1.0D, 1.0D);
      tmpp2.interpolate(tmpp0, tmpp1, 0.75F);
      tmpp2.scale(this.curGlare);

      tmpp2.scale(0.625D);

      gl.Color4f((float)tmpp2.x, (float)tmpp2.y, (float)tmpp2.z, 1.0F - this.curGlare * 0.5F);

      gl.Vertex2f(1.0F, 1.0F);
      gl.Vertex2f(1.0F, 0.0F);
      gl.Vertex2f(0.0F, 0.0F);
      gl.Vertex2f(0.0F, 1.0F);

      gl.End();
      gl.Enable(3553);
    }

    if (i != 0)
    {
      tmpp1.set(rayDir);
      tmpp1.scale(30000.0D);
      tmpp1.add(VisibilityChecker.nosePos);
      if (!Main3D.cur3D().project2d_norm(tmpp1.x, tmpp1.y, tmpp1.z, tmpp2)) {
        Main3D.cur3D().setRenderIndx(0);
        return;
      }

      f5 = ((float)tmpp2.x + 1.0F) * 0.5F;
      float f7 = ((float)tmpp2.y + 1.0F) * 0.5F;

      float f8 = this.curSun <= 0.4F ? 1.0F : 1.0F + 0.3F * ((this.curSun - 0.4F) / 0.6F);

      f9 = 0.25F * f8;
      f10 = f9;

      if ((f5 - f9 > 1.0F) || (f5 + f9 < 0.0F) || (f7 - f10 > 1.0F) || (f7 + f10 < 0.0F))
      {
        Main3D.cur3D().setRenderIndx(0);
        return;
      }

      f11 = Geom.tanDeg(Main3D.cur3D().camera3D.FOV() * 0.5F);
      float f12 = (float)tmpp2.x * f11;
      f12 *= -90.0F;

      gl.BindTexture(3553, this.Tex[0]);

      gl.BlendFunc(770, 1);
      gl.Begin(7);
      gl.Color4f(1.0F, 1.0F, 1.0F, this.curSun);

      float f13 = 0.0039063F;

      gl.TexCoord2f(1.0F - f13, 0.0F + f13);
      glRotVertex2f(f5, f7, f12, f9, f10);

      gl.TexCoord2f(0.0F + f13, 0.0F + f13);
      glRotVertex2f(f5, f7, f12, -f9, f10);

      gl.TexCoord2f(0.0F + f13, 1.0F - f13);
      glRotVertex2f(f5, f7, f12, -f9, -f10);

      gl.TexCoord2f(1.0F - f13, 1.0F - f13);
      glRotVertex2f(f5, f7, f12, f9, -f10);
      gl.End();
    }
    Main3D.cur3D().setRenderIndx(0);
  }

  private static void glRotVertex2f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    float f1 = Geom.cosDeg(paramFloat3);
    float f2 = Geom.sinDeg(paramFloat3);
    gl.Vertex2f(paramFloat1 + (paramFloat4 * f1 - paramFloat5 * f2), paramFloat2 + (paramFloat4 * f2 + paramFloat5 * f1) * 1.333333F);
  }
}