//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "extern_globals.h"
#include "trace.h"

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )




//************************************
// Method:    SASES_DialogProc
// FullName:  SASES_DialogProc
// Access:    public 
// Returns:   INT_PTR CALLBACK
// Qualifier:
// Parameter: HWND hwndDlg
// Parameter: UINT uMsg
// Parameter: WPARAM wParam
// Parameter: LPARAM lParam
//************************************
INT_PTR CALLBACK SASES_DialogProc(
								  HWND hwndDlg,
								  UINT uMsg,
								  WPARAM wParam,
								  LPARAM lParam)
{
	switch(uMsg)
	{
		HANDLE_MSG(hwndDlg, WM_INITDIALOG, SASES_OnInitDialog);
		HANDLE_MSG(hwndDlg, WM_COMMAND, SASES_OnCommand);
		HANDLE_MSG(hwndDlg, WM_HSCROLL, SASES_OnHScroll);
		HANDLE_MSG(hwndDlg, WM_TIMER, SASES_OnTimer);
		HANDLE_MSG(hwndDlg, WM_CTLCOLOREDIT, SASES_OnCtlColorEdit);

	case WM_CREATE:
		InitCommonControls();
		INITCOMMONCONTROLSEX icex;
		icex.dwSize = sizeof (INITCOMMONCONTROLSEX);
		icex.dwICC   = ICC_ANIMATE_CLASS|ICC_COOL_CLASSES|ICC_BAR_CLASSES|ICC_LISTVIEW_CLASSES|ICC_PROGRESS_CLASS|ICC_STANDARD_CLASSES|ICC_TAB_CLASSES|ICC_USEREX_CLASSES;
		InitCommonControlsEx(&icex);
		break;
	case WM_IL2_STARTED:
		SASES_OnIl2Started();
		break;
	case WM_IL2_STOPPED:
		SASES_OnIl2Stopped();
		break;
	case WM_IL2_LOADED:
		SASES_OnIl2Loaded();
		break;
	case WM_IL2_START_ERROR:
		SASES_OnIl2StartError();
		break;
	case WM_IL2_INSUFFICIENT_RAM:
		SASES_OnIl2InsufficientRam((int)lParam);
		break;
	case WM_DESTROY:
		KillTimer(hwndDlg, TIMER_COUNTDOWN);
		break;
	default:
		break;
	}

	return FALSE;
}

//************************************
// Method:    SASES_OnInitDialog
// FullName:  SASES_OnInitDialog
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: HWND hwnd
// Parameter: HWND
// Parameter: LPARAM
//************************************
BOOL SASES_OnInitDialog(HWND hwnd, HWND /*hwndFocus*/, LPARAM /*lParam*/)
{
	g_hWnd = hwnd;

	SendMessage(hwnd, WM_SETICON, ICON_SMALL, (LPARAM) g_hIconSmall ); 
	SendMessage(hwnd, WM_SETICON, ICON_BIG, (LPARAM) g_hIconLarge ); 

	SendMessage(GetDlgItem(hwnd, IDC_EDIT_START_DELAY), EM_LIMITTEXT, 2, 0);

	FillDropdown();
	SettingsToControls();

	//SetWindowLong(hwnd, GWL_EXSTYLE, GetWindowLong(hwnd, GWL_EXSTYLE) | WS_EX_LAYERED);
	//SetLayeredWindowAttributes(hwnd, 0, 0xE0, LWA_ALPHA);

	if (g_iOperationMode == OPERATION_MODE_START) {
		if (g_bStartImmediately) {
			PostMessage(hwnd, WM_COMMAND, IDC_BUTTON_START_NOW, 0);
		} else {
			if (g_bAutoStartIl2) {
				ShowWindow(GetDlgItem(hwnd, IDC_STATIC_START_COUNTDOWN), SW_SHOW);
				ShowTimerLeft(hwnd, g_iDelayLeft);
				SetTimer(hwnd, TIMER_COUNTDOWN, 1000, NULL);
			} else {
				ShowWindow(GetDlgItem(hwnd, IDC_STATIC_START_COUNTDOWN), SW_HIDE);
			}
			ShowCurrentSettings();
		}
	}
	BringToFront(hwnd);
	return FALSE;
}

void ShowCurrentSettings() {
	TCHAR szBuf[MAX_PATH];
	SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, WM_SETFONT, (WPARAM)g_hListBoxFont, TRUE);
	SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Current Game Settings:");
	SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"");
	switch (g_iModType) {
		case 0:
			SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Stock Game");
			break;
		case 1:
		default:
			SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Classic Modded Game");
			break;
		case 2:
			SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"SAS Modact 3 or later");
			break;
		case 3:
			SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"UltraPack 3 or later");
			break;
	}

	_stprintf(szBuf, L"Maximum RAM size: %d MB", g_iRamSize);
	SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)szBuf);
	if (g_bExitWithIl2) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"This Selector Exits with IL-2");
	}
	if (g_bTIREnabled) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"6DoF / TrackIR enabled");
	}
	if (g_b4GBAddressSpaceEnabled) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"4 GB Address Space enabled");
	}
	if (g_bEnableModFilesCache) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Mod Files Cache enabled");
	}
	if (g_bMultipleInstancesEnabled) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Multiple IL-2 Instances enabled");
	}
	if (_tcslen(g_lpCmdLine) > 0) {
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_SETHORIZONTALEXTENT, (WPARAM)(_tcslen(g_lpCmdLine) * 10), (LPARAM)0);
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"");
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)L"Additional Parameters:");
		SendDlgItemMessage(g_hWnd, IDC_LIST_SETTINGS, LB_ADDSTRING, (WPARAM)0, (LPARAM)g_lpCmdLine);
	}
}

//************************************
// Method:    SASES_OnCommand
// FullName:  SASES_OnCommand
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: HWND hwnd
// Parameter: int id
// Parameter: HWND hwndCtl
// Parameter: UINT codeNotify
//************************************
void SASES_OnCommand(HWND hwnd, int id, HWND hwndCtl, UINT codeNotify)
{
	switch(id)
	{
	case IDOK:
		LaunchIl2();
		break;
	case IDCANCEL:
		EndDialog(hwnd, IDCANCEL);
		break;
	case IDC_BUTTON_START_NOW:
		LaunchIl2();
		break;
	case IDC_BUTTON_CHANGE_CONFIG:
		EndDialog(hwnd, IDC_BUTTON_CHANGE_CONFIG);
		break;
	case IDC_BUTTON_SAVE_SETTINGS:
		ControlsToSettings();
		WriteIniSettings();
		EndDialog(hwnd, IDC_BUTTON_SAVE_SETTINGS);
		break;
	case IDC_BUTTON_CANCEL_SETTINGS:
		EndDialog(hwnd, IDC_BUTTON_CANCEL_SETTINGS);
		break;
	case IDC_CHECK_AUTOSTART:
		g_bAutoStartIl2 = IsDlgButtonChecked(g_hWnd, IDC_CHECK_AUTOSTART);
		SettingsToControls();
		break;
	case IDC_CHECK_START_IMMEDIATELY:
		g_bStartImmediately = IsDlgButtonChecked(g_hWnd, IDC_CHECK_START_IMMEDIATELY);
		SettingsToControls();
		break;
	case IDC_CHECK_EXIT_WITH_IL2:
		g_bExitWithIl2 = IsDlgButtonChecked(g_hWnd, IDC_CHECK_EXIT_WITH_IL2);
		SettingsToControls();
		break;
	case IDC_CHECK_TIR:
		g_bTIREnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_TIR);
		SettingsToControls();
	case IDC_CHECK_EXPERT:
		g_bExpertModeEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_EXPERT);
		SettingsToControls();
		//EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_LAA), g_bExpertModeEnabled);
		//EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_MULTI), g_bExpertModeEnabled);
		//CheckDlgButton(g_hWnd, IDC_CHECK_LAA, ( g_b4GBAddressSpaceEnabled && g_bExpertModeEnabled ) ? BST_CHECKED : BST_UNCHECKED);
		//CheckDlgButton(g_hWnd, IDC_CHECK_MULTI, ( g_bMultipleInstancesEnabled && g_bExpertModeEnabled )? BST_CHECKED : BST_UNCHECKED);
		//g_b4GBAddressSpaceEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_LAA);
		//SetRamSliderTicks();
		//g_bMultipleInstancesEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_MULTI);
		break;
	case IDC_CHECK_LAA:
		g_b4GBAddressSpaceEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_LAA);
		SettingsToControls();
		break;
	case IDC_CHECK_CACHED_WRAPPER:
		g_bEnableModFilesCache = IsDlgButtonChecked(g_hWnd, IDC_CHECK_CACHED_WRAPPER);
		SettingsToControls();
		break;
	case IDC_CHECK_MULTI:
		g_bMultipleInstancesEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_MULTI);
		SettingsToControls();
		break;
	case IDC_COMBO_MODTYPES:
		{
			switch (codeNotify) {
				case CBN_SELCHANGE:
					g_iModType = SendMessage(GetDlgItem(g_hWnd, IDC_COMBO_MODTYPES), CB_GETCURSEL, 0, 0);
					SettingsToControls();
					break;
			}
			break;
		}
	case IDC_EDIT_RAM:
		{
			switch (codeNotify) {
				case EN_UPDATE:
					{
						if (GetFocus() == GetDlgItem(hwnd, IDC_EDIT_RAM)) { 
							int iValue = GetDlgItemInt(g_hWnd, IDC_EDIT_RAM, FALSE, FALSE);
							if ((g_bExpertModeEnabled && (iValue >= 64) && (iValue <= 2048)) || ((iValue >= 128) && (iValue <= 1024))) {
								g_iRamSize = iValue;
								SettingsToControls();
							}
							CheckRamUsage();
						}
					}
					break;
			}
			break;
		}
	default:
		break;
	}
}

//************************************
// Method:    SASES_OnTimer
// FullName:  SASES_OnTimer
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: HWND hwnd
// Parameter: UINT_PTR nIDEvent
//************************************
void SASES_OnTimer(HWND hwnd, UINT_PTR nIDEvent) {
	switch (nIDEvent) {
		case TIMER_COUNTDOWN:
			{
				g_iDelayLeft--;
				if (g_iDelayLeft < 1) {
					KillTimer(hwnd, TIMER_COUNTDOWN);
					LaunchIl2();
					break;
				}
				ShowTimerLeft(hwnd, g_iDelayLeft);
			}
			break;
		case TIMER_REENABLE_START:
			{
				KillTimer(hwnd, TIMER_REENABLE_START);
				if (g_bMultipleInstancesEnabled)
					EnableWindow(GetDlgItem(g_hWnd, IDC_BUTTON_START_NOW), TRUE);
			}
			break;
		default:
			break;
	}
}
//************************************
// Method:    SASES_OnHScroll
// FullName:  SASES_OnHScroll
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: HWND hwnd
// Parameter: HWND hwndCtl
// Parameter: UINT code
// Parameter: int pos
//************************************
void SASES_OnHScroll(HWND hwnd, HWND hwndCtl, UINT code, int pos)
{
	if (hwndCtl == GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE)) {
		switch LOWORD(code) 
		{
		case TB_PAGEUP:
		case TB_PAGEDOWN:
			pos = SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_GETPOS, 0, 0);
		case TB_LINEUP:
		case TB_LINEDOWN:
		case TB_THUMBPOSITION:
		case TB_THUMBTRACK:
		case TB_TOP:
		case TB_BOTTOM:
			//case TB_ENDTRACK:
			{
				//TRACE(L"code %d, pos %d\r\n", code, pos);

				g_iRamSize = stepRamSize(pos);
				SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETPOS, (WPARAM) TRUE, (LPARAM) g_iRamSize);
				SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETSEL, (WPARAM) TRUE, (LPARAM) MAKELONG(0, g_iRamSize));
				if (GetFocus() == GetDlgItem(hwnd, IDC_EDIT_RAM)) break;
				SetDlgItemInt(g_hWnd, IDC_EDIT_RAM, g_iRamSize, FALSE);
			}
			break;
		case TB_ENDTRACK:
			CheckRamUsage();
			break;

		default:
			TRACE(L"code %d, pos %d\r\n", code, pos);
			break;
		}
	}
}

//************************************
// Method:    SASES_OnCtlColorEdit
// FullName:  SASES_OnCtlColorEdit
// Access:    public 
// Returns:   HBRUSH
// Qualifier:
// Parameter: HWND hwnd
// Parameter: HDC hdc
// Parameter: HWND hwndChild
// Parameter: int type
//************************************
HBRUSH SASES_OnCtlColorEdit(HWND hwnd, HDC hdc, HWND hwndChild, int type) {
	if (hwndChild != GetDlgItem(g_hWnd, IDC_EDIT_RAM)) return NULL;
	switch(type)
	{
		case CTLCOLOR_EDIT:
			{
				SetTextColor(hdc,RGB(0,0,0));
				if (g_iRamSize <= 1024) {
					SetBkColor(hdc,RGB(0,255,0));
					return g_hBrushGreen;
				}
				if (g_iRamSize <= 1200) {
					SetBkColor(hdc,RGB(255,255,0));
					return g_hBrushYellow;
				}
				MEMORYSTATUSEX status;
				status.dwLength = sizeof(status);
				GlobalMemoryStatusEx(&status);
				int iMegsFree = (int)(status.ullAvailVirtual / 0x100000);
				if (g_iRamSize < iMegsFree) {
					SetBkColor(hdc,RGB(255,140,0));
					return g_hBrushOrange;
				}
				SetBkColor(hdc,RGB(255,0,0));
				return g_hBrushRed;
			}
		default:
			break;
	}
	return NULL;
}


//************************************
// Method:    SASES_OnIl2Started
// FullName:  SASES_OnIl2Started
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SASES_OnIl2Started() {
	g_iNumIl2InstancesRunning++;
	if (g_bExitWithIl2 || !g_bMultipleInstancesEnabled) ShowWindow(g_hWnd, SW_HIDE);
}

//************************************
// Method:    SASES_OnIl2Stopped
// FullName:  SASES_OnIl2Stopped
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SASES_OnIl2Stopped() {
	if (g_iNumIl2InstancesRunning > 0) g_iNumIl2InstancesRunning--;
	AfterIl2Stopped();
}

//************************************
// Method:    SASES_OnIl2Loaded
// FullName:  SASES_OnIl2Loaded
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SASES_OnIl2Loaded() {
	//PostMessage(g_hWnd, WM_IL2_STARTED, 0, 0);
	//DeleteFile(szIl2CreatedFilePath);
}
//************************************
// Method:    SASES_OnIl2StartError
// FullName:  SASES_OnIl2StartError
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SASES_OnIl2StartError() {
	AfterIl2Stopped();
}

//************************************
// Method:    SASES_OnIl2InsufficientRam
// FullName:  SASES_OnIl2InsufficientRam
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: int iMessageType
//************************************
void SASES_OnIl2InsufficientRam(int iMessageType) {
	switch (iMessageType) {
		case RAM_MESSAGE_GOOD:
			{
				if (!g_bRamToolTipVisible) break;
				Edit_HideBalloonTip(GetDlgItem(g_hWnd, IDC_EDIT_RAM));
				g_bRamToolTipVisible = FALSE;
			}
			break;
		case RAM_MESSAGE_CRITICAL:
			{
				if (g_bRamToolTipVisible) {
					if (g_iMessageType == RAM_MESSAGE_CRITICAL) break;
					Edit_HideBalloonTip(GetDlgItem(g_hWnd, IDC_EDIT_RAM));
					Sleep(0);
				}
				EDITBALLOONTIP editballoontip;
				editballoontip.cbStruct = sizeof(EDITBALLOONTIP);
				editballoontip.ttiIcon = TTI_WARNING;
				editballoontip.pszTitle = L"! WARNING !";
				editballoontip.pszText = L"Your RAM usage selection exceeds\r\n"
					L"critical limits. Please be prepared that\r\n"
					L"IL-2 might not launch successfully with\r\n"
					L"this setting.";
				SendMessage(GetDlgItem(g_hWnd, IDC_EDIT_RAM), EM_SHOWBALLOONTIP, 0, (LPARAM)&editballoontip);
				g_bRamToolTipVisible = TRUE;
			}
			break;
		case RAM_MESSAGE_ERROR:
			{
				if (g_bRamToolTipVisible) {
					if (g_iMessageType == RAM_MESSAGE_ERROR) break;
					Edit_HideBalloonTip(GetDlgItem(g_hWnd, IDC_EDIT_RAM));
					Sleep(0);
				}
				EDITBALLOONTIP editballoontip;
				editballoontip.cbStruct = sizeof(EDITBALLOONTIP);
				editballoontip.ttiIcon = TTI_WARNING;
				editballoontip.pszTitle = L"*** !!! ERROR !!! ***";
				editballoontip.pszText = L"Your RAM usage selection exceeds\r\n"
					L"the currently available virtual memory\r\n"
					L"on your system. Please be prepared that\r\n"
					L"IL-2 will not launch successfully with\r\n"
					L"this setting.";
				SendMessage(GetDlgItem(g_hWnd, IDC_EDIT_RAM), EM_SHOWBALLOONTIP, 0, (LPARAM)&editballoontip);
				g_bRamToolTipVisible = TRUE;
			}
			break;
		default:
			break;
	}
	g_iMessageType = iMessageType;
}


