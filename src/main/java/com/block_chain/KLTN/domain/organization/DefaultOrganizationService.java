package com.block_chain.KLTN.domain.organization;

import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultOrganizationService implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserRepository userRepository;

    public DefaultOrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.userRepository = userRepository;
    }


    @Override
    public void createOrganization(CreateOrganizationRequest request) {
        UserEntity user = userRepository.findById(request.id()).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        Optional<OrganizationEntity> optOrganization = organizationRepository.findById(user.getOrganizationId());
        if (optOrganization.isPresent()) {
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "Organization");
        }

        OrganizationEntity organization = organizationMapper.toEntity(request);
        organizationRepository.save(organization);
        user.setOrganizationId(organization.getId());
        userRepository.save(user);
    }
}
