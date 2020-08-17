package com.mo.libsx.utils.file_utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.mo.libsx.k;
import com.mo.libsx.modle.unit.FileSize;
import com.mo.libsx.modle.unit.Limits;
import com.mo.libsx.utils.activity_utils.ActivitysUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;



/**
 * @ author：mo
 * @ data：2019/2/15:10:20
 * @ 功能：
 */
public class FileUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private FileUtils() {
    }








    /**
     * 获取外部储存中以 APP 包名命名的文件夹路径
     *
     * @return 文件夹路径
     */
    public static String getPackageDir() {
        return Environment.getExternalStorageDirectory() + "/" + k.app().getPackageName() + "/";
    }

    /**
     * 创建文件夹及其父文件夹
     *
     * @param dir 文件夹对象
     * @return 成功创建返回 true, 否则返回 false
     */
    public static boolean makeDirs(File dir) {
        if (!FileUtil2.checkFile(dir)) return false;

        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 创建父文件夹
     *
     * @param file 文件或文件夹对象
     * @return 成功创建返回 true, 否则返回 false
     */
    public static boolean makeParentDirs(File file) {
        if (!FileUtil2.checkFile(file)) return false;

        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    //================================================ 读取文件 ================================================//

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(File file) {
        return readFile(file, DEFAULT_CHARSET);
    }

    /**
     * 读取文件, 默认编码 UTF-8
     *
     * @param filePath 文件路径
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(String filePath) {
        return readFile(new File(filePath), DEFAULT_CHARSET);
    }

    /**
     * 读取文件
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本内容, 读取失败返回 null
     */
    public static StringBuilder readFile(File file, String charsetName) {
        if (!FileUtil2.checkFile(file) || !file.isFile()) {
            return null;
        }

        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (fileContent.length() != 0) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素, 默认编码 UTF-8
     *
     * @param file 文件对象
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readFileAsList(File file) {
        return readFileAsList(file, DEFAULT_CHARSET);
    }

    /**
     * 以集合形式读取文件, 每一行为一个元素
     *
     * @param file        文件对象
     * @param charsetName 编码名称
     * @return 文本集合, 每一个元素代表一行
     */
    public static List<String> readFileAsList(File file, String charsetName) {
        if (!FileUtil2.checkFile(file) || !file.isFile()) {
            return null;
        }

        List<String> contents = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
            return contents;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //================================================ 写入文件 ================================================//

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content) {
        return writeFile(file, content, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 文本内容
     * @param append  是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content, boolean append) {
        return writeFile(file, content, append, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file           文件
     * @param content        文本内容
     * @param append         是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @param endWithNewLine 是否在末尾添加换行
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String content, boolean append, boolean endWithNewLine) {
        if (TextUtils.isEmpty(content) || !FileUtil2.checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.append(content);
            if (endWithNewLine)
                writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, List<String> contents) {
        return writeFile(file, contents, false);
    }

    /**
     * 将字符串数组写入文件, 数组中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容数组
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, String... contents) {
        return writeFile(file, Arrays.asList(contents), false);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param contents 文本内容集合
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, List<String> contents, boolean append) {
        if (contents == null || !FileUtil2.checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            for (int i = 0; i < contents.size(); i++) {
                if (i > 0) {
                    writer.newLine();
                }
                writer.append(contents.get(i));
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     * @return 是否写入成功
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean writeFile(File file, Consumer<BufferedWriter> consumer) {
        return writeFile(file, consumer, false);
    }

    /**
     * 将字符串集合写入文件, 集合中每个元素占一行
     *
     * @param file     文件
     * @param consumer 用于自定义写入操作的消费者
     * @param append   是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean writeFile(File file, Consumer<BufferedWriter> consumer, boolean append) {
        if (consumer == null || !FileUtil2.checkFileAndMakeDirs(file)) {
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            consumer.accept(writer);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred. ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将输入流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, InputStream is) {
        return writeFile(file, is, false);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, InputStream is, boolean append) {
        if (is == null || !FileUtil2.checkFileAndMakeDirs(file)) return false;

        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            int sBufferSize = 8192;
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字节数组写入文件
     *
     * @param file  文件
     * @param bytes 字节数组
     * @return 是否写入成功
     */
    public static boolean writeFile(File file, byte[] bytes) {
        return writeFile(file, bytes, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param file   文件
     * @param bytes  字节数组
     * @param append 是否为追加 (true 在文本末尾写入, false 清除原有文本重新写入)
     * @return 是否写入成功
     */
    public static boolean writeFile(final File file, final byte[] bytes, final boolean append) {
        if (bytes == null || !FileUtil2.checkFileAndMakeDirs(file)) return false;

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //================================================ 其他文件操作 ================================================//

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) {
        if (FileUtil2.checkFile(file) && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹及文件夹内的文件
     *
     * @param dir 文件夹
     * @return 是否删除成功
     */
    public static boolean deleteDir(File dir) {
        if (!FileUtil2.checkFile(dir) || !dir.exists() || !dir.isDirectory())
            return false;
        if (!executeDeleteDir(dir))
            return false;
        return dir.delete();
    }

    /**
     * 递归执行删除文件夹操作
     */
    private static boolean executeDeleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete())
                        return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * 重命名文件或文件夹
     *
     * @param file    文件或文件夹
     * @param newName 新文件名或文件夹名
     * @return 是否成功
     */
    public static boolean rename(File file, String newName) {
        if (file == null || !file.exists() || TextUtils.isEmpty(newName))
            return false;
        if (newName.equals(file.getName()))
            return true;

        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 复制文件
     *
     * @param srcFile  原文件对象
     * @param destFile 目标文件对象
     * @return 是否成功
     */
    public static boolean copy(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动文件
     *
     * @param srcFile  原文件对象
     * @param destFile 目标文件对象
     * @return 是否成功
     */
    public static boolean move(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    public static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || !srcFile.exists() || !srcFile.isFile())
            return false;
        if (destFile == null || destFile.exists())
            return false;

        try {
            return writeFile(destFile, new FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动文件夹
     *
     * @param srcDir  原文件夹
     * @param destDir 目标文件夹
     * @param isMove  是否为移动
     * @return 是否成功
     */
    public static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || !srcDir.exists() || !srcDir.isDirectory())
            return false;
        if (!FileUtil2.checkFile(destDir) || (destDir.exists() && destDir.isFile()))
            return false;
        // 如果目标目录在源目录中则返回false
        if (destDir.getAbsolutePath().contains(srcDir.getAbsolutePath()))
            return false;

        if (!executeCopyOrMoveDir(srcDir, destDir, isMove))
            return false;
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 递归执行复制或移动文件夹的操作
     */
    private static boolean executeCopyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destDir, file.getName());
            if (file.isFile()) {
                // 如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove))
                    return false;
            } else if (file.isDirectory()) {
                // 如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove))
                    return false;
            }
        }
        return true;
    }

    /**
     * 获取文件名, 带扩展名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(File file) {
        if (file == null) return null;

        return getFileName(file.getPath());
    }

    /**
     * 获取文件名, 带扩展名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) return filePath;

        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取文件名, 不带扩展名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileNameWithoutExtension(File file) {
        if (file == null) return null;

        return getFileNameWithoutExtension(file.getPath());
    }

    /**
     * 获取文件名, 不带扩展名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtension(File file) {
        if (file == null) return null;

        return getFileExtension(file.getPath());
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath 文件路径
     * @return 文件扩展名
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    /**
     * 获取文件或文件夹的大小
     *
     * @param file 文件或文件夹
     * @return 大小, 单位为 B
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            return file.length();
        }
        long size = 0;
        for (File childFile : file.listFiles()) {
            size += getFileSize(childFile);
        }
        return size;
    }

    /**
     * 指定单位下, 获取文件或文件夹的大小
     *
     * @param file 文件或文件夹
     * @param unit 单位
     * @return 指定单位下的大小
     */
    public static float getFileSize(File file, @Limits.FileSizeDef int unit) {
        return FileSize.formatByte(getFileSize(file), unit);
    }

    /**
     * 获取文件或文件夹格式化后的大小, 单位根据算法自动进行调整
     *
     * @param file 文件或文件夹
     * @return 格式化后的大小
     */
    public static String getFormattedFileSize(File file) {
        return Formatter.formatFileSize(k.app(), getFileSize(file));
    }
}
