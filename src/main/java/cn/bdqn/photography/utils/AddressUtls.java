package cn.bdqn.photography.utils;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.common.mapper.ShootCityMapper;
import cn.bdqn.photography.common.mapper.ShootCountryMapper;
import cn.bdqn.photography.common.mapper.ShootProwMapper;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.mapper.ShootAddressMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component  //放入spring容器创建实例
public class AddressUtls {

    @Autowired
    @Qualifier("shootProwMapper")
    private ShootProwMapper shootProwMapper;

    @Autowired
    @Qualifier("shootCityMapper")
    private ShootCityMapper shootCityMapper;

    @Autowired
    @Qualifier("shootCountryMapper")
    private ShootCountryMapper shootCountryMapper;

    @Autowired
    //@Qualifier("shootAddressMapper")
    private ShootAddressMapper shootAddressMapper;

    /**
     * 添加地址工具方法
     * @param prow
     * @param city
     * @param country
     * @return
     */
    public Long address(ShootProw prow, ShootCity city, ShootCountry country){
        ShootAddress address=new ShootAddress();
        System.out.println("prow:"+prow.getProw());
        QueryWrapper<ShootProw> queryProw=new QueryWrapper<>(); //sql对象
        queryProw.eq("prow",prow.getProw());  //条件
        ShootProw shootProw=shootProwMapper.selectOne(queryProw);
        QueryWrapper<ShootCity> queryCity=new QueryWrapper<>();
        queryCity.eq("city",city.getCity());
        ShootCity shootCity=shootCityMapper.selectOne(queryCity);
        QueryWrapper<ShootCountry> queryCountry=new QueryWrapper<>();
        queryCountry.eq("country",country.getCountry());
        ShootCountry shootCountry=shootCountryMapper.selectList(queryCountry).get(0);
        Long prowId=shootProw.getId();
        Long cityId=shootCity.getId();
        Long countryId=shootCountry.getId();
        QueryWrapper<ShootAddress> queryAddress=new QueryWrapper<>();
        queryAddress.eq("prow_id",prowId);
        queryAddress.eq("city_id",cityId);
        queryAddress.eq("country_id",countryId);
        ShootAddress shootAddress=shootAddressMapper.selectOne(queryAddress);
        if(shootAddress!=null){  //说明这个信息有过人用并且一致
            return shootAddress.getId();
        }else {  //说明没有人用则需要添加一天新的数据
            address.setProwId(prowId);
            address.setCityId(cityId);
            address.setCountryId(countryId);
            shootAddressMapper.insertAddress(address);  //插入数据到address并回写获得新增id
            return address.getId();
        }
    }

}
