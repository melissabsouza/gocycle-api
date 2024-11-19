package fiap.tds.gocycleapi.controller;

import fiap.tds.gocycleapi.dto.AddressDTO;
import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.service.AddressService;
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

//TODO
// PAGEABLE

@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @Tag(name = "GET", description = "GET GET API METHODS")
    @Operation(summary = "List all addresses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List a Address",
            description = "List a Address based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Optional<AddressDTO> addressDTO = addressService.getAddressById(id);
        return addressDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Tag(name = "POST", description = "POST API METHODS")
    @Operation(summary = "Create a address",
            description = "Create a new address")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "409", description = "Address already exists",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {

        try{
            Address newAddress = addressService.saveAddress(addressDTO);
            return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Address already exists", HttpStatus.CONFLICT);
        }
    }

    @Tag(name = "PUT", description = "PUT API METHODS")
    @Operation(summary = "Update a address",
            description = "Update a address based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "404", description = "Profile not found",
                    content = @Content) })
    @PutMapping("/{cpf}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(id, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @Tag(name = "DELETE", description = "DELETE API METHODS")
    @Operation(summary = "Delete a address",
            description = "Delete a address based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>("Address deleted", HttpStatus.NO_CONTENT);
    }
}
