package me.vinitagrawal.common

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    inline fun <reified T : Fragment> addFragment(@IdRes id: Int, fragment: T) {
        supportFragmentManager
            .beginTransaction()
            .replace(id, fragment, fragment.tag)
            .commit()
    }
}