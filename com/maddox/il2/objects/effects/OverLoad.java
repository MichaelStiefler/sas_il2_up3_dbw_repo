package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point2f;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.sound.AudioDevice;

public class OverLoad extends Render
  implements MsgGLContextListener
{
  int _indx = 0;

  private int[] Tex = { 0 };
  private static final int N = 16;
  private static Point2f[] pnts = new Point2f[17];
  private static Point2f[] tnts = new Point2f[17];
  private static final float O_MIN = 0.2F;
  private static final float O_MAX = 0.7F;

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

  protected void contextResize(int paramInt1, int paramInt2) {
  }

  public void preRender() {
    if (this.Tex[0] == 0) {
      gl.Enable(3553);
      gl.GenTextures(1, this.Tex);
      TexImage localTexImage = new TexImage();
      try {
        localTexImage.LoadTGA("3do/effects/overload/overload.tga"); } catch (Exception localException) {
        return;
      }gl.BindTexture(3553, this.Tex[0]);
      gl.TexParameteri(3553, 10242, 10497);
      gl.TexParameteri(3553, 10243, 10497);
      gl.TexParameteri(3553, 10240, 9729);
      gl.TexParameteri(3553, 10241, 9729);
      gl.TexImage2D(3553, 0, 6409, localTexImage.sx, localTexImage.sy, 0, 6409, 5121, localTexImage.image);
    }
  }

  public OverLoad(int paramInt, float paramFloat)
  {
    super(paramFloat);
    this._indx = paramInt;
    useClearDepth(false);
    useClearColor(false);
    if (this._indx == 0)
      setName("renderOverLoad");
    GLContext.getCurrent().msgAddListener(this, null);
    if (paramInt != 0)
      Main3D.cur3D()._getAspectViewPort(paramInt, this.viewPort);
  }

  private static final float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(paramFloat3, Math.max(paramFloat2, paramFloat1));
  }

  public void render()
  {
    if (!World.cur().diffCur.Blackouts_N_Redouts) return;

    if (!(World.getPlayerFM() instanceof RealFlightModel)) {
      if ((World.getPlayerFM() != null) && (Main3D.cur3D().cockpitCur != null)) {
        float f1 = 1.0F;
        f1 = World.getPlayerFM().AS.getPilotHealth(Main3D.cur3D().cockpitCur.astatePilotIndx());
        f2 = 1.0F - f1;

        Render.clearStates();
        gl.ShadeModel(7425);
        gl.Disable(2929);
        gl.Enable(3553);
        gl.Enable(3042);
        gl.AlphaFunc(516, 0.0F);
        renderMinus(f2);
      }
      return;
    }

    RealFlightModel localRealFlightModel = (RealFlightModel)World.getPlayerFM();
    if (localRealFlightModel == null) {
      return;
    }

    float f2 = 1.0F;
    if (Main3D.cur3D().cockpitCur != null) {
      f2 = localRealFlightModel.AS.getPilotHealth(Main3D.cur3D().cockpitCur.astatePilotIndx());
    }
    float f3 = 1.0F - f2;
    if ((localRealFlightModel.saveDeep < 0.02F) && (localRealFlightModel.saveDeep > -0.02F) && (f3 < 0.02D)) return;

    Render.clearStates();
    gl.ShadeModel(7425);
    gl.Disable(2929);
    gl.Enable(3553);
    gl.Enable(3042);
    gl.AlphaFunc(516, 0.0F);

    if (localRealFlightModel.saveDeep >= 0.02F) {
      renderPlus(localRealFlightModel.saveDeep);
      renderSound(localRealFlightModel.saveDeep * 0.66F);
      if (f3 >= 0.02D)
        renderMinus(f3);
    }
    else if (localRealFlightModel.saveDeep <= -0.02F) {
      renderMinus(-localRealFlightModel.saveDeep + f3);
      renderSound(-localRealFlightModel.saveDeep * 1.35F);
    }
    else {
      renderMinus(f3);
    }
  }

  private void renderSound(float paramFloat)
  {
    if (this._indx != 0) return;
    paramFloat = Math.abs(paramFloat);
    if (paramFloat > 0.7F) paramFloat = 1.0F;
    else if (paramFloat < 0.2F) paramFloat = 0.0F; else
      paramFloat = (paramFloat - 0.2F) / 0.5F;
    AudioDevice.setControl(2000, (int)(paramFloat * 100.0F));
  }

  public void setShow(boolean paramBoolean) {
    if (this._indx != 0) return;
    super.setShow(paramBoolean);
    if (!paramBoolean) renderSound(0.0F); 
  }

  public boolean isShow() {
    if (this._indx == 0) return super.isShow();
    return (Config.cur.isUse3Renders()) && (Main3D.cur3D().overLoad.isShow());
  }

  private void renderPlus(float paramFloat)
  {
    gl.BindTexture(3553, this.Tex[0]);
    gl.BlendFunc(774, 770);

    if (paramFloat >= 0.97F) paramFloat = 0.97F;
    paramFloat *= 3.0F;
    float f1;
    float f2;
    float f3;
    float f4;
    if (paramFloat <= 1.0F) {
      f1 = 0.0F; f2 = 1.0F;
      f3 = paramFloat; f4 = 1.0F - paramFloat;
    } else if (paramFloat <= 2.0F) {
      paramFloat -= 1.0F;
      f1 = 1.0F * paramFloat; f2 = 1.0F - paramFloat * 0.5F;
      f3 = 1.0F - paramFloat; f4 = 0.0F;
    } else {
      paramFloat -= 2.0F; if (paramFloat > 1.0F) paramFloat = 1.0F;
      f1 = 1.0F - paramFloat; f2 = 0.5F - paramFloat * 0.5F;
      f3 = 0.0F; f4 = 0.0F;
    }
    if (this._indx != 0) {
      f1 = f3;
      f2 = f4;
    }
    gl.Begin(6);
    gl.Color4f(f1, f1, f1, f2);
    gl.TexCoord2f(0.0F, 0.0F);
    Vertex2f(0.0F, 0.0F);

    gl.Color4f(f3, f3, f3, f4);
    for (int i = 0; i <= 16; i++) {
      gl.TexCoord2f(tnts[i].x, tnts[i].y);
      Vertex2f(pnts[i].x, pnts[i].y);
    }
    gl.End();

    gl.BlendFunc(770, 771);
  }

  private void renderMinus(float paramFloat)
  {
    paramFloat = clamp(paramFloat, 0.0F, 1.0F);
    gl.Disable(3553);
    gl.BlendFunc(770, 771);

    gl.Begin(6);
    gl.Color4f(1.0F - paramFloat, 0.0F, 0.0F, paramFloat);
    Vertex2f(0.0F, 0.0F);

    for (int i = 0; i <= 16; i++) {
      Vertex2f(pnts[i].x, pnts[i].y);
    }
    gl.End();

    gl.Enable(3553);
  }

  private void Vertex2f(float paramFloat1, float paramFloat2) {
    gl.Vertex2f((paramFloat1 + 1.0F) * 0.5F, (paramFloat2 + 1.0F) * 0.5F);
  }

  static
  {
    for (int i = 0; i <= 16; i++) {
      pnts[i] = new Point2f();
      tnts[i] = new Point2f();
      double d = i * 2.0F * 3.141592653589793D / 16.0D;
      pnts[i].x = ((float)Math.cos(d) * 1.48F);
      pnts[i].y = ((float)Math.sin(d) * 1.48F);
      pnts[i].x *= 3.5F;
      pnts[i].y *= 3.5F;
    }
  }
}