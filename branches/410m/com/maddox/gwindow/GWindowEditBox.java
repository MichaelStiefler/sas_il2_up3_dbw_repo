// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowEditBox.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowCellEdit, GWindowRoot, GCanvas, 
//            GFont, GWindowLookAndFeel

public class GWindowEditBox extends com.maddox.gwindow.GWindowDialogControl
    implements com.maddox.gwindow.GWindowCellEdit
{

    public GWindowEditBox()
    {
        value = new StringBuffer();
        maxLength = 255;
        bCanEdit = true;
        bCanEditTab = true;
        bNumericOnly = false;
        bNumericFloat = false;
        bPassword = false;
        bSelectOnFocus = true;
        bDelayedNotify = false;
        offsetX = 0.0F;
        bAllSelected = false;
        caretOffset = 0;
        bShowCaret = false;
        caretTimeout = 0.0F;
        bControlDown = false;
        bShiftDown = false;
        bChangePending = false;
        bHistory = false;
        historyCur = 0;
    }

    public void setHistory(boolean flag)
    {
        bHistory = flag;
        if(bHistory && historyList == null)
            historyList = new ArrayList();
        else
        if(!bHistory && historyList != null)
            historyList = null;
    }

    public void setEnable(boolean flag)
    {
        super.setEnable(flag);
        if(!bEnable)
        {
            bControlDown = false;
            bShiftDown = false;
            bShowCaret = false;
            bAllSelected = false;
            bChangePending = false;
            caretOffset = 0;
        }
    }

    public void setEditable(boolean flag)
    {
        bCanEdit = flag;
    }

    public void setValue(java.lang.String s)
    {
        setValue(s, true);
    }

    public void setValue(java.lang.String s, boolean flag)
    {
        value.delete(0, value.length());
        value.append(s);
        if(caretOffset > value.length())
            caretOffset = value.length();
        if(flag)
            if(bDelayedNotify)
                bChangePending = true;
            else
                notify(2, 0);
    }

    public void setCellEditValue(java.lang.Object obj)
    {
        setValue(obj.toString(), false);
    }

    public java.lang.Object getCellEditValue()
    {
        return getValue();
    }

    public void clear()
    {
        clear(true);
    }

    public void clear(boolean flag)
    {
        caretOffset = 0;
        value.delete(0, value.length());
        bAllSelected = false;
        if(flag)
            if(bDelayedNotify)
                bChangePending = true;
            else
                notify(2, 0);
    }

    public void selectAll()
    {
        if(bCanEdit && value.length() > 0)
        {
            caretOffset = value.length();
            bAllSelected = true;
        }
    }

    public int getFirstChar()
    {
        if(value.length() > 0)
            return value.charAt(0);
        else
            return -1;
    }

    public java.lang.String getValue()
    {
        return value.toString();
    }

    public void insert(java.lang.String s)
    {
        for(int i = 0; i < s.length(); i++)
            insert(s.charAt(i));

    }

    public boolean insert(char c)
    {
        if(value.length() >= maxLength)
            return false;
        value.insert(caretOffset, c);
        caretOffset++;
        if(bDelayedNotify)
            bChangePending = true;
        else
            notify(2, 0);
        return true;
    }

    protected void startShowCaret()
    {
        caretTimeout = 0.3F;
        bShowCaret = true;
    }

    public boolean backspace()
    {
        if(caretOffset == 0)
            return false;
        value.delete(caretOffset - 1, caretOffset);
        caretOffset--;
        if(bDelayedNotify)
            bChangePending = true;
        else
            notify(2, 0);
        return true;
    }

    public boolean delete()
    {
        if(caretOffset == value.length())
            return false;
        value.delete(caretOffset, caretOffset + 1);
        if(bDelayedNotify)
            bChangePending = true;
        else
            notify(2, 0);
        return true;
    }

    public boolean wordLeft()
    {
        do
        {
            if(caretOffset <= 0)
                break;
            char c = value.charAt(caretOffset - 1);
            if(c != ' ' && c != '\t')
                break;
            caretOffset--;
        } while(true);
        do
        {
            if(caretOffset <= 0)
                break;
            char c1 = value.charAt(caretOffset - 1);
            if(c1 == ' ' || c1 == '\t')
                break;
            caretOffset--;
        } while(true);
        startShowCaret();
        return true;
    }

    public boolean moveLeft()
    {
        if(caretOffset == 0)
        {
            return false;
        } else
        {
            caretOffset--;
            startShowCaret();
            return true;
        }
    }

    public boolean moveRight()
    {
        if(caretOffset == value.length())
        {
            return false;
        } else
        {
            caretOffset++;
            startShowCaret();
            return true;
        }
    }

    public boolean wordRight()
    {
        do
        {
            if(caretOffset >= value.length())
                break;
            char c = value.charAt(caretOffset);
            if(c != ' ' && c != '\t')
                break;
            caretOffset++;
        } while(true);
        do
        {
            if(caretOffset >= value.length())
                break;
            char c1 = value.charAt(caretOffset);
            if(c1 == ' ' || c1 == '\t')
                break;
            caretOffset++;
        } while(true);
        startShowCaret();
        return true;
    }

    public boolean moveHome()
    {
        caretOffset = 0;
        startShowCaret();
        return true;
    }

    public boolean moveEnd()
    {
        caretOffset = value.length();
        startShowCaret();
        return true;
    }

    public void editCopy()
    {
        if(bAllSelected || !bCanEdit)
            root.C.copyToClipboard(value.toString());
    }

    public void editPaste()
    {
        if(bCanEdit)
        {
            if(bAllSelected)
                clear();
            insert(root.C.pasteFromClipboard());
        }
    }

    public void editCut()
    {
        if(bCanEdit)
        {
            if(bAllSelected)
            {
                root.C.copyToClipboard(value.toString());
                bAllSelected = false;
                clear();
            }
        } else
        {
            editCopy();
        }
    }

    public void keyboardChar(char c)
    {
        if(bEnable && bCanEdit && !bControlDown)
        {
            if(c == '\t' && !bCanEditTab)
                return;
            if(bAllSelected)
                clear();
            bAllSelected = false;
            if(bNumericOnly)
            {
                if(java.lang.Character.isDigit(c))
                    insert(c);
                else
                if(c == '-' && value.length() == 0)
                    insert(c);
            } else
            if(java.lang.Character.isLetterOrDigit(c) || c >= ' ' && c < '\200' || c == '\t')
                insert(c);
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
            default:
                break;

            case 9: // '\t'
                if(!bCanEditTab)
                    break;
                // fall through

            case 8: // '\b'
            case 35: // '#'
            case 36: // '$'
            case 37: // '%'
            case 39: // '\''
            case 127: // '\177'
                if(bCanEdit)
                    return;
                break;

            case 17: // '\021'
                bControlDown = false;
                break;

            case 16: // '\020'
                bShiftDown = false;
                break;

            case 38: // '&'
            case 40: // '('
                if(bCanEdit && bHistory)
                    return;
                break;

            case 46: // '.'
                if(bCanEdit && bNumericFloat)
                    return;
                break;
            }
            super.keyboardKey(i, flag);
            return;
        }
        switch(i)
        {
        case 9: // '\t'
            if(bCanEdit && !bCanEditTab)
                return;
            break;

        case 17: // '\021'
            bControlDown = true;
            break;

        case 16: // '\020'
            bShiftDown = true;
            break;

        case 10: // '\n'
            if(bCanEdit && bHistory && value.length() > 0)
                historyList.add(0, value.toString());
            break;

        case 39: // '\''
            bAllSelected = false;
            if(!bCanEdit)
                break;
            if(bControlDown)
                wordRight();
            else
                moveRight();
            return;

        case 37: // '%'
            bAllSelected = false;
            if(!bCanEdit)
                break;
            if(bControlDown)
                wordLeft();
            else
                moveLeft();
            return;

        case 38: // '&'
            if(!bCanEdit || !bHistory)
                break;
            bAllSelected = false;
            if(!historyList.isEmpty())
            {
                setValue((java.lang.String)historyList.get(historyCur));
                moveEnd();
                int j = historyList.size();
                historyCur = (historyCur + 1) % j;
            }
            return;

        case 40: // '('
            if(!bCanEdit || !bHistory)
                break;
            bAllSelected = false;
            if(!historyList.isEmpty())
            {
                int k = historyList.size();
                historyCur = ((historyCur - 1) + k) % k;
                setValue((java.lang.String)historyList.get(historyCur));
                moveEnd();
            }
            return;

        case 36: // '$'
            bAllSelected = false;
            if(bCanEdit)
            {
                moveHome();
                return;
            }
            break;

        case 35: // '#'
            bAllSelected = false;
            if(bCanEdit)
            {
                moveEnd();
                return;
            }
            break;

        case 8: // '\b'
            if(bCanEdit)
            {
                if(bAllSelected)
                    clear();
                else
                    backspace();
                bAllSelected = false;
                return;
            }
            bAllSelected = false;
            break;

        case 127: // '\177'
            if(bCanEdit)
            {
                if(bAllSelected)
                    clear();
                else
                    delete();
                bAllSelected = false;
                return;
            }
            bAllSelected = false;
            break;

        case 46: // '.'
            if(bCanEdit && bNumericFloat)
            {
                insert('.');
                return;
            }
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
            break;
        }
        super.keyboardKey(i, flag);
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

    public void keyFocusEnter()
    {
        if(bEnable && bSelectOnFocus)
            selectAll();
        super.keyFocusEnter();
    }

    public void keyFocusExit()
    {
        if(bEnable)
        {
            bAllSelected = false;
            if(bChangePending)
            {
                bChangePending = false;
                notify(2, 0);
            }
        }
        bControlDown = false;
        bShiftDown = false;
        super.keyFocusExit();
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(!bEnable || !bCanEdit || i != 0 || !flag)
        {
            return;
        } else
        {
            f -= offsetX;
            com.maddox.gwindow.GFont gfont = root.textFonts[font];
            caretOffset = gfont.len(getValue(), f, true, false);
            startShowCaret();
            bAllSelected = false;
            return;
        }
    }

    public void mouseDoubleClick(int i, float f, float f1)
    {
        super.mouseDoubleClick(i, f, f1);
        if(bEnable && i == 0)
            selectAll();
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
            bAllSelected = false;
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

    public void render()
    {
        lookAndFeel().render(this, offsetX);
        checkCaretTimeout();
    }

    public void created()
    {
        bEnableDoubleClick[0] = true;
        super.created();
    }

    public java.lang.StringBuffer value;
    public int maxLength;
    public boolean bCanEdit;
    public boolean bCanEditTab;
    public boolean bNumericOnly;
    public boolean bNumericFloat;
    public boolean bPassword;
    public boolean bSelectOnFocus;
    public boolean bDelayedNotify;
    public float offsetX;
    public boolean bAllSelected;
    public int caretOffset;
    public boolean bShowCaret;
    public float caretTimeout;
    public boolean bControlDown;
    public boolean bShiftDown;
    public boolean bChangePending;
    public boolean bHistory;
    public java.util.ArrayList historyList;
    public int historyCur;
}
