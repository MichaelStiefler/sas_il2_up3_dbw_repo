package com.maddox.il2.gui;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GWin95LookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.ai.World;
import com.maddox.il2.builder.Builder;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.GUIWindowManager._Render;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;

public class GUIBuilder extends GameState
{
  public static final String envName = "builder";
  public GWindowRootMenu rootWindow;
  public GUIWindowManager guiManager;
  public Builder builder;

  public void _enter()
  {
    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    localGUIWindowManager.unActivateAll();
    RendersMain.setRenderFocus(null);
    localGUIWindowManager.render.setShow(false);

    this.guiManager.activateTime(true);
    this.guiManager.activateKeyboard(true);
    this.guiManager.activateMouse(true);
    Time.setPause(true);
    this.guiManager.activateJoy(true);
    Time.setRealOnly(true);
    this.guiManager.render.setShow(true);

    World.cur().diffCur.NewCloudsRender = Config.cur.newCloudsRender;

    this.builder.enter();
    Main3D.cur3D(); Main3D.cur3D().enableOnlyHotKeyCmdEnvs(Main3D.builderHotKeyCmdEnvs);
    CmdEnv.top().exec("music PUSH");
    CmdEnv.top().exec("music STOP");
  }

  public void _leave() {
    this.builder.leave();

    Time.setRealOnly(false);
    this.guiManager.unActivateAll();
    this.guiManager.render.setShow(false);

    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    localGUIWindowManager.activateTime(true);
    localGUIWindowManager.activateKeyboard(true);
    localGUIWindowManager.activateMouse(true);
    Time.setPause(true);
    localGUIWindowManager.activateJoy(true);
    localGUIWindowManager.render.setShow(true);
    RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
    Main3D.cur3D().disableAllHotKeyCmdEnv();
    CmdEnv.top().exec("music POP");
    CmdEnv.top().exec("music PLAY");
  }

  public GUIBuilder(GWindowRoot paramGWindowRoot) {
    super(18);
    this.rootWindow = new GWindowRootMenu();
    this.guiManager = new GUIWindowManager(-2.5F, this.rootWindow, new GWin95LookAndFeel(), "renderGUIBuilder");
    this.rootWindow.jdField_textFonts_of_type_ArrayOfComMaddoxGwindowGFont[0] = GFont.New("arial8");
    this.rootWindow.jdField_textFonts_of_type_ArrayOfComMaddoxGwindowGFont[1] = GFont.New("arialb8");
    ((GWin95LookAndFeel)this.rootWindow.lAF()).metric = (int)(this.rootWindow.jdField_textFonts_of_type_ArrayOfComMaddoxGwindowGFont[0].height + 0.5F);
    this.rootWindow.resized();
    this.builder = new Builder(this.rootWindow, "builder");
    this.guiManager.render.setShow(false);
  }
}