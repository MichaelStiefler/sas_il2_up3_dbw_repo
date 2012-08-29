// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowTable.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowEditControl, GWindowCellEdit, GWindowButton, 
//            GWindowHScrollBar, GWindowVScrollBar, GRegion, GWindowRoot, 
//            GFont, GWindowLookAndFeel, GSize, GCanvas, 
//            GPoint

public class GWindowTable extends com.maddox.gwindow.GWindow
{
    public class Column extends com.maddox.gwindow.GWindowButton
    {

        public void setRelativeDx(float f)
        {
            relDx = f;
        }

        public float getRelativeDx()
        {
            return relDx;
        }

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = columns.indexOf(this);
                columnClicked(k);
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        private boolean isSizableArea(float f)
        {
            if(!bColumnsSizable)
                return false;
            float f1 = lookAndFeel().metric() / 2.0F;
            if(f > f1 && f < win.dx - f1)
                return false;
            bSizingLeft = f <= f1;
            if(bSizingLeft && this == columns.get(0))
                return false;
            return !bColAlign || bSizingLeft || this != columns.get(columns.size() - 1);
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            if(i == 0 && (isMouseCaptured() || isSizableArea(f) && !bDown))
            {
                mouseCapture(flag);
                return;
            } else
            {
                super.mouseButton(i, flag, f, f1);
                return;
            }
        }

        public void mouseMove(float f, float f1)
        {
            super.mouseMove(f, f1);
            if(isMouseCaptured())
            {
                mouseCursor = 11;
                int i = columns.indexOf(this);
                if(i < 0)
                    return;
                float f2 = root.mouseStep.dx;
                if(bSizingLeft)
                {
                    float f3 = win.dx - f2;
                    if(f3 < 1.0F)
                        f2 = win.dx - 1.0F;
                    com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(i - 1);
                    f3 = column.win.dx + f2;
                    if(f3 < 1.0F)
                        f2 = -(column.win.dx - 1.0F);
                    column.setSize(column.win.dx + f2, column.win.dy);
                    setSize(win.dx - f2, win.dy);
                } else
                {
                    float f4 = win.dx + f2;
                    if(f4 < 1.0F)
                        f2 = -(win.dx - 1.0F);
                    if(i < columns.size() - 1)
                    {
                        com.maddox.gwindow.Column column1 = (com.maddox.gwindow.Column)columns.get(i + 1);
                        float f5 = column1.win.dx - f2;
                        if(f5 < 1.0F)
                            f2 = column1.win.dx - 1.0F;
                        column1.setSize(column1.win.dx - f2, column1.win.dy);
                    }
                    setSize(win.dx + f2, win.dy);
                }
                parentWindow.resized();
            } else
            if(isSizableArea(f) && !isMouseDownAny(0))
                mouseCursor = 11;
            else
                mouseCursor = 1;
        }

        private boolean bSizingLeft;
        private float relDx;

        public Column(com.maddox.gwindow.GWindow gwindow, java.lang.String s, java.lang.String s1)
        {
            super(gwindow, s, s1);
            relDx = -1F;
            com.maddox.gwindow.GSize gsize = getMinSize();
            setSize(gsize.dx, gsize.dy);
            bDrawActive = false;
        }
    }

    public class Client extends com.maddox.gwindow.GWindow
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(editor != null && gwindow == editor && (bEditExitOnFocusExit && i == 16 || bEditExitOnEnterPress && i == 11 && j == 10))
            {
                java.lang.Object obj = editor.getCellEditValue();
                setValueAt(obj, selectRow, selectCol);
                ((com.maddox.gwindow.GWindow)editor).hideWindow();
                editor = null;
                return true;
            } else
            {
                return false;
            }
        }

        public void mouseClick(int i, float f, float f1)
        {
            super.mouseClick(i, f, f1);
            if(i == 0)
            {
                findSelected(f, f1);
                if(findedRow >= 0 && findedCol >= 0)
                    setSelect(findedRow, findedCol);
            }
        }

        public void keyboardKey(int i, boolean flag)
        {
            super.keyboardKey(i, flag);
            if(!flag)
            {
                if(i == 10)
                    setSelect(selectRow, selectCol);
                return;
            }
            switch(i)
            {
            default:
                break;

            case 38: // '&'
                if(selectRow > 0)
                    setSelect(selectRow - 1, selectCol);
                break;

            case 40: // '('
                if(selectRow < countRows() - 1)
                    setSelect(selectRow + 1, selectCol);
                break;

            case 37: // '%'
                if(selectCol > 0)
                    setSelect(selectRow, selectCol - 1);
                break;

            case 39: // '\''
                if(selectCol < countColumns() - 1)
                    setSelect(selectRow, selectCol + 1);
                break;
            }
        }

        public void findSelected(float f, float f1)
        {
            findedRow = -1;
            findedCol = -1;
            if(countColumns() == 0 || countRows() == 0)
                return;
            if(f < 0.0F || f1 < 0.0F)
                return;
            float f2 = 0.0F;
            if(hSB.isVisible())
                f2 = -(int)hSB.pos();
            int i = 0;
            do
            {
                if(i >= countColumns())
                    break;
                com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(i);
                if(f2 < f && f <= f2 + column.win.dx)
                    break;
                f2 += column.win.dx;
                i++;
            } while(true);
            if(i == countColumns())
                return;
            float f3 = 0.0F;
            if(vSB.isVisible())
                f3 = -(int)vSB.pos();
            int j = 0;
            int k = countRows();
            do
            {
                if(j >= k)
                    break;
                float f4 = rowHeight(j);
                if(f3 + f4 > 0.0F)
                    break;
                f3 += f4;
                j++;
            } while(true);
            if(bRowAlign && f3 < 0.0F)
            {
                f3 = 0.0F;
                j++;
            }
            do
            {
                if(j >= k)
                    break;
                float f5 = rowHeight(j);
                if(f3 < f1 && f1 <= f3 + f5)
                    break;
                f3 += f5;
                j++;
            } while(true);
            if(j == k)
            {
                return;
            } else
            {
                findedRow = j;
                findedCol = i;
                return;
            }
        }

        public void render()
        {
            if(countColumns() == 0 || countRows() == 0)
                return;
            float f = 0.0F;
            if(hSB.isVisible())
                f = -(int)hSB.pos();
            float f1 = 0.0F;
            if(vSB.isVisible())
                f1 = -(int)vSB.pos();
            int i = 0;
            do
            {
                if(i >= countColumns() - 1)
                    break;
                com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(i);
                if(f + column.win.dx > 0.0F)
                    break;
                f += column.win.dx;
                i++;
            } while(true);
            int j = i;
            float f2 = f;
            do
            {
                if(j >= countColumns() - 1)
                    break;
                com.maddox.gwindow.Column column1 = (com.maddox.gwindow.Column)columns.get(j);
                if(f2 + column1.win.dx > win.dx)
                    break;
                f2 += column1.win.dx;
                j++;
            } while(true);
            int k = 0;
            int l = countRows();
            do
            {
                if(k >= l)
                    break;
                float f4 = rowHeight(k);
                if(f1 + f4 > 0.0F)
                    break;
                f1 += f4;
                k++;
            } while(true);
            if(bRowAlign && f1 < 0.0F)
            {
                f1 = 0.0F;
                k++;
            }
            _clipRegion.x = 0.0F;
            _clipRegion.y = 0.0F;
            for(; k < l && f1 <= win.dy; k++)
            {
                float f5 = rowHeight(k);
                _clipRegion.dy = f5;
                float f3 = f;
                for(int i1 = i; i1 <= j; i1++)
                {
                    com.maddox.gwindow.Column column2 = (com.maddox.gwindow.Column)columns.get(i1);
                    _clipRegion.dx = column2.win.dx;
                    com.maddox.gwindow.GPoint gpoint = root.C.org;
                    gpoint.add(f3, f1);
                    if(pushClipRegion(_clipRegion, true, 0.0F))
                    {
                        boolean flag = false;
                        if(bSelecting)
                            if(bSelectRow)
                                flag = k == selectRow;
                            else
                                flag = k == selectRow && i1 == selectCol;
                        if(editor == null || k != selectRow || i1 != selectCol)
                            renderCell(k, i1, flag, _clipRegion.dx, _clipRegion.dy);
                        popClip();
                    }
                    gpoint.sub(f3, f1);
                    f3 += column2.win.dx;
                }

                f1 += f5;
            }

        }

        public void setPosEditor()
        {
            if(editor == null)
                return;
            com.maddox.gwindow.GWindow gwindow = (com.maddox.gwindow.GWindow)editor;
            float f = 0.0F;
            if(hSB.isVisible())
                f = -(int)hSB.pos();
            float f1 = 0.0F;
            if(vSB.isVisible())
                f1 = -(int)vSB.pos();
            for(int i = 0; i < selectCol; i++)
            {
                com.maddox.gwindow.Column column1 = (com.maddox.gwindow.Column)columns.get(i);
                f += column1.win.dx;
            }

            com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(selectCol);
            gwindow.setSize(column.win.dx, rowHeight(selectRow));
            int j = 0;
            int k = countRows();
            do
            {
                if(j >= k)
                    break;
                float f2 = rowHeight(j);
                if(f1 + f2 > 0.0F)
                    break;
                f1 += f2;
                j++;
            } while(true);
            if(bRowAlign && f1 < 0.0F)
            {
                f1 = 0.0F;
                if(++j == selectRow)
                {
                    _setPosEditor(gwindow, f, f1, rowHeight(j));
                    return;
                }
            }
            if(j > selectRow)
                _setPosEditor(gwindow, f, -rowHeight(selectRow), rowHeight(selectRow));
            for(; j < k; j++)
            {
                float f3 = rowHeight(j);
                if(j == selectRow)
                {
                    _setPosEditor(gwindow, f, f1, f3);
                    return;
                }
                f1 += f3;
            }

        }

        private void _setPosEditor(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
        {
            if(bRowAlign && f1 < 0.0F && f1 + f2 > 0.0F)
                f1 = 0.0F;
            gwindow.setPos(f, f1);
        }

        public int findedRow;
        public int findedCol;
        private com.maddox.gwindow.GRegion _clipRegion;

        public Client(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            _clipRegion = new GRegion();
        }
    }


    public int countColumns()
    {
        return columns.size();
    }

    public int countRows()
    {
        return 0;
    }

    public float rowHeight(int i)
    {
        return (float)(int)(root.textFonts[0].height * 1.2F);
    }

    public float fullClientHeight()
    {
        return rowHeight(0) * (float)countRows();
    }

    public void columnClicked(int i)
    {
    }

    public java.lang.Object getValueAt(int i, int j)
    {
        return null;
    }

    public void setValueAt(java.lang.Object obj, int i, int j)
    {
    }

    public boolean isCellEditable(int i, int j)
    {
        return false;
    }

    public com.maddox.gwindow.GWindowCellEdit getCellEdit(int i, int j)
    {
        if(!isCellEditable(i, j))
            return null;
        java.lang.Object obj = getValueAt(i, j);
        if(obj == null)
        {
            return null;
        } else
        {
            com.maddox.gwindow.GWindowCellEdit gwindowcelledit = (com.maddox.gwindow.GWindowCellEdit)wClient.create(new GWindowEditControl());
            gwindowcelledit.setCellEditValue(obj);
            ((com.maddox.gwindow.GWindow)gwindowcelledit).activateWindow();
            return gwindowcelledit;
        }
    }

    public void renderCell(int i, int j, boolean flag, float f, float f1)
    {
        java.lang.Object obj = getValueAt(i, j);
        if(obj == null)
            return;
        java.lang.String s = obj.toString();
        setCanvasFont(0);
        if(flag)
        {
            setCanvasColorBLACK();
            draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            setCanvasColorWHITE();
            draw(0.0F, 0.0F, f, f1, 0, s);
        } else
        {
            setCanvasColorBLACK();
            draw(0.0F, 0.0F, f, f1, 0, s);
        }
    }

    public com.maddox.gwindow.Column addColumn(java.lang.String s, java.lang.String s1)
    {
        com.maddox.gwindow.Column column = new Column(this, s, s1);
        columns.add(column);
        resized();
        return column;
    }

    public com.maddox.gwindow.Column addColumn(int i, java.lang.String s, java.lang.String s1)
    {
        com.maddox.gwindow.Column column = new Column(this, s, s1);
        columns.add(i, column);
        resized();
        return column;
    }

    public void removeColumn(int i)
    {
        columns.remove(i);
        resized();
    }

    public com.maddox.gwindow.Column getColumn(int i)
    {
        return (com.maddox.gwindow.Column)columns.get(i);
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(i == 17 && vSB != null && vSB.isVisible())
        {
            vSB.scrollDz(root.mouseRelMoveZ);
            return true;
        }
        if(gwindow == hSB)
        {
            if(i == 2)
            {
                resized();
                return true;
            }
        } else
        if(gwindow == vSB && i == 2)
        {
            resized();
            return true;
        }
        return false;
    }

    public void setSelect(int i, int j)
    {
        if(i == selectRow && j == selectCol)
        {
            if(selectRow < 0 || selectCol < 0)
                return;
            if(isCellEditable(i, j) && editor == null)
            {
                editor = getCellEdit(selectRow, selectCol);
                if(editor != null)
                    wClient.setPosEditor();
            }
            return;
        }
        if(editor != null)
        {
            java.lang.Object obj = editor.getCellEditValue();
            setValueAt(obj, selectRow, selectCol);
            ((com.maddox.gwindow.GWindow)editor).hideWindow();
            editor = null;
        }
        selectRow = i;
        selectCol = j;
        if(selectRow < 0 || selectCol < 0)
            return;
        editor = getCellEdit(selectRow, selectCol);
        if(editor != null)
            wClient.setPosEditor();
    }

    public void alignColumns()
    {
        if(columns.size() == 0)
            return;
        float f = 0.0F;
        float f1 = 0.0F;
        for(int i = 0; i < columns.size(); i++)
        {
            com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(i);
            com.maddox.gwindow.GSize gsize = column.getMinSize();
            float f4 = column.getRelativeDx();
            if(f4 > 0.0F)
                f += f4;
            else
                f += gsize.dx;
            f1 = gsize.dy;
        }

        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f2 = gregion.dx;
        if(vSB.isVisible())
            f2 -= lookAndFeel().getVScrollBarW();
        float f3 = f2 / f;
        float f5 = gregion.x;
        float f6 = f5;
        float f7 = gregion.y;
        com.maddox.gwindow.Column column1 = null;
        for(int j = 0; j < columns.size(); j++)
        {
            column1 = (com.maddox.gwindow.Column)columns.get(j);
            com.maddox.gwindow.GSize gsize1 = column1.getMinSize();
            float f8 = column1.getRelativeDx();
            if(f8 > 0.0F)
                column1.setSize(f8 * f3, f1);
            else
                column1.setSize(gsize1.dx * f3, f1);
            column1.setPos(f6, f7);
            f6 += column1.win.dx;
        }

        if((column1.win.dx + column1.win.x) - f5 != f2)
            column1.setSize((f2 - column1.win.x) + f5, f1);
    }

    private void _alignColumns()
    {
        if(columns.size() == 0)
            return;
        float f = 0.0F;
        float f1 = 0.0F;
        for(int i = 0; i < columns.size(); i++)
        {
            com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(i);
            com.maddox.gwindow.GSize gsize = column.getMinSize();
            f += column.win.dx;
            f1 = gsize.dy;
        }

        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f2 = gregion.dx;
        if(vSB.isVisible())
            f2 -= lookAndFeel().getVScrollBarW();
        float f3 = f2 / f;
        float f4 = gregion.x;
        float f5 = f4;
        float f6 = gregion.y;
        com.maddox.gwindow.Column column1 = null;
        for(int j = 0; j < columns.size(); j++)
        {
            column1 = (com.maddox.gwindow.Column)columns.get(j);
            column1.setSize(column1.win.dx * f3, f1);
            column1.setPos(f5, f6);
            f5 += column1.win.dx;
        }

        if((column1.win.dx + column1.win.x) - f4 != f2)
            column1.setSize((f2 - column1.win.x) + f4, f1);
    }

    public void resized()
    {
        getClientRegion(_clientRegion, 0.0F);
        float f = 0.0F;
        if(columns.size() > 0)
        {
            com.maddox.gwindow.Column column = (com.maddox.gwindow.Column)columns.get(0);
            com.maddox.gwindow.GSize gsize = column.getMinSize();
            f = gsize.dy;
        }
        if(bColumnsSizable)
        {
            float f1 = 0.0F;
            if(!bColAlign)
            {
                for(int i = 0; i < columns.size(); i++)
                {
                    com.maddox.gwindow.Column column1 = (com.maddox.gwindow.Column)columns.get(i);
                    f1 += column1.win.dx;
                }

            }
            boolean flag = false;
            boolean flag1 = false;
            for(int j = 0; j < 2; j++)
            {
                float f2 = _clientRegion.dy - f;
                if(flag1)
                    f2 -= lookAndFeel().getHScrollBarH();
                float f4 = _clientRegion.dx;
                if(flag)
                    f4 -= lookAndFeel().getVScrollBarW();
                if(!bColAlign)
                    flag1 = f1 > f4;
                flag = fullClientHeight() > f2;
            }

            float f3 = _clientRegion.dy - f;
            if(flag1)
                f3 -= lookAndFeel().getHScrollBarH();
            float f5 = _clientRegion.dx;
            if(flag)
                f5 -= lookAndFeel().getVScrollBarW();
            if(flag)
            {
                vSB.setRange(0.0F, fullClientHeight(), f3, vSB.scroll, vSB.pos);
                vSB.setSize(lookAndFeel().getVScrollBarW(), f3 + f);
                vSB.setPos(_clientRegion.x + f5, _clientRegion.y);
                vSB.showWindow();
            } else
            {
                vSB.setPos(0.0F, false);
                vSB.hideWindow();
            }
            float f6;
            if(flag1)
            {
                hSB.setRange(0.0F, f1, f5, lookAndFeel().metric() / 2.0F, hSB.pos);
                hSB.setSize(f5, lookAndFeel().getHScrollBarH());
                hSB.setPos(_clientRegion.x, _clientRegion.y + f + f3);
                hSB.showWindow();
                f6 = _clientRegion.x - (float)(int)hSB.pos();
            } else
            {
                hSB.setPos(0.0F, false);
                hSB.hideWindow();
                f6 = _clientRegion.x;
            }
            if(bColAlign)
            {
                _alignColumns();
                endColumn.hideWindow();
            } else
            {
                for(int k = 0; k < columns.size(); k++)
                {
                    com.maddox.gwindow.Column column2 = (com.maddox.gwindow.Column)columns.get(k);
                    column2.setPos(f6, _clientRegion.y);
                    column2.setSize(column2.win.dx, f);
                    f6 += column2.win.dx;
                }

                if(f6 < f5 + _clientRegion.x && f > 0.0F)
                {
                    endColumn.setPos(f6, _clientRegion.y);
                    endColumn.setSize((f5 + _clientRegion.x) - f6, f);
                    endColumn.showWindow();
                } else
                {
                    endColumn.hideWindow();
                }
            }
            if(flag1 && flag)
            {
                button.setPos(f5 + _clientRegion.x, _clientRegion.y + f + f3);
                button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
                button.showWindow();
            } else
            {
                button.hideWindow();
            }
            wClient.setSize(f5, f3);
        } else
        {
            if(_clientRegion.dy - f < fullClientHeight())
            {
                vSB.setRange(0.0F, fullClientHeight(), _clientRegion.dy - f, vSB.scroll, vSB.pos);
                vSB.setSize(lookAndFeel().getVScrollBarW(), _clientRegion.dy);
                vSB.setPos((_clientRegion.x + _clientRegion.dx) - lookAndFeel().getVScrollBarW(), _clientRegion.y);
                vSB.showWindow();
                wClient.setSize(_clientRegion.dx - lookAndFeel().getVScrollBarW(), _clientRegion.dy - f);
            } else
            {
                vSB.setPos(0.0F, false);
                vSB.hideWindow();
                wClient.setSize(_clientRegion.dx, _clientRegion.dy - f);
            }
            alignColumns();
        }
        wClient.setPos(_clientRegion.x, _clientRegion.y + f);
        wClient.setPosEditor();
    }

    public void afterCreated()
    {
        endColumn = new GWindowButton(this);
        endColumn.bAcceptsKeyFocus = false;
        endColumn.bDrawActive = false;
        endColumn.bDrawOnlyUP = true;
        button = new GWindowButton(this);
        button.bAcceptsKeyFocus = false;
        button.bAlwaysOnTop = true;
        button.bDrawOnlyUP = true;
        button.bDrawActive = false;
        button.hideWindow();
        hSB = new GWindowHScrollBar(this);
        hSB.bAlwaysOnTop = true;
        hSB.hideWindow();
        vSB = new GWindowVScrollBar(this);
        vSB.bAlwaysOnTop = true;
        vSB.hideWindow();
        wClient = new Client(this);
        resized();
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        return lookAndFeel().getClientRegion(this, gregion, f);
    }

    public GWindowTable(com.maddox.gwindow.GWindow gwindow)
    {
        bColumnsSizable = true;
        bColAlign = true;
        bRowAlign = true;
        bSelecting = true;
        bSelectRow = false;
        bEditExitOnFocusExit = true;
        bEditExitOnEnterPress = true;
        selectRow = -1;
        selectCol = -1;
        columns = new ArrayList();
        _clientRegion = new GRegion();
        doNew(gwindow);
    }

    public GWindowTable(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3)
    {
        bColumnsSizable = true;
        bColAlign = true;
        bRowAlign = true;
        bSelecting = true;
        bSelectRow = false;
        bEditExitOnFocusExit = true;
        bEditExitOnEnterPress = true;
        selectRow = -1;
        selectCol = -1;
        columns = new ArrayList();
        _clientRegion = new GRegion();
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public boolean bColumnsSizable;
    public boolean bColAlign;
    public boolean bRowAlign;
    public boolean bSelecting;
    public boolean bSelectRow;
    public boolean bEditExitOnFocusExit;
    public boolean bEditExitOnEnterPress;
    public int selectRow;
    public int selectCol;
    public com.maddox.gwindow.GWindowCellEdit editor;
    public com.maddox.gwindow.Client wClient;
    public com.maddox.gwindow.GWindowVScrollBar vSB;
    public com.maddox.gwindow.GWindowHScrollBar hSB;
    public java.util.ArrayList columns;
    public com.maddox.gwindow.GWindowButton endColumn;
    public com.maddox.gwindow.GWindowButton button;
    private com.maddox.gwindow.GRegion _clientRegion;
}
