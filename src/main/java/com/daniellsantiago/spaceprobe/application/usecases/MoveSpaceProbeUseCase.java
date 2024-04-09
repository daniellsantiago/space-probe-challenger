package com.daniellsantiago.spaceprobe.application.usecases;

import com.daniellsantiago.spaceprobe.application.dto.ChangeSpaceProbePositionDto;
import com.daniellsantiago.spaceprobe.application.dto.MoveCommandDto;
import com.daniellsantiago.spaceprobe.application.dto.RotateCommandDto;
import com.daniellsantiago.spaceprobe.application.dto.SpaceProbeCommandDto;
import com.daniellsantiago.spaceprobe.application.dto.SpaceProbePositionDto;
import com.daniellsantiago.spaceprobe.domain.entities.SpaceProbe;
import com.daniellsantiago.spaceprobe.domain.exception.BusinessException;
import com.daniellsantiago.spaceprobe.domain.repository.SpaceProbeRepository;
import com.daniellsantiago.spaceprobe.lib.ApplicationService;
import com.daniellsantiago.spaceprobe.lib.exception.DomainException;
import com.daniellsantiago.spaceprobe.lib.exception.NotFoundException;
import com.daniellsantiago.spaceprobe.application.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
public class MoveSpaceProbeUseCase implements
  ApplicationService<ChangeSpaceProbePositionDto, SpaceProbePositionDto> {
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
