// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   VoiceCMD.java

package com.maddox.il2.objects.sounds;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.sounds:
//            Voice

public class VoiceCMD
{

    public VoiceCMD()
    {
    }

    public static void speak(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(i < 357 || i > 412)
            return;
        i -= 357;
        int j = (int)(com.maddox.rts.Time.current() / 60000L);
        if(j < SpeakCMDTime[i])
        {
            return;
        } else
        {
            SpeakCMDTime[i] = j + 1;
            com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, SpeakCMD[i], 2);
            return;
        }
    }

    public static void speak(com.maddox.il2.objects.air.Aircraft aircraft, int i, int j)
    {
        int k = i + rnd.nextInt(j);
        com.maddox.il2.objects.sounds.VoiceCMD.speak(aircraft, k);
    }

    public static final int BEGIN = 357;
    public static final int Bombs_away = 357;
    public static final int Firing_rockets = 358;
    public static final int Attacking_with_rockets = 359;
    public static final int Rockets_gone = 360;
    public static final int Target_hit__secondaries_observed = 361;
    public static final int Target_destroyed = 362;
    public static final int Direct_hit__Target_destroyed = 363;
    public static final int Roger__disengaging_now = 364;
    public static final int Roger__breaking_off = 365;
    public static final int Copy__disengaging = 366;
    public static final int Roger__rejoining_formation = 367;
    public static final int Roger__regrouping = 368;
    public static final int Copy__rejoining = 369;
    public static final int Roger__breaking_right = 370;
    public static final int Roger__breaking_left = 371;
    public static final int Roger__breaking_high = 372;
    public static final int Roger__breaking_low = 373;
    public static final int Roger__going_to_rockets = 374;
    public static final int Roger__rockets_armed = 375;
    public static final int Copy__arm_rockets = 376;
    public static final int Roger__bombs_ready = 377;
    public static final int Roger__bombs_armed = 378;
    public static final int Copy__arm_bombs = 379;
    public static final int Roger__closing_formation = 380;
    public static final int Roger__closing_it_in = 381;
    public static final int Copy__tight_formation = 382;
    public static final int Roger__opening_formation = 383;
    public static final int Roger__kicking_out = 384;
    public static final int Copy__loose_formation = 385;
    public static final int Roger__echelon_right_formation = 386;
    public static final int Roger__echelon_left_formation = 387;
    public static final int Roger__line_abreast_formation = 388;
    public static final int Roger__line_astern_formation = 389;
    public static final int Roger__Vic_formation = 390;
    public static final int Roger__next_check_point = 391;
    public static final int Roger__proceeding_to_next_check_point = 392;
    public static final int Copy__next_check_point = 393;
    public static final int Roger__previous_check_point = 394;
    public static final int Roger__returning_to_previous_check_point = 395;
    public static final int Copy__previous_check_point = 396;
    public static final int Roger__we_are_loitering = 397;
    public static final int Roger__holding_here = 398;
    public static final int Copy__loitering_here = 399;
    public static final int Roger__we_are_returning_to_base = 400;
    public static final int Roger__heading_home = 401;
    public static final int Copy__returning_to_base = 402;
    public static final int Roger = 403;
    public static final int Roger_that = 404;
    public static final int Copy = 405;
    public static final int Black_tulpan_in_your_area = 406;
    public static final int Kraya_1_in_your_area = 407;
    public static final int Attack = 408;
    public static final int by_guns = 409;
    public static final int by_rockets = 410;
    public static final int by_boms = 411;
    public static final int by_all_weapons = 412;
    public static final int END = 412;
    private static int SpeakCMD[] = {
        85, 108, 79, 141, 1, 153, 93, 302, 299, 266, 
        318, 317, 274, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 300, 301, 279, 315, 311, 270, 304, 
        303, 312, 313, 1, 314, 1, 271, 316, 319, 272, 
        1, 308, 269, 325, 305, 276, 298, 1, 1, 1, 
        1, 1, 1, 1, 1, 1
    };
    private static final int CSIZE = 55;
    private static int SpeakCMDTime[] = new int[55];
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();

}
