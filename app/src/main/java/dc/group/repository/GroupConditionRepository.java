package dc.group.repository;

import dc.group.model.GroupCondition;
import dc.group.model.GroupConditionKey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupConditionRepository extends MongoRepository<GroupCondition, GroupConditionKey> {
}
