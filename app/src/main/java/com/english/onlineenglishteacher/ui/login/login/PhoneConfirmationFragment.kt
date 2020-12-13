package com.english.onlineenglishteacher.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentPhoneConfirmationBinding
import com.english.onlineenglishteacher.ui.login.register.RegisterActivity
import com.english.onlineenglishteacher.util.EXTRA_CODE_SENT_PWD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class PhoneConfirmationFragment : Fragment() {

    private var _binding: FragmentPhoneConfirmationBinding? = null
    private val binding: FragmentPhoneConfirmationBinding get() = _binding!!

    private var codeSent: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrBackCodeConfirmationLogin.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            requireActivity().finish()
            startActivity(intent)
        }

        codeSent = arguments?.getString(EXTRA_CODE_SENT_PWD, "")

        binding.buttonConfirmCodeLogin.setOnClickListener {

            if (checkInput()) {
                val credential = PhoneAuthProvider.getCredential(
                    codeSent!!,
                    binding.codeEditTextLogin.text.toString()
                )
                signInWithCredentials(credential)
            }
        }
    }

    private fun signInWithCredentials(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(requireContext(), RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                requireActivity().finish()
                startActivity(intent)
            } else {
                binding.codeErrorLogin.visibility = View.VISIBLE
                binding.codeErrorLogin.text = getString(R.string.wrongCode)
                Log.d("LoginPhoneConfirmationF", "signInWithCredentials: ${it.exception}")
            }
        }
    }

    private fun checkInput(): Boolean {
        if (binding.codeEditTextLogin.text.toString().isEmpty()) {
            binding.codeErrorLogin.visibility = View.VISIBLE
            binding.codeErrorLogin.text = getString(R.string.requiredField)
            return false
        }
        return true
    }


}