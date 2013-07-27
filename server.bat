@ECHO OFF
SET BINDIR=%~dp0
CD /D "%BINDIR%"
"%ProgramFiles%\Java\jre7\bin\java.exe" -Xincgc -Xmx1g -jar server.jar
PAUSE