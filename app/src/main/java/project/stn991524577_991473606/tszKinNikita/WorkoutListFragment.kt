package project.stn991524577_991473606.tszKinNikita

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentWorkoutListBinding
import project.stn991524577_991473606.tszKinNikita.models.BasketballWorkout
import project.stn991524577_991473606.tszKinNikita.models.ClimbingWorkout
import project.stn991524577_991473606.tszKinNikita.models.FreeWeightWorkout
import project.stn991524577_991473606.tszKinNikita.models.Workout
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

        testList.add(BasketballWorkout("1", "1", "02-15-2016", "20", 40, 5, 20))
        testList.add(ClimbingWorkout("2", "1", "02-16-2016", "40", 5000))
        testList.add(FreeWeightWorkout("3", "1", "02-17-2016", "45"))
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

        val adapter = WorkoutListAdapter {
            Log.i("WorkoutApp", "item clicked")
        }
        binding.recycleView.layoutManager = LinearLayoutManager(this.context)
        binding.recycleView.adapter = adapter

        // add records that should be displayed
        adapter.submitList(testList)
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
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToAddRecordFragment()
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