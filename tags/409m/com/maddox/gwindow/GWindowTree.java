// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowTree.java

package com.maddox.gwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GTreePath, GWindowEditControl, GWindowCellEdit, 
//            GWindowButton, GWindowHScrollBar, GWindowVScrollBar, GRegion, 
//            GTreeModelListener, GWindowRoot, GFont, GTreeModel, 
//            GSize, GWindowLookAndFeel

public class GWindowTree extends com.maddox.gwindow.GWindow
    implements com.maddox.gwindow.GTreeModelListener
{
    public class Client extends com.maddox.gwindow.GWindow
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(editor != null && gwindow == editor && (bEditExitOnFocusExit && i == 16 || bEditExitOnEnterPress && i == 11 && j == 10))
            {
                endEditing(false);
                return true;
            } else
            {
                return false;
            }
        }

        public void mouseRelMove(float f, float f1, float f2)
        {
            super.mouseRelMove(f, f1, f2);
            if(editor == null && vSB != null && vSB.isVisible())
                vSB.scrollDz(f2);
        }

        public void mouseClick(int i, float f, float f1)
        {
            super.mouseClick(i, f, f1);
            int j = findRow(f1);
            if(j < 0)
                return;
            com.maddox.gwindow.GTreePath gtreepath = (com.maddox.gwindow.GTreePath)rows.get(j);
            int k = model.getRoot().getPathCount();
            int l = gtreepath.getPathCount();
            float f2 = (float)(l - k) * getTabStep() + getBorderSpace();
            if(f < f2)
            {
                if(f < f2 - getTabStep())
                    return;
                if(model.isLeaf(gtreepath))
                    return;
            } else
            {
                computeSize(gtreepath);
                if(bDrawIcons)
                    f2 += getTabStep();
                f2 += _sizePathDx;
                if(f > f2)
                    return;
                if(!gtreepath.equals(selectPath))
                {
                    setSelect(j);
                    return;
                }
                if(model.isEditable(gtreepath))
                {
                    beginEditor();
                    return;
                }
            }
            if(isExpanded(gtreepath))
            {
                expandedState.remove(gtreepath);
                removeRows(j + 1, gtreepath);
            } else
            {
                expandedState.put(gtreepath, null);
                addRows(j + 1, gtreepath);
            }
            setSelect(j);
            computeSize();
        }

        public void keyboardKey(int i, boolean flag)
        {
            super.keyboardKey(i, flag);
            if(selectRow < 0 || editor != null)
                return;
            if(!flag)
            {
                if(i == 10)
                {
                    if(model.isEditable(selectPath))
                    {
                        beginEditor();
                        return;
                    }
                    if(!model.isLeaf(selectPath))
                    {
                        if(isExpanded(selectPath))
                        {
                            expandedState.remove(selectPath);
                            removeRows(selectRow + 1, selectPath);
                        } else
                        {
                            expandedState.put(selectPath, null);
                            addRows(selectRow + 1, selectPath);
                        }
                        computeSize();
                    }
                }
                return;
            }
            switch(i)
            {
            default:
                break;

            case 33: // '!'
                if(vSB.isVisible())
                {
                    int j = selectRow - (int)(vSB.posVisible / getMonoHeightRows());
                    if(j < 0)
                        j = 0;
                    setSelect(j);
                    break;
                }
                // fall through

            case 36: // '$'
                if(selectRow != 0)
                    setSelect(0);
                break;

            case 34: // '"'
                if(vSB.isVisible())
                {
                    int k = selectRow + (int)(vSB.posVisible / getMonoHeightRows());
                    if(k > rows.size() - 1)
                        k = rows.size() - 1;
                    setSelect(k);
                    break;
                }
                // fall through

            case 35: // '#'
                if(selectRow != rows.size() - 1)
                    setSelect(rows.size() - 1);
                break;

            case 38: // '&'
                if(selectRow > 0)
                    setSelect(selectRow - 1);
                break;

            case 40: // '('
                if(selectRow < rows.size() - 1)
                    setSelect(selectRow + 1);
                break;

            case 37: // '%'
                if(!model.isLeaf(selectPath) && isExpanded(selectPath))
                {
                    expandedState.remove(selectPath);
                    removeRows(selectRow + 1, selectPath);
                    computeSize();
                    break;
                }
                if(selectRow > 0)
                    setSelect(selectRow - 1);
                break;

            case 39: // '\''
                if(!model.isLeaf(selectPath) && !isExpanded(selectPath))
                {
                    expandedState.put(selectPath, null);
                    addRows(selectRow + 1, selectPath);
                    computeSize();
                    break;
                }
                if(selectRow < rows.size() - 1)
                    setSelect(selectRow + 1);
                break;
            }
        }

        public void render()
        {
            renderClient();
        }

        public Client(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public float getBorderSpace()
    {
        return borderSpace;
    }

    public float getTabStep()
    {
        return (float)(java.lang.Math.round(root.textFonts[font].height + 3F) & -4);
    }

    public float getMonoHeightRows()
    {
        return (float)(java.lang.Math.round(root.textFonts[font].height + 3F) & -4);
    }

    public void setRootVisible(boolean flag)
    {
        if(bRootVisible == flag)
        {
            return;
        } else
        {
            bRootVisible = flag;
            setModel(model);
            return;
        }
    }

    public boolean isRootVisible()
    {
        return bRootVisible;
    }

    public boolean isExpanded(com.maddox.gwindow.GTreePath gtreepath)
    {
        return expandedState.containsKey(gtreepath);
    }

    public boolean isVisible(com.maddox.gwindow.GTreePath gtreepath)
    {
        if(gtreepath == null)
            return false;
        for(; gtreepath != null; gtreepath = gtreepath.getParentPath())
            if(!isExpanded(gtreepath))
                return false;

        return true;
    }

    public int setVisible(com.maddox.gwindow.GTreePath gtreepath)
    {
        return setVisible(gtreepath, true);
    }

    public int setVisible(com.maddox.gwindow.GTreePath gtreepath, boolean flag)
    {
        if(gtreepath == null)
            return -1;
        endEditing(true);
        if(selectRow >= 0)
        {
            selectPath = null;
            selectRow = -1;
        }
        for(int i = 0; i < rows.size(); i++)
        {
            com.maddox.gwindow.GTreePath gtreepath1 = (com.maddox.gwindow.GTreePath)rows.get(i);
            if(gtreepath1.equals(gtreepath))
            {
                computeSize();
                if(flag)
                    notify(2, 0);
                return i;
            }
            if(gtreepath1.isDescendant(gtreepath) && !isExpanded(gtreepath1))
            {
                expandedState.put(gtreepath1, null);
                addRows(i + 1, gtreepath1);
            }
        }

        computeSize();
        if(flag)
            notify(2, 0);
        return -1;
    }

    public boolean isSelect(com.maddox.gwindow.GTreePath gtreepath)
    {
        if(selectPath == null)
            return false;
        else
            return selectPath.equals(gtreepath);
    }

    public boolean isSelect(int i)
    {
        return i == selectRow;
    }

    public void setSelect(com.maddox.gwindow.GTreePath gtreepath)
    {
        setSelect(gtreepath, true);
    }

    public void setSelect(com.maddox.gwindow.GTreePath gtreepath, boolean flag)
    {
        if(gtreepath != null)
        {
            int i = setVisible(gtreepath, false);
            if(i >= 0)
                setSelect(i, flag);
        }
    }

    public void setSelect(int i)
    {
        setSelect(i, true);
    }

    public void setSelect(int i, boolean flag)
    {
        if(i < 0)
            return;
        com.maddox.gwindow.GTreePath gtreepath = (com.maddox.gwindow.GTreePath)rows.get(i);
        if(gtreepath.equals(selectPath))
            return;
        endEditing(false);
        selectPath = gtreepath;
        selectRow = i;
        if(vSB.isVisible())
        {
            float f = 0.0F;
            for(int j = 0; j < selectRow; j++)
            {
                com.maddox.gwindow.GTreePath gtreepath1 = (com.maddox.gwindow.GTreePath)rows.get(j);
                f += computeHeight(gtreepath1);
            }

            float f2 = f + computeHeight(selectPath);
            if(f < vSB.pos())
            {
                getClientRegion(_clientRegion, 0.0F);
                vSB.setPos((f - getBorderSpace()) + _clientRegion.y, true);
            } else
            if(f2 > vSB.pos() + vSB.posVisible)
            {
                getClientRegion(_clientRegion, 0.0F);
                vSB.setPos((f2 - vSB.posVisible) + getBorderSpace() + _clientRegion.y, true);
            }
        }
        if(hSB.isVisible())
        {
            float f1 = getBorderSpace();
            f1 += (float)(gtreepath.getPathCount() - model.getRoot().getPathCount()) * getTabStep();
            if(f1 < hSB.pos() || f1 > hSB.pos() + hSB.posVisible)
            {
                getClientRegion(_clientRegion, 0.0F);
                hSB.setPos((f1 - getBorderSpace()) + _clientRegion.x, true);
            }
        }
        if(flag)
            notify(2, 0);
    }

    public void setModel(com.maddox.gwindow.GTreeModel gtreemodel)
    {
        boolean flag = false;
        if(model != null)
        {
            rows.clear();
            expandedState.clear();
            endEditing(true);
            selectPath = null;
            selectRow = -1;
            flag = true;
            model.removeListener(this);
        }
        model = gtreemodel;
        if(model != null)
        {
            expandedState.put(model.getRoot(), null);
            onModelChanged();
            model.addListener(this);
        }
        if(flag)
            notify(2, 0);
    }

    public void treeModelChanged(com.maddox.gwindow.GTreeModel gtreemodel)
    {
        if(model != gtreemodel)
        {
            return;
        } else
        {
            onModelChanged();
            return;
        }
    }

    public void onModelChanged()
    {
        boolean flag = selectRow >= 0;
        rows.clear();
        int i = 0;
        com.maddox.gwindow.GTreePath gtreepath = model.getRoot();
        if(gtreepath == null)
        {
            computeSize();
            return;
        }
        selectRow = -1;
        if(bRootVisible)
        {
            if(gtreepath.equals(selectPath))
                selectRow = i;
            rows.add(i++, gtreepath);
        }
        addRows(i, gtreepath);
        if(selectRow < 0)
        {
            endEditing(true);
            selectPath = null;
        }
        computeSize();
        if(flag)
            notify(2, 0);
    }

    protected int addRows(int i, com.maddox.gwindow.GTreePath gtreepath)
    {
        if(!isExpanded(gtreepath))
            return i;
        if(model.isLeaf(gtreepath))
            return i;
        int j = model.getChildCount(gtreepath);
        for(int k = 0; k < j; k++)
        {
            java.lang.Object obj = model.getChild(gtreepath, k);
            if(obj != null)
            {
                com.maddox.gwindow.GTreePath gtreepath1 = gtreepath.pathByAddingChild(obj);
                if(gtreepath1.equals(selectPath))
                    selectRow = i;
                rows.add(i++, gtreepath1);
                i = addRows(i, gtreepath1);
            }
        }

        return i;
    }

    protected void removeRows(int i, com.maddox.gwindow.GTreePath gtreepath)
    {
        boolean flag = false;
        for(; i < rows.size(); rows.remove(i))
        {
            com.maddox.gwindow.GTreePath gtreepath1 = (com.maddox.gwindow.GTreePath)rows.get(i);
            if(!gtreepath.isDescendant(gtreepath1))
                break;
            if(gtreepath1.equals(selectPath))
            {
                endEditing(true);
                selectPath = null;
                selectRow = -1;
                flag = true;
            }
        }

        if(flag)
            notify(2, 0);
    }

    public void computeSize()
    {
        int i = rows.size();
        if(i == 0)
        {
            wClient.setSize(getTabStep() + 2.0F * borderSpace, getMonoHeightRows() + 2.0F * borderSpace);
            resized();
            return;
        }
        float f = 0.0F;
        float f1 = 0.0F;
        com.maddox.gwindow.GTreePath gtreepath = model.getRoot();
        int j = gtreepath.getPathCount();
        for(int k = 0; k < i; k++)
        {
            com.maddox.gwindow.GTreePath gtreepath1 = (com.maddox.gwindow.GTreePath)rows.get(k);
            computeSize(gtreepath1);
            if(bDrawIcons)
                _sizePathDx += getTabStep();
            _sizePathDx += (float)(gtreepath1.getPathCount() - j) * getTabStep();
            if(f < _sizePathDx)
                f = _sizePathDx;
            f1 += _sizePathDy;
        }

        wClient.setSize(f + 2.0F * borderSpace, f1 + 2.0F * borderSpace);
        resized();
    }

    public void computeSize(com.maddox.gwindow.GTreePath gtreepath)
    {
        boolean flag = true;
        java.lang.String s = null;
        boolean flag1 = isExpanded(gtreepath);
        boolean flag2 = isSelect(gtreepath);
        if(bMonoHeightRows)
        {
            _sizePathDy = getMonoHeightRows();
        } else
        {
            _sizePathDy = model.getRenderHeight(gtreepath, flag2, flag1);
            if(_sizePathDy <= 0.0F)
            {
                s = model.getString(gtreepath, flag2, flag1);
                if(s != null)
                {
                    _sizePathDy = root.textFonts[font].height;
                } else
                {
                    flag = false;
                    _sizePathDy = getMonoHeightRows();
                }
            }
        }
        _sizePathDx = model.getRenderWidth(gtreepath, flag2, flag1);
        if(_sizePathDx <= 0.0F)
        {
            if(flag)
                s = model.getString(gtreepath, flag2, flag1);
            if(s != null)
                _sizePathDx = root.textFonts[font].size(s).dx;
            else
                _sizePathDx = 0.0F;
        }
    }

    public int findRow(float f)
    {
        int i = rows.size();
        if(i == 0 || f < 0.0F)
            return -1;
        if(bMonoHeightRows)
        {
            int j = (int)(f / getMonoHeightRows());
            if(j >= i)
                j = -1;
            return j;
        }
        float f1 = 0.0F;
        for(int k = 0; k < i; k++)
        {
            com.maddox.gwindow.GTreePath gtreepath = (com.maddox.gwindow.GTreePath)rows.get(k);
            f1 += computeHeight(gtreepath);
            if(f < f1)
                return k;
        }

        return -1;
    }

    public float computeHeight(com.maddox.gwindow.GTreePath gtreepath)
    {
        if(bMonoHeightRows)
            return getMonoHeightRows();
        boolean flag = isExpanded(gtreepath);
        boolean flag1 = isSelect(gtreepath);
        float f = model.getRenderHeight(gtreepath, flag1, flag);
        if(f > 0.0F)
            return f;
        java.lang.String s = model.getString(gtreepath, flag1, flag);
        if(s != null)
            return root.textFonts[font].height;
        else
            return getMonoHeightRows();
    }

    public void endEditing(boolean flag)
    {
        if(editor != null)
        {
            if(selectPath != null && !flag)
            {
                java.lang.Object obj = editor.getCellEditValue();
                model.setValueAt(obj, selectPath);
            }
            ((com.maddox.gwindow.GWindow)editor).hideWindow();
            editor = null;
        }
    }

    protected void beginEditor()
    {
        if(editor != null)
            return;
        if(selectPath == null)
            return;
        java.lang.Object obj = model.getValueAt(selectPath);
        if(obj == null)
            return;
        editor = model.getEdit(selectPath, isExpanded(selectPath));
        if(editor == null)
            editor = (com.maddox.gwindow.GWindowCellEdit)wClient.create(new GWindowEditControl());
        editor.setCellEditValue(obj);
        float f = wClient.win.dx;
        f -= (float)(selectPath.getPathCount() - model.getRoot().getPathCount()) * getTabStep();
        if(bDrawIcons)
            f -= getTabStep();
        f -= 2.0F * getBorderSpace();
        ((com.maddox.gwindow.GWindow)editor).setSize(f, computeHeight(selectPath));
        setPosEditor();
        ((com.maddox.gwindow.GWindow)editor).activateWindow();
    }

    public void setPosEditor()
    {
        if(editor == null)
            return;
        float f = getBorderSpace();
        for(int i = 0; i < selectRow; i++)
            f += computeHeight((com.maddox.gwindow.GTreePath)rows.get(i));

        float f1 = getBorderSpace();
        f1 += (float)(selectPath.getPathCount() - model.getRoot().getPathCount()) * getTabStep();
        if(bDrawIcons)
            f1 += getTabStep();
        ((com.maddox.gwindow.GWindow)editor).setPos(f1, f);
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
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

    public void resized()
    {
        getClientRegion(_clientRegion, 0.0F);
        boolean flag = false;
        boolean flag1 = false;
        float f = 0.0F;
        float f1 = 0.0F;
        for(int i = 0; i < 3; i++)
        {
            f = _clientRegion.dy;
            if(flag1)
                f -= lookAndFeel().getHScrollBarH();
            f1 = _clientRegion.dx;
            if(flag)
                f1 -= lookAndFeel().getVScrollBarW();
            if(i == 2)
                break;
            if(bEnableHScrollBar)
                flag1 = wClient.win.dx > f1;
            if(bEnableVScrollBar)
                flag = wClient.win.dy > f;
        }

        if(flag)
        {
            vSB.setRange(0.0F, wClient.win.dy, f, getMonoHeightRows(), vSB.pos);
            vSB.setSize(lookAndFeel().getVScrollBarW(), f);
            vSB.setPos(_clientRegion.x + f1, _clientRegion.y);
            vSB.showWindow();
        } else
        {
            vSB.setPos(0.0F, false);
            vSB.hideWindow();
        }
        if(flag1)
        {
            hSB.setRange(0.0F, wClient.win.dx, f1, getTabStep(), hSB.pos);
            hSB.setSize(f1, lookAndFeel().getHScrollBarH());
            hSB.setPos(_clientRegion.x, _clientRegion.y + f);
            hSB.showWindow();
        } else
        {
            hSB.setPos(0.0F, false);
            hSB.hideWindow();
        }
        if(flag1 && flag)
        {
            button.setPos(f1 + _clientRegion.x, _clientRegion.y + f);
            button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
            button.showWindow();
        } else
        {
            button.hideWindow();
        }
        wClient.setPos(_clientRegion.x - hSB.pos, _clientRegion.y - vSB.pos);
        setPosEditor();
    }

    public void afterCreated()
    {
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

    public void renderClient()
    {
        lookAndFeel().renderClient(this);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        return lookAndFeel().getClientRegion(this, gregion, f);
    }

    public GWindowTree(com.maddox.gwindow.GWindow gwindow)
    {
        bMonoHeightRows = true;
        bDrawTreeLines = true;
        bDrawIcons = true;
        bEditExitOnFocusExit = true;
        bEditExitOnEnterPress = true;
        bEnableHScrollBar = true;
        bEnableVScrollBar = true;
        bRootVisible = true;
        font = 0;
        borderSpace = 2.0F;
        rows = new ArrayList();
        expandedState = new HashMap();
        selectRow = -1;
        _clientRegion = new GRegion();
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowTree(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3)
    {
        bMonoHeightRows = true;
        bDrawTreeLines = true;
        bDrawIcons = true;
        bEditExitOnFocusExit = true;
        bEditExitOnEnterPress = true;
        bEnableHScrollBar = true;
        bEnableVScrollBar = true;
        bRootVisible = true;
        font = 0;
        borderSpace = 2.0F;
        rows = new ArrayList();
        expandedState = new HashMap();
        selectRow = -1;
        _clientRegion = new GRegion();
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public boolean bMonoHeightRows;
    public boolean bDrawTreeLines;
    public boolean bDrawIcons;
    public boolean bEditExitOnFocusExit;
    public boolean bEditExitOnEnterPress;
    public boolean bEnableHScrollBar;
    public boolean bEnableVScrollBar;
    protected boolean bRootVisible;
    public int font;
    public float borderSpace;
    public com.maddox.gwindow.GTreeModel model;
    public java.util.List rows;
    public java.util.HashMap expandedState;
    public com.maddox.gwindow.GTreePath selectPath;
    public int selectRow;
    public com.maddox.gwindow.GWindowCellEdit editor;
    public com.maddox.gwindow.Client wClient;
    public com.maddox.gwindow.GWindowVScrollBar vSB;
    public com.maddox.gwindow.GWindowHScrollBar hSB;
    public com.maddox.gwindow.GWindowButton button;
    public float _sizePathDx;
    public float _sizePathDy;
    private com.maddox.gwindow.GRegion _clientRegion;
}
