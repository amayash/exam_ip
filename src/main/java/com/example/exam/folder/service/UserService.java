package com.example.exam.folder.service;

import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.model.UserSignupDto;
import com.example.exam.folder.repository.UserRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validatorUtil = validatorUtil;
    }

    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    @Transactional
    public User addUser(String login, String password, String passwordConfirm) {
        return createUser(login, password, passwordConfirm, UserRole.USER);
    }

    @Transactional
    public User addUser(UserSignupDto userSignupDto) {
        if (findByLogin(userSignupDto.getLogin()) != null) {
            throw new ValidationException(String.format("User '%s' already exists", userSignupDto.getLogin()));
        }
        if (!Objects.equals(userSignupDto.getPassword(), userSignupDto.getPasswordConfirm())) {
            throw new ValidationException("Passwords not equals");
        }
        final User user = new User(userSignupDto);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }

    public User createUser(String login, String password, String passwordConfirm, UserRole role) {
        final User user = new User(login, passwordEncoder.encode(password), role);
        validatorUtil.validate(user);
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equals");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> User = userRepository.findById(id);
        return User.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, String login, String password) {
        final User currentUser = findUser(id);
        currentUser.setLogin(login);
        currentUser.setPassword(passwordEncoder.encode(password));
        validatorUtil.validate(currentUser);
        return userRepository.save(currentUser);
    }

    @Transactional
    public User deleteUser(Long id) {
        final User User = findUser(id);
        userRepository.deleteById(id);
        return User;
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}
