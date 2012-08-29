package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowEditBox extends GWindowDialogControl
  implements GWindowCellEdit
{
  public StringBuffer value = new StringBuffer();
  public int maxLength = 255;
  public boolean bCanEdit = true;
  public boolean bCanEditTab = true;
  public boolean bNumericOnly = false;
  public boolean bNumericFloat = false;
  public boolean bPassword = false;
  public boolean bSelectOnFocus = true;
  public boolean bDelayedNotify = false;

  public float offsetX = 0.0F;

  public boolean bAllSelected = false;
  public int caretOffset = 0;
  public boolean bShowCaret = false;
  public float caretTimeout = 0.0F;

  public boolean bControlDown = false;
  public boolean bShiftDown = false;
  public boolean bChangePending = false;

  public boolean bHistory = false;
  public ArrayList historyList;
  public int historyCur = 0;

  public void setHistory(boolean paramBoolean)
  {
    this.bHistory = paramBoolean;
    if ((this.bHistory) && (this.historyList == null)) {
      this.historyList = new ArrayList();
    }
    else if ((!this.bHistory) && (this.historyList != null))
      this.historyList = null;
  }

  public void setEnable(boolean paramBoolean)
  {
    super.setEnable(paramBoolean);
    if (!this.bEnable) {
      this.bControlDown = false;
      this.bShiftDown = false;
      this.bShowCaret = false;
      this.bAllSelected = false;
      this.bChangePending = false;
      this.caretOffset = 0;
    }
  }

  public void setEditable(boolean paramBoolean) {
    this.bCanEdit = paramBoolean;
  }

  public void setValue(String paramString) {
    setValue(paramString, true);
  }

  public void setValue(String paramString, boolean paramBoolean) {
    this.value.delete(0, this.value.length());
    this.value.append(paramString);
    if (this.caretOffset > this.value.length())
      this.caretOffset = this.value.length();
    if (paramBoolean)
      if (this.bDelayedNotify) this.bChangePending = true; else
        notify(2, 0);
  }

  public void setCellEditValue(Object paramObject)
  {
    setValue(paramObject.toString(), false);
  }

  public Object getCellEditValue() {
    return getValue();
  }

  public void clear() {
    clear(true);
  }

  public void clear(boolean paramBoolean) {
    this.caretOffset = 0;
    this.value.delete(0, this.value.length());
    this.bAllSelected = false;
    if (paramBoolean)
      if (this.bDelayedNotify) this.bChangePending = true; else
        notify(2, 0);
  }

  public void selectAll()
  {
    if ((this.bCanEdit) && (this.value.length() > 0)) {
      this.caretOffset = this.value.length();
      this.bAllSelected = true;
    }
  }

  public int getFirstChar() {
    if (this.value.length() > 0)
      return this.value.charAt(0);
    return -1;
  }

  public String getValue() {
    return this.value.toString();
  }

  public void insert(String paramString) {
    for (int i = 0; i < paramString.length(); i++)
      insert(paramString.charAt(i));
  }

  public boolean insert(char paramChar) {
    if (this.value.length() >= this.maxLength)
      return false;
    this.value.insert(this.caretOffset, paramChar);
    this.caretOffset += 1;
    if (this.bDelayedNotify) this.bChangePending = true; else
      notify(2, 0);
    return true;
  }

  protected void startShowCaret() {
    this.caretTimeout = 0.3F;
    this.bShowCaret = true;
  }

  public boolean backspace() {
    if (this.caretOffset == 0) return false;
    this.value.delete(this.caretOffset - 1, this.caretOffset);
    this.caretOffset -= 1;
    if (this.bDelayedNotify) this.bChangePending = true; else
      notify(2, 0);
    return true;
  }

  public boolean delete() {
    if (this.caretOffset == this.value.length()) return false;
    this.value.delete(this.caretOffset, this.caretOffset + 1);
    if (this.bDelayedNotify) this.bChangePending = true; else
      notify(2, 0);
    return true;
  }

  public boolean wordLeft()
  {
    int i;
    while (this.caretOffset > 0) {
      i = this.value.charAt(this.caretOffset - 1);
      if ((i != 32) && (i != 9))
        break;
      this.caretOffset -= 1;
    }
    while (this.caretOffset > 0) {
      i = this.value.charAt(this.caretOffset - 1);
      if ((i == 32) || (i == 9))
        break;
      this.caretOffset -= 1;
    }
    startShowCaret();
    return true;
  }

  public boolean moveLeft() {
    if (this.caretOffset == 0) return false;
    this.caretOffset -= 1;
    startShowCaret();
    return true;
  }

  public boolean moveRight() {
    if (this.caretOffset == this.value.length()) return false;
    this.caretOffset += 1;
    startShowCaret();
    return true;
  }

  public boolean wordRight()
  {
    int i;
    while (this.caretOffset < this.value.length()) {
      i = this.value.charAt(this.caretOffset);
      if ((i != 32) && (i != 9))
        break;
      this.caretOffset += 1;
    }
    while (this.caretOffset < this.value.length()) {
      i = this.value.charAt(this.caretOffset);
      if ((i == 32) || (i == 9))
        break;
      this.caretOffset += 1;
    }
    startShowCaret();
    return true;
  }

  public boolean moveHome() {
    this.caretOffset = 0;
    startShowCaret();
    return true;
  }

  public boolean moveEnd() {
    this.caretOffset = this.value.length();
    startShowCaret();
    return true;
  }

  public void editCopy() {
    if ((this.bAllSelected) || (!this.bCanEdit))
      this.root.C.copyToClipboard(this.value.toString());
  }

  public void editPaste() {
    if (this.bCanEdit) {
      if (this.bAllSelected)
        clear();
      insert(this.root.C.pasteFromClipboard());
    }
  }

  public void editCut() {
    if (this.bCanEdit) {
      if (this.bAllSelected) {
        this.root.C.copyToClipboard(this.value.toString());
        this.bAllSelected = false;
        clear();
      }
    }
    else editCopy();
  }

  public void keyboardChar(char paramChar)
  {
    if ((this.bEnable) && (this.bCanEdit) && (!this.bControlDown)) {
      if ((paramChar == '\t') && (!this.bCanEditTab))
        return;
      if (this.bAllSelected)
        clear();
      this.bAllSelected = false;
      if (this.bNumericOnly) {
        if (Character.isDigit(paramChar))
          insert(paramChar);
        else if ((paramChar == '-') && (this.value.length() == 0))
          insert(paramChar);
      } else if ((Character.isLetterOrDigit(paramChar)) || ((paramChar >= ' ') && (paramChar < '')) || (paramChar == '\t'))
      {
        insert(paramChar);
      }
    }
  }

  public void keyboardKey(int paramInt, boolean paramBoolean)
  {
    if (!this.bEnable) {
      super.keyboardKey(paramInt, paramBoolean);
      return;
    }
    if (!paramBoolean) {
      switch (paramInt) {
      case 9:
        if (!this.bCanEditTab) break;
      case 8:
      case 35:
      case 36:
      case 37:
      case 39:
      case 127:
        if (!this.bCanEdit) break; return;
      case 17:
        this.bControlDown = false;
        break;
      case 16:
        this.bShiftDown = false;
        break;
      case 38:
      case 40:
        if ((!this.bCanEdit) || (!this.bHistory)) break; return;
      case 46:
        if ((!this.bCanEdit) || (!this.bNumericFloat)) break; return;
      }

      super.keyboardKey(paramInt, paramBoolean);
      return;
    }
    int i;
    switch (paramInt) {
    case 9:
      if ((!this.bCanEdit) || (this.bCanEditTab)) break;
      return;
    case 17:
      this.bControlDown = true;
      break;
    case 16:
      this.bShiftDown = true;
      break;
    case 10:
      if ((!this.bCanEdit) || 
        (!this.bHistory) || (this.value.length() <= 0)) break;
      this.historyList.add(0, this.value.toString()); break;
    case 39:
      this.bAllSelected = false;
      if (!this.bCanEdit) break;
      if (this.bControlDown) wordRight(); else
        moveRight();
      return;
    case 37:
      this.bAllSelected = false;
      if (!this.bCanEdit) break;
      if (this.bControlDown) wordLeft(); else
        moveLeft();
      return;
    case 38:
      if ((!this.bCanEdit) || (!this.bHistory)) break;
      this.bAllSelected = false;
      if (!this.historyList.isEmpty()) {
        setValue((String)this.historyList.get(this.historyCur));
        moveEnd();
        i = this.historyList.size();
        this.historyCur = ((this.historyCur + 1) % i);
      }
      return;
    case 40:
      if ((!this.bCanEdit) || (!this.bHistory)) break;
      this.bAllSelected = false;
      if (!this.historyList.isEmpty()) {
        i = this.historyList.size();
        this.historyCur = ((this.historyCur - 1 + i) % i);
        setValue((String)this.historyList.get(this.historyCur));
        moveEnd();
      }
      return;
    case 36:
      this.bAllSelected = false;
      if (!this.bCanEdit) break;
      moveHome();
      return;
    case 35:
      this.bAllSelected = false;
      if (!this.bCanEdit) break;
      moveEnd();
      return;
    case 8:
      if (this.bCanEdit) {
        if (this.bAllSelected) clear(); else
          backspace();
        this.bAllSelected = false;
        return;
      }
      this.bAllSelected = false;
      break;
    case 127:
      if (this.bCanEdit) {
        if (this.bAllSelected) clear(); else
          delete();
        this.bAllSelected = false;
        return;
      }
      this.bAllSelected = false;
      break;
    case 46:
      if ((!this.bCanEdit) || (!this.bNumericFloat)) break;
      insert('.');
      return;
    default:
      if (!this.bControlDown) break;
      if (paramInt == 67) editCopy();
      if (paramInt == 86) editPaste();
      if (paramInt != 88) break; editCut();
    }

    super.keyboardKey(paramInt, paramBoolean);
  }

  public void close(boolean paramBoolean) {
    if ((this.bEnable) && (this.bChangePending)) {
      this.bChangePending = false;
      notify(2, 0);
    }
    super.close(paramBoolean);
  }

  public void keyFocusEnter() {
    if ((this.bEnable) && (this.bSelectOnFocus))
      selectAll();
    super.keyFocusEnter();
  }

  public void keyFocusExit() {
    if (this.bEnable) {
      this.bAllSelected = false;
      if (this.bChangePending) {
        this.bChangePending = false;
        notify(2, 0);
      }
    }
    this.bControlDown = false;
    this.bShiftDown = false;
    super.keyFocusExit();
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if ((!this.bEnable) || (!this.bCanEdit) || (paramInt != 0) || (!paramBoolean)) return;
    paramFloat1 -= this.offsetX;
    GFont localGFont = this.root.textFonts[this.font];
    this.caretOffset = localGFont.len(getValue(), paramFloat1, true, false);
    startShowCaret();
    this.bAllSelected = false;
  }

  public void mouseDoubleClick(int paramInt, float paramFloat1, float paramFloat2) {
    super.mouseDoubleClick(paramInt, paramFloat1, paramFloat2);
    if ((this.bEnable) && (paramInt == 0))
      selectAll();
  }

  public boolean _notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 2) {
      if (!this.bChangePending)
        return true;
      this.bChangePending = false;
    }
    return notify(paramInt1, paramInt2);
  }

  public void checkCaretTimeout() {
    if ((!isKeyFocus()) || (!this.bCanEdit) || (!isActivated())) {
      this.bShowCaret = false;
      this.bAllSelected = false;
      return;
    }
    float f = this.root.deltaTimeSec;
    this.caretTimeout -= f;
    if (this.caretTimeout <= 0.0F) {
      this.bShowCaret = (!this.bShowCaret);
      this.caretTimeout = 0.3F;
    }
  }

  public void render() {
    lookAndFeel().render(this, this.offsetX);
    checkCaretTimeout();
  }

  public void created() {
    this.bEnableDoubleClick[0] = true;
    super.created();
  }
}