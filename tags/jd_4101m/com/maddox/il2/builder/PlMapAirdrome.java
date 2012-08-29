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
    if (builder.pathes == null) return;
    Object[] arrayOfObject = builder.pathes.getOwnerAttached();
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
      if (builder.selectedPoint() == paramActor)
        builder.pathes.currentPPoint = null;
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
    if (builder.pathes == null) return;
    Object[] arrayOfObject = builder.pathes.getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor = (Actor)arrayOfObject[i];
      if (localActor == null) break;
      if ((localActor instanceof PathAirdrome)) {
        int j = ((Path)localActor).points();
        for (int k = 0; k < j; k++)
          builder.selectActorsAdd(((Path)localActor).point(k));
      }
    }
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (this.startComboBox1 != i) return;
    PathAirdrome localPathAirdrome = null;
    try
    {
      Point3d localPoint3d = paramLoc.getPoint();
      Object localObject;
      if (builder.selectedPath() != null) {
        localObject = builder.selectedPath();
        if (!(localObject instanceof PathAirdrome))
          return;
        localPathAirdrome = (PathAirdrome)localObject;
        if (j != localPathAirdrome._iType)
          builder.setSelected(null);
      }
      PAirdrome localPAirdrome;
      if (builder.selectedPoint() != null) {
        localObject = (PAirdrome)builder.selectedPoint();
        localPAirdrome = new PAirdrome(builder.selectedPath(), builder.selectedPoint(), localPoint3d, ((PAirdrome)localObject).type());
      } else {
        if ((j < 0) || (j >= this.type.length))
          return;
        localPathAirdrome = new PathAirdrome(builder.pathes, j);
        Property.set(localPathAirdrome, "builderPlugin", this);
        localPathAirdrome.drawing(this.mView.bChecked);
        localPAirdrome = new PAirdrome(localPathAirdrome, null, localPoint3d, j);
      }
      Property.set(localPAirdrome, "builderPlugin", this);
      Property.set(localPAirdrome, "builderSpawn", "");
      localPAirdrome.drawing(localPAirdrome.getOwner().isDrawing());
      if (paramBoolean)
        builder.setSelected(localPAirdrome);
    } catch (Exception localException) {
      if ((localPathAirdrome != null) && (localPathAirdrome.points() == 0))
        localPathAirdrome.destroy();
      System.out.println(localException);
    }
  }

  public void configure()
  {
    if (getPlugin("MapActors") == null)
      throw new RuntimeException("PlMapAirdrome: plugin 'MapActors' not found");
    this.pluginActors = ((PlMapActors)getPlugin("MapActors"));
    PathAirdrome.configure();
    this.type = new Type[] { new Type(PAirdrome.types[0]), new Type(PAirdrome.types[1]), new Type(PAirdrome.types[2]) };
  }

  private void viewUpdate()
  {
    if (builder.pathes == null)
      return;
    Object[] arrayOfObject = builder.pathes.getOwnerAttached();
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
      (builder.selectedPath() != null) && 
      ((builder.selectedPath() instanceof PathAirdrome))) {
      builder.setSelected(null);
    }

    if (!builder.isFreeView())
      builder.repaint();
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.mView.bChecked = paramBoolean;
    viewUpdate();
  }

  public void syncSelector() {
    PathAirdrome localPathAirdrome = (PathAirdrome)builder.selectedPath();
    fillComboBox2(this.startComboBox1, localPathAirdrome._iType);
  }

  private void fillComboBox1() {
    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add("Airdrome Points");
    if (this.startComboBox1 == 0)
      builder.wSelect.comboBox1.setSelected(0, true, false); 
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1)
      return;
    if (builder.wSelect.curFilledType != paramInt1) {
      builder.wSelect.curFilledType = paramInt1;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type.length; i++)
        builder.wSelect.comboBox2.add(this.type[i].name);
      builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    builder.wSelect.comboBox2.setSelected(paramInt2, true, false);
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i == PlMapAirdrome.this.startComboBox1) && (paramInt1 == 2))
          PlMapAirdrome.this.fillComboBox2(i, 0);
        return false;
      }
    });
    int i = builder.mDisplayFilter.subMenu.size() - 1;
    while ((i >= 0) && 
      (this.pluginActors.viewBridge != builder.mDisplayFilter.subMenu.getItem(i)))
    {
      i--;
    }
    i--;
    if (i >= 0) {
      int j = i;
      this.mView = builder.mDisplayFilter.subMenu.addItem(j, new GWindowMenuItem(builder.mDisplayFilter.subMenu, "show Airdrome Points", null)
      {
        public void execute() {
          this.bChecked = (!this.bChecked);
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