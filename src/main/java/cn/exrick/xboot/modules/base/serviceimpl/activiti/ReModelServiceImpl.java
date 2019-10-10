package cn.exrick.xboot.modules.base.serviceimpl.activiti;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.entity.activiti.ReModel;
import cn.exrick.xboot.modules.base.service.activiti.ReModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模型接口实现
 */
@Slf4j
/*@Service
@Transactional*/
public class ReModelServiceImpl implements ReModelService {

    /*@Autowired
    private ReModelDao reModelDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ReModel findByModelName(String name) {
        List<ReModel> lR = jdbcTemplate.queryForList("select * from act_re_model rm where rm.name_ = ?",new Object[]{name},ReModel.class);
        if (lR != null && lR.size() > 0) {
            return lR.get(0);
        }
        return null;
    }

    @Override
    public Integer saveReModel(ReModel reModel) {
        return jdbcTemplate.update("insert into act_re_model (ID_,NAME_,KEY_,META_INFO_) values(?,?,?,?)",new Object[]{reModel.getModelId(),reModel.getName(),reModel.getIdentifier(),reModel.getMetaInfo()});
    }

    @Override
    public List<ReModel> findByModelList() {
        return jdbcTemplate.query("select ar.NAME_ as name,ar.key_ as identifier,ar.META_INFO_ as metaInfo from act_re_model ar",new BeanPropertyRowMapper<>(ReModel.class));
    }*/
}
