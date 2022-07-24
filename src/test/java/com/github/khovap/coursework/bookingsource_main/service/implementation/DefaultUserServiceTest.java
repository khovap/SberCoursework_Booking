package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.entity.UserEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.ClientToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.mapper.SpecialistToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.repository.ClientRepository;
import com.github.khovap.coursework.bookingsource_main.repository.SpecialistRepository;
import com.github.khovap.coursework.bookingsource_main.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientToEntityMapper clientMapper;

    private DefaultUserService userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userService = new DefaultUserService(userRepository,
                cryptPasswordEncoder,
                clientRepository,
                clientMapper);
    }

    public static UserEntity user;
    public static Client clientInDB;
    public static Client clientNotInDB;
    public static ClientEntity clientEntityInDB;
    public static ClientEntity clientEntityNotInDB;

    @BeforeEach
    public void prepareTestData() {
        user = UserEntity.builder()
                .id(1l)
                .phoneNumber("123457890")
                .password("password")
                .build();
        clientInDB =Client.builder()
                .phoneNumber("1234567890")
                .build();
        clientNotInDB =Client.builder()
                .phoneNumber("")
                .build();
        clientEntityInDB =ClientEntity.builder()
                .phoneNumber("1234567890")
                .build();
        clientEntityNotInDB =ClientEntity.builder()
                .phoneNumber("")
                .build();
    }
    @Test
    void isUserSavedWhenReturnTrue() {
        Mockito.when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.ofNullable(null));
        assertTrue(userService.saveUser(user, clientInDB));
        Mockito.verify(userRepository).save(Mockito.any());
        Mockito.verify(cryptPasswordEncoder).encode(Mockito.anyString());
    }

    @Test
    void isUserServiceReturnFalseIfUserFoundInDB() {
        Mockito.when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.ofNullable(user));
        assertFalse(userService.saveUser(user, clientInDB));
        Mockito.verify((userRepository),Mockito.never()).save(Mockito.any());
        Mockito.verify((cryptPasswordEncoder),Mockito.never()).encode(Mockito.anyString());
    }

    @Test
    void isClientSavedWhenClientNotInDB() {
        Mockito.when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.ofNullable(null));
        Mockito.when(clientRepository.findByPhoneNumberEquals(user.getPhoneNumber())).thenReturn(Optional.ofNullable(null));
        assertTrue(userService.saveUser(user, clientNotInDB));
        Mockito.verify(clientRepository).save(Mockito.any());
    }

    @Test
    void isClientSavedWhenClientInDB() {
        Mockito.when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.ofNullable(null));
        Mockito.when(clientRepository.findByPhoneNumberEquals(user.getPhoneNumber())).thenReturn(Optional.ofNullable(clientEntityInDB));
        assertTrue(userService.saveUser(user, clientNotInDB));
        Mockito.verify((clientRepository),Mockito.never()).save(Mockito.any());
    }

    @Test
    void isUserServiceThrowNPEWhenUserNull() {
        assertThrows(NullPointerException.class, () -> userService.saveUser(null, clientInDB));
    }

    @Test
    void isUserDeletedWhenUserPersistInDB() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertTrue(userService.deleteUser(user.getId()));
        Mockito.verify(userRepository).deleteById(user.getId());
    }

    @Test
    void isUserDeletedWhenUserNotPersistInDB() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(null));
        assertFalse(userService.deleteUser(user.getId()));
        Mockito.verify((userRepository),Mockito.never()).deleteById(Mockito.any());
    }
}