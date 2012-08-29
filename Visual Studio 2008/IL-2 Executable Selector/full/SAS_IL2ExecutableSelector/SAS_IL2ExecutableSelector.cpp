//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "SAS_IL2ExecutableSelector.h"
#include "trace.h"

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    _tWinMain
// FullName:  _tWinMain
// Access:    public 
// Returns:   int WINAPI
// Qualifier:
// Parameter: HINSTANCE hInstance
// Parameter: HINSTANCE hPrevInstance
// Parameter: LPWSTR lpCmdLine
// Parameter: int nShowCmd
//************************************
int WINAPI _tWinMain(
					 HINSTANCE hInstance,
					 HINSTANCE hPrevInstance,
					 LPWSTR lpCmdLine,
					 int nShowCmd)
{
	// Make Sure IL-2 Executable Selector is only running with one single instance.
	HANDLE hMutex = CreateMutex(NULL, TRUE, SELECTOR_MUTEX);
	if ( (hMutex == NULL) || (GetLastError() == ERROR_ALREADY_EXISTS) )
	{
		if (hMutex != NULL)
		{
			ReleaseMutex(hMutex);
			CloseHandle(hMutex);
		}
		// If the selector is already running, show it's window and bring it to front.
		HWND hWndSelector = FindWindow(NULL, L"SAS IL-2 Executable Selector");
		if (hWndSelector != NULL) {
			ShowWindow(hWndSelector, SW_SHOW);
			SetForegroundWindow(hWndSelector);
			SetActiveWindow(hWndSelector);
		}
		return 0;
	}

	GetFilesAndPaths();
	DeleteTempIl2Files();
	if (!IsFolderWriteable()) {
		MessageBox(NULL,
			L"Unsufficient file access permissions in your IL-2 game folder!\r\n"
			L"\r\n"
			L"Most probably you've installed IL-2 in the \"C:\\Program Files\\\" folder\r\n"
			L"on a Windows Vista or Windows 7 system.\r\n"
			L"In this case please copy your whole IL-2 game folder to a different\r\n"
			L"location, e.g. \"C:\\IL2\\\", and launch your game there.\r\n"
			L"\r\n"
			L"In any other case, please query SAS or UP staff for assistance.\r\n"
			L"We apologize for any inconvenience.",
			L"IL-2 Executable Selector by SAS & UltraPack",
			MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
	}
	DeleteTempIl2Files();
	ReadIniSettings();
	g_lpCmdLine = lpCmdLine;
	g_hIconSmall = (HICON)LoadImage(hInstance, MAKEINTRESOURCE(IDI_ICON1), IMAGE_ICON, 16, 16, LR_DEFAULTSIZE); 
	g_hIconLarge = (HICON)LoadImage(hInstance, MAKEINTRESOURCE(IDI_ICON1), IMAGE_ICON, 32, 32, LR_DEFAULTSIZE); 
	g_hBrushYellow = CreateSolidBrush(RGB(255,255,0));
	g_hBrushRed = CreateSolidBrush(RGB(255,0,0));
	g_hBrushGreen = CreateSolidBrush(RGB(0,255,0));
	g_hBrushOrange = CreateSolidBrush(RGB(255,140,0));

	LOGFONT LogFont;
	LogFont.lfHeight=16;
	LogFont.lfWidth=9;
	LogFont.lfEscapement=0; // Making Font
	LogFont.lfOrientation=0;
	LogFont.lfWeight=200;
	LogFont.lfItalic=0;
	LogFont.lfUnderline=0;
	LogFont.lfStrikeOut=0;
	LogFont.lfCharSet=1;
	LogFont.lfOutPrecision=OUT_TT_PRECIS;
	LogFont.lfClipPrecision=CLIP_DEFAULT_PRECIS;
	LogFont.lfQuality=CLEARTYPE_QUALITY;
	LogFont.lfPitchAndFamily=FF_SWISS; // font type/family
	g_hListBoxFont = CreateFontIndirect(&LogFont);

	g_iOperationMode = OPERATION_MODE_START;
	if (IsKeyDown(VK_SHIFT) || IsKeyDown(VK_CONTROL) || IsKeyDown(VK_MENU)) {
		g_iOperationMode = OPERATION_MODE_SETTINGS;
	}

	BOOL bContinue = TRUE;
	LPCTSTR lpTemplate;
	while (bContinue == TRUE) {
		switch (g_iOperationMode) {
			case OPERATION_MODE_START:
				lpTemplate = MAKEINTRESOURCE(IDD_IL2EXESELECTOR_START);
				break;
			case OPERATION_MODE_SETTINGS:
			default:
				lpTemplate = MAKEINTRESOURCE(IDD_IL2EXESELECTOR);
				break;
		}
		int iRet = DialogBox(
			hInstance,
			lpTemplate,
			GetDesktopWindow(),
			SASES_DialogProc);
		switch (iRet) {
			case IDC_BUTTON_CHANGE_CONFIG: // Change Config
				bContinue = TRUE;
				g_iOperationMode = OPERATION_MODE_SETTINGS;
				break;
			case IDC_BUTTON_SAVE_SETTINGS: // Change Config
			case IDC_BUTTON_CANCEL_SETTINGS: // Change Config
				bContinue = TRUE;
				g_iDelayLeft = g_iStartDelay;
				g_iOperationMode = OPERATION_MODE_START;
				break;
			case IDCANCEL: // Cancel
				switch (g_iOperationMode) {
					case OPERATION_MODE_START:
						bContinue = FALSE;
						break;
					case OPERATION_MODE_SETTINGS:
					default:
						bContinue = TRUE;
						g_iDelayLeft = g_iStartDelay;
						g_iOperationMode = OPERATION_MODE_START;
						break;
				}
				break;
			default:
				bContinue = FALSE;
				break;
		}
	}
	DeleteTempIl2Files();

	if (GetFileAttributes(szAppSparePath) != INVALID_FILE_ATTRIBUTES) {
		SecureMoveFile(szAppSparePath, szAppPath);
	}

	DeleteBrush(g_hBrushYellow);
	DeleteBrush(g_hBrushRed);
	DeleteBrush(g_hBrushGreen);
	DeleteBrush(g_hBrushOrange);

	ReleaseMutex(hMutex);
	CloseHandle(hMutex);
	return 0;
}
