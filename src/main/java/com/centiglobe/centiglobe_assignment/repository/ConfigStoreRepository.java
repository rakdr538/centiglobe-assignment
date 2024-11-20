package com.centiglobe.centiglobe_assignment.repository;

import com.centiglobe.centiglobe_assignment.model.entities.ConfigStore;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(itemResourceRel = "configStore", path="configs")
public interface ConfigStoreRepository extends JpaRepository<ConfigStore,Long> {

    List<ConfigStore> findByMemberName(@Pattern(regexp = "^[a-z0-9]+([-\\.][a-z0-9]+)*$",
            message = "Member name does not match required pattern")
                                       @Param("memberName") String memberName);
}