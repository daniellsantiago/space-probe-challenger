package com.elo7.spaceprobe.interfaces.http;

import com.elo7.spaceprobe.application.usecases.LandSpaceProbeUseCase;
import com.elo7.spaceprobe.application.usecases.MoveSpaceProbeUseCase;
import com.elo7.spaceprobe.interfaces.http.dto.ChangeSpaceProbePositionResponse;
import com.elo7.spaceprobe.interfaces.http.dto.LandSpaceProbeRequest;
import com.elo7.spaceprobe.interfaces.http.dto.ChangeSpaceProbePositionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/spaceProbe")
public class SpaceProbeController {
    private final LandSpaceProbeUseCase landSpaceProbeUseCase;
    private final MoveSpaceProbeUseCase moveSpaceProbeUseCase;

    public SpaceProbeController(LandSpaceProbeUseCase landSpaceProbeUseCase, MoveSpaceProbeUseCase moveSpaceProbeUseCase) {
        this.landSpaceProbeUseCase = landSpaceProbeUseCase;
        this.moveSpaceProbeUseCase = moveSpaceProbeUseCase;
    }

    @PostMapping("/{spaceProbeId}/landing")
    @ResponseStatus(HttpStatus.CREATED)
    public void spaceProbeLanding(
        @PathVariable UUID spaceProbeId,
        @Valid @RequestBody LandSpaceProbeRequest request
    ) {
        landSpaceProbeUseCase.execute(request.toDto(spaceProbeId));
    }

    @PutMapping("/{spaceProbeId}/position")
    public ChangeSpaceProbePositionResponse moveSpaceProbe(
        @PathVariable UUID spaceProbeId,
        @Valid @RequestBody ChangeSpaceProbePositionRequest request
    ) {
        return ChangeSpaceProbePositionResponse.fromDto(moveSpaceProbeUseCase.execute(request.toDto(spaceProbeId)));
    }
}
