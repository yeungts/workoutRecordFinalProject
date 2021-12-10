package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentAddFreeweightsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFreeweightsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFreeweightsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _binding: FragmentAddFreeweightsBinding? = null
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
        _binding = FragmentAddFreeweightsBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.addButton.setOnClickListener {
            var action = AddFreeweightsFragmentDirections.actionAddFreeweightsFragmentToWorkoutListFragment("")


            var date : String = binding.date.text.toString()
            var time : Int = binding.workoutLength.text.toString().toInt()
            var weight : Int = binding.weight.text.toString().toInt()

            val freeWeightWorkout : MutableMap<String, Any> = HashMap()

            freeWeightWorkout["date"] = date
            freeWeightWorkout["weight"] = weight
            freeWeightWorkout["time"] = time
            freeWeightWorkout["userId"] = "/users/cLvaECuNLAjgGoE9vZx1"

            if (date.isNullOrEmpty() || weight.equals(null)  || time.equals(null)){
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("")
                builder.setMessage("Please fill in all fielfs to proceed with creating workout record.")
                builder.setNeutralButton("OK") { dialog, which ->

                }
                builder.show()
            } else {
                fireStoreDatabase.collection("freeWeightsWorkouts")
                    .add(freeWeightWorkout)
                    .addOnSuccessListener {
                        Log.d(TAG, "Added document with ID ${it.id}")

                        if (action != null) {
                            this.findNavController().navigate(action)
                        }

                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Error adding document ${it}")
                    }

                binding.date.text!!.clear()
                binding.workoutLength.text!!.clear()
                binding.weight.text!!.clear()
            }


        }
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
         * @return A new instance of fragment AddFreeweightsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFreeweightsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}