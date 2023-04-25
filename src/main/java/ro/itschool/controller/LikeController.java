//package ro.itschool.controller;//package ro.itschool.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ro.itschool.entity.User;
//import ro.itschool.exceptions.UserNotFoundException;
//import ro.itschool.service.LikesService;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping(value = "/likes")
//@RequiredArgsConstructor
//public class LikeController {
//    private final LikesService likesService;
//
//    @PostMapping(value = "/like/{id}")
//    public void likePost(@PathVariable Long id){
//    likesService.likePost(id);
//    }
//
//    @DeleteMapping(value = "/unlike/{id}")
//    public void unlikePost(@PathVariable Long id) {
//        likesService.unlikePost(id);
//
//    }
//}
//
