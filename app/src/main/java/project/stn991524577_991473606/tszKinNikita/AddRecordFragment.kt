package project.stn991524577_991473606.tszKinNikita

import android.app.LauncherActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import project.stn991524577_991473606.tszKinNikita.databinding.FragmentAddRecordBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val args: WorkoutListFragmentArgs by navArgs()
    private var _binding: FragmentAddRecordBinding? = null
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarAddRecord)

        binding.spinnerWorkoutType.adapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.workout_types))
        binding.btnNavigateToForm.setOnClickListener{
            var selectedWorkout = binding.spinnerWorkoutType.selectedItem.toString()
            var action: NavDirections? = null
            System.out.println("ARG: " + args.userId)
            when (selectedWorkout) {
                "Basketball" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddBasketballFragment(args.userId)
                "Running" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddRunningFragment()
                "Swimming" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddSwimmingFragment()
                "Climbing" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddClimbingFragment()
                "Free weights" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddFreeweightsFragment()
                "Cycling" -> action = AddRecordFragmentDirections.actionAddRecordFragmentToAddCyclingFragment()
            }

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
         * @return A new instance of fragment AddRecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}