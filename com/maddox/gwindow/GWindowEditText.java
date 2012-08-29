// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowEditText.java

package com.maddox.gwindow;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowRoot, GCanvas, GFont, 
//            GSize, GRegion, GWindowLookAndFeel

public class GWindowEditText extends com.maddox.gwindow.GWindowDialogControl
{
    public class UnDoInsStr extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            item(caret.item).delete(caret.ofs, caret.ofs + str.length());
            super.undo();
        }

        public void redo(boolean flag)
        {
            item(caret.item).insert(caret.ofs, str);
            posCaret.set(caret.item, caret.ofs + str.length());
            super.redo(flag);
        }

        public java.lang.String str;

        public UnDoInsStr(java.lang.String s)
        {
            str = s;
        }
    }

    public class UnDoInsChar extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            item(caret.item).deleteCharAt(caret.ofs);
            super.undo();
        }

        public void redo(boolean flag)
        {
            item(caret.item).insert(caret.ofs, ch);
            posCaret.set(caret.item, caret.ofs + 1);
            super.redo(flag);
        }

        public char ch;

        public UnDoInsChar(char c)
        {
            ch = c;
        }
    }

    public class UnDoIns extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            item(caret.item).append(item(caret.item + 1));
            text.remove(caret.item + 1);
            super.undo();
        }

        public void redo(boolean flag)
        {
            java.lang.StringBuffer stringbuffer = item(caret.item);
            char ac[] = new char[stringbuffer.length() - caret.ofs];
            stringbuffer.getChars(caret.ofs, stringbuffer.length(), ac, 0);
            stringbuffer.delete(caret.ofs, stringbuffer.length());
            stringbuffer = new StringBuffer(ac.length);
            stringbuffer.append(ac);
            text.add(caret.item + 1, stringbuffer);
            posCaret.set(caret.item + 1, 0);
            super.redo(flag);
        }

        public UnDoIns()
        {
        }
    }

    public class UnDoInsR extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            text.remove(caret.item + 1);
            super.undo();
        }

        public void redo(boolean flag)
        {
            text.add(caret.item + 1, new StringBuffer());
            posCaret.set(caret.item + 1, 0);
            super.redo(flag);
        }

        public UnDoInsR()
        {
        }
    }

    public class UnDoInsL extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            text.remove(caret.item);
            super.undo();
        }

        public void redo(boolean flag)
        {
            text.add(caret.item, new StringBuffer());
            posCaret.set(caret.item + 1, 0);
            super.redo(flag);
        }

        public UnDoInsL()
        {
        }
    }

    public class UnDoDelLf extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            if(bLeft)
            {
                java.lang.StringBuffer stringbuffer = item(caret.item - 1);
                char ac[] = new char[stringbuffer.length() - ofs];
                stringbuffer.getChars(ofs, stringbuffer.length(), ac, 0);
                stringbuffer.delete(ofs, stringbuffer.length());
                stringbuffer = new StringBuffer(ac.length);
                stringbuffer.append(ac);
                text.add(caret.item, stringbuffer);
            } else
            {
                java.lang.StringBuffer stringbuffer1 = item(caret.item);
                char ac1[] = new char[stringbuffer1.length() - ofs];
                stringbuffer1.getChars(ofs, stringbuffer1.length(), ac1, 0);
                stringbuffer1.delete(ofs, stringbuffer1.length());
                stringbuffer1 = new StringBuffer(ac1.length);
                stringbuffer1.append(ac1);
                text.add(caret.item + 1, stringbuffer1);
            }
            super.undo();
        }

        public void redo(boolean flag)
        {
            if(bLeft)
            {
                java.lang.StringBuffer stringbuffer = item(caret.item - 1);
                stringbuffer.append(item(caret.item));
                text.remove(caret.item);
                posCaret.set(caret.item - 1, ofs);
            } else
            {
                java.lang.StringBuffer stringbuffer1 = item(caret.item);
                stringbuffer1.append(item(caret.item + 1));
                text.remove(caret.item + 1);
            }
            super.redo(flag);
        }

        public boolean bLeft;
        public int ofs;

        public UnDoDelLf(boolean flag)
        {
            bLeft = flag;
            java.lang.StringBuffer stringbuffer = item(flag ? caret.item - 1 : caret.item);
            ofs = stringbuffer.length();
        }
    }

    public class UnDoDelChar extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            item(caret.item).insert(bLeft ? caret.ofs - 1 : caret.ofs, ch);
            super.undo();
        }

        public void redo(boolean flag)
        {
            item(caret.item).deleteCharAt(bLeft ? caret.ofs - 1 : caret.ofs);
            posCaret.set(caret.item, bLeft ? caret.ofs - 1 : caret.ofs);
            super.redo(flag);
        }

        public char ch;
        public boolean bLeft;

        public UnDoDelChar(boolean flag)
        {
            bLeft = flag;
            ch = item(caret.item).charAt(flag ? caret.ofs - 1 : caret.ofs);
        }
    }

    public class UnDoDelLines extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            text.remove(com.maddox.gwindow.GWindowEditText._p0.item);
            int i = lines.size();
            for(int j = 0; j < i; j++)
            {
                char ac[] = (char[])lines.get(j);
                java.lang.StringBuffer stringbuffer = new StringBuffer(ac.length);
                stringbuffer.append(ac);
                text.add(com.maddox.gwindow.GWindowEditText._p0.item++, stringbuffer);
            }

            super.undo();
        }

        public void redo(boolean flag)
        {
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            while(com.maddox.gwindow.GWindowEditText._p0.item + 1 < com.maddox.gwindow.GWindowEditText._p1.item) 
                text.remove(--com.maddox.gwindow.GWindowEditText._p1.item);
            java.lang.StringBuffer stringbuffer = item(com.maddox.gwindow.GWindowEditText._p0.item);
            stringbuffer.delete(com.maddox.gwindow.GWindowEditText._p0.ofs, stringbuffer.length());
            stringbuffer.append(item(com.maddox.gwindow.GWindowEditText._p1.item).substring(com.maddox.gwindow.GWindowEditText._p1.ofs, item(com.maddox.gwindow.GWindowEditText._p1.item).length()));
            text.remove(com.maddox.gwindow.GWindowEditText._p1.item);
            posCaret.set(com.maddox.gwindow.GWindowEditText._p0);
            super.redo(flag);
        }

        public java.util.ArrayList lines;

        public UnDoDelLines()
        {
            lines = new ArrayList();
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            for(; com.maddox.gwindow.GWindowEditText._p0.item <= com.maddox.gwindow.GWindowEditText._p1.item; com.maddox.gwindow.GWindowEditText._p0.item++)
            {
                java.lang.StringBuffer stringbuffer = item(com.maddox.gwindow.GWindowEditText._p0.item);
                char ac[] = new char[stringbuffer.length()];
                stringbuffer.getChars(0, stringbuffer.length(), ac, 0);
                lines.add(ac);
            }

        }
    }

    public class UnDoDelLine extends com.maddox.gwindow.UnDo
    {

        public void undo()
        {
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            item(com.maddox.gwindow.GWindowEditText._p0.item).insert(com.maddox.gwindow.GWindowEditText._p0.ofs, line);
            super.undo();
        }

        public void redo(boolean flag)
        {
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            item(com.maddox.gwindow.GWindowEditText._p0.item).delete(com.maddox.gwindow.GWindowEditText._p0.ofs, com.maddox.gwindow.GWindowEditText._p1.ofs);
            posCaret.set(com.maddox.gwindow.GWindowEditText._p0);
            super.redo(flag);
        }

        public char line[];

        public UnDoDelLine()
        {
            com.maddox.gwindow.GWindowEditText._sortPos(select, caret);
            line = new char[com.maddox.gwindow.GWindowEditText._p1.ofs - com.maddox.gwindow.GWindowEditText._p0.ofs];
            item(com.maddox.gwindow.GWindowEditText._p0.item).getChars(com.maddox.gwindow.GWindowEditText._p0.ofs, com.maddox.gwindow.GWindowEditText._p1.ofs, line, 0);
        }
    }

    public class UnDo
    {

        public void undo()
        {
            posCaret.set(caret);
            posSelect.set(select);
            _doNotifyChange(true);
        }

        public void redo(boolean flag)
        {
            posSelect.disable();
            _doNotifyChange(flag);
        }

        public com.maddox.gwindow.Pos caret;
        public com.maddox.gwindow.Pos select;
        public boolean bSubOperation;

        public UnDo()
        {
            caret = new Pos();
            select = new Pos();
            bSubOperation = false;
            caret.set(posCaret);
            select.set(posSelect);
        }
    }

    public static class PosLen extends com.maddox.gwindow.Pos
    {

        public void set(int i, int j, int k, float f)
        {
            item = i;
            ofs = j;
            len = k;
            width = f;
        }

        public int len;
        public float width;

        public PosLen()
        {
            len = 0;
            width = 0.0F;
        }

        public PosLen(int i, int j, int k, float f)
        {
            set(i, j, k, f);
        }
    }

    public static class Pos
    {

        public boolean isEnable()
        {
            return item >= 0;
        }

        public void disable()
        {
            item = -1;
        }

        public boolean isEqual(com.maddox.gwindow.Pos pos)
        {
            return pos.item == item && pos.ofs == ofs;
        }

        public boolean isLess(com.maddox.gwindow.Pos pos)
        {
            return item < pos.item || item == pos.item && ofs < pos.ofs;
        }

        public void set(int i, int j)
        {
            item = i;
            ofs = j;
        }

        public void set(com.maddox.gwindow.Pos pos)
        {
            set(pos.item, pos.ofs);
        }

        public int item;
        public int ofs;

        public Pos()
        {
            item = -1;
            ofs = 0;
        }

        public Pos(int i, int j)
        {
            set(i, j);
        }
    }


    public GWindowEditText()
    {
        text = new ArrayList();
        textPos = new ArrayList();
        posCaret = new Pos(0, 0);
        posSelect = new Pos();
        undoList = new ArrayList();
        undoPos = 0;
        maxUnDoSize = 128;
        bCanEdit = true;
        bCanRepeat = true;
        bDelayedNotify = false;
        repeatStartTimeout = 0.5F;
        repeatGoTimeout = 0.1F;
        bWrap = true;
        bControlDown = false;
        bShiftDown = false;
        bMouseDown = false;
        bChangePending = false;
        bShowCaret = false;
        caretTimeout = 0.0F;
        bEnableClampUnDoList = true;
        bEnableNotifyChange = true;
        curRepeatOp = 0;
    }

    private static void _sortPos(com.maddox.gwindow.Pos pos, com.maddox.gwindow.Pos pos1)
    {
        _p0.set(pos);
        _p1.set(pos1);
        if(_p1.isLess(_p0))
        {
            com.maddox.gwindow.Pos pos2 = _p0;
            _p0 = _p1;
            _p1 = pos2;
        }
    }

    public com.maddox.gwindow.UnDo newUnDo(com.maddox.gwindow.UnDo undo)
    {
        undoList.add(undoPos++, undo);
        for(; undoPos < undoList.size(); undoList.remove(undoList.size() - 1));
        clampUnDoList();
        return undo;
    }

    private void clampUnDoList()
    {
        if(!bEnableClampUnDoList || maxUnDoSize < 0)
            return;
        int i = undoList.size() - maxUnDoSize;
        if(i <= 0)
            return;
        for(; i < undoList.size(); i++)
        {
            com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(i);
            if(!undo.bSubOperation)
                break;
        }

        undoPos -= i;
        while(i-- > 0) 
            undoList.remove(0);
    }

    public void clearUnDo()
    {
        undoList.clear();
        undoPos = 0;
    }

    public void doUnDo()
    {
        if(undoPos == 0)
            return;
        com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(--undoPos);
        undo.undo();
        for(; undoPos > 0; undoPos--)
        {
            com.maddox.gwindow.UnDo undo1 = (com.maddox.gwindow.UnDo)undoList.get(undoPos - 1);
            if(!undo1.bSubOperation)
                break;
            undo1.undo();
        }

    }

    public void doReDo()
    {
        while(undoPos < undoList.size()) 
        {
            com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(undoPos++);
            undo.redo(!undo.bSubOperation);
            if(!undo.bSubOperation)
                break;
        }
    }

    public boolean isExistUnDo()
    {
        return undoPos > 0;
    }

    public boolean isExistReDo()
    {
        return undoPos < undoList.size();
    }

    public void setMaxUnDoSize(int i)
    {
        if(i == 0 || i == 1)
            i = 2;
        maxUnDoSize = i;
    }

    public int getMaxUnDoSize()
    {
        return maxUnDoSize;
    }

    public java.lang.StringBuffer item(int i)
    {
        return (java.lang.StringBuffer)text.get(i);
    }

    public com.maddox.gwindow.PosLen itemPos(int i)
    {
        if(i == textPos.size())
            textPos.add(new PosLen());
        return (com.maddox.gwindow.PosLen)textPos.get(i);
    }

    public void setEnable(boolean flag)
    {
        super.setEnable(flag);
        if(!bEnable)
        {
            bShiftDown = false;
            bControlDown = false;
            bMouseDown = false;
            bShowCaret = false;
            bChangePending = false;
            posSelect.disable();
        }
    }

    public void setEditable(boolean flag)
    {
        bCanEdit = flag;
    }

    public void setWrap(boolean flag)
    {
        bWrap = flag;
        updateTextPos();
    }

    public boolean isWrap()
    {
        return bWrap;
    }

    public boolean isSelected()
    {
        return posSelect.isEnable() && !posSelect.isEqual(posCaret);
    }

    public boolean isEmpty()
    {
        return text.size() == 0;
    }

    public boolean isChanged()
    {
        return bChangePending;
    }

    public void setChangedFlag(boolean flag)
    {
        bChangePending = flag;
    }

    public void unselect()
    {
        posSelect.disable();
    }

    public void selectAll()
    {
        int i = text.size() - 1;
        if(i < 0)
            return;
        posSelect.set(0, 0);
        posCaret.set(i, item(i).length());
        if(posSelect.isEqual(posCaret))
            posSelect.disable();
    }

    public void clear()
    {
        clear(true);
    }

    public void clear(boolean flag)
    {
        selectAll();
        delete(flag);
    }

    protected void startShowCaret()
    {
        caretTimeout = 0.3F;
        bShowCaret = true;
        notify(2, 1);
    }

    protected void _doNotifyChange(boolean flag)
    {
        if(!bEnableNotifyChange)
            return;
        updateTextPos();
        startShowCaret();
        if(flag)
            if(bDelayedNotify)
                bChangePending = true;
            else
                notify(2, 0);
    }

    public void delete()
    {
        delete(true);
    }

    public void delete(boolean flag)
    {
        if(!bCanEdit)
            return;
        if(!isSelected())
            return;
        if(posCaret.item == posSelect.item)
            newUnDo(new UnDoDelLine()).redo(flag);
        else
            newUnDo(new UnDoDelLines()).redo(flag);
    }

    public void deleteLeft()
    {
        deleteLeft(true);
    }

    public void deleteLeft(boolean flag)
    {
        if(!bCanEdit)
            return;
        if(isEmpty())
            return;
        if(isSelected())
        {
            delete(flag);
            return;
        }
        if(posCaret.ofs > 0)
            newUnDo(new UnDoDelChar(true)).redo(flag);
        else
        if(posCaret.item > 0)
            newUnDo(new UnDoDelLf(true)).redo(flag);
    }

    public void deleteRight()
    {
        deleteRight(true);
    }

    public void deleteRight(boolean flag)
    {
        if(!bCanEdit)
            return;
        if(isEmpty())
            return;
        if(isSelected())
        {
            delete(flag);
            return;
        }
        java.lang.StringBuffer stringbuffer = item(posCaret.item);
        if(posCaret.ofs < stringbuffer.length())
            newUnDo(new UnDoDelChar(false)).redo(flag);
        else
        if(posCaret.item < text.size() - 1)
            newUnDo(new UnDoDelLf(false)).redo(flag);
    }

    public void insert()
    {
        insert(true);
    }

    public void insert(boolean flag)
    {
        if(!bCanEdit)
            return;
        if(isSelected())
        {
            delete(false);
            com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(undoPos - 1);
            undo.bSubOperation = true;
        }
        if(isEmpty())
        {
            clearUnDo();
            text.add(new StringBuffer());
            _doNotifyChange(flag);
        } else
        {
            java.lang.StringBuffer stringbuffer = item(posCaret.item);
            if(posCaret.ofs == 0)
                newUnDo(new UnDoInsL()).redo(flag);
            else
            if(posCaret.ofs == stringbuffer.length())
                newUnDo(new UnDoInsR()).redo(flag);
            else
                newUnDo(new UnDoIns()).redo(flag);
        }
    }

    public void insert(char c)
    {
        insert(c, true);
    }

    public void insert(char c, boolean flag)
    {
        if(!bCanEdit)
            return;
        if(isSelected())
        {
            delete(false);
            com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(undoPos - 1);
            undo.bSubOperation = true;
        }
        if(isEmpty())
        {
            clearUnDo();
            text.add(new StringBuffer());
            item(posCaret.item).insert(posCaret.ofs++, c);
            unselect();
            _doNotifyChange(flag);
        } else
        {
            newUnDo(new UnDoInsChar(c)).redo(flag);
        }
    }

    public void insert(java.lang.String s)
    {
        insert(s, true);
    }

    public void insert(java.lang.String s, boolean flag)
    {
        if(!bCanEdit)
            return;
        if(isSelected())
        {
            delete(false);
            com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(undoPos - 1);
            undo.bSubOperation = true;
        }
        if(isEmpty())
        {
            clearUnDo();
            text.add(new StringBuffer());
            item(posCaret.item).insert(posCaret.ofs, s);
            posCaret.ofs += s.length();
            unselect();
            _doNotifyChange(flag);
        } else
        {
            newUnDo(new UnDoInsStr(s)).redo(flag);
        }
    }

    public void insert(java.util.List list)
    {
        insert(list, true);
    }

    public void insert(java.util.List list, boolean flag)
    {
        if(!bCanEdit)
            return;
        bEnableClampUnDoList = false;
        bEnableNotifyChange = false;
        boolean flag1 = isEmpty();
        int i = undoPos;
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = (java.lang.String)list.get(k);
            if(k != 0)
                insert(false);
            if(s != null)
                insert(s, false);
        }

        if(flag1)
        {
            clearUnDo();
        } else
        {
            for(int l = i; l < undoPos - 1; l++)
            {
                com.maddox.gwindow.UnDo undo = (com.maddox.gwindow.UnDo)undoList.get(l);
                undo.bSubOperation = true;
            }

        }
        bEnableClampUnDoList = true;
        bEnableNotifyChange = true;
        clampUnDoList();
        _doNotifyChange(flag);
    }

    public void moveLeft()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        if(--posCaret.ofs < 0)
            if(posCaret.item > 0)
                posCaret.ofs = item(--posCaret.item).length();
            else
                posCaret.ofs = 0;
        startShowCaret();
    }

    public void moveRight()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        if(posCaret.ofs + 1 > item(posCaret.item).length())
        {
            if(posCaret.item + 1 < text.size())
            {
                posCaret.item++;
                posCaret.ofs = 0;
            }
        } else
        {
            posCaret.ofs++;
        }
        startShowCaret();
    }

    public void moveHome()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        posCaret.ofs = 0;
        startShowCaret();
    }

    public void moveEnd()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        posCaret.ofs = item(posCaret.item).length();
        startShowCaret();
    }

    public void wordLeft()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        com.maddox.gwindow.Pos pos = _tmpPos;
        pos.set(posCaret);
        boolean flag = false;
label0:
        while(!flag) 
        {
            do
                if(pos.ofs > 0)
                {
                    char c = item(pos.item).charAt(pos.ofs - 1);
                    if(c > ' ')
                        break;
                    pos.ofs--;
                } else
                {
                    if(pos.item == 0)
                        return;
                    pos.ofs = item(--pos.item).length();
                }
            while(true);
            do
            {
                if(pos.ofs <= 0)
                    break;
                char c1 = item(pos.item).charAt(pos.ofs - 1);
                if(c1 <= ' ')
                {
                    flag = true;
                    continue label0;
                }
                pos.ofs--;
            } while(true);
            char c2 = item(pos.item).charAt(pos.ofs);
            if(c2 > ' ')
            {
                flag = true;
            } else
            {
                if(pos.item == 0)
                    return;
                pos.ofs = item(--pos.item).length();
            }
        }
        posCaret.set(pos);
        startShowCaret();
    }

    public void wordRight()
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        com.maddox.gwindow.Pos pos = _tmpPos;
        pos.set(posCaret);
        for(boolean flag = false; !flag;)
        {
label0:
            {
                for(; pos.ofs < item(pos.item).length(); pos.ofs++)
                {
                    char c = item(pos.item).charAt(pos.ofs);
                    if(c <= ' ')
                        break label0;
                }

                if(pos.item + 1 == text.size())
                    return;
                pos.set(pos.item + 1, 0);
            }
            do
                if(pos.ofs < item(pos.item).length())
                {
                    char c1 = item(pos.item).charAt(pos.ofs);
                    if(c1 > ' ')
                    {
                        flag = true;
                        break;
                    }
                    pos.ofs++;
                } else
                {
                    if(pos.item + 1 == text.size())
                        return;
                    pos.set(pos.item + 1, 0);
                }
            while(true);
        }

        posCaret.set(pos);
        startShowCaret();
    }

    public int findLine(com.maddox.gwindow.Pos pos)
    {
        int i = textPos.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.PosLen poslen = itemPos(j);
            if(poslen.item == pos.item && pos.ofs >= poslen.ofs && pos.ofs <= poslen.ofs + poslen.len)
                return j;
        }

        return 0;
    }

    public void moveUpDown(int i)
    {
        if(isEmpty())
            return;
        if(!bShiftDown)
            unselect();
        int j = findLine(posCaret);
        if(i < 0)
        {
            if(j == 0)
                return;
        } else
        if(j == textPos.size() - 1)
            return;
        com.maddox.gwindow.PosLen poslen = itemPos(j);
        if(poslen.ofs == posCaret.ofs)
        {
            j += i;
            poslen = itemPos(j);
            posCaret.set(poslen);
            startShowCaret();
            return;
        }
        setCanvasFont(font);
        com.maddox.gwindow.GFont gfont = root.C.font;
        char ac[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(posCaret.ofs - poslen.ofs);
        java.lang.StringBuffer stringbuffer = item(poslen.item);
        stringbuffer.getChars(poslen.ofs, posCaret.ofs, ac, 0);
        com.maddox.gwindow.GSize gsize = gfont.size(ac, 0, posCaret.ofs - poslen.ofs);
        float f = gsize.dx;
        j += i;
        poslen = itemPos(j);
        if(poslen.len == 0)
        {
            posCaret.set(poslen.item, poslen.ofs);
        } else
        {
            char ac1[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(poslen.len);
            java.lang.StringBuffer stringbuffer1 = item(poslen.item);
            stringbuffer1.getChars(poslen.ofs, poslen.ofs + poslen.len, ac1, 0);
            float f1 = 0.0F;
            int k;
            for(k = 1; k < poslen.len; k++)
            {
                com.maddox.gwindow.GSize gsize1 = gfont.size(ac1, 0, k);
                if(gsize1.dx >= f)
                {
                    if(f - f1 < gsize1.dx - f)
                        k--;
                    break;
                }
                f1 = gsize1.dx;
            }

            posCaret.set(poslen.item, poslen.ofs + k);
        }
        startShowCaret();
    }

    public void moveUp()
    {
        moveUpDown(-1);
    }

    public void moveDown()
    {
        moveUpDown(1);
    }

    public void editCopy()
    {
        if(!isSelected())
            return;
        com.maddox.gwindow.Pos pos = posSelect;
        com.maddox.gwindow.Pos pos1 = posCaret;
        if(pos1.isEqual(pos))
            return;
        if(pos1.isLess(pos))
        {
            com.maddox.gwindow.Pos pos2 = pos;
            pos = pos1;
            pos1 = pos2;
        }
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        com.maddox.gwindow.Pos pos3 = _tmpPos;
        pos3.set(pos);
        while(pos3.isLess(pos1)) 
        {
            java.lang.StringBuffer stringbuffer1 = item(pos3.item);
            if(pos3.ofs == stringbuffer1.length())
            {
                stringbuffer.append("\r\n");
                pos3.ofs = 0;
                pos3.item++;
            } else
            {
                stringbuffer.append(stringbuffer1.charAt(pos3.ofs++));
            }
        }
        root.C.copyToClipboard(stringbuffer.toString());
    }

    public void editPaste()
    {
        if(!bCanEdit)
            return;
        java.lang.String s = root.C.pasteFromClipboard();
        if(s == null || s.length() == 0)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(java.lang.Character.isLetterOrDigit(c) || c == '\u3001' || c == '\u3002' || c >= ' ' && c < '\200' || c >= '\240' && c <= '\377' || c == '\t')
                stringbuffer.append(c);
            else
            if(c == '\n')
            {
                arraylist.add(stringbuffer.toString());
                stringbuffer = new StringBuffer();
            }
        }

        if(stringbuffer.length() > 0)
            arraylist.add(stringbuffer.toString());
        insert(arraylist);
    }

    public void editCut()
    {
        if(!isSelected())
            return;
        editCopy();
        if(bCanEdit)
            delete();
    }

    public void updateTextPos()
    {
        if(isEmpty())
        {
            textPos.clear();
            return;
        }
        setCanvasFont(font);
        com.maddox.gwindow.GFont gfont = root.C.font;
        com.maddox.gwindow.GSize gsize = gfont.size("|");
        float f = gsize.dx;
        if(bWrap)
        {
            int i = 0;
            int k = text.size();
            for(int i1 = 0; i1 < k; i1++)
            {
                java.lang.StringBuffer stringbuffer1 = item(i1);
                int k1 = stringbuffer1.length();
                if(k1 == 0)
                {
                    com.maddox.gwindow.PosLen poslen = itemPos(i);
                    poslen.set(i1, 0, 0, 0.0F);
                    i++;
                } else
                {
                    char ac1[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(k1);
                    stringbuffer1.getChars(0, k1, ac1, 0);
                    int l1 = 0;
                    int i2 = 0;
                    int j2 = 0;
                    while(i2 < k1) 
                    {
                        while(i2 < k1) 
                        {
                            if(ac1[i2] <= ' ')
                                break;
                            i2++;
                        }
                        for(; i2 < k1; i2++)
                            if(ac1[i2] > ' ')
                                break;

                        com.maddox.gwindow.GSize gsize1 = gfont.size(ac1, l1, i2 - l1);
                        if(gsize1.dx + f >= win.dx)
                        {
                            if(j2 != l1)
                            {
                                i2 = j2;
                            } else
                            {
                                int k2 = i2;
                                for(i2 = l1 + 1; i2 < k2; i2++)
                                {
                                    com.maddox.gwindow.GSize gsize2 = gfont.size(ac1, l1, i2 - l1);
                                    if(gsize2.dx + f >= win.dx)
                                        break;
                                }

                                if(i2 > l1 + 1)
                                    i2--;
                            }
                            com.maddox.gwindow.GSize gsize3 = gfont.size(ac1, l1, i2 - l1);
                            com.maddox.gwindow.PosLen poslen2 = itemPos(i);
                            poslen2.set(i1, l1, i2 - l1, gsize3.dx + f);
                            i++;
                            l1 = j2 = i2;
                        } else
                        {
                            j2 = i2;
                        }
                    }
                    if(l1 != i2)
                    {
                        com.maddox.gwindow.GSize gsize4 = gfont.size(ac1, l1, i2 - l1);
                        com.maddox.gwindow.PosLen poslen3 = itemPos(i);
                        poslen3.set(i1, l1, i2 - l1, gsize4.dx + f);
                        i++;
                    }
                }
            }

            for(; textPos.size() > i; textPos.remove(textPos.size() - 1));
        } else
        {
            int j = text.size();
            for(int l = 0; l < j; l++)
            {
                java.lang.StringBuffer stringbuffer = item(l);
                int j1 = stringbuffer.length();
                char ac[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(j1);
                stringbuffer.getChars(0, j1, ac, 0);
                com.maddox.gwindow.GSize gsize5 = gfont.size(ac, 0, j1);
                com.maddox.gwindow.PosLen poslen1 = itemPos(l);
                poslen1.set(l, 0, j1, gsize5.dx + f);
            }

            for(; textPos.size() > j; textPos.remove(textPos.size() - 1));
        }
    }

    public static char[] _getArrayRenderBuffer(int i)
    {
        if(i > _arrayRenderBuffer.length)
            _arrayRenderBuffer = new char[i];
        return _arrayRenderBuffer;
    }

    public void keyboardChar(char c)
    {
        if(bEnable && bCanEdit && !bControlDown && (java.lang.Character.isLetterOrDigit(c) || c == '\u3001' || c == '\u3002' || c >= ' ' && c < '\200' || c >= '\240' && c <= '\377' || c == '\t'))
        {
            insert(c);
            repeatChar = c;
            startRepeat(1);
        }
        super.keyboardChar(c);
    }

    public void stopRepeat()
    {
        curRepeatOp = 0;
    }

    public void startRepeat(int i)
    {
        repeatTimeout = repeatStartTimeout;
        curRepeatOp = i;
    }

    public void doRepeat()
    {
        if(!bCanRepeat)
            return;
        if(curRepeatOp == 0)
            return;
        repeatTimeout -= root.deltaTimeSec;
        if(repeatTimeout > 0.0F)
            return;
        repeatTimeout = repeatGoTimeout;
        switch(curRepeatOp)
        {
        case 1: // '\001'
            insert(repeatChar);
            break;

        case 2: // '\002'
            insert();
            break;

        case 3: // '\003'
            moveRight();
            break;

        case 4: // '\004'
            wordRight();
            break;

        case 5: // '\005'
            moveLeft();
            break;

        case 6: // '\006'
            wordLeft();
            break;

        case 7: // '\007'
            moveUp();
            break;

        case 8: // '\b'
            moveDown();
            break;

        case 9: // '\t'
            deleteLeft();
            break;

        case 10: // '\n'
            deleteRight();
            break;

        default:
            return;
        }
    }

    public void keyboardKey(int i, boolean flag)
    {
        if(!bEnable)
        {
            super.keyboardKey(i, flag);
            return;
        }
        if(!flag)
        {
            switch(i)
            {
            case 17: // '\021'
                bControlDown = false;
                break;

            case 16: // '\020'
                bShiftDown = false;
                break;
            }
            stopRepeat();
            super.keyboardKey(i, flag);
            return;
        }
        switch(i)
        {
        case 17: // '\021'
            bControlDown = true;
            stopRepeat();
            break;

        case 16: // '\020'
            bShiftDown = true;
            if(!isSelected())
                posSelect.set(posCaret);
            stopRepeat();
            break;

        case 10: // '\n'
            insert();
            startRepeat(2);
            break;

        case 39: // '\''
            if(bControlDown)
            {
                wordRight();
                startRepeat(4);
            } else
            {
                moveRight();
                startRepeat(3);
            }
            break;

        case 37: // '%'
            if(bControlDown)
            {
                wordLeft();
                startRepeat(6);
            } else
            {
                moveLeft();
                startRepeat(5);
            }
            break;

        case 38: // '&'
            moveUp();
            startRepeat(7);
            break;

        case 40: // '('
            moveDown();
            startRepeat(8);
            break;

        case 36: // '$'
            moveHome();
            break;

        case 35: // '#'
            moveEnd();
            break;

        case 8: // '\b'
            deleteLeft();
            startRepeat(9);
            break;

        case 127: // '\177'
            deleteRight();
            startRepeat(10);
            break;

        default:
            if(!bControlDown)
                break;
            if(i == 67)
                editCopy();
            if(i == 86)
                editPaste();
            if(i == 88)
                editCut();
            if(i == 65)
                selectAll();
            if(i == 90)
                doUnDo();
            if(i == 89)
                doReDo();
            break;
        }
        super.keyboardKey(i, flag);
    }

    public void setCaretPos(float f, float f1)
    {
        if(isEmpty())
            return;
        com.maddox.gwindow.GFont gfont = root.textFonts[font];
        int i = (int)(f1 / gfont.height);
        if(i >= textPos.size())
            i = textPos.size() - 1;
        com.maddox.gwindow.PosLen poslen = itemPos(i);
        if(poslen.len == 0)
        {
            posCaret.set(poslen);
        } else
        {
            java.lang.StringBuffer stringbuffer = item(poslen.item);
            char ac[] = com.maddox.gwindow.GWindowEditText._getArrayRenderBuffer(poslen.len);
            stringbuffer.getChars(poslen.ofs, poslen.ofs + poslen.len, ac, 0);
            float f2 = 0.0F;
            int j;
            for(j = 1; j < poslen.len; j++)
            {
                com.maddox.gwindow.GSize gsize = gfont.size(ac, 0, j);
                if(gsize.dx >= f)
                {
                    if(f - f2 < gsize.dx - f)
                        j--;
                    break;
                }
                f2 = gsize.dx;
            }

            posCaret.set(poslen.item, poslen.ofs + j);
        }
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        if(bEnable && i == 0)
        {
            if(flag)
            {
                bMouseDown = true;
                if(isSelected())
                    unselect();
                setCaretPos(f, f1);
                posSelect.set(posCaret);
            } else
            {
                bMouseDown = false;
            }
            startShowCaret();
        }
        super.mouseButton(i, flag, f, f1);
    }

    public void mouseMove(float f, float f1)
    {
        if(bMouseDown)
            setCaretPos(f, f1);
        super.mouseMove(f, f1);
    }

    public void close(boolean flag)
    {
        if(bEnable && bChangePending)
        {
            bChangePending = false;
            notify(2, 0);
        }
        super.close(flag);
    }

    public void keyFocusExit()
    {
        stopRepeat();
        if(bEnable && bChangePending)
        {
            bChangePending = false;
            notify(2, 0);
        }
        bControlDown = false;
        bShiftDown = false;
        super.keyFocusExit();
    }

    public boolean _notify(int i, int j)
    {
        if(i == 2)
        {
            if(!bChangePending)
                return true;
            bChangePending = false;
        }
        return notify(i, j);
    }

    public void checkCaretTimeout()
    {
        if(!isKeyFocus() || !bCanEdit || !isActivated())
        {
            bShowCaret = false;
            return;
        }
        float f = root.deltaTimeSec;
        caretTimeout -= f;
        if(caretTimeout <= 0.0F)
        {
            bShowCaret = !bShowCaret;
            caretTimeout = 0.3F;
        }
    }

    public void resized()
    {
        super.resized();
        updateTextPos();
    }

    public void preRender()
    {
        super.preRender();
        doRepeat();
    }

    public void render()
    {
        lookAndFeel().render(this);
        checkCaretTimeout();
    }

    public java.util.ArrayList text;
    public java.util.ArrayList textPos;
    public com.maddox.gwindow.Pos posCaret;
    public com.maddox.gwindow.Pos posSelect;
    public java.util.ArrayList undoList;
    public int undoPos;
    private int maxUnDoSize;
    public boolean bCanEdit;
    public boolean bCanRepeat;
    public boolean bDelayedNotify;
    public float repeatStartTimeout;
    public float repeatGoTimeout;
    public boolean bWrap;
    public boolean bControlDown;
    public boolean bShiftDown;
    public boolean bMouseDown;
    public boolean bChangePending;
    public boolean bShowCaret;
    public float caretTimeout;
    private static com.maddox.gwindow.Pos _p0 = new Pos();
    private static com.maddox.gwindow.Pos _p1 = new Pos();
    private boolean bEnableClampUnDoList;
    private boolean bEnableNotifyChange;
    public static com.maddox.gwindow.Pos _tmpPos = new Pos();
    private static char _arrayRenderBuffer[] = new char[128];
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
    public int curRepeatOp;
    public char repeatChar;
    public float repeatTimeout;




}
