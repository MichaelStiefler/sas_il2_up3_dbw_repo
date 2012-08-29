//*************************************************************************
// Includes
//*************************************************************************
#include "stdafx.h"
#include "common.h"
#include "extern_globals.h"
#include "resource.h"

//*************************************************************************
// Suppress new style warning messages
//*************************************************************************
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    StartIl2Server
// FullName:  StartIl2Server
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void StartIl2Server()
{
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	ZeroMemory( &pi, sizeof(pi) );
	TCHAR szCommandLine[0x8000];
	_tprintf(L"\r\n");
	_tprintf(L"Server Type:           %s\r\n", g_szModTypes[g_iModType]);
	_tprintf(L"RAM usage setting:     %dMB\r\n", g_iRamSize);
	if (g_b4GBAddressSpaceEnabled)
		_tprintf(L"Large Address setting: true\r\n");
	else
		_tprintf(L"Large Address setting: false\r\n");
	if ((_tcslen(g_szCmdLine) + _tcslen(g_szModTypeExeParms[g_iModType])) > 0)
		_tprintf(L"Command line params:  %s%s\r\n", g_szCmdLine, g_szModTypeExeParms[g_iModType]);
	_stprintf(szCommandLine, L"%s%s%s", szIl2ServerExeFilePath, g_szCmdLine, g_szModTypeExeParms[g_iModType]);
	_tprintf(L"\r\n");
	if (CreateProcess(NULL, szCommandLine, NULL,
		NULL, FALSE, 0, NULL, NULL, &si, &pi))
	{
		_tprintf(L"\r\n");
		_tprintf(L"IL-2 Server starting...\r\n");
		_tprintf(L"\r\n");
		CloseHandle(pi.hThread);
		WaitForSingleObject(pi.hProcess, INFINITE);
		CloseHandle(pi.hProcess);
	} else {
		_tprintf(L"\r\n");
		_tprintf(L"IL-2 Server launch failed!\r\n");
		_tprintf(L"\r\n");
		ShowLastError();
	}
}

//************************************
// Method:    GetFilesAndPaths
// FullName:  GetFilesAndPaths
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void GetFilesAndPaths()
{
	memset(szPathName, 0, sizeof(szPathName));
	GetModuleFileName(NULL,szAppPath,MAX_PATH);
	_tcscpy(szPathName, szAppPath);
	LPTSTR pszFileName = NULL;
	pszFileName = _tcsrchr(szPathName, '\\') + 1;
	*pszFileName = '\0';

	_tcscpy(szIniFile, szPathName);
	_tcscat(szIniFile, L"il2server.ini");
	_tcscpy(szIl2ServerExeFilePath, szPathName);
	_tcscat(szIl2ServerExeFilePath, EXE_NAME);
	_tcscpy(szWrapperFilePath, szPathName);
	_tcscat(szWrapperFilePath, DLL_NAME);
}

//************************************
// Method:    ShowLastError
// FullName:  ShowLastError
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void ShowLastError()
{
	LPVOID lpMsgBuf;
	FormatMessage( 
		FORMAT_MESSAGE_ALLOCATE_BUFFER | 
		FORMAT_MESSAGE_FROM_SYSTEM | 
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		GetLastError(),
		0, // Default language
		(LPTSTR) &lpMsgBuf,
		0,
		NULL 
		);
	// Process any inserts in lpMsgBuf.
	// ...
	// Display the string.
	_tprintf(L"***************** ERROR **********************\r\n");
	_tprintf((LPCTSTR)lpMsgBuf);
	_tprintf(L"\r\n");
	_tprintf(L"**********************************************\r\n");
	// Free the buffer.
	LocalFree( lpMsgBuf );
}


//************************************
// Method:    LoadFileInResource
// FullName:  LoadFileInResource
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: int name
// Parameter: int type
// Parameter: DWORD & size
// Parameter: const char * & data
//************************************
void LoadFileInResource(int name, int type, DWORD& size, const char*& data)
{
	HMODULE handle = GetModuleHandle(NULL);
	HRSRC rc = FindResource(handle, MAKEINTRESOURCE(name), MAKEINTRESOURCE(type));
	HGLOBAL rcData = LoadResource(handle, rc);
	size = SizeofResource(handle, rc);
	data = static_cast<const char*>(LockResource(rcData));
}

//************************************
// Method:    CreateIl2FbExe
// FullName:  CreateIl2FbExe
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void CreateIl2FbExe()
{
	DWORD size = 0;
	const char* data = NULL;
	if (g_iModType == 0) {
		LoadFileInResource(IDR_IL2SERVER_STOCK, BASE_FILES, size, data);
	} else {
		LoadFileInResource(IDR_IL2SERVER, BASE_FILES, size, data);
	}
	DeleteTempIl2Files();
	FILE *outfile = _wfopen (szIl2ServerExeFilePath, L"wbT");
	fwrite(data, sizeof(char), size, outfile);
	char strMemSize[16];
	memset(strMemSize, 0, 6);
	_itoa_s(g_iRamSize, strMemSize, 16, 10);
	strcat(strMemSize, "M");
	_fseek_nolock(outfile, EXE_OFFSET_XMX, SEEK_SET);
	fwrite(strMemSize, sizeof(char), strlen(strMemSize), outfile);

	if (g_b4GBAddressSpaceEnabled) {
		BYTE dataLAA[1] = EXE_DATA_LAA;
		_fseek_nolock(outfile, EXE_OFFSET_LAA, SEEK_SET);
		fwrite(dataLAA, sizeof(BYTE), 1, outfile);
	}

	fclose(outfile);
	SecureSetFileAttributes(szIl2ServerExeFilePath,
		FILE_ATTRIBUTE_HIDDEN
		| FILE_ATTRIBUTE_SYSTEM
		| FILE_ATTRIBUTE_NOT_CONTENT_INDEXED
		| FILE_ATTRIBUTE_TEMPORARY
		| FILE_ATTRIBUTE_READONLY);

	if (g_iModType != 0) {
		LoadFileInResource(IDR_WRAPPER, BASE_FILES, size, data);
		outfile = _wfopen (szWrapperFilePath, L"wbT");
		fwrite(data, sizeof(char), size, outfile);
		fclose(outfile);
		SecureSetFileAttributes(szWrapperFilePath,
			FILE_ATTRIBUTE_HIDDEN
			| FILE_ATTRIBUTE_SYSTEM
			| FILE_ATTRIBUTE_NOT_CONTENT_INDEXED
			| FILE_ATTRIBUTE_TEMPORARY
			| FILE_ATTRIBUTE_READONLY);
	}
}

//************************************
// Method:    DeleteTempIl2Files
// FullName:  DeleteTempIl2Files
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void DeleteTempIl2Files() {
	SecureDeleteFile(szIl2ServerExeFilePath);
	SecureDeleteFile(szWrapperFilePath);
}

//************************************
// Method:    SetFileAttributesWithRetry
// FullName:  SetFileAttributesWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
// Parameter: int iAttempts
//************************************
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!SetFileAttributes(lpFileName, dwFileAttributes) && (iAttempts > 0)) {
		iAttempts--;
		_tprintf(L"SetFileAttributes(%s, %08X) failed, %d attempts left\r\n", lpFileName, dwFileAttributes, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    DeleteFileWithRetry
// FullName:  DeleteFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: int iAttempts
//************************************
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	while (!DeleteFile(lpFileName) && (iAttempts > 0)) {
		iAttempts--;
		_tprintf(L"DeleteFile(%s) failed, %d attempts left\r\n", lpFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    MoveFileWithRetry
// FullName:  MoveFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
// Parameter: int iAttempts
//************************************
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!MoveFile(lpExistingFileName, lpNewFileName) && (iAttempts > 0)) {
		iAttempts--;
		_tprintf(L"MoveFile(%s, %s) failed, %d attempts left\r\n", lpExistingFileName, lpNewFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    SecureDeleteFile
// FullName:  SecureDeleteFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
//************************************
BOOL SecureDeleteFile(LPCTSTR lpFileName) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	SecureSetFileAttributes(lpFileName, FILE_ATTRIBUTE_NORMAL);
	return DeleteFileWithRetry(lpFileName, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureSetFileAttributes
// FullName:  SecureSetFileAttributes
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
//************************************
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	return SetFileAttributesWithRetry(lpFileName, dwFileAttributes, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureMoveFile
// FullName:  SecureMoveFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
//************************************
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	SecureSetFileAttributes(lpNewFileName, FILE_ATTRIBUTE_NORMAL);
	SecureDeleteFile(lpNewFileName);
	return MoveFileWithRetry(lpExistingFileName, lpNewFileName, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    IsFolderWriteable
// FullName:  IsFolderWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
//************************************
BOOL IsFolderWriteable()
{
	return (IsFileWriteable(szIniFile) && IsFileWriteable(szWrapperFilePath) && IsFileWriteable(szIl2ServerExeFilePath));
}

//************************************
// Method:    IsFileWriteable
// FullName:  IsFileWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR szFilePath
//************************************
BOOL IsFileWriteable(LPCTSTR szFilePath) {
	BOOL bRetVal = TRUE;
	FILE* fTest;
	fTest = _wfopen(szFilePath, L"a+");
	if (fTest == NULL)
		bRetVal = FALSE;
	else
		fclose(fTest);
	return bRetVal;
}
