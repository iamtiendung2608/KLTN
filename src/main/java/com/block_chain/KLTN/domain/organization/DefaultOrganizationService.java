package com.block_chain.KLTN.domain.organization;

import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletType;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultOrganizationService implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional
    public void createOrganization(CreateOrganizationRequest request) {
        UserEntity user = userRepository.findById(request.id())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        if (user.getOrganizationId() != null) {
            Optional<OrganizationEntity> optOrganization = organizationRepository.findById(user.getOrganizationId());
            if (optOrganization.isPresent()) {
                throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "Organization");
            }
        }

        OrganizationEntity organization = organizationMapper.toEntity(request);
        organization = organizationRepository.save(organization);
        applicationEventPublisher.publishEvent(new CreateWalletEvent(organization.getId(), WalletType.ORGANIZATION));
        user.setOrganizationId(organization.getId());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void editOrganization(Long id, UpdateOrganizationRequest request) {
        OrganizationEntity organization = organizationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization"));

        organization.setName(request.name());
        organization.setDescription(request.description());
        organization.setCategory(request.category());
        organization.setScope(request.scope());
        organization.setScale(request.scale());
        organizationRepository.save(organization);
    }
}
