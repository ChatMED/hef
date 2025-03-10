package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Version;
import io.chatmed.evaluation_platform.repository.VersionRepository;
import io.chatmed.evaluation_platform.service.domain.VersionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;

    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public Optional<Version> save(Version version) {
        return Optional.of(versionRepository.save(version));
    }

    @Override
    public Optional<Version> getLatestVersion() {
        return versionRepository.findAll().stream().max(Comparator.comparingLong(Version::getId));
    }
}
