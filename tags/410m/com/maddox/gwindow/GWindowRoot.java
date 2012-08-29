// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowRoot.java

package com.maddox.gwindow;

import com.maddox.rts.Mouse;
import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GFont, GCursor, GPoint, 
//            GSize, GRegion, GWindowManager, GWindowLookAndFeel, 
//            GCanvas

public class GWindowRoot extends com.maddox.gwindow.GWindow
{

    public final com.maddox.gwindow.GWindow findWindowUnder(float f, float f1)
    {
        _findWindowUnder = this;
        _findTest.set(f, f1);
        _findOrg.set(0.0F, 0.0F);
        _findClip.set(win);
        findWindowUnder(((com.maddox.gwindow.GWindow) (this)), f, f1);
        return _findWindowUnder;
    }

    private void findWindowUnder(com.maddox.gwindow.GWindow gwindow, float f, float f1)
    {
        com.maddox.gwindow.GPoint gpoint;
        com.maddox.gwindow.GRegion gregion;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
label0:
        {
            gpoint = _findOrg;
            gregion = _findClip;
            f2 = gpoint.x;
            f3 = gpoint.y;
            gpoint.add(gwindow.win.x, gwindow.win.y);
            f -= gwindow.win.x;
            f1 -= gwindow.win.y;
            f4 = gregion.x;
            f5 = gregion.y;
            f6 = gregion.dx;
            f7 = gregion.dy;
            if(gwindow.bClip)
            {
                float f8 = gregion.x - gpoint.x;
                if(f8 < 0.0F)
                {
                    gregion.dx += f8;
                    if(gregion.dx <= 0.0F)
                        break label0;
                    gregion.x = gpoint.x;
                    f8 = 0.0F;
                }
                f8 = (gregion.dx + f8) - gwindow.win.dx;
                if(f8 > 0.0F)
                {
                    gregion.dx -= f8;
                    if(gregion.dx <= 0.0F)
                        break label0;
                }
                f8 = gregion.y - gpoint.y;
                if(f8 < 0.0F)
                {
                    gregion.dy += f8;
                    if(gregion.dy <= 0.0F)
                        break label0;
                    gregion.y = gpoint.y;
                    f8 = 0.0F;
                }
                f8 = (gregion.dy + f8) - gwindow.win.dy;
                if(f8 > 0.0F)
                {
                    gregion.dy -= f8;
                    if(gregion.dy <= 0.0F)
                        break label0;
                }
                if(_findTest.x >= gregion.x && _findTest.x < gregion.x + gregion.dx && _findTest.y >= gregion.y && _findTest.y < gregion.y + gregion.dy && !gwindow.isMousePassThrough(f, f1))
                    _findWindowUnder = gwindow;
            } else
            {
                gregion.set(root.win);
                if(_findTest.x >= gpoint.x && _findTest.x < gpoint.x + gwindow.win.dx && _findTest.y >= gpoint.y && _findTest.y < gpoint.y + gwindow.win.dy && !gwindow.isMousePassThrough(f, f1))
                    _findWindowUnder = gwindow;
            }
            if(gwindow.childWindow != null)
            {
                int i = gwindow.childWindow.size();
                for(int j = 0; j < i; j++)
                {
                    com.maddox.gwindow.GWindow gwindow1 = (com.maddox.gwindow.GWindow)gwindow.childWindow.get(j);
                    findWindowUnder(gwindow1, f, f1);
                }

            }
        }
        gregion.set(f4, f5, f6, f7);
        gpoint.set(f2, f3);
    }

    protected void doPreRender()
    {
        super.doRender(false);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        if(manager.bMouseActive)
        {
            com.maddox.gwindow.GCursor gcursor = mouseOverCursor;
            if(gcursor == null)
                gcursor = mouseCursors[mouseWindow.mouseCursor];
            if(gcursor != null && !com.maddox.rts.Mouse.adapter().isExistMouseCursorAdapter())
                gcursor.preRender(this);
        }
    }

    protected void doRender()
    {
        super.doRender(true);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        if(manager.bMouseActive)
        {
            com.maddox.gwindow.GCursor gcursor = mouseOverCursor;
            if(gcursor == null)
                gcursor = mouseCursors[mouseWindow.mouseCursor];
            if(gcursor != null)
                if(com.maddox.rts.Mouse.adapter().isExistMouseCursorAdapter())
                    com.maddox.rts.Mouse.adapter().setMouseCursor(gcursor.nativeCursor);
                else
                    gcursor.render(this);
        }
    }

    protected void doMouseMove(float f, float f1, float f2)
    {
        mouseRelMove.set(f, f1);
        mouseRelMoveZ = f2;
        if(mouseWindow != null)
            mouseWindow.mouseRelMove(f, f1, f2);
    }

    protected void doMouseAbsMove(float f, float f1, float f2)
    {
        mouseOldPos.set(mousePos);
        mouseOldPosZ = mousePosZ;
        mousePos.set(f, f1);
        mousePosZ = f2;
        mouseStep.set(mousePos.x - mouseOldPos.x, mousePos.y - mouseOldPos.y);
        mouseStepZ = mousePosZ - mouseOldPosZ;
        for(int i = 0; i < mouseListeners.size(); i++)
        {
            com.maddox.gwindow.GWindow gwindow1 = (com.maddox.gwindow.GWindow)mouseListeners.get(i);
            if(!gwindow1.isWaitModal())
                gwindow1.msgMouseMove(true, f, f1);
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        com.maddox.gwindow.GWindow gwindow;
        if(!bMouseCapture)
        {
            gwindow = findWindowUnder(f, f1);
            if(gwindow.isWaitModal())
                gwindow = modalWindow;
        } else
        {
            gwindow = mouseWindow;
        }
        if(gwindow != mouseWindow)
        {
            mouseOldWindow = mouseWindow;
            mouseWindow = gwindow;
            mouseOldWindow.mouseLeave();
            mouseWindow.mouseEnter();
            com.maddox.gwindow.GPoint gpoint = mouseWindow.getMouseXY();
            mouseWindow.mouseMove(gpoint.x, gpoint.y);
        } else
        if(mousePos.x != mouseOldPos.x || mousePos.y != mouseOldPos.y)
        {
            com.maddox.gwindow.GPoint gpoint1 = mouseWindow.getMouseXY();
            mouseWindow.mouseMove(gpoint1.x, gpoint1.y);
        }
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        for(int j = 0; j < mouseListeners.size(); j++)
        {
            com.maddox.gwindow.GWindow gwindow2 = (com.maddox.gwindow.GWindow)mouseListeners.get(j);
            if(!gwindow2.isWaitModal())
                gwindow2.msgMouseMove(false, f, f1);
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doMouseButton(int i, boolean flag, float f, float f1)
    {
        for(int j = 0; j < mouseListeners.size(); j++)
        {
            com.maddox.gwindow.GWindow gwindow1 = (com.maddox.gwindow.GWindow)mouseListeners.get(j);
            if(!gwindow1.isWaitModal())
                gwindow1.msgMouseButton(true, i, flag, f, f1);
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        if(!bMouseCapture)
        {
            com.maddox.gwindow.GWindow gwindow = findWindowUnder(f, f1);
            if(gwindow.isWaitModal())
                gwindow = modalWindow;
            if(gwindow != mouseWindow)
            {
                mouseOldWindow = mouseWindow;
                mouseWindow = gwindow;
                mouseOldWindow.mouseLeave();
                mouseWindow.mouseEnter();
                if(bDoCheckKeyFocusWindow)
                    doCheckKeyFocusWindow();
            }
        }
        com.maddox.gwindow.GPoint gpoint = mouseWindow.getMouseXY();
        mouseWindow._mouseButton(i, flag, gpoint.x, gpoint.y);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        for(int k = 0; k < mouseListeners.size(); k++)
        {
            com.maddox.gwindow.GWindow gwindow2 = (com.maddox.gwindow.GWindow)mouseListeners.get(k);
            if(!gwindow2.isWaitModal())
                gwindow2.msgMouseButton(false, i, flag, f, f1);
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    public void registerMouseListener(com.maddox.gwindow.GWindow gwindow)
    {
        if(!mouseListeners.contains(gwindow))
            mouseListeners.add(gwindow);
    }

    public void unRegisterMouseListener(com.maddox.gwindow.GWindow gwindow)
    {
        int i = mouseListeners.indexOf(gwindow);
        if(i >= 0)
            mouseListeners.remove(i);
    }

    public void registerKeyListener(com.maddox.gwindow.GWindow gwindow)
    {
        if(!keyListeners.contains(gwindow))
            keyListeners.add(gwindow);
    }

    public void unRegisterKeyListener(com.maddox.gwindow.GWindow gwindow)
    {
        int i = keyListeners.indexOf(gwindow);
        if(i >= 0)
            keyListeners.remove(i);
    }

    protected void doCheckKeyFocusWindow()
    {
        bDoCheckKeyFocusWindow = false;
        com.maddox.gwindow.GWindow gwindow = checkKeyFocusWindow();
        if(gwindow.isWaitModal())
            return;
        if(gwindow != keyFocusWindow)
        {
            keyFocusWindow.keyFocusExit();
            keyFocusWindow = gwindow;
            keyFocusWindow.keyFocusEnter();
        }
    }

    protected void doKeyboardKey(int i, boolean flag)
    {
        for(int j = keyListeners.size() - 1; j >= 0; j--)
        {
            com.maddox.gwindow.GWindow gwindow = (com.maddox.gwindow.GWindow)keyListeners.get(j);
            if(!gwindow.isWaitModal() && gwindow.hotKey(i, flag))
                return;
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        keyFocusWindow.keyboardKey(i, flag);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doKeyboardChar(char c)
    {
        for(int i = keyListeners.size() - 1; i >= 0; i--)
        {
            com.maddox.gwindow.GWindow gwindow = (com.maddox.gwindow.GWindow)keyListeners.get(i);
            if(!gwindow.isWaitModal() && gwindow.hotKeyChar(c))
                return;
        }

        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
        keyFocusWindow.keyboardChar(c);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doJoyButton(int i, int j, boolean flag)
    {
        keyFocusWindow.joyButton(i, j, flag);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doJoyMove(int i, int j, int k)
    {
        keyFocusWindow.joyMove(i, j, k);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doJoyPov(int i, int j)
    {
        keyFocusWindow.joyPov(i, j);
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    protected void doJoyPoll()
    {
        keyFocusWindow.joyPoll();
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    public boolean isActivated()
    {
        return true;
    }

    public void showWindow()
    {
    }

    public void hideWindow()
    {
    }

    public void doResolutionChanged()
    {
        resolutionChangeCounter++;
        if(lookAndFeel != null)
            lookAndFeel.resolutionChanged(this);
        resolutionChanged();
        if(childWindow != null)
        {
            for(int i = 0; i < childWindow.size(); i++)
            {
                com.maddox.gwindow.GWindow gwindow = (com.maddox.gwindow.GWindow)childWindow.get(i);
                gwindow.doResolutionChanged();
            }

        }
        if(bDoCheckKeyFocusWindow)
            doCheckKeyFocusWindow();
    }

    public void created()
    {
    }

    public GWindowRoot()
    {
        bInRender = false;
        deltaTimeSec = 0.0F;
        textFonts = new com.maddox.gwindow.GFont[8];
        mouseCursors = new com.maddox.gwindow.GCursor[16];
        bMouseCapture = false;
        mousePos = new GPoint();
        mousePosZ = 0.0F;
        mouseOldPos = new GPoint();
        mouseOldPosZ = 0.0F;
        mouseStep = new GSize();
        mouseStepZ = 0.0F;
        mouseRelMove = new GSize();
        mouseRelMoveZ = 0.0F;
        mouseWindowDown = new com.maddox.gwindow.GWindow[7];
        mouseWindowUp = new com.maddox.gwindow.GWindow[7];
        mouseTimeUp = new long[7];
        bDoCheckKeyFocusWindow = false;
        _findTest = new GPoint();
        _findOrg = new GPoint();
        _findClip = new GRegion();
        mouseListeners = new ArrayList();
        keyListeners = new ArrayList();
        root = this;
        mouseOldWindow = this;
        mouseWindow = this;
        keyFocusWindow = this;
        resolutionChangeCounter = 1;
    }

    public static long TIME_DOUBLE_CLICK = 600L;
    public boolean bInRender;
    public float deltaTimeSec;
    public com.maddox.gwindow.GCanvas C;
    public com.maddox.gwindow.GWindowLookAndFeel lookAndFeel;
    public com.maddox.gwindow.GFont textFonts[];
    public com.maddox.gwindow.GCursor mouseCursors[];
    public com.maddox.gwindow.GCursor mouseOverCursor;
    public com.maddox.gwindow.GWindowManager manager;
    public com.maddox.gwindow.GWindow modalWindow;
    public boolean bMouseCapture;
    public com.maddox.gwindow.GWindow mouseWindow;
    public com.maddox.gwindow.GWindow mouseOldWindow;
    public com.maddox.gwindow.GPoint mousePos;
    public float mousePosZ;
    public com.maddox.gwindow.GPoint mouseOldPos;
    public float mouseOldPosZ;
    public com.maddox.gwindow.GSize mouseStep;
    public float mouseStepZ;
    public com.maddox.gwindow.GSize mouseRelMove;
    public float mouseRelMoveZ;
    public com.maddox.gwindow.GWindow mouseWindowDown[];
    protected com.maddox.gwindow.GWindow mouseWindowUp[];
    protected long mouseTimeUp[];
    public com.maddox.gwindow.GWindow keyFocusWindow;
    protected boolean bDoCheckKeyFocusWindow;
    private com.maddox.gwindow.GWindow _findWindowUnder;
    private com.maddox.gwindow.GPoint _findTest;
    private com.maddox.gwindow.GPoint _findOrg;
    private com.maddox.gwindow.GRegion _findClip;
    protected java.util.ArrayList mouseListeners;
    protected java.util.ArrayList keyListeners;

}
