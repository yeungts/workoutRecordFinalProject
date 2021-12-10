package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentWorkoutListBinding
import project.stn991524577_991473606.tszKinNikita.models.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkoutListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkoutListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val args: WorkoutListFragmentArgs by navArgs()

    val adapter = WorkoutListAdapter {
        var action: NavDirections? = null
        if (it.getSportName().equals("Basketball")) {
            Log.i("WorkoutApp", "Basketball item clicked")

            System.out.println(it.id)
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToEditBasketball(args.userId, it.id)

            if (action != null) {
            this.findNavController().navigate(action)
            }

        } else if (it.getSportName().equals("Climbing")) {
            Log.i("WorkoutApp", "Climbing item clicked")
        } else if (it.getSportName().equals("Cycling")) {
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToEditCyclingFragment(args.userId, it.id)

            if (action != null) {
                this.findNavController().navigate(action)
            }
        } else if (it.getSportName().equals("Free Weight")) {
            Log.i("WorkoutApp", "Free Weight item clicked")
        } else if (it.getSportName().equals("Running")) {
            Log.i("WorkoutApp", "Running item clicked")
        } else if (it.getSportName().equals("Swimming")) {
            Log.i("WorkoutApp", "Swimming item clicked")
        }

//        action = WorkoutListFragmentDirections.actionWorkoutListFragmentToRecordDetailFragment()
//
//        if (action != null) {
//            this.findNavController().navigate(action)
//        }
    }

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var testList: MutableList<Workout> = mutableListOf<Workout>();

    private var _binding: FragmentWorkoutListBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val userId = args.userId
        System.out.println("Received userID arg " + userId)

        fireStoreDatabase.collection("basketballWorkouts")
            .whereEqualTo("userId", fireStoreDatabase.document("/users/" + args.userId))
            .get()
            .addOnCompleteListener{

                if (it.isSuccessful){
                    for(document in it.result!!){
                        val timestamp = document.data.getValue("date") as com.google.firebase.Timestamp
                        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        val sdf = SimpleDateFormat("MM/dd/yyyy")
                        val netDate = Date(milliseconds)
                        val date = sdf.format(netDate).toString()

                        testList.add(BasketballWorkout(document.id, document.data.getValue("userId").toString(), date, document.data.getValue("distance").toString(),
                            document.data.getValue("points").toString().toInt(), document.data.getValue("assists").toString().toInt(), document.data.getValue("rebounds").toString().toInt()))
                    }

                    adapter.submitList(testList)
                    adapter.notifyDataSetChanged()

                }
            }

        fireStoreDatabase.collection("cyclingWorkouts")
            .whereEqualTo("userId",  fireStoreDatabase.document("/users/" + args.userId))
            .get()
            .addOnCompleteListener{

                if (it.isSuccessful){
                    for(document in it.result!!){
                        val timestamp = document.data.getValue("date") as com.google.firebase.Timestamp
                        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        val sdf = SimpleDateFormat("MM/dd/yyyy")
                        val netDate = Date(milliseconds)
                        val date = sdf.format(netDate).toString()

                        testList.add(CyclingWorkout(document.id, document.data.getValue("userId").toString(), date, document.data.getValue("time").toString(), document.data.getValue("distance").toString()))
                    }

                    adapter.submitList(testList)
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarWorkoutList)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.layoutManager = LinearLayoutManager(this.context)
        binding.recycleView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add) {
            Toast.makeText(activity, "add clicked", Toast.LENGTH_LONG).show()
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToAddRecordFragment(args.userId)
            this.findNavController().navigate(action)
            return true
        } else if (id == R.id.action_about) {
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToAboutFragment()
            this.findNavController().navigate(action)
            return true
        } else if (id ==  R.id.action_help){
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToHelpFragment()
            this.findNavController().navigate(action)
            return true
        }
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkoutListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkoutListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}