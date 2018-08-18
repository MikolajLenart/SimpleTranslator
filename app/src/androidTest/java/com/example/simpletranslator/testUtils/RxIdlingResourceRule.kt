package com.example.simpletranslator.testUtils

import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxIdlingResourceRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler(
                        Rx2Idler.create("RxJava 2.x Io Scheduler"));
                RxJavaPlugins.setInitNewThreadSchedulerHandler(
                        Rx2Idler.create("RxJava 2.x New Thread Scheduler"));
                RxJavaPlugins.setInitSingleSchedulerHandler(
                        Rx2Idler.create("RxJava 2.x Single Scheduler"));
                RxJavaPlugins.setInitComputationSchedulerHandler(
                        Rx2Idler.create("RxJava 2.x Computation Scheduler"));

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}