package com.elo7.spaceprobe.interfaces.http;

import com.elo7.spaceprobe.application.usecases.LandSpaceProbeUseCase;
import com.elo7.spaceprobe.interfaces.http.dto.LandSpaceProbeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/spaceProbe")
public class SpaceProbeController {
    private final LandSpaceProbeUseCase landSpaceProbeUseCase;

    public SpaceProbeController(LandSpaceProbeUseCase landSpaceProbeUseCase) {
        this.landSpaceProbeUseCase = landSpaceProbeUseCase;
    }

    @PostMapping("/{spaceProbeId}/landing")
    @ResponseStatus(HttpStatus.CREATED)
    public void spaceProbeLanding(
        @PathVariable UUID spaceProbeId,
        @Valid @RequestBody LandSpaceProbeRequest request
    ) {
        landSpaceProbeUseCase.execute(request.toDto(spaceProbeId));
    }
}
