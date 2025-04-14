package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Membership;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Optional<Membership> findByUserAndWorkspace(User user, Workspace workspace);
}
