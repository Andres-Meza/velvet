package com.uniminuto.velvet.model.mapper;

import com.uniminuto.velvet.model.dto.UserDTO;
import com.uniminuto.velvet.model.entity.DocumentType;
import com.uniminuto.velvet.model.entity.Location;
import com.uniminuto.velvet.model.entity.Role;
import com.uniminuto.velvet.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T13:54:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User createUserDtoToUser(UserDTO.CreateUser createUserDto) {
        if ( createUserDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.documentType( documentTypeIdToDocumentType( createUserDto.getDocumentTypeId() ) );
        user.role( roleIdToRole( createUserDto.getRoleId() ) );
        user.location( locationIdToLocation( createUserDto.getLocationId() ) );
        user.username( createUserDto.getUsername() );
        user.password( createUserDto.getPassword() );
        user.name( createUserDto.getName() );
        user.lastName( createUserDto.getLastName() );
        user.email( createUserDto.getEmail() );
        user.document( createUserDto.getDocument() );

        user.active( true );

        return user.build();
    }

    @Override
    public void updateUserFromDto(UserDTO.UpdateUser updateUserDto, User user) {
        if ( updateUserDto == null ) {
            return;
        }

        if ( updateUserDto.getDocumentTypeId() != null ) {
            user.setDocumentType( documentTypeIdToDocumentType( updateUserDto.getDocumentTypeId() ) );
        }
        if ( updateUserDto.getRoleId() != null ) {
            user.setRole( roleIdToRole( updateUserDto.getRoleId() ) );
        }
        if ( updateUserDto.getLocationId() != null ) {
            user.setLocation( locationIdToLocation( updateUserDto.getLocationId() ) );
        }
        if ( updateUserDto.getId() != null ) {
            user.setId( updateUserDto.getId() );
        }
        if ( updateUserDto.getUsername() != null ) {
            user.setUsername( updateUserDto.getUsername() );
        }
        if ( updateUserDto.getName() != null ) {
            user.setName( updateUserDto.getName() );
        }
        if ( updateUserDto.getLastName() != null ) {
            user.setLastName( updateUserDto.getLastName() );
        }
        if ( updateUserDto.getEmail() != null ) {
            user.setEmail( updateUserDto.getEmail() );
        }
        if ( updateUserDto.getDocument() != null ) {
            user.setDocument( updateUserDto.getDocument() );
        }
        if ( updateUserDto.getActive() != null ) {
            user.setActive( updateUserDto.getActive() );
        }
    }

    @Override
    public UserDTO.UserDetails userToUserDetailsDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDetails.UserDetailsBuilder userDetails = UserDTO.UserDetails.builder();

        userDetails.documentTypeName( userDocumentTypeName( user ) );
        userDetails.roleName( userRoleName( user ) );
        userDetails.locationName( userLocationName( user ) );
        userDetails.id( user.getId() );
        userDetails.document( user.getDocument() );
        userDetails.name( user.getName() );
        userDetails.lastName( user.getLastName() );
        userDetails.username( user.getUsername() );
        userDetails.email( user.getEmail() );
        userDetails.createdAt( user.getCreatedAt() );
        userDetails.updatedAt( user.getUpdatedAt() );
        userDetails.lastLogin( user.getLastLogin() );
        userDetails.active( user.getActive() );

        return userDetails.build();
    }

    private String userDocumentTypeName(User user) {
        if ( user == null ) {
            return null;
        }
        DocumentType documentType = user.getDocumentType();
        if ( documentType == null ) {
            return null;
        }
        String name = documentType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String userRoleName(User user) {
        if ( user == null ) {
            return null;
        }
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        String name = role.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String userLocationName(User user) {
        if ( user == null ) {
            return null;
        }
        Location location = user.getLocation();
        if ( location == null ) {
            return null;
        }
        String name = location.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
