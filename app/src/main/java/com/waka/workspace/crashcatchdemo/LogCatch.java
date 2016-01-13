package com.waka.workspace.crashcatchdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * 截取logcat日志
 * <p>
 * 感谢 马涛 大神的无私分享
 * 这里是博客地址：http://www.cnblogs.com/mataojin/archive/2011/11/07/2239260.html
 *
 * @author waka
 */
public class LogCatch {

    private static final String TAG = "LogCatch";

    /**
     * 捕获log，并写入文件中，返回文件名
     *
     * @return log日志文件名
     */
    public static String getLog() {
        Log.i(TAG, "--------func start--------"); // 方法启动
        try {
            ArrayList<String> cmdLine = new ArrayList<String>(); // 设置命令 logcat
            // -d 读取日志
            cmdLine.add("logcat");
            cmdLine.add("-d");

            ArrayList<String> clearLog = new ArrayList<String>(); // 设置命令 logcat
            // -c 清除日志
            clearLog.add("logcat");
            clearLog.add("-c");

            Process process = Runtime.getRuntime().exec(cmdLine.toArray(new String[cmdLine.size()])); // 捕获日志
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream())); // 将捕获内容转换为BufferedReader

            // 创建logXXXXXXXXXXXXX.txt文件，使用当前时间为文件名，避免重复
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String logFileName = rootPath + "/log_" + simpleDateFormat.format(new Date()) + ".log";

            /*//因为是sd卡根目录，所以就需要创建父文件夹了
            File file = new File(rootPath);
            if (!file.exists()) {
                file.mkdirs();// 如果不存在，则创建所有的父文件夹
            }*/

            // Runtime.runFinalizersOnExit(true);
            String str = null;
            FileOutputStream fos = new FileOutputStream(logFileName);
            while ((str = bufferedReader.readLine()) != null) // 开始读取日志，每次读取一行
            {
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()])); // 清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
                fos.write(str.getBytes());
                Log.i(TAG, str);// 输出到log里 //输出，在logcat中查看效果，也可以是其他操作，比如发送给服务器..
            }
            fos.close();
            if (str == null) {
                Log.i(TAG, "--   is null   --");
            }
            return logFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "--------func end--------");
        return null;
    }
}
