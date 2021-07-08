package com.example.tapandgo.screens.information

import android.widget.TextView
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentInformationBinding
import com.example.tapandgo.screens.car_list.CarListFragmentDirections
import com.example.tapandgo.screens.rides_history.RidesHistoryFragmentDirections
import com.example.tapandgo.utils.getSendSupportEmailIntent
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment: BaseFragment<InformationViewModel, FragmentInformationBinding, InformationHandler>() {
    override val layout = R.layout.fragment_information
    override val handler by lazy { requireActivity() as InformationHandler }
    override val viewModel: InformationViewModel by viewModel()

    override fun setup() {
        setNavigationViewListener()

        binding.content.text = "Lorem ipsum dolor amet artisan tacos copper mug man braid brunch raclette craft beer, vice cray fixie offal fam iceland fingerstache.\n\n" +
                "Pour-over PBR&B hot chicken cray photo booth letterpress shabby chic hell of thundercats prism biodiesel. Pitchfork banh mi tofu distillery meh bespoke +1 taiyaki enamel pin.\n\n" +
                "Migas live-edge put a bird on it meh lo-fi plaid direct trade. Pork belly blog man bun williamsburg, austin health goth meditation seitan distillery biodiesel viral YOLO pitchfork tacos. Tofu craft beer franzen quinoa, brunch literally bespoke." +
                "Hell of banjo locavore swag. Pork belly jianbing migas quinoa mustache. Gastropub cray brooklyn food truck seitan coloring book small batch chambray cardigan PBR&B ethical helvetica."

    }

    private fun setNavigationViewListener() {
        binding.nvView.menu.getItem(3).isChecked = true
        binding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.car_rental -> {
                    handler.navigate(InformationFragmentDirections.openCarList())
                }
                R.id.history_rides -> {
                    handler.navigate(InformationFragmentDirections.openRidesHistory())
                }
                R.id.email_us -> {
                    val intent = getSendSupportEmailIntent()
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        requireActivity().startActivity(intent)
                    }
                }
                R.id.information -> {} //already here
                R.id.logout -> {
                    handler.navigate(InformationFragmentDirections.openLogout())
                }
            }
            binding.drawerLayout.close()
            true
        }

        val name = binding.nvView.getHeaderView(0).findViewById<TextView>(R.id.profile_name)
        val email = binding.nvView.getHeaderView(0).findViewById<TextView>(R.id.profile_email)
        //val photo = binding.nvView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image)

        viewModel.user.observe(this, {
            name.text = "${it?.firstName}'s account"
            email.text = it?.email
        })

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}