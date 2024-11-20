package fiap.tds.gocycleapi.controller;

import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.dto.UsageDTO;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.model.Usage;
import fiap.tds.gocycleapi.service.UsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//TODO PAGEABLE

@RestController
@RequestMapping("/usages")
@AllArgsConstructor
public class UsageController {

    private final UsageService usageService;

    @Tag(name = "GET", description = "GET GET API METHODS")
    @Operation(summary = "List all Usages")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "Usages not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<UsageDTO>> getAllUsages() {
        return ResponseEntity.ok(usageService.getAllUsages());
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List a Usage",
            description = "List a Usage based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "Usage not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<UsageDTO> getUsageById(@PathVariable Long id) {
        Optional<UsageDTO> usageDTO = usageService.getUsageById(id);
        return usageDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


}
