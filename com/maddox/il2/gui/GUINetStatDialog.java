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
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserStat;
import com.maddox.rts.NetEnv;
import java.util.List;

public class GUINetStatDialog extends GWindowFramed
{
  public WClient wClient;
  public Table wTable;

  public void doRender(boolean paramBoolean)
  {
    boolean bool = GUI.chatDlg.isTransparent();
    int i = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha;
    if (bool) this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0;
    super.doRender(paramBoolean);
    if (bool) this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = i; 
  }

  public void preRender()
  {
    Chat localChat = Main.cur().chat;
    if (localChat == null)
      hideWindow(); 
  }

  public void afterCreated() {
    this.wClient = ((WClient)create(new WClient()));
    this.clientWindow = this.wClient;
    this.wTable = new Table(this.wClient);
    super.afterCreated();
    this.jdField_closeBox_of_type_ComMaddoxGwindowGWindowFrameCloseBox.hideWindow();
    this.jdField_closeBox_of_type_ComMaddoxGwindowGWindowFrameCloseBox = null;
  }

  public GUINetStatDialog(GWindow paramGWindow) {
    this.bAlwaysOnTop = true;
    this.title = "";
    paramGWindow.create(this);
    set1024PosSize(300.0F, 32.0F, 500.0F, 100.0F);
  }

  public class WClient extends GWindowDialogClient
  {
    public WClient()
    {
    }

    public void resized()
    {
      GUINetStatDialog.this.wTable.setPosSize(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
      super.resized();
    }
  }

  public class Table extends GWindowTable
  {
    public int countRows()
    {
      return NetEnv.hosts().size() + 1;
    }
    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      if (GUI.chatDlg.isTransparent())
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 255;
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      String str = null;
      int i = 1;
      NetUser localNetUser = (NetUser)NetEnv.host();
      if (paramInt1 > 0)
        localNetUser = (NetUser)NetEnv.hosts().get(paramInt1 - 1);
      switch (paramInt2) { case 0:
        str = localNetUser.uniqueName(); break;
      case 1:
        str = "" + localNetUser.ping; break;
      case 2:
        str = "" + (int)localNetUser.stat().score;
      }
      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      }
      if (GUI.chatDlg.isTransparent())
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.alpha = 0; 
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if ((paramInt1 == 11) && (paramInt2 == 27)) {
        GUI.chatUnactivate();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelecting = false;
      addColumn("Name", null);
      addColumn("Ping", null);
      addColumn("Score", null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(4.0F);
      getColumn(1).setRelativeDx(2.0F);
      getColumn(2).setRelativeDx(2.0F);
      alignColumns();

      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super();
    }
  }
}