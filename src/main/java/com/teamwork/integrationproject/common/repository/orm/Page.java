package com.teamwork.integrationproject.common.repository.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
@AllArgsConstructor
public class Page<M>
{
    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<M> dataList;

    public <T> List<T> toDto(Class<T> clazz)
    {
        if (dataList == null || dataList.size() < 1)
        {
            return Collections.emptyList();
        }

        Constructor<T> constructor;
        try
        {
            constructor = clazz.getConstructor(null);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(clazz + " must have a no args constructor!", e);
        }

        List<T> result = new ArrayList<>();
        for (M m : dataList)
        {
            T t;
            try
            {
                t = constructor.newInstance(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException("默认创建对象异常！", e);
            }

            BeanUtils.copyProperties(m, t);
            result.add(t);
        }

        return result;
    }
}
