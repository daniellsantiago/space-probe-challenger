package com.elo7.spaceprobe.application.usecases;

import com.elo7.spaceprobe.application.dto.*;
import com.elo7.spaceprobe.domain.entities.SpaceProbe;
import com.elo7.spaceprobe.domain.exception.BusinessException;
import com.elo7.spaceprobe.domain.repository.SpaceProbeRepository;
import com.elo7.spaceprobe.lib.ApplicationService;
import com.elo7.spaceprobe.lib.exception.DomainException;
import com.elo7.spaceprobe.lib.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
public class MoveSpaceProbeUseCase implements ApplicationService<ChangeSpaceProbePositionDto, SpaceProbePositionDto> {
    private final SpaceProbeRepository spaceProbeRepository;

    public MoveSpaceProbeUseCase(SpaceProbeRepository spaceProbeRepository) {
        this.spaceProbeRepository = spaceProbeRepository;
    }

    @Override
    @Transactional
    public SpaceProbePositionDto execute(ChangeSpaceProbePositionDto payload) {
        SpaceProbe spaceProbe = spaceProbeRepository.findById(payload.getSpaceProbeId()).orElseThrow(
            () -> new NotFoundException("SpaceProbe " + payload.getSpaceProbeId() + " not found")
        );
        payload.getCommands().forEach(command -> performCommand(spaceProbe, command));
        spaceProbeRepository.save(spaceProbe);
        return new SpaceProbePositionDto(
            spaceProbe.getCoordinates().getX(),
            spaceProbe.getCoordinates().getY(),
            spaceProbe.getDirection()
        );
    }

    private void performCommand(SpaceProbe spaceProbe, SpaceProbeCommandDto command) {
        if (command instanceof MoveCommandDto) {
            forwardMovement(spaceProbe);
        } else if (command instanceof RotateCommandDto){
            rotationMovement(spaceProbe, (RotateCommandDto) command);
        }
    }

    private void forwardMovement(SpaceProbe spaceProbe) {
        try {
            spaceProbe.move(spaceProbe.getDirection());
        } catch (BusinessException ex) {
            throw new DomainException(ex.getMessage());
        }
    }

    private void rotationMovement(SpaceProbe spaceProbe, RotateCommandDto command) {
        switch (command) {
            case L:
                spaceProbe.rotateCounterClockwise();
                break;
            case R:
                spaceProbe.rotateClockwise();
                break;
            default:
                throw new ValidationException("Unknown Command provided");
        }
    }
}
