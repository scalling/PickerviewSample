package sample.pickerview.zm.com.pickerviewsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sample.pickerview.zm.com.pickerviewsample.bean.CityBean;
import sample.pickerview.zm.com.pickerviewsample.bean.ProvinceBean;

/**
 * @TODO<解析省市区数据示例>
 * @author 小嵩
 * @date 2017-3-16
 */
public class JsonDataActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data);
        tvData  =(TextView)findViewById(R.id.tv_data);
        initView();
    }
    private void initView() {

        findViewById(R.id.btn_data).setOnClickListener(this);
        findViewById(R.id.btn_show).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_data:
                ShowPickerView(false);
                break;
            case R.id.btn_show:
                ShowPickerView(true);

                break;

        }

    }

//城市

    private List<ProvinceBean> provinces = new ArrayList<>();
    private List<List<String>> citys = new ArrayList<>();
    private List<List<List<String>>> areas = new ArrayList<>();
    private void ShowPickerView(boolean flag) {// 弹出选择器
        if(provinces==null||provinces.size()==0){
            try {
                initJsonData();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"加载失败",Toast.LENGTH_LONG).show();
                return;
            }
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = provinces.get(options1).getPickerViewText();
                String city = citys.get(options1).get(options2);
                String area =areas.get(options1).get(options2).get(options3);
                StringBuffer buffer = new StringBuffer();
                buffer.append(province);
                if(!TextUtils.isEmpty(city)){
                    buffer.append("-");
                    buffer.append(city);
                }
                if(!TextUtils.isEmpty(area)){
                    buffer.append("-");
                    buffer.append(area);
                }
                tvData.setText(buffer.toString());
            }
        })
                .setTitleText("城市选择")
                .setChange(flag)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(provinces, citys,areas);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() throws IOException{//解析数据
        InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("assets/province.json");
        String JsonData = IOUtils.toString(inputStream);
        provinces =Json.fromJson(JsonData, new TypeReference<List<ProvinceBean>>() {});
        for(ProvinceBean province:provinces){
            List<String> cityItem = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> areaItem = new ArrayList<>();//该省的所有地区列表（第三极）
            for(CityBean city:province.getCity()){
                cityItem.add(city.getName());//添加城市
                areaItem.add(city.getArea());
            }
            citys.add(cityItem);
            areas.add(areaItem);
        }

    }
}
