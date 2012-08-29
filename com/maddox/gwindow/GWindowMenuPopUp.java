package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowMenuPopUp extends GWindowMenu
{
  public void beforeExecute(GWindowMenuItem paramGWindowMenuItem)
  {
    if (paramGWindowMenuItem != null) {
      lAF().soundPlay("WindowOpen");
    }
    close(false);
    setSelected(null);
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    int i = this.jdField_items_of_type_JavaUtilArrayList.size();
    if ((!paramBoolean) && (i > 0))
    {
      int j;
      switch (paramInt) {
      case 27:
        close(false);
        setSelected(null);
        break;
      case 10:
        if (this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem == null) break;
        doExecute(this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem); break;
      case 38:
        if (this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem == null) {
          nextSelected(i - 1, -1);
        } else {
          j = this.jdField_items_of_type_JavaUtilArrayList.indexOf(this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem);
          if (j == 0) j = i;
          nextSelected(j - 1, -1);
        }
        break;
      case 40:
        if (this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem == null) {
          nextSelected(0, 1);
        } else {
          j = this.jdField_items_of_type_JavaUtilArrayList.indexOf(this.jdField_selected_of_type_ComMaddoxGwindowGWindowMenuItem);
          if (j == i - 1) j = -1;
          nextSelected(j + 1, 1);
        }
        break;
      }
    }
  }

  public void msgMouseButton(boolean paramBoolean1, int paramInt, boolean paramBoolean2, float paramFloat1, float paramFloat2)
  {
    if (paramBoolean1) return;
    if (!paramBoolean2) return;
    GWindow localGWindow1 = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.findWindowUnder(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y);
    GWindow localGWindow2 = localGWindow1.getParent(GWindowMenuPopUp.class, false);
    if (localGWindow2 != this) {
      close(false);
      setSelected(null);
    }
  }

  public void created() {
    super.created();
    this.bMouseListener = true;
    this.bAcceptsHotKeys = true;
  }
}