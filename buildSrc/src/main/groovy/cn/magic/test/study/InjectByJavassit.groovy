package cn.magic.test.study

import javassist.CannotCompileException
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

import java.util.regex.Pattern

class InjectByJavassit {
    static void inject(String path, Project project) {
        try {
            File dir = new File(path)
            if (dir.isDirectory()) {
                dir.eachFileRecurse { File file ->
                    if (file.name.endsWith(".class")) {
                        injectTrace(project, file, path)
                    }
//                    if (file.name.endsWith("Activity.class")) {
//                        doInject(project, file, path)
//                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private static void doInject(Project project, File clsFile, String originPath) {
        println("[Inject] DoInject: $clsFile.absolutePath")
        String cls = new File(originPath).relativePath(clsFile).replace('/', '.')
        cls = cls.substring(0, cls.lastIndexOf('.class'))
        println("[Inject] Cls:$cls")

        ClassPool pool = ClassPool.getDefault()
        //加入当前路径
        pool.appendClassPath(originPath)
        // project.android.bootClasspath 加入android.jar,不然找不到android相关的所以类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        //引入android.os.Bundle包，因为onCreate方法参数有Bundle
        pool.importPackage('android.os.Bundle')

        CtClass ctClass = pool.getCtClass(cls)
        //解冻
        if (ctClass.isFrozen()) {
            //如果该类已经加载过到classloader里面了，要申请解冻
            ctClass.defrost()
        }
        // 获取方法
        CtMethod ctMethod = ctClass.getDeclaredMethod('onCreate')

        String toastStr = 'android.widget.Toast.makeText(this, "I am the injected code", android.widget.Toast.LENGTH_SHORT).show();'

        //方法尾插入
        ctMethod.insertAfter(toastStr)
        ctClass.writeFile(originPath)

        //释放
        ctClass.detach()
    }

    public static void injectTrace(Project project, File clsFile, String originPath) throws Exception {
        project.android.bootClasspath.each { println("[Inject] magic path:$it") }

        println("[Inject] DoInject: $clsFile.absolutePath")
        String cls = new File(originPath).relativePath(clsFile).replace('/', '.')
        cls = cls.substring(0, cls.lastIndexOf('.class'))
        println("[Inject] Cls:$cls")

        ClassPool pool = ClassPool.getDefault()
        //加入当前路径
        pool.appendClassPath(originPath)
        // project.android.bootClasspath 加入android.jar,不然找不到android相关的所以类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        try {
            String activityPath = findAndroidXPath("activity")
            println("[Inject] activityPath= ${activityPath}")
            if (activityPath.length() > 0) {
                pool.appendClassPath(activityPath)
                println("[Inject] appendClassPath= ${activityPath}")
            }
        } catch (Exception e) {
            System.err.println("[Inject] failure, androidxActivity:" + e.getMessage())
        }
        CtClass ctClass = pool.getCtClass(cls);
        if (ctClass.name.contains('$')) {
            println("[Inject] 跳过匿名类: ${ctClass.name}")
            return
        }
        //解冻
        if (ctClass.isFrozen()) {
            //如果该类已经加载过到classloader里面了，要申请解冻
            ctClass.defrost()
        }
        println("[Inject] ctClass:${ctClass.name}")
        // 遍历所有声明的方法（不包括继承的方法）
        for (CtMethod method : ctClass.getDeclaredMethods()) {
            try {
                String methodName = method.getName();
                // 跳过构造方法、静态初始化块等
                if (methodName.equals("<init>") || methodName.equals("<clinit>")) {
                    continue;
                }
                println("[Inject] insert methodName:$methodName")
                // 在方法开头插入: Trace.beginSection("methodName");
                method.insertBefore(
                        //直接使用全限定名，就不需要import了
                        String.format("android.os.Trace.beginSection(\"magic-%s\");", methodName)
                );

                // 在方法结尾插入: Trace.endSection();
                method.insertAfter(
                        "android.os.Trace.endSection();",
                        true // 确保在 return 或 throw 前执行
                )
            } catch (CannotCompileException e) {
                System.err.println("[Failed] Cannot inject " + method.name + ": " + e.getMessage());
            }
        }

        // 保存修改后的类（通常在编译时处理，运行时需动态加载）
        ctClass.writeFile(originPath);
        ctClass.detach(); // 释放资源
    }

    private Boolean hasMatchName(File file) {
        println("[Inject] hasMatchName fileName=${file.name}")
        // 创建正则表达式匹配.aar文件(aar文件无法直接识别，所以换成jar包)
        Pattern pattern = Pattern.compile("(.+\\.jar)\$")
        return pattern.matcher(file.name).matches()
//        println("[Inject] isMatches= ${isMatches}, aarFile name=${fileName}")
//        if (isMatches) {
//            // 返回第一个匹配的.aar文件的绝对路径
//            return file.getAbsolutePath()
//        } else {
//            return ""
//        }
    }

    static String findAARpath(File dirFile) {
        if (dirFile.exists()) {
            if (dirFile.isDirectory()) {
                File[] aarFiles = dirFile.listFiles()
                for (File aarFile : aarFiles) {
                    if (aarFile.isDirectory()) {
                        println("[Inject] findAARpath aarFile name= ${aarFile.name}")
                        String path = findAARpath(aarFile)
                        if (path != null && path.length() > 0) {
                            return path
                        }
                    } else {
                        if (aarFile.isFile() && !aarFile.name.equals(".DS_Store") && !aarFile.name.contains(".pom")) {
                            println("[Inject] findAARpath aarFile2 name= ${aarFile.name}")
//                            boolean path = hasMatchName(aarFile)
//                            if (path) {
//                                return aarFile.absolutePath
//                            }
                            println("[Inject] hasMatchName fileName=${aarFile.name}")
                            // 创建正则表达式匹配.aar文件
                            Pattern pattern = Pattern.compile("(.+\\.jar)\$")
                            if (pattern.matcher(aarFile.name).matches()) {
                                return aarFile.absolutePath
                            }
                        }
                    }
                }
            } else {
                boolean path = hasMatchName(dirFile)
                if (path) {
                    return dirFile.absolutePath
                }
            }
        } else {
            return ""
        }
    }



    public static String findAndroidXPath(String moduleName) {
        // 获取用户主目录
        String userHome = System.getProperty("user.home")
        // 构造缓存路径模式
        String cacheDir = "${userHome}/.gradle/caches/modules-2/files-2.1/androidx.${moduleName}"
        println("[Inject] cacheDir= ${cacheDir}")
        // 创建正则表达式匹配.aar文件
        Pattern pattern = Pattern.compile("(.+\\.aar)\$")

        // 遍历缓存目录查找匹配的.aar文件
        File cacheDirFile = new File(cacheDir)
        if (cacheDirFile.exists() && cacheDirFile.isDirectory()) {
            String path = findAARpath(cacheDirFile)
            println("[Inject] findAndroidXPath path= ${path}")
            return path
        }

        // 如果没有找到匹配的文件，返回null
        return null
    }
}