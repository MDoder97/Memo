package rs.raf.marko_doder_rn12218.application

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.marko_doder_rn12218.modules.coreModule
import rs.raf.marko_doder_rn12218.modules.memoryModule
import timber.log.Timber

class AppCore : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
        initStetho()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            memoryModule
        )
        startKoin {
            androidLogger(Level.DEBUG)
            // Use application context
            androidContext(this@AppCore)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}