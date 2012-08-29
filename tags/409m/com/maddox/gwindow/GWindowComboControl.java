// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowComboControl.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowCellEdit, GWindowLookAndFeel, GWindowVScrollBar, 
//            GWindowRoot, GRegion, GWindow, GWindowButtonTexture, 
//            GPoint, GWindowEditBox

public class GWindowComboControl extends com.maddox.gwindow.GWindowDialogControl
    implements com.maddox.gwindow.GWindowCellEdit
{
    public class Button extends com.maddox.gwindow.GWindowButtonTexture
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag || !bEnable)
                return;
            if(listArea.isVisible())
                hideList();
            else
                showList();
        }

        public void resized()
        {
            lookAndFeel().setupComboButton(this);
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            bTransient = true;
            bAlwaysOnTop = true;
            lookAndFeel().setupComboButton(this);
        }

        public Button(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class EditBox extends com.maddox.gwindow.GWindowEditBox
    {

        public void mouseClick(int i, float f, float f1)
        {
            if(i != 0)
                return;
            if(!listArea.isVisible())
                showList();
            else
                hideList();
        }

        public boolean notify(int i, int j)
        {
            if(i == 17 && root.mouseRelMoveZ != 0.0F)
            {
                editScroll(root.mouseRelMoveZ > 0.0F);
                return true;
            }
            if(i == 2)
            {
                java.lang.String s = getValue();
                if(s.length() > 0)
                {
                    int k = findStartsWith(s, bFindIgnoreCase);
                    if(k >= 0)
                    {
                        setSelected(k, false, false);
                        if(s.equals(get(k)))
                            return super.notify(i, k);
                    }
                }
                return super.notify(i, -1);
            }
            if(bDelayedNotify && bCanEdit && listArea.isVisible())
            {
                java.lang.String s1 = getValue();
                if(s1.length() > 0)
                {
                    int l = findStartsWith(s1, bFindIgnoreCase);
                    if(l >= 0)
                        setSelected(l, false, false);
                }
            }
            return super.notify(i, j);
        }

        public void created()
        {
            bDelayedNotify = true;
            lookAndFeel().setupComboEditBox(this);
        }

        public void resized()
        {
            lookAndFeel().setupComboEditBox(this);
        }

        public EditBox(com.maddox.gwindow.GWindow gwindow)
        {
            align = 0;
            doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
        }
    }

    public class ListArea extends com.maddox.gwindow.GWindowDialogControl
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag)
                return;
            int j = (int)(f1 / (win.dy / (float)listCountLines)) + listStartLine;
            if(posEnable != null && !posEnable[j])
                return;
            if(isVisible())
                lAF().soundPlay("comboHide");
            hideWindow();
            setSelected(j, true, true);
        }

        public void mouseMove(float f, float f1)
        {
            super.mouseMove(f, f1);
            int i = (int)(f1 / (win.dy / (float)listCountLines)) + listStartLine;
            if(posEnable != null && !posEnable[i])
            {
                return;
            } else
            {
                listSelected = i;
                return;
            }
        }

        public void render()
        {
            lookAndFeel().renderComboList((com.maddox.gwindow.GWindowComboControl)parentWindow);
        }

        public void msgMouseButton(boolean flag, int i, boolean flag1, float f, float f1)
        {
            if(flag)
                return;
            com.maddox.gwindow.GWindow gwindow = root.findWindowUnder(root.mousePos.x, root.mousePos.y);
            com.maddox.gwindow.GWindow gwindow1 = gwindow.getParent(com.maddox.gwindow.GWindowComboControl.class$com$maddox$gwindow$GWindowComboControl != null ? com.maddox.gwindow.GWindowComboControl.class$com$maddox$gwindow$GWindowComboControl : (com.maddox.gwindow.GWindowComboControl.class$com$maddox$gwindow$GWindowComboControl = com.maddox.gwindow.GWindowComboControl._mthclass$("com.maddox.gwindow.GWindowComboControl")), false);
            if(gwindow1 != parentWindow)
            {
                if(isVisible())
                    lAF().soundPlay("comboHide");
                hideWindow();
            }
        }

        public ListArea(com.maddox.gwindow.GWindow gwindow)
        {
            bClip = false;
            bAlwaysOnTop = true;
            bMouseListener = true;
            doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
        }
    }

    public class ScrollBar extends com.maddox.gwindow.GWindowVScrollBar
    {

        public boolean setPos(float f, boolean flag)
        {
            boolean flag1 = super.setPos(f, flag);
            if(flag)
                listStartLine = (int)pos;
            return flag1;
        }

        public ScrollBar(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public void setEnable(boolean flag)
    {
        super.setEnable(flag);
        editBox.setEnable(flag);
        button.setEnable(flag);
    }

    public void setToolTip(java.lang.String s)
    {
        super.setToolTip(s);
        editBox.setToolTip(s);
    }

    public void setEditDelayedNotify(boolean flag)
    {
        editBox.bDelayedNotify = flag;
    }

    public void setMaxLength(int i)
    {
        editBox.maxLength = i;
    }

    public void setEditable(boolean flag)
    {
        editBox.setEditable(flag);
    }

    public void setEditTextColor(int i)
    {
        editBox.color = i;
    }

    public void setNumericOnly(boolean flag)
    {
        editBox.bNumericOnly = flag;
    }

    public void setNumericFloat(boolean flag)
    {
        editBox.bNumericFloat = flag;
    }

    public void setValue(java.lang.String s)
    {
        editBox.setValue(s);
    }

    public void setValue(java.lang.String s, boolean flag)
    {
        editBox.setValue(s, flag);
    }

    public java.lang.String getValue()
    {
        return editBox.getValue();
    }

    public void clearValue()
    {
        editBox.clear();
    }

    public void clearValue(boolean flag)
    {
        editBox.clear(flag);
    }

    public void setCellEditValue(java.lang.Object obj)
    {
        setValue(obj.toString(), false);
    }

    public java.lang.Object getCellEditValue()
    {
        return getValue();
    }

    public int size()
    {
        return list.size();
    }

    public void add(java.lang.String s)
    {
        hideList();
        list.add(s);
    }

    public void add(int i, java.lang.String s)
    {
        hideList();
        list.add(i, s);
    }

    public void remove(int i)
    {
        hideList();
        list.remove(i);
    }

    public void clear()
    {
        hideList();
        clearValue();
        list.clear();
    }

    public void clear(boolean flag)
    {
        hideList();
        clearValue(flag);
        list.clear();
    }

    public java.lang.String get(int i)
    {
        return (java.lang.String)list.get(i);
    }

    public int getSelected()
    {
        return iSelected;
    }

    public void setSelected(int i, boolean flag, boolean flag1)
    {
        if(flag)
            setValue(get(i), false);
        if(iSelected == i)
            return;
        iSelected = i;
        updateList();
        if(flag1)
            notify(2, iSelected);
    }

    public int findStartsWith(java.lang.String s, boolean flag)
    {
        int i = s.length();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            java.lang.String s1 = (java.lang.String)list.get(k);
            if(s1.length() >= i && s1.regionMatches(flag, 0, s, 0, i) && posEnable != null && posEnable[k])
                return k;
        }

        return -1;
    }

    public void hideList()
    {
        if(listArea.isVisible())
            lAF().soundPlay("comboHide");
        listArea.hideWindow();
    }

    public void updateList()
    {
        if(!listArea.isVisible())
            return;
        if(!scrollBar.isVisible())
            return;
        if(iSelected < 0)
            scrollBar.setPos(0.0F, false);
        else
            scrollBar.setPos(iSelected, false);
        listSelected = iSelected;
        listStartLine = (int)scrollBar.pos();
    }

    public void showList()
    {
        if(!bEnable)
            return;
        if(list.size() == 0)
            return;
        if(listArea.isVisible())
        {
            return;
        } else
        {
            lookAndFeel().setupComboList(this);
            listArea.showWindow();
            lAF().soundPlay("comboShow");
            return;
        }
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(i == 17)
        {
            if(scrollBar.isVisible())
                scrollBar.scrollDz(root.mouseRelMoveZ);
            return true;
        }
        if(gwindow == editBox && bEnable && i == 10)
        {
            if(j == 38)
            {
                editScroll(true);
                return true;
            }
            if(j == 40)
            {
                editScroll(false);
                return true;
            }
        }
        return false;
    }

    public void editScroll(boolean flag)
    {
        if(!bEnable)
            return;
        if(listArea.isVisible())
            return;
        if(flag)
        {
            int i = size();
            int k = iSelected;
            while(i-- > 0) 
            {
                k = ((k - 1) + size()) % size();
                if(posEnable == null || posEnable[k])
                {
                    setSelected(k, true, true);
                    break;
                }
            }
        } else
        {
            int j = size();
            int l = iSelected;
            while(j-- > 0) 
            {
                l = (l + 1) % size();
                if(posEnable == null || posEnable[l])
                {
                    setSelected(l, true, true);
                    break;
                }
            }
        }
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public void afterCreated()
    {
        super.afterCreated();
        button = new Button(this);
        editBox = new EditBox(this);
        listArea = new ListArea(this);
        scrollBar = new ScrollBar(listArea);
        listArea.hideWindow();
    }

    public void resized()
    {
        if(metricWin != null)
            metricWin.dy = lookAndFeel().getComboH() / lookAndFeel().metric();
        if(button != null)
            button.resized();
        if(editBox != null)
            editBox.resized();
        hideList();
    }

    public GWindowComboControl()
    {
        list = new ArrayList();
        listVisibleLines = 7;
        bFindIgnoreCase = true;
        iSelected = -1;
        listSelected = -1;
        listStartLine = 0;
        listCountLines = listVisibleLines;
    }

    public GWindowComboControl(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        list = new ArrayList();
        listVisibleLines = 7;
        bFindIgnoreCase = true;
        iSelected = -1;
        listSelected = -1;
        listStartLine = 0;
        listCountLines = listVisibleLines;
        float f3 = gwindow.lookAndFeel().getComboH() / gwindow.lookAndFeel().metric();
        doNew(gwindow, f, f1, f2, f3, true);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public com.maddox.gwindow.EditBox editBox;
    public com.maddox.gwindow.Button button;
    public com.maddox.gwindow.ListArea listArea;
    public com.maddox.gwindow.GWindowVScrollBar scrollBar;
    public java.util.ArrayList list;
    public boolean posEnable[];
    public int listVisibleLines;
    public boolean bFindIgnoreCase;
    public int iSelected;
    public int listSelected;
    public int listStartLine;
    public int listCountLines;
    static java.lang.Class class$com$maddox$gwindow$GWindowComboControl; /* synthetic field */
}
