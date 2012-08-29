package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import java.io.PrintStream;

public class PlMapAirdrome extends Plugin
{
  protected Type[] type;
  private PlMapActors pluginActors;
  private int startComboBox1;
  public GWindowMenuItem mView;

  public void deleteAll()
  {
    if (Plugin.builder.pathes == null) return;
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor = (Actor)arrayOfObject[i];
      if (localActor == null) break;
      if ((localActor instanceof PathAirdrome)) {
        PathAirdrome localPathAirdrome = (PathAirdrome)localActor;
        localPathAirdrome.destroy();
      }
    }
  }

  public void delete(Actor paramActor) {
    if ((paramActor instanceof PAirdrome)) {
      if (Plugin.builder.selectedPoint() == paramActor)
        Plugin.builder.pathes.currentPPoint = null;
      PAirdrome localPAirdrome = (PAirdrome)paramActor;
      PathAirdrome localPathAirdrome = (PathAirdrome)localPAirdrome.getOwner();
      if ((Actor.isValid(localPathAirdrome)) && (localPathAirdrome.points() == 1)) {
        localPathAirdrome.destroy();
        return;
      }
      localPAirdrome.destroy();
    }
  }

  public void selectAll() {
    if (Plugin.builder.pathes == null) return;
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor = (Actor)arrayOfObject[i];
      if (localActor == null) break;
      if ((localActor instanceof PathAirdrome)) {
        int j = ((Path)localActor).points();
        for (int k = 0; k < j; k++)
          Plugin.builder.selectActorsAdd(((Path)localActor).point(k));
      }
    }
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if (this.startComboBox1 != i) return;
    PathAirdrome localPathAirdrome = null;
    try
    {
      Point3d localPoint3d = paramLoc.getPoint();
      Object localObject;
      if (Plugin.builder.selectedPath() != null) {
        localObject = Plugin.builder.selectedPath();
        if (!(localObject instanceof PathAirdrome))
          return;
        localPathAirdrome = (PathAirdrome)localObject;
        if (j != localPathAirdrome._iType)
          Plugin.builder.setSelected(null);
      }
      PAirdrome localPAirdrome;
      if (Plugin.builder.selectedPoint() != null) {
        localObject = (PAirdrome)Plugin.builder.selectedPoint();
        localPAirdrome = new PAirdrome(Plugin.builder.selectedPath(), Plugin.builder.selectedPoint(), localPoint3d, ((PAirdrome)localObject).type());
      } else {
        if ((j < 0) || (j >= this.type.length))
          return;
        localPathAirdrome = new PathAirdrome(Plugin.builder.pathes, j);
        Property.set(localPathAirdrome, "builderPlugin", this);
        localPathAirdrome.drawing(this.mView.bChecked);
        localPAirdrome = new PAirdrome(localPathAirdrome, null, localPoint3d, j);
      }
      Property.set(localPAirdrome, "builderPlugin", this);
      Property.set(localPAirdrome, "builderSpawn", "");
      localPAirdrome.drawing(localPAirdrome.getOwner().isDrawing());
      if (paramBoolean)
        Plugin.builder.setSelected(localPAirdrome);
    } catch (Exception localException) {
      if ((localPathAirdrome != null) && (localPathAirdrome.points() == 0))
        localPathAirdrome.destroy();
      System.out.println(localException);
    }
  }

  public void configure()
  {
    if (Plugin.getPlugin("MapActors") == null)
      throw new RuntimeException("PlMapAirdrome: plugin 'MapActors' not found");
    this.pluginActors = ((PlMapActors)Plugin.getPlugin("MapActors"));
    PathAirdrome.configure();
    this.type = new Type[] { new Type(PAirdrome.types[0]), new Type(PAirdrome.types[1]), new Type(PAirdrome.types[2]) };
  }

  private void viewUpdate()
  {
    if (Plugin.builder.pathes == null)
      return;
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor = (Actor)arrayOfObject[i];
      if (localActor == null) break;
      if ((localActor instanceof PathAirdrome)) {
        localActor.drawing(this.mView.bChecked);
        int j = ((Path)localActor).points();
        for (int k = 0; k < j; k++)
          ((Path)localActor).point(k).drawing(this.mView.bChecked);
      }
    }
    if ((!this.mView.bChecked) && 
      (Plugin.builder.selectedPath() != null) && 
      ((Plugin.builder.selectedPath() instanceof PathAirdrome))) {
      Plugin.builder.setSelected(null);
    }

    if (!Plugin.builder.isFreeView())
      Plugin.builder.repaint();
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.mView.bChecked = paramBoolean;
    viewUpdate();
  }

  public void syncSelector() {
    PathAirdrome localPathAirdrome = (PathAirdrome)Plugin.builder.selectedPath();
    fillComboBox2(this.startComboBox1, localPathAirdrome._iType);
  }

  private void fillComboBox1() {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    Plugin.builder.wSelect.comboBox1.add("Airdrome Points");
    if (this.startComboBox1 == 0)
      Plugin.builder.wSelect.comboBox1.setSelected(0, true, false); 
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1)
      return;
    if (Plugin.builder.wSelect.curFilledType != paramInt1) {
      Plugin.builder.wSelect.curFilledType = paramInt1;
      Plugin.builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type.length; i++)
        Plugin.builder.wSelect.comboBox2.add(this.type[i].name);
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, false);
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i == PlMapAirdrome.this.startComboBox1) && (paramInt1 == 2))
          PlMapAirdrome.this.fillComboBox2(i, 0);
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
      int j = i;
      this.mView = Plugin.builder.mDisplayFilter.subMenu.addItem(j, new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "show Airdrome Points", null)
      {
        public void execute() {
          this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
          PlMapAirdrome.this.viewUpdate();
        }
      });
      this.mView.bChecked = true;
      viewUpdate();
    }
  }

  static {
    Property.set(PlMapAirdrome.class, "name", "MapAirdrome");
  }

  public static class Type
  {
    public String name;

    public Type(String paramString)
    {
      this.name = paramString;
    }
  }
}