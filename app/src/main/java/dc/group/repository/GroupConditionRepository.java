package dc.group.repository;

import dc.group.model.GroupCondition;
import dc.group.model.GroupConditionKey;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupConditionRepository extends MongoRepository<GroupCondition, GroupConditionKey> {
    @Override
    @CachePut("groups")
    <S extends GroupCondition> S save(S entity);
}
