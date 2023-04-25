//package ro.itschool.service.impl;//package ro.itschool.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PathVariable;
//import ro.itschool.entity.User;
//import ro.itschool.exceptions.UserNotFoundException;
//import ro.itschool.repository.LikesRepository;
//import ro.itschool.repository.UserRepository;
//import ro.itschool.service.LikesService;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class LikesServiceImpl implements LikesService {
//
//  final private LikesRepository likesRepository;
//  final private UserRepository userRepository;
//
//  @Override
//  public void likePost(Long postId) {
//    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    Optional<User> optionalLoggedInUser = userRepository.findById(principal.getId());
//    likesRepository.likePost(optionalLoggedInUser.get().getId(), postId);
//  }
//
//  @Override
//  public void unlikePost(Long postId) {
//    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    Optional<User> optionalLoggedInUser = userRepository.findByUsername(principal.getUsername());
//    likesRepository.unlikePost(optionalLoggedInUser.get().getId(), postId);
//  }
//
//
//}

