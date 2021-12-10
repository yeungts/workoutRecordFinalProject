package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {


    val fireStoreDatabase = FirebaseFirestore.getInstance()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener{

            param1 = binding.email.text.toString()
            param2 = binding.password.text.toString()
            System.out.println(param1)
            System.out.println(param2)

            if (param1.isNullOrEmpty() || param2.isNullOrEmpty()){
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("")
                builder.setMessage("Password or Email field is blank. Please fill in both to proceed with login.")
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))


                builder.setNeutralButton("OK") { dialog, which ->

                }
                builder.show()
            } else {
                fireStoreDatabase.collection("users")
                    .whereEqualTo("email", param1)
                    .whereEqualTo("password", param2)
                    .get()
                    .addOnCompleteListener{
                        val result: StringBuffer = StringBuffer()
                        if (it.isSuccessful){
                            for(document in it.result!!){
                                result.append(document.id)
                            }
                            System.out.println("User ID is" + result)
                            //binding?.textView?.setText(result)


                            if(result.isNotEmpty()){
                                var userId = result.toString()
                                var action = LoginFragmentDirections.actionLoginFragmentToWorkoutListFragment(userId)
                                if (action != null) {

                                    this.findNavController().navigate(action)
                                }
                            } else {
                                System.out.println("PLEASE REGISTER")

                                val builder = AlertDialog.Builder(requireActivity())
                                builder.setTitle("")
                                builder.setMessage("Password or Email may be incorrect. Please try again or create a new account.")
                                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))


                                builder.setNeutralButton("OK") { dialog, which ->

                                }
                                builder.show()
                            }
                        }
                    }
            }




        }

        binding.btnToRegister.setOnClickListener{
            var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()

            if (action != null) {
                this.findNavController().navigate(action)
            }
        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}