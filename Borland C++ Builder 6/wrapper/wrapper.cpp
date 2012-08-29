// SAS / UP enhanced wrapper for IL-2 Sturmovik 1946

#include <vcl.h>
#include <windows.h>
#include <io.h>
#include <stdio.h>
#include <IniFiles.hpp>
#include <vector>
#include <algorithm>
#include <functional>
#include <iostream>
#include <Shellapi.h>
#pragma hdrstop

#include "sfs.h"

#define TRACEBUILD
#ifdef TRACEBUILD
bool _trace(TCHAR *format, ...);
bool _trace(AnsiString format, ...);
#define TRACE _trace
#else
#define TRACE __noop //false && _trace
#endif

#define DIVIDER_SECONDS         1000000000000.
#define DIVIDER_MILLISECONDS    1000000000.
#define DIVIDER_MICROSECONDS    1000000.
#define DIVIDER_NANOSECONDS     1000.
#define MULTIPLIER_PIKOSECONDS  1000000000000.


// Forward declarations
void ListFiles(const AnsiString &Parent, const AnsiString &Root, const AnsiString &AddFront);
AnsiString MyExtractFilePath(const AnsiString &S);
unsigned __int64 SFS_hash(const unsigned __int64 hash, const void *buf, const int len);
void GetCommandLineParams();
void LinkIl2fbExe();
void SortList(float * pfPikoSeconds);
void CreateModsFolderList(float * pfPikoSeconds);
bool CreateCachedModsFolderList(float * pfPikoSeconds);
void CreateFilesFolderList(float * pfPikoSeconds);
bool CreateCachedFilesFolderList(float * pfPikoSeconds);
bool ReadCachedFileList(const char* pCachedFileListName);
int RemoveDuplicates(float * pfPikoSeconds);
int binarySearchFileListEnhanced(unsigned __int64 theHash);
int __fastcall CompareFileList(void *Item1, void *Item2);
void StopWatchStart(float *pfPikoSeconds);
void StopWatchStop(float *pfPikoSeconds);
bool _trace(TCHAR *format, ...);
void StartWrapper();
void StopWrapper();

struct TMyList
{
	unsigned __int64 hash;
	AnsiString Path;
	DWORD dwIndex;
};

// Global Parameters
char ExeName[MAX_PATH];
AnsiString ExePath;
TList *FileList;
TMemoryStream *ms;
DWORD g_dwIndex;
float g_SearchPikoseconds;
ULONG g_SFSOpenfCalls;
ULONG g_SearchIterations;
ULONGLONG First;
ULONG ulFreq;

AnsiString FilesFolder = "FILES";
AnsiString ModsFolder = "MODS";
AnsiString CurParam;
AnsiString LinkBackName;
AnsiString IniFileName;
AnsiString LogFileName;
bool g_bUseCachedFileLists = FALSE;
FILE *g_cachedListFile = NULL;

// Imported functions from il2fb.exe

typedef int __stdcall TSFS_open (char *filename, int flags);
typedef int __stdcall TSFS_openf (unsigned __int64 hash, int flags);

HINSTANCE hExecutable = NULL;
TSFS_open* SFS_open = NULL;
TSFS_openf* SFS_openf = NULL;

#pragma argsused
int WINAPI DllEntryPoint(HINSTANCE hinst, unsigned long reason, void *lpReserved) {
	switch(reason) {
		case DLL_PROCESS_ATTACH:
			StartWrapper();
			break;
		case DLL_PROCESS_DETACH:
			StopWrapper();
			break;
		default:
			break;
	}
	return 1;
}

void StartWrapper() {
	ULONGLONG ullFreq;
	QueryPerformanceFrequency ( reinterpret_cast<LARGE_INTEGER*>(&ullFreq) );
	ulFreq = (ULONG)ullFreq;
	FileList = new TList;
	ms = new TMemoryStream;
	g_dwIndex = 0;
	try	{
		ms->LoadFromFile(ExePath + "runtimedump.bin");
	} catch(...) { }
	GetCommandLineParams();
	LinkIl2fbExe();
	if (LogFileName.Length()) {
		DeleteFile(LogFileName);
	}
	float fCreateMods=0, fCreateFiles = 0, fSortList=0, fRemoveDups=0;
	AnsiString sTimeConsumed;

	try	{
		if(ModsFolder != "none") {
			if(DirectoryExists(ModsFolder)) {
				if (g_bUseCachedFileLists) {
					bool bCacheUsed = CreateCachedModsFolderList(&fCreateMods);
					sTimeConsumed = Format("%.0f", &TVarRec(fCreateMods/DIVIDER_MILLISECONDS), 0);
					TRACE("Scanning %s folder took %s milliseconds ", ModsFolder, sTimeConsumed);
					if (bCacheUsed) TRACE("(cached).\n"); else TRACE("(cache created).\n");
			 } else {
					AnsiString cachedListFileName = ExePath + ModsFolder + "\\~wrapper.cache";
					if (FileExists(cachedListFileName)) DeleteFile(cachedListFileName);
					CreateModsFolderList(&fCreateMods);
					sTimeConsumed = Format("%.0f", &TVarRec(fCreateMods/DIVIDER_MILLISECONDS), 0);
					TRACE("Scanning %s folder took %s milliseconds.\n", ModsFolder, sTimeConsumed);
				}
			}
		}
	} catch(...) {
		TRACE("Error loading files from Folder " + ModsFolder + "\n");
	}

	try	{
		if(FilesFolder != "none") {
			if(DirectoryExists(FilesFolder)) {
				if (g_bUseCachedFileLists) {
					bool bCacheUsed = CreateCachedFilesFolderList(&fCreateFiles);
					sTimeConsumed = Format("%.0f", &TVarRec(fCreateFiles/DIVIDER_MILLISECONDS), 0);
					TRACE("Scanning %s folder took %s milliseconds ", FilesFolder, sTimeConsumed);
					if (bCacheUsed) TRACE("(cached).\n"); else TRACE("(cache created).\n");
				} else {
					AnsiString cachedListFileName = ExePath + FilesFolder + "\\~wrapper.cache";
					if (FileExists(cachedListFileName)) DeleteFile(cachedListFileName);
					CreateFilesFolderList(&fCreateFiles);
					sTimeConsumed = Format("%.0f", &TVarRec(fCreateFiles/DIVIDER_MILLISECONDS), 0);
					TRACE("Scanning %s folder took %s milliseconds.\n", FilesFolder, sTimeConsumed);
				}
			}
		}
	} catch(...) {
		TRACE("Error loading files from Folder " + FilesFolder + "\n");
	}

	TRACE("Total number of modded files = %d.\n", g_dwIndex);
	SortList(&fSortList);
	sTimeConsumed = Format("%.3f", &TVarRec(fSortList/DIVIDER_MILLISECONDS), 0);
	TRACE("Sorting modded files list took %s milliseconds.\n", sTimeConsumed);
	int iDups = RemoveDuplicates(&fRemoveDups);
	sTimeConsumed = Format("%.3f", &TVarRec(fRemoveDups/DIVIDER_MILLISECONDS), 0);
	TRACE("Removing %d Duplicates took %s milliseconds.\n", iDups, sTimeConsumed);
}

void StopWrapper() {
	for(int i = 0; i < FileList->Count; i++) {
		TMyList *MyStruct = (TMyList *)FileList->Items[i];
		delete MyStruct;
	}
	delete ms;
	delete FileList;
	if (hExecutable != NULL)
		FreeLibrary( hExecutable );
	AnsiString sTimeConsumed;
	TRACE("Total files opened = %d\n", g_SFSOpenfCalls);
	float fTotalSearchSeconds = g_SearchPikoseconds / DIVIDER_SECONDS;
	AnsiString sTotalSearchSeconds = Format("%.12f", &TVarRec(fTotalSearchSeconds), 0);
	sTimeConsumed = Format("%.3f", &TVarRec(g_SearchPikoseconds / DIVIDER_MILLISECONDS), 0);
	TRACE("Total search time consumed = %s milliseconds ", sTimeConsumed);
	TRACE("(%s Seconds)\n", sTotalSearchSeconds);
	LONG lSFSOpenfCalls = long(g_SFSOpenfCalls);
	float fSearchPerFileSeconds = g_SearchPikoseconds / float(lSFSOpenfCalls) / DIVIDER_SECONDS;
	AnsiString sSearchPerFileSeconds = Format("%.12f", &TVarRec(fSearchPerFileSeconds), 0);
	sTimeConsumed = Format("%.3f", &TVarRec(g_SearchPikoseconds / float(lSFSOpenfCalls) / 1000), 0);
	TRACE("Search Time per File = %s nanoseconds ", sTimeConsumed);
	TRACE("(%s Seconds)\n", sSearchPerFileSeconds);
	float fSearchIterationsPerFile = float(g_SearchIterations) / float(g_SFSOpenfCalls);
	AnsiString sSearchIterationsPerFile = Format("%.1f", &TVarRec(fSearchIterationsPerFile), 0);
	TRACE("Average Search Iterations required per File = %s\n", sSearchIterationsPerFile);
}

int __fastcall CompareFileList(void *mfli1, void *mfli2) {
	int iRet = 0;
	if (((TMyList*)mfli1)->hash < ((TMyList*)mfli2)->hash) iRet = -1;
	else if (((TMyList*)mfli1)->hash > ((TMyList*)mfli2)->hash)	iRet = 1;
	else if (((TMyList*)mfli1)->dwIndex < ((TMyList*)mfli2)->dwIndex) iRet = -1;
	else if (((TMyList*)mfli1)->dwIndex > ((TMyList*)mfli2)->dwIndex) iRet = 1;
	return iRet;
}

void SortList(float * pfPikoSeconds) {
  StopWatchStart(pfPikoSeconds);
  FileList->Sort(CompareFileList);
  StopWatchStop(pfPikoSeconds);
}

void CreateModsFolderList(float * pfPikoSeconds) {
  StopWatchStart(pfPikoSeconds);
	try	{
		if(ModsFolder != "none") {
			TSearchRec sr;
			if(FindFirst(ExePath + ModsFolder + "\\*.*", faAnyFile, sr) == 0) {
				do {
					if(sr.Name == "." || sr.Name == ".." || sr.Name.SubString(1, 1) == "-")	continue;
					if(sr.Attr & faDirectory) {
						try {
							try	{
								AnsiString Path = ExePath + ModsFolder + "\\" + sr.Name + "\\";
								ListFiles(Path, Path, ModsFolder + "\\" + sr.Name + "\\");
							} catch(...) { }
						} __finally { }
					}
				}
				while(FindNext(sr) == 0);
				FindClose(sr);
			}
		}
	} catch(...) {
		TRACE("Error creating Files List from Folder " + ModsFolder + "\n");
	}
	StopWatchStop(pfPikoSeconds);
}


bool CreateCachedModsFolderList(float * pfPikoSeconds) {
  bool bRetVal = false;
	try {
		if(ModsFolder != "none") {
			StopWatchStart(pfPikoSeconds);
			AnsiString cachedListFileName = ExePath + ModsFolder + "\\~wrapper.cache";
			if (FileExists(cachedListFileName)) {
				bRetVal = ReadCachedFileList(cachedListFileName.c_str());
			}
			if (!bRetVal) {
				g_cachedListFile = fopen(cachedListFileName.c_str(), "wt");
				CreateModsFolderList(NULL);
				fflush(g_cachedListFile);
				fclose(g_cachedListFile);
				g_cachedListFile = NULL;
			}
			StopWatchStop(pfPikoSeconds);
		} else bRetVal = true;
	} catch(...) {
		TRACE("Error creating cached Files List from Folder " + ModsFolder + "\n");
	}
	return bRetVal;
}

void CreateFilesFolderList(float * pfPikoSeconds) {
	StopWatchStart(pfPikoSeconds);
	try {
		if(FilesFolder != "none") {
			AnsiString Path = ExePath + FilesFolder + "\\";
			ListFiles(Path, Path, FilesFolder + "\\");
		}
	} catch(...) {
		TRACE("Error creating Files List from Folder " + FilesFolder + "\n");
	}
	StopWatchStop(pfPikoSeconds);
}

bool CreateCachedFilesFolderList(float * pfPikoSeconds) {
  bool bRetVal = false;
	try {
		if(FilesFolder != "none") {
			StopWatchStart(pfPikoSeconds);
			AnsiString cachedListFileName = ExePath + FilesFolder + "\\~wrapper.cache";
			if (FileExists(cachedListFileName)) {
				bRetVal = ReadCachedFileList(cachedListFileName.c_str());
			}
			if (!bRetVal) {
				g_cachedListFile = fopen(cachedListFileName.c_str(), "wt");
				CreateFilesFolderList(NULL);
				fflush(g_cachedListFile);
				fclose(g_cachedListFile);
				g_cachedListFile = NULL;
			}
			StopWatchStop(pfPikoSeconds);
		} else bRetVal = true;
	} catch(...) {
		TRACE("Error creating cached Files List from Folder " + FilesFolder + "\n");
	}
	return bRetVal;
}

bool ReadCachedFileList(const char* pCachedFileListName) {
	try {
		try {
			g_cachedListFile = fopen(pCachedFileListName, "rt");
			if (g_cachedListFile == NULL) return false;
			char cachedFileLineBuf[MAX_PATH * 2];
			while (!feof(g_cachedListFile)) {
				fgets(cachedFileLineBuf, MAX_PATH * 2, g_cachedListFile);
				if (strlen(cachedFileLineBuf) > 20) { // ensure the line is valid
					try {
						AnsiString cachedFileLine = AnsiString(cachedFileLineBuf);
						int separatorPos = cachedFileLine.Pos("?");
						AnsiString hashString = "0x" + cachedFileLine.SubString(1, separatorPos-1);
						AnsiString fileString = cachedFileLine.SubString(separatorPos + 1, cachedFileLine.Length() - separatorPos - 1);
						if (cachedFileLine.Pos("~wrapper.cache") != 0) continue;
						TMyList *MyStruct = new TMyList;
						MyStruct->Path = fileString;
						sscanf(hashString.c_str(), "%I64X", &(MyStruct->hash) );
						MyStruct->dwIndex = g_dwIndex++;
						FileList->Add(MyStruct);
					} catch(...) {
						TRACE("Error Parsing Line \"%s\" from wrapper cache file \"%s\"!\n", cachedFileLineBuf, pCachedFileListName);
					}
				}
			}
		} catch(...) {
			TRACE("Error reading cached file list\n");
		}
	}	__finally {
		fclose(g_cachedListFile);
		g_cachedListFile = NULL;
	}
  return true;
}

void GetCommandLineParams() {
	try {
		GetModuleFileName(NULL, ExeName, sizeof(ExeName));
		LinkBackName = ExeName;
		LinkBackName.Unique();
		ExePath = MyExtractFilePath(ExeName);
		if(ExePath[ExePath.Length()] != '\\')
			ExePath += '\\';
		IniFileName = ExePath + "il2fb.ini";
		IniFileName.Unique();
		LogFileName = ExePath + "wrapper.log";
		LogFileName.Unique();
		TIniFile *pIniFile = new TIniFile(IniFileName);
		int iModType = pIniFile->ReadInteger("Settings", "ModType", 1);
		switch (iModType) {
		case 1:
			FilesFolder = "FILES";
			ModsFolder = "MODS";
			break;
		case 2:
			FilesFolder = "none";
			ModsFolder = "#SAS";
			break;
		case 3:
			FilesFolder = "none";
			ModsFolder = "#UP#";
			break;
		case 0:
		default:
			FilesFolder = "none";
			ModsFolder = "none";
			break;
		}
		FilesFolder = pIniFile->ReadString("Settings", "FilesFolder", FilesFolder);
		ModsFolder = pIniFile->ReadString("Settings", "ModsFolder", ModsFolder);
		g_bUseCachedFileLists = pIniFile->ReadBool("Settings", "UseCachedFileLists", false);
		delete pIniFile;

		for (int i=1;i<=ParamCount();i++) {
			CurParam = ParamStr(i);
			CurParam.Unique().LowerCase().Trim();
			if (CurParam.Pos("/") == 1) CurParam = CurParam.SubString(2, CurParam.Length() - 1);
			if (CurParam.Pos("f:") == 1) {
				FilesFolder = AnsiDequotedStr(CurParam.SubString(3,CurParam.Length()-2), '"');
			} else if (CurParam.Pos("m:") == 1) {
				ModsFolder = AnsiDequotedStr(CurParam.SubString(3,CurParam.Length()-2), '"');
			} else if (CurParam.Pos("lb:") == 1) {
				LinkBackName = ExePath + AnsiDequotedStr(CurParam.SubString(4,CurParam.Length()-3), '"');
				LinkBackName.Unique();
			} else if (CurParam.Pos("cache") == 1) {
				g_bUseCachedFileLists = true;
			}
		}
	} catch(...) {
		TRACE("Error reading command line parameters / ini file\n");
	}
}

void LinkIl2fbExe() {
	try {
		if (hExecutable == NULL)
			hExecutable = LoadLibrary( LinkBackName.c_str() );
		if (hExecutable == NULL) {
			MessageBox(NULL, "Attaching to IL-2 Executable failed!", "SAS wrapper", MB_OK | MB_ICONERROR);
			ExitProcess(-1);
		}
		if (SFS_open == NULL) SFS_open = (TSFS_open*)GetProcAddress(hExecutable, "SFS_open");
		if (SFS_open == NULL) SFS_open = (TSFS_open*)GetProcAddress(hExecutable, (LPCTSTR)0x87);
		if (SFS_open == NULL) {
			MessageBox(NULL, "SFS open function pointer missing!", "SAS wrapper", MB_OK | MB_ICONERROR);
			ExitProcess(-1);
		}
		if (SFS_openf == NULL) SFS_openf = (TSFS_openf*)GetProcAddress(hExecutable, "SFS_openf");
		if (SFS_openf == NULL) SFS_openf = (TSFS_openf*)GetProcAddress(hExecutable, (LPCTSTR)0x88);
		if (SFS_openf == NULL) {
			MessageBox(NULL, "SFS openf function pointer missing!", "SAS wrapper", MB_OK | MB_ICONERROR);
			ExitProcess(-1);
		}
	} catch(...) {
		TRACE("Error creating backlink to IL-2 executable\n");
	}
}

int RemoveDuplicates(float * pfPikoSeconds) {
	StopWatchStart(pfPikoSeconds);
	int iRetVal = 0;
	try {
		TMyList *dupElement = NULL;
		for (int i = 0; i<FileList->Count; i++) {
			TMyList *listElement = (TMyList *)FileList->Items[i];
			if (dupElement != NULL) {
				if (listElement->hash == dupElement->hash) {
					listElement->Path = dupElement->Path;
																	iRetVal++;
					continue;
				}
			}
			dupElement = listElement;
		}
	} catch(...) {
		TRACE("Error removing duplicates from list\n");
	}
	StopWatchStop(pfPikoSeconds);
  return iRetVal;
}

int binarySearchFileListEnhanced(unsigned __int64 theHash) {
  float fPikoSeconds = 0.;
	StopWatchStart(&fPikoSeconds);
	int first = 0;
	int last = FileList->Count - 1;
	int mid = 0;
	while (first < last) {
		g_SearchIterations++;
		unsigned __int64 hashSpan = ((TMyList *)FileList->Items[last])->hash - ((TMyList *)FileList->Items[first])->hash;
		unsigned __int64 hashDistance = theHash - ((TMyList *)FileList->Items[first])->hash;
		float fHashDistance = (float)hashDistance / (float)hashSpan;
		if (fHashDistance > 1.0) return -1;
		mid = (int)((last-first) * fHashDistance) + first;
		if (theHash > ((TMyList *)FileList->Items[mid])->hash) first = mid + 1;
		else if (theHash < ((TMyList *)FileList->Items[mid])->hash) last = mid - 1;
		else {
			StopWatchStop(&fPikoSeconds);
			g_SearchPikoseconds += fPikoSeconds;
			return mid;      // found it. return position /////
		}
	}
	if (theHash == ((TMyList *)FileList->Items[mid])->hash) return mid;
	return -1;     // failed to find key
}

// Exported function for opening files.
// Opens files from modded folders in case they exist, otherwise from SFS archives

extern "C" __declspec(dllexport) int WINAPI __SFS_openf(const unsigned __int64 hash, const int flags) {
	g_SFSOpenfCalls++;
	AnsiString Name = "";
	AnsiString HashString = IntToHex((__int64)hash, sizeof(hash) * 2);
	unsigned __int64 hash2 = SFS_hash(0, HashString.c_str(), HashString.Length());
	int i = binarySearchFileListEnhanced(hash);
	if (i==-1) i=binarySearchFileListEnhanced(hash2);
	if (i!=-1) Name = ((TMyList *)FileList->Items[i])->Path;
	int fp = -1;
	if(i!=-1) fp = SFS_open(Name.c_str(), flags);
	if (fp == -1) fp = SFS_openf(hash, flags);
	return fp;
}

//---------------------------------------------------------------------------

extern "C" __declspec(dllexport) void WINAPI ReadDump(void *buf, unsigned len) {
	if(ms && ms->Size > 0) {
		if(ms->Position >= ms->Size)
			ms->Position = 0;
		try {
			ms->Read(buf, len);
		} catch(...) { }
	}
}
//---------------------------------------------------------------------------

void ListFiles(const AnsiString &Parent, const AnsiString &Root, const AnsiString &AddFront) {
	AnsiString Dir = Parent;
	if(Dir[Dir.Length()] != '\\')
		Dir += '\\';
        TSearchRec sr;
	if(FindFirst(Dir + "*.*", faAnyFile, sr) == 0) {
		do {
			if(sr.Name == "." || sr.Name == "..")
				continue;
			if(sr.Attr & faDirectory)
				ListFiles(Dir + sr.Name, Root, AddFront);
			else {
				TMyList *MyStruct = new TMyList;
				AnsiString foundItem = (Dir + sr.Name).SubString(Root.Length() + 1, MaxInt);
				MyStruct->Path = AddFront + foundItem;
				MyStruct->hash = SFS_hash(0, foundItem.UpperCase().c_str(), foundItem.Length());
				MyStruct->dwIndex = g_dwIndex++;
				FileList->Add(MyStruct);
        if (g_cachedListFile != NULL) {
          fprintf(g_cachedListFile, "%016I64X?", MyStruct->hash);
          fprintf(g_cachedListFile, "%s\n", MyStruct->Path);
        }
			}
		} while(FindNext(sr) == 0);
		FindClose(sr);
	}
}
//---------------------------------------------------------------------------

AnsiString MyExtractFilePath(const AnsiString &S) {
	int nIndex = 0;
	for(int i = S.Length(); i >= 1; i--)
		if(S[i] == '\\') {
			nIndex = i;
			break;
		}
	if(!nIndex) return "";
	return S.SubString(1, nIndex);
}
//---------------------------------------------------------------------------

unsigned __int64 SFS_hash(const unsigned __int64 hash, const void *buf, const int len) {
	unsigned char c;
	unsigned a = (unsigned)(hash & 0xFFFFFFFF);
	unsigned b = (unsigned)(hash >> 32 & 0xFFFFFFFF);
	for(int i = 0; i < len; i++) {
		c = ((unsigned char *)buf)[i];
		a = (a << 8 | c) ^ FPaTable[a >> 24];
		b = (b << 8 | c) ^ FPbTable[b >> 24];
	}
	return (unsigned __int64)a & 0xFFFFFFFF | (unsigned __int64)b << 32;
}
//---------------------------------------------------------------------------

void StopWatchStart(float *pfPikoSeconds) {
	if (pfPikoSeconds != NULL) QueryPerformanceCounter( reinterpret_cast<LARGE_INTEGER*>(&First) );
}

void StopWatchStop(float *pfPikoSeconds) {
  if (pfPikoSeconds != NULL) {
		ULONGLONG Last;
		QueryPerformanceCounter( reinterpret_cast<LARGE_INTEGER*>(&Last) );
    ULONG ulEclapsedCount = Last - First;
    float fDiffSecs = float(ulEclapsedCount) / float(ulFreq);
		*pfPikoSeconds = fDiffSecs * MULTIPLIER_PIKOSECONDS; //Pikoseconds
	}
}

bool _trace(TCHAR *format, ...) {
	TCHAR buffer[1000];
	va_list argptr;
	va_start(argptr, format);
	wvsprintf(buffer, format, argptr);
	va_end(argptr);
	OutputDebugString(buffer);
	if (LogFileName.Length()) {
		FILE *log = fopen(LogFileName.c_str(), "at");
		fprintf(log, buffer);
		fflush(log);
		fclose(log);
	}
	return true;
}

bool _trace(AnsiString format, ...) {
	return _trace(format.c_str());
}


