package cn.magic.test.study

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project

class InjectTransform extends Transform {
    private Project mProject;

    public InjectTransform(Project project) {
        this.mProject = project
    }

    @Override
    public String getName() {
        return "Magic-Test"
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each { input ->
            input.directoryInputs.each { directoryInput ->

                String path = directoryInput.getFile().getAbsolutePath()
                System.out.println("[cn.magic.test.study.InjectTransform] Begin to inject: " + path)
                //执行注入逻辑
                InjectByJavassit.inject(path, mProject)

                //获取输出目录
                def dest = transformInvocation.outputProvider.
                        getContentLocation(
                                directoryInput.name,
                                directoryInput.contentTypes,
                                directoryInput.scopes,
                                Format.DIRECTORY
                        )
                println("[cn.magic.test.study.InjectTransform] Directory output dest: $dest.absolutePath")
                //将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)

            }
            input.jarInputs.each { jarInput ->
                def dest = transformInvocation.outputProvider.
                        getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }
}