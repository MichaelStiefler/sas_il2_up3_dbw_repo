package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.ChatMessage;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.util.UnicodeTo8bit;
import java.io.PrintStream;
import java.util.List;

public class GUIChatDialog extends GWindow
{
  public static final int MODE_CHAT = 0;
  public static final int MODE_CHAT_TO = 1;
  public static final int MODE_RADIO = 2;
  public static final int MODE_CONSOLE = 3;
  public static final int EDIT_SLOTS = 10;
  public static final int ADR_SLOTS = 10;
  public WClient wClient;
  public float clientHeight;
  public WEdit wEdit;
  public GWindow wViewChat;
  public WDrawChat wDrawChat;
  public GWindow wViewConsole;
  public WDrawConsole wDrawConsole;
  public GFont consoleFont;
  public GTexture texIndicator;
  public int posChat = 0;
  public int posConsole = 0;

  public boolean chatStateSend = false;
  public int chatCurEditSlot = -1;
  public int chatCurAdrSlot = 0;
  public String[] chatEditSlot = new String[10];
  public String[] chatAdrSlot = new String[10];
  public String chatMessage;
  public static final int RADIO_NONE = 1;
  public static final int RADIO_COMMON = 2;
  public static final int RADIO_ARMY = 3;
  public static final int RADIO_PRIVATE = 4;
  public String[] radioSlot = { "    Radio channels:", "None", "Common", "Army", "" };

  public int radioCurSlot = 1;
  static final int SIZING_NONE = 0;
  static final int SIZING_MOVE = 1;
  static final int SIZING_TL = 2;
  static final int SIZING_T = 3;
  static final int SIZING_TR = 4;
  static final int SIZING_L = 5;
  static final int SIZING_R = 6;
  static final int SIZING_BL = 7;
  static final int SIZING_B = 8;
  static final int SIZING_BR = 9;
  int sizingState = 0;

  private static GSize _newSize = new GSize();

  public GUIChatDialog THIS()
  {
    return this;
  }
  public int mode() {
    int i = this.wEdit.getFirstChar();
    if (i == 46)
      return 2;
    if (i == 62)
      return 3;
    return this.chatStateSend ? 1 : 0;
  }

  public boolean isTransparent() {
    return !this.wEdit.bCanEdit;
  }

  public boolean isDownVisible() {
    if (isTransparent()) {
      return mode() == 3;
    }

    return true;
  }

  public float downHeight() {
    return this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height * 11.0F;
  }

  private void chatDrawPos(boolean paramBoolean)
  {
    Chat localChat = Main.cur().chat;
    if (localChat == null) return;
    GFont localGFont = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0];
    float f = localGFont.height;
    int i = localChat.buf.size();
    if (i == 0) {
      this.posChat = 0;
      return;
    }
    int j = (int)(this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f);
    int k = j / 3;
    if (k == 0) k = 1;
    this.posChat = (paramBoolean ? this.posChat + k : this.posChat - k);
    if (this.posChat + j >= i)
      this.posChat = (i - j);
    if (this.posChat < 0) this.posChat = 0;
  }

  private void consoleDrawPos(boolean paramBoolean)
  {
    if (!isDownVisible()) return;
    if (mode() != 3) return;
    List localList = RTSConf.cur.console.historyOut();
    GFont localGFont = this.consoleFont;
    float f = localGFont.height;
    int i = localList.size();
    if (i == 0) {
      this.posConsole = 0;
      return;
    }
    int j = (int)(this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f);
    int k = j / 3;
    if (k == 0) k = 1;
    this.posConsole = (paramBoolean ? this.posConsole + k : this.posConsole - k);
    if (this.posConsole + j >= i)
      this.posConsole = (i - j);
    if (this.posConsole < 0) this.posConsole = 0;
  }

  public void afterCreated()
  {
    for (int i = 0; i < 10; i++)
      this.chatEditSlot[i] = UnicodeTo8bit.load(Config.cur.ini.get("chat", "msg" + i, new String()));
    this.chatAdrSlot[0] = "ALL";
    this.chatAdrSlot[1] = "MY_ARMY";
    for (int j = 2; j < 10; j++) {
      this.chatAdrSlot[j] = UnicodeTo8bit.load(Config.cur.ini.get("chat", "adr" + j, new String()));
    }
    this.wClient = ((WClient)create(new WClient()));

    this.wEdit = ((WEdit)this.wClient.addDefault(new WEdit(this.wClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wEdit.setHistory(false);
    this.wEdit.maxLength = 80;
    this.wEdit.setEditable(false);

    this.wViewChat = this.wClient.create(new GWindow());
    this.wViewChat.bNotify = true;
    this.wDrawChat = ((WDrawChat)this.wViewChat.create(new WDrawChat()));
    this.wDrawChat.bAcceptsKeyFocus = false;

    this.wViewConsole = this.wClient.create(new GWindow());
    this.wViewConsole.bNotify = true;
    this.consoleFont = GFont.New("courSmall");
    this.wDrawConsole = ((WDrawConsole)this.wViewConsole.create(new WDrawConsole()));
    this.wDrawConsole.bAcceptsKeyFocus = false;
    this.texIndicator = GTexture.New("GUI/game/indicator.mat");
  }

  public void render()
  {
    Object localObject;
    if (!isTransparent()) {
      localObject = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      setCanvasColorWHITE();
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, (GBevel)localObject, ((GUILookAndFeel)lookAndFeel()).basicelements, false);
    } else {
      if (Main3D.cur3D().hud.isDrawNetStat()) return;
      localObject = Main3D.cur3D().guiManager;
      if (((GUIWindowManager)localObject).isMouseActive()) {
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 255;
        GUISeparate.draw(this, GColor.Black, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, 2.0F);
        GUISeparate.draw(this, GColor.Black, 0.0F, 0.0F, 2.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
        GUISeparate.draw(this, GColor.Black, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F, 0.0F, 2.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
        GUISeparate.draw(this, GColor.Black, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, 2.0F);
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0;
      }
    }
  }

  private void drawU(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    float f1 = x1024(6.0F);
    float f2 = y1024(9.0F);
    float f3 = x1024(9.0F);
    if (paramInt1 > 8) paramInt1 = 8;
    if (paramInt1 > 0) {
      for (int i = 0; i < paramInt1; i++) {
        GColor localGColor1 = GColor.Green;
        if (i >= 6)
          localGColor1 = GColor.Yellow;
        GUISeparate.draw(this.wEdit, localGColor1, paramFloat1 + i * f3, paramFloat2, f1, f2);
      }
    }
    if (paramInt2 > 5) paramInt2 = 5;
    if (paramInt2 > 0) {
      float f4 = 9.0F * f3;
      for (int j = 0; j < paramInt2; j++) {
        GColor localGColor2 = GColor.Yellow;
        if (j >= 1)
          localGColor2 = GColor.Red;
        GUISeparate.draw(this.wEdit, localGColor2, f4 + paramFloat1 + j * f3, paramFloat2, f1, f2);
      }
    }
  }

  public boolean isFindTestOk(GWindow paramGWindow, boolean paramBoolean) {
    return paramGWindow.isVisible();
  }
  public void doRender(boolean paramBoolean) {
    boolean bool = isTransparent();
    int i = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha;
    if (bool) this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0;
    super.doRender(paramBoolean);
    if (bool) this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = i;

    int j = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha;
    this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 255;

    float f = 0.0F;
    int k = AudioDevice.getRadioStatus();
    if (getChild(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, GUIInfoMenu.class, false, true) != null) f = 32.0F;
    int m;
    int n;
    if ((k & 0x3) >= 2) {
      m = Math.round(AudioDevice.getRadioLevel() / 100.0F * 8.0F);
      n = Math.round(AudioDevice.getRadioOverflow() / 100.0F * 5.0F);
      if ((k & 0x10) == 0) {
        if (k == 2) setCanvasColor(GColor.Blue); else
          setCanvasColor(16777215);
      }
      else if (k == 2) setCanvasColor(GColor.Yellow); else {
        setCanvasColor(GColor.Green);
      }
      draw(x1024(882.0F), y1024(f), x1024(16.0F), y1024(16.0F), this.texIndicator, 0.0F, 0.0F, 16.0F, 16.0F);
      setCanvasColorWHITE();
      drawU(m, n, x1024(900.0F), y1024(f + 2.0F));
    }
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMirror()))
    {
      m = Main.cur().netServerParams.masterChannel().getCurTimeout();
      n = Main.cur().netServerParams.masterChannel().ping();
      int i1 = 8;
      if (n < 50) i1 = 1;
      else if (n < 100) i1 = 2;
      else if (n < 150) i1 = 3;
      else if (n < 200) i1 = 4;
      else if (n < 250) i1 = 5;
      else if (n < 400) i1 = 6;
      else if (n < 600) i1 = 7;

      int i2 = 5;
      if (m < 5000) i2 = 0;
      else if (m < 10000) i2 = 1;
      else if (m < 15000) i2 = 2;
      else if (m < 20000) i2 = 3;
      else if (m < 25000) i2 = 4;

      setCanvasColorWHITE();
      draw(x1024(882.0F), y1024(f + 16.0F), x1024(16.0F), y1024(16.0F), this.texIndicator, 0.0F, 16.0F, 16.0F, 16.0F);
      drawU(i1, i2, x1024(900.0F), y1024(f + 2.0F + 16.0F));
    }
    this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = j;
  }
  public void preRender() {
    Chat localChat = Main.cur().chat;
    if (localChat == null)
      hideWindow();
    GBevel localGBevel;
    if (isDownVisible()) {
      if (this.wClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dy == this.clientHeight + downHeight())
        return;
      localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.clientHeight + downHeight() + localGBevel.T.dy + localGBevel.B.dy);
    } else {
      if (this.wClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dy == this.clientHeight)
        return;
      localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.clientHeight + localGBevel.T.dy + localGBevel.B.dy);
    }
  }

  public void resized() {
    GSize localGSize = getMinSize();
    if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx < localGSize.dx) this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = localGSize.dx;
    if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy < localGSize.dy) this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = localGSize.dy;
    GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
    this.wClient.setPosSize(localGBevel.L.dx, localGBevel.T.dy, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy);
  }

  public void resolutionChanged() {
    this.consoleFont.resolutionChanged();
    loadRegion();
  }
  public GSize getMinSize(GSize paramGSize) {
    float f1 = x1024(100.0F);
    float f2 = y1024(50.0F);
    paramGSize.set(f1, f2);
    return paramGSize;
  }
  public GUIChatDialog(GWindow paramGWindow) {
    this.bAlwaysOnTop = true;
    paramGWindow.create(this);
    loadRegion();
  }

  private void loadRegion() {
    GRegion localGRegion = new GRegion(0.0F, 0.2F, 0.3F, 0.2F);
    Config.cur.ini.get("chat", "region", localGRegion);
    this.clientHeight = (localGRegion.dy * this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
    float f = this.clientHeight + localGBevel.T.dy + localGBevel.B.dy;
    if (isDownVisible()) f += downHeight();
    setPosSize(localGRegion.x * this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, localGRegion.y * this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGRegion.dx * this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f);
  }
  private void saveRegion() {
    GRegion localGRegion = new GRegion();
    localGRegion.x = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.x / this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
    localGRegion.y = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.y / this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    localGRegion.dx = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
    localGRegion.dy = (this.clientHeight / this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    Config.cur.ini.set("chat", "region", localGRegion, false);
  }

  int frameHitTest(float paramFloat1, float paramFloat2)
  {
    GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
    if ((paramFloat1 < 0.0F) || (paramFloat1 > this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx) || (paramFloat2 < 0.0F) || (paramFloat2 > this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy))
    {
      return 0;
    }if (paramFloat1 <= localGBevel.L.dx) {
      if (paramFloat2 <= localGBevel.T.dy) return 2;
      if (paramFloat2 >= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy) return 7;
      return 5;
    }if (paramFloat1 >= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx) {
      if (paramFloat2 <= localGBevel.T.dy) return 4;
      if (paramFloat2 >= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy) return 9;
      return 6;
    }
    if (paramFloat2 <= localGBevel.T.dy) return 1;
    if (paramFloat2 >= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy) return 8;

    return 1;
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    int i;
    if (paramInt == 0) {
      if (paramBoolean) {
        if (isMouseCaptured())
          return;
        if (isTransparent()) return;
        i = frameHitTest(paramFloat1, paramFloat2);
        if (i == 0) return;
        this.sizingState = i;
        mouseCapture(true);
      }
      else if (isMouseCaptured()) {
        this.sizingState = 0;
        mouseCapture(false);
        this.jdField_mouseCursor_of_type_Int = 1;
        saveRegion();
      }
    }
    else if (paramInt == 1)
      if (paramBoolean) {
        if (isMouseCaptured())
          return;
        if (isTransparent()) return;
        i = frameHitTest(paramFloat1, paramFloat2);
        if (i == 0) return;
        this.sizingState = 1;
        this.jdField_mouseCursor_of_type_Int = 3;
        mouseCapture(true);
      }
      else if (isMouseCaptured()) {
        this.sizingState = 0;
        mouseCapture(false);
        this.jdField_mouseCursor_of_type_Int = 1;
        saveRegion();
      }
  }

  public void mouseMove(float paramFloat1, float paramFloat2)
  {
    super.mouseMove(paramFloat1, paramFloat2);
    GRegion localGRegion = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.getClientRegion();
    if ((this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x < localGRegion.x) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x >= localGRegion.x + localGRegion.dx) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y < localGRegion.y) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y >= localGRegion.y + localGRegion.dy))
    {
      return;
    }
    GSize localGSize = null;
    if ((this.sizingState != 1) && (this.sizingState != 0)) {
      _newSize.set(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy); localGSize = getMinSize(); localGSize.dy += downHeight();
    }
    switch (this.sizingState)
    {
    case 0:
      int i = frameHitTest(paramFloat1, paramFloat2);
      this.jdField_mouseCursor_of_type_Int = 1;
      if (i == 0) return;
      switch (i) { case 2:
        this.jdField_mouseCursor_of_type_Int = 10; break;
      case 3:
        this.jdField_mouseCursor_of_type_Int = 9; break;
      case 4:
        this.jdField_mouseCursor_of_type_Int = 8; break;
      case 5:
        this.jdField_mouseCursor_of_type_Int = 11; break;
      case 6:
        this.jdField_mouseCursor_of_type_Int = 11; break;
      case 7:
        this.jdField_mouseCursor_of_type_Int = 8; break;
      case 8:
        this.jdField_mouseCursor_of_type_Int = 9; break;
      case 9:
        this.jdField_mouseCursor_of_type_Int = 10; break;
      case 1:
        this.jdField_mouseCursor_of_type_Int = 3;
      }

      return;
    case 1:
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      return;
    case 2:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize();
      return;
    case 3:
      _newSize.add(0.0F, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if (_newSize.dy < localGSize.dy) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize();
      return;
    case 4:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize();
      return;
    case 5:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, 0.0F);
      if (_newSize.dx < localGSize.dx) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y);
      setSize();
      return;
    case 6:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, 0.0F);
      if (_newSize.dx < localGSize.dx) break;
      setSize();
      return;
    case 7:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y);
      setSize();
      return;
    case 8:
      _newSize.add(0.0F, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if (_newSize.dy < localGSize.dy) break;
      setSize();
      return;
    case 9:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setSize();
      return;
    }

    this.sizingState = 0;
    mouseCapture(false);
    this.jdField_mouseCursor_of_type_Int = 1;
  }

  private void setSize() {
    GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
    this.clientHeight = (_newSize.dy - downHeight() - localGBevel.T.dy - localGBevel.B.dy);
    setSize(_newSize.dx, _newSize.dy);
  }

  public class WClient extends GWindowDialogClient
  {
    public WClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 == 15) {
        GUI.chatActivate();
        return true;
      }
      if ((paramInt1 == 11) && 
        (paramInt2 == 27)) {
        if (GUIChatDialog.this.mode() == 0) {
          if (GUIChatDialog.this.chatCurEditSlot != -1) {
            GUIChatDialog.this.chatCurEditSlot = -1;
            GUIChatDialog.this.wEdit.clear(false);
          }
        } else if (GUIChatDialog.this.mode() == 1) {
          GUIChatDialog.this.chatStateSend = false;
          GUIChatDialog.this.wEdit.clear(false);
        } else if (GUIChatDialog.this.mode() == 2) {
          GUIChatDialog.this.wEdit.setValue("", false);
        }
        GUI.chatUnactivate();
        return true;
      }

      Chat localChat = Main.cur().chat;
      if ((paramGWindow != GUIChatDialog.this.wEdit) || (paramInt1 != 10) || (paramInt2 != 10) || (localChat == null))
      {
        return super.notify(paramGWindow, paramInt1, paramInt2);
      }String str1 = GUIChatDialog.this.wEdit.getValue();
      if ((str1 == null) || (str1.length() == 0))
        return super.notify(paramGWindow, paramInt1, paramInt2);
      String str2 = null;
      int i = 0;
      int j = 0;
      switch (GUIChatDialog.this.mode()) {
      case 0:
        if (GUIChatDialog.this.chatCurEditSlot >= 0)
          Config.cur.ini.set("chat", "msg" + GUIChatDialog.this.chatCurEditSlot, UnicodeTo8bit.save(str1, true));
        GUIChatDialog.this.chatMessage = str1;
        GUIChatDialog.this.wEdit.setValue(GUIChatDialog.this.chatAdrSlot[GUIChatDialog.this.chatCurAdrSlot], false);
        GUIChatDialog.this.chatCurEditSlot = -1;
        GUIChatDialog.this.chatStateSend = true;
        break;
      case 1:
        Config.cur.ini.set("chat", "adr" + GUIChatDialog.this.chatCurAdrSlot, UnicodeTo8bit.save(str1, true));
        if (GUIChatDialog.this.chatCurAdrSlot >= 2)
          str1 = "chat " + GUIChatDialog.this.chatMessage + " TO " + str1;
        else
          str1 = "chat " + GUIChatDialog.this.chatMessage + " " + str1;
        i = 1;
        j = 1;
        GUIChatDialog.this.chatStateSend = false;
        GUIChatDialog.this.wEdit.clear(false);
        break;
      case 2:
        NetUser localNetUser = (NetUser)NetEnv.host();
        switch (GUIChatDialog.this.radioCurSlot) { case 1:
          if (localNetUser.isRadioNone()) break;
          str1 = "radio NONE";
          str2 = "radioNone";
          j = 1; break;
        case 2:
          if (localNetUser.isRadioCommon()) break;
          str1 = "radio COMMON";
          str2 = "radioCommon";
          j = 1; break;
        case 3:
          if ((localNetUser.getArmy() == 0) || (localNetUser.isRadioArmy())) break;
          str1 = "radio ARMY";
          str2 = "radioArmy";
          j = 1; break;
        case 4:
          if (str1.length() == 1) {
            if (localNetUser.isRadioNone()) break;
            str1 = "radio NONE";
            str2 = "radioNone";
            j = 1;
          }
          else {
            String str3 = str1.substring(1);
            if (str3.equals(localNetUser.radio())) break;
            str1 = "radio " + str3;
            str2 = "radioPrivate";
            j = 1;
          }

        }

        break;
      case 3:
        str1 = str1.substring(1);
        j = 1;
        GUIChatDialog.this.wEdit.setValue(">", false);
        break;
      default:
        return true;
      }
      if (j != 0) {
        System.out.println(RTSConf.cur.console.getPrompt() + str1);
        RTSConf.cur.console.getEnv().exec(str1);
        RTSConf.cur.console.addHistoryCmd(str1);
        RTSConf.cur.console.curHistoryCmd = -1;
        if ((str2 != null) && (!Time.isPaused()))
          HUD.log(str1);
      }
      if (i != 0)
        GUI.chatUnactivate();
      return true;
    }

    private void drawScroll(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3)
    {
      float f1 = (paramInt1 - paramInt3 - paramInt2) / paramInt1;
      float f2 = paramInt3 / paramInt1;
      float f3 = paramInt2 / paramInt1;
      if (f1 > 0.0F)
        GUISeparate.draw(this, 65535, paramFloat1, paramFloat2, paramFloat3, paramFloat4 * f1);
      if (f2 > 0.0F)
        GUISeparate.draw(this, 0, paramFloat1, paramFloat2 + paramFloat4 * f1, paramFloat3, paramFloat4 * f2);
      if (f3 > 0.0F)
        GUISeparate.draw(this, 65535, paramFloat1, paramFloat2 + paramFloat4 * (f1 + f2), paramFloat3, paramFloat4 - paramFloat4 * (f1 + f2));
    }

    public void render() {
      if (GUIChatDialog.this.isTransparent()) return;
      super.render();
      Chat localChat = Main.cur().chat;
      if (localChat == null) return;
      GFont localGFont = this.root.textFonts[0];
      float f = localGFont.height;
      int i = localChat.buf.size();
      int j = (int)(GUIChatDialog.this.wDrawChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f);
      if (j > i)
        j = i;
      if (j > 0) {
        drawScroll(GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.x + GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.y, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.x - GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, i, GUIChatDialog.this.posChat, j);
      }

      if (!GUIChatDialog.this.isDownVisible()) return;
      if (GUIChatDialog.this.mode() != 3) return;
      List localList = RTSConf.cur.console.historyOut();
      localGFont = GUIChatDialog.this.consoleFont;
      f = localGFont.height;
      i = localList.size();
      j = (int)(GUIChatDialog.this.wDrawConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f);
      if (j > i)
        j = i;
      if (j > 0)
        drawScroll(GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.x + GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.y, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.x - GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, i, GUIChatDialog.this.posConsole, j);
    }

    public void resized()
    {
      GUIChatDialog.this.wEdit.setPosSize(0.0F, GUIChatDialog.this.clientHeight - y1024(32.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, y1024(32.0F));
      GUIChatDialog.this.wViewChat.setPosSize(x1024(2.0F), y1024(2.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - x1024(4.0F), GUIChatDialog.this.clientHeight - y1024(36.0F));
      GUIChatDialog.this.wDrawChat.setPosSize(0.0F, 0.0F, GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewChat.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
      GUIChatDialog.this.wViewConsole.setPosSize(x1024(2.0F), y1024(2.0F) + GUIChatDialog.this.clientHeight, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - x1024(4.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - GUIChatDialog.this.clientHeight - y1024(4.0F));

      GUIChatDialog.this.wDrawConsole.setPosSize(0.0F, 0.0F, GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GUIChatDialog.this.wViewConsole.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
      super.resized();
    }
  }

  public class WDrawConsole extends GWindow
  {
    public WDrawConsole()
    {
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if (!GUIChatDialog.this.isDownVisible()) return;
      if ((paramInt == 0) && (!paramBoolean))
        GUIChatDialog.this.consoleDrawPos(paramFloat2 < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F);
    }

    public void render() {
      if (Main3D.cur3D().hud.isDrawNetStat()) return;
      if (!GUIChatDialog.this.isDownVisible()) return;
      Object localObject;
      GFont localGFont;
      float f1;
      switch (GUIChatDialog.this.mode())
      {
      case 3:
        localObject = RTSConf.cur.console.historyOut();
        localGFont = GUIChatDialog.this.consoleFont;
        f1 = localGFont.height;
        int i = ((List)localObject).size();
        int j = (int)(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f1);
        if (i > j)
          i = j;
        if (i <= 0) return;
        float f3 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f1;
        int k = GUIChatDialog.this.posConsole;
        if (k + i >= ((List)localObject).size()) k = ((List)localObject).size() - i;
        setCanvasColor(16777215);
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font = localGFont;
        if (GUIChatDialog.this.isTransparent())
          this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 127;
        for (int m = 0; m < i; m++) {
          String str = (String)((List)localObject).get(k);
          int n = str.length();
          for (int i1 = 0; i1 < n; i1++) {
            int i2 = str.charAt(i1);
            if ((i2 < 32) && (i2 != 9)) {
              str = str.substring(0, i1);
              break;
            }
          }
          draw(0.0F, f3, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, str);
          f3 -= f1;
          k++;
        }
        if (!GUIChatDialog.this.isTransparent()) break;
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0; break;
      case 0:
        renderTable(GUIChatDialog.this.chatEditSlot, GUIChatDialog.this.chatCurEditSlot);
        break;
      case 1:
        renderTable(GUIChatDialog.this.chatAdrSlot, GUIChatDialog.this.chatCurAdrSlot);
        break;
      case 2:
        localObject = (NetUser)NetEnv.host();
        localGFont = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0];
        f1 = localGFont.height;
        float f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f1 * 10.0F - f1 / 2.0F;

        setCanvasColor(16777215);
        draw(0.0F, f2, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, GUIChatDialog.this.radioSlot[0]);
        f2 += f1;

        renderRadioSlot(f2, localGFont, 1, ((NetUser)localObject).isRadioNone(), null);
        f2 += f1;
        renderRadioSlot(f2, localGFont, 2, ((NetUser)localObject).isRadioCommon(), " 0");
        f2 += f1;
        renderRadioSlot(f2, localGFont, 3, ((NetUser)localObject).isRadioArmy(), ((NetUser)localObject).getArmy() != 0 ? " " + ((NetUser)localObject).getArmy() : null);

        f2 += f1;
        renderRadioSlot(f2, localGFont, 4, ((NetUser)localObject).isRadioPrivate(), ((NetUser)localObject).isRadioPrivate() ? ((NetUser)localObject).radio() : null);

        break;
      }
    }

    private void renderTable(String[] paramArrayOfString, int paramInt)
    {
      GFont localGFont = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0];
      float f1 = localGFont.height;
      float f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f1 * paramArrayOfString.length - f1 / 2.0F;
      for (int i = 0; i < paramArrayOfString.length; i++) {
        if (paramInt == i) setCanvasColor(0); else
          setCanvasColor(16777215);
        draw(0.0F, f2, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, " " + i + ". " + paramArrayOfString[i]);
        f2 += f1;
      }
    }

    private void renderRadioSlot(float paramFloat, GFont paramGFont, int paramInt, boolean paramBoolean, String paramString) {
      if (GUIChatDialog.this.radioCurSlot == paramInt) setCanvasColor(0); else
        setCanvasColor(16777215);
      int i = 0;
      if (paramBoolean) i++;
      List localList = NetEnv.hosts();
      for (int j = 0; j < localList.size(); j++) {
        NetUser localNetUser = (NetUser)localList.get(j);
        if (paramString == null) {
          if ((paramInt == 1) && (localNetUser.radio() == null))
            i++;
        } else if (paramString.equals(localNetUser.radio())) {
          i++;
        }
      }
      if (paramBoolean)
        draw(0.0F, paramFloat, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGFont.height, 0, " " + paramInt + ". (" + i + ")  \t *" + GUIChatDialog.this.radioSlot[paramInt]);
      else
        draw(0.0F, paramFloat, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGFont.height, 0, " " + paramInt + ". (" + i + ")  \t  " + GUIChatDialog.this.radioSlot[paramInt]);
    }
  }

  public class WDrawChat extends GWindow
  {
    public WDrawChat()
    {
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt == 0) && (!paramBoolean))
        GUIChatDialog.this.chatDrawPos(paramFloat2 < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F);
    }

    public void render()
    {
      if (Main3D.cur3D().hud.isDrawNetStat()) return;
      Chat localChat = Main.cur().chat;
      if (localChat == null) return;
      GFont localGFont = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0];
      float f1 = localGFont.height;
      int i = localChat.buf.size();
      int j = (int)(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / f1);
      if (i > j)
        i = j;
      if (i <= 0) return;
      float f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f1;
      int k = GUIChatDialog.this.posChat;
      if (k + i >= localChat.buf.size()) k = localChat.buf.size() - i;
      setCanvasColor(16777215);
      setCanvasFont(0);
      if (GUIChatDialog.this.isTransparent())
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 255;
      for (int m = 0; m < i; m++) {
        ChatMessage localChatMessage = (ChatMessage)localChat.buf.get(k);
        if (localChatMessage.from != null) {
          String str = localChatMessage.from.shortName() + ":\t" + localChatMessage.msg;
          setCanvasColor(0);
          draw(0.0F, f2 + 1.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, str);
          draw(1.0F, f2, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, str);
          draw(1.0F, f2 + 1.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, str);
          setCanvasColor(Army.color(((NetUser)localChatMessage.from).getArmy()));
          draw(0.0F, f2, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, str);
        } else {
          setCanvasColor(16777215);
          draw(0.0F, f2, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, 0, "--- " + localChatMessage.msg);
        }
        f2 -= f1;
        k++;
      }
      if (GUIChatDialog.this.isTransparent())
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0;
    }
  }

  public class WEdit extends GWindowEditControl
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      boolean bool = super.notify(paramInt1, paramInt2);
      if (paramInt1 == 2)
      {
        String str;
        int i;
        switch (GUIChatDialog.this.mode()) {
        case 0:
          str = getValue();
          if (GUIChatDialog.this.chatCurEditSlot >= 0) {
            GUIChatDialog.this.chatEditSlot[GUIChatDialog.this.chatCurEditSlot] = str;
          }
          else if ((str.length() == 1) && (Character.isDigit(str.charAt(0)))) {
            i = str.charAt(0) - '0';
            setValue(GUIChatDialog.this.chatEditSlot[i], false);
            GUIChatDialog.this.chatCurEditSlot = i;
            break;
          }

          if ((this.jdField_caretOffset_of_type_Int < 2) || (this.jdField_caretOffset_of_type_Int > str.length()) || (str.charAt(this.jdField_caretOffset_of_type_Int - 2) != '\\') || (!Character.isDigit(str.charAt(this.jdField_caretOffset_of_type_Int - 1)))) {
            break;
          }
          i = str.charAt(this.jdField_caretOffset_of_type_Int - 1) - '0';
          this.jdField_value_of_type_JavaLangStringBuffer.deleteCharAt(this.jdField_caretOffset_of_type_Int - 1);
          this.jdField_value_of_type_JavaLangStringBuffer.deleteCharAt(this.jdField_caretOffset_of_type_Int - 2);
          this.jdField_value_of_type_JavaLangStringBuffer.insert(this.jdField_caretOffset_of_type_Int - 2, GUIChatDialog.this.chatEditSlot[i]);
          setValue(this.jdField_value_of_type_JavaLangStringBuffer.toString(), false); break;
        case 1:
          if ((GUIChatDialog.this.chatCurAdrSlot == 0) || (GUIChatDialog.this.chatCurAdrSlot == 1)) {
            setValue(GUIChatDialog.this.chatAdrSlot[GUIChatDialog.this.chatCurAdrSlot], false); } else {
            if (GUIChatDialog.this.chatCurAdrSlot < 2) break;
            GUIChatDialog.this.chatAdrSlot[GUIChatDialog.this.chatCurAdrSlot] = getValue(); } break;
        case 2:
          str = getValue();
          if (str.length() == 1) {
            if (!AudioDevice.npFlags.get(0)) {
              setValue("", false);
            } else {
              if (((NetUser)NetEnv.host()).isRadioPrivate())
                GUIChatDialog.this.radioSlot[4] = ((NetUser)NetEnv.host()).radio();
              else {
                GUIChatDialog.this.radioSlot[4] = "";
              }
              if (GUIChatDialog.this.radioCurSlot != 4) break;
              setValue("." + GUIChatDialog.this.radioSlot[4], false);
            }
          } else {
            if ((str.length() == 2) && (Character.isDigit(str.charAt(1)))) {
              i = str.charAt(1) - '0';
              if ((i != GUIChatDialog.this.radioCurSlot) && (i >= 1) && (i <= 4)) {
                GUIChatDialog.this.radioCurSlot = i;
                if (GUIChatDialog.this.radioCurSlot == 4) {
                  setValue("." + GUIChatDialog.this.radioSlot[4], false); break;
                }
                setValue(".", false);
                break;
              }
            }
            if (GUIChatDialog.this.radioCurSlot == 4) {
              while ((getValue().length() > 1) && (getValue().charAt(1) == ' '))
                setValue("." + getValue().substring(2), false);
              GUIChatDialog.this.radioSlot[GUIChatDialog.this.radioCurSlot] = getValue().substring(1);
            } else {
              setValue(".", false);
            }
          }

          break;
        case 3:
        }

      }

      return bool;
    }

    public void keyboardKey(int paramInt, boolean paramBoolean) {
      switch (paramInt) {
      case 33:
      case 34:
        if (!paramBoolean)
          GUIChatDialog.this.chatDrawPos(paramInt == 33);
        return;
      case 38:
      case 40:
        if (!paramBoolean) {
          if (this.bControlDown) {
            GUIChatDialog.this.consoleDrawPos(paramInt == 38);
            return;
          }
          switch (GUIChatDialog.this.mode())
          {
          case 3:
            List localList = RTSConf.cur.console.historyCmd();
            int i = RTSConf.cur.console.curHistoryCmd;
            if (localList.size() > 0) {
              if (paramInt == 38) {
                if (i < localList.size()) i++; else
                  i = 0;
              }
              else if (i >= 0) i--; else {
                i = localList.size() - 1;
              }
            }
            if ((i >= 0) && (i < localList.size())) {
              String str = (String)localList.get(i);
              setValue(">" + str, false);
            }
            RTSConf.cur.console.curHistoryCmd = i;

            break;
          case 0:
            if (paramInt == 40) {
              GUIChatDialog.this.chatCurEditSlot = ((GUIChatDialog.this.chatCurEditSlot + 1) % GUIChatDialog.this.chatEditSlot.length);
            }
            else if (GUIChatDialog.this.chatCurEditSlot == -1)
              GUIChatDialog.this.chatCurEditSlot = 9;
            else {
              GUIChatDialog.this.chatCurEditSlot = ((GUIChatDialog.this.chatCurEditSlot - 1 + GUIChatDialog.this.chatEditSlot.length) % GUIChatDialog.this.chatEditSlot.length);
            }
            setValue(GUIChatDialog.this.chatEditSlot[GUIChatDialog.this.chatCurEditSlot], false);
            break;
          case 1:
            if (paramInt == 40)
              GUIChatDialog.this.chatCurAdrSlot = ((GUIChatDialog.this.chatCurAdrSlot + 1) % GUIChatDialog.this.chatAdrSlot.length);
            else
              GUIChatDialog.this.chatCurAdrSlot = ((GUIChatDialog.this.chatCurAdrSlot - 1 + GUIChatDialog.this.chatAdrSlot.length) % GUIChatDialog.this.chatAdrSlot.length);
            setValue(GUIChatDialog.this.chatAdrSlot[GUIChatDialog.this.chatCurAdrSlot], false);
            break;
          case 2:
            if (paramInt == 40) {
              GUIChatDialog.this.radioCurSlot += 1;
              if (GUIChatDialog.this.radioCurSlot > 4)
                GUIChatDialog.this.radioCurSlot = 1;
            } else {
              GUIChatDialog.this.radioCurSlot -= 1;
              if (GUIChatDialog.this.radioCurSlot < 1)
                GUIChatDialog.this.radioCurSlot = 4;
            }
            if (GUIChatDialog.this.radioCurSlot == 4)
              setValue("." + GUIChatDialog.this.radioSlot[GUIChatDialog.this.radioCurSlot], false);
            else
              setValue(".", false);
            break;
          }

        }

        return;
      case 35:
      case 36:
      case 37:
      case 39: } super.keyboardKey(paramInt, paramBoolean);
    }

    public void render()
    {
      super.render();
    }

    public void keyFocusExit()
    {
      super.keyFocusExit();
      setEditable(false);
    }

    public WEdit(GWindow paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramString, String arg7)
    {
      super(paramFloat2, paramFloat3, paramFloat4, paramString, str);
    }
  }
}