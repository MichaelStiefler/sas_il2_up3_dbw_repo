// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WSnap.java

package com.maddox.il2.builder;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenuItem;

// Referenced classes of package com.maddox.il2.builder:
//            Builder, Plugin

public class WSnap extends com.maddox.gwindow.GWindowFramed
{

    public void windowShown()
    {
        builder.mSnap.bChecked = true;
        super.windowShown();
    }

    public void windowHidden()
    {
        builder.mSnap.bChecked = false;
        super.windowHidden();
    }

    public void created()
    {
        bAlwaysOnTop = true;
        super.created();
        title = com.maddox.il2.builder.Plugin.i18n("title.Snap");
        clientWindow = create(new GWindowDialogClient());
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Step"), null));
        gwindowdialogclient.addControl(wStep = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 1.0F, 6F, 1.3F, "") {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    try
                    {
                        builder.snapStep = java.lang.Double.parseDouble(getValue());
                    }
                    catch(java.lang.Exception exception) { }
                return super.notify(i, j);
            }

            public void created()
            {
                bNumericOnly = true;
                bNumericFloat = true;
                bDelayedNotify = true;
            }

        }
);
        wStep.setValue("" + builder.snapStep, false);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Enable"), null));
        gwindowdialogclient.addControl(wEnable = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 9F, 3F, null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    builder.bSnap = isChecked();
                return true;
            }

        }
);
    }

    public void afterCreated()
    {
        super.afterCreated();
        wEnable.setChecked(builder.bSnap, false);
        resized();
        close(false);
    }

    public WSnap(com.maddox.il2.builder.Builder builder1, com.maddox.gwindow.GWindow gwindow)
    {
        builder = builder1;
        doNew(gwindow, 2.0F, 2.0F, 17F, 7F, true);
    }

    com.maddox.il2.builder.Builder builder;
    com.maddox.gwindow.GWindowEditControl wStep;
    com.maddox.gwindow.GWindowCheckBox wEnable;
}
