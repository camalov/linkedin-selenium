@echo off
@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------

@REM set %HOME% to equivalent of $HOME
if "%HOME%" == "" (set "HOME=%HOMEDRIVE%%HOMEPATH%")

@setlocal

set ERROR_CODE=0

@REM To isolate internal variables from possible post scripts, we use another setlocal
@setlocal

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OkJHome

for %%i in (java.exe) do set "JAVACMD=%%~$PATH:i"
if not "%JAVACMD%"=="" goto init

echo.
echo Error: JAVA_HOME not found in your environment. >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto init

echo.
echo Error: JAVA_HOME is set to an invalid directory. >&2
echo JAVA_HOME = "%JAVA_HOME%" >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:init
if "%JAVACMD%"=="" set "JAVACMD=%JAVA_HOME%\bin\java.exe"

@REM Find the project base dir
set MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%
if "%MAVEN_PROJECTBASEDIR%"=="" set "MAVEN_PROJECTBASEDIR=%~dp0"
set "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

set MAVEN_WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties
set MAVEN_WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

if exist "%MAVEN_WRAPPER_JAR%" goto wrapperJarFound

set WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar

echo Couldn't find %MAVEN_WRAPPER_JAR%, downloading it ...
echo Downloading from: %WRAPPER_URL%

powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%', '%MAVEN_WRAPPER_JAR%')"

if exist "%MAVEN_WRAPPER_JAR%" goto wrapperJarFound

echo.
echo Error: %MAVEN_WRAPPER_JAR% not found and failed to download. >&2
echo Please ensure you have internet connection and that the property wrapperUrl >&2
echo is set in %MAVEN_WRAPPER_PROPERTIES% >&2
echo.
goto error

:wrapperJarFound

set MAVEN_CMD_LINE_ARGS=%*
if "%MAVEN_CMD_LINE_ARGS%"=="" set MAVEN_CMD_LINE_ARGS=help

@REM Run Maven with quoted JAVACMD and explicit multiModuleProjectDirectory
"%JAVACMD%" %MAVEN_OPTS% -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -classpath "%MAVEN_WRAPPER_JAR%" %MAVEN_WRAPPER_LAUNCHER% %MAVEN_CMD_LINE_ARGS%

if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%

if not "%MAVEN_TERMINATE_CMD%" == "on" exit /B %ERROR_CODE%
exit %ERROR_CODE%
