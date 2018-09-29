package cn.anytec.anguan.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/26 11:24
 * DAO基础类
 */
@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable> extends PagingAndSortingRepository<T, I>, JpaSpecificationExecutor<T> {
}
