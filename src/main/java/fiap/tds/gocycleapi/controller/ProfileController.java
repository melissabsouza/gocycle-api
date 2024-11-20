package fiap.tds.gocycleapi.controller;

import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.model.Profile;
import fiap.tds.gocycleapi.repository.ProfileRepository;
import fiap.tds.gocycleapi.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// TODO HATEOAS

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @Tag(name = "PAGEABLE", description = "PAGEABLE API METHODS")
    @Operation(summary = "List all profiles in pages")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "profiles not found",
                    content = @Content) })
    @GetMapping("/pageable")
    public ResponseEntity<Page<ProfileDTO>> read(
            @PageableDefault(size = 3, sort = "cpf", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ProfileDTO> profiles = profileService.findAll(pageable);
        if (profiles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(profiles, HttpStatus.OK);
        }
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List all profiles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "profiles not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List a Profile",
            description = "List a Profile based on their cpf")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "Profile not found",
                    content = @Content) })
    @GetMapping("/{cpf}")
    public ResponseEntity<ProfileDTO> getProfileByCpf(@PathVariable String cpf) {
        Optional<ProfileDTO> profileDTO = profileService.getProfileByCpf(cpf);
        return profileDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Tag(name = "POST", description = "POST API METHODS")
    @Operation(summary = "Create a profile",
            description = "Create a new profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "409", description = "Profile already exists",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO profileDTO) {

        try{
            ProfileDTO newProfile = profileService.saveProfile(profileDTO);
            return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Profile already exists", HttpStatus.CONFLICT);
        }
    }

    @Tag(name = "PUT", description = "PUT API METHODS")
    @Operation(summary = "Update a profile",
            description = "Update a profile based on their cpf")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "Profile not found",
                    content = @Content) })
    @PutMapping("/{cpf}")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable String cpf, @RequestBody ProfileDTO profileDTO) {
        ProfileDTO updatedProfile = profileService.updateProfile(cpf, profileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @Tag(name = "DELETE", description = "DELETE API METHODS")
    @Operation(summary = "Delete a profile",
            description = "Delete a profile based on their cpf")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "Profile not found",
                    content = @Content) })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteProfileByCpf(@PathVariable String cpf) {
        profileService.deleteProfileByCpf(cpf);
        return new ResponseEntity<>("Profile deleted", HttpStatus.NO_CONTENT);
    }


}
