package com.example.maystech.ui.product_details;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class ProductDetailViewModel extends ViewModel {
    public ObservableField<String> name;
    public ObservableField<Double> price;
    public ObservableField<String> description;


}
