// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WViewLand.java

package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenuItem;

// Referenced classes of package com.maddox.il2.builder:
//            Builder, Plugin, BldConfig

public class WViewLand extends com.maddox.gwindow.GWindowFramed
{

    public void windowShown()
    {
        builder.mViewLand.bChecked = true;
        super.windowShown();
    }

    public void windowHidden()
    {
        builder.mViewLand.bChecked = false;
        super.windowHidden();
    }

    public void created()
    {
        bAlwaysOnTop = true;
        super.created();
        title = com.maddox.il2.builder.Plugin.i18n("Landscape");
        clientWindow = create(new GWindowDialogClient());
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
        com.maddox.gwindow.GWindowLabel gwindowlabel;
        gwindowdialogclient.addLabel(gwindowlabel = new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Light"), null));
        gwindowlabel.align = 2;
        if(builder.conf.iLightLand < 0)
            builder.conf.iLightLand = 0;
        if(builder.conf.iLightLand > 255)
            builder.conf.iLightLand = 255;
        gwindowdialogclient.addControl(wLight = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 256, builder.conf.iLightLand, 8F, 1.0F, 10F) {

            public boolean notify(int i, int j)
            {
                builder.conf.iLightLand = pos();
                return super.notify(i, j);
            }

            public void created()
            {
                bSlidingNotify = true;
            }

        }
);
        wLight.resized();
        wLight.toolTip = com.maddox.il2.builder.Plugin.i18n("TIPLight");
        gwindowdialogclient.addLabel(gwindowlabel = new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Show"), null));
        gwindowlabel.align = 2;
        gwindowdialogclient.addControl(wShow = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 8F, 3F, com.maddox.il2.builder.Plugin.i18n("TIPShow")) {

            public boolean _notify(int i, int j)
            {
                if(i == 2)
                    builder.changeViewLand();
                return true;
            }

        }
);
    }

    public void afterCreated()
    {
        super.afterCreated();
        wShow.setChecked(builder.conf.bShowLandscape, false);
        resized();
        close(false);
    }

    public WViewLand(com.maddox.il2.builder.Builder builder1, com.maddox.gwindow.GWindow gwindow)
    {
        builder = builder1;
        doNew(gwindow, 2.0F, 2.0F, 20F, 7F, true);
        bSizable = false;
    }

    com.maddox.il2.builder.Builder builder;
    com.maddox.gwindow.GWindowHSliderInt wLight;
    com.maddox.gwindow.GWindowCheckBox wShow;
}
