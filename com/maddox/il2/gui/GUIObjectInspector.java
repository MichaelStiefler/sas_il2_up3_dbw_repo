package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIObjectInspector extends GameState
{
  public static String type;
  public static int s_country = 0;
  public static int s_object = 0;
  public static float s_scroll = 0.0F;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wView;
  public GWindowComboControl wCountry;
  public Table wTable;
  public ScrollDescript wScrollDescription;
  public Descript wDescript;
  public GWindowVScrollBar wDistance;
  public static String[] cnt = { "", "" };

  public void enterPush(GameState paramGameState)
  {
    s_object = 0;
    s_scroll = 0.0F;
    _enter();
  }

  public void _enter() {
    World.cur().camouflage = 0;
    this.wCountry.setSelected(s_country, true, false);

    Main3D.menuMusicPlay(s_country == 0 ? "ru" : "de");
    fillObjects();
    this.client.activateWindow();
    this.wTable.resolutionChanged();

    if (this.wTable.countRows() > 0) {
      this.wTable.setSelect(s_object, 0);
      if (this.wTable.vSB.isVisible())
        this.wTable.vSB.setPos(s_scroll, true); 
    }
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public void fillCountries() {
    this.wCountry.clear();
    String str = "NoName";
    for (int i = 0; i < cnt.length; i++) {
      try {
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
        str = localResourceBundle.getString(cnt[i]);
      } catch (Exception localException) {
        str = cnt[i];
      }
      this.wCountry.add(str);
    }
  }

  public void fillObjects() {
    this.wTable.objects.clear();
    int i = this.wCountry.getSelected() + 1;
    String str1;
    SectFile localSectFile;
    int k;
    int m;
    Object localObject1;
    Object localObject2;
    String str4;
    int i1;
    if ("air".equals(type)) {
      str1 = "com/maddox/il2/objects/air.ini";
      localSectFile = new SectFile(str1, 0);
      int j = localSectFile.sectionIndex("AIR");
      k = localSectFile.vars(j);
      for (m = 0; m < k; m++) {
        String str3 = localSectFile.var(j, m);
        localObject1 = new NumberTokenizer(localSectFile.value(j, m));
        localObject2 = "i18n/air";
        str4 = ((NumberTokenizer)localObject1).next();
        i1 = ((NumberTokenizer)localObject1).next(0);
        int i2 = 1;
        Object localObject3 = null;
        Object localObject4;
        while (((NumberTokenizer)localObject1).hasMoreTokens()) {
          localObject4 = ((NumberTokenizer)localObject1).next();
          if ("NOINFO".equals(localObject4)) {
            i2 = 0;
            break;
          }if (!"NOQUICK".equals(localObject4))
          {
            localObject3 = localObject4;
          }
        }
        if ((i2 != 0) && (i1 == i)) {
          localObject4 = new ObjectInfo((String)localObject2, str3, false, localObject3);
          this.wTable.objects.add(localObject4);
        }

      }

    }
    else
    {
      str1 = "i18n/" + type + ".ini";
      localSectFile = new SectFile(str1, 0);
      String str2 = "i18n/" + type;
      k = localSectFile.sectionIndex("ALL");
      m = localSectFile.vars(k);
      for (int n = 0; n < m; n++) {
        localObject1 = localSectFile.var(k, n);
        localObject2 = new NumberTokenizer(localSectFile.value(k, n));
        str4 = ((NumberTokenizer)localObject2).next();
        i1 = ((NumberTokenizer)localObject2).next(0);
        if (i1 == i) {
          ObjectInfo localObjectInfo = new ObjectInfo(str2, (String)localObject1, true, null);
          this.wTable.objects.add(localObjectInfo);
        }
      }
    }
    this.wTable.resized();
  }

  public GUIObjectInspector(GWindowRoot paramGWindowRoot)
  {
    super(22);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("obj.infoI");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wCountry = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wCountry.setEditable(false);
    fillCountries();
    this.wCountry.setSelected(s_country, true, false);

    this.wTable = new Table(this.dialogClient);
    this.dialogClient.create(this.wScrollDescription = new ScrollDescript());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wView = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  static
  {
    cnt[0] = "allies";
    cnt[1] = "axis";
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIObjectInspector.this.wCountry) {
        GUIObjectInspector.this.fillObjects();
        int i = GUIObjectInspector.this.wCountry.getSelected();
        if (i >= 0)
        {
          Main3D.menuMusicPlay(i == 0 ? "ru" : "de");
          GUIObjectInspector.s_country = GUIObjectInspector.this.wCountry.getSelected();
          if (GUIObjectInspector.this.wTable.countRows() > 0) {
            GUIObjectInspector.s_object = 0;
            GUIObjectInspector.s_scroll = 0.0F;
            GUIObjectInspector.this.wTable.setSelect(GUIObjectInspector.s_object, 0);
            if (GUIObjectInspector.this.wTable.vSB.isVisible())
              GUIObjectInspector.this.wTable.vSB.setPos(GUIObjectInspector.s_scroll, true);
          }
        }
        return true;
      }
      if (paramGWindow == GUIObjectInspector.this.wPrev) {
        Main.stateStack().pop();
        return true;
      }

      if (paramGWindow == GUIObjectInspector.this.wView) {
        GUIObjectInspector.type = GUIObjectInspector.type;
        GUIObjectInspector.s_object = GUIObjectInspector.this.wTable.selectRow;
        GUIObjectInspector.s_scroll = GUIObjectInspector.this.wTable.vSB.pos();
        Main.stateStack().change(23);
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(40.0F), y1024(620.0F), x1024(250.0F), 2.0F);

      GUISeparate.draw(this, GColor.Gray, x1024(320.0F), y1024(32.0F), 2.0F, y1024(655.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(40.0F), y1024(40.0F), x1024(240.0F), y1024(32.0F), 0, GUIObjectInspector.this.i18n("obj.SelectCountry"));
      draw(x1024(360.0F), y1024(40.0F), x1024(248.0F), y1024(32.0F), 0, GUIObjectInspector.this.i18n("obj.Description"));
      draw(x1024(104.0F), y1024(652.0F), x1024(192.0F), y1024(48.0F), 0, GUIObjectInspector.this.i18n("obj.Back"));

      draw(x1024(730.0F), y1024(652.0F), x1024(192.0F), y1024(48.0F), 2, GUIObjectInspector.this.i18n("obj.View"));
      setCanvasColorWHITE();
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIObjectInspector.this.wPrev.setPosC(x1024(64.0F), y1024(676.0F));
      GUIObjectInspector.this.wView.setPosC(x1024(960.0F), y1024(676.0F));
      GUIObjectInspector.this.wCountry.setPosSize(x1024(40.0F), y1024(82.0F), x1024(246.0F), M(2.0F));
      GUIObjectInspector.this.wTable.setPosSize(x1024(40.0F), y1024(194.0F), x1024(246.0F), y1024(400.0F));
      GUIObjectInspector.this.wScrollDescription.setPosSize(x1024(360.0F), y1024(80.0F), x1024(625.0F), y1024(514.0F));
    }
  }

  public class Descript extends GWindowDialogClient
  {
    public Descript()
    {
    }

    public void render()
    {
      String str = null;
      if (GUIObjectInspector.this.wTable.selectRow >= 0) {
        str = ((GUIObjectInspector.ObjectInfo)GUIObjectInspector.this.wTable.objects.get(GUIObjectInspector.this.wTable.selectRow)).info;
        if ((str != null) && (str.length() == 0)) str = null;
      }
      if (str != null) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        setCanvasColorBLACK();
        this.root.C.clip.y += localGBevel.T.dy;
        this.root.C.clip.dy -= localGBevel.T.dy + localGBevel.B.dy;
        drawLines(localGBevel.L.dx + 2.0F, localGBevel.T.dy + 2.0F, str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F, this.root.C.font.height);
      }
    }

    public void computeSize() {
      String str = null;
      if (GUIObjectInspector.this.wTable.selectRow >= 0) {
        str = ((GUIObjectInspector.ObjectInfo)GUIObjectInspector.this.wTable.objects.get(GUIObjectInspector.this.wTable.selectRow)).info;
        if ((str != null) && (str.length() == 0)) str = null;
      }
      if (str != null) {
        this.win.dx = this.parentWindow.win.dx;
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        int i = computeLines(str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
        this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        if (this.win.dy > this.parentWindow.win.dy) {
          this.win.dx = (this.parentWindow.win.dx - lookAndFeel().getVScrollBarW());
          i = computeLines(str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
          this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        }
      } else {
        this.win.dx = this.parentWindow.win.dx;
        this.win.dy = this.parentWindow.win.dy;
      }
    }
  }

  public class ScrollDescript extends GWindowScrollingDialogClient
  {
    public ScrollDescript()
    {
    }

    public void created()
    {
      this.fixed = (GUIObjectInspector.this.wDescript = (GUIObjectInspector.Descript)create(new GUIObjectInspector.Descript(GUIObjectInspector.this)));
      this.fixed.bNotify = true;
      this.bNotify = true;
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void resized() {
      if (GUIObjectInspector.this.wDescript != null) {
        GUIObjectInspector.this.wDescript.computeSize();
      }
      super.resized();
      if (this.vScroll.isVisible()) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        this.vScroll.setPos(this.win.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.vScroll.setSize(lookAndFeel().getVScrollBarW(), this.win.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList objects = new ArrayList();

    public int countRows() { return this.objects != null ? this.objects.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      String str = ((GUIObjectInspector.ObjectInfo)this.objects.get(paramInt1)).name;
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

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      GUIObjectInspector.this.wScrollDescription.resized();
      if (GUIObjectInspector.this.wScrollDescription.vScroll.isVisible())
        GUIObjectInspector.this.wScrollDescription.vScroll.setPos(0.0F, true); 
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("obj.ObjectTypesList"), null);
      this.vSB.scroll = rowHeight(0);
      this.bNotify = true;
      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super(2.0F, 4.0F, 20.0F, 16.0F);
    }
  }

  static class ObjectInfo
  {
    public String key;
    public String name;
    public String info;
    public boolean bGround;
    public Regiment reg;

    public ObjectInfo(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
    {
      this.key = paramString2;
      this.bGround = paramBoolean;
      if (!paramBoolean) {
        this.name = I18N.plane(paramString2);
        try {
          ResourceBundle localResourceBundle1 = ResourceBundle.getBundle(paramString1, RTSConf.cur.locale, LDRres.loader());
          this.info = localResourceBundle1.getString(paramString2);
        } catch (Exception localException1) {
          this.info = "";
        }
      } else {
        try {
          ResourceBundle localResourceBundle2 = ResourceBundle.getBundle(paramString1, RTSConf.cur.locale, LDRres.loader());
          this.name = localResourceBundle2.getString(paramString2 + "_NAME");
          this.info = localResourceBundle2.getString(paramString2 + "_INFO");
        } catch (Exception localException2) {
          this.name = paramString2;
          this.info = "";
        }
      }
      if (paramString3 != null)
        this.reg = ((Regiment)Actor.getByName(paramString3));
    }
  }
}