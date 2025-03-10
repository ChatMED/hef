package io.chatmed.evaluation_platform.repository;

import io.chatmed.evaluation_platform.domain.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {
}
