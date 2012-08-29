package com.maddox.gwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GWindowTree extends GWindow
  implements GTreeModelListener
{
  public boolean bMonoHeightRows = true;

  public boolean bDrawTreeLines = true;

  public boolean bDrawIcons = true;

  public boolean bEditExitOnFocusExit = true;

  public boolean bEditExitOnEnterPress = true;

  public boolean bEnableHScrollBar = true;

  public boolean bEnableVScrollBar = true;
  protected boolean bRootVisible = true;

  public int font = 0;

  public float borderSpace = 2.0F;
  public GTreeModel model;
  public List rows = new ArrayList();
  public HashMap expandedState = new HashMap();
  public GTreePath selectPath;
  public int selectRow = -1;
  public GWindowCellEdit editor;
  public Client wClient;
  public GWindowVScrollBar vSB;
  public GWindowHScrollBar hSB;
  public GWindowButton button;
  public float _sizePathDx;
  public float _sizePathDy;
  private GRegion _clientRegion = new GRegion();

  public float getBorderSpace()
  {
    return this.borderSpace;
  }

  public float getTabStep() {
    return Math.round(this.root.textFonts[this.font].height + 3.0F) & 0xFFFFFFFC;
  }

  public float getMonoHeightRows() {
    return Math.round(this.root.textFonts[this.font].height + 3.0F) & 0xFFFFFFFC;
  }

  public void setRootVisible(boolean paramBoolean) {
    if (this.bRootVisible == paramBoolean) return;
    this.bRootVisible = paramBoolean;
    setModel(this.model);
  }
  public boolean isRootVisible() { return this.bRootVisible; }

  public boolean isExpanded(GTreePath paramGTreePath) {
    return this.expandedState.containsKey(paramGTreePath);
  }
  public boolean isVisible(GTreePath paramGTreePath) {
    if (paramGTreePath == null) return false;
    while (paramGTreePath != null) {
      if (!isExpanded(paramGTreePath))
        return false;
      paramGTreePath = paramGTreePath.getParentPath();
    }
    return true;
  }

  public int setVisible(GTreePath paramGTreePath)
  {
    return setVisible(paramGTreePath, true);
  }

  public int setVisible(GTreePath paramGTreePath, boolean paramBoolean) {
    if (paramGTreePath == null) return -1;
    endEditing(true);
    if (this.selectRow >= 0) {
      this.selectPath = null;
      this.selectRow = -1;
    }
    for (int i = 0; i < this.rows.size(); i++) {
      GTreePath localGTreePath = (GTreePath)this.rows.get(i);
      if (localGTreePath.equals(paramGTreePath)) {
        computeSize();
        if (paramBoolean)
          notify(2, 0);
        return i;
      }
      if ((localGTreePath.isDescendant(paramGTreePath)) && (!isExpanded(localGTreePath))) {
        this.expandedState.put(localGTreePath, null);
        addRows(i + 1, localGTreePath);
      }
    }
    computeSize();
    if (paramBoolean)
      notify(2, 0);
    return -1;
  }
  public boolean isSelect(GTreePath paramGTreePath) {
    if (this.selectPath == null) return false;
    return this.selectPath.equals(paramGTreePath);
  }
  public boolean isSelect(int paramInt) {
    return paramInt == this.selectRow;
  }
  public void setSelect(GTreePath paramGTreePath) {
    setSelect(paramGTreePath, true);
  }
  public void setSelect(GTreePath paramGTreePath, boolean paramBoolean) {
    if (paramGTreePath != null) {
      int i = setVisible(paramGTreePath, false);
      if (i >= 0)
        setSelect(i, paramBoolean);
    }
  }

  public void setSelect(int paramInt) {
    setSelect(paramInt, true);
  }
  public void setSelect(int paramInt, boolean paramBoolean) {
    if (paramInt < 0) return;
    GTreePath localGTreePath1 = (GTreePath)this.rows.get(paramInt);
    if (localGTreePath1.equals(this.selectPath)) return;
    endEditing(false);
    this.selectPath = localGTreePath1;
    this.selectRow = paramInt;
    float f1;
    if (this.vSB.isVisible()) {
      f1 = 0.0F;
      for (int i = 0; i < this.selectRow; i++) {
        GTreePath localGTreePath2 = (GTreePath)this.rows.get(i);
        f1 += computeHeight(localGTreePath2);
      }
      float f2 = f1 + computeHeight(this.selectPath);
      if (f1 < this.vSB.pos()) {
        getClientRegion(this._clientRegion, 0.0F);
        this.vSB.setPos(f1 - getBorderSpace() + this._clientRegion.y, true);
      } else if (f2 > this.vSB.pos() + this.vSB.posVisible) {
        getClientRegion(this._clientRegion, 0.0F);
        this.vSB.setPos(f2 - this.vSB.posVisible + getBorderSpace() + this._clientRegion.y, true);
      }
    }
    if (this.hSB.isVisible()) {
      f1 = getBorderSpace();
      f1 += (localGTreePath1.getPathCount() - this.model.getRoot().getPathCount()) * getTabStep();
      if ((f1 < this.hSB.pos()) || (f1 > this.hSB.pos() + this.hSB.posVisible)) {
        getClientRegion(this._clientRegion, 0.0F);
        this.hSB.setPos(f1 - getBorderSpace() + this._clientRegion.x, true);
      }
    }
    if (paramBoolean)
      notify(2, 0);
  }

  public void setModel(GTreeModel paramGTreeModel) {
    int i = 0;
    if (this.model != null) {
      this.rows.clear();
      this.expandedState.clear();
      endEditing(true);
      this.selectPath = null;
      this.selectRow = -1;
      i = 1;
      this.model.removeListener(this);
    }
    this.model = paramGTreeModel;
    if (this.model != null)
    {
      this.expandedState.put(this.model.getRoot(), null);

      onModelChanged();
      this.model.addListener(this);
    }
    if (i != 0)
      notify(2, 0);
  }

  public void treeModelChanged(GTreeModel paramGTreeModel) {
    if (this.model != paramGTreeModel) return;
    onModelChanged();
  }

  public void onModelChanged() {
    int i = this.selectRow >= 0 ? 1 : 0;
    this.rows.clear();
    int j = 0;
    GTreePath localGTreePath = this.model.getRoot();
    if (localGTreePath == null) {
      computeSize();
      return;
    }
    this.selectRow = -1;
    if (this.bRootVisible) {
      if (localGTreePath.equals(this.selectPath)) this.selectRow = j;
      this.rows.add(j++, localGTreePath);
    }
    addRows(j, localGTreePath);
    if (this.selectRow < 0) {
      endEditing(true);
      this.selectPath = null;
    }
    computeSize();
    if (i != 0)
      notify(2, 0);
  }

  protected int addRows(int paramInt, GTreePath paramGTreePath) {
    if (!isExpanded(paramGTreePath))
      return paramInt;
    if (this.model.isLeaf(paramGTreePath))
      return paramInt;
    int i = this.model.getChildCount(paramGTreePath);
    for (int j = 0; j < i; j++) {
      Object localObject = this.model.getChild(paramGTreePath, j);
      if (localObject != null) {
        GTreePath localGTreePath = paramGTreePath.pathByAddingChild(localObject);
        if (localGTreePath.equals(this.selectPath)) this.selectRow = paramInt;
        this.rows.add(paramInt++, localGTreePath);
        paramInt = addRows(paramInt, localGTreePath);
      }
    }
    return paramInt;
  }

  protected void removeRows(int paramInt, GTreePath paramGTreePath) {
    int i = 0;
    while (paramInt < this.rows.size()) {
      GTreePath localGTreePath = (GTreePath)this.rows.get(paramInt);
      if (!paramGTreePath.isDescendant(localGTreePath)) break;
      if (localGTreePath.equals(this.selectPath)) {
        endEditing(true);
        this.selectPath = null;
        this.selectRow = -1;
        i = 1;
      }
      this.rows.remove(paramInt);
    }

    if (i != 0)
      notify(2, 0);
  }

  public void computeSize() {
    int i = this.rows.size();
    if (i == 0) {
      this.wClient.setSize(getTabStep() + 2.0F * this.borderSpace, getMonoHeightRows() + 2.0F * this.borderSpace);
      resized();
      return;
    }
    float f1 = 0.0F;
    float f2 = 0.0F;
    GTreePath localGTreePath = this.model.getRoot();
    int j = localGTreePath.getPathCount();
    for (int k = 0; k < i; k++) {
      localGTreePath = (GTreePath)this.rows.get(k);
      computeSize(localGTreePath);
      if (this.bDrawIcons)
        this._sizePathDx += getTabStep();
      this._sizePathDx += (localGTreePath.getPathCount() - j) * getTabStep();
      if (f1 < this._sizePathDx)
        f1 = this._sizePathDx;
      f2 += this._sizePathDy;
    }
    this.wClient.setSize(f1 + 2.0F * this.borderSpace, f2 + 2.0F * this.borderSpace);
    resized();
  }

  public void computeSize(GTreePath paramGTreePath)
  {
    int i = 1;
    String str = null;
    boolean bool1 = isExpanded(paramGTreePath);
    boolean bool2 = isSelect(paramGTreePath);
    if (this.bMonoHeightRows) {
      this._sizePathDy = getMonoHeightRows();
    } else {
      this._sizePathDy = this.model.getRenderHeight(paramGTreePath, bool2, bool1);
      if (this._sizePathDy <= 0.0F) {
        str = this.model.getString(paramGTreePath, bool2, bool1);
        if (str != null) {
          this._sizePathDy = this.root.textFonts[this.font].height;
        } else {
          i = 0;
          this._sizePathDy = getMonoHeightRows();
        }
      }
    }
    this._sizePathDx = this.model.getRenderWidth(paramGTreePath, bool2, bool1);
    if (this._sizePathDx <= 0.0F) {
      if (i != 0)
        str = this.model.getString(paramGTreePath, bool2, bool1);
      if (str != null)
        this._sizePathDx = this.root.textFonts[this.font].size(str).dx;
      else
        this._sizePathDx = 0.0F;
    }
  }

  public int findRow(float paramFloat) {
    int i = this.rows.size();
    if ((i == 0) || (paramFloat < 0.0F))
      return -1;
    if (this.bMonoHeightRows) {
      int j = (int)(paramFloat / getMonoHeightRows());
      if (j >= i) j = -1;
      return j;
    }
    float f = 0.0F;
    for (int k = 0; k < i; k++) {
      GTreePath localGTreePath = (GTreePath)this.rows.get(k);
      f += computeHeight(localGTreePath);
      if (paramFloat < f)
        return k;
    }
    return -1;
  }
  public float computeHeight(GTreePath paramGTreePath) {
    if (this.bMonoHeightRows)
      return getMonoHeightRows();
    boolean bool1 = isExpanded(paramGTreePath);
    boolean bool2 = isSelect(paramGTreePath);
    float f = this.model.getRenderHeight(paramGTreePath, bool2, bool1);
    if (f > 0.0F)
      return f;
    String str = this.model.getString(paramGTreePath, bool2, bool1);
    if (str != null) {
      return this.root.textFonts[this.font].height;
    }
    return getMonoHeightRows();
  }

  public void endEditing(boolean paramBoolean) {
    if (this.editor != null) {
      if ((this.selectPath != null) && (!paramBoolean)) {
        Object localObject = this.editor.getCellEditValue();
        this.model.setValueAt(localObject, this.selectPath);
      }
      ((GWindow)this.editor).hideWindow();
      this.editor = null;
    }
  }

  protected void beginEditor() {
    if (this.editor != null) return;
    if (this.selectPath == null) return;
    Object localObject = this.model.getValueAt(this.selectPath);
    if (localObject == null) return;
    this.editor = this.model.getEdit(this.selectPath, isExpanded(this.selectPath));
    if (this.editor == null) {
      this.editor = ((GWindowCellEdit)this.wClient.create(new GWindowEditControl()));
    }
    this.editor.setCellEditValue(localObject);
    float f = this.wClient.win.dx;
    f -= (this.selectPath.getPathCount() - this.model.getRoot().getPathCount()) * getTabStep();
    if (this.bDrawIcons)
      f -= getTabStep();
    f -= 2.0F * getBorderSpace();
    ((GWindow)this.editor).setSize(f, computeHeight(this.selectPath));
    setPosEditor();
    ((GWindow)this.editor).activateWindow();
  }
  public void setPosEditor() {
    if (this.editor == null) return;
    float f1 = getBorderSpace();
    for (int i = 0; i < this.selectRow; i++)
      f1 += computeHeight((GTreePath)this.rows.get(i));
    float f2 = getBorderSpace();
    f2 += (this.selectPath.getPathCount() - this.model.getRoot().getPathCount()) * getTabStep();
    if (this.bDrawIcons)
      f2 += getTabStep();
    ((GWindow)this.editor).setPos(f2, f1);
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    if (paramGWindow == this.hSB) {
      if (paramInt1 == 2) {
        resized();
        return true;
      }
    } else if ((paramGWindow == this.vSB) && 
      (paramInt1 == 2)) {
      resized();
      return true;
    }

    return false;
  }

  public void resized() {
    getClientRegion(this._clientRegion, 0.0F);
    int i = 0;
    int j = 0;
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int k = 0; k < 3; k++) {
      f1 = this._clientRegion.dy;
      if (j != 0) f1 -= lookAndFeel().getHScrollBarH();
      f2 = this._clientRegion.dx;
      if (i != 0) f2 -= lookAndFeel().getVScrollBarW();
      if (k == 2) break;
      if (this.bEnableHScrollBar)
        j = this.wClient.win.dx > f2 ? 1 : 0;
      if (this.bEnableVScrollBar)
        i = this.wClient.win.dy > f1 ? 1 : 0;
    }
    if (i != 0) {
      this.vSB.setRange(0.0F, this.wClient.win.dy, f1, getMonoHeightRows(), this.vSB.pos);
      this.vSB.setSize(lookAndFeel().getVScrollBarW(), f1);
      this.vSB.setPos(this._clientRegion.x + f2, this._clientRegion.y);
      this.vSB.showWindow();
    } else {
      this.vSB.setPos(0.0F, false);
      this.vSB.hideWindow();
    }
    if (j != 0) {
      this.hSB.setRange(0.0F, this.wClient.win.dx, f2, getTabStep(), this.hSB.pos);
      this.hSB.setSize(f2, lookAndFeel().getHScrollBarH());
      this.hSB.setPos(this._clientRegion.x, this._clientRegion.y + f1);
      this.hSB.showWindow();
    } else {
      this.hSB.setPos(0.0F, false);
      this.hSB.hideWindow();
    }
    if ((j != 0) && (i != 0)) {
      this.button.setPos(f2 + this._clientRegion.x, this._clientRegion.y + f1);
      this.button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
      this.button.showWindow();
    } else {
      this.button.hideWindow();
    }
    this.wClient.setPos(this._clientRegion.x - this.hSB.pos, this._clientRegion.y - this.vSB.pos);
    setPosEditor();
  }

  public void afterCreated()
  {
    this.button = new GWindowButton(this);
    this.button.bAcceptsKeyFocus = false;
    this.button.bAlwaysOnTop = true;
    this.button.bDrawOnlyUP = true;
    this.button.bDrawActive = false;
    this.button.hideWindow();
    this.hSB = new GWindowHScrollBar(this);
    this.hSB.bAlwaysOnTop = true;
    this.hSB.hideWindow();
    this.vSB = new GWindowVScrollBar(this);
    this.vSB.bAlwaysOnTop = true;
    this.vSB.hideWindow();
    this.wClient = new Client(this);
    resized();
  }

  public void renderClient() {
    lookAndFeel().renderClient(this);
  }

  public void render() {
    lookAndFeel().render(this);
  }

  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }

  public GWindowTree(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowTree(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }

  public class Client extends GWindow
  {
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if ((GWindowTree.this.editor != null) && (paramGWindow == GWindowTree.this.editor) && (
        ((GWindowTree.this.bEditExitOnFocusExit) && (paramInt1 == 16)) || ((GWindowTree.this.bEditExitOnEnterPress) && (paramInt1 == 11) && (paramInt2 == 10))))
      {
        GWindowTree.this.endEditing(false);
        return true;
      }

      return false;
    }

    public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
      super.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
      if ((GWindowTree.this.editor == null) && (GWindowTree.this.vSB != null) && (GWindowTree.this.vSB.isVisible()))
        GWindowTree.this.vSB.scrollDz(paramFloat3);
    }

    public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
      super.mouseClick(paramInt, paramFloat1, paramFloat2);
      int i = GWindowTree.this.findRow(paramFloat2);
      if (i < 0) return;
      GTreePath localGTreePath = (GTreePath)GWindowTree.this.rows.get(i);
      int j = GWindowTree.this.model.getRoot().getPathCount();
      int k = localGTreePath.getPathCount();
      float f = (k - j) * GWindowTree.this.getTabStep() + GWindowTree.this.getBorderSpace();
      if (paramFloat1 < f) {
        if (paramFloat1 < f - GWindowTree.this.getTabStep()) return;
        if (GWindowTree.this.model.isLeaf(localGTreePath)) return; 
      }
      else {
        GWindowTree.this.computeSize(localGTreePath);
        if (GWindowTree.this.bDrawIcons)
          f += GWindowTree.this.getTabStep();
        f += GWindowTree.this._sizePathDx;
        if (paramFloat1 > f)
          return;
        if (!localGTreePath.equals(GWindowTree.this.selectPath)) {
          GWindowTree.this.setSelect(i);
          return;
        }
        if (GWindowTree.this.model.isEditable(localGTreePath)) {
          GWindowTree.this.beginEditor();
          return;
        }
      }
      if (GWindowTree.this.isExpanded(localGTreePath)) {
        GWindowTree.this.expandedState.remove(localGTreePath);
        GWindowTree.this.removeRows(i + 1, localGTreePath);
      } else {
        GWindowTree.this.expandedState.put(localGTreePath, null);
        GWindowTree.this.addRows(i + 1, localGTreePath);
      }
      GWindowTree.this.setSelect(i);
      GWindowTree.this.computeSize();
    }

    public void keyboardKey(int paramInt, boolean paramBoolean) {
      super.keyboardKey(paramInt, paramBoolean);
      if ((GWindowTree.this.selectRow < 0) || (GWindowTree.this.editor != null))
        return;
      if (!paramBoolean) {
        if (paramInt == 10) {
          if (GWindowTree.this.model.isEditable(GWindowTree.this.selectPath)) {
            GWindowTree.this.beginEditor();
            return;
          }
          if (!GWindowTree.this.model.isLeaf(GWindowTree.this.selectPath)) {
            if (GWindowTree.this.isExpanded(GWindowTree.this.selectPath)) {
              GWindowTree.this.expandedState.remove(GWindowTree.this.selectPath);
              GWindowTree.this.removeRows(GWindowTree.this.selectRow + 1, GWindowTree.this.selectPath);
            } else {
              GWindowTree.this.expandedState.put(GWindowTree.this.selectPath, null);
              GWindowTree.this.addRows(GWindowTree.this.selectRow + 1, GWindowTree.this.selectPath);
            }
            GWindowTree.this.computeSize();
          }
        }
        return;
      }
      int i;
      switch (paramInt) {
      case 33:
        if (!GWindowTree.this.vSB.isVisible()) break;
        i = GWindowTree.this.selectRow - (int)(GWindowTree.this.vSB.posVisible / GWindowTree.this.getMonoHeightRows());
        if (i < 0)
          i = 0;
        GWindowTree.this.setSelect(i);
        break;
      case 36:
        if (GWindowTree.this.selectRow != 0)
          GWindowTree.this.setSelect(0); break;
      case 34:
        if (GWindowTree.this.vSB.isVisible()) {
          i = GWindowTree.this.selectRow + (int)(GWindowTree.this.vSB.posVisible / GWindowTree.this.getMonoHeightRows());
          if (i > GWindowTree.this.rows.size() - 1)
            i = GWindowTree.this.rows.size() - 1;
          GWindowTree.this.setSelect(i);
        }break;
      case 35:
        if (GWindowTree.this.selectRow != GWindowTree.this.rows.size() - 1)
          GWindowTree.this.setSelect(GWindowTree.this.rows.size() - 1); break;
      case 38:
        if (GWindowTree.this.selectRow > 0)
          GWindowTree.this.setSelect(GWindowTree.this.selectRow - 1); break;
      case 40:
        if (GWindowTree.this.selectRow < GWindowTree.this.rows.size() - 1)
          GWindowTree.this.setSelect(GWindowTree.this.selectRow + 1); break;
      case 37:
        if ((!GWindowTree.this.model.isLeaf(GWindowTree.this.selectPath)) && 
          (GWindowTree.this.isExpanded(GWindowTree.this.selectPath))) {
          GWindowTree.this.expandedState.remove(GWindowTree.this.selectPath);
          GWindowTree.this.removeRows(GWindowTree.this.selectRow + 1, GWindowTree.this.selectPath);
          GWindowTree.this.computeSize();
        }
        else if (GWindowTree.this.selectRow > 0) {
          GWindowTree.this.setSelect(GWindowTree.this.selectRow - 1); } break;
      case 39:
        if ((!GWindowTree.this.model.isLeaf(GWindowTree.this.selectPath)) && 
          (!GWindowTree.this.isExpanded(GWindowTree.this.selectPath))) {
          GWindowTree.this.expandedState.put(GWindowTree.this.selectPath, null);
          GWindowTree.this.addRows(GWindowTree.this.selectRow + 1, GWindowTree.this.selectPath);
          GWindowTree.this.computeSize();
        }
        else if (GWindowTree.this.selectRow < GWindowTree.this.rows.size() - 1) {
          GWindowTree.this.setSelect(GWindowTree.this.selectRow + 1); } break;
      }
    }

    public void render()
    {
      GWindowTree.this.renderClient();
    }

    public Client(GWindow arg2) {
      super();
    }
  }
}