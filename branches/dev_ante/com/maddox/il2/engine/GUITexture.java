package com.maddox.il2.engine;

import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GTexture.Loader;

public class GUITexture extends GTexture
{
  public Mat mat = null;
  protected float scalex = 1.0F;
  protected float scaley = 1.0F;

  private GUITexture()
  {
  }

  GUITexture(1 param1)
  {
    this();
  }

  public static class _Loader extends GTexture.Loader
  {
    public GTexture load(String paramString)
    {
      GUITexture localGUITexture = new GUITexture(null);
      localGUITexture.mat = Mat.New(paramString);
      if (localGUITexture.mat != null) {
        localGUITexture.mat.setLayer(0);
        localGUITexture.jdField_size_of_type_ComMaddoxGwindowGSize = new GSize(localGUITexture.mat.get(2), localGUITexture.mat.get(3));
        localGUITexture.scalex = (1.0F / localGUITexture.jdField_size_of_type_ComMaddoxGwindowGSize.dx);
        localGUITexture.scaley = (1.0F / localGUITexture.jdField_size_of_type_ComMaddoxGwindowGSize.dy);
      } else {
        localGUITexture.jdField_size_of_type_ComMaddoxGwindowGSize = new GSize(1.0F, 1.0F);
      }
      return localGUITexture;
    }
  }
}