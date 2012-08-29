package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class GUIDGenPilotDetail extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public GUIButton bBack;
  private ArrayList events = new ArrayList();
  private GUIDGenRoster roster;

  public void _enter()
  {
    loadEvents();
    this.client.activateWindow();
    this.wTable.resized();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private void loadEvents() {
    try {
      this.roster = ((GUIDGenRoster)GameState.get(65));
      Campaign localCampaign = Main.cur().campaign;
      String str = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/logbook.dat";
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str, RTSConf.charEncoding));
      this.events.clear();
      Object localObject = null;
      while ((localObject = loadEvent(localBufferedReader)) != null)
        this.events.add(localObject);
      localBufferedReader.close();
    } catch (Exception localException) {
      System.out.println("Squadron file load failed: " + localException.getMessage());
      localException.printStackTrace();
      Main.stateStack().pop();
    }
  }

  private Event loadEvent(BufferedReader paramBufferedReader) throws IOException {
    Event localEvent = new Event(null);

    while (paramBufferedReader.ready())
    {
      String str1 = paramBufferedReader.readLine();
      if (str1 == null)
        break;
      int i = str1.length();
      if (i == 0)
        continue;
      int j = 0;

      if (str1.startsWith("DATE:")) { localEvent.date = this.roster.readArgStr(UnicodeTo8bit.load(str1, false));
      } else if (str1.startsWith("PLANE:")) { localEvent.plane = this.roster.readArgStr(str1);
        try {
          Class localClass = ObjIO.classForName("air." + localEvent.plane);
          String str2 = Property.stringValue(localClass, "keyName", null);
          if (str2 != null)
            localEvent.plane = I18N.plane(str2);
        }
        catch (Throwable localThrowable) {
        }
      } else if (str1.startsWith("MISSION:")) { localEvent.mission = this.roster.readArgStr(UnicodeTo8bit.load(str1, false));
      } else if (str1.startsWith("EVENT:")) { localEvent.actions.add(this.roster.readArgStr(UnicodeTo8bit.load(str1, false)));
      } else if (str1.startsWith("FLIGHT TIME:")) { localEvent.flightTime = this.roster.readArgStr(str1); j = 1;
      }

      if (j != 0)
        break;
    }
    if ((localEvent.date == null) || (localEvent.plane == null) || (localEvent.mission == null))
    {
      return null;
    }return localEvent;
  }

  public GUIDGenPilotDetail(GWindowRoot paramGWindowRoot)
  {
    super(67);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("dgendetail.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

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

      if (paramGWindow == GUIDGenPilotDetail.this.bBack) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(658.0F), x1024(288.0F), y1024(48.0F), 0, GUIDGenPilotDetail.this.i18n("camps.Back"));
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIDGenPilotDetail.this.bBack.setPosC(x1024(56.0F), y1024(682.0F));
      GUIDGenPilotDetail.this.wTable.set1024PosSize(32.0F, 32.0F, 960.0F, 560.0F);
    }
  }

  public class Table extends GWindowTable
  {
    private ArrayList _events = GUIDGenPilotDetail.this.events;
    private int[] c = new int[5];
    private GFont fnt;
    private GColor myBrass = new GColor(99, 89, 74);

    private void computeHeight(int paramInt)
    {
      GUIDGenPilotDetail.Event localEvent = (GUIDGenPilotDetail.Event)GUIDGenPilotDetail.this.events.get(paramInt);
      localEvent.h = (int)(this.fnt.height * 1.2F);
      float f = this.fnt.height - this.fnt.descender;
      int i = (int)(computeLines(localEvent.mission, 0, localEvent.mission.length(), this.c[2]) * f);
      if (localEvent.h < i)
        localEvent.h = i;
      i = 0;
      for (int j = 0; j < localEvent.actions.size(); j++) {
        String str = (String)localEvent.actions.get(j);
        i += (int)(computeLines(str, 0, str.length(), this.c[4]) * f);
      }
      if (localEvent.h < i)
        localEvent.h = i;
    }

    private void computeHeights() {
      this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font = this.fnt;
      for (int i = 0; i < 5; i++)
        this.c[i] = (int)((GWindowTable.Column)this.jdField_columns_of_type_JavaUtilArrayList.get(i)).jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      int j = GUIDGenPilotDetail.this.events.size();
      for (int k = 0; k < j; k++)
        computeHeight(k); 
    }

    public float rowHeight(int paramInt) {
      if (this._events == null) return super.rowHeight(paramInt);
      if (paramInt >= GUIDGenPilotDetail.this.events.size()) return 0.0F;
      if (GUIDGenPilotDetail.this.events.size() == 0) return 0.0F;
      int i = 0;
      for (int j = 0; j < 5; j++)
        if (this.c[j] != ((GWindowTable.Column)this.jdField_columns_of_type_JavaUtilArrayList.get(j)).jdField_win_of_type_ComMaddoxGwindowGRegion.dx)
          i = 1;
      if (((GUIDGenPilotDetail.Event)GUIDGenPilotDetail.this.events.get(paramInt)).h == 0)
        i = 1;
      if (i != 0)
        computeHeights();
      return ((GUIDGenPilotDetail.Event)GUIDGenPilotDetail.this.events.get(paramInt)).h + 2;
    }
    public float fullClientHeight() {
      if (this._events == null) return super.fullClientHeight();
      int i = 0;
      int j = countRows();
      for (int k = 0; k < j; k++)
        i = (int)(i + rowHeight(k));
      return i;
    }
    public int countRows() {
      if (this._events == null) return 0;
      return GUIDGenPilotDetail.this.events.size();
    }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      if (paramInt1 > 0)
        GUISeparate.draw(this, this.myBrass, 0.0F, 0.0F, paramFloat1, 1.0F);
      setCanvasColorBLACK();
      this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font = this.fnt;
      GUIDGenPilotDetail.Event localEvent = (GUIDGenPilotDetail.Event)GUIDGenPilotDetail.this.events.get(paramInt1);
      String str = null;
      int i = 0;
      float f = this.fnt.height - this.fnt.descender;
      switch (paramInt2) { case 0:
        str = localEvent.date;
        i = 0;
        break;
      case 1:
        str = localEvent.plane;
        i = 0;
        break;
      case 2:
        str = localEvent.mission;
        drawLines(0.0F, 2.0F, str, 0, str.length(), this.c[2], f);
        return;
      case 3:
        if (localEvent.flightTime == null) str = ""; else
          str = localEvent.flightTime;
        i = 1;
        break;
      case 4:
        if (localEvent.actions.size() > 0) {
          int j = 2;
          for (int m = 0; m < localEvent.actions.size(); m++) {
            str = (String)localEvent.actions.get(m);
            int k;
            j += (int)(drawLines(0.0F, j, str, 0, str.length(), this.c[4], f) * f);
          }
        }
        return;
      }
      setCanvasColorBLACK();
      draw(0.0F, 2.0F, paramFloat1, (int)(this.fnt.height * 1.2F) - 2, i, str);
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void afterCreated() {
      super.afterCreated();
      this.fnt = GFont.New("courSmall");
      this.bColumnsSizable = true;
      this.bSelecting = false;
      this.bSelectRow = false;
      addColumn(I18N.gui("dgendetail.Date"), null);
      addColumn(I18N.gui("dgendetail.Plane"), null);
      addColumn(I18N.gui("dgendetail.Mission"), null);
      addColumn(I18N.gui("dgendetail.FlightTime"), null);
      addColumn(I18N.gui("dgendetail.Notes"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(10.0F);
      getColumn(1).setRelativeDx(10.0F);
      getColumn(2).setRelativeDx(20.0F);
      getColumn(3).setRelativeDx(10.0F);
      getColumn(4).setRelativeDx(20.0F);
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

  private static class Event
  {
    public String date;
    public String plane;
    public String flightTime;
    public String mission;
    public ArrayList actions = new ArrayList();
    public int h;

    private Event()
    {
    }

    Event(GUIDGenPilotDetail.1 param1)
    {
      this();
    }
  }
}