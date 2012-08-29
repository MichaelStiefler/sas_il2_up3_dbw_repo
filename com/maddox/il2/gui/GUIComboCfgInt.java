package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.il2.game.I18N;
import com.maddox.rts.CfgInt;

public class GUIComboCfgInt extends GWindowComboControl
{
  public CfgInt cfg;
  public boolean bUpdate;
  public boolean bChanged;
  public String i18nPref;

  public GUIComboCfgInt(GWindow paramGWindow, CfgInt paramCfgInt, boolean paramBoolean)
  {
    this(paramGWindow, paramCfgInt, paramBoolean, null);
  }

  public GUIComboCfgInt(GWindow paramGWindow, CfgInt paramCfgInt, boolean paramBoolean, String paramString) {
    super(paramGWindow, 0.0F, 0.0F, 1.0F);

    this.i18nPref = paramString;
    setEditable(false);
    this.bUpdate = paramBoolean;
    this.bChanged = false;
    this.cfg = paramCfgInt;
    refresh(true);
    int i = paramCfgInt.firstState();
    setSelected(paramCfgInt.get() - i, true, false);
  }

  public void created() {
    super.created();
    this.metricWin = null;
  }

  public void update() {
    refresh(false);
  }

  public void refresh(boolean paramBoolean) {
    this.cfg.reset();
    int i = this.cfg.countStates();
    int j = this.cfg.firstState();
    if (this.jdField_posEnable_of_type_ArrayOfBoolean == null)
      this.jdField_posEnable_of_type_ArrayOfBoolean = new boolean[i];
    for (int k = 0; k < i; k++) {
      if (paramBoolean) {
        if (this.i18nPref == null) {
          add(this.cfg.nameState(k + j));
        } else {
          String str1 = this.cfg.nameState(k + j);
          String str2 = this.i18nPref + str1;
          if (I18N.isGuiExist(str2))
            add(I18N.gui(str2));
          else
            add(str1);
        }
      }
      this.jdField_posEnable_of_type_ArrayOfBoolean[k] = this.cfg.isEnabledState(k + j);
    }
    setEnable(this.cfg.isEnabled());
  }

  public void resolutionChanged() {
    super.resolutionChanged();
    refresh(false);
  }
  public boolean notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 2) {
      int i = this.cfg.get() - this.cfg.firstState();
      if (i != getSelected()) {
        if (this.bUpdate) {
          this.cfg.set(getSelected() + this.cfg.firstState());
          int j = this.cfg.apply();
          this.cfg.reset();
          this.cfg.applyExtends(j);
          int k = this.cfg.get() - this.cfg.firstState();
          if (k != getSelected())
            setSelected(k, true, false);
        } else {
          this.bChanged = true;
        }
      }
    }
    return super.notify(paramInt1, paramInt2);
  }
}