package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;

public class GUINetDifficulty extends GUIDifficulty
{
  public void enterPush(GameState paramGameState)
  {
    if (Main.cur().netServerParams.isMirror()) {
      this.bEnable = false;
    }
    else if (paramGameState.id() == 69)
      this.bEnable = (GUINetServerNGenSelect.cur.missions == 0);
    else {
      this.bEnable = (paramGameState.id() == 38);
    }

    _enter();
  }

  public void _enter() {
    super._enter();
  }

  protected DifficultySettings settings() {
    return World.cur().diffCur;
  }

  public GUINetDifficulty(GWindowRoot paramGWindowRoot) {
    super(paramGWindowRoot, 41);
  }
}