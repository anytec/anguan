package cn.anytec.anguan.service;

import cn.anytec.anguan.po.Camera;
import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.repository.CameraRepository;
import cn.anytec.anguan.service.inf.ICamera;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CameraService implements ICamera {

    @Autowired
    private CameraRepository repository;

    @Override
    public Page findAll(Integer pageNum, Integer pageSize, String macAddress, String pushIp) {

        pageNum = pageNum > 0 ? pageNum - 1 : pageNum;

        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            ArrayList<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(macAddress)) {
                predicates.add(criteriaBuilder.like(root.get("macAddress"), "%" + macAddress + "%"));
            }
            if (StrUtil.isNotBlank(pushIp)) {
                // criteriaBuilder.greaterThan...方法
                // like模糊查询
                predicates.add(criteriaBuilder.like(root.get("pushIp"), "%" + pushIp + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return repository.findAll(specification, PageRequest.of(pageNum, pageSize));
    }

    @Override
    @Cacheable(value = "camera", key = "#id")
    public Optional<Camera> findOne(Integer id) {

        return repository.findById(id);
    }

    @Override
    public Camera save(Camera camera) {
        Assert.isTrue(camera != null, "不能保存, 设备信息为空");
        camera.setId(null);
        String macAddress = camera.getMacAddress();

        if (StrUtil.isNotBlank(macAddress)) {
            Camera byMacAddress = this.repository.findByMacAddress(macAddress);
            if (null == byMacAddress) {
                return repository.save(camera);
            }else {
                throw new AnguanException("MacAddress 已存在");
            }
        }
        return null;
    }

    @Override
    @CachePut(value = "camera", key = "#p0.id")
    public Camera update(Camera camera) {
        return repository.save(camera);
    }

    @Override
    @CacheEvict(value = "camera", key = "#id")
    public void delete(Integer id) throws EmptyResultDataAccessException {
        repository.deleteById(id);
    }
}
