package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUIDGenRoster extends GameState
{
  public ArrayList pilots = new ArrayList();
  public ArrayList gones = new ArrayList();
  public Pilot pilotPlayer = null;
  public Pilot pilotCur = null;
  private Regiment regiment;
  private GTexture texRegiment;
  private int sorties;
  private int kills;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public Gone wGone;
  public GUIButton bBack;
  public GUIButton bDocs;
  public GUIButton bProfile;

  public void enterPush(GameState paramGameState)
  {
    loadPilotList();
    loadGoneList();
    loadRegiment();
    this.pilotCur = null;
    this.wTable.setSelect(-1, -1);
    this.wGone.setSelect(-1, -1);
    this.bProfile.hideWindow();
    if (GUIDGenDocs.isExist())
      this.bDocs.showWindow();
    else
      this.bDocs.hideWindow();
    this.client.activateWindow();
    this.wTable.resized();
    this.wGone.resized();
  }
  public void enterPop(GameState paramGameState) {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public void loadPilotList() {
    TreeMap localTreeMap = new TreeMap(new PilotComparator());
    ResourceBundle localResourceBundle = null;
    this.pilotPlayer = null;
    try {
      localResourceBundle = ResourceBundle.getBundle("missions/campaign/" + Main.cur().campaign.branch() + "/" + "rank", RTSConf.cur.locale); } catch (Exception localException1) {
    }
    try {
      Campaign localCampaign = Main.cur().campaign;
      this.pilots.clear();
      this.sorties = 0;
      this.kills = 0;
      Object localObject = null;
      String str = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/squadron.dat";
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str, RTSConf.charEncoding));
      while ((localObject = loadPilot(localBufferedReader)) != null) {
        if (this.pilotPlayer == null) {
          ((Pilot)localObject).bPlayer = true;
          this.pilotPlayer = ((Pilot)localObject);
        } else {
          localTreeMap.put(localObject, localObject);
        }
        if (localResourceBundle != null)
          ((Pilot)localObject).sRank = localResourceBundle.getString("" + ((Pilot)localObject).rank);
        else
          ((Pilot)localObject).sRank = "";
        this.sorties += ((Pilot)localObject).sorties;
        this.kills += ((Pilot)localObject).kills;
      }
      localBufferedReader.close();
    } catch (Exception localException2) {
      System.out.println("Squadron file load failed: " + localException2.getMessage());
      localException2.printStackTrace();
      Main.stateStack().pop();
    }
    if (this.pilotPlayer == null) {
      return;
    }
    localTreeMap.put(this.pilotPlayer, this.pilotPlayer);
    Iterator localIterator = localTreeMap.keySet().iterator();
    while (localIterator.hasNext())
      this.pilots.add(localIterator.next()); 
  }

  private void loadGoneList() {
    ResourceBundle localResourceBundle = null;
    try {
      localResourceBundle = ResourceBundle.getBundle("missions/campaign/" + Main.cur().campaign.branch() + "/" + "rank", RTSConf.cur.locale); } catch (Exception localException1) {
    }
    try {
      Campaign localCampaign = Main.cur().campaign;
      this.gones.clear();
      String str = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/archive.dat";
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str, RTSConf.charEncoding));
      Object localObject = null;
      while ((localObject = loadPilot(localBufferedReader)) != null) {
        this.gones.add(localObject);
        if (localResourceBundle != null)
          ((Pilot)localObject).sRank = localResourceBundle.getString("" + ((Pilot)localObject).rank);
        else
          ((Pilot)localObject).sRank = "";
        this.sorties += ((Pilot)localObject).sorties;
        this.kills += ((Pilot)localObject).kills;
      }
      localBufferedReader.close(); } catch (Exception localException2) {
    }
  }

  public Pilot loadPilot(BufferedReader paramBufferedReader) throws IOException {
    Pilot localPilot = new Pilot();

    while (paramBufferedReader.ready())
    {
      String str = paramBufferedReader.readLine();
      if (str == null)
        break;
      int i = str.length();
      if (i == 0)
        continue;
      int j = 0;

      if (str.startsWith("--------")) { j = 1;
      } else if (str.startsWith("FIRST NAME:")) { localPilot.firstName = readArgStr(UnicodeTo8bit.load(str, false));
      } else if (str.startsWith("LAST NAME:")) { localPilot.lastName = readArgStr(UnicodeTo8bit.load(str, false));
      } else if (str.startsWith("DATE OF BIRTH:")) { localPilot.dateBirth = readArgStr(UnicodeTo8bit.load(str, false));
      } else if (str.startsWith("PLACE OF BIRTH:")) { localPilot.placeBirth = readArgStr(UnicodeTo8bit.load(str, false));
      } else if (str.startsWith("PHOTO:")) { localPilot.photo = readArgStr(str);
      } else if (str.startsWith("RANK:")) { localPilot.rank = readArgInt(str, 0);
      } else if (str.startsWith("SORTIES:")) { localPilot.sorties = readArgInt(str, 0);
      } else if (str.startsWith("KILLS:")) { localPilot.kills = readArgInt(str, 0);
      } else if (str.startsWith("GROUND:")) { localPilot.ground = readArgInt(str, 0);
      } else if (str.startsWith("NMEDALS:")) { localPilot.nmedals = readArgInt(str, 0);
      } else if (str.startsWith("MEDALS:")) {
        SharedTokenizer.set(str);
        int k = Main.cur().campaign._awards.count(1000000) - 1;
        int m = SharedTokenizer.countTokens() - 1;
        if (m > 0) {
          localPilot.medals = new int[m];
          SharedTokenizer.next();
          for (int n = 0; n < m; n++) {
            localPilot.medals[n] = SharedTokenizer.next(0, 0, k);
          }
        }

      }
      else if (str.startsWith("EVENT:")) { localPilot.events.add(readArgStr(UnicodeTo8bit.load(str, false)));
      }

      if (j != 0)
        break;
    }
    if ((localPilot.firstName == null) || (localPilot.lastName == null))
    {
      return null;
    }return localPilot;
  }
  public int readArgInt(String paramString, int paramInt) {
    String str = readArgStr(paramString);
    if ("".equals(str)) return paramInt; try
    {
      return Integer.parseInt(str); } catch (Exception localException) {
    }
    return paramInt;
  }

  public String readArgStr(String paramString) {
    int i = paramString.indexOf(":") + 1;
    int j = paramString.length();
    while (i < j) {
      if (paramString.charAt(i) > ' ') break; i++;
    }

    if (i >= j)
      return "";
    do {
      if (paramString.charAt(j - 1) > ' ') break; j--;
    }
    while (i < j);

    if (i == j)
      return "";
    String str = paramString.substring(i, j);
    return str;
  }

  private void loadRegiment() {
    try {
      Campaign localCampaign = Main.cur().campaign;
      String str1 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/conf.dat";
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str1, RTSConf.charEncoding));
      localBufferedReader.readLine();
      String str2 = localBufferedReader.readLine();
      str2 = str2.trim();
      localBufferedReader.close();
      this.regiment = ((Regiment)Actor.getByName(str2));
      Mat localMat = PaintScheme.makeMat(this.regiment.name(), this.regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
      this.texRegiment = GTexture.New(localMat.Name());
    } catch (Exception localException) {
      System.out.println("Conf file load failed: " + localException.getMessage());
      localException.printStackTrace();
    }
  }

  public GUIDGenRoster(GWindowRoot paramGWindowRoot)
  {
    super(65);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("dgenroster.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    this.wGone = new Gone(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDocs = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bProfile = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUIDGenRoster.this.bBack) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUIDGenRoster.this.bDocs) {
        Main.stateStack().push(70);
        return true;
      }
      if (paramGWindow == GUIDGenRoster.this.bProfile) {
        int i = GUIDGenRoster.this.wTable.selectRow;
        if (i >= 0) {
          GUIDGenRoster.this.pilotCur = ((GUIDGenRoster.Pilot)GUIDGenRoster.this.pilots.get(i));
        } else {
          i = GUIDGenRoster.this.wGone.selectRow;
          if (i < 0)
            return true;
          GUIDGenRoster.this.pilotCur = ((GUIDGenRoster.Pilot)GUIDGenRoster.this.gones.get(i));
        }
        Main.stateStack().push(66);
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColorWHITE();
      draw(x1024(80.0F), y1024(32.0F), x1024(64.0F), y1024(64.0F), GUIDGenRoster.this.texRegiment);
      setCanvasFont(1);
      draw(x1024(160.0F), y1024(48.0F), x1024(786.0F), y1024(32.0F), 0, I18N.regimentInfo(GUIDGenRoster.this.regiment.info()));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(112.0F), y1024(354.0F), x1024(512.0F), y1024(32.0F), 0, GUIDGenRoster.this.i18n("dgenroster.Gone"));

      draw(x1024(176.0F), y1024(560.0F), x1024(384.0F), y1024(32.0F), 2, GUIDGenRoster.this.i18n("dgenroster.Flight"));
      draw(x1024(176.0F), y1024(592.0F), x1024(384.0F), y1024(32.0F), 2, GUIDGenRoster.this.i18n("dgenroster.Kills"));

      draw(x1024(576.0F), y1024(560.0F), x1024(208.0F), y1024(32.0F), 0, "" + GUIDGenRoster.this.sorties);
      draw(x1024(576.0F), y1024(592.0F), x1024(208.0F), y1024(32.0F), 0, "" + GUIDGenRoster.this.kills);

      draw(x1024(96.0F), y1024(656.0F), x1024(320.0F), y1024(48.0F), 0, GUIDGenRoster.this.i18n("camps.Back"));
      if (GUIDGenRoster.this.bDocs.isVisible())
        draw(x1024(560.0F), y1024(656.0F), x1024(320.0F), y1024(48.0F), 0, GUIDGenRoster.this.i18n("camps.Docs"));
      if (GUIDGenRoster.this.bProfile.isVisible()) {
        draw(x1024(432.0F), y1024(656.0F), x1024(498.0F), y1024(48.0F), 2, GUIDGenRoster.this.i18n("dgenroster.PilotProfile"));
      }
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(640.0F), x1024(962.0F), 2.0F);
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIDGenRoster.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));
      GUIDGenRoster.this.bDocs.setPosC(x1024(512.0F), y1024(680.0F));
      GUIDGenRoster.this.bProfile.setPosC(x1024(968.0F), y1024(680.0F));
      GUIDGenRoster.this.wTable.set1024PosSize(96.0F, 112.0F, 832.0F, 240.0F);
      GUIDGenRoster.this.wGone.set1024PosSize(96.0F, 384.0F, 832.0F, 160.0F);
    }
  }

  public class Gone extends GWindowTable
  {
    public ArrayList playerList = GUIDGenRoster.this.gones;

    public int countRows() { return this.playerList != null ? GUIDGenRoster.this.gones.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      GUIDGenRoster.Pilot localPilot = (GUIDGenRoster.Pilot)GUIDGenRoster.this.gones.get(paramInt1);
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str = localPilot.sRank;
        i = 0;
        break;
      case 1:
        str = localPilot.lastName;
        i = 0;
        break;
      case 2:
        str = "";
        if (localPilot.events.size() > 0)
          str = (String)localPilot.events.get(localPilot.events.size() - 1);
        i = 0;
      }

      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      }
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      if (paramInt1 >= 0) {
        GUIDGenRoster.this.wTable.setSelect(-1, -1);
        GUIDGenRoster.this.bProfile.showWindow();
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
      addColumn(I18N.gui("dgenroster.Rank"), null);
      addColumn(I18N.gui("dgenroster.Name"), null);
      addColumn(I18N.gui("dgenroster.To"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(17.0F);
      getColumn(1).setRelativeDx(17.0F);
      getColumn(2).setRelativeDx(46.0F);
      alignColumns();
      this.bNotify = true;
      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Gone(GWindow arg2) {
      super();
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList playerList = GUIDGenRoster.this.pilots;

    GColor playerColor = new GColor(35, 117, 137);

    public int countRows() { return this.playerList != null ? GUIDGenRoster.this.pilots.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      setCanvasFont(0);
      GUIDGenRoster.Pilot localPilot = (GUIDGenRoster.Pilot)GUIDGenRoster.this.pilots.get(paramInt1);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      } else if (localPilot == GUIDGenRoster.this.pilotPlayer) {
        setCanvasColor(this.playerColor);
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str = localPilot.sRank;
        i = 0;
        break;
      case 1:
        str = localPilot.lastName;
        i = 0;
        break;
      case 2:
        str = "" + localPilot.sorties;
        i = 1;
        break;
      case 3:
        str = "" + localPilot.kills;
        i = 1;
      }

      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      }
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      if (paramInt1 >= 0) {
        GUIDGenRoster.this.wGone.setSelect(-1, -1);
        GUIDGenRoster.this.bProfile.showWindow();
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
      addColumn(I18N.gui("dgenroster.Rank"), null);
      addColumn(I18N.gui("dgenroster.Name"), null);
      addColumn(I18N.gui("dgenroster.Sorties"), null);
      addColumn(I18N.gui("dgenroster.Kills"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(17.0F);
      getColumn(1).setRelativeDx(17.0F);
      getColumn(2).setRelativeDx(23.0F);
      getColumn(3).setRelativeDx(23.0F);
      alignColumns();
      this.bNotify = true;
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

  static class PilotComparator
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      GUIDGenRoster.Pilot localPilot1 = (GUIDGenRoster.Pilot)paramObject1;
      GUIDGenRoster.Pilot localPilot2 = (GUIDGenRoster.Pilot)paramObject2;
      int i = localPilot2.rank - localPilot1.rank;
      if (i == 0)
        i = localPilot1.lastName.compareToIgnoreCase(localPilot2.lastName);
      return i;
    }
  }

  public static class Pilot
  {
    public boolean bPlayer;
    public String firstName;
    public String lastName;
    public String dateBirth;
    public String placeBirth;
    public String photo;
    public int rank;
    public String sRank;
    public int sorties;
    public int kills;
    public int ground;
    public int nmedals;
    public int[] medals;
    public ArrayList events = new ArrayList();
  }
}