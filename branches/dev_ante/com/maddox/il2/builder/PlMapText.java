package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PlMapText extends Plugin
{
  protected ArrayList allActors = new ArrayList();
  GWindowTabDialogClient.Tab tabText;
  GWindowEditControl wText;
  GWindowComboControl wFont;
  GWindowComboControl wAlign;
  GWindowComboControl wColor;
  GWindowCheckBox wLevel0;
  GWindowCheckBox wLevel1;
  GWindowCheckBox wLevel2;
  private PlMapActors pluginActors;
  private int startComboBox1;
  private GWindowMenuItem viewType;

  private String staticFileName()
  {
    String str1 = "maps/" + PlMapLoad.mapFileName();
    SectFile localSectFile = new SectFile(str1);
    int i = localSectFile.sectionIndex("text");
    if ((i >= 0) && (localSectFile.vars(i) > 0)) {
      String str2 = localSectFile.var(i, 0);
      return HomePath.concatNames(str1, str2);
    }
    return null;
  }

  public boolean save(SectFile paramSectFile) {
    String str = staticFileName();
    if (str == null) return true; try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(str)));
      int i = this.allActors.size();
      for (int j = 0; j < i; j++) {
        ActorText localActorText = (ActorText)this.allActors.get(j);

        int k = 0;
        if (localActorText.bLevel[0] != 0) k |= 1;
        if (localActorText.bLevel[1] != 0) k |= 2;
        if (localActorText.bLevel[2] != 0) k |= 4;
        localPrintWriter.println("" + (int)localActorText.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double + " " + (int)localActorText.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double + " " + k + " " + localActorText.align + " " + localActorText.getFont() + " " + localActorText.color + " " + localActorText.getText());
      }

      localPrintWriter.close();
      return true;
    } catch (Exception localException) {
      System.out.println("MapTexts save failed: " + localException.getMessage());
      localException.printStackTrace();
    }
    return false;
  }

  private void load() {
    String str1 = staticFileName();
    if (str1 == null) return; try
    {
      Point3d localPoint3d = new Point3d();
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str1));
      while (true) {
        String str2 = localBufferedReader.readLine();
        if (str2 == null)
          break;
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(str2);
        localPoint3d.jdField_x_of_type_Double = localNumberTokenizer.next(0);
        localPoint3d.jdField_y_of_type_Double = localNumberTokenizer.next(0);
        localPoint3d.z = 0.0D;
        int i = localNumberTokenizer.next(7, 1, 7);
        int j = localNumberTokenizer.next(1, 0, 2);
        int k = localNumberTokenizer.next(1, 0, 2);
        int m = localNumberTokenizer.next(0, 0, 19);
        String str3 = localNumberTokenizer.nextToken("");
        int n = 0;
        int i1 = str3.length() - 1;
        for (; n < i1; n++) if (str3.charAt(n) > ' ')
            break; for (; n < i1; i1--) if (str3.charAt(i1) > ' ')
            break; if (n == i1) return;
        if ((n != 0) || (i1 != str3.length() - 1))
          str3 = str3.substring(n, i1 + 1);
        ActorText localActorText = new ActorText(localPoint3d);
        localActorText.setFont(k);
        localActorText.setText(str3);
        localActorText.align = j;
        localActorText.color = m;
        localActorText.bLevel[0] = ((i & 0x1) != 0 ? 1 : false);
        localActorText.bLevel[1] = ((i & 0x2) != 0 ? 1 : false);
        localActorText.bLevel[2] = ((i & 0x4) != 0 ? 1 : false);
        insert(localActorText, false);
      }
      localBufferedReader.close();
    } catch (Exception localException) {
      System.out.println("MapTexts load failed: " + localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void mapLoaded() {
    deleteAll();
    if (!Plugin.builder.isLoadedLandscape()) return;
    load();
  }

  public void deleteAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorText localActorText = (ActorText)this.allActors.get(j);
      localActorText.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  public void selectAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorText localActorText = (ActorText)this.allActors.get(j);
      Plugin.builder.selectActorsAdd(localActorText);
    }
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    if (i != this.startComboBox1)
      return;
    ActorText localActorText = new ActorText(paramLoc.getPoint());
    insert(localActorText, paramBoolean);
  }

  private void insert(ActorText paramActorText, boolean paramBoolean) {
    Plugin.builder.align(paramActorText);
    Property.set(paramActorText, "builderSpawn", "");
    Property.set(paramActorText, "builderPlugin", this);
    this.allActors.add(paramActorText);
    if (paramBoolean)
      Plugin.builder.setSelected(paramActorText);
  }

  public void renderMap2D() {
    if (Plugin.builder.isFreeView()) return;
    if (Plugin.builder.isView3D()) return;
    if (!this.viewType.bChecked) return;
    double d = Plugin.builder.camera2D.worldScale;
    int i = 1;
    if (d < 0.01D) i = 0;
    else if (d > 0.05D) i = 2;
    ActorText.setRenderLevel(i);
    ActorText.setRenderClip(0.0D, 0.0D, Plugin.builder.clientWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, Plugin.builder.clientWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);

    int j = this.allActors.size();
    for (int k = 0; k < j; k++) {
      ActorText localActorText = (ActorText)this.allActors.get(k);
      localActorText.render2d();
    }
  }

  public void syncSelector()
  {
    ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    Plugin.builder.wSelect.tabsClient.addTab(1, this.tabText);
    this.wText.setValue(localActorText.getText(), false);
    this.wFont.setSelected(localActorText.getFont(), true, false);
    this.wAlign.setSelected(localActorText.align, true, false);

    this.wColor.setSelected(localActorText.color, true, false);
    this.wLevel0.setChecked(localActorText.bLevel[0], false);
    this.wLevel1.setChecked(localActorText.bLevel[1], false);
    this.wLevel2.setChecked(localActorText.bLevel[2], false);
    localActorText.saveAsDef();
  }

  private void updateView() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorText localActorText = (ActorText)this.allActors.get(j);
      localActorText.drawing(this.viewType.bChecked);
    }
  }

  public void configure()
  {
    if (Plugin.getPlugin("MapActors") == null)
      throw new RuntimeException("PlMisStatic: plugin 'MapActors' not found");
    this.pluginActors = ((PlMapActors)Plugin.getPlugin("MapActors"));
  }

  private void fillComboBox2(int paramInt) {
    if (paramInt != this.startComboBox1)
      return;
    if (Plugin.builder.wSelect.curFilledType != paramInt) {
      Plugin.builder.wSelect.curFilledType = paramInt;
      Plugin.builder.wSelect.comboBox2.clear(false);
      Plugin.builder.wSelect.comboBox2.add("Text");
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.viewType.bChecked = paramBoolean;
    updateView();
  }

  public void createGUI() {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    Plugin.builder.wSelect.comboBox1.add("Text");
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMapText.this.fillComboBox2(i);
        return false;
      }
    });
    int i = Plugin.builder.mDisplayFilter.subMenu.size() - 1;
    while (i >= 0) {
      if (this.pluginActors.viewBridge == Plugin.builder.mDisplayFilter.subMenu.getItem(i))
        break;
      i--;
    }
    i--;
    if (i >= 0) {
      this.viewType = Plugin.builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "show Text", null)
      {
        public void execute() {
          this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
          PlMapText.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabText = Plugin.builder.wSelect.tabsClient.createTab("Text", localGWindowDialogClient);

    localGWindowDialogClient.addControl(this.wText = new GWindowEditControl(localGWindowDialogClient, 1.0F, 1.0F, 16.0F, 1.3F, "") {
      public void created() { super.created(); }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.setText(getValue());
        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 4.0F, 1.3F, "Font:", null));
    localGWindowDialogClient.addControl(this.wFont = new GWindowComboControl(localGWindowDialogClient, 6.0F, 3.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("Small");
        add("Medium");
        add("Large"); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.setFont(getSelected());
        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 4.0F, 1.3F, "Align:", null));
    localGWindowDialogClient.addControl(this.wAlign = new GWindowComboControl(localGWindowDialogClient, 6.0F, 5.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("Left");
        add("Center");
        add("Right"); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.align = getSelected();
        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 4.0F, 1.3F, "Color:", null));
    localGWindowDialogClient.addControl(this.wColor = new GWindowComboControl(localGWindowDialogClient, 6.0F, 7.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("(  0,  0,  0)");
        add("(128,  0,  0)");
        add("(  0,128,  0)");
        add("(128,128,  0)");
        add("(  0,  0,128)");
        add("(128,  0,128)");
        add("(  0,128,128)");
        add("(192,192,192)");
        add("(192,220,192)");
        add("(166,202,240)");
        add("(255,251,240)");
        add("(160,160,164");
        add("(128,128,128)");
        add("(255,  0,  0)");
        add("(  0,255,  0)");
        add("(255,255,  0)");
        add("(  0,  0,255)");
        add("(255,  0,255)");
        add("(  0,255,255)");
        add("(255,255,255)"); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.color = getSelected();

        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 4.0F, 1.3F, "Level:", null));
    localGWindowDialogClient.addControl(this.wLevel0 = new GWindowCheckBox(localGWindowDialogClient, 6.0F, 9.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.bLevel[0] = isChecked();
        localActorText.checkLevels(0);
        setChecked(localActorText.bLevel[0], false);
        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wLevel1 = new GWindowCheckBox(localGWindowDialogClient, 8.0F, 9.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.bLevel[1] = isChecked();
        localActorText.checkLevels(1);
        setChecked(localActorText.bLevel[1], false);
        localActorText.saveAsDef();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wLevel2 = new GWindowCheckBox(localGWindowDialogClient, 10.0F, 9.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorText localActorText = (ActorText)Plugin.builder.selectedActor();
        localActorText.bLevel[2] = isChecked();
        localActorText.checkLevels(2);
        setChecked(localActorText.bLevel[2], false);
        localActorText.saveAsDef();
        return false;
      } } );
  }

  public void start() {
    ActorText.setupFonts();
  }
  static {
    Property.set(PlMapText.class, "name", "MapText");
  }
}