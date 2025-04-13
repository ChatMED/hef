package io.chatmed.evaluation_platform.service.domain;

import io.chatmed.evaluation_platform.domain.Membership;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.domain.Workspace;

import java.util.Optional;

public interface MembershipService {

    Optional<Membership> findMembership(User user, Workspace workspace);

    Membership save(User user, Workspace workspace, Question question);

    Membership updateCurrentAndNextQuestion(Membership membership, Question question);

    Membership updateCurrent(Membership membership, Question question);
}
