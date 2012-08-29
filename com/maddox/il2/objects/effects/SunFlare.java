package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.rts.Message;

public class SunFlare extends Actor
{
  private Point3d p = new Point3d();
  private Orient o = new Orient();

  private Point3d pcoll = new Point3d();
  private Orient ocoll = new Orient();
  private Mat[] mat;
  private SunFlareRender render;
  private static Point3d p1 = new Point3d();

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public void destroy() {
    if (this.render != null)
      this.render.setActor(null);
    super.destroy();
  }

  public SunFlare(Render paramRender)
  {
    this.draw = new Draw();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, new Loc());
    this.render = ((SunFlareRender)paramRender);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this.render.getCamera(), null, false);
    drawing(false);
    this.render.setActor(this);

    this.mat = new Mat[4];
    this.mat[0] = Mat.New("Effects/SunFlare/Flare0.mat");
    this.mat[1] = Mat.New("Effects/SunFlare/Flare1.mat");
    this.mat[2] = Mat.New("Effects/SunFlare/Flare2.mat");
    this.mat[3] = Mat.New("Effects/SunFlare/Flare3.mat");
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  public static Render newRender(int paramInt, float paramFloat, Camera paramCamera) {
    SunFlareRender localSunFlareRender = new SunFlareRender(paramInt, paramFloat);
    localSunFlareRender.setCamera(paramCamera);
    return localSunFlareRender;
  }

  class Draw extends ActorDraw
  {
    private Point3d sun = new Point3d();
    private Point3d center = new Point3d();

    private Point3d startp = new Point3d();
    private Point3d endp = new Point3d();

    private Vector3d rayDir = new Vector3d();

    int[] material = { 3, 1, 1, 3, 0, 1, 3, 0, 0, 0, 0, 3, 0, 3, 2, 1, 2, 3, 3 };

    float[] frac = { 0.4F, 0.7F, 0.65F, 0.6F, 0.5F, -0.45F, -0.5F, -0.6F, -1.3F, -1.35F, -1.5F, -1.6F, -1.7F, -1.9F, -2.3F, -2.5F, -2.9F, -3.0F, -5.0F };

    float[] radius = { 0.4F, 1.2F, 1.55F, 0.45F, 1.4F, 2.2F, 1.25F, 1.4F, 0.8F, 0.2F, 0.7F, 0.5F, 1.35F, 0.4F, 1.35F, 1.25F, 1.6F, 1.8F, 0.4F };

    float[] alpha = { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };

    float[] rgb_r = { 0.13F, 0.06F, 0.16F, 0.24F, 0.2F, 0.06F, 0.24F, 0.2F, 0.06F, 0.13F, 0.2F, 0.06F, 0.06F, 0.32F, 0.06F, 0.06F, 0.2F, 0.06F, 0.13F };
    float[] rgb_g = { 0.13F, 0.1F, 0.14F, 0.27F, 0.09F, 0.1F, 0.27F, 0.09F, 0.15F, 0.16F, 0.09F, 0.15F, 0.15F, 0.45F, 0.15F, 0.15F, 0.09F, 0.15F, 0.13F };
    float[] rgb_b = { 0.19F, 0.1F, 0.25F, 0.34F, 0.31F, 0.1F, 0.34F, 0.31F, 0.1F, 0.2F, 0.32F, 0.1F, 0.1F, 0.45F, 0.1F, 0.1F, 0.32F, 0.1F, 0.19F };

    Draw()
    {
    }

    public int preRender(Actor paramActor)
    {
      if (SunFlare.this.mat == null) {
        SunFlare.this.postDestroy();
        return 0;
      }
      SunFlare.this.pos.getAbs(SunFlare.this.pcoll, SunFlare.this.ocoll);
      Render.currentCamera().pos.getRender(SunFlare.this.p, SunFlare.this.o);

      return 2;
    }

    public void render(Actor paramActor) {
      draw();
    }

    private void draw()
    {
      if (!Main3D.cur3D().isViewOutside())
      {
        return;
      }

      this.center.set(500.0D, 0.0D, 0.0D);
      SunFlare.this.o.transform(this.center);
      this.center.add(SunFlare.this.p, this.center);

      this.sun.jdField_x_of_type_Double = (World.Sun().ToSun.x * 500.0F);
      this.sun.jdField_y_of_type_Double = (World.Sun().ToSun.y * 500.0F);
      this.sun.jdField_z_of_type_Double = (World.Sun().ToSun.z * 500.0F);
      this.sun.add(SunFlare.this.p, this.sun);

      if (!Render.currentCamera().isSphereVisible(this.sun, 1.0F))
      {
        return;
      }

      VisibilityChecker.checkLandObstacle = true;
      VisibilityChecker.checkCabinObstacle = false;
      VisibilityChecker.checkPlaneObstacle = true;
      VisibilityChecker.checkObjObstacle = true;

      this.rayDir.set(World.Sun().ToSun);

      float f = VisibilityChecker.computeVisibility(this.rayDir, null);
      if (f <= 0.0F)
      {
        return;
      }
      if (f >= 1.0F) {
        f = 1.0F;
      }

      for (int i = 0; i < 19; i++)
      {
        Render.drawSetMaterial(SunFlare.this.mat[this.material[i]]);

        Render.drawBeginSprites(0);

        SunFlare.p1.jdField_x_of_type_Double = (this.center.jdField_x_of_type_Double + (this.sun.jdField_x_of_type_Double - this.center.jdField_x_of_type_Double) * this.frac[i]);
        SunFlare.p1.jdField_y_of_type_Double = (this.center.jdField_y_of_type_Double + (this.sun.jdField_y_of_type_Double - this.center.jdField_y_of_type_Double) * this.frac[i]);
        SunFlare.p1.jdField_z_of_type_Double = (this.center.jdField_z_of_type_Double + (this.sun.jdField_z_of_type_Double - this.center.jdField_z_of_type_Double) * this.frac[i]);

        Render.drawPushSprite((float)SunFlare.p1.jdField_x_of_type_Double, (float)SunFlare.p1.jdField_y_of_type_Double, (float)SunFlare.p1.jdField_z_of_type_Double, this.radius[i] * 40.0F, this.rgb_r[i], this.rgb_g[i], this.rgb_b[i], this.alpha[i] * f, 0.0F);

        Render.drawEnd();
      }
    }
  }
}