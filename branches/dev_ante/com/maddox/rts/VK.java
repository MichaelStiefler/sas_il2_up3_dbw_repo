package com.maddox.rts;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class VK
{
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
  private static ResourceBundle resources;
  private static HashMap hashNames;

  public static final String getKeyText(int paramInt)
  {
    if (((paramInt >= 48) && (paramInt <= 57)) || ((paramInt >= 65) && (paramInt <= 90)))
    {
      return String.valueOf((char)paramInt);
    }

    switch (paramInt) { case 10:
      return getProperty("VK.enter", "Enter");
    case 8:
      return getProperty("VK.backSpace", "Backspace");
    case 9:
      return getProperty("VK.tab", "Tab");
    case 3:
      return getProperty("VK.cancel", "Cancel");
    case 12:
      return getProperty("VK.clear", "Clear");
    case 16:
      return getProperty("VK.shift", "Shift");
    case 17:
      return getProperty("VK.control", "Control");
    case 18:
      return getProperty("VK.alt", "Alt");
    case 19:
      return getProperty("VK.pause", "Pause");
    case 20:
      return getProperty("VK.capsLock", "Caps Lock");
    case 27:
      return getProperty("VK.escape", "Escape");
    case 32:
      return getProperty("VK.space", "Space");
    case 33:
      return getProperty("VK.pgup", "Page Up");
    case 34:
      return getProperty("VK.pgdn", "Page Down");
    case 35:
      return getProperty("VK.end", "End");
    case 36:
      return getProperty("VK.home", "Home");
    case 37:
      return getProperty("VK.left", "Left");
    case 38:
      return getProperty("VK.up", "Up");
    case 39:
      return getProperty("VK.right", "Right");
    case 40:
      return getProperty("VK.down", "Down");
    case 44:
      return getProperty("VK.comma", "Comma");
    case 46:
      return getProperty("VK.period", "Period");
    case 47:
      return getProperty("VK.slash", "Slash");
    case 59:
      return getProperty("VK.semicolon", "Semicolon");
    case 61:
      return getProperty("VK.equals", "Equals");
    case 91:
      return getProperty("VK.open_bracket", "OpenBracket");
    case 92:
      return getProperty("VK.back_slash", "BackSlash");
    case 93:
      return getProperty("VK.close_bracket", "CloseBracket");
    case 106:
      return getProperty("VK.multiply", "NumPad *");
    case 107:
      return getProperty("VK.add", "NumPad +");
    case 108:
      return getProperty("VK.separater", "NumPad ,");
    case 109:
      return getProperty("VK.subtract", "NumPad -");
    case 110:
      return getProperty("VK.decimal", "NumPad .");
    case 111:
      return getProperty("VK.divide", "NumPad /");
    case 127:
      return getProperty("VK.delete", "Delete");
    case 144:
      return getProperty("VK.numLock", "Num Lock");
    case 145:
      return getProperty("VK.scrollLock", "Scroll Lock");
    case 112:
      return getProperty("VK.f1", "F1");
    case 113:
      return getProperty("VK.f2", "F2");
    case 114:
      return getProperty("VK.f3", "F3");
    case 115:
      return getProperty("VK.f4", "F4");
    case 116:
      return getProperty("VK.f5", "F5");
    case 117:
      return getProperty("VK.f6", "F6");
    case 118:
      return getProperty("VK.f7", "F7");
    case 119:
      return getProperty("VK.f8", "F8");
    case 120:
      return getProperty("VK.f9", "F9");
    case 121:
      return getProperty("VK.f10", "F10");
    case 122:
      return getProperty("VK.f11", "F11");
    case 123:
      return getProperty("VK.f12", "F12");
    case 154:
      return getProperty("VK.printScreen", "Print Screen");
    case 155:
      return getProperty("VK.insert", "Insert");
    case 156:
      return getProperty("VK.help", "Help");
    case 157:
      return getProperty("VK.meta", "Meta");
    case 192:
      return getProperty("VK.backQuote", "Back Quote");
    case 222:
      return getProperty("VK.quote", "Quote");
    case 224:
      return getProperty("VK.KeyPadUp", "KeyPadUp");
    case 225:
      return getProperty("VK.KeyPadDown", "KeyPadDown");
    case 226:
      return getProperty("VK.KeyPadLeft", "KeyPadLeft");
    case 227:
      return getProperty("VK.KeyPadRight", "KeyPadRight");
    case 128:
      return getProperty("VK.deadGrave", "Dead Grave");
    case 129:
      return getProperty("VK.deadAcute", "Dead Acute");
    case 130:
      return getProperty("VK.deadCircumflex", "Dead Circumflex");
    case 131:
      return getProperty("VK.deadTilde", "Dead Tilde");
    case 132:
      return getProperty("VK.deadMacron", "Dead Macron");
    case 133:
      return getProperty("VK.deadBreve", "Dead Breve");
    case 134:
      return getProperty("VK.deadAboveDot", "Dead Above Dot");
    case 135:
      return getProperty("VK.deadDiaeresis", "Dead Diaeresis");
    case 136:
      return getProperty("VK.deadAboveRing", "Dead Above Ring");
    case 137:
      return getProperty("VK.deadDoubleAcute", "Dead Double Acute");
    case 138:
      return getProperty("VK.deadCaron", "Dead Caron");
    case 139:
      return getProperty("VK.deadCedilla", "Dead Cedilla");
    case 140:
      return getProperty("VK.deadOgonek", "Dead Ogonek");
    case 141:
      return getProperty("VK.deadIota", "Dead Iota");
    case 142:
      return getProperty("VK.deadVoicedSound", "Dead Voiced Sound");
    case 143:
      return getProperty("VK.deadSemivoicedSound", "Dead Semivoiced Sound");
    case 150:
      return getProperty("VK.ampersand", "Ampersand");
    case 151:
      return getProperty("VK.asterisk", "Asterisk");
    case 152:
      return getProperty("VK.quoteDbl", "Double Quote");
    case 153:
      return getProperty("VK.Less", "Less");
    case 160:
      return getProperty("VK.greater", "Greater");
    case 161:
      return getProperty("VK.braceLeft", "Left Brace");
    case 162:
      return getProperty("VK.braceRight", "Right Brace");
    case 512:
      return getProperty("VK.at", "At");
    case 513:
      return getProperty("VK.colon", "Colon");
    case 514:
      return getProperty("VK.circumflex", "Circumflex");
    case 515:
      return getProperty("VK.dollar", "Dollar");
    case 516:
      return getProperty("VK.euro", "Euro");
    case 517:
      return getProperty("VK.exclamationMark", "Exclamation Mark");
    case 518:
      return getProperty("VK.invertedExclamationMark", "Inverted Exclamation Mark");
    case 519:
      return getProperty("VK.leftParenthesis", "Left Parenthesis");
    case 520:
      return getProperty("VK.numberSign", "Number Sign");
    case 521:
      return getProperty("VK.plus", "Plus");
    case 522:
      return getProperty("VK.rightParenthesis", "Right Parenthesis");
    case 523:
      return getProperty("VK.underscore", "Underscore");
    case 24:
      return getProperty("VK.final", "Final");
    case 28:
      return getProperty("VK.convert", "Convert");
    case 29:
      return getProperty("VK.noconvert", "No Convert");
    case 30:
      return getProperty("VK.accept", "Accept");
    case 31:
      return getProperty("VK.modechange", "Mode Change");
    case 21:
      return getProperty("VK.kana", "Kana");
    case 25:
      return getProperty("VK.kanji", "Kanji");
    case 240:
      return getProperty("VK.alphanumeric", "Alphanumeric");
    case 241:
      return getProperty("VK.katakana", "Katakana");
    case 242:
      return getProperty("VK.hiragana", "Hiragana");
    case 243:
      return getProperty("VK.fullWidth", "Full-Width");
    case 244:
      return getProperty("VK.halfWidth", "Half-Width");
    case 245:
      return getProperty("VK.romanCharacters", "Roman Characters");
    case 256:
      return getProperty("VK.allCandidates", "All Candidates");
    case 257:
      return getProperty("VK.previousCandidate", "Previous Candidate");
    case 258:
      return getProperty("VK.codeInput", "Code Input");
    case 259:
      return getProperty("VK.japaneseKatakana", "Japanese Katakana");
    case 260:
      return getProperty("VK.japaneseHiragana", "Japanese Hiragana");
    case 261:
      return getProperty("VK.japaneseRoman", "Japanese Roman");
    case 524:
      return getProperty("VK.mouse0", "MouseLeft");
    case 525:
      return getProperty("VK.mouse1", "MouseRight");
    case 526:
      return getProperty("VK.mouse2", "MouseMiddle");
    case 527:
      return getProperty("VK.mouse3", "MouseFour");
    case 528:
      return getProperty("VK.mouseAxe_X", "MouseAXE_X");
    case 529:
      return getProperty("VK.mouseAxe_Y", "MouseAXE_Y");
    case 530:
      return getProperty("VK.mouseAxe_Z", "MouseAXE_Z");
    case 531:
      return getProperty("VK.joy0", "Joystick0");
    case 532:
      return getProperty("VK.joy1", "Joystick1");
    case 533:
      return getProperty("VK.joy2", "Joystick2");
    case 534:
      return getProperty("VK.joy3", "Joystick3");
    case 535:
      return getProperty("VK.joy4", "Joystick4");
    case 536:
      return getProperty("VK.joy5", "Joystick5");
    case 537:
      return getProperty("VK.joy6", "Joystick6");
    case 538:
      return getProperty("VK.joy7", "Joystick7");
    case 539:
      return getProperty("VK.joy8", "Joystick8");
    case 540:
      return getProperty("VK.joy9", "Joystick9");
    case 541:
      return getProperty("VK.joy10", "Joystick10");
    case 542:
      return getProperty("VK.joy11", "Joystick11");
    case 543:
      return getProperty("VK.joy12", "Joystick12");
    case 544:
      return getProperty("VK.joy13", "Joystick13");
    case 545:
      return getProperty("VK.joy14", "Joystick14");
    case 546:
      return getProperty("VK.joy15", "Joystick15");
    case 547:
      return getProperty("VK.joy16", "Joystick16");
    case 548:
      return getProperty("VK.joy17", "Joystick17");
    case 549:
      return getProperty("VK.joy18", "Joystick18");
    case 550:
      return getProperty("VK.joy19", "Joystick19");
    case 551:
      return getProperty("VK.joy20", "Joystick10");
    case 552:
      return getProperty("VK.joy21", "Joystick21");
    case 553:
      return getProperty("VK.joy22", "Joystick22");
    case 554:
      return getProperty("VK.joy23", "Joystick23");
    case 555:
      return getProperty("VK.joy24", "Joystick24");
    case 556:
      return getProperty("VK.joy25", "Joystick25");
    case 557:
      return getProperty("VK.joy26", "Joystick26");
    case 558:
      return getProperty("VK.joy27", "Joystick27");
    case 559:
      return getProperty("VK.joy28", "Joystick28");
    case 560:
      return getProperty("VK.joy29", "Joystick29");
    case 561:
      return getProperty("VK.joy30", "Joystick30");
    case 562:
      return getProperty("VK.joy31", "Joystick31");
    case 563:
      return getProperty("VK.axe_X", "AXE_X");
    case 564:
      return getProperty("VK.axe_Y", "AXE_Y");
    case 565:
      return getProperty("VK.axe_Z", "AXE_Z");
    case 566:
      return getProperty("VK.axe_RX", "AXE_RX");
    case 567:
      return getProperty("VK.axe_RY", "AXE_RY");
    case 568:
      return getProperty("VK.axe_RZ", "AXE_RZ");
    case 569:
      return getProperty("VK.axe_U", "AXE_U");
    case 570:
      return getProperty("VK.axe_V", "AXE_V");
    case 571:
      return getProperty("VK.pov_1", "Pov-1");
    case 572:
      return getProperty("VK.pov0", "Pov0");
    case 573:
      return getProperty("VK.pov45", "Pov45");
    case 574:
      return getProperty("VK.pov90", "Pov90");
    case 575:
      return getProperty("VK.pov135", "Pov135");
    case 576:
      return getProperty("VK.pov180", "Pov180");
    case 577:
      return getProperty("VK.pov225", "Pov225");
    case 578:
      return getProperty("VK.pov270", "Pov270");
    case 579:
      return getProperty("VK.pov315", "Pov315");
    case 580:
      return getProperty("VK.joyDev0", "JoystickDevice0");
    case 581:
      return getProperty("VK.joyDev1", "JoystickDevice1");
    case 582:
      return getProperty("VK.joyDev2", "JoystickDevice2");
    case 583:
      return getProperty("VK.joyDev3", "JoystickDevice3");
    case 584:
      return getProperty("VK.joyPov0", "JoystickPov0");
    case 585:
      return getProperty("VK.joyPov1", "JoystickPov1");
    case 586:
      return getProperty("VK.joyPov2", "JoystickPov2");
    case 587:
      return getProperty("VK.joyPov3", "JoystickPov3");
    case 588:
      return getProperty("VK.joyPov4", "JoystickPov4");
    case 589:
      return getProperty("VK.joyPov5", "JoystickPov5");
    case 590:
      return getProperty("VK.joyPov6", "JoystickPov6");
    case 591:
      return getProperty("VK.joyPov7", "JoystickPov7");
    case 592:
      return getProperty("VK.joyPov8", "JoystickPov8");
    case 593:
      return getProperty("VK.joyPov9", "JoystickPov9");
    case 594:
      return getProperty("VK.joyPov10", "JoystickPov10");
    case 595:
      return getProperty("VK.joyPov11", "JoystickPov11");
    case 596:
      return getProperty("VK.joyPov12", "JoystickPov12");
    case 597:
      return getProperty("VK.joyPov13", "JoystickPov13");
    case 598:
      return getProperty("VK.joyPov14", "JoystickPov14");
    case 599:
      return getProperty("VK.joyPov15", "JoystickPov15");
    case 600:
      return getProperty("VK.joyPoll", "JoystickPoll");
    case 4:
    case 5:
    case 6:
    case 7:
    case 11:
    case 13:
    case 14:
    case 15:
    case 22:
    case 23:
    case 26:
    case 41:
    case 42:
    case 43:
    case 45:
    case 48:
    case 49:
    case 50:
    case 51:
    case 52:
    case 53:
    case 54:
    case 55:
    case 56:
    case 57:
    case 58:
    case 60:
    case 62:
    case 63:
    case 64:
    case 65:
    case 66:
    case 67:
    case 68:
    case 69:
    case 70:
    case 71:
    case 72:
    case 73:
    case 74:
    case 75:
    case 76:
    case 77:
    case 78:
    case 79:
    case 80:
    case 81:
    case 82:
    case 83:
    case 84:
    case 85:
    case 86:
    case 87:
    case 88:
    case 89:
    case 90:
    case 94:
    case 95:
    case 96:
    case 97:
    case 98:
    case 99:
    case 100:
    case 101:
    case 102:
    case 103:
    case 104:
    case 105:
    case 124:
    case 125:
    case 126:
    case 146:
    case 147:
    case 148:
    case 149:
    case 158:
    case 159:
    case 163:
    case 164:
    case 165:
    case 166:
    case 167:
    case 168:
    case 169:
    case 170:
    case 171:
    case 172:
    case 173:
    case 174:
    case 175:
    case 176:
    case 177:
    case 178:
    case 179:
    case 180:
    case 181:
    case 182:
    case 183:
    case 184:
    case 185:
    case 186:
    case 187:
    case 188:
    case 189:
    case 190:
    case 191:
    case 193:
    case 194:
    case 195:
    case 196:
    case 197:
    case 198:
    case 199:
    case 200:
    case 201:
    case 202:
    case 203:
    case 204:
    case 205:
    case 206:
    case 207:
    case 208:
    case 209:
    case 210:
    case 211:
    case 212:
    case 213:
    case 214:
    case 215:
    case 216:
    case 217:
    case 218:
    case 219:
    case 220:
    case 221:
    case 223:
    case 228:
    case 229:
    case 230:
    case 231:
    case 232:
    case 233:
    case 234:
    case 235:
    case 236:
    case 237:
    case 238:
    case 239:
    case 246:
    case 247:
    case 248:
    case 249:
    case 250:
    case 251:
    case 252:
    case 253:
    case 254:
    case 255:
    case 262:
    case 263:
    case 264:
    case 265:
    case 266:
    case 267:
    case 268:
    case 269:
    case 270:
    case 271:
    case 272:
    case 273:
    case 274:
    case 275:
    case 276:
    case 277:
    case 278:
    case 279:
    case 280:
    case 281:
    case 282:
    case 283:
    case 284:
    case 285:
    case 286:
    case 287:
    case 288:
    case 289:
    case 290:
    case 291:
    case 292:
    case 293:
    case 294:
    case 295:
    case 296:
    case 297:
    case 298:
    case 299:
    case 300:
    case 301:
    case 302:
    case 303:
    case 304:
    case 305:
    case 306:
    case 307:
    case 308:
    case 309:
    case 310:
    case 311:
    case 312:
    case 313:
    case 314:
    case 315:
    case 316:
    case 317:
    case 318:
    case 319:
    case 320:
    case 321:
    case 322:
    case 323:
    case 324:
    case 325:
    case 326:
    case 327:
    case 328:
    case 329:
    case 330:
    case 331:
    case 332:
    case 333:
    case 334:
    case 335:
    case 336:
    case 337:
    case 338:
    case 339:
    case 340:
    case 341:
    case 342:
    case 343:
    case 344:
    case 345:
    case 346:
    case 347:
    case 348:
    case 349:
    case 350:
    case 351:
    case 352:
    case 353:
    case 354:
    case 355:
    case 356:
    case 357:
    case 358:
    case 359:
    case 360:
    case 361:
    case 362:
    case 363:
    case 364:
    case 365:
    case 366:
    case 367:
    case 368:
    case 369:
    case 370:
    case 371:
    case 372:
    case 373:
    case 374:
    case 375:
    case 376:
    case 377:
    case 378:
    case 379:
    case 380:
    case 381:
    case 382:
    case 383:
    case 384:
    case 385:
    case 386:
    case 387:
    case 388:
    case 389:
    case 390:
    case 391:
    case 392:
    case 393:
    case 394:
    case 395:
    case 396:
    case 397:
    case 398:
    case 399:
    case 400:
    case 401:
    case 402:
    case 403:
    case 404:
    case 405:
    case 406:
    case 407:
    case 408:
    case 409:
    case 410:
    case 411:
    case 412:
    case 413:
    case 414:
    case 415:
    case 416:
    case 417:
    case 418:
    case 419:
    case 420:
    case 421:
    case 422:
    case 423:
    case 424:
    case 425:
    case 426:
    case 427:
    case 428:
    case 429:
    case 430:
    case 431:
    case 432:
    case 433:
    case 434:
    case 435:
    case 436:
    case 437:
    case 438:
    case 439:
    case 440:
    case 441:
    case 442:
    case 443:
    case 444:
    case 445:
    case 446:
    case 447:
    case 448:
    case 449:
    case 450:
    case 451:
    case 452:
    case 453:
    case 454:
    case 455:
    case 456:
    case 457:
    case 458:
    case 459:
    case 460:
    case 461:
    case 462:
    case 463:
    case 464:
    case 465:
    case 466:
    case 467:
    case 468:
    case 469:
    case 470:
    case 471:
    case 472:
    case 473:
    case 474:
    case 475:
    case 476:
    case 477:
    case 478:
    case 479:
    case 480:
    case 481:
    case 482:
    case 483:
    case 484:
    case 485:
    case 486:
    case 487:
    case 488:
    case 489:
    case 490:
    case 491:
    case 492:
    case 493:
    case 494:
    case 495:
    case 496:
    case 497:
    case 498:
    case 499:
    case 500:
    case 501:
    case 502:
    case 503:
    case 504:
    case 505:
    case 506:
    case 507:
    case 508:
    case 509:
    case 510:
    case 511: } if ((paramInt >= 96) && (paramInt <= 105)) {
      String str = getProperty("VK.numpad", "NumPad");
      char c = (char)(paramInt - 96 + 48);
      return str + "-" + c;
    }

    return "";
  }

  public static final int getKeyFromText(String paramString)
  {
    Integer localInteger = (Integer)hashNames.get(paramString);
    if (localInteger == null) return 0;
    return localInteger.intValue();
  }

  public static String getProperty(String paramString1, String paramString2)
  {
    if (resources != null) try {
        return resources.getString(paramString1);
      }
      catch (MissingResourceException localMissingResourceException) {
      } return paramString2;
  }

  static
  {
    try {
      resources = ResourceBundle.getBundle("com.maddox.rts.VK", Locale.getDefault(), LDRres.loader());
    }
    catch (MissingResourceException localMissingResourceException) {
    }
    hashNames = new HashMap(621, 0.99F);
    for (int i = 0; i <= 601; i++) {
      String str = getKeyText(i);
      if (str.length() > 0)
        hashNames.put(str, new Integer(i));
    }
  }
}