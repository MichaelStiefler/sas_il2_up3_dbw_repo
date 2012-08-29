package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Render;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
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

public class PlMapLabel extends Plugin
{
  protected ArrayList allActors = new ArrayList();

  private Point2d p2d = new Point2d();
  private PlMapActors pluginActors;
  private int startComboBox1;

  private String staticFileName()
  {
    return "maps/" + PlMapLoad.mapDirName() + "/labels.txt";
  }

  public boolean save(SectFile paramSectFile) {
    String str = staticFileName();
    if (str == null) return true; try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(str)));
      int i = this.allActors.size();
      for (int j = 0; j < i; j++) {
        ActorLabel localActorLabel = (ActorLabel)this.allActors.get(j);
        localPrintWriter.println("" + (int)localActorLabel.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double + " " + (int)localActorLabel.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double);
      }
      localPrintWriter.close();
      return true;
    } catch (Exception localException) {
      System.out.println("MapLabels save failed: " + localException.getMessage());
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
        ActorLabel localActorLabel = new ActorLabel(localPoint3d);
        insert(localActorLabel, false);
      }
      localBufferedReader.close();
    } catch (Exception localException) {
      System.out.println("MapLabels load failed: " + localException.getMessage());
    }
  }

  public void mapLoaded()
  {
    deleteAll();
    if (!Plugin.builder.isLoadedLandscape()) return;
    load();
  }

  private void _deleteAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorText localActorText = (ActorText)this.allActors.get(j);
      localActorText.destroy();
    }
    this.allActors.clear();
  }

  public void deleteAll()
  {
  }

  public void delete(Actor paramActor)
  {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  public void selectAll()
  {
  }

  public static void insert(Point3d paramPoint3d)
  {
    PlMapLabel localPlMapLabel = (PlMapLabel)Plugin.getPlugin("MapLabel");
    localPlMapLabel._insert(paramPoint3d);
  }

  public void _insert(Point3d paramPoint3d) {
    ActorLabel localActorLabel = new ActorLabel(paramPoint3d);
    insert(localActorLabel, false);
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    if (i != this.startComboBox1)
      return;
    ActorLabel localActorLabel = new ActorLabel(paramLoc.getPoint());
    insert(localActorLabel, paramBoolean);
  }

  private void insert(ActorLabel paramActorLabel, boolean paramBoolean) {
    Plugin.builder.align(paramActorLabel);
    Property.set(paramActorLabel, "builderSpawn", "");
    Property.set(paramActorLabel, "builderPlugin", this);
    this.allActors.add(paramActorLabel);
    if (paramBoolean)
      Plugin.builder.setSelected(paramActorLabel);
  }

  public void renderMap2D() {
    if (Plugin.builder.isFreeView()) return;
    Render.prepareStates();
    IconDraw.setColor(255, 255, 255, 255);
    for (int i = 0; i < this.allActors.size(); i++) {
      Actor localActor = (Actor)this.allActors.get(i);
      if (Plugin.builder.project2d(localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d))
        if (Plugin.builder.selectedActor() == localActor) {
          IconDraw.setColor(255, 255, 0, 255);
          IconDraw.render(localActor, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
          IconDraw.setColor(255, 255, 255, 255);
        } else {
          IconDraw.render(localActor, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
        }
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
      Plugin.builder.wSelect.comboBox2.add("Label");
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
  }

  public void createGUI() {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    Plugin.builder.wSelect.comboBox1.add("Label");
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMapLabel.this.fillComboBox2(i);
        return false;
      } } );
  }

  public void start() {
    HotKeyCmdEnv.setCurrentEnv(Builder.envName);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectLabel") {
      public void begin() { PlMapLabel.this.setSelected(this.sName, PlMapLabel.this.startComboBox1, 0); } } );
  }

  private void setSelected(String paramString, int paramInt1, int paramInt2) {
    Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, true);
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, true);
    Plugin.builder.tip(paramString);
  }
  static {
    Property.set(PlMapLabel.class, "name", "MapLabel");
  }
}