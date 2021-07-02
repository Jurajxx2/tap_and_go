package com.example.tapandgo.screens.information

import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentInformationBinding
import com.example.tapandgo.databinding.FragmentPickerLocationBinding
import com.example.tapandgo.screens.picker_location.PickerLocationHandler
import com.example.tapandgo.screens.picker_location.PickerLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment: BaseFragment<InformationViewModel, FragmentInformationBinding, InformationHandler>() {
    override val layout = R.layout.fragment_information
    override val handler by lazy { requireActivity() as InformationHandler }
    override val viewModel: InformationViewModel by viewModel()

    override fun setup() {

        binding.content.text = "Lorem ipsum dolor amet artisan tacos copper mug man braid brunch raclette craft beer, vice cray fixie offal fam iceland fingerstache.\n\n" +
                "Pour-over PBR&B hot chicken cray photo booth letterpress shabby chic hell of thundercats prism biodiesel. Pitchfork banh mi tofu distillery meh bespoke +1 taiyaki enamel pin.\n\n" +
                "Migas live-edge put a bird on it meh lo-fi plaid direct trade. Pork belly blog man bun williamsburg, austin health goth meditation seitan distillery biodiesel viral YOLO pitchfork tacos. Tofu craft beer franzen quinoa, brunch literally bespoke." +
                "Hell of banjo locavore swag. Pork belly jianbing migas quinoa mustache. Gastropub cray brooklyn food truck seitan coloring book small batch chambray cardigan PBR&B ethical helvetica."

    }
}