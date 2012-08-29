package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.HomePath;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.KeyRecord;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GUIRecordSelect extends GameState
{
  public String selectedFile;
  public boolean bCycle = true;
  public boolean bManualTimeCompression = false;
  public boolean bManualViewControls = false;
  public boolean bDrawAllMessages = true;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wPlay;
  public GUIButton wDelete;
  public Table wTable;
  public GUISwitchBox2 sCycle;
  public GUISwitchBox2 sTimeCompression;
  public GUISwitchBox2 sViewControls;
  public GUISwitchBox2 sViewMessages;
  public boolean bSaveManualTimeCompression = false;
  public boolean bSaveManualViewControls = false;

  public TreeMap _scanMap = new TreeMap();

  public void _enter()
  {
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed()))
      Mission.cur().destroy();
    if (Main3D.cur3D().keyRecord != null)
      Main3D.cur3D().keyRecord.clearRecorded();
    this.bSaveManualTimeCompression = HotKeyEnv.isEnabled("timeCompression");
    this.bSaveManualViewControls = HotKeyEnv.isEnabled("aircraftView");
    this.sCycle.setChecked(this.bCycle, false);
    this.sTimeCompression.setChecked(this.bManualTimeCompression, false);
    this.sViewControls.setChecked(this.bManualViewControls, false);
    this.sViewMessages.setChecked(this.bDrawAllMessages, false);
    Main3D.cur3D().hud.bDrawAllMessages = this.bDrawAllMessages;
    fillFiles();
    this.client.activateWindow();
  }
  public void leavePop(GameState paramGameState) {
    Main3D.cur3D().hud.bDrawAllMessages = true;
    World.cur().setUserCovers();
    super.leavePop(paramGameState);
  }

  public void _leave() {
    HotKeyEnv.enable("timeCompression", this.bSaveManualTimeCompression);
    HotKeyEnv.enable("aircraftView", this.bSaveManualViewControls);
    HotKeyEnv.enable("HookView", this.bSaveManualViewControls);
    HotKeyEnv.enable("PanView", this.bSaveManualViewControls);
    HotKeyEnv.enable("SnapView", this.bSaveManualViewControls);
    this.client.hideWindow();
  }

  public void fillFiles() {
    this.wTable.files.clear();
    File localFile = new File(HomePath.get(0), "Records");
    File[] arrayOfFile = localFile.listFiles();
    if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
      for (int i = 0; i < arrayOfFile.length; i++) {
        if ((!arrayOfFile[i].isDirectory()) && (!arrayOfFile[i].isHidden()))
          this._scanMap.put(arrayOfFile[i].getName(), null);
      }
      Iterator localIterator = this._scanMap.keySet().iterator();
      while (localIterator.hasNext())
        this.wTable.files.add(localIterator.next());
      if (this._scanMap.size() > 0)
        this.wTable.setSelect(0, 0);
      this._scanMap.clear();
    }
    this.wTable.resized();
  }

  public GUIRecordSelect(GWindowRoot paramGWindowRoot)
  {
    super(7);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("record.infoSelect");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wPlay = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.wDelete = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.sCycle = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sTimeCompression = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sViewControls = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sViewMessages = ((GUISwitchBox2)this.dialogClient.addControl(new GUISwitchBox2(this.dialogClient)));
    this.sCycle.setChecked(this.bCycle, false);
    this.sTimeCompression.setChecked(this.bManualTimeCompression, false);
    this.sViewControls.setChecked(this.bManualViewControls, false);
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIRecordSelect.this.wPrev) {
        GUIRecordSelect.this.sCycle.setChecked(GUIRecordSelect.this.bCycle, false);
        GUIRecordSelect.this.sTimeCompression.setChecked(GUIRecordSelect.this.bManualTimeCompression, false);
        GUIRecordSelect.this.sViewControls.setChecked(GUIRecordSelect.this.bManualViewControls, false);
        Main3D.cur3D().viewSet_Load();
        Main.stateStack().pop();
      }
      else
      {
        int i;
        if (paramGWindow == GUIRecordSelect.this.wPlay) {
          GUIRecordSelect.this.bCycle = GUIRecordSelect.this.sCycle.isChecked();
          GUIRecordSelect.this.bManualTimeCompression = GUIRecordSelect.this.sTimeCompression.isChecked();
          GUIRecordSelect.this.bManualViewControls = GUIRecordSelect.this.sViewControls.isChecked();
          i = GUIRecordSelect.this.wTable.jdField_selectRow_of_type_Int;
          if ((i < 0) || (i >= GUIRecordSelect.this.wTable.files.size())) return true;
          GUIRecordSelect.this.selectedFile = ((String)GUIRecordSelect.this.wTable.files.get(i));

          Main.stateStack().push(8);
          return true;
        }if (paramGWindow == GUIRecordSelect.this.wDelete) {
          i = GUIRecordSelect.this.wTable.jdField_selectRow_of_type_Int;
          if ((i < 0) || (i >= GUIRecordSelect.this.wTable.files.size())) return true;
          new GUIRecordSelect.1(this, this.root, 20.0F, true, GUIRecordSelect.this.i18n("warning.Warning"), GUIRecordSelect.this.i18n("warning.DeleteFile"), 1, 0.0F);

          return true;
        }if (paramGWindow == GUIRecordSelect.this.sViewMessages) {
          Main3D.cur3D().hud.bDrawAllMessages = GUIRecordSelect.this.sViewMessages.isChecked();
          GUIRecordSelect.this.bDrawAllMessages = GUIRecordSelect.this.sViewMessages.isChecked();
        }
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(464.0F), x1024(720.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(448.0F), y1024(352.0F), x1024(305.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(432.0F), y1024(32.0F), 2.0F, y1024(400.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(528.0F), y1024(48.0F), x1024(224.0F), y1024(48.0F), GUIRecordSelect.this.i18n("record.Cycle"), 2);
      draw(x1024(528.0F), y1024(128.0F), x1024(224.0F), y1024(48.0F), GUIRecordSelect.this.i18n("record.ManualTime"), 2);
      draw(x1024(528.0F), y1024(208.0F), x1024(224.0F), y1024(48.0F), GUIRecordSelect.this.i18n("record.ManualView"), 2);
      draw(x1024(528.0F), y1024(288.0F), x1024(224.0F), y1024(48.0F), GUIRecordSelect.this.i18n("record.InflightMessages"), 2);
      draw(x1024(528.0F), y1024(384.0F), x1024(224.0F), y1024(48.0F), GUIRecordSelect.this.i18n("record.Delete"), 2);
      draw(x1024(96.0F), y1024(496.0F), x1024(208.0F), y1024(48.0F), 0, GUIRecordSelect.this.i18n("record.MainMenu"));
      draw(x1024(448.0F), y1024(496.0F), x1024(240.0F), y1024(48.0F), 2, GUIRecordSelect.this.i18n("record.Play"));
    }

    public void setPosSize() {
      set1024PosSize(128.0F, 112.0F, 784.0F, 576.0F);
      GUIRecordSelect.this.wPrev.setPosC(x1024(56.0F), y1024(520.0F));
      GUIRecordSelect.this.wPlay.setPosC(x1024(728.0F), y1024(520.0F));
      GUIRecordSelect.this.wDelete.setPosC(x1024(488.0F), y1024(408.0F));
      GUIRecordSelect.this.sCycle.setPosC(x1024(496.0F), y1024(72.0F));
      GUIRecordSelect.this.sTimeCompression.setPosC(x1024(496.0F), y1024(152.0F));
      GUIRecordSelect.this.sViewControls.setPosC(x1024(496.0F), y1024(232.0F));
      GUIRecordSelect.this.sViewMessages.setPosC(x1024(496.0F), y1024(312.0F));
      GUIRecordSelect.this.wTable.setPosSize(x1024(32.0F), y1024(32.0F), x1024(384.0F), y1024(400.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList files = new ArrayList();

    public int countRows() { return this.files != null ? this.files.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.files.get(paramInt1));
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.files.get(paramInt1));
      }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("record.TrackFiles"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public Table(GWindow arg2) {
      super();
      this.bNotify = true;
      this.wClient.bNotify = true;
    }
  }
}