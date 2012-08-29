package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIDialogClient extends GWindowDialogClient
{
  static GTexture texture;
  static GTexture detail;
  static GTexRegion[] bolt = new GTexRegion[8];
  static GBevel bevel;
  static GRegion region = new GRegion();

  private static void init() {
    if (Config.isUSE_RENDER()) {
      if (texture != null)
        return;
      texture = GTexture.New("GUI/game/basicelements.mat");
      detail = GTexture.New("GUI/game/detail.mat");
      GTexture localGTexture = GTexture.New("GUI/game/staticelements.mat");
      for (int i = 0; i < 8; i++) {
        bolt[i] = new GTexRegion(localGTexture, i * 16, 32.0F, 16.0F, 16.0F);
      }
      bevel = new GBevel();
      bevel.set(new GRegion(224.0F, 192.0F, 32.0F, 32.0F), new GRegion(239.0F, 207.0F, 2.0F, 2.0F));
    }
  }

  public GUIDialogClient() {
    init();
  }

  public float M(float paramFloat) {
    return lookAndFeel().metric(paramFloat);
  }

  public void render() {
    setCanvasColorWHITE();
    lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, bevel, texture);

    draw(3.0F, 3.0F, this.win.dx - 6.0F, this.win.dy - 6.0F, detail, 0.0F, 0.0F, this.win.dx - 6.0F, this.win.dy - 6.0F);

    region.set(bevel.L.dx - bolt[0].dx * 0.5F, bevel.T.dy - bolt[0].dy * 0.5F, this.win.dx - bevel.L.dx - bevel.R.dx + bolt[0].dx * 0.25F, this.win.dy - bevel.T.dy - bevel.B.dy + bolt[0].dy * 0.25F);

    int i = Math.round(region.dx / 156.0F);
    if (i < 1) i = 1;
    float f1 = region.dx / i;
    int j = Math.round(region.dy / 156.0F);
    if (j < 1) j = 1;
    float f2 = region.dy / j;
    int m = 0;
    for (int k = 0; k <= i; k++) {
      draw(Math.round(region.x + k * f1), Math.round(region.y), bolt[m]);

      m = (m + 1) % 8;
      draw(Math.round(region.x + k * f1), Math.round(region.y + region.dy), bolt[m]);

      m = (m + 1) % 8;
    }
    for (k = 1; k < j; k++) {
      draw(Math.round(region.x), Math.round(region.y + k * f2), bolt[m]);

      m = (m + 1) % 8;
      draw(Math.round(region.x + region.dx), Math.round(region.y + k * f2), bolt[m]);

      m = (m + 1) % 8;
    }
  }

  public void setPosSize() {
    set1024Pos(20.0F, 20.0F);
    set1024Size(984.0F, 728.0F);
  }

  public void resolutionChanged() {
    super.resolutionChanged();
    setPosSize();
  }
}