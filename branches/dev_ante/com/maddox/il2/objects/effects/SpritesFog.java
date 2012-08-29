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
  public static final float HEIGHT_MAX = 6000.0F;
  private Mat mat;
  private float zMin;
  private float zMax;
  private static final float DZ = 50.0F;
  private static final float U0 = 0.1F;
  private static final float U1 = 0.2F;
  private static final float U2 = 0.8F;
  private int rgb;
  private Point3d p;
  private Orient o;
  private static Point3d p1 = new Point3d();
  protected static SpritesFog actor = null;
  protected static SpritesFogRender render = null;

  public boolean isShow()
  {
    return render.isShow();
  }

  public void setShow(boolean flag) {
    render.setShow(flag);
  }

  public void setHeight(float f, float f1) {
    this.zMin = (f + 200.0F);
    this.zMax = (f1 - 200.0F);
    if (this.zMin >= this.zMax) {
      this.zMin = ((this.zMin + this.zMax) / 2.0F);
      this.zMax = (this.zMin + 1.0F);
    }
  }

  public float getHeightMin() {
    return this.zMin - 200.0F;
  }

  public float getHeightMax() {
    return this.zMax + 200.0F;
  }

  public Object getSwitchListener(Message message) {
    return this;
  }

  public SpritesFog(Actor actor1, float f, float f1, float f2) {
    this.rgb = 16777215;
    this.p = new Point3d();
    this.o = new Orient();
    this.draw = new Draw();
    this.pos = new ActorPosMove(this, new Loc());
    this.pos.setBase(actor1, null, false);
    drawing(false);
    setHeight(f1, f2);
    this.mat = Mat.New("3do/Effects/SpritesFog/Fog.mat");
    actor = this;
    if (render == null)
      render = new SpritesFogRender(f);
    else
      render.setShow(true);
    render.setCamera((Camera)actor1);
    render.setName("renderSpritesFog");
  }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  class Draw extends ActorDraw
  {
    public int preRender(Actor actor1)
    {
      if (SpritesFog.this.mat == null) {
        SpritesFog.this.postDestroy();
        return 0;
      }
      Render.currentCamera().pos.getRender(SpritesFog.this.p, SpritesFog.this.o);
      return SpritesFog.this.mat.preRender();
    }

    public void render(Actor actor1)
    {
      float f = 1.0F;
      f = Landscape.getDynamicFogAlpha();
      SpritesFog.this.rgb = (Landscape.getDynamicFogRGB() & 0xFFFFFF);
      SpritesFog.dynamicFogAlpha = f;
      if (f < 0.01D) {
        return;
      }
      Render.drawSetMaterial(SpritesFog.this.mat, (float)SpritesFog.this.p.x, (float)SpritesFog.this.p.y, 
        (float)SpritesFog.this.p.z, 30.0F);
      Render.drawBeginSprites(0);
      draw(69, 83.0F, 1, f);
      Render.drawEnd();
    }

    private void draw(int i, float f, int j, float f1)
    {
      float f2 = i * j + i / 2;
      float f3 = (int)(SpritesFog.this.p.x / i + 0.5D) * i;
      float f4 = (int)(SpritesFog.this.p.y / i + 0.5D) * i;
      float f5 = (int)(SpritesFog.this.p.z / i + 0.5D) * i;
      for (int k = -j; k <= j; k++)
        for (int l = -j; l <= j; l++)
          for (int i1 = -j; i1 <= j; i1++) {
            float f6 = f3 + i * i1;
            float f7 = f4 + i * l;
            float f8 = f5 + i * k;
            SpritesFog.p1.set(f2, 0.0D, 0.0D);
            SpritesFog.this.o.transform(SpritesFog.p1);
            SpritesFog.p1.add(SpritesFog.this.p, SpritesFog.p1);
            float f9 = (float)(SpritesFog.p1.x - SpritesFog.this.p.x);
            float f10 = (float)(SpritesFog.p1.y - SpritesFog.this.p.y);
            float f11 = (float)(SpritesFog.p1.z - SpritesFog.this.p.z);
            float f12 = f9 * f9 + f10 * f10 + f11 * f11;
            float f13 = ((f6 - (float)SpritesFog.this.p.x) * f9 + 
              (f7 - (float)SpritesFog.this.p.y) * f10 + (f8 - (float)SpritesFog.this.p.z) * 
              f11) / 
              f12;
            if ((f13 >= 0.1F) && (f13 <= 1.0F)) {
              float f14 = f1;
              if (f13 < 0.2F)
                f14 *= (f13 - 0.1F) / 0.1F;
              else if (f13 > 0.8F)
                f14 *= (1.0F - (f13 - 0.8F) / 0.2F);
              Render.drawPushSprite(f6, f7, f8, f, SpritesFog.this.rgb, 
                f14 * 0.5F, 0.0F);
            }
          }
    }

    Draw()
    {
    }
  }
}