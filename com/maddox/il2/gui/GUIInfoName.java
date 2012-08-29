package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;

public class GUIInfoName extends GUIInfoTop
{
  public static String nickName = null;

  public void render() {
    super.render();
    setCanvasColor(GColor.Gray);
    setCanvasFont(0);
    UserCfg localUserCfg = World.cur().userCfg;
    if (nickName != null)
      draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - M(2.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, 2, nickName);
    else
      draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - M(2.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, 2, localUserCfg.name + " '" + localUserCfg.callsign + "' " + localUserCfg.surname);
  }

  public void setPosSize() {
    set1024PosSize(300.0F, 0.0F, 724.0F, 32.0F);
  }
}