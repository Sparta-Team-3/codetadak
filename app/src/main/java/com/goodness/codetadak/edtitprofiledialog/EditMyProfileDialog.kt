package com.goodness.codetadak.edtitprofiledialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.goodness.codetadak.databinding.DialogEditMyfrofileBinding
import com.goodness.codetadak.sharedpreferences.UserInfo

interface OkClick {
    fun onClick(userInfo: UserInfo)
}

class EditMyProfileDialog() : DialogFragment() {
    var okClick: OkClick? = null
    private var _binding : DialogEditMyfrofileBinding? = null
    private val binding get() = _binding!!
    private var profileUri : Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivEditProfile.setImageURI(uri)
            profileUri = uri
        }
    }

    private val editTexts get() = listOf(
        binding.etEditName,
        binding.etEditInfo
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogEditMyfrofileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnEditCheck.isEnabled = false // 버튼 비활성화

            ivAddProfile.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            setAddButtonEnable()
            setTextChangeListener()
            setFocusChangedListener()

            btnEditCheck.setOnClickListener {
                val userInfo = UserInfo(profileUri, etEditName.text.toString(),etEditInfo.text.toString())
                okClick?.onClick(userInfo)
                dismiss()
            }
        }

    }

    private fun setTextChangeListener(){ // edit text에 입력되어 있다면 버튼 활성화
        editTexts.forEach{editText ->
            editText.addTextChangedListener {
                editText.addTextChangedListener()
                setAddButtonEnable()
            }
        }
    }

    private fun setFocusChangedListener() { //
        editTexts.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    (editText.parent.parent as? ScrollView)?.smoothScrollTo(0, editText.bottom)
                }
                if (hasFocus.not()) {
                    editText.setErrorMessage()
                    setAddButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage(){ // 에러메세지 뿌리기
        when(this) {
            binding.etEditName -> error = getMessageValidName()
            binding.etEditInfo -> error = getMessageValidInfo()
        }
    }

    private fun getMessageValidName() : String? { // edit name 칸 비어있을 시
        val name = binding.etEditName.text.toString()
        return when {
            name.isBlank() -> EditMyProfileErrorMessage.EMPTY_NAME
            else -> null
        }?.message?.let { getString(it) }
    }

    private fun getMessageValidInfo() : String? { // edit info 칸 비어있을 시
        val info = binding.etEditInfo.text.toString()
        return when {
            info.isBlank() -> EditMyProfileErrorMessage.EMPTY_INFO
            else -> null
        }?.message?.let { getString(it) }
    }

    private fun setAddButtonEnable() { // 저장 버튼 활성화
        binding.btnEditCheck.isEnabled =
            getMessageValidName() == null && getMessageValidInfo() == null // 둘다 null 값인 경우 true 반환  isEnabled  = true
    }

}