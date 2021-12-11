package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentEditCyclingBinding
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentEditRunningBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditRunningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditRunningFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _binding: FragmentEditRunningBinding? = null
    val args: EditRunningFragmentArgs by navArgs()
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
        _binding = FragmentEditRunningBinding.inflate(inflater, container, false)
        val view = binding.root

        fireStoreDatabase.collection("runningWorkouts").document(args.exercId).get().addOnCompleteListener{
            if(it.isSuccessful){
                binding.distance.setText(it.result?.data?.getValue("distance").toString())
                binding.workoutLength.setText(it.result?.data?.getValue("time").toString())
            }

        }


        binding.deleteButton.setOnClickListener {


            fireStoreDatabase.collection("runningWorkouts").document(args.exercId).delete()

            val action = EditRunningFragmentDirections.actionEditRunningFragmentToWorkoutListFragment(args.userId)

            if (action != null) {
                this.findNavController().navigate(action)
            }

        }

        binding.editButton.setOnClickListener {

            val basketballWorkout : MutableMap<String, Any> = HashMap()

            var time : Int = binding.workoutLength.text.toString().toInt()
            var distance : Double = binding.distance.text.toString().toDouble()
//            var points : Int = binding.points.text.toString().toInt()
//            var assists : Int = binding.assists.text.toString().toInt()
//            var rebounds : Int = binding.rebounds.text.toString().toInt()

            //basketballWorkout["date"] = dt
            //basketballWorkout["assists"] = assists
            basketballWorkout["distance"] = distance
//            basketballWorkout["points"] = points
//            basketballWorkout["rebounds"] = rebounds
            basketballWorkout["time"] = time

            fireStoreDatabase.collection("runningWorkouts").document(args.exercId).update(basketballWorkout)
                .addOnSuccessListener {
                    val action = EditRunningFragmentDirections.actionEditRunningFragmentToWorkoutListFragment(args.userId)

                    if (action != null){
                        this.findNavController().navigate(action)
                    }
                }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditRunningFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditRunningFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}