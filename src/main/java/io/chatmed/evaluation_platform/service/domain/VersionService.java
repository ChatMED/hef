package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Version;

import java.util.Optional;

public interface VersionService {

    Optional<Version> save(Version version);

    Optional<Version> getLatestVersion();
}
