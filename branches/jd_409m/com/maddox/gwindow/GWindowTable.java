package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowTable extends GWindow
{
  public boolean bColumnsSizable = true;
  public boolean bColAlign = true;
  public boolean bRowAlign = true;
  public boolean bSelecting = true;
  public boolean bSelectRow = false;
  public boolean bEditExitOnFocusExit = true;
  public boolean bEditExitOnEnterPress = true;

  public int selectRow = -1;
  public int selectCol = -1;
  public GWindowCellEdit editor;
  public Client wClient;
  public GWindowVScrollBar vSB;
  public GWindowHScrollBar hSB;
  public ArrayList columns = new ArrayList();
  public GWindowButton endColumn;
  public GWindowButton button;
  private GRegion _clientRegion = new GRegion();

  public int countColumns()
  {
    return this.columns.size();
  }
  public int countRows() { return 0; } 
  public float rowHeight(int paramInt) {
    return (int)(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height * 1.2F);
  }
  public float fullClientHeight() { return rowHeight(0) * countRows(); } 
  public void columnClicked(int paramInt) {
  }
  public Object getValueAt(int paramInt1, int paramInt2) {
    return null;
  }
  public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
  }
  public boolean isCellEditable(int paramInt1, int paramInt2) { return false; }

  public GWindowCellEdit getCellEdit(int paramInt1, int paramInt2) {
    if (!isCellEditable(paramInt1, paramInt2)) return null;
    Object localObject = getValueAt(paramInt1, paramInt2);
    if (localObject == null) return null;

    GWindowCellEdit localGWindowCellEdit = (GWindowCellEdit)this.wClient.create(new GWindowEditControl());
    localGWindowCellEdit.setCellEditValue(localObject);
    ((GWindow)localGWindowCellEdit).activateWindow();
    return localGWindowCellEdit;
  }

  public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    Object localObject = getValueAt(paramInt1, paramInt2);
    if (localObject == null) return;
    String str = localObject.toString();
    setCanvasFont(0);
    if (paramBoolean) {
      setCanvasColorBLACK();
      draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      setCanvasColorWHITE();
      draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
    }
    else
    {
      setCanvasColorBLACK();
      draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
    }
  }

  public Column addColumn(String paramString1, String paramString2) {
    Column localColumn = new Column(this, paramString1, paramString2);
    this.columns.add(localColumn);
    resized();
    return localColumn;
  }
  public Column addColumn(int paramInt, String paramString1, String paramString2) {
    Column localColumn = new Column(this, paramString1, paramString2);
    this.columns.add(paramInt, localColumn);
    resized();
    return localColumn;
  }
  public void removeColumn(int paramInt) {
    this.columns.remove(paramInt);
    resized();
  }
  public Column getColumn(int paramInt) {
    return (Column)this.columns.get(paramInt);
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
    if ((paramInt1 == 17) && (this.vSB != null) && (this.vSB.isVisible())) {
      this.vSB.scrollDz(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseRelMoveZ);
      return true;
    }
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

  public void setSelect(int paramInt1, int paramInt2) {
    if ((paramInt1 == this.selectRow) && (paramInt2 == this.selectCol)) {
      if ((this.selectRow < 0) || (this.selectCol < 0)) return;
      if ((isCellEditable(paramInt1, paramInt2)) && (this.editor == null)) {
        this.editor = getCellEdit(this.selectRow, this.selectCol);
        if (this.editor != null)
          this.wClient.setPosEditor();
      }
      return;
    }
    if (this.editor != null) {
      Object localObject = this.editor.getCellEditValue();
      setValueAt(localObject, this.selectRow, this.selectCol);
      ((GWindow)this.editor).hideWindow();
      this.editor = null;
    }
    this.selectRow = paramInt1;
    this.selectCol = paramInt2;
    if ((this.selectRow < 0) || (this.selectCol < 0)) return;
    this.editor = getCellEdit(this.selectRow, this.selectCol);
    if (this.editor != null)
      this.wClient.setPosEditor();
  }

  public void alignColumns()
  {
    if (this.columns.size() == 0) return;
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int i = 0; i < this.columns.size(); i++) {
      localObject = (Column)this.columns.get(i);
      GSize localGSize1 = ((Column)localObject).getMinSize();
      f4 = ((Column)localObject).getRelativeDx();
      if (f4 > 0.0F) f1 += f4; else
        f1 += localGSize1.dx;
      f2 = localGSize1.dy;
    }
    Object localObject = getClientRegion();
    float f3 = ((GRegion)localObject).dx;
    if (this.vSB.isVisible()) f3 -= lookAndFeel().getVScrollBarW();
    float f4 = f3 / f1;
    float f5 = ((GRegion)localObject).x;
    float f6 = f5;
    float f7 = ((GRegion)localObject).y;
    Column localColumn = null;
    for (int j = 0; j < this.columns.size(); j++) {
      localColumn = (Column)this.columns.get(j);
      GSize localGSize2 = localColumn.getMinSize();
      float f8 = localColumn.getRelativeDx();
      if (f8 > 0.0F) localColumn.setSize(f8 * f4, f2); else
        localColumn.setSize(localGSize2.dx * f4, f2);
      localColumn.setPos(f6, f7);
      f6 += localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    }
    if (localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.x - f5 != f3)
      localColumn.setSize(f3 - localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.x + f5, f2);
  }

  private void _alignColumns() {
    if (this.columns.size() == 0) return;
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int i = 0; i < this.columns.size(); i++) {
      localObject = (Column)this.columns.get(i);
      GSize localGSize = ((Column)localObject).getMinSize();
      f1 += ((Column)localObject).jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      f2 = localGSize.dy;
    }
    Object localObject = getClientRegion();
    float f3 = ((GRegion)localObject).dx;
    if (this.vSB.isVisible()) f3 -= lookAndFeel().getVScrollBarW();
    float f4 = f3 / f1;
    float f5 = ((GRegion)localObject).x;
    float f6 = f5;
    float f7 = ((GRegion)localObject).y;
    Column localColumn = null;
    for (int j = 0; j < this.columns.size(); j++) {
      localColumn = (Column)this.columns.get(j);
      localColumn.setSize(localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx * f4, f2);
      localColumn.setPos(f6, f7);
      f6 += localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    }
    if (localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.x - f5 != f3)
      localColumn.setSize(f3 - localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.x + f5, f2);
  }

  public void resized() {
    getClientRegion(this._clientRegion, 0.0F);
    float f1 = 0.0F;
    if (this.columns.size() > 0) {
      Column localColumn1 = (Column)this.columns.get(0);
      GSize localGSize = localColumn1.getMinSize();
      f1 = localGSize.dy;
    }

    if (this.bColumnsSizable) {
      float f2 = 0.0F;
      if (!this.bColAlign) {
        for (i = 0; i < this.columns.size(); i++) {
          Column localColumn2 = (Column)this.columns.get(i);
          f2 += localColumn2.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        }
      }
      int i = 0;
      int j = 0;

      for (int k = 0; k < 2; k++) {
        f3 = this._clientRegion.dy - f1;
        if (j != 0) f3 -= lookAndFeel().getHScrollBarH();
        f4 = this._clientRegion.dx;
        if (i != 0) f4 -= lookAndFeel().getVScrollBarW();
        if (!this.bColAlign)
          j = f2 > f4 ? 1 : 0;
        i = fullClientHeight() > f3 ? 1 : 0;
      }
      float f3 = this._clientRegion.dy - f1;
      if (j != 0) f3 -= lookAndFeel().getHScrollBarH();
      float f4 = this._clientRegion.dx;
      if (i != 0) f4 -= lookAndFeel().getVScrollBarW();

      if (i != 0) {
        this.vSB.setRange(0.0F, fullClientHeight(), f3, this.vSB.scroll, this.vSB.pos);
        this.vSB.setSize(lookAndFeel().getVScrollBarW(), f3 + f1);
        this.vSB.setPos(this._clientRegion.x + f4, this._clientRegion.y);
        this.vSB.showWindow();
      } else {
        this.vSB.setPos(0.0F, false);
        this.vSB.hideWindow();
      }
      float f5;
      if (j != 0) {
        this.hSB.setRange(0.0F, f2, f4, lookAndFeel().metric() / 2.0F, this.hSB.pos);
        this.hSB.setSize(f4, lookAndFeel().getHScrollBarH());
        this.hSB.setPos(this._clientRegion.x, this._clientRegion.y + f1 + f3);
        this.hSB.showWindow();
        f5 = this._clientRegion.x - (int)this.hSB.pos();
      } else {
        this.hSB.setPos(0.0F, false);
        this.hSB.hideWindow();
        f5 = this._clientRegion.x;
      }
      if (this.bColAlign) {
        _alignColumns();
        this.endColumn.hideWindow();
      } else {
        for (int m = 0; m < this.columns.size(); m++) {
          Column localColumn3 = (Column)this.columns.get(m);
          localColumn3.setPos(f5, this._clientRegion.y);
          localColumn3.setSize(localColumn3.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1);
          f5 += localColumn3.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        }
        if ((f5 < f4 + this._clientRegion.x) && (f1 > 0.0F)) {
          this.endColumn.setPos(f5, this._clientRegion.y);
          this.endColumn.setSize(f4 + this._clientRegion.x - f5, f1);
          this.endColumn.showWindow();
        } else {
          this.endColumn.hideWindow();
        }
      }
      if ((j != 0) && (i != 0)) {
        this.button.setPos(f4 + this._clientRegion.x, this._clientRegion.y + f1 + f3);
        this.button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
        this.button.showWindow();
      } else {
        this.button.hideWindow();
      }
      this.wClient.setSize(f4, f3);
    } else {
      if (this._clientRegion.dy - f1 < fullClientHeight()) {
        this.vSB.setRange(0.0F, fullClientHeight(), this._clientRegion.dy - f1, this.vSB.scroll, this.vSB.pos);
        this.vSB.setSize(lookAndFeel().getVScrollBarW(), this._clientRegion.dy);
        this.vSB.setPos(this._clientRegion.x + this._clientRegion.dx - lookAndFeel().getVScrollBarW(), this._clientRegion.y);

        this.vSB.showWindow();
        this.wClient.setSize(this._clientRegion.dx - lookAndFeel().getVScrollBarW(), this._clientRegion.dy - f1);
      } else {
        this.vSB.setPos(0.0F, false);
        this.vSB.hideWindow();
        this.wClient.setSize(this._clientRegion.dx, this._clientRegion.dy - f1);
      }
      alignColumns();
    }
    this.wClient.setPos(this._clientRegion.x, this._clientRegion.y + f1);
    this.wClient.setPosEditor();
  }

  public void afterCreated()
  {
    this.endColumn = new GWindowButton(this);
    this.endColumn.jdField_bAcceptsKeyFocus_of_type_Boolean = false;
    this.endColumn.bDrawActive = false;
    this.endColumn.bDrawOnlyUP = true;
    this.button = new GWindowButton(this);
    this.button.jdField_bAcceptsKeyFocus_of_type_Boolean = false;
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

  public void render() {
    lookAndFeel().render(this);
  }

  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }

  public GWindowTable(GWindow paramGWindow) {
    doNew(paramGWindow);
  }

  public GWindowTable(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }

  public class Column extends GWindowButton
  {
    private boolean bSizingLeft;
    private float relDx = -1.0F;

    public void setRelativeDx(float paramFloat) { this.relDx = paramFloat; } 
    public float getRelativeDx() { return this.relDx; }

    public boolean notify(int paramInt1, int paramInt2) {
      if (paramInt1 == 2) {
        int i = GWindowTable.this.columns.indexOf(this);
        GWindowTable.this.columnClicked(i);
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }

    private boolean isSizableArea(float paramFloat) {
      if (!GWindowTable.this.bColumnsSizable) return false;
      float f = lookAndFeel().metric() / 2.0F;
      if ((paramFloat > f) && (paramFloat < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f)) return false;
      this.bSizingLeft = (paramFloat <= f);
      if ((this.bSizingLeft) && (this == GWindowTable.this.columns.get(0))) return false;

      return (!GWindowTable.this.bColAlign) || (this.bSizingLeft) || (this != GWindowTable.this.columns.get(GWindowTable.this.columns.size() - 1));
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      if ((paramInt == 0) && (
        (isMouseCaptured()) || ((isSizableArea(paramFloat1)) && (!this.bDown)))) {
        mouseCapture(paramBoolean);
        return;
      }

      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    }

    public void mouseMove(float paramFloat1, float paramFloat2) {
      super.mouseMove(paramFloat1, paramFloat2);
      if (isMouseCaptured()) {
        this.jdField_mouseCursor_of_type_Int = 11;
        int i = GWindowTable.this.columns.indexOf(this);
        if (i < 0) return;
        float f1 = this.root.mouseStep.dx;
        float f2;
        Column localColumn;
        if (this.bSizingLeft) {
          f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f1;
          if (f2 < 1.0F) f1 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 1.0F;
          localColumn = (Column)GWindowTable.this.columns.get(i - 1);
          f2 = localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + f1;
          if (f2 < 1.0F) f1 = -(localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 1.0F);
          localColumn.setSize(localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + f1, localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f1, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
        } else {
          f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + f1;
          if (f2 < 1.0F) f1 = -(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 1.0F);
          if (i < GWindowTable.this.columns.size() - 1) {
            localColumn = (Column)GWindowTable.this.columns.get(i + 1);
            f2 = localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f1;
            if (f2 < 1.0F) f1 = localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 1.0F;
            localColumn.setSize(localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f1, localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          }
          setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + f1, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
        }
        this.parentWindow.resized();
      } else if ((isSizableArea(paramFloat1)) && (!isMouseDownAny(0))) {
        this.jdField_mouseCursor_of_type_Int = 11;
      } else {
        this.jdField_mouseCursor_of_type_Int = 1;
      }
    }

    public Column(GWindow paramString1, String paramString2, String arg4) {
      super(paramString2, str);
      GSize localGSize = getMinSize();
      setSize(localGSize.dx, localGSize.dy);
      this.bDrawActive = false;
    }
  }

  public class Client extends GWindow
  {
    public int findedRow;
    public int findedCol;
    private GRegion _clipRegion = new GRegion();

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if ((GWindowTable.this.editor != null) && (paramGWindow == GWindowTable.this.editor) && (
        ((GWindowTable.this.bEditExitOnFocusExit) && (paramInt1 == 16)) || ((GWindowTable.this.bEditExitOnEnterPress) && (paramInt1 == 11) && (paramInt2 == 10))))
      {
        Object localObject = GWindowTable.this.editor.getCellEditValue();
        GWindowTable.this.setValueAt(localObject, GWindowTable.this.selectRow, GWindowTable.this.selectCol);
        ((GWindow)GWindowTable.this.editor).hideWindow();
        GWindowTable.this.editor = null;
        return true;
      }

      return false;
    }

    public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
      super.mouseClick(paramInt, paramFloat1, paramFloat2);
      if (paramInt == 0) {
        findSelected(paramFloat1, paramFloat2);
        if ((this.findedRow >= 0) && (this.findedCol >= 0))
          GWindowTable.this.setSelect(this.findedRow, this.findedCol);
      }
    }

    public void keyboardKey(int paramInt, boolean paramBoolean) {
      super.keyboardKey(paramInt, paramBoolean);
      if (!paramBoolean) {
        if (paramInt == 10)
          GWindowTable.this.setSelect(GWindowTable.this.selectRow, GWindowTable.this.selectCol);
        return;
      }
      switch (paramInt) {
      case 38:
        if (GWindowTable.this.selectRow <= 0) break;
        GWindowTable.this.setSelect(GWindowTable.this.selectRow - 1, GWindowTable.this.selectCol); break;
      case 40:
        if (GWindowTable.this.selectRow >= GWindowTable.this.countRows() - 1) break;
        GWindowTable.this.setSelect(GWindowTable.this.selectRow + 1, GWindowTable.this.selectCol); break;
      case 37:
        if (GWindowTable.this.selectCol <= 0) break;
        GWindowTable.this.setSelect(GWindowTable.this.selectRow, GWindowTable.this.selectCol - 1); break;
      case 39:
        if (GWindowTable.this.selectCol >= GWindowTable.this.countColumns() - 1) break;
        GWindowTable.this.setSelect(GWindowTable.this.selectRow, GWindowTable.this.selectCol + 1); break;
      }
    }

    public void findSelected(float paramFloat1, float paramFloat2)
    {
      this.findedRow = -1;
      this.findedCol = -1;
      if ((GWindowTable.this.countColumns() == 0) || (GWindowTable.this.countRows() == 0)) return;
      if ((paramFloat1 < 0.0F) || (paramFloat2 < 0.0F)) return;
      float f1 = 0.0F;
      if (GWindowTable.this.hSB.isVisible()) f1 = -(int)GWindowTable.this.hSB.pos();
      int i = 0;
      for (; i < GWindowTable.this.countColumns(); i++) {
        GWindowTable.Column localColumn = (GWindowTable.Column)GWindowTable.this.columns.get(i);
        if ((f1 < paramFloat1) && (paramFloat1 <= f1 + localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx))
          break;
        f1 += localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      }
      if (i == GWindowTable.this.countColumns()) return;
      float f2 = 0.0F;
      if (GWindowTable.this.vSB.isVisible()) f2 = -(int)GWindowTable.this.vSB.pos();
      int j = 0;
      int k = GWindowTable.this.countRows();
      float f3;
      for (; j < k; j++) {
        f3 = GWindowTable.this.rowHeight(j);
        if (f2 + f3 > 0.0F)
          break;
        f2 += f3;
      }
      if ((GWindowTable.this.bRowAlign) && (f2 < 0.0F)) {
        f2 = 0.0F;
        j++;
      }
      for (; j < k; j++) {
        f3 = GWindowTable.this.rowHeight(j);
        if ((f2 < paramFloat2) && (paramFloat2 <= f2 + f3))
          break;
        f2 += f3;
      }
      if (j == k) return;
      this.findedRow = j;
      this.findedCol = i;
    }

    public void render() {
      if ((GWindowTable.this.countColumns() == 0) || (GWindowTable.this.countRows() == 0)) return;
      float f1 = 0.0F;
      if (GWindowTable.this.hSB.isVisible()) f1 = -(int)GWindowTable.this.hSB.pos();
      float f2 = 0.0F;
      if (GWindowTable.this.vSB.isVisible()) f2 = -(int)GWindowTable.this.vSB.pos();
      GWindowTable.Column localColumn1 = 0;
      for (; localColumn1 < GWindowTable.this.countColumns() - 1; localColumn1++) {
        localColumn2 = (GWindowTable.Column)GWindowTable.this.columns.get(localColumn1);
        if (f1 + localColumn2.jdField_win_of_type_ComMaddoxGwindowGRegion.dx > 0.0F)
          break;
        f1 += localColumn2.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      }
      GWindowTable.Column localColumn2 = localColumn1;
      float f3 = f1;
      for (; localColumn2 < GWindowTable.this.countColumns() - 1; localColumn2++) {
        GWindowTable.Column localColumn3 = (GWindowTable.Column)GWindowTable.this.columns.get(localColumn2);
        if (f3 + localColumn3.jdField_win_of_type_ComMaddoxGwindowGRegion.dx > this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx)
          break;
        f3 += localColumn3.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      }
      int i = 0;
      int j = GWindowTable.this.countRows();
      float f4;
      for (; i < j; i++) {
        f4 = GWindowTable.this.rowHeight(i);
        if (f2 + f4 > 0.0F)
          break;
        f2 += f4;
      }
      if ((GWindowTable.this.bRowAlign) && (f2 < 0.0F)) {
        f2 = 0.0F;
        i++;
      }
      this._clipRegion.x = 0.0F;
      this._clipRegion.y = 0.0F;
      for (; i < j; i++) {
        if (f2 > this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) break;
        f4 = GWindowTable.this.rowHeight(i);
        this._clipRegion.dy = f4;
        f3 = f1;
        for (GWindowTable.Column localColumn4 = localColumn1; localColumn4 <= localColumn2; localColumn4++) {
          GWindowTable.Column localColumn5 = (GWindowTable.Column)GWindowTable.this.columns.get(localColumn4);
          this._clipRegion.dx = localColumn5.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
          GPoint localGPoint = this.root.C.org;
          localGPoint.add(f3, f2);
          if (pushClipRegion(this._clipRegion, true, 0.0F)) {
            boolean bool = false;
            if (GWindowTable.this.bSelecting) {
              if (GWindowTable.this.bSelectRow)
                bool = i == GWindowTable.this.selectRow;
              else {
                bool = (i == GWindowTable.this.selectRow) && (localColumn4 == GWindowTable.this.selectCol);
              }
            }
            if ((GWindowTable.this.editor == null) || (i != GWindowTable.this.selectRow) || (localColumn4 != GWindowTable.this.selectCol))
            {
              GWindowTable.this.renderCell(i, localColumn4, bool, this._clipRegion.dx, this._clipRegion.dy);
            }

            popClip();
          }
          localGPoint.sub(f3, f2);
          f3 += localColumn5.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        }
        f2 += f4;
      }
    }

    public void setPosEditor()
    {
      if (GWindowTable.this.editor == null) return;
      GWindow localGWindow = (GWindow)GWindowTable.this.editor;
      float f1 = 0.0F;
      if (GWindowTable.this.hSB.isVisible()) f1 = -(int)GWindowTable.this.hSB.pos();
      float f2 = 0.0F;
      if (GWindowTable.this.vSB.isVisible()) f2 = -(int)GWindowTable.this.vSB.pos();
      for (int i = 0; i < GWindowTable.this.selectCol; i++) {
        localColumn = (GWindowTable.Column)GWindowTable.this.columns.get(i);
        f1 += localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      }
      GWindowTable.Column localColumn = (GWindowTable.Column)GWindowTable.this.columns.get(GWindowTable.this.selectCol);
      localGWindow.setSize(localColumn.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, GWindowTable.this.rowHeight(GWindowTable.this.selectRow));

      int j = 0;
      int k = GWindowTable.this.countRows();
      float f3;
      for (; j < k; j++) {
        f3 = GWindowTable.this.rowHeight(j);
        if (f2 + f3 > 0.0F)
          break;
        f2 += f3;
      }
      if ((GWindowTable.this.bRowAlign) && (f2 < 0.0F)) {
        f2 = 0.0F;
        j++;
        if (j == GWindowTable.this.selectRow) {
          _setPosEditor(localGWindow, f1, f2, GWindowTable.this.rowHeight(j));
          return;
        }
      }
      if (j > GWindowTable.this.selectRow)
        _setPosEditor(localGWindow, f1, -GWindowTable.this.rowHeight(GWindowTable.this.selectRow), GWindowTable.this.rowHeight(GWindowTable.this.selectRow));
      for (; j < k; j++) {
        f3 = GWindowTable.this.rowHeight(j);
        if (j == GWindowTable.this.selectRow) {
          _setPosEditor(localGWindow, f1, f2, f3);
          return;
        }
        f2 += f3;
      }
    }

    private void _setPosEditor(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
      if ((GWindowTable.this.bRowAlign) && 
        (paramFloat2 < 0.0F) && (paramFloat2 + paramFloat3 > 0.0F)) {
        paramFloat2 = 0.0F;
      }
      paramGWindow.setPos(paramFloat1, paramFloat2);
    }

    public Client(GWindow arg2) {
      super();
    }
  }
}