package com.maddox.gwindow;

import java.util.ArrayList;
import java.util.List;

public class GWindowEditText extends GWindowDialogControl
{
  public ArrayList text = new ArrayList();
  public ArrayList textPos = new ArrayList();
  public Pos posCaret = new Pos(0, 0);
  public Pos posSelect = new Pos();

  public ArrayList undoList = new ArrayList();
  public int undoPos = 0;
  private int maxUnDoSize = 128;

  public boolean bCanEdit = true;
  public boolean bCanRepeat = true;
  public boolean bDelayedNotify = false;

  public float repeatStartTimeout = 0.5F;
  public float repeatGoTimeout = 0.1F;

  public boolean bWrap = true;
  public boolean bControlDown = false;
  public boolean bShiftDown = false;
  public boolean bMouseDown = false;
  public boolean bChangePending = false;

  public boolean bShowCaret = false;
  public float caretTimeout = 0.0F;

  private static Pos _p0 = new Pos();
  private static Pos _p1 = new Pos();

  private boolean bEnableClampUnDoList = true;

  private boolean bEnableNotifyChange = true;

  public static Pos _tmpPos = new Pos();

  private static char[] _arrayRenderBuffer = new char[''];
  public static final int OP_NONE = 0;
  public static final int OP_INSERT = 1;
  public static final int OP_INSERTLN = 2;
  public static final int OP_RIGHT = 3;
  public static final int OP_WORDRIGHT = 4;
  public static final int OP_LEFT = 5;
  public static final int OP_WORDLEFT = 6;
  public static final int OP_UP = 7;
  public static final int OP_DOWN = 8;
  public static final int OP_DELLEFT = 9;
  public static final int OP_DELRIGHT = 10;
  public int curRepeatOp = 0;
  public char repeatChar;
  public float repeatTimeout;

  private static void _sortPos(Pos paramPos1, Pos paramPos2)
  {
    _p0.set(paramPos1);
    _p1.set(paramPos2);
    if (_p1.isLess(_p0)) { Pos localPos = _p0; _p0 = _p1; _p1 = localPos;
    }
  }

  public UnDo newUnDo(UnDo paramUnDo)
  {
    this.undoList.add(this.undoPos++, paramUnDo);
    while (this.undoPos < this.undoList.size())
      this.undoList.remove(this.undoList.size() - 1);
    clampUnDoList();
    return paramUnDo;
  }

  private void clampUnDoList()
  {
    if ((!this.bEnableClampUnDoList) || (this.maxUnDoSize < 0))
      return;
    int i = this.undoList.size() - this.maxUnDoSize;
    if (i <= 0)
      return;
    while (i < this.undoList.size()) {
      UnDo localUnDo = (UnDo)this.undoList.get(i);
      if (!localUnDo.bSubOperation)
        break;
      i++;
    }
    this.undoPos -= i;
    while (i-- > 0)
      this.undoList.remove(0);
  }

  public void clearUnDo() {
    this.undoList.clear();
    this.undoPos = 0;
  }
  public void doUnDo() {
    if (this.undoPos == 0) return;
    UnDo localUnDo = (UnDo)this.undoList.get(--this.undoPos);
    localUnDo.undo();
    while (this.undoPos > 0) {
      localUnDo = (UnDo)this.undoList.get(this.undoPos - 1);
      if (!localUnDo.bSubOperation)
        break;
      localUnDo.undo();
      this.undoPos -= 1;
    }
  }

  public void doReDo() {
    while (this.undoPos < this.undoList.size()) {
      UnDo localUnDo = (UnDo)this.undoList.get(this.undoPos++);
      localUnDo.redo(!localUnDo.bSubOperation);
      if (!localUnDo.bSubOperation) break;
    }
  }

  public boolean isExistUnDo() {
    return this.undoPos > 0;
  }
  public boolean isExistReDo() {
    return this.undoPos < this.undoList.size();
  }
  public void setMaxUnDoSize(int paramInt) {
    if ((paramInt == 0) || (paramInt == 1)) paramInt = 2;
    this.maxUnDoSize = paramInt;
  }
  public int getMaxUnDoSize() { return this.maxUnDoSize;
  }

  public StringBuffer item(int paramInt)
  {
    return (StringBuffer)this.text.get(paramInt);
  }
  public PosLen itemPos(int paramInt) {
    if (paramInt == this.textPos.size())
      this.textPos.add(new PosLen());
    return (PosLen)this.textPos.get(paramInt);
  }

  public void setEnable(boolean paramBoolean) {
    super.setEnable(paramBoolean);
    if (!this.bEnable) {
      this.bShiftDown = false;
      this.bControlDown = false;
      this.bMouseDown = false;
      this.bShowCaret = false;
      this.bChangePending = false;
      this.posSelect.disable();
    }
  }

  public void setEditable(boolean paramBoolean) {
    this.bCanEdit = paramBoolean;
  }

  public void setWrap(boolean paramBoolean) {
    this.bWrap = paramBoolean;
    updateTextPos();
  }
  public boolean isWrap() {
    return this.bWrap;
  }
  public boolean isSelected() { return (this.posSelect.isEnable()) && (!this.posSelect.isEqual(this.posCaret)); } 
  public boolean isEmpty() { return this.text.size() == 0; } 
  public boolean isChanged() {
    return this.bChangePending;
  }
  public void setChangedFlag(boolean paramBoolean) { this.bChangePending = paramBoolean; }

  public void unselect() {
    this.posSelect.disable();
  }
  public void selectAll() {
    int i = this.text.size() - 1;
    if (i < 0) return;
    this.posSelect.set(0, 0);
    this.posCaret.set(i, item(i).length());
    if (this.posSelect.isEqual(this.posCaret))
      this.posSelect.disable();
  }

  public void clear() {
    clear(true);
  }
  public void clear(boolean paramBoolean) {
    selectAll();
    delete(paramBoolean);
  }

  protected void startShowCaret() {
    this.caretTimeout = 0.3F;
    this.bShowCaret = true;
    notify(2, 1);
  }

  protected void _doNotifyChange(boolean paramBoolean)
  {
    if (!this.bEnableNotifyChange) return;
    updateTextPos();
    startShowCaret();
    if (paramBoolean)
      if (this.bDelayedNotify) this.bChangePending = true; else
        notify(2, 0);
  }

  public void delete()
  {
    delete(true);
  }
  public void delete(boolean paramBoolean) {
    if (!this.bCanEdit) return;
    if (!isSelected()) return;
    if (this.posCaret.item == this.posSelect.item)
      newUnDo(new UnDoDelLine()).redo(paramBoolean);
    else
      newUnDo(new UnDoDelLines()).redo(paramBoolean);
  }

  public void deleteLeft()
  {
    deleteLeft(true);
  }
  public void deleteLeft(boolean paramBoolean) {
    if (!this.bCanEdit) return;
    if (isEmpty()) return;
    if (isSelected()) {
      delete(paramBoolean);
      return;
    }
    if (this.posCaret.ofs > 0)
      newUnDo(new UnDoDelChar(true)).redo(paramBoolean);
    else if (this.posCaret.item > 0)
      newUnDo(new UnDoDelLf(true)).redo(paramBoolean);
  }

  public void deleteRight()
  {
    deleteRight(true);
  }
  public void deleteRight(boolean paramBoolean) {
    if (!this.bCanEdit) return;
    if (isEmpty()) return;
    if (isSelected()) {
      delete(paramBoolean);
      return;
    }
    StringBuffer localStringBuffer = item(this.posCaret.item);
    if (this.posCaret.ofs < localStringBuffer.length())
      newUnDo(new UnDoDelChar(false)).redo(paramBoolean);
    else if (this.posCaret.item < this.text.size() - 1)
      newUnDo(new UnDoDelLf(false)).redo(paramBoolean);
  }

  public void insert()
  {
    insert(true);
  }
  public void insert(boolean paramBoolean) {
    if (!this.bCanEdit) return;
    Object localObject;
    if (isSelected()) {
      delete(false);
      localObject = (UnDo)this.undoList.get(this.undoPos - 1);
      ((UnDo)localObject).bSubOperation = true;
    }
    if (isEmpty()) {
      clearUnDo();
      this.text.add(new StringBuffer());
      _doNotifyChange(paramBoolean);
    } else {
      localObject = item(this.posCaret.item);
      if (this.posCaret.ofs == 0)
        newUnDo(new UnDoInsL()).redo(paramBoolean);
      else if (this.posCaret.ofs == ((StringBuffer)localObject).length())
        newUnDo(new UnDoInsR()).redo(paramBoolean);
      else
        newUnDo(new UnDoIns()).redo(paramBoolean);
    }
  }

  public void insert(char paramChar)
  {
    insert(paramChar, true);
  }
  public void insert(char paramChar, boolean paramBoolean) {
    if (!this.bCanEdit) return;
    if (isSelected()) {
      delete(false);
      UnDo localUnDo = (UnDo)this.undoList.get(this.undoPos - 1);
      localUnDo.bSubOperation = true;
    }
    if (isEmpty()) {
      clearUnDo();
      this.text.add(new StringBuffer());
      item(this.posCaret.item).insert(this.posCaret.ofs++, paramChar);
      unselect();
      _doNotifyChange(paramBoolean);
    } else {
      newUnDo(new UnDoInsChar(paramChar)).redo(paramBoolean);
    }
  }

  public void insert(String paramString) {
    insert(paramString, true);
  }
  public void insert(String paramString, boolean paramBoolean) {
    if (!this.bCanEdit) return;
    if (isSelected()) {
      delete(false);
      UnDo localUnDo = (UnDo)this.undoList.get(this.undoPos - 1);
      localUnDo.bSubOperation = true;
    }
    if (isEmpty()) {
      clearUnDo();
      this.text.add(new StringBuffer());
      item(this.posCaret.item).insert(this.posCaret.ofs, paramString);
      this.posCaret.ofs += paramString.length();
      unselect();
      _doNotifyChange(paramBoolean);
    } else {
      newUnDo(new UnDoInsStr(paramString)).redo(paramBoolean);
    }
  }

  public void insert(List paramList) {
    insert(paramList, true);
  }
  public void insert(List paramList, boolean paramBoolean) {
    if (!this.bCanEdit) return;
    this.bEnableClampUnDoList = false;
    this.bEnableNotifyChange = false;
    boolean bool = isEmpty();
    int i = this.undoPos;
    int j = paramList.size();
    Object localObject;
    for (int k = 0; k < j; k++) {
      localObject = (String)paramList.get(k);
      if (k != 0) insert(false);
      if (localObject == null) continue; insert((String)localObject, false);
    }
    if (bool)
      clearUnDo();
    else {
      for (k = i; k < this.undoPos - 1; k++) {
        localObject = (UnDo)this.undoList.get(k);
        ((UnDo)localObject).bSubOperation = true;
      }
    }
    this.bEnableClampUnDoList = true;
    this.bEnableNotifyChange = true;
    clampUnDoList();
    _doNotifyChange(paramBoolean);
  }

  public void moveLeft() {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    if (--this.posCaret.ofs < 0) {
      if (this.posCaret.item > 0)
        this.posCaret.ofs = item(--this.posCaret.item).length();
      else
        this.posCaret.ofs = 0;
    }
    startShowCaret();
  }

  public void moveRight() {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    if (this.posCaret.ofs + 1 > item(this.posCaret.item).length()) {
      if (this.posCaret.item + 1 < this.text.size()) {
        this.posCaret.item += 1;
        this.posCaret.ofs = 0;
      }
    }
    else this.posCaret.ofs += 1;

    startShowCaret();
  }

  public void moveHome() {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    this.posCaret.ofs = 0;
    startShowCaret();
  }

  public void moveEnd() {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    this.posCaret.ofs = item(this.posCaret.item).length();
    startShowCaret();
  }

  public void wordLeft()
  {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    Pos localPos = _tmpPos;
    localPos.set(this.posCaret);
    int i = 0;
    while (i == 0) {
      while (true) {
        if (localPos.ofs > 0) {
          j = item(localPos.item).charAt(localPos.ofs - 1);
          if (j > 32)
            break;
          localPos.ofs -= 1;
          continue;
        }if (localPos.item == 0)
          return;
        localPos.ofs = item(--localPos.item).length();
      }
      while (true)
      {
        if (localPos.ofs <= 0) break label166; j = item(localPos.item).charAt(localPos.ofs - 1);
        if (j <= 32) {
          i = 1;
          break;
        }
        localPos.ofs -= 1;
      }
      label166: int j = item(localPos.item).charAt(localPos.ofs);
      if (j > 32) {
        i = 1;
        continue;
      }
      if (localPos.item == 0) {
        return;
      }
      localPos.ofs = item(--localPos.item).length();
    }

    this.posCaret.set(localPos);
    startShowCaret();
  }

  public void wordRight() {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    Pos localPos = _tmpPos;
    localPos.set(this.posCaret);
    int i = 0;
    if (i == 0) { int j;
      while (true) if (localPos.ofs < item(localPos.item).length()) {
          j = item(localPos.item).charAt(localPos.ofs);
          if (j <= 32)
            break;
          localPos.ofs += 1;
          continue; } else {
          if (localPos.item + 1 == this.text.size())
            return;
          localPos.set(localPos.item + 1, 0);
        }

      while (true)
      {
        if (localPos.ofs < item(localPos.item).length()) {
          j = item(localPos.item).charAt(localPos.ofs);
          if (j > 32) {
            i = 1;
            break;
          }
          localPos.ofs += 1;
          continue;
        }if (localPos.item + 1 == this.text.size()) {
          return;
        }
        localPos.set(localPos.item + 1, 0);
      }
    }

    this.posCaret.set(localPos);
    startShowCaret();
  }

  public int findLine(Pos paramPos) {
    int i = this.textPos.size();
    for (int j = 0; j < i; j++) {
      PosLen localPosLen = itemPos(j);
      if ((localPosLen.item == paramPos.item) && (paramPos.ofs >= localPosLen.ofs) && (paramPos.ofs <= localPosLen.ofs + localPosLen.len))
        return j;
    }
    return 0;
  }

  public void moveUpDown(int paramInt) {
    if (isEmpty()) return;
    if (!this.bShiftDown) unselect();
    int i = findLine(this.posCaret);
    if (paramInt < 0) {
      if (i == 0) return;
    }
    else if (i == this.textPos.size() - 1) return;

    PosLen localPosLen = itemPos(i);
    if (localPosLen.ofs == this.posCaret.ofs) {
      i += paramInt;
      localPosLen = itemPos(i);
      this.posCaret.set(localPosLen);
      startShowCaret();
      return;
    }
    setCanvasFont(this.font);
    GFont localGFont = this.root.C.font;
    char[] arrayOfChar = _getArrayRenderBuffer(this.posCaret.ofs - localPosLen.ofs);
    StringBuffer localStringBuffer = item(localPosLen.item);
    localStringBuffer.getChars(localPosLen.ofs, this.posCaret.ofs, arrayOfChar, 0);
    GSize localGSize = localGFont.size(arrayOfChar, 0, this.posCaret.ofs - localPosLen.ofs);
    float f1 = localGSize.dx;
    i += paramInt;
    localPosLen = itemPos(i);
    if (localPosLen.len == 0) {
      this.posCaret.set(localPosLen.item, localPosLen.ofs);
    } else {
      arrayOfChar = _getArrayRenderBuffer(localPosLen.len);
      localStringBuffer = item(localPosLen.item);
      localStringBuffer.getChars(localPosLen.ofs, localPosLen.ofs + localPosLen.len, arrayOfChar, 0);
      float f2 = 0.0F;

      for (int j = 1; j < localPosLen.len; j++) {
        localGSize = localGFont.size(arrayOfChar, 0, j);
        if (localGSize.dx >= f1) {
          if (f1 - f2 >= localGSize.dx - f1) break;
          j--; break;
        }

        f2 = localGSize.dx;
      }
      this.posCaret.set(localPosLen.item, localPosLen.ofs + j);
    }
    startShowCaret();
  }

  public void moveUp() {
    moveUpDown(-1);
  }
  public void moveDown() {
    moveUpDown(1);
  }

  public void editCopy() {
    if (!isSelected()) return;
    Object localObject1 = this.posSelect;
    Object localObject2 = this.posCaret;
    if (((Pos)localObject2).isEqual((Pos)localObject1)) return;
    if (((Pos)localObject2).isLess((Pos)localObject1)) { localObject3 = localObject1; localObject1 = localObject2; localObject2 = localObject3; }
    Object localObject3 = new StringBuffer();
    Pos localPos = _tmpPos;
    localPos.set((Pos)localObject1);
    while (localPos.isLess((Pos)localObject2)) {
      StringBuffer localStringBuffer = item(localPos.item);
      if (localPos.ofs == localStringBuffer.length()) {
        ((StringBuffer)localObject3).append("\r\n");
        localPos.ofs = 0;
        localPos.item += 1;
      } else {
        ((StringBuffer)localObject3).append(localStringBuffer.charAt(localPos.ofs++));
      }
    }
    this.root.C.copyToClipboard(((StringBuffer)localObject3).toString());
  }

  public void editPaste() {
    if (!this.bCanEdit) return;
    String str = this.root.C.pasteFromClipboard();
    if ((str == null) || (str.length() == 0)) return;
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = str.length();
    for (int j = 0; j < i; j++) {
      int k = str.charAt(j);
      if ((Character.isLetterOrDigit(k)) || (k == 12289) || (k == 12290) || ((k >= 32) && (k < 128)) || ((k >= 160) && (k <= 255)) || (k == 9))
      {
        localStringBuffer.append(k);
      } else if (k == 10) {
        localArrayList.add(localStringBuffer.toString());
        localStringBuffer = new StringBuffer();
      }
    }
    if (localStringBuffer.length() > 0)
      localArrayList.add(localStringBuffer.toString());
    insert(localArrayList);
  }

  public void editCut() {
    if (!isSelected()) return;
    editCopy();
    if (this.bCanEdit)
      delete();
  }

  public void updateTextPos() {
    if (isEmpty()) {
      this.textPos.clear();
      return;
    }
    setCanvasFont(this.font);
    GFont localGFont = this.root.C.font;
    GSize localGSize = localGFont.size("|");
    float f = localGSize.dx;
    int i;
    int j;
    Object localObject;
    if (this.bWrap) {
      i = 0;
      j = this.text.size();
      for (int k = 0; k < j; k++) {
        StringBuffer localStringBuffer2 = item(k);
        int n = localStringBuffer2.length();
        if (n == 0) {
          localObject = itemPos(i);
          ((PosLen)localObject).set(k, 0, 0, 0.0F);
          i++;
        } else {
          localObject = _getArrayRenderBuffer(n);
          localStringBuffer2.getChars(0, n, localObject, 0);
          int i1 = 0;
          int i2 = 0;
          int i3 = 0;
          PosLen localPosLen;
          while (i2 < n) {
            while ((i2 < n) && 
              (localObject[i2] > ' '))
              i2++;
            while ((i2 < n) && 
              (localObject[i2] <= ' '))
              i2++;
            localGSize = localGFont.size(localObject, i1, i2 - i1);
            if (localGSize.dx + f >= this.win.dx) {
              if (i3 != i1) {
                i2 = i3;
              } else {
                int i4 = i2;
                for (i2 = i1 + 1; i2 < i4; i2++) {
                  localGSize = localGFont.size(localObject, i1, i2 - i1);
                  if (localGSize.dx + f >= this.win.dx)
                    break;
                }
                if (i2 > i1 + 1)
                  i2--;
              }
              localGSize = localGFont.size(localObject, i1, i2 - i1);
              localPosLen = itemPos(i);
              localPosLen.set(k, i1, i2 - i1, localGSize.dx + f);
              i++;
              i1 = i3 = i2;
              continue;
            }i3 = i2;
          }

          if (i1 != i2) {
            localGSize = localGFont.size(localObject, i1, i2 - i1);
            localPosLen = itemPos(i);
            localPosLen.set(k, i1, i2 - i1, localGSize.dx + f);
            i++;
          }
        }
      }
      while (this.textPos.size() > i)
        this.textPos.remove(this.textPos.size() - 1);
    }
    else {
      i = this.text.size();
      for (j = 0; j < i; j++) {
        StringBuffer localStringBuffer1 = item(j);
        int m = localStringBuffer1.length();
        char[] arrayOfChar = _getArrayRenderBuffer(m);
        localStringBuffer1.getChars(0, m, arrayOfChar, 0);
        localGSize = localGFont.size(arrayOfChar, 0, m);
        localObject = itemPos(j);
        ((PosLen)localObject).set(j, 0, m, localGSize.dx + f);
      }
      while (this.textPos.size() > i)
        this.textPos.remove(this.textPos.size() - 1);
    }
  }

  public static char[] _getArrayRenderBuffer(int paramInt)
  {
    if (paramInt > _arrayRenderBuffer.length)
      _arrayRenderBuffer = new char[paramInt];
    return _arrayRenderBuffer;
  }

  public void keyboardChar(char paramChar) {
    if ((this.bEnable) && (this.bCanEdit) && (!this.bControlDown))
    {
      if ((Character.isLetterOrDigit(paramChar)) || (paramChar == '、') || (paramChar == '。') || ((paramChar >= ' ') && (paramChar < '')) || ((paramChar >= ' ') && (paramChar <= 'ÿ')) || (paramChar == '\t'))
      {
        insert(paramChar);
        this.repeatChar = paramChar;
        startRepeat(1);
      }
    }
    super.keyboardChar(paramChar);
  }

  public void stopRepeat()
  {
    this.curRepeatOp = 0;
  }
  public void startRepeat(int paramInt) { this.repeatTimeout = this.repeatStartTimeout;
    this.curRepeatOp = paramInt; }

  public void doRepeat() {
    if (!this.bCanRepeat) return;
    if (this.curRepeatOp == 0) return;
    this.repeatTimeout -= this.root.deltaTimeSec;
    if (this.repeatTimeout > 0.0F) return;
    this.repeatTimeout = this.repeatGoTimeout;
    switch (this.curRepeatOp) { case 1:
      insert(this.repeatChar); break;
    case 2:
      insert(); break;
    case 3:
      moveRight(); break;
    case 4:
      wordRight(); break;
    case 5:
      moveLeft(); break;
    case 6:
      wordLeft(); break;
    case 7:
      moveUp(); break;
    case 8:
      moveDown(); break;
    case 9:
      deleteLeft(); break;
    case 10:
      deleteRight(); break;
    default:
      return; }
  }

  public void keyboardKey(int paramInt, boolean paramBoolean)
  {
    if (!this.bEnable) {
      super.keyboardKey(paramInt, paramBoolean);
      return;
    }
    if (!paramBoolean) {
      switch (paramInt) {
      case 17:
        this.bControlDown = false;
        break;
      case 16:
        this.bShiftDown = false;
        break;
      }

      stopRepeat();
      super.keyboardKey(paramInt, paramBoolean);
      return;
    }
    switch (paramInt) {
    case 17:
      this.bControlDown = true;
      stopRepeat();
      break;
    case 16:
      this.bShiftDown = true;
      if (!isSelected())
        this.posSelect.set(this.posCaret);
      stopRepeat();
      break;
    case 10:
      insert(); startRepeat(2);
      break;
    case 39:
      if (this.bControlDown) { wordRight(); startRepeat(4); } else {
        moveRight(); startRepeat(3);
      }break;
    case 37:
      if (this.bControlDown) { wordLeft(); startRepeat(6); } else {
        moveLeft(); startRepeat(5);
      }break;
    case 38:
      moveUp(); startRepeat(7);
      break;
    case 40:
      moveDown(); startRepeat(8);
      break;
    case 36:
      moveHome();
      break;
    case 35:
      moveEnd();
      break;
    case 8:
      deleteLeft(); startRepeat(9);
      break;
    case 127:
      deleteRight(); startRepeat(10);
      break;
    default:
      if (!this.bControlDown) break;
      if (paramInt == 67) editCopy();
      if (paramInt == 86) editPaste();
      if (paramInt == 88) editCut();
      if (paramInt == 65) selectAll();
      if (paramInt == 90) doUnDo();
      if (paramInt != 89) break; doReDo();
    }

    super.keyboardKey(paramInt, paramBoolean);
  }

  public void setCaretPos(float paramFloat1, float paramFloat2) {
    if (isEmpty()) return;
    GFont localGFont = this.root.textFonts[this.font];
    int i = (int)(paramFloat2 / localGFont.height);
    if (i >= this.textPos.size())
      i = this.textPos.size() - 1;
    PosLen localPosLen = itemPos(i);
    if (localPosLen.len == 0) {
      this.posCaret.set(localPosLen);
    } else {
      StringBuffer localStringBuffer = item(localPosLen.item);
      char[] arrayOfChar = _getArrayRenderBuffer(localPosLen.len);
      localStringBuffer.getChars(localPosLen.ofs, localPosLen.ofs + localPosLen.len, arrayOfChar, 0);

      float f = 0.0F;

      for (int j = 1; j < localPosLen.len; j++) {
        GSize localGSize = localGFont.size(arrayOfChar, 0, j);
        if (localGSize.dx >= paramFloat1) {
          if (paramFloat1 - f >= localGSize.dx - paramFloat1) break;
          j--; break;
        }

        f = localGSize.dx;
      }
      this.posCaret.set(localPosLen.item, localPosLen.ofs + j);
    }
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    if ((this.bEnable) && (paramInt == 0)) {
      if (paramBoolean) {
        this.bMouseDown = true;
        if (isSelected()) unselect();
        setCaretPos(paramFloat1, paramFloat2);
        this.posSelect.set(this.posCaret);
      } else {
        this.bMouseDown = false;
      }
      startShowCaret();
    }
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
  }

  public void mouseMove(float paramFloat1, float paramFloat2) {
    if (this.bMouseDown)
      setCaretPos(paramFloat1, paramFloat2);
    super.mouseMove(paramFloat1, paramFloat2);
  }

  public void close(boolean paramBoolean)
  {
    if ((this.bEnable) && (this.bChangePending)) {
      this.bChangePending = false;
      notify(2, 0);
    }
    super.close(paramBoolean);
  }

  public void keyFocusExit() {
    stopRepeat();
    if ((this.bEnable) && (this.bChangePending)) {
      this.bChangePending = false;
      notify(2, 0);
    }
    this.bControlDown = false;
    this.bShiftDown = false;
    super.keyFocusExit();
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
      return;
    }
    float f = this.root.deltaTimeSec;
    this.caretTimeout -= f;
    if (this.caretTimeout <= 0.0F) {
      this.bShowCaret = (!this.bShowCaret);
      this.caretTimeout = 0.3F;
    }
  }

  public void resized() {
    super.resized();
    updateTextPos();
  }

  public void preRender() {
    super.preRender();
    doRepeat();
  }

  public void render() {
    lookAndFeel().render(this);
    checkCaretTimeout();
  }

  public class UnDoInsStr extends GWindowEditText.UnDo
  {
    public String str;

    public void undo()
    {
      GWindowEditText.this.item(this.caret.item).delete(this.caret.ofs, this.caret.ofs + this.str.length());
      super.undo();
    }
    public void redo(boolean paramBoolean) {
      GWindowEditText.this.item(this.caret.item).insert(this.caret.ofs, this.str);
      GWindowEditText.this.posCaret.set(this.caret.item, this.caret.ofs + this.str.length());
      super.redo(paramBoolean);
    }
    public UnDoInsStr(String arg2) {
      super();
      Object localObject;
      this.str = localObject;
    }
  }

  public class UnDoInsChar extends GWindowEditText.UnDo
  {
    public char ch;

    public void undo()
    {
      GWindowEditText.this.item(this.caret.item).deleteCharAt(this.caret.ofs);
      super.undo();
    }
    public void redo(boolean paramBoolean) {
      GWindowEditText.this.item(this.caret.item).insert(this.caret.ofs, this.ch);
      GWindowEditText.this.posCaret.set(this.caret.item, this.caret.ofs + 1);
      super.redo(paramBoolean);
    }
    public UnDoInsChar(char arg2) {
      super();
      char c;
      this.ch = c;
    }
  }

  public class UnDoIns extends GWindowEditText.UnDo
  {
    public UnDoIns()
    {
      super();
    }
    public void undo() { GWindowEditText.this.item(this.caret.item).append(GWindowEditText.this.item(this.caret.item + 1).toString());
      GWindowEditText.this.text.remove(this.caret.item + 1);
      super.undo(); }

    public void redo(boolean paramBoolean) {
      StringBuffer localStringBuffer = GWindowEditText.this.item(this.caret.item);
      char[] arrayOfChar = new char[localStringBuffer.length() - this.caret.ofs];
      localStringBuffer.getChars(this.caret.ofs, localStringBuffer.length(), arrayOfChar, 0);
      localStringBuffer.delete(this.caret.ofs, localStringBuffer.length());
      localStringBuffer = new StringBuffer(arrayOfChar.length);
      localStringBuffer.append(arrayOfChar);
      GWindowEditText.this.text.add(this.caret.item + 1, localStringBuffer);
      GWindowEditText.this.posCaret.set(this.caret.item + 1, 0);
      super.redo(paramBoolean);
    }
  }

  public class UnDoInsR extends GWindowEditText.UnDo
  {
    public UnDoInsR()
    {
      super();
    }
    public void undo() { GWindowEditText.this.text.remove(this.caret.item + 1);
      super.undo(); }

    public void redo(boolean paramBoolean) {
      GWindowEditText.this.text.add(this.caret.item + 1, new StringBuffer());
      GWindowEditText.this.posCaret.set(this.caret.item + 1, 0);
      super.redo(paramBoolean);
    }
  }

  public class UnDoInsL extends GWindowEditText.UnDo
  {
    public UnDoInsL()
    {
      super();
    }
    public void undo() { GWindowEditText.this.text.remove(this.caret.item);
      super.undo(); }

    public void redo(boolean paramBoolean) {
      GWindowEditText.this.text.add(this.caret.item, new StringBuffer());
      GWindowEditText.this.posCaret.set(this.caret.item + 1, 0);
      super.redo(paramBoolean);
    }
  }

  public class UnDoDelLf extends GWindowEditText.UnDo
  {
    public boolean bLeft;
    public int ofs;

    public void undo()
    {
      StringBuffer localStringBuffer;
      char[] arrayOfChar;
      if (this.bLeft) {
        localStringBuffer = GWindowEditText.this.item(this.caret.item - 1);
        arrayOfChar = new char[localStringBuffer.length() - this.ofs];
        localStringBuffer.getChars(this.ofs, localStringBuffer.length(), arrayOfChar, 0);
        localStringBuffer.delete(this.ofs, localStringBuffer.length());
        localStringBuffer = new StringBuffer(arrayOfChar.length);
        localStringBuffer.append(arrayOfChar);
        GWindowEditText.this.text.add(this.caret.item, localStringBuffer);
      } else {
        localStringBuffer = GWindowEditText.this.item(this.caret.item);
        arrayOfChar = new char[localStringBuffer.length() - this.ofs];
        localStringBuffer.getChars(this.ofs, localStringBuffer.length(), arrayOfChar, 0);
        localStringBuffer.delete(this.ofs, localStringBuffer.length());
        localStringBuffer = new StringBuffer(arrayOfChar.length);
        localStringBuffer.append(arrayOfChar);
        GWindowEditText.this.text.add(this.caret.item + 1, localStringBuffer);
      }
      super.undo();
    }

    public void redo(boolean paramBoolean)
    {
      StringBuffer localStringBuffer;
      if (this.bLeft) {
        localStringBuffer = GWindowEditText.this.item(this.caret.item - 1);
        localStringBuffer.append(GWindowEditText.this.item(this.caret.item).toString());
        GWindowEditText.this.text.remove(this.caret.item);
        GWindowEditText.this.posCaret.set(this.caret.item - 1, this.ofs);
      } else {
        localStringBuffer = GWindowEditText.this.item(this.caret.item);
        localStringBuffer.append(GWindowEditText.this.item(this.caret.item + 1).toString());
        GWindowEditText.this.text.remove(this.caret.item + 1);
      }
      super.redo(paramBoolean);
    }
    public UnDoDelLf(boolean arg2) {
      super();
      boolean bool;
      this.bLeft = bool;
      StringBuffer localStringBuffer = GWindowEditText.this.item(bool ? this.caret.item - 1 : this.caret.item);
      this.ofs = localStringBuffer.length();
    }
  }

  public class UnDoDelChar extends GWindowEditText.UnDo
  {
    public char ch;
    public boolean bLeft;

    public void undo()
    {
      GWindowEditText.this.item(this.caret.item).insert(this.bLeft ? this.caret.ofs - 1 : this.caret.ofs, this.ch);
      super.undo();
    }
    public void redo(boolean paramBoolean) {
      GWindowEditText.this.item(this.caret.item).deleteCharAt(this.bLeft ? this.caret.ofs - 1 : this.caret.ofs);
      GWindowEditText.this.posCaret.set(this.caret.item, this.bLeft ? this.caret.ofs - 1 : this.caret.ofs);
      super.redo(paramBoolean);
    }
    public UnDoDelChar(boolean arg2) {
      super();
      boolean bool;
      this.bLeft = bool;
      this.ch = GWindowEditText.this.item(this.caret.item).charAt(bool ? this.caret.ofs - 1 : this.caret.ofs);
    }
  }

  public class UnDoDelLines extends GWindowEditText.UnDo
  {
    public ArrayList lines = new ArrayList();

    public void undo() { GWindowEditText.access$000(this.select, this.caret);
      GWindowEditText.this.text.remove(GWindowEditText._p0.item);
      int i = this.lines.size();
      for (int j = 0; j < i; j++) {
        char[] arrayOfChar = (char[])(char[])this.lines.get(j);
        StringBuffer localStringBuffer = new StringBuffer(arrayOfChar.length);
        localStringBuffer.append(arrayOfChar);
        GWindowEditText.this.text.add(GWindowEditText._p0.item++, localStringBuffer);
      }
      super.undo(); }

    public void redo(boolean paramBoolean) {
      GWindowEditText.access$000(this.select, this.caret);
      while (GWindowEditText._p0.item + 1 < GWindowEditText._p1.item)
        GWindowEditText.this.text.remove(--GWindowEditText._p1.item);
      StringBuffer localStringBuffer = GWindowEditText.this.item(GWindowEditText._p0.item);
      localStringBuffer.delete(GWindowEditText._p0.ofs, localStringBuffer.length());
      localStringBuffer.append(GWindowEditText.this.item(GWindowEditText._p1.item).substring(GWindowEditText._p1.ofs, GWindowEditText.this.item(GWindowEditText._p1.item).length()));
      GWindowEditText.this.text.remove(GWindowEditText._p1.item);
      GWindowEditText.this.posCaret.set(GWindowEditText._p0);
      super.redo(paramBoolean);
    }
    public UnDoDelLines() { super();
      GWindowEditText.access$000(this.select, this.caret);
      while (GWindowEditText._p0.item <= GWindowEditText._p1.item) {
        StringBuffer localStringBuffer = GWindowEditText.this.item(GWindowEditText._p0.item);
        char[] arrayOfChar = new char[localStringBuffer.length()];
        localStringBuffer.getChars(0, localStringBuffer.length(), arrayOfChar, 0);
        this.lines.add(arrayOfChar);
        GWindowEditText._p0.item += 1;
      }
    }
  }

  public class UnDoDelLine extends GWindowEditText.UnDo
  {
    public char[] line;

    public void undo()
    {
      GWindowEditText.access$000(this.select, this.caret);
      GWindowEditText.this.item(GWindowEditText._p0.item).insert(GWindowEditText._p0.ofs, this.line);
      super.undo();
    }
    public void redo(boolean paramBoolean) {
      GWindowEditText.access$000(this.select, this.caret);
      GWindowEditText.this.item(GWindowEditText._p0.item).delete(GWindowEditText._p0.ofs, GWindowEditText._p1.ofs);
      GWindowEditText.this.posCaret.set(GWindowEditText._p0);
      super.redo(paramBoolean);
    }
    public UnDoDelLine() { super();
      GWindowEditText.access$000(this.select, this.caret);
      this.line = new char[GWindowEditText._p1.ofs - GWindowEditText._p0.ofs];
      GWindowEditText.this.item(GWindowEditText._p0.item).getChars(GWindowEditText._p0.ofs, GWindowEditText._p1.ofs, this.line, 0);
    }
  }

  public class UnDo
  {
    public GWindowEditText.Pos caret = new GWindowEditText.Pos();
    public GWindowEditText.Pos select = new GWindowEditText.Pos();
    public boolean bSubOperation = false;

    public void undo() { GWindowEditText.this.posCaret.set(this.caret);
      GWindowEditText.this.posSelect.set(this.select);
      GWindowEditText.this._doNotifyChange(true); }

    public void redo(boolean paramBoolean) {
      GWindowEditText.this.posSelect.disable();
      GWindowEditText.this._doNotifyChange(paramBoolean);
    }
    public UnDo() {
      this.caret.set(GWindowEditText.this.posCaret);
      this.select.set(GWindowEditText.this.posSelect);
    }
  }

  public static class PosLen extends GWindowEditText.Pos
  {
    public int len;
    public float width;

    public void set(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
    {
      this.item = paramInt1;
      this.ofs = paramInt2;
      this.len = paramInt3;
      this.width = paramFloat;
    }

    public PosLen() {
      this.len = 0;
      this.width = 0.0F;
    }
    public PosLen(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
      set(paramInt1, paramInt2, paramInt3, paramFloat);
    }
  }

  public static class Pos
  {
    public int item;
    public int ofs;

    public boolean isEnable()
    {
      return this.item >= 0; } 
    public void disable() { this.item = -1; } 
    public boolean isEqual(Pos paramPos) {
      return (paramPos.item == this.item) && (paramPos.ofs == this.ofs);
    }
    public boolean isLess(Pos paramPos) {
      return (this.item < paramPos.item) || ((this.item == paramPos.item) && (this.ofs < paramPos.ofs));
    }
    public void set(int paramInt1, int paramInt2) {
      this.item = paramInt1;
      this.ofs = paramInt2;
    }
    public void set(Pos paramPos) {
      set(paramPos.item, paramPos.ofs);
    }
    public Pos() { this.item = -1; this.ofs = 0; } 
    public Pos(int paramInt1, int paramInt2) {
      set(paramInt1, paramInt2);
    }
  }
}