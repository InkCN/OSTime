import java.io.IOException;

/**
 * 批处理启动jar包：
 * @echo off
 * cd 存放jar包文件夹路径
 * java -jar OSTime.jar
 *
 * 不显示cmd窗口后台运行批处理：
 * Set shell=CreateObject("Wscript.shell")
 * a=shell.run("上面批处理文件的路径",0)
 */
@SuppressWarnings({"SpellCheckingInspection", "JavaDoc"})
public class Test {
    public static void main(String[] args) throws IOException {
        //重启explore.exe
        Runtime.getRuntime().exec("cmd /c start /wait taskkill /F /IM explorer.exe & explorer");
    }
}
