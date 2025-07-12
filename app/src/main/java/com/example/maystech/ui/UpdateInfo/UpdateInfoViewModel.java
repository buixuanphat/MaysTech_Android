package com.example.maystech.ui.UpdateInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.GhnApiResponse;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.Ward;
import com.example.maystech.data.repository.GhnRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInfoViewModel extends ViewModel {

    private GhnRepository ghnRepository;
    private MutableLiveData<List<Province>> provinces;
    private MutableLiveData<List<District>> districts;
    private MutableLiveData<List<Ward>> wards;

    public LiveData<List<Province>> getProvinces()
    {
        return provinces;
    }

    public LiveData<List<District>> getDistricts()
    {
        return districts;
    }

    public LiveData<List<Ward>> getWards()
    {
        return wards;
    }
    public UpdateInfoViewModel() {
        ghnRepository = new GhnRepository();
        provinces = new MutableLiveData<>();
        districts = new MutableLiveData<>();
        wards = new MutableLiveData<>();
    }

    public void fetchProvincesList()
    {
        ghnRepository.getProvince(new Callback<GhnApiResponse<Province>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<Province>> call, Response<GhnApiResponse<Province>> response) {
                if(response.isSuccessful())
                {
                    List<Province> list = new LinkedList<>();
                    response.body().getData().forEach(p -> {
                        Province newProvince = new Province();
                        newProvince.setId(p.getId());
                        newProvince.setName(p.getName());
                        list.add(newProvince);
                    });
                    provinces.setValue(list);


                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<Province>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void fetchDistrict(int provinceId)
    {
        ghnRepository.getDistrict(provinceId, new Callback<GhnApiResponse<District>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<District>> call, Response<GhnApiResponse<District>> response) {
                if(response.isSuccessful())
                {
                    List<District> list = new LinkedList<>();
                    response.body().getData().forEach(d -> {
                        District newDistrict = new District();
                        newDistrict.setId(d.getId());
                        newDistrict.setName(d.getName());
                        list.add(newDistrict);
                    });
                    districts.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<District>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
    public void fetchWard(int districtId)
    {
        ghnRepository.getWard(districtId, new Callback<GhnApiResponse<Ward>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<Ward>> call, Response<GhnApiResponse<Ward>> response) {
                if(response.isSuccessful())
                {
                    List<Ward> list = new LinkedList<>();
                    response.body().getData().forEach( w-> {
                        Ward newWard = new Ward();
                        newWard.setId(w.getId());
                        newWard.setName(w.getName());
                        list.add(newWard);
                    });
                    wards.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<Ward>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

}
