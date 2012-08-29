package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgInt;

public class GUISwitch3 extends GUISwitchN
{
  static GTexRegion[] st = new GTexRegion[3];

  private static void init() { if (Config.isUSE_RENDER()) {
      if (st[0] != null)
        return;
      GTexture localGTexture = GTexture.New("GUI/game/buttons.mat");
      st[0] = new GTexRegion(localGTexture, 0.0F, 176.0F, 48.0F, 80.0F);
      st[1] = new GTexRegion(localGTexture, 48.0F, 176.0F, 48.0F, 80.0F);
      st[2] = new GTexRegion(localGTexture, 96.0F, 176.0F, 48.0F, 80.0F);
    }
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
    int i = this.jdField_state_of_type_Int;
    if (this.jdField_states_of_type_Int == 2) {
      i = (i + 1) % 2;
    }
    else if (paramFloat2 > this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F) {
      i = this.jdField_state_of_type_Int - 1;
      if (i < 0) i = this.jdField_state_of_type_Int; 
    }
    else {
      i = this.jdField_state_of_type_Int + 1;
      if (i >= this.jdField_states_of_type_Int) i = this.jdField_state_of_type_Int;
    }

    setState(i, true);
  }

  public void render() {
    setCanvasColorWHITE();
    draw(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, st[this.pos[this.jdField_state_of_type_Int]]);
  }

  public void created() {
    this.texDx = 48.0F;

    this.texDy = 80.0F;
    resolutionChanged();
  }

  public GUISwitch3(GWindow paramGWindow, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean) {
    super(paramGWindow, 0.0F, 30.0F, paramArrayOfInt, paramArrayOfBoolean);
    init();
  }
  public GUISwitch3(GWindow paramGWindow, int[] paramArrayOfInt, CfgInt paramCfgInt, boolean paramBoolean) {
    super(paramGWindow, 0.0F, 30.0F, paramArrayOfInt, paramCfgInt, paramBoolean);
    init();
  }
}