package com.maddox.il2.gui;

import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;

public class LogoIcons extends Render
{
  private static final boolean bFixedSize = false;
  private Mat matIcon1;
  private Mat matIcon2;
  private int size;

  public void preRender()
  {
    this.matIcon1.preRender();
    this.matIcon2.preRender();
  }

  public void render() {
    Render.prepareStates();
    float f1 = getViewPortWidth();

    float f2 = getViewPortWidth() / 10.0F;
    float f3 = getViewPortHeight() / 7.5F;
    Render.drawTile(f1 - f2 - 0.1F * f2, 0.1F * f3, f2, f3, 0.0F, this.matIcon1, -1, 0.0F, 1.0F, 1.0F, -1.0F);

    Render.drawTile(f1 - f2 - 0.1F * f2, 0.2F * f3 + f3, f2, f3, 0.0F, this.matIcon2, -1, 0.0F, 1.0F, 1.0F, -1.0F);
  }

  public LogoIcons(float paramFloat, int paramInt)
  {
    super(paramFloat);
    this.size = paramInt;
    useClearDepth(false);
    useClearColor(false);
    CameraOrtho2D localCameraOrtho2D = new CameraOrtho2D();
    localCameraOrtho2D.setName("cameraLogoIcons");
    localCameraOrtho2D.set(0.0F, getViewPortWidth(), 0.0F, getViewPortHeight());
    setCamera(localCameraOrtho2D);
    setShow(true);
    setName("renderLogoIcons");
    this.matIcon1 = Mat.New("3do/gui/LogoIcons/maddox.mat");
    this.matIcon2 = Mat.New("3do/gui/LogoIcons/il2.mat");
  }
}