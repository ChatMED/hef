package io.chatmed.evaluation_platform.service.domain.impl;

import io.chatmed.evaluation_platform.domain.Membership;
import io.chatmed.evaluation_platform.domain.Question;
import io.chatmed.evaluation_platform.domain.User;
import io.chatmed.evaluation_platform.domain.Workspace;
import io.chatmed.evaluation_platform.repository.MembershipRepository;
import io.chatmed.evaluation_platform.service.domain.MembershipService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }


    @Override
    public Optional<Membership> findMembership(User user, Workspace workspace) {
        return membershipRepository.findByUserAndWorkspace(user, workspace);
    }

    @Override
    public Membership save(User user, Workspace workspace, Question question) {
        return membershipRepository.save(Membership.builder()
                                                   .user(user)
                                                   .workspace(workspace)
                                                   .currentQuestion(question)
                                                   .nextQuestion(question)
                                                   .build());
    }

    @Override
    public Membership updateCurrentAndNextQuestion(Membership membership, Question question) {
        membership.setCurrentQuestion(question);
        membership.setNextQuestion(question);
        return membershipRepository.save(membership);
    }

    @Override
    public Membership updateCurrent(Membership membership, Question question) {
        membership.setCurrentQuestion(question);
        return membershipRepository.save(membership);
    }
}
