package fiap.tds.gocycleapi.controller;

import fiap.tds.gocycleapi.dto.UserDTO;
import fiap.tds.gocycleapi.model.User;
import fiap.tds.gocycleapi.service.UserService;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Tag(name = "PAGEABLE", description = "PAGEABLE API METHODS")
    @Operation(summary = "List all users in pages")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "users not found",
                    content = @Content) })
    @GetMapping("/pageable")
    public ResponseEntity<Page<UserDTO>> read(
            @PageableDefault(size = 3, sort = "email", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserDTO> users = userService.findAll(pageable);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "users not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Tag(name = "GET", description = "GET API METHODS")
    @Operation(summary = "List a user",
            description = "List a user based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> searchUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.getUserById(id);
        return userDTO.map(user -> {
            EntityModel<UserDTO> userModel = EntityModel.of(user);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).searchUserById(id)).withSelfRel();
            userModel.add(selfLink);

            Link allUsersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all-users");
            userModel.add(allUsersLink);

            Link updateUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUser(id, user)).withRel("update-user");
            userModel.add(updateUserLink);

            Link deleteUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUserById(id)).withRel("delete-user");
            userModel.add(deleteUserLink);

            return ResponseEntity.ok(userModel);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Tag(name = "POST", description = "POST API METHODS")
    @Operation(summary = "Create a user",
            description = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {

        try{
            User newUser = userService.saveUser(userDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @Tag(name = "PUT", description = "PUT API METHODS")
    @Operation(summary = "Update a user",
            description = "Update a user based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        EntityModel<UserDTO> userModel = EntityModel.of(updatedUser);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUser(id, userDTO)).withSelfRel();
        userModel.add(selfLink);

        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).searchUserById(id)).withRel("user");
        userModel.add(userLink);

        return ResponseEntity.ok(userModel);
    }

    @Tag(name = "DELETE", description = "DELETE API METHODS")
    @Operation(summary = "Delete a user",
            description = "Delete a user based on their id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }


}
