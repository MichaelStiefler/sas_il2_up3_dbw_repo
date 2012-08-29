// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMessageBox.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowFramed, GWindowRoot, GWindowLookAndFeel, GRegion, 
//            GFont, GWindow, GWindowButton, GWindowDialogClient

public class GWindowMessageBox extends com.maddox.gwindow.GWindowFramed
{
    class Button extends com.maddox.gwindow.GWindowButton
    {

        public boolean notify(int i, int j)
        {
            boolean flag = super.notify(i, j);
            if(i == 2)
                doResult(res);
            return flag;
        }

        int res;

        public Button(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, java.lang.String s, 
                java.lang.String s1, int i)
        {
            super(gwindow, f, f1, f2, f3, s, s1);
            res = i;
        }
    }

    class Client extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            super.render();
            com.maddox.gwindow.GRegion gregion = getClientRegion();
            lookAndFeel().drawSeparateH(this, gregion.x + lookAndFeel().metric(0.5F), gregion.dy - lookAndFeel().metric(3.5F), gregion.dx - 2.0F * lookAndFeel().metric(0.5F));
            gregion.x += lookAndFeel().metric(1.0F);
            gregion.dx -= lookAndFeel().metric(2.0F);
            gregion.y += lookAndFeel().metric(1.0F);
            gregion.dy -= lookAndFeel().metric(5.5F);
            if(pushClipRegion(gregion, true, 0.0F))
            {
                setCanvasFont(messageFont);
                lookAndFeel().setMessageBoxTextColor(this);
                drawLines(0.0F, 0.0F, message, 0, message.length(), gregion.dx, lookAndFeel().metric(0.5F) + root.textFonts[messageFont].height);
                popClip();
            }
        }

        Client()
        {
        }
    }


    public void result(int i)
    {
    }

    public void doResult(int i)
    {
        result = i;
        close(false);
        result(i);
    }

    public void close(boolean flag)
    {
        super.close(flag);
        if(result == 0)
        {
            result = 1;
            result(result);
        }
    }

    public void preRender()
    {
        super.preRender();
        if(bTimeOut)
        {
            timeOut -= root.deltaTimeSec;
            if(timeOut <= 0.0F)
                doResult(5);
        }
    }

    private void computeWin()
    {
        float f = lookAndFeel().metric();
        win.dx = metricWidth * f;
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f1 = gregion.dx - f * 2.0F;
        setCanvasFont(messageFont);
        int i = computeLines(message, 0, message.length(), f1);
        if(i == 0)
            i = 1;
        float f2 = (float)i * root.textFonts[messageFont].height + (float)(i - 1) * (0.5F * f);
        f2 += f * 5.5F;
        win.dy = (win.dy - gregion.dy) + f2;
        gregion = root.getClientRegion();
        if(win.dy > gregion.dy)
            win.dy = gregion.dy;
        win.x = (root.win.dx - win.dx) / 2.0F;
        win.y = (root.win.dy - win.dy) / 2.0F;
    }

    public void created()
    {
        super.created();
        computeWin();
    }

    public void resolutionChanged()
    {
        computeWin();
        resized();
    }

    public void afterCreated()
    {
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f = gregion.dx / lookAndFeel().metric() - 8F;
        float f1 = gregion.dy / lookAndFeel().metric() - 2.5F;
        clientWindow = create(gregion.x, gregion.y, gregion.dx, gregion.dy, false, new Client());
        com.maddox.gwindow.Client client = (com.maddox.gwindow.Client)clientWindow;
        switch(buttons)
        {
        case 0: // '\0'
            client.addDefault(new Button(client, f - 16F, f1, 7F, 2.0F, lAF().i18n("&OK"), null, 2));
            client.addControl(new Button(client, f - 8F, f1, 7F, 2.0F, lAF().i18n("&No"), null, 4));
            client.addEscape(new Button(client, f, f1, 7F, 2.0F, lAF().i18n("&Cancel"), null, 1));
            break;

        case 1: // '\001'
            client.addDefault(new Button(client, f - 8F, f1, 7F, 2.0F, lAF().i18n("&Yes"), null, 3));
            client.addEscape(new Button(client, f, f1, 7F, 2.0F, lAF().i18n("&No"), null, 4));
            break;

        case 2: // '\002'
            client.addDefault(new Button(client, f - 8F, f1, 7F, 2.0F, lAF().i18n("&OK"), null, 2));
            client.addEscape(new Button(client, f, f1, 7F, 2.0F, lAF().i18n("&Cancel"), null, 1));
            break;

        case 3: // '\003'
            client.addDefault(new Button(client, f, f1, 7F, 2.0F, lAF().i18n("&OK"), null, 2));
            break;

        case 5: // '\005'
            client.addDefault(new Button(client, f, f1, 7F, 2.0F, lAF().i18n("&Cancel"), null, 1));
            break;
        }
        super.afterCreated();
        if(bModal)
            showModal();
    }

    public GWindowMessageBox(com.maddox.gwindow.GWindow gwindow, float f, boolean flag, java.lang.String s, java.lang.String s1, int i, float f1)
    {
        messageFont = 0;
        result = 0;
        bSizable = false;
        title = s;
        message = s1;
        buttons = i;
        if(f1 <= 0.0F)
        {
            bTimeOut = false;
        } else
        {
            bTimeOut = true;
            timeOut = f1;
        }
        metricWidth = f;
        bModal = flag;
        doNew(gwindow, 0.0F, 0.0F, 100F, 100F, false);
    }

    public static final int RESULT_CANCEL = 1;
    public static final int RESULT_OK = 2;
    public static final int RESULT_YES = 3;
    public static final int RESULT_NO = 4;
    public static final int RESULT_TIMEOUT = 5;
    public static final int BUTTONS_YES_NO_CANCEL = 0;
    public static final int BUTTONS_YES_NO = 1;
    public static final int BUTTONS_OK_CANCEL = 2;
    public static final int BUTTONS_OK = 3;
    public static final int BUTTONS_NONE = 4;
    public static final int BUTTONS_CANCEL = 5;
    public static final float zTEXT_LEFT_SPACE = 1F;
    public static final float zTEXT_RIGHT_SPACE = 1F;
    public static final float zTEXT_TOP_SPACE = 1F;
    public static final float zTEXT_BOTTOM_SPACE = 1F;
    public static final float zTEXT_HLINE_SPACE = 0.5F;
    public static final float zBUTTONS_TOP_SPACE = 1F;
    public static final float zBUTTONS_BOTTOM_SPACE = 0.5F;
    public static final float zBUTTON_HEIGHT = 2F;
    public static final float zBUTTON_WIDTH = 7F;
    public static final float zBUTTONS_RIGHT_SPACE = 1F;
    public static final float zBUTTONS_SPACE = 1F;
    public static final float zSEPARATE_SPACE = 0.5F;
    public java.lang.String message;
    public int messageFont;
    public int buttons;
    public boolean bTimeOut;
    public float timeOut;
    public float metricWidth;
    public boolean bModal;
    private int result;
}
