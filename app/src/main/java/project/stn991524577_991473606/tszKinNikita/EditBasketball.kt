package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentAddBasketballBinding
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentEditBasketballBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditBasketball.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditBasketball : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val args: EditBasketballArgs by navArgs()
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _binding: FragmentEditBasketballBinding  ? = null

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

        //_binding = FragmentAddBasketballBinding.inflate(inflater, container, false)

        _binding = FragmentEditBasketballBinding.inflate(inflater, container, false)
        val view = binding.root

        System.out.println("EX ID: " + args.exercId)

        fireStoreDatabase.collection("basketballWorkouts").document(args.exercId).get().addOnCompleteListener{
            if(it.isSuccessful){
                binding.assists.setText(it.result?.data?.getValue("assists").toString())
                binding.distance.setText(it.result?.data?.getValue("distance").toString())
                binding.points.setText(it.result?.data?.getValue("points").toString())
                binding.rebounds.setText(it.result?.data?.getValue("rebounds").toString())
                binding.workoutLength.setText(it.result?.data?.getValue("time").toString())
            }

        }


        binding.deleteButton.setOnClickListener {


            fireStoreDatabase.collection("basketballWorkouts").document(args.exercId).delete()

            val action = EditBasketballDirections.actionEditBasketballToWorkoutListFragment(args.userId)

            if (action != null) {
                this.findNavController().navigate(action)
            }

        }

        binding.editButtton.setOnClickListener {

            val basketballWorkout : MutableMap<String, Any> = HashMap()

            var time : Int = binding.workoutLength.text.toString().toInt()
            var distance : Double = binding.distance.text.toString().toDouble()
            var points : Int = binding.points.text.toString().toInt()
            var assists : Int = binding.assists.text.toString().toInt()
            var rebounds : Int = binding.rebounds.text.toString().toInt()

            //basketballWorkout["date"] = dt
            basketballWorkout["assists"] = assists
            basketballWorkout["distance"] = distance
            basketballWorkout["points"] = points
            basketballWorkout["rebounds"] = rebounds
            basketballWorkout["time"] = time

            fireStoreDatabase.collection("basketballWorkouts").document(args.exercId).update(basketballWorkout)
                .addOnSuccessListener {
                    val action = EditBasketballDirections.actionEditBasketballToWorkoutListFragment(args.userId)

                    if (action != null){
                        this.findNavController().navigate(action)
                    }
                }
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditBasketball.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditBasketball().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}