package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentAddCyclingBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddCyclingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCyclingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val args: WorkoutListFragmentArgs by navArgs()
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _binding: FragmentAddCyclingBinding? = null
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
        _binding = FragmentAddCyclingBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.addButton.setOnClickListener {
            var action = AddCyclingFragmentDirections.actionAddCyclingFragmentToWorkoutListFragment(args.userId)

            val c = FirebaseFirestore.getInstance()
            var date : String = binding.date.text.toString()

            System.out.println(date)
            //val l = LocalDate.parse("2018-02-14 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val l = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date)
            //val dt = Timestamp(l.year, l.monthValue, l.dayOfMonth, 12, 30, 0, 0)
            //val dt = Timestamp()
            //val dt = Timestamp(Date(l)

            val dt = Timestamp(l.year, l.month, l.day, l.hours, l.minutes, 0, 0)

            System.out.println("Year: " + l.year)
            System.out.println("MOnth: " + l.month)
            System.out.println("Dat: " + l.day)
            System.out.println("hour: " + l.hours)
            System.out.println("mins: " + l.minutes)
            System.out.println(dt.toString())
            var time : Int = binding.workoutLength.text.toString().toInt()
            var distance : Int = binding.distance.text.toString().toInt()

            val cyclingWorkout : MutableMap<String, Any> = HashMap()

            cyclingWorkout["date"] = date
            cyclingWorkout["distance"] = distance
            cyclingWorkout["time"] = time
            cyclingWorkout["userId"] = fireStoreDatabase.document("/users/" + args.userId)

            if (date.isNullOrEmpty() || distance.equals(null)  || time.equals(null)){
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("")
                builder.setMessage("Please fill in all fielfs to proceed with creating workout record.")
                builder.setNeutralButton("OK") { dialog, which ->

                }
                builder.show()
            } else {
                fireStoreDatabase.collection("cyclingWorkouts")
                    .add(cyclingWorkout)
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
         * @return A new instance of fragment AddCyclingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCyclingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}