package dc.group.repository;

import dc.ProviderId;
import dc.group.model.Group;
import dc.group.model.GroupKey;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, GroupKey> {
    @Cacheable("groups")
    List<Group> findByKey_ProviderId(ProviderId providerId);

    @Override
    @CachePut("groups")
    <S extends Group> S save(S entity);
}
