// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetStatDialog.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFrameCloseBox;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserStat;
import com.maddox.rts.NetEnv;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUI, GUIChatDialog

public class GUINetStatDialog extends com.maddox.gwindow.GWindowFramed
{
    public class WClient extends com.maddox.gwindow.GWindowDialogClient
    {

        public void resized()
        {
            wTable.setPosSize(0.0F, 0.0F, win.dx, win.dy);
            super.resized();
        }

        public WClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return com.maddox.rts.NetEnv.hosts().size() + 1;
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            if(com.maddox.il2.gui.GUI.chatDlg.isTransparent())
                root.C.alpha = 255;
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            java.lang.String s = null;
            int k = 1;
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(i > 0)
                netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(i - 1);
            switch(j)
            {
            case 0: // '\0'
                s = netuser.uniqueName();
                break;

            case 1: // '\001'
                s = "" + netuser.ping;
                break;

            case 2: // '\002'
                s = "" + (int)netuser.stat().score;
                break;
            }
            if(flag)
            {
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, k, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, k, s);
            }
            if(com.maddox.il2.gui.GUI.chatDlg.isTransparent())
                root.C.alpha = 0;
        }

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i == 11 && j == 27)
            {
                com.maddox.il2.gui.GUI.chatUnactivate();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelecting = false;
            addColumn("Name", null);
            addColumn("Ping", null);
            addColumn("Score", null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(4F);
            getColumn(1).setRelativeDx(2.0F);
            getColumn(2).setRelativeDx(2.0F);
            alignColumns();
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public void doRender(boolean flag)
    {
        boolean flag1 = com.maddox.il2.gui.GUI.chatDlg.isTransparent();
        int i = root.C.alpha;
        if(flag1)
            root.C.alpha = 0;
        super.doRender(flag);
        if(flag1)
            root.C.alpha = i;
    }

    public void preRender()
    {
        com.maddox.il2.net.Chat chat = com.maddox.il2.game.Main.cur().chat;
        if(chat == null)
            hideWindow();
    }

    public void afterCreated()
    {
        wClient = (com.maddox.il2.gui.WClient)create(new WClient());
        clientWindow = wClient;
        wTable = new Table(wClient);
        super.afterCreated();
        closeBox.hideWindow();
        closeBox = null;
    }

    public GUINetStatDialog(com.maddox.gwindow.GWindow gwindow)
    {
        bAlwaysOnTop = true;
        title = "";
        gwindow.create(this);
        set1024PosSize(300F, 32F, 500F, 100F);
    }

    public com.maddox.il2.gui.WClient wClient;
    public com.maddox.il2.gui.Table wTable;
}
