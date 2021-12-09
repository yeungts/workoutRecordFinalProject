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
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentAddBasketballBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBasketballFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBasketballFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _binding: FragmentAddBasketballBinding? = null
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
        _binding = FragmentAddBasketballBinding.inflate(inflater, container, false)


        val view = binding.root

        binding.addButton.setOnClickListener {
            var action = AddBasketballFragmentDirections.actionAddBasketballFragmentToWorkoutListFragment2()


            var date : String = binding.date.text.toString()
            var time : Int = binding.workoutLength.text.toString().toInt()
            var distance : Double = binding.distance.text.toString().toDouble()
            var points : Int = binding.points.text.toString().toInt()
            var assists : Int = binding.assists.text.toString().toInt()
            var rebounds : Int = binding.rebounds.text.toString().toInt()

            val basketballWorkout : MutableMap<String, Any> = HashMap()

            basketballWorkout["date"] = date
            basketballWorkout["assists"] = assists
            basketballWorkout["distance"] = distance
            basketballWorkout["points"] = points
            basketballWorkout["rebounds"] = rebounds
            basketballWorkout["time"] = time
            basketballWorkout["userId"] = "/users/cLvaECuNLAjgGoE9vZx1"

            if (date.isNullOrEmpty() || assists.equals(null) || distance.equals(null) || points.equals(null) || rebounds.equals(null) || time.equals(null)){
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("")
                builder.setMessage("Please fill in all fielfs to proceed with creating workout record.")
                builder.setNeutralButton("OK") { dialog, which ->

                }
                builder.show()
            } else {
                fireStoreDatabase.collection("basketballWorkouts")
                    .add(basketballWorkout)
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
                binding.distance.text!!.clear()
                binding.points.text!!.clear()
                binding.assists.text!!.clear()
                binding.rebounds.text!!.clear()
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
         * @return A new instance of fragment AddBasketballFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddBasketballFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}