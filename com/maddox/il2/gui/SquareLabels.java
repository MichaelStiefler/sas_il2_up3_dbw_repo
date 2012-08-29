package com.maddox.il2.gui;

import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;

public class SquareLabels
{
  private static TTFont _squareLabelsFont;
  private static Mat _squareLabelsMat;

  public static void draw(CameraOrtho2D paramCameraOrtho2D, double paramDouble)
  {
    draw(paramCameraOrtho2D, 0.0D, 0.0D, paramDouble);
  }
  public static void draw(CameraOrtho2D paramCameraOrtho2D, double paramDouble1, double paramDouble2, double paramDouble3) {
    if (_squareLabelsFont == null)
      _squareLabelsFont = TTFont.font[0];
    if (_squareLabelsMat == null) {
      _squareLabelsMat = Mat.New("icons/empty.mat");
    }
    int i = 10000;
    int j = (int)((paramCameraOrtho2D.worldXOffset + paramDouble1) / i + 0.5D);
    int k = (int)((paramCameraOrtho2D.worldYOffset + paramDouble2) / i + 0.5D);
    int m = (int)((paramCameraOrtho2D.right - paramCameraOrtho2D.left) / paramCameraOrtho2D.worldScale / i) + 2;
    int n = (int)((paramCameraOrtho2D.top - paramCameraOrtho2D.bottom) / paramCameraOrtho2D.worldScale / i) + 2;
    float f1 = (float)((j * i + 0.5D * i - paramCameraOrtho2D.worldXOffset - paramDouble1) * paramCameraOrtho2D.worldScale + 0.5D);
    float f2 = (float)((k * i + 0.5D * i - paramCameraOrtho2D.worldYOffset - paramDouble2) * paramCameraOrtho2D.worldScale + 0.5D);
    float f3 = (float)(i * paramCameraOrtho2D.worldScale);
    int i1 = paramDouble3 / i + 1.0D > 26.0D ? 1 : 0;
    float f4 = _squareLabelsFont.height() - _squareLabelsFont.descender();
    float f5 = 0.0F;
    for (int i2 = 0; i2 < 2; i2++)
    {
      float f9;
      for (int i3 = 0; i3 <= m; i3++) {
        float f6 = f1 + i3 * f3;
        String str1;
        if (i1 != 0) {
          int i5 = j + i3;
          str1 = "" + (char)(65 + i5 / 26) + (char)(65 + i5 % 26);
        } else {
          str1 = "" + (char)(65 + (j + i3));
        }
        float f8 = _squareLabelsFont.width(str1);
        if ((i3 > 0) && (f6 - f8 / 2.0F < f5))
          continue;
        f5 = f6 + f8 / 2.0F + 2.0F;
        f9 = paramCameraOrtho2D.top - paramCameraOrtho2D.bottom - f4 + _squareLabelsFont.descender();
        if (i2 == 0)
          Render.drawTile(f6 - f8, f9 + _squareLabelsFont.descender(), 2.0F * f8, f4, 0.0F, _squareLabelsMat, -4144960, 0.0F, 0.0F, 1.0F, 1.0F);
        else
          _squareLabelsFont.output(-16777216, f6 - f8 / 2.0F, f9, 0.0F, str1);
      }
      for (int i4 = 0; i4 <= n; i4++) {
        float f7 = f2 + i4 * f3 - f4 / 2.0F;
        String str2 = "" + (k + i4 + 1);
        f9 = _squareLabelsFont.width(str2);
        if ((i4 > 0) && (f7 < f5))
          continue;
        f5 = f7 + f4;
        float f10 = paramCameraOrtho2D.right - paramCameraOrtho2D.left - 2.0F - f9 + _squareLabelsFont.descender();
        if (i2 == 0)
          Render.drawTile(f10 - f9 / 2.0F, f7 + _squareLabelsFont.descender(), 2.0F * f9, f4, 0.0F, _squareLabelsMat, -4144960, 0.0F, 0.0F, 1.0F, 1.0F);
        else
          _squareLabelsFont.output(-16777216, f10, f7, 0.0F, str2);
      }
      if (i2 == 0)
        Render.drawEnd();
    }
  }
}