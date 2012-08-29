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
import com.maddox.rts.KeyRecord;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUITrainingSelect extends GameState
{
  public String selectedTrack;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wPlay;
  public Table wTable;

  public void _enter()
  {
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed()))
      Mission.cur().destroy();
    if (Main3D.cur3D().keyRecord != null)
      Main3D.cur3D().keyRecord.clearRecorded();
    fillTracks();
    this.client.activateWindow();
  }
  public void leavePop(GameState paramGameState) {
    World.cur().setUserCovers();
    super.leavePop(paramGameState);
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public void fillTracks() {
    this.wTable.tracks.clear();
    SectFile localSectFile = new SectFile("Training/all.ini", 0);
    int i = localSectFile.sectionIndex("all");
    if (i >= 0) {
      ResourceBundle localResourceBundle = null;
      try {
        localResourceBundle = ResourceBundle.getBundle("Training/all", RTSConf.cur.locale);
      } catch (Exception localException1) {
      }
      int j = localSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        String str = localSectFile.line(i, k);
        this.wTable.tracks.add(str);
        try {
          this.wTable.names.add(localResourceBundle.getString(str));
        } catch (Exception localException2) {
          this.wTable.names.add(str);
        }
      }

      if (j > 0)
        this.wTable.setSelect(0, 0);
    }
    this.wTable.resized();
  }

  public GUITrainingSelect(GWindowRoot paramGWindowRoot)
  {
    super(56);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("training.infoSelect");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wPlay = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUITrainingSelect.this.wPrev) {
        Main3D.cur3D().viewSet_Load();
        Main.stateStack().pop();
      }
      else if (paramGWindow == GUITrainingSelect.this.wPlay) {
        int i = GUITrainingSelect.this.wTable.selectRow;
        if ((i < 0) || (i >= GUITrainingSelect.this.wTable.tracks.size())) return true;

        GUITrainingSelect.this.selectedTrack = ((String)GUITrainingSelect.this.wTable.tracks.get(i));

        Main.stateStack().push(57);
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(300.0F), x1024(832.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(332.0F), x1024(240.0F), y1024(48.0F), 0, GUITrainingSelect.this.i18n("training.MainMenu"));
      draw(x1024(512.0F), y1024(332.0F), x1024(288.0F), y1024(48.0F), 2, GUITrainingSelect.this.i18n("training.Play"));
    }

    public void setPosSize() {
      set1024PosSize(64.0F, 194.0F, 896.0F, 412.0F);
      GUITrainingSelect.this.wPrev.setPosC(x1024(56.0F), y1024(356.0F));
      GUITrainingSelect.this.wPlay.setPosC(x1024(840.0F), y1024(356.0F));
      GUITrainingSelect.this.wTable.setPosSize(x1024(32.0F), y1024(32.0F), x1024(832.0F), y1024(236.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList tracks = new ArrayList();
    public ArrayList names = new ArrayList();

    public int countRows() { return this.tracks != null ? this.tracks.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.names.get(paramInt1));
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, (String)this.names.get(paramInt1));
      }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("training.Tracks"), null);
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