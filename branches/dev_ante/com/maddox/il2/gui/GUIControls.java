package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.IntHashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUIControls extends GameState
{
  private static final String JOYENV = "move";
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public ScrollClient scrollClient;
  public FixedClient fixedClient;
  private Item[] items;
  private HashMapExt mapItems;
  private HashMapExt mapItemsJoy;
  private float itemsDY_1024 = 1000.0F;

  private IntHashtable hashJoyMove = new IntHashtable();
  private HashMap mapEnvLink = new HashMap();
  private int[] povState = new int[16];
  public GUIButton bBack;
  public GUIButton bDefault;
  ResourceBundle resource;
  TreeMap _sortMap = new TreeMap();

  int[] curKey = new int[2];
  int keySum;
  int keySum2;
  int iFinded;
  GWindowMessageBox messageBox = null;

  public void _enter()
  {
    fillItems();
    this.client.showWindow();

    this.client.doResolutionChanged();
    this.client.doResolutionChanged();
    GUI.activateJoy();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private void fillItems()
  {
    for (int i = 0; i < this.items.length; i++) {
      localObject1 = this.items[i];
      int tmp23_22 = 0; localObject1.jdField_key_of_type_ArrayOfInt[1] = tmp23_22; localObject1.jdField_key_of_type_ArrayOfInt[0] = tmp23_22;
    }

    Object localObject1 = new ArrayList();
    String[] arrayOfString = UserCfg.nameHotKeyEnvs;
    for (int j = 0; j < arrayOfString.length; j++) {
      HotKeyEnv localHotKeyEnv = HotKeyEnv.env(arrayOfString[j]);
      boolean bool = "move".equals(arrayOfString[j]);
      HashMapInt localHashMapInt = localHotKeyEnv.all();
      HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
      int m;
      Object localObject2;
      while (localHashMapIntEntry != null) {
        m = localHashMapIntEntry.getKey();
        localObject2 = (String)localHashMapIntEntry.getValue();
        Item localItem2 = (Item)this.mapItems.get(localObject2);
        if (localItem2 != null) {
          if (localItem2.jdField_key_of_type_ArrayOfInt[0] == 0) localItem2.jdField_key_of_type_ArrayOfInt[0] = m;
          else if (localItem2.jdField_key_of_type_ArrayOfInt[1] == 0) localItem2.jdField_key_of_type_ArrayOfInt[1] = m;
          else {
            ((ArrayList)localObject1).add(new Integer(m));
          }
          if (bool) {
            ((ItemJoy)localItem2).bMinus = false;
            if (localItem2.jdField_key_of_type_ArrayOfInt[1] != 0) { ((ArrayList)localObject1).add(new Integer(localItem2.jdField_key_of_type_ArrayOfInt[1])); localItem2.jdField_key_of_type_ArrayOfInt[1] = 0; }
          }
        } else if ((bool) && (((String)localObject2).charAt(0) == '-')) {
          ItemJoy localItemJoy = (ItemJoy)this.mapItems.get(((String)localObject2).substring(1));
          if (localItemJoy != null) {
            localItemJoy.bMinus = true;
            if (localItemJoy.jdField_key_of_type_ArrayOfInt[0] == 0) localItemJoy.jdField_key_of_type_ArrayOfInt[0] = m;
            else {
              ((ArrayList)localObject1).add(new Integer(m));
            }
            if (localItemJoy.jdField_key_of_type_ArrayOfInt[1] != 0) { ((ArrayList)localObject1).add(new Integer(localItemJoy.jdField_key_of_type_ArrayOfInt[1])); localItemJoy.jdField_key_of_type_ArrayOfInt[1] = 0; }
          }
        }
        localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
      }
      if (((ArrayList)localObject1).size() > 0) {
        for (m = 0; m < ((ArrayList)localObject1).size(); m++) {
          localObject2 = (Integer)((ArrayList)localObject1).get(m);
          localHashMapInt.remove(((Integer)localObject2).intValue());
        }
        ((ArrayList)localObject1).clear();
      }
    }

    for (int k = 0; k < this.items.length; k++) {
      Item localItem1 = this.items[k];
      if (localItem1.control != null)
        localItem1.control.fillCaption();
    }
  }

  private void initResource()
  {
    try {
      this.resource = ResourceBundle.getBundle("i18n/controls", RTSConf.cur.locale, LDRres.loader()); } catch (Exception localException) {
    }
  }

  private String resName(String paramString) {
    if (this.resource == null) return paramString; try
    {
      return this.resource.getString(paramString); } catch (Exception localException) {
    }
    return paramString;
  }

  private void createItems() {
    initResource();
    this.mapEnvLink.put("PanView", "SnapView");
    this.mapEnvLink.put("SnapView", "PanView");
    ArrayList localArrayList = new ArrayList();
    this.mapItems = new HashMapExt();
    String[] arrayOfString = UserCfg.nameHotKeyEnvs;
    float f = 0.0F;
    for (int i = 0; i < arrayOfString.length; i++) {
      Object localObject = new Item();
      f += 32.0F;
      ((Item)localObject).y = f;
      if (arrayOfString[i].startsWith("$$$"))
        ((Item)localObject).label = this.fixedClient.addLabel(new ItemLabel(this.fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, "", null));
      else
        ((Item)localObject).label = this.fixedClient.addLabel(new ItemLabel(this.fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(arrayOfString[i]), null));
      ((Item)localObject).label.color = -16632394;
      localArrayList.add(localObject);
      HotKeyCmdEnv localHotKeyCmdEnv = HotKeyCmdEnv.env(arrayOfString[i]);
      HotKeyEnv localHotKeyEnv = HotKeyEnv.env(arrayOfString[i]);
      ((Item)localObject).env = localHotKeyEnv;
      HashMapExt localHashMapExt = localHotKeyCmdEnv.all();
      Iterator localIterator = localHashMapExt.keySet().iterator();
      while (localIterator.hasNext()) {
        HotKeyCmd localHotKeyCmd1 = (HotKeyCmd)localHashMapExt.get(localIterator.next());
        if (localHotKeyCmd1.sortingName != null)
          this._sortMap.put(localHotKeyCmd1.sortingName, localHotKeyCmd1);
      }
      boolean bool = "move".equals(arrayOfString[i]);
      localIterator = this._sortMap.keySet().iterator();
      while (localIterator.hasNext()) {
        HotKeyCmd localHotKeyCmd2 = (HotKeyCmd)this._sortMap.get(localIterator.next());
        if (localHotKeyCmd2.name().startsWith("$$$")) {
          localObject = new Item();
          f += 32.0F;
          ((Item)localObject).y = f;
          ((Item)localObject).label = this.fixedClient.addLabel(new ItemLabel(this.fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, "", null));
          localArrayList.add(localObject);
        }
        else if (localHotKeyCmd2.name().startsWith("$$+")) {
          localObject = new Item();
          f += 32.0F;
          ((Item)localObject).y = f;
          ((Item)localObject).label = this.fixedClient.addLabel(new ItemLabel(this.fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(localHotKeyCmd2.name().substring(3)), null));
          ((Item)localObject).label.color = -16632394;
          localArrayList.add(localObject);
        }
        else {
          if (bool) {
            ItemJoy localItemJoy = new ItemJoy();
            localObject = localItemJoy;
          } else {
            localObject = new Item();
          }
          f += 32.0F;
          ((Item)localObject).y = f;
          ((Item)localObject).cmd = localHotKeyCmd2;
          ((Item)localObject).env = localHotKeyEnv;
          ((Item)localObject).label = this.fixedClient.addLabel(new ItemLabel(this.fixedClient, 0.0F, 0.0F, 1.0F, 1.0F, resName(localHotKeyCmd2.name()), null));
          ((Item)localObject).control = ((ItemControl)this.fixedClient.addControl(new ItemControl(this.fixedClient, localHotKeyCmd2.name(), null)));
          localArrayList.add(localObject);
          this.mapItems.put(localHotKeyCmd2.name(), localObject);
        }
      }
      this._sortMap.clear();
    }

    this.items = new Item[localArrayList.size()];
    for (int j = 0; j < localArrayList.size(); j++) {
      this.items[j] = ((Item)localArrayList.get(j));
      this.items[j].indx = j;
      if (this.items[j].control != null)
        this.items[j].control.indx = j;
    }
    localArrayList.clear();
    f += 64.0F;
    this.itemsDY_1024 = f;
  }

  private void setPosItems(float paramFloat)
  {
    for (int i = 0; i < this.items.length; i++) {
      Item localItem = this.items[i];
      if (localItem.control == null) {
        localItem.label.set1024PosSize(16.0F, localItem.y, paramFloat - 16.0F - 16.0F, 32.0F);
      } else {
        localItem.label.set1024PosSize(48.0F, localItem.y, paramFloat - 48.0F - 320.0F - 32.0F, 32.0F);
        localItem.control.set1024PosSize(paramFloat - 320.0F - 48.0F, localItem.y, 352.0F, 32.0F);
      }
    }
  }

  public GUIControls(GWindowRoot paramGWindowRoot)
  {
    super(20);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("ctrl.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bBack = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDefault = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.scrollClient = ((ScrollClient)this.dialogClient.create(new ScrollClient()));

    createItems();
    this.dialogClient.setPosSize();

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

      if (paramGWindow == GUIControls.this.bBack) {
        World.cur().userCfg.saveConf();
        Main.stateStack().pop();
        return true;
      }if (paramGWindow == GUIControls.this.bDefault) {
        IniFile localIniFile = new IniFile("users/default.ini", 0);
        String[] arrayOfString = UserCfg.nameHotKeyEnvs;
        for (int i = 0; i < arrayOfString.length; i++) {
          HotKeyEnv.env(arrayOfString[i]).all().clear();
          HotKeyEnv.fromIni(arrayOfString[i], localIniFile, "HotKey " + arrayOfString[i]);
        }
        GUIControls.this.fillItems();
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(962.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(656.0F), x1024(224.0F), y1024(48.0F), 0, GUIControls.this.i18n("ctrl.Apply"));
      draw(x1024(400.0F), y1024(656.0F), x1024(352.0F), y1024(48.0F), 0, GUIControls.this.i18n("ctrl.Reset"));
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIControls.this.scrollClient.set1024PosSize(32.0F, 32.0F, 962.0F, 562.0F);
      GUIControls.this.fixedClient.setSize(GUIControls.this.fixedClient.getMinSize().dx, GUIControls.this.fixedClient.getMinSize().dy);
      GUIControls.this.setPosItems((GUIControls.this.scrollClient.win.dx - GUIControls.this.scrollClient.vScroll.win.dx) * 1024.0F / this.root.win.dx);
      GUIControls.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));
      GUIControls.this.bDefault.setPosC(x1024(360.0F), y1024(680.0F));
    }
  }

  public class FixedClient extends GWindowDialogClient
  {
    public FixedClient()
    {
    }

    public void render()
    {
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if (paramBoolean)
        setKeyFocus(); 
    }

    public boolean doNotify(int paramInt1, int paramInt2) {
      if (paramInt1 == 17)
        return super.doNotify(paramInt1, paramInt2);
      return false;
    }
    public GSize getMinSize(GSize paramGSize) {
      paramGSize.dx = (GUIControls.this.scrollClient.win.dx - GUIControls.this.scrollClient.vScroll.win.dx - 1.0F);
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      paramGSize.dy = y1024(GUIControls.this.itemsDY_1024);
      return paramGSize;
    }
  }

  public class ScrollClient extends GWindowScrollingDialogClient
  {
    GRegion clipReg = new GRegion();

    public ScrollClient() {  } 
    public void created() { this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient = (GUIControls.this.fixedClient = (GUIControls.FixedClient)create(new GUIControls.FixedClient(GUIControls.this)));
      this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient.bNotify = true;
      this.bNotify = true; }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void resized() {
      super.resized();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      if (this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible()) {
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setSize(lookAndFeel().getVScrollBarW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
      this.clipReg.set(0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.R.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.B.dy);
    }
    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
    public void doChildrensRender(boolean paramBoolean) {
      pushClipRegion(this.clipReg, true, 0.0F);
      super.doChildrensRender(paramBoolean);
      popClip();
    }
  }

  public class ItemControl extends GWindowLabel
  {
    public int indx;

    public void keyFocusExit()
    {
      if (GUIControls.this.messageBox == null)
      {
        int tmp27_26 = 0; GUIControls.this.curKey[1] = tmp27_26; GUIControls.this.curKey[0] = tmp27_26;
      }
    }

    private boolean findItem(boolean paramBoolean) {
      int i = 0;
      GUIControls.Item localItem1 = GUIControls.this.items[this.indx];
      for (int j = 0; j < GUIControls.this.items.length; j++) {
        GUIControls.Item localItem2 = GUIControls.this.items[j];
        if ((localItem2.control == null) || 
          (localItem2.cmd.hotKeyCmdEnv().name().equals(GUIControls.this.mapEnvLink.get(localItem1.cmd.hotKeyCmdEnv().name()))))
          continue;
        if ((localItem2.key[0] == GUIControls.this.keySum) || (localItem2.key[0] == GUIControls.this.keySum2)) {
          GUIControls.this.iFinded = j;
          if (!paramBoolean)
            return true;
          i = 1;
          localItem2.key[0] = localItem2.key[1];
          localItem2.key[1] = 0; } else {
          if ((localItem2.key[1] != GUIControls.this.keySum) && (localItem2.key[1] != GUIControls.this.keySum2)) continue;
          GUIControls.this.iFinded = j;
          if (!paramBoolean)
            return true;
          i = 1;
          localItem2.key[1] = 0;
        }

        if (localItem2.env.all() != null) {
          localItem2.env.all().remove(GUIControls.this.keySum);
          localItem2.env.all().remove(GUIControls.this.keySum2);
        }
        localItem2.control.fillCaption();
      }
      return i;
    }

    private void fillItem(boolean paramBoolean) {
      if (paramBoolean)
        findItem(true);
      GUIControls.Item localItem = GUIControls.this.items[this.indx];
      localItem.env.add(GUIControls.this.curKey[0], GUIControls.this.curKey[1], localItem.cmd.name());
      if (localItem.key[0] != 0) {
        if (localItem.key[1] != 0) {
          localItem.env.all().remove(localItem.key[1]);
          localItem.env.all().remove((localItem.key[1] & 0xFFFF) << 16 | (localItem.key[1] & 0xFFFF0000) >>> 16);
        }
        localItem.key[1] = localItem.key[0];
      }
      localItem.key[0] = GUIControls.this.keySum;
      int tmp168_167 = 0; GUIControls.this.curKey[1] = tmp168_167; GUIControls.this.curKey[0] = tmp168_167;
      fillCaption();
      this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.setKeyFocus();
    }

    private void requestItem() {
      GUIControls.Item localItem = GUIControls.this.items[GUIControls.this.iFinded];
      String str = localItem.label.cap.caption;
      GUIControls.this.messageBox = new GUIControls.1(this, GUIControls.this.client, 24.0F, true, I18N.gui("ctrl.Warning"), I18N.gui("ctrl.ReplaceCommand0") + str + I18N.gui("ctrl.ReplaceCommand1"), 1, 0.0F);
    }

    protected void doKey(int paramInt, boolean paramBoolean)
    {
      doKey(paramInt, paramBoolean, false, false);
    }

    protected void doKey(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
      if (paramBoolean1) {
        if (GUIControls.this.curKey[0] == 0) GUIControls.this.curKey[0] = paramInt;
        else if (GUIControls.this.curKey[1] == 0) GUIControls.this.curKey[1] = paramInt; 
      }
      else {
        if (GUIControls.this.messageBox != null) return;
        if ((GUIControls.this.curKey[0] == paramInt) || (GUIControls.this.curKey[1] == paramInt)) {
          if (GUIControls.this.curKey[0] > GUIControls.this.curKey[1]) {
            int i = GUIControls.this.curKey[0];
            GUIControls.this.curKey[0] = GUIControls.this.curKey[1];
            GUIControls.this.curKey[1] = i;
          }
          GUIControls.this.keySum = ((GUIControls.this.curKey[0] & 0xFFFF) << 16 | GUIControls.this.curKey[1] & 0xFFFF);
          GUIControls.this.keySum2 = ((GUIControls.this.curKey[1] & 0xFFFF) << 16 | GUIControls.this.curKey[0] & 0xFFFF);
          GUIControls.Item localItem = GUIControls.this.items[this.indx];
          int j = 0;
          if (!paramBoolean2) {
            if ((localItem.key[0] == GUIControls.this.keySum) || (localItem.key[0] == GUIControls.this.keySum2)) {
              localItem.env.all().remove(localItem.key[1]);
              localItem.env.all().remove((localItem.key[1] & 0xFFFF) << 16 | (localItem.key[1] & 0xFFFF0000) >>> 16);
              localItem.key[1] = 0;
              j = 1;
            } else if ((localItem.key[1] == GUIControls.this.keySum) || (localItem.key[1] == GUIControls.this.keySum2)) {
              localItem.env.all().remove(localItem.key[0]);
              localItem.env.all().remove((localItem.key[0] & 0xFFFF) << 16 | (localItem.key[0] & 0xFFFF0000) >>> 16);
              localItem.key[0] = localItem.key[1];
              localItem.key[1] = 0;
              j = 1;
            }
          }
          if (j == 0) {
            boolean bool = findItem(false);

            if (!paramBoolean2) {
              if (bool) requestItem(); else
                fillItem(false);
              return;
            }

            if (bool)
              findItem(true);
            ((GUIControls.ItemJoy)localItem).bMinus = paramBoolean3;
            if (paramBoolean3)
              localItem.env.add(GUIControls.this.curKey[0], GUIControls.this.curKey[1], "-" + localItem.cmd.name());
            else {
              localItem.env.add(GUIControls.this.curKey[0], GUIControls.this.curKey[1], localItem.cmd.name());
            }

            if (localItem.key[0] != 0) {
              localItem.env.all().remove(localItem.key[0]);
              localItem.env.all().remove((localItem.key[0] & 0xFFFF) << 16 | (localItem.key[0] & 0xFFFF0000) >>> 16);
            }
            localItem.key[0] = GUIControls.this.keySum;
          }
          int tmp714_713 = 0; GUIControls.this.curKey[1] = tmp714_713; GUIControls.this.curKey[0] = tmp714_713;
          fillCaption();
          this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.setKeyFocus();
        }
      }
    }

    public void keyboardKey(int paramInt, boolean paramBoolean) {
      super.keyboardKey(paramInt, paramBoolean);
      if (paramInt == 27) {
        this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.setKeyFocus();
        return;
      }
      if ((isKeyFocus()) && (!(GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null))
        doKey(paramInt, paramBoolean); 
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((isKeyFocus()) && (!(GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null))
        doKey(paramInt + 524, paramBoolean); 
    }

    public void joyButton(int paramInt1, int paramInt2, boolean paramBoolean) {
      if ((isKeyFocus()) && (!(GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null)) {
        if (paramBoolean)
        {
          int tmp56_55 = 0; GUIControls.this.curKey[1] = tmp56_55; GUIControls.this.curKey[0] = tmp56_55;
          doKey(paramInt1, paramBoolean);
        }
        doKey(paramInt2, paramBoolean);
        if (!paramBoolean)
          doKey(paramInt1, paramBoolean); 
      }
    }

    public void joyPov(int paramInt1, int paramInt2) {
      if ((isKeyFocus()) && (!(GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null))
      {
        if (paramInt2 != 571)
        {
          int tmp59_58 = 0; GUIControls.this.curKey[1] = tmp59_58; GUIControls.this.curKey[0] = tmp59_58;
          doKey(paramInt1, true);
          doKey(paramInt2, true);
          doKey(paramInt2, false);
          doKey(paramInt1, false);
        }
      }
    }

    public void joyMove(int paramInt1, int paramInt2, int paramInt3) {
      int i = paramInt1 | paramInt2 << 16;
      int j = GUIControls.this.hashJoyMove.get(i);
      if (j == -1) {
        GUIControls.this.hashJoyMove.put(i, paramInt3);
        return;
      }
      if (Math.abs(Joy.normal(paramInt3 - j)) < 0.2D) return;
      GUIControls.this.hashJoyMove.put(i, paramInt3);
      if ((isKeyFocus()) && ((GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null)) {
        boolean bool = paramInt3 - j < 0;
        int tmp140_139 = 0; GUIControls.this.curKey[1] = tmp140_139; GUIControls.this.curKey[0] = tmp140_139;
        doKey(paramInt1, true);
        doKey(paramInt2, true);
        doKey(paramInt2, false, true, bool);
        doKey(paramInt1, false, true, bool);
      }
    }

    public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
      super.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
      if ((paramFloat3 != 0.0F) && (isKeyFocus()) && ((GUIControls.this.items[this.indx] instanceof GUIControls.ItemJoy)) && (GUIControls.this.messageBox == null)) {
        doKey(530, true);
        doKey(530, false, true, paramFloat3 < 0.0F);
      }
    }

    public void keyFocusEnter() {
      super.keyFocusEnter();
      GUIControls.this.hashJoyMove.clear();
      for (int i = 0; i < GUIControls.this.povState.length; i++)
        GUIControls.this.povState[i] = 0;
    }

    public void fillCaption() {
      GUIControls.Item localItem = GUIControls.this.items[this.indx];
      String str;
      if ((localItem.key[0] == 0) && (localItem.key[1] == 0))
        str = "";
      else if ((localItem.key[0] != 0) && (localItem.key[1] != 0)) {
        str = " " + keyToStr(localItem.key[0]) + ", " + keyToStr(localItem.key[1]);
      }
      else if (((localItem instanceof GUIControls.ItemJoy)) && (((GUIControls.ItemJoy)localItem).bMinus))
        str = " -" + keyToStr(localItem.key[0]);
      else {
        str = " " + keyToStr(localItem.key[0]);
      }

      this.cap = new GCaption(str);
    }
    private String keyToStr(int paramInt) {
      if (paramInt == 0) return "";
      if ((paramInt & 0xFFFF0000) == 0) {
        return GUIControls.this.resName(VK.getKeyText(paramInt));
      }
      return GUIControls.this.resName(VK.getKeyText(paramInt >> 16 & 0xFFFF)) + " " + GUIControls.this.resName(VK.getKeyText(paramInt & 0xFFFF));
    }

    public boolean isMousePassThrough(float paramFloat1, float paramFloat2) {
      return false;
    }
    public boolean notify(int paramInt1, int paramInt2) {
      if ((paramInt1 == 17) && (isActivated()))
        return false;
      return super.notify(paramInt1, paramInt2);
    }
    public void render() {
      if (isActivated()) {
        setCanvasColorWHITE();
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
      }
      super.render();
    }
    public ItemControl(GWindow paramString1, String paramString2, String arg4) {
      super(0.0F, 0.0F, 1.0F, 1.0F, paramString2, str);
    }
  }

  public class ItemLabel extends GWindowLabel
  {
    public boolean isMousePassThrough(float paramFloat1, float paramFloat2)
    {
      return true;
    }
    public ItemLabel(GWindow paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramString1, String paramString2, String arg8) {
      super(paramFloat2, paramFloat3, paramFloat4, paramString1, paramString2, str);
    }
  }

  public class ItemJoy extends GUIControls.Item
  {
    boolean bMinus;

    public ItemJoy()
    {
      super();
    }
  }

  public class Item
  {
    GWindowDialogControl label;
    GUIControls.ItemControl control;
    HotKeyCmd cmd;
    HotKeyEnv env;
    float y;
    int indx;
    int[] key = new int[2];

    public Item()
    {
    }
  }
}