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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/usages")
@AllArgsConstructor
public class UsageController {

    private final UsageService usageService;

    @Tag(name = "POST", description = "POST API METHODS")
    @Operation(summary = "Create a usage",
            description = "Create a new usage")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "409", description = "Usage already exists",
                    content = @Content) })
    @PostMapping("/create")
    public ResponseEntity<EntityModel<Usage>> createUsage(@RequestBody UsageDTO usageDTO) {
        try {
            Usage usage = usageService.saveUsage(usageDTO);
            EntityModel<Usage> usageModel = EntityModel.of(usage);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).getUsageById(usage.getId())).withSelfRel();
            usageModel.add(selfLink);

            Link allUsagesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).getAllUsages()).withRel("all-usages");
            usageModel.add(allUsagesLink);

            return ResponseEntity.status(HttpStatus.CREATED).body(usageModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Tag(name = "PAGEABLE", description = "PAGEABLE API METHODS")
    @Operation(summary = "List all usages in pages")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "usages not found",
                    content = @Content) })
    @GetMapping("/pageable")
    public ResponseEntity<Page<UsageDTO>> read(
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UsageDTO> usages = usageService.findAll(pageable);
        if (usages.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(usages, HttpStatus.OK);
        }
    }

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
    public ResponseEntity<EntityModel<UsageDTO>> getUsageById(@PathVariable Long id) {
        Optional<UsageDTO> usageDTO = usageService.getUsageById(id);
        return usageDTO.map(usage -> {
            EntityModel<UsageDTO> usageModel = EntityModel.of(usage);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).getUsageById(id)).withSelfRel();
            usageModel.add(selfLink);

            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).updateUsage(id, usage)).withRel("update-usage");
            usageModel.add(updateLink);

            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).deleteUsageById(id)).withRel("delete-usage");
            usageModel.add(deleteLink);

            return ResponseEntity.ok(usageModel);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @Tag(name = "POST", description = "POST API METHODS")
//    @Operation(summary = "Create a usage",
//            description = "Create a new usage")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = Usage.class)) }),
//            @ApiResponse(responseCode = "409", description = "Usage already exists",
//                    content = @Content) })
//    @PostMapping
//    public ResponseEntity<?> createUsage(@RequestBody UsageDTO usageDTO) {
//
//
//        try{
//            UsageDTO newUsage = usageService.saveUsage(usageDTO);
//            return new ResponseEntity<>(newUsage, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Usage already exists", HttpStatus.CONFLICT);
//        }
//    }

    @Tag(name = "PUT", description = "PUT API METHODS")
    @Operation(summary = "Update a usage",
            description = "Update a usage based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usage.class)) }),
            @ApiResponse(responseCode = "404", description = "Usage not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsageDTO>> updateUsage(@PathVariable Long id, @RequestBody UsageDTO usageDTO) {
        UsageDTO updatedUsage = usageService.updateUsage(id, usageDTO);
        EntityModel<UsageDTO> usageModel = EntityModel.of(updatedUsage);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).updateUsage(id, usageDTO)).withSelfRel();
        usageModel.add(selfLink);

        Link usageLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsageController.class).getUsageById(id)).withRel("usage");
        usageModel.add(usageLink);

        return ResponseEntity.ok(usageModel);
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
