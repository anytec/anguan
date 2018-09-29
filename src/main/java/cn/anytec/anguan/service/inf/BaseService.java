package cn.anytec.anguan.service.inf;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by imyzt on 2018/9/27 17:35
 */
public interface BaseService<I, T extends Serializable, Q, P> {

    void del(I id);

    T edit(T data);

    T update(T data);

    T save(T data);

    T findById(I id);

    P list(Integer pageNum, Integer pageSize, Q query);

    /**
     * 从对象中取出不为空的字段
     * @param target 目标对象
     * @return 不为空的字段名
     */
    default String[] getNoNullProperties(T target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) {
                noEmptyName.add(p.getName());
            }
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }

    /**
     * 将目标源中不为空的字段过滤，将数据库中查出的数据源复制到提交的目标源中
     *
     * @param source 用id从数据库中查出来的数据源
     * @param target 提交的实体，目标源
     */
    default void copyNullProperties(T source, T target) {
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));
    }
}
