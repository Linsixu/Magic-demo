package cn.magic.test.study

import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.android.registerTransform(new InjectTransform(target))
    }
}