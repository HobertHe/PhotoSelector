package com.kelin.photoselector.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kelin.photoselector.R
import com.kelin.photoselector.model.AlbumType
import com.kelin.photoselector.model.Photo
import com.kelin.photoselector.utils.fullScreen
import com.kelin.photoselector.utils.translucentStatusBar


/**
 * **描述:** 照片选择的Activity。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2020/7/9 3:50 PM
 *
 * **版本:** v 1.0.0
 */
class PhotoSelectorActivity : AppCompatActivity() {

    companion object {

        private const val KEY_TARGET_PAGE = "kelin_photo_selector_key_target_page"
        private const val PAGE_ALBUM = 0x01
        private const val PAGE_PREVIEW = 0x02

        internal fun createPictureSelectorIntent(context: Context, albumType: AlbumType, maxLength: Int, id: Int): Intent {
            return Intent(context, PhotoSelectorActivity::class.java).also { intent ->
                intent.putExtra(KEY_TARGET_PAGE, PAGE_ALBUM)
                AlbumFragment.configurationPictureSelectorIntent(intent, albumType, maxLength, id)
            }
        }

        internal fun startPreview(context: Activity, list: List<Photo>, position: Int = 0) {
            Intent(context, PhotoSelectorActivity::class.java).apply {
                putExtra(KEY_TARGET_PAGE, PAGE_PREVIEW)
            }.also { intent ->
                PhotoPreviewFragment.configurationPreviewIntent(intent, list, position)
                context.startActivity(intent)
            }
            context.overridePendingTransition(R.anim.anim_alpha_in_400, R.anim.anim_alpha_out_400)
        }
    }

    private val targetPage by lazy { intent.getIntExtra(KEY_TARGET_PAGE, 0) }

    private val isPreViewPage by lazy { targetPage == PAGE_PREVIEW }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            if (isPreViewPage) {
                setBackgroundDrawableResource(android.R.color.black)
            }
            fullScreen()
            translucentStatusBar()
        }
        supportActionBar?.hide()
        setContentView(R.layout.activity_kelin_photo_selector_photo_common)
        onCreateContainer().also { fragment ->
            supportFragmentManager.beginTransaction()
                .add(R.id.flKelinPhotoSelectorFragmentContainer, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }

    private fun onCreateContainer(): Fragment {
        return when (targetPage) {
            PAGE_ALBUM -> createFragment<AlbumFragment>()
            PAGE_PREVIEW -> createFragment<PhotoPreviewFragment>()
            else -> throw RuntimeException("Unknown targetPage:${targetPage}!")
        }
    }

    private inline fun <reified F : Fragment> createFragment(): F {
        return F::class.java.getDeclaredConstructor().let { f ->
            f.isAccessible = true
            f.newInstance()
        }.apply {
            arguments = intent.extras
        }
    }

    override fun finish() {
        super.finish()
        if (isPreViewPage) {
            overridePendingTransition(R.anim.anim_alpha_in_400, R.anim.anim_alpha_out_400)
        }
    }
}