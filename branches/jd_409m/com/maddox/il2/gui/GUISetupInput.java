package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.gwindow.GWindowVSliderInt;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.Joy;
import com.maddox.rts.JoyFF;
import com.maddox.rts.Mouse;

public class GUISetupInput extends GameState
{
  public static final int MAX_DEAD_BAND = 50;
  public static final int MAX_FILTER = 10;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowComboControl comboAxe;
  public GWindowVSliderInt[] slider = new GWindowVSliderInt[10];
  public GWindowEditControl[] edit = new GWindowEditControl[10];
  public GWindowHSliderInt deadSlider;
  public GWindowHSliderInt filterSlider;
  public GWindowEditControl wMouse;
  public GUIButton bBack;
  public GUIButton bDefault;
  public GUISwitchBox3 sForceFeedback;
  public GUIButton bControlPanel;
  private int[] comboAxeIndx = { 1, 0, 5 };
  private int[] js = new int[12];

  public void _enter() {
    fillDialogs();
    if (this.sForceFeedback != null)
      this.sForceFeedback.setChecked(JoyFF.isStarted(), false);
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private void fillDialogs() {
    int i = this.comboAxe.getSelected();
    this.comboAxe.clear(false);
    int j = 0;
    if (Joy.adapter().isExistAxe(0, 1)) {
      this.comboAxe.add(i18n("setupIn.Pitch"));
      j++;
    }
    if (Joy.adapter().isExistAxe(0, 0)) {
      this.comboAxe.add(i18n("setupIn.Roll"));
      j++;
    }
    if (Joy.adapter().isExistAxe(0, 5)) {
      this.comboAxe.add(i18n("setupIn.Yaw"));
      j++;
    }
    int k;
    if (j == 0) {
      for (k = 0; k < 9; k++) {
        this.slider[k].setEnable(false);
        this.edit[k].setEnable(false);
      }
      this.deadSlider.setEnable(false);
      this.filterSlider.setEnable(false);
      this.comboAxe.add(i18n("setupIn.none"));
      this.comboAxe.setSelected(0, true, false);
      this.comboAxe.setEnable(false);
    } else {
      for (k = 0; k < 9; k++) {
        this.slider[k].setEnable(true);
        this.edit[k].setEnable(true);
      }
      this.deadSlider.setEnable(true);
      this.filterSlider.setEnable(true);
      this.comboAxe.setEnable(true);
      if ((i < 0) || (i >= j))
        i = 0;
      this.comboAxe.setSelected(-1, false, false);
      this.comboAxe.setSelected(i, true, true);
    }

    float[] arrayOfFloat = Mouse.adapter().getSensitivity();
    this.wMouse.setValue("" + arrayOfFloat[0], false);
  }

  private int curAxe() {
    int i = this.comboAxe.getSelected();
    if (i < 0) return -1;

    switch (i) { case 0:
      return 1;
    case 1:
      return 0;
    case 2:
      return 5; }
    return -1;
  }

  private void fillSliders(int paramInt)
  {
    if (paramInt < 0) return;
    Joy.adapter().getSensitivity(0, paramInt, this.js);
    if (this.js[0] < 0) this.js[0] = 0;
    if (this.js[0] > 50) this.js[0] = 50;
    this.deadSlider.setPos(this.js[0], false);
    this.filterSlider.setPos(10 * this.js[11] / 100, false);
    for (int i = 0; i < 10; i++) {
      if (this.js[(i + 1)] < 0) this.js[(i + 1)] = 0;
      if (this.js[(i + 1)] > 100) this.js[(i + 1)] = 100;
      this.slider[i].setPos(this.js[(i + 1)], false);
      this.edit[i].setValue("" + this.js[(i + 1)], false);
    }
  }

  private void doSetDefault() {
    this.js[0] = 0;
    for (int i = 1; i < 11; i++)
      this.js[i] = (i * 10);
    this.js[11] = 0;
    if (Joy.adapter().isExistAxe(0, 0))
      Joy.adapter().setSensitivity(0, 0, this.js);
    if (Joy.adapter().isExistAxe(0, 1))
      Joy.adapter().setSensitivity(0, 1, this.js);
    if (Joy.adapter().isExistAxe(0, 5))
      Joy.adapter().setSensitivity(0, 5, this.js);
    float[] arrayOfFloat = Mouse.adapter().getSensitivity();
    float tmp120_119 = 1.0F; arrayOfFloat[1] = tmp120_119; arrayOfFloat[0] = tmp120_119;
    fillDialogs();
  }

  private void setJoyS(int paramInt1, int paramInt2) {
    int i = curAxe();
    if (i < 0) return;
    Joy.adapter().getSensitivity(0, i, this.js);
    this.js[paramInt1] = paramInt2;
    Joy.adapter().setSensitivity(0, i, this.js);
    fillSliders(i);
  }

  private float clampValue(GWindowEditControl paramGWindowEditControl, float paramFloat1, float paramFloat2, float paramFloat3) {
    String str = paramGWindowEditControl.getValue();
    try { paramFloat1 = Float.parseFloat(str); } catch (Exception localException) {
    }
    if (paramFloat1 < paramFloat2) paramFloat1 = paramFloat2;
    if (paramFloat1 > paramFloat3) paramFloat1 = paramFloat3;
    paramGWindowEditControl.setValue("" + paramFloat1, false);
    return paramFloat1;
  }

  private int clampValue(GWindowEditControl paramGWindowEditControl, int paramInt1, int paramInt2, int paramInt3) {
    String str = paramGWindowEditControl.getValue();
    try { paramInt1 = Integer.parseInt(str); } catch (Exception localException) {
    }
    if (paramInt1 < paramInt2) paramInt1 = paramInt2;
    if (paramInt1 > paramInt3) paramInt1 = paramInt3;
    paramGWindowEditControl.setValue("" + paramInt1, false);
    return paramInt1;
  }

  public GUISetupInput(GWindowRoot paramGWindowRoot)
  {
    super(53);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setupIn.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.dialogClient.addControl(this.comboAxe = new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F));
    this.comboAxe.setEditable(false);
    this.comboAxe.resized();
    for (int i = 0; i < 10; i++) {
      this.dialogClient.addControl(this.slider[i] =  = new GWindowVSliderInt(this.dialogClient));
      this.slider[i].setRange(0, 101, 10 * (i + 1));
      this.edit[i] = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {
        public void keyboardKey(int paramInt, boolean paramBoolean) {
          super.keyboardKey(paramInt, paramBoolean);
          if ((paramInt == 10) && (paramBoolean))
            notify(2, 0);
        }
      }));
      this.edit[i].bSelectOnFocus = false;
      this.edit[i].jdField_bDelayedNotify_of_type_Boolean = true;
      this.edit[i].jdField_bNumericOnly_of_type_Boolean = true;
      this.edit[i].jdField_align_of_type_Int = 1;
    }
    this.dialogClient.addControl(this.deadSlider = new GWindowHSliderInt(this.dialogClient));
    this.deadSlider.setRange(0, 51, 0);
    this.dialogClient.addControl(this.filterSlider = new GWindowHSliderInt(this.dialogClient));
    this.filterSlider.setRange(0, 11, 0);

    this.wMouse = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {
      public void keyboardKey(int paramInt, boolean paramBoolean) {
        super.keyboardKey(paramInt, paramBoolean);
        if ((paramInt == 10) && (paramBoolean))
          notify(2, 0);
      }
    }));
    this.wMouse.jdField_bNumericOnly_of_type_Boolean = (this.wMouse.bNumericFloat = 1);
    this.wMouse.jdField_bDelayedNotify_of_type_Boolean = true;
    this.wMouse.jdField_align_of_type_Int = 1;

    this.bControlPanel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDefault = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    if (JoyFF.isAttached()) {
      this.sForceFeedback = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    }
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    private int[] joyPos = new int[8];
    private int[] joyRawPos = new int[8];

    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUISetupInput.this.bControlPanel)
      {
        Main.stateStack().push(20);
        GUIControls localGUIControls = (GUIControls)Main.stateStack().peek();
        localGUIControls.scrollClient.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(localGUIControls.scrollClient.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.posMax, true);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.comboAxe) {
        GUISetupInput.this.fillSliders(GUISetupInput.access$000(GUISetupInput.this));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[0]) {
        GUISetupInput.this.setJoyS(1, GUISetupInput.this.slider[0].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[1]) {
        GUISetupInput.this.setJoyS(2, GUISetupInput.this.slider[1].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[2]) {
        GUISetupInput.this.setJoyS(3, GUISetupInput.this.slider[2].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[3]) {
        GUISetupInput.this.setJoyS(4, GUISetupInput.this.slider[3].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[4]) {
        GUISetupInput.this.setJoyS(5, GUISetupInput.this.slider[4].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[5]) {
        GUISetupInput.this.setJoyS(6, GUISetupInput.this.slider[5].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[6]) {
        GUISetupInput.this.setJoyS(7, GUISetupInput.this.slider[6].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[7]) {
        GUISetupInput.this.setJoyS(8, GUISetupInput.this.slider[7].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[8]) {
        GUISetupInput.this.setJoyS(9, GUISetupInput.this.slider[8].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[9]) {
        GUISetupInput.this.setJoyS(10, GUISetupInput.this.slider[9].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.deadSlider) {
        GUISetupInput.this.setJoyS(0, GUISetupInput.this.deadSlider.pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.filterSlider) {
        GUISetupInput.this.setJoyS(11, 100 * GUISetupInput.this.filterSlider.pos() / 10);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[0]) {
        GUISetupInput.this.setJoyS(1, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[0], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[1]) {
        GUISetupInput.this.setJoyS(2, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[1], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[2]) {
        GUISetupInput.this.setJoyS(3, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[2], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[3]) {
        GUISetupInput.this.setJoyS(4, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[3], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[4]) {
        GUISetupInput.this.setJoyS(5, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[4], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[5]) {
        GUISetupInput.this.setJoyS(6, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[5], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[6]) {
        GUISetupInput.this.setJoyS(7, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[6], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[7]) {
        GUISetupInput.this.setJoyS(8, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[7], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[8]) {
        GUISetupInput.this.setJoyS(9, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[8], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[9]) {
        GUISetupInput.this.setJoyS(10, GUISetupInput.access$300(GUISetupInput.this, GUISetupInput.this.edit[9], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.wMouse) {
        float f = GUISetupInput.this.clampValue(GUISetupInput.this.wMouse, 1.0F, 0.1F, 10.0F);
        float[] arrayOfFloat = Mouse.adapter().getSensitivity();
        float tmp1020_1018 = f; arrayOfFloat[1] = tmp1020_1018; arrayOfFloat[0] = tmp1020_1018;
        GUISetupInput.this.wMouse.setValue("" + f, false);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.bDefault) {
        GUISetupInput.this.doSetDefault();
        return true;
      }
      if (paramGWindow == GUISetupInput.this.bBack) {
        Mouse.adapter().saveConfig(Config.cur.ini, "rts_mouse");
        if (GUISetupInput.this.sForceFeedback != null) {
          if (GUISetupInput.this.sForceFeedback.isChecked()) {
            if (!JoyFF.isStarted()) {
              JoyFF.setEnable(true);
              ForceFeedback.start();
              if (Mission.isPlaying())
                ForceFeedback.startMission();
            }
          }
          else if (JoyFF.isStarted()) {
            ForceFeedback.stop();
            JoyFF.setEnable(false);
          }
        }

        Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick");
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(256.0F), y1024(48.0F), x1024(160.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(480.0F), x1024(528.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(576.0F), x1024(832.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(48.0F), 2.0F, y1024(432.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(48.0F), y1024(32.0F), x1024(192.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.JoystickAxes"));
      draw(x1024(432.0F), y1024(32.0F), x1024(208.0F), y1024(32.0F), 0, GUISetupInput.this.i18n("setupIn.Profile"));

      draw(x1024(384.0F), y1024(80.0F), x1024(400.0F), y1024(32.0F), 0, GUISetupInput.this.comboAxe.getValue());

      draw(x1024(368.0F), y1024(368.0F), x1024(208.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.DeadBand"));
      draw(x1024(576.0F), y1024(368.0F), x1024(48.0F), y1024(32.0F), 1, "0");
      draw(x1024(832.0F), y1024(368.0F), x1024(48.0F), y1024(32.0F), 1, "50");
      draw(x1024(368.0F), y1024(416.0F), x1024(208.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.Filtering"));
      draw(x1024(576.0F), y1024(416.0F), x1024(48.0F), y1024(32.0F), 1, "0");
      draw(x1024(832.0F), y1024(416.0F), x1024(48.0F), y1024(32.0F), 1, "1");

      if (GUISetupInput.this.sForceFeedback != null)
        draw(x1024(136.0F), y1024(512.0F), x1024(208.0F), y1024(32.0F), 0, GUISetupInput.this.i18n("setupIn.ForceFeedback"));
      draw(x1024(416.0F), y1024(512.0F), x1024(336.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.MouseSensitivity"));

      draw(x1024(96.0F), y1024(608.0F), x1024(128.0F), y1024(48.0F), 0, GUISetupInput.this.i18n("setupIn.Back"));
      draw(x1024(304.0F), y1024(608.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupInput.this.i18n("setupIn.Default"));
      draw(x1024(528.0F), y1024(608.0F), x1024(272.0F), y1024(48.0F), 2, GUISetupInput.this.i18n("setupIn.ControlPanel"));

      if (!Joy.adapter().isExistAxe(0, 0))
        return;
      Joy.adapter().getNotFilteredPos(0, this.joyRawPos);
      Joy.adapter().getPos(0, this.joyPos);

      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      GBevel localGBevel = localGUILookAndFeel.bevelComboDown;
      float f1 = localGBevel.L.dx;
      float f2 = x1024(16.0F);
      setCanvasColorWHITE();
      localGUILookAndFeel.drawBevel(this, x1024(64.0F), y1024(240.0F), x1024(208.0F), y1024(208.0F), localGBevel, localGUILookAndFeel.basicelements);
      GUISeparate.draw(this, GColor.Black, x1024(64.0F) + 2.0F * f1, y1024(344.0F), x1024(208.0F) - 4.0F * f1, 1.0F);
      GUISeparate.draw(this, GColor.Black, x1024(168.0F), y1024(240.0F) + 2.0F * f1, 1.0F, y1024(208.0F) - 4.0F * f1);
      float f3 = Joy.normal(this.joyPos[0]);
      float f4 = Joy.normal(this.joyPos[1]);
      float f5 = Joy.normal(this.joyRawPos[0]);
      float f6 = Joy.normal(this.joyRawPos[1]);
      float f7 = (x1024(208.0F) - f2) / 2.0F - f1;
      float f8 = x1024(168.0F) + f5 * f7;
      float f9 = y1024(344.0F) + f6 * f7;
      GUISeparate.draw(this, GColor.Red, f8 - f2 / 2.0F, f9 - f2 / 2.0F, f2, f2);
      f8 = x1024(168.0F) + f3 * f7;
      f9 = y1024(344.0F) + f4 * f7;
      GUISeparate.draw(this, GColor.Green, f8 - f2 / 2.0F, f9 - f2 / 2.0F, f2, f2);

      int i = 5;
      if (Joy.adapter().isExistAxe(0, i)) {
        setCanvasColorWHITE();
        localGUILookAndFeel.drawBevel(this, x1024(64.0F), y1024(448.0F), x1024(208.0F), y1024(32.0F), localGBevel, localGUILookAndFeel.basicelements);
        GUISeparate.draw(this, GColor.Black, x1024(168.0F), y1024(448.0F) + 2.0F * f1, 1.0F, y1024(32.0F) - 4.0F * f1);
        f5 = Joy.normal(this.joyRawPos[i]);
        f3 = Joy.normal(this.joyPos[i]);
        f8 = x1024(168.0F) + f5 * f7;
        GUISeparate.draw(this, GColor.Red, f8 - f2 / 2.0F, y1024(448.0F) + 2.0F * f1, f2, y1024(32.0F) - 4.0F * f1);
        f8 = x1024(168.0F) + f3 * f7;
        GUISeparate.draw(this, GColor.Green, f8 - f2 / 2.0F, y1024(448.0F) + 2.0F * f1, f2, y1024(32.0F) - 4.0F * f1);
      }
    }

    public void setPosSize()
    {
      set1024PosSize(64.0F, 48.0F, 896.0F, 688.0F);

      GUISetupInput.this.comboAxe.set1024PosSize(32.0F, 80.0F, 224.0F, 32.0F);
      float f1 = lookAndFeel().getVSliderIntW();
      for (int i = 0; i < 10; i++) {
        GUISetupInput.this.slider[i].setPosSize(x1024(384 + i * 48) + (x1024(48.0F) - f1) / 2.0F, y1024(128.0F), f1, y1024(176.0F));
        GUISetupInput.this.edit[i].set1024PosSize(384 + i * 48, 320.0F, 48.0F, 32.0F);
      }
      float f2 = lookAndFeel().getHSliderIntH();
      GUISetupInput.this.deadSlider.setPosSize(x1024(624.0F), y1024(368.0F) + (y1024(32.0F) - f2) / 2.0F, x1024(208.0F), f2);
      GUISetupInput.this.filterSlider.setPosSize(x1024(624.0F), y1024(416.0F) + (y1024(32.0F) - f2) / 2.0F, x1024(208.0F), f2);
      GUISetupInput.this.wMouse.set1024PosSize(768.0F, 512.0F, 96.0F, 32.0F);
      GUISetupInput.this.bBack.setPosC(x1024(56.0F), y1024(632.0F));
      GUISetupInput.this.bDefault.setPosC(x1024(264.0F), y1024(632.0F));
      GUISetupInput.this.bControlPanel.setPosC(x1024(840.0F), y1024(632.0F));
      if (GUISetupInput.this.sForceFeedback != null)
        GUISetupInput.this.sForceFeedback.setPosC(x1024(96.0F), y1024(528.0F));
    }
  }
}