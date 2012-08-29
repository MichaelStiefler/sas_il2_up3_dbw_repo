package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowComboControl extends GWindowDialogControl
  implements GWindowCellEdit
{
  public EditBox editBox;
  public Button button;
  public ListArea listArea;
  public GWindowVScrollBar scrollBar;
  public ArrayList list = new ArrayList();
  public boolean[] posEnable;
  public int listVisibleLines = 7;
  public boolean bFindIgnoreCase = true;

  public int iSelected = -1;

  public int listSelected = -1;
  public int listStartLine = 0;
  public int listCountLines = this.listVisibleLines;

  public void setEnable(boolean paramBoolean)
  {
    super.setEnable(paramBoolean);
    this.editBox.setEnable(paramBoolean);
    this.button.setEnable(paramBoolean);
  }

  public void setToolTip(String paramString) {
    super.setToolTip(paramString);
    this.editBox.setToolTip(paramString);
  }

  public void setEditDelayedNotify(boolean paramBoolean) {
    this.editBox.bDelayedNotify = paramBoolean;
  }

  public void setMaxLength(int paramInt) {
    this.editBox.maxLength = paramInt;
  }
  public void setEditable(boolean paramBoolean) {
    this.editBox.setEditable(paramBoolean);
  }
  public void setEditTextColor(int paramInt) {
    this.editBox.color = paramInt;
  }
  public void setNumericOnly(boolean paramBoolean) {
    this.editBox.bNumericOnly = paramBoolean;
  }
  public void setNumericFloat(boolean paramBoolean) {
    this.editBox.bNumericFloat = paramBoolean;
  }
  public void setValue(String paramString) {
    this.editBox.setValue(paramString);
  }
  public void setValue(String paramString, boolean paramBoolean) {
    this.editBox.setValue(paramString, paramBoolean);
  }
  public String getValue() {
    return this.editBox.getValue();
  }
  public void clearValue() {
    this.editBox.clear();
  }
  public void clearValue(boolean paramBoolean) {
    this.editBox.clear(paramBoolean);
  }

  public void setCellEditValue(Object paramObject) {
    setValue(paramObject.toString(), false);
  }
  public Object getCellEditValue() {
    return getValue();
  }

  public int size() {
    return this.list.size();
  }
  public void add(String paramString) {
    hideList();
    this.list.add(paramString);
  }
  public void add(int paramInt, String paramString) {
    hideList();
    this.list.add(paramInt, paramString);
  }
  public void remove(int paramInt) {
    hideList();
    this.list.remove(paramInt);
  }
  public void clear() {
    hideList();
    clearValue();
    this.list.clear();
  }
  public void clear(boolean paramBoolean) {
    hideList();
    clearValue(paramBoolean);
    this.list.clear();
  }
  public String get(int paramInt) {
    return (String)this.list.get(paramInt);
  }
  public int getSelected() { return this.iSelected; } 
  public void setSelected(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean1)
      setValue(get(paramInt), false);
    if (this.iSelected == paramInt) return;
    this.iSelected = paramInt;
    updateList();
    if (paramBoolean2)
      notify(2, this.iSelected); 
  }

  public int findStartsWith(String paramString, boolean paramBoolean) {
    int i = paramString.length();
    int j = this.list.size();
    for (int k = 0; k < j; k++) {
      String str = (String)this.list.get(k);
      if ((str.length() >= i) && (str.regionMatches(paramBoolean, 0, paramString, 0, i)) && (this.posEnable != null) && (this.posEnable[k] != 0))
      {
        return k;
      }
    }
    return -1;
  }

  public void hideList() {
    if (this.listArea.isVisible())
      lAF().soundPlay("comboHide");
    this.listArea.hideWindow();
  }
  public void updateList() {
    if (!this.listArea.isVisible()) return;
    if (!this.scrollBar.isVisible()) return;
    if (this.iSelected < 0)
      this.scrollBar.setPos(0.0F, false);
    else
      this.scrollBar.setPos(this.iSelected, false);
    this.listSelected = this.iSelected;
    this.listStartLine = (int)this.scrollBar.pos();
  }
  public void showList() {
    if (!this.jdField_bEnable_of_type_Boolean) return;
    if (this.list.size() == 0) return;
    if (this.listArea.isVisible()) return;
    lookAndFeel().setupComboList(this);
    this.listArea.showWindow();
    lAF().soundPlay("comboShow");
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
    if (paramInt1 == 17) {
      if (this.scrollBar.isVisible())
        this.scrollBar.scrollDz(this.root.mouseRelMoveZ);
      return true;
    }
    if ((paramGWindow == this.editBox) && 
      (this.jdField_bEnable_of_type_Boolean) && (paramInt1 == 10)) {
      if (paramInt2 == 38) {
        editScroll(true);
        return true;
      }
      if (paramInt2 == 40) {
        editScroll(false);
        return true;
      }
    }

    return false;
  }

  public void editScroll(boolean paramBoolean) {
    if (!this.jdField_bEnable_of_type_Boolean) return;
    if (this.listArea.isVisible()) return;
    int i;
    int j;
    if (paramBoolean) {
      i = size();
      j = this.iSelected;
      while (i-- > 0) {
        j = (j - 1 + size()) % size();
        if ((this.posEnable == null) || (this.posEnable[j] != 0)) {
          setSelected(j, true, true);
          break;
        }
      }
    } else {
      i = size();
      j = this.iSelected;
      while (i-- > 0) {
        j = (j + 1) % size();
        if ((this.posEnable == null) || (this.posEnable[j] != 0)) {
          setSelected(j, true, true);
          break;
        }
      }
    }
  }

  public void render()
  {
    lookAndFeel().render(this);
  }

  public void afterCreated() {
    super.afterCreated();
    this.button = new Button(this);
    this.editBox = new EditBox(this);
    this.listArea = new ListArea(this);
    this.scrollBar = new ScrollBar(this.listArea);
    this.listArea.hideWindow();
  }
  public void resized() {
    if (this.jdField_metricWin_of_type_ComMaddoxGwindowGRegion != null)
      this.jdField_metricWin_of_type_ComMaddoxGwindowGRegion.dy = (lookAndFeel().getComboH() / lookAndFeel().metric());
    if (this.button != null) this.button.resized();
    if (this.editBox != null) this.editBox.resized();
    hideList();
  }

  public GWindowComboControl()
  {
  }

  public GWindowComboControl(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = paramGWindow.lookAndFeel().getComboH() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, f, true);
  }

  public class Button extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
      if (GWindowComboControl.this.listArea.isVisible()) GWindowComboControl.this.hideList(); else
        GWindowComboControl.this.showList(); 
    }

    public void resized() {
      lookAndFeel().setupComboButton(this);
    }
    public void created() {
      this.bAcceptsKeyFocus = false;
      this.bTransient = true;
      this.bAlwaysOnTop = true;
      lookAndFeel().setupComboButton(this);
    }
    public Button(GWindow arg2) { super();
    }
  }

  public class EditBox extends GWindowEditBox
  {
    public void mouseClick(int paramInt, float paramFloat1, float paramFloat2)
    {
      if (paramInt != 0) return;
      if (!GWindowComboControl.this.listArea.isVisible()) GWindowComboControl.this.showList(); else
        GWindowComboControl.this.hideList(); 
    }

    public boolean notify(int paramInt1, int paramInt2) {
      if ((paramInt1 == 17) && (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseRelMoveZ != 0.0F)) {
        GWindowComboControl.this.editScroll(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseRelMoveZ > 0.0F);
        return true;
      }
      String str;
      int i;
      if (paramInt1 == 2) {
        str = getValue();
        if (str.length() > 0) {
          i = GWindowComboControl.this.findStartsWith(str, GWindowComboControl.this.bFindIgnoreCase);
          if (i >= 0) {
            GWindowComboControl.this.setSelected(i, false, false);
            if (str.equals(GWindowComboControl.this.get(i)))
              return super.notify(paramInt1, i);
          }
        }
        return super.notify(paramInt1, -1);
      }if ((this.jdField_bDelayedNotify_of_type_Boolean) && (this.bCanEdit) && (GWindowComboControl.this.listArea.isVisible())) {
        str = getValue();
        if (str.length() > 0) {
          i = GWindowComboControl.this.findStartsWith(str, GWindowComboControl.this.bFindIgnoreCase);
          if (i >= 0)
            GWindowComboControl.this.setSelected(i, false, false);
        }
      }
      return super.notify(paramInt1, paramInt2);
    }
    public void created() {
      this.jdField_bDelayedNotify_of_type_Boolean = true;
      lookAndFeel().setupComboEditBox(this);
    }
    public void resized() {
      lookAndFeel().setupComboEditBox(this);
    }
    public EditBox(GWindow arg2) {
      this.align = 0;
      GWindow localGWindow;
      doNew(localGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }
  }

  public class ListArea extends GWindowDialogControl
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean)) return;
      int i = (int)(paramFloat2 / (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / GWindowComboControl.this.listCountLines)) + GWindowComboControl.this.listStartLine;
      if ((GWindowComboControl.this.posEnable != null) && (GWindowComboControl.this.posEnable[i] == 0)) return;
      if (isVisible())
        lAF().soundPlay("comboHide");
      hideWindow();
      GWindowComboControl.this.setSelected(i, true, true);
    }
    public void mouseMove(float paramFloat1, float paramFloat2) {
      super.mouseMove(paramFloat1, paramFloat2);
      int i = (int)(paramFloat2 / (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / GWindowComboControl.this.listCountLines)) + GWindowComboControl.this.listStartLine;
      if ((GWindowComboControl.this.posEnable != null) && (GWindowComboControl.this.posEnable[i] == 0)) return;
      GWindowComboControl.this.listSelected = i;
    }

    public void render() {
      lookAndFeel().renderComboList((GWindowComboControl)this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow);
    }
    public void msgMouseButton(boolean paramBoolean1, int paramInt, boolean paramBoolean2, float paramFloat1, float paramFloat2) {
      if (paramBoolean1) return;
      GWindow localGWindow1 = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.findWindowUnder(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y);
      GWindow localGWindow2 = localGWindow1.getParent(GWindowComboControl.class, false);
      if (localGWindow2 != this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow) {
        if (isVisible())
          lAF().soundPlay("comboHide");
        hideWindow();
      }
    }

    public ListArea(GWindow arg2) {
      this.bClip = false;
      this.bAlwaysOnTop = true;
      this.bMouseListener = true;
      GWindow localGWindow;
      doNew(localGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }
  }

  public class ScrollBar extends GWindowVScrollBar
  {
    public boolean setPos(float paramFloat, boolean paramBoolean)
    {
      boolean bool = super.setPos(paramFloat, paramBoolean);
      if (paramBoolean)
        GWindowComboControl.this.listStartLine = (int)this.pos;
      return bool;
    }
    public ScrollBar(GWindow arg2) {
      super();
    }
  }
}