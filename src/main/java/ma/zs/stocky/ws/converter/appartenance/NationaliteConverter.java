package  ma.zs.stocky.ws.converter.appartenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zs.stocky.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zs.stocky.zynerator.util.StringUtil;
import ma.zs.stocky.zynerator.converter.AbstractConverter;
import ma.zs.stocky.zynerator.util.DateUtil;
import ma.zs.stocky.bean.core.appartenance.Nationalite;
import ma.zs.stocky.ws.dto.appartenance.NationaliteDto;

@Component
public class NationaliteConverter {


    public  NationaliteConverter() {
    }


    public Nationalite toItem(NationaliteDto dto) {
        if (dto == null) {
            return null;
        } else {
        Nationalite item = new Nationalite();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());



        return item;
        }
    }


    public NationaliteDto toDto(Nationalite item) {
        if (item == null) {
            return null;
        } else {
            NationaliteDto dto = new NationaliteDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());


        return dto;
        }
    }


	
    public List<Nationalite> toItem(List<NationaliteDto> dtos) {
        List<Nationalite> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (NationaliteDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<NationaliteDto> toDto(List<Nationalite> items) {
        List<NationaliteDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Nationalite item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(NationaliteDto dto, Nationalite t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Nationalite> copy(List<NationaliteDto> dtos) {
        List<Nationalite> result = new ArrayList<>();
        if (dtos != null) {
            for (NationaliteDto dto : dtos) {
                Nationalite instance = new Nationalite();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
