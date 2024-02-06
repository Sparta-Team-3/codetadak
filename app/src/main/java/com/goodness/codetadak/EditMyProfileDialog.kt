package com.goodness.codetadak

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.goodness.codetadak.databinding.DialogEditMyfrofileBinding

interface OkClick {
    fun onClick(profileImage: Drawable, name: String, info: String)
}

class EditMyProfileDialog() : DialogFragment() {
    var okClick: OkClick? = null
    private var _binding : DialogEditMyfrofileBinding? = null
    private val binding get() = _binding!!
    private var selectedProfile: Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivEditProfile.setImageURI(uri)
            selectedProfile = uri
        }
    }

    private val edittexts get() = listOf(
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

            btnEditCheck.setOnClickListener {
                okClick?.onClick(
                    ivEditProfile.drawable,
                    etEditName.text.toString(),
                    etEditInfo.text.toString(),)
            }
        }
    }

}