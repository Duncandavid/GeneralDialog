package com.primary.generaldialog

import android.app.Dialog
import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.primary.generaldialog.Utils.dipToPx

/**
 * Created by David at 2023/4/4
 */
class GeneralDialog(private val builder: Builder) {
    private lateinit var dialogTitle: TextView
    private lateinit var dialogContent: TextView
    private lateinit var cancelText: TextView
    private lateinit var positiveButton: Button
    private lateinit var negativeButton: Button

    interface ClickButtonListener {
        fun onPositiveButtonClick()
        fun onNegativeButtonClick()
        fun onCancelTextClick()
    }

    private fun show() {
        val context = builder.context
        val dialog = Dialog(context, R.style.general_dialog)
        val view = View.inflate(context, R.layout.general_dialog, null)
        positiveButton = view.findViewById<View>(R.id.positiveButton) as Button
        negativeButton = view.findViewById<View>(R.id.negativeButton) as Button
        dialogTitle = view.findViewById<View>(R.id.dialogTitle) as TextView
        dialogContent = view.findViewById<View>(R.id.dialogContent) as TextView
        cancelText = view.findViewById<View>(R.id.cancelText) as TextView
        showDialogTitle()
        setDialogContent()
        setButtonText(negativeButton, builder.negativeButtonText)
        setButtonText(positiveButton, builder.positiveButtonText)
        setCancelText(cancelText, builder.cancelText)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(builder.isCancelOutSide)
        dialog.setCancelable(builder.isCancelable)
        setLayoutAttributes(context, dialog)
        setButtonClickListener(dialog)
        dialog.show()
    }

    private fun setButtonClickListener(dialog: Dialog) {
        positiveButton?.setOnClickListener {
            dialog.dismiss()
            if (builder.buttonClickListener != null) {
                builder.buttonClickListener!!.onPositiveButtonClick()
            }
        }
        negativeButton?.setOnClickListener {
            if (!builder.isNotDismissDialogForNegativeButton) {
                dialog.dismiss()
            }
            if (builder.buttonClickListener != null) {
                builder.buttonClickListener!!.onNegativeButtonClick()
            }
        }
        cancelText?.setOnClickListener {
            dialog.dismiss()
            if (builder.buttonClickListener != null) {
                builder.buttonClickListener!!.onCancelTextClick()
            }
        }
    }

    private fun setButtonText(button: Button?, text: String?) {
        if (TextUtils.isEmpty(text)) {
            button!!.visibility = View.GONE
        } else {
            button!!.visibility = View.VISIBLE
            button.text = text
        }
    }

    private fun setCancelText(textView: TextView?, text: String?) {
        if (TextUtils.isEmpty(text)) {
            textView!!.visibility = View.GONE
        } else {
            textView!!.visibility = View.VISIBLE
            textView.text = text
        }
    }

    private fun setDialogContent() {
        dialogContent!!.text = if (builder.message == null) builder.spanMessage else Html.fromHtml(builder.message)
        dialogContent!!.movementMethod = LinkMovementMethod.getInstance()
        dialogContent.post {
            if ((!TextUtils.isEmpty(builder.message) || (!TextUtils.isEmpty(builder.spanMessage))) &&
                dialogContent.lineCount > 1) {
                val lp = dialogContent.layoutParams as LinearLayout.LayoutParams
                lp.bottomMargin = dipToPx(builder.context, 20f)
                dialogContent.layoutParams = lp
            } else {
                val lp = dialogContent.layoutParams as LinearLayout.LayoutParams
                lp.bottomMargin = dipToPx(builder.context, 40f)
                dialogContent.layoutParams = lp

            }
        }
    }

    private fun showDialogTitle() {
        if (TextUtils.isEmpty(builder.title)) {
            dialogTitle!!.visibility = View.GONE
        } else {
            dialogTitle!!.visibility = View.VISIBLE
            dialogTitle!!.text = builder.title
        }
    }

    private fun setLayoutAttributes(context: Context, dialog: Dialog) {
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width = (context.resources.displayMetrics.widthPixels * 0.9f).toInt()
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialogWindow.attributes = lp
    }

    class Builder(val context: Context) {
        var title: String? = null
        var message: String? = null
        var spanMessage: SpannableString? = null
        var positiveButtonText: String? = null
        var negativeButtonText: String? = null
        var cancelText: String? = null
        var buttonClickListener: ClickButtonListener? = null
        var isCancelOutSide = false
        var isCancelable = false
        var isNotDismissDialogForNegativeButton = false

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setSpanMessage(spanMessage: SpannableString?): Builder {
            this.spanMessage = spanMessage
            return this
        }

        fun setPositiveButton(text: String?): Builder {
            positiveButtonText = text
            return this
        }

        fun setNegativeButton(text: String?): Builder {
            negativeButtonText = text
            return this
        }

        fun setCancelText(text: String?): Builder {
            cancelText = text
            return this
        }

        fun setButtonClickListener(listener: ClickButtonListener?): Builder {
            buttonClickListener = listener
            return this
        }

        fun setCancelableOutSide(isCancelableOutSide: Boolean): Builder {
            isCancelOutSide = isCancelableOutSide
            return this
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun setIsNotDismissDialogForNegativeButton(isNotDismissDialogForNegativeButton: Boolean): Builder {
            this.isNotDismissDialogForNegativeButton = isNotDismissDialogForNegativeButton
            return this
        }

        fun show() {
            GeneralDialog(this).show()
        }
    }
}