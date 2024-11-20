package fiap.tds.gocycleapi.controller;

import fiap.tds.gocycleapi.dto.UsageDTO;
import fiap.tds.gocycleapi.model.Usage;
import fiap.tds.gocycleapi.service.UsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO PAGEABLE
// TODO LOGICA DOS PONTOS
// TODO HATEOAS

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

    @Tag(name = "POST", description = "POST API METHODS")
    @Operation(summary = "Create a usage",
            description = "Create a new usage")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "409", description = "Usage already exists",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody UsageDTO usageDTO) {

        try{
            UsageDTO newUsage = usageService.saveUsage(usageDTO);
            return new ResponseEntity<>(newUsage, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Usage already exists", HttpStatus.CONFLICT);
        }
    }

    @Tag(name = "PUT", description = "PUT API METHODS")
    @Operation(summary = "Update a usage",
            description = "Update a usage based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "Usage not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<UsageDTO> updateUsage(@PathVariable Long id, @RequestBody UsageDTO usageDTO) {
        UsageDTO updatedUsage = usageService.updateUsage(id, usageDTO);
        return new ResponseEntity<>(updatedUsage, HttpStatus.OK);
    }



    @Tag(name = "DELETE", description = "DELETE API METHODS")
    @Operation(summary = "Delete a usage",
            description = "Delete a usage based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "Usage not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsageById(@PathVariable Long id) {
        usageService.deleteUsageById(id);
        return new ResponseEntity<>("Usage deleted", HttpStatus.NO_CONTENT);
    }
}