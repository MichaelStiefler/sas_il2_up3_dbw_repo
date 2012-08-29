package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;

public class WViewLand extends GWindowFramed
{
  Builder builder;
  GWindowHSliderInt wLight;
  GWindowCheckBox wShow;

  public void windowShown()
  {
    this.builder.mViewLand.bChecked = true;
    super.windowShown();
  }
  public void windowHidden() {
    this.builder.mViewLand.bChecked = false;
    super.windowHidden();
  }

  public void created() {
    this.bAlwaysOnTop = true;
    super.created();
    this.title = Plugin.i18n("Landscape");
    this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(new GWindowDialogClient());
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
    GWindowLabel localGWindowLabel;
    localGWindowDialogClient.addLabel(localGWindowLabel = new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 6.0F, 1.3F, Plugin.i18n("Light"), null));
    localGWindowLabel.jdField_align_of_type_Int = 2;
    if (this.builder.conf.iLightLand < 0) this.builder.conf.iLightLand = 0;
    if (this.builder.conf.iLightLand > 255) this.builder.conf.iLightLand = 255;
    localGWindowDialogClient.addControl(this.wLight = new GWindowHSliderInt(localGWindowDialogClient, 0, 256, this.builder.conf.iLightLand, 8.0F, 1.0F, 10.0F) {
      public boolean notify(int paramInt1, int paramInt2) {
        WViewLand.this.builder.conf.iLightLand = pos();
        return super.notify(paramInt1, paramInt2);
      }
      public void created() { this.bSlidingNotify = true;
      }
    });
    this.wLight.resized();
    this.wLight.toolTip = Plugin.i18n("TIPLight");
    localGWindowDialogClient.addLabel(localGWindowLabel = new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 6.0F, 1.3F, Plugin.i18n("Show"), null));
    localGWindowLabel.jdField_align_of_type_Int = 2;
    localGWindowDialogClient.addControl(this.wShow = new GWindowCheckBox(localGWindowDialogClient, 8.0F, 3.0F, Plugin.i18n("TIPShow")) {
      public boolean _notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2)
          WViewLand.this.builder.changeViewLand();
        return true;
      } } );
  }

  public void afterCreated() {
    super.afterCreated();
    this.wShow.setChecked(this.builder.conf.bShowLandscape, false);
    resized();
    close(false);
  }

  public WViewLand(Builder paramBuilder, GWindow paramGWindow) {
    this.builder = paramBuilder;
    doNew(paramGWindow, 2.0F, 2.0F, 20.0F, 7.0F, true);
    this.bSizable = false;
  }
}