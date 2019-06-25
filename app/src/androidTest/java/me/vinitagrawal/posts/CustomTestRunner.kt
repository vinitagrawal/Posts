package me.vinitagrawal.posts

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import io.appflate.restmock.RESTMockOptions
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger

class CustomTestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        RESTMockServerStarter.startSync(
            AndroidAssetsFileParser(context), AndroidLogger(),
            RESTMockOptions.Builder()
                .useHttps(false)
                .build()
        )
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        val testApplicationClassName = TestApplication::class.java.canonicalName
        return super.newApplication(cl, testApplicationClassName, context)
    }
}