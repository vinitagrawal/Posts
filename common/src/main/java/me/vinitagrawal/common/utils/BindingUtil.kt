package me.vinitagrawal.common.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun <V : View> Fragment.bindView(@IdRes idRes: Int) =
    lazy { view!!.findViewById<V>(idRes) }

fun <V : View> RecyclerView.ViewHolder.bindView(@IdRes idRes: Int) =
    lazy { itemView.findViewById<V>(idRes) }
