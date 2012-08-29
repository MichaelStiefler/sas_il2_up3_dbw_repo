package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.I18N;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Plugin
{
  public static Builder builder;
  public static final String SELECT_ICON = "icons/SelectIcon.mat";
  public static final String TARGET_ICON = "icons/target.mat";
  public static final String PLAYER_ICON = "icons/player.mat";
  public static Mat selectIcon;
  public static Mat targetIcon;
  public static Mat playerIcon;
  public String sectFile;
  private static ArrayList all;
  private static HashMap mapNames;

  public final String name()
  {
    return Property.stringValue(getClass(), "name", null);
  }
  public static String i18n(String paramString) {
    return I18N.bld(paramString);
  }
  public void render3D() {
  }
  public void preRenderMap2D() {
  }
  public void renderMap2DBefore() {
  }
  public void renderMap2D() {
  }
  public void renderMap2DAfter() {
  }
  public void insert(Loc paramLoc, boolean paramBoolean) {
  }
  public void beginFill(Point3d paramPoint3d) {
  }
  public void fill(Point3d paramPoint3d) {
  }
  public void endFill(Point3d paramPoint3d) {
  }
  public void delete(Loc paramLoc) {
  }
  public void deleteAll() {
  }
  public void delete(Actor paramActor) {
    paramActor.destroy();
  }
  public void afterDelete() {
  }
  public void selectAll() {
  }
  public void changeType(boolean paramBoolean1, boolean paramBoolean2) {
  }
  public void changeType() {
  }
  public void fillPopUpMenu(GWindowMenuPopUp paramGWindowMenuPopUp, Point3d paramPoint3d) {
  }
  public void syncSelector() {
  }
  public void updateSelector() {
  }
  public void updateSelectorMesh() {
  }
  public String[] actorInfo(Actor paramActor) { return null; }

  public static boolean isValidActorName(String paramString) {
    if (paramString == null) return false;
    if (paramString.length() == 0) return false;
    for (int i = 0; i < paramString.length(); i++) {
      char c = paramString.charAt(i);
      if ((Character.isWhitespace(c)) || (Character.isISOControl(c)))
        return false;
    }
    return true;
  }
  public void viewTypeAll(boolean paramBoolean) {
  }
  public void load(SectFile paramSectFile) {
  }
  public boolean save(SectFile paramSectFile) {
    return true;
  }
  public void mapLoaded() {
  }
  public boolean exitBuilder() { return true;
  }

  public void freeResources()
  {
  }

  public void configure()
  {
  }

  public void createGUI()
  {
  }

  public void start()
  {
  }

  protected static Plugin getPlugin(String paramString)
  {
    return (Plugin)mapNames.get(paramString);
  }

  protected static void loadAll(SectFile paramSectFile, String paramString, Builder paramBuilder)
  {
    builder = paramBuilder;
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    if (j <= 0)
      return;
    for (int k = 0; k < j; k++) {
      Object localObject = ObjIO.fromString(paramSectFile.line(i, k));
      if ((localObject != null) && ((localObject instanceof Plugin))) {
        Plugin localPlugin = (Plugin)localObject;
        String str = localPlugin.name();
        if ((str != null) && (!mapNames.containsKey(str))) {
          all.add(localObject);
          mapNames.put(str, localPlugin);
        }
      }
    }

    k = all.size();
    for (int m = 0; m < k; m++)
      ((Plugin)(Plugin)all.get(m)).configure();
  }

  public static String timeSecToString(double paramDouble) {
    int i = (int)Math.round(paramDouble / 60.0D);
    int j = i % 60;
    if (j < 10) {
      return "" + i / 60 % 24 + ":0" + j;
    }
    return "" + i / 60 % 24 + ":" + j;
  }

  protected static void doRender3D() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).render3D(); 
  }

  protected static void doPreRenderMap2D() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).preRenderMap2D(); 
  }

  protected static void doRenderMap2DBefore() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).renderMap2DBefore(); 
  }

  protected static void doRenderMap2D() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).renderMap2D(); 
  }

  protected static void doRenderMap2DAfter() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).renderMap2DAfter(); 
  }

  protected static void doCreateGUI() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).createGUI(); 
  }

  protected static void doStart() {
    selectIcon = IconDraw.get("icons/SelectIcon.mat");
    targetIcon = IconDraw.get("icons/target.mat");
    playerIcon = IconDraw.get("icons/player.mat");
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).start(); 
  }

  protected static void doInsert(Loc paramLoc, boolean paramBoolean) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).insert(paramLoc, paramBoolean);
  }

  protected static String mis_doGetProperties(Actor paramActor) {
    String str = "";
    for (int i = 0; i < all.size(); i++) {
      str = str + ((Plugin)all.get(i)).mis_getProperties(paramActor);
    }
    return str;
  }

  protected static Actor mis_doInsert(Loc paramLoc, String paramString) {
    Actor localActor = null;
    for (int i = 0; i < all.size(); i++) {
      localActor = ((Plugin)all.get(i)).mis_insert(paramLoc, paramString);
      if (localActor != null) {
        return localActor;
      }
    }
    return localActor;
  }

  protected static boolean mis_doValidateSelected(int paramInt1, int paramInt2) {
    int i = 0;
    for (int j = 0; j < all.size(); j++) {
      i = (i != 0) || (((Plugin)all.get(j)).mis_validateSelected(paramInt1, paramInt2)) ? 1 : 0;
    }
    return i;
  }

  public String mis_getProperties(Actor paramActor) {
    return "";
  }

  public Actor mis_insert(Loc paramLoc, String paramString) {
    return null;
  }

  public boolean mis_validateSelected(int paramInt1, int paramInt2) {
    return false;
  }

  protected static void doFillPopUpMenu(GWindowMenuPopUp paramGWindowMenuPopUp, Point3d paramPoint3d) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).fillPopUpMenu(paramGWindowMenuPopUp, paramPoint3d);
  }

  protected static void doBeginFill(Point3d paramPoint3d) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).beginFill(paramPoint3d); 
  }

  protected static void doFill(Point3d paramPoint3d) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).fill(paramPoint3d); 
  }

  protected static void doEndFill(Point3d paramPoint3d) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).endFill(paramPoint3d); 
  }

  protected static void doDelete(Loc paramLoc) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).delete(paramLoc); 
  }

  protected static void doDeleteAll() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).deleteAll(); 
  }

  protected static void doAfterDelete() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).afterDelete(); 
  }

  protected static void doSelectAll() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).selectAll(); 
  }

  protected static void doChangeType(boolean paramBoolean1, boolean paramBoolean2) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).changeType(paramBoolean1, paramBoolean2); 
  }

  protected static void doViewTypeAll(boolean paramBoolean) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).viewTypeAll(paramBoolean); 
  }

  protected static void doLoad(SectFile paramSectFile) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).load(paramSectFile); 
  }

  protected static boolean doSave(SectFile paramSectFile) {
    int i = all.size();
    for (int j = 0; j < i; j++)
      if (!((Plugin)(Plugin)all.get(j)).save(paramSectFile))
        return false;
    return true;
  }
  protected static void doMapLoaded() {
    builder.mapLoaded();
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).mapLoaded(); 
  }

  protected static void doFreeResources() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      ((Plugin)(Plugin)all.get(j)).freeResources(); 
  }

  protected static boolean doExitBuilder() {
    int i = all.size();
    for (int j = 0; j < i; j++)
      if (!((Plugin)(Plugin)all.get(j)).exitBuilder())
        return false;
    return true;
  }

  public static ArrayList zutiGetAllActors()
  {
    return all;
  }

  static
  {
    ObjIO.field(Plugin.class, "sectFile");

    all = new ArrayList();
    mapNames = new HashMap();
  }
}