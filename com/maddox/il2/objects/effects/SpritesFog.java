package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.rts.Message;

public class SpritesFog extends Actor
{
  public static final boolean bUseAsGetAlpha = true;
  public static float dynamicFogAlpha = 0.0F;
  public static final float HEIGHT_MIN = 0.0F;
  public static final float HEIGHT_MAX = 4000.0F;
  private Mat mat;
  private float zMin;
  private float zMax;
  private static final float DZ = 50.0F;
  private static final float U0 = 0.1F;
  private static final float U1 = 0.2F;
  private static final float U2 = 0.8F;
  private int rgb = 16777215;
  private Point3d p = new Point3d();
  private Orient o = new Orient();

  private static Point3d p1 = new Point3d();
  protected static SpritesFog actor = null;
  protected static SpritesFogRender render = null;

  public boolean isShow()
  {
    return render.isShow(); } 
  public void setShow(boolean paramBoolean) { render.setShow(paramBoolean); }

  public void setHeight(float paramFloat1, float paramFloat2) {
    this.zMin = (paramFloat1 + 50.0F);
    this.zMax = (paramFloat2 - 50.0F);
    if (this.zMin >= this.zMax) {
      this.zMin = ((this.zMin + this.zMax) / 2.0F);
      this.zMax = (this.zMin + 1.0F);
    }
  }

  public float getHeightMin() {
    return this.zMin - 50.0F;
  }
  public float getHeightMax() {
    return this.zMax + 50.0F;
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public SpritesFog(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3) {
    this.draw = new Draw();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, new Loc());
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(paramActor, null, false);
    drawing(false);

    setHeight(paramFloat2, paramFloat3);
    this.mat = Mat.New("3do/Effects/SpritesFog/Fog.mat");

    actor = this;
    if (render == null)
      render = new SpritesFogRender(paramFloat1);
    else {
      render.setShow(true);
    }
    render.setCamera((Camera)paramActor);
    render.setName("renderSpritesFog");
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  class Draw extends ActorDraw
  {
    Draw()
    {
    }

    public int preRender(Actor paramActor)
    {
      if (SpritesFog.this.mat == null) {
        SpritesFog.this.postDestroy();
        return 0;
      }
      Render.currentCamera().pos.getRender(SpritesFog.this.p, SpritesFog.this.o);

      return SpritesFog.this.mat.preRender();
    }

    public void render(Actor paramActor) {
      float f = 1.0F;

      f = Landscape.getDynamicFogAlpha();
      SpritesFog.access$302(SpritesFog.this, Landscape.getDynamicFogRGB() & 0xFFFFFF);
      SpritesFog.dynamicFogAlpha = f;

      if (f < 0.01D)
        return;
      Render.drawSetMaterial(SpritesFog.this.mat, (float)SpritesFog.this.p.jdField_x_of_type_Double, (float)SpritesFog.this.p.jdField_y_of_type_Double, (float)SpritesFog.this.p.jdField_z_of_type_Double, 30.0F);
      Render.drawBeginSprites(0);
      draw(69, 83.0F, 1, f);
      Render.drawEnd();
    }

    private void draw(int paramInt1, float paramFloat1, int paramInt2, float paramFloat2) {
      float f1 = paramInt1 * paramInt2 + paramInt1 / 2;
      float f2 = (int)(SpritesFog.this.p.jdField_x_of_type_Double / paramInt1 + 0.5D) * paramInt1;
      float f3 = (int)(SpritesFog.this.p.jdField_y_of_type_Double / paramInt1 + 0.5D) * paramInt1;
      float f4 = (int)(SpritesFog.this.p.jdField_z_of_type_Double / paramInt1 + 0.5D) * paramInt1;
      for (int i = -paramInt2; i <= paramInt2; i++)
        for (int j = -paramInt2; j <= paramInt2; j++)
          for (int k = -paramInt2; k <= paramInt2; k++) {
            float f5 = f2 + paramInt1 * k;
            float f6 = f3 + paramInt1 * j;
            float f7 = f4 + paramInt1 * i;
            SpritesFog.p1.set(f1, 0.0D, 0.0D);
            SpritesFog.this.o.transform(SpritesFog.p1);
            SpritesFog.p1.add(SpritesFog.this.p, SpritesFog.p1);
            float f8 = (float)(SpritesFog.p1.jdField_x_of_type_Double - SpritesFog.this.p.jdField_x_of_type_Double);
            float f9 = (float)(SpritesFog.p1.jdField_y_of_type_Double - SpritesFog.this.p.jdField_y_of_type_Double);
            float f10 = (float)(SpritesFog.p1.jdField_z_of_type_Double - SpritesFog.this.p.jdField_z_of_type_Double);
            float f11 = f8 * f8 + f9 * f9 + f10 * f10;
            float f12 = ((f5 - (float)SpritesFog.this.p.jdField_x_of_type_Double) * f8 + (f6 - (float)SpritesFog.this.p.jdField_y_of_type_Double) * f9 + (f7 - (float)SpritesFog.this.p.jdField_z_of_type_Double) * f10) / f11;
            if ((f12 >= 0.1F) && (f12 <= 1.0F)) {
              float f13 = paramFloat2;
              if (f12 < 0.2F) f13 *= (f12 - 0.1F) / 0.1F;
              else if (f12 > 0.8F) f13 *= (1.0F - (f12 - 0.8F) / 0.2F);

              Render.drawPushSprite(f5, f6, f7, paramFloat1, SpritesFog.this.rgb, f13 * 0.5F, 0.0F);
            }
          }
    }
  }
}