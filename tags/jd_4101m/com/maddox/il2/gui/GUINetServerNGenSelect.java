package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.USGS;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

public class GUINetServerNGenSelect extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public GUIButton bExit;
  public GUIButton bDel;
  public GUIButton bStart;
  static Item cur = null;

  public void _enter() {
    NetEnv.cur().connect.bindEnable(true);

    Main.cur().netServerParams.USGSupdate();
    fillCampList();
    this.wTable.resized();
    this.client.activateWindow();
  }
  public void _leave() {
    this.wTable.campList.clear();
    this.client.hideWindow();
  }

  private void fillCampList() {
    String str1 = RTSConf.cur.locale.getLanguage();
    String str2 = "campaign";
    String str3 = null;
    if (!"us".equals(str1))
      str3 = "_" + str1 + ".dat";
    String str4 = ".dat";

    File localFile = new File(HomePath.get(0), "ngen");
    File[] arrayOfFile = localFile.listFiles();
    String[] arrayOfString = localFile.list();
    if ((arrayOfString == null) || (arrayOfString.length == 0)) return;

    TreeMap localTreeMap = new TreeMap();
    String str5;
    Object localObject2;
    for (int i = 0; i < arrayOfString.length; i++) {
      if ((arrayOfFile[i].isDirectory()) || (arrayOfFile[i].isHidden()))
        continue;
      str5 = arrayOfString[i];
      if (str5 != null) {
        str5 = str5.toLowerCase();
        if ((str5.length() <= str2.length()) || (!str5.regionMatches(true, 0, str2, 0, str2.length())))
          continue;
        int k = -1;
        int m = 0;
        if ((str3 != null) && (str5.length() > str3.length()) && (str5.regionMatches(true, str5.length() - str3.length(), str3, 0, str3.length())))
        {
          k = str5.length() - str3.length();
          m = 1;
        }
        if ((k == -1) && (str5.length() > str4.length()) && (str5.regionMatches(true, str5.length() - str4.length(), str4, 0, str4.length())))
        {
          k = str5.length() - str4.length();
          if ((str5.length() > str4.length() + 3) && (str5.charAt(str5.length() - str4.length() - 3) == '_'))
            continue;
          m = 0;
        }
        if (k >= str2.length()) {
          localObject2 = str5.substring(str2.length(), k);
          if ((m == 0) && (localTreeMap.containsKey(localObject2)))
            continue;
          localTreeMap.put(localObject2, str5);
        }
      }
    }
    if (localTreeMap.size() == 0) {
      return;
    }
    Iterator localIterator = localTreeMap.keySet().iterator();
    Object localObject1;
    Item localItem2;
    while (localIterator.hasNext()) {
      str5 = (String)localIterator.next();
      localObject1 = (String)localTreeMap.get(str5);
      localItem2 = new Item();
      localItem2.bNew = true;
      localItem2.prefix = str5;
      localItem2.fileName = ("ngen/" + (String)localObject1);
      localObject2 = new SectFile(localItem2.fileName, 4, true, null, RTSConf.charEncoding, true);
      localItem2.name = ((SectFile)localObject2).get("$locale", "name", "");
      localItem2.note = ((SectFile)localObject2).get("$locale", "note", "");
      this.wTable.campList.add(localItem2);
    }

    localFile = new File(HomePath.get(0), "missions/net/ngen");
    arrayOfFile = localFile.listFiles();
    int j;
    if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
      for (j = 0; j < arrayOfFile.length; j++) {
        if ((!arrayOfFile[j].isDirectory()) || (arrayOfFile[j].isHidden())) continue;
        try {
          localObject1 = new File(arrayOfFile[j], "conf.dat");
          if (((File)localObject1).exists()) {
            localItem2 = new Item();
            localItem2.bNew = false;
            localItem2.fileName = ("missions/net/ngen/" + arrayOfFile[j].getName().toLowerCase() + "/conf.dat");
            localObject2 = new SectFile(localItem2.fileName, 4, true, null, RTSConf.charEncoding, true);
            localItem2.bEnd = ((SectFile)localObject2).get("$select", "complete", false);
            localItem2.name = ((SectFile)localObject2).get("$locale", "name", "");
            localItem2.note = ((SectFile)localObject2).get("$locale", "note", "");
            this.wTable.campList.add(localItem2);
            int n = ((SectFile)localObject2).sectionIndex("$missions");
            if (n >= 0)
              localItem2.missions = ((SectFile)localObject2).vars(n);
          }
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
        }
      }
    }

    if (this.wTable.campList.size() == 0)
      return;
    if (cur != null) {
      j = 0;
      for (; j < this.wTable.campList.size(); j++) {
        Item localItem1 = (Item)this.wTable.campList.get(j);
        if (cur.equals(localItem1))
          break;
      }
      if (j < this.wTable.campList.size())
        this.wTable.setSelect(j, 0);
      else
        this.wTable.setSelect(0, 0);
    }
    else {
      this.wTable.setSelect(0, 0);
    }
  }

  public GUINetServerNGenSelect(GWindowRoot paramGWindowRoot)
  {
    super(68);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("ngens.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bStart = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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
      if (paramInt1 != 2) {
        return super.notify(paramGWindow, paramInt1, paramInt2);
      }
      if (paramGWindow == GUINetServerNGenSelect.this.bExit) {
        GUINetServer.exitServer(true);
        return true;
      }
      GUINetServerNGenSelect.Item localItem;
      int i;
      Object localObject;
      if (paramGWindow == GUINetServerNGenSelect.this.bDel) {
        if (GUINetServerNGenSelect.this.wTable.selectRow < 0) return true;
        if (GUINetServerNGenSelect.this.wTable.selectRow >= GUINetServerNGenSelect.this.wTable.campList.size()) return true;
        GUINetServerNGenSelect.cur = null;
        localItem = (GUINetServerNGenSelect.Item)GUINetServerNGenSelect.this.wTable.campList.get(GUINetServerNGenSelect.this.wTable.selectRow);
        try {
          String str1 = localItem.fileName;
          i = str1.lastIndexOf("/conf.dat");
          if (i >= 0)
            str1 = str1.substring(0, i);
          localObject = new File(HomePath.get(0), str1);
          clearDir((File)localObject);
          GUINetServerNGenSelect.this.wTable.campList.remove(GUINetServerNGenSelect.this.wTable.selectRow);
          if (GUINetServerNGenSelect.this.wTable.selectRow >= GUINetServerNGenSelect.this.wTable.campList.size())
            GUINetServerNGenSelect.this.wTable.setSelect(GUINetServerNGenSelect.this.wTable.campList.size() - 1, 0);
        } catch (Exception localException1) {
          System.out.println(localException1.getMessage());
          localException1.printStackTrace();
          return true;
        }
        return true;
      }if (paramGWindow == GUINetServerNGenSelect.this.bStart) {
        if (GUINetServerNGenSelect.this.wTable.selectRow < 0) return true;
        if (GUINetServerNGenSelect.this.wTable.selectRow >= GUINetServerNGenSelect.this.wTable.campList.size()) return true;
        GUINetServerNGenSelect.cur = null;
        localItem = (GUINetServerNGenSelect.Item)GUINetServerNGenSelect.this.wTable.campList.get(GUINetServerNGenSelect.this.wTable.selectRow);
        if (localItem.bNew) {
          try {
            String str2 = localItem.prefix;
            for (i = 1; i > 0; i++) {
              localObject = new File(HomePath.get(0), "missions/net/ngen/" + str2 + i);
              if (!((File)localObject).exists()) {
                str2 = "missions/net/ngen/" + str2 + i;
                ((File)localObject).mkdirs();
                break;
              }
            }
            String str3 = str2 + "/conf.dat";
            localObject = new GUINetServerNGenSelect.Item(localItem);
            ((GUINetServerNGenSelect.Item)localObject).bNew = false;
            ((GUINetServerNGenSelect.Item)localObject).fileName = str3;
            SectFile localSectFile = new SectFile(localItem.fileName, 0, true, null, RTSConf.charEncoding, true);
            localSectFile.saveFile(str3);

            GUINetServerNGenSelect.cur = (GUINetServerNGenSelect.Item)localObject;
          }
          catch (Exception localException2) {
            System.out.println(localException2.getMessage());
            localException2.printStackTrace();
            return true;
          }
        }
        else {
          GUINetServerNGenSelect.cur = localItem;
        }
        if (GUINetServerNGenSelect.cur != null) {
          Main.stateStack().change(69);
        }
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    private void clearDir(File paramFile) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null) {
        for (int i = 0; i < arrayOfFile.length; i++) {
          File localFile = arrayOfFile[i];
          String str = localFile.getName();
          if ((".".equals(str)) || ("..".equals(str)))
            continue;
          if (localFile.isDirectory())
            clearDir(localFile);
          else
            localFile.delete();
        }
      }
      paramFile.delete();
    }

    public void preRender() {
      super.preRender();
      if (GUINetServerNGenSelect.this.wTable.isEnableDel()) {
        if (!GUINetServerNGenSelect.this.bDel.isVisible()) GUINetServerNGenSelect.this.bDel.showWindow();
      }
      else if (GUINetServerNGenSelect.this.bDel.isVisible()) GUINetServerNGenSelect.this.bDel.hideWindow();

      if (GUINetServerNGenSelect.this.wTable.isEnableLoad()) {
        if (!GUINetServerNGenSelect.this.bStart.isVisible()) GUINetServerNGenSelect.this.bStart.showWindow();
      }
      else if (GUINetServerNGenSelect.this.bStart.isVisible()) GUINetServerNGenSelect.this.bStart.hideWindow();
    }

    public void render()
    {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(658.0F), x1024(128.0F), y1024(48.0F), 0, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? GUINetServerNGenSelect.this.i18n("main.Quit") : GUINetServerNGenSelect.this.i18n("netsms.MainMenu"));

      if (GUINetServerNGenSelect.this.wTable.isEnableDel())
        draw(x1024(256.0F), y1024(658.0F), x1024(160.0F), y1024(48.0F), 2, GUINetServerNGenSelect.this.i18n("camps.Delete"));
      if (GUINetServerNGenSelect.this.wTable.isEnableLoad())
        draw(x1024(766.0F), y1024(658.0F), x1024(160.0F), y1024(48.0F), 2, GUINetServerNGenSelect.this.i18n("camps.Load"));
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUINetServerNGenSelect.this.bExit.setPosC(x1024(56.0F), y1024(682.0F));
      GUINetServerNGenSelect.this.bDel.setPosC(x1024(456.0F), y1024(682.0F));
      GUINetServerNGenSelect.this.bStart.setPosC(x1024(968.0F), y1024(682.0F));
      GUINetServerNGenSelect.this.wTable.set1024PosSize(32.0F, 32.0F, 960.0F, 480.0F);
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList campList = new ArrayList();

    public int countRows() { return this.campList != null ? this.campList.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      GUINetServerNGenSelect.Item localItem = (GUINetServerNGenSelect.Item)this.campList.get(paramInt1);
      float f = 0.0F;
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str = localItem.name; break;
      case 1:
        if (localItem.bNew) str = I18N.gui("ngens.new");
        else if (localItem.bEnd) str = I18N.gui("ngens.complete"); else
          str = I18N.gui("ngens.progress");
        i = 1; break;
      case 2:
        str = "" + localItem.missions;
        i = 1; break;
      case 3:
        str = localItem.note;
      }
      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(f, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(f, 0.0F, paramFloat1, paramFloat2, i, str);
      }
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelectRow = true;
      addColumn(I18N.gui("ngens.name"), null);
      addColumn(I18N.gui("ngens.state"), null);
      addColumn(I18N.gui("ngens.missions"), null);
      addColumn(I18N.gui("ngens.note"), null);
      this.vSB.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(10.0F);
      getColumn(1).setRelativeDx(5.0F);
      getColumn(2).setRelativeDx(5.0F);
      getColumn(3).setRelativeDx(20.0F);
      alignColumns();
      this.bNotify = true;
      this.wClient.bNotify = true;

      resized();
    }
    public boolean isEnableDel() {
      if (this.campList == null) return false;
      if (this.selectRow < 0) return false;
      if (this.selectRow >= this.campList.size()) return false;
      GUINetServerNGenSelect.Item localItem = (GUINetServerNGenSelect.Item)this.campList.get(this.selectRow);
      return !localItem.bNew;
    }
    public boolean isEnableLoad() {
      if (this.campList == null) return false;
      if (this.selectRow < 0) return false;
      if (this.selectRow >= this.campList.size()) return false;
      GUINetServerNGenSelect.Item localItem = (GUINetServerNGenSelect.Item)this.campList.get(this.selectRow);
      return !localItem.bEnd;
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super();
    }
  }

  static class Item
  {
    public boolean bNew;
    public boolean bEnd;
    public String prefix;
    public String fileName;
    public String name = "";
    public int missions = 0;
    public String note = "";

    public boolean equals(Object paramObject) {
      if (paramObject == null) return false;
      if (!(paramObject instanceof Item)) return false;
      Item localItem = (Item)paramObject;
      return this.fileName.equalsIgnoreCase(localItem.fileName);
    }
    public Item() {
    }
    public Item(Item paramItem) {
      this.bNew = paramItem.bNew;
      this.bEnd = paramItem.bEnd;
      this.fileName = paramItem.fileName;
      this.name = paramItem.name;
      this.missions = paramItem.missions;
      this.note = paramItem.note;
    }
  }
}