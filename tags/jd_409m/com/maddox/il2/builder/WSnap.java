package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;

public class WSnap extends GWindowFramed
{
  Builder builder;
  GWindowEditControl wStep;
  GWindowCheckBox wEnable;

  public void windowShown()
  {
    this.builder.mSnap.bChecked = true;
    super.windowShown();
  }
  public void windowHidden() {
    this.builder.mSnap.bChecked = false;
    super.windowHidden();
  }

  public void created() {
    this.bAlwaysOnTop = true;
    super.created();
    this.title = "Snap";
    this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(new GWindowDialogClient());
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 3.0F, 1.3F, "Step", null));
    localGWindowDialogClient.addControl(this.wStep = new GWindowEditControl(localGWindowDialogClient, 5.0F, 1.0F, 6.0F, 1.3F, "") {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) try {
            WSnap.this.builder.snapStep = Double.parseDouble(getValue());
          }
          catch (Exception localException) {
          } return super.notify(paramInt1, paramInt2);
      }
      public void created() {
        this.bNumericOnly = true;
        this.bNumericFloat = true;
        this.bDelayedNotify = true;
      }
    });
    this.wStep.setValue("" + this.builder.snapStep, false);
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 3.0F, 1.3F, "Enable", null));
    localGWindowDialogClient.addControl(this.wEnable = new GWindowCheckBox(localGWindowDialogClient, 5.0F, 3.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2)
          WSnap.this.builder.bSnap = isChecked();
        return true;
      } } );
  }

  public void afterCreated() {
    super.afterCreated();
    this.wEnable.setChecked(this.builder.bSnap, false);
    resized();
    close(false);
  }

  public WSnap(Builder paramBuilder, GWindow paramGWindow) {
    this.builder = paramBuilder;
    doNew(paramGWindow, 2.0F, 2.0F, 17.0F, 7.0F, true);
  }
}