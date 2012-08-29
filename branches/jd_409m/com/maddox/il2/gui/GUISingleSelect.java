package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUISingleSelect extends GameState
{
  public static final String HOME_DIR = "missions/single";
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wNext;
  public GWindowComboControl wCountry;
  public GTexture countryIcon;
  public GWindowComboControl wDirs;
  public Table wTable;
  public WDescript wDescript;
  public String country;
  public boolean bInited = false;
  public ResourceBundle resCountry;
  public ArrayList countryLst = new ArrayList();

  public TreeMap _scanMap = new TreeMap();

  public void _enter()
  {
    init();
    int i = this.wCountry.getSelected();
    if (i >= 0) Main3D.menuMusicPlay((String)this.countryLst.get(i));
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private void init() {
    if (this.bInited) return;
    this.resCountry = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    this._scanMap.put(this.resCountry.getString("ru"), "ru");
    this._scanMap.put(this.resCountry.getString("de"), "de");

    File localFile = new File(HomePath.get(0), "missions/single");
    if (localFile != null) {
      localObject = localFile.listFiles();
      if (localObject != null) {
        for (int i = 0; i < localObject.length; i++) {
          if ((localObject[i].isDirectory()) && (!localObject[i].isHidden())) {
            String str2 = localObject[i].getName().toLowerCase();
            String str3 = null;
            try {
              str3 = this.resCountry.getString(str2);
            } catch (Exception localException) {
              continue;
            }
            if (this._scanMap.containsKey(str3))
              continue;
            this._scanMap.put(str3, str2);
          }
        }
      }
    }

    Object localObject = this._scanMap.keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      String str1 = (String)((Iterator)localObject).next();
      this.countryLst.add(this._scanMap.get(str1));
      this.wCountry.add(str1);
    }
    this._scanMap.clear();

    this.wCountry.setSelected(-1, false, true);
    if (this.countryLst.size() > 0)
      this.wCountry.setSelected(0, true, true);
    this.bInited = true;
  }

  public void fillDirs() {
    this.countryIcon = null;
    this.country = null;
    int i = this.wCountry.getSelected();
    if (i < 0) {
      this.wDirs.clear(false);
      this.wTable.files.clear();
      this.wTable.setSelect(-1, 0);
      return;
    }
    this.country = ((String)this.countryLst.get(i));
    File localFile = new File(HomePath.get(0), "missions/single/" + this.country);
    File[] arrayOfFile = localFile.listFiles();
    this.wDirs.clear(false);
    if ((arrayOfFile == null) || (arrayOfFile.length == 0)) {
      this.wTable.files.clear();
      this.wTable.setSelect(-1, 0);
      return;
    }

    for (int j = 0; j < arrayOfFile.length; j++) {
      if ((!arrayOfFile[j].isDirectory()) || (arrayOfFile[j].isHidden()) || (".".equals(arrayOfFile[j].getName())) || ("..".equals(arrayOfFile[j].getName()))) {
        continue;
      }
      this._scanMap.put(arrayOfFile[j].getName(), null);
    }
    Iterator localIterator = this._scanMap.keySet().iterator();
    while (localIterator.hasNext())
      this.wDirs.add((String)localIterator.next());
    if (this._scanMap.size() > 0)
      this.wDirs.setSelected(0, true, false);
    this._scanMap.clear();
    fillFiles();
  }

  public void fillFiles()
  {
    this.wTable.files.clear();
    String str1 = this.wDirs.getValue();
    int i = this.wCountry.getSelected();
    if (str1 != null) {
      String str2 = "missions/single/" + this.country + "/" + str1;
      File localFile = new File(HomePath.get(0), str2);
      File[] arrayOfFile = localFile.listFiles();
      if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
        for (int j = 0; j < arrayOfFile.length; j++) {
          if ((arrayOfFile[j].isDirectory()) || (arrayOfFile[j].isHidden()) || (arrayOfFile[j].getName().toLowerCase().lastIndexOf(".properties") >= 0)) {
            continue;
          }
          localObject = new FileMission(str2, arrayOfFile[j].getName());
          this._scanMap.put(((FileMission)localObject).fileName, localObject);
        }

        Object localObject = this._scanMap.keySet().iterator();
        while (((Iterator)localObject).hasNext())
          this.wTable.files.add(this._scanMap.get(((Iterator)localObject).next()));
        if (this._scanMap.size() > 0)
          this.wTable.setSelect(0, 0);
        else
          this.wTable.setSelect(-1, 0);
        this._scanMap.clear();
      } else {
        this.wTable.setSelect(-1, 0);
      }
    } else {
      this.wTable.setSelect(-1, 0);
    }
    this.wTable.resized();
  }

  public GUISingleSelect(GWindowRoot paramGWindowRoot)
  {
    super(3);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("singleSelect.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wCountry = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wCountry.setEditable(false);

    this.wDirs = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wDirs.setEditable(false);
    this.wTable = new Table(this.dialogClient);

    this.dialogClient.create(this.wDescript = new WDescript());
    this.wDescript.bNotify = true;

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wNext = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUISingleSelect.this.wPrev) {
        Main.stateStack().pop();
        return true;
      }
      int i;
      if (paramGWindow == GUISingleSelect.this.wCountry) {
        GUISingleSelect.this.fillDirs();
        i = GUISingleSelect.this.wCountry.getSelected();
        if (i >= 0) {
          Main3D.menuMusicPlay((String)GUISingleSelect.this.countryLst.get(i));
          ((GUIRoot)this.root).setBackCountry("single", (String)GUISingleSelect.this.countryLst.get(i));
        }
        return true;
      }if (paramGWindow == GUISingleSelect.this.wDirs) {
        GUISingleSelect.this.fillFiles();
        return true;
      }if (paramGWindow == GUISingleSelect.this.wNext) {
        if (GUISingleSelect.this.wDirs.getValue() == null) return true;
        i = GUISingleSelect.this.wTable.selectRow;
        if ((i < 0) || (i >= GUISingleSelect.this.wTable.files.size())) return true;
        GUISingleSelect.FileMission localFileMission = (GUISingleSelect.FileMission)GUISingleSelect.this.wTable.files.get(i);

        int j = GUISingleSelect.this.wCountry.getSelected();
        Main.cur().currentMissionFile = new SectFile("missions/single/" + GUISingleSelect.this.country + "/" + GUISingleSelect.this.wDirs.getValue() + "/" + localFileMission.fileName, 0);

        Main.stateStack().push(4);
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(432.0F), y1024(546.0F), x1024(384.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(416.0F), y1024(32.0F), 2.0F, y1024(608.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(64.0F), y1024(40.0F), x1024(240.0F), y1024(32.0F), 0, GUISingleSelect.this.i18n("singleSelect.Country"));
      draw(x1024(64.0F), y1024(156.0F), x1024(240.0F), y1024(32.0F), 0, GUISingleSelect.this.i18n("singleSelect.MissType"));
      draw(x1024(64.0F), y1024(264.0F), x1024(240.0F), y1024(32.0F), 0, GUISingleSelect.this.i18n("singleSelect.Miss"));
      draw(x1024(464.0F), y1024(264.0F), x1024(248.0F), y1024(32.0F), 0, GUISingleSelect.this.i18n("singleSelect.Desc"));

      draw(x1024(104.0F), y1024(592.0F), x1024(192.0F), y1024(48.0F), 0, GUISingleSelect.this.i18n("singleSelect.MainMenu"));
      draw(x1024(528.0F), y1024(592.0F), x1024(216.0F), y1024(48.0F), 2, GUISingleSelect.this.i18n("singleSelect.Brief"));
    }

    public void setPosSize() {
      set1024PosSize(80.0F, 64.0F, 848.0F, 672.0F);
      GUISingleSelect.this.wPrev.setPosC(x1024(56.0F), y1024(616.0F));
      GUISingleSelect.this.wNext.setPosC(x1024(792.0F), y1024(616.0F));
      GUISingleSelect.this.wCountry.setPosSize(x1024(48.0F), y1024(80.0F), x1024(336.0F), M(2.0F));
      GUISingleSelect.this.wDirs.setPosSize(x1024(48.0F), y1024(192.0F), x1024(336.0F), M(2.0F));
      GUISingleSelect.this.wTable.setPosSize(x1024(48.0F), y1024(304.0F), x1024(336.0F), y1024(256.0F));
      GUISingleSelect.this.wDescript.setPosSize(x1024(448.0F), y1024(312.0F), x1024(354.0F), y1024(212.0F));
    }
  }

  public class WDescript extends GWindow
  {
    public WDescript()
    {
    }

    public void render()
    {
      String str = null;
      if (GUISingleSelect.this.wTable.jdField_selectRow_of_type_Int >= 0) {
        str = ((GUISingleSelect.FileMission)GUISingleSelect.this.wTable.files.get(GUISingleSelect.this.wTable.jdField_selectRow_of_type_Int)).description;
        if ((str != null) && (str.length() == 0)) str = null;
      }
      if (str != null) {
        setCanvasFont(0);
        setCanvasColorBLACK();
        drawLines(0.0F, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.descender, str, 0, str.length(), this.win.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height);
      }
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList files = new ArrayList();

    public int countRows() { return this.files != null ? this.files.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      String str = ((GUISingleSelect.FileMission)this.files.get(paramInt1)).name;
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

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("singleSelect.MissFiles"), null);
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

  static class FileMission
  {
    public String fileName;
    public String name;
    public String description;

    public FileMission(String paramString1, String paramString2)
    {
      this.fileName = paramString2;
      try {
        String str = paramString2;
        int i = str.lastIndexOf(".");
        if (i >= 0)
          str = str.substring(0, i);
        ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString1 + "/" + str, RTSConf.cur.locale);
        this.name = localResourceBundle.getString("Name");
        this.description = localResourceBundle.getString("Short");
      } catch (Exception localException) {
        this.name = paramString2;
        this.description = null;
      }
    }
  }
}