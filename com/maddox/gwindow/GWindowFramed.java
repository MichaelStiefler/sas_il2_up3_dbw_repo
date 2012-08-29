// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFramed.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowFrameCloseBox, GSize, GWindowLookAndFeel, 
//            GWindowRoot, GPoint, GRegion, GWindowMenuBar

public class GWindowFramed extends com.maddox.gwindow.GWindow
{

    public GWindowFramed()
    {
        bMovable = true;
        bSizable = true;
        sizingState = 0;
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(i != 0)
            return;
        if(flag)
        {
            if(isMouseCaptured())
                return;
            int j = lookAndFeel().frameHitTest(this, f, f1);
            if(j == 0)
                return;
            if(j == 9)
                if(!bMovable)
                {
                    return;
                } else
                {
                    sizingState = 1;
                    mouseCapture(true);
                    mouseCursor = 3;
                    return;
                }
            if(!bSizable)
                return;
            switch(j)
            {
            case 1: // '\001'
                sizingState = 2;
                mouseCursor = 10;
                break;

            case 2: // '\002'
                sizingState = 3;
                mouseCursor = 9;
                break;

            case 3: // '\003'
                sizingState = 4;
                mouseCursor = 8;
                break;

            case 4: // '\004'
                sizingState = 5;
                mouseCursor = 11;
                break;

            case 5: // '\005'
                sizingState = 6;
                mouseCursor = 11;
                break;

            case 6: // '\006'
                sizingState = 7;
                mouseCursor = 8;
                break;

            case 7: // '\007'
                sizingState = 8;
                mouseCursor = 9;
                break;

            case 8: // '\b'
                sizingState = 9;
                mouseCursor = 10;
                break;

            default:
                return;
            }
            mouseCapture(true);
        } else
        if(isMouseCaptured())
        {
            sizingState = 0;
            mouseCapture(false);
            mouseCursor = 1;
        }
    }

    public void mouseMove(float f, float f1)
    {
        super.mouseMove(f, f1);
        com.maddox.gwindow.GRegion gregion = root.getClientRegion();
        if(root.mousePos.x < gregion.x || root.mousePos.x >= gregion.x + gregion.dx || root.mousePos.y < gregion.y || root.mousePos.y >= gregion.y + gregion.dy)
            return;
        com.maddox.gwindow.GSize gsize = null;
        if(sizingState != 1 && sizingState != 0)
        {
            _newSize.set(win.dx, win.dy);
            gsize = getMinSize();
        }
        switch(sizingState)
        {
        default:
            break;

        case 1: // '\001'
            setPos(win.x + root.mouseStep.dx, win.y + root.mouseStep.dy);
            return;

        case 0: // '\0'
            int i = lookAndFeel().frameHitTest(this, f, f1);
            mouseCursor = 1;
            if(i == 0)
                return;
            if(i == 9)
                if(!bMovable)
                {
                    return;
                } else
                {
                    mouseCursor = 3;
                    return;
                }
            if(!bSizable)
                return;
            switch(i)
            {
            case 1: // '\001'
                mouseCursor = 10;
                break;

            case 2: // '\002'
                mouseCursor = 9;
                break;

            case 3: // '\003'
                mouseCursor = 8;
                break;

            case 4: // '\004'
                mouseCursor = 11;
                break;

            case 5: // '\005'
                mouseCursor = 11;
                break;

            case 6: // '\006'
                mouseCursor = 8;
                break;

            case 7: // '\007'
                mouseCursor = 9;
                break;

            case 8: // '\b'
                mouseCursor = 10;
                break;
            }
            return;

        case 2: // '\002'
            _newSize.add(-root.mouseStep.dx, -root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x + root.mouseStep.dx, win.y + root.mouseStep.dy);
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 3: // '\003'
            _newSize.add(0.0F, -root.mouseStep.dy);
            if(_newSize.dy >= gsize.dy)
            {
                setPos(win.x, win.y + root.mouseStep.dy);
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 4: // '\004'
            _newSize.add(root.mouseStep.dx, -root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x, win.y + root.mouseStep.dy);
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 5: // '\005'
            _newSize.add(-root.mouseStep.dx, 0.0F);
            if(_newSize.dx >= gsize.dx)
            {
                setPos(win.x + root.mouseStep.dx, win.y);
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 6: // '\006'
            _newSize.add(root.mouseStep.dx, 0.0F);
            if(_newSize.dx >= gsize.dx)
            {
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 7: // '\007'
            _newSize.add(-root.mouseStep.dx, root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setPos(win.x + root.mouseStep.dx, win.y);
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 8: // '\b'
            _newSize.add(0.0F, root.mouseStep.dy);
            if(_newSize.dy >= gsize.dy)
            {
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;

        case 9: // '\t'
            _newSize.add(root.mouseStep.dx, root.mouseStep.dy);
            if(_newSize.dx >= gsize.dx && _newSize.dy >= gsize.dy)
            {
                setSize(_newSize.dx, _newSize.dy);
                return;
            }
            break;
        }
        sizingState = 0;
        mouseCapture(false);
        mouseCursor = 1;
    }

    public void resized()
    {
        super.resized();
        com.maddox.gwindow.GSize gsize = getMinSize();
        if(win.dx < gsize.dx)
            win.dx = gsize.dx;
        if(win.dy < gsize.dy)
            win.dy = gsize.dy;
        if(menuBar != null)
        {
            float f = menuBar.getMinSize().dy;
            menuBar.setSize(getClientRegion().dx, f);
            com.maddox.gwindow.GRegion gregion1 = getClientRegion();
            menuBar.setPos(gregion1.x, gregion1.y - f);
        }
        if(clientWindow != null)
        {
            com.maddox.gwindow.GRegion gregion = getClientRegion();
            float f1 = gregion.dx;
            float f2 = gregion.dy;
            clientWindow.setPos(gregion.x, gregion.y);
            clientWindow.setSize(f1, f2);
        }
        if(closeBox != null)
            lookAndFeel().frameSetCloseBoxPos(this);
    }

    public void resolutionChanged()
    {
        if(root == parentWindow)
            clampWin(root.getClientRegion());
        super.resolutionChanged();
    }

    public void clampWin(com.maddox.gwindow.GRegion gregion)
    {
        float f = win.x;
        float f1 = win.y;
        float f2 = win.dx;
        float f3 = win.dy;
        if(f + f2 > gregion.x + gregion.dx)
            f = (gregion.x + gregion.dx) - f2;
        if(f1 + f3 > gregion.y + gregion.dy)
            f1 = (gregion.y + gregion.dy) - f3;
        if(f < gregion.x)
        {
            f = gregion.x;
            if(f + f2 > gregion.x + gregion.dx)
                f2 = (gregion.x + gregion.dx) - f;
        }
        if(f1 < gregion.y)
        {
            f1 = gregion.y;
            if(f1 + f3 > gregion.y + gregion.dy)
                f3 = (gregion.y + gregion.dy) - f1;
        }
        setPos(f, f1);
        setSize(f2, f3);
    }

    public void created()
    {
        super.created();
        if(root == parentWindow)
            clampWin(root.getClientRegion());
        resized();
    }

    public void afterCreated()
    {
        closeBox = (com.maddox.gwindow.GWindowFrameCloseBox)create(new GWindowFrameCloseBox());
        lookAndFeel().frameSetCloseBoxPos(this);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        return lookAndFeel().getMinSize(this, gsize);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        return lookAndFeel().getClientRegion(this, gregion, f);
    }

    public java.lang.String title;
    public com.maddox.gwindow.GWindowMenuBar menuBar;
    public com.maddox.gwindow.GWindow clientWindow;
    public com.maddox.gwindow.GWindowFrameCloseBox closeBox;
    public boolean bMovable;
    public boolean bSizable;
    public static final int SIZING_NONE = 0;
    public static final int SIZING_MOVE = 1;
    public static final int SIZING_TL = 2;
    public static final int SIZING_T = 3;
    public static final int SIZING_TR = 4;
    public static final int SIZING_L = 5;
    public static final int SIZING_R = 6;
    public static final int SIZING_BL = 7;
    public static final int SIZING_B = 8;
    public static final int SIZING_BR = 9;
    public int sizingState;
    private static com.maddox.gwindow.GSize _newSize = new GSize();

}
