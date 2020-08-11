package com.kelin.photoselector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kelin.photoselector.R

/**
 * **描述:** 进度弹窗
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2020/8/11 5:20 PM
 *
 * **版本:** v 1.0.0
 */
internal class ProgressDialog(private val style: Int = R.style.KelinPhotoSelectorCenterAnimDialog) : DialogFragment() {

    private var isShowing = false

    override fun getTheme(): Int {
        return style
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_kelin_photo_selector_progress, container)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }
}