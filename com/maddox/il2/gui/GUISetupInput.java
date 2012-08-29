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
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.JoyFF;
import com.maddox.rts.LDRres;
import com.maddox.rts.Mouse;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ResourceBundle;

public class GUISetupInput extends GameState
{
  public GWindowComboControl wJoyProfile;
  public GUIButton bLoad;
  public GUIButton bSave;
  public GUISwitchBox3 sMirrorControl;
  public static final int MAX_DEAD_BAND = 50;
  public static final int MAX_FILTER = 10;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowComboControl comboAxe;
  public GWindowVSliderInt[] slider;
  public GWindowEditControl[] edit;
  public GWindowHSliderInt deadSlider;
  public GWindowHSliderInt filterSlider;
  public GWindowEditControl wMouse;
  public GUIButton bBack;
  public GUIButton bDefault;
  public GUISwitchBox3 sForceFeedback;
  public GUIButton bControlPanel;
  private int[] comboAxeReversed;
  private int[] comboAxeInts;
  private int[] comboAxeREA = { -1, -1, -1 };
  private int[] comboAxeIndx = { 1, 0, 5 };
  public Joy joy;
  private int[] js;

  public void _enter()
  {
    fillDialogs();
    if (this.sForceFeedback != null)
      this.sForceFeedback.setChecked(JoyFF.isStarted(), false);
    this.client.activateWindow();
  }

  public void _leave()
  {
    this.client.hideWindow();
  }

  private void fillDialogs()
  {
    for (int i = 0; i < 3; i++) {
      this.comboAxeREA[i] = -1;
    }
    for (i = 0; i < 32; i++) {
      this.comboAxeReversed[i] = 1;
    }
    i = 0;
    int j = this.comboAxe.getSelected();
    this.comboAxe.clear(false);
    String[] arrayOfString = UserCfg.nameHotKeyEnvs;
    for (int k = 0; k < arrayOfString.length; k++)
    {
      if (arrayOfString[k].equals("move")) {
        HotKeyEnv localHotKeyEnv = HotKeyEnv.env(arrayOfString[k]);
        HashMapInt localHashMapInt = localHotKeyEnv.all();
        HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);

        while (localHashMapIntEntry != null)
        {
          this.comboAxeInts[i] = localHashMapIntEntry.getKey();
          String str1 = (String)localHashMapIntEntry.getValue();
          if (str1.startsWith("-")) {
            str1 = str1.substring(1);
            this.comboAxeReversed[i] = -1;
          }String str2;
          try {
            str2 = ResourceBundle.getBundle("i18n/controls", RTSConf.cur.locale, LDRres.loader()).getString(str1);
          }
          catch (Exception localException) {
            str2 = str1;
            System.out.println("Warning: Control" + str1 + "is not present in i18n/controls.properties file");
          }
          int m = (this.comboAxeInts[i] & 0xFFFF) - 531 - 49;
          int n = (this.comboAxeInts[i] >> 16) - 531 - 32;
          if (VK.getKeyText(this.comboAxeInts[i] >> 16 & 0xFFFF).startsWith("Joystick")) {
            this.comboAxeInts[i] = ((this.comboAxeInts[i] >> 16) + ((this.comboAxeInts[i] & 0xFFFF) << 16));
            m = (this.comboAxeInts[i] & 0xFFFF) - 531 - 49;
            n = (this.comboAxeInts[i] >> 16) - 531 - 32;
          }
          if (Joy.adapter().isExistAxe(m, n)) {
            this.comboAxe.add(str2);
            if (str1.equals("rudder")) {
              this.comboAxeREA[0] = i;
            }
            else if (str1.equals("elevator")) {
              this.comboAxeREA[1] = i;
            }
            else if (str1.equals("aileron")) {
              this.comboAxeREA[2] = i;
            }

            i++;
          }
          localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
        }
      }
    }

    if (i == 0)
    {
      this.sMirrorControl.setEnable(false);
      for (k = 0; k < 9; k++)
      {
        this.slider[k].setEnable(false);
        this.edit[k].setEnable(false);
      }

      this.deadSlider.setEnable(false);
      this.filterSlider.setEnable(false);
      this.comboAxe.add(i18n("setupIn.none"));
      this.comboAxe.setSelected(0, true, false);
      this.comboAxe.setEnable(false);
    }
    else {
      this.sMirrorControl.setEnable(true);
      for (k = 0; k < 9; k++)
      {
        this.slider[k].setEnable(true);
        this.edit[k].setEnable(true);
      }

      this.deadSlider.setEnable(true);
      this.filterSlider.setEnable(true);
      this.comboAxe.setEnable(true);
      if ((j < 0) || (j >= i))
        j = 0;
      this.comboAxe.setSelected(-1, false, false);
      this.comboAxe.setSelected(j, true, true);
    }
    float[] arrayOfFloat = Mouse.adapter().getSensitivity();
    this.wMouse.setValue("" + arrayOfFloat[0], false);
  }

  private int curAxe()
  {
    int i = this.comboAxe.getSelected();
    int j = this.comboAxeInts[i];
    if (i < 0)
      return -1;
    return j;
  }

  private void fillSliders(int paramInt)
  {
    if (paramInt < 0)
      return;
    int i = (paramInt & 0xFFFF) - 531 - 49;
    int j = (paramInt >> 16) - 531 - 32;
    Joy.adapter().getSensitivity(i, j, this.js);
    if (this.js[12] == 0) {
      this.sMirrorControl.setChecked(true, false);
    }
    else {
      this.sMirrorControl.setChecked(false, false);
    }
    if (this.js[0] < 0)
      this.js[0] = 0;
    if (this.js[0] > 50)
      this.js[0] = 50;
    this.deadSlider.setPos(this.js[0], false);
    this.filterSlider.setPos(10 * this.js[11] / 100, false);
    for (int k = 0; k < 10; k++)
    {
      if (this.js[(k + 1)] < 0)
        this.js[(k + 1)] = 0;
      if (this.js[(k + 1)] > 100)
        this.js[(k + 1)] = 100;
      this.slider[k].setPos(this.js[(k + 1)], false);
      this.edit[k].setValue("" + this.js[(k + 1)], false);
    }
  }

  private void doSetDefault()
  {
    this.js[0] = 0;
    for (int i = 1; i < 11; i++) {
      this.js[i] = (i * 10);
    }
    this.js[11] = 0;
    this.js[12] = 0;
    for (i = 0; i < 4; i++) {
      for (int j = 0; j < 8; j++) {
        if (Joy.adapter().isExistAxe(i, j))
          Joy.adapter().setSensitivity(i, j, this.js);
      }
    }
    float[] arrayOfFloat = Mouse.adapter().getSensitivity();
    float tmp109_108 = 1.0F; arrayOfFloat[1] = tmp109_108; arrayOfFloat[0] = tmp109_108;
    fillDialogs();
  }

  private void setJoyS(int paramInt1, int paramInt2)
  {
    int i = curAxe();
    int j = (i & 0xFFFF) - 531 - 49;
    int k = (i >> 16) - 531 - 32;
    if (i < 0)
    {
      return;
    }

    Joy.adapter().getSensitivity(j, k, this.js);
    this.js[paramInt1] = paramInt2;
    Joy.adapter().setSensitivity(j, k, this.js);
    fillSliders(i);
  }

  private float clampValue(GWindowEditControl paramGWindowEditControl, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    String str = paramGWindowEditControl.getValue();
    try
    {
      paramFloat1 = Float.parseFloat(str);
    } catch (Exception localException) {
    }
    if (paramFloat1 < paramFloat2)
      paramFloat1 = paramFloat2;
    if (paramFloat1 > paramFloat3)
      paramFloat1 = paramFloat3;
    paramGWindowEditControl.setValue("" + paramFloat1, false);
    return paramFloat1;
  }

  private int clampValue(GWindowEditControl paramGWindowEditControl, int paramInt1, int paramInt2, int paramInt3)
  {
    String str = paramGWindowEditControl.getValue();
    try
    {
      paramInt1 = Integer.parseInt(str);
    } catch (Exception localException) {
    }
    if (paramInt1 < paramInt2)
      paramInt1 = paramInt2;
    if (paramInt1 > paramInt3)
      paramInt1 = paramInt3;
    paramGWindowEditControl.setValue("" + paramInt1, false);
    return paramInt1;
  }

  public GUISetupInput(GWindowRoot paramGWindowRoot)
  {
    super(53);
    this.slider = new GWindowVSliderInt[10];
    this.edit = new GWindowEditControl[10];
    this.js = new int[13];
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setupIn.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));
    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.dialogClient.addControl(this.comboAxe = new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F));
    this.wJoyProfile = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.bLoad = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSave = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.sMirrorControl = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.comboAxe.setEditable(false);
    this.comboAxe.resized();
    for (int i = 0; i < 10; i++)
    {
      this.dialogClient.addControl(this.slider[i] =  = new GWindowVSliderInt(this.dialogClient));
      this.slider[i].setRange(0, 101, 10 * (i + 1));
      this.edit[i] = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)
      {
        public void keyboardKey(int paramInt, boolean paramBoolean)
        {
          super.keyboardKey(paramInt, paramBoolean);
          if ((paramInt == 10) && (paramBoolean))
            notify(2, 0);
        }
      }));
      this.edit[i].bSelectOnFocus = false;
      this.edit[i].bDelayedNotify = true;
      this.edit[i].bNumericOnly = true;
      this.edit[i].align = 1;
    }

    this.dialogClient.addControl(this.deadSlider = new GWindowHSliderInt(this.dialogClient));
    this.deadSlider.setRange(0, 51, 0);
    this.dialogClient.addControl(this.filterSlider = new GWindowHSliderInt(this.dialogClient));
    this.filterSlider.setRange(0, 11, 0);
    this.wMouse = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)
    {
      public void keyboardKey(int paramInt, boolean paramBoolean)
      {
        super.keyboardKey(paramInt, paramBoolean);
        if ((paramInt == 10) && (paramBoolean))
          notify(2, 0);
      }
    }));
    this.wMouse.bNumericOnly = (this.wMouse.bNumericFloat = 1);
    this.wMouse.bDelayedNotify = true;
    this.wMouse.align = 1;
    this.wJoyProfile.add(i18n("setupIn.Joystick1"));
    this.wJoyProfile.add(i18n("setupIn.Joystick2"));
    this.wJoyProfile.add(i18n("setupIn.Joystick3"));
    this.wJoyProfile.add(i18n("setupIn.Joystick4"));
    this.wJoyProfile.setEditable(false);
    this.wJoyProfile.setSelected(Config.cur.ini.get("rts", "JoyProfile", 0), true, false);
    this.comboAxeInts = new int[32];
    this.comboAxeReversed = new int[32];
    this.bControlPanel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDefault = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    if (JoyFF.isAttached())
      this.sForceFeedback = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    private int[] joyPos;
    private int[] joyRawPos;

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2)
        return super.notify(paramGWindow, paramInt1, paramInt2);
      if (paramGWindow == GUISetupInput.this.bControlPanel)
      {
        if (GUISetupInput.this.wJoyProfile.getSelected() > 0) {
          Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick" + GUISetupInput.this.wJoyProfile.getSelected());
        }
        else {
          Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick");
        }
        Main.stateStack().push(20);
        GUIControls localGUIControls = (GUIControls)Main.stateStack().peek();
        localGUIControls.scrollClient.vScroll.setPos(localGUIControls.scrollClient.vScroll.posMax, true);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.sMirrorControl) {
        if (GUISetupInput.this.js[12] == 0) {
          if (GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxe.getSelected()] == -1.0D) {
            GUISetupInput.this.js[12] = 2;
          }
          else {
            GUISetupInput.this.js[12] = 1;
          }
        }
        else {
          GUISetupInput.this.js[12] = 0;
        }
        GUISetupInput.this.setJoyS(12, GUISetupInput.this.js[12]);
        setPosSize();
        return true;
      }

      if (paramGWindow == GUISetupInput.this.bSave)
      {
        Mouse.adapter().saveConfig(Config.cur.ini, "rts_mouse");
        if (GUISetupInput.this.sForceFeedback != null)
          if (GUISetupInput.this.sForceFeedback.isChecked())
          {
            if (!JoyFF.isStarted())
            {
              JoyFF.setEnable(true);
              ForceFeedback.start();
              if (Mission.isPlaying())
                ForceFeedback.startMission();
            }
          }
          else if (JoyFF.isStarted())
          {
            ForceFeedback.stop();
            JoyFF.setEnable(false);
          }
        if (GUISetupInput.this.wJoyProfile.getSelected() > 0) {
          Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick" + GUISetupInput.this.wJoyProfile.getSelected());
        }
        else {
          Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick");
        }
        return true;
      }
      if (paramGWindow == GUISetupInput.this.bLoad)
      {
        if (GUISetupInput.this.wJoyProfile.getSelected() > 0) {
          Joy.adapter().loadConfig(Config.cur.ini, "rts_joystick" + GUISetupInput.this.wJoyProfile.getSelected());
        }
        else {
          Joy.adapter().loadConfig(Config.cur.ini, "rts_joystick");
        }
        GUISetupInput.this.fillDialogs();
        return true;
      }

      if (paramGWindow == GUISetupInput.this.comboAxe)
      {
        GUISetupInput.this.fillSliders(GUISetupInput.access$400(GUISetupInput.this));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[0])
      {
        GUISetupInput.this.setJoyS(1, GUISetupInput.this.slider[0].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[1])
      {
        GUISetupInput.this.setJoyS(2, GUISetupInput.this.slider[1].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[2])
      {
        GUISetupInput.this.setJoyS(3, GUISetupInput.this.slider[2].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[3])
      {
        GUISetupInput.this.setJoyS(4, GUISetupInput.this.slider[3].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[4])
      {
        GUISetupInput.this.setJoyS(5, GUISetupInput.this.slider[4].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[5])
      {
        GUISetupInput.this.setJoyS(6, GUISetupInput.this.slider[5].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[6])
      {
        GUISetupInput.this.setJoyS(7, GUISetupInput.this.slider[6].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[7])
      {
        GUISetupInput.this.setJoyS(8, GUISetupInput.this.slider[7].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[8])
      {
        GUISetupInput.this.setJoyS(9, GUISetupInput.this.slider[8].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.slider[9])
      {
        GUISetupInput.this.setJoyS(10, GUISetupInput.this.slider[9].pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.deadSlider)
      {
        GUISetupInput.this.setJoyS(0, GUISetupInput.this.deadSlider.pos());
        return true;
      }
      if (paramGWindow == GUISetupInput.this.filterSlider)
      {
        GUISetupInput.this.setJoyS(11, 100 * GUISetupInput.this.filterSlider.pos() / 10);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[0])
      {
        GUISetupInput.this.setJoyS(1, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[0], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[1])
      {
        GUISetupInput.this.setJoyS(2, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[1], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[2])
      {
        GUISetupInput.this.setJoyS(3, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[2], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[3])
      {
        GUISetupInput.this.setJoyS(4, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[3], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[4])
      {
        GUISetupInput.this.setJoyS(5, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[4], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[5])
      {
        GUISetupInput.this.setJoyS(6, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[5], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[6])
      {
        GUISetupInput.this.setJoyS(7, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[6], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[7])
      {
        GUISetupInput.this.setJoyS(8, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[7], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[8])
      {
        GUISetupInput.this.setJoyS(9, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[8], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.edit[9])
      {
        GUISetupInput.this.setJoyS(10, GUISetupInput.access$600(GUISetupInput.this, GUISetupInput.this.edit[9], 0, 0, 100));
        return true;
      }
      if (paramGWindow == GUISetupInput.this.wMouse)
      {
        float f = GUISetupInput.this.clampValue(GUISetupInput.this.wMouse, 1.0F, 0.1F, 10.0F);
        float[] arrayOfFloat = Mouse.adapter().getSensitivity();
        float tmp1452_1450 = f; arrayOfFloat[1] = tmp1452_1450; arrayOfFloat[0] = tmp1452_1450;
        GUISetupInput.this.wMouse.setValue("" + f, false);
        return true;
      }
      if (paramGWindow == GUISetupInput.this.bDefault)
      {
        GUISetupInput.this.doSetDefault();
        return true;
      }
      if (paramGWindow == GUISetupInput.this.bBack)
      {
        Mouse.adapter().saveConfig(Config.cur.ini, "rts_mouse");
        if (GUISetupInput.this.sForceFeedback != null)
          if (GUISetupInput.this.sForceFeedback.isChecked())
          {
            if (!JoyFF.isStarted())
            {
              JoyFF.setEnable(true);
              ForceFeedback.start();
              if (Mission.isPlaying())
                ForceFeedback.startMission();
            }
          }
          else if (JoyFF.isStarted())
          {
            ForceFeedback.stop();
            JoyFF.setEnable(false);
          }
        Config.cur.ini.set("rts", "JoyProfile", GUISetupInput.this.wJoyProfile.getSelected());
        if (GUISetupInput.this.wJoyProfile.getSelected() > 0)
        {
          Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick" + GUISetupInput.this.wJoyProfile.getSelected());
        }
        else Joy.adapter().saveConfig(Config.cur.ini, "rts_joystick");
        Main.stateStack().pop();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render()
    {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(256.0F), y1024(48.0F), x1024(160.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(490.0F), x1024(528.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(576.0F), x1024(832.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(48.0F), 2.0F, y1024(444.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(48.0F), y1024(32.0F), x1024(192.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.JoystickAxes"));
      draw(x1024(432.0F), y1024(32.0F), x1024(208.0F), y1024(32.0F), 0, GUISetupInput.this.i18n("setupIn.Profile"));
      draw(x1024(384.0F), y1024(80.0F), x1024(400.0F), y1024(32.0F), 0, GUISetupInput.this.comboAxe.getValue());
      draw(x1024(368.0F), y1024(364.0F), x1024(208.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.DeadBand"));
      draw(x1024(576.0F), y1024(364.0F), x1024(48.0F), y1024(32.0F), 1, "0");
      draw(x1024(832.0F), y1024(364.0F), x1024(48.0F), y1024(32.0F), 1, "50");
      draw(x1024(368.0F), y1024(402.0F), x1024(208.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.Filtering"));
      draw(x1024(576.0F), y1024(402.0F), x1024(48.0F), y1024(32.0F), 1, "0");
      draw(x1024(832.0F), y1024(402.0F), x1024(48.0F), y1024(32.0F), 1, "1");
      if (GUISetupInput.this.sForceFeedback != null)
        draw(x1024(136.0F), y1024(520.0F), x1024(208.0F), y1024(32.0F), 0, GUISetupInput.this.i18n("setupIn.ForceFeedback"));
      draw(x1024(416.0F), y1024(520.0F), x1024(336.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.MouseSensitivity"));
      draw(x1024(96.0F), y1024(608.0F), x1024(128.0F), y1024(48.0F), 0, GUISetupInput.this.i18n("setupIn.Back"));
      draw(x1024(304.0F), y1024(608.0F), x1024(160.0F), y1024(48.0F), 0, GUISetupInput.this.i18n("setupIn.Default"));
      draw(x1024(528.0F), y1024(608.0F), x1024(272.0F), y1024(48.0F), 2, GUISetupInput.this.i18n("setupIn.ControlPanel"));
      draw(x1024(654.0F), y1024(62.0F), x1024(128.0F), y1024(48.0F), 1, GUISetupInput.this.i18n("setupIn.Load"));
      draw(x1024(754.0F), y1024(62.0F), x1024(128.0F), y1024(48.0F), 1, GUISetupInput.this.i18n("setupIn.Save"));
      draw(x1024(368.0F), y1024(444.0F), x1024(208.0F), y1024(32.0F), 2, GUISetupInput.this.i18n("setupIn.Symmetrical"));
      int i = 1;
      int j = 1;
      int k = 1;
      if ((GUISetupInput.this.comboAxeREA[1] == -1) || (GUISetupInput.this.comboAxeREA[2] == -1)) {
        j = 0;
      }
      if (GUISetupInput.this.comboAxeREA[0] == -1) {
        i = 0;
      }
      if ((GUISetupInput.this.comboAxe.getValue().equals(GUISetupInput.this.i18n("setupIn.none"))) && (i == 0) && (j == 0)) {
        return;
      }
      for (int m = 0; m < 3; m++) {
        if (GUISetupInput.this.comboAxe.getSelected() == GUISetupInput.this.comboAxeREA[m]) {
          k = 0;
        }
      }
      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      GBevel localGBevel = localGUILookAndFeel.bevelComboDown;
      if (k != 0) {
        float f1 = localGBevel.L.dx;
        float f2 = x1024(32.0F);
        float f3 = y1024(208.0F) / 2.0F - f1;
        float f4 = y1024(16.0F);
        int i4 = GUISetupInput.this.curAxe();
        int i5 = (i4 & 0xFFFF) - 531 - 49;
        int i6 = (i4 >> 16) - 531 - 32;
        Joy.adapter().getNotFilteredPos(i5, this.joyRawPos);
        Joy.adapter().getPos(i5, this.joyPos);
        f8 = Joy.normal(this.joyRawPos[i6]);
        f9 = Joy.normal(this.joyPos[i6]);
        setCanvasColorWHITE();
        localGUILookAndFeel.drawBevel(this, x1024(272.0F), y1024(240.0F), f2, y1024(208.0F), localGBevel, localGUILookAndFeel.basicelements);
        if (GUISetupInput.this.js[12] == 0) {
          f3 -= f4 / 2.0F;
          if (GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxe.getSelected()] == -1.0D) {
            f8 = -f8;
            f9 = -f9;
          }
          GUISeparate.draw(this, GColor.Black, x1024(272.0F) + 2.0F * f1, y1024(344.0F), x1024(32.0F) - 4.0F * f1, 1.0F);
          GUISeparate.draw(this, GColor.Red, x1024(272.0F) + 2.0F * f1, y1024(448.0F) - (f8 + 1.0F) * f3 - f1 - f4, f2 - 4.0F * f1, f4);
          GUISeparate.draw(this, GColor.Green, x1024(272.0F) + 2.0F * f1, y1024(448.0F) - (f9 + 1.0F) * f3 - f1 - f4, f2 - 4.0F * f1, f4);
        }
        else {
          if (GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxe.getSelected()] == -1.0D) {
            f8 = -f8;
            f9 = -f9;
          }
          GUISeparate.draw(this, GColor.Red, x1024(272.0F) + f1, y1024(448.0F) - (f8 + 1.0F) * f3 - f1, x1024(32.0F) - 2.0F * f1, (f8 + 1.0F) * f3);
          GUISeparate.draw(this, GColor.Green, x1024(272.0F) + 2.0F * f1, y1024(448.0F) - (f9 + 1.0F) * f3 - f1, x1024(32.0F) - 4.0F * f1, (f9 + 1.0F) * f3);
        }
      }
      if (j == 0) {
        return;
      }
      int n = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[1]] & 0xFFFF) - 531 - 49;
      int i1 = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[1]] >> 16) - 531 - 32;
      int i2 = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[2]] & 0xFFFF) - 531 - 49;
      int i3 = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[2]] >> 16) - 531 - 32;
      Joy.adapter().getNotFilteredPos(n, this.joyRawPos);
      Joy.adapter().getPos(n, this.joyPos);
      float f5 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[1]] * this.joyPos[i1]);
      float f6 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[1]] * this.joyRawPos[i1]);
      Joy.adapter().getNotFilteredPos(i2, this.joyRawPos);
      Joy.adapter().getPos(i2, this.joyPos);
      float f7 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[2]] * this.joyPos[i3]);
      float f8 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[2]] * this.joyRawPos[i3]);
      float f9 = localGBevel.L.dx;
      float f10 = x1024(16.0F);
      setCanvasColorWHITE();
      localGUILookAndFeel.drawBevel(this, x1024(64.0F), y1024(240.0F), x1024(208.0F), y1024(208.0F), localGBevel, localGUILookAndFeel.basicelements);
      GUISeparate.draw(this, GColor.Black, x1024(64.0F) + 2.0F * f9, y1024(344.0F), x1024(208.0F) - 4.0F * f9, 1.0F);
      GUISeparate.draw(this, GColor.Black, x1024(168.0F), y1024(240.0F) + 2.0F * f9, 1.0F, y1024(208.0F) - 4.0F * f9);
      float f11 = (x1024(208.0F) - f10) / 2.0F - f9;
      float f12 = x1024(168.0F) + f8 * f11;
      float f13 = y1024(344.0F) + f6 * f11;
      GUISeparate.draw(this, GColor.Red, f12 - f10 / 2.0F, f13 - f10 / 2.0F, f10, f10);
      f12 = x1024(168.0F) + f7 * f11;
      f13 = y1024(344.0F) + f5 * f11;
      GUISeparate.draw(this, GColor.Green, f12 - f10 / 2.0F, f13 - f10 / 2.0F, f10, f10);
      if (i == 0) {
        return;
      }
      int i7 = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[0]] & 0xFFFF) - 531 - 49;
      int i8 = (GUISetupInput.this.comboAxeInts[GUISetupInput.this.comboAxeREA[0]] >> 16) - 531 - 32;
      Joy.adapter().getNotFilteredPos(i7, this.joyRawPos);
      Joy.adapter().getPos(i7, this.joyPos);
      float f14 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[0]] * this.joyRawPos[i8]);
      float f15 = Joy.normal(GUISetupInput.this.comboAxeReversed[GUISetupInput.this.comboAxeREA[0]] * this.joyPos[i8]);
      setCanvasColorWHITE();
      localGUILookAndFeel.drawBevel(this, x1024(64.0F), y1024(448.0F), x1024(208.0F), y1024(32.0F), localGBevel, localGUILookAndFeel.basicelements);
      GUISeparate.draw(this, GColor.Black, x1024(168.0F), y1024(448.0F) + 2.0F * f9, 1.0F, y1024(32.0F) - 4.0F * f9);
      float f16 = x1024(168.0F) + f14 * f11;
      GUISeparate.draw(this, GColor.Red, f16 - f10 / 2.0F, y1024(448.0F) + 2.0F * f9, f10, y1024(32.0F) - 4.0F * f9);
      f16 = x1024(168.0F) + f15 * f11;
      GUISeparate.draw(this, GColor.Green, f16 - f10 / 2.0F, y1024(448.0F) + 2.0F * f9, f10, y1024(32.0F) - 4.0F * f9);
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
      GUISetupInput.this.wJoyProfile.setPosSize(x1024(500.0F), y1024(32.0F), x1024(160.0F), M(1.7F));
      GUISetupInput.this.bLoad.setPosC(x1024(720.0F), y1024(50.0F));
      GUISetupInput.this.bSave.setPosC(x1024(820.0F), y1024(50.0F));
      GUISetupInput.this.sMirrorControl.setPosC(x1024(672.0F), y1024(460.0F));
      float f2 = lookAndFeel().getHSliderIntH();
      GUISetupInput.this.deadSlider.setPosSize(x1024(624.0F), y1024(364.0F) + (y1024(32.0F) - f2) / 2.0F, x1024(208.0F), f2);
      GUISetupInput.this.filterSlider.setPosSize(x1024(624.0F), y1024(402.0F) + (y1024(32.0F) - f2) / 2.0F, x1024(208.0F), f2);
      GUISetupInput.this.wMouse.set1024PosSize(768.0F, 520.0F, 96.0F, 32.0F);
      GUISetupInput.this.bBack.setPosC(x1024(56.0F), y1024(632.0F));
      GUISetupInput.this.bDefault.setPosC(x1024(264.0F), y1024(632.0F));
      GUISetupInput.this.bControlPanel.setPosC(x1024(840.0F), y1024(632.0F));
      if (GUISetupInput.this.sForceFeedback != null)
        GUISetupInput.this.sForceFeedback.setPosC(x1024(96.0F), y1024(532.0F));
    }

    public DialogClient()
    {
      this.joyPos = new int[8];
      this.joyRawPos = new int[8];
    }
  }
}