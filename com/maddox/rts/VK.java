// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   VK.java

package com.maddox.rts;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.rts:
//            LDRres

public final class VK
{

    public static final java.lang.String getKeyText(int i)
    {
        if(i >= 48 && i <= 57 || i >= 65 && i <= 90)
            return java.lang.String.valueOf((char)i);
        switch(i)
        {
        case 10: // '\n'
            return com.maddox.rts.VK.getProperty("VK.enter", "Enter");

        case 8: // '\b'
            return com.maddox.rts.VK.getProperty("VK.backSpace", "Backspace");

        case 9: // '\t'
            return com.maddox.rts.VK.getProperty("VK.tab", "Tab");

        case 3: // '\003'
            return com.maddox.rts.VK.getProperty("VK.cancel", "Cancel");

        case 12: // '\f'
            return com.maddox.rts.VK.getProperty("VK.clear", "Clear");

        case 16: // '\020'
            return com.maddox.rts.VK.getProperty("VK.shift", "Shift");

        case 17: // '\021'
            return com.maddox.rts.VK.getProperty("VK.control", "Control");

        case 18: // '\022'
            return com.maddox.rts.VK.getProperty("VK.alt", "Alt");

        case 19: // '\023'
            return com.maddox.rts.VK.getProperty("VK.pause", "Pause");

        case 20: // '\024'
            return com.maddox.rts.VK.getProperty("VK.capsLock", "Caps Lock");

        case 27: // '\033'
            return com.maddox.rts.VK.getProperty("VK.escape", "Escape");

        case 32: // ' '
            return com.maddox.rts.VK.getProperty("VK.space", "Space");

        case 33: // '!'
            return com.maddox.rts.VK.getProperty("VK.pgup", "Page Up");

        case 34: // '"'
            return com.maddox.rts.VK.getProperty("VK.pgdn", "Page Down");

        case 35: // '#'
            return com.maddox.rts.VK.getProperty("VK.end", "End");

        case 36: // '$'
            return com.maddox.rts.VK.getProperty("VK.home", "Home");

        case 37: // '%'
            return com.maddox.rts.VK.getProperty("VK.left", "Left");

        case 38: // '&'
            return com.maddox.rts.VK.getProperty("VK.up", "Up");

        case 39: // '\''
            return com.maddox.rts.VK.getProperty("VK.right", "Right");

        case 40: // '('
            return com.maddox.rts.VK.getProperty("VK.down", "Down");

        case 44: // ','
            return com.maddox.rts.VK.getProperty("VK.comma", "Comma");

        case 46: // '.'
            return com.maddox.rts.VK.getProperty("VK.period", "Period");

        case 47: // '/'
            return com.maddox.rts.VK.getProperty("VK.slash", "Slash");

        case 59: // ';'
            return com.maddox.rts.VK.getProperty("VK.semicolon", "Semicolon");

        case 61: // '='
            return com.maddox.rts.VK.getProperty("VK.equals", "Equals");

        case 91: // '['
            return com.maddox.rts.VK.getProperty("VK.open_bracket", "OpenBracket");

        case 92: // '\\'
            return com.maddox.rts.VK.getProperty("VK.back_slash", "BackSlash");

        case 93: // ']'
            return com.maddox.rts.VK.getProperty("VK.close_bracket", "CloseBracket");

        case 106: // 'j'
            return com.maddox.rts.VK.getProperty("VK.multiply", "NumPad *");

        case 107: // 'k'
            return com.maddox.rts.VK.getProperty("VK.add", "NumPad +");

        case 108: // 'l'
            return com.maddox.rts.VK.getProperty("VK.separater", "NumPad ,");

        case 109: // 'm'
            return com.maddox.rts.VK.getProperty("VK.subtract", "NumPad -");

        case 110: // 'n'
            return com.maddox.rts.VK.getProperty("VK.decimal", "NumPad .");

        case 111: // 'o'
            return com.maddox.rts.VK.getProperty("VK.divide", "NumPad /");

        case 127: // '\177'
            return com.maddox.rts.VK.getProperty("VK.delete", "Delete");

        case 144: 
            return com.maddox.rts.VK.getProperty("VK.numLock", "Num Lock");

        case 145: 
            return com.maddox.rts.VK.getProperty("VK.scrollLock", "Scroll Lock");

        case 112: // 'p'
            return com.maddox.rts.VK.getProperty("VK.f1", "F1");

        case 113: // 'q'
            return com.maddox.rts.VK.getProperty("VK.f2", "F2");

        case 114: // 'r'
            return com.maddox.rts.VK.getProperty("VK.f3", "F3");

        case 115: // 's'
            return com.maddox.rts.VK.getProperty("VK.f4", "F4");

        case 116: // 't'
            return com.maddox.rts.VK.getProperty("VK.f5", "F5");

        case 117: // 'u'
            return com.maddox.rts.VK.getProperty("VK.f6", "F6");

        case 118: // 'v'
            return com.maddox.rts.VK.getProperty("VK.f7", "F7");

        case 119: // 'w'
            return com.maddox.rts.VK.getProperty("VK.f8", "F8");

        case 120: // 'x'
            return com.maddox.rts.VK.getProperty("VK.f9", "F9");

        case 121: // 'y'
            return com.maddox.rts.VK.getProperty("VK.f10", "F10");

        case 122: // 'z'
            return com.maddox.rts.VK.getProperty("VK.f11", "F11");

        case 123: // '{'
            return com.maddox.rts.VK.getProperty("VK.f12", "F12");

        case 154: 
            return com.maddox.rts.VK.getProperty("VK.printScreen", "Print Screen");

        case 155: 
            return com.maddox.rts.VK.getProperty("VK.insert", "Insert");

        case 156: 
            return com.maddox.rts.VK.getProperty("VK.help", "Help");

        case 157: 
            return com.maddox.rts.VK.getProperty("VK.meta", "Meta");

        case 192: 
            return com.maddox.rts.VK.getProperty("VK.backQuote", "Back Quote");

        case 222: 
            return com.maddox.rts.VK.getProperty("VK.quote", "Quote");

        case 224: 
            return com.maddox.rts.VK.getProperty("VK.KeyPadUp", "KeyPadUp");

        case 225: 
            return com.maddox.rts.VK.getProperty("VK.KeyPadDown", "KeyPadDown");

        case 226: 
            return com.maddox.rts.VK.getProperty("VK.KeyPadLeft", "KeyPadLeft");

        case 227: 
            return com.maddox.rts.VK.getProperty("VK.KeyPadRight", "KeyPadRight");

        case 128: 
            return com.maddox.rts.VK.getProperty("VK.deadGrave", "Dead Grave");

        case 129: 
            return com.maddox.rts.VK.getProperty("VK.deadAcute", "Dead Acute");

        case 130: 
            return com.maddox.rts.VK.getProperty("VK.deadCircumflex", "Dead Circumflex");

        case 131: 
            return com.maddox.rts.VK.getProperty("VK.deadTilde", "Dead Tilde");

        case 132: 
            return com.maddox.rts.VK.getProperty("VK.deadMacron", "Dead Macron");

        case 133: 
            return com.maddox.rts.VK.getProperty("VK.deadBreve", "Dead Breve");

        case 134: 
            return com.maddox.rts.VK.getProperty("VK.deadAboveDot", "Dead Above Dot");

        case 135: 
            return com.maddox.rts.VK.getProperty("VK.deadDiaeresis", "Dead Diaeresis");

        case 136: 
            return com.maddox.rts.VK.getProperty("VK.deadAboveRing", "Dead Above Ring");

        case 137: 
            return com.maddox.rts.VK.getProperty("VK.deadDoubleAcute", "Dead Double Acute");

        case 138: 
            return com.maddox.rts.VK.getProperty("VK.deadCaron", "Dead Caron");

        case 139: 
            return com.maddox.rts.VK.getProperty("VK.deadCedilla", "Dead Cedilla");

        case 140: 
            return com.maddox.rts.VK.getProperty("VK.deadOgonek", "Dead Ogonek");

        case 141: 
            return com.maddox.rts.VK.getProperty("VK.deadIota", "Dead Iota");

        case 142: 
            return com.maddox.rts.VK.getProperty("VK.deadVoicedSound", "Dead Voiced Sound");

        case 143: 
            return com.maddox.rts.VK.getProperty("VK.deadSemivoicedSound", "Dead Semivoiced Sound");

        case 150: 
            return com.maddox.rts.VK.getProperty("VK.ampersand", "Ampersand");

        case 151: 
            return com.maddox.rts.VK.getProperty("VK.asterisk", "Asterisk");

        case 152: 
            return com.maddox.rts.VK.getProperty("VK.quoteDbl", "Double Quote");

        case 153: 
            return com.maddox.rts.VK.getProperty("VK.Less", "Less");

        case 160: 
            return com.maddox.rts.VK.getProperty("VK.greater", "Greater");

        case 161: 
            return com.maddox.rts.VK.getProperty("VK.braceLeft", "Left Brace");

        case 162: 
            return com.maddox.rts.VK.getProperty("VK.braceRight", "Right Brace");

        case 512: 
            return com.maddox.rts.VK.getProperty("VK.at", "At");

        case 513: 
            return com.maddox.rts.VK.getProperty("VK.colon", "Colon");

        case 514: 
            return com.maddox.rts.VK.getProperty("VK.circumflex", "Circumflex");

        case 515: 
            return com.maddox.rts.VK.getProperty("VK.dollar", "Dollar");

        case 516: 
            return com.maddox.rts.VK.getProperty("VK.euro", "Euro");

        case 517: 
            return com.maddox.rts.VK.getProperty("VK.exclamationMark", "Exclamation Mark");

        case 518: 
            return com.maddox.rts.VK.getProperty("VK.invertedExclamationMark", "Inverted Exclamation Mark");

        case 519: 
            return com.maddox.rts.VK.getProperty("VK.leftParenthesis", "Left Parenthesis");

        case 520: 
            return com.maddox.rts.VK.getProperty("VK.numberSign", "Number Sign");

        case 521: 
            return com.maddox.rts.VK.getProperty("VK.plus", "Plus");

        case 522: 
            return com.maddox.rts.VK.getProperty("VK.rightParenthesis", "Right Parenthesis");

        case 523: 
            return com.maddox.rts.VK.getProperty("VK.underscore", "Underscore");

        case 24: // '\030'
            return com.maddox.rts.VK.getProperty("VK.final", "Final");

        case 28: // '\034'
            return com.maddox.rts.VK.getProperty("VK.convert", "Convert");

        case 29: // '\035'
            return com.maddox.rts.VK.getProperty("VK.noconvert", "No Convert");

        case 30: // '\036'
            return com.maddox.rts.VK.getProperty("VK.accept", "Accept");

        case 31: // '\037'
            return com.maddox.rts.VK.getProperty("VK.modechange", "Mode Change");

        case 21: // '\025'
            return com.maddox.rts.VK.getProperty("VK.kana", "Kana");

        case 25: // '\031'
            return com.maddox.rts.VK.getProperty("VK.kanji", "Kanji");

        case 240: 
            return com.maddox.rts.VK.getProperty("VK.alphanumeric", "Alphanumeric");

        case 241: 
            return com.maddox.rts.VK.getProperty("VK.katakana", "Katakana");

        case 242: 
            return com.maddox.rts.VK.getProperty("VK.hiragana", "Hiragana");

        case 243: 
            return com.maddox.rts.VK.getProperty("VK.fullWidth", "Full-Width");

        case 244: 
            return com.maddox.rts.VK.getProperty("VK.halfWidth", "Half-Width");

        case 245: 
            return com.maddox.rts.VK.getProperty("VK.romanCharacters", "Roman Characters");

        case 256: 
            return com.maddox.rts.VK.getProperty("VK.allCandidates", "All Candidates");

        case 257: 
            return com.maddox.rts.VK.getProperty("VK.previousCandidate", "Previous Candidate");

        case 258: 
            return com.maddox.rts.VK.getProperty("VK.codeInput", "Code Input");

        case 259: 
            return com.maddox.rts.VK.getProperty("VK.japaneseKatakana", "Japanese Katakana");

        case 260: 
            return com.maddox.rts.VK.getProperty("VK.japaneseHiragana", "Japanese Hiragana");

        case 261: 
            return com.maddox.rts.VK.getProperty("VK.japaneseRoman", "Japanese Roman");

        case 524: 
            return com.maddox.rts.VK.getProperty("VK.mouse0", "MouseLeft");

        case 525: 
            return com.maddox.rts.VK.getProperty("VK.mouse1", "MouseRight");

        case 526: 
            return com.maddox.rts.VK.getProperty("VK.mouse2", "MouseMiddle");

        case 527: 
            return com.maddox.rts.VK.getProperty("VK.mouse3", "MouseFour");

        case 528: 
            return com.maddox.rts.VK.getProperty("VK.mouseAxe_X", "MouseAXE_X");

        case 529: 
            return com.maddox.rts.VK.getProperty("VK.mouseAxe_Y", "MouseAXE_Y");

        case 530: 
            return com.maddox.rts.VK.getProperty("VK.mouseAxe_Z", "MouseAXE_Z");

        case 531: 
            return com.maddox.rts.VK.getProperty("VK.joy0", "Joystick0");

        case 532: 
            return com.maddox.rts.VK.getProperty("VK.joy1", "Joystick1");

        case 533: 
            return com.maddox.rts.VK.getProperty("VK.joy2", "Joystick2");

        case 534: 
            return com.maddox.rts.VK.getProperty("VK.joy3", "Joystick3");

        case 535: 
            return com.maddox.rts.VK.getProperty("VK.joy4", "Joystick4");

        case 536: 
            return com.maddox.rts.VK.getProperty("VK.joy5", "Joystick5");

        case 537: 
            return com.maddox.rts.VK.getProperty("VK.joy6", "Joystick6");

        case 538: 
            return com.maddox.rts.VK.getProperty("VK.joy7", "Joystick7");

        case 539: 
            return com.maddox.rts.VK.getProperty("VK.joy8", "Joystick8");

        case 540: 
            return com.maddox.rts.VK.getProperty("VK.joy9", "Joystick9");

        case 541: 
            return com.maddox.rts.VK.getProperty("VK.joy10", "Joystick10");

        case 542: 
            return com.maddox.rts.VK.getProperty("VK.joy11", "Joystick11");

        case 543: 
            return com.maddox.rts.VK.getProperty("VK.joy12", "Joystick12");

        case 544: 
            return com.maddox.rts.VK.getProperty("VK.joy13", "Joystick13");

        case 545: 
            return com.maddox.rts.VK.getProperty("VK.joy14", "Joystick14");

        case 546: 
            return com.maddox.rts.VK.getProperty("VK.joy15", "Joystick15");

        case 547: 
            return com.maddox.rts.VK.getProperty("VK.joy16", "Joystick16");

        case 548: 
            return com.maddox.rts.VK.getProperty("VK.joy17", "Joystick17");

        case 549: 
            return com.maddox.rts.VK.getProperty("VK.joy18", "Joystick18");

        case 550: 
            return com.maddox.rts.VK.getProperty("VK.joy19", "Joystick19");

        case 551: 
            return com.maddox.rts.VK.getProperty("VK.joy20", "Joystick10");

        case 552: 
            return com.maddox.rts.VK.getProperty("VK.joy21", "Joystick21");

        case 553: 
            return com.maddox.rts.VK.getProperty("VK.joy22", "Joystick22");

        case 554: 
            return com.maddox.rts.VK.getProperty("VK.joy23", "Joystick23");

        case 555: 
            return com.maddox.rts.VK.getProperty("VK.joy24", "Joystick24");

        case 556: 
            return com.maddox.rts.VK.getProperty("VK.joy25", "Joystick25");

        case 557: 
            return com.maddox.rts.VK.getProperty("VK.joy26", "Joystick26");

        case 558: 
            return com.maddox.rts.VK.getProperty("VK.joy27", "Joystick27");

        case 559: 
            return com.maddox.rts.VK.getProperty("VK.joy28", "Joystick28");

        case 560: 
            return com.maddox.rts.VK.getProperty("VK.joy29", "Joystick29");

        case 561: 
            return com.maddox.rts.VK.getProperty("VK.joy30", "Joystick30");

        case 562: 
            return com.maddox.rts.VK.getProperty("VK.joy31", "Joystick31");

        case 563: 
            return com.maddox.rts.VK.getProperty("VK.axe_X", "AXE_X");

        case 564: 
            return com.maddox.rts.VK.getProperty("VK.axe_Y", "AXE_Y");

        case 565: 
            return com.maddox.rts.VK.getProperty("VK.axe_Z", "AXE_Z");

        case 566: 
            return com.maddox.rts.VK.getProperty("VK.axe_RX", "AXE_RX");

        case 567: 
            return com.maddox.rts.VK.getProperty("VK.axe_RY", "AXE_RY");

        case 568: 
            return com.maddox.rts.VK.getProperty("VK.axe_RZ", "AXE_RZ");

        case 569: 
            return com.maddox.rts.VK.getProperty("VK.axe_U", "AXE_U");

        case 570: 
            return com.maddox.rts.VK.getProperty("VK.axe_V", "AXE_V");

        case 571: 
            return com.maddox.rts.VK.getProperty("VK.pov_1", "Pov-1");

        case 572: 
            return com.maddox.rts.VK.getProperty("VK.pov0", "Pov0");

        case 573: 
            return com.maddox.rts.VK.getProperty("VK.pov45", "Pov45");

        case 574: 
            return com.maddox.rts.VK.getProperty("VK.pov90", "Pov90");

        case 575: 
            return com.maddox.rts.VK.getProperty("VK.pov135", "Pov135");

        case 576: 
            return com.maddox.rts.VK.getProperty("VK.pov180", "Pov180");

        case 577: 
            return com.maddox.rts.VK.getProperty("VK.pov225", "Pov225");

        case 578: 
            return com.maddox.rts.VK.getProperty("VK.pov270", "Pov270");

        case 579: 
            return com.maddox.rts.VK.getProperty("VK.pov315", "Pov315");

        case 580: 
            return com.maddox.rts.VK.getProperty("VK.joyDev0", "JoystickDevice0");

        case 581: 
            return com.maddox.rts.VK.getProperty("VK.joyDev1", "JoystickDevice1");

        case 582: 
            return com.maddox.rts.VK.getProperty("VK.joyDev2", "JoystickDevice2");

        case 583: 
            return com.maddox.rts.VK.getProperty("VK.joyDev3", "JoystickDevice3");

        case 584: 
            return com.maddox.rts.VK.getProperty("VK.joyPov0", "JoystickPov0");

        case 585: 
            return com.maddox.rts.VK.getProperty("VK.joyPov1", "JoystickPov1");

        case 586: 
            return com.maddox.rts.VK.getProperty("VK.joyPov2", "JoystickPov2");

        case 587: 
            return com.maddox.rts.VK.getProperty("VK.joyPov3", "JoystickPov3");

        case 588: 
            return com.maddox.rts.VK.getProperty("VK.joyPov4", "JoystickPov4");

        case 589: 
            return com.maddox.rts.VK.getProperty("VK.joyPov5", "JoystickPov5");

        case 590: 
            return com.maddox.rts.VK.getProperty("VK.joyPov6", "JoystickPov6");

        case 591: 
            return com.maddox.rts.VK.getProperty("VK.joyPov7", "JoystickPov7");

        case 592: 
            return com.maddox.rts.VK.getProperty("VK.joyPov8", "JoystickPov8");

        case 593: 
            return com.maddox.rts.VK.getProperty("VK.joyPov9", "JoystickPov9");

        case 594: 
            return com.maddox.rts.VK.getProperty("VK.joyPov10", "JoystickPov10");

        case 595: 
            return com.maddox.rts.VK.getProperty("VK.joyPov11", "JoystickPov11");

        case 596: 
            return com.maddox.rts.VK.getProperty("VK.joyPov12", "JoystickPov12");

        case 597: 
            return com.maddox.rts.VK.getProperty("VK.joyPov13", "JoystickPov13");

        case 598: 
            return com.maddox.rts.VK.getProperty("VK.joyPov14", "JoystickPov14");

        case 599: 
            return com.maddox.rts.VK.getProperty("VK.joyPov15", "JoystickPov15");

        case 600: 
            return com.maddox.rts.VK.getProperty("VK.joyPoll", "JoystickPoll");
        }
        if(i >= 96 && i <= 105)
        {
            java.lang.String s = com.maddox.rts.VK.getProperty("VK.numpad", "NumPad");
            char c = (char)((i - 96) + 48);
            return s + "-" + c;
        } else
        {
            return "";
        }
    }

    public static final int getKeyFromText(java.lang.String s)
    {
        java.lang.Integer integer = (java.lang.Integer)hashNames.get(s);
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    private VK()
    {
    }

    public static java.lang.String getProperty(java.lang.String s, java.lang.String s1)
    {
        if(resources == null)
            break MISSING_BLOCK_LABEL_18;
        return resources.getString(s);
        java.util.MissingResourceException missingresourceexception;
        missingresourceexception;
        return s1;
    }

    public static final int KEYBOARD_KEYS_OFS = 0;
    public static final int KEYBOARD_KEYS = 524;
    public static final int MOUSE_KEYS_OFS = 524;
    public static final int MOUSE_KEYS = 7;
    public static final int JOY_KEYS_OFS = 531;
    public static final int JOY_KEYS = 70;
    public static final int ALL_KEYS = 601;
    public static final int ENTER = 10;
    public static final int BACK_SPACE = 8;
    public static final int TAB = 9;
    public static final int CANCEL = 3;
    public static final int CLEAR = 12;
    public static final int SHIFT = 16;
    public static final int CONTROL = 17;
    public static final int ALT = 18;
    public static final int PAUSE = 19;
    public static final int CAPS_LOCK = 20;
    public static final int ESCAPE = 27;
    public static final int SPACE = 32;
    public static final int PAGE_UP = 33;
    public static final int PAGE_DOWN = 34;
    public static final int END = 35;
    public static final int HOME = 36;
    public static final int LEFT = 37;
    public static final int UP = 38;
    public static final int RIGHT = 39;
    public static final int DOWN = 40;
    public static final int COMMA = 44;
    public static final int PERIOD = 46;
    public static final int SLASH = 47;
    public static final int _0 = 48;
    public static final int _1 = 49;
    public static final int _2 = 50;
    public static final int _3 = 51;
    public static final int _4 = 52;
    public static final int _5 = 53;
    public static final int _6 = 54;
    public static final int _7 = 55;
    public static final int _8 = 56;
    public static final int _9 = 57;
    public static final int SEMICOLON = 59;
    public static final int EQUALS = 61;
    public static final int A = 65;
    public static final int B = 66;
    public static final int C = 67;
    public static final int D = 68;
    public static final int E = 69;
    public static final int F = 70;
    public static final int G = 71;
    public static final int H = 72;
    public static final int I = 73;
    public static final int J = 74;
    public static final int K = 75;
    public static final int L = 76;
    public static final int M = 77;
    public static final int N = 78;
    public static final int O = 79;
    public static final int P = 80;
    public static final int Q = 81;
    public static final int R = 82;
    public static final int S = 83;
    public static final int T = 84;
    public static final int U = 85;
    public static final int V = 86;
    public static final int W = 87;
    public static final int X = 88;
    public static final int Y = 89;
    public static final int Z = 90;
    public static final int OPEN_BRACKET = 91;
    public static final int BACK_SLASH = 92;
    public static final int CLOSE_BRACKET = 93;
    public static final int NUMPAD0 = 96;
    public static final int NUMPAD1 = 97;
    public static final int NUMPAD2 = 98;
    public static final int NUMPAD3 = 99;
    public static final int NUMPAD4 = 100;
    public static final int NUMPAD5 = 101;
    public static final int NUMPAD6 = 102;
    public static final int NUMPAD7 = 103;
    public static final int NUMPAD8 = 104;
    public static final int NUMPAD9 = 105;
    public static final int MULTIPLY = 106;
    public static final int ADD = 107;
    public static final int SEPARATER = 108;
    public static final int SUBTRACT = 109;
    public static final int DECIMAL = 110;
    public static final int DIVIDE = 111;
    public static final int DELETE = 127;
    public static final int NUM_LOCK = 144;
    public static final int SCROLL_LOCK = 145;
    public static final int F1 = 112;
    public static final int F2 = 113;
    public static final int F3 = 114;
    public static final int F4 = 115;
    public static final int F5 = 116;
    public static final int F6 = 117;
    public static final int F7 = 118;
    public static final int F8 = 119;
    public static final int F9 = 120;
    public static final int F10 = 121;
    public static final int F11 = 122;
    public static final int F12 = 123;
    public static final int PRINTSCREEN = 154;
    public static final int INSERT = 155;
    public static final int HELP = 156;
    public static final int META = 157;
    public static final int BACK_QUOTE = 192;
    public static final int QUOTE = 222;
    public static final int KP_UP = 224;
    public static final int KP_DOWN = 225;
    public static final int KP_LEFT = 226;
    public static final int KP_RIGHT = 227;
    public static final int DEAD_GRAVE = 128;
    public static final int DEAD_ACUTE = 129;
    public static final int DEAD_CIRCUMFLEX = 130;
    public static final int DEAD_TILDE = 131;
    public static final int DEAD_MACRON = 132;
    public static final int DEAD_BREVE = 133;
    public static final int DEAD_ABOVEDOT = 134;
    public static final int DEAD_DIAERESIS = 135;
    public static final int DEAD_ABOVERING = 136;
    public static final int DEAD_DOUBLEACUTE = 137;
    public static final int DEAD_CARON = 138;
    public static final int DEAD_CEDILLA = 139;
    public static final int DEAD_OGONEK = 140;
    public static final int DEAD_IOTA = 141;
    public static final int DEAD_VOICED_SOUND = 142;
    public static final int DEAD_SEMIVOICED_SOUND = 143;
    public static final int AMPERSAND = 150;
    public static final int ASTERISK = 151;
    public static final int QUOTEDBL = 152;
    public static final int LESS = 153;
    public static final int GREATER = 160;
    public static final int BRACELEFT = 161;
    public static final int BRACERIGHT = 162;
    public static final int AT = 512;
    public static final int COLON = 513;
    public static final int CIRCUMFLEX = 514;
    public static final int DOLLAR = 515;
    public static final int EURO_SIGN = 516;
    public static final int EXCLAMATION_MARK = 517;
    public static final int INVERTED_EXCLAMATION_MARK = 518;
    public static final int LEFT_PARENTHESIS = 519;
    public static final int NUMBER_SIGN = 520;
    public static final int PLUS = 521;
    public static final int RIGHT_PARENTHESIS = 522;
    public static final int UNDERSCORE = 523;
    public static final int FINAL = 24;
    public static final int CONVERT = 28;
    public static final int NONCONVERT = 29;
    public static final int ACCEPT = 30;
    public static final int MODECHANGE = 31;
    public static final int KANA = 21;
    public static final int KANJI = 25;
    public static final int ALPHANUMERIC = 240;
    public static final int KATAKANA = 241;
    public static final int HIRAGANA = 242;
    public static final int FULL_WIDTH = 243;
    public static final int HALF_WIDTH = 244;
    public static final int ROMAN_CHARACTERS = 245;
    public static final int ALL_CANDIDATES = 256;
    public static final int PREVIOUS_CANDIDATE = 257;
    public static final int CODE_INPUT = 258;
    public static final int JAPANESE_KATAKANA = 259;
    public static final int JAPANESE_HIRAGANA = 260;
    public static final int JAPANESE_ROMAN = 261;
    public static final int MOUSE0 = 524;
    public static final int MOUSE1 = 525;
    public static final int MOUSE2 = 526;
    public static final int MOUSE3 = 527;
    public static final int MOUSEAXE_X = 528;
    public static final int MOUSEAXE_Y = 529;
    public static final int MOUSEAXE_Z = 530;
    public static final int JOY0 = 531;
    public static final int JOY1 = 532;
    public static final int JOY2 = 533;
    public static final int JOY3 = 534;
    public static final int JOY4 = 535;
    public static final int JOY5 = 536;
    public static final int JOY6 = 537;
    public static final int JOY7 = 538;
    public static final int JOY8 = 539;
    public static final int JOY9 = 540;
    public static final int JOY10 = 541;
    public static final int JOY11 = 542;
    public static final int JOY12 = 543;
    public static final int JOY13 = 544;
    public static final int JOY14 = 545;
    public static final int JOY15 = 546;
    public static final int JOY16 = 547;
    public static final int JOY17 = 548;
    public static final int JOY18 = 549;
    public static final int JOY19 = 550;
    public static final int JOY20 = 551;
    public static final int JOY21 = 552;
    public static final int JOY22 = 553;
    public static final int JOY23 = 554;
    public static final int JOY24 = 555;
    public static final int JOY25 = 556;
    public static final int JOY26 = 557;
    public static final int JOY27 = 558;
    public static final int JOY28 = 559;
    public static final int JOY29 = 560;
    public static final int JOY30 = 561;
    public static final int JOY31 = 562;
    public static final int JOYAXE_X = 563;
    public static final int JOYAXE_Y = 564;
    public static final int JOYAXE_Z = 565;
    public static final int JOYAXE_RX = 566;
    public static final int JOYAXE_RY = 567;
    public static final int JOYAXE_RZ = 568;
    public static final int JOYAXE_U = 569;
    public static final int JOYAXE_V = 570;
    public static final int POV_1 = 571;
    public static final int POV0 = 572;
    public static final int POV45 = 573;
    public static final int POV90 = 574;
    public static final int POV135 = 575;
    public static final int POV180 = 576;
    public static final int POV225 = 577;
    public static final int POV270 = 578;
    public static final int POV315 = 579;
    public static final int JOYDEV0 = 580;
    public static final int JOYDEV1 = 581;
    public static final int JOYDEV2 = 582;
    public static final int JOYDEV3 = 583;
    public static final int JOYPOV0 = 584;
    public static final int JOYPOV1 = 585;
    public static final int JOYPOV2 = 586;
    public static final int JOYPOV3 = 587;
    public static final int JOYPOV4 = 588;
    public static final int JOYPOV5 = 589;
    public static final int JOYPOV6 = 590;
    public static final int JOYPOV7 = 591;
    public static final int JOYPOV8 = 592;
    public static final int JOYPOV9 = 593;
    public static final int JOYPOV10 = 594;
    public static final int JOYPOV11 = 595;
    public static final int JOYPOV12 = 596;
    public static final int JOYPOV13 = 597;
    public static final int JOYPOV14 = 598;
    public static final int JOYPOV15 = 599;
    public static final int JOYPOLL = 600;
    private static java.util.ResourceBundle resources;
    private static java.util.HashMap hashNames;

    static 
    {
        try
        {
            resources = java.util.ResourceBundle.getBundle("com.maddox.rts.VK", java.util.Locale.getDefault(), com.maddox.rts.LDRres.loader());
        }
        catch(java.util.MissingResourceException missingresourceexception) { }
        hashNames = new HashMap(621, 0.99F);
        for(int i = 0; i <= 601; i++)
        {
            java.lang.String s = com.maddox.rts.VK.getKeyText(i);
            if(s.length() > 0)
                hashNames.put(s, new Integer(i));
        }

    }
}
