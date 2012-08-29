package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.Provider;
import com.maddox.rts.IniFile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.ScreenMode;
import java.util.ArrayList;

public class GUISetupVideo extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUISwitchN sFull;
  public GUISwitchBox3 sStencil;
  public GUIButton bApply;
  public GUIButton bExit;
  public GTexRegion texFull;
  public GWindowComboControl comboProvider;
  public GWindowComboControl comboResolution;
  public ArrayList providerName = new ArrayList();
  public ArrayList providerDll = new ArrayList();
  public String enterCmdLine;
  public boolean enterSaveAspect;
  public boolean enterUse3Renders;
  public int[] screenModesWindow = { 640, 800, 1024 };
  public ArrayList screenModes = new ArrayList();
  private boolean bCmdLineUse3Renders;

  public int _findVideoMode(ScreenMode paramScreenMode, boolean paramBoolean)
  {
    for (int i = 0; i < this.screenModes.size(); i++) {
      ScreenMode localScreenMode = (ScreenMode)this.screenModes.get(i);
      if ((localScreenMode.width() != paramScreenMode.width()) || (localScreenMode.height() != paramScreenMode.height()) || (localScreenMode.colourBits() != paramScreenMode.colourBits())) continue; if (paramBoolean == (localScreenMode.ext != null))
      {
        return i;
      }
    }
    return 0;
  }
  public int _findVideoModeWindow(int paramInt1, int paramInt2) {
    for (int i = 0; i < paramInt2; i++) {
      if (this.screenModesWindow[i] == paramInt1)
        return i;
    }
    if (paramInt1 < this.screenModesWindow[0]) return 0;
    return paramInt2 - 1;
  }

  public void _enter() {
    if (this.screenModes.size() <= 0) {
      Main.stateStack().pop();
      return;
    }
    update();
    this.enterCmdLine = cmdLine();
    this.enterSaveAspect = Config.cur.windowSaveAspect;
    this.enterUse3Renders = Config.cur.windowUse3Renders;
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private void fillComboProvider() {
    String[] arrayOfString = Config.cur.ini.getVariables("GLPROVIDERS");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++) {
        String str2 = Config.cur.ini.get("GLPROVIDERS", arrayOfString[i], (String)null);
        if (str2 != null) {
          this.providerName.add(arrayOfString[i]);
          this.providerDll.add(str2);
        }
      }
    }
    String str1 = Provider.GLname();
    int j = -1;
    for (int k = 0; k < this.providerDll.size(); k++)
      if (((String)this.providerDll.get(k)).compareToIgnoreCase(str1) == 0) {
        j = k;
        break;
      }
    if (j == -1) {
      j = 0;
      this.providerName.add(0, "OpenGL");
      this.providerDll.add(0, str1);
    }
    for (int m = 0; m < this.providerName.size(); m++)
      this.comboProvider.add((String)this.providerName.get(m));
  }

  private int _findProvider() {
    String str = Provider.GLname();
    int i = -1;
    for (int j = 0; j < this.providerDll.size(); j++)
      if (((String)this.providerDll.get(j)).compareToIgnoreCase(str) == 0) {
        i = j;
        break;
      }
    if (i == -1)
      i = 0;
    return i;
  }

  public void update() {
    if (this.screenModes.size() <= 0)
      return;
    this.comboProvider.setSelected(_findProvider(), true, false);
    if (Config.cur.windowChangeScreenRes) {
      updateComboResolution(true);
      this.sFull.setState(1, false);
      this.sStencil.setChecked(Config.cur.windowStencilBits == 8, false);
    } else {
      updateComboResolution(false);
      this.sFull.setState(0, false);
      this.sStencil.setChecked(Config.cur.windowStencilBits == 8, false);
    }
  }

  private void prepareUse3Renders(boolean paramBoolean) {
    if (!paramBoolean)
      this.bCmdLineUse3Renders = this.enterUse3Renders;
    if (this.bCmdLineUse3Renders) {
      Main3D.cur3D().setSaveAspect(true);
      Config.cur.windowUse3Renders = true;
      Config.cur.checkWindowUse3Renders();
    } else {
      Config.cur.windowUse3Renders = false;
    }
    if (!Config.cur.isUse3Renders())
      if (ScreenMode.current().width() == ScreenMode.current().height() * 4)
        Main3D.cur3D().setSaveAspect(false);
      else
        Main3D.cur3D().setSaveAspect(true);
  }

  public void updateComboResolution(boolean paramBoolean)
  {
    int i;
    Object localObject;
    if (paramBoolean) {
      this.comboResolution.clear(false);
      for (i = 0; i < this.screenModes.size(); i++) {
        localObject = (ScreenMode)this.screenModes.get(i);
        if (((ScreenMode)localObject).ext != null)
          this.comboResolution.add("3x" + ((ScreenMode)localObject).width() / 3 + "x" + ((ScreenMode)localObject).height() + "x" + ((ScreenMode)localObject).colourBits());
        else
          this.comboResolution.add(((ScreenMode)localObject).width() + "x" + ((ScreenMode)localObject).height() + "x" + ((ScreenMode)localObject).colourBits());
      }
      this.comboResolution.setSelected(_findVideoMode(ScreenMode.current(), Config.cur.isUse3Renders()), true, false);
    } else {
      i = -1;
      if ((this.comboResolution.size() > 0) && (this.comboResolution.getSelected() >= 0)) {
        localObject = this.comboResolution.getValue();
        if ((localObject != null) && (((String)localObject).indexOf('x') >= 0)) {
          localObject = ((String)localObject).substring(0, ((String)localObject).indexOf('x'));
          i = Integer.parseInt((String)localObject);
        }
      }
      this.comboResolution.clear(false);
      int j = ScreenMode.startup().width();
      if (i < 0) i = j;
      for (int k = 0; k < this.screenModesWindow.length; k++) {
        int m = this.screenModesWindow[k];
        if (m >= j)
          break;
        this.comboResolution.add(m + "x" + m * 3 / 4);
      }
      this.comboResolution.setValue(Config.cur.windowWidth + "x" + Config.cur.windowHeight, false);

      this.comboResolution.setSelected(_findVideoModeWindow(i, this.comboResolution.size()), false, false);
    }
  }

  private String cmdLine() {
    int i = this.comboResolution.getSelected();
    if (i < 0) return null;
    String str = null;
    this.bCmdLineUse3Renders = false;
    if (this.sFull.getState() == 1) {
      ScreenMode localScreenMode = (ScreenMode)this.screenModes.get(i);
      str = "window " + localScreenMode.width() + " " + localScreenMode.height() + " " + localScreenMode.colourBits() + " " + localScreenMode.colourBits() + (this.sStencil.isChecked() ? " 8" : " 0") + " FULL PROVIDER " + this.providerDll.get(this.comboProvider.getSelected());

      this.bCmdLineUse3Renders = (localScreenMode.ext != null);
    } else {
      int j = this.screenModesWindow[i];
      str = "window " + j + " " + j * 3 / 4 + " " + Config.cur.windowColourBits + " " + Config.cur.windowColourBits + (this.sStencil.isChecked() ? " 8" : " 0") + " PROVIDER " + this.providerDll.get(this.comboProvider.getSelected());
    }

    return str;
  }

  private void doConfirm()
  {
    prepareUse3Renders(true);
    update();
    new MsgAction(72, 0.0D) {
      public void doAction() { new GUISetupVideo.2(this, Main3D.cur3D().guiManager.root, 20.0F, true, GUISetupVideo.this.i18n("setupVideo.Confirm"), GUISetupVideo.this.i18n("setupVideo.Keep"), 1, 0.0F);
      }
    };
  }

  public GUISetupVideo(GWindowRoot paramGWindowRoot)
  {
    super(12);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setupVideo.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.texFull = new GTexRegion("GUI/game/staticelements.mat", 192.0F, 96.0F, 64.0F, 64.0F);

    this.sFull = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch16(this.dialogClient, new int[] { 10, 15 }, new boolean[] { true, true })));
    this.sStencil = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    this.bApply = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.addControl(this.comboProvider = new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F));
    this.comboProvider.setEditable(false);
    this.comboProvider.resized();
    fillComboProvider();

    ScreenMode[] arrayOfScreenMode = ScreenMode.all();
    for (int i = 0; i < arrayOfScreenMode.length; i++) {
      ScreenMode localScreenMode1 = arrayOfScreenMode[i];
      if ((localScreenMode1.colourBits() >= 15) && (localScreenMode1.width() >= 640.0F)) {
        if (localScreenMode1.width() == localScreenMode1.height() * 4 / 3) {
          this.screenModes.add(localScreenMode1);
        } else if (localScreenMode1.width() == localScreenMode1.height() * 4) {
          ScreenMode localScreenMode2 = new ScreenMode(localScreenMode1);
          localScreenMode2.ext = new Object();
          this.screenModes.add(localScreenMode1);
          this.screenModes.add(localScreenMode2);
        }
      }
    }
    if (this.screenModes.size() <= 0) {
      this.client.hideWindow();
      return;
    }

    this.dialogClient.addControl(this.comboResolution = new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F));
    this.comboResolution.setEditable(false);
    this.comboResolution.resized();
    update();

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUISetupVideo.this.sFull) {
        GUISetupVideo.this.updateComboResolution(GUISetupVideo.this.sFull.getState() == 1);
        return true;
      }
      if (paramGWindow == GUISetupVideo.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUISetupVideo.this.bApply) {
        new GUISetupVideo.3(this, 72, 0.0D);

        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(208.0F), x1024(384.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(428.0F), x1024(384.0F), 2.0F);
      setCanvasColorWHITE();
      draw(x1024(64.0F), y1024(256.0F), x1024(64.0F), y1024(64.0F), GUISetupVideo.this.texFull);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(64.0F), y1024(32.0F), x1024(320.0F), y1024(32.0F), 1, GUISetupVideo.this.i18n("setupVideo.Driver"));
      draw(x1024(64.0F), y1024(112.0F), x1024(320.0F), y1024(32.0F), 1, GUISetupVideo.this.i18n("setupVideo.Resolution"));
      draw(x1024(192.0F), y1024(240.0F), x1024(224.0F), y1024(32.0F), 0, GUISetupVideo.this.i18n("setupVideo.Windowed"));
      draw(x1024(192.0F), y1024(304.0F), x1024(224.0F), y1024(32.0F), 0, GUISetupVideo.this.i18n("setupVideo.FullScreen"));
      draw(x1024(192.0F), y1024(368.0F), x1024(224.0F), y1024(48.0F), 0, GUISetupVideo.this.i18n("setupVideo.Stencil"));
      draw(x1024(96.0F), y1024(480.0F), x1024(224.0F), y1024(48.0F), 0, GUISetupVideo.this.i18n("setupVideo.Apply"));
      draw(x1024(96.0F), y1024(544.0F), x1024(224.0F), y1024(48.0F), 0, GUISetupVideo.this.i18n("setupVideo.Back"));
    }

    public void setPosSize() {
      set1024PosSize(302.0F, 80.0F, 448.0F, 624.0F);

      GUISetupVideo.this.comboProvider.set1024PosSize(32.0F, 64.0F, 384.0F, 32.0F);
      GUISetupVideo.this.comboResolution.set1024PosSize(32.0F, 144.0F, 384.0F, 32.0F);
      GUISetupVideo.this.sFull.setPosC(x1024(96.0F), y1024(288.0F));
      GUISetupVideo.this.sStencil.setPosC(x1024(120.0F), y1024(392.0F));

      GUISetupVideo.this.bApply.setPosC(x1024(56.0F), y1024(504.0F));
      GUISetupVideo.this.bExit.setPosC(x1024(56.0F), y1024(568.0F));
      GUISetupVideo.this.update();
    }
  }
}