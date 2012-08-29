package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import java.util.Locale;

public class GUIRoot extends GWindowRoot
{
  GTexture background;
  GTexture backgroundCountry = null;

  public void setBackCountry(String paramString1, String paramString2) {
    if ((paramString1 == null) || (paramString2 == null)) {
      this.backgroundCountry = null;
    } else {
      String str1 = RTSConf.cur.locale.getLanguage();
      String str2 = null;
      if ((!"us".equalsIgnoreCase(str1)) && (!"en".equalsIgnoreCase(str1))) {
        str2 = "missions/" + paramString1 + "/" + paramString2 + "/background_" + str1 + ".mat";
        if (!existSFSFile(str2))
          str2 = null;
      }
      if (str2 == null)
        str2 = "missions/" + paramString1 + "/" + paramString2 + "/background.mat";
      Object localObject = FObj.Get(str2);
      if (localObject != null)
        this.backgroundCountry = GTexture.New(str2);
    }
  }

  public void render() {
    if (RendersMain.getRenderFocus() == (Render)Actor.getByName("renderGUI")) {
      setCanvasColorWHITE();
      if (this.backgroundCountry != null)
        draw(0.0F, 0.0F, this.win.dx, this.win.dy, this.backgroundCountry);
      else
        draw(0.0F, 0.0F, this.win.dx, this.win.dy, this.background);
    }
  }

  public void created() {
    this.background = GTexture.New("GUI/background.mat");

    String str1 = null;
    String str2 = RTSConf.cur.locale.getLanguage();
    if ((!"us".equalsIgnoreCase(str2)) && (!"en".equalsIgnoreCase(str2))) {
      str1 = "missions/background_" + RTSConf.cur.locale.getLanguage() + ".mat";
      if (!existSFSFile(str1))
        str1 = null;
    }
    if (str1 == null)
      str1 = "missions/background.mat";
    Object localObject = FObj.Get(str1);
    if (localObject != null)
      this.background = GTexture.New(str1);
    super.created();
  }

  private boolean existSFSFile(String paramString) {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
      return true; } catch (Exception localException) {
    }
    return false;
  }
}