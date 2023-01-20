package ru.mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.mobile.R
import ru.mobile.activities.BaseActivity
import ru.mobile.activities.ContentActivity
/**
 * A simple [Fragment] subclass.
 * Use the [BaseMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BaseMenuFragment : Fragment() {

    private var uploadButton: Button? = null
    private var menuButton: ImageButton? = null
    private var mainActivity: AppCompatActivity? = null
    private var bottomMenu: FrameLayout? = null
    private var translatingDistances = emptyList<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //arguments?.let {
        //    param1 = it.getString(ARG_PARAM1)
        //}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View =  inflater.inflate(R.layout.fragment_base_menu, container, false)

        val activity = activity
        if (activity is AppCompatActivity) {
           mainActivity = activity
        }


        bottomMenu = view.findViewById(R.id.bottom_panel_menu)
        bottomMenu!!.visibility = if ((activity is ContentActivity) ) {
           // activateContentButtons(view, activity)
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        menuButton = view.findViewById(R.id.main_button)

        translatingDistances = listOf(
            resources.getDimension(R.dimen.fab_3_ejection),
            resources.getDimension(R.dimen.fab_2_ejection),
            resources.getDimension(R.dimen.fab_1_ejection),
            resources.getDimension(R.dimen.fab_4_ejection),
            resources.getDimension(R.dimen.fab_5_ejection),
            resources.getDimension(R.dimen.fab_6_ejection),
            resources.getDimension(R.dimen.fab_7_ejection),
        )

        if (mainActivity != null) {
            menuButton!!.setOnClickListener(
                viewClickListener
            )
        }

        return view
    }

    var viewClickListener = View.OnClickListener { v ->
        BaseActivity.showPopupMenu(
            context = mainActivity!!,
            view = v,
            inflater = mainActivity!!.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            translatingDistances = translatingDistances
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment BaseMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            BaseMenuFragment().apply {
                arguments = Bundle().apply {
                   // putString(ARG_PARAM1, param1)
                }
            }
    }
}