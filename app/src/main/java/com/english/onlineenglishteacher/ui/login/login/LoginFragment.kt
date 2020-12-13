package com.english.onlineenglishteacher.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentLoginBinding
import com.english.onlineenglishteacher.ui.login.register.RegisterActivity
import com.english.onlineenglishteacher.util.EXTRA_CODE_SENT_PWD
import com.english.onlineenglishteacher.util.hide
import com.english.onlineenglishteacher.util.hideKeyboard
import com.english.onlineenglishteacher.util.show
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() =  _binding!!

    private var mCallbacksClient: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mResendingTokenClient: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ccpLogin.registerCarrierNumberEditText(binding.editTextPhoneLogin)

        mCallbacksClient = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
                binding.prBarLogin.hide()
                Log.d("LoginFragment", "onVerificationCompleted: Success")
                signInWithPhone(authCredential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("LoginFragment", "onVerificationFailed: ${p0.message}")
            }

            override fun onCodeSent(
                s: String,
                resendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, resendingToken)
                try {
                    binding.prBarLogin.hide()
                } catch (e: Exception) {
                }
                mResendingTokenClient = resendingToken
                val bundle = Bundle()
                Log.d("ForgotPasswordFragment", "onCodeSent (line 143): $s what")
                bundle.putString(EXTRA_CODE_SENT_PWD, s)
                findNavController().navigate(
                    R.id.action_loginFragment_to_phoneConfirmationFragment,
                    bundle
                )
            }
        }

        binding.buttonSignInLogin.setOnClickListener {
            if (binding.editTextPhoneLogin.text.toString().isEmpty()){
                binding.phoneErrorLogin.visibility = View.VISIBLE
            } else {
                hideKeyboard()
                binding.prBarLogin.show()
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    binding.ccpLogin.fullNumberWithPlus,
                    60,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    mCallbacksClient!!
                )
            }
        }

    }

    private fun signInWithPhone(authCredential: PhoneAuthCredential) {
        binding.prBarLogin.show()

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(requireContext(), RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                requireActivity().finish()
                startActivity(intent)
            } else {
                Log.d("LoginFragment", "signInWithPhone: ${it.exception}")
            }
        }
    }

}