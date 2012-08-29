package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.HomePath;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class GUIDGenDocs extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public ScrollDescript wScrollDescription;
  public Descript wDescript;
  public GUIButton bBack;
  private String textDescription = null;

  public static boolean isExist() {
    Campaign localCampaign = Main.cur().campaign;
    if (localCampaign == null)
      return false;
    String str = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/documents.dat";
    File localFile = new File(HomePath.toFileSystemName(str, 0));
    return localFile.exists();
  }

  public void enterPush(GameState paramGameState) {
    this.client.activateWindow();
    this.wTable.fill();
    this.wScrollDescription.resized();
    if (this.wScrollDescription.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible())
      this.wScrollDescription.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(0.0F, true); 
  }

  public void _leave() {
    this.client.hideWindow();
  }

  protected String textDescription()
  {
    return this.textDescription;
  }

  protected Descript createDescript(GWindow paramGWindow) {
    return (Descript)paramGWindow.create(new Descript());
  }

  public GUIDGenDocs(GWindowRoot paramGWindowRoot)
  {
    super(70);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("dgendocs.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    this.dialogClient.create(this.wScrollDescription = new ScrollDescript());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUIDGenDocs.this.bBack) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(656.0F), x1024(320.0F), y1024(48.0F), 0, GUIDGenDocs.this.i18n("camps.Back"));

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(640.0F), x1024(962.0F), 2.0F);
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIDGenDocs.this.wTable.setPosSize(x1024(48.0F), y1024(48.0F), x1024(928.0F), y1024(132.0F));
      GUIDGenDocs.this.wScrollDescription.setPosSize(x1024(48.0F), y1024(212.0F), x1024(928.0F), y1024(396.0F));
      GUIDGenDocs.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));
    }
  }

  public class ScrollDescript extends GWindowScrollingDialogClient
  {
    public ScrollDescript()
    {
    }

    public void created()
    {
      this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient = (GUIDGenDocs.this.wDescript = GUIDGenDocs.this.createDescript(this));
      this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient.bNotify = true;
      this.bNotify = true;
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void resized() {
      if (GUIDGenDocs.this.wDescript != null) {
        GUIDGenDocs.this.wDescript.computeSize();
      }
      super.resized();
      if (this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible()) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setSize(lookAndFeel().getVScrollBarW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
  }

  public class Descript extends GWindowDialogClient
  {
    public Descript()
    {
    }

    public void render()
    {
      String str = GUIDGenDocs.this.textDescription();
      if (str != null) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        setCanvasColorBLACK();
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.clip.y += localGBevel.T.dy;
        this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.clip.dy -= localGBevel.T.dy + localGBevel.B.dy;
        drawLines(localGBevel.L.dx + 2.0F, localGBevel.T.dy + 2.0F, str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height);
      }
    }

    public void computeSize() {
      String str = GUIDGenDocs.this.textDescription();
      if (str != null) {
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        int i = computeLines(str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy > this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) {
          this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = (this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW());
          i = computeLines(str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
          this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        }
      } else {
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
      }
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList files = new ArrayList();
    public ArrayList names = new ArrayList();

    public int countRows() { return this.files != null ? this.files.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      String str = (String)this.names.get(paramInt1);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
    }

    public void fill() {
      this.files.clear();
      this.names.clear();
      Campaign localCampaign = Main.cur().campaign;
      String str1 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/";
      String str2 = str1 + "documents.dat";
      BufferedReader localBufferedReader = null;
      try {
        localBufferedReader = new BufferedReader(new SFSReader(str2, RTSConf.charEncoding));
        while (true) {
          String str3 = localBufferedReader.readLine();
          if (str3 == null)
            break;
          int i = str3.length();
          if (i == 0)
            continue;
          str3 = UnicodeTo8bit.load(str3, false);
          SharedTokenizer.set(str3);
          String str4 = SharedTokenizer.next();
          String str5 = SharedTokenizer.getGap();
          if ((str4 != null) && (str5 != null)) {
            this.files.add(str1 + str4);
            this.names.add(str5);
          }
        }
        localBufferedReader.close();
      } catch (Exception localException1) {
        if (localBufferedReader != null) try {
            localBufferedReader.close(); } catch (Exception localException2) {
          } System.out.println("List docs load failed: " + localException1.getMessage());
        localException1.printStackTrace();
      }
      if (this.files.size() > 0)
        setSelect(0, 0);
      else
        setSelect(-1, 0);
      resized();
    }
    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      GUIDGenDocs.access$002(GUIDGenDocs.this, null);
      if (this.jdField_selectRow_of_type_Int >= 0) {
        String str1 = (String)this.files.get(this.jdField_selectRow_of_type_Int);
        BufferedReader localBufferedReader = null;
        try {
          localBufferedReader = new BufferedReader(new SFSReader(str1, RTSConf.charEncoding));
          StringBuffer localStringBuffer = null;
          while (true)
          {
            String str2 = localBufferedReader.readLine();
            if (str2 == null)
              break;
            int i = str2.length();
            if (i == 0)
              continue;
            str2 = UnicodeTo8bit.load(str2, false);
            if (localStringBuffer == null) {
              localStringBuffer = new StringBuffer(str2); continue;
            }

            localStringBuffer.append('\n');
            localStringBuffer.append(str2);
          }

          localBufferedReader.close();
          if (localStringBuffer != null)
            GUIDGenDocs.access$002(GUIDGenDocs.this, localStringBuffer.toString());
        } catch (Exception localException1) {
          if (localBufferedReader != null) try {
              localBufferedReader.close(); } catch (Exception localException2) {
            } System.out.println("Text load failed: " + localException1.getMessage());
          localException1.printStackTrace();
        }
      }
      if (GUIDGenDocs.this.wScrollDescription != null) {
        GUIDGenDocs.this.wScrollDescription.resized();
        if (GUIDGenDocs.this.wScrollDescription.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible())
          GUIDGenDocs.this.wScrollDescription.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(0.0F, true);
      }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("dgendocs.docs"), null);
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
      super(2.0F, 4.0F, 20.0F, 16.0F);
      this.bNotify = true;
      this.wClient.bNotify = true;
    }
  }
}